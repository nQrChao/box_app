package com.zqhy.app.core.view.transfer.holder;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class TransferGameHolder extends AbsItemHolder<GameInfoVo, TransferGameHolder.ViewHolder> {

    public TransferGameHolder(Context context) {
        super(context);
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameInfoVo item) {

        GlideApp.with(mContext)
                .asBitmap()
                .load(item.getGameicon())
                .centerCrop()
                .into(holder.mGameIconIV);

        holder.mTvGameName.setText(item.getGamename());

        int showDiscount = item.showDiscount();
        if (showDiscount == 0) {
            holder.mFlDiscount.setVisibility(View.GONE);
        } else if (showDiscount == 1 || showDiscount == 2) {
            holder.mFlDiscount.setVisibility(View.VISIBLE);
            if (showDiscount == 1){
                holder.mTvGameDiscount.setText(String.valueOf(item.getDiscount()));
            }else if (showDiscount == 2){
                holder.mTvGameDiscount.setText(String.valueOf(item.getFlash_discount()));
            }
        } else {
            holder.mFlDiscount.setVisibility(View.GONE);
        }
        holder.mTvGameType.setText(item.getGenre_str());
        holder.mTvTransferDetail.setOnClickListener(view -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_transfer_game;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private ImageView mGameIconIV;
        private TextView mTvGameName;
        private FrameLayout mFlDiscount;
        private TextView mTvGameDiscount;
        private TextView mTvGameType;
        private TextView mTvTransferDetail;

        public ViewHolder(View view) {
            super(view);
            mGameIconIV = itemView.findViewById(R.id.gameIconIV);
            mTvGameName = itemView.findViewById(R.id.tv_game_name);
            mFlDiscount = itemView.findViewById(R.id.fl_discount);
            mTvGameDiscount = itemView.findViewById(R.id.tv_game_discount);
            mTvGameType = itemView.findViewById(R.id.tv_game_type);
            mTvTransferDetail = itemView.findViewById(R.id.tv_transfer_detail);

        }
    }
}
