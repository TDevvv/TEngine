package com.thundergod.tutil.core;

import com.thundergod.ITPart;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class TUTime implements ITPart {
    double start;
    double finish;
    double delta_time;
    double fps;
    public TUTime(){
    }
    public static TUTime create(){
        return new TUTime();
    }
    public void t_start(){
        start = glfwGetTime();
    }
    public void t_end(){
        finish = glfwGetTime();
    }
    public void parse(){
        delta_time = finish-start;
    }
    public double delta_time(){
        return delta_time;
    }
    public void reset(){
        start = finish;
    }
    public void parseFps(){
        fps =  1/delta_time();
    }

    @Override
    public void init() {

    }

    @Override
    public void loop(float dt) {

    }

    @Override
    public void dispose() {

    }
    public double fps(){
        return fps;
    }
    public static double time(){return glfwGetTime();}

    public float getTime() {
        return (float) glfwGetTime();
    }
}
