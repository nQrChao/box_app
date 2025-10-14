package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class GameNoMoreBigItemHolder extends AbsItemHolder<NoMoreDataVo, GameNoMoreBigItemHolder.ViewHolder> {
    public GameNoMoreBigItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_no_more_big_data;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull NoMoreDataVo noMoreDataVo) {
    }

    public class ViewHolder extends AbsHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }
}
