package com.example.huaweiot.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import com.example.huaweiot.R;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    //Toolbar 相关
    private Toolbar mToolbar;
    private TextView mToolbarTitle;

    protected FragmentActivity mActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mContentView = inflater.inflate(setLayoutId(), container, false);
        ButterKnife.bind(this, mContentView);
        setStatusBarColor(true);
        initData();
        initView();
        return mContentView;
    }

    //设置LayoutResouceId
    protected abstract int setLayoutId();

    //初始化Data
    protected abstract void initData();

    //初始化View
    protected abstract void initView();


    /*
     * 设置Toolbar的标题
     *
     * @param title toolbar title
     */
    protected void setToolbarTitle(CharSequence title) {
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        }
    }

    protected void setToolbar(Toolbar toolbar, TextView mToolbarTitle) {
        mToolbar = toolbar;
        this.mToolbarTitle = mToolbarTitle;
        if (mToolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
    }

    /**
     * 设置Toolbar的透明度
     *
     * @param alpha Toolbar的透明度
     */
    protected void setToolbarAlpha(int alpha) {
        mToolbar.getBackground().setAlpha(alpha);
        //setSupportActionBar(mToolbar);
    }

    /**
     * 透明状态栏
     *
     * @param useStatusBarColor 是否使用透明状态栏
     */
    private void setStatusBarColor(boolean useStatusBarColor) {

        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //注意要清除 FLAG_TRANSLUCENT_STATUS flag
        mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mActivity.getWindow().setStatusBarColor(Color.TRANSPARENT);//透明
        //6.0以后的系统需要设置状态栏的字体和图案为亮色才能正常显示
        mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }
}
