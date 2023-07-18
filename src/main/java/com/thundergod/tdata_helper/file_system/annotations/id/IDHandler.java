package com.thundergod.tdata_helper.file_system.annotations.id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class IDHandler {
    public static Class CLS;

    public static void setCLS(Class CLS) {
        IDHandler.CLS = CLS;
    }

    public static final int METHOD_CODE  = 0;
    public static final int FIELD_CODE = 1;
    public static List<IDField[]> handle(Class c){
        int i;
        int k;
        int j;
        int FIELD_ID;
        int CONSTRUCTOR_ID;
        int METHOD_ID;
        IDField[] FIELD_ID_ARRAY = new IDField[c.getDeclaredFields().length];
        IDField[] METHOD_ID_ARRAY = new IDField[c.getMethods().length];
        Field[] fields = c.getDeclaredFields();
        Constructor constructor;
        Method[] methods = c.getMethods();
        Type type = c.arrayType();
        i  =0;
        for (Field field:
             fields) {
           if (field.isAnnotationPresent(ID.class)){
              FIELD_ID =  field.getAnnotation(ID.class).ID();
              FIELD_ID_ARRAY[i] = IDField.create(FIELD_ID, IDField.ELEMENT_TYPE.FIELD,field.getName());
              i++;
           }
        }
        j = 0;
        for (Method method:
             methods) {
          if (method.isAnnotationPresent(ID.class)){
              METHOD_ID = method.getAnnotation(ID.class).ID();
              METHOD_ID_ARRAY[j] = IDField.create(METHOD_ID, IDField.ELEMENT_TYPE.METHOD,method.getName());
              j++;
          }
        }

        List<IDField[]> TOTAL = new ArrayList<>();
        //TOTAL.add(METHOD_ID_ARRAY);
        TOTAL
                .add(METHOD_ID_ARRAY);
        TOTAL.add(FIELD_ID_ARRAY);
        return TOTAL;
    }
    public static IDField getMethod(String NAME,Class<?>...PARAMETER)  {
        Method method = null;
        try {
            method = CLS.getMethod(NAME,PARAMETER);
        } catch (NoSuchMethodException e) {
            System.out.println("this is not a valid method for this class");
            throw new RuntimeException(e);
        }
        IDField LAST;
       if (method.isAnnotationPresent(ID.class)){
           LAST = IDField.create(method.getAnnotation(ID.class).ID(), IDField.ELEMENT_TYPE.METHOD,method.getName());
       }else {
           LAST = null;
       }
       return LAST;

    }

}
