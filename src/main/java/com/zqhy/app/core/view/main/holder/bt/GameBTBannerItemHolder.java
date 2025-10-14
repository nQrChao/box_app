package com.zqhy.app.core.view.main.holder.bt;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shizhefei.view.indicator.BannerComponent;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.banner.BannerLayout;
import com.zqhy.app.core.card_banner.view.RoundedImageView;
import com.zqhy.app.core.data.model.banner.BannerListVo;
import com.zqhy.app.core.data.model.banner.BannerVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.network.statistics.JiuYaoStatisticsApi;
import com.zqhy.app.newproject.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leeham2734
 * @date 2020/8/18-11:51
 * @description
 */
public class GameBTBannerItemHolder extends BaseItemHolder<BannerListVo, GameBTBannerItemHolder.ViewHolder> {

    public GameBTBannerItemHolder(Context context) {
        super(context);
    }

    private int itemHeightDp = 156;

    public GameBTBannerItemHolder(Context context, int itemHeightDp) {
        super(context);
        this.itemHeightDp = itemHeightDp;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_bt_banner;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    BannerComponent bannerComponent;

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull BannerListVo item) {
        //        List<String> list = new ArrayList<>();
        //        for (BannerVo bannerVo : item.getData()) {
        //            list.add(bannerVo.getPic());
        //        }
        //
        //        int viewWidth, borderWidth;
        //        viewWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        //        borderWidth = (int) (mContext.getResources().getDisplayMetrics().density * 16);
        //
        //        BannerAdapter webBannerAdapter = new BannerAdapter(mContext, list, viewWidth, borderWidth);
        //        webBannerAdapter.setOnBannerItemClickListener(index -> {
        //            appJump(item.getData().get(index).getJumpInfo());
        //        });
        //        holder.mRecyclerBanner.setAdapter(webBannerAdapter);

        InnerIndicatorViewPagerAdapter adapter = new InnerIndicatorViewPagerAdapter(item.getData(), holder.mBannerViewPager);

        bannerComponent = new BannerComponent(holder.mBannerIndicator, holder.mBannerViewPager, false);
        holder.mBannerViewPager.setPageMargin(16);
        holder.mBannerViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            float scale = 0.95f;

            @Override
            public void transformPage(View page, float position) {
                if (position >= 0 && position <= 1) {
                    page.setScaleY(scale + (1 - scale) * (1 - position));
                } else if (position > -1 && position < 0) {
                    page.setScaleY(1 + (1 - scale) * position);
                } else {
                    page.setScaleY(scale);
                }
            }
        });
        holder.mBannerViewPager.setOffscreenPageLimit(item.getData().size());
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

                    params.width = (int) (7 * density);
                    params.height = (int) (7 * density);
                    GradientDrawable gd = new GradientDrawable();
                    gd.setCornerRadius(7 * density);
                    gd.setShape(GradientDrawable.OVAL);
                    if (key == currentItem) {
                        gd.setColor(Color.parseColor("#0052FE"));
                    } else {
                        gd.setColor(Color.parseColor("#FFFFFF"));
                    }
                    selectItemView.setBackground(gd);
                    selectItemView.setLayoutParams(params);
                }
            }
        });
        bannerComponent.setAdapter(adapter);
        bannerComponent.setCurrentItem(0, false);
        bannerComponent.setAutoPlayTime(5000);
        bannerComponent.startAutoPlay();

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
                //                int padding = ScreenUtil.dp2px(mContext, 14);
                //                convertView.setPadding(padding, 0, padding, 0);
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                ImageView imageView = new ImageView(mContext);
                imageView.setId(R.id.banner);
                imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                ((ViewGroup) convertView).addView(imageView);
            }
            ImageView mImageView = convertView.findViewById(R.id.banner);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            String imageUrl = mBannerVos.get(position).getPic();
            GlideUtils.loadImage(mContext, imageUrl, mImageView, R.mipmap.img_placeholder_v_1);
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

    public class ViewHolder extends AbsHolder {
        //        private BannerLayout       mRecyclerBanner;
        private ViewPager          mBannerViewPager;
        private FixedIndicatorView mBannerIndicator;

        public ViewHolder(View view) {
            super(view);
            //            mRecyclerBanner = findViewById(R.id.recycler_banner);
            mBannerViewPager = findViewById(R.id.banner_viewPager);
            mBannerIndicator = findViewById(R.id.banner_indicator);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mBannerViewPager.getLayoutParams();
            if (params != null) {
                params.height = ScreenUtil.dp2px(mContext, itemHeightDp);
                mBannerViewPager.setLayoutParams(params);
            }
        }
    }


    class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {

        private Context                                context;
        private List<String>                           urlList;
        private BannerLayout.OnBannerItemClickListener onBannerItemClickListener;

        private int width;
        private int borderWidth;

        public BannerAdapter(Context context, List<String> urlList, int width, int borderWidth) {
            this.context = context;
            this.urlList = urlList;

            this.width = width;
            this.borderWidth = borderWidth;
        }

        public void setOnBannerItemClickListener(BannerLayout.OnBannerItemClickListener onBannerItemClickListener) {
            this.onBannerItemClickListener = onBannerItemClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(com.library.R.layout.banner_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(width - borderWidth * 2, ViewGroup.LayoutParams.MATCH_PARENT));
            if (urlList == null || urlList.isEmpty())
                return;

            final int P = position % urlList.size();
            String url = urlList.get(P);
            ImageView img = holder.roundedImageView;
            GlideUtils.loadNormalImage(mContext, url, img, R.mipmap.img_placeholder_v_1);
            img.setOnClickListener(view -> {
                if (onBannerItemClickListener != null) {
                    onBannerItemClickListener.onItemClick(P);
                }
            });
        }

        @Override
        public int getItemCount() {
            if (urlList != null) {
                return urlList.size();
            }
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public RoundedImageView roundedImageView;
            public TextView         mainTitle, subtitleTitle;
            //            private ImageView mImage;

            public ViewHolder(View itemView) {
                super(itemView);
                roundedImageView = (RoundedImageView) itemView.findViewById(com.library.R.id.item_img);
                mainTitle = (TextView) itemView.findViewById(com.library.R.id.main_text);
                subtitleTitle = (TextView) itemView.findViewById(com.library.R.id.subtitle_text);

                //                mImage = itemView.findViewById(R.id.image);

            }
        }
    }
}
