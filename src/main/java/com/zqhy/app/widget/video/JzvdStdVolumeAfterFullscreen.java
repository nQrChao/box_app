package com.zqhy.app.widget.video;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.newproject.R;

import java.lang.reflect.Constructor;

import cn.jzvd.JZDataSource;
import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUserActionStd;
import cn.jzvd.JZUtils;
import cn.jzvd.JzvdMgr;
import cn.jzvd.JzvdStd;

/**
 * @author Administrator
 */
public class JzvdStdVolumeAfterFullscreen extends JzvdStd {

    public JzvdStdVolumeAfterFullscreen(Context context) {
        super(context);
    }

    public JzvdStdVolumeAfterFullscreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onPrepared() {
        super.onPrepared();
        //        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
        //            JZMediaManager.instance().jzMediaInterface.setVolume(1f, 1f);
        //        } else {
        //            JZMediaManager.instance().jzMediaInterface.setVolume(0f, 0f);
        //        }
        setVolume(isMute);
    }

    @Override
    public int getLayoutId() {
        return R.layout.jz_layout_std_with_volume;
    }

    private ImageView mVolume;

    @Override
    public void init(Context context) {
        super.init(context);
        mVolume = findViewById(R.id.volume);
        mVolume.setOnClickListener(this);
        mVolume.setVisibility(VISIBLE);
    }

    @Override
    public void setUp(JZDataSource jzDataSource, int screen) {
        super.setUp(jzDataSource, screen);
        if (screen == SCREEN_WINDOW_FULLSCREEN) {
            titleTextView.setVisibility(View.VISIBLE);
            fullscreenButton.setImageResource(R.mipmap.ic_video_shrink);
        } else {
            titleTextView.setVisibility(View.INVISIBLE);
            fullscreenButton.setImageResource(R.mipmap.ic_video_enlarge);
        }
        mVolume.setImageResource(isMute ? R.mipmap.ic_video_volume_mute : R.mipmap.ic_video_volume_normal);
    }


    /**
     * 退出全屏模式的时候开启静音模式
     */
    @Override
    public void playOnThisJzvd() {
        super.playOnThisJzvd();
        //        JZMediaManager.instance().jzMediaInterface.setVolume(0f, 0f);
        isMute = isFullScreenMute;
        setVolume(isMute);
    }


    @Override
    public void showWifiDialog() {
        //        super.showWifiDialog();
        showNonWifiPlayVideoTipsDialog();
    }

    /**
     * 2019.05.30 更新非WIFI 播放视频提示框
     ***********************************************************************************************************************/

    protected void showNonWifiPlayVideoTipsDialog() {
        CustomDialog wifiPlayVideoTipsDialog = new CustomDialog(getContext(), LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog_non_wifi_play_video_tips, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        TextView mTvCancel = (TextView) wifiPlayVideoTipsDialog.findViewById(R.id.tv_cancel);
        TextView mTvContinue = (TextView) wifiPlayVideoTipsDialog.findViewById(R.id.tv_continue);
        mTvCancel.setOnClickListener(v -> {
            if (wifiPlayVideoTipsDialog != null && wifiPlayVideoTipsDialog.isShowing()) {
                wifiPlayVideoTipsDialog.dismiss();
            }
            clearFloatScreen();
        });
        mTvContinue.setOnClickListener(v -> {
            if (wifiPlayVideoTipsDialog != null && wifiPlayVideoTipsDialog.isShowing()) {
                wifiPlayVideoTipsDialog.dismiss();
            }
            onEvent(JZUserActionStd.ON_CLICK_START_WIFIDIALOG);
            startVideo();
            //            WIFI_TIP_DIALOG_SHOWED = true;
        });
        wifiPlayVideoTipsDialog.show();
    }

    private boolean isMute = true;

    @Override
    public void onClick(View v) {
        try {
            super.onClick(v);
            switch (v.getId()) {
                case R.id.volume:
                    isMute = !isMute;
                    setVolume(isMute);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setVolume(boolean isMute) {
        try {
            this.isMute = isMute;
            if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
                isFullScreenMute = isMute;
            }
            if (isMute) {
                mVolume.setImageResource(R.mipmap.ic_video_volume_mute);
                JZMediaManager.instance().jzMediaInterface.setVolume(0f, 0f);
            } else {
                mVolume.setImageResource(R.mipmap.ic_video_volume_normal);
                JZMediaManager.instance().jzMediaInterface.setVolume(1f, 1f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isFullScreenMute;

    @Override
    public void startWindowFullscreen() {
        Log.i(TAG, "startWindowFullscreen " + " [" + this.hashCode() + "] ");
        hideSupportActionBar(getContext());

        ViewGroup vp = (JZUtils.scanForActivity(getContext()))//.getWindow().getDecorView();
                .findViewById(Window.ID_ANDROID_CONTENT);
        View old = vp.findViewById(cn.jzvd.R.id.jz_fullscreen_id);
        if (old != null) {
            vp.removeView(old);
        }
        textureViewContainer.removeView(JZMediaManager.textureView);
        try {
            Constructor<JzvdStdVolumeAfterFullscreen> constructor = (Constructor<JzvdStdVolumeAfterFullscreen>) JzvdStdVolumeAfterFullscreen.this.getClass().getConstructor(Context.class);
            JzvdStdVolumeAfterFullscreen jzvd = constructor.newInstance(getContext());
            jzvd.setId(cn.jzvd.R.id.jz_fullscreen_id);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            vp.addView(jzvd, lp);
            jzvd.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
            jzvd.setUp(jzDataSource, JzvdStd.SCREEN_WINDOW_FULLSCREEN);
            jzvd.setState(currentState);
            jzvd.addTextureView();
            jzvd.setVolume(isMute);
            isFullScreenMute = jzvd.isMute;
            JzvdMgr.setSecondFloor(jzvd);
            JZUtils.setRequestedOrientation(getContext(), FULLSCREEN_ORIENTATION);

            onStateNormal();
            jzvd.progressBar.setSecondaryProgress(progressBar.getSecondaryProgress());
            jzvd.startProgressTimer();
            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
