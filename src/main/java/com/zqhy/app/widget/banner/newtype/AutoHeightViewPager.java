package com.zqhy.app.widget.banner.newtype;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * [功能说明]
 *
 * @author 韩国桐
 * @version [0.1, 2020/5/25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AutoHeightViewPager extends ViewPager {
    int lastX = -1;
    int lastY = -1;

    public AutoHeightViewPager(@NonNull Context context) {
        super(context);
    }

    public AutoHeightViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height){
                height = h;
            }
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            int x = (int) ev.getRawX();
            int y = (int) ev.getRawY();
            int dealtX = 0;
            int dealtY = 0;
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = x;
                    lastY = y;
                    // 保证子View能够接收到Action_move事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    dealtX += Math.abs(x - lastX);
                    dealtY += Math.abs(y - lastY);
                    // 这里是否拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                    if (dealtX >= dealtY) { // 左右滑动请求父 View 不要拦截
                        getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                    break;
                case MotionEvent.ACTION_UP:
                    break;

            }
            return super.dispatchTouchEvent(ev);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false ;
    }
}
