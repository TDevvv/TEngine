package com.thundergod.thunder_2d.util.data;

import com.thundergod.ITPart;
import com.thundergod.tdata_helper.core.TDataHelper;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.thunder_2d.util.components.T2DSpriteRenderer;
import com.thundergod.thunder_2d.util.fabric.T2DSprite;
import com.thundergod.thunder_2d.util.fabric.T2DSpriteSheet;
import com.thundergod.thunder_2d.util.fabric.T2DTexture;
import com.thundergod.tiostream.core.TIOStream;
import com.thundergod.tiostream.util.TIOStreamUtils;
import com.thundergod.tlogger.core.TLogger;
import com.thundergod.tmath.core.TMath;
import com.thundergod.tmath.util.TMPosition;
import com.thundergod.tmath.util.TMScale;
import com.thundergod.tmath.util.TMTransform;
import com.thundergod.tobjectsystem.core.TOSGameObject;
import com.thundergod.tphysics_2d.util.primitives.TP2DBody;
import com.thundergod.tutil.core.TUText;
import com.thundergod.tutil.core.TUtil;
import com.thundergod.twindow.tscene_system.util.TScene;
import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.joml.Random;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class T2DMap implements ITPart {
    public List<List<String>> objectNameList = new ArrayList<>();
    public List<List<String>> getObjectNameList() {
        return objectNameList;
    }

    @Override
    public void init() {

    }

    @Override
    public void loop(float dt) {

    }

    @Override
    public void dispose() {

    }

    public static class Pointer<T,K>{
        T val;
        K pointer;
        public Pointer(T val,K pointer){
            this.val = val;
            this.pointer = pointer;
        }

        public K getPointer() {
            return pointer;
        }

        public T getVal() {
            return val;
        }

        public void setVal(T val) {
            this.val = val;
        }

        public void setPointer(K pointer) {
            this.pointer = pointer;
        }

        @Override
        public String toString() {
            Vector2f pos = null;
            if (val instanceof Vector2f){
                pos = (Vector2f) val;
            }else{
                pos = new Vector2f();
            }
            return "Pointer{" +
                    "val=" + pos.x +" "+pos.y+
                    ", pointer=" + pointer +
                    '}';
        }
    }
    public static class MapObject{
        public static MapObject create(){
            return new MapObject(TMath.createNullPosition(),TMath.createNullScale(),null);
        }
        TMPosition position;
        TMScale scale;
        T2DSprite sprite;
        String pointer;
        Vector4f color;
        BodyType type;

        public void setType(BodyType type) {
            this.type = type;
        }

        public void setColor(Vector4f color) {
            this.color = color;
        }

        public void setPointer(String pointer) {
            this.pointer = pointer;
        }

        public MapObject(TMPosition position, TMScale scale, T2DSprite sprite) {
            this.position = position;
            this.scale = scale;
            this.sprite = sprite;
        }

        public TMPosition getPosition() {
            return position;
        }

        public void setPosition(TMPosition position) {
            this.position = position;
        }

        public TMScale getScale() {
            return scale;
        }

        public void setScale(TMScale scale) {
            this.scale = scale;
        }

        public T2DSprite getSprite() {
            return sprite;
        }

        public void setSprite(T2DSprite sprite) {
            this.sprite = sprite;
        }

        @Override
        public String toString() {
            return "MapObject{" +
                    "position=" + position +
                    ", scale=" + scale +
                    ", sprite=" + sprite +
                    '}';
        }
    }
    public static class Mapping{
        List<MapObject> objects;
        int width,height,xspacing,yspacing,all,xTime,yTime;
        List<String> tos_objs;

        public List<String> getTos_objs() {
            return tos_objs;
        }

        private void getMapString(){
            StringBuilder builder = new StringBuilder();
            builder.append("\n");
            for (int i = 0; i < yTime; i++) {
                for (int j = 0; j < xTime; j++) {
                    builder.append("0");
                }
                builder.append("\n");
            }
            logger.debug(builder.toString());
        }
        public void render(TMPosition position){
            Random random = new Random();
            int i = 0;
            for (Pointer<Vector2f,String> pointer:
                 map) {
                for (MapObject mapObject:
                     objects) {
                    TOSGameObject gameObject;
                    if (mapObject.pointer.equals(pointer.pointer)){
                        pointer.val.add(position.getX_f(),position.getY_f());
                        gameObject = new TOSGameObject("T2DMap::obj["+pointer.pointer+"]/"+pointer.val.x+","+pointer.val.y,
                                new TMTransform(
                                        pointer.val,
                                        mapObject.scale._v2f()
                                ),
                                mapObject.position.getZ());
                        if (mapObject.type!=null){

                            BodyDef staticBox = new BodyDef();
                            staticBox.position.set(pointer.val.x/30f,pointer.val.y/30f);
                            staticBox.type = mapObject.type;
                            PolygonShape shape = new PolygonShape();
                            shape.setAsBox(gameObject.transform.scale.x/30f/2.0f,gameObject.transform.scale.y/30f/2.0f);
                            Body ground = TEngine.ENGINE_SCENE$SYSTEM.getCurrentScene().world.createBody(staticBox);
                            FixtureDef staticFixture = new FixtureDef();
                            staticFixture.shape = shape;
                            staticFixture.density = 0.1f;
                            ground.createFixture(staticFixture);
                            gameObject.addComponent(new TP2DBody<>(ground,TEngine.ENGINE_SCENE$SYSTEM.getCurrentScene().world,shape));
                        }
                        if (mapObject.sprite==null){
                            if (mapObject.color!=null){
                                gameObject.addComponent(new T2DSpriteRenderer(mapObject.color));
                            }
                        }else{
                            gameObject.addComponent(new T2DSpriteRenderer(mapObject.sprite));
                        }
                        tos_objs.add(gameObject.getName());
                       // TEngine.ENGINE_SCENE$SYSTEM.getCurrentScene().addGameObjectToScene(gameObject);
                    }
                }
            }
            for (MapObject mapObject:
                 objects) {
                if (mapObject.pointer.equals("null")){
                    TOSGameObject gameObject = new TOSGameObject("T2DMap::obj$free["+random.nextInt(10000)+"]"
                            ,
                            new TMTransform(
                                    new Vector2f(mapObject.position._2vf().add(0,0f)),
                                    new Vector2f(mapObject.scale._v2f())
                            ),mapObject.getPosition().getZ());
                    if (mapObject.type!=null){
                        BodyDef staticBox = new BodyDef();
                        staticBox.position.set(mapObject.position.getX_f()/30f,mapObject.position.getY_f()/30f);
                        staticBox.type = mapObject.type;
                        PolygonShape shape = new PolygonShape();
                        shape.setAsBox(gameObject.transform.scale.x/30f/2.0f,gameObject.transform.scale.y/30f/2.0f);
                        Body ground = TEngine.ENGINE_SCENE$SYSTEM.getCurrentScene().world.createBody(staticBox);
                        FixtureDef staticFixture = new FixtureDef();
                        staticFixture.shape = shape;
                        staticFixture.density = 0.1f;
                        ground.createFixture(staticFixture);
                        gameObject.addComponent(new TP2DBody<>(ground,TEngine.ENGINE_SCENE$SYSTEM.getCurrentScene().world,shape));
                    }
                    if (mapObject.sprite==null){
                        if (mapObject.color!=null){
                            gameObject.addComponent(new T2DSpriteRenderer(mapObject.color));
                        }
                    }else{
                        gameObject.addComponent(new T2DSpriteRenderer(mapObject.sprite));
                    }
                    tos_objs.add(gameObject.getName());
                }
            }
        }

        public String getMappingString() {
            return mappingString;
        }

        public void setMappingString(String mappingString) {
            this.mappingString = mappingString;
        }

        public void addObj(MapObject object){
            objects.add(object);
        }
        public List<Pointer<Vector2f,String>> map;
        public Mapping(int width,int height){
            this.width = width;
            this.height = height;
            map = new ArrayList<>();
            objects = new ArrayList<>();
            tos_objs = new ArrayList<>();
        }
        public Mapping(){
            this.width = 0;
            this.height =0;
            this.yspacing = 0;
            this.xspacing = 0;
            this.xTime = 0;
            this.yTime = 0;
            this.all = 0;
        }

        public void setSpacing(int xspacing,int yspacing){
            this.xspacing = xspacing;
            this.yspacing = yspacing;
        }
        private int  calc(){
            int xTime = width/xspacing;
            int yTime = height/yspacing;
            this.xTime = xTime;
            this.yTime = yTime;
            int all = xTime*yTime;
            this.all = all;
            return all;
        }
        public void setup(){
            int x_d = -(width/2)+(xspacing/2);
            int y_d = (height/2)-(yspacing/2);
            Vector2f startPos = new Vector2f(x_d,y_d);
            map.add(new Pointer<>(startPos,"e"));
            logger.debug("xtime: "+xTime);
            logger.debug("ytime: "+yTime);
            for (int i = 0; i < yTime; i++) {
                int y = y_d-(yspacing*i);
                for (int j = 0; j < xTime; j++) {
                    int x = x_d+(xspacing*j);
                    map.add(new Pointer<>(new Vector2f(x,y),"e"));
                    logger.debug("x: "+x+" y: "+y);
                }
            }
            //logger.debug(""+y_d);
            compileMappinString();
        }
        public void test(){

        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getXspacing() {
            return xspacing;
        }

        public void setXspacing(int xspacing) {
            this.xspacing = xspacing;
        }

        public int getYspacing() {
            return yspacing;
        }

        public void setYspacing(int yspacing) {
            this.yspacing = yspacing;
        }

        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }

        public int getxTime() {
            return xTime;
        }

        public void setxTime(int xTime) {
            this.xTime = xTime;
        }

        public int getyTime() {
            return yTime;
        }

        public void setyTime(int yTime) {
            this.yTime = yTime;
        }

        public List<Pointer<Vector2f, String>> getMap() {
            return map;
        }

        public void setMap(List<Pointer<Vector2f, String>> map) {
            this.map = map;
        }

        public static void main(String[] args) {
            TEngine.attachLogger(logger);
            TEngine.attachTPartToEnvironment(new ITPart() {
                @Override
                public void init() {
                    Mapping mapping = new Mapping(500,500);
                    mapping.setSpacing(50,50);
                    mapping.calc();
                    mapping.setup();
                    mapping.getMapString();
                }

                @Override
                public void loop(float dt) {

                }

                @Override
                public void dispose() {

                }
            },T2DMap.Mapping.class);
            TEngine.ENGINE.start();
        }

        @Override
        public String toString() {
            return "Mapping{" +
                    "width=" + width +
                    ", height=" + height +
                    ", xspacing=" + xspacing +
                    ", yspacing=" + yspacing +
                    ", all=" + all +
                    ", xTime=" + xTime +
                    ", yTime=" + yTime +
                    ", map=" + map +
                    '}';
        }
        String mappingString;
        public void resetAndSetMappingString(String mappingString){
            map = new ArrayList<>();
            this.mappingString = mappingString;
        }
        public void compileMappinString(){
            for (int i = 0; i < mappingString.length(); i++) {
               char c1 =  mappingString.charAt(i);
               map.get(i).pointer = String.valueOf(c1);
            }
        }
    }
    static TLogger logger = new TLogger(T2DMap.class);
    private void check(){
        if (!TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem().componentList.contains(logger)){
            TEngine.attachLogger(logger);
        }
    }
    File file;
    List<TOSGameObject> mapObjs;
    public T2DMap(boolean empty){
        if (empty){
            return;
        }else{
            check();
        }
    }
    public T2DMap(String path){
        check();
        File check = new File(path);
        if (!check.exists()){
            logger.warning("file is not exists.");
            try {
                boolean result = check.createNewFile();
                logger.debug("creating new file for path::  "+path+" result--> "+result);
                logger.objectCreate(File.class,"file");
            } catch (IOException e) {
                logger.fatal("can not create new file.");
                throw new RuntimeException(e);
            }
        }else{
            logger.info("found file path: "+path);
        }
        init(check);
    }
    public T2DMap(File file){
        check();
        if (!file.exists()){
            logger.warning("file is not exists.");
            try {
                boolean result = file.createNewFile();
                logger.debug("creating new file for path::  "+file.getAbsolutePath()+" result--> "+result);
                logger.objectCreate(File.class,"file");
            } catch (IOException e) {
                logger.fatal("can not create new file.");
                throw new RuntimeException(e);
            }
        }else{
            logger.info("found file path: "+file.getAbsolutePath());
        }
        init(file);
    }
    private  void  init(File file){
        this.file = file;
        this.mapObjs = new ArrayList<>();
    }
    public void setFlatStaticShape(TOSGameObject first,TOSGameObject last){
        BodyDef bodyDef = new BodyDef();
        ChainShape shape = new ChainShape();
        shape.createChain(new Vec2[]{new Vec2(first.transform.pos.x,first.transform.pos.y),new Vec2(last.transform.pos.x,last.transform.pos.y)},2);
        bodyDef.type = BodyType.STATIC;
        Body body = TEngine.ENGINE_SCENE$SYSTEM.getCurrentScene().world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }
    public void readSpriteSheet(T2DSpriteSheet sheet,String objectPattern,String sheet_key,TMScale scale){
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (T2DSprite sprite:
             sheet.getSprites()) {
            builder.append("create object "+objectPattern+i);
            i++;
            builder.append("\n");
        }
        i = 0;
        for (T2DSprite sprite:
             sheet.getSprites()) {
            builder.append("create img "+objectPattern+"s"+i+" "+sheet_key+" "+i);
            i++;
            builder.append("\n");
        }
        i = 0;
        for (T2DSprite sprite:
             sheet.getSprites()) {
           builder.append(objectPattern+i+" width "+scale.getWidth_i());
            builder.append("\n");
           builder.append(objectPattern+i+" height "+scale.getHeight_i());
            builder.append("\n");
           builder.append(objectPattern+i+" img "+objectPattern+"s"+i);
            builder.append("\n");
           builder.append(objectPattern+i+" pointer "+"null");
            builder.append("\n");
            i++;
        }
        TIOStream stream = new TIOStream(file,true);
        stream.start();
        stream.write(builder.toString());
        stream.stop();
        logger.info("auto create -> "+builder.toString());
    }
    public static final String
             CREATE ="create",
             METHOD="method",
             OBJECT="object",
             PARAMETERS="param",
             WIDTH = "width",
             HEIGHT = "height",
             POS_X = "xpos",
             POS_Y = "ypos",
             POS_Z = "zpos",
             IMAGE = "img",
             SET = "set",
             POINTER = "pointer",
             COLOR = "color",
             SPLIT = "split",
             MAPPING = "mapping",
             SPACING = "spacing",
             BODY = "body",
             SHAPE = "shape",
             TYPE = "type";

    private HashMap<String,List<String>> methodParams;
    private HashMap<String,String> methodExec;
    private HashMap<String,MapObject> objectBridges;
    private HashMap<String,String> images;
    private HashMap<String, Vector4f> colors;
    private HashMap<String,T2DSprite> imageInstances;
    private HashMap<String,String> pointers;
    private HashMap<String,Mapping> mappings;
    private HashMap<String,Boolean> canAdd;
    private HashMap<String, Body> bodyInstances;
    private HashMap<String,BodyType> types;
    private List<String> bodies;
    private List<String> methods,objects;
    private TMPosition position;
    public void compile(@SuppressWarnings("req:float") TMPosition position){
        this.position = position;
        methods = new ArrayList<>();
        objects = new ArrayList<>();
        bodies = new ArrayList<>();
        images = new HashMap<>();
        pointers = new HashMap<>();
        colors = new HashMap<>();
        mappings = new HashMap<>();
        types = new HashMap<>();

        canAdd = new HashMap<>();

        methodExec = new HashMap<>();
        methodParams = new HashMap<>();
        objectBridges = new HashMap<>();
        imageInstances = new HashMap<>();
        bodyInstances = new HashMap<>();

        TIOStream stream = new TIOStream(file,true);
        TIOStreamUtils streamUtils = new TIOStreamUtils(stream);
        stream.debugMode(true);
        stream.start();
        while (stream.canRead()){
            String r1 = stream.read();
            if (r1.equals(CREATE)){
              String r2 =    stream.read();
              if (r2.equals(METHOD)){
                  String r3 = stream.read();
                  methods.add(r3);
              }
              if (r2.equals(OBJECT)){
                  String r3 = stream.read();
                  objectBridges.put(r3,MapObject.create());
                  objects.add(r3);
                  pointers.put(r3,"null");
                  colors.put(r3,new Vector4f());
              }
              if (r2.equals(IMAGE)){
                  String r3 = stream.read();
                  String r4 = stream.read();
                  images.put(r3,TUText.deserializeQuotes(r4));
                  if (TEngine.ENGINE_ASSET_MANAGER._getSpriteSheet(images.get(r3))!=null){
                      String r5 = stream.read();
                      imageInstances.put(r3,TEngine.ENGINE_ASSET_MANAGER._getSpriteSheet(images.get(r3)).getSprites().get(Integer.parseInt(r5)));
                  }else{
                      imageInstances.put(r3,new T2DSprite(new T2DTexture(images.get(r3),TMath.createNullScale())));
                  }
              }
              if (r2.equals(COLOR)){
                  String r3 = stream.read();
                  String r4 = stream.read();
                  Vector4f color = new Vector4f();
                  int i = 0;
                  for (String str:
                  TUText.convertDotToList(r4)) {
                      if (i==0){
                          color.x = Float.parseFloat(str);
                      }
                      if (i==1){
                          color.y = Float.parseFloat(str);
                      }
                      if (i==2){
                          color.z = Float.parseFloat(str);
                      }
                      if (i==3){
                          color.w = Float.parseFloat(str);
                      }
                      i++;
                  }
                  colors.put(r3,color);
              }
              if (r2.equals(MAPPING)){
                  String r3 = stream.read();
                  String r4 = stream.read();
                List<String> strings  = TUText.convertDotToList(r4);
                  mappings.put(r3,new Mapping(Integer.parseInt(strings.get(0)),Integer.parseInt(strings.get(1))));
              }
              if (r2.equals(BODY)){
                  String r3  =stream.read();
                  bodies.add(r3);
                  bodyInstances.put(r3,null);
              }
            }else{
                if (r1.equals(WIDTH)){
                    String r2 = stream.read();
                    if (r2.equals(SET+TUText.setRectBracket(WIDTH))){
                        TUtil.ReworkOptional<List<String>, String> reworkOptional = streamUtils.readMethod(SET+TUText.setRectBracket(WIDTH));
                        List<String> elements  =  reworkOptional.get();
                        logger.info("setting "+r1+" for "+elements.toString());
                        for (String obj:
                                objects) {
                            for (String objCheck:
                                    elements) {
                                if (obj.equals(objCheck)){
                                    float v1 = Float.parseFloat(reworkOptional.getVal());
                                    objectBridges.get(obj).scale.setWidth_i((int) v1);
                                    objectBridges.get(obj).scale.setWidth_f(v1);
                                    objectBridges.get(obj).scale.setWidth_d((double) v1);
                                }
                            }
                        }
                    }

                }
                if (r1.equals(HEIGHT)){
                    String r2 = stream.read();
                    if (r2.equals(SET+TUText.setRectBracket(HEIGHT))){
                        TUtil.ReworkOptional<List<String>, String> reworkOptional = streamUtils.readMethod(SET+TUText.setRectBracket(HEIGHT));
                        List<String> elements  =  reworkOptional.get();
                        logger.info("setting "+r1+" for "+elements.toString());
                        for (String obj:
                                objects) {
                            for (String objCheck:
                                    elements) {
                                if (obj.equals(objCheck)){
                                    float v1 = Float.parseFloat(reworkOptional.getVal());
                                    objectBridges.get(obj).scale.setHeight_i((int) v1);
                                    objectBridges.get(obj).scale.setHeight_f(v1);
                                    objectBridges.get(obj).scale.setHeight_d((double) v1);
                                }
                            }
                        }
                    }
                }
                if (r1.equals(POS_X)){
                    String r2 = stream.read();
                    if (r2.equals(SET+TUText.setRectBracket(POS_X))){
                        TUtil.ReworkOptional<List<String>, String> reworkOptional = streamUtils.readMethod(SET+TUText.setRectBracket(POS_X));
                        List<String> elements  =  reworkOptional.get();
                        logger.info("setting "+r1+" for "+elements.toString());
                        for (String obj:
                                objects) {
                            for (String objCheck:
                                    elements) {
                                if (obj.equals(objCheck)){
                                    float v1 = Float.parseFloat(reworkOptional.getVal());
                                    objectBridges.get(obj).position.setX_i((int) v1);
                                    objectBridges.get(obj).position.setX_f(v1);
                                    objectBridges.get(obj).position.setX_d((double) v1);
                                }
                            }
                        }
                    }

                }
                if (r1.equals(POS_Y)){
                    String r2 = stream.read();
                    if (r2.equals(SET+TUText.setRectBracket(POS_Y))){
                        TUtil.ReworkOptional<List<String>, String> reworkOptional = streamUtils.readMethod(SET+TUText.setRectBracket(POS_Y));
                        List<String> elements  =  reworkOptional.get();
                        logger.info("setting "+r1+" for "+elements.toString());
                        for (String obj:
                                objects) {
                            for (String objCheck:
                                    elements) {
                                if (obj.equals(objCheck)){
                                    float v1 = Float.parseFloat(reworkOptional.getVal());
                                    objectBridges.get(obj).position.setY_i((int) v1);
                                    objectBridges.get(obj).position.setY_f(v1);
                                    objectBridges.get(obj).position.setY_d((double) v1);
                                }
                            }
                        }
                    }

                }
                if (r1.equals(IMAGE)){
                    String r2 = stream.read();
                    if (r2.equals(SET+TUText.setRectBracket(IMAGE))){
                        TUtil.ReworkOptional<List<String>,String> reworkOptional = streamUtils.readMethod(SET+TUText.setRectBracket(IMAGE));
                        List<String> elements = reworkOptional.get();
                        logger.info("setting "+r1+" for "+elements.toString());
                        for (String obj:
                                objects) {
                            for (String objCheck:
                                    elements) {
                                if (obj.equals(objCheck)){
                                    objectBridges.get(obj).sprite = imageInstances.get(reworkOptional.getVal());
                                }
                            }
                        }
                    }
                }
                if (r1.equals(POINTER)){
                    String r2 = stream.read();
                    if (r2.equals(SET+TUText.setRectBracket(POINTER))){
                        TUtil.ReworkOptional<List<String>,String> reworkOptional = streamUtils.readMethod(SET+ TUText.setRectBracket(POINTER));
                        List<String> elements = reworkOptional.get();
                        logger.info("setting "+r1+" for "+elements.toString());
                        for (String obj:
                             objects) {
                            for (String objChecks:
                                 elements) {
                                if (obj.equals(objChecks)){
                                    pointers.put(obj,reworkOptional.getVal());
                                }
                            }
                        }
                    }
                }
                for (String mappingName:
                     mappings.keySet()) {
                    if (r1.equals(mappingName)){
                        String r2 = stream.read();
                        String r3 = stream.read();
                        if (r2.equals(WIDTH)){
                            mappings.get(mappingName).setWidth(Integer.parseInt(r3));
                        }
                        if (r2.equals(HEIGHT)){
                            mappings.get(mappingName).setHeight(Integer.parseInt(r3));
                        }
                        if (r2.equals(SPACING+TUText.setRectBracket("x"))){
                            mappings.get(mappingName).setXspacing(Integer.parseInt(r3));
                        }
                        if (r2.equals(SPACING+TUText.setRectBracket("y"))){
                            mappings.get(mappingName).setYspacing(Integer.parseInt(r3));
                        }
                        if (r2.equals(SET+TUText.setRectBracket("mapString"))){
                            StringBuilder builder = new StringBuilder();
                            for (int i = 0; i < Integer.parseInt(r3)+1; i++) {
                                String r4 = stream.read();
                                builder.append(r4);
                            }
                            mappings.get(mappingName).setMappingString(builder.toString());
                        }
                    }
                }
                for (String bodyName:
                     bodies) {
                    if (r1.equals(bodyName)){
                        String r2 = stream.read();
                        String r3 = stream.read();
                        if (r2.equals("type")){
                            if (r3.equals("static")){
                                types.put(r1,BodyType.STATIC);
                            }
                            if (r3.equals("dynamic")){
                                types.put(r1,BodyType.DYNAMIC);
                            }
                            if (r3.equals("kinematic")){
                                types.put(r1,BodyType.KINEMATIC);
                            }
                        }
                    }
                }
                for (String methodName:
                     methods) {
                    if (r1.equals(methodName)){
                        String r2 = stream.read();
                        if (r2.equals(PARAMETERS)){
                            String r3 = stream.read();
                           List<String> params =  TUText.convertDotToList(r3.substring(1,r3.length()-1));
                           methodParams.put(methodName,params);
                        }
                        r2 =  r2.trim();
                        if (r2.startsWith("{")&&r2.endsWith("}")){
                            methodExec.put(methodName,r2);
                        }
                    }
                }
                for (String objectName:
                     objects) {
                    if (r1.equals(objectName)){
                        MapObject object =  objectBridges.get(objectName);
                        String r2 = stream.read();
                        if (r2.equals(WIDTH)){
                            String r3 = stream.read();
                            int width_i = Integer.parseInt(r3);
                            float width_f =  Float.parseFloat(r3);
                            double width_d = Double.parseDouble(r3);
                            object.scale.setWidth_i(width_i);
                            object.scale.setWidth_f(width_f);
                            object.scale.setWidth_d(width_d);
                        }
                        if (r2.equals(HEIGHT)){
                            String r3 = stream.read();
                            int height_i = Integer.parseInt(r3);
                            float height_f =  Float.parseFloat(r3);
                            double height_d = Double.parseDouble(r3);
                            object.scale.setHeight_i(height_i);
                            object.scale.setHeight_f(height_f);
                            object.scale.setHeight_d(height_d);
                        }
                        if (r2.equals(POS_X)){
                            String r3 = stream.read();
                            int x_i = Integer.parseInt(r3);
                            float x_f =  Float.parseFloat(r3);
                            double x_d = Double.parseDouble(r3);
                            object.position.setX_i(x_i);
                            object.position.setX_f(x_f);
                            object.position.setX_d(x_d);
                        }
                        if (r2.equals(POS_Y)){
                            String r3 = stream.read();
                            int y_i = Integer.parseInt(r3);
                            float y_f =  Float.parseFloat(r3);
                            double y_d = Double.parseDouble(r3);
                            object.position.setY_i(y_i);
                            object.position.setY_f(y_f);
                            object.position.setY_d(y_d);
                        }
                        if (r2.equals(POS_Z)){
                            String r3 = stream.read();
                            int z_i = Integer.parseInt(r3);
                            object.position.setZ(z_i);
                        }
                        if (r2.equals(IMAGE)){
                            String r3 = stream.read();
                            if (images.containsKey(r3)){
                                object.sprite = imageInstances.get(r3);
                            }
                        }
                        if (r2.equals(POINTER)){
                            String r3 = stream.read();
                            if (pointers.containsKey(r1)){
                                objectBridges.get(r1).pointer = r3;
                                pointers.put(r1,r3);
                            }
                        }
                        if (r2.equals(COLOR)){
                            String r3 = stream.read();
                            if (colors.containsKey(r1)){
                                objectBridges.get(r1).color = colors.get(r3);
                                colors.put(r1,colors.get(r3));
                            }
                        }
                        if (r2.equals("!add")){
                            this.canAdd.put(r1,false);
                        }
                        if (r2.equals(TYPE)){
                            String  r3 = stream.read();
                            if (r3.equals("static")){
                                types.put(r1,BodyType.STATIC);
                            }
                            if (r3.equals("dynamic")){
                                types.put(r1,BodyType.DYNAMIC);
                            }
                            if (r3.equals("kinematic")){
                                types.put(r1,BodyType.KINEMATIC);
                            }
                            objectBridges.get(r1).setType(types.get(r1));
                        }
                        objectBridges.replace(objectName,object);
                    }
                }
            }
        }
        stream.stop();
        logger.info("methods: "+methods.toString());
        logger.info("method params: "+methodParams.toString());
        logger.info("method exec: "+methodExec.toString());

        logger.info("objects: "+objects.toString());
        logger.info("objectBridges: "+objectBridges.toString());

        logger.info("images: "+images.toString());
        logger.info("pointers: "+pointers.toString());
        logger.info("colors: "+colors.toString());
        logger.info("mappings: "+mappings.toString());
        logger.info("image instance: "+imageInstances.toString());

        //compile
    }
    public TOSGameObject getObject(int i){
        return TEngine.ENGINE_SCENE$SYSTEM.getCurrentScene()._object(getObjectNameList().get(0).get(i));
    }
    public TOSGameObject getObject(int i0,int i1){
        return TEngine.ENGINE_SCENE$SYSTEM.getCurrentScene()._object(getObjectNameList().get(i0).get(i1));
    }
    private void setup(TScene scene){
        if (mappings.size()>0){
            for (Mapping mapping:
                 mappings.values()) {
                mapping.calc();
                for (MapObject object:
                     objectBridges.values()) {
                    mapping.addObj(object);
                }
                mapping.setup();
                mapping.render(this.position);
                objectNameList.add(mapping.tos_objs);
            }
        }
    }
    public void init(TScene scene){
        //render
        /*
        public method of setup method.
        parameters: TScene::scene
        public[void] :: init (TScene::scene)
         */
        setup(scene);
    }
    public static void main(String[] args) {
        TEngine.ENGINE_WINDOW.TWindow_background.setBlack();
        TEngine.ENGINE.start();
        TEngine.attachTPartToEnvironment(new ITPart() {
            @Override
            public void init() {
               T2DMap map = new T2DMap(TDataHelper.assets("maps\\flatmap.t2dm"));
               map.compile(TMath.createPosition_f(0,0));
               map.init(TEngine.ENGINE_SCENE$SYSTEM.getCurrentScene());
            }

            @Override
            public void loop(float dt) {

            }

            @Override
            public void dispose() {

            }
        },T2DMap.class);
    }
}
