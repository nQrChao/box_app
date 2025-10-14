package com.zqhy.app.widget.layout_manager;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * @author leeham2734
 * @date 2020/9/2-10:29
 * @description
 */
public class OffsetHorizontalLayoutManager extends RecyclerView.LayoutManager {


    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    private int mSumDx = 0;

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int travel = dx;
        //如果滑到最左边
        if (mSumDx + dx < 0) {
            travel = -mSumDx;
        } else if (mSumDx + dx > mTotalWidth - getHorizontalSpace()) {
            travel = mTotalWidth - getHorizontalSpace() - mSumDx;
        }
        mSumDx += travel;
        offsetChildrenHorizontal(-travel);
        return dx;
    }

    private int mTotalWidth = 0;

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.getItemCount() == 0 || state.isPreLayout()) {
            return;
        }

        //定义水平方向的偏移量
        int offsetX = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int width = getDecoratedMeasuredWidth(view);
            int height = getDecoratedMeasuredHeight(view);
            layoutDecorated(view, 0, offsetX, width, offsetX + height);
            offsetX += width;
        }
        //如果所有子View的高度和没有填满RecyclerView的高度，
        // 则将高度设置为RecyclerView的高度
        mTotalWidth = Math.max(offsetX, getHorizontalSpace());
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }
}
