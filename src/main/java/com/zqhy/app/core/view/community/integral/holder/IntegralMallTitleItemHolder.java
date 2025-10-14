package com.zqhy.app.core.view.community.integral.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.community.integral.IntegralMallTitleVo;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/11/25-10:47
 * @description
 */
public class IntegralMallTitleItemHolder extends AbsItemHolder<IntegralMallTitleVo, IntegralMallTitleItemHolder.ViewHolder> {
    public IntegralMallTitleItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_integral_mall_title;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        RecyclerView.LayoutParams clp = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        if (clp instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) clp).setFullSpan(true);
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull IntegralMallTitleVo item) {
        holder.mTvTitle.setText(item.getTitle());
        holder.mTvSubTitle.setText(item.getSubTitle());
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvTitle;
        private TextView mTvSubTitle;

        public ViewHolder(View view) {
            super(view);
            mTvTitle = findViewById(R.id.tv_title);
            mTvSubTitle = findViewById(R.id.tv_sub_title);

        }
    }
}
