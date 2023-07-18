package com.thundergod.tutil.core;

public class TULogic {
    boolean one_time=false;
    public void _oneTime(Runnable runnable){
        if (!one_time){
            runnable.run();
            one_time=true;
        }
    }
    public TULogic reset(){
        return new TULogic();
    }
}
