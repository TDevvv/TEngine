package com.thundergod.twindow.util;

public class TWBackground {
    public float r,g,b,a;
    public TWBackground(float r,float g,float b,float a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    public void setWhite(){
        r = 1;
        g = 1;
        b = 1;
        a = 1;
    }
    public void setBlack(){
        r = 0;
        g = 0;
        b = 0;
        a = 0;
    }
    public void changeColor(float r,float g,float b){
        changeColor(r,g,b,0);
    }
    public void changeColor(float r,float g,float b,float a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
}
