package com.zhongcai.base.base.application;

import android.content.res.Configuration;
import android.content.res.Resources;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zhongcai.base.Config;
import com.zhongcai.base.https.HttpProvider;

import java.io.IOException;
import java.io.InputStream;


public class BaseApplication extends AbsApplication {

    public static BaseApplication app;

    @Override
    public void init() {
        app = this;
        if (Config.DEVELOP || Config.TEST) {
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        ARouter.init(this);


        //初始化通用的网络请求
        HttpProvider.init();

    }


    public InputStream getAssentsInputStream(String path){
        try {
            return getAssets().open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1f) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

}
