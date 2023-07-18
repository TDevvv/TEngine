package com.thundergod.thunder_2d.util.components;

import com.thundergod.tobjectsystem.util.TOSComponent;

public class T2DPlayer extends TOSComponent {
    Facing facing = Facing.RIGHT;

    public Facing getFacing() {
        return facing;
    }

    public T2DPlayer(Facing facing){
        this.facing = facing;
    }
    public void setFacing(Facing facing) {
        this.facing = facing;
    }
    public enum Facing{
        LEFT,RIGHT
    }
}
