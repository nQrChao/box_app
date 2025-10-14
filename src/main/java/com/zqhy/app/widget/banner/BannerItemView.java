package com.zqhy.app.widget.banner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.shizhefei.view.indicator.BannerComponent;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.banner.BannerListVo;
import com.zqhy.app.core.data.model.banner.BannerVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideRoundTransformNew;
import com.zqhy.app.network.statistics.JiuYaoStatisticsApi;
import com.zqhy.app.newproject.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class BannerItemView extends BaseItemHolder<BannerListVo, BannerItemView.ViewHolder> {

    public BannerItemView(Context context) {
        super(context);
    }

    private int itemHeightDp = 156;

    public BannerItemView(Context context, int itemHeightDp) {
        super(context);
        this.itemHeightDp = itemHeightDp;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_banner_view;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    BannerComponent bannerComponent;

    @Override
    protected void onBindViewHolder(@NonNull BannerItemView.ViewHolder holder, @NonNull final BannerListVo item) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.mBannerViewPager.getLayoutParams();
        if (params != null && item.itemHeightDp > 0) {
            params.height = ScreenUtil.dp2px(mContext, item.itemHeightDp);
            holder.mBannerViewPager.setLayoutParams(params);
        }
        setIndicatorLocation(holder, item);
        int bannerSize = item.getData().size();
        bannerComponent = new BannerComponent(holder.mBannerIndicator, holder.mBannerViewPager, false);
        holder.mBannerViewPager.setOffscreenPageLimit(bannerSize);
        InnerIndicatorViewPagerAdapter adapter = new InnerIndicatorViewPagerAdapter(item.getData(), holder.mBannerViewPager);
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
                    params.leftMargin = (int) (1 * density);
                    params.rightMargin = (int) (1 * density);

                    params.width = (int) (4 * density);
                    params.height = (int) (4 * density);
                    GradientDrawable gd = new GradientDrawable();
                    gd.setCornerRadius(6 * density);
                    gd.setShape(GradientDrawable.OVAL);
                    if (key == currentItem) {
                        gd.setColor(Color.parseColor("#0052FE"));
                    } else {
                        gd.setColor(Color.parseColor("#D6D6D6"));
                    }
                    selectItemView.setBackground(gd);
                    selectItemView.setLayoutParams(params);
                }
            }
        });
        bannerComponent.setAdapter(adapter);
        bannerComponent.setCurrentItem(0, false);
        if (item.isLoop && bannerSize > 1) {
            bannerComponent.setAutoPlayTime(5000);
            bannerComponent.startAutoPlay();
        } else {
            bannerComponent.stopAutoPlay();
        }
    }

    private void setIndicatorLocation(ViewHolder holder, BannerListVo item) {
        if (item.INDICATOR_LOCATION == item.INDICATOR_LOCATION_INSIDE_BOTTOM_RIGHT) {

        } else if (item.INDICATOR_LOCATION == item.INDICATOR_LOCATION_OUTSIDE_BOTTOM_CENTER) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int target = holder.mBannerViewPager.getId();

            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.addRule(RelativeLayout.BELOW, target);
            params.topMargin = ScreenUtil.dp2px(mContext, 8);
            holder.mBannerIndicator.setLayoutParams(params);
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        RecyclerView.LayoutParams clp = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        if (clp instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) clp).setFullSpan(true);
        }

    }


    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (bannerComponent != null) {
            bannerComponent.stopAutoPlay();
        }
    }

    class InnerIndicatorViewPagerAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {

        private Map<Integer, View> tabViewMaps = new HashMap<>();

        private List<BannerVo> mBannerVos;
        private ViewPager      mViewPager;

        public InnerIndicatorViewPagerAdapter(List<BannerVo> mBannerVos, ViewPager targetViewPager) {
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
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            String imageUrl = mBannerVos.get(position).getPic();
            //            GlideUtils.loadImage(mContext, imageUrl, mImageView, R.mipmap.img_placeholder_v_1, 10);
            GlideApp.with(mContext)
                    .asBitmap()
                    .load(imageUrl)
                    .transform(new GlideRoundTransformNew(mContext, 10))
                    .into(mImageView);
            //            GlideApp.with(mContext)
            //                    .load(imageUrl)
            //                    .asBitmap()
            //                    .placeholder(R.mipmap.img_placeholder_v_1)
            //                    .transform(new GlideRoundTransform(mContext, 5))
            //                    .into(new SimpleTarget<Bitmap>() {
            //                        @Override
            //                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            //                            /*if (position == 0 && mViewPager != null) {
            //                                //设置ViewPager高度
            //                                int bitWidth = resource.getWidth();
            //                                int bitHeight = resource.getHeight();
            //
            //                                int pagerWidth = (int) (ScreenUtil.getScreenWidth(mContext) - 12 * ScreenUtil.getScreenDensity(mContext));
            //                                int pagerHeight = (pagerWidth * bitHeight) / bitWidth;
            //
            //                                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mViewPager.getLayoutParams();
            //                                if (params == null) {
            //                                    params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            //                                }
            //                                params.height = pagerHeight;
            //                                mViewPager.setLayoutParams(params);
            //                            }*/
            //                            mImageView.setImageBitmap(resource);
            //                        }
            //                    });


            mImageView.setOnClickListener(v -> {
                try {
                    BannerVo bannerVo = mBannerVos.get(position);
                    int game_type = 0;
                    if (bannerVo != null) {
                        appJump(bannerVo.getJumpInfo());
                        game_type = bannerVo.getGame_type();
                    }
                    int index = position;
                    switch (game_type) {
                        case 1:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(1, 3, (index + 1));
                            break;
                        case 2:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(2, 21, (index + 1));
                            break;
                        case 3:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(3, 40, (index + 1));
                            break;
                        case 4:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(4, 58, (index + 1));
                            break;
                        default:
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return convertView;
        }

    }


    class ViewHolder extends AbsHolder {
        private ViewPager          mBannerViewPager;
        private FixedIndicatorView mBannerIndicator;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mBannerViewPager = findViewById(R.id.banner_viewPager);
            mBannerIndicator = findViewById(R.id.banner_indicator);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mBannerViewPager.getLayoutParams();
            if (params != null) {
                params.height = ScreenUtil.dp2px(mContext, itemHeightDp);
                mBannerViewPager.setLayoutParams(params);
            }
        }

    }
}
