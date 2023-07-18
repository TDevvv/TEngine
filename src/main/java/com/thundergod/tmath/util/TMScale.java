package com.thundergod.tmath.util;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class TMScale {
    int width_i, height_i;
    float width_f,height_f;
    double width_d,height_d;
    public TMScale(int width_i, int height_i){
        this.height_i = height_i;
        this.width_i = width_i;
    }
    public TMScale(float width_f,float height_f){
        this.height_f = height_f;
        this.width_f = width_f;
    }
    public TMScale(double width_d,double height_d){
        this.width_d = width_d;
        this.height_d = height_d;
    }
    public Vector2i _v2i(){
        return new Vector2i(width_i, height_i);
    }
    public Vector2f _v2f(){
        return new Vector2f( width_f, height_f);
    }
    public Vector2d _v2d(){
        return new Vector2d( width_f, height_f);
    }
    public int getHeight_i() {
        return height_i;
    }
    public int getWidth_i() {
        return width_i;
    }
    public double getHeight_d() {
        return height_d;
    }
    public double getWidth_d() {
        return width_d;
    }
    public float getHeight_f() {
        return height_f;
    }
    public float getWidth_f() {
        return width_f;
    }

    public void setWidth_i(int width_i) {
        this.width_i = width_i;
    }

    public void setHeight_i(int height_i) {
        this.height_i = height_i;
    }

    public void setWidth_f(float width_f) {
        this.width_f = width_f;
    }

    public void setHeight_f(float height_f) {
        this.height_f = height_f;
    }

    public void setWidth_d(double width_d) {
        this.width_d = width_d;
    }

    public void setHeight_d(double height_d) {
        this.height_d = height_d;
    }

    @Override
    public String toString() {
        return "TMScale{" +
                "width_i=" + width_i +
                ", height_i=" + height_i +
                ", width_f=" + width_f +
                ", height_f=" + height_f +
                ", width_d=" + width_d +
                ", height_d=" + height_d +
                '}';
    }
}
