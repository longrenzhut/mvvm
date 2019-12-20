package com.zhongcai.base.https;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/3/7.
 */

public interface IReqCallBack<T>{
    void OnSuccess(T model);
    void OnFailed(int code);
    void OnFailed(int code, String msg);
    void onError(Throwable e);
    void onCompleted();

    void OnSuccessJson(JSONObject josn);

}
