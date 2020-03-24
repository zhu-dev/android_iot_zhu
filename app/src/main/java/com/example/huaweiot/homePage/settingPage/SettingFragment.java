package com.example.huaweiot.homePage.settingPage;

import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.huaweiot.R;
import com.example.huaweiot.base.BaseFragment;

import butterknife.BindView;

public class SettingFragment extends BaseFragment {

    @BindView(R.id.base_toolbar_title)
    TextView baseToolbarTitle;
    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;

    public SettingFragment() {
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        //toolbar
        setToolbar(baseToolbar, baseToolbarTitle);
        setToolbarTitle("我的");
        setToolbarAlpha(255);
    }
}
