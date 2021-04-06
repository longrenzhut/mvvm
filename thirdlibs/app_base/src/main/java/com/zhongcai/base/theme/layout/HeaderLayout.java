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
import com.zhongcai.base.rxbinding.RxClick;
import com.zhongcai.base.utils.BaseUtils;
import com.zhongcai.base.utils.DensityUtils;

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

    ImageView vIvLeft;
    TextView vTvTitle;
    TextView vTvRight;
    ImageView vIvRight;
    TextView vTvLeft;
    View vline;

    private void init(Context context){
        setId(R.id.header);
        setBgColor(R.color.white);
        LayoutInflater.from(context).inflate(R.layout.layout_header,this,true);

        vIvLeft = findViewById(R.id.vIvLeft);
        vTvLeft = findViewById(R.id.vTvLeft);
        vTvTitle = findViewById(R.id.vTvTitle);
        vTvRight = findViewById(R.id.vTvRight);
        vIvRight = findViewById(R.id.vIvRight);
        vline = findViewById(R.id.vline);
    }


    public void setBgColor(int color){
        setBackgroundColor(BaseUtils.getColor(color));
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
        vTvTitle.setText(title);
    }

    public void showline(){
        BaseUtils.setVisible(vline,1);
    }

    public void setIvTitle(String title){
        BaseUtils.setVisible(vIvLeft,1);
        vTvTitle.setText(title);
        vIvLeft.setOnClickListener(this);
    }

    public String getTitle(){
        return vTvTitle.getText().toString();
    }

    public void hideIvRight(){
        BaseUtils.setVisible(vIvRight,-1);
    }

    public void showIvRight(){
        BaseUtils.setVisible(vIvRight,1);
    }

    public void hideIvLeft(){
        BaseUtils.setVisible(vIvLeft,-1);
    }

    public void showIvLeft(){
        BaseUtils.setVisible(vIvLeft,1);
    }

    public void hideTvLeft(){
        BaseUtils.setVisible(vTvLeft,-1);
    }

    public void setTvLeft(String text){
        vTvLeft.setText(text);
        vTvLeft.setOnClickListener(this);
        BaseUtils.setVisible(vTvLeft,1);
    }

    public void setIvLeft(int drawable){
        BaseUtils.setVisible(vIvLeft,1);
        vIvLeft.setImageResource(drawable);
        vIvLeft.setOnClickListener(this);
    }


    public void setTvRight(String text){
        vTvRight.setText(text);
        BaseUtils.setVisible(vTvRight,1);

        vTvRight.setOnClickListener(this);
    }

    public void hideTvRight(){
        BaseUtils.setVisible(vTvRight,-1);
    }

    public void setIvRight(int drawable){
        vIvRight.setImageResource(drawable);
    }



    public void setIvRight(int drawable,int width,int height){
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)vIvRight.getLayoutParams();
        lp.width = DensityUtils.dp2px(width);
        lp.height = DensityUtils.dp2px(height);
        vIvRight.setLayoutParams(lp);
        vIvRight.setImageResource(drawable);

        BaseUtils.setVisible(vIvRight,1);
        vIvRight.setOnClickListener(this);
    }


    public void setIvTitleTv(String title,String rightText){
        vTvTitle.setText(title);
        vTvRight.setText(rightText);
        BaseUtils.setVisible(vIvLeft,1);
        BaseUtils.setVisible(vTvRight,1);
        vIvLeft.setOnClickListener(this);
        vTvRight.setOnClickListener(this);
    }


    public void setTvTitle(String title,String leftText){
        vTvTitle.setText(title);
        vTvLeft.setText(leftText);
        BaseUtils.setVisible(vTvLeft,1);
        vTvLeft.setOnClickListener(this);
    }



    public void setIvTitleIv(String title){
        vTvTitle.setText(title);
        BaseUtils.setVisible(vIvLeft,1);
        BaseUtils.setVisible(vIvRight,1);
        vIvLeft.setOnClickListener(this);
        vIvRight.setOnClickListener(this);
    }



    public void setTitleIv(String title){
        vTvTitle.setText(title);
        BaseUtils.setVisible(vIvRight,1);
        vIvRight.setOnClickListener(this);
    }


    public void setTitleTv(String title,String rightText){
        vTvTitle.setText(title);
        vTvRight.setText(rightText);
        BaseUtils.setVisible(vTvRight,1);
        vTvRight.setOnClickListener(this);
    }

    public void setTvRightColor(int color){
        BaseUtils.setTvColor(vTvRight,color);
    }


    private long mLastClickTime = 0L;
    long TIME_INTERVAL = 500L;

    @Override
    public void onClick(View v) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastClickTime > TIME_INTERVAL) {
            mLastClickTime = nowTime;
            viewClick(v);
        }
    }

    private void viewClick(View v){

        if(v.getId() == R.id.vIvLeft){
            if(null == fragment){
                activity.onIvLeftClick();
            }
            else{
                fragment.onIvLeftClick();
            }
        }
        else if(v.getId() == R.id.vTvRight){
            if(null == fragment){
                activity.onTvRightClick();
            }
            else{
                fragment.onTvRightClick();
            }
        }
        else if(v.getId() == R.id.vTvLeft){
            if(null == fragment){
                activity.onTvLeftClick();
            }
            else{
                fragment.onTvLeftClick();
            }
        }
        else if(v.getId() == R.id.vIvRight){
            if(null == fragment){
                activity.onIvRightClick();
            }
            else{
                fragment.onIvRightClick();
            }
        }
    }
}
