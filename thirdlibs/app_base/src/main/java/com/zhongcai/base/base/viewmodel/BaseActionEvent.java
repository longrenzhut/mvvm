package com.zhongcai.base.base.viewmodel;

public class BaseActionEvent extends BaseEvent {

    public static final int loading_success = 1;

    public static final int loading_error = 2;

    public static final int loading_onCompleted = 3;


    public static final int error = 4;

    public static final int failed = 5;


    private int code;

    public BaseActionEvent(int action) {
        super(action);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
