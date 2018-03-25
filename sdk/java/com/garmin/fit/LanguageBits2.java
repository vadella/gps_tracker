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


package com.garmin.fit;

import java.util.HashMap;
import java.util.Map;

public class LanguageBits2 {
    public static final short SLOVENIAN = 0x01;
    public static final short SWEDISH = 0x02;
    public static final short RUSSIAN = 0x04;
    public static final short TURKISH = 0x08;
    public static final short LATVIAN = 0x10;
    public static final short UKRAINIAN = 0x20;
    public static final short ARABIC = 0x40;
    public static final short FARSI = 0x80;
    public static final short INVALID = Fit.UINT8Z_INVALID;

    private static final Map<Short, String> stringMap;

    static {
        stringMap = new HashMap<Short, String>();
        stringMap.put(SLOVENIAN, "SLOVENIAN");
        stringMap.put(SWEDISH, "SWEDISH");
        stringMap.put(RUSSIAN, "RUSSIAN");
        stringMap.put(TURKISH, "TURKISH");
        stringMap.put(LATVIAN, "LATVIAN");
        stringMap.put(UKRAINIAN, "UKRAINIAN");
        stringMap.put(ARABIC, "ARABIC");
        stringMap.put(FARSI, "FARSI");
    }


    /**
     * Retrieves the String Representation of the Value
     * @return The string representation of the value, or empty if unknown
     */
    public static String getStringFromValue( Short value ) {
        if( stringMap.containsKey( value ) ) {
            return stringMap.get( value );
        }

        return "";
    }

    /**
     * Retrieves a value given a string representation
     * @return The value or INVALID if unkwown
     */
    public static Short getValueFromString( String value ) {
        for( Map.Entry<Short, String> entry : stringMap.entrySet() ) {
            if( entry.getValue().equals( value ) ) {
                return entry.getKey();
            }
        }

        return INVALID;
    }

}
