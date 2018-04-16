import datetime
import re
from collections import defaultdict

import pandas as pd
from bokeh.layouts import Column
from bokeh.models import ColumnDataSource, HoverTool
from bokeh.models.tiles import WMTSTileSource
from bokeh.plotting import figure
from fitparse import FitFile
from pyproj import Proj

URL_OSM = 'https://www.openstreetmap.org/copyright'
URL_FIXMAP = 'https://www.openstreetmap.org/fixthemap'
DEFAULT_ATTRIB = f'&copy; <a href="{URL_OSM}">OpenStreetMap</a> <a href="{URL_FIXMAP}">contributors</a>'

TILE_SOURCES = {
    'hikebike': {
        'url': 'http://toolserver.org/tiles/hikebike/{z}/{x}/{y}.png',
        'attrib': DEFAULT_ATTRIB
    },
    'thuderforest': {
        'url': 'http://a.tile.thunderforest.com/outdoors/{z}/{x}/{y}.png',
        'attrib': DEFAULT_ATTRIB
    },
    'opencyclemap': {
        'url': 'http://a.tile.opencyclemap.org/cycle/{z}/{x}/{y}.png',
        'attrib': DEFAULT_ATTRIB
    },
}


class MyFitFileBase:

    def __init__(self, filename):
        fit_file = FitFile(filename)
        messages = _parse_messages(fit_file)

        time_dif = _parse_timediff(messages)

        self._data = _parse_records(messages, time_dif)
        self._messages = messages
        self._datasource = ColumnDataSource(self._data)

    @property
    def data(self) -> pd.DataFrame:
        return self._data.copy()

    @property
    def datasource(self):
        return self._datasource


class MyFitFileTrack(MyFitFileBase):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        route = _parse_route(self.data)
        self._data = pd.concat((self.data, route), axis=1)

    def route(self, projection=None):
        labels = 'lat_degrees', 'lon_degrees'
        if projection == 'latlong':
            labels = 'lat_degrees', 'lon_degrees'
        elif projection == 'mercator':
            labels = 'lat_mercator', 'mercator'
        return self.data[[*labels]]


class BokehMixin:
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self._data_resampled = self.data.resample('5s').agg(lambda x: x.mean())
        self._datasource = ColumnDataSource(self._data_resampled)

        self._hovertool_settings = dict(
            tooltips=[
                ("timestamp", "@timestamp{%F %T}"),
                ("(lat, lon)", "(@lat_degrees, @lon_degrees)"),
                ('time since start', '@time_start{%T}'),
                ("speed", "@speed_km{%0.2f}"),
                ("cadence", "@cadence"),
                ("heart_rate", "@heart_rate"),
                ('altitude', '@enhanced_altitude')
            ],
            formatters={
                'timestamp': 'datetime',  # use 'datetime' formatter for 'date' field
                'time_start': 'datetime',  #
                'speed_km': 'printf',  # use 'printf' formatter for 'adj close' field
                # use default 'numeral' formatter for other fields
            },
            mode="vline",
        )

        try:
            coord_min = self.data[['lat_mercator', 'lon_mercator']].min()
            coord_max = self.data[['lat_mercator', 'lon_mercator']].max()
            self._coord_limits = tuple(zip(*(coord_min, coord_max)))
        except KeyError:
            self._coord_limits = None

    def route_map(self, source='hikebike'):
        if self._coord_limits is None:
            return None

        x_limit, y_limit = self._coord_limits
        tilesource = _get_tilesource(source)
        p = figure(x_range=y_limit, y_range=x_limit,
                   x_axis_type="mercator", y_axis_type="mercator")
        p.add_tools(self.hovertool(mode='mouse'))
        p.add_tile(tilesource)
        p.scatter('lon_mercator', 'lat_mercator', source=self._datasource, hover_color="firebrick", size=1)
        return p

    def hovertool(self, **kwargs):
        settings = self._hovertool_settings.copy()
        settings.update(kwargs)
        return HoverTool(**settings)

    def plots(self, x_label='timestamp', subjects=('speed', 'cadence', 'heart_rate', 'altitude'), include_map=True):

        y_labels = {'speed': 'speed_km'}
        source = self._datasource
        x_axis_types = {
            'timestamp': 'datetime'
        }

        x_range = None
        plots = [self.route_map()] if (include_map and self._coord_limits) else []
        for subject in subjects:
            p = figure(
                x_axis_type=x_axis_types.get(x_label, 'linear'),
                x_range=x_range,
                title=subject,
                title_location="left",
                plot_width=800,
                plot_height=300
            )
            p.add_tools(self.hovertool())
            x_range = p.x_range if x_range is None else x_range
            p.scatter(x_label, y_labels.get(subject, subject), source=source, hover_color="firebrick", size=1, )
            plots.append(p)
        return Column(*plots)


class MyFitFileTrackMap(BokehMixin, MyFitFileTrack):
    pass


def _get_tilesource(source='hikebike'):
    src = TILE_SOURCES[source]
    return WMTSTileSource(
        url=src['url'],
        attribution=src['attrib'],
    )


def _parse_route(data: pd.DataFrame) -> pd.DataFrame:
    try:
        route = _transform_coord(data)
        factor = 180 / 2 ** 31
        route[['lat_degrees', 'lon_degrees']] = data[['position_lat', 'position_long']] * factor
    except KeyError:
        route = None
    return route


def _parse_timediff(messages) -> datetime.timedelta:
    time_info = messages['timestamp_correlation'][0].get_values()
    return time_info['local_timestamp'] - time_info['timestamp']


def _parse_messages(fit_file: FitFile) -> dict:
    """distributes all the messages according to their message type"""
    messages = defaultdict(list)
    pat = re.compile('<MessageType: (\w+) \(#\d+\)>')
    for message in fit_file.messages:
        messages[pat.match(str(message.mesg_type))[1]].append(message)
    return dict(messages)


def _parse_records(messages: dict, time_dif: datetime.timedelta = None) -> pd.DataFrame:
    """selects the records messages and turns it into a DataFrame"""
    records = [i.get_values() for i in messages['record']]
    df = pd.DataFrame.from_records(records)
    if time_dif:
        df['timestamp'] = df['timestamp'] + time_dif
    df['speed_km'] = df['speed'] * 3.6
    df['time_start'] = df['timestamp'] - df['timestamp'].iloc[0]
    return df.dropna(how='all', axis='columns').set_index('timestamp')


def _transform_coord(data: pd.DataFrame) -> pd.DataFrame:
    p = Proj(init='epsg:3857')
    factor = 180 / 2 ** 31
    label_lat, label_lon = 'position_lat', 'position_long'
    lat, lon = (data[label_lat] * factor).values, (data[label_lon] * factor).values
    #     lat_trans, lon_trans = transform(p2, p, lon, lat)
    lon_trans, lat_trans, = p(lon, lat)
    return pd.DataFrame(data={'lat_mercator': lat_trans, 'lon_mercator': lon_trans}, index=data.index)
