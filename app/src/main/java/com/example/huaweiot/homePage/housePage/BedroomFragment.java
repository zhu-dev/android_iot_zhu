package com.example.huaweiot.homePage.housePage;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.example.huaweiot.R;
import com.example.huaweiot.api.Beans.HomeData;
import com.example.huaweiot.base.BaseFragment;
import com.example.huaweiot.homePage.SmartHomeContract;
import com.example.huaweiot.homePage.SmartHomePresenter;
import com.example.huaweiot.utils.RxTimerUtil;
import com.example.huaweiot.view.CustomProgress;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class BedroomFragment extends BaseFragment implements SmartHomeContract.View {

    private static final String TAG = "BedroomFragment";

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
    @BindView(R.id.sw_bathroom_light)
    SwitchCompat swBathroomLight;
    @BindView(R.id.progress_bedroom_light)
    CustomProgress progressBedroomLight;
    @BindView(R.id.sw_bedroom_curtain)
    SwitchCompat swBedroomCurtain;
    @BindView(R.id.progress_bedroom_curtain)
    CustomProgress progressBedroomCurtain;

    private boolean sw_bedroomLight_isOpen = false;
    private boolean sw_bedroomCurtain_isOpen = false;

    private SmartHomeContract.Presenter mPresenter;
    private SmartHomePresenter presenter ;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_bedroom;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        //toolbar
        setToolbar(baseToolbar, baseToolbarTitle);
        setToolbarTitle("卧室");
        setToolbarAlpha(255);

        swBathroomLight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.i(TAG, "onCheckedChanged: swBathroomLight ---> " + isChecked);
            if (!sw_bedroomLight_isOpen){
                Toast.makeText(getActivity(), "开启卧室灯光", Toast.LENGTH_SHORT).show();
                sw_bedroomLight_isOpen = true;
                Map<String, String> paras = new HashMap<>();
                paras.put("open", "O");
                mPresenter.postCommand("lighting_bedroom", paras);
            }else {
                Toast.makeText(getActivity(), "关闭卧室灯光", Toast.LENGTH_SHORT).show();
                sw_bedroomLight_isOpen = false;
                Map<String, String> paras = new HashMap<>();
                paras.put("open", "C");
                mPresenter.postCommand("lighting_bedroom", paras);
            }
        });
        swBedroomCurtain.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.i(TAG, "onCheckedChanged: swBedroomCurtain ---> " + isChecked);
            if (!sw_bedroomCurtain_isOpen){
                Toast.makeText(getActivity(), "开启卧室窗帘", Toast.LENGTH_SHORT).show();
                sw_bedroomCurtain_isOpen = true;
                Map<String, String> paras = new HashMap<>();
                paras.put("open", "O");
                mPresenter.postCommand("curtain", paras);
            }else {
                Toast.makeText(getActivity(), "关闭卧室窗帘", Toast.LENGTH_SHORT).show();
                sw_bedroomCurtain_isOpen = false;
                Map<String, String> paras = new HashMap<>();
                paras.put("open", "C");
                mPresenter.postCommand("curtain", paras);
            }
        });

        progressBedroomLight.setVisibility(View.VISIBLE);
        progressBedroomLight.setOnProgressListener(progress -> {
            Log.i(TAG, "onSelect: progressBedroomLight  progress--> " + progress);
            Toast.makeText(getActivity(), "卧室灯光亮度：" + progress, Toast.LENGTH_SHORT).show();
        });
        progressBedroomCurtain.setVisibility(View.VISIBLE);
        progressBedroomCurtain.setOnProgressListener(progress -> {
            Log.i(TAG, "onSelect: progressBedroomCurtain  progress--> " + progress);
            Toast.makeText(getActivity(), "卧室窗帘开启程度：" + progress, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SmartHomePresenter(this);

        RxTimerUtil.interval(7000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                mPresenter.getData("bedroom");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getData("bedroom");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxTimerUtil.cancel();
    }

    @Override
    public void showData(HomeData homeData) {
        if (homeData !=null){
            Log.d(TAG, "showData: "+homeData.getData().getTemperature());
            tvInfoTemperatureValue.setText(homeData.getData().getTemperature()+" ℃");
            tvInfoHumidityValue.setText(homeData.getData().getHumidity()+" %RH");
            tvInfoSmokeValue.setText(homeData.getData().getSmoke()+" ml/m3");
        }else {
            tvInfoTemperatureValue.setText("0 ℃");
            tvInfoHumidityValue.setText("0 %RH");
            tvInfoSmokeValue.setText("0 ml/m3");
        }
    }

    @Override
    public void setPresenter(SmartHomeContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
