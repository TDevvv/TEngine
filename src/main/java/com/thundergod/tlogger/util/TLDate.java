package com.thundergod.tlogger.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TLDate {
    static SimpleDateFormat consoleDateFormat = new SimpleDateFormat("yyyy$MM/dd/hh/mm;::;hh.mm.ss.SSSXXX");
    public static String getConsoleDate(){
      return  consoleDateFormat.format(new Date());
    }
}
