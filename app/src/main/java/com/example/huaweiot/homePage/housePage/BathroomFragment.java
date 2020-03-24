package com.example.huaweiot.homePage.housePage;

import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.example.huaweiot.R;
import com.example.huaweiot.base.BaseFragment;
import com.example.huaweiot.view.CustomProgress;

import butterknife.BindView;

public class BathroomFragment extends BaseFragment {
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
    @BindView(R.id.progress_bathroom_light)
    CustomProgress progressBathroomLight;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_bathroom;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        //toolbar
        setToolbar(baseToolbar, baseToolbarTitle);
        setToolbarTitle("客厅");
    }
}
