package com.thundergod.tdata_helper.file_system.annotations.run_with_warning;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RunWithWarning {
    String warning_message();
    int warning_code();
    boolean show_detail();
}
