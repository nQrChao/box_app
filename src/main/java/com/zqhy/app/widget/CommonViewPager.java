package com.zqhy.app.widget;

import android.content.Context;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author Administrator
 * @date 2018/3/28
 */

public class CommonViewPager extends ViewPager {

    public CommonViewPager(Context context) {
        super(context);
    }

    public CommonViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private int mLastX, mLastY;

    /**
     * 事件拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs(x - mLastX);
                int dy = Math.abs(y - mLastY);

                if (dx * 4 > dy) {
                    // 上一页
                    if (x >= mLastX) {
                        return ViewCompat.canScrollHorizontally(this, -1);
                    } else {
                        // 下一页
                        return ViewCompat.canScrollHorizontally(this, 1);
                    }
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 事件分发
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs(x - mLastX);
                int dy = Math.abs(y - mLastY);

                if (dx * 4 > dy) {
                    if (x >= mLastX) {
                        // 上一页
                        if (ViewCompat.canScrollHorizontally(this, -1)) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        } else {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    } else {
                        // 下一页
                        if (ViewCompat.canScrollHorizontally(this, 1)) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        } else {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }

        return super.dispatchTouchEvent(ev);
    }
}
