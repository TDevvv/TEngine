package com.thundergod.tengine.core;

import com.thundergod.tcomponent_system.core.TComponentSystem;
import com.thundergod.thunder_2d.util.components.T2DCameraLock;
import com.thundergod.tmain.interfaces.ITMHasFirst;
import com.thundergod.twindow.tscene_system.core.TSceneSystem;
import com.thundergod.thunder_2d.util.projection.T2DCamera;
import com.thundergod.twindow.tscene_system.util.TScene;
import com.thundergod.tcomponent_system.util.TComponent;
import com.thundergod.thunder_2d.util.fabric.T2DTexture;
import com.thundergod.tassetmanager.core.TAssetManager;
import com.thundergod.tdata_helper.core.TDataHelper;
import com.thundergod.tcallbacks.core.TCWindowSize;
import com.thundergod.tcallbacks.core.TCWindowPos;
import com.thundergod.tengine.interfaces.ITEngine;
import com.thundergod.tlogger.core.TLEngineLogger;
import com.thundergod.thunder_2d.core.Thunder2D;
import com.thundergod.tengine.util.TEController;
import com.thundergod.thunder_2d.util.T2DShader;
import com.thundergod.tcallbacks.core.TCMouse;
import com.thundergod.tlogger.util.TLSystem;
import com.thundergod.tcallbacks.core.TCKey;
import com.thundergod.twindow.util.TWConfig;
import com.thundergod.tlogger.core.TLogger;
import com.thundergod.twindow.core.TWindow;
import com.thundergod.tengine.util.TEInfo;
import com.thundergod.tutil.core.TUTime;
import com.thundergod.tmath.core.TMath;
import com.thundergod.ITPart;
import org.lwjgl.stb.STBTruetype;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class TEngine extends TComponent implements ITEngine {
    public static final TEController ENGINE_CONTROLLER = new TEController();
    private static final TComponentSystem<TEngine, ITPart> ENGINE_COMPONENT$SYSTEM = new TComponentSystem<>(new TEngine());
    private static final TComponentSystem<TEngine,ITPart> ENGINE_PUBLIC_COMPONENT$SYSTEM = new TComponentSystem<>(TEngine.ENGINE);
    public static final TSceneSystem ENGINE_SCENE$SYSTEM = new TSceneSystem(TScene.createNullScene());
    public static final TScene ENGINE_SCENE = new TScene(TEngine.ENGINE_CAMERA) {
        @Override
        public void loop(float dt) {

        }

        @Override
        public void update(float dt) {

        }
    };
    public static final TEngine ENGINE= ENGINE_COMPONENT$SYSTEM.getParent();
    public static final TWindow ENGINE_WINDOW = new TWindow(TWConfig.createNullConfig());
    public static final TCKey ENGINE_KEY_CALLBACK = new TCKey();
    public static final TCMouse ENGINE_MOUSE_CALLBACK = TCMouse.createInstance();
    public static final TCWindowSize ENGINE_WINDOW$SIZE_CALLBACK = TCWindowSize.createInstance();
    public static final TCWindowPos ENGINE_WINDOW$POS_CALLBACK = TCWindowPos.createInstance();
    public static final TComponentSystem<Thunder2D,ITPart> ENGINE_T2D_COMPONENT$SYSTEM = Thunder2D.T2D_COMPONENT_SYSTEM;
    public static final Thunder2D ENGINE_T2D = ENGINE_T2D_COMPONENT$SYSTEM.getParent();
    public static final T2DShader ENGINE_T2D_SHADER = Thunder2D.T2D_SHADER;
    public static final TDataHelper ENGINE_DATA$HELPER = TDataHelper.createInstance();
    public static final TLSystem ENGINE_LOGGER$SYSTEM = TLSystem.createInstance();
    public static final TLEngineLogger ENGINE_LOGGER = ENGINE_LOGGER$SYSTEM.getLoggerSystem().getParent();
    public static final TMath<Integer> ENGINE_MATH = new TMath<>();
    public static final TUTime ENGINE_TIME = new TUTime();
    public static final TEInfo ENGINE_INFO = new TEInfo(null,null);
    public static final T2DCamera ENGINE_CAMERA = Thunder2D.T2D_CAMERA;
    public static final T2DTexture ENGINE_DEMO_TEXTURE = Thunder2D.T2D_DEMO_TEXTURE;
    private static final TComponentSystem<TAssetManager,ITPart> ENGINE_ASSET_COMPONENT$SYSTEM = new TComponentSystem<>(new TAssetManager());
    public static final TAssetManager ENGINE_ASSET_MANAGER = new TAssetManager();
    TUTime loop_timer,init_timer,dispose_timer;
    static{
        //logger nonThink init.
        TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem().addPart(T2DTexture.getLogger());
    }
    public void register(){

        //bu bölümde bütün componentlerin girişi sağlanıyor.

        ENGINE_CONTROLLER.setState(TEController.State.REGISTER);
        ENGINE_COMPONENT$SYSTEM.addPart(ENGINE_WINDOW);
        ENGINE_COMPONENT$SYSTEM.addPart(ENGINE_SCENE$SYSTEM);
        ENGINE_COMPONENT$SYSTEM.addPart(ENGINE_KEY_CALLBACK);
        ENGINE_COMPONENT$SYSTEM.addPart(ENGINE_MOUSE_CALLBACK);
        ENGINE_COMPONENT$SYSTEM.addPart(ENGINE_WINDOW$SIZE_CALLBACK);
        ENGINE_COMPONENT$SYSTEM.addPart(ENGINE_WINDOW$POS_CALLBACK);
        ENGINE_COMPONENT$SYSTEM.addPart(ENGINE_DATA$HELPER);
        ENGINE_COMPONENT$SYSTEM.addPart(ENGINE_LOGGER);
        ENGINE_COMPONENT$SYSTEM.addPart(ENGINE_MATH);
        ENGINE_COMPONENT$SYSTEM.addPart(ENGINE_ASSET_MANAGER);
        ENGINE_COMPONENT$SYSTEM.addPart(ENGINE_TIME);
        ENGINE_COMPONENT$SYSTEM.addPart(ENGINE_INFO);
        ENGINE_COMPONENT$SYSTEM.addPart(ENGINE_CONTROLLER);
        ENGINE_COMPONENT$SYSTEM.addPart(ENGINE_SCENE);


        ENGINE_ASSET_COMPONENT$SYSTEM.addPart(TEngine.ENGINE_ASSET_MANAGER);
        //ENGINE_COMPONENT$SYSTEM.addPart(ENGINE_CAMERA);
        ENGINE_T2D.register();
        ENGINE_LOGGER$SYSTEM.getLoggerSystem().addPart(ENGINE_LOGGER);
    }
    @Override
    public void init() {
        ENGINE_LOGGER.setState(TEController.State.SHOW);
        ENGINE_LOGGER.info("TEngine is started.");
        ENGINE_CONTROLLER.setState(TEController.State.INIT);
        TEngine.ENGINE_LOGGER.setState(ENGINE_CONTROLLER.getState());
        init_timer = TUTime.create();
        init_timer.t_start();
        ENGINE_LOGGER.info("starting init....");
        int countAll = 0;
        int count = 0;
        for (ITPart part:
                ENGINE_COMPONENT$SYSTEM.componentList) {
            countAll++;
            ENGINE_LOGGER.lifecycle("ENGINE_COMPONENT_SYSTEM_INIT @Parent("+ENGINE_COMPONENT$SYSTEM.getParent().getClass().getSimpleName()+") {"+count+"} "+part.getClass().getSimpleName());
            count++;
        }
        count = 0;
        for (ITPart part:
                ENGINE_LOGGER$SYSTEM.getLoggerSystem().componentList) {
            countAll++;
            ENGINE_LOGGER.lifecycle("ENGINE_LOGGER_SYSTEM_INIT @Parent("+ENGINE_LOGGER$SYSTEM.getLoggerSystem().getParent().getClass().getSimpleName()+")"+  "{"+count+"} "+part.getClass().getSimpleName());
            count++;
        }
        count = 0;
        for (ITPart part:
                ENGINE_T2D_COMPONENT$SYSTEM.componentList) {
            countAll++;
            ENGINE_LOGGER.lifecycle("ENGINE_T2D_COMPONENT_SYSTEM @Parent("+ENGINE_T2D_COMPONENT$SYSTEM.getParent().getClass().getSimpleName()+") {"+count+"} "+part.getClass().getSimpleName());
            count++;
        }
        count = 0;
        for (ITPart part:
             ENGINE_PUBLIC_COMPONENT$SYSTEM.componentList) {
            countAll++;
            if (part instanceof TComponentSystem<?,?>){
                ENGINE_LOGGER.lifecycle("PUBLIC_ENGINE_COMPONENT_SYSTEM @Parent("+((TComponentSystem<?, ?>) part).getParent().getClass().getSimpleName()+") {"+count+"} "+part.getClass().getSimpleName());
            }else{
                ENGINE_LOGGER.lifecycle("PUBLIC_ENGINE_COMPONENT_SYSTEM @Parent("+"self"+") {"+count+"} "+part.getClass().getSimpleName());
            }
            count++;
        }
        for (int i = 0; i < countAll; i++) {
            ENGINE_INFO.add();
        }
        ENGINE_INFO.refresh();
        ENGINE_COMPONENT$SYSTEM.init();
        ENGINE_LOGGER$SYSTEM.init();
        ENGINE_T2D_COMPONENT$SYSTEM.init();
        ENGINE_PUBLIC_COMPONENT$SYSTEM.init();
        ENGINE_ASSET_COMPONENT$SYSTEM.init();
        init_timer.t_end();
        init_timer.parse();
        ENGINE_LOGGER.info("init finished elapsed time -> "+init_timer.delta_time()+" ms");
    }
    @Override
    public void loop() {
        ENGINE_LOGGER.setState(TEController.State.SHOW);
        ENGINE_LOGGER.info(ENGINE_INFO.toString());
        ENGINE_CONTROLLER.setState(TEController.State.LOOP);
        TEngine.ENGINE_LOGGER.setState(ENGINE_CONTROLLER.getState());
        loop_timer =  TUTime.create();
        loop_timer.t_start();
        TEngine.ENGINE_LOGGER.info("starting loop....");
        ENGINE_TIME.t_start();
        while (ENGINE_CONTROLLER.isShouldRun()){
            ENGINE_COMPONENT$SYSTEM.loop((float)ENGINE_TIME.delta_time());
            ENGINE_PUBLIC_COMPONENT$SYSTEM.loop((float) ENGINE_TIME.delta_time());
            ENGINE_T2D.loop((float)ENGINE_TIME.delta_time());
            TEngine.ENGINE_WINDOW.l_util();
            ENGINE_TIME.t_end();
            ENGINE_TIME.parse();
            ENGINE_TIME.parseFps();
            ENGINE_TIME.reset();
            if (!ENGINE_CONTROLLER.isShouldRun()){
                glfwSetWindowShouldClose(ENGINE_WINDOW.TWindow_code.code,!ENGINE_CONTROLLER.isShouldRun());
            }
            ENGINE_CONTROLLER.setShouldRun(!glfwWindowShouldClose(ENGINE_WINDOW.TWindow_code.code));
        }
        loop_timer.t_end();
        loop_timer.parse();
        TEngine.ENGINE_LOGGER.info("finishing loop elapsed time -> "+loop_timer.delta_time()+" ms");
    }
    @Override
    public void onFinish() {
    }
    @Override
    public void dispose() {
        ENGINE_CONTROLLER.setState(TEController.State.DISPOSE);
        TEngine.ENGINE_LOGGER.setState(ENGINE_CONTROLLER.getState());
        ENGINE_COMPONENT$SYSTEM.dispose();
        ENGINE_PUBLIC_COMPONENT$SYSTEM.dispose();
        ENGINE_T2D.dispose();
        ENGINE_LOGGER$SYSTEM.dispose();
        ENGINE_LOGGER.warning("TEngine disposing everything.");
    }
    private void kill(){
        TEngine.ENGINE_CONTROLLER.setState(TEController.State.KILL);
        TEngine.ENGINE_LOGGER.setState(TEngine.ENGINE_CONTROLLER.getState());
        ENGINE_LOGGER.info("TEngine going to exit. elapsed time ->  init : "+init_timer.delta_time()+"ms loop : "+loop_timer.delta_time()+"ms");
        ENGINE_LOGGER.info("Goodbye!");
        System.exit(0);
    }
    public void first(){
        for (ITPart part:
             ENGINE_PUBLIC_COMPONENT$SYSTEM.getComponentList()) {
            if (part instanceof ITMHasFirst){
                ((ITMHasFirst) part).first();
            }
        }
    }
    private void main(){
        //System.out.println("OnRun:: "+isRunning());
        ENGINE_CONTROLLER.setShouldRun(true);
        first();
        register();
        init();
        try {
            loop();
            onFinish();
        }catch (Exception e){
            TEngine.ENGINE_LOGGER.fatal(e.getMessage());
            dispose();
            kill();
            throw e;
        }
        finally
        {
            dispose();
            kill();
        }
    }
    //public boolean isRunning(){
       // return TEngine.ENGINE.isRunning();
    //}
    public void start(){
        main();
    }
    static final List<Class<?>> nonEngineClasses = new ArrayList<>();
    public static void attachTPartToEnvironment(ITPart part,Class<?> _class){
        ENGINE_PUBLIC_COMPONENT$SYSTEM.addPart(part);
        nonEngineClasses.add(_class);
    }
    public static void attachLogger(TLogger logger){
        TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem().addPart(logger);
    }
}
