package com.thundergod.tdata_helper.core;

import com.thundergod.ITPart;
import com.thundergod.tdata_helper.file_system.TUFileHandler;
import com.thundergod.tdata_helper.file_system.TUFileReader;
import com.thundergod.tdata_helper.file_system.annotations.dtype.DType;
import com.thundergod.tdata_helper.file_system.annotations.id.ID;
import com.thundergod.tdata_helper.file_system.annotations.reader_settings.IOSettings;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class TDataHelper implements ITPart {
    private static FragmentShader fragment_reader = new FragmentShader();
    private static VertexShader vertex_reader = new VertexShader();
    static boolean isUsed;
    private TDataHelper(){

    }
    public static TDataHelper createInstance(){
        if (!isUsed){
            isUsed = true;
            return new TDataHelper();
        }else{
            return null;
        }
    }
    public FragmentShader getFragment_reader() {
        return fragment_reader;
    }
    public VertexShader getVertex_reader() {
        return vertex_reader;
    }

    @Override
    public void init() {
        fragment_reader.setup(shader("fragment"));
        vertex_reader.setup(shader("vertex"));
    }

    @Override
    public void loop(float dt) {

    }

    @Override
    public void dispose() {

    }

    public static class FragmentShader{
        @ID(ID = -2)
        @IOSettings(MODE = TUFileReader.ReaderMode.DEFAULT,SYNTAX_MODE = TUFileReader.ReaderMode.DEFAULT)
        @DType(file_extension = ".frag",show_result = false)
        public static  TUFileReader READER = TUFileReader.create(TUFileHandler.createDataFile("", FragmentShader.class,"READER"), FragmentShader.class);

        public FragmentShader setup(String path){
            READER = TUFileReader.create(TUFileHandler.createDataFile(path, FragmentShader.class,"READER"),FragmentShader.class);
            READER.setup(true);
            return this;
        }
        public String read(){
            try {
                READER.setSCANNER(new Scanner(READER.getFILE()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            return READER.readWithNewLine();
        }
    }
    public static class VertexShader{
        @ID(ID = -1)
        @IOSettings(MODE = TUFileReader.ReaderMode.DEFAULT,SYNTAX_MODE = TUFileReader.ReaderMode.DEFAULT)
        @DType(file_extension = ".vert",show_result = false)
        public static  TUFileReader READER = TUFileReader.create(TUFileHandler.createDataFile("", VertexShader.class,"READER"), VertexShader.class);

        public VertexShader setup(String path){
            READER = TUFileReader.create(TUFileHandler.createDataFile(path, VertexShader.class,"READER"),VertexShader.class);
            READER.setup(true);
            return this;
        }
        public String read(){
            try {
                READER.setSCANNER(new Scanner(READER.getFILE()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            return READER.readWithNewLine();
        }
    }
    private static String getResourcesPath(){
        return "src\\main\\resources\\";
    }
    private static String getAssetsPath(){
        return getResourcesPath()+"assets\\";
    }
    private static String getShaderPath(){
        return getAssetsPath()+"shader\\";
    }
    private static String getTexturesPath(){return getAssetsPath()+"textures\\";}

    public static String assets(String path){
        return getAssetsPath()+path;
    }
    public static String shader(String path){
        return getShaderPath()+path;
    }
    public static String textures(String path){
        return getTexturesPath()+path;
    }
    public static String resources(String path){
        return getResourcesPath()+path;
    }
}
