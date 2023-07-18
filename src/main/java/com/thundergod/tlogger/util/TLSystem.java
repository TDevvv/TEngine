package com.thundergod.tlogger.util;

import com.thundergod.ITPart;
import com.thundergod.tcomponent_system.core.TComponentSystem;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.tlogger.core.TLEngineLogger;

public class TLSystem implements ITPart{
    public static TLSystem createInstance(){
        return new TLSystem();
    }
    private static final TComponentSystem<TLEngineLogger, ITPart> LOGGER_SYSTEM = new TComponentSystem<>(new TLEngineLogger(TEngine.class));
    public static final TLEngineLogger LOGGER = LOGGER_SYSTEM.getParent();
    @Override
    public void init() {
        LOGGER_SYSTEM.addPart(LOGGER);
        //LOGGER_SYSTEM.init();
    }

    @Override
    public void loop(float dt) {
        //LOGGER_SYSTEM.loop(dt);
    }

    @Override
    public void dispose() {
        LOGGER_SYSTEM.dispose();
    }
    public TComponentSystem<TLEngineLogger, ITPart> getLoggerSystem() {
        return LOGGER_SYSTEM;
    }
}
