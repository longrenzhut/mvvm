package com.zhongcai.base.base.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zhongcai.base.https.HttpProvider;
import com.zhongcai.base.https.Params;
import com.zhongcai.base.https.ReqCallBack;
import com.zhongcai.base.https.UpFileParam;
import com.zhongcai.base.https.zip.BaseBiFunction;
import com.zhongcai.base.https.zip.BaseFunction3;
import com.zhongcai.base.https.zip.BaseFunction4;
import com.zhongcai.base.https.zip.BaseFunction5;
import com.zhongcai.base.https.zip.ReqZip3Subscriber;
import com.zhongcai.base.https.zip.ReqZip4Subscriber;
import com.zhongcai.base.https.zip.ReqZip5Subscriber;
import com.zhongcai.base.https.zip.ReqZipSubscriber;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class BaseViewModel extends ViewModel implements IViewModelAction{

    private CompositeDisposable mCompositeDisposable;


    void dispose() {
        if (null != mCompositeDisposable &&!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    private void addDisposable(Disposable disposable){
        if(null == mCompositeDisposable)
            mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(disposable);
    }

    //1.MutableLiveData的父类是LiveData
    //2.LiveData在实体类里可以通知指定某个字段的数据更新.
    //3.MutableLiveData则是完全是整个实体类或者数据类型变化后才通知.不会细节到某个字段

    @Override
    protected void onCleared() {
        dispose();
        super.onCleared();
    }


    protected <T> void postC(String url, Params params, ReqCallBack<T> callBack){

        addDisposable(
                HttpProvider.getHttp().postC(url,params,callBack.setViewModel(this))
        );
    }

    protected <T> void postJ(String url, Params params, ReqCallBack<T> callBack){

        addDisposable(
                HttpProvider.getHttp().postJ(url,params,callBack.setViewModel(this))
        );
    }

    protected <T> void postJBuy(String url, Params params, ReqCallBack<T> callBack){

        addDisposable(
                HttpProvider.getHttp().postJBuy(url,params,callBack.setViewModel(this))
        );
    }

    protected <T> void upFile(String url, UpFileParam params, ReqCallBack<T> callBack){

        addDisposable(
                HttpProvider.getHttp().upFile(url,params,callBack.setViewModel(this))
        );
    }


    protected <T> void postP(String url, Params params, ReqCallBack<T> callBack){

        addDisposable(
                HttpProvider.getHttp().postP(url,params,callBack.setViewModel(this))
        );
    }

    //---------------------- 当需要使用onEorror 独立调用

    private MutableLiveData<BaseActionEvent> mActionLiveDataCallBack;

    @Override
    public MutableLiveData<BaseActionEvent> getCallBackLiveData() {
        if(null == mActionLiveDataCallBack)
            mActionLiveDataCallBack = new MutableLiveData<>();
        return mActionLiveDataCallBack;
    }


    @Override
    public void OnFailed(int code) {
        BaseActionEvent baseActionEvent = new BaseActionEvent(BaseActionEvent.failed);
        baseActionEvent.setCode(code);
        mActionLiveDataCallBack.setValue(baseActionEvent);
    }

    @Override
    public void onError() {
        BaseActionEvent baseActionEvent = new BaseActionEvent(BaseActionEvent.error);
        mActionLiveDataCallBack.setValue(baseActionEvent);
    }



    //---------------------------------------

    private MutableLiveData<BaseActionEvent> mActionLiveData;


    @Override
    public MutableLiveData<BaseActionEvent> getActionLiveData() {
        if(null == mActionLiveData)
            mActionLiveData = new MutableLiveData<>();
        return mActionLiveData;
    }


    //网络加载失败 登录失败等
    @Override
    public void errorUILoading() {
        BaseActionEvent baseActionEvent = new BaseActionEvent(BaseActionEvent.loading_error);
        mActionLiveData.setValue(baseActionEvent);
    }

    //网络请求成功
    @Override
    public void hideUILoading() {
        BaseActionEvent baseActionEvent = new BaseActionEvent(BaseActionEvent.loading_success);
        mActionLiveData.setValue(baseActionEvent);
    }

    @Override
    public void onCompleted() {
        BaseActionEvent baseActionEvent = new BaseActionEvent(BaseActionEvent.loading_onCompleted);
        mActionLiveData.setValue(baseActionEvent);
    }




    /**
     * 2个接口合并请求
     */
    protected <S,R> void zipPost(Observable<ResponseBody> s, Observable<ResponseBody> r,
                                 ReqCallBack<S> callBack1, ReqCallBack<R> callBack2){


        Disposable disposable = Observable.zip(s, r, new BaseBiFunction())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new ReqZipSubscriber<>(callBack1.setViewModel(this),callBack2));

        addDisposable(disposable);
    }

    /**
     * 3个接口合并请求
     */
    protected <S,R,H> void zipPost(Observable<ResponseBody> s, Observable<ResponseBody> r,Observable<ResponseBody> t,
                                   ReqCallBack<S> callBack1, ReqCallBack<R> callBack2, ReqCallBack<H> callBack3){


        Disposable disposable = Observable.zip(s, r,t, new BaseFunction3())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new ReqZip3Subscriber<>(callBack1.setViewModel(this),callBack2,callBack3));

        addDisposable(disposable);
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


        Disposable disposable = Observable.zip(t,r,s,h,new BaseFunction4())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new ReqZip4Subscriber<>(callBack1.setViewModel(this),callBack2,callBack3,callBack4));

        addDisposable(disposable);
    }

    /**
     * 5个接口合并请求
     */
    protected <T,R,S,H,X> void zipPost(Observable<ResponseBody> t,
                                     Observable<ResponseBody> r,
                                     Observable<ResponseBody> s,
                                     Observable<ResponseBody> h,
                                     Observable<ResponseBody> x,
                                     ReqCallBack<T> callBack1,
                                     ReqCallBack<R> callBack2,
                                     ReqCallBack<S> callBack3,
                                     ReqCallBack<H> callBack4,
                                     ReqCallBack<X> callBack5
                                       ){


        Disposable disposable = Observable.zip(t,r,s,h,x,new BaseFunction5())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new ReqZip5Subscriber<>(callBack1.setViewModel(this),callBack2,callBack3,callBack4,callBack5));

        addDisposable(disposable);
    }


    protected Observable<ResponseBody> postJResponseBody(String url,Params params){
        return HttpProvider.getHttp().createJService().post(url,params.getBody());
    }

    protected Observable<ResponseBody> postJBuyResponseBody(String url,Params params){
        return HttpProvider.getHttp().createJBuyService().post(url,params.getBody());
    }

    protected Observable<ResponseBody> postCResponseBody(String url,Params params){
        return HttpProvider.getHttp().createCService().post(url,params.getBody());
    }


    protected Observable<ResponseBody> postPResponseBody(String url,Params params){
        return HttpProvider.getHttp().createPService().post(url,params.getBody());
    }


}
