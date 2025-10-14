package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.google.android.flexbox.FlexboxLayout;
import com.zqhy.app.base.BaseFragment;
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
public class MainPageJingXuanGameBannerHolder extends BaseItemHolder<LunboGameDataBeanVo, MainPageJingXuanGameBannerHolder.ViewHolder> {
    public MainPageJingXuanGameBannerHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_page_recycler;
    }

    private BaseFragment mSubFragment;

    @Override
    protected void initView(View view) {
        super.initView(view);
        mSubFragment = (BaseFragment) view.getTag(R.id.tag_sub_fragment);
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull LunboGameDataBeanVo item) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.mViewPager.getLayoutParams();
        if (params != null) {
            params.height = (int) ((ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 20)) / 1.16);
            params.setMargins(ScreenUtil.dp2px(mContext, 20), ScreenUtil.dp2px(mContext, 0), ScreenUtil.dp2px(mContext, 20), 0);
            holder.mViewPager.setLayoutParams(params);
        }
        MyAdapter myAdapter = new MyAdapter(mContext, item.data);
        holder.mViewPager.setAdapter(myAdapter);
        holder.mViewPager.setOffscreenPageLimit(3);
        holder.mViewPager.setCurrentItem(1000*item.data.size());
        holder.mViewPager.setPageMargin(ScreenUtil.dp2px(mContext, 10));
        holder.mViewPager.setClipChildren(false);
    }

    public class ViewHolder extends AbsHolder {
        private ViewPager mViewPager;

        public ViewHolder(View view) {
            super(view);
            mViewPager = findViewById(R.id.viewPager);
        }
    }

    public class MyAdapter extends PagerAdapter {

        private Context        context;//上下文
        private List<LunboGameDataBeanVo.DataBean> list;//数据源

        public MyAdapter(Context context, List<LunboGameDataBeanVo.DataBean> list) {
            this.context = context;
            this.list = list;
        }

        //ViewPager总共有几个页面
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
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
            //加载vp的布局
            View inflate = View.inflate(context, R.layout.item_main_page_game_banner, null);
            LunboGameDataBeanVo.DataBean item = list.get(position%list.size());
            LinearLayout mLlContainer = inflate.findViewById(R.id.ll_container);
            AppCompatImageView mIvGameImage = inflate.findViewById(R.id.iv_game_image);
            ImageView mIvGameIcon = inflate.findViewById(R.id.iv_game_icon);
            TextView mTvGameName = inflate.findViewById(R.id.tv_game_name);
            TextView mTvGameDiscount = inflate.findViewById(R.id.tv_game_discount);
            TextView mTvGameSuffix = inflate.findViewById(R.id.tv_game_suffix);
            TextView mTvGameGenre = inflate.findViewById(R.id.tv_game_genre);
            TextView mTvGameFirst = inflate.findViewById(R.id.tv_game_first);
            FlexboxLayout mFlexBoxLayout = inflate.findViewById(R.id.flex_box_layout);
            LinearLayout mLlTuijianContainer = inflate.findViewById(R.id.ll_tuijian_container);
            TextView mTvInfoBottom = inflate.findViewById(R.id.tv_info_bottom);

            GradientDrawable gdTemp1 = new GradientDrawable();
            int radius = ScreenUtil.dp2px(mContext, 10);
            gdTemp1.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, 0, 0});
            gdTemp1.setColor(Color.WHITE);
            mLlTuijianContainer.setBackground(gdTemp1);

            int width = ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 20);
            int height = (int) (width / 1.54f);
            GradientDrawable gdTemp = new GradientDrawable();
            gdTemp.setSize(width, height);
            gdTemp.setColor(Color.parseColor("#F2F2F2"));
            GlideApp.with(mContext).asBitmap()
                    .load(item.getPic())
                    .override(width, height)
                    .placeholder(gdTemp)
                    .transform(new GlideRoundTransformNew(mContext, 10))
                    .into(mIvGameImage);
            GlideUtils.loadRoundImage(mContext, item.getGameicon(), mIvGameIcon);

            try {
                GradientDrawable gd = new GradientDrawable();
                gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                gd.setCornerRadius(ScreenUtil.dp2px(mContext, 10));
                gd.setColors(item.getBgColors());
                mLlContainer.setBackground(gd);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mTvGameName.setText(item.getGamename());
            int showDiscount = item.showDiscount();
            if (showDiscount == 1 || showDiscount == 2) {
                mTvGameDiscount.setVisibility(View.VISIBLE);
                if (showDiscount == 1){
                    mTvGameDiscount.setText(item.getDiscount() + "折");
                }else if (showDiscount == 2){
                    mTvGameDiscount.setText(item.getFlash_discount() + "折");
                }
                GradientDrawable gd = new GradientDrawable();
                gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                gd.setCornerRadius(ScreenUtil.dp2px(mContext, 30));
                gd.setColors(new int[]{Color.parseColor("#FF8B06"), Color.parseColor("#FF1313")});
                mTvGameDiscount.setBackground(gd);
            } else {
                mTvGameDiscount.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(item.getOtherGameName())){//游戏后缀
                mTvGameSuffix.setVisibility(View.VISIBLE);
                mTvGameSuffix.setText(item.getOtherGameName());
            }else {
                mTvGameSuffix.setVisibility(View.GONE);
            }
            mTvGameGenre.setText(item.getGenre_str());
            if (item.getIs_first() == 1) {
                mTvGameFirst.setVisibility(View.VISIBLE);
                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(ScreenUtil.dp2px(mContext, 2));
                gd.setStroke(ScreenUtil.dp2px(mContext, 1), ContextCompat.getColor(mContext, R.color.white));
                mTvGameFirst.setBackground(gd);
            } else {
                mTvGameFirst.setVisibility(View.GONE);
            }

            mLlTuijianContainer.setVisibility(item.tuijian_flag ? View.VISIBLE : View.GONE);


            mTvInfoBottom.setVisibility(View.GONE);
            boolean hasLineTag = false;
            mFlexBoxLayout.setVisibility(View.VISIBLE);
            mFlexBoxLayout.removeAllViews();
            int tagSize = 2;
            /*if (item.getCoupon_amount() > 0) {
                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                params.rightMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                params.topMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                mFlexBoxLayout.addView(createLabelView("送" + (int) (item.getCoupon_amount()) + "元券"), params);
                hasLineTag = true;
                tagSize--;
            }*/

            if (item.getGame_labels() != null && !item.getGame_labels().isEmpty()) {
                List<GameInfoVo.GameLabelsBean> list = item.getGame_labels().size() > tagSize ? item.getGame_labels().subList(0, tagSize) : item.getGame_labels();
                for (GameInfoVo.GameLabelsBean labelsBean : list) {
                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    params.rightMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                    params.topMargin = (int) (4 * ScreenUtil.getScreenDensity(mContext));
                    mFlexBoxLayout.addView(createLabelView(labelsBean.getLabel_name()), params);
                }
                hasLineTag = true;
            } else {
                mTvInfoBottom.setVisibility(View.VISIBLE);
                mTvInfoBottom.setText(item.getGame_summary());
            }

            if (!hasLineTag) {
                mFlexBoxLayout.setVisibility(View.GONE);
            }

            mFlexBoxLayout.setOnClickListener(v -> {
                if (_mFragment != null) _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
            });

            mLlContainer.setOnClickListener(view -> {
                if (_mFragment != null) {
                    _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
                }
            });

            container.addView(inflate);
            return inflate;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //        super.destroyItem(container, position, object);
            //移除视图
            container.removeView((View) object);
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
    }
}
