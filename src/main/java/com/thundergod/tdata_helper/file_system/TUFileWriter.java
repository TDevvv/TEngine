package com.thundergod.tdata_helper.file_system;

import com.thundergod.tdata_helper.file_system.data.TUDataFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class TUFileWriter {
    TUDataFile FILE_INST;
    FileWriter WRITER_INST;
    BufferedWriter BF_WRITER_INST;
    public static TUFileWriter create(TUDataFile FILE){
        return new TUFileWriter(FILE);
    }
    private TUFileWriter(TUDataFile FILE){
        FILE_INST = FILE;
        try {
            WRITER_INST = new FileWriter(FILE_INST);
            BF_WRITER_INST = new BufferedWriter(WRITER_INST);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BF_WRITER_INST.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }));
    }
    public void write(String str){
        try {
            BF_WRITER_INST.write(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void write(int integer){
        try {
            BF_WRITER_INST.write(integer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void write(boolean bool){
        try {
            BF_WRITER_INST.write(String.valueOf(bool));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void write(Class C){
        try {
            BF_WRITER_INST.write(C.toGenericString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void write(List<?> LIST){
        if (LIST.isEmpty()){
            return;
        }
        for (Object obj:
             LIST) {
            write(obj.toString());
        }
    }
    public void write(HashMap<?,?> HASH){
        if (HASH.isEmpty()){
            return;
        }
        for (Object obj:
             HASH.values()) {
            write(obj.toString());
        }
    }
    public void write(Object[] test){
        for (Object o:
             test) {
            write(o.toString());
        }
    }
    public void write(long lng){
        write(lng);
    }
    public void write(float flt){
        write(flt);
    }
    public void write(double dbl){
        write(dbl);
    }
    public void write(String PATTERN,String VALUE){
        write(PATTERN);
        write(" ");
        write(VALUE);
        write("\n");
    }
    public void write(String PATTERN,int VALUE){
        write(PATTERN,VALUE);
    }
    public void write(String PATTERN,boolean VALUE){
        write(PATTERN,VALUE);
    }
    public void write(String PATTERN,long VALUE){
        write(PATTERN,VALUE);
    }
    public void write(String PATTERN,float VALUE){
        write(PATTERN,VALUE);
    }
    public void write(String PATTERN,double VALUE){
        write(PATTERN,VALUE);
    }
    public void write(int PATTERN,String VALUE){
        write(PATTERN);
        write(" ");
        write(VALUE);
        write("\n");
    }
    public void write(boolean PATTERN,String VALUE){
        write(PATTERN,VALUE);
    }
    public void write(String PATTERN,List<String> VALUE){

    }

}
