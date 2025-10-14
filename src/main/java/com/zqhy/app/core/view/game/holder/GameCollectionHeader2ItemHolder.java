package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chaoji.im.glide.GlideApp;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameCollectionHeader2Vo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/8/24-9:30
 * @description
 */
public class GameCollectionHeader2ItemHolder extends AbsItemHolder<GameCollectionHeader2Vo, GameCollectionHeader2ItemHolder.ViewHolder> {

    public GameCollectionHeader2ItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_collection_header_2;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameCollectionHeader2Vo item) {
        holder.mIvBack.setOnClickListener(view -> {
            if (_mFragment != null) {
                _mFragment.pop();
            }
        });
        GlideApp.with(mContext)
                .asBitmap()
                .load(item.getImage())
                .placeholder(R.mipmap.img_placeholder_v_1)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        try {
                            holder.mImage.setImageBitmap(bitmap);

                            int width = ScreenUtil.getScreenWidth(mContext);
                            int height = (width * bitmap.getHeight()) / bitmap.getWidth();

                            ViewGroup.LayoutParams params = holder.mRlTop.getLayoutParams();
                            params.width = width;
                            params.height = height;
                            holder.mRlTop.setLayoutParams(params);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable drawable) {

                    }
                });

        if (TextUtils.isEmpty(item.getDescription())){
            holder.mTvDescription.setVisibility(View.GONE);
        }else {
            holder.mTvDescription.setVisibility(View.VISIBLE);
        }
        holder.mTvDescription.setText(item.getDescription());
        holder.mTvTitle.setText(item.getTitle());
    }

    public class ViewHolder extends AbsHolder {
        private ImageView mIvBack;
        private ImageView mImage;
        private TextView  mTvTitle;
        private TextView  mTvDescription;
        private RelativeLayout mRlTop;

        public ViewHolder(View view) {
            super(view);
            mImage = findViewById(R.id.image);
            mTvTitle = findViewById(R.id.tv_title);
            mTvDescription = findViewById(R.id.tv_description);
            mIvBack = findViewById(R.id.iv_back);
            mRlTop = findViewById(R.id.rl_top);

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(6 * ScreenUtil.getScreenDensity(mContext));
            gd.setColor(Color.parseColor("#FFF6F1"));
            mTvDescription.setBackground(gd);
        }
    }
}
