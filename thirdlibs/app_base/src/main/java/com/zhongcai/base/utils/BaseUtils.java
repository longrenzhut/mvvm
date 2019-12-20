package com.zhongcai.base.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zhongcai.base.base.application.BaseApplication;
import com.zhongcai.base.theme.statusbar.StatusBarKitkatImpl;

import java.util.List;


/**
 * Created by zhutao on 2018/3/7.
 */

public class BaseUtils {


    public static Resources getResource() {
        return BaseApplication.app.getResources();
    }

    public static int getDimen(int dimen) {
        return getResource().getDimensionPixelOffset(dimen);
    }

    public static int getColor(int color) {
        return getResource().getColor(color);
    }

    public static String getString(int id) {
        return getResource().getString(id);
    }

    public static Drawable getDrawable(int color) {
        return getResource().getDrawable(color);
    }



    public static void setTvColor(TextView tv, int color) {
        tv.setTextColor(getColor(color));
    }


    public static void setTvSize(TextView tv, int dimen) {
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,getDimen(dimen));
    }


    public static void showSoftinput(EditText et) {
        if (null == et)
            return;
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_FORCED);
    }


    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(View et) {
        if (null == et)
            return;
//        et.setFocusable(false);
        InputMethodManager manager = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(et.getWindowToken(),0);
    }




    public static String[] getStringArray(int id){

        return  getResource().getStringArray(id);
    }



    public static <T> boolean listisNotEmpty(List<T> list){
        if(null == list || list.size() == 0)
            return false;
        return  true;
    }

    public static  boolean arrayisNotEmpty(String[] strs){
        if(null == strs || strs.length == 0)
            return false;
        return  true;
    }



    /**
     * @param view    1 显示 占位 0 不显示 占位  1 不显示 不占位
     * @param visible
     */
    public static void setVisible(View view, int visible) {
        if (null == view)
            return;
        switch (visible) {
            case 1:
                view.setVisibility(View.VISIBLE);
                break;
            case 0:
                view.setVisibility(View.INVISIBLE);
                break;
            case -1:
                view.setVisibility(View.GONE);
                break;
        }
    }


    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarh() {

        return StatusBarKitkatImpl.getStatusBarHeight(BaseApplication.app);
    }


}
