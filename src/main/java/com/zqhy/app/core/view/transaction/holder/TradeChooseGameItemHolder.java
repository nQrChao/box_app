package com.zqhy.app.core.view.transaction.holder;

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
 */
public class TradeChooseGameItemHolder extends AbsItemHolder<GameInfoVo, TradeChooseGameItemHolder.ViewHolder> {

    public TradeChooseGameItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_transaction_choose_game;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameInfoVo item) {
        GlideUtils.loadRoundImage(mContext,item.getGameicon(),holder.mIvGameImage, R.mipmap.ic_placeholder);
        holder.mTvGameName.setText(item.getGamename());
    }

    public class ViewHolder extends AbsHolder {

        private ImageView mIvGameImage;
        private TextView mTvGameName;

        public ViewHolder(View view) {
            super(view);
            mIvGameImage = findViewById(R.id.iv_game_image);
            mTvGameName = findViewById(R.id.tv_game_name);

        }
    }
}
