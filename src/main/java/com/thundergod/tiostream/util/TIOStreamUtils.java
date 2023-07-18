package com.thundergod.tiostream.util;

import com.thundergod.tiostream.core.TIOStream;
import com.thundergod.tutil.core.TUText;
import com.thundergod.tutil.core.TUtil;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.List;

public class TIOStreamUtils {
    TIOStream stream;
    public TIOStreamUtils(TIOStream stream){
        this.stream = stream;
    }
    public <T extends Object>void writeList(List<T> objectList,String splitter){
        //stream.start();
        for (Object o :
             objectList) {
            stream.write(splitter+o.toString());
        }
        //stream.stop();
    }
    public List<String> readELementsToList(){
        List<String> trt = new ArrayList<>();
        while (stream.canRead()){
            trt.add(stream.read());
        }
        return trt;
    }
    public void writeConfig(String config_name,String config_Val){
        stream.write("\n"+config_name+": ");
        stream.write(config_Val);
    }
    public String readConfig(String config_name){
        stream.reset(true);
        _find(config_name+":");
        String read = stream.read();
        return read;
    }
    public  TUtil.ReworkOptional<List<String>, String> readMethod(String method_name){
        stream.reset(true);
        _find(method_name);
        String read = stream.readLine();
        int start = read.indexOf('(');
        int stop = read.indexOf(')');
        String parameters =  read.substring(start+1,stop);
        List<String> parameterList = TUText.convertDotToList(parameters);
        parameters = null;
        int valPointer = read.indexOf('=');
        String finalStr = read.substring(valPointer+2);
        TUtil.ReworkOptional<List<String>,String> rtr = new TUtil.ReworkOptional<>(parameterList,finalStr);
        return rtr;
    }
    private void _find(String element){
        while (stream.canRead()){
            String s = stream.read();
            if (s.equals(element)){
                return;
            }
        }
    }
}
