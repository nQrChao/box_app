package com.zqhy.app.core.view.user.vip.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/11/25-17:42
 * @description
 */
public class GrowthNoMoreValueItemHolder extends AbsItemHolder<NoMoreDataVo, GrowthNoMoreValueItemHolder.ViewHolder> {

    public GrowthNoMoreValueItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_layout_no_more_data_vip_score;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull NoMoreDataVo item) {

    }

    public class ViewHolder extends AbsHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }
}
