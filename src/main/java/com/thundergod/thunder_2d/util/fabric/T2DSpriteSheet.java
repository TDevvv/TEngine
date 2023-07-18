package com.thundergod.thunder_2d.util.fabric;

import com.thundergod.tdata_helper.file_system.TU;
import com.thundergod.thunder_2d.util.fabric.animation.T2DAnimation;
import com.thundergod.tiostream.core.TIOStream;
import com.thundergod.tmath.util.TMScale;
import com.thundergod.tutil.core.TUText;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class T2DSpriteSheet {
    private T2DTexture ss_texture;
    private List<T2DSprite> sprites;

    public T2DSpriteSheet(T2DTexture texture,int spriteWidth,int spriteHeight,int spriteCount,int spriteSpacing){
        this.sprites = new ArrayList<>();
        this.ss_texture = texture;
        int c_x = 0;
        int c_y = texture.getScale_height()-spriteHeight;
        for (int i = 0; i < spriteCount; i++) {
            float t_y = (c_y+spriteHeight)/(float)texture.getScale_height();
            float r_x = (c_x+spriteWidth)/(float)texture.getScale_width();
            float l_x = c_x/(float)texture.getScale_width();
            float b_y = c_y/(float)texture.getScale_height();
            Vector2f[] tCoords = {
              new Vector2f(r_x,t_y),
              new Vector2f(r_x,b_y),
              new Vector2f(l_x,b_y),
              new Vector2f(l_x,t_y)
            };
            T2DSprite sprite = new T2DSprite(this.ss_texture,tCoords);
            this.sprites.add(sprite);

            c_x += spriteWidth+spriteSpacing;
            if (c_x>=texture.getScale_width()){
                c_x = 0;
                c_y-= spriteHeight+spriteSpacing;
            }
        }
    }
    public static class T2DSpriteSheetField{
        TMScale scale;
        TMScale split_scale;
        int count;
        int spacing;

        public TMScale getScale() {
            return scale;
        }

        public void setScale(TMScale scale) {
            this.scale = scale;
        }

        public TMScale getSplit_scale() {
            return split_scale;
        }

        public void setSplit_scale(TMScale split_scale) {
            this.split_scale = split_scale;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getSpacing() {
            return spacing;
        }

        public void setSpacing(int spacing) {
            this.spacing = spacing;
        }

        @Override
        public String toString() {
            return "T2DSpriteSheetField{" +
                    "scale=" + scale +
                    ", split_scale=" + split_scale +
                    ", count=" + count +
                    ", spacing=" + spacing +
                    '}';
        }
    }

    public static T2DSpriteSheet _sheet(String path){
        String img_path = null;
        TMScale scale = null;
        TMScale split = null;
        int count = 0;
        int spacing = 0;
        TIOStream stream = new TIOStream(path,true);
        stream.debugMode(true);
        stream.start();
        while (stream.canRead()){
            String r1 = stream.read();
            if (r1.equals("img")){
                img_path = TUText.deserializeQuotes(stream.read());
            }
            if (r1.equals("scale")){
                String r2 = stream.read();
                List<String> strs = TUText.convertDotToList(r2);
                scale = new TMScale(Integer.parseInt(strs.get(0)),Integer.parseInt(strs.get(1)));
            }
            if (r1.equals("split")){
                String r2 = stream.read();
                List<String> strs = TUText.convertDotToList(r2);
                split = new TMScale(Integer.parseInt(strs.get(0)),Integer.parseInt(strs.get(1)));
            }
            if (r1.equals("count")){
                count = Integer.parseInt(stream.read());
            }
            if (r1.equals("spacing")){
                spacing = Integer.parseInt(stream.read());
            }
        }
        stream.stop();
        return new T2DSpriteSheet(new T2DTexture(
                img_path,
                scale
        ),split.getWidth_i(),split.getHeight_i(),count,spacing);
    }

    public List<T2DSprite> getSprites() {
        return sprites;
    }
    public T2DTexture getSs_texture() {
        return ss_texture;
    }
}
