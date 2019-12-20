package com.zhongcai.base.https;


import com.google.gson.Gson;
import com.zhongcai.base.rxbus.RxBus;
import com.zhongcai.base.theme.layout.LoadingDialog;
import com.zhongcai.base.theme.layout.UILoadLayout;
import com.zhongcai.base.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by zhutao on 2018/3/7.
 */

 abstract public class ReqCallBack<T> implements IReqCallBack<T> {

    private UILoadLayout mLayout;

    public void setUILayout(UILoadLayout mLayout){
        if(null != mLayout){
            WeakReference<UILoadLayout> weak = new WeakReference<>(mLayout);
            this.mLayout = weak.get();
        }
    }

    private boolean isToast = true;

    public boolean isToast() {
        return isToast;
    }

    public ReqCallBack<T> setNoToast() {
        isToast = false;

        return this;
    }

    private LoadingDialog mLoading;

    public void setLoading(LoadingDialog mLoading){
        if(null != mLoading){
            WeakReference<LoadingDialog> weak = new WeakReference(mLoading);
            this.mLoading = weak.get();
        }
    }


    private boolean ispaging = false;

    public ReqCallBack<T> setIspaging() {
        this.ispaging = true;
        return this;
    }

    private String  key = "records";

    public ReqCallBack<T> setKey(String key){
        this.key = key;
        return this;
    }

    private Type modelType;

    public ReqCallBack setModelType(Type modelType) {
        this.modelType = modelType;
        return this;
    }

    public void onNext(String str){

        String value = "";
        if(ispaging) {
            try {
                JSONObject json = new JSONObject(str);
                value = json.optString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            value = str;
        }

        if (modelType == null) {
            //以下代码是通过泛型解析实际参数,泛型必须传
            Type genType = getClass().getGenericSuperclass();
            Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
            modelType = params[0];
        }
        if(modelType == null){
            ToastUtils.showToast("模型没有序列化");
        }
        else if(modelType ==  Void.class){
            OnSuccess(null);
        }
        else if(modelType == JSONObject.class){
            JSONObject json = null;
            try {
                json = new JSONObject(value);
                OnSuccess((T)json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(modelType == JSONArray.class){
            JSONArray json = null;
            try {
                json = new JSONArray(value);
                OnSuccess((T)json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(modelType == String.class){
            OnSuccess((T)value);
        }
        else {
            T result = new Gson().fromJson(value, modelType);
            OnSuccess(result);
        }

        if(null != mLayout)
            mLayout.loadok();

    }

    @Override
    public void OnFailed(int code) {
        if(code == 12100 || code == 12300 || code == 12400){
            RxBus.instance().post(21,1);
        } else if(code == 12700){
            RxBus.instance().post(21,2);
        }else if(code == 13100){
            RxBus.instance().post(13100,0);
        }
        if(null != mLayout)
            mLayout.loadok();

    }

    @Override
    public void OnFailed(int code,String msg) {
        if(null != mLayout)
            mLayout.loadok();
    }

    @Override
    public void onError(Throwable e) {
        if(null != mLayout)
            mLayout.loadFailed();
    }

    @Override
    public void onCompleted() {
        if(null != mLoading)
            mLoading.dismiss();
    }

    @Override
    public void OnSuccessJson(JSONObject josn) {

    }
}
