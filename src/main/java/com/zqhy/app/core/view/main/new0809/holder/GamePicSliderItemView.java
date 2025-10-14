package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.MainXingYouDataVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideRoundTransformNew;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2021/8/25-12:10
 * @description
 */
public class GamePicSliderItemView extends BaseItemHolder<MainXingYouDataVo.GamePicSliderDataBeanVo, GamePicSliderItemView.ViewHolder> {

    public GamePicSliderItemView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_page_banner;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainXingYouDataVo.GamePicSliderDataBeanVo item) {
        ViewGroup.LayoutParams params = holder.mBanner.getLayoutParams();
        if (params != null) {
            params.height = (ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 28)) * 442 / 750;
            holder.mBanner.setLayoutParams(params);
        }
        int bannerSize = item.data.size();
        //设置banner样式
        holder.mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        holder.mBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                GameInfoVo bannerVo = (GameInfoVo) path;
                GlideApp.with(mContext)
                        .load(bannerVo.getBg_pic())
                        .placeholder(R.mipmap.img_placeholder_v_load)
                        .error(R.mipmap.img_placeholder_v_load)
                        .transform(new GlideRoundTransformNew(mContext, 10))
                        .into(imageView);
            }
        });
        //设置图片集合
        holder.mBanner.setImages(item.data);
        //设置banner动画效果
        holder.mBanner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        if (bannerSize > 1) {
            //设置轮播时间
            holder.mBanner.setDelayTime(5000);
            holder.mBanner.isAutoPlay(true);
        } else {
            holder.mBanner.isAutoPlay(false);
        }
        holder.mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置指示器位置（当banner模式中有指示器时）
        holder.mBanner.setIndicatorGravity(BannerConfig.CENTER);
        holder.mBanner.setOnBannerListener(position -> {
            GameInfoVo gameInfoVo = item.data.get(position);
            if (_mFragment != null) _mFragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
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
