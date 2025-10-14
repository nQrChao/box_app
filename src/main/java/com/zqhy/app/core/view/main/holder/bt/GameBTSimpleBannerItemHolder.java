package com.zqhy.app.core.view.main.holder.bt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chaoji.im.glide.GlideApp;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.bt.MainBTBannerListVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideRoundTransform;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/8/20-10:49
 * @description
 */
public class GameBTSimpleBannerItemHolder extends BaseItemHolder<MainBTBannerListVo, GameBTSimpleBannerItemHolder.ViewHolder> {
    public GameBTSimpleBannerItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_bt_simple_banner;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainBTBannerListVo item) {
        ViewGroup.LayoutParams params = holder.mLlImageContainer.getLayoutParams();


        String img = item.getItem().getPic();
        int radius = 6;
        //        GlideUtils.loadRoundImage(mContext, img, holder.mImage, R.mipmap.img_placeholder_v_1, radius);

        GlideApp.with(mContext)
                .asBitmap()
                .load(img)
                .placeholder(R.mipmap.img_placeholder_v_1)
                .transform(new GlideRoundTransform(mContext, 6))
                .centerCrop()
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        if (bitmap != null) {
                            if (params != null && params instanceof ViewGroup.MarginLayoutParams) {
                                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) params;
                                lp.width = ScreenUtil.getScreenWidth(mContext) - lp.leftMargin - lp.rightMargin;
                                lp.height = lp.width * bitmap.getHeight() / bitmap.getWidth();
                                holder.mLlImageContainer.setLayoutParams(lp);
                            }
                            holder.mImage.setImageBitmap(bitmap);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable drawable) {

                    }
                });


        float density = ScreenUtil.getScreenDensity(mContext);
        //设置阴影
        GradientDrawable shadowGd = new GradientDrawable();
        shadowGd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        shadowGd.setColors(new int[]{Color.parseColor("#00000000"), Color.parseColor("#FF000000")});
        shadowGd.setCornerRadius(radius * density);
        holder.mLayoutShadow.setBackground(shadowGd);
        holder.mTvGameDes.setText(item.getItem().getTitle());

        holder.mLlImageContainer.setOnClickListener(view -> {
            appJump(item.getItem());
        });
    }


    public class ViewHolder extends AbsHolder {
        private RelativeLayout mLlImageContainer;
        private ImageView      mImage;
        private FrameLayout    mLayoutShadow;
        private TextView       mTvGameDes;

        public ViewHolder(View view) {
            super(view);
            mLlImageContainer = findViewById(R.id.ll_image_container);
            mImage = findViewById(R.id.image);
            mLayoutShadow = findViewById(R.id.layout_shadow);
            mTvGameDes = findViewById(R.id.tv_game_des);

        }
    }
}
