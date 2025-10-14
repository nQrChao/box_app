package com.zqhy.app.core.view.main.holder;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.box.common.ui.adapter.SpacingItemDecorator;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.BuildConfig;

public class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int space;

    public HorizontalSpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        // 只添加右侧间距，避免叠加
        if (position < parent.getAdapter().getItemCount() - 1) {
            outRect.right = space; // 仅设置右侧间距
        }
    }
}
