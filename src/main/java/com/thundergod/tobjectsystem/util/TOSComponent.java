package com.thundergod.tobjectsystem.util;

import com.thundergod.tobjectsystem.core.TOSGameObject;
import com.thundergod.tobjectsystem.interfaces.ITOSComponent;

public class TOSComponent extends ITOSComponent {
    private TOSGameObject parent;
    boolean iR;

    public TOSGameObject getParentObject() {
        return parent;
    }

    public void setParent(TOSGameObject parent) {
        this.parent = parent;
    }

    @Override
    public void init() {
        updateIR();
    }

    @Override
    public void bind() {
        updateIR();
    }

    @Override
    public void unbind() {
        updateIR();
    }

    @Override
    public void dispose() {
        updateIR();
    }

    @Override
    public void update(float dt) {
        updateIR();
    }
    public void remove(){
    }
    private void updateIR(){
        this.iR = parent.iR;
    }
}
