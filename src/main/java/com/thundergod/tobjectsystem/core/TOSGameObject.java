package com.thundergod.tobjectsystem.core;

import com.thundergod.ITPart;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.thunder_2d.util.components.T2DCameraLock;
import com.thundergod.thunder_2d.util.components.T2DPlayer;
import com.thundergod.thunder_2d.util.components.T2DSpriteRenderer;
import com.thundergod.thunder_2d.util.fabric.T2DSprite;
import com.thundergod.thunder_2d.util.fabric.T2DTexture;
import com.thundergod.thunder_2d.util.render.T2DDebugDraw;
import com.thundergod.tlogger.core.TLogger;
import com.thundergod.tmain.interfaces.ITMBasicHandler;
import com.thundergod.tmath.core.TMath;
import com.thundergod.tmath.util.TMPosition;
import com.thundergod.tmath.util.TMScale;
import com.thundergod.tmath.util.TMTransform;
import com.thundergod.tobjectsystem.util.TOSComponent;
import com.thundergod.tphysics_2d.util.primitives.TP2DBody;
import com.thundergod.twindow.tscene_system.util.TScene;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.joml.Matrix2f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TOSGameObject implements ITPart, ITMBasicHandler {
    TOSGameObject old;
    static TLogger logger = new TLogger(TOSGameObject.class);
    public static TLogger getLogger() {
        return logger;
    }
    static {TEngine.attachLogger(logger);}
    HashMap<T2DPlayer.Facing,Vec2[]> facingVertices;
    ArrayList<LogicVertices> logicVertices;
    public void set(T2DPlayer.Facing facing, Vec2[] vertices){
        this.facingVertices.put(facing,vertices);
    }
    public void set(LogicVertices vertices){
        this.logicVertices.add(vertices);
    }
    public static class LogicVertices{
        IIFCall call;
        Vec2[] vertices;
        public LogicVertices(IIFCall call,Vec2[] vertices){
            this.call = call;
            this.vertices = vertices;
        }

        public void setVertices(Vec2[] vertices) {
            this.vertices = vertices;
        }

        public void setCall(IIFCall call) {
            this.call = call;
        }

        public IIFCall getCall() {
            return call;
        }

        public Vec2[] getVertices() {
            return vertices;
        }
    }
    public interface IIFCall{
        boolean If();
    }
    Body body;
    TScene parentScene;
    float body_width,body_height,bhx,bhy;

    enum Direction{
        UP,DOWN,RIGHT,LEFT
    }
    private void _init_D(){
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Fixture fixture = body.getFixtureList();
        PolygonShape old  = (PolygonShape) fixture.m_shape;
        Vec2[] transf = old.m_vertices;
        Vec2 s = body.getPosition();
        s.y = s.y-((+transf[0].y)/4);
        fixtureDef.shape = shape;
        shape.setAsBox((+(transf[0].x))-(0.1f),0.1f,s, transform.rotation);
        body.createFixture(fixtureDef);
    }
    public boolean _contact(Direction direction){
        return false;
    }
    public boolean goingUp(){
        return body.getLinearVelocity().y>0;
    }
    public boolean goingDown(){
        return body.getLinearVelocity().y<0;
    }
    public boolean onPerfectVelocity(){
        return body.getLinearVelocity().y==0;
    }
    public boolean canJump(){
        return !isOnAir();
    }
    public Body getBody() {
        if (body==null){
            TP2DBody component = getComponent(TP2DBody.class);
            if (component!=null){
              body = component.getBody();
            }
        }
        return body;
    }

    String name;
    int z_i;

    public boolean isOnAir(){
        return !isOnGround();
    }
    public boolean isOnGround(){
        return body.getLinearVelocity().y==0f&&isTouching();
    }
    public void drawDebug(){
      PolygonShape shape = (PolygonShape) body.getFixtureList().getShape();
      float x = body.getPosition().mul(30f).x;
      float y = body.getPosition().mul(30f).y;
      float w = shape.m_vertices[2].x*30f*2f;
      float h = shape.m_vertices[2].y*30f*2f;
      float rot = transform.rotation;
        T2DDebugDraw.start();
        T2DDebugDraw.beginFrame();
        T2DDebugDraw.addBox2D(new Vector2f(x,y),new Vector2f(w,h),rot);
        T2DDebugDraw.draw();
    }
    public void drawDebugDetailed(){
        T2DDebugDraw.start();
        T2DDebugDraw.beginFrame();
        PolygonShape shape = ((PolygonShape) body.getFixtureList().getShape());
        Vec2[] vertices =new Vec2[4];
        vertices[0] = shape.m_vertices[0].mul(30f).add(TMath.to(transform.pos));
        vertices[1] = shape.m_vertices[1].mul(30f).add(TMath.to(transform.pos));
        vertices[2] = shape.m_vertices[2].mul(30f).add(TMath.to(transform.pos));
        vertices[3] = shape.m_vertices[3].mul(30f).add(TMath.to(transform.pos));
        for (int i = 0; i < vertices.length-1; i++) {
            T2DDebugDraw.addLine2D(TMath.to(vertices[i]),TMath.to(vertices[i+1]));
        }
        T2DDebugDraw.addLine2D(TMath.to(vertices[0]),TMath.to(vertices[3]));
        T2DDebugDraw.draw();
    }
    public void refreshOld(){
        old = this;
    }
    public void delete(){
        refreshOld();
        getComponent(T2DSpriteRenderer.class).setColor(new Vector4f(0,0,0,0));
        getComponent(T2DSpriteRenderer.class);
        removeComponent(T2DCameraLock.class);
        removeComponent(TP2DBody.class);
        TEngine.ENGINE_SCENE$SYSTEM.getCurrentScene().getGameObjects().remove(this);
    }
    public boolean isTouching(TOSGameObject to){
        return isTouching(to,false);
    }
    public boolean isTouching(TOSGameObject to,boolean showDebug){
        if (showDebug){
            logger.info("given body: "+to.body.toString());
            if (body.getContactList()!=null){
                logger.info("res body: "+body.getContactList().other.toString());
            }
        }
        return body.getContactList() != null && body.getContactList().contact.isTouching()&&body.getContactList().other == to.body;
    }
    public boolean isTouching(){
        return body.getContactList()!=null&&body.getContactList().contact.isTouching();
    }
    public String getName() {
        return name;
    }

    Class<?> _class = this.getClass();
    List<TOSComponent> componentList;
    public TMTransform transform;
    T2DPlayer.Facing facing;

    private TMTransform[] vertices;
    public TOSGameObject(String name){
        this.name = name;
        logger.objectCreate(this._class,this.name);
        this.z_i = 0;
        componentList = new ArrayList<>();
        this.facingVertices = new HashMap<>();
        this.transform = new TMTransform();
        this.vertices = new TMTransform[4];
        this.logicVertices = new ArrayList<>();
        TEngine.ENGINE_SCENE$SYSTEM.getCurrentScene().addGameObjectToScene(this);
    }
    public TOSGameObject(String name,int z_i){
        this.name = name;
        logger.objectCreate(this._class,this.name);
        this.z_i = z_i;
        componentList = new ArrayList<>();
        this.transform = new TMTransform();
        this.vertices = new TMTransform[4];
        this.logicVertices = new ArrayList<>();
        TEngine.ENGINE_SCENE$SYSTEM.getCurrentScene().addGameObjectToScene(this);
        this.facingVertices = new HashMap<>();
    }
    public TOSGameObject(String name, TScene parentScene,int z_i){
        this.name = name;
        logger.objectCreate(this._class,this.name);
        this.z_i = z_i;
        componentList = new ArrayList<>();
        this.transform = new TMTransform();
        this.vertices = new TMTransform[4];
        this.parentScene = parentScene;
        TEngine.ENGINE_SCENE$SYSTEM.getCurrentScene().addGameObjectToScene(this);
        this.facingVertices = new HashMap<>();
        this.logicVertices = new ArrayList<>();
    }
    public TOSGameObject(String name, TScene parentScene){
        this.name = name;
        logger.objectCreate(this._class,this.name);
        this.z_i = 0;
        componentList = new ArrayList<>();
        this.transform = new TMTransform();
        this.vertices = new TMTransform[4];
        this.parentScene = parentScene;
        TEngine.ENGINE_SCENE$SYSTEM.getCurrentScene().addGameObjectToScene(this);
        this.facingVertices = new HashMap<>();
        this.logicVertices = new ArrayList<>();
    }
    public TOSGameObject(String name,TMTransform transform,int z_i){
        this.name = name;
        logger.objectCreate(this._class,this.name);
        this.z_i = z_i;
        this.componentList = new ArrayList<>();
        this.transform = transform;
        this.vertices = new TMTransform[4];
        TEngine.ENGINE_SCENE$SYSTEM.getCurrentScene().addGameObjectToScene(this);
        this.facingVertices = new HashMap<>();
        this.logicVertices = new ArrayList<>();
    }

    public void addComponent(TOSComponent component){
        if (component!=null){
            this.componentList.add(component);
            component.setParent(this);
            if (component instanceof TP2DBody){
                this.body = ((TP2DBody<?>) component).body;
                transform.pos.x = ((TP2DBody) component).body.getPosition().x;
                transform.pos.y = ((TP2DBody) component).body.getPosition().y;
                if (((TP2DBody<?>) component).shape instanceof PolygonShape){
                    if (this.isBodyScaleSync()){
                        transform.scale.y = ((PolygonShape)((TP2DBody) component).shape).m_vertices[2].y*2f*30f;
                        transform.scale.x = ((PolygonShape)((TP2DBody) component).shape).m_vertices[2].x*2f*30f;
                    }
                }
                TEngine.ENGINE_SCENE.addBody(((TP2DBody) component).getName(),((TP2DBody) component).body);
            }
            if (component instanceof T2DPlayer){
                this.facing =((T2DPlayer) component).getFacing();
            }
            if (component instanceof T2DCameraLock){

            }
            if (component.getParentObject()!=null){
                // logger.warning("this component already added another object.");
            }
        }

    }

    boolean bodyScaleSync = true;
    private boolean isBodyScaleSync() {
        return bodyScaleSync;
    }
    public void setBodyScaleSync(boolean bodyScaleSync) {
        this.bodyScaleSync = bodyScaleSync;
    }

    public void removeComponent(Class<?> comp_class){
        for (TOSComponent component_1:
             componentList) {
            if (comp_class.isAssignableFrom(component_1.getClass())) {
                component_1.remove();
                componentList.remove(component_1);
                return;
            }
        }
    }
    public <T extends TOSComponent> T getComponent(Class<T> _class){
        for (TOSComponent component:
             componentList) {
        if (_class.isAssignableFrom(component.getClass())){
                try{
                    return _class.cast(component);
                }catch (ClassCastException e){
                    throw new ClassCastException();
                }
            }
        }
        return null;
    }
    public void adaptTexture(T2DTexture texture){
        addComponent(new T2DSpriteRenderer(new T2DSprite(texture)));
    }

    @Override
    public void init() {
        TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem().addPart(logger);
    }

    @Override
    public void loop(float dt) {

    }

    @Override
    public void dispose() {
        this.iR = false;
        for (TOSComponent component:
             componentList) {
            component.dispose();
        }
    }
    public boolean iR;
    @Override
    public void bind() {
        this.iR = true;
        for (TOSComponent component:
             componentList) {
            component.bind();
        }
    }

    @Override
    public void unbind() {
        this.iR = false;
        for (TOSComponent component:
             componentList) {
            component.unbind();
        }
    }

    @Override
    public void update(float dt) {
        for (TOSComponent component:
             componentList) {
            component.update(dt);
            if (body!=null){
                PolygonShape shape = (PolygonShape) body.getFixtureList().getShape();
                T2DPlayer facing = getComponent(T2DPlayer.class);
                for (LogicVertices vertice:
                     this.logicVertices) {
                    if (vertice.call.If()){
                        for (int i = 0; i < shape.m_vertices.length; i++) {
                            shape.m_vertices[i] = vertice.vertices[i];
                        }
                    }
                }
                if (facing!=null){
                    Vec2[] vertices = facingVertices.get(facing.getFacing());
                    if (vertices!=null){
                        for (int i = 0; i < shape.m_vertices.length; i++) {
                            shape.m_vertices[i] = vertices[i];
                        }
                    }
                }
            }
        }
    }

    public static void createStaticGround(TMPosition position, TMScale scale){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0,0);
        bodyDef.type = BodyType.STATIC;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(scale.getHeight_f()/30f/2f,scale.getWidth_f()/30f/2f);
        Body body = TEngine.ENGINE_SCENE$SYSTEM.getCurrentScene().world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        TOSGameObject gameObject = new TOSGameObject("TOSGameObject::obj[new]->"+position.toString()+"/*/"+scale.toString(),
                new TMTransform(
                        new Vector2f(position._2vf()),
                        new Vector2f(scale._v2f())
                ),3);
        gameObject.addComponent(new TP2DBody<>(body,TEngine.ENGINE_SCENE$SYSTEM.getCurrentScene().world,shape));
    }
    public int getZ_i() {
        return z_i;
    }
}
