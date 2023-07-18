package com.thundergod.tdata_helper.file_system.annotations.run_with_warning;

public class RunWithWarningField {
    protected String WARNING_MESSAGE;
    protected int warning_code;
    protected boolean show_detail;

    public void setShow_detail(boolean show_detail) {
        this.show_detail = show_detail;
    }

    public void setWarning_code(int warning_code) {
        this.warning_code = warning_code;
    }

    public void setWARNING_MESSAGE(String WARNING_MESSAGE) {
        this.WARNING_MESSAGE = WARNING_MESSAGE;
    }

    public int getWarning_code() {
        return warning_code;
    }

    public String getWARNING_MESSAGE() {
        return WARNING_MESSAGE;
    }

    public boolean isShow_detail() {
        return show_detail;
    }

    public static RunWithWarningField create(String WARNING_MESSAGE, int warning_code, boolean show_detail){
        return new RunWithWarningField(WARNING_MESSAGE,warning_code,show_detail);
    }

    private RunWithWarningField(String WARNING_MESSAGE, int warning_code, boolean show_detail) {
        this.WARNING_MESSAGE = WARNING_MESSAGE;
        this.warning_code = warning_code;
        this.show_detail = show_detail;
    }
}
