package com.thundergod.tengine.interfaces;

import com.thundergod.tutil.ICanDispose;

public interface ITEngine extends ICanDispose,OnFinishLoop {
    void init();
    @Override
    void dispose();
}
