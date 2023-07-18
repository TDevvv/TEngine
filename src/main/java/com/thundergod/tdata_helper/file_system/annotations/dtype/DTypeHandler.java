package com.thundergod.tdata_helper.file_system.annotations.dtype;

import java.lang.reflect.Field;

public class DTypeHandler {
   static String FILE_EXTENSION;
   static boolean SHOW_RESULT;
    public static DTypeField handle(Class CLASS){
        for (Field field:
             CLASS.getDeclaredFields()) {
            if (field.isAnnotationPresent(DType.class)){
                FILE_EXTENSION = field.getAnnotation(DType.class).file_extension();
                SHOW_RESULT = field.getAnnotation(DType.class).show_result();
            }
        }
        return DTypeField.create(FILE_EXTENSION,SHOW_RESULT);
    }
    public static DTypeField handle(Class CLASS,String FIELD_NAME){
        for (Field field:
                CLASS.getDeclaredFields()) {
            if (field.isAnnotationPresent(DType.class)){
                if (field.getName().equals(FIELD_NAME)){
                    FILE_EXTENSION = field.getAnnotation(DType.class).file_extension();
                    SHOW_RESULT = field.getAnnotation(DType.class).show_result();
                }
            }
        }
        return DTypeField.create(FILE_EXTENSION,SHOW_RESULT);
    }
}
