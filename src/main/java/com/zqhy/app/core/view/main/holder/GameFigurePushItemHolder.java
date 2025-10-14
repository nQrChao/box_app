package com.zqhy.app.core.view.main.holder;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chaoji.im.glide.GlideApp;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.mainpage.figurepush.GameFigurePushVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.glide.GlideRoundTransform;
import com.zqhy.app.network.statistics.JiuYaoStatisticsApi;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.TitleTextView;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class GameFigurePushItemHolder extends BaseItemHolder<GameFigurePushVo, GameFigurePushItemHolder.ViewHolder> {

    private float density;

    public GameFigurePushItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_figure_push;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull GameFigurePushVo gameFigurePushVo) {
        GlideApp.with(mContext)
                .asBitmap()
                .load(gameFigurePushVo.getPic())
                .placeholder(R.mipmap.img_placeholder_v_1)
                .transform(new GlideRoundTransform(mContext, 5))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        int bitWidth = bitmap.getWidth();
                        int bitHeight = bitmap.getHeight();

                        int mImageHeight = bitHeight * ScreenUtils.getScreenWidth(mContext) / bitWidth;

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mImageHeight);
                        viewHolder.mIvFigurePush.setLayoutParams(params);
                        viewHolder.mIvFigurePush.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable drawable) {

                    }
                });
        if (!TextUtils.isEmpty(gameFigurePushVo.getTitle())) {
            viewHolder.mLlTitleContainer.setVisibility(View.VISIBLE);
            viewHolder.mTvGameTitle.setText(gameFigurePushVo.getTitle());
        } else {
            viewHolder.mLlTitleContainer.setVisibility(View.GONE);
        }

        viewHolder.mLlGameFigurePush.setOnClickListener(view -> {
            appJump(gameFigurePushVo.getPage_type(), gameFigurePushVo.getParam());
            int position = gameFigurePushVo.getEventPosition();
            List<Integer> eventList = gameFigurePushVo.getEventList();
            if (eventList != null && eventList.size() > 0) {
                for (Integer event : eventList) {
                    switch (gameFigurePushVo.getGame_type()) {
                        case 1:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(1, event, position);
                            break;
                        case 2:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(2, event, position);
                            break;
                        case 3:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(3, event, position);
                            break;
                        case 4:
                            JiuYaoStatisticsApi.getInstance().eventStatistics(4, event, position);
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        viewHolder.mTvAllGame.setVisibility(gameFigurePushVo.showAllGameText() ? View.VISIBLE : View.GONE);
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout       mLlTitleContainer;
        private LinearLayout       mLlGameFigurePush;
        private ImageView mIvFigurePush;
        private TextView           mTvGameTitle;
        private TitleTextView      mTvAllGame;

        public ViewHolder(View view) {
            super(view);
            mLlTitleContainer = findViewById(R.id.ll_title_container);
            mLlGameFigurePush = findViewById(R.id.ll_game_figure_push);
            mIvFigurePush = findViewById(R.id.iv_figure_push);
            mTvGameTitle = findViewById(R.id.tv_game_title);
            mTvAllGame = findViewById(R.id.tv_all_game);

        }
    }
}
