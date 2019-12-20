package com.zhongcai.base.base.presenter;
import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import com.zhongcai.base.base.activity.AbsActivity;
import com.zhongcai.base.base.fragment.AbsFragment;
import com.zhongcai.base.base.view.IBaseView;
import com.zhongcai.base.https.DownFileSubscriber;
import com.zhongcai.base.https.DownParam;
import com.zhongcai.base.https.UpFileParam;
import com.zhongcai.base.https.zip.BaseBiFunction;
import com.zhongcai.base.https.zip.BaseFunction3;
import com.zhongcai.base.https.zip.BaseFunction4;
import com.zhongcai.base.https.zip.ReqZip3Subscriber;
import com.zhongcai.base.https.zip.ReqZip4Subscriber;
import com.zhongcai.base.https.zip.ReqZipSubscriber;
import com.zhongcai.base.https.HttpProvider;
import com.zhongcai.base.https.Params;
import com.zhongcai.base.https.ReqCallBack;
import com.zhongcai.base.https.ReqSubscriber;

import java.lang.ref.WeakReference;

public class BasePresenter<T extends IBaseView> implements IBasePresenter<T> {


    private WeakReference<T> mView;
    protected AbsActivity context;

    protected AbsFragment baseFra;


    public <H extends BasePresenter> H attachView(T view) {
        this.mView = new WeakReference<>(view);
        return (H)this;
    }


    @Override
    public void attachActivity(AbsActivity act) {
        context = act;
    }

    @Override
    public void attachFragment(AbsFragment fra) {
        baseFra = fra;
    }


    @Override
    public T getView() {
//        if(mView.get() == null)
//            throw new RuntimeException("View has been detach by detachView method. " +
//                    "Ensure calling detach method inside activity destroy method");
        if(null != mView)
            return mView.get();
        return null;
    }


    @Override
    public void detachView() {
        if(mView != null) {
            mView.clear();
            mView = null;
        }
    }

    @Override
    public void onResume(){

    }


    public  <T> void post(Observable<ResponseBody> observable,ReqCallBack<T> callBack){
        setLayout(callBack);

        if(null != baseFra)
            baseFra.request(observable,
                    new ReqSubscriber(callBack));
        else
            context.request(observable,
                    new ReqSubscriber(callBack));
    }


    protected <T> void postJ(String url, Params params, ReqCallBack<T> callBack){

        post(HttpProvider.createJService().post(url,params.getBody()), callBack);
    }


    public  <T> void postP(String url, Params params, ReqCallBack<T> callBack){

        post(HttpProvider.createPService().post(url,params.getBody()), callBack);
    }


//    public  <T> void postV(String url, Params params, ReqCallBack<T> callBack){
//
//        post(HttpProvider.createVService().post(url,params.getBody()), callBack);
//    }

    public void download(String url, DownParam param, DownFileSubscriber ob){
        context.request(HttpProvider.downloadFile().loadFile(url,param.getMap()),
                ob);
    }

    public <T> void upFile(String[] files,ReqCallBack<T> reqCallBack){
        UpFileParam params = new UpFileParam();
        for(int i = 0;i < files.length;i ++) {
            params.putFile(files[i]);
        }

        context.request(HttpProvider.createUpService().upFile("attach/upload",params.getMap())
                ,new ReqSubscriber(reqCallBack));
    }


    /**
     * @param url 上传的地址  如index.php
     * @param params 参数
     * @param callBack
     * @param <T>
     */
    protected <T> void upFile(String url,UpFileParam params,ReqCallBack<T> callBack){
        post(HttpProvider.createUpService().upFile(url,params.getMap()),callBack);
    }


    protected <T> void UpImg(UpFileParam params,ReqCallBack<T> callBack){
        upFile("index.php",params,callBack);
    }



    /**
     * 2个接口合并请求
     */
    protected <S,R> void zipPost(Observable<ResponseBody> s, Observable<ResponseBody> r,
                                 ReqCallBack<S> callBack1, ReqCallBack<R> callBack2){
        setLayout(callBack1);

        LifecycleTransformer compose;
        if(null != baseFra)
            compose = baseFra.bindUntilEvent(FragmentEvent.DESTROY);
        else
            compose = context.bindUntilEvent(ActivityEvent.DESTROY);

        Observable.zip(s, r, new BaseBiFunction())
                .compose(compose)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ReqZipSubscriber(callBack1,callBack2).setBasePt(this));
    }

    /**
     * 3个接口合并请求
     */
    protected <S,R,H> void zipPost(Observable<ResponseBody> s, Observable<ResponseBody> r,Observable<ResponseBody> t,
                                   ReqCallBack<S> callBack1, ReqCallBack<R> callBack2, ReqCallBack<H> callBack3){

        setLayout(callBack1);

        LifecycleTransformer compose;
        if(null != baseFra)
            compose = baseFra.bindUntilEvent(FragmentEvent.DESTROY);
        else
            compose = context.bindUntilEvent(ActivityEvent.DESTROY);

        Observable.zip(s, r,t, new BaseFunction3())
                .compose(compose)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ReqZip3Subscriber<>(callBack1,callBack2,callBack3).setBasePt(this));
    }

    /**
     * 4个接口合并请求
     */
    protected <T,R,S,H> void zipPost(Observable<ResponseBody> t,
                                     Observable<ResponseBody> r,
                                     Observable<ResponseBody> s,
                                     Observable<ResponseBody> h,
                                     ReqCallBack<T> callBack1,
                                     ReqCallBack<R> callBack2,
                                     ReqCallBack<S> callBack3,
                                     ReqCallBack<H> callBack4){

        setLayout(callBack1);

        LifecycleTransformer compose;
        if(null != baseFra)
            compose = baseFra.bindUntilEvent(FragmentEvent.DESTROY);
        else
            compose = context.bindUntilEvent(ActivityEvent.DESTROY);

        Observable.zip(t,r,s,h,new BaseFunction4())
                .compose(compose)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ReqZip4Subscriber(callBack1,callBack2,callBack3,callBack4)
                        .setBasePt(this));
    }


    private <T> void setLayout(ReqCallBack<T> callBack1){
        if(null != baseFra)
            callBack1.setUILayout(baseFra.getUiLoad());
        else
            callBack1.setUILayout(context.getUiLoad());

        callBack1.setLoading(context.getLoading());
    }

}
