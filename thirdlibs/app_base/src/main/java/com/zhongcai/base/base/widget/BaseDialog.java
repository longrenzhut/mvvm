package com.zhongcai.base.base.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.zhongcai.base.R;
import com.zhongcai.base.theme.statusbar.StatusBarCompat;
import com.zhongcai.base.utils.BaseUtils;

/**
 * Created by zhutap on 2018/3/19.
 */

abstract public class BaseDialog extends Dialog{


    public BaseDialog(@NonNull Context context) {
        this(context, R.style.CommonDialogTheme);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setCanceledOnTouchOutside(true);
        final Window window = getWindow();

        if(isBottom()) {
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.BOTTOM;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);
        }
        else{
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);
        }
        if(getLayoutId() != 0)
            setContentView(getLayoutId());
        setStatusBar(R.color.transparent);
        init(context);
    }


    protected boolean isBottom(){

        return false;
    }

    abstract public int getLayoutId();
    abstract public void init(Context context);

    public <T extends View> T findId(int id) {
        return (T) findViewById( id);
    }


    protected void setStatusBar(int color){
        StatusBarCompat.setStatusBarColor(getWindow(),
                BaseUtils.getColor(color), true, true);
    }


    /**
     * 设置透明度
     */
    public void setAttr(float alph) {
        WindowManager.LayoutParams windowLP = getWindow().getAttributes();
        windowLP.dimAmount = alph;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(windowLP);
    }


}
