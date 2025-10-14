package com.zqhy.app.core.dialog;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chaoji.im.glide.GlideApp;
import com.chaoji.other.hjq.toast.Toaster;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.base.Request;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.game.new0809.XinRenPopDataVo;
import com.zqhy.app.core.data.model.user.newvip.AllPopVo;
import com.zqhy.app.core.data.model.user.newvip.BirthdayRewardVo;
import com.zqhy.app.core.data.model.user.newvip.ComeBackVo;
import com.zqhy.app.core.data.model.user.newvip.RmbusergiveVo;
import com.zqhy.app.core.data.model.user.newvip.SuperBirthdayRewardVo;
import com.zqhy.app.core.data.model.version.AppPopDataVo;
import com.zqhy.app.core.data.model.version.UnionVipDataVo;
import com.zqhy.app.core.data.model.version.VersionVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.AppUtil;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.AppsUtils;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.core.tool.utilcode.SizeUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.main.adapter.RmbusergiveAdapter;
import com.zqhy.app.core.view.user.welfare.MyCouponsListFragment;
import com.zqhy.app.core.vm.main.MainViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.LifeUtil;
import com.zqhy.app.utils.sdcard.SdCardManager;
import com.zqhy.app.utils.sp.SPUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainDialogHelper {

    private Activity mContext;
    private AllPopVo.DataBean dataBean;
    private MainViewModel mViewModel;
    private boolean isReLogin;

    public MainDialogHelper(Activity mContext, AllPopVo.DataBean dataBean, MainViewModel mViewModel, boolean isReLogin) {
        this.mContext = mContext;
        this.dataBean = dataBean;
        this.mViewModel = mViewModel;
        this.isReLogin = isReLogin;
    }

    public void show(){
        showVersion(false, dataBean.getGet_version(), () -> {
            showUpdataSuccessDialog(dataBean.getGet_version(), () -> {
                if (BuildConfig.SHOW_ROOKIES_DIALOG){
                    showRookiesShowDialog(dataBean.getXinren_pop(), () -> {
                        getBirthdayReward(dataBean.getSuper_user_birthday_reward_status(), () -> {
                            showComeBackDialogSecond(dataBean.getCome_back(), () -> {
                                showRmbusergive(dataBean.getRmbusergive(), () -> {
                                    getUnionVipPop(dataBean.getKefu_cps_vip_pop(), () -> {
                                        if (isReLogin) return;
                                        List<AppPopDataVo.DataBean> home_page_pop = dataBean.getHome_page_pop();
                                        List<AppPopDataVo.DataBean> homePopList = new ArrayList<>();
                                        if (home_page_pop != null && home_page_pop.size() > 0){
                                            homePopList.addAll(home_page_pop);
                                            showAppPop(homePopList);
                                        }
                                    });
                                });
                            });
                        });
                    });
                }else {
                    getBirthdayReward(dataBean.getSuper_user_birthday_reward_status(), () -> {
                        showComeBackDialogSecond(dataBean.getCome_back(), () -> {
                            showRmbusergive(dataBean.getRmbusergive(), () -> {
                                getUnionVipPop(dataBean.getKefu_cps_vip_pop(), () -> {
                                    if (isReLogin) return;
                                    List<AppPopDataVo.DataBean> home_page_pop = dataBean.getHome_page_pop();
                                    List<AppPopDataVo.DataBean> homePopList = new ArrayList<>();
                                    if (home_page_pop != null && home_page_pop.size() > 0){
                                        homePopList.addAll(home_page_pop);
                                        showAppPop(homePopList);
                                    }
                                });
                            });
                        });
                    });
                }
            });
        });
    }

    private void showAppPop(List<AppPopDataVo.DataBean> homePopList){
        if (homePopList.size() > 0){
            AppPopDataVo.DataBean dataBean = homePopList.get(0);
            homePopList.remove(0);
            showAppPopDialog(dataBean, () -> {
                showAppPop(homePopList);
            });
        }
    }

    private SPUtils spUtils;
    private long versionLimitTime = 24 * 60 * 60 * 1000;
    private String SP_VERSION = "SP_VERSION";
    private String TIME_VERSION = "TIME_VERSION";
    private void showVersion(boolean isNeedUpdate, VersionVo.DataBean dataBean, OnDialogCancelListener onDialogCancelListener) {
        if (isReLogin) {
            onDialogCancelListener.onCancel();
            return;
        }
        if (dataBean == null) {
            onDialogCancelListener.onCancel();
            return;
        }
        spUtils = new SPUtils(mContext, SP_VERSION);
        int updateCode = dataBean.getVercode();
        int isForce = dataBean.getIsforce();
        if (updateCode > AppsUtils.getAppInfo(mContext).getVersionCode()) {
            try {
                if (isForce == 1) {
                    //强制更新
                    showVersionDialog(isNeedUpdate, dataBean, onDialogCancelListener);
                } else {
                    //非强制更新，有时间限制
                    long lastAppVersionTime = spUtils.getLong(TIME_VERSION);
                    if (isNeedUpdate || (System.currentTimeMillis() - lastAppVersionTime > versionLimitTime)) {
                        showVersionDialog(isNeedUpdate, dataBean, onDialogCancelListener);
                    }else {
                        onDialogCancelListener.onCancel();
                    }
                }
            } catch (Exception e) {
                onDialogCancelListener.onCancel();
                e.printStackTrace();
            }
        } else {
            onDialogCancelListener.onCancel();
            if (isNeedUpdate) {
                //ToastT.warning(mContext, "已是最新版本");
                Toaster.show("已是最新版本");
            }
        }
    }

    private boolean isCheck = false;
    private boolean isShowDownload = false;
    private void showVersionDialog(boolean isNeedUpdate, VersionVo.DataBean dataBean, OnDialogCancelListener onDialogCancelListener) {
        CustomDialog versionDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_update_new, null),
                ScreenUtils.getScreenWidth(mContext),
                WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        TextView mTvVersion = versionDialog.findViewById(R.id.tv_version);
        TextView mTvSize = versionDialog.findViewById(R.id.tv_size);
        TextView mTvContent = versionDialog.findViewById(R.id.tv_content);
        TextView mTvUpdate = versionDialog.findViewById(R.id.tv_update);
        TextView mTvCancel = versionDialog.findViewById(R.id.tv_cancel);
        TextView mTvBottom = versionDialog.findViewById(R.id.tv_bottom);

        mTvVersion.setText("发现新版本" + dataBean.getVersion());
        mTvSize.setText("大小" + dataBean.getPackage_size());
        mTvContent.setText(dataBean.getUpdateContent());

        mTvUpdate.setOnClickListener(v -> {
            isShowDownload = true;
            showDownloadDialog(dataBean, onDialogCancelListener);
            if (versionDialog != null && versionDialog.isShowing()) {
                versionDialog.dismiss();
            }
        });

        mTvCancel.setVisibility(dataBean.getIsforce() == 1 ? View.INVISIBLE : View.VISIBLE);
        mTvCancel.setOnClickListener(v -> {
            if (mTvCancel.getVisibility() != View.INVISIBLE){
                if (versionDialog != null && versionDialog.isShowing()) {
                    versionDialog.dismiss();
                }
            }
        });

        isCheck = false;
        mTvBottom.setOnClickListener(v -> {
            if (isCheck){
                isCheck = false;
                mTvBottom.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_update_dialog_check), null, null, null);
            }else {
                isCheck = true;
                mTvBottom.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_update_dialog_checked), null, null, null);
            }
        });

        versionDialog.setOnDismissListener(dialog -> {
            if(!isShowDownload){
                onDialogCancelListener.onCancel();
            }
        });

        versionDialog.setCanceledOnTouchOutside(isNeedUpdate);
        versionDialog.setCancelable(isNeedUpdate);
        versionDialog.show();
        spUtils.putLong(TIME_VERSION, System.currentTimeMillis());
    }

    private void showUpdataSuccessDialog(VersionVo.DataBean dataBean, OnDialogCancelListener onDialogCancelListener) {
        spUtils = new SPUtils(mContext, SP_VERSION);
        String successVersionName = spUtils.getString("successVersionName", "");
        if (!AppConfig.isOfficialChannel()){//只有官方包和投放包弹
            onDialogCancelListener.onCancel();
            return;
        }

        if (!AppsUtils.getAppInfo(mContext).getVersionName().equals(dataBean.getVersion())){
            onDialogCancelListener.onCancel();
            return;
        }

        if (successVersionName.equals(AppsUtils.getAppInfo(mContext).getVersionName())){
            onDialogCancelListener.onCancel();
            return;
        }

        CustomDialog successDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_update_success, null), ScreenUtils.getScreenWidth(mContext), WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        TextView mTvContent = successDialog.findViewById(R.id.tv_content);
        TextView mTvConfirm = successDialog.findViewById(R.id.tv_confirm);

        mTvContent.setText(dataBean.getUpdateContent());

        mTvConfirm.setOnClickListener(v -> {
            if (successDialog != null && successDialog.isShowing()) {
                successDialog.dismiss();
            }
        });

        successDialog.setOnDismissListener(dialog -> {
            if(!isShowDownload){
                onDialogCancelListener.onCancel();
            }
        });
        //successDialog.show();
        spUtils.putString("successVersionName", AppsUtils.getAppInfo(mContext).getVersionName());
    }

    CustomDialog downloadDialog;
    private TextView mTvVersion;
    private TextView mTvSize;
    private TextView mTvContent;
    private ProgressBar mProgressBar;
    private TextView mTvPlan;
    private TextView mTvSpeed;
    private TextView mTvCancel;
    private void showDownloadDialog(VersionVo.DataBean dataBean, OnDialogCancelListener onDialogCancelListener) {
        if (downloadDialog == null) {
            downloadDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_download_new, null),
                    ScreenUtils.getScreenWidth(mContext), WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            downloadDialog.setCanceledOnTouchOutside(false);
            if (dataBean.getIsforce() == 1) {
                downloadDialog.setCancelable(false);
            }
            mTvVersion = downloadDialog.findViewById(R.id.tv_version);
            mTvSize = downloadDialog.findViewById(R.id.tv_size);
            mTvContent = downloadDialog.findViewById(R.id.tv_content);
            mProgressBar = downloadDialog.findViewById(R.id.download_progress);
            mTvPlan = downloadDialog.findViewById(R.id.tv_plan);
            mTvSpeed = downloadDialog.findViewById(R.id.tv_speed);
            mTvCancel = downloadDialog.findViewById(R.id.tv_cancel);

            mTvVersion.setText("发现新版本" + dataBean.getVersion());
            mTvSize.setText("大小" + dataBean.getPackage_size());
            mTvContent.setText(dataBean.getUpdateContent());

            mProgressBar.setMax(100);

            mTvCancel.setOnClickListener(view -> {
                OkGo.getInstance().cancelAll();
                OkGo.delete(dataBean.getAppdir());
                downloadDialog.dismiss();
            });
        }
        downloadDialog.setOnDismissListener(dialog -> {
            onDialogCancelListener.onCancel();
        });
        downloadDialog.show();
        downloadApk(dataBean);
    }

    private void downloadApk(VersionVo.DataBean versionBean) {
        String appName = mContext.getResources().getString(R.string.app_name);
        String fileName = appName + "_v" + versionBean.getVercode() + "_" + versionBean.getIsforce();

        OkGo.<File>get(versionBean.getAppdir())
                .tag(this)
                .execute(new FileCallback(SdCardManager.getInstance().getDownloadApkDir().getPath(), fileName) {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                        if (downloadDialog != null && downloadDialog.isShowing()) {
                            downloadDialog.dismiss();
                        }
                        File file = response.body();
                        if (file.exists()) {
                            AppUtil.install(mContext, file);
                        }
                    }

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<File> response) {
                        super.onError(response);
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        mProgressBar.setProgress((int) (progress.fraction * 100));
                        mTvPlan.setText((int) (progress.fraction * 100) + "%");
                        mTvSpeed.setText(Formatter.formatFileSize(mContext, progress.speed) + "/s");
                    }
                });
    }

    private void showRookiesShowDialog(XinRenPopDataVo.DataBean bean, OnDialogCancelListener onDialogCancelListener) {
        if (isReLogin) {
            onDialogCancelListener.onCancel();
            return;
        }
        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
        boolean hasShow = spUtils.getBoolean("IS_SHOW_ROOKIES_DIALOG", false);
        if (dataBean.getXinren_pop() == null) {
            onDialogCancelListener.onCancel();
            return;
        }
        if (dataBean.getXinren_pop().rookies_show != 1){
            onDialogCancelListener.onCancel();
            return;
        }
        if (hasShow){
            onDialogCancelListener.onCancel();
            return;
        }
        CustomDialog dialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_rookies, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        AppCompatImageView mIvTarget = dialog.findViewById(R.id.iv_target);
        ImageView mIvClosed = dialog.findViewById(R.id.iv_closed);

        dialog.setCanceledOnTouchOutside(false);
        mIvTarget.setOnClickListener(view -> {
            BrowserActivity.newInstance(mContext, bean.url);
        });
        mIvClosed.setOnClickListener(v -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(dialog1 -> {
            onDialogCancelListener.onCancel();
        });
        if (LifeUtil.isAlive(mContext)) {
            dialog.show();
            spUtils.putBoolean("IS_SHOW_ROOKIES_DIALOG", true);
        }
    }

    private void getBirthdayReward(SuperBirthdayRewardVo.DataBean payInfoVo, OnDialogCancelListener onDialogCancelListener) {
        if (payInfoVo == null) {
            onDialogCancelListener.onCancel();
            return;
        }
        if (!payInfoVo.getStatus().equals("now")) {
            onDialogCancelListener.onCancel();
            return;
        }
        mViewModel.getBirthdayReward(new OnBaseCallback<BirthdayRewardVo>() {
            @Override
            public void onSuccess(BirthdayRewardVo payInfoVo) {
                if (payInfoVo != null && payInfoVo.isStateOK()) {
                    CustomDialog tipsDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_vip_birthday, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
                    ((TextView)tipsDialog.findViewById(R.id.tv_price)).setText(String.valueOf(payInfoVo.getData().getAmount()));
                    ((TextView)tipsDialog.findViewById(R.id.tv_cdt)).setText("满" + payInfoVo.getData().getCdt() + "元可用");
                    tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
                        if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
                    });
                    tipsDialog.setOnDismissListener(dialog -> {
                        onDialogCancelListener.onCancel();
                    });
                    tipsDialog.show();
                }else{
                    onDialogCancelListener.onCancel();
                }
            }
        });
    }

    private void showComeBackDialogSecond(ComeBackVo.DataBean dataBean, OnDialogCancelListener onDialogCancelListener){
        if(dataBean == null){
            onDialogCancelListener.onCancel();
            return;
        }
        if (!"yes".equals(dataBean.getIs_come_back())){
            onDialogCancelListener.onCancel();
            return;
        }
        if (0 > dataBean.getDay()){
            onDialogCancelListener.onCancel();
            return;
        }
        SPUtils spUtils = new SPUtils(mContext, Constants.SP_COMMON_NAME);
        if (CommonUtils.isTodayOrTomorrow(spUtils.getLong("showComeBackDialogTime", 0)) >= 0) {
            onDialogCancelListener.onCancel();
            return;
        }
        spUtils.putLong("showComeBackDialogTime", System.currentTimeMillis());
        if (TextUtils.isEmpty(spUtils.getString("showComeBack", "")) || !spUtils.getString("showComeBack", "").equals(dataBean.getId())) {
            spUtils.putString("showComeBack", dataBean.getId());
            CustomDialog tipsDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_come_back, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            tipsDialog.setCancelable(false);
            tipsDialog.setCanceledOnTouchOutside(false);
            TextView mTvTips = (TextView) tipsDialog.findViewById(R.id.tv_tips);
            SpannableString spannableString = new SpannableString("将于" + CommonUtils.formatTimeStamp(dataBean.getEnd_time() * 1000, "yyyy-MM-dd ") + "过期");
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FEA047")), 2, 12, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mTvTips.setText(spannableString);
            tipsDialog.findViewById(R.id.iv_close).setOnClickListener(view -> {
                if (tipsDialog != null && tipsDialog.isShowing()){
                    tipsDialog.dismiss();
                }
            });
            tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
                if (tipsDialog != null && tipsDialog.isShowing()){
                    tipsDialog.dismiss();
                }
                BrowserActivity.newInstance(mContext, dataBean.getHd_url(), true);
            });
            tipsDialog.setOnDismissListener(dialog -> {
                onDialogCancelListener.onCancel();
            });
            tipsDialog.show();
        }else {
            CustomDialog tipsDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_come_back_second, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            tipsDialog.setCancelable(false);
            tipsDialog.setCanceledOnTouchOutside(false);
            TextView mTvTips = (TextView) tipsDialog.findViewById(R.id.tv_tips);
            SpannableString spannableString = new SpannableString("将于" + CommonUtils.formatTimeStamp(dataBean.getEnd_time() * 1000, "yyyy-MM-dd ") + "过期");
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FEA047")), 2, 12, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mTvTips.setText(spannableString);
            ((TextView) tipsDialog.findViewById(R.id.tv_day)).setText(String.valueOf(dataBean.getDay()));
            tipsDialog.findViewById(R.id.iv_close).setOnClickListener(view -> {
                if (tipsDialog != null && tipsDialog.isShowing()){
                    tipsDialog.dismiss();
                }
            });
            tipsDialog.findViewById(R.id.iv_confirm).setOnClickListener(view -> {
                if (tipsDialog != null && tipsDialog.isShowing()){
                    tipsDialog.dismiss();
                }
                BrowserActivity.newInstance(mContext, dataBean.getHd_url(), true);
            });
            tipsDialog.setOnDismissListener(dialog -> {
                onDialogCancelListener.onCancel();
            });
            tipsDialog.show();
        }
    }

    private boolean showRmbusergive = false;
    private void showRmbusergive(RmbusergiveVo.DataBean dataBean, OnDialogCancelListener onDialogCancelListener){
        if (dataBean == null){
            onDialogCancelListener.onCancel();
            return;
        }
        if (dataBean.getCoupon_total() <= 0){
            onDialogCancelListener.onCancel();
            return;
        }
        if (!"yes".equals(dataBean.getHas_give())){
            onDialogCancelListener.onCancel();
            return;
        }
        CustomDialog tipsDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_rmbusergive_ungain, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        tipsDialog.setCancelable(false);
        tipsDialog.setCanceledOnTouchOutside(false);
        ((TextView) tipsDialog.findViewById(R.id.tv_coupon_total)).setText(String.valueOf(dataBean.getCoupon_total()));
        RecyclerView recyclerView = (RecyclerView) tipsDialog.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RmbusergiveAdapter(mContext, dataBean.getCoupon_list()));
        tipsDialog.findViewById(R.id.ll_confirm).setOnClickListener(view -> {
            showRmbusergive = true;
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            mViewModel.rmbusergiveGetReward(new OnBaseCallback<RmbusergiveVo>() {
                @Override
                public void onSuccess(RmbusergiveVo rmbusergiveVo) {
                    if (rmbusergiveVo != null && rmbusergiveVo.isStateOK()){
                        CustomDialog tipsDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_rmbusergive_gained, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
                        tipsDialog.setCancelable(false);
                        tipsDialog.setCanceledOnTouchOutside(false);
                        RecyclerView recyclerView = (RecyclerView) tipsDialog.findViewById(R.id.recycler_view);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(new RmbusergiveAdapter(mContext, dataBean.getCoupon_list()));
                        tipsDialog.findViewById(R.id.ll_confirm).setOnClickListener(view -> {
                            if (tipsDialog != null && tipsDialog.isShowing())
                                tipsDialog.dismiss();
                            //代金券
                            if (UserInfoModel.getInstance().isLogined())
                                FragmentHolderActivity.startFragmentInActivity(mContext, new MyCouponsListFragment());
                        });
                        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
                            if (tipsDialog != null && tipsDialog.isShowing())
                                tipsDialog.dismiss();
                        });
                        tipsDialog.setOnDismissListener(dialog -> {
                            onDialogCancelListener.onCancel();
                        });
                        tipsDialog.show();
                    }else {
                        showRmbusergive = false;
                        onDialogCancelListener.onCancel();
                    }
                }
            });
        });
        tipsDialog.setOnDismissListener(dialog -> {
            if (!showRmbusergive) onDialogCancelListener.onCancel();
        });
        tipsDialog.show();
    }

    private void getUnionVipPop(UnionVipDataVo.DataBean data, OnDialogCancelListener onDialogCancelListener) {
        if (data == null){
            onDialogCancelListener.onCancel();
            return;
        }
        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
        spUtils.putLong("showUnionVipDialogTime", System.currentTimeMillis());
        long unionVip = spUtils.getLong("unionVip");
        if (data.getUtime() < unionVip){
            onDialogCancelListener.onCancel();
            return;
        }
        CustomDialog unionVipDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_union_vip, null),
                ScreenUtils.getScreenWidth(mContext) - SizeUtils.dp2px(mContext, 40),
                WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        unionVipDialog.setCanceledOnTouchOutside(false);
        String text = data.getText();
        String type = "";
        if (!TextUtils.isEmpty(text)){
            String[] split = text.split(" ");
            if (split.length == 2){
                type = split[0];
                text = split[1];
            }
        }
        ((TextView)unionVipDialog.findViewById(R.id.tv_type)).setText(type);
        ((TextView)unionVipDialog.findViewById(R.id.tv_contact)).setText(text);
        if (data != null &&!TextUtils.isEmpty(data.getMsg())) ((TextView)unionVipDialog.findViewById(R.id.tv_tips)).setText(data.getMsg());
        String finalText = text;
        unionVipDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (CommonUtils.copyString(mContext, finalText)) {
                //ToastT.success(mContext, "联系方式已复制");
                Toaster.show("联系方式已复制");
            }
        });
        unionVipDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (unionVipDialog != null && unionVipDialog.isShowing()){
                unionVipDialog.dismiss();
            }
        });
        unionVipDialog.setOnDismissListener(dialog -> {
            onDialogCancelListener.onCancel();
        });
        unionVipDialog.show();
        spUtils.putLong("unionVip", data.getNtime());
    }

    private final String SP_APP_POP_SHOW_TYLY = "SP_APP_POP_SHOW_TYLY";
    private final String SP_APP_POP_SHOW_DAILY       = "SP_APP_POP_SHOW_DAILY";
    private final String SP_APP_POP_SHOW_ONCE        = "SP_APP_POP_SHOW_ONCE";
    private final String SP_APP_POP_SHOW_CB_TIPS     = "SP_APP_POP_SHOW_CB_TIPS";
    private void showAppPopDialog(AppPopDataVo.DataBean dataBean, OnDialogCancelListener onDialogCancelListener) {
        if (dataBean == null) {
            onDialogCancelListener.onCancel();
            return;
        }
        //1 只弹一次  2 每天弹一次  3每次都弹
        int pop_model = dataBean.getFrequency();
        //是否强制(1:是; 2:否)
        int pop_force = dataBean.getTerminable();

        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME + dataBean.getPop_id());
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
                doShowDialog(dataBean, false, dataBean.getPic(), onDialogCancelListener);
                spUtils.putBoolean(SP_APP_POP_SHOW_ONCE, true);
            }else {
                onDialogCancelListener.onCancel();
            }
        } else if (pop_model == 2) {
            String showDaily = spUtils.getString(SP_APP_POP_SHOW_DAILY, "");
            String todayDate = CommonUtils.formatTimeStamp(System.currentTimeMillis(), "yyyyMMdd");
            if (!showDaily.equals(todayDate)) {
                doShowDialog(dataBean, false, dataBean.getPic(), onDialogCancelListener);
                spUtils.putString(SP_APP_POP_SHOW_DAILY, todayDate);
            }else {
                onDialogCancelListener.onCancel();
            }
        } else if (pop_model == 3) {
            boolean showDialogTips = spUtils.getBoolean(SP_APP_POP_SHOW_CB_TIPS);
            if (!showDialogTips) {
                doShowDialog(dataBean, pop_force == 1, dataBean.getPic(), onDialogCancelListener);
            }else {
                onDialogCancelListener.onCancel();
            }
        } else {
            onDialogCancelListener.onCancel();
            return;
        }
    }

    private void doShowDialog(AppPopDataVo.DataBean dataBean, boolean isForce, String pic, OnDialogCancelListener onDialogCancelListener) {
        CustomDialog appPopDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_app_pop, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        ImageView image = appPopDialog.findViewById(R.id.image);
        ImageView ivClose = appPopDialog.findViewById(R.id.iv_close);
        CheckBox mCbShow = appPopDialog.findViewById(R.id.cb_show);

        image.setOnClickListener(v -> {
            if (appPopDialog != null && appPopDialog.isShowing()) {
                appPopDialog.dismiss();
            }
            new AppJumpAction(mContext).jumpAction(dataBean);
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
            SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME + dataBean.getPop_id());
            if (isChecked) {
                spUtils.putBoolean(SP_APP_POP_SHOW_CB_TIPS, true);
            } else {
                spUtils.remove(SP_APP_POP_SHOW_CB_TIPS);
            }
        });
        appPopDialog.setOnDismissListener(dialog -> {
            onDialogCancelListener.onCancel();
        });
        GlideApp.with(mContext).asBitmap()
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
}
