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


public class ExdDataConceptConfigurationMesg extends Mesg {

   
   public static final int ScreenIndexFieldNum = 0;
   
   public static final int ConceptFieldFieldNum = 1;
   
   public static final int FieldIdFieldNum = 2;
   
   public static final int ConceptIndexFieldNum = 3;
   
   public static final int DataPageFieldNum = 4;
   
   public static final int ConceptKeyFieldNum = 5;
   
   public static final int ScalingFieldNum = 6;
   
   public static final int DataUnitsFieldNum = 8;
   
   public static final int QualifierFieldNum = 9;
   
   public static final int DescriptorFieldNum = 10;
   
   public static final int IsSignedFieldNum = 11;
   

   protected static final  Mesg exdDataConceptConfigurationMesg;
   static {int field_index = 0;
      // exd_data_concept_configuration
      exdDataConceptConfigurationMesg = new Mesg("exd_data_concept_configuration", MesgNum.EXD_DATA_CONCEPT_CONFIGURATION);
      exdDataConceptConfigurationMesg.addField(new Field("screen_index", ScreenIndexFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
      field_index++;
      exdDataConceptConfigurationMesg.addField(new Field("concept_field", ConceptFieldFieldNum, 13, 1, 0, "", false, Profile.Type.BYTE));
      exdDataConceptConfigurationMesg.fields.get(field_index).components.add(new FieldComponent(2, false, 4, 1, 0)); // field_id
      exdDataConceptConfigurationMesg.fields.get(field_index).components.add(new FieldComponent(3, false, 4, 1, 0)); // concept_index
      field_index++;
      exdDataConceptConfigurationMesg.addField(new Field("field_id", FieldIdFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
      field_index++;
      exdDataConceptConfigurationMesg.addField(new Field("concept_index", ConceptIndexFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
      field_index++;
      exdDataConceptConfigurationMesg.addField(new Field("data_page", DataPageFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
      field_index++;
      exdDataConceptConfigurationMesg.addField(new Field("concept_key", ConceptKeyFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
      field_index++;
      exdDataConceptConfigurationMesg.addField(new Field("scaling", ScalingFieldNum, 2, 1, 0, "", false, Profile.Type.UINT8));
      field_index++;
      exdDataConceptConfigurationMesg.addField(new Field("data_units", DataUnitsFieldNum, 0, 1, 0, "", false, Profile.Type.EXD_DATA_UNITS));
      field_index++;
      exdDataConceptConfigurationMesg.addField(new Field("qualifier", QualifierFieldNum, 0, 1, 0, "", false, Profile.Type.EXD_QUALIFIERS));
      field_index++;
      exdDataConceptConfigurationMesg.addField(new Field("descriptor", DescriptorFieldNum, 0, 1, 0, "", false, Profile.Type.EXD_DESCRIPTORS));
      field_index++;
      exdDataConceptConfigurationMesg.addField(new Field("is_signed", IsSignedFieldNum, 0, 1, 0, "", false, Profile.Type.BOOL));
      field_index++;
   }

   public ExdDataConceptConfigurationMesg() {
      super(Factory.createMesg(MesgNum.EXD_DATA_CONCEPT_CONFIGURATION));
   }

   public ExdDataConceptConfigurationMesg(final Mesg mesg) {
      super(mesg);
   }


   /**
    * Get screen_index field
    *
    * @return screen_index
    */
   public Short getScreenIndex() {
      return getFieldShortValue(0, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Set screen_index field
    *
    * @param screenIndex
    */
   public void setScreenIndex(Short screenIndex) {
      setFieldValue(0, 0, screenIndex, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get concept_field field
    *
    * @return concept_field
    */
   public Byte getConceptField() {
      return getFieldByteValue(1, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Set concept_field field
    *
    * @param conceptField
    */
   public void setConceptField(Byte conceptField) {
      setFieldValue(1, 0, conceptField, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get field_id field
    *
    * @return field_id
    */
   public Short getFieldId() {
      return getFieldShortValue(2, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Set field_id field
    *
    * @param fieldId
    */
   public void setFieldId(Short fieldId) {
      setFieldValue(2, 0, fieldId, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get concept_index field
    *
    * @return concept_index
    */
   public Short getConceptIndex() {
      return getFieldShortValue(3, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Set concept_index field
    *
    * @param conceptIndex
    */
   public void setConceptIndex(Short conceptIndex) {
      setFieldValue(3, 0, conceptIndex, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get data_page field
    *
    * @return data_page
    */
   public Short getDataPage() {
      return getFieldShortValue(4, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Set data_page field
    *
    * @param dataPage
    */
   public void setDataPage(Short dataPage) {
      setFieldValue(4, 0, dataPage, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get concept_key field
    *
    * @return concept_key
    */
   public Short getConceptKey() {
      return getFieldShortValue(5, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Set concept_key field
    *
    * @param conceptKey
    */
   public void setConceptKey(Short conceptKey) {
      setFieldValue(5, 0, conceptKey, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get scaling field
    *
    * @return scaling
    */
   public Short getScaling() {
      return getFieldShortValue(6, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Set scaling field
    *
    * @param scaling
    */
   public void setScaling(Short scaling) {
      setFieldValue(6, 0, scaling, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get data_units field
    *
    * @return data_units
    */
   public ExdDataUnits getDataUnits() {
      Short value = getFieldShortValue(8, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
      if (value == null)
         return null;
      return ExdDataUnits.getByValue(value);
   }

   /**
    * Set data_units field
    *
    * @param dataUnits
    */
   public void setDataUnits(ExdDataUnits dataUnits) {
      setFieldValue(8, 0, dataUnits.value, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get qualifier field
    *
    * @return qualifier
    */
   public ExdQualifiers getQualifier() {
      Short value = getFieldShortValue(9, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
      if (value == null)
         return null;
      return ExdQualifiers.getByValue(value);
   }

   /**
    * Set qualifier field
    *
    * @param qualifier
    */
   public void setQualifier(ExdQualifiers qualifier) {
      setFieldValue(9, 0, qualifier.value, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get descriptor field
    *
    * @return descriptor
    */
   public ExdDescriptors getDescriptor() {
      Short value = getFieldShortValue(10, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
      if (value == null)
         return null;
      return ExdDescriptors.getByValue(value);
   }

   /**
    * Set descriptor field
    *
    * @param descriptor
    */
   public void setDescriptor(ExdDescriptors descriptor) {
      setFieldValue(10, 0, descriptor.value, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

   /**
    * Get is_signed field
    *
    * @return is_signed
    */
   public Bool getIsSigned() {
      Short value = getFieldShortValue(11, 0, Fit.SUBFIELD_INDEX_MAIN_FIELD);
      if (value == null)
         return null;
      return Bool.getByValue(value);
   }

   /**
    * Set is_signed field
    *
    * @param isSigned
    */
   public void setIsSigned(Bool isSigned) {
      setFieldValue(11, 0, isSigned.value, Fit.SUBFIELD_INDEX_MAIN_FIELD);
   }

}
