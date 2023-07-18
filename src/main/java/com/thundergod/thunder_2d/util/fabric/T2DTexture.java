package com.thundergod.thunder_2d.util.fabric;

import com.thundergod.ITPart;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.tlogger.core.TLogger;
import com.thundergod.tmath.util.TMScale;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

public class T2DTexture implements ITPart {
    static TLogger logger  = new TLogger(T2DTexture.class);
    public static TLogger getLogger() {
        return logger;
    }
    static{
    }
    String file_path;
    TMScale scale;
    private int texID;
    boolean itsAlreadyCreated;
    public T2DTexture(String file_path, TMScale scale){
    this.file_path = file_path;
    this.scale = scale;
    this.texID = -1;
    }
    public void create(){
        texID = glGenTextures();

        glBindTexture(GL_TEXTURE_2D,texID);

        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL_REPEAT);

        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_REPEAT);


        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);

        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);


        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        stbi_set_flip_vertically_on_load(true);

        ByteBuffer img = stbi_load(file_path,width,height,channels,0);

        if (img!=null){
            this.scale.setWidth_i(width.get(0));
            this.scale.setHeight_i(width.get(0));
            if (channels.get(0)==3){
                glTexImage2D(GL_TEXTURE_2D,0,GL_RGB,width.get(0),height.get(0)
                        ,0,GL_RGB,GL_UNSIGNED_BYTE,img);
                logger.info("loaded image successfully from --> "+file_path);
            }else if (channels.get(0)==4){
                glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,width.get(0),height.get(0)
                        ,0,GL_RGBA,GL_UNSIGNED_BYTE,img);
                logger.info("loaded image successfully from --> "+file_path);
            }else{

                logger.error("unknown number of channels --> "+channels.get(0));
            }

        }else{
            logger.error("cannot load image from -> "+file_path);
        }
        stbi_image_free(img);

    }
    public void bind(){
        if (!itsAlreadyCreated){
            create();
            itsAlreadyCreated = true;
        }
        glBindTexture(GL_TEXTURE_2D,texID);
    }
    public void unbind(){
        glBindTexture(GL_TEXTURE_2D,0);
    }
    @Override
    public void init() {
        TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem().addPart(logger);
        logger.attachTo(TEngine.ENGINE_T2D.getLogger());
        //logger.info("engine demo texture is loaded.");
    }

    public TMScale getScale() {
        return scale;
    }
    public int getScale_width(){
        return scale.getWidth_i();
    }
    public int getScale_height(){
        return scale.getHeight_i();
    }

    @Override
    public void loop(float dt) {

    }

    @Override
    public void dispose() {

    }
}
