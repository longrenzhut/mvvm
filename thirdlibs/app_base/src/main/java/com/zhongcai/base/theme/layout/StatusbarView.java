package com.zhongcai.base.theme.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zhongcai.base.R;
import com.zhongcai.base.utils.BaseUtils;

/**
 * Created by Administrator on 2018/3/9.
 * 获取状态栏的高度
 */

public class StatusbarView extends View{

    public StatusbarView(Context context) {
        super(context);
        init();
    }

    public StatusbarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StatusbarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setId(R.id.statusbar);
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
