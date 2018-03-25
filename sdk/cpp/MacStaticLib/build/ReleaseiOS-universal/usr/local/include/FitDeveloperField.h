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


#if !defined(FIT_DEVELOPERFIELD_H)
#define FIT_DEVELOPERFIELD_H

#include "fit.hpp"
#include "fit_developer_field_definition.hpp"

@interface FitDeveloperField : NSObject
- (FIT_UINT8) Write:(FILE*) file forDeveloperFieldDef:(const fit::DeveloperFieldDefinition *)fieldDef;
@end

#endif /* FIT_DEVELOPERFIELD_H */
