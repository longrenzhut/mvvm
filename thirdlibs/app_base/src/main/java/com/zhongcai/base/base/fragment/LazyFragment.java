package com.zhongcai.base.base.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;

import com.zhongcai.base.base.viewmodel.BaseActionEvent;
import com.zhongcai.base.base.viewmodel.BaseViewModel;

/**
 * Created by zhutao on 2018/3/16.
 * 懒加载 当使用viewpager 时候用到
 */

abstract public class LazyFragment<T extends BaseViewModel> extends AbsFragment {


    @Override
    protected boolean isUseHeader() {
        return false;
    }

    @Override
    protected boolean isUseStatus() {
        return false;
    }

    //Fragment的View加载完毕的标记
    private boolean isPrepared = false;


    //Fragment对用户可见的标记
    private  boolean isUIVisible = false;



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUIVisible = true;
            load();
        } else {
            isUIVisible = false;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
        load();
    }


    private void load() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,
        // 并且会在onCreateView执行前回调,
        // 必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isPrepared && isUIVisible) {
            lazyLoad();
            //数据加载完毕,恢复标记,防止重复加载
            isPrepared = false;
            isUIVisible = false;
        }
    }


    /**
     * 懒加载
     * 当fragment显示在前台的时候 请求数据
     */
    public abstract void lazyLoad();





    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    protected T mViewModel;

    @Override
    public void setViewModel() {
        mViewModel = getViewModel();
        observe(mViewModel.getActionLiveData(), new Observer<BaseActionEvent>() {
            @Override
            public void onChanged(BaseActionEvent baseActionEvent) {
                switch (baseActionEvent.getAction()){
                    case BaseActionEvent.loading_success:
                        if(null != mUiLayout)
                            mUiLayout.loadok();
                        break;
                    case BaseActionEvent.loading_error:
                        if(null != mUiLayout)
                            mUiLayout.loadFailed();
                        break;
                    case BaseActionEvent.loading_onCompleted:
                        dismiss();
                        onCompleted();
                        break;
                }
            }
        });

        observe(mViewModel.getCallBackLiveData(), new Observer<BaseActionEvent>() {
            @Override
            public void onChanged(BaseActionEvent baseActionEvent) {
                switch (baseActionEvent.getAction()){
                    case BaseActionEvent.failed:
                        onFailed(baseActionEvent.getCode());
                        break;
                    case BaseActionEvent.error:
                        onError();
                        break;
                }
            }
        });

        setObserve();
    }

    abstract public  T getViewModel();


    protected void onCompleted(){

    }
    abstract public void setObserve();

    protected void onError(){

    }

    protected void onFailed(int code){

    }


}
