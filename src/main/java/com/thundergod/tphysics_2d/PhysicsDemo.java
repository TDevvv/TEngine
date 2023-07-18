package com.thundergod.tphysics_2d;/*
 * Copyright (c) 2013, Oskar Veerhoek
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those
 * of the authors and should not be interpreted as representing official policies,
 * either expressed or implied, of the FreeBSD Project.
 */

import com.thundergod.ITPart;
import com.thundergod.tengine.core.TEngine;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.opengl.GL11.*;

/**
 * 30 pixels = 1 meter
 *
 * @author Oskar Veerhoek
 */
public class PhysicsDemo {

    private static final String WINDOW_TITLE = "Physics in 2D!";
    private static final int[] WINDOW_DIMENSIONS = {640, 480};

    private static final World world = new World(new Vec2(0, -9.8f));
    private static final Set<Body> bodies = new HashSet<>();

    Body body;
    public PhysicsDemo(Body body){
        this.body = body;
        setUpDisplay();
        setUpObjects();
        setUpMatrices();

    }
    public static  void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        for (Body body:
             bodies) {
            if (body.getType() == BodyType.DYNAMIC) {
                glPushMatrix();
                Vec2 bodyPosition = body.getPosition().mul(30);
                glTranslatef(bodyPosition.x, bodyPosition.y, 0);
                glRotated(Math.toDegrees(body.getAngle()), 0, 0, 1);
                glRectf(-50f, -50f, 50f, 50f);
                glPopMatrix();

            }
        }

        logic();
        input();
        update();
    }

    private static void logic() {
        world.step(1 / 60f, 8, 3);
    }

    private static void input() {
        for (Body body : bodies) {
            if (body.getType() == BodyType.DYNAMIC) {
                if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW.GLFW_KEY_D) && !TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW.GLFW_KEY_A)) {
                    body.applyAngularImpulse(+0.01f);
                } else if (TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW.GLFW_KEY_A) && !TEngine.ENGINE_KEY_CALLBACK.isKeyPressed(GLFW.GLFW_KEY_D)) {
                    body.applyAngularImpulse(-0.01f);
                }
                if (TEngine.ENGINE_MOUSE_CALLBACK.getMouseButtonDown()==1) {
                    Vec2 mousePosition = new Vec2(TEngine.ENGINE_MOUSE_CALLBACK.getX(), TEngine.ENGINE_MOUSE_CALLBACK.getY()).mul(0.5f).mul(1 / 30f);
                    Vec2 bodyPosition = body.getPosition();
                    Vec2 force = mousePosition.sub(bodyPosition);
                    body.applyForce(force, body.getPosition());
                }
            }
        }
    }

    private static void cleanUp(boolean asCrash) {
        System.exit(asCrash ? 1 : 0);
    }

    private static void setUpMatrices() {
        glMatrixMode(GL_PROJECTION);
        glOrtho(0, 320, 0, 240, 1, -1);
        glMatrixMode(GL_MODELVIEW);
    }

    private static void setUpObjects() {
        BodyDef boxDef = new BodyDef();
        boxDef.position.set(320 / 30 / 2, 240 / 30 / 2);
        boxDef.type = BodyType.DYNAMIC;
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(0.75f, 0.75f);
        Body box = world.createBody(boxDef);
        FixtureDef boxFixture = new FixtureDef();
        boxFixture.density = 0.1f;
        boxFixture.shape = boxShape;
        box.createFixture(boxFixture);
        bodies.add(box);

        BodyDef groundDef = new BodyDef();
        groundDef.position.set(0, 0);
        groundDef.type = BodyType.STATIC;
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(1000, 0);
        Body ground = world.createBody(groundDef);
        FixtureDef groundFixture = new FixtureDef();
        groundFixture.density = 1;
        groundFixture.restitution = 0.3f;
        groundFixture.shape = groundShape;
        ground.createFixture(groundFixture);
        bodies.add(ground);
    }

    private static void update() {

    }

    private static void enterGameLoop() {
            render();
            logic();
            input();
            update();

    }

    private static void setUpDisplay() {

    }

    public static void main(String[] args) {
        TEngine.ENGINE_WINDOW.TWindow_background.setBlack();
        TEngine.attachTPartToEnvironment(new ITPart() {
            @Override
            public void init() {
                setUpDisplay();
                setUpObjects();
                setUpMatrices();
            }

            @Override
            public void loop(float dt) {
                enterGameLoop();
            }

            @Override
            public void dispose() {

            }
        },PhysicsDemo.class);
        TEngine.ENGINE.start();
        cleanUp(false);
    }
}
