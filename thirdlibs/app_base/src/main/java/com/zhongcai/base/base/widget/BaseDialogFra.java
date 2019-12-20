package com.zhongcai.base.base.widget;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.zhongcai.base.R;


/**
 * Created by zc3 on 2018/8/22.
 */

abstract public class BaseDialogFra extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialog_fragment);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialog_enter_bottom_out_bottom);
    }


    protected View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(true);
        rootView = inflater.inflate(getLayoutId(), container, false);


        final Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
        initView();
        return rootView;
    }


    public <T extends View> T findVId(int id) {
        return (T) rootView.findViewById(id);
    }


    protected abstract int getLayoutId();
    protected abstract void initView();


    public void show(FragmentManager manager, String tag) {
        if(!isAdded())
            super.show(manager, tag);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        if(null != lisenter)
            lisenter.OnDismiss();
        super.onDismiss(dialog);
    }

    private OnDismissLisenter lisenter;

    public BaseDialogFra setOnDismissLisenter(OnDismissLisenter lisenter){
        this.lisenter = lisenter;

        return this;
    }

    public interface OnDismissLisenter{
        void OnDismiss();
    }
}
