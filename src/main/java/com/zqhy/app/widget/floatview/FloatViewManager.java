package com.zqhy.app.widget.floatview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.box.common.glide.GlideApp;
import com.box.other.hjq.toast.Toaster;
import com.xuexiang.xui.widget.button.ButtonView;
import com.zqhy.app.core.tool.MD5Utils;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.AppSplashActivity;
import com.zqhy.app.network.simple.download.SimpleDownloadManger;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.AppManager;
import com.zqhy.app.utils.LifeUtil;
import com.zqhy.app.utils.sdcard.SdCardManager;
import com.zqhy.app.utils.sp.SPUtils;

import java.io.File;

/**
 * @author leeham2734
 * @date 2021/4/15-11:32
 * @description
 */
public class FloatViewManager {

    public static final String SP_ANDROID_DOWNLOAD_URL = "SP_ANDROID_DOWNLOAD_URL";
    public static final String SP_FLOAT_IMAGE_URL      = "SP_FLOAT_IMAGE_URL";
    public static final String SP_FLOAT_IMAGE_DES      = "SP_FLOAT_IMAGE_DES";

    private static final String TAG = "FloatViewManager";

    private static FloatViewManager newInstance;

    private FloatViewManager() {
    }

    public static FloatViewManager getInstance() {
        if (newInstance == null) {
            newInstance = new FloatViewManager();
        }
        return newInstance;
    }

    private boolean isEnable = false;

    public void setEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    private View createImage(Context context) {
        ImageView view = new ImageView(context);
        view.setImageResource(R.mipmap.audit_img_float_image);

        view.setOnClickListener(v -> {
            showDialog(v);
        });
        return view;
    }

    private void showDialog(View clickFrom) {
        if (AppManager.getInstance().getTopActivity() != null) {
            Activity activity = AppManager.getInstance().getTopActivity();
            if (!LifeUtil.isAlive(activity)) {
                return;
            }
            SPUtils spUtils = new SPUtils(activity, AppSplashActivity.SP_MARKET_INIT);

            CustomDialog dialog = new CustomDialog(activity, LayoutInflater.from(activity).inflate(R.layout.layout_dialog_float, null),
                    WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

            ImageView mIvClosed = dialog.findViewById(R.id.iv_closed);
            ButtonView mBtnConfirm = dialog.findViewById(R.id.btn_confirm);
            ImageView mPopImage = dialog.findViewById(R.id.pop_image);


            String imageUrl = spUtils.getString(SP_FLOAT_IMAGE_URL);
            String imageDes = spUtils.getString(SP_FLOAT_IMAGE_DES);

            GlideApp.with(activity)
                    .asBitmap()
                    .load(imageUrl)
                    .placeholder(R.mipmap.img_placeholder_v_1)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            if (bitmap == null) {
                                return;
                            }
                            mPopImage.setImageBitmap(bitmap);
                            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mPopImage.getLayoutParams();

                            int itemWidth = ScreenUtil.getScreenWidth(activity) - layoutParams.leftMargin - layoutParams.rightMargin;
                            int itemHeight = itemWidth * bitmap.getHeight() / bitmap.getWidth();

                            layoutParams.height = itemHeight;
                            mPopImage.setLayoutParams(layoutParams);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable drawable) {

                        }
                    });
            mBtnConfirm.setText(imageDes);

            dialog.setOnDismissListener(dialogInterface -> {
                if (dialogInterface instanceof Dialog) {
                    clickFrom.setEnabled(!((Dialog) dialogInterface).isShowing());
                    clickFrom.setVisibility(((Dialog) dialogInterface).isShowing() ? View.INVISIBLE : View.VISIBLE);
                }
            });
            dialog.setOnShowListener(dialogInterface -> {
                if (dialogInterface instanceof Dialog) {
                    clickFrom.setEnabled(!((Dialog) dialogInterface).isShowing());
                    clickFrom.setVisibility(((Dialog) dialogInterface).isShowing() ? View.INVISIBLE : View.VISIBLE);
                }
            });
            mIvClosed.setOnClickListener(view -> {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            });
            mBtnConfirm.setOnClickListener(view -> {
                //具体业务跳转
                //系统下载框架下载文件
                String downloadUrl = spUtils.getString(SP_ANDROID_DOWNLOAD_URL);
                if (TextUtils.isEmpty(downloadUrl)) {
                    Toaster.show("下载地址错误");
                    return;
                }
                File saveFile = new File(SdCardManager.getInstance().getDownloadApkDir(), MD5Utils.encode(downloadUrl) + ".apk");
                //                long downloadId = SimpleDownloadManger.getInstance().downloadApk(activity.getApplicationContext(), downloadUrl, "更新下载", "", saveFile);
                //                if (downloadId > 0) {
                //                    Toaster.show("正在更新...");
                //                    setReceiver(activity);
                //                    if (dialog != null && dialog.isShowing()) {
                //                        dialog.dismiss();
                //                    }
                //                } else {
                //                    Toaster.show("下载错误");
                //                }
                SimpleDownloadManger.getInstance().showAndDownloadApk(activity, downloadUrl, "精彩马上开启", "", saveFile);
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    /**
     * 注入固定位置的悬浮按钮
     *
     * @param activity
     */
    public void injectFloatView(Activity activity) {
        if (isEnable) {
            if (!LifeUtil.isAlive(activity)) {
                return;
            }
            FrameLayout decorView = (FrameLayout) activity.getWindow().getDecorView();
            View itemView = createImage(activity);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            int floatWidth = ScreenUtil.dp2px(activity, 100);
            int floatHeight = ScreenUtil.dp2px(activity, 100);

            int marginX = ScreenUtil.dp2px(activity, 12);
            int marginY = ScreenUtil.dp2px(activity, 60);

            int offsetX = ScreenUtil.getScreenWidth(activity) - floatWidth - marginX;
            int offsetY = ScreenUtil.getScreenHeight(activity) - floatHeight - ScreenUtil.getStatusBarHeight(activity) - marginY;

            itemView.setX(offsetX);
            itemView.setY(offsetY);

            decorView.addView(itemView, params);

            decorView.postDelayed(() -> {
                itemView.performClick();
            }, 1000);
        }
    }
}
