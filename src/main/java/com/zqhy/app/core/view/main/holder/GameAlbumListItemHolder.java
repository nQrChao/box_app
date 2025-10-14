package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.box.common.glide.GlideApp;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.mainpage.gamealbum.GameAlbumListVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class GameAlbumListItemHolder extends AbsItemHolder<GameAlbumListVo, GameAlbumListItemHolder.ViewHolder> {

    private float density;

    public GameAlbumListItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_album_list;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull GameAlbumListVo gameAlbumListVo) {
        int rootViewWidth = ScreenUtils.getScreenWidth(mContext);
        int rootViewHeight = (int) (rootViewWidth / 1.78f);

        LinearLayout.LayoutParams rootViewParams = new LinearLayout.LayoutParams(rootViewWidth, rootViewHeight);
        viewHolder.mLlRootview.setLayoutParams(rootViewParams);

        if (!TextUtils.isEmpty(gameAlbumListVo.getBackground())) {
            GlideApp.with(mContext)
                    .asBitmap()
                    .load(gameAlbumListVo.getBackground())
                    .placeholder(R.mipmap.img_placeholder_v_1)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            viewHolder.mIvImgBg.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable drawable) {

                        }
                    });
        }

        viewHolder.mTvGameCollectionTitle.setText(gameAlbumListVo.getTitle());
        try {
            viewHolder.mTvGameCollectionTitle.setTextColor(Color.parseColor(gameAlbumListVo.getTitle_color()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewHolder.mLlBoutiqueGamesList.removeAllViews();

        for (int i = 0; i < gameAlbumListVo.getGame_items().size(); i++) {
            GameAlbumListVo.GameItem gameItem = gameAlbumListVo.getGame_items().get(i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = (i == 0) ? (int) (14 * density) : 0;
            int rightMargin = (i == gameAlbumListVo.getGame_items().size() - 1) ? (int) (14 * density) : (int) (8 * density);
            int topMargin = (int) (3 * density);
            int bottomMargin = (int) (3 * density);
            params.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
            viewHolder.mLlBoutiqueGamesList.addView(createGameItem(gameItem), params);
        }
    }

    private View createGameItem(GameAlbumListVo.GameItem gameItem) {
        int game_type = gameItem.getGame_type();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_collection_game_list, null);

        ImageView mIvGameIcon = view.findViewById(R.id.iv_game_icon);
        FrameLayout mFlSubTag = view.findViewById(R.id.fl_sub_tag);
        TextView mTvSubTag = view.findViewById(R.id.tv_sub_tag);
        TextView mTvGameName = view.findViewById(R.id.tv_game_name);
        TextView mTvGameType = view.findViewById(R.id.tv_game_type);

        LinearLayout mLlDiscountTag = view.findViewById(R.id.ll_discount_tag);
        TextView mTvGameDiscount = view.findViewById(R.id.tv_game_discount);

        TextView mTvGameAction = view.findViewById(R.id.tv_game_action);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(20 * density);
        gd.setColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
        mTvGameAction.setBackground(gd);

        if (gameItem != null) {
            GlideUtils.loadRoundImage(mContext, gameItem.getGameicon(), mIvGameIcon);
            mTvGameName.setText(gameItem.getGamename());
            mTvGameType.setText(gameItem.getGenre_str());

            mTvSubTag.setText(gameItem.getGame_label());
            mFlSubTag.setVisibility(TextUtils.isEmpty(gameItem.getGame_label()) ? View.GONE : View.VISIBLE);

            mLlDiscountTag.setVisibility(View.VISIBLE);
            if (gameItem.showDiscount() == 0) {
                mLlDiscountTag.setVisibility(View.GONE);
            } else if(gameItem.showDiscount() == 1){
                mTvGameDiscount.setText(String.valueOf(gameItem.getDiscount()));
                mLlDiscountTag.setBackgroundResource(R.mipmap.ic_discount_tag);
            }else if(gameItem.showDiscount() == 2){
                mTvGameDiscount.setText(String.valueOf(gameItem.getFlash_discount()));
                mLlDiscountTag.setBackgroundResource(R.mipmap.ic_discount_tag_2);
            }else{
                mLlDiscountTag.setVisibility(View.VISIBLE);
            }

            //BT默认不显示折扣
            if (game_type == 1) {
                mLlDiscountTag.setVisibility(View.GONE);
            } else {
                mFlSubTag.setVisibility(View.GONE);
            }

            if (game_type == 3) {
                mTvGameAction.setText("开始玩");
            }

            view.setOnClickListener(v -> {
                if (_mFragment != null) {
                    _mFragment.goGameDetail(gameItem.getGameid(), gameItem.getGame_type());
                }
            });
        }
        return view;
    }


    public class ViewHolder extends AbsHolder {
        private FrameLayout mLlRootview;
        private ImageView mIvImgBg;
        private TextView mTvGameCollectionTitle;
        private HorizontalScrollView mGameBoutiqueList;
        private LinearLayout mLlBoutiqueGamesList;

        public ViewHolder(View view) {
            super(view);
            mLlRootview = itemView.findViewById(R.id.ll_rootview);
            mIvImgBg = itemView.findViewById(R.id.iv_img_bg);
            mTvGameCollectionTitle = itemView.findViewById(R.id.tv_game_collection_title);
            mGameBoutiqueList = itemView.findViewById(R.id.game_boutique_list);
            mLlBoutiqueGamesList = itemView.findViewById(R.id.ll_boutique_games_list);

        }
    }
}
