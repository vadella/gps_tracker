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


#include "fit_buffer_encode.hpp"
#include "fit_crc.hpp"

namespace fit
{

BufferEncode::BufferEncode(void)
{
   this->Open();
}

void BufferEncode::Open()
{
   stringWriter.str(std::string(""));
   stringWriter.clear();
   dataSize = 0;

   for (FIT_UINT8 i = 0; i < FIT_MAX_LOCAL_MESGS; ++i)
   {
      lastMesgDefinition[i].SetNum(FIT_MESG_NUM_INVALID);
      lastMesgDefinition[i].SetLocalNum(i);
      lastMesgDefinition[i].ClearFields();
   }
}

void BufferEncode::Write(const MesgDefinition& mesgDef)
{
   dataSize += mesgDef.Write(stringWriter);
   lastMesgDefinition[mesgDef.GetLocalNum()] = mesgDef;
}

void BufferEncode::Write(const Mesg& mesg)
{
   MesgDefinition mesgDefinition(mesg);

   if (!lastMesgDefinition[mesg.GetLocalNum()].Supports(mesgDefinition))
      Write(mesgDefinition);

   dataSize += mesg.Write(stringWriter, &(lastMesgDefinition[mesg.GetLocalNum()]));
}

void BufferEncode::Write(const std::vector<Mesg>& mesgs)
{
   for (std::vector<Mesg>::size_type i = 0; i < mesgs.size(); i++)
      Write(mesgs[i]);
}

std::string BufferEncode::Close(void)
{
   FIT_FILE_HDR file_header;

   // Get the data.
   std::string dataString = stringWriter.str();

   // Write the header.
   stringWriter.str(std::string(""));
   stringWriter.clear();
   file_header.header_size = FIT_FILE_HDR_SIZE;
   file_header.profile_version = FIT_PROFILE_VERSION;
   file_header.protocol_version = FIT_PROTOCOL_VERSION;
   memcpy((FIT_UINT8 *)&file_header.data_type, ".FIT", 4);
   file_header.data_size = dataSize;
   file_header.crc = CRC::Calc16(&file_header, FIT_STRUCT_OFFSET(crc, FIT_FILE_HDR));
   stringWriter.write((const char *)&file_header, FIT_FILE_HDR_SIZE);

   std::string headerString = stringWriter.str();

   // Write the CRC.
   stringWriter.str(std::string(""));
   stringWriter.clear();

   FIT_UINT16 crc = 0;

   for (std::string::size_type i = 0; i < headerString.size(); i++)
      crc = CRC::Get16(crc, (int) headerString[i]);

   for (std::string::size_type i = 0; i < dataString.size(); i++)
      crc = CRC::Get16(crc, (int) dataString[i]);

   stringWriter.put(crc & 0xFF);
   stringWriter.put((char)(crc >> 8));

   std::string crcString = stringWriter.str();

   // Discard the output stream and re-initialize.
   Open();

   // Put the result together.
   return (headerString + dataString + crcString);
}

void BufferEncode::OnMesg(Mesg &mesg)
{
   Write(mesg);
}

void BufferEncode::OnMesgDefinition(MesgDefinition& mesgDef)
{
   Write(mesgDef);
}

} // namespace fit
