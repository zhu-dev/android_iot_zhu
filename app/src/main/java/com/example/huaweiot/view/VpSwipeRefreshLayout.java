package com.example.huaweiot.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * 处理ViewPaper和SwipeRefresh滑动冲突的方案
 */
public class VpSwipeRefreshLayout extends SwipeRefreshLayout {

    private float startX;
    private float startY;
    private boolean mIsVpDragger;
    private int mTouchSlop;


    public VpSwipeRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public VpSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsVpDragger = false;//初始化标志位
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsVpDragger) return false;//如果ViewPaper在拖拽，则不拦截它的事件

                // 获取当前手指位置
                float endY = ev.getY();
                float endX = ev.getX();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                // 如果X轴位移大于Y轴位移，那么将事件交给viewPager处理。
                if (distanceX > mTouchSlop && distanceX > distanceY) {
                    mIsVpDragger = true;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        //如果Y>X则交给SwipeRefresh处理
        return super.onInterceptTouchEvent(ev);
    }
}
