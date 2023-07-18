package com.thundergod.tdata_helper.file_system.annotations.reader_settings;

import com.thundergod.tdata_helper.file_system.TUFileReader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.LOCAL_VARIABLE,ElementType.FIELD})
public @interface IOSettings {
    TUFileReader.ReaderMode MODE();
    TUFileReader.ReaderMode SYNTAX_MODE();
}
