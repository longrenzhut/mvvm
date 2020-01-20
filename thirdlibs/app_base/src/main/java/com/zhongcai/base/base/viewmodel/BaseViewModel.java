package com.zhongcai.base.base.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zhongcai.base.https.HttpProvider;
import com.zhongcai.base.https.Params;
import com.zhongcai.base.https.ReqCallBack;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel implements IViewModelAction{

    private CompositeDisposable mCompositeDisposable;


    void dispose() {
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    //1.MutableLiveData的父类是LiveData
    //2.LiveData在实体类里可以通知指定某个字段的数据更新.
    //3.MutableLiveData则是完全是整个实体类或者数据类型变化后才通知.不会细节到某个字段

    @Override
    protected void onCleared() {
        dispose();
        super.onCleared();
    }

    protected <T> void postJ(String url, Params params, ReqCallBack<T> callBack){
        if(null == mCompositeDisposable)
            mCompositeDisposable = new CompositeDisposable();

        mCompositeDisposable.add(
                HttpProvider.getHttp().postJ(url,params,callBack.setViewModel(this))
        );
    }


    protected <T> void postP(String url, Params params, ReqCallBack<T> callBack){
        if(null == mCompositeDisposable)
            mCompositeDisposable = new CompositeDisposable();

        mCompositeDisposable.add(
                HttpProvider.getHttp().postP(url,params,callBack.setViewModel(this))
        );
    }



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
    public void dismissLoading() {
        BaseActionEvent baseActionEvent = new BaseActionEvent(BaseActionEvent.loading_onCompleted);
        mActionLiveData.setValue(baseActionEvent);
    }



}
