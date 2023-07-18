package com.thundergod.tdata_helper.file_system.annotations.id;

public class IDField {
    public enum ELEMENT_TYPE{
        CONSTRUCTOR,
        METHOD,
        FIELD,
        LOCAL_VAR
    }
    public ELEMENT_TYPE TYPE;
    public int ID;
    public String SPECIAL_NAME;


    public void setTYPE(ELEMENT_TYPE TYPE) {
        this.TYPE = TYPE;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setSPECIAL_NAME(String SPECIAL_NAME) {
        this.SPECIAL_NAME = SPECIAL_NAME;
    }

    public ELEMENT_TYPE getTYPE() {
        return TYPE;
    }

    public int getID() {
        return ID;
    }

    public String getSPECIAL_NAME() {
        return SPECIAL_NAME;
    }
    private IDField(int ID,ELEMENT_TYPE TYPE,String SPECIAL_NAME){
        setID(ID);
        setTYPE(TYPE);
        if (SPECIAL_NAME == null) {
            SPECIAL_NAME = "";
        }
        setSPECIAL_NAME(SPECIAL_NAME);
    }
    public static IDField create(int ID,ELEMENT_TYPE TYPE,String SPECIAL_NAME){
        return new IDField(ID,TYPE,SPECIAL_NAME);
    }

    @Override
    public String toString() {
        if(SPECIAL_NAME == null){
            SPECIAL_NAME = "";
        }
        return "IDField{" +
                "TYPE=" + TYPE +
                ", ID=" + ID +
                ", SPECIAL_NAME='" + SPECIAL_NAME + '\'' +
                '}';
    }
}
