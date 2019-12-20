package com.zhongcai.base.base.presenter;


import com.zhongcai.base.base.activity.AbsActivity;
import com.zhongcai.base.base.fragment.AbsFragment;

/**
 * Created by zhutao on 2016/10/9.
 */
public interface IBasePresenter<T> {


    void attachActivity(AbsActivity act);

    void attachFragment(AbsFragment fra);

    void detachView();
    T getView();

    void onResume();
}
