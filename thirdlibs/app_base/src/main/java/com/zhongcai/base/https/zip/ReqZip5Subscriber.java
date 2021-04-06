package com.zhongcai.base.https.zip;

import com.zhongcai.base.https.BaseSubscriber;
import com.zhongcai.base.https.ReqCallBack;

/**
 * Created by zc3 on 2018/5/15.
 */

public class ReqZip5Subscriber<T,R,S,H,X> extends BaseSubscriber<String[]> {

    ReqCallBack<T> callBack1;
    ReqCallBack<R> callBack2;
    ReqCallBack<S> callBack3;
    ReqCallBack<H> callBack4;
    ReqCallBack<X> callBack5;



    public ReqZip5Subscriber(ReqCallBack<T> callBack1,ReqCallBack<R> callBack2,
                             ReqCallBack<S> callBack3,
                             ReqCallBack<H> callBack4,
                             ReqCallBack<X> callBack5
    ){

        this.callBack1 = callBack1;
        this.callBack2 = callBack2;
        this.callBack3 = callBack3;
        this.callBack4 = callBack4;
        this.callBack5 = callBack5;
    }


    @Override
    public void onNext(String[] result) {
        if(result.length == 5){
            ValueUtil.json(result[0],callBack1);
            ValueUtil.json(result[1],callBack2);
            ValueUtil.json(result[2],callBack3);
            ValueUtil.json(result[3],callBack4);
            ValueUtil.json(result[4],callBack5);
        }
        callBack1.onCompleted();
    }


    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if(null != callBack1) {
            callBack1.onError(e);
            callBack1.onCompleted();
        }
    }
}

