package com.thundergod.tiostream.core;

import com.thundergod.tengine.core.TEngine;
import com.thundergod.tlogger.core.TLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TIOStream {
    static TLogger logger = new TLogger(TIOStream.class);
    static {
        TEngine.attachLogger(logger);
    }
    String path;
    File file;
    FileWriter writer;
    Scanner scanner;
    boolean saveStream;
    boolean ir;
    public TIOStream(String path,boolean saveStream){
        this.path = path;
        setup(this.path,saveStream);
    }
    public TIOStream(File file,boolean saveStream){
        this.path = file.getAbsolutePath();
        setup(file,saveStream);
    }
    public void setup(File file,boolean saveStream){
       this.file =file;
       if (!file.exists()){
           logger.warning("can not find file --> "+file.getAbsolutePath());
           return;
       }else{
           //logger.info("found file --> "+file.getAbsolutePath());
       }
       try {
           writer = new FileWriter(file,saveStream);
           scanner = new Scanner(file);
       }catch (IOException e){
           logger.fatal(e.getMessage());
           throw new RuntimeException();
       }
    }
    public void setup(String path,boolean saveStream){
        file = new File(path);
        if (!file.exists()){
            logger.warning("can not find file -> "+path);
            return;
        }else{
            //logger.info("found file --> "+path);
        }
        try {
            writer = new FileWriter(file,saveStream);
            scanner = new Scanner(file);
        } catch (IOException e) {
            logger.fatal(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public void start() {
        if (this.ir) {
            logger.fatal("stream is already running.");
            throw new IllegalStateException();
        }
        this.ir = true;
    }
    public void stop(){
        if (!this.ir){
            logger.fatal("stream is already closed.");
            throw new IllegalStateException();
        }
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.ir=false;
    }
    public void write(String s){
        _run();
        try {
            this.writer.write(s);
        } catch (IOException e) {
            logger.fatal("print : "+e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public void _write(String s){
        _run();
        try {
            this.writer.append(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void mod(Runnable runnable){
        if (debugMode){
            runnable.run();
        }
    }
    public String read(){
        _run();
        if (scanner.hasNext()){
            String rtr = scanner.next();
            mod(()->logger.debug("[DEBUGMODE==TIOStream] read() --> "+rtr));
           return rtr;
        }else{
            logger.warning("file has no elements returning empty string.");
            return "";
        }
    }
    public String readLine(){
        _run();
        if (scanner.hasNextLine()){
            return scanner.nextLine();
        }else{
            logger.warning("file has no elements returning empty string.");
            return "";
        }
    }
    private boolean _run(){
        if (!ir){
            logger.fatal("stream is not running.");
            throw new IllegalStateException();
        }
        return ir;
    }
    public boolean canRead(){
        return scanner.hasNext();
    }
    public boolean canReadLine(){
        return scanner.hasNextLine();
    }
    public int countElements(){
        int count = 0;
        Scanner old = this.scanner;
        try {
            scanner = new Scanner(file);
            while (canRead()){
                read();
                count++;
            }
            this.scanner = old;
            return count;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void reset(boolean saveStream){
       setup(file,saveStream);
    }
    public String readAllNewLine(){
        _run();
        StringBuilder builder = new StringBuilder();
        while (canReadLine()){
            builder.append(readLine()).append("\n");
        }
        return builder.toString();
    }
    boolean debugMode;

    public void debugMode(boolean v1){
        debugMode = v1;
    }
}
