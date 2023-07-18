package com.thundergod.tassetmanager.util;

public class TAMEditableShader {
    String path;
    ShaderCreator.Version version;
    public void _default(String path, ShaderCreator.Version version){
        this.path = path;
        this.version = version;
    }
}
