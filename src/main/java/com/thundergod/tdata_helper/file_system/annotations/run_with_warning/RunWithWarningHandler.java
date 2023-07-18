package com.thundergod.tdata_helper.file_system.annotations.run_with_warning;

import java.lang.reflect.Field;
import java.util.Date;

public class RunWithWarningHandler {
    protected String WARNING_MESSAGE;
    protected int WARNING_CODE;
    protected boolean SHOW_DETAIL;

    public static RunWithWarningField[] handle(Class CLASS){
        RunWithWarningField[] field =new RunWithWarningField[3];
        int i = 0;
        for (Field f:
             CLASS.getDeclaredFields()) {
            if (f.isAnnotationPresent(RunWithWarning.class)){
                i++;
               field[i] =  RunWithWarningField.create(
                f.getAnnotation(RunWithWarning.class).warning_message(),
                        f.getAnnotation(RunWithWarning.class).warning_code(),
                f.getAnnotation(RunWithWarning.class).show_detail());
            }else{


            }
        }
        return field;
    }

    public static void setup(Class c){
        System.out.println(handle(c)[0].WARNING_MESSAGE);
        RunWithWarningField[] has =  handle(c);
        System.out.println(has[0].WARNING_MESSAGE);
        RunWithWarningField h = null;
        for (int i = 0; i < has.length; i++) {
            h = has[i];
            System.out.println("begin:");
            System.out.println("WARNING />:"+h.WARNING_MESSAGE);
            if (h.show_detail){
                System.out.println("begin::detailed");
                int j = 0;
                String[] f_name = new String[c.getDeclaredFields().length];
                for (Field f:
                        c.getDeclaredFields()) {
                    if (f.isAnnotationPresent(RunWithWarning.class)){
                        j++;
                        f_name[j] = f.getName();
                    }else{

                    }
                }

                System.out.println("Sender />: "+c.getName());
                for (String s:
                        f_name) {
                    System.out.println("Declared field to this warning />: "+s);
                }
                System.out.println("warning level />: "+h.warning_code);
                System.out.println("1/normal 2/medium 3/high [current level /> "+h.warning_code+"]");
                System.out.println("Date : "+new Date());
                System.out.println("class package :"+c.getPackageName());
                System.out.println("end::detailed");
            }
            System.out.println("end:");
        }

    }
}
