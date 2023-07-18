package com.thundergod.twindow.tscene_system.util;

import com.thundergod.ITPart;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.thunder_2d.util.projection.T2DCamera;
import com.thundergod.thunder_2d.util.render.T2DRenderer;
import com.thundergod.tlogger.core.TLogger;
import com.thundergod.tmain.interfaces.ITMCanBindAndUnbind;
import com.thundergod.tobjectsystem.core.TOSGameObject;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class TScene implements ITMCanBindAndUnbind, ITPart {
     static TLogger logger = new TLogger(TScene.class);

     private HashMap<String,Body> bodies;
     public void addBody(String key,Body body){
         this.bodies.put(key,body);
     }
     public World world;
    private T2DCamera camera;
    private T2DRenderer renderer;
    public T2DRenderer getRenderer() {
        return renderer;
    }

    public void setCamera(T2DCamera camera) {
        this.camera = camera;
    }
    public T2DCamera getCamera() {
        return camera;
    }

    private List<TOSGameObject> gameObjects;
    private final HashMap<String,TOSGameObject> utilMap;
    public HashMap<String, TOSGameObject> getUtilMap() {
        return utilMap;
    }

    public List<TOSGameObject> getGameObjects() {
        return gameObjects;
    }

    private boolean iR = false;
    public TScene(T2DCamera camera){
        this.utilMap = new HashMap<>();
        this.gameObjects = new ArrayList<>();
        this.camera = camera;
        this.renderer = new T2DRenderer();
        this.bodies = new HashMap<>();
    }
    public static TScene createNullScene(){
        return new TScene(TEngine.ENGINE_CAMERA) {
            @Override
            public void bind() {
            }

            @Override
            public void unbind() {

            }

            @Override
            public void update(float dt) {

            }

        };
    }
    public void init(){
    }
    public void dispose(){

    }
    public void addGameObjectToScene(TOSGameObject gameObject){
        if (iR){
            this.utilMap.put(gameObject.getName(),gameObject);
            this.gameObjects.add(gameObject);
            gameObject.bind();
            this.renderer.add(gameObject);
        }else{
            this.utilMap.put(gameObject.getName(),gameObject);
            gameObjects.add(gameObject);
        }
    }
    @Override
    public void bind() {
        for (TOSGameObject gameObject:
             gameObjects) {
            gameObject.bind();
            this.renderer.add(gameObject);
        }
        iR = true;
    }

    @Deprecated
    @Override
    public void loop(float dt) {

    }

    @Override
    public void unbind() {
        for (TOSGameObject gameObject:
             gameObjects) {
            gameObject.unbind();
        }
        iR = false;
    }

    public abstract void update(float dt);
    public void update_obj(float dt){
        for (TOSGameObject object:
             gameObjects) {
            object.update(dt);
        }
    }
    public TOSGameObject _object(String key$name){
        if (!TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem().getComponentList().contains(logger)){
            TEngine.attachLogger(logger);
        }
        TOSGameObject gameObject = this.utilMap.get(key$name);
        if (gameObject==null){
            logger.error("can not return object because key$ is not valid for any values.");
        }
        return this.utilMap.get(key$name);
    }
}
