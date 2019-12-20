package com.zhongcai.base.base.fragment;


import com.zhongcai.base.base.presenter.BasePresenter;

/**
 * Created by Administrator on 2018/3/7.
 */

abstract public class BaseFragment<V extends BasePresenter> extends AbsFragment {

    protected V mPresenter;

    @Override
    protected void setPresenter(){
        mPresenter = getPresenter();
        mPresenter.attachActivity(mContext);
        mPresenter.attachFragment(this);
    }

    protected abstract V getPresenter();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

}
