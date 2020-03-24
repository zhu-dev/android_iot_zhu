package com.example.huaweiot.homePage.housePage;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.huaweiot.R;
import com.example.huaweiot.api.Beans.HomeData;
import com.example.huaweiot.base.BaseFragment;
import com.example.huaweiot.homePage.SmartHomeContract;
import com.example.huaweiot.homePage.SmartHomePresenter;
import com.example.huaweiot.view.VpSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RoomContainerFragment extends BaseFragment {

    private static final String TAG = "RoomContainerFragment";

    @BindView(R.id.vp_rooms_container)
    ViewPager viewPager;
    @BindView(R.id.swipe_room)
    VpSwipeRefreshLayout swipeRoom;

    private ParlorFragment parlorFragment;
    private BedroomFragment bedroomFragment;
    private KitchenFragment kitchenFragment;

    private List<Fragment> fragments;

    private FragmentPagerAdapter mAdapter;

    public RoomContainerFragment() {
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_room_container;
    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        parlorFragment = new ParlorFragment();
        bedroomFragment = new BedroomFragment();
        kitchenFragment = new KitchenFragment();
        fragments.add(parlorFragment);
        fragments.add(bedroomFragment);
        fragments.add(kitchenFragment);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected void initView() {

        //初始化fragment适配器和viewPaper
        mAdapter = new MyFragmentViewPaper(getChildFragmentManager(), fragments);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0); //默认显示第一个
        viewPager.addOnPageChangeListener(mOnPageChangeListener); //添加监听器
        Log.d(TAG, "initView: " + mAdapter + "----" + viewPager);

        //初始化SwipeRefreshLayout
        swipeRoom.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRoom.setSize(SwipeRefreshLayout.DEFAULT);
        swipeRoom.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white_color));
        swipeRoom.setProgressViewEndTarget(true, 100);
        swipeRoom.setOnRefreshListener(mOnRefreshListener);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        fragments.clear();
        viewPager.removeOnPageChangeListener(mOnPageChangeListener);
    }

    //SwipeRefreshLayout下拉刷新视图监听器
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = () -> {
        //刷新触发执行体
        //后台运行刷新数据，回主线程更新视图

    };

    //ViewPager页面改变监听器
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //按照划到的界面加载相应的数据
            Log.d(TAG, "onPageSelected: " + position);
            //mAdapter.getItem(position);
            Log.d(TAG, "onPageSelected: item" + mAdapter.getItem(position));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    //适配器内部类
    private class MyFragmentViewPaper extends FragmentPagerAdapter {

        List<Fragment> fragments;

        public MyFragmentViewPaper(@NonNull FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (fragments == null) Log.d(TAG, "getItem: ");
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
