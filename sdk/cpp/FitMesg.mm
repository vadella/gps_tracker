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


#import <Foundation/Foundation.h>
#import "FitMesg.h"
#import "FitField.h"

#include "fit_mesg.hpp"

@interface FitMesg ()
- (FIT_UINT8) WriteField:(FILE *)file withField:(const fit::FieldBase *)field defSize:(FIT_UINT8)defSize andType:(FIT_UINT8)defType;
@end

@implementation FitMesg

- (FIT_UINT8) Write:(FILE *) file forMesg:(const fit::Mesg *)mesg
{
    return [self Write:file forMesg:mesg withDef:FIT_NULL];
}

- (FIT_UINT8) Write:(FILE *) file forMesg:(const fit::Mesg *)mesg withDef:(const fit::MesgDefinition *)mesgDef
{
    fit::MesgDefinition mesgDefOnNull;
    FIT_UINT8 mesgSize = 1;
    FIT_UINT8 fieldSize = 0;
    FIT_UINT8 byte;

    // Message record header with local message number.
    byte = mesg->GetLocalNum() & FIT_HDR_TYPE_MASK;
    fwrite(&byte, 1, 1, file);

    if (mesgDef == FIT_NULL)
    {
        mesgDefOnNull = fit::MesgDefinition(*mesg);
        mesgDef = &mesgDefOnNull;
    }

    for (FIT_UINT16 fieldDefIndex = 0; fieldDefIndex < (mesgDef->GetFields().size()); fieldDefIndex++)
    {
        const fit::Field* field = mesg->GetField(mesgDef->GetFieldByIndex(fieldDefIndex)->GetNum());
        const fit::FieldDefinition* definition = mesgDef->GetFieldByIndex(fieldDefIndex);

        fieldSize = [self WriteField:file withField:field defSize:definition->GetSize() andType:definition->GetType()];
        if (fieldSize == 0)
        {
            // Something went wrong during our write
            return 0;
        }

        mesgSize += fieldSize;
    }

    for (fit::DeveloperFieldDefinition def : mesgDef->GetDevFields())
    {
        const fit::DeveloperField* field = mesg->GetDeveloperField(def.GetDeveloperDataIndex(), def.GetNum());

        fieldSize = [self WriteField:file withField:field defSize:def.GetSize() andType:def.GetType()];
        if (fieldSize == 0)
        {
            // Something went wrong during our write
            return 0;
        }

        mesgSize += fieldSize;
    }

    return mesgSize;
}

- (FIT_UINT8) WriteField:(FILE *)file withField:(const fit::FieldBase *)field defSize:(FIT_UINT8)defSize andType:(FIT_UINT8)defType
{
    FIT_UINT8 fieldSize = 0;

    if (field != FIT_NULL)
    {
        FitField *ff = [[FitField alloc] init];
        fieldSize = [ff Write:file forField:field];

        if (fieldSize == 0)
            return 0;
    }

    if (fieldSize < defSize)
    {
        FIT_UINT8 baseTypeNum = (defType & FIT_BASE_TYPE_NUM_MASK);

        if (baseTypeNum < FIT_BASE_TYPES)
        {
            FIT_UINT8 baseTypeByteIndex = (fieldSize % (fit::baseTypeSizes[baseTypeNum]));
            FIT_UINT8 numBytesRemaining = (defSize) - fieldSize;

            while (numBytesRemaining--)
            {
                fwrite((fit::baseTypeInvalids[baseTypeNum] + baseTypeByteIndex), 1, 1, file);

                if ((++baseTypeByteIndex) >= fit::baseTypeSizes[baseTypeNum])
                    baseTypeByteIndex = 0;

                fieldSize++;
            }
        }
        else
        {
            return 0;
            // Do not continue if base type not supported.
        }
    }

    return fieldSize;
}

@end
