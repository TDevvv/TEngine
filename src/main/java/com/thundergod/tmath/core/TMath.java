package com.thundergod.tmath.core;

import com.thundergod.ITPart;
import com.thundergod.thunder_2d.util.fabric.T2DSprite;
import com.thundergod.tmath.util.TMPosition;
import com.thundergod.tmath.util.TMScale;
import org.jbox2d.common.Vec2;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.List;

public class TMath<T> implements ITPart {
    public static Vector2f to(Vec2 vec2){return new Vector2f(vec2.x,vec2.y);}
    public static Vec2 to(Vector2f vec2f){return new Vec2(vec2f.x,vec2f.y);}
    public static TMScale createScale(int width,int height){
        return new TMScale(width,height);
    }
    public static TMScale createScale(float width,float height){return new TMScale(width,height);};
    public static TMScale createScale(double width,double height){return new TMScale(width,height);};
    public static TMScale createNullScale(){
        return new TMScale(0,0);
    }
    public static TMPosition createPosition_f(float x,float y){
        return new TMPosition(x,y);
    }
    public static TMPosition createPosition_d(double x,double y){
        return new TMPosition(x,y);
    }
    public static TMPosition createPosition_i(int x,int y){
        return new TMPosition(x,y);
    }
    public static TMPosition createNullPosition(){
        return new TMPosition(0,0);
    }
    public static Vector2i createVector(int x, int y){
        return new Vector2i(x,y);
    }
    public static T2DSprite[] convertSheetToArray(List<T2DSprite> list){
        T2DSprite[] rtr = new T2DSprite[list.size()];
        for (int i = 0; i < rtr.length; i++) {
            rtr[i] =list.get(i);
        }
        return rtr;
    }
    public static int[] convertIntegerToint(List<Integer> integerList){
        int[] to = new int[integerList.size()];
        for (int i = 0; i < integerList.size(); i++) {
            if (i==integerList.get(i)){
                to[i] = integerList.get(i);
            }
        }
        return to;
    }
    public static Vector2f rotate(Vector2f vec, float angleDeg, Vector2f origin) {
        float x = vec.x - origin.x;
        float y = vec.y - origin.y;

        float cos = (float)Math.cos(Math.toRadians(angleDeg));
        float sin = (float)Math.sin(Math.toRadians(angleDeg));

        float xPrime = (x * cos) - (y * sin);
        float yPrime = (x * sin) + (y * cos);

        xPrime += origin.x;
        yPrime += origin.y;

        vec.x = xPrime;
        vec.y = yPrime;
        return vec;
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
