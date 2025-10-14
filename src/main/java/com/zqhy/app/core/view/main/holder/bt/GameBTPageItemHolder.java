package com.zqhy.app.core.view.main.holder.bt;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.bt.MainBTPageGameVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.main.holder.GameNormalItemHolder;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.TitleTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leeham2734
 * @date 2020/8/19-18:25
 * @description
 */
public class GameBTPageItemHolder extends BaseItemHolder<MainBTPageGameVo, GameBTPageItemHolder.ViewHolder> {

    public GameBTPageItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_bt_common_layout;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final MainBTPageGameVo item) {
        //        if (item.isLoad() && holder.columnSize > 0) {
        //            return;
        //        }
        if (TextUtils.isEmpty(item.getMainTitle())) {
            holder.mTitleTextView.setVisibility(View.GONE);
        } else {
            holder.mTitleTextView.setVisibility(View.VISIBLE);
            holder.mTitleTextView.setText(item.getMainTitle());
        }
        int totalSize = item.getGameInfoVoList().size();
        int rowSize = item.getRowSize();
        ViewGroup.LayoutParams layoutParams = holder.mViewPager.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = ScreenUtil.dp2px(mContext, 90 * rowSize);
            holder.mViewPager.setLayoutParams(layoutParams);
        }

        holder.columnSize = totalSize % rowSize == 0 ? totalSize / rowSize : (totalSize / rowSize) + 1;
        holder.pageViewList.clear();
        int index = 0;
        for (int x = 0; x < holder.columnSize; x++) {
            List<GameInfoVo> infoVos = new ArrayList<>();
            for (int y = 0; y < rowSize && index < totalSize; y++) {
                infoVos.add(item.getGameInfoVoList().get(index));
                index++;
            }
            View pageView = holder.getPageView(infoVos);
            holder.pageViewList.add(pageView);
        }

        if (item.additional != null) {
            holder.mIvAction.setVisibility(View.VISIBLE);
            //            GlideUtils.loadNormalImage(mContext, item.additional.icon, holder.mIvAction);
            GlideApp.with(mContext).asBitmap()
                    .load(item.additional.icon)

                    .override(ScreenUtil.dp2px(mContext, 96), ScreenUtil.dp2px(mContext, 32))
                    .into(holder.mIvAction);
            holder.mIvAction.setOnClickListener(v -> {
                appJump(item.additional);
            });
        } else {
            holder.mIvAction.setVisibility(View.GONE);
        }


        LinearLayoutManager indicatorLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        holder.mIndicatorRecyclerView.setLayoutManager(indicatorLayoutManager);
        IndicatorAdapter indicatorAdapter = new IndicatorAdapter(holder.columnSize);
        holder.mIndicatorRecyclerView.setAdapter(indicatorAdapter);

        DynamicPagerAdapter adapter = new DynamicPagerAdapter(holder.pageViewList);
        holder.mViewPager.setAdapter(adapter);
        holder.mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (holder.mViewPager.getLayoutParams() != null) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mViewPager.getLayoutParams();
                    if (position == holder.columnSize - 1) {
                        //last one
                        params.rightMargin = 0;
                    } else {
                        params.rightMargin = ScreenUtil.dp2px(mContext, 48);
                    }
                    holder.mViewPager.setLayoutParams(params);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                indicatorAdapter.setPosition(position % holder.columnSize);
                indicatorAdapter.notifyDataSetChanged();
                item.pageIndex = position;
            }
        });
//        holder.mViewPager.setCurrentItem(item.pageIndex, true);
//        indicatorAdapter.setPosition(item.pageIndex % holder.columnSize);
        //        holder.mViewPager.setCurrentItem(holder.currentIndex);
        holder.mViewPager.setOffscreenPageLimit(holder.pageViewList.size());
        item.setLoad(true);
    }

    private class DynamicPagerAdapter extends PagerAdapter {
        private List<View>         pageViews;
        private Map<Integer, View> mMap;
        private int                Tag = 0;

        public DynamicPagerAdapter(List<View> pageViews) {
            this.pageViews = pageViews;
            mMap = new HashMap<>();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return Tag == 0 ? POSITION_UNCHANGED : POSITION_NONE;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            mMap.remove(position);
        }

        @Override
        public int getCount() {
            return pageViews == null ? 0 : pageViews.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Tag = 0;
            View itemView = pageViews.get(position);
            container.addView(itemView);
            mMap.put(position, itemView);
            return itemView;
        }

        @Override
        public void notifyDataSetChanged() {
            Tag = 1;
            super.notifyDataSetChanged();
        }


    }


    private View createGameItemView(GameInfoVo gameInfoVo) {
        //        int maxGameNameTextLength = 190;

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_game_normal, null);
        GameNormalItemHolder.ViewHolder viewHolder = new GameNormalItemHolder.ViewHolder(itemView);
        GameNormalItemHolder normalItemHolder = new GameNormalItemHolder(mContext, 50);
        HashMap<Integer, Object> map = new HashMap<>();
        map.put(R.id.tag_fragment, _mFragment);
        normalItemHolder.initViewHolder(viewHolder, map);
        normalItemHolder.onBindViewHolder(viewHolder, gameInfoVo);

        //        LinearLayout mLlRootView = itemView.findViewById(R.id.ll_rootview);
        //        RelativeLayout.LayoutParams rootParams;
        //        if (mLlRootView.getLayoutParams() == null) {
        //            rootParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(mContext, 99));
        //        } else {
        //            rootParams = (RelativeLayout.LayoutParams) mLlRootView.getLayoutParams();
        //            rootParams.height = ScreenUtil.dp2px(mContext, 99);
        //        }
        //        mLlRootView.setLayoutParams(rootParams);
        //        RelativeLayout mRlImage = itemView.findViewById(R.id.rl_image);
        //        if (mRlImage.getLayoutParams() != null) {
        //            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mRlImage.getLayoutParams();
        //            params.width = (int) (68 * ScreenUtil.getScreenDensity(mContext));
        //            params.height = (int) (68 * ScreenUtil.getScreenDensity(mContext));
        //            mRlImage.setLayoutParams(params);
        //        }
        //
        //        ImageView mGameIconIV = itemView.findViewById(R.id.gameIconIV);
        //        TextView mTvGameName = itemView.findViewById(R.id.tv_game_name);
        //        LinearLayout mFlGameTitleContainer = itemView.findViewById(R.id.fl_game_title_container);
        //        TextView mTvInfoMiddle = itemView.findViewById(R.id.tv_info_middle);
        //        FlexboxLayout mFlexBoxLayout = itemView.findViewById(R.id.flex_box_layout);
        //        TextView mTvInfoBottom = itemView.findViewById(R.id.tv_info_bottom);
        //
        //
        //        //图标
        //        GlideUtils.loadGameIcon(mContext, gameInfoVo.getGameicon(), mGameIconIV);
        //
        //        int game_type = gameInfoVo.getGame_type();
        //
        //        //游戏名
        //        String gameName = gameInfoVo.getGamename();
        //        mTvGameName.setText(gameName);
        //
        //        if (game_type == 1) {
        //            mFlexBoxLayout.removeAllViews();
        //            mFlexBoxLayout.setVisibility(View.VISIBLE);
        //            mTvInfoBottom.setVisibility(View.GONE);
        //            if (gameInfoVo.getGame_labels() != null && !gameInfoVo.getGame_labels().isEmpty()) {
        //                int tagSize = 3;
        //                List<GameInfoVo.GameLabelsBean> list = gameInfoVo.getGame_labels().size() > tagSize ? gameInfoVo.getGame_labels().subList(0, tagSize) : gameInfoVo.getGame_labels();
        //                for (GameInfoVo.GameLabelsBean labelsBean : list) {
        //                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
        //                    params.rightMargin = (int) (8 * ScreenUtil.getScreenDensity(mContext));
        //                    params.topMargin = (int) (0 * ScreenUtil.getScreenDensity(mContext));
        //                    mFlexBoxLayout.addView(createLabelView(labelsBean), params);
        //                }
        //            }
        //        } else {
        //            mFlexBoxLayout.setVisibility(View.GONE);
        //            mTvInfoBottom.setVisibility(View.VISIBLE);
        //            mTvInfoBottom.setText(gameInfoVo.getGame_summary());
        //        }
        //
        //        mFlGameTitleContainer.removeAllViews();
        //        if (gameInfoVo.getIs_first() == 1) {
        //            //新增“首发”标签
        //            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //            params.rightMargin = (int) (8 * ScreenUtil.getScreenDensity(mContext));
        //            mFlGameTitleContainer.addView(createFirstView(), params);
        //            maxGameNameTextLength -= 40;
        //
        //            StringBuilder sb = new StringBuilder();
        //            sb.append(gameInfoVo.getGenre_str()).append("   ");
        //            if (game_type == 3) {
        //                sb.append(CommonUtils.formatNumberType2(gameInfoVo.getPlay_count()) + "人在玩").append("   ");
        //            } else {
        //                sb.append(gameInfoVo.getClient_size() + "M").append("   ");
        //            }
        //            int startIndex = sb.length();
        //            String onlineTime = CommonUtils.formatTimeStamp(gameInfoVo.getOnline_time() * 1000, "HH:mm上线");
        //            sb.append(onlineTime);
        //            int endIndex = sb.length();
        //
        //            SpannableString ss = new SpannableString(sb.toString());
        //            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //            mTvInfoMiddle.setText(ss);
        //        } else {
        //            StringBuilder sb = new StringBuilder();
        //            sb.append(gameInfoVo.getGenre_str()).append("   ");
        //            sb.append(CommonUtils.formatNumberType2(gameInfoVo.getPlay_count()) + "人在玩").append("   ");
        //            if (game_type != 3) {
        //                sb.append(gameInfoVo.getClient_size() + "M").append("   ");
        //            }
        //            mTvInfoMiddle.setText(sb.toString());
        //        }
        //
        //        //显示折扣
        //        if (gameInfoVo.showDiscount() == 1) {
        //            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //            params.rightMargin = (int) (8 * ScreenUtil.getScreenDensity(mContext));
        //            mFlGameTitleContainer.addView(createDiscountView(String.valueOf(gameInfoVo.getDiscount()) + "折"), params);
        //            maxGameNameTextLength -= 50;
        //        }
        //        mTvGameName.setMaxWidth(ScreenUtil.dp2px(mContext, maxGameNameTextLength));
        //
        //        itemView.setOnClickListener(view -> {
        //            if (_mFragment != null) {
        //                _mFragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
        //            }
        //        });
        return itemView;
    }

    private View createLabelView(GameInfoVo.GameLabelsBean labelsBean) {
        TextView textView = new TextView(mContext);
        textView.setText(labelsBean.getLabel_name());
        textView.setIncludeFontPadding(false);
        textView.setTextColor(Color.parseColor(labelsBean.getText_color()));
        textView.setTextSize(12);
        return textView;
    }

    private View createFirstView() {
        TextView view = new TextView(mContext);
        view.setText("首发");
        view.setTextSize(13);
        view.setIncludeFontPadding(false);
        view.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        float density = ScreenUtil.getScreenDensity(mContext);
        view.setPadding((int) (6 * density), (int) (2 * density), (int) (6 * density), (int) (2 * density));


        GradientDrawable gd = new GradientDrawable();
        gd.setColors(new int[]{Color.parseColor("#FE7448"), Color.parseColor("#F63653")});
        gd.setCornerRadius(6 * density);
        view.setBackground(gd);

        return view;
    }

    private View createDiscountView(String discount) {
        TextView view = new TextView(mContext);
        view.setText(discount);
        view.setTextSize(13);
        view.setIncludeFontPadding(false);
        view.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        float density = ScreenUtil.getScreenDensity(mContext);
        view.setPadding((int) (6 * density), (int) (2 * density), (int) (6 * density), (int) (2 * density));

        GradientDrawable gd = new GradientDrawable();
        gd.setColors(new int[]{Color.parseColor("#7056ED"), Color.parseColor("#9F87FF")});
        gd.setCornerRadius(6 * density);
        view.setBackground(gd);

        return view;
    }


    /**
     * 标示点适配器
     */
    protected class IndicatorAdapter extends RecyclerView.Adapter {

        private Drawable mSelectedDrawable;
        private Drawable mUnselectedDrawable;

        private int bannerSize;
        private int currentPosition = 0;

        private int indicatorMargin;//指示器间距
        private int indicatorSize = 6;//指示器大小

        public IndicatorAdapter(int bannerSize) {
            this.bannerSize = bannerSize;
            indicatorMargin = dp2px(4);
            if (mSelectedDrawable == null) {
                //绘制默认选中状态图形
                GradientDrawable selectedGradientDrawable = new GradientDrawable();
                selectedGradientDrawable.setShape(GradientDrawable.RECTANGLE);
                selectedGradientDrawable.setColor(Color.parseColor("#0052FE"));
                selectedGradientDrawable.setSize(dp2px(22), dp2px(indicatorSize));
                selectedGradientDrawable.setCornerRadius(dp2px(indicatorSize) / 2);
                mSelectedDrawable = new LayerDrawable(new Drawable[]{selectedGradientDrawable});
            }
            if (mUnselectedDrawable == null) {
                //绘制默认未选中状态图形
                GradientDrawable unSelectedGradientDrawable = new GradientDrawable();
                unSelectedGradientDrawable.setShape(GradientDrawable.OVAL);
                unSelectedGradientDrawable.setColor(Color.parseColor("#ECECEC"));
                unSelectedGradientDrawable.setSize(dp2px(indicatorSize), dp2px(indicatorSize));
                unSelectedGradientDrawable.setCornerRadius(dp2px(indicatorSize) / 2);
                mUnselectedDrawable = new LayerDrawable(new Drawable[]{unSelectedGradientDrawable});
            }
        }

        public void setPosition(int currentPosition) {
            this.currentPosition = currentPosition;
        }

        public void notifyIndicator(int bannerSize) {
            this.bannerSize = bannerSize;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView bannerPoint = new ImageView(mContext);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(indicatorMargin, indicatorMargin, indicatorMargin, indicatorMargin);
            bannerPoint.setLayoutParams(lp);
            return new RecyclerView.ViewHolder(bannerPoint) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ImageView bannerPoint = (ImageView) holder.itemView;
            bannerPoint.setBackground(currentPosition == position ? mSelectedDrawable : mUnselectedDrawable);
        }

        @Override
        public int getItemCount() {
            return bannerSize;
        }
    }

    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    public class ViewHolder extends AbsHolder {

        private ViewPager    mViewPager;
        private RecyclerView mIndicatorRecyclerView;

        private List<View>    pageViewList = new ArrayList<>();
        private int           columnSize;
        private TitleTextView mTitleTextView;
        private ImageView     mIvAction;

        public ViewHolder(View view) {
            super(view);
            mIndicatorRecyclerView = findViewById(R.id.indicator_recycler_view);
            mViewPager = findViewById(R.id.view_pager);
            mTitleTextView = findViewById(R.id.title_text_view);
            mIvAction = findViewById(R.id.iv_action);

            //            LinearLayoutManager indicatorLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            //            mIndicatorRecyclerView.setLayoutManager(indicatorLayoutManager);
            //            indicatorAdapter = new IndicatorAdapter(columnSize);
            //            mIndicatorRecyclerView.setAdapter(indicatorAdapter);
            //
            //            adapter = new DynamicPagerAdapter(pageViewList);
            //            mViewPager.setAdapter(adapter);
            //            mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            //                @Override
            //                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            //                    if (mViewPager.getLayoutParams() != null) {
            //                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mViewPager.getLayoutParams();
            //                        if (position == columnSize - 1) {
            //                            //last one
            //                            params.rightMargin = 0;
            //                        } else {
            //                            params.rightMargin = ScreenUtil.dp2px(mContext, 48);
            //                        }
            //                        mViewPager.setLayoutParams(params);
            //                    }
            //                }
            //
            //                @Override
            //                public void onPageSelected(int position) {
            //                    super.onPageSelected(position);
            //                    currentIndex = position;
            //                    indicatorAdapter.setPosition(currentIndex % columnSize);
            //                    indicatorAdapter.notifyDataSetChanged();
            //                }
            //            });
            //            mViewPager.setOffscreenPageLimit(pageViewList.size());

        }

        protected View getPageView(List<GameInfoVo> item) {
            LinearLayout layout = new LinearLayout(mContext);
            layout.setOrientation(LinearLayout.VERTICAL);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(lp);
            layout.removeAllViews();
            for (int i = 0; i < item.size(); i++) {
                View itemView = createGameItemView(item.get(i));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(mContext, 90));
                params.gravity = Gravity.CENTER;
                layout.addView(itemView, params);
            }
            return layout;
        }
    }
}
