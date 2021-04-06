package com.zhongcai.base.ui.widget.popwindow

import android.app.Activity
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow

/**
 * Created by zhutao on 2017/8/18.
 */
abstract class BasePopupWindow(val mActivity: Activity) : PopupWindow(){

    val view by lazy(LazyThreadSafetyMode.NONE) {
        if(getLayoutId() == 0) null
        else
        LayoutInflater.from(mActivity).inflate(getLayoutId(),null)
    }
    init {
        setClippingEnabled(false)
        width = getPopWidth()
        height = getPopHeight()
//        setAnimationStyle(R.style.popwindow_anim_style);
        isOutsideTouchable = true
        isFocusable = true
        val dw = ColorDrawable(0)
        setBackgroundDrawable(dw)

        if(view != null) {
            contentView = view
            initView()
        }
        setOnDismissListener { setAlpha(1f) }
    }

    fun <T : View> findId(id: Int): T {
        return contentView.findViewById(id)
    }


    abstract fun getPopWidth(): Int
    abstract fun getPopHeight(): Int

    abstract fun initView()

    abstract fun getLayoutId(): Int

     fun setAlpha(alpha: Float) {
        val lp = mActivity.window.attributes
        lp.alpha = alpha
        mActivity.window.attributes = lp
    }

    override fun showAsDropDown(anchor: View) {
        postDelayed()
        super.showAsDropDown(anchor)
    }

    override fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        postDelayed()
        super.showAtLocation(parent, gravity, x, y)
    }

     fun showAtLocation(parent: View) {
        postDelayed()
        super.showAtLocation(parent, Gravity.FILL, 0, 0)
    }


    fun showAsDropDownBg(parent: View,  x: Int, y: Int) {
        postDelayed()
        super.showAsDropDown(parent, x, y)
    }

    private fun postDelayed() {
        Handler().postDelayed({ setAlpha(getAlpha()) }, 100)
    }

    fun setDefalueAnim(){
        setAnimationStyle(PopupWindow.INPUT_METHOD_NOT_NEEDED)
    }

    //全屏的时候调用
    fun showAsViewDown(parent: View){
        set(parent)
        super.showAsDropDown(parent, 0, 0)
    }

    protected fun set(anchor: View){
        if (Build.VERSION.SDK_INT >= 24) {
            val rect = Rect()
            anchor.getGlobalVisibleRect(rect)
            val h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom
            height = h
        }
    }



   open fun getAlpha(): Float = 0.7f

}