package com.zqhy.app.core.view.main.new0809;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.banner.BannerListVo;
import com.zqhy.app.core.data.model.banner.BannerVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideRoundTransformNew;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leeham2734
 * @date 2021/8/25-12:10
 * @description
 */
public class MainZkBannerItemView extends BaseItemHolder<BannerListVo, MainZkBannerItemView.ViewHolder> {


    private int itemHeightDp = 156;

    public MainZkBannerItemView(Context context) {
        super(context);
    }

    public MainZkBannerItemView(Context context, int itemHeightDp) {
        super(context);
        this.itemHeightDp = itemHeightDp;
    }


    @Override
    public int getLayoutResId() {
        return R.layout.item_main_page_banner_zk;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull BannerListVo item) {
        ViewGroup.LayoutParams params = holder.mBanner.getLayoutParams();
        if (params != null) {
            if (item.itemHeightDp == 86){
                params.height = (ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 28)) * 180 / 710;
            }else {
                params.height = ScreenUtil.dp2px(mContext, item.itemHeightDp);
            }
            holder.mBanner.setLayoutParams(params);
        }
        int bannerSize = item.getData().size();
        //设置banner样式
        holder.mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        holder.mBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                BannerVo bannerVo = (BannerVo) path;
                GlideApp.with(mContext)
                        .load(bannerVo.getPic())
                        .placeholder(R.mipmap.img_placeholder_v_load)
                        .error(R.mipmap.img_placeholder_v_load)
                        .transform(new GlideRoundTransformNew(mContext, 10))
                        .into(imageView);
            }
        });
        //设置图片集合
        holder.mBanner.setImages(item.getData());
        //设置banner动画效果
        holder.mBanner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        if (item.isLoop && bannerSize > 1) {
            //设置轮播时间
            holder.mBanner.setDelayTime(5000);
            holder.mBanner.isAutoPlay(true);
        } else {
            holder.mBanner.isAutoPlay(false);
        }
        holder.mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置指示器位置（当banner模式中有指示器时）
        holder.mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        List<String> titleList = new ArrayList<>();
        for (int i = 0; i < bannerSize; i++) {
            if (!TextUtils.isEmpty(item.getData().get(i).getTitle())){
                titleList.add(item.getData().get(i).getTitle());
            }else {
                titleList.add("");
            }
        }
        holder.mBanner.setBannerTitles(titleList);
        holder.mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerVo bannerVo = item.getData().get(position);
                if (bannerVo != null) {
                    appJump(bannerVo.getJumpInfo());
                }
            }
        });
        //banner设置方法全部调用完毕时最后调用
        holder.mBanner.start();
    }

    public class ViewHolder extends AbsHolder {
        private Banner mBanner;

        public ViewHolder(View view) {
            super(view);
            mBanner = findViewById(R.id.banner);
        }
    }
}
