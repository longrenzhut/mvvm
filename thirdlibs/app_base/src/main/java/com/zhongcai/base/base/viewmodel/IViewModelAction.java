package com.zhongcai.base.base.viewmodel;

import androidx.lifecycle.MutableLiveData;

public interface IViewModelAction {

    void errorUILoading();

    void hideUILoading();

    void dismissLoading();


    MutableLiveData<BaseActionEvent> getActionLiveData();


}
