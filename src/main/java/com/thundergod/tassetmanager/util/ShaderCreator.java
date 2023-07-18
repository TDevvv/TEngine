package com.thundergod.tassetmanager.util;

public class ShaderCreator {
    public static void _default(){

    }
    public static void _vertex(TAMEditableShader shader){
        String path = shader.path;
        Version version = shader.version;

    }
    public static void _fragment(){

    }
    enum Version{
        CORE_330,CORE_440;
    }
}
