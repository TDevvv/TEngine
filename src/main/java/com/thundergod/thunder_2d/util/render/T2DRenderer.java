package com.thundergod.thunder_2d.util.render;

import com.thundergod.thunder_2d.util.components.T2DSpriteRenderer;
import com.thundergod.thunder_2d.util.fabric.T2DTexture;
import com.thundergod.tobjectsystem.core.TOSGameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class T2DRenderer {
    private final int MAX_BATCH_SIZE = 1000;
    private List<T2DRenderBatch> batches;

    public T2DRenderer() {
        this.batches = new ArrayList<>();
    }

    public void add(TOSGameObject go) {
        T2DSpriteRenderer spr = go.getComponent(T2DSpriteRenderer.class);
        if (spr != null) {
            add(spr);
        }
    }

    private void add(T2DSpriteRenderer sprite) {
        boolean added = false;
        for (T2DRenderBatch batch : batches) {
            if (batch.hasRoom()&&batch.getZ_i()==sprite.getParentObject().getZ_i()) {
                T2DTexture texture = sprite.getTexture();
                if (texture == null || (batch.haveTexture(texture) || batch.canAddSprite())){
                    batch.addSprite(sprite);
                    added = true;
                    break;
                }

            }
        }

        if (!added) {
            T2DRenderBatch newBatch = new T2DRenderBatch(MAX_BATCH_SIZE,sprite.getParentObject().getZ_i());
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(sprite);
            Collections.sort(batches);
        }
    }

    public void render() {
        for (T2DRenderBatch batch : batches) {
            batch.render();
        }
    }
}
