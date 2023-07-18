package com.thundergod.tdata_helper.file_system.annotations.reader_settings;

import com.thundergod.tdata_helper.file_system.TUFileReader;

import java.lang.reflect.Field;

public class ReaderSettingsHandler {
    protected static TUFileReader.ReaderMode MOD;
    protected static TUFileReader.ReaderMode SYNTAX_MOD;
    public static ReaderSettingsField handle(Class CLASS){
        for (Field field:
                CLASS.getDeclaredFields()) {
            if (field.isAnnotationPresent(IOSettings.class)){
                MOD = field.getAnnotation(IOSettings.class).MODE();
                SYNTAX_MOD = field.getAnnotation(IOSettings.class).SYNTAX_MODE();
            }
        }
        return ReaderSettingsField.create(MOD,SYNTAX_MOD);
    }
}
