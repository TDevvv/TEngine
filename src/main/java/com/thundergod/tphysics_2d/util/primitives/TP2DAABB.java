package com.thundergod.tphysics_2d.util.primitives;

import org.joml.Vector2f;

public class TP2DAABB {
    private Vector2f center;
    private Vector2f size;
    public TP2DAABB(){

    }
    public TP2DAABB(Vector2f min,Vector2f max){
        this.size = new Vector2f(max).sub(min);
        this.center = new Vector2f(min).add(new Vector2f(size).mul(0.5f));
    }
}
