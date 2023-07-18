package com.thundergod.tphysics_2d.util.primitives;

import com.thundergod.tmath.util.TMScale;
import com.thundergod.tobjectsystem.core.TOSGameObject;
import com.thundergod.tobjectsystem.util.TOSComponent;
import jogamp.opengl.egl.EGLDummyUpstreamSurfaceHook;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.awt.*;

public class TP2DBody<T extends Shape> extends TOSComponent {
    private String name;
    public World world;
    public enum ShapeType{
        POLYGON,
        CIRCLE,
        EDGE,
        CHAIN;
    }
    public static TP2DBody<PolygonShape> createPolygon(World world, BodyType type, TMScale scale){
        PolygonShape shape  = new PolygonShape();
        shape.setAsBox((scale.getHeight_f()/30f)/2f,(scale.getHeight_f()/30f)/2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        TP2DBody<PolygonShape> rtr =
                new TP2DBody<>(world,new Vec2(),type,shape,fixtureDef);
        return rtr;
    }
    public static TP2DBody<CircleShape> createCircle(World world){
        return null;
    }
    public static TP2DBody<EdgeShape> createEdge(World world){
        return null;
    }
    public Body body;
    public BodyDef bodyDef;
    public Shape shape;
    public FixtureDef fixture;
    public TP2DBody(World world,Vec2 pos,BodyType type,T shape,FixtureDef fixture){
        this.bodyDef = new BodyDef();
        this.bodyDef.position.set(pos);
        this.bodyDef.type = type;
        this.shape = shape;
        this.body = world.createBody(bodyDef);
        this.fixture = fixture;
        this.body.createFixture(fixture);
    }
    @Override
    public void bind() {
        super.bind();
    }

    public TP2DBody(Body body, World world){
        this.body = body;
        this.world = world;
    }
    public TP2DBody(Body body, World world, Shape shape){
        this.body = body;
        this.world = world;
        this.shape = shape;
    }
    public TP2DBody(Body body, World world, Vec2 scale){
        this.body = body;
        this.world = world;
        this.shape = new PolygonShape();
        ((PolygonShape) shape).m_vertices[2].x = scale.x;
        ((PolygonShape) shape).m_vertices[2].y = scale.y;
    }
    public TP2DBody(Body body, World world, TOSGameObject copy){
        this.body = body;
        this.world = world;
        setParent(copy);
        this.body.setTransform(new Vec2(copy.transform.pos.x,copy.transform.pos.y),0);
    }

    public String getName() {
        if (name==null){
            name = getParentObject().getName();
        }
        return name;
    }
    Vec2 position;
    @Override
    public void update(float dt) {
        position = this.getBody().getPosition().mul(30);
        if (body.getType()==BodyType.DYNAMIC){
            this.getParentObject().transform.pos.x = position.x;
            this.getParentObject().transform.pos.y = position.y;
            this.getParentObject().transform.rotation = body.getAngle();
        }else{
            this.getParentObject().transform.pos.x = position.x;
            this.getParentObject().transform.pos.y = position.y;
            this.getParentObject().transform.rotation = body.getAngle();
        }
        super.update(dt);
    }

    public Body getBody() {
        return body;
    }

    public World getWorld() {
        return world;
    }
}
