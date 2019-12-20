package com.zhongcai.base.base.activity;

import com.zhongcai.base.base.presenter.BasePresenter;


/**
 * Created by Administrator on 2018/3/7.
 */

abstract public class BaseActivity<V extends BasePresenter> extends AbsActivity{

    protected V mPresenter;

    @Override
    protected void setPresenter(){
        mPresenter = getPresenter();
        mPresenter.attachActivity(this);
    }

    protected abstract V getPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
