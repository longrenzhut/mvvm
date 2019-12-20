package com.zhongcai.base.theme.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhongcai.base.R;
import com.zhongcai.base.base.activity.AbsActivity;
import com.zhongcai.base.base.fragment.AbsFragment;
import com.zhongcai.base.utils.BaseUtils;

/**
 * Created by zc3 on 2018/10/19.
 */

public class HeaderLayout extends RelativeLayout implements View.OnClickListener {
    public HeaderLayout(Context context) {
        super(context);
        init(context);
    }

    public HeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    ImageView mIvLeft;
    TextView mTvTitle;
    TextView mTvRight;
    TextView mTvCancel;
    View line;

    private void init(Context context){
        setId(R.id.header);
        setBackgroundColor(getResources().getColor(R.color.white));
        LayoutInflater.from(context).inflate(R.layout.layout_header,this,true);

        mIvLeft = findViewById(R.id.iv_exit);
        mTvTitle = findViewById(R.id.tv_title);
        mTvRight = findViewById(R.id.tv_right);
        mTvCancel = findViewById(R.id.tv_cancel);
        line = findViewById(R.id.line);
    }

    AbsActivity activity;
    AbsFragment fragment;

    public void setTarget(AbsActivity act){
        this.activity = act;
    }

    public void setTarget(AbsFragment fragment){
        this.fragment = fragment;
    }


    public void setTitle(String title){
        mTvTitle.setText(title);
    }

    public void hideline(){
        BaseUtils.setVisible(line,-1);
    }

    public void setIvTitle(String title){
        BaseUtils.setVisible(mIvLeft,1);
        mTvTitle.setText(title);
        mIvLeft.setOnClickListener(this);
    }

    public void setIvLeft(int drawable){
        BaseUtils.setVisible(mIvLeft,1);
        mIvLeft.setImageResource(drawable);
        mIvLeft.setOnClickListener(this);
    }

    public void setTvCancel(String text){
        BaseUtils.setVisible(mTvCancel,1);
        mTvCancel.setText(text);
        mTvCancel.setOnClickListener(this);
    }

    public void setTvRightSelected(boolean selected){
        mTvRight.setSelected(selected);
    }

    public boolean isSelect(){
        return mTvRight.isSelected();
    }



    public void setIvTitleTv(String title,String rightText){
        BaseUtils.setVisible(mIvLeft,1);
        BaseUtils.setVisible(mTvRight,1);
        mTvTitle.setText(title);
        mTvRight.setText(rightText);
        mIvLeft.setOnClickListener(this);
        mTvRight.setOnClickListener(this);
    }

    public void setRightTv(String rightText){
        BaseUtils.setVisible(mTvRight,1);
        mTvRight.setText(rightText);
        mTvRight.setOnClickListener(this);
    }


    public String getTvRightText(){
        return mTvRight.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.iv_exit){
            if(null == fragment){
                activity.onIvLeftClick();
            }
            else{
                fragment.onIvLeftClick();
            }
        }
        else if(v.getId() == R.id.tv_right){
            if(null == fragment){
                activity.onTvRightClick();
            }
            else{
                fragment.onTvRightClick();
            }
        }
        else if(v.getId() == R.id.tv_cancel){
            if(null == fragment){
                activity.onTvCancelClick();
            }
            else{
                fragment.onTvCancelClick();
            }
        }
    }
}
