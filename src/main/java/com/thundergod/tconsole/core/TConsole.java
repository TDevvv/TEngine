package com.thundergod.tconsole.core;

import com.thundergod.ITPart;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.tengine.util.TEInfo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TConsole extends JFrame implements ITPart {
    JPanel panel = new JPanel(){
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for (ITCGraphicCall call:
                 calls) {
                call.call(g);
            }
        }
    };
    public TConsole(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(150,150);
        this.add(panel);
        calls = new ArrayList<>();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (ITCGraphicCall call:
             calls) {
            call.call(g);
            call.call(panel.getGraphics());
        }
    }
    List<ITCGraphicCall> calls;
    public void addCall(ITCGraphicCall call){
        this.calls.add(call);
    }

    @Override
    public void init() {
        addCall(new ITCGraphicCall() {
            @Override
            public void call(Graphics g) {
                g.drawString("asdlşkaslşdkalşkdlşakslşdkalşskdşkasşdkşalskdlşaksşdfps: "+ TEngine.ENGINE_TIME.fps(),0,0);
            }
        });
    }

    @Override
    public void loop(float dt) {

    }

    interface ITCGraphicCall{
        void call(Graphics g);
    }
}
