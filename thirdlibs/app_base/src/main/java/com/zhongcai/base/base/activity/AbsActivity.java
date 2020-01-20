package com.zhongcai.base.base.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.zhongcai.base.R;
import com.zhongcai.base.base.viewmodel.BaseViewModel;
import com.zhongcai.base.theme.layout.HeaderLayout;
import com.zhongcai.base.theme.layout.LoadingDialog;
import com.zhongcai.base.theme.layout.StatusbarView;
import com.zhongcai.base.theme.layout.UILoadLayout;
import com.zhongcai.base.theme.statusbar.StatusBarCompat;
import com.zhongcai.base.utils.BaseUtils;
import com.zhongcai.base.utils.ScreenUtils;

abstract public class AbsActivity extends RxActivity {


    /**
     * 是否全屏 首页 启动页 等使用 覆盖方法设置为true
     * @return
     */
    protected boolean isUseStatus(){
        return true;
    }

    protected boolean isUseHeader() {
        return true;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(null != savedInstanceState)
            ScreenUtils.init(this);
        if(getLayoutId() != 0)
            setContentView(getRootView());

        setStatusBar();

        setViewModel();

        initView(savedInstanceState);


    }


    /**
     * 设置状态栏 全屏
     */
    public boolean islightStatusBar(){
        return true;
    }

    protected void setStatusBar() {
        setStatusBar(islightStatusBar());
    }

    protected void setStatusBar(boolean islight) {
        StatusBarCompat.setStatusBarColor(getWindow(),
                getResources().getColor(R.color.transparent), islight,true);
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

        mRootView = new RelativeLayout(this);
        mRootView.setLayoutParams(new ViewGroup.LayoutParams(-1,-1));
        if(isUseStatus()){
            statusbarView = new StatusbarView(this);
            mRootView.addView(statusbarView);
        }

        if(isUseHeader()) {
            mHeaderLayout = new HeaderLayout(this);
            mHeaderLayout.setTarget(this);
            RelativeLayout.LayoutParams headerlp = new RelativeLayout.LayoutParams(-1,-2);
            headerlp.addRule(RelativeLayout.BELOW, R.id.statusbar);
            mHeaderLayout.setLayoutParams(headerlp);
            mRootView.addView(mHeaderLayout);
        }
        View contentView = LayoutInflater.from(this).inflate(getLayoutId(),null);
        RelativeLayout.LayoutParams contentViewlp = new RelativeLayout.LayoutParams(-1,-1);
        if(isUseHeader())
            contentViewlp.addRule(RelativeLayout.BELOW,R.id.header);
        else if(isUseStatus()){
            contentViewlp.addRule(RelativeLayout.BELOW,R.id.statusbar);
        }
        mRootView.addView(contentView,contentViewlp);
        return mRootView;
    }

    public UILoadLayout getUiLoad(){
        return mUiLayout;
    }

    /**
     * 进入有网络请求的界面的统一的加载动画  以及 网络失败 网络 逻辑处理
     */
    protected void setUiLoadLayout(){

        if(null == mUiLayout) {
            mUiLayout = new UILoadLayout(this);
            mUiLayout.sethasTop(true);
            mUiLayout.setOnloadListener(new UILoadLayout.OnLoadListener() {
                @Override
                public void load() {
                    request();
                }
            });
        }
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-1, -1);
        mRootView.addView(mUiLayout, lp);
        if(null != mUiLayout){
            mUiLayout.resetUi();
        }
    }

    /**
     * 设置一些自定义的空页面
     */
    protected void setUiLoadSelfLayout(int layoutId){

        View view = LayoutInflater.from(this).inflate(layoutId,null);
        view.findViewById(R.id.m_iv_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIvLeftClick();
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-1, -1);
        mRootView.addView(view, lp);
    }

    protected void showNetError(){

        if(null != mUiLayout){
            mUiLayout.resetUi();
            mUiLayout.loadFailed();
        }
    }


    protected void request(){

    }

    protected boolean isHideSoft(){
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(isHideSoft()) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View view = getCurrentFocus();
                if (isHideInput(view, ev)) {
                    BaseUtils.hideSoftInput(view);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判定是否需要隐藏
     */
    private boolean isHideInput(View v,MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = new int[]{0,0};
            v.getLocationInWindow(l);
            int left = l[0];
            int top = l[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom) {
                return false;
            }
            return true;
        }
        return false;
    }



    @Override
    public void setContentView(View view) {
        super.setContentView(view);

        //当不使用头部时 可以自己在布局xml里面使用 Headerlayout
        if(!isUseHeader()) {
            mHeaderLayout = findId(R.id.header);
            if(null != mHeaderLayout)
                mHeaderLayout.setTarget(this);
        }
    }



    public <T extends View> T findViewId(View view, int id) {
        return (T) view.findViewById(id);
    }

    public <T extends View> T findId(int id) {
        return (T) findViewById(id);
    }



    abstract public int getLayoutId();

    public void setViewModel(){}

    public  <T extends BaseViewModel> T LViewModelProviders(Class<T> tClass){
        return ViewModelProviders.of(this).get(tClass);
    }

    public void observe(){

    }

    abstract public void initView(Bundle savedInstanceState);

    public void onIvLeftClick(){
        finish();
    }

    public void onTvRightClick(){

    }

    public void onTvCancelClick(){

    }

    //请求接口时
    protected LoadingDialog mLoadingDialog;

    public void show(){
        if(null == mLoadingDialog)
            mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.show();
    }


    public void dismiss(){
        if(null != mLoadingDialog)
            mLoadingDialog.dismiss();
    }






}
