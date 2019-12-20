package com.zhongcai.common.widget.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zc3 on 2018/5/21.
 */

public class AutoReclerView extends RecyclerView {

    public AutoReclerView(Context context) {
        super(context);
    }

    public AutoReclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoReclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int sizeWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = View.MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = View.MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;//自己测量的 宽度
        int height = 0;//自己测量的高度
        int lineWidth = 0;
        int lineHeight = 0;

        //获取子view的个数
        int childCount = getChildCount();
        for(int i = 0;i < childCount; i ++){
            View child = getChildAt(i);
            //测量子View的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //得到LayoutParams
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
            //子View占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            //子View占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            //换行时候
            if(lineWidth + childWidth > sizeWidth){
                //对比得到最大的宽度
                width = Math.max(width, lineWidth);
                //重置lineWidth
                lineWidth = childWidth;
                //记录行高
                height += lineHeight;
                lineHeight = childHeight;
            }else{//不换行情况
                //叠加行宽
                lineWidth += childWidth;
                //得到最大行高
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //处理最后一个子View的情况
            if(i == childCount -1){
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        //wrap_content
        setMeasuredDimension(modeWidth == View.MeasureSpec.EXACTLY ? sizeWidth : width,
                modeHeight == View.MeasureSpec.EXACTLY ? sizeHeight : height);

    }

}
