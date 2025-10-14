package com.zqhy.app.core.view.transfer.holder;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.transfer.TransferGameListVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class TransferMainHolder extends AbsItemHolder<TransferGameListVo.DataBean, TransferMainHolder.ViewHolder> {

    private float density;

    public TransferMainHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TransferGameListVo.DataBean item) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int position = getPosition(holder);
        if (position % 2 == 0) {
            params.setMargins((int) (5 * density), 0, 0, 0);
        } else {
            params.setMargins(0, 0, (int) (5 * density), 0);
        }
        holder.mRootView.setLayoutParams(params);

        GradientDrawable rootGd = new GradientDrawable();
        rootGd.setColor(ContextCompat.getColor(mContext, R.color.white));
        rootGd.setCornerRadius(density * 5);
        rootGd.setStroke((int) (1 * density), ContextCompat.getColor(mContext, R.color.color_ebebeb));
        holder.mRootView.setBackground(rootGd);

        GlideApp.with(mContext)
                .asBitmap()
                .load(item.getGameicon())
                .placeholder(R.mipmap.ic_placeholder)
                .centerCrop()
                .into(holder.mGameIconIV);

        holder.mTvGameName.setText(item.getGamename());
        holder.mTvGameType.setText(item.getGenre_name());


        GradientDrawable tagGd = new GradientDrawable();

        holder.mTvTag1.setVisibility(View.VISIBLE);
        if (1 == item.getGame_type()) {
            holder.mTvTag.setText("BT");
            holder.mTvTag1.setText("版");
            tagGd.setColor(ContextCompat.getColor(mContext, R.color.color_a882fe));
        } else if (2 == item.getGame_type()) {
            holder.mTvTag.setText(item.getDiscount());
            holder.mTvTag1.setText("折");
            tagGd.setColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
        } else if (3 == item.getGame_type()) {
            holder.mTvTag.setText("H5");
            holder.mTvTag1.setVisibility(View.GONE);
            tagGd.setColor(ContextCompat.getColor(mContext, R.color.color_8fcc52));
        }


        tagGd.setCornerRadius(density * 8);
        holder.mLlTag.setBackground(tagGd);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_transfer_main_list;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mRootView;
        private ImageView mGameIconIV;
        private TextView mTvGameName;
        private TextView mTvGameType;
        private LinearLayout mLlTag;
        private TextView mTvTag;
        private TextView mTvTag1;


        public ViewHolder(View view) {
            super(view);
            mRootView = itemView.findViewById(R.id.rootView);
            mGameIconIV = itemView.findViewById(R.id.gameIconIV);
            mTvGameName = itemView.findViewById(R.id.tv_game_name);
            mTvGameType = itemView.findViewById(R.id.tv_game_type);
            mLlTag = itemView.findViewById(R.id.ll_tag);
            mTvTag = itemView.findViewById(R.id.tv_tag);
            mTvTag1 = itemView.findViewById(R.id.tv_tag_1);


        }
    }
}
