package com.zhongcai.base.base.application;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zhongcai.base.Config;
import com.zhongcai.base.https.HttpProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class BaseApplication extends AbsApplication {

    public static BaseApplication app;


    private ActivityLifecycleCallbacks mActivityLifecycleCallbacks;

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

        /**
         * 解决Android 7.0 FileUriExposedException 异常
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        //初始化通用的网络请求
        HttpProvider.init();



        register();
    }

    private void register(){
        if(null == mActivityLifecycleCallbacks)
            mActivityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                    if(null == savedInstanceState)
                        addActivity(activity);
                }

                @Override
                public void onActivityStarted(@NonNull Activity activity) {

                }

                @Override
                public void onActivityResumed(@NonNull Activity activity) {
                    if(activity.getComponentName().getClassName().contains("MessageAct"))
                        Config.isPause = false;
                }

                @Override
                public void onActivityPaused(@NonNull Activity activity) {
                    if(activity.getComponentName().getClassName().contains("MessageAct"))
                        Config.isPause = true;
                }

                @Override
                public void onActivityStopped(@NonNull Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(@NonNull Activity activity) {
                    removeActivity(activity);
                }
            };

        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }


    @Override
    public void onTerminate() {
        if(null != mActivityLifecycleCallbacks)
            unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
        super.onTerminate();
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


    private List<Activity> list;

    public void removeActivity(Activity a){
        if(null == a)
            return;
        if(null == list)
            list = new ArrayList<>();
        list.remove(a);
    }

    public void addActivity(Activity a){
        if(null == a)
            return;
        if(null == list)
            list = new ArrayList<>();
        list.add(a);
    }

    public void finishActivity(){
        if(null == list)
            list = new ArrayList<>();
        for (Activity activity : list) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
