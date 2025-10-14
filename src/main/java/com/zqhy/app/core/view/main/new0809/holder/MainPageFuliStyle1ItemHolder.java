package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.flexbox.FlexboxLayout;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.MainFuliStyle1Vo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.StarsView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2021/8/11 0011-16:27
 * @description
 */
public class MainPageFuliStyle1ItemHolder extends BaseItemHolder<MainFuliStyle1Vo, MainPageFuliStyle1ItemHolder.ViewHolder> {

    public MainPageFuliStyle1ItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_page_fulifu_style_1;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainFuliStyle1Vo item) {
        holder.mTvTitle.setText(item.title);
        if (item.data_list == null || item.data_list.isEmpty()) {
            holder.mLlItemContainer.setVisibility(View.GONE);
        } else {
            holder.mLlItemContainer.setVisibility(View.VISIBLE);
            holder.mLlContainer.removeAllViews();
            Map<Integer, View> viewMap = new HashMap<>();
            View lastItemView = null;
            for (int i = 0; i < item.data_list.size(); i++) {
                MainFuliStyle1Vo.DataBean gameInfoVo = item.data_list.get(i);
                View itemView = createItemHeaderView(gameInfoVo, viewMap);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER_VERTICAL;
                params.rightMargin = ScreenUtil.dp2px(mContext, 8);
                if (i == 0) {
                    params.leftMargin = ScreenUtil.dp2px(mContext, 10);
                }
                if (gameInfoVo.getGameid() == item.getLastIndexItemGameId()) {
                    lastItemView = itemView;
                }
                holder.mLlContainer.addView(itemView, params);

            }
            for (Integer gameid : viewMap.keySet()) {
                View target = viewMap.get(gameid);
                target.setOnClickListener(v -> {
                    item.setLastIndexItemGameId(gameid);
                    onHeaderItemClick(holder, v, viewMap);
                });
            }
            /*if (lastItemView != null) {
                lastItemView.performClick();
            }*/
            if (item.data_list.size() > 0){
                int selectCount = (int) Math.round(Math.random() * (item.data_list.size() - 1));
                if (selectCount > 5) selectCount = 5;
                int gameid = item.data_list.get(selectCount).getGameid();
                item.setLastIndexItemGameId(gameid);
                View target = viewMap.get(gameid);
                onHeaderItemClick(holder, target, viewMap);
            }
        }
    }


    private View createItemHeaderView(MainFuliStyle1Vo.DataBean gameInfoVo, Map<Integer, View> viewMap) {
        FrameLayout layout = new FrameLayout(mContext);
        View bigRound = new View(mContext);
        bigRound.setId(R.id.fuli_style_id_big_round);
        FrameLayout.LayoutParams bigRoundParams = new FrameLayout.LayoutParams(ScreenUtil.dp2px(mContext, 86),
                ScreenUtil.dp2px(mContext, 86));
        bigRoundParams.gravity = Gravity.CENTER;
        bigRound.setLayoutParams(bigRoundParams);
        try {
            GradientDrawable gd = new GradientDrawable();
            gd.setShape(GradientDrawable.OVAL);
            gd.setColor(Color.parseColor(gameInfoVo.game_icon_big_color));
            bigRound.setBackground(gd);
        } catch (Exception e) {
            e.printStackTrace();
        }


        View middleRound = new View(mContext);
        middleRound.setId(R.id.fuli_style_id_middle_round);
        FrameLayout.LayoutParams middleRoundParams = new FrameLayout.LayoutParams(ScreenUtil.dp2px(mContext, 75),
                ScreenUtil.dp2px(mContext, 75));
        middleRoundParams.gravity = Gravity.CENTER;
        middleRound.setLayoutParams(middleRoundParams);
        try {
            GradientDrawable gd = new GradientDrawable();
            gd.setShape(GradientDrawable.OVAL);
            gd.setColor(Color.parseColor(gameInfoVo.game_icon_middle_color));
            middleRound.setBackground(gd);
        } catch (Exception e) {
            e.printStackTrace();
        }


        ImageView mImage = new ImageView(mContext);
        mImage.setId(R.id.fuli_style_id_image);
        FrameLayout.LayoutParams imageParams = new FrameLayout.LayoutParams(ScreenUtil.dp2px(mContext, 54),
                ScreenUtil.dp2px(mContext, 54));
        imageParams.gravity = Gravity.CENTER;
        mImage.setLayoutParams(imageParams);
        GlideUtils.loadCircleImage(mContext, gameInfoVo.getGameicon(), mImage, R.mipmap.ic_placeholder, 4, R.color.white);

        layout.addView(bigRound);
        layout.addView(middleRound);
        layout.addView(mImage);

        bigRound.setVisibility(View.GONE);
        middleRound.setVisibility(View.GONE);

        layout.setTag(R.id.tag_first, gameInfoVo);
        viewMap.put(gameInfoVo.getGameid(), layout);
        return layout;
    }

    private void onHeaderItemClick(ViewHolder holder, View view, Map<Integer, View> viewMap) {
        for (Integer gameid : viewMap.keySet()) {
            View target = viewMap.get(gameid);
            boolean isCheck = view == target;
            View bigRound = target.findViewById(R.id.fuli_style_id_big_round);
            View middleRound = target.findViewById(R.id.fuli_style_id_middle_round);
            View mImage = target.findViewById(R.id.fuli_style_id_image);
            if (isCheck) {
                bigRound.setVisibility(View.VISIBLE);
                middleRound.setVisibility(View.VISIBLE);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mImage.getLayoutParams();
                if (params != null) {
                    params.width = ScreenUtil.dp2px(mContext, 64);
                    params.height = ScreenUtil.dp2px(mContext, 64);
                    mImage.setLayoutParams(params);
                }
                MainFuliStyle1Vo.DataBean gameInfoVo = (MainFuliStyle1Vo.DataBean) view.getTag(R.id.tag_first);
                setViewContainer(gameInfoVo, holder);
            } else {
                bigRound.setVisibility(View.GONE);
                middleRound.setVisibility(View.GONE);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mImage.getLayoutParams();
                if (params != null) {
                    params.width = ScreenUtil.dp2px(mContext, 54);
                    params.height = ScreenUtil.dp2px(mContext, 54);
                    mImage.setLayoutParams(params);
                }
            }
        }
    }

    private void setViewContainer(MainFuliStyle1Vo.DataBean gameInfoVo, ViewHolder holder) {
        holder.mTvGameTitle.setText(gameInfoVo.getTitle());
        holder.mTvSubGameTitle.setText(gameInfoVo.getTitle2());
        try {
            GradientDrawable gd = new GradientDrawable();
            gd.setShape(GradientDrawable.RECTANGLE);
            gd.setCornerRadius(ScreenUtil.dp2px(mContext, 38));
            gd.setColor(Color.parseColor(gameInfoVo.game_icon_big_color));
            holder.mGameIconBigRound.setBackground(gd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            GradientDrawable gd = new GradientDrawable();
            gd.setShape(GradientDrawable.RECTANGLE);
            gd.setCornerRadius(ScreenUtil.dp2px(mContext, 28));
            gd.setColor(Color.parseColor(gameInfoVo.game_icon_middle_color));
            holder.mGameIconMiddleRound.setBackground(gd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int showDiscount = gameInfoVo.showDiscount();
        if (showDiscount == 1 || showDiscount == 2) {
            holder.mTvVipTag.setVisibility(View.VISIBLE);
            if (showDiscount == 1){
                holder.mTvVipTag.setText(gameInfoVo.getDiscount() + "折");
            }else if (showDiscount == 2){
                holder.mTvVipTag.setText(gameInfoVo.getFlash_discount() + "折");
            }

            GradientDrawable gdTemp1 = new GradientDrawable();
            gdTemp1.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
            int radius = ScreenUtil.dp2px(mContext, 100);
            gdTemp1.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, 0, 0});
            gdTemp1.setColors(new int[]{Color.parseColor("#FE6631"), Color.parseColor("#EF0F16")});
            holder.mTvVipTag.setBackground(gdTemp1);
        } else {
            holder.mTvVipTag.setVisibility(View.GONE);
        }


        GlideUtils.loadRoundImage(mContext, gameInfoVo.getGameicon(), holder.mIvGameIcon, R.mipmap.ic_placeholder, 20);
        holder.mTvGameName.setText(gameInfoVo.getGamename());

        StringBuilder sb = new StringBuilder();
        int startIndex1 = sb.length();
        sb.append(gameInfoVo.getGenre_str());
        int endIndex1 = sb.length();
        sb.append("  ");
        int startIndex2 = sb.length();
        sb.append(gameInfoVo.getServer_str());
        int endIndex2 = sb.length();
        sb.append("  ");
        SpannableString ss = new SpannableString(sb.toString());
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), startIndex1, endIndex1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#59BAF6")), startIndex2, endIndex2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.mTvGameInfo.setText(ss);
        if (!TextUtils.isEmpty(gameInfoVo.getOtherGameName())){//游戏后缀
            holder.mTvGameSuffix.setVisibility(View.VISIBLE);
            holder.mTvGameSuffix.setText(gameInfoVo.getOtherGameName());
        }else {
            holder.mTvGameSuffix.setVisibility(View.GONE);
        }
        if (gameInfoVo.getIs_first() == 1) {
            holder.mTvGameFirst.setVisibility(View.VISIBLE);
            holder.mTvGameFirst.setText("首发");

            GradientDrawable gd = new GradientDrawable();
            gd.setShape(GradientDrawable.RECTANGLE);
            gd.setCornerRadius(ScreenUtil.dp2px(mContext, 2));
            gd.setColor(ContextCompat.getColor(mContext, R.color.color_333333));
            holder.mTvGameFirst.setBackground(gd);

        } else {
            holder.mTvGameFirst.setVisibility(View.GONE);
        }

        boolean hasLineTag = false;
        int tagSize = 2;
        holder.mFlexBoxLayout.setVisibility(View.VISIBLE);
        holder.mFlexBoxLayout.removeAllViews();
        /*if (gameInfoVo.getCoupon_amount() > 0) {
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
            params.topMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
            holder.mFlexBoxLayout.addView(createSpLabelView("送" + (int) (gameInfoVo.getCoupon_amount()) + "元券"), params);
            tagSize--;
            hasLineTag = true;
        }*/
        if (gameInfoVo.getGame_labels() != null && !gameInfoVo.getGame_labels().isEmpty()) {
            List<GameInfoVo.GameLabelsBean> list = gameInfoVo.getGame_labels().size() > tagSize ? gameInfoVo.getGame_labels().subList(0, tagSize) : gameInfoVo.getGame_labels();
            for (GameInfoVo.GameLabelsBean labelsBean : list) {
                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                params.rightMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                params.topMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                holder.mFlexBoxLayout.addView(createLabelView(labelsBean), params);
            }
            hasLineTag = true;
        }
        if (!hasLineTag) {
            holder.mFlexBoxLayout.setVisibility(View.GONE);
        }
        holder.mFlexBoxLayout.setOnClickListener(v -> {
            if (_mFragment != null) _mFragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
        });

        holder.mLlStarContainer.removeAllViews();
        for (int i = 0; i < gameInfoVo.start_num; i++) {
            StarsView starsView = new StarsView(mContext);
            starsView.setAngleColor(Color.parseColor("#FF6D1B"));
            starsView.setAngleNum(5);
            starsView.setAngleStyle(StarsView.STYLE_FILL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = ScreenUtil.dp2px(mContext, 2);
            params.rightMargin = ScreenUtil.dp2px(mContext, 2);

            holder.mLlStarContainer.addView(starsView, params);
        }
        holder.mTvGamePlayCount.setText(CommonUtils.formatNumber(gameInfoVo.getPlay_count()) + "人加入游戏");

        holder.mLlGameContainer.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
            }
        });
    }

    private View createSpLabelView(String text) {
        TextView textView = new TextView(mContext);
        textView.setText(text);
        textView.setIncludeFontPadding(false);
        textView.setTextColor(Color.parseColor("#333333"));
        textView.setTextSize(11.5f);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(ScreenUtil.dp2px(mContext, 4));
        gd.setColor(Color.parseColor("#FFBE00"));
        textView.setBackground(gd);

        textView.setPadding(ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2), ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2));

        return textView;
    }

    private View createLabelView(GameInfoVo.GameLabelsBean labelsBean) {
        TextView textView = new TextView(mContext);
        textView.setText(labelsBean.getLabel_name());
        textView.setIncludeFontPadding(false);
        textView.setTextColor(Color.parseColor(labelsBean.getText_color()));
        textView.setTextSize(11.5f);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(ScreenUtil.dp2px(mContext, 4));
        gd.setStroke(ScreenUtil.dp2px(mContext, 1), Color.parseColor(labelsBean.getText_color()));
        textView.setBackground(gd);

        textView.setPadding(ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2), ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2));

        return textView;
    }

    public class ViewHolder extends AbsHolder {
        private TextView      mTvTitle;
        private LinearLayout  mLlContainer;
        private TextView      mTvGameTitle;
        private TextView      mTvSubGameTitle;
        private View          mGameIconBigRound;
        private View          mGameIconMiddleRound;
        private ImageView     mIvGameIcon;
        private TextView      mTvGameName;
        private TextView      mTvGameInfo;
        private TextView      mTvGameSuffix;
        private FlexboxLayout mFlexBoxLayout;
        private TextView      mTvGamePlayCount;
        private TextView      mBtnPlay;
        private LinearLayout  mLlStarContainer;
        private TextView      mTvGameFirst;
        private LinearLayout  mLlItemContainer;
        private LinearLayout  mLlGameContainer;
        private TextView      mTvVipTag;

        public ViewHolder(View view) {
            super(view);
            mTvTitle = findViewById(R.id.tv_title);
            mLlContainer = findViewById(R.id.ll_container);
            mTvGameTitle = findViewById(R.id.tv_game_title);
            mTvSubGameTitle = findViewById(R.id.tv_sub_game_title);
            mGameIconBigRound = findViewById(R.id.game_icon_big_round);
            mGameIconMiddleRound = findViewById(R.id.game_icon_middle_round);
            mIvGameIcon = findViewById(R.id.iv_game_icon);
            mTvGameName = findViewById(R.id.tv_game_name);
            mTvGameSuffix = findViewById(R.id.tv_game_suffix);
            mTvGameInfo = findViewById(R.id.tv_game_info);
            mFlexBoxLayout = findViewById(R.id.flex_box_layout);
            mLlStarContainer = findViewById(R.id.ll_star_container);
            mTvGamePlayCount = findViewById(R.id.tv_game_play_count);
            mBtnPlay = findViewById(R.id.btn_play);
            mTvGameFirst = findViewById(R.id.tv_game_first);
            mLlItemContainer = findViewById(R.id.ll_item_container);
            mLlGameContainer = findViewById(R.id.ll_game_container);
            mTvVipTag = findViewById(R.id.tv_vip_tag);


            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(ScreenUtil.dp2px(mContext, 100));
            gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
            gd.setColors(new int[]{Color.parseColor("#55A8FE"), Color.parseColor("#4E77FE")});
            mBtnPlay.setBackground(gd);
        }
    }
}
