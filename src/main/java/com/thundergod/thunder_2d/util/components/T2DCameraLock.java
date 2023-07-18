package com.thundergod.thunder_2d.util.components;

import com.thundergod.ITPart;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.thunder_2d.interfaces.INonParamaterLockable;
import com.thundergod.thunder_2d.util.projection.T2DCamera;
import com.thundergod.tobjectsystem.util.TOSComponent;

public class T2DCameraLock  extends TOSComponent implements INonParamaterLockable, ITPart {
    T2DCamera camera;
    boolean locked = true;

    public boolean isLocked() {
        return locked;
    }

    public float x_spacing,y_spacing;

    public float getX_spacing() {
        return x_spacing;
    }

    public float getY_spacing() {
        return y_spacing;
    }

    public T2DCameraLock(T2DCamera camera, float x_spacing, float y_spacing){
     this.camera = camera;
     this.x_spacing = x_spacing;
     this.y_spacing = y_spacing;
    }
    public T2DCameraLock(T2DCamera camera){
        this.camera = camera;
        this.x_spacing = 0;
        this.y_spacing = 0;
    }

    @Override
    public void update(float dt) {
        if (locked){
            float xlock,ylock;
            xlock = getParentObject().transform.pos.x+x_spacing;
            ylock = getParentObject().transform.pos.y+y_spacing;
            camera.lock(xlock,ylock);
        }
        super.update(dt);
    }

    public T2DCamera getCamera() {
        return camera;
    }
    public void setCamera(T2DCamera camera) {
        this.camera = camera;
    }

    @Override
    public void lock() {
        locked
                = true;
    }

    @Override
    public void unlock() {

        locked
                = false;
    }

    @Override
    public void remove() {
        TEngine.ENGINE_CAMERA.unlock();
    }

    @Override
    public void loop(float dt) {

    }
}
