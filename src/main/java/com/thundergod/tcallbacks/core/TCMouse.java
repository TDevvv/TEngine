package com.thundergod.tcallbacks.core;

import com.thundergod.ITPart;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.tlogger.core.TLogger;
import com.thundergod.twindow.core.TWindow;

import static org.lwjgl.glfw.GLFW.*;

public class TCMouse implements ITPart {
    static TLogger logger = new TLogger(TCMouse.class);
    public TLogger getLogger() {
        return logger;
    }

    private static TWindow windowInstance = TEngine.ENGINE_WINDOW;
    private static double scrollX, scrollY;
    private static double xPos, yPos, lastY, lastX, worldX, worldY, lastWorldX, lastWorldY;
    private static boolean mouseButtonPressed[] = new boolean[9];
    private static boolean isDragging;

    public boolean mouseButtonClicked(int number){
        return mouseButtonPressed[number];
    }
    public static boolean[] getMouseButtonPressed() {
        return mouseButtonPressed;
    }

    private static int mouseButtonDown = 0;


    static boolean isUsed;
    public static TCMouse createInstance(){
        if (!TCMouse.isUsed){
            isUsed = true;
            return new TCMouse();
        }else{
            return null;
        }
    }
    private TCMouse() {
        scrollX = 0.0;
        scrollY = 0.0;
        xPos = 0.0;
        yPos = 0.0;
        lastX = 0.0;
        lastY = 0.0;
    }

    public  void endFrame() {
        scrollY = 0.0f;
        scrollX = 0.0f;
    }

    public  void clear() {
        TCMouse.scrollX = 0.0;
        TCMouse.scrollY = 0.0;
        TCMouse.xPos = 0.0;
        TCMouse.yPos = 0.0;
        TCMouse.lastX = 0.0;
        TCMouse.lastY = 0.0;
        TCMouse.mouseButtonDown = 0;
        TCMouse.isDragging = false;
        for (int i = 0; i < TCMouse.mouseButtonPressed.length; i++) {
            TCMouse.mouseButtonPressed[i] = false;
        }
    }


    private static void mousePosCallback(long window, double xpos, double ypos) {
        if (TCMouse.mouseButtonDown > 0) {
            TCMouse.isDragging = true;
        }

        TCMouse.lastX = TCMouse.xPos;
        TCMouse.lastY = TCMouse.yPos;
        TCMouse.lastWorldX = TCMouse.worldX;
        TCMouse.lastWorldY = TCMouse.worldY;
        TCMouse.xPos = xpos;
        TCMouse.yPos = ypos;
    }

    private static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            TCMouse.mouseButtonDown++;

            if (button < TCMouse.mouseButtonPressed.length) {
                TCMouse.mouseButtonPressed[button] = true;
            }
        } else if (action == GLFW_RELEASE) {
            TCMouse.mouseButtonDown--;

            if (button < TCMouse.mouseButtonPressed.length) {
                TCMouse.mouseButtonPressed[button] = false;
                TCMouse.isDragging = false;
            }
        }
    }

    private static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        TCMouse.scrollX = xOffset;
        TCMouse.scrollY = yOffset;
    }

    public float getX() {
        return (float) TCMouse.xPos;
    }

    public float getY() {
        return (float) TCMouse.yPos;
    }

    @Override
    public void init() {
        TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem().addPart(this.getLogger());
        logger.setState(TEngine.ENGINE_CONTROLLER.getState());
        glfwSetScrollCallback(windowInstance.TWindow_code.code,TCMouse::mouseScrollCallback);
        glfwSetMouseButtonCallback(windowInstance.TWindow_code.code,TCMouse::mouseButtonCallback);
        glfwSetCursorPosCallback(windowInstance.TWindow_code.code,TCMouse::mousePosCallback);
    }

    @Deprecated(forRemoval = true)
    @Override
    public void loop(float dt) {

    }

    @Deprecated(forRemoval = true)
    @Override
    public void dispose() {

    }
    public  double getScrollX() {
        return scrollX;
    }

    public  double getScrollY() {
        return scrollY;
    }

    public  double getxPos() {
        return xPos;
    }

    public  double getyPos() {
        return yPos;
    }

    public  double getLastY() {
        return lastY;
    }

    public  double getLastX() {
        return lastX;
    }

    public  boolean isIsDragging() {
        return isDragging;
    }

    public int getMouseButtonDown() {
        return mouseButtonDown;
    }
}
