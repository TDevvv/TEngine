package com.thundergod.tlogger.core;

import java.io.PrintStream;

public class TLEngineLogger extends TLogger{
    private static PrintStream out = new PrintStream(System.out);
    public static PrintStream getOut() {
        return out;
    }

    public TLEngineLogger(Class<?> _class) {
        super(_class);
    }

    public static PrintStream out() {
        return out;
    }
    private static PrintStream oldOut;
    public static PrintStream old_out() {
        return oldOut;
    }

    @Override
    public void init() {
    oldOut = System.out;
    System.setOut(out);
    }
    @Override
    public void dispose() {
        System.setOut(oldOut);
        System.out.flush();
    }
}
