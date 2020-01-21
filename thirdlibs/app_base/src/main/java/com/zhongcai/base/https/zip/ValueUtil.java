package com.zhongcai.base.https.zip;


import com.zhongcai.base.https.AESHexEncrypt;
import com.zhongcai.base.https.ReqCallBack;
import com.zhongcai.base.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by zc3 on 2019/2/13.
 */

public class ValueUtil {


    static String ResponseBody2Str(ResponseBody responseBody) {

        try {
            String value = responseBody.string();
            responseBody.close();
            return value;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "";
    }


    public static <T> void json(String value, ReqCallBack<T> callback){
        try {

            String resultData = AESHexEncrypt.decrypt(value,AESHexEncrypt.KEY);//解密

            if(resultData == null){
                callback.onError(null);
                if(null != callback)
                    ToastUtils.showToast("解析错误");
                return;
            }

            JSONObject json = new JSONObject(resultData);
            int code = json.optInt("code");
            if (code == 200) {
                JSONObject data = json.optJSONObject("data");
                int subCode = data.optInt("subCode");
                if (subCode == 10000) {
                    if(null != callback)
                        callback.onNext(data.optString("result"));
                }
                else {
                    //逻辑业务处理提示
                    if(null != callback) {
                        if (callback.isToast())
                            ToastUtils.showToast(data.optString("subMsg"));
                        callback.OnFailed(subCode);
                    }
                }
            } else {
                //服务器错误提示
                if(null != callback) {
                    if (callback.isToast())
                        ToastUtils.showToast(json.optString("msg"));
                    callback.onError(null);
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
