package com.zhongcai.base.base.activity;


import androidx.lifecycle.Observer;

import com.zhongcai.base.base.viewmodel.BaseActionEvent;
import com.zhongcai.base.base.viewmodel.BaseViewModel;

abstract public class BaseAcivity<T extends BaseViewModel> extends AbsActivity {


    protected T mViewModel;

    @Override
    public void setViewModel() {
        mViewModel = getViewModel();
        mViewModel.getActionLiveData().observe(this, new Observer<BaseActionEvent>() {
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
                        break;
                }
            }
        });
    }

    abstract public  T getViewModel();
}
