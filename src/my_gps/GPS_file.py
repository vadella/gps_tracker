from collections import defaultdict
import re

from fitparse import FitFile
import folium
import pandas as pd


def _load_file(fit_file):
    d = defaultdict(list)
    pat = re.compile('<MessageType: (\w+) \(#\d+\)>')
    for m in fit_file.messages:
        d[pat.match(str(m.mesg_type))[1]].append(m)
    return d

def _get_records(messages) -> pd.DataFrame:
    l = [i.get_values() for i in messages['record']]
    df = pd.DataFrame.from_records(l).dropna(how='all', axis='columns').set_index('timestamp')
    return df

def get_laps(messages):
    return [i.get_values() for i in messages['lap']]


class myFitFile:
    def __init__(self, filename):
        self.filename = filename

    def load(self):
        self._fit_file = FitFile(self.filename)
        messages = _load_file(self._fit_file)
        self._records = _get_records(messages)
        self._laps = messages['lap']
        self._timestamp_correlation = messages['timestamp_correlation'][0]

    def __enter__(self):
        self.load()
        return self

    def __exit__(self, *args):
        self.close()

    def close(self):
        self._fit_file.close()

    @property
    def data(self) -> pd.DataFrame:
        return self._records.copy()

    @property
    def route(self) -> pd.DataFrame:
        return self.data[['position_lat', 'position_long']] * 180 / 2 ** 31

    def map(self):
        try:
            center_x, center_y = self.route.mean()
        except KeyError:
            return None
        m = folium.Map(
            location=[center_x, center_y],
            zoom_start=13,
            #     tiles='Bertem',
            tiles='http://toolserver.org/tiles/hikebike/{z}/{x}/{y}.png',
            #     tiles='http://a.tile.thunderforest.com/outdoors/{z}/{x}/{y}.png',
            #     tiles='http://a.tile.opencyclemap.org/cycle/{z}/{x}/{y}.png',
            attr='test',
        )
        folium.PolyLine(list(map(list, self.route.values))).add_to(m)

        return m