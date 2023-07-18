package com.thundergod.tdata_helper.file_system.abstracts;

import com.thundergod.tdata_helper.file_system.TUFileReader;

public abstract class Pattern {
    public void run(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                pattern();
            }
        });
        thread.run();
    }
    public void pattern(){

    }
    public TUFileReader instance(TUFileReader reader){
        return reader;
    }
}
