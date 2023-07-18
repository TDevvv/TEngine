package com.thundergod.tiostream.core;

public class StaticTIOStream {
    static TIOStream stream = new TIOStream("",true);
    static {

    }
    public static void write(String path,String string){
        stream.setup(path,true);
        stream.start();
        stream.write(string);
        stream.stop();
    }

}
