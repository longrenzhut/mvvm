package com.zhongcai.common.widget.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by zhtuao on 2018/3/16.
 * 腾讯的X5 内核浏览器
 */

public class X5WebView extends WebView{
    public X5WebView(Context context) {
        this(context,null);
    }

    public X5WebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet,0);
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);

        init();
    }

    private void init(){

        WebSettings settings = getSettings();


        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        settings.setUseWideViewPort(true);
        settings.setSupportMultipleWindows(true);
        // settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        // settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setAppCacheMaxSize(Long.MAX_VALUE);
        // settings.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        settings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // settings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        settings.setDefaultTextEncodingName("UTF-8") ;
        settings.setLoadWithOverviewMode(true);

        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        settings.setSaveFormData(false);
        settings.setLoadsImagesAutomatically(true);

        settings.setBuiltInZoomControls(false); //不支持缩放
        settings.setSupportZoom(false);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.LOAD_NORMAL);
        }
        settings.setBlockNetworkImage(false);  //解除数据阻止

        /*settings.setSupportZoom(true);   //支持缩放
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(true);*/


        setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                if(null != listener)
                    listener.OnLoadUrl(s);
                return true;
            }
        });
    }

    OnLoadUrlListener listener;

    public void setOnLoadUrlListener(OnLoadUrlListener listener){
        this.listener = listener;
    }

    public interface OnLoadUrlListener{
        void OnLoadUrl(String s);
    }

    public float oldY;
    public float oldX;
    public float newY;
    public float newX;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        getParent().getParent().requestDisallowInterceptTouchEvent(true);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                newX = ev.getX();
                newY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //手指滑动同时判断滑动方向，一旦滑动方向大于+-60便调用
                //getParent().getParent().requestDisallowInterceptTouchEvent(false);
                //将滑动事件交给RecyclerView来处理
                oldX = newX;
                oldY = newY;
                newX = ev.getX();
                newY = ev.getY();
                float moveX = Math.abs(oldX - newX);
                float moveY = Math.abs(oldY - newY);
                //moveX * 1.73 < moveY  ,判断左右滑动范围为+-60度
                if (moveX * 1.73 < moveY) {
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}
