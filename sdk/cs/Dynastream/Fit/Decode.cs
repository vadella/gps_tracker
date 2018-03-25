#region Copyright
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

#endregion

using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;

namespace Dynastream.Fit
{
    /// <summary>
    /// Event Args Class associated with the DeveloperFieldDescrtiption Event
    /// </summary>
    public class DeveloperFieldDescriptionEventArgs : EventArgs
    {
        public DeveloperFieldDescription Description { get; private set; }

        public DeveloperFieldDescriptionEventArgs(DeveloperFieldDescription description)
        {
            Description = description;
        }
    }

    /// <summary>
    /// This class will decode a .fit file reading the file header and any definition or data messages.
    /// </summary>
    public class Decode
    {
        private const long CRCSIZE = 2;
        private const uint INVALID_DATA_SIZE = 0;

        #region Fields
        private MesgDefinition[] localMesgDefs = new MesgDefinition[Fit.MaxLocalMesgs];
        private Header fileHeader;
        private uint timestamp = 0;
        private int lastTimeOffset = 0;
        private bool invalidDataSize = false;
        private Accumulator accumulator = new Accumulator();

        private readonly DeveloperDataLookup m_lookup = new DeveloperDataLookup();
        #endregion

        #region Properties
        public bool InvalidDataSize
        {
            get
            {
                return invalidDataSize;
            }
            set
            {
                invalidDataSize = value;
            }
        }
        #endregion

        #region Constructors
        public Decode()
        {
        }
        #endregion

        #region Methods
        public event MesgEventHandler MesgEvent;
        public event MesgDefinitionEventHandler MesgDefinitionEvent;
        public event EventHandler<DeveloperFieldDescriptionEventArgs> DeveloperFieldDescriptionEvent;

        /// <summary>
        /// Reads the file header to check if the file is FIT.
        /// Does not check CRC.
        /// Returns true if file is FIT.
        /// </summary>
        /// <param name="fitStream"> Seekable (file)stream to parse</param>
        public bool IsFIT(Stream fitStream)
        {
            long position = fitStream.Position;
            bool status = false;
            try
            {
                // Does the header contain the flag string ".FIT"?
                Header header = new Header(fitStream);
                fitStream.Position = position;
                status = header.IsValid();
            }
            // If the header is malformed the ctor could throw an exception
            catch (FitException)
            {
            }

            fitStream.Position = position;
            return status;
        }

        /// <summary>
        /// Reads the FIT binary file header and crc to check compatibility and integrity.
        /// Also checks data reords size.
        /// Returns true if file is ok (not corrupt).
        ///</summary>
        /// <param name="fitStream">Seekable (file)stream to parse.</param>
        public bool CheckIntegrity(Stream fitStream)
        {
            bool isValid = true;
            long position = fitStream.Position;
            long fileSize = 0;

            try
            {
                while ((fitStream.Position < fitStream.Length) && isValid)
                {
                    // Is there a valid header?
                    Header header = new Header(fitStream);
                    isValid = header.IsValid();

                    // Get the file size from the header
                    // When the data size is 0 set flags, don't calculate CRC
                    if (header.DataSize > INVALID_DATA_SIZE)
                    {
                        fileSize = header.Size + header.DataSize + CRCSIZE;

                        // Is the file CRC ok?
                        // Need to rewind the header size because the header is part of the CRC calculation.
                        byte[] data = new byte[fileSize];
                        fitStream.Position = fitStream.Position - header.Size;
                        fitStream.Read(data, 0, data.Length);
                        isValid &= (CRC.Calc16(data, data.Length) == 0x0000);
                    }
                    else
                    {
                        invalidDataSize = true;
                        isValid = false;
                    }
                }
            }
            catch (FitException)
            {
                isValid = false;
            }

            fitStream.Position = position;
            return isValid;
        }

        /// <summary>
        /// Reads a FIT binary file.
        /// </summary>
        /// <param name="fitStream">Seekable (file)stream to parse.</param>
        /// <returns>
        /// Returns true if reading finishes successfully.
        /// </returns>
        public bool Read(Stream fitStream)
        {
            bool status = true;
            long position = fitStream.Position;

            while ((fitStream.Position < fitStream.Length) && status)
            {
                status = Read(fitStream, DecodeMode.Normal);
            }

            fitStream.Position = position;

            return status;
        }

        /// <summary>
        /// Reads a FIT binary file.
        /// </summary>
        /// <param name="fitStream">Seekable (file)stream to parse.</param>
        /// <param name="skipHeader">When true, skip file header.  Also CRC will not be calculated.</param>
        /// <returns>
        /// Returns true if reading finishes successfully.
        /// </returns>
        [Obsolete(
            "Arguments to this function are ambiguous, " +
            "use Read(stream, DecodeMode) instead. " +
            "Function will be removed after 20.30.00",
            false)]
        public bool Read(Stream fitStream, bool skipHeader)
        {
            return Read(fitStream, skipHeader ? DecodeMode.InvalidHeader : DecodeMode.Normal);
        }


        /// <summary>
        /// Reads a FIT binary File
        /// </summary>
        /// <param name="fitStream">Seekable (file)stream to parse.</param>
        /// <param name="mode">Decode Mode to use for reading the file</param>
        /// <returns>
        /// Returns true if reading finishes successfully.
        /// </returns>
        public bool Read(Stream fitStream, DecodeMode mode)
        {
            bool readOK = true;
            long fileSize = 0;
            long filePosition = fitStream.Position;

            try
            {
                // Attempt to read header
                if (mode == DecodeMode.Normal)
                {
                    fileHeader = new Header(fitStream);
                    readOK &= fileHeader.IsValid();

                    // Get the file size from the header
                    // When the data size is invalid set the file size to the fitstream length
                    if (!invalidDataSize)
                    {
                        fileSize = fileHeader.Size + fileHeader.DataSize + CRCSIZE;
                    }
                    else
                    {
                        fileSize = fitStream.Length;
                    }

                    if (!readOK)
                    {
                        throw new FitException("FIT decode error: File is not FIT format. Check file header data type. Error at stream position: " + fitStream.Position);
                    }
                    if ((fileHeader.ProtocolVersion & Fit.ProtocolVersionMajorMask) > (Fit.ProtocolMajorVersion << Fit.ProtocolVersionMajorShift))
                    {
                        // The decoder does not support decode accross protocol major revisions
                        throw new FitException(String.Format("FIT decode error: Protocol Version {0}.X not supported by SDK Protocol Ver{1}.{2} ", (fileHeader.ProtocolVersion & Fit.ProtocolVersionMajorMask) >> Fit.ProtocolVersionMajorShift, Fit.ProtocolMajorVersion, Fit.ProtocolMinorVersion));
                    }
                }
                else if(mode == DecodeMode.InvalidHeader)
                {
                    // When skipping the header force the stream position to be at the beginning of the data
                    // Also the fileSize is the length of the filestream.
                    fitStream.Position += Fit.HeaderWithCRCSize;
                    fileSize = fitStream.Length;
                }
                else if (mode == DecodeMode.DataOnly)
                {
                    // When the stream is only data move the position of the stream
                    // to the start. FileSize is the length of the stream
                    fitStream.Position = 0;
                    fileSize = fitStream.Length;
                }
                else
                {
                    throw new FitException("Invalid Decode Mode Provided to read");
                }

                // Read data messages and definitions
                while (fitStream.Position < (filePosition + fileSize - CRCSIZE))
                {
                    DecodeNextMessage(fitStream);
                }

                // Is the file CRC ok?
                if ((mode == DecodeMode.Normal) && !invalidDataSize)
                {
                    byte[] data = new byte[fileSize];
                    fitStream.Position = filePosition;
                    fitStream.Read(data, 0, data.Length);
                    readOK &= (CRC.Calc16(data, data.Length) == 0x0000);
                    fitStream.Position = filePosition + fileSize;
                }
            }
            catch (EndOfStreamException e)
            {
                readOK = false;
                Debug.WriteLine("{0} caught and ignored. ", e.GetType().Name);
                throw new FitException("Decode:Read - Unexpected End of File at stream position" + fitStream.Position, e);
            }
            catch (FitException e)
            {
                // When attempting to decode files with invalid data size this indicates the EOF.
                if (!invalidDataSize)
                {
                    throw e;
                }
            }
            return readOK;
        }

        public void DecodeNextMessage(Stream fitStream)
        {
            BinaryReader br = new BinaryReader(fitStream);
            byte nextByte = br.ReadByte();

            // Is it a compressed timestamp mesg?
            if ((nextByte & Fit.CompressedHeaderMask) == Fit.CompressedHeaderMask)
            {
                MemoryStream mesgBuffer = new MemoryStream();

                int timeOffset = nextByte & Fit.CompressedTimeMask;
                timestamp += (uint)((timeOffset - lastTimeOffset) & Fit.CompressedTimeMask);
                lastTimeOffset = timeOffset;
                Field timestampField = new Field(Profile.GetMesg(MesgNum.Record).GetField("Timestamp"));
                timestampField.SetValue(timestamp);

                byte localMesgNum = (byte)((nextByte & Fit.CompressedLocalMesgNumMask) >> 5);
                mesgBuffer.WriteByte(localMesgNum);
                if (localMesgDefs[localMesgNum] == null)
                {
                    throw new FitException("Decode:DecodeNextMessage - FIT decode error: Missing message definition for local message number " + localMesgNum + " at stream position " + fitStream.Position);
                }
                int fieldsSize = localMesgDefs[localMesgNum].GetMesgSize() - 1;
                try
                {
                    byte[] read = br.ReadBytes(fieldsSize);
                    if( read.Length < fieldsSize )
                    {
                        throw new Exception("Field size mismatch, expected: " + fieldsSize + "received: " + read.Length);
                    }
                    mesgBuffer.Write(read, 0, fieldsSize);
                }
                catch (Exception e)
                {
                    throw new FitException("Decode:DecodeNextMessage - Compressed Data Message unexpected end of file.  Wanted " + fieldsSize + " bytes at stream position " + fitStream.Position, e);
                }

                Mesg newMesg = new Mesg(mesgBuffer, localMesgDefs[localMesgNum]);
                newMesg.InsertField(0, timestampField);
                RaiseMesgEvent(newMesg);
            }
            // Is it a mesg def?
            else if ((nextByte & Fit.MesgDefinitionMask) == Fit.MesgDefinitionMask)
            {
                MemoryStream mesgDefBuffer = new MemoryStream();

                // Figure out number of fields (length) of our defn and build buffer
                mesgDefBuffer.WriteByte(nextByte);
                mesgDefBuffer.Write(br.ReadBytes(4), 0, 4);
                byte numFields = br.ReadByte();
                mesgDefBuffer.WriteByte(numFields);
                int numBytes = numFields * 3; //3 Bytes per field
                try
                {
                    byte[] read = br.ReadBytes(numBytes);
                    if( read.Length < numBytes )
                    {
                        throw new Exception("Message Definition size mismatch, expected: " + numBytes + "received: " + read.Length);
                    }
                    mesgDefBuffer.Write(read, 0, numBytes);

                    if ((nextByte & Fit.DevDataMask) == Fit.DevDataMask)
                    {
                        // Definition Contains Dev Data
                        byte numDevFields = br.ReadByte();
                        mesgDefBuffer.WriteByte(numDevFields);

                        numBytes = numDevFields * 3;
                        read = br.ReadBytes(numBytes);
                        if( read.Length < numBytes )
                        {
                            throw new Exception("Message Definition size mismatch, expected: " + numBytes + "received: " + read.Length);
                        }

                        // Read Dev Data
                        mesgDefBuffer.Write(read, 0, numBytes);
                    }
                }
                catch (Exception e)
                {
                    throw new FitException("Decode:DecodeNextMessage - Defn Message unexpected end of file.  Wanted " + numBytes + " bytes at stream position " + fitStream.Position, e);
                }

                MesgDefinition newMesgDef = new MesgDefinition(mesgDefBuffer, m_lookup);
                localMesgDefs[newMesgDef.LocalMesgNum] = newMesgDef;
                if (MesgDefinitionEvent != null)
                {
                    MesgDefinitionEvent(this, new MesgDefinitionEventArgs(newMesgDef));
                }
            }
            // Is it a data mesg?
            else if ((nextByte & Fit.MesgDefinitionMask) == Fit.MesgHeaderMask)
            {
                MemoryStream mesgBuffer = new MemoryStream();

                byte localMesgNum = (byte)(nextByte & Fit.LocalMesgNumMask);
                mesgBuffer.WriteByte(localMesgNum);
                if (localMesgDefs[localMesgNum] == null)
                {
                    throw new FitException("Decode:DecodeNextMessage - FIT decode error: Missing message definition for local message number " + localMesgNum + " at stream position " + fitStream.Position);
                }
                int fieldsSize = localMesgDefs[localMesgNum].GetMesgSize() - 1;
                try
                {
                    byte[] read = br.ReadBytes(fieldsSize);
                    if( read.Length < fieldsSize )
                    {
                        throw new Exception("Field size mismatch, expected: " + fieldsSize + "received: " + read.Length);
                    }
                    mesgBuffer.Write(read, 0, fieldsSize);
                }
                catch (Exception e)
                {
                    throw new FitException("Decode:DecodeNextMessage - Data Message unexpected end of file.  Wanted " + fieldsSize + " bytes at stream position " + fitStream.Position, e);
                }

                Mesg newMesg = new Mesg(mesgBuffer, localMesgDefs[localMesgNum]);

                // If the new message contains a timestamp field, record the value to use as
                // a reference for compressed timestamp headers
                Field timestampField = newMesg.GetField("Timestamp");
                if (timestampField != null)
                {
                    object tsValue = timestampField.GetValue();
                    if (tsValue != null)
                    {
                        timestamp = (uint)tsValue;
                        lastTimeOffset = (int)timestamp & Fit.CompressedTimeMask;
                    }
                }

                foreach (Field field in newMesg.FieldsList)
                {
                    if (field.IsAccumulated)
                    {
                        int i;
                        for (i = 0; i < field.GetNumValues(); i++)
                        {
                            long value = Convert.ToInt64(field.GetRawValue(i));

                            foreach (Field fieldIn in newMesg.FieldsList)
                            {
                                foreach (FieldComponent fc in fieldIn.components)
                                {
                                    if ((fc.fieldNum == field.Num) && (fc.accumulate))
                                    {
                                        value = (long) ((((value / field.Scale) - field.Offset) + fc.offset) * fc.scale);
                                    }
                                }
                            }
                            accumulator.Set(newMesg.Num, field.Num, value);
                        }
                    }
                }

                // Now that the entire message is decoded we can evaluate subfields and expand any components
                newMesg.ExpandComponents(accumulator);

                RaiseMesgEvent(newMesg);
            }
            else
            {
                throw new FitException("Decode:Read - FIT decode error: Unexpected Record Header Byte 0x" + nextByte.ToString("X") + " at stream position: " + fitStream.Position);
            }
        }

        /// <summary>
        ///
        /// </summary>
        /// <param name="newMesg"></param>
        /// <exception cref="System.InvalidOperationException"></exception>
        private void RaiseMesgEvent(Mesg newMesg)
        {
            if ((newMesg.Num == MesgNum.DeveloperDataId) ||
                (newMesg.Num == MesgNum.FieldDescription))
            {
                HandleMetaData(newMesg);
            }

            if (MesgEvent != null)
            {
                MesgEvent(this, new MesgEventArgs(newMesg));
            }
        }

        private void HandleMetaData(Mesg newMesg)
        {
            if (newMesg.Num == MesgNum.DeveloperDataId)
            {
                var mesg = new DeveloperDataIdMesg(newMesg);
                m_lookup.Add(mesg);
            }
            else if (newMesg.Num == MesgNum.FieldDescription)
            {
                var mesg = new FieldDescriptionMesg(newMesg);
                DeveloperFieldDescription desc = m_lookup.Add(mesg);
                if (desc != null)
                {
                    OnDeveloperFieldDescriptionEvent(
                        new DeveloperFieldDescriptionEventArgs(desc));
                }
            }
        }
        #endregion

        protected virtual void OnDeveloperFieldDescriptionEvent(DeveloperFieldDescriptionEventArgs e)
        {
            EventHandler<DeveloperFieldDescriptionEventArgs> handler =
                DeveloperFieldDescriptionEvent;

            if (handler != null)
            {
                handler(this, e);
            }
        }
    } // class
} // namespace
