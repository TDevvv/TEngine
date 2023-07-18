package com.thundergod.tgame.util;

import com.thundergod.thunder_2d.util.components.T2DCameraLock;
import com.thundergod.thunder_2d.util.components.T2DFontRenderer;
import com.thundergod.thunder_2d.util.components.Text;
import com.thundergod.thunder_2d.util.data.T2DMap;
import com.thundergod.thunder_2d.util.fabric.animation.T2DAnimation;
import com.thundergod.thunder_2d.util.components.T2DSpriteRenderer;
import com.thundergod.thunder_2d.util.fabric.T2DSpriteSheet;
import com.thundergod.thunder_2d.util.projection.T2DCamera;
import com.thundergod.tmath.util.TMPosition;
import com.thundergod.twindow.tscene_system.util.TScene;
import com.thundergod.tobjectsystem.core.TOSGameObject;
import com.thundergod.tdata_helper.core.TDataHelper;
import com.thundergod.tmath.util.TMTransform;
import com.thundergod.tengine.core.TEngine;
import org.lwjgl.glfw.GLFW;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class T2DFlatGame extends TScene {
    enum Side{
        RIGHT,LEFT,UP,DOWN
    }
    Side side = Side.DOWN;
    T2DAnimation animation;
    public T2DFlatGame(T2DCamera camera) {
        super(camera);
    }
    @Override
    public void init() {
        TEngine.ENGINE_ASSET_MANAGER._addSpriteSheet("char_base",T2DSpriteSheet._sheet(TDataHelper.assets("sSheet\\character.t2dss")));
        TEngine.ENGINE_ASSET_MANAGER._addSpriteSheet("grass_tile",T2DSpriteSheet._sheet(TDataHelper.assets("sSheet\\grass_tile.t2dss")));
        TEngine.ENGINE_ASSET_MANAGER._addSpriteSheet("water",T2DSpriteSheet._sheet(TDataHelper.assets("sSheet\\water.t2dss")));

        TOSGameObject fontRenderer = new TOSGameObject("frenderer",
                new TMTransform(
                        new Vector2f(),
                        new Vector2f()
                ),4);
        fontRenderer.addComponent(new T2DFontRenderer("test.tmp",25));
        TOSGameObject gameObject = new TOSGameObject("test",
                new TMTransform(
                        new Vector2f(),
                        new Vector2f(68,68)
                ),3);
        gameObject.addComponent(new T2DSpriteRenderer(TEngine.ENGINE_ASSET_MANAGER._getSpriteSheet("char_base").getSprites().get(0)));
        gameObject.addComponent(new T2DCameraLock(getCamera(),-300f,-150f));

        animation = new T2DAnimation(gameObject.getComponent(T2DSpriteRenderer.class), TEngine.ENGINE_ASSET_MANAGER._getSpriteSheet("char_base"),0.7f);
        animation.anim("down_stand",0,1);
        animation.anim("down_walk",2,3);
        animation.anim("up_stand",4,5);
        animation.anim("up_walk",6,7);
        animation.anim("left_stand",8,9);
        animation.anim("left_walk",10,11);
        animation.anim("right_stand",12,13);
        animation.anim("right_walk",14,15);

        T2DMap map = new T2DMap(TDataHelper.assets("maps\\flatmap.t2dm"));
        //map.readSpriteSheet(TEngine.ENGINE_ASSET_MANAGER._getSpriteSheet("water"),"wt","'water'",new TMScale(40,40));
        map.compile(new TMPosition(0,0));
        map.init(this);

        getCamera().setKeys(GLFW.GLFW_KEY_UP,GLFW.GLFW_KEY_DOWN,GLFW.GLFW_KEY_LEFT,GLFW.GLFW_KEY_RIGHT,10f);
    }

    @Override
    public void update(float dt) {
        if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_SPACE)){
            if (_object("test").getComponent(T2DCameraLock.class).isLocked()){
                _object("test").getComponent(T2DCameraLock.class).unlock();
            }else{
                _object("test").getComponent(T2DCameraLock.class).lock();
            }
        }
        if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_W)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_D)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_A)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_S)){
            side = Side.UP;
            _object("test").transform.pos.add(0,3.5f);
            animation.animate("up_walk",0.1f);
        }
        if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_S)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_D)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_A)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_W)){
            side = Side.DOWN;
            _object("test").transform.pos.add(0,-3.5f);
            animation.animate("down_walk",0.1f);
        }
        if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_A)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_W)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_S)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_D)){
            side = Side.LEFT;
            _object("test").transform.pos.add(-3.5f,0);
            animation.animate("left_walk",0.1f);
        }
        if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_D)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_W)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_S)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_A)){
            side = Side.RIGHT;
            _object("test").transform.pos.add(3.5f,0);
            animation.animate("right_walk",0.1f);
        }
        if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_W)&&TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_D)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_A)){
            side = Side.UP;
            _object("test").transform.pos.add(0,3.5f);
            _object("test").transform.pos.add(3.5f,0);
            animation.animate("right_walk",0.1f);
        }
        if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_W)&&TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_A)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_D)){
            side = Side.UP;
            _object("test").transform.pos.add(0,3.5f);
            _object("test").transform.pos.add(-3.5f,0);
            animation.animate("left_walk",0.1f);
        }
        if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_S)&&TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_D)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_A)){
            side = Side.UP;
            _object("test").transform.pos.add(0,-3.5f);
            _object("test").transform.pos.add(3.5f,0);
            animation.animate("right_walk",0.1f);
        }
        if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_S)&&TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_A)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_D)){
            side = Side.UP;
            _object("test").transform.pos.add(0,-3.5f);
            _object("test").transform.pos.add(-3.5f,0);
            animation.animate("left_walk",0.1f);
        }
        switch (side){
            case UP -> {
                animation.animate("up_stand",0.02f);
            }
            case DOWN -> {
                animation.animate("down_stand",0.02f);
            }
            case LEFT -> {
                animation.animate("left_stand",0.02f);
            }
            case RIGHT -> {
                animation.animate("right_stand",0.02f);
            }
            default -> {
                return;
            }
        }
        getCamera().update();
        update_obj(dt);
        this.getRenderer().render();
    }
}
