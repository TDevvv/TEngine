package com.thundergod.tcallbacks.core;

import com.thundergod.ITPart;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.tlogger.core.TLogger;
import com.thundergod.twindow.core.TWindow;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;

public class TCKey implements ITPart {
    static TLogger logger = new TLogger(TCKey.class);
    public TLogger getLogger() {
        return logger;
    }

    static TWindow windowInstance = TEngine.ENGINE_WINDOW;
    enum State{
        PRESS,RELEASE
    }
    static HashMap<Integer,State> keyStates = new HashMap<>();
    static boolean[] keys = new boolean[500];
    private static void callback(long window, int key, int scancode, int action, int mods){
        if (!keyStates.containsKey(key)){
            keyStates.put(key,State.RELEASE);
        }
        if (action== GLFW.GLFW_PRESS){
            if (key<0||key>500){
                return;
            }
            keyStates.put(key,State.PRESS);
            keys[key] = true;
        }else if (action == GLFW.GLFW_RELEASE){
            if (key<0||key>500){
                return;
            }
            keyStates.put(key,State.RELEASE);
            keys[key] = false;
        }
    }
    public boolean isKeyPressed(int key){
        return keys[key];
    }
    public boolean isJustPressed(){
        return false;
    }
    public boolean _press(int key){
        return keyStates.get(key)==State.PRESS;
    }
    public boolean _release(int key){
        return keyStates.get(key)==State.RELEASE;
    }
    public void call(int keycode,IKeyCall keyCall){
        if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(keycode)){
            keyCall._do();
        }
    }

    @Override
    public void init() {
        TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem().addPart(this.getLogger());
        logger.setState(TEngine.ENGINE_CONTROLLER.getState());
        glfwSetKeyCallback(windowInstance.TWindow_code.code,TCKey::callback);
    }
    int jump_count = 1;
    public  void platformerDefaultMovement(Body body){
        float velX = 0f;
        float speed = 10f;
        if (body.getContactList()!=null){
        boolean is = body.getLinearVelocity().y==0.0f;
        if (is){
            jump_count=1;
        }
        }
        if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW.GLFW_KEY_SPACE)&&jump_count==1&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_LEFT_SHIFT)){
            body.applyLinearImpulse(new Vec2(0.0f,1.5f),body.getWorldCenter());
            jump_count --;
        }
        if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_A)&&body.getLinearVelocity().x>=-10.0f&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_D)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_LEFT_SHIFT)){
            velX = -1;
        }
        if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW.GLFW_KEY_D)&&body.getLinearVelocity().x<=10.0f&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_A)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_LEFT_SHIFT)){
            velX = 1;
        }
        body.setLinearVelocity(new Vec2(velX*speed,body.getLinearVelocity().y));
    }
    @Override
    public void loop(float dt) {
        logger.setState(TEngine.ENGINE_CONTROLLER.getState());
    }

    @Override
    public void dispose() {

        logger.setState(TEngine.ENGINE_CONTROLLER.getState());
    }
    public interface IKeyCall{
        void _do();
    }
}
