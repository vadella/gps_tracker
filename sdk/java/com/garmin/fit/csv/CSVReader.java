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


package com.garmin.fit.csv;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import com.garmin.fit.*;

public class CSVReader {
   private static final Pattern csvPattern = Pattern.compile("\"([^\"]+?)\",?|([^,]+),?|,");

   static public boolean read(final InputStream in, final MesgListener mesgListener, final MesgDefinitionListener mesgDefinitionListener) {
      try {
         BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
         ArrayList<String> cells;
         int typeCol = -1;
         int localNumCol = -1;
         int mesgCol = -1;
         int fieldCol = -1;
         int lineNum = 1;
         String line;

         LinkedList<FieldDescriptionMesg> fieldDescriptionMesgs = new LinkedList<FieldDescriptionMesg>();
         HashMap<Short, DeveloperDataIdMesg> developerDataIdMesgs = new HashMap<Short, DeveloperDataIdMesg>();

         line = reader.readLine();
         // Strips the UTF-8 BOM if it exists
         line = stripUTF8Bom(line);
         cells = readCells(line);

         for (int col = 0; col < cells.size(); col++) {
            if (cells.get(col).equals("Type")) {
               typeCol = col;
            } else if (cells.get(col).equals("Local Number")) {
               localNumCol = col;
            } else if (cells.get(col).equals("Message")) {
               mesgCol = col;
            } else if (cells.get(col).equals("Field 1")) {
               fieldCol = col;
               break;
            }
         }

         while ((line = reader.readLine()) != null) {
            int cellIndex;
            Mesg mesg;
            MesgDefinition mesgDef = null;

            cells = readCells(line);
            lineNum++;

            if ((cells.size() <= typeCol) || (cells.size() <= localNumCol) || (cells.size() <= mesgCol))
               continue; // Ignore empty lines.

            mesg = Factory.createMesg(cells.get(mesgCol));

            if (mesg.getNum() == MesgNum.INVALID) {
               System.err.printf("CSVReader.read(): Error on line %d - Unknown message \"%s\".\n", lineNum, mesg.getName());
               return false;
            }

            if (localNumCol >= 0)
               mesg.setLocalNum(Integer.valueOf(cells.get(localNumCol)));

            if ((typeCol >= 0) && cells.get(typeCol).equals("Definition"))
               mesgDef = new MesgDefinition(mesg);

            cellIndex = fieldCol;

            while ((cellIndex + 2) <= cells.size()) {
               String fieldOrSubFieldName = cells.get(cellIndex++);
               ArrayList<String> values = readValues(cells.get(cellIndex++));
               FieldBase field = null;
               FieldDefinitionBase fieldDef = null;

               cellIndex++; // ignore units

               if (fieldOrSubFieldName == null)
                  break; // Blank cell.

               // See if there is an available native field for this field that we are searching for
               Field nativeField = Factory.createField(mesg.getName(), fieldOrSubFieldName);

               if (values == null)
                  continue; // Blank cell. Continue as other cells may be valid.

               if (nativeField.getNum() == Fit.FIELD_NUM_INVALID) {
                  // Check for Developer Field
                  for(FieldDescriptionMesg descriptionMesg : fieldDescriptionMesgs) {
                     if(descriptionMesg.getFieldName(0).equals(fieldOrSubFieldName)) {
                        DeveloperDataIdMesg devId = developerDataIdMesgs.get(descriptionMesg.getDeveloperDataIndex());
                        field = new DeveloperField(descriptionMesg, devId);
                        if(mesgDef != null) {
                           fieldDef = new DeveloperFieldDefinition(descriptionMesg, devId);
                        }
                        break;
                     }
                  }
               }
               else {
                  field = nativeField;

                  if (mesgDef != null)
                     fieldDef = new FieldDefinition((Field)field);
               }

               if(field == null){
                  // The field is not native or a defined developer data field
                  System.err.printf("CSVReader.read(): Error on line %d - Unknown field \"%s\" in message \"%s\".\n", lineNum, fieldOrSubFieldName, mesg.getName());
                  return false;
               }

               for (String value : values) {
                  int numValues = field.getNumValues();

                  if (field.getType() == Fit.BASE_TYPE_STRING) {
                     // Pass the read in string through
                     field.setValue(numValues, value, fieldOrSubFieldName);
                  } else {
                     // Parse the numeric values of the string
                     try {
                        Double doubleValue = Double.valueOf(value);
                        boolean setRawValue = false;

                        switch (field.getType()) {
                           case Fit.BASE_TYPE_ENUM:
                              if (Short.valueOf(value).equals(Fit.ENUM_INVALID))
                                 setRawValue = true;
                              break;
                           case Fit.BASE_TYPE_SINT8:
                              if (Byte.valueOf(value).equals(Fit.SINT8_INVALID))
                                 setRawValue = true;
                              break;
                           case Fit.BASE_TYPE_UINT8:
                              if (Short.valueOf(value).equals(Fit.UINT8_INVALID))
                                 setRawValue = true;
                              break;
                           case Fit.BASE_TYPE_UINT8Z:
                              if (Short.valueOf(value).equals(Fit.UINT8Z_INVALID))
                                 setRawValue = true;;
                              break;
                           case Fit.BASE_TYPE_SINT16:
                              if (Short.valueOf(value).equals(Fit.SINT16_INVALID))
                                 setRawValue = true;
                              break;
                           case Fit.BASE_TYPE_UINT16:
                              if (Integer.valueOf(value).equals(Fit.UINT16_INVALID))
                                 setRawValue = true;
                              break;
                           case Fit.BASE_TYPE_UINT16Z:
                              if (Integer.valueOf(value).equals(Fit.UINT16Z_INVALID))
                                 setRawValue = true;
                              break;
                           case Fit.BASE_TYPE_SINT32:
                              if (Integer.valueOf(value).equals(Fit.SINT32_INVALID))
                                 setRawValue = true;
                              break;
                           case Fit.BASE_TYPE_UINT32:
                              if (Long.valueOf(value).equals(Fit.UINT32_INVALID))
                                 setRawValue = true;
                              break;
                           case Fit.BASE_TYPE_UINT32Z:
                              if (Long.valueOf(value).equals(Fit.UINT32Z_INVALID))
                                 setRawValue = true;
                              break;
                           case Fit.BASE_TYPE_FLOAT32:
                              if (Float.valueOf(value).equals(Fit.FLOAT32_INVALID))
                                 setRawValue = true;
                              break;
                           case Fit.BASE_TYPE_FLOAT64:
                              if (Double.valueOf(value).equals(Fit.FLOAT64_INVALID))
                                 setRawValue = true;
                              break;
                           case Fit.BASE_TYPE_BYTE:
                              if (Short.valueOf(value).equals(Fit.BYTE_INVALID))
                                 setRawValue = true;
                              break;
                           default:
                              break;
                        }

                        if ( setRawValue ) {
                           field.setRawValue(numValues, doubleValue);
                        }
                        else {
                           field.setValue(numValues, doubleValue, fieldOrSubFieldName);
                        }

                     } catch (java.lang.NumberFormatException e) {
                        field.setValue(numValues, Double.valueOf(value), fieldOrSubFieldName);
                     }
                  }

                  // The 'value' for a field definition in the CSV is number of base type elements that the
                  // field contains. The size of the field in bytes is this number multiplied by the base type
                  // size.
                  if (fieldDef != null) {
                     fieldDef.setSize(Integer.valueOf(value) * Fit.baseTypeSizes[field.getType() & Fit.BASE_TYPE_NUM_MASK]);
                  }
               }

               if(field instanceof  Field) {
                  mesg.addField((Field)field);
                  if (mesgDef != null)
                     mesgDef.addField((FieldDefinition)fieldDef);
               }
               else {
                  mesg.addDeveloperField((DeveloperField)field);
                  if (mesgDef != null)
                     mesgDef.addDeveloperField((DeveloperFieldDefinition)fieldDef);
               }
            }

            if (mesgDef != null) {
               if (mesgDefinitionListener != null)
                  mesgDefinitionListener.onMesgDefinition(mesgDef);
            }
            else {
               if(mesg.getNum() == MesgNum.FIELD_DESCRIPTION) {
                  fieldDescriptionMesgs.add(new FieldDescriptionMesg(mesg));
               }
               else if (mesg.getNum() == MesgNum.DEVELOPER_DATA_ID) {
                  DeveloperDataIdMesg devId = new DeveloperDataIdMesg(mesg);
                  short developerIndex = devId.getDeveloperDataIndex();

                  // Save the Developer Data Id Message
                  developerDataIdMesgs.put(developerIndex, devId);

                  Iterator<FieldDescriptionMesg> it = fieldDescriptionMesgs.iterator();

                  // Remove fields associated with the old developer assigned to the index of the new dev
                  while(it.hasNext()){
                     FieldDescriptionMesg next = it.next();

                     if(next.getDeveloperDataIndex() == developerIndex) {
                        it.remove();
                     }
                  }
               }

               if (mesgListener != null)
                  mesgListener.onMesg(mesg);
            }
         }
      } catch (java.io.IOException e) {
         throw new RuntimeException(e);
      }

      return true;
   }

   static private ArrayList<String> readCells(String line) {
      ArrayList<String> list = new ArrayList<String>();
      Matcher m;

      if (line == null)
         return null;

      m = csvPattern.matcher(line);

      while (m.find()) {
         String match = m.group();

         if (match == null)
            break;

         if (match.endsWith(",")) { // trim trailing ,
            match = match.substring(0, match.length() - 1);
         }

         if (match.startsWith("\"")) { // assume also ends with
            match = match.substring(1, match.length() - 1);
         }

         if (match.length() == 0)
            match = null;

         list.add(match);
      }

      return list;
   }

   static private ArrayList<String> readValues(String cell) {
      ArrayList<String> list = new ArrayList<String>();
      String value;
      int i = 0;

      if (cell == null)
         return null;

      value = "";
      while (i < cell.length()) {
         if (cell.charAt(i) == '|') {
            list.add(value);
            value = "";
         } else {
            value += cell.charAt(i);
         }
         i++;
      }

      if (value.length() > 0)
         list.add(value);

      return list;
   }

   static private String stripUTF8Bom(String input) {

      byte[] beforeStrip = input.getBytes(Charset.forName("UTF-8"));
      if (beforeStrip[0] == Fit.UTF8_BOM_BYTE_1 && beforeStrip[1] == Fit.UTF8_BOM_BYTE_2 && beforeStrip[2] == Fit.UTF8_BOM_BYTE_3) {
         byte[] afterStrip = new byte[beforeStrip.length-Fit.UTF8_NUM_BOM_BYTES];
         System.arraycopy(beforeStrip, Fit.UTF8_NUM_BOM_BYTES, afterStrip, 0, afterStrip.length);

         try {
            input = new String(afterStrip, "UTF-8");
         } catch (UnsupportedEncodingException e) {
         }
      }

       return input;
   }
}
