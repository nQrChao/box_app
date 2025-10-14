package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.google.android.flexbox.FlexboxLayout;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.item.LunboGameDataBeanVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideRoundTransformNew;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/12 0012-14:08
 * @description
 */
public class MainPageJingXuanGameBannerItemHolder extends BaseItemHolder<LunboGameDataBeanVo.DataBean, MainPageJingXuanGameBannerItemHolder.ViewHolder> {

    public MainPageJingXuanGameBannerItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_page_game_banner;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected boolean INFLATER_TYPE_NEED_ROOT() {
        return false;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull LunboGameDataBeanVo.DataBean item) {
        ViewGroup.MarginLayoutParams cardParams = (ViewGroup.MarginLayoutParams) holder.mLlContainer.getLayoutParams();
        if (cardParams != null) {
            cardParams.width = ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 10) * 3;
            if (item.getIndexPosition() == 0) {
                cardParams.leftMargin = ScreenUtil.dp2px(mContext, 10);
            } else {
                cardParams.leftMargin = ScreenUtil.dp2px(mContext, 0);
            }
            holder.mLlContainer.setLayoutParams(cardParams);
        }

        //        float scale = 1.54f;
        //        ViewGroup.LayoutParams layoutParams = holder.mIvGameImage.getLayoutParams();
        //        if (layoutParams != null) {
        //            int targetHeight = (int) ((holder.mIvGameImage.getRight() - holder.mIvGameImage.getLeft()) / scale);
        //            layoutParams.height = targetHeight;
        //            holder.mIvGameImage.setLayoutParams(layoutParams);
        //        }

        //        GlideUtils.loadImage(mContext, item.getPic(), holder.mIvGameImage, R.mipmap.img_placeholder_v_2, 10);
        int width = ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 10) * 3;
        int height = (int) (width / 1.54f);

        GradientDrawable gdTemp1 = new GradientDrawable();
        gdTemp1.setSize(width, height);
        gdTemp1.setColor(Color.parseColor("#F2F2F2"));
        GlideApp.with(mContext).asBitmap()
                .load(item.getPic())
                .override(width, height)
                .placeholder(gdTemp1)
                .transform(new GlideRoundTransformNew(mContext, 10))
                .into(holder.mIvGameImage);
        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mIvGameIcon);

        try {
            GradientDrawable gd = new GradientDrawable();
            gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
            gd.setCornerRadius(ScreenUtil.dp2px(mContext, 10));
            gd.setColors(item.getBgColors());
            holder.mLlContainer.setBackground(gd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.mTvGameName.setText(item.getGamename());
        int showDiscount = item.showDiscount();
        if (showDiscount == 1 || showDiscount == 2) {
            holder.mTvGameDiscount.setVisibility(View.VISIBLE);
            if (showDiscount == 1){
                holder.mTvGameDiscount.setText(item.getDiscount() + "折");
            }else if (showDiscount == 2){
                holder.mTvGameDiscount.setText(item.getFlash_discount() + "折");
            }
            GradientDrawable gd = new GradientDrawable();
            gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            gd.setCornerRadius(ScreenUtil.dp2px(mContext, 30));
            gd.setColors(new int[]{Color.parseColor("#FF8B06"), Color.parseColor("#FF1313")});
            holder.mTvGameDiscount.setBackground(gd);
        } else {
            holder.mTvGameDiscount.setVisibility(View.GONE);
        }
        holder.mTvGameGenre.setText(item.getGenre_str());
        if (item.getIs_first() == 1) {
            holder.mTvGameFirst.setVisibility(View.VISIBLE);
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(ScreenUtil.dp2px(mContext, 2));
            gd.setStroke(ScreenUtil.dp2px(mContext, 1), ContextCompat.getColor(mContext, R.color.white));
            holder.mTvGameFirst.setBackground(gd);
        } else {
            holder.mTvGameFirst.setVisibility(View.GONE);
        }

        holder.mLlTuijianContainer.setVisibility(item.tuijian_flag ? View.VISIBLE : View.GONE);


        holder.mTvInfoBottom.setVisibility(View.GONE);
        boolean hasLineTag = false;
        holder.mFlexBoxLayout.setVisibility(View.VISIBLE);
        holder.mFlexBoxLayout.removeAllViews();
        int tagSize = 2;
        /*if (item.getCoupon_amount() > 0) {
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
            params.topMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
            holder.mFlexBoxLayout.addView(createLabelView("送" + (int) (item.getCoupon_amount()) + "元券"), params);
            hasLineTag = true;
            tagSize--;
        }*/

        if (item.getGame_labels() != null && !item.getGame_labels().isEmpty()) {
            List<GameInfoVo.GameLabelsBean> list = item.getGame_labels().size() > tagSize ? item.getGame_labels().subList(0, tagSize) : item.getGame_labels();
            for (GameInfoVo.GameLabelsBean labelsBean : list) {
                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                params.rightMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                params.topMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                holder.mFlexBoxLayout.addView(createLabelView(labelsBean.getLabel_name()), params);
            }
            hasLineTag = true;
        } else {
            holder.mTvInfoBottom.setVisibility(View.VISIBLE);
            holder.mTvInfoBottom.setText(item.getGame_summary());
        }

        if (!hasLineTag) {
            holder.mFlexBoxLayout.setVisibility(View.GONE);
        }
        holder.mFlexBoxLayout.setOnClickListener(v -> {
            if (_mFragment != null) _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
        });

        holder.mLlContainer.setOnClickListener(view -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
            }
        });
    }

    private View createLabelView(String label) {
        TextView textView = new TextView(mContext);
        textView.setText(label);
        textView.setIncludeFontPadding(false);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setTextSize(10f);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(ScreenUtil.dp2px(mContext, 2));
        gd.setColor(Color.parseColor("#1AFFFFFF"));
        textView.setBackground(gd);

        textView.setPadding(ScreenUtil.dp2px(mContext, 5), ScreenUtil.dp2px(mContext, 2), ScreenUtil.dp2px(mContext, 5), ScreenUtil.dp2px(mContext, 2));

        return textView;
    }

    public class ViewHolder extends AbsHolder {
        //        private CardView           mImageCardView;
        private AppCompatImageView mIvGameImage;
        private AppCompatImageView mIvGameIcon;
        private TextView           mTvGameName;
        private TextView           mTvGameDiscount;
        private TextView           mTvGameGenre;
        private TextView           mTvGameFirst;
        private FlexboxLayout      mFlexBoxLayout;
        private LinearLayout       mLlContainer;
        private LinearLayout       mLlTuijianContainer;
        private TextView           mTvInfoBottom;

        public ViewHolder(View view) {
            super(view);
            //            mImageCardView = findViewById(R.id.image_card_view);
            mLlContainer = findViewById(R.id.ll_container);
            mIvGameImage = findViewById(R.id.iv_game_image);
            mIvGameIcon = findViewById(R.id.iv_game_icon);
            mTvGameName = findViewById(R.id.tv_game_name);
            mTvGameDiscount = findViewById(R.id.tv_game_discount);
            mTvGameGenre = findViewById(R.id.tv_game_genre);
            mTvGameFirst = findViewById(R.id.tv_game_first);
            mFlexBoxLayout = findViewById(R.id.flex_box_layout);
            mLlTuijianContainer = findViewById(R.id.ll_tuijian_container);
            mTvInfoBottom = findViewById(R.id.tv_info_bottom);


            GradientDrawable gdTemp1 = new GradientDrawable();
            int radius = ScreenUtil.dp2px(mContext, 10);
            gdTemp1.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, 0, 0});
            gdTemp1.setColor(Color.WHITE);
            mLlTuijianContainer.setBackground(gdTemp1);
        }
    }
}
