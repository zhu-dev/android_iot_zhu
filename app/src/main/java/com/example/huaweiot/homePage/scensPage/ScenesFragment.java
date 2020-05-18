package com.example.huaweiot.homePage.scensPage;

import android.os.Bundle;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class ScenesFragment extends BaseFragment implements SmartHomeContract.View {

    private static final String TAG = "ScenesFragment";

    @BindView(R.id.base_toolbar_title)
    TextView baseToolbarTitle;
    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.sw_light_on)
    SwitchCompat swLightOn;
    @BindView(R.id.sw_leave_home)
    SwitchCompat swLeaveHome;
    @BindView(R.id.sw_back_home)
    SwitchCompat swBackHome;
    @BindView(R.id.sw_entertainment)
    SwitchCompat swEntertainment;

    private boolean sw_homeMode_isOpen = false;

    //跟presenter双向绑定
    private SmartHomeContract.Presenter mPresenter;
    private SmartHomePresenter presenter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_scenes;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        //toolbar
        setToolbar(baseToolbar, baseToolbarTitle);
        setToolbarTitle("场景");
        setToolbarAlpha(255);

        swLightOn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.i(TAG, "onCheckedChanged: sw_light_on ");
            if (!sw_homeMode_isOpen){
                Toast.makeText(getActivity(), "打开家庭所有灯光", Toast.LENGTH_SHORT).show();
                sw_homeMode_isOpen = true;
                Map<String, String> paras = new HashMap<>();
                paras.put("open", "O");
                mPresenter.postCommand("home_mode", paras);
            }else {
                Toast.makeText(getActivity(), "关闭家庭所有灯光", Toast.LENGTH_SHORT).show();
                sw_homeMode_isOpen = false;
                Map<String, String> paras = new HashMap<>();
                paras.put("open", "C");
                mPresenter.postCommand("home_mode", paras);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SmartHomePresenter(this);

    }


    @Override
    public void showData(HomeData homeData) {

    }

    @Override
    public void setPresenter(SmartHomeContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
