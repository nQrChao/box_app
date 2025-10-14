package com.zqhy.app.core.view.main.new_game.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.google.android.flexbox.FlexboxLayout;
import com.zqhy.app.Setting;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.network.statistics.JiuYaoStatisticsApi;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/8/24-12:23
 * @description
 */
public class NewGameStartingItemHolder extends AbsItemHolder<GameInfoVo, NewGameStartingItemHolder.ViewHolder> {
    public NewGameStartingItemHolder(Context context) {
        super(context);
    }

    private boolean isMustHideLastButton;

    public NewGameStartingItemHolder(Context context, boolean isMustHideLastButton) {
        super(context);
        this.isMustHideLastButton = isMustHideLastButton;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_new_game_starting_game;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NewGameStartingItemHolder.ViewHolder viewHolder, @NonNull GameInfoVo gameInfoVo) {
        //图标
        GlideUtils.loadGameIcon(mContext, gameInfoVo.getGameicon(), viewHolder.mGameIconIV);

        int game_type = gameInfoVo.getGame_type();
        viewHolder.mLlRootview.setVisibility(View.VISIBLE);
        viewHolder.mLlRootview.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(gameInfoVo.getGameid(), game_type);
            }
            int position = gameInfoVo.getEventPosition();
            List<Integer> eventList = gameInfoVo.getEventList();
            if (eventList != null && eventList.size() > 0) {
                for (Integer event : eventList) {
                    switch (game_type) {
                        case 1:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(1, event, position);
                            break;
                        case 2:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(2, event, position);
                            break;
                        case 3:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(3, event, position);
                            break;
                        case 4:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(4, event, position);
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        //游戏名
        String gameName = gameInfoVo.getGamename();
        viewHolder.mTvGameName.setText(gameName);

        boolean hasLineTag = false;
        viewHolder.mFlexBoxLayout.removeAllViews();
        viewHolder.mFlexBoxLayout.setVisibility(View.VISIBLE);
        viewHolder.mTvInfoBottom.setVisibility(View.GONE);
        int tagSize = 3;
        /*if (gameInfoVo.getCoupon_amount() > 0) {
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
            params.topMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
            viewHolder.mFlexBoxLayout.addView(createSpLabelView("送" + (int) (gameInfoVo.getCoupon_amount()) + "元券"), params);
            tagSize--;
            hasLineTag = true;
        }*/
        if (gameInfoVo.getUnshare() == 1) {//专服
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = (int) (2 * ScreenUtil.getScreenDensity(mContext));
            params.topMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
            viewHolder.mFlexBoxLayout.addView(createLabelView("专服"), params);
            hasLineTag = true;
            tagSize--;
        }

        if (!BuildConfig.NEED_BIPARTITION){//头条包不显示加速标签
            if (Setting.HIDE_FIVE_FIGURE != 1) {
                if (gameInfoVo.getAccelerate_status() != 0) {
                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    params.rightMargin = (int) (2 * ScreenUtil.getScreenDensity(mContext));
                    params.topMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                    viewHolder.mFlexBoxLayout.addView(createLabelView("可加速"), params);
                    hasLineTag = true;
                    tagSize--;
                }
            }
        }

        if (Setting.REFUND_GAME_LIST != null && Setting.REFUND_GAME_LIST.size() > 0){
            for (int i = 0; i < Setting.REFUND_GAME_LIST.size(); i++) {
                if (Setting.REFUND_GAME_LIST.get(i).equals(String.valueOf(gameInfoVo.getGameid()))){
                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    params.rightMargin = (int) (2 * ScreenUtil.getScreenDensity(mContext));
                    params.topMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                    viewHolder.mFlexBoxLayout.addView(createLabelView("省心玩"), params);
                    hasLineTag = true;
                    tagSize--;
                }
            }
        }

        if (tagSize == 0) tagSize = 1;
        if (gameInfoVo.getGame_labels() != null && !gameInfoVo.getGame_labels().isEmpty()) {
            List<GameInfoVo.GameLabelsBean> list = gameInfoVo.getGame_labels().size() > tagSize ? gameInfoVo.getGame_labels().subList(0, tagSize) : gameInfoVo.getGame_labels();
            for (GameInfoVo.GameLabelsBean labelsBean : list) {
                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                params.rightMargin = (int) (2 * ScreenUtil.getScreenDensity(mContext));
                params.topMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                viewHolder.mFlexBoxLayout.addView(createLabelView(labelsBean), params);
            }
            hasLineTag = true;
        }
        if (hasLineTag) {
            viewHolder.mFlexBoxLayout.setVisibility(View.VISIBLE);
            viewHolder.mTvInfoBottom.setVisibility(View.GONE);
        }else {
            viewHolder.mFlexBoxLayout.setVisibility(View.GONE);
            viewHolder.mTvInfoBottom.setVisibility(View.VISIBLE);
            viewHolder.mTvInfoBottom.setText(gameInfoVo.getGame_summary());
        }
        viewHolder.mFlexBoxLayout.setOnClickListener(v -> {
            if (_mFragment != null) _mFragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
        });

        viewHolder.mTvGameFirstTag.setVisibility(View.GONE);
        if (gameInfoVo.isIs_reserve_status()) {//新游标签
            viewHolder.mLlGameReserveTag.setVisibility(View.VISIBLE);
        }else {
            viewHolder.mLlGameReserveTag.setVisibility(View.GONE);
            if (gameInfoVo.getIs_first() == 1){
                viewHolder.mTvGameFirstTag.setVisibility(View.VISIBLE);
                if (0 == CommonUtils.isTodayOrTomorrow(gameInfoVo.getOnline_time() * 1000)){
                    viewHolder.mTvGameFirstTag.setText(CommonUtils.friendlyTime3(gameInfoVo.getOnline_time() * 1000));
                }else {
                    viewHolder.mTvGameFirstTag.setText("首发");
                }
            }else {
                viewHolder.mTvGameFirstTag.setVisibility(View.GONE);
            }
        }

        if (TextUtils.isEmpty(gameInfoVo.getOtherGameName())){
            if (gameInfoVo.getPlay_count() > 0){
                viewHolder.mTvPlayCount.setVisibility(View.VISIBLE);
                viewHolder.mTvPlayCount.setText(CommonUtils.formatNumberType2(gameInfoVo.getPlay_count()) + "人玩过");
            }else {
                viewHolder.mTvPlayCount.setVisibility(View.GONE);
            }
        }else {
            viewHolder.mTvPlayCount.setVisibility(View.GONE);
        }

        viewHolder.mTvInfoMiddle.setVisibility(View.VISIBLE);
        viewHolder.mTvInfoMiddle.setTextColor(Color.parseColor("#999999"));
        String genreStr = gameInfoVo.getGenre_str();
        String str = genreStr.replace("•", " ");
        if (!TextUtils.isEmpty(gameInfoVo.getOtherGameName())){
            str = str + "•" + gameInfoVo.getOtherGameName();
        }
        viewHolder.mTvInfoMiddle.setText(str);

        setDiscountLabel(viewHolder, gameInfoVo);
    }

    private View createLabelView(GameInfoVo.GameLabelsBean labelsBean) {
        TextView textView = new TextView(mContext);
        textView.setText(labelsBean.getLabel_name());
        textView.setIncludeFontPadding(false);
        textView.setTextSize(10f);
        textView.setTextColor(Color.parseColor("#666666"));

        GradientDrawable gd = new GradientDrawable();
        gd.setColors(new int[]{Color.parseColor("#F5F5F5"), Color.parseColor("#F5F5F5")});
        gd.setCornerRadius(ScreenUtil.dp2px(mContext, 2));
        textView.setBackground(gd);

        textView.setPadding(ScreenUtil.dp2px(mContext, 5), ScreenUtil.dp2px(mContext, 2), ScreenUtil.dp2px(mContext, 5), ScreenUtil.dp2px(mContext, 2));

        return textView;
    }

    private View createLabelView(String labelsBean) {
        TextView textView = new TextView(mContext);
        textView.setText(labelsBean);
        textView.setIncludeFontPadding(false);
        textView.setTextSize(10f);
        textView.setTextColor(Color.parseColor("#4E76FF"));

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(ScreenUtil.dp2px(mContext, 2));
        gd.setColors(new int[]{Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF")});
        gd.setStroke(ScreenUtil.dp2px(mContext, 0.5F), Color.parseColor("#4E76FF"));
        textView.setBackground(gd);

        textView.setPadding(ScreenUtil.dp2px(mContext, 5), ScreenUtil.dp2px(mContext, 2), ScreenUtil.dp2px(mContext, 5), ScreenUtil.dp2px(mContext, 2));
        return textView;
    }

    private void setDiscountLabel(@NonNull NewGameStartingItemHolder.ViewHolder viewHolder, @NonNull GameInfoVo gameInfoVo){
        viewHolder.mLlDiscount1.setVisibility(View.GONE);
        viewHolder.mLlDiscount2.setVisibility(View.GONE);
        viewHolder.mLlDiscount3.setVisibility(View.GONE);
        viewHolder.mLlDiscount4.setVisibility(View.GONE);
        viewHolder.mLlDiscount5.setVisibility(View.GONE);
        viewHolder.mLlDiscount6.setVisibility(View.GONE);
        viewHolder.mTvDiscount3.setVisibility(View.GONE);
        viewHolder.mTvDiscount6.setVisibility(View.GONE);
        if (gameInfoVo.getGdm() == 1){//头条包不显示GM标签
            viewHolder.mTvDiscount6.setVisibility(View.VISIBLE);
        }else {
            //显示折扣
            int showDiscount = gameInfoVo.showDiscount();
            if (showDiscount == 1 || showDiscount == 2) {
                if (showDiscount == 1){
                    if (gameInfoVo.getDiscount() <= 0 || gameInfoVo.getDiscount() >= 10) {
                        if (gameInfoVo.getFree() == 1){//免费游戏
                            viewHolder.mLlDiscount4.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount4.setVisibility(View.GONE);
                        }else if (gameInfoVo.getSelected_game() == 1){//是否是精选游戏
                            viewHolder.mLlDiscount2.setVisibility(View.VISIBLE);
                        }else {
                            viewHolder.mTvDiscount3.setVisibility(View.VISIBLE);
                        }
                    }else {
                        if (gameInfoVo.getFree() == 1){//免费游戏
                            viewHolder.mLlDiscount4.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount4.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount4.setText(gameInfoVo.getDiscount() + "折");
                        }else if (gameInfoVo.getSelected_game() == 1){//是否是精选游戏
                            viewHolder.mLlDiscount3.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount2.setText(String.valueOf(gameInfoVo.getDiscount()));
                            viewHolder.mTvDiscount3.setVisibility(View.GONE);
                        }else {
                            viewHolder.mLlDiscount1.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount1.setText(String.valueOf(gameInfoVo.getDiscount()));
                        }
                    }
                }else if (showDiscount == 2){
                    if (gameInfoVo.getFlash_discount() <= 0 || gameInfoVo.getFlash_discount() >= 10) {
                        if (gameInfoVo.getFree() == 1){//免费游戏
                            viewHolder.mLlDiscount4.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount4.setVisibility(View.GONE);
                        }else if (gameInfoVo.getSelected_game() == 1){//是否是精选游戏
                            viewHolder.mLlDiscount2.setVisibility(View.VISIBLE);
                        }else {
                            viewHolder.mTvDiscount3.setVisibility(View.VISIBLE);
                        }
                    }else {
                        if (gameInfoVo.getFree() == 1){//免费游戏
                            viewHolder.mLlDiscount4.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount4.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount4.setText(gameInfoVo.getDiscount() + "折");
                        }else if (gameInfoVo.getSelected_game() == 1){//是否是精选游戏
                            viewHolder.mLlDiscount3.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount2.setText(String.valueOf(gameInfoVo.getFlash_discount()));
                            viewHolder.mTvDiscount3.setVisibility(View.GONE);
                        }else {
                            viewHolder.mLlDiscount1.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount1.setText(String.valueOf(gameInfoVo.getFlash_discount()));
                        }
                    }
                }
            }else {
                if (gameInfoVo.getFree() == 1){//免费游戏
                    viewHolder.mLlDiscount4.setVisibility(View.VISIBLE);
                    viewHolder.mTvDiscount4.setVisibility(View.GONE);
                }else if (gameInfoVo.getSelected_game() == 1){//是否是精选游戏
                    viewHolder.mLlDiscount2.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.mTvDiscount3.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public static class ViewHolder extends AbsHolder {
        public  LinearLayout   mLlRootview;
        public  ImageView      mGameIconIV;
        public  TextView       mTvGameName;
        public  FlexboxLayout  mFlexBoxLayout;
        public  LinearLayout   mLlDiscount1;
        public  LinearLayout   mLlDiscount2;
        public  LinearLayout   mLlDiscount3;
        public  LinearLayout   mLlDiscount4;
        public  LinearLayout   mLlDiscount5;
        public  LinearLayout   mLlDiscount6;
        public  TextView   mTvDiscount1;
        public  TextView   mTvDiscount2;
        public  TextView   mTvDiscount3;
        public  TextView   mTvDiscount4;
        public  TextView   mTvDiscount5;
        public  TextView   mTvDiscount6;
        public  TextView       mTvInfoMiddle;
        public  TextView       mTvInfoBottom;
        private TextView       mTvGameFirstTag;
        public  TextView   mTvPlayCount;

        private TextView mTvGameSuffix;
        public LinearLayout mLlGameReserveTag;

        public ViewHolder(View view) {
            super(view);
            mLlRootview = findViewById(R.id.ll_rootview);
            mGameIconIV = findViewById(R.id.gameIconIV);
            mTvGameName = findViewById(R.id.tv_game_name);
            mTvInfoMiddle = findViewById(R.id.tv_info_middle);
            mFlexBoxLayout = findViewById(R.id.flex_box_layout);
            mTvInfoBottom = findViewById(R.id.tv_info_bottom);
            mTvGameFirstTag = findViewById(R.id.tv_game_first_tag);
            mTvGameSuffix = view.findViewById(R.id.tv_game_suffix);
            mTvPlayCount = view.findViewById(R.id.tv_play_count);
            mLlGameReserveTag = view.findViewById(R.id.ll_game_reserve_tag);

            mLlDiscount1 = view.findViewById(R.id.ll_discount_1);
            mLlDiscount2 = view.findViewById(R.id.ll_discount_2);
            mLlDiscount3 = view.findViewById(R.id.ll_discount_3);
            mLlDiscount4 = view.findViewById(R.id.ll_discount_4);
            mLlDiscount5 = view.findViewById(R.id.ll_discount_5);
            mLlDiscount6 = view.findViewById(R.id.ll_discount_6);
            mTvDiscount1 = view.findViewById(R.id.tv_discount_1);
            mTvDiscount2 = view.findViewById(R.id.tv_discount_2);
            mTvDiscount3 = view.findViewById(R.id.tv_discount_3);
            mTvDiscount4 = view.findViewById(R.id.tv_discount_4);
            mTvDiscount5 = view.findViewById(R.id.tv_discount_5);
            mTvDiscount6 = view.findViewById(R.id.tv_discount_6);
        }
    }
}
