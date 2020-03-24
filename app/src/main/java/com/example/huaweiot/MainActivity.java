//package com.example.huaweiot;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.example.huaweiot.api.Beans.AuthDataBean;
//import com.example.huaweiot.api.Beans.NbiotDataBean;
//import com.example.huaweiot.api.retrofit2.RetrofitManager;
//import com.example.huaweiot.base.BaseActivity;
//import com.example.huaweiot.homePage.SmartHomeActivity;
//import com.example.huaweiot.utils.Constant;
//import com.example.huaweiot.utils.RequestBodyUtil;
//import com.example.huaweiot.view.CustomMultiButton;
//import com.example.huaweiot.view.CustomProgress;
//import com.tencent.mmkv.MMKV;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.functions.Consumer;
//
//import static io.reactivex.schedulers.Schedulers.io;
//
//public class MainActivity extends BaseActivity {
//
//    private static final String TAG = "MainActivity";
//    @BindView(R.id.tv_show)
//    TextView tvShow;
//    @BindView(R.id.btn_request_device_data)
//    Button btnResquest;
//    @BindView(R.id.btn_sendcommand)
//    Button btnSendcommand;
//    @BindView(R.id.btn_authen)
//    Button btnAuthen;
//    @BindView(R.id.multi_btn_1)
//    CustomMultiButton multiBtn1;
//    @BindView(R.id.progress_bedroom_light)
//    CustomProgress progressParlorLight;
//    @BindView(R.id.btn_smarthome)
//    Button btnSmarthome;
//
//    @Override
//    protected int setLayoutId() {
//        return R.layout.activity_main;
//    }
//
//    @Override
//    protected void initView() {
//
//        progressParlorLight.setVisibility(View.VISIBLE);
//        progressParlorLight.setOnProgressListener(new CustomProgress.OnProgressListener() {
//            @Override
//            public void onSelect(int progress) {
//                Log.i(TAG, "onSelect: " + progress);
//            }
//        });
//        List<String> textList = new ArrayList<>();
//        textList.add("自动");
//        textList.add("制冷");
//        textList.add("制热");
//        textList.add("送风");
//
//        multiBtn1.setText(textList);
//        multiBtn1.setOnButtonItemChecked(new CustomMultiButton.OnButtonItemChecked() {
//            @Override
//            public void onItemChecked(int id) {
//                switch (id) {
//                    case 1:
//                        Log.i(TAG, "onItemChecked: checked 1 --> " + id);
//                        break;
//                    case 2:
//                        Log.i(TAG, "onItemChecked: checked 2 --> " + id);
//                        break;
//                    case 3:
//                        Log.i(TAG, "onItemChecked: checked 3 --> " + id);
//                        break;
//                    case 4:
//                        Log.i(TAG, "onItemChecked: checked 4 --> " + id);
//                        break;
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void initData() {
//
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @OnClick({R.id.btn_sendcommand, R.id.btn_authen, R.id.btn_request_device_data, R.id.btn_smarthome})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btn_authen:
//                getAuthentication();
//                break;
//            case R.id.btn_sendcommand:
//                sendCommand();
//                break;
//            case R.id.btn_request_device_data:
//                getHistoricalData();
//                break;
//            case R.id.btn_smarthome:
//                Intent intent = new Intent(MainActivity.this, SmartHomeActivity.class);
//                startActivity(intent);
//                break;
//        }
//
//    }
//
//    @SuppressLint("CheckResult")
//    public void getAuthentication() {
//        RetrofitManager.getInstance().login(Constant.APPID, Constant.SECRET)
//                .subscribeOn(io())
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//                        //容器销毁
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<AuthDataBean>() {
//                    @Override
//                    public void accept(AuthDataBean authDataBean) throws Exception {
//                        //获取成功
//                        String accessToken = authDataBean.getAccessToken();
//                        MMKV kv = MMKV.defaultMMKV();
//                        kv.encode("accessToken", accessToken);
//                        Log.i(TAG, "response success! accept: accessToken--->" + accessToken);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        //异常
//                        Log.i(TAG, "response failed! accept: --->" + throwable.getMessage());
//                    }
//                });
//    }
//
//
//    @SuppressLint("CheckResult")
//    public void getHistoricalData() {
//
//        MMKV kv = MMKV.defaultMMKV();
//        String token = kv.decodeString(Constant.ACCESSTOKEN_KEY);
//
//        Map<String, String> headers = new HashMap<>();
//        headers.put(Constant.HEADER_APP_KEY, Constant.APPID);
//        headers.put(Constant.HEADER_APP_AUTH_KEY, "Bearer  " + token);
//        headers.put(Constant.HEADER_CONTENT_TYPE_KEY, Constant.HEADER_CONTENT_TYPE_VALUE_JSON);
//
//        Map<String, String> params = new HashMap<>();
//        params.put(Constant.PARAMS_DEVICE_ID_KEY, Constant.PARAMS_DEVICE_ID_VALUE);
//        params.put(Constant.PARAMS_GATEWAY_ID_KEY, Constant.PARAMS_GATEWAY_ID__VALUE);
//        params.put(Constant.PARAMS_ENDTIME_KEY, "20191218T172500Z");
//        RetrofitManager.getInstance().getDeviceHistoricalData(headers, params)
//                .subscribeOn(io())
//                .doOnSubscribe(disposable -> {
//                    //
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(data -> {
//                    int totalCount = data.getTotalCount();
//
//                    //判断是否查询该设备的记录
//                    if (totalCount != 0) {
//                        String serviceId = data.getDeviceDataHistoryDTOs().get(0).getServiceId();
//                        String appId = data.getDeviceDataHistoryDTOs().get(0).getAppId();
//                        String deviceId = data.getDeviceDataHistoryDTOs().get(0).getDeviceId();
//                        String timestamp = data.getDeviceDataHistoryDTOs().get(0).getTimestamp();
//                        NbiotDataBean.DeviceDataHistoryDTOsBean.DataBean dataBean = data.getDeviceDataHistoryDTOs().get(0).getData();//这里数据对象也可能为空
//                        String temperature = dataBean.getTemperature();
//                        Log.i(TAG, "success! getHistoricalData data->\n" +
//                                "{ "
//                                + "appId: " + appId + "\n"
//                                + "serviceId: " + serviceId + "\n"
//                                + "deviceId: " + deviceId + "\n"
//                                + "temperature: " + temperature + "\n"
//                                + "timestamp: " + timestamp
//                                + "}");
//                    } else {
//                        Log.i(TAG, "success! getHistoricalData: but the message is null" + totalCount);
//                    }
//
//
//                }, throwable -> Log.i(TAG, "failed!  getHistoricalData throwable message-> " + throwable.getMessage()));
//    }
//
//
//    @SuppressLint("CheckResult")
//    public void sendCommand() {
//
//        MMKV kv = MMKV.defaultMMKV();
//        String token = kv.decodeString(Constant.ACCESSTOKEN_KEY);
//
//        Map<String, String> headers = new HashMap<>();
//        headers.put(Constant.HEADER_APP_KEY, Constant.APPID);
//        headers.put(Constant.HEADER_APP_AUTH_KEY, "Bearer  " + token);
//        headers.put(Constant.HEADER_CONTENT_TYPE_KEY, Constant.HEADER_CONTENT_TYPE_VALUE_JSON);
//
//        String deviceId = "418fe43b-f7ac-474b-891a-d883d5252312";
//        String serviceId = "heatlose";
//        String method = "fanswitching";
//        Map<String, String> paras = new HashMap<>();
//        paras.put("fanswitch", "ON");
//
//        RetrofitManager.getInstance()
//                .sendCommand(headers, RequestBodyUtil.getRequestBody(deviceId, serviceId, method, paras))
//                .subscribeOn(io())
//                .doOnSubscribe(disposable -> {
//                    //do something before subscribe
//                }).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(responseData -> {
//                    String status = responseData.getStatus();
//                    String commanded = responseData.getCommand().getParas().getFanswitch();
//                    Log.i(TAG, "success! accept: send command response data->\n" +
//                            "{"
//                            + "status:" + status + "\n"
//                            + "commanded:" + commanded + "}");
//                }, throwable -> Log.i(TAG, " failed! accept: throwable message:" + throwable.getMessage()));
//    }
////
////
////}
