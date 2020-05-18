package com.example.huaweiot.homePage.housePage;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huaweiot.R;
import com.example.huaweiot.api.Beans.HomeData;
import com.example.huaweiot.base.BaseFragment;
import com.example.huaweiot.homePage.SmartHomeContract;
import com.example.huaweiot.homePage.SmartHomePresenter;
import com.example.huaweiot.utils.RxTimerUtil;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;

public class KitchenFragment extends BaseFragment implements SmartHomeContract.View {

    private static final String TAG = "KitchenFragment";

    @BindView(R.id.base_toolbar_title)
    TextView baseToolbarTitle;
    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.tv_info_temperature_value)
    TextView tvInfoTemperatureValue;
    @BindView(R.id.tv_info_humidity_value)
    TextView tvInfoHumidityValue;
    @BindView(R.id.tv_info_smoke_value)
    TextView tvInfoSmokeValue;
    @BindView(R.id.btn_house_device_edit)
    Button btnHouseDeviceEdit;
    @BindView(R.id.sw_kitchen_light)
    SwitchCompat swKitchenLight;
    @BindView(R.id.sw_kitchen_fan)
    SwitchCompat swKitchenFan;
    @BindView(R.id.sw_bathroom_light)
    SwitchCompat swBathroomLight;

    private boolean sw_kitchenLight_isOpen;
    private boolean sw_bathroomLight_isOpen;
    private boolean sw_bathroomfan_isOpen;

    private SmartHomeContract.Presenter mPresenter;
    private SmartHomePresenter presenter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_kitchen;
    }

    @Override
    protected void initData() {

        //toolbar
        setToolbar(baseToolbar, baseToolbarTitle);
        setToolbarTitle("厨房");
        setToolbarAlpha(255);

        swKitchenFan.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.i(TAG, "onCheckedChanged: swKitchenFan-->" + isChecked);
            if (isChecked) {
                btnHouseDeviceEdit.setText("关闭");
                sw_bathroomfan_isOpen = true;
                //开启风扇
                Map<String, String> paras = new HashMap<>();
                paras.put("fan", "O");
                mPresenter.postCommand("fanning", paras);
            } else {
                btnHouseDeviceEdit.setText("打开");
                sw_bathroomfan_isOpen = false;
                //关闭风扇
                Map<String, String> paras = new HashMap<>();
                paras.put("fan", "C");
                mPresenter.postCommand("fanning", paras);
            }

//            if (!sw_bathroomfan_isOpen){
//                sw_bathroomfan_isOpen = true;
//                //开启风扇
//                Map<String, String> paras = new HashMap<>();
//                paras.put("fan", "O");
//                mPresenter.postCommand("fanning", paras);
//            }else {
//                sw_bathroomfan_isOpen = false;
//                //关闭风扇
//                Map<String, String> paras = new HashMap<>();
//                paras.put("fan", "C");
//                mPresenter.postCommand("fanning", paras);
//            }

        });
        swKitchenLight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.i(TAG, "onCheckedChanged: swKitchenLight-->" + isChecked);
            if (isChecked) {
                sw_kitchenLight_isOpen = true;
                Map<String, String> paras = new HashMap<>();
                paras.put("open", "O");
                mPresenter.postCommand("lighting_kitchen", paras);
            } else {
                sw_kitchenLight_isOpen = false;
                Map<String, String> paras = new HashMap<>();
                paras.put("open", "C");
                mPresenter.postCommand("lighting_kitchen", paras);
            }

//            if (!sw_kitchenLight_isOpen){
//                sw_kitchenLight_isOpen = true;
//                Map<String, String> paras = new HashMap<>();
//                paras.put("open", "O");
//                mPresenter.postCommand("lighting_kitchen", paras);
//            }else {
//                sw_kitchenLight_isOpen = false;
//                Map<String, String> paras = new HashMap<>();
//                paras.put("open", "C");
//                mPresenter.postCommand("lighting_kitchen", paras);
//            }

        });

        swBathroomLight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.i(TAG, "onCheckedChanged: swBathroomLight-->" + isChecked);
            if (!sw_bathroomLight_isOpen) {
                sw_bathroomLight_isOpen = true;
                //开启浴室灯光
            } else {
                sw_bathroomLight_isOpen = false;
                //关闭浴室灯光
            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SmartHomePresenter(this);

        RxTimerUtil.interval(7000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                mPresenter.getData("kitchen");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getData("kitchen");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxTimerUtil.cancel();
    }

    @Override
    public void showData(HomeData homeData) {
        if (homeData != null) {
            tvInfoTemperatureValue.setText(homeData.getData().getTemperature() + " ℃");
            tvInfoHumidityValue.setText(homeData.getData().getHumidity() + " %RH");
            tvInfoSmokeValue.setText(homeData.getData().getSmoke() + " ml/m3");
        } else {
            tvInfoTemperatureValue.setText("0 ℃");
            tvInfoHumidityValue.setText("0 %RH");
            tvInfoSmokeValue.setText("0 ml/m3");
        }
    }

    @Override
    public void setPresenter(SmartHomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @OnClick(R.id.btn_house_device_edit)
    public void onViewClicked() {
        if (sw_bathroomfan_isOpen) {
            Toast.makeText(getActivity(), "关闭抽风机", Toast.LENGTH_SHORT).show();
            Map<String, String> paras = new HashMap<>();
            paras.put("fan", "C");
            mPresenter.postCommand("fanning", paras);
            btnHouseDeviceEdit.setText("打开");
            sw_bathroomfan_isOpen = false;
        } else {
            Toast.makeText(getActivity(), "打开抽风机", Toast.LENGTH_SHORT).show();
            //开启风扇
            Map<String, String> paras = new HashMap<>();
            paras.put("fan", "O");
            mPresenter.postCommand("fanning", paras);
            btnHouseDeviceEdit.setText("关闭");
            sw_bathroomfan_isOpen = true;
        }
    }
}
