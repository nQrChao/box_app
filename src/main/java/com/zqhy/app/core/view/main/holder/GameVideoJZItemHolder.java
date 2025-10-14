package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.chaoji.other.blankj.utilcode.util.Logs;
import com.danikula.videocache.HttpProxyCacheServer;
import com.zqhy.app.App;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.data.model.video.GameVideoInfoVo;
import com.zqhy.app.core.data.model.video.VideoParamVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.video.CustomMediaPlayer.JZExoPlayer;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * @author Administrator
 */
public class GameVideoJZItemHolder extends BaseItemHolder<GameVideoInfoVo, GameVideoJZItemHolder.ViewHolder> {

    private float density;

    public GameVideoJZItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_video_jz;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameVideoInfoVo item) {
        holder.mTvIdTag.setText(item.getTitle2());
        holder.mTvGameTitle.setText(item.getTitle());

        try {
            try {
                int txtColor = Color.parseColor(item.getTitle2_color());
                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(4 * density);
                gd.setStroke((int) (0.8 * density), txtColor);
                holder.mTvIdTag.setTextColor(txtColor);
                holder.mTvIdTag.setBackground(gd);
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.mLlVideoDescription.setOnClickListener(v -> {
                if (_mFragment != null) {
                    int gameid = item.getGameid();
                    int game_type = item.getGame_type();
                    FragmentHolderActivity.startFragmentInActivity(_mFragment.getActivity(),
                            GameDetailInfoFragment.newInstance(gameid, game_type));
                }
            });

            AppBaseJumpInfoBean.ParamBean paramBean = item.getParam();
            if (paramBean != null) {
                VideoParamVo videoParamVo = paramBean.getVideo_param();
                if (videoParamVo != null) {
                    setVideoPlayer(holder, videoParamVo);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void setVideoPlayer(ViewHolder holder, VideoParamVo videoParamVo) {
        String urls = videoParamVo.getVideo_url();
        if (holder.mVideoPlayer == null || urls == null) {
            return;
        }
        Logs.e("视频链接：" + urls);

        HttpProxyCacheServer proxy = App.getProxy(mContext);
        String proxyUrl = proxy.getProxyUrl(urls);

        Logs.e("视频链接(proxyUrl)：" + proxyUrl);
        Jzvd.setMediaInterface(new JZExoPlayer());
        holder.mVideoPlayer.setUp(proxyUrl, "", JzvdStd.SCREEN_WINDOW_LIST);
        GlideApp.with(_mFragment).load(videoParamVo.getVideo_preview()).centerCrop().into(holder.mVideoPlayer.thumbImageView);
    }

    public class ViewHolder extends AbsHolder {

        private JzvdStd mVideoPlayer;
        private TextView mTvIdTag;
        private TextView mTvGameTitle;
        private LinearLayout mLlVideoDescription;
        private FrameLayout mFlVideoContainer;

        public ViewHolder(View view) {
            super(view);
            mVideoPlayer = findViewById(R.id.video_player);
            mTvIdTag = findViewById(R.id.tv_id_tag);
            mTvGameTitle = findViewById(R.id.tv_game_title);
            mLlVideoDescription = findViewById(R.id.ll_video_description);
            mFlVideoContainer = findViewById(R.id.fl_video_container);

            int width = ScreenUtil.getScreenWidth(mContext);
            int height = width * 9 / 16;
            LinearLayout.LayoutParams videoContainerParams = new LinearLayout.LayoutParams(width, height);
            mFlVideoContainer.setLayoutParams(videoContainerParams);
        }
    }
}
