package com.zhongcai.base.theme.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zhongcai.base.R;
import com.zhongcai.base.utils.BaseUtils;

/**
 * Created by zc3 on 2018/10/31.
 */

public class StatusbarUIView  extends View {

    public StatusbarUIView(Context context) {
        super(context);
        init();
    }

    public StatusbarUIView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StatusbarUIView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setBackgroundColor(getResources().getColor(R.color.colorTheme));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int h = BaseUtils.getStatusBarh();

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        setMeasuredDimension(widthSize, h);
    }
}
