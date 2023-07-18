package com.thundergod.tassetmanager.core;

import com.thundergod.ITPart;
import com.thundergod.tdata_helper.core.TDHReader;
import com.thundergod.tdata_helper.core.TDataHelper;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.thunder_2d.util.T2DShader;
import com.thundergod.thunder_2d.util.fabric.T2DSpriteSheet;
import com.thundergod.thunder_2d.util.fabric.T2DTexture;
import com.thundergod.tlogger.core.TLogger;
import com.thundergod.tmath.core.TMath;
import com.thundergod.tmath.util.TMScale;
import com.thundergod.tutil.core.TUText;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.thundergod.tdata_helper.core.TDHReader.*;
public class TAssetManager implements ITPart {
    static TLogger logger = new TLogger(TAssetManager.class);

    static {
        TEngine.attachLogger(logger);
    }

    final Map<String, T2DShader> shaders;
    final Map<String, T2DTexture> textures;
    final Map<String, T2DSpriteSheet> spriteSheets;

    public TAssetManager() {
        shaders = new HashMap<>();
        textures = new HashMap<>();
        spriteSheets = new HashMap<>();
    }

    public static EnumShader createEnumShader(String vertex, String fragment) {
        return new EnumShader(vertex, fragment);
    }
    public T2DShader _addShader(String key,T2DShader shader){
        if (shaders.containsValue(shader)){
            logger.warning("already added value to map.");
            return null;
        }
        this.shaders.put(key,shader);
        logger.info("loading shader -> "+"map key : "+ TUText.setCurlyBracket(key)+" shader :"+shader.getClass().getSimpleName());
        return shader;
    }
    public T2DTexture _addTexture(String key,T2DTexture t2DTexture){
        if (textures.containsValue(t2DTexture)){
            logger.warning("already added value to map.");
            return null;
        }
        this.textures.put(key,t2DTexture);
        logger.info("loading image -> "+"map key : "+ TUText.setCurlyBracket(key)+" texture :"+t2DTexture.getClass().getSimpleName());
        return t2DTexture;
    }
    public T2DSpriteSheet _addSpriteSheet(String key,T2DSpriteSheet spriteSheet){
        if (spriteSheets.containsValue(spriteSheet)){
            logger.warning("already added value to map.");
            return null;
        }
        this.spriteSheets.put(key,spriteSheet);
        logger.info("loading sprite_sheet -> "+"map key : "+TUText.setCurlyBracket(key));
        return spriteSheet;
    }
    public T2DShader _getShader(String key){
        T2DShader shader = this.shaders.get(key);
        logger.nullCheck(shader);
        return shader;
    }
    public T2DTexture _getTexture(String key){
        T2DTexture texture = this.textures.get(key);
        logger.nullCheck(texture);
        return texture;
    }
    public T2DSpriteSheet _getSpriteSheet(String key){
        T2DSpriteSheet spriteSheet = this.spriteSheets.get(key);
        logger.nullCheck(spriteSheet);
        return spriteSheet;
    }
    public T2DTexture _texture(EnumTexture path$key){
        File file = new File(path$key.path);
        if (file.exists()){
            T2DTexture t2DTexture = new T2DTexture(path$key.path,path$key.scale);
            _addTexture("{auto} newTexture["+id_tex+"]",t2DTexture);
            id_tex++;
            return t2DTexture;
        }else{
            return null;
        }
    }
    public T2DShader _shader(EnumShader path$key) {
        T2DShader shader =null;
        File vertex = new File(TDataHelper.shader(path$key.vertex_path));
        File fragment = new File(TDataHelper.shader(path$key.fragment_path));
        boolean vertex_valid = vertex.exists();
        boolean fragment_valid = fragment.exists();
        List<FileBool> boolList = new ArrayList<>();
        boolList.add(new FileBool(vertex_valid, vertex));
        boolList.add(new FileBool(fragment_valid, fragment));
        logger.info("files on -> "+boolList.toString());
        String vertex_code = "";
        String fragment_code = "";
        shader = new T2DShader();
        shader.createProgram();
        if (vertex_valid&&fragment_valid){
            vertex_code = readAll(TDHReader._shader(path$key.vertex_path),"\n");
            fragment_code = readAll(TDHReader._shader(path$key.fragment_path),"\n");
            shader.createFragmentShader(fragment_code);
            shader.createVertexShader(vertex_code);
            shader.link();
        }else{
            logger.error("vertex or fragment path is not valid./on54");
        }
        id_sha++;
        _addShader("shader;"+TUText.setCurlyBracket("frag: "+path$key.fragment_path)+" "+TUText.setCurlyBracket("vert: "+path$key.vertex_path),shader);
        return shader;
    }

    int id_sha;
    int id_tex;
    @Override
    public void init() {
        _addShader("default_engine_shader",TEngine.ENGINE_T2D_SHADER);
    }

    @Override
    public void loop(float dt) {

    }

    @Override
    public void dispose() {

    }

    public static class EnumShader{
        String vertex_path;
        String fragment_path;

        public void setFragment_path(String fragment_path) {
            this.fragment_path = fragment_path;
        }

        public void setVertex_path(String vertex_path) {
            this.vertex_path = vertex_path;
        }
        public EnumShader(String vertex_path,String fragment_path){
            setFragment_path(fragment_path);
            setVertex_path(vertex_path);
        }
    }
    public static class EnumTexture{
        TMScale scale;
        String path;
        public EnumTexture(TMScale scale,String path){
            this.scale =scale;
            this.path = path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public void setScale(TMScale scale) {
            this.scale = scale;
        }

        public TMScale getScale() {
            return scale;
        }

        public String getPath() {
            return path;
        }
    }
    record FileBool(boolean bool, File file) {
    }

}
