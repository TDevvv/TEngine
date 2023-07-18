package com.thundergod.tdata_helper.util;

public class TUObject<T extends Object>{
    T OBJ;

    public void setOBJ(T OBJ) {
        this.OBJ = OBJ;
    }

    public T getOBJ() {
        return OBJ;
    }

    public TUObject(T OBJ){
      setOBJ(OBJ);
    }
}
