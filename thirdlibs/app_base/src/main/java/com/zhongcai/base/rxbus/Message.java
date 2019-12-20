package com.zhongcai.base.rxbus;


public class Message<T> {


    private int code = -11;
    private T value;


    public Message(int code, T value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public T getValue() {
        return value;
    }
}
