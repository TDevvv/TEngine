package com.thundergod.thunder_2d.util.other;

import com.thundergod.tmath.core.TMath;
import com.thundergod.tmath.util.TMPosition;
import org.joml.Vector2f;

public class CharI {
    public int sX;
    public int sY;
    public int w;
    public int h;
    public TMPosition[] coords;
    public CharI(int sX,int sY,int w,int h){
        this.sX = sX;
        this.sY = sY;
        this.w = w;
        this.h = h;
        coords = new TMPosition[4];
    }

    public void calcCoords(int fontWidth,int fontHeight) {
        float x0 = (float) sX /(float) fontWidth;
        float x1 = (float) (sX+w) /fontWidth;

        float y0 = (float) sY /(float)fontHeight;
        float y1 = (float) (sY+h) / (float) fontHeight;

        coords[0]  = TMath.createPosition_f(x0,y0);
        coords[1] = TMath.createPosition_f(x0,y1);
        coords[2] = TMath.createPosition_f(x1,y0);
        coords[3] = TMath.createPosition_f(x1,y1);

    }
}
