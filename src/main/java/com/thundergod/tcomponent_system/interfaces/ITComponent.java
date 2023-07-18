package com.thundergod.tcomponent_system.interfaces;

import com.thundergod.ITPart;

public interface ITComponent extends ITPart {
    @Override
    void init();

    @Override
    void loop(float dt);

    @Override
    void dispose();
}
