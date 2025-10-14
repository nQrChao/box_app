package com.zqhy.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * @author Administrator
 * @date 2021/8/11 0011-17:02
 * @description
 */
public class MyLinearLayoutView extends LinearLayout {

    public MyLinearLayoutView(Context context) {
        this(context, null);
    }

    public MyLinearLayoutView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLinearLayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float downX, downY = 0;
    private float moveX, moveY = 0;
    private long currentMS, moveTime = 0;
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                currentMS = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX += Math.abs(event.getX() - downX);//x轴移动距离
                moveY += Math.abs(event.getY() - downY);//y轴移动距离
                break;
            case MotionEvent.ACTION_UP:
                moveTime = System.currentTimeMillis() - currentMS;
                //判断是滑动还是点击操作、判断是否继续传递信号
                if (moveTime < 300 && moveX < 20 && moveY < 20) {//点击事件
                    moveX = moveY = 0;//归零
                    if (moreAction != null)moreAction.onMoreAction();
                    return true;
                } else {//滑动事件
                    moveX = moveY = 0;//归零
                    return super.dispatchTouchEvent(event);//返回true,表示不再执行后面的事件
                }
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    public MoreAction moreAction;

    public void setMoreAction(MoreAction moreAction) {
        this.moreAction = moreAction;
    }

    public interface MoreAction{
        void onMoreAction();
    }
}
