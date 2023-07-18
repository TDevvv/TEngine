package com.thundergod.tdata_helper.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TDHReader {
    public static Scanner scanner = new Scanner("");
    public static String readAll(String path,String separator){
        try {
            scanner = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNext()){
        builder.append(scanner.nextLine());
        builder.append(separator);
        }
        return builder.toString();
    }
    public static String _shader(String path){
       return TDataHelper.shader(path);
    }
    public static String _textures(String path){
        return TDataHelper.textures(path);
    }
}
