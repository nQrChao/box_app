package com.zqhy.app.widget.decoration;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * @author leeham2734
 * @date 2020/11/12-11:50
 * @description
 */
public class TopItemDecoration extends RecyclerView.ItemDecoration {

    private int topSpacing = 0;

    public TopItemDecoration(int topSpacing) {
        this.topSpacing = topSpacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int totalSize = parent.getAdapter().getItemCount();
        if (itemPosition == 0) {
            outRect.top = topSpacing;
        } else if (itemPosition == totalSize - 1) {
            outRect.bottom = topSpacing;
        } else {
            outRect.top = 0;
        }
    }
}
