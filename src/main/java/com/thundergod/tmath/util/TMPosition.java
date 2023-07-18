package com.thundergod.tmath.util;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class TMPosition {
    int x_i,y_i;
    float x_f,y_f;
    double x_d,y_d;

    int z;
    public void setZ(int z) {
        this.z = z;
    }

    public TMPosition(int x_i, int y_i){
     this.x_i = x_i;
     this.y_i = y_i;
    }
    public TMPosition(float x_f,float y_f){
        this.x_f =x_f;
        this.y_f = y_f;
    }
    public TMPosition(double x_d,double y_d){
        this.x_d = x_d;
        this.y_d = y_d;
    }
    public Vector2f _2vf(){
        return new Vector2f(this.x_f,this.y_f);
    }
    public Vector2i _2vi(){
        return new Vector2i(this.x_i,this.y_i);
    }
    public Vector2d _2vd(){  return new Vector2d(this.x_d,this.y_d);  }
    @Override
    public String toString() {
        return "TMPosition{" +
                "x_i=" + x_i +
                ", y_i=" + y_i +
                ", x_f=" + x_f +
                ", y_f=" + y_f +
                ", x_d=" + x_d +
                ", y_d=" + y_d +
                '}';
    }

    public int getX_i() {
        return x_i;
    }

    public int getY_i() {
        return y_i;
    }

    public float getX_f() {
        return x_f;
    }

    public float getY_f() {
        return y_f;
    }

    public double getX_d() {
        return x_d;
    }

    public double getY_d() {
        return y_d;
    }

    public void setX_i(int x_i) {
        this.x_i = x_i;
    }

    public void setY_i(int y_i) {
        this.y_i = y_i;
    }

    public void setX_f(float x_f) {
        this.x_f = x_f;
    }

    public void setY_f(float y_f) {
        this.y_f = y_f;
    }

    public void setX_d(double x_d) {
        this.x_d = x_d;
    }

    public void setY_d(double y_d) {
        this.y_d = y_d;
    }
    public void set(int x,int y){
        setX_i(x);
        setY_i(y);
    }
    public void set(float x,float y){
        setX_f(x);
        setY_f(y);
    }
    public void set(double x,double y){
        setX_d(x);
        setY_d(y);
    }

    public int getZ() {
        return z;
    }
}
