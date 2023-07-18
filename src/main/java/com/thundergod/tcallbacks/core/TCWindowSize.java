package com.thundergod.tcallbacks.core;

import com.thundergod.ITPart;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.tlogger.core.TLogger;
import com.thundergod.tmath.core.TMath;
import com.thundergod.tmath.util.TMScale;
import com.thundergod.twindow.core.TWindow;

import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;


public class TCWindowSize implements ITPart {
    static TLogger logger = new TLogger(TCWindowSize.class);
    public TLogger getLogger() {
        return logger;
    }

    private static TWindow windowInstance = TEngine.ENGINE_WINDOW;
    static int width,height;
    static boolean isUsed;
    private static void callback(long window,int width,int height){
        TCWindowSize.width  = width;
        TCWindowSize.height = height;
    }
    public TMScale getScale(){
        return TMath.createScale(width,height);
    }
    @Override
    public void init() {
        TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem().addPart(this.getLogger());
        logger.setState(TEngine.ENGINE_CONTROLLER.getState());
        this.getLogger().attachTo(TEngine.ENGINE_WINDOW.getLogger());
        glfwSetWindowSizeCallback(windowInstance.TWindow_code.code,TCWindowSize::callback);
    }
    @Override
    public void loop(float dt) {
        logger.setState(TEngine.ENGINE_CONTROLLER.getState());
    }
    @Override
    public void dispose() {
        logger.setState(TEngine.ENGINE_CONTROLLER.getState());
    }
    private TCWindowSize(){

    }
    public static TCWindowSize createInstance(){
        if (!isUsed){
            isUsed = true;
            return new TCWindowSize();
        }else{
            return null;
        }
    }
}
