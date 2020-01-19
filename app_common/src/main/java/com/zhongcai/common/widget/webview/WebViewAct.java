package com.zhongcai.common.widget.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.zhongcai.common.R;


/**
 * Created by zc3 on 2018/4/4.
 */

public class WebViewAct extends AbsActivity {

    protected X5WebView mWebview;
    ProgressBar mProgressBar;

    public static void startAct(Context ctx, WebParam param){
        Intent intent = new Intent(ctx, WebViewAct.class);
        intent.putExtra("model",param);
        ctx.startActivity(intent);

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_wv;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        mWebview = findViewById(R.id.webview);
        mProgressBar = findViewById(R.id.m_progress_bar);
        WebParam param = getIntent().getParcelableExtra("model");
        mHeaderLayout.setIvTitle(param.getTitle());


        mWebview.loadUrl(param.getUrl());
//        mWebview.loadDataWithBaseURL(param.getUrl(),"","","","");

        mWebview.setWebChromeClient(new MyWebClient()); //对js交互的对话框、title以及页面加载进度条的管理
//        mWebview.setWebViewClient(new HelloWebViewClient(this)); //对webview页面加载管理、如url重定向
    }


    public void loadHtml(String text){
        String strUTF8 = htmldecode(text);
        String head = "<html><head><style></style></head>" + strUTF8 + "</html>";

        mWebview.loadDataWithBaseURL(null, head, "text/html", "UTF-8",null);
    }

    public String htmldecode(String s) {
//        String str = s.replace("&lt;","<")
//                .replace("&gt;",">")
//                .replace("&amp;","&")
//                .replace("&quot;","\"")
//                .replace("<br/>","");
        return s;
    }


    public void onBackPressed() {
        if (mWebview.canGoBack()) {
            mWebview.goBack(); // webview返回上一层
        } else {
            finish();
        }
    }


    public class MyWebClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //mProgressBar.setProgress(newProgress);
            if (null != mProgressBar) {
                if (mProgressBar.getVisibility() != View.VISIBLE && newProgress != 100) {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                mProgressBar.setProgress(newProgress);

                if (newProgress >= 100) {
                    if (null != mProgressBar)
                        mProgressBar.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            //txtTitle.setText("ReceivedTitle:" +title);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebview != null) {
//            mWebview.clearCache(true); //清空缓存
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ((ViewGroup) mWebview.getParent()).removeView(mWebview);
                mWebview.removeAllViews();
                mWebview.destroy();
            } else {
                mWebview.removeAllViews();
                mWebview.destroy();
                ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            }
            mWebview = null;
        }

    }

}
