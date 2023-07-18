package com.thundergod.thunder_2d.util.fabric.animation;

import com.thundergod.tengine.core.TEngine;
import com.thundergod.thunder_2d.util.components.T2DSpriteRenderer;
import com.thundergod.thunder_2d.util.fabric.T2DSprite;
import com.thundergod.thunder_2d.util.fabric.T2DSpriteSheet;
import com.thundergod.thunder_2d.util.fabric.T2DTexture;
import com.thundergod.tiostream.core.TIOStream;
import com.thundergod.tlogger.core.TLogger;
import com.thundergod.tmath.core.TMath;
import com.thundergod.tmath.util.TMScale;
import com.thundergod.tutil.core.TUText;

import java.util.*;


public class T2DAnimation {
    static TLogger logger = new TLogger(T2DAnimation.class);
    static{
        logger.attachTo(TEngine.ENGINE_T2D.getLogger());
        TEngine.attachLogger(logger);
    }
    T2DSpriteSheet spriteSheet;
    Map<String, T2DSprite[]> anims;
    T2DSpriteRenderer renderer;
    T2DSprite[] sprites;
    boolean disable_first_frame_in_loop;
    public void setDisable_first_frame_in_loop(boolean disable_first_frame_in_loop) {
        this.disable_first_frame_in_loop = disable_first_frame_in_loop;
    }
    float ms;
    public T2DAnimation(T2DSpriteRenderer renderer,T2DSprite[] sprites,float ms){
        init(renderer,sprites);
        this.ms = ms;
        anims = new HashMap<>();
    }
    public T2DAnimation(T2DSpriteRenderer renderer, T2DSpriteSheet spriteSheet,float ms){
        init(renderer, TMath.convertSheetToArray(spriteSheet.getSprites()));
        this.ms = ms;
        anims = new HashMap<>();
        this.spriteSheet = spriteSheet;
    }
    public void _privSet(T2DSprite sprite){
        this.renderer.setSprite(sprite);
    }
    String path;
    public T2DAnimation(T2DSpriteRenderer renderer,String file_path){
        TEngine.ENGINE_ASSET_MANAGER._addSpriteSheet(file_path,T2DSpriteSheet._sheet(file_path));
        init(renderer,TMath.convertSheetToArray(TEngine.ENGINE_ASSET_MANAGER._getSpriteSheet(file_path).getSprites()));
        this.ms = 1f;
        anims = new HashMap<>();
        this.spriteSheet = TEngine.ENGINE_ASSET_MANAGER._getSpriteSheet(file_path);
    }
  private void init(T2DSpriteRenderer renderer, T2DSprite[] sprites){
        this.renderer = renderer;
        this.sprites = sprites;
    }
    float last_ms=0.0f;
    int spriteCount = 0;
    int spriteNum = 0;
    public void animate(String key,float dt){
        T2DSprite[] sprites1 = anims.get(key);
        spriteCount = sprites1.length;
        last_ms-=dt;
        if (last_ms<=0){
            last_ms = ms;
            spriteNum++;
            if (spriteNum>=spriteCount){
                spriteNum=0;
            }
            if (disable_first_frame_in_loop&&spriteNum==0){
                return;
            }
            renderer.setSprite(sprites1[spriteNum]);
        }
    }
    public void reset(){
        renderer.setSprite(sprites[0]);
    }
    public void anim(String key$,int ...array){
        anim(key$,spriteSheet,array);
    }
    List<int[]> arrays;
    public void anim(String key$,T2DSpriteSheet spriteSheet,int ...array){
        if (arrays==null){
            arrays= new ArrayList<>();
        }
        arrays.add(array);
        this.anims.put(key$,_piece(spriteSheet,array));
    }

    public static T2DSprite[] _piece(T2DSpriteSheet sprite,int ...nums){
        T2DSprite[] _return = new T2DSprite[nums.length];
        for (int i = 0; i < _return.length; i++) {
            _return[i] = sprite.getSprites().get(nums[i]);
        }
        return _return;
    }
}
