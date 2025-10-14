package com.zqhy.app.core.view.video;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.chaoji.other.blankj.utilcode.util.Logs;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.video.CustomMediaPlayer.JZExoPlayer;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;


/**
 * @author Administrator
 */
public class VideoPlayFragment extends BaseFragment {

    public static VideoPlayFragment newInstance(String video_pic, String video_url) {
        VideoPlayFragment fragment = new VideoPlayFragment();
        Bundle bundle = new Bundle();
        bundle.putString("video_pic", video_pic);
        bundle.putString("video_url", video_url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_play_video;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private String video_url;
    private String video_pic;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            video_url = getArguments().getString("video_url");
            video_pic = getArguments().getString("video_pic");
        }
        super.initView(state);
        showSuccess();
        bindViews();
    }


    private JzvdStd mVideoPlayer;

    private void bindViews() {
        mVideoPlayer = findViewById(R.id.video_player);
        setVideoPlayer(video_url, video_pic);
    }


    private void setVideoPlayer(String video_url, String video_pic) {
        String urls = video_url;
        if (mVideoPlayer == null || urls == null) {
            return;
        }
        Logs.e("视频链接：" + urls);

        Jzvd.setMediaInterface(new JZExoPlayer());
        mVideoPlayer.setUp(urls, "", JzvdStd.SCREEN_WINDOW_LIST);
        GlideApp.with(this).load(video_pic).centerCrop().into(mVideoPlayer.thumbImageView);
        mVideoPlayer.startVideo();
    }
}
