package com.example.huaweiot.homePage.housePage;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.huaweiot.view.CustomMultiButton;
import com.example.huaweiot.view.CustomProgress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ParlorFragment extends BaseFragment implements SmartHomeContract.View {

    private static final String TAG = "ParlorFragment";

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
    @BindView(R.id.sw_parlor_air_condition)
    SwitchCompat swParlorAirCondition;
    @BindView(R.id.et_set_temperature)
    EditText etSetTemperature;
    @BindView(R.id.multi_btn_air_condition_mode)
    CustomMultiButton multiBtnAirConditionMode;
    @BindView(R.id.multi_btn_air_condition_speed)
    CustomMultiButton multiBtnAirConditionSpeed;
    @BindView(R.id.progress_parlor_light)
    CustomProgress progressParlorLight;
    @BindView(R.id.sw_parlor_light)
    SwitchCompat swParlorLight;
    @BindView(R.id.sw_balcony_light)
    SwitchCompat swBalconyLight;

    //灯光是否开启标志
    private boolean sw_parlorLight_isOpen = false;
    private boolean sw_balconyLight_isOpen = false;
    private boolean sw_airCondition_isOpen = false;

    //保存设置的值
    private int mode_index = 1;
    private int speed_index = 1;
    private String temperatureInput = "26";

    //多项选择器文本填充
    private List<String> air_condition_modes;
    private List<String> air_condition_speed;

    private List<String> air_condition_modes_paras;
    private List<String> air_condition_speed_paras;

    //跟presenter双向绑定
    private SmartHomeContract.Presenter mPresenter;
    private SmartHomePresenter presenter;

    //fragment需要有无参的构造方法
    public ParlorFragment() {
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_parlor_room;
    }

    @Override
    protected void initData() {

        //两个选择器的文本内容
        air_condition_modes = new ArrayList<>();
        air_condition_modes.add("自动");
        air_condition_modes.add("制冷");
        air_condition_modes.add("制热");
        air_condition_modes.add("送风");

        air_condition_speed = new ArrayList<>();
        air_condition_speed.add("自动");
        air_condition_speed.add("低");
        air_condition_speed.add("中");
        air_condition_speed.add("高");

        //下发的参数列表
        air_condition_modes_paras = new ArrayList<>();
        air_condition_modes_paras.add("A");
        air_condition_modes_paras.add("L");
        air_condition_modes_paras.add("R");
        air_condition_modes_paras.add("F");

        air_condition_speed_paras = new ArrayList<>();
        air_condition_speed_paras.add("A");
        air_condition_speed_paras.add("L");
        air_condition_speed_paras.add("M");
        air_condition_speed_paras.add("H");

        //获取初始的温湿度、烟雾数据、开关状态、灯光状态
        //记得去读取初始状态后设置显示出来
    }

    @Override
    protected void initView() {
        //toolbar
        setToolbar(baseToolbar, baseToolbarTitle);
        setToolbarTitle("客厅");
        // setToolbarAlpha(255);

        //灯光开关、空调开关
        swParlorLight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.i(TAG, "onCheckedChanged: sw_light->" + isChecked);

            if(isChecked){
                Toast.makeText(getActivity(), "打开客厅灯光", Toast.LENGTH_SHORT).show();
                Map<String, String> paras = new HashMap<>();
                paras.put("open", "O");
                mPresenter.postCommand("lighting_parlour", paras);
            }else{
                Toast.makeText(getActivity(), "关闭客厅灯光", Toast.LENGTH_SHORT).show();
                Map<String, String> paras = new HashMap<>();
                paras.put("open", "C");
                mPresenter.postCommand("lighting_parlour", paras);
            }

//            if (!sw_parlorLight_isOpen) {
//                sw_parlorLight_isOpen = true;
//                Toast.makeText(getActivity(), "打开客厅灯光", Toast.LENGTH_SHORT).show();
//                Map<String, String> paras = new HashMap<>();
//                paras.put("open", "O");
//                mPresenter.postCommand("lighting_parlour", paras);
//            } else {
//                sw_parlorLight_isOpen = false;
//                Toast.makeText(getActivity(), "关闭客厅灯光", Toast.LENGTH_SHORT).show();
//                Map<String, String> paras = new HashMap<>();
//                paras.put("open", "C");
//                mPresenter.postCommand("lighting_parlour", paras);
//            }

        });

        swParlorAirCondition.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.i(TAG, "onCheckedChanged: sw_air_condition->" + isChecked);
            if (isChecked) {
                sw_airCondition_isOpen = true;
                Toast.makeText(getActivity(), "打开客厅空调", Toast.LENGTH_SHORT).show();
                Map<String, String> paras = new HashMap<>();
                paras.put("open", "O");
                paras.put("mode", "A");
                paras.put("temperature", "26");
                paras.put("wind_speed", "A");
                mPresenter.postCommand("aircondition", paras);
            } else {
                sw_airCondition_isOpen = false;
                Toast.makeText(getActivity(), "关闭客厅空调", Toast.LENGTH_SHORT).show();
                Map<String, String> paras = new HashMap<>();
                paras.put("open", "C");
                paras.put("mode", "A");
                paras.put("temperature", "26");
                paras.put("wind_speed", "A");
                mPresenter.postCommand("aircondition", paras);
            }
        });

        swBalconyLight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.i(TAG, "onCheckedChanged: sw_balcony_light -> " + isChecked);
        });

        //客厅灯光进度条
        progressParlorLight.setVisibility(View.VISIBLE);
        progressParlorLight.setOnProgressListener(progress -> {
            Log.i(TAG, "onSelect: " + progress);
            Toast.makeText(getActivity(), "客厅灯光亮度：" + progress, Toast.LENGTH_SHORT).show();
        });

        //空调模式选择器
        multiBtnAirConditionMode.setText(air_condition_modes);
        multiBtnAirConditionMode.setOnButtonItemChecked(id -> {
            Log.i(TAG, "onItemChecked: ---->" + id);
            mode_index = id;
        });

        //风速模式选择器
        multiBtnAirConditionSpeed.setText(air_condition_speed);
        multiBtnAirConditionSpeed.setOnButtonItemChecked(id -> {
            Log.i(TAG, "onItemChecked: ---->" + id);
            speed_index = id;
        });

        //空调输入设置温度
        etSetTemperature.setOnEditorActionListener((v, actionId, event) -> {
            Log.i(TAG, "onEditorAction: ----input number:" + etSetTemperature.getText().toString());

            temperatureInput = etSetTemperature.getText().toString();
            //true：按回车键后获取数据，并不能再做其他操作
            //false: 按下回车键还能做其他的操作
            return true;
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SmartHomePresenter(this);

        //每隔5秒定时请求服务器，获取最新的室内传感器数据
        RxTimerUtil.interval(5000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                mPresenter.getData("parlour");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //每次重新进入本页面都会请求一次最新的数据
        mPresenter.getData("parlour");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //页面销毁时，释放定时任务
        RxTimerUtil.cancel();
    }

    @OnClick(R.id.btn_house_device_edit)
    public void onViewClicked() {
        if (!etSetTemperature.getText().toString().equals("")) {
            temperatureInput = etSetTemperature.getText().toString();
        }
        Log.i(TAG, "onViewClicked: btn_click_callback");
        if (sw_airCondition_isOpen){
            Map<String, String> paras = new HashMap<>();
            paras.put("open", "O");
            paras.put("mode", air_condition_modes_paras.get(mode_index-1));
            paras.put("temperature", temperatureInput);
            paras.put("wind_speed", air_condition_speed_paras.get(speed_index-1));
            mPresenter.postCommand("aircondition", paras);
        }
    }


    @Override
    public void showData(HomeData homeData) {

        if (homeData != null) {
            Log.d(TAG, "showData: "+homeData.getData().getTemperature());
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
}
