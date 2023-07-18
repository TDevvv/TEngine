package com.thundergod.twindow.util;

public class TWConfig {
    public int Width;
    public int Height;
    public String Title;
    public TWConfig(int width, int height, String title) {
        Width = width;
        Height = height;
        Title = title;
    }
    public static TWConfig createNullConfig(){
        TWConfig config = new TWConfig(500,500,"TWConfig/NULL");
        return config;
    }
}
