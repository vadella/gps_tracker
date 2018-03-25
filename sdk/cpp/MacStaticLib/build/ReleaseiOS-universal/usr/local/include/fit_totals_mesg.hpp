////////////////////////////////////////////////////////////////////////////////
// The following FIT Protocol software provided may be used with FIT protocol
// devices only and remains the copyrighted property of Dynastream Innovations Inc.
// The software is being provided on an "as-is" basis and as an accommodation,
// and therefore all warranties, representations, or guarantees of any kind
// (whether express, implied or statutory) including, without limitation,
// warranties of merchantability, non-infringement, or fitness for a particular
// purpose, are specifically disclaimed.
//
// Copyright 2018 Dynastream Innovations Inc.
////////////////////////////////////////////////////////////////////////////////
// ****WARNING****  This file is auto-generated!  Do NOT edit this file.
// Profile Version = 20.62Release
// Tag = production/akw/20.62.00-0-gac8709a
////////////////////////////////////////////////////////////////////////////////


#if !defined(FIT_TOTALS_MESG_HPP)
#define FIT_TOTALS_MESG_HPP

#include "fit_mesg.hpp"

namespace fit
{

class TotalsMesg : public Mesg
{
public:
    class FieldDefNum final
    {
    public:
       static const FIT_UINT8 MessageIndex = 254;
       static const FIT_UINT8 Timestamp = 253;
       static const FIT_UINT8 TimerTime = 0;
       static const FIT_UINT8 Distance = 1;
       static const FIT_UINT8 Calories = 2;
       static const FIT_UINT8 Sport = 3;
       static const FIT_UINT8 ElapsedTime = 4;
       static const FIT_UINT8 Sessions = 5;
       static const FIT_UINT8 ActiveTime = 6;
       static const FIT_UINT8 SportIndex = 9;
       static const FIT_UINT8 Invalid = FIT_FIELD_NUM_INVALID;
    };

    TotalsMesg(void) : Mesg(Profile::MESG_TOTALS)
    {
    }

    TotalsMesg(const Mesg &mesg) : Mesg(mesg)
    {
    }

    ///////////////////////////////////////////////////////////////////////
    // Checks the validity of message_index field
    // Returns FIT_TRUE if field is valid
    ///////////////////////////////////////////////////////////////////////
    FIT_BOOL IsMessageIndexValid() const
    {
        const Field* field = GetField(254);
        if( FIT_NULL == field )
        {
            return FIT_FALSE;
        }

        return field->IsValueValid();
    }

    ///////////////////////////////////////////////////////////////////////
    // Returns message_index field
    ///////////////////////////////////////////////////////////////////////
    FIT_MESSAGE_INDEX GetMessageIndex(void) const
    {
        return GetFieldUINT16Value(254, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

    ///////////////////////////////////////////////////////////////////////
    // Set message_index field
    ///////////////////////////////////////////////////////////////////////
    void SetMessageIndex(FIT_MESSAGE_INDEX messageIndex)
    {
        SetFieldUINT16Value(254, messageIndex, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

    ///////////////////////////////////////////////////////////////////////
    // Checks the validity of timestamp field
    // Returns FIT_TRUE if field is valid
    ///////////////////////////////////////////////////////////////////////
    FIT_BOOL IsTimestampValid() const
    {
        const Field* field = GetField(253);
        if( FIT_NULL == field )
        {
            return FIT_FALSE;
        }

        return field->IsValueValid();
    }

    ///////////////////////////////////////////////////////////////////////
    // Returns timestamp field
    // Units: s
    ///////////////////////////////////////////////////////////////////////
    FIT_DATE_TIME GetTimestamp(void) const
    {
        return GetFieldUINT32Value(253, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

    ///////////////////////////////////////////////////////////////////////
    // Set timestamp field
    // Units: s
    ///////////////////////////////////////////////////////////////////////
    void SetTimestamp(FIT_DATE_TIME timestamp)
    {
        SetFieldUINT32Value(253, timestamp, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

    ///////////////////////////////////////////////////////////////////////
    // Checks the validity of timer_time field
    // Returns FIT_TRUE if field is valid
    ///////////////////////////////////////////////////////////////////////
    FIT_BOOL IsTimerTimeValid() const
    {
        const Field* field = GetField(0);
        if( FIT_NULL == field )
        {
            return FIT_FALSE;
        }

        return field->IsValueValid();
    }

    ///////////////////////////////////////////////////////////////////////
    // Returns timer_time field
    // Units: s
    // Comment: Excludes pauses
    ///////////////////////////////////////////////////////////////////////
    FIT_UINT32 GetTimerTime(void) const
    {
        return GetFieldUINT32Value(0, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

    ///////////////////////////////////////////////////////////////////////
    // Set timer_time field
    // Units: s
    // Comment: Excludes pauses
    ///////////////////////////////////////////////////////////////////////
    void SetTimerTime(FIT_UINT32 timerTime)
    {
        SetFieldUINT32Value(0, timerTime, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

    ///////////////////////////////////////////////////////////////////////
    // Checks the validity of distance field
    // Returns FIT_TRUE if field is valid
    ///////////////////////////////////////////////////////////////////////
    FIT_BOOL IsDistanceValid() const
    {
        const Field* field = GetField(1);
        if( FIT_NULL == field )
        {
            return FIT_FALSE;
        }

        return field->IsValueValid();
    }

    ///////////////////////////////////////////////////////////////////////
    // Returns distance field
    // Units: m
    ///////////////////////////////////////////////////////////////////////
    FIT_UINT32 GetDistance(void) const
    {
        return GetFieldUINT32Value(1, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

    ///////////////////////////////////////////////////////////////////////
    // Set distance field
    // Units: m
    ///////////////////////////////////////////////////////////////////////
    void SetDistance(FIT_UINT32 distance)
    {
        SetFieldUINT32Value(1, distance, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

    ///////////////////////////////////////////////////////////////////////
    // Checks the validity of calories field
    // Returns FIT_TRUE if field is valid
    ///////////////////////////////////////////////////////////////////////
    FIT_BOOL IsCaloriesValid() const
    {
        const Field* field = GetField(2);
        if( FIT_NULL == field )
        {
            return FIT_FALSE;
        }

        return field->IsValueValid();
    }

    ///////////////////////////////////////////////////////////////////////
    // Returns calories field
    // Units: kcal
    ///////////////////////////////////////////////////////////////////////
    FIT_UINT32 GetCalories(void) const
    {
        return GetFieldUINT32Value(2, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

    ///////////////////////////////////////////////////////////////////////
    // Set calories field
    // Units: kcal
    ///////////////////////////////////////////////////////////////////////
    void SetCalories(FIT_UINT32 calories)
    {
        SetFieldUINT32Value(2, calories, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

    ///////////////////////////////////////////////////////////////////////
    // Checks the validity of sport field
    // Returns FIT_TRUE if field is valid
    ///////////////////////////////////////////////////////////////////////
    FIT_BOOL IsSportValid() const
    {
        const Field* field = GetField(3);
        if( FIT_NULL == field )
        {
            return FIT_FALSE;
        }

        return field->IsValueValid();
    }

    ///////////////////////////////////////////////////////////////////////
    // Returns sport field
    ///////////////////////////////////////////////////////////////////////
    FIT_SPORT GetSport(void) const
    {
        return GetFieldENUMValue(3, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

    ///////////////////////////////////////////////////////////////////////
    // Set sport field
    ///////////////////////////////////////////////////////////////////////
    void SetSport(FIT_SPORT sport)
    {
        SetFieldENUMValue(3, sport, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

    ///////////////////////////////////////////////////////////////////////
    // Checks the validity of elapsed_time field
    // Returns FIT_TRUE if field is valid
    ///////////////////////////////////////////////////////////////////////
    FIT_BOOL IsElapsedTimeValid() const
    {
        const Field* field = GetField(4);
        if( FIT_NULL == field )
        {
            return FIT_FALSE;
        }

        return field->IsValueValid();
    }

    ///////////////////////////////////////////////////////////////////////
    // Returns elapsed_time field
    // Units: s
    // Comment: Includes pauses
    ///////////////////////////////////////////////////////////////////////
    FIT_UINT32 GetElapsedTime(void) const
    {
        return GetFieldUINT32Value(4, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

    ///////////////////////////////////////////////////////////////////////
    // Set elapsed_time field
    // Units: s
    // Comment: Includes pauses
    ///////////////////////////////////////////////////////////////////////
    void SetElapsedTime(FIT_UINT32 elapsedTime)
    {
        SetFieldUINT32Value(4, elapsedTime, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

    ///////////////////////////////////////////////////////////////////////
    // Checks the validity of sessions field
    // Returns FIT_TRUE if field is valid
    ///////////////////////////////////////////////////////////////////////
    FIT_BOOL IsSessionsValid() const
    {
        const Field* field = GetField(5);
        if( FIT_NULL == field )
        {
            return FIT_FALSE;
        }

        return field->IsValueValid();
    }

    ///////////////////////////////////////////////////////////////////////
    // Returns sessions field
    ///////////////////////////////////////////////////////////////////////
    FIT_UINT16 GetSessions(void) const
    {
        return GetFieldUINT16Value(5, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

    ///////////////////////////////////////////////////////////////////////
    // Set sessions field
    ///////////////////////////////////////////////////////////////////////
    void SetSessions(FIT_UINT16 sessions)
    {
        SetFieldUINT16Value(5, sessions, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

    ///////////////////////////////////////////////////////////////////////
    // Checks the validity of active_time field
    // Returns FIT_TRUE if field is valid
    ///////////////////////////////////////////////////////////////////////
    FIT_BOOL IsActiveTimeValid() const
    {
        const Field* field = GetField(6);
        if( FIT_NULL == field )
        {
            return FIT_FALSE;
        }

        return field->IsValueValid();
    }

    ///////////////////////////////////////////////////////////////////////
    // Returns active_time field
    // Units: s
    ///////////////////////////////////////////////////////////////////////
    FIT_UINT32 GetActiveTime(void) const
    {
        return GetFieldUINT32Value(6, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

    ///////////////////////////////////////////////////////////////////////
    // Set active_time field
    // Units: s
    ///////////////////////////////////////////////////////////////////////
    void SetActiveTime(FIT_UINT32 activeTime)
    {
        SetFieldUINT32Value(6, activeTime, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

    ///////////////////////////////////////////////////////////////////////
    // Checks the validity of sport_index field
    // Returns FIT_TRUE if field is valid
    ///////////////////////////////////////////////////////////////////////
    FIT_BOOL IsSportIndexValid() const
    {
        const Field* field = GetField(9);
        if( FIT_NULL == field )
        {
            return FIT_FALSE;
        }

        return field->IsValueValid();
    }

    ///////////////////////////////////////////////////////////////////////
    // Returns sport_index field
    ///////////////////////////////////////////////////////////////////////
    FIT_UINT8 GetSportIndex(void) const
    {
        return GetFieldUINT8Value(9, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

    ///////////////////////////////////////////////////////////////////////
    // Set sport_index field
    ///////////////////////////////////////////////////////////////////////
    void SetSportIndex(FIT_UINT8 sportIndex)
    {
        SetFieldUINT8Value(9, sportIndex, 0, FIT_SUBFIELD_INDEX_MAIN_FIELD);
    }

};

} // namespace fit

#endif // !defined(FIT_TOTALS_MESG_HPP)
