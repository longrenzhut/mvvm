package com.zhongcai.common.widget.webview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;


import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zhongcai.common.ui.activity.ImageActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhutao on 2019/4/28.
 * 加载文本H5
 */

public class TextWebView extends WebView {
    public TextWebView(Context context) {
        this(context,null);
    }

    public TextWebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet,0);
    }

    public TextWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);

        init();
    }

    private void init(){
        WebSettings webSettings = getSettings();

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本 ☆☆
        webSettings.setSupportZoom(false);//不支持缩放功能
        setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);

                /**
                 * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
                 **/

              loadUrl("javascript:(function(){"
                        + "var objs = document.getElementsByTagName('img'); "
                        + "for(var i=0;i<objs.length;i++)  "
                        + "{"
                        + "var img = objs[i];   "
                        + "    img.style.maxWidth = '100%'; img.style.height = 'auto';  "
                        + "}"
                        + "})()");


                // html加载完成之后，添加监听图片的点击js函数
              loadUrl("javascript:(function(){"
                        + "var objs = document.getElementsByTagName(\"img\"); "
                        + " var array=new Array(); "
                        +" for(var j=0;j<objs.length;j++){ array[j]=objs[j].src; }"
                        + "for(var i=0;i<objs.length;i++)  "
                        + "{"
                        + "    objs[i].onclick=function()  "
                        + "    {  "
                        + "        window.imagelistner.openImage(this.src,array);  "
                        + "    }  "
                        + "}"
                        + "})()");


            }
        });


    }


    public void loadData(Activity activity, String url){
        addJavascriptInterface(new JavaScriptInterface(activity,this),"imagelistner");
        loadDataWithBaseURL(null, url, "text/html", "UTF-8", null);
    }

    public static class JavaScriptInterface {

        private Activity activity;
        private List<String> list_imgs;
        private View target;
        private int index = 0;

        public JavaScriptInterface(Activity activity, View target) {
            this.activity = activity;
            list_imgs = new ArrayList<>();
            this.target = target;
        }

        Long exitTime = 0l;

        //点击图片回调方法
        //必须添加注解,否则无法响应
        @JavascriptInterface
        public void openImage(String img, String[] imgs) {

            if (System.currentTimeMillis() - exitTime > 1000) {
                exitTime = System.currentTimeMillis();
            } else {

                return;
            }


            list_imgs.clear();

            for (int i = 0; i < imgs.length; i++) {
                if (imgs[i].equals(img)){
                    index = i;
                }
                list_imgs.add(imgs[i]);
            }

            ImageActivity.startImageActivity(activity,target,list_imgs,index);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        getParent().getParent().requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(ev);
    }
}
