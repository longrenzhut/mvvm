package com.zhongcai.base.https;

import com.zhongcai.base.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/3/7.
 */

public class ReqSubscriber<T> extends BaseSubscriber<ResponseBody> {

    private ReqCallBack<T> callBack;
    private CompositeDisposable compositeDisposable;

    public ReqSubscriber(ReqCallBack<T> callBack){
        this.callBack = callBack;
    }


    public ReqSubscriber(ReqCallBack<T> callBack,CompositeDisposable compositeDisposable){
        this.callBack = callBack;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);
        if(null != compositeDisposable)
            compositeDisposable.add(d);
    }

    @Override
    public void onNext(ResponseBody responseBody) {

        try {
            String value = responseBody.string();
            String resultData = AESHexEncrypt.decrypt(value, AESHexEncrypt.KEY);//解密

            if(resultData == null){
                callBack.onError(null);
                ToastUtils.showToast("解析错误");
                return;
            }

            JSONObject json = new JSONObject(resultData);
            responseBody.close();

            int code = json.optInt("code");
            if(code == 200){
                JSONObject data = json.optJSONObject("data");
                int subCode = data.optInt("subCode");
                if(subCode == 10000){
                    String result = data.optString("result");
                    callBack.OnSuccessJson(data);
                    callBack.onNext(result);
                }else {
                    //逻辑业务处理提示
                    if(callBack.isToast())
                        ToastUtils.showToast(data.optString("subMsg"));
                    callBack.OnFailed(subCode);
                    callBack.OnFailed(subCode,data.optString("subMsg"));
                }
            } else{
                //服务器错误提示
                if(callBack.isToast())
                    ToastUtils.showToast(json.optString("msg"));
                callBack.onError(null);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        callBack.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if(null != callBack) {
            callBack.onError(e);
            callBack.onCompleted();
        }
    }
}
