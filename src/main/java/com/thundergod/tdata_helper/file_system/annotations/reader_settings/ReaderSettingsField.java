package com.thundergod.tdata_helper.file_system.annotations.reader_settings;

import com.thundergod.tdata_helper.file_system.TUFileReader;

public class ReaderSettingsField {
    TUFileReader.ReaderMode MOD;
    TUFileReader.ReaderMode SYNTAX_MOD;

    public void setSYNTAX_MOD(TUFileReader.ReaderMode SYNTAX_MOD) {
        this.SYNTAX_MOD = SYNTAX_MOD;
    }

    public TUFileReader.ReaderMode getSYNTAX_MOD() {
        return SYNTAX_MOD;
    }

    public void setMOD(TUFileReader.ReaderMode MOD) {
        this.MOD = MOD;
    }

    public TUFileReader.ReaderMode getMOD() {
        return MOD;
    }
    public static ReaderSettingsField create(TUFileReader.ReaderMode MOD, TUFileReader.ReaderMode SYNTAX_MOD){
        return new ReaderSettingsField(MOD,SYNTAX_MOD);
    }
    private ReaderSettingsField(TUFileReader.ReaderMode MOD, TUFileReader.ReaderMode SYNTAX_MOD){
        setMOD(MOD);
        setSYNTAX_MOD(SYNTAX_MOD);
    }
}
