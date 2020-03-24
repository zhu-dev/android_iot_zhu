package com.example.huaweiot.base;

import android.app.Application;
import android.content.Context;

import com.tencent.mmkv.MMKV;

public class BaseApplication extends Application {
    private static Context context;//全局的Context

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();//获得应用程序级别的Context
        MMKV.initialize(this);//初始化MMKV
    }

    /**
     * 获取应用程序的Context
     *
     * @return context
     */
    public static Context getContext() {
        return context;
    }
}
