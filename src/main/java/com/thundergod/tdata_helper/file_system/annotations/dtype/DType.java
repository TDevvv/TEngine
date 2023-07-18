package com.thundergod.tdata_helper.file_system.annotations.dtype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface DType {
     String file_extension();
     boolean show_result();

}
