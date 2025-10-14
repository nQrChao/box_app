package com.zqhy.app.core.view.video;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.newproject.R;

import cn.jzvd.Jzvd;


/**
 * @author Administrator
 */
public class VideoPlayActivity extends FragmentHolderActivity {

    public static void launch(Activity mActivity, String video_url, String video_pic) {
        Intent intent = new Intent(mActivity, VideoPlayActivity.class);
        intent.putExtra("video_url", video_url);
        intent.putExtra("video_pic", video_pic);
        mActivity.startActivity(intent);
    }

    private String video_url, video_pic;

    @Override
    public void initViews(Bundle savedInstanceState) {
        if (getIntent() != null) {
            video_url = getIntent().getStringExtra("video_url");
            video_pic = getIntent().getStringExtra("video_pic");
        }
        super.initViews(savedInstanceState);
        loadRootFragment(R.id.content, VideoPlayFragment.newInstance(video_pic, video_url));
    }

    @Override
    public void onStop() {
        super.onStop();
        Jzvd.releaseAllVideos();
    }
}
