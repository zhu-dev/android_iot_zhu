package com.example.huaweiot.homePage;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.huaweiot.R;
import com.example.huaweiot.api.Beans.HomeData;
import com.example.huaweiot.base.BaseActivity;
import com.example.huaweiot.homePage.devicePage.DeviceFragment;
import com.example.huaweiot.homePage.housePage.ParlorFragment;
import com.example.huaweiot.homePage.housePage.RoomContainerFragment;
import com.example.huaweiot.homePage.housePage.ScenesFragment;
import com.example.huaweiot.homePage.settingPage.SettingFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SmartHomeActivity extends BaseActivity implements SmartHomeContract.View {

    private static final String TAG = "SmartHomeActivity";

    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.bot_nav_device)
    RadioButton botNavDevice;
    @BindView(R.id.bot_nav_rooms)
    RadioButton botNavRooms;
    @BindView(R.id.bot_nav_scenes)
    RadioButton botNavScenes;
    @BindView(R.id.bot_nav_about_me)
    RadioButton botNavAboutMe;
    @BindView(R.id.radio_group_bottom)
    RadioGroup radioGroupBottom;
    @BindView(R.id.ib_voice_btn)
    ImageButton ibVoiceBtn;

    DeviceFragment deviceFragment;
    ParlorFragment parlorFragment;
    RoomContainerFragment containerFragment;
    ScenesFragment scenesFragment;
    SettingFragment settingFragment;


    private SparseArray<Fragment> mFragmentSparseArray;

    private SmartHomeContract.Presenter mPresenter;
    private SmartHomePresenter presenter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SmartHomePresenter(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_smart_home;
    }

    @Override
    protected void initView() {

        mFragmentSparseArray.append(R.id.bot_nav_device, deviceFragment);
        mFragmentSparseArray.append(R.id.bot_nav_rooms, containerFragment);
        mFragmentSparseArray.append(R.id.bot_nav_scenes, scenesFragment);
        mFragmentSparseArray.append(R.id.bot_nav_about_me, settingFragment);

        Log.d(TAG, "initView: R.id.bot_nav_device-->" + R.id.bot_nav_device);

        radioGroupBottom.setOnCheckedChangeListener((group, checkedId) -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mFragmentSparseArray.get(checkedId))
                    .commit();
            Log.d(TAG, "initView: checkedId->" + checkedId);
        });

        //默认显示第一个
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mFragmentSparseArray.get(R.id.bot_nav_device)).commit();
        botNavDevice.setChecked(true);
    }

    @Override
    protected void initData() {

        //装载房间fragment
        mFragmentSparseArray = new SparseArray<>();
        deviceFragment = new DeviceFragment();
        parlorFragment = new ParlorFragment();
        containerFragment = new RoomContainerFragment();
        scenesFragment = new ScenesFragment();
        settingFragment = new SettingFragment();

        //拉取设备数据

    }

    @Override
    public void showData(HomeData homeData) {
        Toast.makeText(SmartHomeActivity.this,
                "data:" + homeData.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(SmartHomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @OnClick(R.id.ib_voice_btn)
    public void onViewClicked() {
        //跳转到语音识别界面
       // mPresenter.getData();

        Map<String,String> paras = new HashMap<>();
        paras.put("mode","A");
        paras.put("temperature","28");
        paras.put("wind_speed","A");
        paras.put("open","O");
        mPresenter.postCommand("aircondition",paras);
    }


}
