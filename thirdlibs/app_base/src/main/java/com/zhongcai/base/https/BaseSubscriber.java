package com.zhongcai.base.https;

import android.accounts.NetworkErrorException;
import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.zhongcai.base.Config;
import com.zhongcai.base.utils.Logger;
import com.zhongcai.base.utils.ToastUtils;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhutao on 2018/3/7.
 */

abstract public class BaseSubscriber<T> extends DisposableObserver<T> {


    public BaseSubscriber(){

    }


    private String msg(Throwable t){
        if (t instanceof NetworkErrorException ||
                t instanceof UnknownHostException ||
                t instanceof ConnectException){
            Logger.info(t.getMessage());
            return "网络异常";
        }

        else if (t instanceof SocketTimeoutException ||
                t instanceof InterruptedIOException ||
                t instanceof TimeoutException){
            Logger.info(t.getMessage());
            return "网络请求超时";
        }

        else if (t instanceof JsonSyntaxException) {
            return "请求不合法";
        } else if (t instanceof JsonParseException
                || t instanceof JSONException
                || t instanceof ParseException) {   //  解析错误
            return "解析错误";
        }
        else if (t instanceof javax.net.ssl.SSLHandshakeException) {
            return "证书验证失败";
        } else if (t instanceof NullPointerException) {
            return "空指针异常";
        }
        else {
            return "";
        }
    }





    @Override
    public void onError(Throwable e) {
        if(Config.TEST || Config.DEVELOP || Config.PRE)
            ToastUtils.showToast(msg(e));
    }

    @Override
    public void onComplete() {

    }
}
