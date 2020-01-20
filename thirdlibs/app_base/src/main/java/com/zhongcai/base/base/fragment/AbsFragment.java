package com.zhongcai.base.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.trello.rxlifecycle3.android.FragmentEvent;
import com.zhongcai.base.R;
import com.zhongcai.base.base.activity.AbsActivity;
import com.zhongcai.base.theme.layout.HeaderLayout;
import com.zhongcai.base.theme.layout.StatusbarView;
import com.zhongcai.base.theme.layout.UILoadLayout;
import com.zhongcai.base.utils.BaseUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/3/7.
 */

abstract public class AbsFragment extends RxFragment{

    protected View mView;


    public AbsActivity mContext;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.mContext = (AbsActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = getRootView();
        if(!isUseHeader()) {
            mHeaderLayout = findId(R.id.header);
            if(null != mHeaderLayout)
                mHeaderLayout.setTarget(this);
        }
        if(!isUseStatus()){
            statusbarView = findId(R.id.statusbar);
        }
        return mView;
    }

    //统一的头部

    protected boolean isUseStatus(){
        return true;
    }

    protected boolean isUseHeader(){
        return true;
    }

    //统一的头部

    public HeaderLayout mHeaderLayout;

    protected RelativeLayout mRootView;
    private StatusbarView statusbarView;
    protected UILoadLayout mUiLayout;
    /**
     * 设置根容器  里面添加状态栏 头部 以及  进入界面加载动画
     */
    private View getRootView(){
//        if(!isUseStatus() && !isUseHeader()){
//            View contentView = LayoutInflater.from(this).inflate(getLayoutId(),null);
//            return contentView;
//        }

        mRootView = new RelativeLayout(mContext);
        mRootView.setLayoutParams(new ViewGroup.LayoutParams(-1,-1));
        if(isUseStatus()){
            statusbarView = new StatusbarView(mContext);
            mRootView.addView(statusbarView);
        }

        if(isUseHeader()) {
            mHeaderLayout = new HeaderLayout(mContext);
            mHeaderLayout.setTarget(this);
            RelativeLayout.LayoutParams headerlp = new RelativeLayout.LayoutParams(-1,-2);
            headerlp.addRule(RelativeLayout.BELOW,R.id.statusbar);
            mHeaderLayout.setLayoutParams(headerlp);
            mRootView.addView(mHeaderLayout);
        }
        View contentView = LayoutInflater.from(mContext).inflate(getLayoutId(),null);
        RelativeLayout.LayoutParams contentViewlp = new RelativeLayout.LayoutParams(-1,-1);
        if(isUseHeader())
            contentViewlp.addRule(RelativeLayout.BELOW,R.id.header);
        else if(isUseStatus()){
            contentViewlp.addRule(RelativeLayout.BELOW,R.id.statusbar);
        }
        mRootView.addView(contentView,contentViewlp);
        return mRootView;
    }

    public void onIvLeftClick(){
        mContext.finish();
    }

    public void onTvRightClick(){

    }

    public void onTvCancelClick(){

    }

    /**
     * 设置根容器  里面添加状态栏 头部 以及  进入界面加载动画
     */
    protected void setUiLoadLayout(){
        if(null == mUiLayout) {
            mUiLayout = new UILoadLayout(mContext);
            mUiLayout.sethasTop(false);
            mUiLayout.setOnloadListener(new UILoadLayout.OnLoadListener() {
                @Override
                public void load() {
                    request();
                }
            });

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-1, -1);
            mRootView.addView(mUiLayout, lp);
        }
        if(null != mUiLayout){
            mUiLayout.resetUi();
        }
    }

    protected void request(){

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewModel();
        initView(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setPresenter();
//        initView();
    }

    public <T extends View> T findViewId(View view, int id) {
        return (T) view.findViewById(id);
    }

    public <T extends View> T findId(int id) {
        return (T) mView.findViewById(id);
    }



    /**
     * 设置一些自定义的空页面
     */
    protected void setUiLoadSelfLayout(int layoutId){

        View view = LayoutInflater.from(mContext).inflate(layoutId,null);
//        view.findViewById(R.id.m_iv_exit).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onIvLeftClick();
//            }
//        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-1, -1);
        mRootView.addView(view, lp);
    }



    //请求接口时
    public void show(){
        if(null != mContext)
            mContext.show();
    }

    public void dismiss(){
        if(null != mContext)
            mContext.dismiss();
    }

    /**
     * 动态设置状态栏的颜色
     * @param color
     */
    protected void setStatusBarColor(int color){
        if(isUseStatus())
            statusbarView.setBackgroundColor(BaseUtils.getColor(color));
    }


    abstract public int getLayoutId();

    abstract public void initView(Bundle savedInstanceState);

    protected void setViewModel(){

    }

    public UILoadLayout getUiLoad(){
        return mUiLayout;
    }




}
