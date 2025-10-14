package com.zqhy.app.widget.recycler_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * @author leeham2734
 * @date 2020/9/25-11:59
 * @description
 */
public class MainXRecyclerView extends XRecyclerView {
    public MainXRecyclerView(Context context) {
        super(context);
    }

    public MainXRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainXRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    private float mDownPosX, mDownPosY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final float x = ev.getX();
        final float y = ev.getY();

        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownPosX = x;
                mDownPosY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaX = Math.abs(x - mDownPosX);
                final float deltaY = Math.abs(y - mDownPosY);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (deltaX > deltaY) {
                    return false;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
