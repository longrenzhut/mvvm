package com.zhongcai.common.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.zhongcai.base.utils.BaseUtils;

/**
 * Created by Administrator on 2017/12/7.
 */

public class AndroidBug5497Workaround {

    // For more information, see https://issuetracker.google.com/issues/36911528
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

    public static void assistActivity (Activity activity) {
        new AndroidBug5497Workaround(activity);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private RelativeLayout.LayoutParams frameLayoutParams;

    private ViewTreeObserver.OnGlobalLayoutListener glod;

    public AndroidBug5497Workaround(Activity activity) {
        FrameLayout content = activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver()
                .addOnGlobalLayoutListener(glod =
                        new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContentMy();
            }
        });
        frameLayoutParams = (RelativeLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    public AndroidBug5497Workaround(View view) {
        mChildOfContent = view;
        mChildOfContent.getViewTreeObserver()
                .addOnGlobalLayoutListener(glod =
                        new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContentMy();
            }
        });
        frameLayoutParams = (RelativeLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    int oldHeight = 0;
    int newHeight = 0;
    int status = 0;

    private int curHeight = 0;

    private void possiblyResizeChildOfContentMy(){
        //第一次测量
        if(oldHeight == 0) {
            status = BaseUtils.getStatusBarh();
            oldHeight = computeUsableHeight() + status;
            newHeight = oldHeight;
        }
        else{
            newHeight = computeUsableHeight() + status;
        }

        int heightDifference = oldHeight - newHeight;
        if(curHeight == heightDifference)
            return;
        //虚拟键盘关闭
        if (heightDifference > (oldHeight/4)) {
            // keyboard probably just became visible
            frameLayoutParams.height = oldHeight - heightDifference;
        } else {
            // keyboard probably just became hidden
            frameLayoutParams.height = oldHeight;
        }
        if(null != listener)
            listener.openKeyBord(heightDifference > (oldHeight/4),frameLayoutParams.height);
        mChildOfContent.requestLayout();

        curHeight = heightDifference;
    }

    private OnKeyBordListener listener;

    public void setOnKeyBordListener(OnKeyBordListener listener){
        this.listener = listener;
    }

    interface OnKeyBordListener{
        void openKeyBord(boolean isOpen, int height);
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard/4)) {
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard;
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }

//    private int computeUsableHeight() {
//        Rect r = new Rect();
//        mChildOfContent.getWindowVisibleDisplayFrame(r);
//        if(r.top==0){
//            r.top= statusBarH;//状态栏目的高度
//        }
//        return (r.bottom - r.top);
//    }

    public void destory(){
        mChildOfContent.getViewTreeObserver()
                .removeOnGlobalLayoutListener(glod);
    }
}
