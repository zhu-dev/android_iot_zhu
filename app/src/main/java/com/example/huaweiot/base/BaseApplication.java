package com.example.huaweiot.base;

import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.mmkv.MMKV;

public class BaseApplication extends Application {
    private static Context context;//全局的Context

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();//获得应用程序级别的Context
        MMKV.initialize(this);//初始化MMKV
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5e79e75d"); //初始化科大讯飞SDK
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
