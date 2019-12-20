package com.zhongcai.common.widget.dialog;

import androidx.annotation.StyleRes;

import com.alibaba.android.vlayout.base.BaseViewHolder;
import com.zhongcai.base.base.widget.BaseDialogFra;

import org.jetbrains.annotations.NotNull;

public class CommonDialog extends BaseDialogFra {

    private  int layout;

    CommonDialog setLayout(@NotNull  int layout){
        this.layout = layout;
        return this;
    }


    @Override
    protected int getLayoutId() {
        return layout;
    }

    @Override
    protected void initView() {

        if(listener != null)
            listener.onBindViewHolder(new BaseViewHolder(rootView));
    }

    private ViewHoldListener listener;

    public void setAdapter(ViewHoldListener listener){
        this.listener = listener;
    }

    public interface ViewHoldListener{
        void onBindViewHolder(BaseViewHolder holder);
    }

    private float dimAmount = 0.5f;//灰度深浅
    private boolean showBottom;//是否底部显示
    private boolean outCancel = true;//是否点击外部取消
    @StyleRes
    private int animStyle;

    public CommonDialog setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    public CommonDialog setShowBottom(boolean showBottom) {
        this.showBottom = showBottom;
        return this;
    }

    public CommonDialog setOutCancel(boolean outCancel) {
        this.outCancel = outCancel;
        return this;
    }

    public CommonDialog setAnimStyle(@StyleRes int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

}
