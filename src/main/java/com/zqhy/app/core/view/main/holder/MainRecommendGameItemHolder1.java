package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.shizhefei.view.indicator.BannerComponent;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.mainpage.MainGameRecommendVo;
import com.zqhy.app.core.data.model.mainpage.recommend.MainGameRecommendItemVo1;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leeham2734
 * @date 2021/6/7-11:01
 * @description
 */
public class MainRecommendGameItemHolder1 extends BaseItemHolder<MainGameRecommendItemVo1, MainRecommendGameItemHolder1.ViewHolder> {

    GradientDrawable gdCheck  = new GradientDrawable();
    GradientDrawable gdNormal = new GradientDrawable();

    public MainRecommendGameItemHolder1(Context context) {
        super(context);
        float density = mContext.getResources().getDisplayMetrics().density;
        gdCheck.setCornerRadius(4 * density);
        gdCheck.setColor(Color.parseColor("#FFFFFFFF"));

        gdNormal.setCornerRadius(4 * density);
        gdNormal.setShape(GradientDrawable.OVAL);
        gdNormal.setColor(Color.parseColor("#9AFFFFFF"));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_game_recommend_1;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    BannerComponent                bannerComponent;
    InnerIndicatorViewPagerAdapter adapter;

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (bannerComponent != null) {
            bannerComponent.stopAutoPlay();
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainGameRecommendItemVo1 item) {
        holder.mFlexMenu.removeAllViews();
        List<MainGameRecommendVo.GameDataVo> iconMenu = item.getIconMenu();
        if (iconMenu != null && iconMenu.size() > 0) {
            holder.mFlexMenu.setVisibility(View.VISIBLE);
            for (MainGameRecommendVo.GameDataVo gameDataVo : iconMenu) {
                View view = createMenuView(gameDataVo);
                holder.mFlexMenu.addView(view);
            }
        } else {
            holder.mFlexMenu.setVisibility(View.GONE);
        }

        if (item.getSliderBanner() != null && item.getSliderBanner().size() > 0) {
            holder.mRlBanner.setVisibility(View.VISIBLE);
            int bannerSize = item.getSliderBanner().size();
            if (bannerComponent == null) {
                holder.mBannerIndicator.setSplitMethod(FixedIndicatorView.SPLITMETHOD_WRAP);
                bannerComponent = new BannerComponent(holder.mBannerIndicator, holder.mBannerViewPager, false);
                holder.mBannerViewPager.setOffscreenPageLimit(bannerSize);
                if (adapter == null) {
                    adapter = new InnerIndicatorViewPagerAdapter(item.getSliderBanner(), holder.mBannerViewPager);
                }
                bannerComponent.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
                    @Override
                    public void onIndicatorPageChange(int preItem, int currentItem) {
                        Map<Integer, View> tabViewMaps = adapter.getTabViewMaps();
                        for (Integer key : tabViewMaps.keySet()) {
                            View selectItemView = tabViewMaps.get(key);
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) selectItemView.getLayoutParams();
                            if (params == null) {
                                params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            }
                            float density = mContext.getResources().getDisplayMetrics().density;
                            params.leftMargin = (int) (2 * density);
                            params.rightMargin = (int) (2 * density);

                            if (key == currentItem) {
                                params.width = (int) (10 * density);
                                params.height = (int) (4 * density);
                                selectItemView.setBackground(gdCheck);
                            } else {
                                params.width = (int) (4 * density);
                                params.height = (int) (4 * density);
                                selectItemView.setBackground(gdNormal);
                            }
                            selectItemView.setLayoutParams(params);
                        }
                    }
                });
                bannerComponent.setAdapter(adapter);
                bannerComponent.setCurrentItem(0, false);
                bannerComponent.setAutoPlayTime(5000);
            } else {
                adapter.refreshAll(item.getSliderBanner());
            }
            if (bannerSize > 1) {
                bannerComponent.startAutoPlay();
            }
        } else {
            holder.mRlBanner.setVisibility(View.GONE);
        }
    }


    private View createMenuView(MainGameRecommendVo.GameDataVo item) {
        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        ImageView image = new ImageView(mContext);

        int imageWidth = ScreenUtil.dp2px(mContext, 58);
        int imageHeight = ScreenUtil.dp2px(mContext, 58);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(imageWidth, imageHeight);
        layout.addView(image, imageParams);

        TextView text = new TextView(mContext);
        text.setTextSize(12);
        text.setIncludeFontPadding(false);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.gravity = Gravity.CENTER_HORIZONTAL;
        textParams.topMargin = ScreenUtil.dp2px(mContext, 8);
        layout.addView(text, textParams);

        try {
            GlideUtils.loadNormalImage(mContext, item.getIcon(), image);
            text.setText(item.getTitle());
            text.setTextColor(Color.parseColor(item.getTitle_color()));

            layout.setOnClickListener(v -> {
                try {
                    appJump(item.getJumpInfo());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return layout;
    }


    public class ViewHolder extends AbsHolder {
        private FlexboxLayout      mFlexMenu;
        private RelativeLayout     mRlBanner;
        private ViewPager          mBannerViewPager;
        private FixedIndicatorView mBannerIndicator;

        public ViewHolder(View view) {
            super(view);
            mFlexMenu = findViewById(R.id.flex_menu);
            mRlBanner = findViewById(R.id.rl_banner);
            mBannerViewPager = findViewById(R.id.banner_viewPager);
            mBannerIndicator = findViewById(R.id.banner_indicator);
        }
    }


    class InnerIndicatorViewPagerAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {

        private Map<Integer, View> tabViewMaps = new HashMap<>();

        private List<MainGameRecommendVo.GameDataVo> mBannerVos;
        private ViewPager                            mViewPager;

        public InnerIndicatorViewPagerAdapter(List<MainGameRecommendVo.GameDataVo> mBannerVos, ViewPager targetViewPager) {
            this.mBannerVos = mBannerVos;
            this.mViewPager = targetViewPager;
        }

        @Override
        public int getCount() {
            return mBannerVos == null ? 0 : mBannerVos.size();
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new View(mContext);
            }
            tabViewMaps.put(position, convertView);
            return convertView;
        }

        public Map<Integer, View> getTabViewMaps() {
            return tabViewMaps;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new RelativeLayout(mContext);
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                ImageView imageView = new ImageView(mContext);
                imageView.setId(R.id.banner);
                imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                ((ViewGroup) convertView).addView(imageView);
            }
            ImageView mImageView = convertView.findViewById(R.id.banner);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            String imageUrl = mBannerVos.get(position).getPic();
            GlideUtils.loadRoundImage(mContext, imageUrl, mImageView, R.mipmap.img_placeholder_v_1);

            mImageView.setOnClickListener(v -> {
                try {
                    MainGameRecommendVo.GameDataVo bannerVo = mBannerVos.get(position);
                    if (bannerVo != null) {
                        appJump(bannerVo.getJumpInfo());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return convertView;
        }

        public void refreshAll(List<MainGameRecommendVo.GameDataVo> sliderBanner) {
            if (sliderBanner == null) {
                return;
            }
            mBannerVos.clear();
            mBannerVos.addAll(sliderBanner);
            notifyDataSetChanged();
        }
    }
}
