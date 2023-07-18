package com.thundergod;

import com.thundergod.tmain.interfaces.ITMCanDispose;

public interface ITPart extends ITMCanDispose {
    void init();
    void loop(float dt);
    @Override
    void dispose();
}
