package com.zhongcai.base.base.viewmodel;

public class BaseEvent {
    private int mAction;

    public BaseEvent(int action) {
        mAction = action;
    }

    public int getAction() {
        return mAction;
    }
}
