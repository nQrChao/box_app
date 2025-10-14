package com.zqhy.app.core.view.user.provincecard.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class ProvinceGameItemHolder extends AbsItemHolder<GameInfoVo, ProvinceGameItemHolder.ViewHolder> {
    public ProvinceGameItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameInfoVo item) {
        GlideUtils.loadGameIcon(mContext, item.getGameicon(), holder.mIvIcon);
        holder.mTvName.setText(item.getGamename());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_province_game;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private ImageView mIvIcon;
        private TextView  mTvName;

        public ViewHolder(View view) {
            super(view);
            mIvIcon = findViewById(R.id.iv_icon);
            mTvName = findViewById(R.id.tv_name);
        }
    }
}
