package com.zhongcai.base.base.application;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import java.lang.reflect.Method;

public abstract class AbsApplication extends Application {

    public AbsApplication() {
        try {
            Class<?> clazz = this.getClass().getClassLoader().loadClass("dalvik.system.VMRuntime");
            Method method = clazz.getMethod("getRuntime", new Class[]{});
            method.setAccessible(true);
            Object instance = method.invoke(clazz);
            method = instance.getClass().getMethod("setTargetHeapUtilization", new Class[]{float.class});
            method.setAccessible(true);
            method.invoke(instance, new Object[]{0.75f});
        } catch (Exception e) {

        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 解决Android 7.0 FileUriExposedException 异常
         */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        init();
    }

    abstract public void init();//初始化

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}
