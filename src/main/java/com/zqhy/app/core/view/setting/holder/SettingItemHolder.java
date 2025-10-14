package com.zqhy.app.core.view.setting.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.setting.SettingItemVo;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/14
 */

public class SettingItemHolder extends AbsItemHolder<SettingItemVo, SettingItemHolder.ViewHolder> {
    public SettingItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SettingItemVo item) {
        holder.mTvTxt.setText(item.getTxt());
        holder.mTvSubTxt.setText(item.getSubTxt());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_layout_setting;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvTxt;
        private TextView mTvSubTxt;

        public ViewHolder(View view) {
            super(view);
            mTvTxt = view.findViewById(R.id.tv_txt);
            mTvSubTxt = view.findViewById(R.id.tv_sub_txt);
        }

    }
}
