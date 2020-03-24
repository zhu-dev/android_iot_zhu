package com.example.huaweiot.homePage;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.huaweiot.api.ApiManager;
import com.example.huaweiot.api.Beans.CommandResponse;
import com.example.huaweiot.api.Beans.HomeData;
import com.example.huaweiot.api.retrofit2.RetrofitManager;
import com.example.huaweiot.utils.RequestBodyUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class SmartHomePresenter implements SmartHomeContract.Presenter {
    private static final String TAG = "SmartHomePresenter";
    private SmartHomeContract.View view; //持有view的引用

    public SmartHomePresenter(SmartHomeContract.View view) {
        this.view = view;
        view.setPresenter(this);
        //统一管理Disposable 对象
    }

    @SuppressLint("CheckResult")
    @Override
    public void getData() {
        RetrofitManager.getInstance().getHomeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(homeData -> {
                    Log.d(TAG, "accept: result:" + homeData.getMessage());
                    view.showData(homeData);
                }, throwable -> Log.d(TAG, "accept: error: " + throwable.getMessage()));
    }

    @SuppressLint("CheckResult")
    @Override
    public void postCommand(String method, Map<String, String> paras) {
        RetrofitManager.getInstance().postCommands(RequestBodyUtil.getRequestBody(method, paras))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CommandResponse>() {
                    @Override
                    public void accept(CommandResponse commandResponse) throws Exception {
                        switch (commandResponse.getStatus()) {
                            case 201:
                                Log.d(TAG, "status:" + commandResponse
                                        .getData().getStatus() + " method:" + commandResponse
                                        .getData().getMethod() + " paras:" + commandResponse
                                        .getData().getParas());
                                break;
                            case 200:
                                Log.d(TAG, "accept: data:" + commandResponse.getMessage());
                                break;
                            case 101:
                                Log.d(TAG, "accept: data:" + commandResponse.getMessage());
                                break;
                            case 102:
                                Log.d(TAG, "accept: data:" + commandResponse.getMessage());
                                break;
                            case 103:
                                Log.d(TAG, "accept: data:" + commandResponse.getMessage());
                                break;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, throwable.getMessage());
                    }
                });

    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
