package com.example.huaweiot.homePage.settingPage;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huaweiot.R;
import com.example.huaweiot.base.BaseFragment;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;

public class SettingFragment extends BaseFragment {

    @BindView(R.id.base_toolbar_title)
    TextView baseToolbarTitle;
    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.view_about_app)
    ConstraintLayout viewAboutApp;
    @BindView(R.id.view_personal_info)
    ConstraintLayout viewPersonalInfo;
    @BindView(R.id.view_general_setting)
    ConstraintLayout viewGeneralSetting;
    @BindView(R.id.view_check_upgrade)
    ConstraintLayout viewCheckUpgrade;

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

    @OnClick({R.id.view_personal_info, R.id.view_general_setting, R.id.view_check_upgrade, R.id.view_about_app})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.view_personal_info:
                Toast.makeText(getContext(), "我好想毕业！！！！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.view_general_setting:
                Toast.makeText(getContext(), "我好想毕业！！！！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.view_check_upgrade:
                Toast.makeText(getContext(), "我好想毕业！！！！", Toast.LENGTH_SHORT).show();
                break;
            case  R.id.view_about_app :
                Toast.makeText(getContext(), "我好想毕业！！！！", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
