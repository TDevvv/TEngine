package com.thundergod.twindow.core;

import com.thundergod.ITPart;
import com.thundergod.tcallbacks.interfaces.ITWCallback;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.tlogger.core.TLogger;
import com.thundergod.tmath.core.TMath;
import com.thundergod.tmath.util.TMScale;
import com.thundergod.twindow.util.TWBackground;
import com.thundergod.twindow.util.TWCode;
import com.thundergod.twindow.util.TWConfig;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class TWindow  implements ITPart {
    static TLogger logger = new TLogger(TWindow.class);
    public TLogger getLogger() {
        return logger;
    }

    public TWCode TWindow_code;
    public TWConfig TWindow_config;
    public TWBackground TWindow_background;
    private List<Vector2i> TWindow_hints = new ArrayList<>();
    public void addGLFWInitWindowHint(Vector2i v0){
        this.TWindow_hints.add(v0);
    }
    private List<ITWCallback> before_init_calls = new ArrayList<>();
    public void addGLFWInitCallback(ITWCallback callback){
        this.before_init_calls.add(callback);
    }
    public void setWindowSize_i(TMScale scale){
        this.TWindow_config.Height = scale.getHeight_i();
        this.TWindow_config.Width = scale.getWidth_i();
    }
    public TWindow(TWConfig config){
        this.TWindow_config = config;
        this.TWindow_code = new TWCode(0);
        this.TWindow_background = new TWBackground(1,1,1,0);
    }
    @Override
    public void init() {
        addGLFWInitWindowHint(TMath.createVector(GLFW_VISIBLE,GLFW_FALSE));
        addGLFWInitWindowHint(TMath.createVector(GLFW_RESIZABLE,GLFW_TRUE));
        TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem().addPart(this.getLogger());
        logger.setState(TEngine.ENGINE_CONTROLLER.getState());
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        for (Vector2i vi:
             TWindow_hints) {
            glfwWindowHint(vi.x,vi.y);
        }

        TWindow_code.code = glfwCreateWindow(TWindow_config.Width, TWindow_config.Height, TWindow_config.Title, NULL, NULL);
        if ( TWindow_code.code == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwMakeContextCurrent(TWindow_code.code);

        glfwSwapInterval(1);

        glfwShowWindow(TWindow_code.code);

        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE,GL_ONE_MINUS_SRC_ALPHA);

        glClearColor(TWindow_background.r, TWindow_background.g, TWindow_background.b, TWindow_background.a);
        for (ITWCallback callback:
                before_init_calls) {
            callback.call(TEngine.ENGINE_WINDOW.TWindow_code.code);
        }
    }

    @Override
    public void loop(float dt) {
        logger.setState(TEngine.ENGINE_CONTROLLER.getState());

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    }
    public void l_util(){
        glfwSwapBuffers(TWindow_code.code);
        glfwPollEvents();
    }

    @Override
    public void dispose() {
        logger.setState(TEngine.ENGINE_CONTROLLER.getState());
        glfwFreeCallbacks(TWindow_code.code);
        glfwDestroyWindow(TWindow_code.code);
        this.TWindow_config = null;
        this.TWindow_background = null;
        this.TWindow_hints = null;
        this.TWindow_code = null;
        this.before_init_calls = null;
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
