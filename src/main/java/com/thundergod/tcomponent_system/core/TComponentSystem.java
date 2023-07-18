package com.thundergod.tcomponent_system.core;

import com.thundergod.ITPart;

import java.util.ArrayList;
import java.util.List;

public class TComponentSystem<T,K extends ITPart> implements ITPart {
    T parent;
    public List<K> componentList;

    public List<K> getComponentList() {
        return componentList;
    }

    public TComponentSystem(T parent){
        this.parent = parent;
        componentList = new ArrayList<>();
    }
    public void addPart(K component){
        componentList.add(component);
    }
    public T getParent(){
        return this.parent;
    }

    @Override
    public void init() {
        for (K component:
             this.componentList) {
            component.init();
        }
    }

    @Override
    public void loop(float dt) {
        for (K component:
             this.componentList) {
            component.loop(dt);
        }
    }

    @Override
    public void dispose() {
        for (K component:
             this.componentList) {
            component.dispose();
        }
    }

}
