package com.thundergod.thunder_2d.util.render;

import com.thundergod.tengine.core.TEngine;
import com.thundergod.thunder_2d.util.T2DShader;
import com.thundergod.thunder_2d.util.components.T2DSpriteRenderer;
import com.thundergod.thunder_2d.util.fabric.T2DTexture;
import com.thundergod.tlogger.core.TLogger;
import com.thundergod.tmath.util.TMTransform;
import org.jbox2d.common.Vec2;
import org.joml.*;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class T2DRenderBatch implements Comparable<T2DRenderBatch> {
    public static TLogger logger = new TLogger(T2DRenderBatch.class);
    static{
        TEngine.attachLogger(logger);
        logger.attachTo(TEngine.ENGINE_T2D.getLogger());
    }

    private final int POS_SIZE = 2;
    private final int COLOR_SIZE = 4;
    private final int TCOORDS_SIZE = 2;
    private final int TID_SIZE = 1;


    private final int POS_OFFSET = 0;
    private final int COLOR_OFFSET = POS_OFFSET + POS_SIZE * Float.BYTES;
    private final int TCOORDS_OFFSET = COLOR_OFFSET+COLOR_SIZE*Float.BYTES;
    private final int TID_OFFSET = TCOORDS_OFFSET+TCOORDS_SIZE*Float.BYTES;
    private final int VERTEX_SIZE = 9;
    private final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;

    private T2DSpriteRenderer[] sprites;
    private int numSprites;
    private boolean hasRoom;
    private float[] vertices;
    private int[] texS = {0,1,2,3,4,5,6,7};

    private List<T2DTexture> textureList;
    private int vaoID, vboID;
    private int maxBatchSize;
    private T2DShader shader;
    private int z_i;
    private List<TMTransform> vertices_ac = new ArrayList<>();
    public T2DRenderBatch(int maxBatchSize,int z_i) {
        shader = TEngine.ENGINE_T2D_SHADER;
        this.sprites = new T2DSpriteRenderer[maxBatchSize];
        this.maxBatchSize = maxBatchSize;
        this.z_i = z_i;

        // 4 vertices quads
        vertices = new float[maxBatchSize * 4 * VERTEX_SIZE];


        this.numSprites = 0;
        this.hasRoom = true;
        this.textureList = new ArrayList<>();
    }

    public void start() {
        //1.0f,-1.9

        // Generate and bind a Vertex Array Object
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Allocate space for vertices
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

        // Create and upload indices buffer
        int eboID = glGenBuffers();
        int[] indices = generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        // Enable the buffer attribute pointers
        glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POS_OFFSET);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2,TCOORDS_SIZE,GL_FLOAT,false,VERTEX_SIZE_BYTES,TCOORDS_OFFSET);
        glEnableVertexAttribArray(2);

        glVertexAttribPointer(3,TID_SIZE,GL_FLOAT,false,VERTEX_SIZE_BYTES,TID_OFFSET);
        glEnableVertexAttribArray(3);

    }

    public void addSprite(T2DSpriteRenderer spr) {
        // Get index and add renderObject
        int index = this.numSprites;
        this.sprites[index] = spr;
        this.numSprites++;


        if (spr.getTexture()!=null){
            if (!textureList.contains(spr.getTexture())){
                textureList.add(spr.getTexture());
            }
        }
        // Add properties to local vertices array
        loadVertexProperties(index);
        if (spr.isPrimitive()){
            logger.info("it is primitive.");
            vertices = spr.getVertices();
        }
        if (numSprites >= this.maxBatchSize) {
            this.hasRoom = false;
        }
    }
    public void render() {
        boolean rbData = false;
        for (int i = 0; i <numSprites; i++) {
            T2DSpriteRenderer spriteRenderer = sprites[i];
            if (spriteRenderer._flag()){
                loadVertexProperties(i);
                spriteRenderer._resetFlag();
                rbData = true;
            }
        }
        if (rbData){
            glBindBuffer(GL_ARRAY_BUFFER,vboID);
            glBufferSubData(GL_ARRAY_BUFFER,0,vertices);
        }
        // For now, we will rebuffer all data every frame
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

        // Use shader
        shader.start();
        shader._mat4f("uProjection", TEngine.ENGINE_SCENE$SYSTEM.getCurrentScene().getCamera().getProjectionMatrix());
        shader._mat4f("uView", TEngine.ENGINE_SCENE$SYSTEM.getCurrentScene().getCamera().getViewMatrix());
        for (int j = 0; j < textureList.size(); j++) {
            glActiveTexture(GL_TEXTURE0+j+1);
            textureList.get(j).bind();
        }
        shader._tarray("uTex_a",texS);


        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, this.numSprites * 6, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        for (T2DTexture t2DTexture : textureList) {
            t2DTexture.unbind();
        }
        shader.stop();
    }

    private void loadVertexProperties(int index) {
        T2DSpriteRenderer sprite = this.sprites[index];

        // Find offset within array (4 vertices per sprite)
        int offset = index * 4 * VERTEX_SIZE;

        Vector4f color = sprite.getColor();
        Vector2f[] tCoords = sprite.getTCoords();

        int t_id = 0;
        if (sprite.getTexture()!=null){
            for (int j = 0; j < textureList.size(); j++) {
                if (textureList.get(j)==sprite.getTexture()){
                    t_id = j+1;
                    break;
                }
            }
        }

        float x_pos = sprite.getParentObject().transform.pos.x;
        float y_pos = sprite.getParentObject().transform.pos.y;

        boolean isRotated = sprite.getParentObject().transform.rotation != 0.0f;
        Matrix4f transformMatrix = new Matrix4f().identity();
        if(isRotated){
            transformMatrix.translate(x_pos,y_pos,0f);
            transformMatrix.rotate(sprite.getParentObject().transform.rotation,
                    0,0,1f);
            transformMatrix.scale(sprite.getParentObject().transform.scale.x,sprite.getParentObject().transform.scale.y,1);
        }
        // Add vertices with the appropriate properties
        float xAdd = 0.5f;
        float yAdd = 0.5f;
        for (int i=0; i < 4; i++) {
            if (i == 1) {
                yAdd = -0.5f;
            } else if (i == 2) {
                xAdd = -0.5f;
            } else if (i == 3) {
                yAdd = 0.5f;
            }

            Vector4f currentPos = new Vector4f(sprite.getParentObject().transform.pos.x + (xAdd * sprite.getParentObject().transform.scale.x),
                    sprite.getParentObject().transform.pos.y + (yAdd * sprite.getParentObject().transform.scale.y),
                    0,1);
            if (isRotated){
                currentPos = new Vector4f(xAdd,yAdd,0,1).mul(transformMatrix);
            }
            // Load position
            vertices[offset] =currentPos.x;
            vertices[offset + 1] =currentPos.y;

            // Load color
            vertices[offset + 2] = color.x;
            vertices[offset + 3] = color.y;
            vertices[offset + 4] = color.z;
            vertices[offset + 5] = color.w;

            vertices[offset+6] =tCoords[i].x;
            vertices[offset+7] = tCoords[i].y;


            vertices[offset+8] = t_id;

            offset += VERTEX_SIZE;
        }
        if (!sprite.isPrimitive()){
            sprite.setVertices(vertices);
        }
    }

    private int[] generateIndices() {
        // 6 indices per quad (3 per triangle)
        int[] elements = new int[6 * maxBatchSize];
        for (int i=0; i < maxBatchSize; i++) {
            loadElementIndices(elements, i);
        }
        return elements;
    }

    private void loadElementIndices(int[] elements, int index) {
        int offsetArrayIndex = 6 * index;
        int offset = 4 * index;

        // 3, 2, 0, 0, 2, 1        7, 6, 4, 4, 6, 5
        // Triangle 1
        elements[offsetArrayIndex] = offset + 3;
        elements[offsetArrayIndex + 1] = offset + 2;
        elements[offsetArrayIndex + 2] = offset + 0;

        // Triangle 2
        elements[offsetArrayIndex + 3] = offset + 0;
        elements[offsetArrayIndex + 4] = offset + 2;
        elements[offsetArrayIndex + 5] = offset + 1;
    }

    public boolean hasRoom() {
        return this.hasRoom;
    }
    public boolean canAddSprite(){
        return this.numSprites<8;
    }
    public boolean haveTexture(T2DTexture texture){

        return this.textureList.contains(texture);
    }

    public int getZ_i() {
        return z_i;
    }

    @Override
    public int compareTo(T2DRenderBatch o) {
        return Integer.compare(this.z_i,o.z_i);
    }
}
