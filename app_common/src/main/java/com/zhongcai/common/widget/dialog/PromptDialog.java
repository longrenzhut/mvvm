package com.zhongcai.common.widget.dialog;

import android.widget.TextView;

import com.alibaba.android.vlayout.base.BaseViewHolder;
import com.zhongcai.base.base.widget.BaseDialogFra;
import com.zhongcai.base.utils.BaseUtils;
import com.zhongcai.common.R;


/**
 * Created by zhutao on 2018/3/26.
 * 通用的弹出框
 */

public class PromptDialog extends CommonDialog {
//    R.style.Anim_right_to_left

    TextView mTvPrompt;
    TextView mTvOk;
    TextView mTvCancel;
    TextView mTvTitle;


    @Override
    public int getLayoutId() {
        return R.layout.dialog_prompt;
    }

    @Override
    protected void initView() {
        setAdapter(new ViewHoldListener() {
            @Override
            public void onBindViewHolder(BaseViewHolder holder) {
                mTvPrompt = holder.getView(R.id.mTvPrompt);
                mTvCancel = holder.getView(R.id.mTvCancel);
                mTvOk = holder.getView(R.id.mTvOk);
                mTvTitle = holder.getView(R.id.mTvTitle);

            }
        });

        super.initView();
    }


    public PromptDialog isSingle(){
        BaseUtils.setVisible(mTvCancel,-1);
        return this;
    }


    public PromptDialog setTitle(String context){
        BaseUtils.setVisible(mTvTitle,1);
        mTvTitle.setText(context);
        return this;
    }

    public PromptDialog setContent(String context){
        mTvPrompt.setText(context);
        return this;
    }

    public PromptDialog setContentColor(int color){
        BaseUtils.setTvColor(mTvPrompt,color);
        return this;
    }

    public PromptDialog setContentSize(int size){
        BaseUtils.setTvSize(mTvPrompt,size);
        return this;
    }

    public PromptDialog setPCancelable(boolean c){
        setCancelable(c);
        return this;
    }


    public PromptDialog setLeft(String context){
        mTvCancel.setText(context);
        return this;
    }

    public PromptDialog setRight(String context){
        if(null != mTvTitle)
            mTvTitle.setText(context);
        return this;
    }

    OnLeftClickListener leftlistener;
    public PromptDialog setLeftListener(OnLeftClickListener listener){
        this.leftlistener = listener;
        return this;
    }

    OnRightClickListener rightlistener;

    public PromptDialog setRightListener(OnRightClickListener listener){
        this.rightlistener = listener;
        return this;
    }

    public interface OnLeftClickListener{
        void OnClick();
    }

    public interface OnRightClickListener{
        void OnClick();
    }
}
