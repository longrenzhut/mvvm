package com.zhongcai.base.theme.layout;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhongcai.base.R;
import com.zhongcai.base.utils.BaseUtils;


/**
 * Created by zhutao on 2018/3/10.
 * 当进入界面有网络请求的时候用到
 * <p>
 * 统一的加载动画  以及 网络请求的界面展示
 */

public class UILoadLayout extends LinearLayout {


    RelativeLayout mPbloading;
    ImageView mIvAnim;
    TextView mTvLoadAgain;
    LinearLayout mllyFailed;
    ImageView mIvExit;
    RelativeLayout mrlyExit;
    StatusbarUIView mViewStatus;

    public UILoadLayout(Context context) {
        super(context);
        init(context);
    }

    public UILoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UILoadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }


    public void init(Context context) {

        LayoutInflater.from(context).inflate(R.layout.layout_ui_load,this,true);

        setId(R.id.uiloadlayout);
//        setBackgroundColor(CommonUtils.getColor(R.color.colorBgTheme));

        mPbloading = findViewById(R.id.pb_loading);
        mIvAnim = findViewById(R.id.m_iv_anim);
        mTvLoadAgain = findViewById(R.id.tv_load_again);
        mllyFailed = findViewById(R.id.lly_failed);
        mIvExit = findViewById(R.id.m_iv_exit);
        mrlyExit = findViewById(R.id.m_rly_exit);
        mViewStatus = findViewById(R.id.view_status);

        setOrientation(LinearLayout.VERTICAL);
//        setGravity(Gravity.CENTER);
        setClickable(true);

        mTvLoadAgain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseUtils.setVisible(mllyFailed,-1);
                BaseUtils.setVisible(mPbloading,1);
                startAnim();
                if(null != listener)
                    listener.load();
            }
        });

        startAnim();
    }

    /**
     * 请求接口失败展示
     */
    public void loadFailed(){
        BaseUtils.setVisible(mPbloading,-1);
        BaseUtils.setVisible(mllyFailed,1);
        stopLoadingAnimation();
    }


    public void sethasTop(boolean ishas){
        if(ishas){
            mIvExit.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity)getContext()).finish();
                }
            });
            BaseUtils.setVisible(mViewStatus,1);
            BaseUtils.setVisible(mrlyExit,1);
        }
        else{
            BaseUtils.setVisible(mrlyExit,-1);
            BaseUtils.setVisible(mViewStatus,-1);
        }
    }
//    ObjectAnimator mFrameAnimation;
    Animatable mFrameAnimation;



    private void startAnim(){
        if(mPbloading.getVisibility() == VISIBLE){
//            if(null == mFrameAnimation) {
//                mFrameAnimation = ObjectAnimator.ofFloat(mIvAnim, "rotation", 0,
//                        30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330, 360);
//                mFrameAnimation.setDuration(1000);
//                mFrameAnimation.setInterpolator(new LinearInterpolator());
//                mFrameAnimation.setRepeatCount(-1);
//            }
//            mFrameAnimation.start();
            mFrameAnimation =  ((Animatable)mIvAnim.getDrawable());
            mFrameAnimation.start();
        }
    }


    private void stopLoadingAnimation() {
        if (mFrameAnimation != null) {
            if (mFrameAnimation.isRunning()) {
                mFrameAnimation.stop();
            }
            mFrameAnimation = null;
        }
    }

    /**
     * 接口请求成功 隐藏
     */
    public void loadok(){
        stopLoadingAnimation();
        BaseUtils.setVisible(this,-1);
    }

    /**
     *
     */
    public void resetUi(){
        BaseUtils.setVisible(this,1);
        BaseUtils.setVisible(mPbloading,1);
        BaseUtils.setVisible(mllyFailed,-1);
        startAnim();
    }





    public interface OnLoadListener{
        void load();
    }

    OnLoadListener listener;
    /**
     * 网络加载失败，点击重新加载
     */
    public void setOnloadListener(OnLoadListener listener){
        this.listener = listener;
    }

}
