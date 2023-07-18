package com.thundergod.tgame.util;

import com.thundergod.thunder_2d.util.components.Text;
import com.thundergod.thunder_2d.util.fabric.animation.T2DAnimation;
import com.thundergod.thunder_2d.util.components.T2DSpriteRenderer;
import com.thundergod.thunder_2d.util.components.T2DCameraLock;
import com.thundergod.thunder_2d.util.fabric.T2DSpriteSheet;
import com.thundergod.thunder_2d.util.components.T2DPlayer;
import com.thundergod.tmath.util.TMPosition;
import com.thundergod.tphysics_2d.util.primitives.TP2DBody;
import com.thundergod.thunder_2d.util.projection.T2DCamera;
import com.thundergod.twindow.tscene_system.util.TScene;
import com.thundergod.tobjectsystem.core.TOSGameObject;
import com.thundergod.tdata_helper.core.TDataHelper;
import com.thundergod.thunder_2d.util.data.T2DMap;
import org.jbox2d.collision.shapes.PolygonShape;
import com.thundergod.tmath.util.TMTransform;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.tmath.core.TMath;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.lwjgl.glfw.GLFW;
import org.joml.Vector2f;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public class T2DPlatformerGame extends TScene {
    public T2DPlatformerGame(T2DCamera camera) {
        super(camera);
    }
    Body box;
    T2DMap map;
    T2DAnimation animation;
    @Override
    public void init() {
        TEngine.ENGINE_ASSET_MANAGER._addSpriteSheet("samurai",T2DSpriteSheet._sheet(TDataHelper.assets("sSheet\\samurai.t2dss")));
        world = new World(new Vec2(0,-9f));

        BodyDef box2def = new BodyDef();
        box2def.position.set(0,0);
        box2def.type= BodyType.DYNAMIC;
        PolygonShape box2shape = new PolygonShape();
        box2shape.setAsBox(0.54f,1f);
        Vec2[] rightVertices = new Vec2[8];
        Vec2[] leftVertices = new Vec2[8];
        for (int i = 0; i < box2shape.m_vertices.length; i++) {
            rightVertices[i] = box2shape.m_vertices[i];
        }
        for (int i = 0; i < box2shape.m_vertices.length; i++) {
            leftVertices[i] = box2shape.m_vertices[i];
        }
        rightVertices[0] = new Vec2(-0.18f,rightVertices[0].y);
        rightVertices[3] = new Vec2(-0.18f,rightVertices[3].y);
        leftVertices[1] = new Vec2(+0.18f,leftVertices[1].y);
        leftVertices[2] = new Vec2(+0.18f,leftVertices[2].y);
        TEngine.ENGINE_LOGGER.info("vertices[]-> "+ Arrays.toString(box2shape.m_vertices));
        box = world.createBody(box2def);
        FixtureDef fixtureDef  =new FixtureDef();
        fixtureDef.shape = box2shape;
        fixtureDef.density=0.1f;
        fixtureDef.friction = 0f;
        box.createFixture(fixtureDef);

        TOSGameObject gameObject = new TOSGameObject("test",
                new TMTransform(
                        new Vector2f(0,0),
                        new Vector2f(60,60)
                ),2);
        gameObject.setBodyScaleSync(false);
        gameObject.addComponent(new TP2DBody<>(box,world,box2shape));
        gameObject.addComponent(new T2DSpriteRenderer(TEngine.ENGINE_ASSET_MANAGER._getSpriteSheet("samurai").getSprites().get(0)));
        gameObject.addComponent(new T2DPlayer(T2DPlayer.Facing.RIGHT));
        gameObject.addComponent(new T2DCameraLock(getCamera(),-300,-150));
        gameObject.getBody().setFixedRotation(true);
        gameObject.set(new TOSGameObject.LogicVertices(new TOSGameObject.IIFCall() {
            @Override
            public boolean If() {
                T2DPlayer player = _object("test").getComponent(T2DPlayer.class);
                return player!=null&&player.getFacing()== T2DPlayer.Facing.RIGHT;
            }
        },rightVertices));
        gameObject.set(new TOSGameObject.LogicVertices(new TOSGameObject.IIFCall() {
            @Override
            public boolean If() {
                T2DPlayer player = _object("test").getComponent(T2DPlayer.class);
                return player!=null&&player.getFacing()== T2DPlayer.Facing.LEFT;
            }
        },leftVertices));




        animation = new T2DAnimation(gameObject.getComponent(T2DSpriteRenderer.class),TEngine.ENGINE_ASSET_MANAGER._getSpriteSheet("samurai"), 0.7f);
        animation.anim("idle/right",0,1,2,3,4,5);
        animation.anim("idle/left",16,17,18,19,20);
        animation.anim("run/right",6,7,8,9,10,11,12,13);
        animation.anim("run/left",27,26,25,24,23);
        map = new T2DMap(TDataHelper.assets("maps\\platformermap.t2dm"));
        map.compile(new TMPosition(100,100));
        map.init(this);

        ////////////////////////////////////////////////////

        getCamera().setKeys(GLFW.GLFW_KEY_UP,GLFW.GLFW_KEY_DOWN,GLFW.GLFW_KEY_LEFT,GLFW.GLFW_KEY_RIGHT,10f);
    }

    @Override
    public void update(float dt) {
        if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_D)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_A)){
            animation.animate("run/right",0.1f);
            _object("test").getComponent(T2DPlayer.class).setFacing(T2DPlayer.Facing.RIGHT);
        }
        else if (_object("test").getComponent(T2DPlayer.class).getFacing()== T2DPlayer.Facing.RIGHT) {
            animation.animate("idle/right",0.1f);
        }
        if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_A)&&!TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW_KEY_D)){
            animation.animate("run/left",0.1f);
            _object("test").getComponent(T2DPlayer.class).setFacing(T2DPlayer.Facing.LEFT);
        }
        else if (_object("test").getComponent(T2DPlayer.class).getFacing()== T2DPlayer.Facing.LEFT) {
            animation.animate("idle/left",0.1f);
        }

        //TExampleGames.getLogger().info("facing: "+_object("test").getComponent(T2DPlayer.class).getFacing());
        _object("test").drawDebugDetailed();
        TEngine.ENGINE_KEY_CALLBACK.platformerDefaultMovement(_object("test").getBody());
        getCamera().update();
        world.step(dt, 8, 3);
        update_obj(dt);
        this.getRenderer().render();
    }
}

