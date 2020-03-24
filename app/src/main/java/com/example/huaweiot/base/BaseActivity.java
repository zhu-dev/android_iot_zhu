package com.example.huaweiot.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import butterknife.ButterKnife;


/**
 * 通用的Activity模板
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    //Toolbar 相关
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private CharSequence mToolbarTitleContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);//添加当前的activity到管理容器中
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏
        //getWindow().setNavigationBarColor(Color.WHITE);//设置底部导航虚拟按键颜色为白色
        //setStatusBarColor(true);
        setContentView(setLayoutId());
        ButterKnife.bind(this);
        initData();
        initView();
        //setToolBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);//移除当前马上要销毁的活动
    }

    /**
     * 显示短的Toast
     *
     * @param msg
     */
    protected void showShortToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示长的Toast
     *
     * @param msg
     */
    protected void showLongToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 透明状态栏
     *
     * @param useStatusBarColor 是否使用透明状态栏
     */
    protected void setStatusBarColor(boolean useStatusBarColor) {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //注意要清除 FLAG_TRANSLUCENT_STATUS flag
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);//透明
        //6.0以后的系统需要设置状态栏的字体和图案为亮色才能正常显示
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }

    /**
     * 设置layout布局
     * 让子类重写这个方法，获得布局文件
     *
     * @return layout xml id
     */
    protected abstract int setLayoutId();

    /**
     * 初始化视图组件
     * 让子类重写这个方法，规范初始化View
     */
    protected abstract void initView();

    /**
     * 初始化数据
     * 让子类重写这个方法，统一的在这个方法里初始化数据
     */
    protected abstract void initData();



}

