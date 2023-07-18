package com.thundergod.tdebugdraw.core;

import com.thundergod.tengine.core.TEngine;
import com.thundergod.thunder_2d.util.components.T2DSpriteRenderer;
import com.thundergod.tlogger.core.TLogger;
import com.thundergod.tmath.util.TMTransform;
import com.thundergod.tobjectsystem.core.TOSGameObject;
import com.thundergod.tphysics_2d.util.primitives.TP2DCollider;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.joml.Vector4f;

public class TDebugDraw extends DebugDraw {
    public TDebugDraw(IViewportTransform viewport) {
        super(viewport);
    }

    @Override
    public void drawPoint(Vec2 argPoint, float argRadiusOnScreen, Color3f argColor) {
        TEngine.ENGINE_LOGGER.warning("dp");
    }

    @Override
    public void drawSolidPolygon(Vec2[] vertices, int vertexCount, Color3f color) {
        TEngine.ENGINE_LOGGER.warning("dsp");
    }

    @Override
    public void drawCircle(Vec2 center, float radius, Color3f color) {
        TEngine.ENGINE_LOGGER.warning("dc");
    }

    @Override
    public void drawSolidCircle(Vec2 center, float radius, Vec2 axis, Color3f color) {
        TEngine.ENGINE_LOGGER.warning("dsc");
    }

    @Override
    public void drawSegment(Vec2 p1, Vec2 p2, Color3f color) {
        TEngine.ENGINE_LOGGER.warning("ds");
    }

    @Override
    public void drawTransform(Transform xf) {
        TOSGameObject gameObject = new TOSGameObject("new",new TMTransform(),1);
        gameObject.addComponent(new T2DSpriteRenderer(new Vector4f(1,1,1,0)));
        gameObject.transform.pos.x = xf.p.x;
        gameObject.transform.pos.y = xf.p.y;
        gameObject.transform.scale.x = xf.q.s;
        gameObject.transform.scale.y = xf.q.c;
        TEngine.ENGINE_SCENE.getGameObjects().add(gameObject);
    }

    @Override
    public void drawString(float x, float y, String s, Color3f color) {

    }
}
