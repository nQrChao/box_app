package com.zqhy.app.core.view.main.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.box.common.glide.GlideApp;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.model.version.AppPopDataVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.sp.SPUtils;

/**
 * @author Administrator
 */
public class AppPopDialogHelper {

    private Context                  mContext;
    private OnMainPagerClickListener onMainPagerClickListener;

    private final String SP_APP_POP_SHOW_TYLY = "SP_APP_POP_SHOW_TYLY";
    private final String SP_APP_POP_SHOW_DAILY       = "SP_APP_POP_SHOW_DAILY";
    private final String SP_APP_POP_SHOW_ONCE        = "SP_APP_POP_SHOW_ONCE";
    private final String SP_APP_POP_SHOW_CB_TIPS     = "SP_APP_POP_SHOW_CB_TIPS";
    private final String SP_APP_POP_SHOW_USER_RECALL = "SP_APP_POP_SHOW_USER_RECALL";


    public AppPopDialogHelper(Context mContext, OnMainPagerClickListener onMainPagerClickListener) {
        this.mContext = mContext;
        this.onMainPagerClickListener = onMainPagerClickListener;
    }

    private ImageView image;
    private ImageView ivClose;
    private CheckBox  mCbShow;

    private CustomDialog appPopDialog;

    public void showAppPopDialog(AppPopDataVo.DataBean dataBean) {
        if (dataBean == null) {
            return;
        }
        //1 只弹一次  2 每天弹一次  3每次都弹
        int pop_model = dataBean.getFrequency();
        //是否强制(1:是; 2:否)
        int pop_force = dataBean.getTerminable();

        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
        int popType = spUtils.getInt(SP_APP_POP_SHOW_TYLY, -1);
        if (popType == -1 || popType != pop_model){
            spUtils.putInt(SP_APP_POP_SHOW_TYLY, pop_model);
            spUtils.remove(SP_APP_POP_SHOW_ONCE);
            spUtils.remove(SP_APP_POP_SHOW_DAILY);
            spUtils.remove(SP_APP_POP_SHOW_CB_TIPS);
        }

        if (pop_model == 1) {
            boolean showOnce = spUtils.getBoolean(SP_APP_POP_SHOW_ONCE);
            if (!showOnce) {
                doShowDialog(false, dataBean.getPic());
                spUtils.putBoolean(SP_APP_POP_SHOW_ONCE, true);
            }
        } else if (pop_model == 2) {
            String showDaily = spUtils.getString(SP_APP_POP_SHOW_DAILY, "");
            String todayDate = CommonUtils.formatTimeStamp(System.currentTimeMillis(), "yyyyMMdd");
            if (!showDaily.equals(todayDate)) {
                doShowDialog(false, dataBean.getPic());
                spUtils.putString(SP_APP_POP_SHOW_DAILY, todayDate);
            }
        } else if (pop_model == 3) {
            boolean showDialogTips = spUtils.getBoolean(SP_APP_POP_SHOW_CB_TIPS);
            if (!showDialogTips) {
                /*String showDaily = spUtils.getString(SP_APP_POP_SHOW_DAILY, "");
                String todayDate = CommonUtils.formatTimeStamp(System.currentTimeMillis(), "yyyyMMdd");
                if (!showDaily.equals(todayDate)) {
                    doShowDialog(pop_force == 1, true, dataBean.getPic());
                    spUtils.putString(SP_APP_POP_SHOW_DAILY, todayDate);
                }*/
                doShowDialog(pop_force == 1, dataBean.getPic());
            }
        } else {
            return;
        }
    }

    protected void doShowDialog(boolean isForce, String pic) {
        if (appPopDialog == null) {
            appPopDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_app_pop, null),
                    WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            image = appPopDialog.findViewById(R.id.image);
            ivClose = appPopDialog.findViewById(R.id.iv_close);
            mCbShow = appPopDialog.findViewById(R.id.cb_show);


            image.setOnClickListener(v -> {
                if (appPopDialog != null && appPopDialog.isShowing()) {
                    appPopDialog.dismiss();
                }
                if (onMainPagerClickListener != null) {
                    onMainPagerClickListener.onClick(v.getId());
                }
            });
            appPopDialog.setCancelable(false);
            appPopDialog.setCanceledOnTouchOutside(false);
            ivClose.setVisibility(View.VISIBLE);
            ivClose.setOnClickListener(v -> {
                if (appPopDialog != null && appPopDialog.isShowing()) {
                    appPopDialog.dismiss();
                }
            });

            mCbShow.setVisibility(isForce ? View.VISIBLE : View.GONE);

            mCbShow.setOnCheckedChangeListener((buttonView, isChecked) -> {
                SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
                if (isChecked) {
                    spUtils.putBoolean(SP_APP_POP_SHOW_CB_TIPS, true);
                } else {
                    spUtils.remove(SP_APP_POP_SHOW_CB_TIPS);
                }
            });
        }
        GlideApp.with(mContext)
                .asBitmap()
                .load(pic)
                .placeholder(R.mipmap.img_placeholder_h)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        if (bitmap != null) {
                            int imageWidth = ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 20) * 2;
                            int imageHeight = imageWidth * bitmap.getHeight() / bitmap.getWidth();

                            if (image.getLayoutParams() != null) {
                                ViewGroup.LayoutParams params = image.getLayoutParams();
                                params.width = imageWidth;
                                params.height = imageHeight;
                                image.setLayoutParams(params);
                            }
                            image.setImageBitmap(bitmap);
                            appPopDialog.show();
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable drawable) {

                    }
                });
    }


    public void showUserRecallDialog(UserInfoVo.DataBean user) {
        if (user == null || !user.isReCallUser()) {
            return;
        }
        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
        boolean hasShowUserRecall = spUtils.getBoolean(SP_APP_POP_SHOW_USER_RECALL);


        if (hasShowUserRecall) {
            return;
        }
        CustomDialog dialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_app_pop, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        ImageView image = dialog.findViewById(R.id.image);
        ImageView ivClose = dialog.findViewById(R.id.iv_close);
        CheckBox mCbShow = dialog.findViewById(R.id.cb_show);
        mCbShow.setVisibility(View.GONE);

        image.setImageResource(R.mipmap.img_show_user_recall);
        ivClose.setOnClickListener(v -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        });
        image.setOnClickListener(view -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            BrowserActivity.newInstance((Activity) mContext, Constants.URL_YONGHUZHAOHUIA);
        });
        dialog.show();
        spUtils.putBoolean(SP_APP_POP_SHOW_USER_RECALL, true);
    }
}
