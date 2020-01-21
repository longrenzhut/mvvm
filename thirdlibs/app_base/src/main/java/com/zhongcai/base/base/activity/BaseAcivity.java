package com.zhongcai.base.base.activity;


import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.zhongcai.base.base.viewmodel.BaseActionEvent;
import com.zhongcai.base.base.viewmodel.BaseViewModel;
import com.zhongcai.base.utils.Logger;
import com.zhongcai.base.utils.ToastUtils;

abstract public class BaseAcivity<T extends BaseViewModel> extends AbsActivity {


    protected T mViewModel;

    @Override
    public void setViewModel() {
        mViewModel = getViewModel();
        observe(mViewModel.getActionLiveData(), new Observer<BaseActionEvent>() {
            @Override
            public void onChanged(BaseActionEvent baseActionEvent) {
                switch (baseActionEvent.getAction()){
                    case BaseActionEvent.loading_success:
                        Logger.debug("loading_success");
                        if(null != mUiLayout)
                            mUiLayout.loadok();
                        break;
                    case BaseActionEvent.loading_error:
                        Logger.debug("loading_error");
                        if(null != mUiLayout)
                            mUiLayout.loadFailed();
                        break;
                    case BaseActionEvent.loading_onCompleted:
                        Logger.debug("loading_onCompleted");
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


    //自动调用
    protected void onCompleted(){

    }

    protected void onError(){

    }

    protected void onFailed(int code){

    }




    abstract public T getViewModel();
    abstract public void setObserve();



}
