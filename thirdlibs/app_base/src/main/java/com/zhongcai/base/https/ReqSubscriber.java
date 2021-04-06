package com.zhongcai.base.https;

import com.zhongcai.base.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOError;
import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/3/7.
 */

public class ReqSubscriber<T> extends BaseSubscriber<ResponseBody> {

    private ReqCallBack<T> callBack;

    public ReqSubscriber(ReqCallBack<T> callBack){
        this.callBack = callBack;
    }


    @Override
    public void onNext(ResponseBody responseBody) {

        try {
            String value = responseBody.string();
            String resultData = AESHexEncrypt.decrypt(value, AESHexEncrypt.KEY);//解密

            if(resultData == null){
                if(null != callBack)
                    callBack.onError(null);
                toast("解析错误");
                return;
            }


            JSONObject json = new JSONObject(resultData);
            responseBody.close();

            if(!json.has("meta")){
                callBack.onError(null);
                return;
            }

            JSONObject meta = json.optJSONObject("meta");
            int code = meta.optInt("code");
            if(code == 200){
                JSONObject data = json.optJSONObject("data");
                int subCode = data.optInt("subCode");
                if(subCode == 10000){
                    String result = data.optString("result");
                    if(null != callBack) {
                        callBack.OnSuccessJson(data);
                        callBack.onNext(result);
                    }
                }else {
                    //逻辑业务处理提示
                    if(null != callBack) {
                        if (callBack.isToast())
                            toast(data.optString("subMsg"));
                        callBack.OnFailed(subCode);
                        callBack.OnFailed(subCode, data.optString("subMsg"));
                    }
                }
            } else{
                //服务器错误提示
                if(null != callBack) {
                    if (callBack.isToast())
                        toast(meta.optString("msg"));
                    callBack.onError(null);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        if(null != callBack)
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
    
    
    private void toast(String text){
        try{
            ToastUtils.showToast(text);
        }
        catch (IOError error){
            error.printStackTrace();
        }
        catch (Exception error){
            error.printStackTrace();
        }

    }
}
