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
import java.math.BigInteger;


public class MagnetometerDataMesg extends Mesg {

   
   public static final int TimestampFieldNum = 253;
   
   public static final int TimestampMsFieldNum = 0;
   
   public static final int SampleTimeOffsetFieldNum = 1;
   
   public static final int MagXFieldNum = 2;
   
   public static final int MagYFieldNum = 3;
   
   public static final int MagZFieldNum = 4;
   
   public static final int CalibratedMagXFieldNum = 5;
   
   public static final int CalibratedMagYFieldNum = 6;
   
   public static final int CalibratedMagZFieldNum = 7;
   

   protected static final  Mesg magnetometerDataMesg;
   static {
      // magnetometer_data
      magnetometerDataMesg = new Mesg("magnetometer_data", MesgNum.MAGNETOMETER_DATA);
      magnetometerDataMesg.addField(new Field("timestamp", TimestampFieldNum, 134, 1, 0, "s", false, Profile.Type.DATE_TIME));
      
      magnetometerDataMesg.addField(new Field("timestamp_ms", TimestampMsFieldNum, 132, 1, 0, "ms", false, Profile.Type.UINT16));
      
      magnetometerDataMesg.addField(new Field("sample_time_offset", SampleTimeOffsetFieldNum, 132, 1, 0, "ms", false, Profile.Type.UINT16));
      
      magnetometerDataMesg.addField(new Field("mag_x", MagXFieldNum, 132, 1, 0, "counts", false, Profile.Type.UINT16));
      
      magnetometerDataMesg.addField(new Field("mag_y", MagYFieldNum, 132, 1, 0, "counts", false, Profile.Type.UINT16));
      
      magnetometerDataMesg.addField(new Field("mag_z", MagZFieldNum, 132, 1, 0, "counts", false, Profile.Type.UINT16));
      
      magnetometerDataMesg.addField(new Field("calibrated_mag_x", CalibratedMagXFieldNum, 136, 1, 0, "G", false, Profile.Type.FLOAT32));
      
      magnetometerDataMesg.addField(new Field("calibrated_mag_y", CalibratedMagYFieldNum, 136, 1, 0, "G", false, Profile.Type.FLOAT32));
      
      magnetometerDataMesg.addField(new Field("calibrated_mag_z", CalibratedMagZFieldNum, 136, 1, 0, "G", false, Profile.Type.FLOAT32));
      
   }

   public MagnetometerDataMesg() {
      super(Factory.createMesg(MesgNum.MAGNETOMETER_DATA));
   }

   public MagnetometerDataMesg(final Mesg mesg) {
      super(mesg);
   }


   /**
    * Get timestamp field
    * Units: s
    * Comment: Whole second part of the timestamp
    *
    * @return timestamp
    */
   public DateTime getTimestamp() {
      return timestampToDateTime(getFieldLongValue(253, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD));
   }

   /**
    * Set timestamp field
    * Units: s
    * Comment: Whole second part of the timestamp
    *
    * @param timestamp
    */
   public void setTimestamp(DateTime timestamp) {
      setFieldValue(253, 0, timestamp.getTimestamp(), Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get timestamp_ms field
    * Units: ms
    * Comment: Millisecond part of the timestamp.
    *
    * @return timestamp_ms
    */
   public Integer getTimestampMs() {
      return getFieldIntegerValue(0, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Set timestamp_ms field
    * Units: ms
    * Comment: Millisecond part of the timestamp.
    *
    * @param timestampMs
    */
   public void setTimestampMs(Integer timestampMs) {
      setFieldValue(0, 0, timestampMs, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   public Integer[] getSampleTimeOffset() {
      
      return getFieldIntegerValues(1, Fit.SUBFIELD_INDEX_MAIN_FIELD);
      
   }

   /**
    * @return number of sample_time_offset
    */
   public int getNumSampleTimeOffset() {
      return getNumFieldValues(1, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get sample_time_offset field
    * Units: ms
    * Comment: Each time in the array describes the time at which the compass sample with the corrosponding index was taken. Limited to 30 samples in each message. The samples may span across seconds. Array size must match the number of samples in cmps_x and cmps_y and cmps_z
    *
    * @param index of sample_time_offset
    * @return sample_time_offset
    */
   public Integer getSampleTimeOffset(int index) {
      return getFieldIntegerValue(1, index, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Set sample_time_offset field
    * Units: ms
    * Comment: Each time in the array describes the time at which the compass sample with the corrosponding index was taken. Limited to 30 samples in each message. The samples may span across seconds. Array size must match the number of samples in cmps_x and cmps_y and cmps_z
    *
    * @param index of sample_time_offset
    * @param sampleTimeOffset
    */
   public void setSampleTimeOffset(int index, Integer sampleTimeOffset) {
      setFieldValue(1, index, sampleTimeOffset, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   public Integer[] getMagX() {
      
      return getFieldIntegerValues(2, Fit.SUBFIELD_INDEX_MAIN_FIELD);
      
   }

   /**
    * @return number of mag_x
    */
   public int getNumMagX() {
      return getNumFieldValues(2, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get mag_x field
    * Units: counts
    * Comment: These are the raw ADC reading. Maximum number of samples is 30 in each message. The samples may span across seconds. A conversion will need to be done on this data once read.
    *
    * @param index of mag_x
    * @return mag_x
    */
   public Integer getMagX(int index) {
      return getFieldIntegerValue(2, index, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Set mag_x field
    * Units: counts
    * Comment: These are the raw ADC reading. Maximum number of samples is 30 in each message. The samples may span across seconds. A conversion will need to be done on this data once read.
    *
    * @param index of mag_x
    * @param magX
    */
   public void setMagX(int index, Integer magX) {
      setFieldValue(2, index, magX, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   public Integer[] getMagY() {
      
      return getFieldIntegerValues(3, Fit.SUBFIELD_INDEX_MAIN_FIELD);
      
   }

   /**
    * @return number of mag_y
    */
   public int getNumMagY() {
      return getNumFieldValues(3, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get mag_y field
    * Units: counts
    * Comment: These are the raw ADC reading. Maximum number of samples is 30 in each message. The samples may span across seconds. A conversion will need to be done on this data once read.
    *
    * @param index of mag_y
    * @return mag_y
    */
   public Integer getMagY(int index) {
      return getFieldIntegerValue(3, index, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Set mag_y field
    * Units: counts
    * Comment: These are the raw ADC reading. Maximum number of samples is 30 in each message. The samples may span across seconds. A conversion will need to be done on this data once read.
    *
    * @param index of mag_y
    * @param magY
    */
   public void setMagY(int index, Integer magY) {
      setFieldValue(3, index, magY, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   public Integer[] getMagZ() {
      
      return getFieldIntegerValues(4, Fit.SUBFIELD_INDEX_MAIN_FIELD);
      
   }

   /**
    * @return number of mag_z
    */
   public int getNumMagZ() {
      return getNumFieldValues(4, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get mag_z field
    * Units: counts
    * Comment: These are the raw ADC reading. Maximum number of samples is 30 in each message. The samples may span across seconds. A conversion will need to be done on this data once read.
    *
    * @param index of mag_z
    * @return mag_z
    */
   public Integer getMagZ(int index) {
      return getFieldIntegerValue(4, index, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Set mag_z field
    * Units: counts
    * Comment: These are the raw ADC reading. Maximum number of samples is 30 in each message. The samples may span across seconds. A conversion will need to be done on this data once read.
    *
    * @param index of mag_z
    * @param magZ
    */
   public void setMagZ(int index, Integer magZ) {
      setFieldValue(4, index, magZ, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   public Float[] getCalibratedMagX() {
      
      return getFieldFloatValues(5, Fit.SUBFIELD_INDEX_MAIN_FIELD);
      
   }

   /**
    * @return number of calibrated_mag_x
    */
   public int getNumCalibratedMagX() {
      return getNumFieldValues(5, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get calibrated_mag_x field
    * Units: G
    * Comment: Calibrated Magnetometer reading
    *
    * @param index of calibrated_mag_x
    * @return calibrated_mag_x
    */
   public Float getCalibratedMagX(int index) {
      return getFieldFloatValue(5, index, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Set calibrated_mag_x field
    * Units: G
    * Comment: Calibrated Magnetometer reading
    *
    * @param index of calibrated_mag_x
    * @param calibratedMagX
    */
   public void setCalibratedMagX(int index, Float calibratedMagX) {
      setFieldValue(5, index, calibratedMagX, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   public Float[] getCalibratedMagY() {
      
      return getFieldFloatValues(6, Fit.SUBFIELD_INDEX_MAIN_FIELD);
      
   }

   /**
    * @return number of calibrated_mag_y
    */
   public int getNumCalibratedMagY() {
      return getNumFieldValues(6, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get calibrated_mag_y field
    * Units: G
    * Comment: Calibrated Magnetometer reading
    *
    * @param index of calibrated_mag_y
    * @return calibrated_mag_y
    */
   public Float getCalibratedMagY(int index) {
      return getFieldFloatValue(6, index, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Set calibrated_mag_y field
    * Units: G
    * Comment: Calibrated Magnetometer reading
    *
    * @param index of calibrated_mag_y
    * @param calibratedMagY
    */
   public void setCalibratedMagY(int index, Float calibratedMagY) {
      setFieldValue(6, index, calibratedMagY, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   public Float[] getCalibratedMagZ() {
      
      return getFieldFloatValues(7, Fit.SUBFIELD_INDEX_MAIN_FIELD);
      
   }

   /**
    * @return number of calibrated_mag_z
    */
   public int getNumCalibratedMagZ() {
      return getNumFieldValues(7, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get calibrated_mag_z field
    * Units: G
    * Comment: Calibrated Magnetometer reading
    *
    * @param index of calibrated_mag_z
    * @return calibrated_mag_z
    */
   public Float getCalibratedMagZ(int index) {
      return getFieldFloatValue(7, index, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Set calibrated_mag_z field
    * Units: G
    * Comment: Calibrated Magnetometer reading
    *
    * @param index of calibrated_mag_z
    * @param calibratedMagZ
    */
   public void setCalibratedMagZ(int index, Float calibratedMagZ) {
      setFieldValue(7, index, calibratedMagZ, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

}
