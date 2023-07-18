package com.thundergod.thunder_2d.util;

import com.thundergod.thunder_2d.interfaces.IShaderCall;
import com.thundergod.tengine.core.TEngine;
import com.thundergod.tlogger.core.TLogger;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;
import com.thundergod.ITPart;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import org.joml.Matrix2f;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import java.util.List;


import static org.lwjgl.opengl.GL20.*;

public class T2DShader implements ITPart {
    static TLogger logger = new TLogger(T2DShader.class);
    int vertexID,fragmentID,shaderProgramID=0;
    private List<IShaderCall> shaderUseCalls;
    public void addShaderCall(IShaderCall shaderCall){
        this.shaderUseCalls.add(shaderCall);
    }
    public T2DShader(){
        this.shaderUseCalls = new ArrayList<>();
        //createProgram();
    }
    private boolean useShaderCallbacks = false;
    public void setUseShaderCallbacks(boolean v0){
        this.useShaderCallbacks = v0;
        this.disableShader = !v0;
    }
    public int createVertexShader(String code){
        vertexID = createShader(code,GL_VERTEX_SHADER);
        return vertexID;
    }
    public int createFragmentShader(String code){
        fragmentID = createShader(code,GL_FRAGMENT_SHADER);
        return fragmentID;
    }
    private int createShader(String code,int type){
        int shaderID;
        shaderID = glCreateShader(type);
        glShaderSource(shaderID,code);
        logger.info("binding shader source -> \n"+code.trim()+"\n to shader -> "+shaderID);
        glCompileShader(shaderID);
        logger.info("compiling shader -> "+shaderID);
        int succes = glGetShaderi(shaderID,GL_COMPILE_STATUS);
        if (succes==0){
            int length = glGetShaderi(shaderID,GL_INFO_LOG_LENGTH);
            logger.error("error in phase : compile id/1");
            logger.error("[1] log : "+glGetShaderInfoLog(shaderID,length));
        }
        glAttachShader(shaderProgramID,shaderID);
        logger.info("attached shader -> "+shaderID+" to shaderProgram -> "+shaderProgramID);
        return shaderID;
    }
    public void link(){
        glLinkProgram(shaderProgramID);
        logger.info("linking program.");
        int success = glGetProgrami(shaderProgramID,GL_LINK_STATUS);
        if (success==GL_FALSE){
            int length = glGetProgrami(shaderProgramID,GL_INFO_LOG_LENGTH);
            logger.error("error in phase : link id/2");
            System.out.println("[2] log : "+glGetProgramInfoLog(shaderProgramID,length));
        }
    }
    public void start(){
        glUseProgram(shaderProgramID);
    }
    public void stop(){
        glUseProgram(0);
    }

    @Override
    public void init() {
        addShaderCall(new IShaderCall() {
            @Override
            public void call(T2DShader shader) {
                _mat4f("uProjection",TEngine.ENGINE_CAMERA.getProjectionMatrix());
                _mat4f("uView",TEngine.ENGINE_CAMERA.getViewMatrix());
            }
        });
        if (!disableShader){
            logger.setState(TEngine.ENGINE_CONTROLLER.getState());
            logger.attachTo(TEngine.ENGINE_T2D.getLogger());
            TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem().addPart(T2DShader.logger);
            createProgram();
            this.createFragmentShader(TEngine.ENGINE_DATA$HELPER.getFragment_reader().read());
            logger.info("created fragment shader.");
            this.createVertexShader(TEngine.ENGINE_DATA$HELPER.getVertex_reader().read());
            logger.info("created vertex shader.");
            this.link();
            logger.info("linking shader.");
        }else{
            if (!useShaderCallbacks){
                logger.setState(TEngine.ENGINE_CONTROLLER.getState());
                logger.attachTo(TEngine.ENGINE_T2D.getLogger());
                TEngine.ENGINE_LOGGER$SYSTEM.getLoggerSystem().addPart(T2DShader.logger);
                createProgram();
                this.createFragmentShader(TEngine.ENGINE_DATA$HELPER.getFragment_reader().read());
                logger.info("created fragment shader.");
                this.createVertexShader(TEngine.ENGINE_DATA$HELPER.getVertex_reader().read());
                logger.info("created vertex shader.");
                this.link();
                logger.info("linking shader.");
            }
        }

    }

    public void createProgram(){
        if (shaderProgramID==0){
            shaderProgramID=glCreateProgram();
            logger.info("created shader program --> id: "+shaderProgramID);
        }else{
            logger.warning("shader program is already created.");
        }
    }
    private boolean u1 = true;
    private boolean u2 = true;
    @Override
    public void loop(float dt) {
        logger.setState(TEngine.ENGINE_CONTROLLER.getState());
        if(useShaderCallbacks){
            if (u2){
                logger.warning("shader callbacks enabled.");
                u2 = false;
            }
            TEngine.ENGINE_T2D_SHADER.start();

            for (IShaderCall shaderCall:
                    this.shaderUseCalls) {
                shaderCall.call(this);
            }

            TEngine.ENGINE_T2D_SHADER.stop();
        }else{
            if (!disableShader){
            }
            if (u1){
                if (disableShader){
                    return;
                }
                logger.warning("shader callbacks disabled.");
                u1 = false;
            }
        }
    }
    @Override
    public void dispose() {logger.setState(TEngine.ENGINE_CONTROLLER.getState());}
    public void _mat4f(String v0, Matrix4f v1){
        start();
        T2DShaderRecord shaderRecord = _default(v0);
        shaderRecord.buffer = MemoryUtil.memAllocFloat(16);
        v1.get(shaderRecord.buffer);
        glUniformMatrix4fv(shaderRecord.location,false,shaderRecord.buffer);
    }
    public void _mat3f(String v0, Matrix3f v1){
        T2DShaderRecord shaderRecord = _default(v0);
        shaderRecord.buffer = MemoryUtil.memAllocFloat(12);
        v1.get(shaderRecord.buffer);
        glUniformMatrix3fv(shaderRecord.location,false,shaderRecord.buffer);
    }
    public void _mat2f(String v0, Matrix2f v1){
        T2DShaderRecord shaderRecord = _default(v0);
        shaderRecord.buffer = MemoryUtil.memAllocFloat(8);
        v1.get(shaderRecord.buffer);
        glUniformMatrix2fv(shaderRecord.location,false,shaderRecord.buffer);
    }
    public void _vec4(String v0,float[] v1){
        T2DShaderRecord shaderRecord = _default(v0);
        FloatBuffer buffer = MemoryUtil.memAllocFloat(v1.length);
        buffer.put(v1).flip();
        glUniform4fv(shaderRecord.location,buffer);
    }
    public void _4f(String v0,float v1,float v2,float v3,float v4){
        T2DShaderRecord shaderRecord = _default(v0);
        glUniform4f(shaderRecord.location,v1,v2,v3,v4);
    }
    public void _3f(String v0,float v1,float v2,float v3){
        T2DShaderRecord shaderRecord = _default(v0);
        glUniform3f(shaderRecord.location,v1,v2,v3);
    }
    public void _2f(String v0,float v1,float v2){
        T2DShaderRecord shaderRecord = _default(v0);
        glUniform2f(shaderRecord.location,v1,v2);
    }
    public void _1f(String v0,float v1){
        T2DShaderRecord shaderRecord = _default(v0);
        glUniform1f(shaderRecord.location,v1);
    }
    public void _4i(String v0,int v1,int v2,int v3,int v4){
        T2DShaderRecord shaderRecord = _default(v0);
        glUniform4i(shaderRecord.location,v1,v2,v3,v4);
    }
    public void _3i(String v0,int v1,int v2,int v3){
        T2DShaderRecord shaderRecord = _default(v0);
        glUniform3i(shaderRecord.location,v1,v2,v3);
    }
    public void _2i(String v0,int v1,int v2){
        T2DShaderRecord shaderRecord = _default(v0);
        glUniform2i(shaderRecord.location,v1,v2);
    }
    public void _1i(String v0,int v1){
        T2DShaderRecord shaderRecord = _default(v0);
        glUniform1i(shaderRecord.location,v1);

    }
    public void _tex(String v0,int s0){
        T2DShaderRecord shaderRecord = _default(v0);
        glUniform1i(shaderRecord.location,s0);

    }
    public void _tarray(String v0,int[] ms0){
        T2DShaderRecord shaderRecord = _default(v0);
       glUniform1iv(shaderRecord.location,ms0);
    }
    public T2DShaderRecord _default(String v0){
        T2DShaderRecord record = new T2DShaderRecord(0,null);
        int location = glGetUniformLocation(shaderProgramID,v0);
        //logger.info("uniform location -> "+location);
        record.location = location;
        return record;
    }
    public static class T2DShaderRecord{
        FloatBuffer buffer;
        int location;
        public T2DShaderRecord(int location,FloatBuffer buffer){
         this.buffer = buffer;
         this.location = location;
        }
    }
    private boolean disableShader = false;
    public void disableShaderFromAllContext(){
        useShaderCallbacks = false;
        disableShader = true;
    }
    public static void call(T2DShader shader,IShaderCall shaderCall){
        shader.start();
        shaderCall.call(shader);
        shader.stop();
    }
    public static T2DShader _shader(String vertex_code,String fragment_code){
        T2DShader rtr;
        rtr = new T2DShader();
        if (rtr.shaderProgramID==0){
            rtr.shaderProgramID = glCreateProgram();
        }
        rtr.vertexID = rtr.createVertexShader(vertex_code);
       rtr.fragmentID =  rtr.createFragmentShader(fragment_code);
       return rtr;
    }
    public static T2DShader _linked_shader(String vertex_code,String fragment_code){
        T2DShader shader = _shader(vertex_code,fragment_code);
        shader.link();
        return shader;
    }


}
