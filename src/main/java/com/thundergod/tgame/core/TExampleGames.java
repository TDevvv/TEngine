package com.thundergod.tgame.core;

import com.thundergod.tgame.util.T2DPlatformerGame;
import com.thundergod.tmain.interfaces.ITMHasFirst;
import com.thundergod.tgame.util.T2DFlatGame;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.tlogger.core.TLogger;
import com.thundergod.ITPart;
import com.thundergod.twindow.tscene_system.util.TScene;
import org.lwjgl.glfw.GLFW;
import org.joml.Vector2i;

public class TExampleGames implements ITPart, ITMHasFirst {
    static float g;
    @Override
    public void dispose() {
        logger = null;
    }
    @Override
    public void loop(float dt) {
       // logger.info("Frames Per Second [FPS] : "+TEngine.ENGINE_TIME.fps());
    }
    static TScene scene;
    static TLogger logger = new TLogger(TExampleGames.class);

    public static TLogger getLogger() {
        return logger;
    }
    public static void startFlat(){
        g = 1;
        TExampleGames game = new TExampleGames();
        scene  =new T2DFlatGame(TEngine.ENGINE_CAMERA);
        TEngine.attachTPartToEnvironment(game, TExampleGames.class);
        TEngine.ENGINE.start();
    }
    public static void startPlatformer(){
        g = 0;
        TExampleGames game = new TExampleGames();
        scene = new T2DPlatformerGame(TEngine.ENGINE_CAMERA);
        TEngine.attachTPartToEnvironment(game, TExampleGames.class);
        TEngine.ENGINE.start();
    }
    public static void main(String[] args) {
    }

    @Override
    public void init() {
        TEngine.ENGINE_SCENE$SYSTEM.changeScene(TExampleGames.scene);
    }
    @Override
    public void first() {
        TEngine.ENGINE_WINDOW.TWindow_background.changeColor(0,g,0);
        TEngine.attachLogger(logger);
        TEngine.ENGINE_WINDOW.addGLFWInitWindowHint(new Vector2i(GLFW.GLFW_MAXIMIZED,GLFW.GLFW_TRUE));
    }
}
