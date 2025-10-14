package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.google.android.flexbox.FlexboxLayout;
import com.zqhy.app.Setting;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.MainJingXuanDataVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideRoundTransformNew;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/14 0014-11:23
 * @description
 */
public class MainTuiJianDanTuiItemHolder extends BaseItemHolder<MainJingXuanDataVo.DanTuiItemDataBeanVo, MainTuiJianDanTuiItemHolder.ViewHolder> {
    public MainTuiJianDanTuiItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_page_tj_dantui;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainJingXuanDataVo.DanTuiItemDataBeanVo item) {
        if (TextUtils.isEmpty(item.module_title) && TextUtils.isEmpty(item.module_title_two)) {
            holder.mTvTitle.setVisibility(View.GONE);
        } else {
            holder.mTvTitle.setVisibility(View.VISIBLE);
            String title = "";
            if (!TextUtils.isEmpty(item.module_title)) {
                title += item.module_title;
            }
            if (!TextUtils.isEmpty(item.module_title_two)) {
                title += item.module_title_two;
            }
            SpannableString spannableString = new SpannableString(title);
            if (!TextUtils.isEmpty(item.module_title) && !TextUtils.isEmpty(item.module_title_color)) {
                try {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(item.module_title_color)), 0, item.module_title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } catch (Exception e) {
                }
            }
            if (!TextUtils.isEmpty(item.module_title_two) && !TextUtils.isEmpty(item.module_title_two_color)) {
                try {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(item.module_title_two_color)), title.length() - item.module_title_two.length(), title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } catch (Exception e) {
                }
            }
            holder.mTvTitle.setText(spannableString);
        }
        if (!TextUtils.isEmpty(item.module_sub_title)) {
            holder.mTvDescription.setVisibility(View.VISIBLE);
            holder.mTvDescription.setText(item.module_sub_title);
            try {
                if (!TextUtils.isEmpty(item.module_sub_title_color)) holder.mTvDescription.setTextColor(Color.parseColor(item.module_sub_title_color));
            } catch (Exception e) {
            }
        } else {
            holder.mTvDescription.setVisibility(View.GONE);
        }
        if (item.additional != null) {
            holder.mTvMore.setVisibility(View.VISIBLE);
            holder.mTvMore.setText(item.additional.text);
            try {
                if (!TextUtils.isEmpty(item.additional.textcolor)) holder.mTvMore.setTextColor(Color.parseColor(item.additional.textcolor));
            } catch (Exception e) {
            }
            holder.mTvMore.setOnClickListener(v -> {
                appJump(item.additional.getPage_type(), item.additional.getParam());
            });
        } else {
            holder.mTvMore.setVisibility(View.GONE);
        }

        MyAdapter myAdapter = new MyAdapter(mContext, item.mGameLisVo);
        holder.mViewPager.setAdapter(myAdapter);
        holder.mViewPager.setOffscreenPageLimit(item.mGameLisVo.size());
        holder.mViewPager.setCurrentItem(0);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.mViewProgress.getLayoutParams();
        layoutParams.width = ScreenUtil.dp2px(mContext, 39) / item.mGameLisVo.size();
        layoutParams.leftMargin = 0;
        holder.mViewProgress.setLayoutParams(layoutParams);


        holder.mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                layoutParams.leftMargin = ScreenUtil.dp2px(mContext, (39F / item.mGameLisVo.size()) * position);
                holder.mViewProgress.setLayoutParams(layoutParams);
            }
        });
        if (item.mGameLisVo.size() > 1) {
            holder.mLlProgress.setVisibility(View.VISIBLE);
        } else {
            holder.mLlProgress.setVisibility(View.GONE);
        }
    }

    public class MyAdapter extends PagerAdapter {

        private Context context;//上下文
        private List<GameInfoVo> list;//数据源

        public MyAdapter(Context context, List<GameInfoVo> list) {
            this.context = context;
            this.list = list;
        }

        //ViewPager总共有几个页面
        @Override
        public int getCount() {
            return list.size();
        }

        //判断一个页面(View)是否与instantiateItem方法返回的Object一致
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        //添加视图
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            GameInfoVo gameInfoVo = list.get(position);
            //加载vp的布局
            View view = View.inflate(context, R.layout.item_main_signle_game_item, null);
            ImageView mIvImage = view.findViewById(R.id.iv_image);
            ImageView mGameIconIV = view.findViewById(R.id.gameIconIV);
            TextView mTvGameName = view.findViewById(R.id.tv_game_name);
            TextView mTvInfoMiddle = view.findViewById(R.id.tv_info_middle);
            FlexboxLayout mFlexBoxLayout = view.findViewById(R.id.flex_box_layout);
            TextView mTvInfoBottom = view.findViewById(R.id.tv_info_bottom);
            TextView mTvGameFirstTag = view.findViewById(R.id.tv_game_first_tag);
            TextView mTvGameSuffix = view.findViewById(R.id.tv_game_suffix);
            TextView mTvPlayCount = view.findViewById(R.id.tv_play_count);
            LinearLayout mLlDiscount1 = view.findViewById(R.id.ll_discount_1);
            LinearLayout mLlDiscount2 = view.findViewById(R.id.ll_discount_2);
            LinearLayout mLlDiscount3 = view.findViewById(R.id.ll_discount_3);
            LinearLayout mLlDiscount4 = view.findViewById(R.id.ll_discount_4);
            LinearLayout mLlDiscount5 = view.findViewById(R.id.ll_discount_5);
            LinearLayout mLlDiscount6 = view.findViewById(R.id.ll_discount_6);
            TextView mTvDiscount1 = view.findViewById(R.id.tv_discount_1);
            TextView mTvDiscount2 = view.findViewById(R.id.tv_discount_2);
            TextView mTvDiscount3 = view.findViewById(R.id.tv_discount_3);
            TextView mTvDiscount4 = view.findViewById(R.id.tv_discount_4);
            TextView mTvDiscount5 = view.findViewById(R.id.tv_discount_5);
            TextView mTvDiscount6 = view.findViewById(R.id.tv_discount_6);
            LinearLayout mLlGameReserveTag = view.findViewById(R.id.ll_game_reserve_tag);

            ViewGroup.LayoutParams layoutParams = mIvImage.getLayoutParams();
            layoutParams.width = ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 60);
            layoutParams.height = layoutParams.width * 442 / 750;
            mIvImage.setLayoutParams(layoutParams);
            GlideApp.with(mContext).asBitmap()
                    .load(gameInfoVo.getBg_pic())
                    .placeholder(R.mipmap.img_placeholder_v_2)
                    .error(R.mipmap.img_placeholder_v_2)
                    .transform(new GlideRoundTransformNew(mContext, 10))
                    .into(mIvImage);
            view.setOnClickListener(v -> {
                if (_mFragment != null) {
                    _mFragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
                }
            });

            //图标
            GlideUtils.loadGameIcon(mContext, gameInfoVo.getGameicon(), mGameIconIV);
            //游戏名
            String gameName = gameInfoVo.getGamename();
            mTvGameName.setText(gameName);

            boolean hasLineTag = false;
            mFlexBoxLayout.removeAllViews();
            mFlexBoxLayout.setVisibility(View.VISIBLE);
            mTvInfoBottom.setVisibility(View.GONE);
            int tagSize = 3;
            if (gameInfoVo.getUnshare() == 1) {//专服
                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                params.rightMargin = (int) (2 * ScreenUtil.getScreenDensity(mContext));
                params.topMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                mFlexBoxLayout.addView(createLabelView("专服"), params);
                hasLineTag = true;
                tagSize--;
            }

            if (!BuildConfig.NEED_BIPARTITION) {//头条包不显示加速标签
                if (Setting.HIDE_FIVE_FIGURE != 1) {
                    if (gameInfoVo.getAccelerate_status() != 0) {
                        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                        params.rightMargin = (int) (2 * ScreenUtil.getScreenDensity(mContext));
                        params.topMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                        mFlexBoxLayout.addView(createLabelView("可加速"), params);
                        hasLineTag = true;
                        tagSize--;
                    }
                }
            }

            if (Setting.REFUND_GAME_LIST != null && Setting.REFUND_GAME_LIST.size() > 0) {
                for (int i = 0; i < Setting.REFUND_GAME_LIST.size(); i++) {
                    if (Setting.REFUND_GAME_LIST.get(i).equals(String.valueOf(gameInfoVo.getGameid()))) {
                        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                        params.rightMargin = (int) (2 * ScreenUtil.getScreenDensity(mContext));
                        params.topMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                        mFlexBoxLayout.addView(createLabelView("省心玩"), params);
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
                    mFlexBoxLayout.addView(createLabelView(labelsBean), params);
                }
                hasLineTag = true;
            }
            if (hasLineTag) {
                mFlexBoxLayout.setVisibility(View.VISIBLE);
                mTvInfoBottom.setVisibility(View.GONE);
            } else {
                mFlexBoxLayout.setVisibility(View.GONE);
                mTvInfoBottom.setVisibility(View.VISIBLE);
                mTvInfoBottom.setText(gameInfoVo.getGame_summary());
            }

            mFlexBoxLayout.setOnClickListener(v -> {
                if (_mFragment != null) _mFragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
            });

            mTvGameFirstTag.setVisibility(View.GONE);
            if (gameInfoVo.isIs_reserve_status()) {//新游标签
                mLlGameReserveTag.setVisibility(View.VISIBLE);
            } else {
                mLlGameReserveTag.setVisibility(View.GONE);
                if (gameInfoVo.getIs_first() == 1) {
                    mTvGameFirstTag.setVisibility(View.VISIBLE);
                    if (0 == CommonUtils.isTodayOrTomorrow(gameInfoVo.getOnline_time() * 1000)) {
                        mTvGameFirstTag.setText(CommonUtils.friendlyTime3(gameInfoVo.getOnline_time() * 1000));
                    } else {
                        mTvGameFirstTag.setText("首发");
                    }
                } else {
                    mTvGameFirstTag.setVisibility(View.GONE);
                }
            }

            if (TextUtils.isEmpty(gameInfoVo.getOtherGameName())) {
                if (gameInfoVo.getPlay_count() > 0) {
                    mTvPlayCount.setVisibility(View.VISIBLE);
                    mTvPlayCount.setText(CommonUtils.formatNumberType2(gameInfoVo.getPlay_count()) + "人玩过");
                } else {
                    mTvPlayCount.setVisibility(View.GONE);
                }
            } else {
                mTvPlayCount.setVisibility(View.GONE);
            }

            mTvInfoMiddle.setVisibility(View.VISIBLE);
            mTvInfoMiddle.setTextColor(Color.parseColor("#999999"));
            String genreStr = gameInfoVo.getGenre_str();
            String str = genreStr.replace("•", " ");
            if (!TextUtils.isEmpty(gameInfoVo.getOtherGameName())) {
                str = str + "•" + gameInfoVo.getOtherGameName();
            }
            mTvInfoMiddle.setText(str);

            //显示折扣
            int showDiscount = gameInfoVo.showDiscount();
            mLlDiscount1.setVisibility(View.GONE);
            mLlDiscount2.setVisibility(View.GONE);
            mLlDiscount3.setVisibility(View.GONE);
            mLlDiscount4.setVisibility(View.GONE);
            mLlDiscount5.setVisibility(View.GONE);
            mLlDiscount6.setVisibility(View.GONE);
            mTvDiscount3.setVisibility(View.GONE);
            mTvDiscount6.setVisibility(View.GONE);
            if (gameInfoVo.getGdm() == 1) {//头条包不显示GM标签
                mTvDiscount6.setVisibility(View.VISIBLE);
            } else {
                //显示折扣
                if (showDiscount == 1 || showDiscount == 2) {
                    if (showDiscount == 1) {
                        if (gameInfoVo.getDiscount() <= 0 || gameInfoVo.getDiscount() >= 10) {
                            if (gameInfoVo.getFree() == 1) {//免费游戏
                                mLlDiscount4.setVisibility(View.VISIBLE);
                                mTvDiscount4.setVisibility(View.GONE);
                            } else if (gameInfoVo.getSelected_game() == 1) {//是否是精选游戏
                                mLlDiscount2.setVisibility(View.VISIBLE);
                            } else {
                                mTvDiscount3.setVisibility(View.VISIBLE);
                            }
                        } else {
                            if (gameInfoVo.getFree() == 1) {//免费游戏
                                mLlDiscount4.setVisibility(View.VISIBLE);
                                mTvDiscount4.setVisibility(View.VISIBLE);
                                mTvDiscount4.setText(gameInfoVo.getDiscount() + "折");
                            } else if (gameInfoVo.getSelected_game() == 1) {//是否是精选游戏
                                mLlDiscount3.setVisibility(View.VISIBLE);
                                mTvDiscount2.setText(String.valueOf(gameInfoVo.getDiscount()));
                                mTvDiscount3.setVisibility(View.GONE);
                            } else {
                                mLlDiscount1.setVisibility(View.VISIBLE);
                                mTvDiscount1.setText(String.valueOf(gameInfoVo.getDiscount()));
                            }
                        }
                    } else if (showDiscount == 2) {
                        if (gameInfoVo.getFlash_discount() <= 0 || gameInfoVo.getFlash_discount() >= 10) {
                            if (gameInfoVo.getFree() == 1) {//免费游戏
                                mLlDiscount4.setVisibility(View.VISIBLE);
                                mTvDiscount4.setVisibility(View.GONE);
                            } else if (gameInfoVo.getSelected_game() == 1) {//是否是精选游戏
                                mLlDiscount2.setVisibility(View.VISIBLE);
                            } else {
                                mTvDiscount3.setVisibility(View.VISIBLE);
                            }
                        } else {
                            if (gameInfoVo.getFree() == 1) {//免费游戏
                                mLlDiscount4.setVisibility(View.VISIBLE);
                                mTvDiscount4.setVisibility(View.VISIBLE);
                                mTvDiscount4.setText(gameInfoVo.getDiscount() + "折");
                            } else if (gameInfoVo.getSelected_game() == 1) {//是否是精选游戏
                                mLlDiscount3.setVisibility(View.VISIBLE);
                                mTvDiscount2.setText(String.valueOf(gameInfoVo.getFlash_discount()));
                                mTvDiscount3.setVisibility(View.GONE);
                            } else {
                                mLlDiscount1.setVisibility(View.VISIBLE);
                                mTvDiscount1.setText(String.valueOf(gameInfoVo.getFlash_discount()));
                            }
                        }
                    }
                } else {
                    if (gameInfoVo.getFree() == 1) {//免费游戏
                        mLlDiscount4.setVisibility(View.VISIBLE);
                        mTvDiscount4.setVisibility(View.GONE);
                    } else if (gameInfoVo.getSelected_game() == 1) {//是否是精选游戏
                        mLlDiscount2.setVisibility(View.VISIBLE);
                    } else {
                        mTvDiscount3.setVisibility(View.VISIBLE);
                    }
                }
            }

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //        super.destroyItem(container, position, object);
            //移除视图
            container.removeView((View) object);
        }
    }

    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    private View createLabelView(GameInfoVo.GameLabelsBean labelsBean) {
        TextView textView = new TextView(mContext);
        textView.setText(labelsBean.getLabel_name());
        textView.setIncludeFontPadding(false);
        try {
            textView.setTextColor(Color.parseColor(labelsBean.getText_color()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        textView.setTextSize(9.5f);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(ScreenUtil.dp2px(mContext, 2));
        try {
            gd.setStroke(ScreenUtil.dp2px(mContext, 0.5F), Color.parseColor(labelsBean.getText_color()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        textView.setBackground(gd);

        textView.setPadding(ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2), ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2));

        return textView;
    }

    private View createLabelView(String labelsBean) {
        TextView textView = new TextView(mContext);
        textView.setText(labelsBean);
        textView.setIncludeFontPadding(false);
        textView.setTextSize(9.5f);
        textView.setTextColor(Color.parseColor("#5571FE"));
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        GradientDrawable gd = new GradientDrawable();
        float radius = ScreenUtil.dp2px(mContext, 2);
        float radius1 = ScreenUtil.dp2px(mContext, 6);
        gd.setCornerRadii(new float[]{radius1, radius1, radius, radius, radius, radius, radius, radius});
        gd.setStroke(ScreenUtil.dp2px(mContext, 0.5F), Color.parseColor("#4E76FF"));
        textView.setBackground(gd);
        textView.setPadding(ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2), ScreenUtil.dp2px(mContext, 4), ScreenUtil.dp2px(mContext, 2));
        return textView;
    }

    public class ViewHolder extends AbsHolder {

        private TextView mTvTitle;
        private TextView mTvMore;
        private TextView mTvDescription;
        private ViewPager mViewPager;
        private LinearLayout mLlProgress;
        private View mViewProgress;

        public ViewHolder(View view) {
            super(view);
            mTvTitle = view.findViewById(R.id.tv_title);
            mTvMore = view.findViewById(R.id.tv_more);
            mTvDescription = view.findViewById(R.id.tv_description);

            mViewPager = view.findViewById(R.id.viewPager);
            mLlProgress = findViewById(R.id.ll_progress);
            mViewProgress = findViewById(R.id.view_progress);

            ViewGroup.LayoutParams pagerLayoutParams = mViewPager.getLayoutParams();
            pagerLayoutParams.height = (ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 60)) * 442 / 750 + ScreenUtil.dp2px(mContext, 88);
            mViewPager.setLayoutParams(pagerLayoutParams);
        }
    }
}
