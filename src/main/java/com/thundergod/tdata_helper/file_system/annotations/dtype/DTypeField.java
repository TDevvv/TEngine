package com.thundergod.tdata_helper.file_system.annotations.dtype;

public class DTypeField {
    protected String file_extension;
    protected boolean show_result;

    public void setShow_result(boolean show_result) {
        this.show_result = show_result;
    }

    public boolean isShow_result() {
        return show_result;
    }

    public String getFile_extension() {
        return file_extension;
    }

    public void setFile_extension(String file_extension) {
        this.file_extension = file_extension;
    }

    public static DTypeField create(String file_extension,boolean show_result){
        return new DTypeField(file_extension,show_result);
    }
    private DTypeField(String file_extension,boolean show_result){
        setFile_extension(file_extension);
        setShow_result(show_result);
    }
}
