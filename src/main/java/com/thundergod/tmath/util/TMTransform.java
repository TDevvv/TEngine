package com.thundergod.tmath.util;

import org.joml.Vector2f;

public class TMTransform {
    public Vector2f pos;
    public Vector2f scale;
    public float rotation;
    public TMTransform(Vector2f pos,Vector2f scale){
        init(pos,scale);
    }
    public TMTransform(Vector2f scale){
        init(new Vector2f(),scale);
    }
    public TMTransform(Vector2f pos,Vector2f scale,float rotation){
        init(new Vector2f(),scale);
        this.rotation = rotation;
    }
    public TMTransform(){
        init(new Vector2f(),new Vector2f());
    }
    public void init(Vector2f pos,Vector2f scale){
        this.pos = pos;
        this.scale = scale;
    }
    public Vector2f getCenter(){
        return new Vector2f(pos.x+(scale.x/2),pos.y+(scale.y/2));
    }
    public TMTransform copy(){
        return new TMTransform(new Vector2f(this.pos),new Vector2f(this.scale));
    }
    public void copy(TMTransform rtr){
        rtr.pos.set(this.pos);
        rtr.scale.set(this.scale);
    }
    @Override
    public String toString() {
        return "TMTransform{" +
                "pos=" + pos +
                ", scale=" + scale +
                '}';
    }
    @Override
    public boolean equals(Object obj) {
        if (obj==null) return false;
        if (!(obj instanceof TMTransform)) return false;
        return ((TMTransform) obj).pos.equals(this.pos) && ((TMTransform) obj).scale.equals(this.scale);
    }
}
