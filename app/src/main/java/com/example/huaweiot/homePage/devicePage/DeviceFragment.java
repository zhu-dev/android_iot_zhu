package com.example.huaweiot.homePage.devicePage;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.huaweiot.R;
import com.example.huaweiot.base.BaseFragment;

import butterknife.BindView;


public class DeviceFragment extends BaseFragment {


    @BindView(R.id.base_toolbar_title)
    TextView baseToolbarTitle;
    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;

    public DeviceFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_device;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        //toolbar
        setToolbar(baseToolbar, baseToolbarTitle);
        setToolbarTitle("我的设备");
        setToolbarAlpha(255);
    }

}
