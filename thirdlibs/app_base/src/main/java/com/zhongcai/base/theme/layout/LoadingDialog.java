package com.zhongcai.base.theme.layout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zhongcai.base.R;
import com.zhongcai.base.base.widget.BaseDialog;
import com.zhongcai.base.utils.BaseUtils;

/**
 * Created by zc3 on 2018/3/20.
 */

public class LoadingDialog extends BaseDialog {
    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_loading;
    }

    Animatable mFrameAnimation;
//    ObjectAnimator mFrameAnimation;
    ImageView mIvAnim;
    LinearLayout vLyRoot;
    TextView vTvPrompt;

    private void startAnim(){
//        if(null == mFrameAnimation) {
//            mFrameAnimation = ObjectAnimator.ofFloat(mIvAnim, "rotation", 0,
//                    30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330, 360);
//            mFrameAnimation.setDuration(1000);
//            mFrameAnimation.setInterpolator(new LinearInterpolator());
//            mFrameAnimation.setRepeatCount(-1);
//        }
//        mFrameAnimation.start();
        setAttr(0);
        mFrameAnimation =  ((Animatable)mIvAnim.getDrawable());
        mFrameAnimation.start();

    }


    private void stopLoadingAnimation() {
        if (mFrameAnimation != null) {
            if (mFrameAnimation.isRunning()) {
                mFrameAnimation.stop();
            }
            mFrameAnimation = null;
        }
    }

    public void onWindowFocusChanged(boolean hasFocus){
        startAnim();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopLoadingAnimation();
    }

    @Override
    public void init(Context context) {
        setCanceledOnTouchOutside(false);
        mIvAnim = findId(R.id.mIvAnim);
        vTvPrompt = findId(R.id.vTvPrompt);
        vLyRoot = findId(R.id.vLyRoot);
    }

    public LoadingDialog setContent(String text){
        if(null != vTvPrompt && !TextUtils.isEmpty(text)){
            BaseUtils.setVisible(vTvPrompt,1);
            vTvPrompt.setText(text);
        }

        return this;
    }

    public LoadingDialog setBgColor(){
        if(null != vLyRoot){
            vLyRoot.setBackgroundResource(R.drawable.shape_dialog_bg);
//            BaseUtils.setBgColor(vLyRoot,R.color.cl_0B23CE);
        }

        return this;
    }


}
