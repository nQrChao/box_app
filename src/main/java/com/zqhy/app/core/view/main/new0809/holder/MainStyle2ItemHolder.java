package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chaoji.im.glide.GlideApp;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.item.CommonStyle1DataBeanVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Administrator
 * @date 2021/8/11 0011-12:08
 * @description
 */
public class MainStyle2ItemHolder extends BaseItemHolder<CommonStyle1DataBeanVo, MainStyle2ItemHolder.ViewHolder> {
    public MainStyle2ItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_page_style_2;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CommonStyle1DataBeanVo item) {
        if (!TextUtils.isEmpty(item.module_title_icon)){
            holder.mIvTitleImage.setVisibility(View.VISIBLE);
            GlideUtils.loadNormalImage(mContext, item.module_title_icon, holder.mIvTitleImage);
        }else {
            holder.mIvTitleImage.setVisibility(View.INVISIBLE);
        }
        if (TextUtils.isEmpty(item.module_title) && TextUtils.isEmpty(item.module_title_two)) {
            holder.mTvTitle.setVisibility(View.GONE);
        } else {
            holder.mTvTitle.setVisibility(View.VISIBLE);
            String title = "";
            if (!TextUtils.isEmpty(item.module_title)){
                title += item.module_title;
            }
            if (!TextUtils.isEmpty(item.module_title_two)){
                title += item.module_title_two;
            }
            SpannableString spannableString = new SpannableString(title);
            if (!TextUtils.isEmpty(item.module_title) && !TextUtils.isEmpty(item.module_title_color)){
                try {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(item.module_title_color)), 0, item.module_title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }catch (Exception e){}
            }
            if (!TextUtils.isEmpty(item.module_title_two) && !TextUtils.isEmpty(item.module_title_two_color)){
                try {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(item.module_title_two_color)), title.length() - item.module_title_two.length(), title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }catch (Exception e){}
            }
            holder.mTvTitle.setText(spannableString);
        }
        if (!TextUtils.isEmpty(item.module_sub_title)){
            holder.mTvDescription.setVisibility(View.VISIBLE);
            holder.mTvDescription.setText(item.module_sub_title);
            try {
                if (!TextUtils.isEmpty(item.module_sub_title_color)) holder.mTvDescription.setTextColor(Color.parseColor(item.module_sub_title_color));
            }catch (Exception e){}
        }else {
            holder.mTvDescription.setVisibility(View.GONE);
        }
        if (item.additional != null){
            if (!TextUtils.isEmpty(item.additional.icon)){
                holder.mTvMore.setVisibility(View.GONE);
                holder.mIvMore.setVisibility(View.VISIBLE);
                //GlideUtils.loadGameIcon(mContext, item.additional.icon, holder.mIvMore);
                GlideApp.with(mContext).asBitmap()
                        .load(item.additional.icon)
                        .placeholder(R.mipmap.img_placeholder_h)
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                                if (bitmap != null) {
                                    int imageHeight = ScreenUtil.dp2px(mContext, 28);
                                    int imageWidth = imageHeight * bitmap.getWidth() / bitmap.getHeight();

                                    if (holder.mIvMore.getLayoutParams() != null) {
                                        ViewGroup.LayoutParams params = holder.mIvMore.getLayoutParams();
                                        params.width = imageWidth;
                                        params.height = imageHeight;
                                        holder.mIvMore.setLayoutParams(params);
                                    }
                                    holder.mIvMore.setImageBitmap(bitmap);
                                }
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable drawable) {

                            }
                        });

                holder.mIvMore.setOnClickListener(v -> {
                    appJump(item.additional.getPage_type(), item.additional.getParam());
                });
            }else {
                holder.mTvMore.setVisibility(View.VISIBLE);
                holder.mIvMore.setVisibility(View.GONE);
                holder.mTvMore.setText(item.additional.text);
                try {
                    if (!TextUtils.isEmpty(item.additional.textcolor)) holder.mTvMore.setTextColor(Color.parseColor(item.additional.textcolor));
                }catch (Exception e){}
                holder.mTvMore.setOnClickListener(v -> {
                    appJump(item.additional.getPage_type(), item.additional.getParam());
                });
            }
        }else {
            holder.mTvMore.setVisibility(View.GONE);
        }

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)holder.mViewProgress.getLayoutParams();
        layoutParams.width = ScreenUtil.dp2px(mContext, 39) / item.data.size();
        layoutParams.leftMargin = 0;
        holder.mViewProgress.setLayoutParams(layoutParams);

        if (item.data.size() > 1){
            holder.mLlProgress.setVisibility(View.VISIBLE);
        }else {
            holder.mLlProgress.setVisibility(View.GONE);
        }

        holder.mLlContainer.removeAllViews();
        for (GameInfoVo gameInfoVo : item.data) {
            View itemView = createGameItem(gameInfoVo);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 50)) / 4 , LinearLayout.LayoutParams.MATCH_PARENT);
            itemView.setOnClickListener(v -> {
                if (_mFragment != null) {
                    _mFragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
                }
            });
            holder.mLlContainer.addView(itemView, params);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.mScrollView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                layoutParams.leftMargin = ((int)((float)scrollX / ((holder.mLlContainer.getWidth()) - holder.mScrollView.getWidth()) * (ScreenUtil.dp2px(mContext, 39)- holder.mViewProgress.getWidth())));
                holder.mViewProgress.setLayoutParams(layoutParams);
            });
        }
        holder.mScrollView.scrollTo(0, 0);
    }

    private View createGameItem(GameInfoVo gameInfoVo){
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_main_style_game_list_item, null);
        ImageView mIvGameIcon = itemView.findViewById(R.id.iv_game_icon);
        TextView mTvGameTag = itemView.findViewById(R.id.iv_game_tag);
        TextView mTvGameName = itemView.findViewById(R.id.tv_game_name);
        TextView mTvGameSuffix = itemView.findViewById(R.id.tv_game_suffix);
        //图标
        GlideUtils.loadGameIcon(mContext, gameInfoVo.getGameicon(), mIvGameIcon);
        //游戏名
        mTvGameName.setText(gameInfoVo.getGamename());
        mTvGameSuffix.setText(gameInfoVo.getJingxuan_label());

        if (!TextUtils.isEmpty(gameInfoVo.getVip_label())) {
            mTvGameTag.setVisibility(View.VISIBLE);
            mTvGameTag.setText(gameInfoVo.getVip_label());
        }else {
            if (gameInfoVo.showDiscount() == 1 || gameInfoVo.showDiscount() == 2){
                if (gameInfoVo.showDiscount() == 1){
                    if (gameInfoVo.getDiscount() <= 0 || gameInfoVo.getDiscount() >= 10) {
                        mTvGameTag.setVisibility(View.GONE);
                    }else {
                        mTvGameTag.setVisibility(View.VISIBLE);
                        mTvGameTag.setText(gameInfoVo.getDiscount() + "折");
                    }
                }else if (gameInfoVo.showDiscount() == 2){
                    if (gameInfoVo.getFlash_discount() <= 0 || gameInfoVo.getFlash_discount() >= 10) {
                        mTvGameTag.setVisibility(View.GONE);
                    }else {
                        mTvGameTag.setVisibility(View.VISIBLE);
                        mTvGameTag.setText(gameInfoVo.getFlash_discount() + "折");
                    }
                }
            }else {
                mTvGameTag.setVisibility(View.GONE);
            }
        }
        GradientDrawable gd2 = new GradientDrawable();
        float radius = ScreenUtil.dp2px(mContext, 100);
        gd2.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, 0, 0});
        gd2.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        gd2.setColors(new int[]{Color.parseColor("#4E76FF"), Color.parseColor("#4E76FF")});
        mTvGameTag.setBackground(gd2);

        return itemView;
    }

    public class ViewHolder extends AbsHolder {

        private ImageView mIvTitleImage;
        private TextView mTvTitle;
        private TextView mTvMore;
        private ImageView mIvMore;
        private TextView mTvDescription;
        private LinearLayout mLlProgress;
        private View mViewProgress;
        private HorizontalScrollView mScrollView;
        private LinearLayout mLlContainer;

        public ViewHolder(View view) {
            super(view);
            mIvTitleImage = view.findViewById(R.id.iv_title_image);
            mTvTitle = view.findViewById(R.id.tv_title);
            mTvMore = view.findViewById(R.id.tv_more);
            mIvMore = view.findViewById(R.id.iv_more);
            mTvDescription = view.findViewById(R.id.tv_description);
            mLlProgress = findViewById(R.id.ll_progress);
            mViewProgress = findViewById(R.id.view_progress);
            mScrollView = findViewById(R.id.scrollView);
            mLlContainer = findViewById(R.id.ll_container);
        }
    }
}
