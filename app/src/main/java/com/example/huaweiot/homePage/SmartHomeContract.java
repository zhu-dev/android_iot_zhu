package com.example.huaweiot.homePage;

import com.example.huaweiot.api.Beans.HomeData;
import com.example.huaweiot.base.BasePresenter;
import com.example.huaweiot.base.BaseView;

import java.util.HashMap;
import java.util.Map;

public interface SmartHomeContract {
    interface View extends BaseView<SmartHomeContract.Presenter> {
        void showData(HomeData homeData); //显示数据，接口需要分离出几个情况
    }

    interface Presenter extends BasePresenter {

        void getData(); //获取数据，是否分块数据,分开接口

        void postCommand(String method, Map<String, String> paras); //下发命令，是否要分开下发
    }
}
