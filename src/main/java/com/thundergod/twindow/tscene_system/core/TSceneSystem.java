package com.thundergod.twindow.tscene_system.core;

import com.thundergod.ITPart;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.tlogger.core.TLogger;
import com.thundergod.twindow.tscene_system.util.TScene;

public class TSceneSystem implements ITPart {
    static TLogger logger = new TLogger(TSceneSystem.class);
    public TLogger getLogger() {
        return logger;
    }
    TScene nullScene;
    TScene currentScene;
    public TScene getCurrentScene() {
        return currentScene;
    }

    public TSceneSystem(TScene nullScene){
        this.nullScene = nullScene;
    }
    public void changeScene(TScene scene){
        if (scene==null){
            logger.warning("changed scene is null.");
            logger.warning("null scene converted to empty engine scene.");
            scene = nullScene;
        }
        this.currentScene = scene;
        logger.info("scene changed --> "+scene.getClass().getSimpleName());
        logger.info("scene init method is running.");
        this.currentScene.init();
        this.currentScene.bind();
        logger.info("scene init method is finished.");
    }
    @Override
    public void init() {
        TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem().addPart(this.getLogger());
        logger.setState(TEngine.ENGINE_CONTROLLER.getState());
        if (currentScene==null){
            currentScene = nullScene;
        }
    }

    @Override
    public void loop(float dt) {
        logger.setState(TEngine.ENGINE_CONTROLLER.getState());
        if (currentScene==null){
            currentScene = nullScene;
        }
        currentScene.update(dt);
    }

    @Override
    public void dispose() {
        if (currentScene==null){
            currentScene = nullScene;
        }
        logger.setState(TEngine.ENGINE_CONTROLLER.getState());
        currentScene.dispose();
    }
}
