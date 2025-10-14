package com.zqhy.app.core.view.splash;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chaoji.im.glide.GlideApp;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.zqhy.app.core.data.model.splash.AppStyleConfigs;
import com.zqhy.app.core.data.model.splash.SplashVo;
import com.zqhy.app.core.tool.ImageUtils;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.utils.cache.ACache;
import com.zqhy.app.utils.sdcard.SdCardManager;

import java.io.File;
import java.io.IOException;

/**
 * @author Administrator
 * @date 2018/11/11
 */

public class AppStyleHelper {

    private static volatile AppStyleHelper instance;

    private AppStyleHelper() {
    }

    public static AppStyleHelper getInstance() {
        if (instance == null) {
            synchronized (AppStyleHelper.class) {
                if (instance == null) {
                    instance = new AppStyleHelper();
                }
            }
        }
        return instance;
    }

    public void handlerAppStyle(Activity mActivity, String resultJson, SplashVo.AppStyleVo.DataBean dataBean) {
        File targetFile = SdCardManager.getFileMenuDir(mActivity);
        if (targetFile == null) {
            return;
        }
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        ACache.get(targetFile).put(AppStyleConfigs.JSON_KEY, resultJson);
        saveAppStyleInfo(mActivity, targetFile, dataBean);
    }

    public void removeAppStyleData(Activity mActivity) {
        File targetFile = SdCardManager.getFileMenuDir(mActivity);
        if (targetFile == null) {
            return;
        }
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        ACache.get(targetFile).remove(AppStyleConfigs.JSON_KEY);
    }

    private void saveHeaderInfo(Context mContext, String url, String destFileDir, String destFileName, int fileHeight) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        try {
            GlideApp.with(mContext)
                    .asBitmap()
                    .load(url)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            if (bitmap != null) {
                                int width = bitmap.getWidth();
                                int height = bitmap.getHeight();
                                int fileWidth = width * fileHeight / height;
                                Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, fileWidth, fileHeight, true);
                                newBitmap.setHasAlpha(true);
                                ImageUtils.saveBitmap(newBitmap, destFileDir, destFileName);
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable drawable) {

                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存App主题信息
     *
     * @param targetFile
     * @param dataBean
     */
    public void saveAppStyleInfo(Context mContext, File targetFile, SplashVo.AppStyleVo.DataBean dataBean) {
        if (dataBean == null) {
            return;
        }

        //头部底部图片
        if (dataBean.getApp_header_info() != null) {
            int topHeaderHeight = (int) (36 * ScreenUtil.getScreenDensity(mContext));
            saveHeaderInfo(mContext, dataBean.getApp_header_info().getHeader_bg(), targetFile.getPath(), AppStyleConfigs.IMG_TOP_BG, topHeaderHeight);
        }
        //底部
        if (dataBean.getApp_bottom_info() != null) {

            int bottomTabIconWidth = (int) (23 * ScreenUtil.getScreenDensity(mContext));
            int bottomTabIconHeight = (int) (23 * ScreenUtil.getScreenDensity(mContext));

            saveAppStylePicWithGlide(mContext, dataBean.getApp_bottom_info().getIndex_button_default_icon(), targetFile.getPath(), AppStyleConfigs.TAB_MAIN_1_NORMAL_FILE_NAME, bottomTabIconWidth, bottomTabIconHeight);
            saveAppStylePicWithGlide(mContext, dataBean.getApp_bottom_info().getIndex_button_selected_icon(), targetFile.getPath(), AppStyleConfigs.TAB_MAIN_1_SELECT_FILE_NAME, bottomTabIconWidth, bottomTabIconHeight);

            saveAppStylePicWithGlide(mContext, dataBean.getApp_bottom_info().getServer_button_default_icon(), targetFile.getPath(), AppStyleConfigs.TAB_MAIN_2_NORMAL_FILE_NAME, bottomTabIconWidth, bottomTabIconHeight);
            saveAppStylePicWithGlide(mContext, dataBean.getApp_bottom_info().getServer_button_selected_icon(), targetFile.getPath(), AppStyleConfigs.TAB_MAIN_2_SELECT_FILE_NAME, bottomTabIconWidth, bottomTabIconHeight);

            saveAppStylePicWithGlide(mContext, dataBean.getApp_bottom_info().getType_button_default_icon(), targetFile.getPath(), AppStyleConfigs.TAB_MAIN_3_NORMAL_FILE_NAME, bottomTabIconWidth, bottomTabIconHeight);
            saveAppStylePicWithGlide(mContext, dataBean.getApp_bottom_info().getType_button_selected_icon(), targetFile.getPath(), AppStyleConfigs.TAB_MAIN_3_SELECT_FILE_NAME, bottomTabIconWidth, bottomTabIconHeight);

            saveAppStylePicWithGlide(mContext, dataBean.getApp_bottom_info().getService_button_default_icon(), targetFile.getPath(), AppStyleConfigs.TAB_MAIN_4_NORMAL_FILE_NAME, bottomTabIconWidth, bottomTabIconHeight);
            saveAppStylePicWithGlide(mContext, dataBean.getApp_bottom_info().getService_button_selected_icon(), targetFile.getPath(), AppStyleConfigs.TAB_MAIN_4_SELECT_FILE_NAME, bottomTabIconWidth, bottomTabIconHeight);

            int centerTabIconWidth = (int) (102 * ScreenUtil.getScreenDensity(mContext));
            int centerTabIconHeight = (int) (80 * ScreenUtil.getScreenDensity(mContext));

            saveAppStylePicWithGlide(mContext, dataBean.getApp_bottom_info().getCenter_button_icon(), targetFile.getPath(), AppStyleConfigs.TAB_MAIN_CENTER_FILE_NAME, centerTabIconWidth, centerTabIconHeight);
        }
        //节日按钮
        if (dataBean.getContainer_icon() != null) {
            saveAppStyleFile(dataBean.getContainer_icon().getIcon(), targetFile.getPath(), AppStyleConfigs.BUTTON_GAME_LIST_URL);
            AppStyleConfigs.BUTTON_GAME_LIST_URL = dataBean.getContainer_icon().getIcon();
        }

        //按钮颜色
        if (dataBean.getGame_button_color() != null) {
            AppStyleConfigs.BUTTON_GAME_COLOR = dataBean.getGame_button_color().getColor();
        }
    }

    private void saveAppStyleFile(String url, String destFileDir, String destFileName) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        OkGo.<File>get(url).execute(new FileCallback(destFileDir, destFileName) {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<File> response) {

            }
        });
    }

    private void saveAppStylePicWithGlide(Context mContext, String url, String destFileDir, String destFileName, int width, int height) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        try {
            File targetFile = new File(destFileDir, destFileName);
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            GlideApp.with(mContext)
                    .asBitmap()
                    .load(url)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            // 下载成功回调函数
                            // 数据处理方法，保存bytes到文件
                            ImageUtils.saveBitmap(bitmap, destFileDir, destFileName);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable drawable) {

                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
