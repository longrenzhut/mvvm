package com.zhongcai.base.https.zip;

import com.zhongcai.base.base.presenter.BasePresenter;
import com.zhongcai.base.https.BaseSubscriber;
import com.zhongcai.base.https.ReqCallBack;

/**
 * Created by zc3 on 2018/4/3.
 */

public class ReqZipSubscriber<T,R> extends BaseSubscriber<String[]>{

    ReqCallBack<T> callBack1;
    ReqCallBack<R> callBack2;


    public ReqZipSubscriber(ReqCallBack<T> callBack1,ReqCallBack<R> callBack2){

        this.callBack1 = callBack1;
        this.callBack2 = callBack2;
    }


    private BasePresenter pt;

    public ReqZipSubscriber<T,R> setBasePt(BasePresenter pt){
        this.pt = pt;
        return this;
    }

    @Override
    public void onNext(String[] result) {
        if(null == pt || null == pt.getView())
            return;
        if(result.length == 2){
            ValueUtil.json(result[0],callBack1);
            ValueUtil.json(result[1],callBack2);
        }
        callBack1.onCompleted();
    }


    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if(null == pt || null == pt.getView())
            return;

        if(null != callBack1) {
            callBack1.onError(e);
            callBack1.onCompleted();
        }
    }


}
