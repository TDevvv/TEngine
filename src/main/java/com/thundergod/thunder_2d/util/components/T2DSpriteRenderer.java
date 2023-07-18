package com.thundergod.thunder_2d.util.components;

import com.thundergod.ITPart;
import com.thundergod.thunder_2d.util.fabric.T2DSprite;
import com.thundergod.thunder_2d.util.fabric.T2DTexture;
import com.thundergod.tmath.util.TMPosition;
import com.thundergod.tmath.util.TMTransform;
import com.thundergod.tobjectsystem.util.TOSComponent;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class T2DSpriteRenderer extends TOSComponent implements ITPart {
    private Vector4f color;
    private T2DSprite sprite;
    private TMTransform l_transform;
    private float angle;
    public void setAngle(float angle) {
        this.angle = angle;
        this.flag = true;
    }

    private float[] vertices;
    public void setVertices(float[] vertices) {
        this.vertices = vertices;
    }
    public float[] getVertices() {
        return vertices;
    }

    private boolean isPrimitive;

    public void setPrimitive(boolean primitive) {
        isPrimitive = primitive;
    }
    public boolean isPrimitive() {
        return isPrimitive;
    }

    private boolean flag = false;

    public T2DSpriteRenderer(Vector4f color){
        this.color = color;
        this.sprite = new T2DSprite(null);
        this.flag = true;

    }
    public T2DSpriteRenderer(T2DSprite sprite){
     this.sprite= sprite;
     this.color = new Vector4f(1,1,1,1);
     this.flag = true;
    }
    public T2DSpriteRenderer(T2DSprite sprite, float angle){
        this.angle = angle;
        this.sprite = sprite;
        this.color = new Vector4f(1,1,1,0);
        this.flag = true;
    }
    public T2DTexture getTexture() {
        return sprite.getTexture();
    }
    public T2DSprite getSprite() {
        return sprite;
    }
    public Vector2f[] getTCoords(){
        return sprite.getTCoords();
    }

    public void setSprite(T2DSprite sprite) {
        this.sprite = sprite;
        this.flag = true;
    }

    public void setColor(Vector4f color) {
        if (!this.color.equals(color)){
            this.flag = true;
            this.color.set(color);
        }
    }

    public Vector4f getColor() {
        return color;
    }


    @Override
    public void bind() {
        this.l_transform = getParentObject().transform.copy();
        this.flag = true;
        super.bind();
    }

    @Override
    public void update(float dt) {
        if (!this.l_transform.equals(this.getParentObject().transform)){
            this.getParentObject().transform.copy(this.l_transform);
            flag = true;
        }
        super.update(dt);
    }

    @Override
    public void loop(float dt) {

    }
    public boolean _flag(){
        return this.flag;
    }
    public void _resetFlag(){
        this.flag = false;

    }

    public float getAngle() {
        return angle;
    }
}
