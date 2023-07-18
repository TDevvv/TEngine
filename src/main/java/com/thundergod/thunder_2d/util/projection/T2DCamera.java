package com.thundergod.thunder_2d.util.projection;

import com.thundergod.ITPart;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.thunder_2d.interfaces.ILockable;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.HashMap;

public class T2DCamera implements ITPart, ILockable {
    boolean locked;
    private float locked_x,locked_y;
    private Matrix4f projectionMatrix,viewMatrix;

    private HashMap<String,Integer> keys;
    boolean settledKeys;
    private float cameraMovementForce;
    public void setCameraMovementForce(float cameraMovementForce) {
        this.cameraMovementForce = cameraMovementForce;
    }

    public void setSettledKeys(boolean settledKeys) {
        this.settledKeys = settledKeys;
    }

    public boolean isSettledKeys() {
        return settledKeys;
    }

    public void setKeys(int up, int down, int left, int right,float force){
        keys.put("up",up);
        keys.put("down",down);
        keys.put("left",left);
        keys.put("right",right);
        settledKeys = true;
        this.cameraMovementForce = force;
        setSettledKeys(true);
    }
    private Vector2f position;
    public void set(float x,float y){
        position.set(x,y);
    }
    private void lookAt(){};
    public void translate_x(float x){
        position.x +=x;
    }
    public void translate_y(float y){
        position.y +=y;
    }

    public T2DCamera(Vector2f position){
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.keys = new HashMap<>();
    }
    float r0=20.0f,u0=10.0f;
    public void _xandy(float xay){
        this.r0 = xay;
        this.u0 = xay;
    }
    @Override
    public void init() {
        projectionMatrix.identity();
        projectionMatrix.ortho(0f,32.0f*r0,0.0f,32.0f*u0,0.0f,100.0f);
    }
    public Matrix4f getViewMatrix() {
        Vector3f camFront = new Vector3f(0.0f,0.0f,-1.0f);
        Vector3f camUp = new Vector3f(0.0f,1.0f,0.0f);
        this.viewMatrix.identity();
        this.viewMatrix.lookAt(new Vector3f(position.x,position.y,20.0f),
                camFront.add(position.x,position.y,0.0f),
                camUp);

        return viewMatrix;
    }
    public void update(){
        //projectionMatrix.ortho(0f,32.0f*r0,0.0f,32.0f*u0,0.0f,100.0f);
        if (locked){
            set(locked_x,locked_y);
        }
        else if (isSettledKeys()){
            if(TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(keys.get("up"))){
                translate_y(cameraMovementForce);
            }
            if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(keys.get("down"))){
                translate_y(-cameraMovementForce);
            }
            if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(keys.get("left"))){
                translate_x(-cameraMovementForce);
            }
            if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(keys.get("right"))){
                translate_x(cameraMovementForce);
            }
        }
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    @Override
    public void loop(float dt) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void lock(float x,float y) {
        locked = true;
        this.locked_x  =x;
        this.locked_y = y;
    }

    @Override
    public void unlock() {
        locked = false;
    }
}
