package com.thundergod.tlogger.util;

public enum TLLevel {
    FATAL(TLColor.RED_BOLD,TLColor.RED_UNDERLINED,TLColor.PURPLE_BOLD,TLColor.CYAN_BOLD_BRIGHT,true),
    ERROR(TLColor.RED,TLColor.RED_BOLD_BRIGHT,TLColor.PURPLE_BOLD, TLColor.CYAN_BOLD_BRIGHT,true),
    WARNING(TLColor.YELLOW,TLColor.YELLOW_BOLD_BRIGHT,TLColor.PURPLE_BOLD,TLColor.CYAN_BOLD_BRIGHT,true),
    DEBUG(TLColor.BLUE,TLColor.BLUE_BOLD_BRIGHT,TLColor.PURPLE_BOLD,TLColor.CYAN_BOLD_BRIGHT,true),
    INFO(TLColor.GREEN,TLColor.GREEN_BOLD_BRIGHT,TLColor.PURPLE_BOLD,TLColor.CYAN_BOLD_BRIGHT,true),
    LIFECYCLE(TLColor.PURPLE,TLColor.PURPLE_BOLD_BRIGHT,TLColor.PURPLE_BOLD,TLColor.CYAN_BOLD_BRIGHT,true),
    ;
    public String level,text,bracket_etc,date;
    public boolean level_upper_case;
    TLLevel(String level_color_code,String text_color_code,String bracket_etc_color_code,String date_color_code,boolean level_upper_case){
     this.level = level_color_code;
     this.text = text_color_code;
     this.bracket_etc = bracket_etc_color_code;
     this.date = date_color_code;
     this.level_upper_case = level_upper_case;
    }
    public static class TLLevelCustom{
        public String level,text,bracket_etc,date;
        public boolean level_upper_case;
        public  TLLevelCustom(String level_color_code,String text_color_code,String bracket_etc_color_code,String date_color_code,boolean level_upper_case){
            this.level = level_color_code;
            this.text = text_color_code;
            this.bracket_etc = bracket_etc_color_code;
            this.date = date_color_code;
            this.level_upper_case = level_upper_case;
        }
    }
}
