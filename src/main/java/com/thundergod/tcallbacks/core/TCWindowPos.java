package com.thundergod.tcallbacks.core;

import com.thundergod.ITPart;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.tlogger.core.TLogger;
import com.thundergod.tmath.core.TMath;
import com.thundergod.tmath.util.TMPosition;
import com.thundergod.twindow.core.TWindow;

import static org.lwjgl.glfw.GLFW.glfwSetWindowPosCallback;

public class TCWindowPos implements ITPart {
    static TLogger logger = new TLogger(TCWindowPos.class);
    public TLogger getLogger() {
        return logger;
    }

    static TWindow windowInstance = TEngine.ENGINE_WINDOW;
    static int xpos,ypos;
    static boolean isUsed;
    static TMPosition position;
    private static void callback(long window,int xpos,int ypos){
        TCWindowPos.xpos = xpos;
        TCWindowPos.ypos = ypos;
    }
    public int getXpos() {
        return xpos;
    }
    public int getYpos() { return ypos; }
    public TMPosition getPosition(){
        position = TMath.createPosition_i(xpos,ypos);
        return position;
    }
    @Override
    public void init() {
        TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem().addPart(this.getLogger());
        logger.setState(TEngine.ENGINE_CONTROLLER.getState());
        this.getLogger().attachTo(TEngine.ENGINE_WINDOW.getLogger());
        glfwSetWindowPosCallback(windowInstance.TWindow_code.code,TCWindowPos::callback);
    }

    @Override
    public void loop(float dt) {
        logger.setState(TEngine.ENGINE_CONTROLLER.getState());
    }

    @Override
    public void dispose() {
        logger.setState(TEngine.ENGINE_CONTROLLER.getState());
    }
    private TCWindowPos(){

    }
    public static TCWindowPos createInstance(){
        if (!isUsed){
            return new TCWindowPos();
        }else{
            return null;
        }
    }
}
