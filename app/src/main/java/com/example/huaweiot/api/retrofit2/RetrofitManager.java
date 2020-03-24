package com.example.huaweiot.api.retrofit2;

import android.util.Log;

import com.example.huaweiot.base.BaseApplication;
import com.example.huaweiot.utils.Constant;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static final String TAG = "RetrofitManager";
    private static final long TIMEOUT = 30;//设置超时时间

    private static OkHttpClient httpClient = null;
    private static RetrofitService retrofitService = null;


    private static void initRetrofitService() {
        httpClient = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();

        retrofitService = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()
                .create(RetrofitService.class);
    }

    //获取RetrofitApi实例
    public static RetrofitService getInstance() {
        try {
            initRetrofitService();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "getInstance: verifyCertificate failed message:" + e.getMessage());
        }

        return retrofitService;
    }
}
