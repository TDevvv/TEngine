package com.thundergod.tengine.util;

import com.thundergod.ITPart;
import com.thundergod.tlogger.core.TLogger;

public class TEController implements ITPart {
    static TLogger logger = new TLogger(TEController.class);
    public TLogger getLogger() {
        return logger;
    }
    public TEController(){
    }
    State state;
    public void setState(State state) {
        this.state = state;
    }
    public State getState() {
        return state;
    }
    Boolean shouldRun;
    public void setShouldRun(Boolean shouldRun) {
        this.shouldRun = shouldRun;
    }
    public Boolean isShouldRun() {
        return shouldRun;
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

    public enum State{
        REGISTER,INIT,LOOP,DISPOSE,KILL,SHOW
    }
}
