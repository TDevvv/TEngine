package com.thundergod.thunder_2d.core;

import com.thundergod.ITPart;
import com.thundergod.tcomponent_system.core.TComponentSystem;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.thunder_2d.util.T2DShader;
import com.thundergod.thunder_2d.util.components.*;
import com.thundergod.thunder_2d.util.data.T2DMap;
import com.thundergod.thunder_2d.util.fabric.T2DSprite;
import com.thundergod.thunder_2d.util.fabric.T2DSpriteSheet;
import com.thundergod.thunder_2d.util.fabric.T2DTexture;
import com.thundergod.thunder_2d.util.fabric.animation.T2DAnimation;
import com.thundergod.thunder_2d.util.projection.T2DCamera;
import com.thundergod.tlogger.core.TLogger;
import com.thundergod.tmath.core.TMath;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Thunder2D implements ITPart {
    static TLogger logger = new TLogger(Thunder2D.class);
    public TLogger getLogger() {
        return logger;
    }
    public static final TComponentSystem<Thunder2D, ITPart> T2D_COMPONENT_SYSTEM = new TComponentSystem<>(new Thunder2D());
    public static final Thunder2D T2D = T2D_COMPONENT_SYSTEM.getParent();
    public static final T2DShader T2D_SHADER = new T2DShader();
    public static final T2DCamera T2D_CAMERA = new T2DCamera(new Vector2f());
    public static final T2DTexture T2D_DEMO_TEXTURE = new T2DTexture(TEngine.ENGINE_DATA$HELPER.textures("test.jpg"), TMath.createScale(500,500));
    public static final T2DSprite T2D_SPRITE = new T2DSprite(T2D_DEMO_TEXTURE);
    public static final T2DMap T2D_MAP = new T2DMap(true);
    public void register(){
        TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem().addPart(Thunder2D.logger);
        T2D_COMPONENT_SYSTEM.addPart(T2D_SHADER);
        T2D_COMPONENT_SYSTEM.addPart(T2D_CAMERA);
        T2D_COMPONENT_SYSTEM.addPart(T2D_DEMO_TEXTURE);
        T2D_COMPONENT_SYSTEM.addPart(T2D_SPRITE);
        T2D_COMPONENT_SYSTEM.addPart(T2D_MAP);
    }
    @Override
    public void init() {
        logger.setState(TEngine.ENGINE_CONTROLLER.getState());
        T2D_COMPONENT_SYSTEM.init();
    }

    @Override
    public void loop(float dt) {
        logger.setState(TEngine.ENGINE_CONTROLLER.getState());
        T2D_COMPONENT_SYSTEM.loop(dt);
    }

    @Override
    public void dispose() {
        logger.setState(TEngine.ENGINE_CONTROLLER.getState());
        T2D_COMPONENT_SYSTEM.dispose();
    }
    private void attachPartToEnvironment(ITPart part){
        T2D_COMPONENT_SYSTEM.addPart(part);
    }
}
