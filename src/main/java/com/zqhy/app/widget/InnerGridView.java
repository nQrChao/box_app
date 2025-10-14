package com.zqhy.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 *
 * @author Administrator
 * @date 2018/1/25
 */

public class InnerGridView extends GridView {
    public InnerGridView(Context context) {
        super(context);
    }

    public InnerGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
