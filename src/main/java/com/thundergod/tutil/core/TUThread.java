package com.thundergod.tutil.core;

public class TUThread {
    public static void sleep(Runnable event,long millis){
        try {
            Thread thread = new Thread(event);
            thread.wait(millis);
            thread.start();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
