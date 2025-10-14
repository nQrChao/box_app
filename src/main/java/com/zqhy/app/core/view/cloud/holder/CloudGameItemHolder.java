package com.zqhy.app.core.view.cloud.holder;

import android.content.Context;
import android.text.TextUtils;
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
 *
 * @author Administrator
 * @date 2018/11/24
 */

public class CloudGameItemHolder extends AbsItemHolder<GameInfoVo, CloudGameItemHolder.ViewHolder> {

    public CloudGameItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameInfoVo item) {
        //图标
        GlideUtils.loadGameIcon(mContext, item.getGameicon(), holder.mIvGameIcon);
        holder.mTvGameName.setText(item.getGamename());
        holder.mTvGameSuffix.setText(item.getOtherGameName());
        if (TextUtils.isEmpty(item.getOtherGameName())){
            holder.mTvGameSuffix.setVisibility(View.GONE);
        }else {
            holder.mTvGameSuffix.setVisibility(View.VISIBLE);
        }
        holder.mTvInfoMiddle.setText(item.getGenre_str());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_cloud_game;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private ImageView mIvGameIcon;
        private TextView mTvGameName;
        private TextView mTvGameSuffix;
        private TextView mTvInfoMiddle;

        public ViewHolder(View view) {
            super(view);
            mIvGameIcon = view.findViewById(R.id.iv_game_icon);
            mTvGameName = view.findViewById(R.id.tv_game_name);
            mTvGameSuffix = view.findViewById(R.id.tv_game_suffix);
            mTvInfoMiddle = view.findViewById(R.id.tv_info_middle);
        }
    }
}
