package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chaoji.im.glide.GlideApp;
import com.google.android.flexbox.FlexboxLayout;
import com.zqhy.app.Setting;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideRoundTransformNew;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/20
 */

public class GameReCommonedItemHolder extends AbsItemHolder<GameListVo, GameReCommonedItemHolder.ViewHolder> {

    private int maxGameNameTextLength;
    private int leftSidleWidthDP;

    public GameReCommonedItemHolder(Context context, int leftSidleWidthDP) {
        super(context);
        this.leftSidleWidthDP = leftSidleWidthDP;
    }
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameListVo item) {
        maxGameNameTextLength = 240;
        MyAdapter myAdapter = new MyAdapter(mContext, item.getData());
        holder.mViewPager.setAdapter(myAdapter);
        holder.mViewPager.setOffscreenPageLimit(item.getData().size());
        holder.mViewPager.setCurrentItem(0);

        LinearLayoutManager indicatorLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        holder.mIndicatorRecyclerView.setLayoutManager(indicatorLayoutManager);
        IndicatorAdapter indicatorAdapter = new IndicatorAdapter(item.getData().size());
        holder.mIndicatorRecyclerView.setAdapter(indicatorAdapter);

        holder.mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                indicatorAdapter.setPosition(position);
                indicatorAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_recommend_list;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private ViewPager mViewPager;
        private RecyclerView mIndicatorRecyclerView;

        public ViewHolder(View view) {
            super(view);
            mViewPager = view.findViewById(R.id.viewPager);
            mIndicatorRecyclerView = view.findViewById(R.id.recycler_indicator);

            ViewGroup.LayoutParams pagerLayoutParams = mViewPager.getLayoutParams();
            pagerLayoutParams.height = (ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 100)) * 442 / 750 + ScreenUtil.dp2px(mContext, 90);
            mViewPager.setLayoutParams(pagerLayoutParams);
        }
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

        public IndicatorAdapter(int bannerSize) {
            this.bannerSize = bannerSize;
            indicatorMargin = dp2px(6);
            if (mSelectedDrawable == null) {
                //绘制默认选中状态图形
                GradientDrawable selectedGradientDrawable = new GradientDrawable();
                selectedGradientDrawable.setShape(GradientDrawable.RECTANGLE);
                selectedGradientDrawable.setColor(Color.parseColor("#54A6FE"));
                selectedGradientDrawable.setSize(dp2px(10), dp2px(4));
                selectedGradientDrawable.setCornerRadius(dp2px(90));
                mSelectedDrawable = new LayerDrawable(new Drawable[]{selectedGradientDrawable});
            }
            if (mUnselectedDrawable == null) {
                //绘制默认未选中状态图形
                GradientDrawable unSelectedGradientDrawable = new GradientDrawable();
                unSelectedGradientDrawable.setShape(GradientDrawable.RECTANGLE);
                unSelectedGradientDrawable.setColor(Color.parseColor("#EEEEEE"));
                unSelectedGradientDrawable.setSize(dp2px(4), dp2px(4));
                unSelectedGradientDrawable.setCornerRadius(dp2px(90));
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
            lp.setMargins(indicatorMargin, dp2px(2), 0, indicatorMargin);
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

    public class MyAdapter extends PagerAdapter {

        private Context        context;//上下文
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
            View view = View.inflate(context, R.layout.item_game_recommoned_pager_item, null);
            ImageView mIvImage = view.findViewById(R.id.iv_image);
            ImageView mGameIconIV = view.findViewById(R.id.gameIconIV);
            RecyclerView mTagsRecyclerView = view.findViewById(R.id.recycler_tags);
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
            layoutParams.width = ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 100);
            layoutParams.height = layoutParams.width * 442 / 750;
            mIvImage.setLayoutParams(layoutParams);
            GlideApp.with(mContext).asBitmap()
                    .load(gameInfoVo.getVideo_pic())
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
            // 设置标签布局管理器
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    mContext,
                    LinearLayoutManager.HORIZONTAL,
                    false
            );
            mTagsRecyclerView.setLayoutManager(layoutManager);

            // 添加标签间距
            int horizontalSpacing = (int) (2 * ScreenUtil.getScreenDensity(mContext));
            int verticalSpacing = (int) (4 * ScreenUtil.getScreenDensity(mContext));

            mTagsRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(
                        @NonNull Rect outRect,
                        @NonNull View view,
                        @NonNull RecyclerView parent,
                        @NonNull RecyclerView.State state
                ) {
                    int position = parent.getChildAdapterPosition(view);
                    if (position != parent.getAdapter().getItemCount() - 1) {
                        outRect.right = horizontalSpacing;
                    }
                    outRect.top = verticalSpacing;
                }
            });

            // 创建标签数据列表
            List<TagItem> tagItems = new ArrayList<>();

            // 添加专服标签
            if (gameInfoVo.getUnshare() == 1) {
                tagItems.add(new TagItem("专服", 0));
            }

            // 添加可加速标签
            if (!BuildConfig.NEED_BIPARTITION &&
                    Setting.HIDE_FIVE_FIGURE != 1 &&
                    gameInfoVo.getAccelerate_status() != 0) {
                tagItems.add(new TagItem("可加速", 0));
            }

            // 添加省心玩标签
            if (Setting.REFUND_GAME_LIST != null && Setting.REFUND_GAME_LIST.size() > 0) {
                for (String gameId : Setting.REFUND_GAME_LIST) {
                    if (gameId.equals(String.valueOf(gameInfoVo.getGameid()))) {
                        tagItems.add(new TagItem("省心玩", 0));
                        break;
                    }
                }
            }

            // 添加游戏标签
            int tagSize = 3 - tagItems.size(); // 剩余标签数量
            if (tagSize > 0 &&
                    gameInfoVo.getGame_labels() != null &&
                    !gameInfoVo.getGame_labels().isEmpty()) {

                List<GameInfoVo.GameLabelsBean> labels =
                        gameInfoVo.getGame_labels().size() > tagSize ?
                                gameInfoVo.getGame_labels().subList(0, tagSize) :
                                gameInfoVo.getGame_labels();

                for (GameInfoVo.GameLabelsBean label : labels) {
                    tagItems.add(new TagItem(label.getLabel_name(), 1));
                }
            }

            // 设置标签适配器
            TagAdapter tagAdapter = new TagAdapter(tagItems);
            mTagsRecyclerView.setAdapter(tagAdapter);

            // 控制标签显示逻辑
            if (!tagItems.isEmpty()) {
                mTagsRecyclerView.setVisibility(View.VISIBLE);
                mTvInfoBottom.setVisibility(View.GONE);
            } else {
                mTagsRecyclerView.setVisibility(View.GONE);
                mTvInfoBottom.setVisibility(View.VISIBLE);
                mTvInfoBottom.setText(gameInfoVo.getGame_summary());
            }

            mTvGameFirstTag.setVisibility(View.GONE);
            if (gameInfoVo.isIs_reserve_status()) {//新游标签
                mLlGameReserveTag.setVisibility(View.VISIBLE);
            }else {
                mLlGameReserveTag.setVisibility(View.GONE);
                if (gameInfoVo.getIs_first() == 1){
                    mTvGameFirstTag.setVisibility(View.VISIBLE);
                    if (0 == CommonUtils.isTodayOrTomorrow(gameInfoVo.getOnline_time() * 1000)){
                        mTvGameFirstTag.setText(CommonUtils.friendlyTime3(gameInfoVo.getOnline_time() * 1000));
                    }else {
                        mTvGameFirstTag.setText("首发");
                    }
                }else {
                    mTvGameFirstTag.setVisibility(View.GONE);
                }
            }


            if (TextUtils.isEmpty(gameInfoVo.getOtherGameName())){
                if (gameInfoVo.getPlay_count() > 0){
                    mTvPlayCount.setVisibility(View.VISIBLE);
                    mTvPlayCount.setText(CommonUtils.formatNumberType2(gameInfoVo.getPlay_count()) + "人玩过");
                }else {
                    mTvPlayCount.setVisibility(View.GONE);
                }
            }else {
                mTvPlayCount.setVisibility(View.GONE);
            }

            mTvInfoMiddle.setVisibility(View.VISIBLE);
            mTvInfoMiddle.setTextColor(Color.parseColor("#999999"));
            String genreStr = gameInfoVo.getGenre_str();
            String str = genreStr.replace("•", " ");
            if (!TextUtils.isEmpty(gameInfoVo.getOtherGameName())){
                str = str + "•" + gameInfoVo.getOtherGameName();
            }
            mTvInfoMiddle.setText(str);

            //显示折扣
            setDiscountLabel(view, gameInfoVo);

            mTvGameName.setMaxWidth(ScreenUtil.dp2px(mContext, maxGameNameTextLength));

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

    private void setDiscountLabel(@NonNull View view, @NonNull GameInfoVo gameInfoVo){
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

        mLlDiscount1.setVisibility(View.GONE);
        mLlDiscount2.setVisibility(View.GONE);
        mLlDiscount3.setVisibility(View.GONE);
        mLlDiscount4.setVisibility(View.GONE);
        mLlDiscount5.setVisibility(View.GONE);
        mLlDiscount6.setVisibility(View.GONE);
        mTvDiscount3.setVisibility(View.GONE);
        mTvDiscount6.setVisibility(View.GONE);

        if (gameInfoVo.getGdm() == 1 && !BuildConfig.NEED_BIPARTITION){//头条包不显示GM标签
            mTvDiscount6.setVisibility(View.VISIBLE);
        }else {
            //显示折扣
            int showDiscount = gameInfoVo.showDiscount();
            if (showDiscount == 1 || showDiscount == 2) {
                if (showDiscount == 1) {
                    if (gameInfoVo.getDiscount() <= 0 || gameInfoVo.getDiscount() >= 10) {
                        if (gameInfoVo.getFree() == 1) {//免费游戏
                            mLlDiscount4.setVisibility(View.VISIBLE);
                            mTvDiscount4.setVisibility(View.GONE);
                        } else if (gameInfoVo.getUnshare() == 1) {//专服
                            mLlDiscount6.setVisibility(View.VISIBLE);
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
                        } else if (gameInfoVo.getUnshare() == 1) {//专服
                            mLlDiscount5.setVisibility(View.VISIBLE);
                            mTvDiscount5.setText(String.valueOf(gameInfoVo.getDiscount()));
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
                        } else if (gameInfoVo.getUnshare() == 1) {//专服
                            mLlDiscount6.setVisibility(View.VISIBLE);
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
                        } else if (gameInfoVo.getUnshare() == 1) {//专服
                            mLlDiscount5.setVisibility(View.VISIBLE);
                            mTvDiscount5.setText(String.valueOf(gameInfoVo.getFlash_discount()));
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
                } else if (gameInfoVo.getUnshare() == 1) {//专服
                    mLlDiscount6.setVisibility(View.VISIBLE);
                } else if (gameInfoVo.getSelected_game() == 1) {//是否是精选游戏
                    mLlDiscount2.setVisibility(View.VISIBLE);
                } else {
                    mTvDiscount3.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    // 标签数据类
    private static class TagItem {
        String text;
        int type; // 0:特殊标签 1:普通标签

        TagItem(String text, int type) {
            this.text = text;
            this.type = type;
        }
    }

    // 标签适配器
    private class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {
        private final List<TagItem> tagItems;

        TagAdapter(List<TagItem> tagItems) {
            this.tagItems = tagItems;
        }

        @NonNull
        @Override
        public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setIncludeFontPadding(false);
            textView.setTextSize(10f);
            textView.setMaxLines(1);
            textView.setPadding(
                    ScreenUtil.dp2px(mContext, 5),
                    ScreenUtil.dp2px(mContext, 2),
                    ScreenUtil.dp2px(mContext, 5),
                    ScreenUtil.dp2px(mContext, 2)
            );
            return new TagViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
            TagItem item = tagItems.get(position);
            TextView textView = (TextView) holder.itemView;
            textView.setText(item.text);

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(ScreenUtil.dp2px(mContext, 2));

            if (item.type == 0) { // 特殊标签
                textView.setTextColor(Color.parseColor("#4E76FF"));
                gd.setColors(new int[]{Color.WHITE, Color.WHITE});
                gd.setStroke(ScreenUtil.dp2px(mContext, 0.5F), Color.parseColor("#4E76FF"));
            } else { // 普通标签
                textView.setTextColor(Color.parseColor("#666666"));
                gd.setColors(new int[]{Color.parseColor("#F5F5F5"), Color.parseColor("#F5F5F5")});
            }
            textView.setBackground(gd);
        }

        @Override
        public int getItemCount() {
            return tagItems.size();
        }

        class TagViewHolder extends RecyclerView.ViewHolder {
            TagViewHolder(View itemView) {
                super(itemView);
            }
        }
    }



}
