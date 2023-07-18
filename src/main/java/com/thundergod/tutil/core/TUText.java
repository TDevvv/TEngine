package com.thundergod.tutil.core;

import com.thundergod.tdata_helper.file_system.TU;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.tlogger.core.TLogger;
import com.thundergod.tlogger.util.TLColor;

import javax.print.attribute.TextSyntax;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterators;

public class TUText {
    static TLogger logger = new TLogger(TUText.class);
    public static String setBracket(String str, String bracket_1, String bracket_2){
        return bracket_1+str+bracket_2;
    }
    public static String setRectBracket(String str){
      return setBracket(str,"[","]");
    }
    public static String setCurlyBracket(String str){
        return setBracket(str,"{","}");
    }
    public static String setNormalBracket(String str){
        return setBracket(str,"(",")");
    }
    public static String setBracketWithColor(String str,String bracket_1,String bracket_2,String color_txt){
        return color_txt+bracket_1+ TLColor.RESET+str+color_txt+bracket_2+TLColor.RESET;
    }
    private static void check(){
        if (!TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem().componentList.contains(logger)){
            TEngine.attachLogger(logger);
        }
    }
    public static String deserializeQuotes(String string){
        check();
        logger.debug("deserializing quote marked string _?> "+setRectBracket(string));
        int begin = string.indexOf("'");
        int end = string.lastIndexOf("'");
        return string.substring(begin+1,end);
    }
    public static List<String> convertDotToList(String string){
        check();
        logger.debug("converting string"+setRectBracket(string)+" to list.");
        List<String> rtr = new ArrayList<>();
        int i = 0;
        int last = 0;
        for (int j = 0; j < string.length(); j++) {
            String str = string.substring(j,j+1);
            logger.info("str: "+str);
            if (str.equals(",")){
                if (rtr.size()==0){
                    String _1 = string.substring(0,j);
                    logger.info("word: "+_1);
                    rtr.add(_1);
                    last = j+1;
                }else{
                    String _2 = string
                            .substring(last,j);
                    logger.info("word: "+_2);
                    rtr.add(_2);
                    last = j+1;
                }
            }
        }
        logger.info("word: "+string.substring(last));
        rtr.add(string.substring(last));
        logger.debug("string is successfully converted to list. return-> "+rtr.toString());
        return rtr;
    }
    public static String convertClassListToUsableString(List<Class<?>> list){
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        int i = 0;
        for (Class cl_:
             list) {
            builder.append(cl_.getSimpleName()).append("/");
        }
        builder.append(")");
        return builder.toString();
    }

}
