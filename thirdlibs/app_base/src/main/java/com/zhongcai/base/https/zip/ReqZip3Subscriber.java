package com.zhongcai.base.https.zip;

import com.zhongcai.base.base.presenter.BasePresenter;
import com.zhongcai.base.https.BaseSubscriber;
import com.zhongcai.base.https.ReqCallBack;

/**
 * Created by zc3 on 2018/4/3.
 */

public class ReqZip3Subscriber<T,R,S> extends BaseSubscriber<String[]> {

    ReqCallBack<T> callBack1;
    ReqCallBack<R> callBack2;
    ReqCallBack<S> callBack3;


    private BasePresenter pt;

    public ReqZip3Subscriber<T,R,S> setBasePt(BasePresenter pt){
        this.pt = pt;
        return this;
    }

    public ReqZip3Subscriber(ReqCallBack<T> callBack1, ReqCallBack<R> callBack2, ReqCallBack<S> callBack3){

        this.callBack1 = callBack1;
        this.callBack2 = callBack2;
        this.callBack3 = callBack3;
    }


    @Override
    public void onNext(String[] result) {
        if(null == pt || null == pt.getView())
            return;
        if(result.length == 3){
            ValueUtil.json(result[0],callBack1);
            ValueUtil.json(result[1],callBack2);
            ValueUtil.json(result[2],callBack3);
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

