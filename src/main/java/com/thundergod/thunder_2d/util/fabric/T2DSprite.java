package com.thundergod.thunder_2d.util.fabric;

import com.thundergod.ITPart;
import org.joml.Vector2f;

public class T2DSprite implements ITPart {
    private T2DTexture texture;
    private Vector2f[] tCoords;
    public T2DSprite(T2DTexture texture){
        this.texture = texture;
        Vector2f[] tCoords = {
          new Vector2f(1,1),
          new Vector2f(1,0),
          new Vector2f(0,0),
          new Vector2f(0,1)
        };
        this.tCoords = tCoords;
    }
    public T2DSprite(T2DTexture texture,Vector2f[] tCoords){
        this.texture = texture;
        this.tCoords = tCoords;
    }
    public T2DTexture getTexture() {
        return texture;
    }
    public Vector2f[] getTCoords() {
        return tCoords;
    }

    @Override
    public void init() {

    }

    @Override
    public void loop(float dt) {

    }

    @Override
    public void dispose() {

    }
}
