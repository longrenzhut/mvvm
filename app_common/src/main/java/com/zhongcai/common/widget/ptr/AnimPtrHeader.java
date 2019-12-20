package com.zhongcai.common.widget.ptr;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import com.zhongcai.common.R;
/**
 * Created by zc3 on 2018/5/4.
 */

public class AnimPtrHeader extends LinearLayout implements PtrUIHandler {
    public AnimPtrHeader(Context context) {
        super(context);
        init(context);
    }

    public AnimPtrHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AnimPtrHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private ImageView mIvIcon;

    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_anim_header,this,true);
        mIvIcon = findViewById(R.id.mIvAnim);
    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {
        mIvIcon.setImageResource(R.drawable.icon_0);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mIvIcon.setImageResource(R.drawable.icon_0);
    }

    Animatable mFrameAnimation;

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mIvIcon.setImageResource(R.drawable.anim_loading);
        mFrameAnimation =  ((Animatable)mIvIcon.getDrawable());
        mFrameAnimation.start();
//        if(null == mFrameAnimation) {
//            mFrameAnimation = ObjectAnimator.ofFloat(mIvIcon, "rotation", 0,
//                    30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330, 360);
//            mFrameAnimation.setDuration(1000);
//            mFrameAnimation.setInterpolator(new LinearInterpolator());
//            mFrameAnimation.setRepeatCount(-1);
//        }
//        mFrameAnimation.start();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        stopLoadingAnimation();
        mIvIcon.setImageResource(R.drawable.icon_0);
    }

    private void stopLoadingAnimation() {
        if (mFrameAnimation != null) {
            if (mFrameAnimation.isRunning()) {
                mFrameAnimation.stop();
            }
            mFrameAnimation = null;
        }
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

    }
}
