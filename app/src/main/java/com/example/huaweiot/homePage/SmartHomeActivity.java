package com.example.huaweiot.homePage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.huaweiot.R;
import com.example.huaweiot.api.Beans.HomeData;
import com.example.huaweiot.base.ActivityCollector;
import com.example.huaweiot.base.BaseActivity;
import com.example.huaweiot.homePage.devicePage.DeviceFragment;
import com.example.huaweiot.homePage.housePage.ParlorFragment;
import com.example.huaweiot.homePage.housePage.RoomContainerFragment;
import com.example.huaweiot.homePage.scensPage.ScenesFragment;
import com.example.huaweiot.homePage.settingPage.SettingFragment;

import com.example.huaweiot.utils.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
    private SmartHomePresenter presenter;

    //语音识别相关
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private SpeechRecognizer mIat;// 语音听写
    private RecognizerDialog iatDialog;//听写动画


    //动态权限申请
    //1、首先声明一个数组permissions，将需要的权限都放在里面
    String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //2、创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPermissionList中
    List<String> mPermissionList = new ArrayList<>();
    private final int mRequestCode = 100;//权限请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //绑定presenter
        presenter = new SmartHomePresenter(this);

        //判断权限和申请权限
        applyPermission();

        //语音识别部分配置
        // 语音听写1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        mIat = SpeechRecognizer.createRecognizer(SmartHomeActivity.this, mInitListener);
        if (mIat == null) Log.d(TAG, "onCreate: ----null----");
        // 1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        iatDialog = new RecognizerDialog(this, mInitListener);

    }

    /**
     * 绑定layout文件
     *
     * @return
     */
    @Override
    protected int setLayoutId() {
        return R.layout.activity_smart_home;
    }

    /**
     * 初始化视图
     */
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

    /**
     * 初始化视图需要的数据
     */
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

    /**
     * 初始化监听器
     */
    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int i) {
            Log.d(TAG, "SpeechRecognizer init() code = " + i);
            if (i != ErrorCode.SUCCESS) {
                Log.d(TAG, "onInit: 初始化失败，错误码：" + i);
            }
        }
    };


    /**
     * 听写对话框UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            //一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
            //isLast等于true时会话结束。
            //这个版本isLast = b
            if (!b) {
                printResult(recognizerResult);
            }

        }

        @Override
        public void onError(SpeechError speechError) {
            Toast.makeText(getApplication(), speechError.getPlainDescription(true), Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 读取动态修正返回结果示例代码
     *
     * @param results
     */
    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());
        String sn = null;
        String pgs = null;
        String rg = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
            pgs = resultJson.optString("pgs");
            rg = resultJson.optString("rg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //如果pgs是rpl就在已有的结果中删除掉要覆盖的sn部分
        if (pgs.equals("rpl")) {
            String[] strings = rg.replace("[", "").replace("]", "").split(",");
            int begin = Integer.parseInt(strings[0]);
            int end = Integer.parseInt(strings[1]);
            for (int i = begin; i <= end; i++) {
                mIatResults.remove(i + "");
            }
        }

        mIatResults.put(sn, text);
        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

//        mResultText.setText(resultBuffer.toString());
//        mResultText.setSelection(mResultText.length());
        Log.d(TAG, "printResult: " + resultBuffer.toString());

        matchResult(resultBuffer.toString());

    }

    private void matchResult(String result) {


        if (result.contains("打开客厅灯")) {
            Map<String, String> paras = new HashMap<>();
            paras.put("open", "O");
            mPresenter.postCommand("lighting_parlour", paras);

        } else if (result.contains("关闭客厅灯")) {
            Map<String, String> paras = new HashMap<>();
            paras.put("open", "C");
            mPresenter.postCommand("lighting_parlour", paras);

        } else if (result.contains("打开卧室灯")) {
            Map<String, String> paras = new HashMap<>();
            paras.put("open", "O");
            mPresenter.postCommand("lighting_bedroom", paras);

        } else if (result.contains("关闭卧室灯")) {
            Map<String, String> paras = new HashMap<>();
            paras.put("open", "C");
            mPresenter.postCommand("lighting_bedroom", paras);

        } else if (result.contains("打开厨房灯")) {
            Map<String, String> paras = new HashMap<>();
            paras.put("open", "O");
            mPresenter.postCommand("lighting_kitchen", paras);

        } else if (result.contains("关闭厨房灯")) {
            Map<String, String> paras = new HashMap<>();
            paras.put("open", "C");
            mPresenter.postCommand("lighting_kitchen", paras);

        } else if (result.contains("打开窗帘")) {
            Map<String, String> paras = new HashMap<>();
            paras.put("open", "O");
            mPresenter.postCommand("curtain", paras);

        } else if (result.contains("关闭窗帘")) {
            Map<String, String> paras = new HashMap<>();
            paras.put("open", "C");
            mPresenter.postCommand("curtain", paras);
        }
        else if (result.contains("打开抽风机")) {
            Map<String, String> paras = new HashMap<>();
            paras.put("fan", "O");
            mPresenter.postCommand("fanning", paras);

        }else if (result.contains("关闭抽风机")) {
            Map<String, String> paras = new HashMap<>();
            paras.put("fan", "C");
            mPresenter.postCommand("fanning", paras);
        }
    }


    /**
     * 开始听写
     */
    private void starWrite() {
        // 2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类
        // 语音识别应用领域（：iat，search，video，poi，music）
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        // 接收语言中文
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 接受的语言是普通话
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
        // 设置听写引擎（云端）
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        iatDialog.setListener(mRecognizerDialogListener);
        iatDialog.show();
        Toast.makeText(getApplication(), "请开始说话…", Toast.LENGTH_SHORT).show();

    }

    /**
     * MVP
     * 显示数据回调
     *
     * @param homeData
     */
    @Override
    public void showData(HomeData homeData) {

    }

    /**
     * MVP
     * 绑定presenter
     *
     * @param presenter
     */
    @Override
    public void setPresenter(SmartHomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 语音按键点击监听器
     */
    @OnClick(R.id.ib_voice_btn)
    public void onViewClicked() {
        //跳转到语音识别界面
        starWrite();

    }

    /**
     * 判断和申请权限
     */
    public void applyPermission() {
        mPermissionList.clear();//清空没有通过的权限

        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }

        //申请权限
        if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this, permissions, mRequestCode);
        } else {
            //说明权限都已经通过，可以做你想做的事情去
        }

    }

    /**
     * 申请权限结果的回调方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (mRequestCode == requestCode) {
            for (int grantResult : grantResults) {
                if (grantResult == -1) {
                    hasPermissionDismiss = true;
                    break;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                //可以弹出对话框，并引导跳转到设置界面，或者直接关闭
                Log.d(TAG, "onRequestPermissionsResult: you have not refuse the permission");
                //关闭所有页面并退出
                ActivityCollector.finishAll();
            } else {
                //全部权限通过，可以进行下一步操作。。。
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //记得及时释放MVP里面的引用

        // 退出时释放连接语音识别用到的资源
        mIat.cancel();
        mIat.destroy();
    }
}
