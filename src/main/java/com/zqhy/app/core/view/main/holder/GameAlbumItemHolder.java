package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.mainpage.gamealbum.GameAlbumVo;
import com.zqhy.app.core.data.model.splash.AppStyleConfigs;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class GameAlbumItemHolder extends BaseItemHolder<GameAlbumVo, GameAlbumItemHolder.ViewHolder> {

    private float density;

    public GameAlbumItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_album;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull GameAlbumVo gameAlbumVo) {

        viewHolder.mLlRootview.setOnClickListener(v -> {
            appJump(gameAlbumVo.getPage_type(), gameAlbumVo.getParam());
        });
        GlideUtils.loadRoundImage(mContext, gameAlbumVo.getPic(), viewHolder.mGameIconIV);
        viewHolder.mTvGameName.setText(gameAlbumVo.getTitle());
        viewHolder.mTvGameIntro.setText(gameAlbumVo.getDescription());

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#FFF4E8"));
        gd.setCornerRadius(2 * density);

        viewHolder.mTvTagCollection1.setBackground(gd);
        viewHolder.mTvTagCollection2.setBackground(gd);
        viewHolder.mTvTagCollection3.setBackground(gd);

        try {
            String[] labels = gameAlbumVo.getLabels().split(",", -1);
            if (labels.length >= 1) {
                viewHolder.mTvTagCollection1.setVisibility(View.VISIBLE);
                viewHolder.mTvTagCollection1.setText(labels[0]);
            } else {
                viewHolder.mTvTagCollection1.setVisibility(View.GONE);
            }
            if (labels.length >= 2) {
                viewHolder.mTvTagCollection2.setVisibility(View.VISIBLE);
                viewHolder.mTvTagCollection2.setText(labels[1]);
            } else {
                viewHolder.mTvTagCollection3.setVisibility(View.GONE);
            }
            if (labels.length >= 3) {
                viewHolder.mTvTagCollection3.setVisibility(View.VISIBLE);
                viewHolder.mTvTagCollection3.setText(labels[2]);
            } else {
                viewHolder.mTvTagCollection3.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(AppStyleConfigs.BUTTON_GAME_LIST_URL)) {
            viewHolder.mTvLookOut.setVisibility(View.GONE);
            viewHolder.mIvCollectionButton.setVisibility(View.VISIBLE);
            GlideUtils.loadNormalImage(mContext, AppStyleConfigs.BUTTON_GAME_LIST_URL, viewHolder.mIvCollectionButton,R.mipmap.ic_placeholder);
        } else {
            viewHolder.mTvLookOut.setVisibility(View.VISIBLE);
            viewHolder.mIvCollectionButton.setVisibility(View.GONE);
        }
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlRootview;
        private ImageView mGameIconIV;
        private TextView mTvGameName;
        private LinearLayout mLlTagCollection;
        private TextView mTvTagCollection1;
        private TextView mTvTagCollection2;
        private TextView mTvTagCollection3;
        private TextView mTvGameIntro;
        private FrameLayout mFlLookOut;
        private TextView mTvLookOut;
        private ImageView mIvCollectionButton;

        public ViewHolder(View view) {
            super(view);
            mLlRootview = itemView.findViewById(R.id.ll_rootview);
            mGameIconIV = itemView.findViewById(R.id.gameIconIV);
            mTvGameName = itemView.findViewById(R.id.tv_game_name);
            mLlTagCollection = itemView.findViewById(R.id.ll_tag_collection);
            mTvTagCollection1 = itemView.findViewById(R.id.tv_tag_collection_1);
            mTvTagCollection2 = itemView.findViewById(R.id.tv_tag_collection_2);
            mTvTagCollection3 = itemView.findViewById(R.id.tv_tag_collection_3);
            mTvGameIntro = itemView.findViewById(R.id.tv_game_intro);
            mFlLookOut = itemView.findViewById(R.id.fl_look_out);
            mTvLookOut = itemView.findViewById(R.id.tv_look_out);
            mIvCollectionButton = itemView.findViewById(R.id.iv_collection_button);

        }
    }
}
