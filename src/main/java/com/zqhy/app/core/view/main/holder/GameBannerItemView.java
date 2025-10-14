package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.banner.BannerListVo;
import com.zqhy.app.core.data.model.banner.BannerVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideRoundTransform;
import com.zqhy.app.network.statistics.JiuYaoStatisticsApi;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.banner.BannerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pc
 * @date 2020/1/14-11:49
 * @description
 */
public class GameBannerItemView extends BaseItemHolder<BannerListVo, GameBannerItemView.ViewHolder> {
    public GameBannerItemView(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.common_banner_view;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    private float scale = 2.30f;
    private float density;

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final BannerListVo bannerAdListVo) {
        int bannerHeight = (int) (ScreenUtil.getScreenWidth(mContext) / scale);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, bannerHeight);
        params.rightMargin = (int) (14 * density);
        params.leftMargin = (int) (14 * density);
        holder.mBannerView.setLayoutParams(params);

        if (bannerAdListVo.getData() != null && !bannerAdListVo.getData().isEmpty()) {
            holder.mBannerView.delayTime(5).setBannerView(new BannerView.OnBindView() {
                @Override
                public List<ImageView> bindView() {
                    List<ImageView> imageViewList = new ArrayList<>();
                    if (bannerAdListVo.getData() != null) {
                        for (int i = 0; i < bannerAdListVo.getData().size(); i++) {
                            BannerVo bannerVo = bannerAdListVo.getData().get(i);
                            ImageView mImageView = new ImageView(mContext);
                            mImageView.setAdjustViewBounds(true);
                            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            mImageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            int padding = ScreenUtil.dp2px(mContext, 16);
                            mImageView.setPadding(padding, 0, padding, 0);
                            imageViewList.add(mImageView);
                            GlideApp.with(mContext).asBitmap()
                                    .load(bannerVo.getPic())

                                    .transform(new GlideRoundTransform(mContext, 6))
                                    .placeholder(R.mipmap.img_placeholder_v_2)
                                    .into(mImageView);
                        }
                    }
                    return imageViewList;
                }
            }).build(bannerAdListVo.getData());

            holder.mBannerView.setOnBannerItemClickListener(index -> {
                try {
                    BannerVo bannerVo = bannerAdListVo.getData().get(index);
                    int game_type = 0;
                    if (bannerVo != null) {
                        appJump(bannerVo.getJumpInfo());
                        game_type = bannerVo.getGame_type();
                    }
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

    static class ViewHolder extends AbsHolder {

        private BannerView mBannerView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mBannerView = findViewById(R.id.banner);
        }

    }

}
