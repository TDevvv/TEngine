package com.thundergod.tdata_helper.file_system;


import com.thundergod.tdata_helper.file_system.annotations.dtype.DTypeHandler;
import com.thundergod.tdata_helper.file_system.data.TUDataFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TUFileHandler extends TU{
    public static Class classForAnnotation;

    public static Class getClassForAnnotation() {
        return classForAnnotation;
    }


    public static File create(String PATH){
     File NEW_FILE = new File(PATH);
     return NEW_FILE;
    }

    public static void setClassForAnnotation(Class c){
    }
    public static TUDataFile createDataFile(String PATH){
        TUDataFile RETURN = new TUDataFile(PATH);
        try {
            RETURN.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return RETURN;
    }
    public static File get(String PATH){
     if (!new File(PATH).exists()){
         try {
             throw new FileNotFoundException("file doesnt exist in internal path :"+PATH);
         } catch (FileNotFoundException e) {
             throw new RuntimeException(e);
         }
     }
     File EXIST_FILE = new File(PATH);
     return EXIST_FILE;
    }
    public static TUDataFile createDataFile(String PATH, Class INSTANCE,String FIELD_NAME){
        TUDataFile NEW_FILE = new TUDataFile(PATH+ DTypeHandler.handle(INSTANCE,FIELD_NAME).getFile_extension());
        if (DTypeHandler.handle(INSTANCE).isShow_result()){
            try {
                System.out.println("result ?=: "+NEW_FILE.createNewFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            if (NEW_FILE.exists()){
                return NEW_FILE;
            }else{
                try {
                    NEW_FILE.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return NEW_FILE;
    }
    public static TUDataFile createDataFile(String PATH, Class INSTANCE){
        TUDataFile NEW_FILE = new TUDataFile(PATH+ DTypeHandler.handle(INSTANCE).getFile_extension());
        if (DTypeHandler.handle(INSTANCE).isShow_result()){
            try {
                System.out.println("result ?=: "+NEW_FILE.createNewFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            if (NEW_FILE.exists()){
                return NEW_FILE;
            }else{
                try {
                    NEW_FILE.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return NEW_FILE;
    }
    public static TUDataFile getDataFile(String PATH){
        if (!new TUDataFile(PATH).exists()){
            try {
                throw new FileNotFoundException("file doesnt exist in internal path : "+PATH);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        TUDataFile EXIST_FILE = new TUDataFile(PATH);
        return EXIST_FILE;
    }
}
