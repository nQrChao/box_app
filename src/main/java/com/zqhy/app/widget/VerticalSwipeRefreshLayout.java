package com.zqhy.app.widget;

import android.content.Context;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author leeham2734
 * @date 2021/3/25-16:13
 * @description
 */
public class VerticalSwipeRefreshLayout extends SwipeRefreshLayout {


    private View mScrollUpChild;

    public VerticalSwipeRefreshLayout(Context context) {
        super(context);
    }

    public VerticalSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollUpChild(View view) {
        mScrollUpChild = view;
    }

    @Override
    public boolean canChildScrollUp() {
        if (mScrollUpChild != null) {
            return mScrollUpChild.canScrollVertically(-1);
        }
        return super.canChildScrollUp();
    }
}
