package com.zhongcai.base.base.viewmodel;

public class BaseActionEvent extends BaseEvent {

    public static final int loading_success = 1;

    public static final int loading_error = 2;

    public static final int loading_onCompleted = 3;



    private String mMessage;

    public BaseActionEvent(int action) {
        super(action);
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
