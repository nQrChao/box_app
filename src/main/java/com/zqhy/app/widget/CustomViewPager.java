package com.zqhy.app.widget;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 可控制滑动ViewPager
 * @author 韩国桐
 * @version [0.1, 2020/5/7]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CustomViewPager extends ViewPager {

    private boolean isCanScroll=true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            //允许滑动则应该调用父类的方法
            return super.onTouchEvent(ev);
        } else {
            //禁止滑动则不做任何操作，直接返回true即可
            return true;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (isCanScroll){
            return super.onInterceptTouchEvent(arg0);
        }else{
            return false;
        }
    }

    public void setIsCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }
}