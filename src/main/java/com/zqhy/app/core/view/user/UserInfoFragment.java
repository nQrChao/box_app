package com.zqhy.app.core.view.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.box.common.glide.GlideApp;
import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.model.version.VersionVo;
import com.zqhy.app.core.dialog.VersionDialogHelper;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.AppUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.login.LoginActivity;
import com.zqhy.app.core.view.redpacket.RedPacketFragment;
import com.zqhy.app.core.view.test.TestFragment;
import com.zqhy.app.core.vm.user.UserViewModel;
import com.zqhy.app.glide.GlideModuleConfig;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.listener.NetworkPollingListener;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.PermissionHelper;
import com.zqhy.app.utils.cache.FolderUtils;
import com.zqhy.app.utils.photo.CropFileUtils;
import com.zqhy.app.utils.sdcard.SdCardManager;
import com.zqhy.app.utils.sp.SPUtils;
import com.zqhy.app.widget.SwitchView;
import com.zqhy.app.widget.imageview.ClipRoundImageView;

import java.io.File;
import java.util.List;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;

/**
 * @author Administrator
 */

public class UserInfoFragment extends BaseFragment<UserViewModel> implements View.OnClickListener {

    public static final int ACTION_USER_LOGOUT = 0x8888;


    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user_info;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    protected String getUmengPageName() {
        return "个人资料页";
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("账户安全");
        bindView();
    }

    private LinearLayout mLlUserAccount;
    private TextView     mTvUserAccount;
    private LinearLayout mLlBindPhone;
    private TextView     mTvBindPhone;
    private TextView     mTvUnSave;
    private LinearLayout mLlRealNameSystem;
    private TextView     mTvRealNameSystem;
    private TextView     mTvUnReal;
    private ImageView    mIvArrowRealNameSystem;
    private LinearLayout mLlModify;
    private LinearLayout mLlSecurity;
    private Button       mBtnLogout;

    private LinearLayout       mLlUserNickname;
    private TextView           mTvUserNickname;
    private LinearLayout       mLlUserPortrait;
    private ClipRoundImageView mIvUserPortrait;

    private LinearLayout mLlUserLevel;
    private TextView     mTvUserLevel;

    private Button mBtnTest;
    private Button mBtnRedPacket;

    private LinearLayout mLlNetworkLine;
    private TextView mTvNetworkLine;
    private LinearLayout mLlClearCache;
    private TextView mTvClearCache;
    private LinearLayout mLlCheckUpdate;
    private TextView mTvCheckUpdate;

    private LinearLayout mLlLogin;
    private TextView mTvUnLogin;
    private LinearLayout mLlPermission;
    private SwitchView mSwitchViewDownload;
    private LinearLayout mLlRecord;
    private TextView mTvRecord;

    private void bindView() {
        mLlUserAccount = findViewById(R.id.ll_user_account);
        mTvUserAccount = findViewById(R.id.tv_user_account);
        mLlBindPhone = findViewById(R.id.ll_bind_phone);
        mTvBindPhone = findViewById(R.id.tv_bind_phone);
        mTvUnSave = findViewById(R.id.tv_un_save);
        mLlRealNameSystem = findViewById(R.id.ll_real_name_system);
        mTvRealNameSystem = findViewById(R.id.tv_real_name_system);
        mTvUnReal = findViewById(R.id.tv_un_real);
        mIvArrowRealNameSystem = findViewById(R.id.iv_arrow_real_name_system);
        mLlSecurity = findViewById(R.id.ll_security);
        mLlModify = findViewById(R.id.ll_modify);
        mBtnLogout = findViewById(R.id.btn_logout);
        mBtnTest = findViewById(R.id.btn_model_test);
        mBtnRedPacket = findViewById(R.id.btn_red_packet);

        mLlLogin = findViewById(R.id.ll_login);
        mTvUnLogin = findViewById(R.id.tv_un_login);
        mLlPermission = findViewById(R.id.ll_permission_line);


        mLlUserNickname = findViewById(R.id.ll_user_nickname);
        mTvUserNickname = findViewById(R.id.tv_user_nickname);
        mLlUserPortrait = findViewById(R.id.ll_user_portrait);
        mIvUserPortrait = findViewById(R.id.iv_user_portrait);

        mLlUserLevel = findViewById(R.id.ll_user_level);
        mTvUserLevel = findViewById(R.id.tv_user_level);

        mLlNetworkLine = findViewById(R.id.ll_network_line);
        mTvNetworkLine = findViewById(R.id.tv_network_line);
        mLlClearCache = findViewById(R.id.ll_clear_cache);
        mTvClearCache = findViewById(R.id.tv_clear_cache);
        mLlCheckUpdate = findViewById(R.id.ll_check_update);
        mTvCheckUpdate = findViewById(R.id.tv_check_update);

        mSwitchViewDownload = findViewById(R.id.switch_view_download);

        mLlRecord = findViewById(R.id.ll_record);
        mTvRecord = findViewById(R.id.tv_record);

        mLlBindPhone.setOnClickListener(this);
        mLlSecurity.setOnClickListener(this);
        mLlModify.setOnClickListener(this);

        mLlUserNickname.setOnClickListener(this);
        mLlUserPortrait.setOnClickListener(this);

        mLlUserLevel.setOnClickListener(this);

        mLlNetworkLine.setOnClickListener(this);
        mLlClearCache.setOnClickListener(this);
        mLlCheckUpdate.setOnClickListener(this);
        mTvUnLogin.setOnClickListener(this);
        mLlPermission.setOnClickListener(this);
        mLlRecord.setOnClickListener(this);

        if (TextUtils.isEmpty(AppConfig.APP_FILING)){
            mLlRecord.setVisibility(View.GONE);
            findViewById(R.id.view_record).setVisibility(View.GONE);
        }else {
            mLlRecord.setVisibility(View.VISIBLE);
            findViewById(R.id.view_record).setVisibility(View.VISIBLE);
            mTvRecord.setText(AppConfig.APP_FILING);
        }

        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
        boolean download_switch = spUtils.getBoolean("download_switch", true);
        mSwitchViewDownload.toggleSwitch(download_switch);
        mSwitchViewDownload.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                spUtils.putBoolean("download_switch", true);
                view.toggleSwitch(true); // or false
            }

            @Override
            public void toggleToOff(SwitchView view) {
                spUtils.putBoolean("download_switch", false);
                view.toggleSwitch(false); // or false
            }
        });

        mBtnTest.setVisibility(BuildConfig.DEBUG ? View.VISIBLE : View.GONE);
        mBtnTest.setOnClickListener(v -> {
            start(new TestFragment());
        });
        mBtnRedPacket.setVisibility(BuildConfig.DEBUG ? View.VISIBLE : View.GONE);
        mBtnRedPacket.setOnClickListener(v -> {
            start(new RedPacketFragment());
        });

        if (BuildConfig.IS_SHOW_ABOUT_US){
            findViewById(R.id.ll_about_us).setVisibility(View.VISIBLE);
        }else {
            findViewById(R.id.ll_about_us).setVisibility(View.GONE);
        }
        findViewById(R.id.ll_about_us).setOnClickListener(view -> {
            BrowserActivity.newInstance(_mActivity, "https://mobile.tsyule.cn/index.php/Index/view/?id=103330");
        });

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(density * 20);
        gd.setColor(Color.parseColor("#FFFFFF"));
        mBtnLogout.setBackground(gd);

        mBtnLogout.setOnClickListener(this);

        findViewById(R.id.tv_user_agreement).setOnClickListener(v -> {
            //用户协议
            goUserAgreement();
        });
        findViewById(R.id.tv_privacy_agreement).setOnClickListener(v -> {
            //隐私协议
            goPrivacyAgreement();
        });
        findViewById(R.id.tv_anti_addiction).setOnClickListener(v -> {
            //防沉迷
            BrowserActivity.newInstance(_mActivity, "https://mobile.xiaodianyouxi.com/index.php/Index/view/?id=100000009", false);
        });

        setData();
    }

    private void setData() {
        if (UserInfoModel.getInstance().isLogined()){
            mLlLogin.setVisibility(View.VISIBLE);
            mTvUnLogin.setVisibility(View.GONE);
            mBtnLogout.setVisibility(View.VISIBLE);
            //设置用户信息
            setUserInfo();
            //手机绑定
            setBindPhone();
            //实名制
            setRealNameSystem();
        }else {
            mLlLogin.setVisibility(View.GONE);
            mTvUnLogin.setVisibility(View.VISIBLE);
            mBtnLogout.setVisibility(View.GONE);
        }

        mTvNetworkLine.setText("优选");
        mTvClearCache.setText(getCacheSize());
        mTvCheckUpdate.setText(AppUtil.getVersionName(_mActivity));
    }

    private void setUserInfo() {
        UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
        if (userInfo != null) {
            mTvUserAccount.setText(userInfo.getUsername());
            mTvUserNickname.setText(userInfo.getUser_nickname());
            mTvUserLevel.setText("Lv." + userInfo.getUser_level());
            GlideApp.with(_mActivity)
                    .load(userInfo.getUser_icon())
                    .placeholder(R.mipmap.ic_user_login_new_sign)
                    .error(R.mipmap.ic_user_login_new_sign)
                    .into(mIvUserPortrait);
            //GlideUtils.loadCircleImage(_mActivity, userInfo.getUser_icon(), mIvUserPortrait, R.mipmap.ic_user_login_new_sign);

            targetFileAvatar = createFileAvatar();
            setFileAvatar(targetFileAvatar);
        }
    }

    private File createFileAvatar() {
        File target = null;
        UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
        if (Build.VERSION.SDK_INT >= 30) {
            target = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + userInfo.getUsername() + "_image_headPortrait.jpg");
        } else {
            target = new File(SdCardManager.getInstance().getImageDir(), userInfo.getUsername() + "_image_headPortrait.jpg");
        }
        return target;
    }

    private boolean isSetBindViewPhone = false;

    /**
     * 绑定/解绑手机号
     */
    private void setBindPhone() {
        UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
        if (userInfo != null) {
            String mob = userInfo.getMobile();
            if (!TextUtils.isEmpty(mob)) {
                if (mob.length() > 8){
                    mTvBindPhone.setText(mob.replace(mob.substring(3, 8), "*****"));
                }
                mTvUnSave.setVisibility(View.GONE);
                isSetBindViewPhone = true;
            } else {
                //mTvBindPhone.setText("未设置");
                mTvUnSave.setVisibility(View.VISIBLE);
                isSetBindViewPhone = false;
            }
        }
    }

    private void setRealNameSystem() {
        UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
        if (userInfo != null) {
            String realName = userInfo.getReal_name();
            String cardId = userInfo.getIdcard();

            if (TextUtils.isEmpty(realName) || TextUtils.isEmpty(cardId)) {
                //mTvRealNameSystem.setText("未设置");
                mIvArrowRealNameSystem.setVisibility(View.VISIBLE);
                mTvUnReal.setVisibility(View.VISIBLE);
                String finalRealName = realName;
                String finalCardId = cardId;
                mLlRealNameSystem.setOnClickListener(view -> {
                    //设置实名制
                    startForResult(CertificationFragment.newInstance(finalRealName, finalCardId), REQUEST_CODE_CERTIFICATION);
                });
            } else {
                realName = CommonUtils.getHideRealName(realName);
                cardId = CommonUtils.getHideIdCard(cardId);

                mTvRealNameSystem.setText(realName + "," + cardId);
                mIvArrowRealNameSystem.setVisibility(View.GONE);
                mTvUnReal.setVisibility(View.GONE);
                mLlRealNameSystem.setOnClickListener(null);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_bind_phone:
                if (checkLogin()){
                    canUnbindMobile();//检查是否有绑定密码
                }
                break;
            case R.id.ll_modify:
                //修改登录密码
                if (isSetBindViewPhone) {
                    start(ModifyPasswordFragment.newInstance());
                } else {
                    Toaster.show( "您还未绑定手机号");
                }
                break;
            case R.id.ll_security:
                //隐私权限安全
                startFragment(SecurityFragment.newInstance());
                break;
            case R.id.btn_logout:
                //退出登录
                userLogout();
                break;
            case R.id.ll_user_nickname:
                //修改昵称
                setUserNickName();
                break;
            case R.id.ll_user_portrait:
                //修改头像
                userPortraitPick();
                break;
            case R.id.ll_user_level:
                //用户等级
                showUserLevelRule();
                break;

            case R.id.ll_network_line:
                //切换线路
                netWorkPolling();
                break;
            case R.id.ll_clear_cache:
                //清理缓存
                clearCache();
                break;
            case R.id.ll_check_update:
                //检查更新
                getAppVersion();
                break;
            case R.id.tv_un_login:
                //登录
                startActivity(new Intent(_mActivity, LoginActivity.class));
                break;
            case R.id.ll_permission_line:
                //权限设置
                try {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", _mActivity.getPackageName(), null);
                    intent.setData(uri);
                    _mActivity.startActivity(intent);
                } catch (Exception ex) { }
                break;
            case R.id.ll_record:
                BrowserActivity.newInstance(_mActivity, "https://beian.miit.gov.cn/");
                break;
            default:
                break;
        }
    }

    /**
     * 退出登录
     */
    private void userLogout() {
        AlertDialog dialog = new AlertDialog.Builder(_mActivity)
                .setTitle("提示")
                .setMessage("是否退出登录")
                .setPositiveButton("是", (DialogInterface dialogInterface, int i) -> {
                    dialogInterface.dismiss();
                    //NIMClient.getService(AuthService.class).logout();
                    UserInfoModel.getInstance().logout();
                    setFragmentResult(ACTION_USER_LOGOUT, null);
                    pop();
                })
                .setNegativeButton("否", (DialogInterface dialogInterface, int i) -> {
                    dialogInterface.dismiss();
                })
                .create();
        dialog.show();
    }

    private static final int REQUEST_CODE_BIND_PHONE = 2001;

    private static final int REQUEST_CODE_CERTIFICATION = 2002;

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BIND_PHONE ||
                requestCode == REQUEST_CODE_CERTIFICATION) {
            setFragmentResult(RESULT_OK, null);
            setData();
        }
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        setData();
    }
    private CustomDialog nickNameDialog;
    private AppCompatEditText mEtNickName;
    private void setUserNickName(){
        nickNameDialog = new CustomDialog(activity,
                LayoutInflater.from(activity).inflate(R.layout.dialog_change_nickname, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER, com.zqhy.app.core.R.style.sheetdialog);
        nickNameDialog.setCanceledOnTouchOutside(true);
        nickNameDialog.setCancelable(true);
        mEtNickName = nickNameDialog.findViewById(R.id.et_nick_name);
        TextView tvCancel = nickNameDialog.findViewById(R.id.tv_cancel);
        TextView tvConfirm = nickNameDialog.findViewById(R.id.tv_confirm);
        tvCancel.setOnClickListener(view -> nickNameDialog.dismiss());
        tvConfirm.setOnClickListener(view -> {
            String strNickName = mEtNickName.getText().toString().trim();
            if (TextUtils.isEmpty(strNickName)) {
                Toaster.show(mEtNickName.getHint());
                return;
            }
            if (nickNameDialog != null) nickNameDialog.dismiss();
            modifyNickName(strNickName);
        });
        if (nickNameDialog != null && !nickNameDialog.isShowing()) {
            nickNameDialog.show();
        }
    }


    private void modifyNickName(String nickname) {
        if (mViewModel != null) {
            mViewModel.modifyNickName(nickname, new OnBaseCallback() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    loading();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (nickNameDialog != null && nickNameDialog.isShowing()) {
                                nickNameDialog.dismiss();
                            }
                            mEtNickName.getText().clear();
                            //mTvUserNickname.setText(nickname);
                            Toaster.show( "新昵称已提交审核");
                            mViewModel.refreshUserData();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }


    private File targetFileAvatar = null;

    CustomDialog userPortraitPickDialog;
    private TextView mTvAlbum;
    private TextView mTvCamera;
    private TextView mTvCancel;

    @SuppressLint("WrongConstant")
    private void userPortraitPick() {
        if (userPortraitPickDialog == null) {
            userPortraitPickDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_user_portrait_pick, null),
                    WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
            mTvAlbum = userPortraitPickDialog.findViewById(R.id.tv_album);
            mTvCamera = userPortraitPickDialog.findViewById(R.id.tv_camera);
            mTvCancel = userPortraitPickDialog.findViewById(R.id.tv_cancel);

            mTvCancel.setOnClickListener(v -> {
                if (userPortraitPickDialog != null && userPortraitPickDialog.isShowing()) {
                    userPortraitPickDialog.dismiss();
                }
            });
            mTvAlbum.setOnClickListener(v -> {
                //图库
                if (userPortraitPickDialog != null && userPortraitPickDialog.isShowing()) {
                    userPortraitPickDialog.dismiss();
                }
                /*String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                PermissionHelper.checkPermissions(new PermissionHelper.OnPermissionListener() {
                    @Override
                    public void onGranted() {
                        boolean hasPermissions = PermissionHelper.hasPermissions(permissions);
                        if (hasPermissions) {
                            album();
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        Toaster.show( "请授权后再尝试操作哦~");
                    }
                }, permissions);*/
                int hasWritePermission = ContextCompat.checkSelfPermission(_mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                int hasReadPermission = ContextCompat.checkSelfPermission(_mActivity, Manifest.permission.READ_EXTERNAL_STORAGE);
                if (hasWritePermission == PackageManager.PERMISSION_GRANTED && hasReadPermission == PackageManager.PERMISSION_GRANTED){
                    album();
                }else {
                    showReadPermissionsTipDialog();
                }
            });
            mTvCamera.setOnClickListener(v -> {
                //相机
                if (userPortraitPickDialog != null && userPortraitPickDialog.isShowing()) {
                    userPortraitPickDialog.dismiss();
                }
                /*String[] permissions = new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA};

                PermissionHelper.checkPermissions(new PermissionHelper.OnPermissionListener() {
                    @Override
                    public void onGranted() {
                        boolean hasPermissions = PermissionHelper.hasPermissions(permissions);
                        if (hasPermissions){
                            camera();
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        Toaster.show( "请授权后再尝试操作哦~");
                    }
                }, permissions);*/
                int hasWritePermission = ContextCompat.checkSelfPermission(_mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                int hasCameraPermission = ContextCompat.checkSelfPermission(_mActivity, Manifest.permission.CAMERA);
                if (hasWritePermission == PackageManager.PERMISSION_GRANTED && hasCameraPermission == PackageManager.PERMISSION_GRANTED){
                    camera();
                }else {
                    showCameraPermissionsTipDialog();
                }
            });


        }
        userPortraitPickDialog.show();
    }

    @SuppressLint("WrongConstant")
    public void showReadPermissionsTipDialog(){
        if (_mActivity != null) {
            CustomDialog authorityDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.dialog_read_authority_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            authorityDialog.setCancelable(false);
            authorityDialog.setCanceledOnTouchOutside(false);

            TextView mTvCancel = authorityDialog.findViewById(R.id.tv_cancel);
            TextView mTvConfirm = authorityDialog.findViewById(R.id.tv_confirm);

            mTvCancel.setOnClickListener(v -> {
                if (authorityDialog != null && authorityDialog.isShowing()){
                    authorityDialog.dismiss();
                }
            });
            mTvConfirm.setOnClickListener(v -> {
                if (authorityDialog != null && authorityDialog.isShowing()){
                    authorityDialog.dismiss();
                }
                String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                PermissionHelper.checkPermissions(new PermissionHelper.OnPermissionListener() {
                    @Override
                    public void onGranted() {
                        boolean hasPermissions = PermissionHelper.hasPermissions(permissions);
                        if (hasPermissions) {
                            album();
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        Toaster.show( "请授权后再尝试操作哦~");
                        new Handler().postDelayed(() -> {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri aPackage = Uri.fromParts("package", _mActivity.getPackageName(), null);
                            intent.setData(aPackage);
                            startActivity(intent);
                        }, 800);
                    }
                }, permissions);
            });
            authorityDialog.show();
        }
    }

    @SuppressLint("WrongConstant")
    public void showCameraPermissionsTipDialog(){
        if (_mActivity != null) {
            CustomDialog authorityDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.dialog_camera_authority_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            authorityDialog.setCancelable(false);
            authorityDialog.setCanceledOnTouchOutside(false);

            TextView mTvCancel = authorityDialog.findViewById(R.id.tv_cancel);
            TextView mTvConfirm = authorityDialog.findViewById(R.id.tv_confirm);

            mTvCancel.setOnClickListener(v -> {
                if (authorityDialog != null && authorityDialog.isShowing()){
                    authorityDialog.dismiss();
                }
            });
            mTvConfirm.setOnClickListener(v -> {
                if (authorityDialog != null && authorityDialog.isShowing()){
                    authorityDialog.dismiss();
                }
                String[] permissions = new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA};

                PermissionHelper.checkPermissions(new PermissionHelper.OnPermissionListener() {
                    @Override
                    public void onGranted() {
                        boolean hasPermissions = PermissionHelper.hasPermissions(permissions);
                        if (hasPermissions){
                            camera();
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        Toaster.show( "请授权后再尝试操作哦~");
                        new Handler().postDelayed(() -> {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri aPackage = Uri.fromParts("package", _mActivity.getPackageName(), null);
                            intent.setData(aPackage);
                            startActivity(intent);
                        }, 800);
                    }
                }, permissions);
            });
            authorityDialog.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQ_CROP:
                    // 从剪切图片返回的数据
                    // 裁减后s
                    if (data != null) {
                        try {
                            if (Build.VERSION.SDK_INT >= 30) {
                                File path = CropFileUtils.getCropFile(activity, CropFileUtils.uri);
                                compressAction(path);
                            } else {
                                String path = targetFileAvatar.getPath();
                                Logs.e("uri:" + fileUri);
                                compressAction(new File(fileUri.getPath()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 压缩图片
     *
     * @param targetFile
     */
    private void compressAction(File targetFile) {
        if (targetFile == null) {
            return;
        }
        Log.e("targetFile", targetFile.length() + "");
        loading("正在压缩图片...");
        Luban.compress(_mActivity, targetFile)
                .putGear(Luban.THIRD_GEAR)
                .setMaxSize(200)
                .launch(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        Logs.e("compress start");
                    }

                    @Override
                    public void onSuccess(File file) {
                        userPortraitUpload(file);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Logs.e("compress error");
                        throwable.printStackTrace();
                    }
                });
    }

    /**
     * 上传用户头像
     *
     * @param localPathFile
     */
    private void userPortraitUpload(File localPathFile) {
        if (mViewModel != null) {
            mViewModel.uploadUserIcon(localPathFile, new OnBaseCallback() {

                @Override
                public void onBefore() {
                    super.onBefore();
                    loading("正在上传图片...");
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            Toaster.show( R.string.string_commit_tips);
                            Bitmap bitmap = BitmapFactory.decodeFile(localPathFile.getPath());
                            if (bitmap != null) {
                                mIvUserPortrait.setImageBitmap(toRoundBitmap(bitmap));
                                //                                GlideApp.with(_mActivity)
                                //                                        .load(localPathFile.getPath())
                                //                                        .asBitmap()
                                //                                        .placeholder(R.mipmap.ic_user_login)
                                //                                        .transform(new GlideCircleTransform(_mActivity, (int) (3 * ScreenUtil.getScreenDensity(_mActivity)), ContextCompat.getColor(_mActivity, R.color.white)))
                                //                                        .into(mIvUserPortrait);
                            }
                            mViewModel.refreshUserData();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private Bitmap toRoundBitmap(Bitmap bitmap) {
        //获取bmp的宽高 小的一个做为圆的直径r
        int R = Math.min(bitmap.getWidth(), bitmap.getHeight());
        //创建一个paint
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        //新创建一个Bitmap对象newBitmap 宽高都是r
        Bitmap newBitmap = Bitmap.createBitmap(R, R, Bitmap.Config.ARGB_8888);
        //创建一个使用newBitmap的Canvas对象
        Canvas canvas = new Canvas(newBitmap);
        //创建一个BitmapShader对象 使用传递过来的原Bitmap对象bmp
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //paint设置shader
        paint.setShader(bitmapShader);
        //canvas画一个圆 使用设置了shader的paint
        canvas.drawCircle(R / 2, R / 2, R / 2, paint);
        return newBitmap;
    }

    /**
     * 切换网络节点
     */
    private void netWorkPolling() {
        if (mViewModel != null) {
            mViewModel.polling(new NetworkPollingListener() {
                @Override
                public void onSuccess() {
                    Toaster.show( "切换成功");
                }

                @Override
                public void onFailure() {
                    Toaster.show( "网络异常，请稍后再试");
                }
            });
        }
    }

    /**
     * 获取缓存大小
     *
     * @return
     */
    private String getCacheSize() {
        try {
            File glideCacheDir = GlideModuleConfig.getGlideDiskCachePath(_mActivity);
            long cacheSize = FolderUtils.getFolderSize(glideCacheDir);
            String cache = FolderUtils.getFormatSize(cacheSize);
            return cache;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0K";
    }

    /**
     * 清理缓存
     */
    private void clearCache() {
        //清除缓存
        AlertDialog dialog = new AlertDialog.Builder(_mActivity)
                .setTitle("提示")
                .setMessage("是否清除缓存")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        File glideCacheDir = GlideModuleConfig.getGlideDiskCachePath(_mActivity);
                        if (FolderUtils.deleteDir(glideCacheDir)) {
                            Toaster.show( "清理缓存成功");
                            mTvClearCache.setText(getCacheSize());
                        }
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        dialog.show();
    }

    private void getAppVersion() {
        if (mViewModel != null) {
            mViewModel.getAppVersion(new OnBaseCallback<VersionVo>() {
                @Override
                public void onSuccess(VersionVo versionVo) {
                    if (versionVo != null) {
                        if (versionVo.isStateOK()) {
                            if (versionVo.getData() != null) {
                                VersionDialogHelper versionDialogHelper = new VersionDialogHelper(_mActivity);
                                versionDialogHelper.showVersion(true, versionVo.getData());
                            }
                        }
                    }
                }
            });
        }
    }

    private void canUnbindMobile() {
        if (mViewModel != null) {
            mViewModel.canUnbindMobile(new OnBaseCallback<BaseVo>() {

                @Override
                public void onSuccess(BaseVo data) {
                    if (data.isStateOK()){
                        //手机绑定
                        startForResult(BindPhoneFragment.newInstance(isSetBindViewPhone, mTvBindPhone.getText().toString()), REQUEST_CODE_BIND_PHONE);
                    }else {
                        Toaster.show("请设置密码后再进行操作！");
                    }
                }
            });
        }
    }

}
