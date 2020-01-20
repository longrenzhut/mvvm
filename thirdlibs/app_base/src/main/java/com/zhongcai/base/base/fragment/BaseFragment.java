package com.zhongcai.base.base.fragment;


import androidx.lifecycle.Observer;

import com.zhongcai.base.base.viewmodel.BaseActionEvent;
import com.zhongcai.base.base.viewmodel.BaseViewModel;

/**
 * Created by Administrator on 2018/3/7.
 */
abstract public class BaseFragment<T extends BaseViewModel> extends AbsFragment {

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
