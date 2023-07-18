package com.thundergod.tdata_helper.directory_system;

import com.thundergod.tdata_helper.file_system.TU;
import com.thundergod.tdata_helper.file_system.TUFileHandler;
import com.thundergod.tdata_helper.file_system.annotations.dtype.DTypeHandler;

import java.io.File;
import java.util.List;

public class TUDirectoryHandler extends TU {
    public static void create(String PATH,Class C)
    {
        File FILE = TUFileHandler.create(PATH);
        if (DTypeHandler.handle(C).isShow_result()){
            System.out.println("result ?=: "+FILE.mkdirs());
        }else{
            FILE.mkdirs();
        }
    }
    public static void create_multiple(List<String> PATH, Class C){
        for (String Drc:
             PATH) {
            create(Drc,C);
        }
    }
}