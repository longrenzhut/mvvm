package com.zhongcai.base.base.viewmodel;

import androidx.lifecycle.MutableLiveData;

public interface IViewModelAction {

    void errorUILoading();

    void hideUILoading();

    void onCompleted();


    MutableLiveData<BaseActionEvent> getActionLiveData();
    MutableLiveData<BaseActionEvent> getCallBackLiveData();


    void OnFailed(int code);

    void onError();


}
