package com.zqhy.app.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.provider.Settings;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.constant.PermissionConstants;
import com.box.other.blankj.utilcode.util.AppUtils;
import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.hjq.toast.Toaster;
import com.box.other.immersionbar.ImmersionBar;
import com.donkingliang.imageselector.PreviewActivity;
import com.donkingliang.imageselector.entry.Image;
import com.mvvm.base.AbsLifecycleFragment;
import com.mvvm.base.AbsViewModel;
import com.mvvm.stateview.ErrorState;
import com.mvvm.stateview.StateConstants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.umeng.analytics.MobclickAgent;
import com.zqhy.app.App;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.config.InviteConfig;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.game.appointment.GameAppointmentVo;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;
import com.zqhy.app.core.data.model.share.InviteDataVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnFragmentHiddenListener;
import com.zqhy.app.core.inner.WifiDownloadActionListener;
import com.zqhy.app.core.tool.MResource;
import com.zqhy.app.core.tool.OsUtil;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.KeyboardUtils;
import com.zqhy.app.core.tool.utilcode.NetWorkUtils;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.core.tool.utilcode.SizeUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.ui.dialog.LoadingDialog;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.core.view.kefu.KefuCenterFragment;
import com.zqhy.app.core.view.kefu.KefuHelperFragment;
import com.zqhy.app.core.view.login.LoginActivity;
import com.zqhy.app.core.view.main.MainActivity;
import com.zqhy.app.core.view.message.MessageMainFragment;
import com.zqhy.app.core.view.user.BindPhoneFragment;
import com.zqhy.app.core.vm.BaseViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.listener.NetworkPollingListener;
import com.zqhy.app.newproject.R;
import com.zqhy.app.share.ShareHelper;
import com.zqhy.app.share.WxShareEvent;
import com.zqhy.app.utils.CalendarReminderUtils;
import com.zqhy.app.utils.PermissionHelper;
import com.zqhy.app.utils.PopupWindowUtils;
import com.zqhy.app.utils.photo.CropFileUtils;
import com.zqhy.app.utils.photo.PhotoAlbumUtils;
import com.zqhy.app.utils.sdcard.SdCardManager;
import com.zqhy.app.widget.popup.OperationPopWindow;
import com.zqhy.app.widget.state.EmptyState;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Administrator
 * @date 2018/11/8
 */

public abstract class BaseFragment<T extends AbsViewModel> extends AbsLifecycleFragment<T> {

    protected static final int REQUEST_CODE_BIND_PHONE = 6000;

    private static final String TAG = BaseFragment.class.getSimpleName();

    private static final boolean isDebug = false;

    protected boolean isStatusBarFullWindow() {
        return true;
    }

    protected boolean isSetImmersiveStatusBar() {
        return true;
    }

    private LoadingDialog loadingDialog;


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initLoading();
        View titleBar = findViewById(MResource.getResourceId(_mActivity, "id", "titleBar_Layout"));
        if (titleBar == null) {
            if (isSetImmersiveStatusBar()) {
                setImmersiveStatusBar(true);
            }
            FrameLayout mFlStatusBar = findViewById(R.id.fl_status_bar);
            if (isHiddenStatusBar() && mFlStatusBar != null) {
                mFlStatusBar.setVisibility(View.GONE);
            }
        }
    }

    protected void setTitleBottomLine(int visibility) {
        TextView mTitleBottomLine = findViewById(R.id.title_bottom_line);
        if (mTitleBottomLine != null) {
            mTitleBottomLine.setVisibility(visibility);
        }
    }

    protected boolean isHiddenStatusBar() {
        return false;
    }

    @Override
    protected void initStatusBar() {
        ImmersionBar
                .with(this)
                .navigationBarColor(R.color.white)
                .statusBarDarkFont(true)
                .autoDarkModeEnable(true, 0.2f)
                .init();
        View titleBar = findViewById(MResource.getResourceId(_mActivity, "id", "titleBar_Layout"));
        if (titleBar != null) {
            Logs.e("initStatusBar", titleBar.getTag());
            if (titleBar.getTag() != "KEFU_TITLE") {
                ImmersionBar.setTitleBar(this, titleBar);
            }
        } else {
            if (!isStatusBarFullWindow()) {
                return;
            }
            FrameLayout fl_status_bar = findViewById(MResource.getResourceId(_mActivity, "id", "fl_status_bar"));
            if (fl_status_bar == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.height = ScreenUtil.getStatusBarHeight(_mActivity) + ScreenUtil.dp2px(_mActivity, 8);
                fl_status_bar.setLayoutParams(params);
            }
        }
    }

    public T getViewModel() {
        return mViewModel;
    }

    /**
     * 获取友盟统计页面标识
     *
     * @return
     */
    protected String getUmengPageName() {
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (isDebug) {
            Logs.e(TAG, this.getClass().getSimpleName() + "------onAttach");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
//        if(UserInfoModel.getInstance().isLogined()){
//            FloatViewManager.showAppFloat(_mActivity);
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isDebug) {
            Logs.e(TAG, this.getClass().getSimpleName() + "------onStart");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isDebug) {
            Logs.e(TAG, this.getClass().getSimpleName() + "------onResume");
        }
        if (!TextUtils.isEmpty(getUmengPageName())) {
            MobclickAgent.onPageStart(getUmengPageName());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isDebug) {
            Logs.e(TAG, this.getClass().getSimpleName() + "------onPause");
        }
        if (!TextUtils.isEmpty(getUmengPageName())) {
            MobclickAgent.onPageEnd(getUmengPageName());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isDebug) {
            Logs.e(TAG, this.getClass().getSimpleName() + "------onStop");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isDebug) {
            Logs.e(TAG, this.getClass().getSimpleName() + "------onDestroyView");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isDebug) {
            Logs.e(TAG, this.getClass().getSimpleName() + "------onDestroy");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (isDebug) {
            Logs.e(TAG, this.getClass().getSimpleName() + "------onDetach");
        }
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(EventCenter eventCenter) {
        onUserLoginOrLogoutListener(eventCenter);
        onApiServiceListener(eventCenter);
        //onShareListener(eventCenter);
        onPageStateListener(eventCenter);
    }

    protected void onPageStateListener(EventCenter eventCenter) {
        if (eventCenter.getEventCode() == EventConfig.PAGE_STATE_EVENT_CODE) {
            if (getStateEventKey() == null) {
                return;
            }
            String key;
            if (!TextUtils.isEmpty(getStateEventTag())) {
                key = getStateEventKey() + getStateEventTag();
            } else {
                key = (String) getStateEventKey();
            }
            if (key.equals(eventCenter.getTag())) {
                String state = (String) eventCenter.getData();
                if (!TextUtils.isEmpty(state)) {
                    if (StateConstants.ERROR_STATE.equals(state)) {
                        showError(ErrorState.class, "2");
                    } else if (StateConstants.NET_WORK_STATE.equals(state)) {
                        showError(ErrorState.class, "1");
                    } else if (StateConstants.EMPTY_DATA_STATE.equals(state)) {
                        showEmptyData();
                    } else if (StateConstants.LOADING_STATE.equals(state)) {
                        showLoading();
                    } else if (StateConstants.SUCCESS_STATE.equals(state)) {
                        showSuccess();
                    }
                }
            }
        }
    }


    public void showEmptyData(String state) {
        loadManager.showStateView(EmptyState.class, state);
    }

    /**
     * 监听用户登录登出
     *
     * @param eventCenter
     */
    private void onUserLoginOrLogoutListener(EventCenter eventCenter) {
        if (eventCenter.getEventCode() == EventConfig.USER_LOGIN_EVENT_CODE) {
            Logs.e("用户重新登录了");
            onUserReLogin();
        } else if (eventCenter.getEventCode() == EventConfig.AUDIT_USER_LOGIN_EVENT_CODE) {
            Logs.e("Audit用户重新登录了");
            onAuditUserReLogin();
        }
    }

    protected void onAuditUserReLogin() {
    }

    protected void onUserReLogin() {

    }

    /**
     * ApiService 监测
     *
     * @param eventCenter
     */
    private void onApiServiceListener(EventCenter eventCenter) {
        if (eventCenter.getEventCode() == EventConfig.REFRESH_API_SERVICE_EVENT_CODE) {
            int integer = (int) eventCenter.getData();
            if (integer == NetworkPollingListener.POLLING_SUCCESS) {
                if (mViewModel != null) {
                    mViewModel.refreshApiService();
                }
            }
        }
    }

    @Override
    public void pop() {
        KeyboardUtils.hideSoftInput(_mActivity);
        if (getPreFragment() == null) {
            _mActivity.finish();
        } else {
            super.pop();
        }
    }


    /**
     * 设置点击空白区域隐藏键盘
     *
     * @param isEnable
     */
    protected void setClickBlankAreaHideKeyboard(boolean isEnable) {
        if (_mActivity instanceof BaseActivity) {
            ((BaseActivity) _mActivity).setClickBlankAreaHideKeyboardEnable(isEnable);
        }
    }

    private void initLoading() {
        loadingDialog = new LoadingDialog(getActivity());
    }

    public void loading(String msg) {
        try {
            if (loadingDialog != null) {
                if (loadingDialog.isShowing()) {
                    loadingDialog.setTip(msg);
                    return;
                }
                loadingDialog.setTip(msg);
                loadingDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loading(int resourceId) {
        loading(getResources().getString(resourceId));
    }

    public void loading() {
        loading("数据加载中...");
    }

    public void loadingComplete() {
        try {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 注册ActionBar后退键以及标题
     *
     * @param mTitle
     */
    protected void initActionBackBarAndTitle(String mTitle) {
        initActionBackBarAndTitle(mTitle, true);
    }

    /**
     * 注册ActionBar后退键以及标题
     *
     * @param mTitle
     */
    protected void initActionBackBarAndTitle(String mTitle, boolean isShowBack) {
        showActionBack(isShowBack);
        setTitleText(mTitle);
    }

    /**
     * 注册ActionBar后退键以及标题
     *
     * @param resId
     */
    protected void initActionBackBarAndTitle(int resId) {
        initActionBackBarAndTitle(getActivity().getResources().getString(resId));
    }

    /**
     * 注册ActionBar后退键以及标题
     *
     * @param resId
     */
    protected void initActionBackBarAndTitle(int resId, boolean isShowBack) {
        initActionBackBarAndTitle(getActivity().getResources().getString(resId), isShowBack);
    }

    /**
     * 设置标题
     *
     * @param titleName
     */
    protected void setTitleText(String titleName) {
        setTitleText(titleName, true);
    }

    /**
     * 设置标题
     *
     * @param titleName
     * @param isBold
     */
    protected void setTitleText(String titleName, boolean isBold) {
        TextView mTitle = findViewById(MResource.getResourceId(_mActivity, "id", "tv_title"));
        RelativeLayout mTitleBar = findViewById(MResource.getResourceId(_mActivity, "id", "rl_title_bar"));
        if (mTitleBar != null && mTitle != null) {
            mTitleBar.setVisibility(View.VISIBLE);
            if (isBold) {
                mTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
            mTitle.setText(titleName);
        }
    }

    /**
     * 设置标题
     *
     * @param resId
     */
    protected void setTitleText(int resId) {
        setTitleText(getActivity().getResources().getString(resId));
    }

    /**
     * 设置标题颜色
     */
    protected void setTitleColor(int resId) {
        TextView mTitle = findViewById(MResource.getResourceId(_mActivity, "id", "tv_title"));
        if (mTitle != null) {
            mTitle.setTextColor(resId);
        }
    }

    protected final int LAYOUT_ON_LEFT = 1;
    protected final int LAYOUT_ON_CENTER = 2;

    protected void setTitleLayout(int layout) {
        TextView mTitle = findViewById(MResource.getResourceId(_mActivity, "id", "tv_title"));
        RelativeLayout mTitleBar = findViewById(MResource.getResourceId(_mActivity, "id", "rl_title_bar"));
        ImageView mImageView = findViewById(MResource.getResourceId(_mActivity, "id", "iv_back"));

        if (mTitle != null && mTitleBar != null) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTitle.getLayoutParams();
            if (layout == LAYOUT_ON_CENTER) {
                params.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                params.leftMargin = ScreenUtil.dp2px(_mActivity, 60);
                params.rightMargin = ScreenUtil.dp2px(_mActivity, 60);
            } else if (layout == LAYOUT_ON_LEFT) {
                params.removeRule(RelativeLayout.CENTER_IN_PARENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                params.leftMargin = ScreenUtil.dp2px(_mActivity, 48);
                params.rightMargin = ScreenUtil.dp2px(_mActivity, 48);
            }
        }
    }

    protected void setActionBackBar(int resId) {
        ImageView mImageView = findViewById(MResource.getResourceId(_mActivity, "id", "iv_back"));
        RelativeLayout mTitleBar = findViewById(MResource.getResourceId(_mActivity, "id", "rl_title_bar"));
        if (mTitleBar != null && mImageView != null) {
            mImageView.setImageResource(resId);
        }
    }

    protected void showActionBack(boolean isShowBack) {
        ImageView mImageView = findViewById(MResource.getResourceId(_mActivity, "id", "iv_back"));
        RelativeLayout mTitleBar = findViewById(MResource.getResourceId(_mActivity, "id", "rl_title_bar"));
        if (mTitleBar != null && mImageView != null) {
            mImageView.setOnClickListener(v -> {
                pop();
            });
            mTitleBar.setVisibility(isShowBack ? View.VISIBLE : View.GONE);
            mImageView.setVisibility(isShowBack ? View.VISIBLE : View.GONE);
        }
    }

    protected void hideActionBack(boolean isShowBack) {
        ImageView mImageView = findViewById(MResource.getResourceId(_mActivity, "id", "iv_back"));
        if (mImageView != null) {
            mImageView.setOnClickListener(v -> {
                pop();
            });
            mImageView.setVisibility(isShowBack ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 设置返回箭头点击事件
     *
     * @param onClickListener
     */
    protected void setActionBackBarClickListener(View.OnClickListener onClickListener) {
        ImageView mImageView = findViewById(MResource.getResourceId(_mActivity, "id", "iv_back"));
        if (mImageView != null) {
            mImageView.setOnClickListener(onClickListener);
        }
    }

    /**
     * 隐藏头部标题栏
     */
    protected void hideActionBackBarAndTitle() {
        RelativeLayout mTitleBar = findViewById(MResource.getResourceId(_mActivity, "id", "rl_title_bar"));
        if (mTitleBar != null) {
            mTitleBar.setVisibility(View.GONE);
        }
    }


    protected ShareHelper mShareHelper;
    protected InviteDataVo.InviteDataInfoVo inviteDataInfoVo;


    /**
     * 分享监测
     *
     * @param eventCenter
     */
    private void onShareListener(EventCenter eventCenter) {
        loadingComplete();
        if (eventCenter.getEventCode() == EventConfig.SHARE_INVITE_EVENT_CODE) {
            InviteDataVo inviteDataVo = (InviteDataVo) eventCenter.getData();
            String eventTag = eventCenter.getTag();
            if (TextUtils.isEmpty(eventTag)) {
                return;
            }
            String eventKey = (String) getStateEventKey();
            if (inviteDataVo != null && eventTag.equals(eventKey)) {
                if (inviteDataVo.isStateOK() && inviteDataVo.getData() != null) {
                    inviteDataInfoVo = inviteDataVo.getData().getInvite_info();
                    mShareHelper = new ShareHelper(_mActivity, new ShareHelper.OnShareListener() {
                        @Override
                        public void onSuccess() {
                            //ToastT.success("分享成功");
                            Toaster.show("分享成功");
                            Logs.e("OnShareListener : onSuccess");
                            onShareSuccess();
                        }

                        @Override
                        public void onError(String error) {
                            //ToastT.error("分享失败");
                            Toaster.show("分享失败");
                            Logs.e("OnShareListener : onError：" + error);
                        }

                        @Override
                        public void onCancel() {
                            //ToastT.normal("分享取消");
                            Toaster.show("分享取消");
                            Logs.e("OnShareListener : onCancel");
                        }
                    });
                    setInviteData(inviteDataVo.getData());
                }
            }
        } else if (eventCenter.getEventCode() == EventConfig.SHARE_WECHAT_CALLBACK_EVENT_CODE) {
            WxShareEvent mWxShareEvent = (WxShareEvent) eventCenter.getData();
            if (mWxShareEvent != null && mShareHelper != null) {
                mShareHelper.handleWxShareListener(mWxShareEvent);
            }
        }
    }


    public void onHalfShareAction() {
        if (mShareHelper != null && inviteDataInfoVo != null) {
            //mShareHelper.showShareInviteFriend(inviteDataInfoVo);
        } else {
            if (mViewModel != null && mViewModel instanceof BaseViewModel) {
                //loading();
                //((BaseViewModel) mViewModel).getShareData((String) getStateEventKey());
            }
        }
    }


    /**
     * 分享成功回调
     */
    public void onShareSuccess() {
    }

    /**
     * 设置邀请数据
     *
     * @param data
     */
    public void setInviteData(InviteDataVo.DataBean data) {
        if (InviteConfig.isJustShare()) {
            onHalfShareAction();
        }
    }


    /**
     * fragment 状态栏设置
     *
     * @param fontIconDark true 状态栏颜色深色
     *                     false 状态栏颜色白色
     */
    protected void setImmersiveStatusBar(boolean fontIconDark) {
        setImmersiveStatusBar(fontIconDark, 0xffcccccc);
    }

    /**
     * fragment 状态栏设置
     *
     * @param fontIconDark
     * @param statusBarPlaceColor
     */
    protected void setImmersiveStatusBar(boolean fontIconDark, int statusBarPlaceColor) {
        setStatusBar(statusBarPlaceColor);
        if (_mActivity instanceof BaseActivity) {
            ((BaseActivity) _mActivity).setImmersiveStatusBar(fontIconDark);
        }
    }

    protected void setStatusBar(int statusBarPlaceColor) {
        View mflStatusBar = findViewById(R.id.fl_status_bar);
        if (mflStatusBar != null) {
            if (!OsUtil.isNeedSetStatusBar()) {
                mflStatusBar.setBackgroundColor(statusBarPlaceColor);
            }
        }
    }

    /**
     * 验证用户是否登录
     *
     * @return
     */
    public boolean checkLogin() {
        if (!UserInfoModel.getInstance().isLogined()) {
            startActivity(new Intent(_mActivity, LoginActivity.class));
            return false;
        } else {
            return true;
        }
    }

    /**
     * 游戏详情
     *
     * @param gameid
     * @param game_type
     */
    public void goGameDetail(int gameid, int game_type) {
        goGameDetail(gameid, game_type, false);
    }

    /**
     * 游戏详情
     *
     * @param gameid
     * @param game_type
     * @param autoDownload
     */
    public void goGameDetail(int gameid, int game_type, boolean autoDownload) {
        GameDetailInfoFragment gameDetailInfoFragment = GameDetailInfoFragment.newInstance(gameid, game_type, autoDownload);
        startFragment(gameDetailInfoFragment);
    }


    /**
     * 跳转Fragment
     *
     * @param toFragment
     */
    public void startFragment(BaseFragment toFragment) {
        try {
            if (_mActivity instanceof MainActivity) {
                FragmentHolderActivity.startFragmentInActivity(_mActivity, toFragment);
            } else if (getParentFragment() != null) {
                FragmentHolderActivity.startFragmentInActivity(_mActivity, toFragment);
            } else {
                super.start(toFragment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转Fragment
     *
     * @param toFragment
     */
    public void startFragmentForResult(BaseFragment toFragment, int code) {
        try {
            if (!(_mActivity instanceof FragmentHolderActivity) && getParentFragment() != null) {
                FragmentHolderActivity.startFragmentForResult(_mActivity, toFragment, code);
            } else {
                super.startForResult(toFragment, code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 人工客服
     */
    public void goKefuCenter() {
        FragmentHolderActivity.startFragmentInActivity(_mActivity, new KefuCenterFragment());
    }

    public void goKefuCenter(int showType) {
        FragmentHolderActivity.startFragmentInActivity(_mActivity, KefuCenterFragment.newInstance(showType));
    }

    /**
     * @param isAutoShowKefuDialog
     */
    public void goKefuCenter(boolean isAutoShowKefuDialog) {
        FragmentHolderActivity.startFragmentInActivity(_mActivity, KefuCenterFragment.newInstance(isAutoShowKefuDialog ? 1 : 0));
    }

    /**
     * 客服主页
     */
    public void goKefuMain() {
        startFragment(new KefuHelperFragment());
    }

    /**
     * 消息主页
     */
    public void goMessageCenter() {
        if (checkLogin()) {
            startFragment(new MessageMainFragment());
        }
    }


    /**
     * 用户协议
     */
    public void goUserAgreement() {
        Intent intent = new Intent(_mActivity, BrowserActivity.class);
        intent.putExtra("url", AppConfig.APP_REGISTRATION_PROTOCOL);
        _mActivity.startActivity(intent);
    }

    /**
     * 隐私协议
     */
    public void goPrivacyAgreement() {
        Intent intent = new Intent(_mActivity, BrowserActivity.class);
        intent.putExtra("url", AppConfig.APP_PRIVACY_PROTOCOL);
        _mActivity.startActivity(intent);
    }

    /****************绑定手机提示弹窗***********************************************************************/


    /**
     * 验证用户是否绑定手机
     *
     * @return
     */
    protected boolean checkUserBindPhone() {
        return checkUserBindPhone("", "");
    }

    /**
     * 验证用户是否绑定手机
     *
     * @param title
     * @param message
     * @return
     */
    public boolean checkUserBindPhone(String title, String message) {
        if (checkLogin()) {
            UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
            if (TextUtils.isEmpty(userInfoBean.getMobile())) {
                showBindPhoneDialogTips(title, message);
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean checkUserBindPhoneTips1() {
        return checkUserBindPhone("温馨提示", "请先绑定手机号码哦！");
    }


    public boolean checkUserBindPhoneByCardGift() {
        if (checkLogin()) {
            if (AppConfig.isGonghuiChannel()) {
                return true;
            } else {
                if (UserInfoModel.getInstance().isBindMobile()) {
                    return true;
                } else {
                    showBindPhoneDialogTips("领取提示", "绑定手机后\n即可领取海量礼包福利！");
                }
            }
        }
        return false;

    }

    public void showBindPhoneDialogTips(String title, String message) {
        CustomDialog bindPhoneDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_bind_phone_tips, null),
                ScreenUtils.getScreenWidth(_mActivity) - SizeUtils.dp2px(_mActivity, 24),
                WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        Button mBtnCancel = bindPhoneDialog.findViewById(R.id.btn_cancel);
        Button mBtnConfirm = bindPhoneDialog.findViewById(R.id.btn_confirm);
        TextView mTvTitle = bindPhoneDialog.findViewById(R.id.tv_title);
        TextView mTvMessage = bindPhoneDialog.findViewById(R.id.tv_message);

        mBtnCancel.setOnClickListener(v -> {
            if (bindPhoneDialog != null && bindPhoneDialog.isShowing()) {
                bindPhoneDialog.dismiss();
            }
        });
        mBtnConfirm.setOnClickListener(v -> {
            if (bindPhoneDialog != null && bindPhoneDialog.isShowing()) {
                bindPhoneDialog.dismiss();
            }
            startForResult(BindPhoneFragment.newInstance(false, ""), REQUEST_CODE_BIND_PHONE);
        });

        mTvTitle.setText(title);
        mTvMessage.setText(message);

        bindPhoneDialog.show();
    }

    @Override
    public void startForResult(ISupportFragment toFragment, int requestCode) {
        if (_mActivity instanceof MainActivity) {
            FragmentHolderActivity.startFragmentForResult(_mActivity, (SupportFragment) toFragment, requestCode);
        } else {
            if (getParentFragment() != null && getParentFragment() instanceof SupportFragment) {
                ((SupportFragment) getParentFragment()).startForResult(toFragment, requestCode);
            } else {
                super.startForResult(toFragment, requestCode);
            }
        }
    }

    /****************绑定手机提示弹窗 end***********************************************************************/


    PopupWindow floatPop;
    TextView tvContent;

    /**
     * popupwindow显示
     *
     * @param content
     * @param anchor
     */
    public void showFloatView(String content, View anchor) {
        if (floatPop == null) {
            View layoutView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_pop_float, null);
            tvContent = layoutView.findViewById(R.id.tv_content);

            floatPop = new PopupWindow(layoutView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
            floatPop.setOutsideTouchable(false);
            ColorDrawable colorDrawable = new ColorDrawable(0x00000000);
            floatPop.setBackgroundDrawable(colorDrawable);
        }
        tvContent.setText(content);
        int windowPos[] = PopupWindowUtils.calculatePopWindowPos(anchor, tvContent);
        windowPos[1] += ScreenUtil.dp2px(App.instance(), 20);
        floatPop.showAtLocation(anchor, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);

        Logs.e("windowPos[0] = " + windowPos[0]);
        Logs.e("windowPos[1] = " + windowPos[1]);

        //        floatPop.showAsDropDown(anchor);
    }


    OperationPopWindow popWindow;

    /**
     * popupwindow显示
     *
     * @param content
     * @param anchor
     */
    public void showFloatPopView(String content, View anchor) {
        if (popWindow == null) {
            popWindow = new OperationPopWindow(_mActivity);
        }
        popWindow.showPopupWindow(anchor, content);
    }

    /**
     *
     * 2017.10.30 更新非WIFI下载时提示框
     *
     * ***********************************************************************************************************************/

    /**
     * 检查网络状态
     *
     * @param wifiDownloadActionListener
     */
    public void checkWiFiType(WifiDownloadActionListener wifiDownloadActionListener) {
        int netWorkType = NetWorkUtils.getNetWorkType(_mActivity);
        if (AppConfig.IS_ALLOW_NON_WIFI_DOWNLOAD_GAME) {
            if (wifiDownloadActionListener != null) {
                wifiDownloadActionListener.onDownload();
            }
        } else {
            switch (netWorkType) {
                case NetWorkUtils.NETWORK_WIFI:
                    if (wifiDownloadActionListener != null) {
                        wifiDownloadActionListener.onDownload();
                    }
                    break;
                case NetWorkUtils.NETWORK_NO:
                    //ToastT.warning(_mActivity, "当前无网络链接，请先链接网络");
                    Toaster.show("当前无网络链接，请先链接网络");
                    break;
                case NetWorkUtils.NETWORK_2G:
                case NetWorkUtils.NETWORK_3G:
                case NetWorkUtils.NETWORK_4G:
                case NetWorkUtils.NETWORK_UNKNOWN:
                    showWifiDownloadTipsDialog(wifiDownloadActionListener);
                    break;
                default:
                    break;
            }
        }

    }

    protected void showWifiDownloadTipsDialog(WifiDownloadActionListener wifiDownloadActionListener) {
        CustomDialog wifiDownloadTipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_download_wifi_tips, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        TextView mTvCancel = (TextView) wifiDownloadTipsDialog.findViewById(R.id.tv_cancel);
        TextView mTvContinue = (TextView) wifiDownloadTipsDialog.findViewById(R.id.tv_continue);
        mTvCancel.setOnClickListener(v -> {
            if (wifiDownloadTipsDialog != null && wifiDownloadTipsDialog.isShowing()) {
                wifiDownloadTipsDialog.dismiss();
            }
        });
        mTvContinue.setOnClickListener(v -> {
            if (wifiDownloadTipsDialog != null && wifiDownloadTipsDialog.isShowing()) {
                wifiDownloadTipsDialog.dismiss();
            }
            //            AppConfig.IS_ALLOW_NON_WIFI_DOWNLOAD_GAME = true;
            if (wifiDownloadActionListener != null) {
                wifiDownloadActionListener.onDownload();
            }
        });


        wifiDownloadTipsDialog.show();
    }


    protected TextView getSystemDialogTitleView(AlertDialog dialog) {
        try {
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(dialog);
            Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
            mTitle.setAccessible(true);
            TextView mTitleView = (TextView) mTitle.get(mAlertController);
            return mTitleView;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected TextView getSystemDialogMessageView(AlertDialog dialog) {
        try {
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(dialog);
            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
            mMessage.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mAlertController);
            return mMessageView;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void setSystemDialogTitleSize(AlertDialog dialog, int titleSize) {
        TextView mTitleView = getSystemDialogTitleView(dialog);
        if (mTitleView != null) {
            mTitleView.setTextSize(titleSize);
        }
    }

    protected void setSystemDialogMessageSize(AlertDialog dialog, int messageSize) {
        TextView mMessageView = getSystemDialogMessageView(dialog);
        if (mMessageView != null) {
            mMessageView.setTextSize(messageSize);
        }
    }

    /**
     * 设置默认dialog标题内容文字大小
     *
     * @param dialog
     */
    public void setDefaultSystemDialogTextSize(AlertDialog dialog) {
        setSystemDialogTitleSize(dialog, 16);
        setSystemDialogMessageSize(dialog, 14);
    }


    /****2018.06.25 把首页显示/隐藏底部导航的方法提取到基类里********************************************************************************/

    private boolean canScrollHideToolbar = false;

    protected void showToolbar() {
        if (!canScrollHideToolbar) {
            return;
        }
        if (_mActivity instanceof MainActivity) {
            ((MainActivity) _mActivity).showBottomToolbar();
        }
    }

    protected void hideToolbar() {
        if (!canScrollHideToolbar) {
            return;
        }
        if (_mActivity instanceof MainActivity) {
            ((MainActivity) _mActivity).hideBottomToolbar();
        }
    }

    /**
     * RecyclerView 联动滑动
     *
     * @param dx
     * @param dy
     */
    protected void onRecyclerViewScrolled(View view, int dx, int dy) {
        if (_mActivity instanceof MainActivity) {
            ((MainActivity) _mActivity).onRecyclerViewScrolled(view, dx, dy);
        }
    }


    /**********2019.02.14 更新************************************************/

    public void showTransactionRule() {
        BrowserActivity.newInstance(_mActivity, Constants.URL_SPECIFICATION_TRADE);
    }

    public void showCommentRule() {
        BrowserActivity.newInstance(_mActivity, Constants.URL_SPECIFICATION_H_COMMENT);
    }

    protected void showAnswerRule() {
        BrowserActivity.newInstance(_mActivity, Constants.URL_SPECIFICATION_QA);
    }

    public void showHeadPortraitRule() {
        BrowserActivity.newInstance(_mActivity, Constants.URL_SPECIFICATION_HEAD_PORTRAIT);
    }

    public void showUserLevelRule() {
        BrowserActivity.newInstance(_mActivity, Constants.URL_SPECIFICATION_USER_LEVEL);
    }

    /**************2019.03.04 自定义跳转**************************************************************/

    /**
     * 跳转详情
     *
     * @param json
     */
    protected void appJumpAction(String json) {
        Logs.e("AppJumpAction Json = " + json);
        if (TextUtils.isEmpty(json)) {
            return;
        }
        new AppJumpAction(_mActivity, this).jumpAction(json);
    }

    protected void appJumpAction(AppJumpInfoBean jumpInfoBean) {
        if (jumpInfoBean == null) {
            return;
        }
        new AppJumpAction(_mActivity, this).jumpAction(jumpInfoBean);
    }


    /**
     * @param type      0 本地图片   1 网络图片
     * @param imagePath 路径或者链接
     */
    public void ShowOnePicDetail(int type, String imagePath) {
        if (TextUtils.isEmpty(imagePath)) {
            return;
        }
        ArrayList<Image> images = new ArrayList();
        Image image = new Image();
        image.setType(type);
        image.setPath(imagePath);
        images.add(image);
        PreviewActivity.openActivity(_mActivity, images, true, 0, true);
    }


    public void showPicListDetail(ArrayList<Image> images, int position) {
        PreviewActivity.openActivity(_mActivity, images, true, position, true);
    }

    /**********************点评规则弹窗************************************************************************************/
    public void showCommentRuleDialog() {
        CustomDialog commentDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_comment_rule, null),
                ScreenUtils.getScreenWidth(_mActivity) - SizeUtils.dp2px(_mActivity, 24), WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        TextView mTvCommentRule1 = commentDialog.findViewById(R.id.tv_comment_rule_1);
        Button mBtnConfirm = commentDialog.findViewById(R.id.btn_confirm);

        mBtnConfirm.setOnClickListener(v -> {
            if (commentDialog != null && commentDialog.isShowing()) {
                commentDialog.dismiss();
            }
        });

        mTvCommentRule1.setText(Html.fromHtml(_mActivity.getResources().getString(R.string.string_comment_rule_1)));
        commentDialog.show();
    }

    /**
     * 问答规范
     * 弹窗
     */
    protected void showAnswerRuleDialog() {
        CustomDialog answerRuleDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_question_answer_rule, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        TextView mTvQaRule1 = answerRuleDialog.findViewById(R.id.tv_qa_rule_1);
        TextView mTvQaRule2 = answerRuleDialog.findViewById(R.id.tv_qa_rule_2);
        Button mBtnConfirm = answerRuleDialog.findViewById(R.id.btn_confirm);

        mTvQaRule1.setText(Html.fromHtml(_mActivity.getResources().getString(R.string.string_qa_rule_1)));
        mTvQaRule2.setText(Html.fromHtml(_mActivity.getResources().getString(R.string.string_qa_rule_2)));

        mBtnConfirm.setOnClickListener(view -> {
            if (answerRuleDialog != null && answerRuleDialog.isShowing()) {
                answerRuleDialog.dismiss();
            }
        });

        answerRuleDialog.show();
    }


    /**
     * 试玩规则弹窗
     */
    protected void showTryGameRuleDialog() {
        CustomDialog tryGameRuleDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_try_game_rule, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        tryGameRuleDialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if (tryGameRuleDialog != null && tryGameRuleDialog.isShowing()) {
                tryGameRuleDialog.dismiss();
            }
        });
        ((TextView) tryGameRuleDialog.findViewById(R.id.tv_app)).setText(getAppNameByXML(R.string.string_try_game_rule_text));
        tryGameRuleDialog.show();
    }

    /****************************************************** 相机相册调用 start******************************************************/


    /**
     * uri
     */
    private Uri photoUri;
    /**
     * 拍照照片路径
     */
    private File cameraSavePath;

    /**
     * 调用相机
     */
    public void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= 30) {
            cameraSavePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + System.currentTimeMillis() + ".jpg");
        } else {
            cameraSavePath = new File(SdCardManager.getInstance().getImageDir() + File.separator + System.currentTimeMillis() + ".jpg");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //第二个参数为 包名.fileProvider
            photoUri = FileProvider.getUriForFile(_mActivity, AppUtils.getAppPackageName() + ".fileProvider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            photoUri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, Constants.REQ_FROM_CAM);
    }

    /**
     * 调用相册
     */
    public void album() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if (intent.resolveActivity(getActivity().getPackageManager()) == null) {
                Toast.makeText(_mActivity, "很抱歉，当前您的手机不支持相册选择功能，请安装相册软件", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivityForResult(intent, Constants.REQ_FROM_GALLERY);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(_mActivity, "很抱歉，当前您的手机不支持相册选择功能，请安装相册软件", Toast.LENGTH_SHORT).show();
        }

    }


    private Map<String, Uri> mUriMap;

    /**
     * 调用裁剪
     *
     * @param path
     */
    private void crop(String path, File fileAvatar, boolean isCamera) {
        if (path == null) {
            return;
        }
        try {
            File fileIn = new File(path);
            if (fileAvatar != null) {
                Uri uriIn = createUriByFile(_mActivity, fileIn, false);
                Uri uriOut = Uri.fromFile(fileAvatar);
                crop(uriIn, uriOut);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crop(Uri uriIn, Uri uriOut) {
        cropImageByUri(_mActivity,
                uriIn, uriOut,
                new File(PhotoAlbumUtils.getRealPathFromUri(_mActivity, uriOut)),
                512,
                512, Constants.REQ_CROP);
    }

    private Uri createUriByFile(Context mContext, File file, boolean isCamera) {
        if (isCamera && Build.VERSION.SDK_INT >= 30) {
            ContentValues values = new ContentValues();

            values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            values.put(MediaStore.Images.Media.DISPLAY_NAME, file.getName());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            Uri uri = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            putFileUri(file, uri);
            return uri;
        }
        return Uri.fromFile(file);
    }

    private void putFileUri(File targetFile, Uri targetUri) {
        if (targetFile == null || !targetFile.exists()) {
            return;
        }
        if (mUriMap == null) {
            mUriMap = new HashMap<>();
        }
        mUriMap.put(targetFile.getAbsolutePath(), targetUri);
    }


    protected Uri getUriByFile(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        return mUriMap == null ? null : mUriMap.get(file.getAbsolutePath());
    }

    /**
     * 单独-裁减,生成jpg
     *
     * @param activity
     * @param uriInput
     * @param uriOutput
     * @param outputX
     * @param outputY
     * @param requestCode
     */
    public void cropImageByUri(Activity activity, Uri uriInput,
                               Uri uriOutput, File outputFile, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.setDataAndType(uriInput, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // no face detection
        intent.putExtra("noFaceDetection", true);

        if (Build.VERSION.SDK_INT >= 30) {
            //intent.putExtra(MediaStore.EXTRA_OUTPUT, uriOutput);
            //android 11以上，将文件创建在公有目录
            //创建文件
            CropFileUtils.createImageFile(activity, true);
            Uri croppedImageUri = CropFileUtils.uri;
            intent.putExtra(MediaStore.EXTRA_OUTPUT, croppedImageUri);
        } else {
            Uri imgCropUri = Uri.fromFile(outputFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgCropUri);

            fileUri = imgCropUri;
        }
        startActivityForResult(intent, requestCode);
    }

    // 临时文件
    public Uri fileUri;
    private File fileAvatar = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mShareHelper != null) {
            IUiListener iUiListener = mShareHelper.getIUiListener();
            Tencent.onActivityResultData(requestCode, resultCode, data, iUiListener);
        }

        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.REQ_FROM_GALLERY:
                    // 相册
                    Uri uri = data.getData();
                    if (uri == null) {
                        return;
                    }
                    String imgToSend = PhotoAlbumUtils.getRealPathFromUri(_mActivity, uri);
                    crop(imgToSend, getFileAvatar(), false);
                    break;
                case Constants.REQ_FROM_CAM:
                    // 相机
                    String photoPath;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        photoPath = String.valueOf(cameraSavePath);
                    } else {
                        photoPath = photoUri.getEncodedPath();
                    }
                    crop(photoPath, getFileAvatar(), true);
                    break;
                default:
                    break;
            }
        }
    }

    protected void setFileAvatar(File fileAvatar) {
        this.fileAvatar = fileAvatar;
    }

    protected File getFileAvatar() {
        return fileAvatar;
    }
    /****************************************************** 相机相册调用 end******************************************************/


    /**********设置Fragment onHidden 监听 2019..05.28**************************************************************************************/
    private OnFragmentHiddenListener onFragmentHiddenListener;

    public void setOnFragmentHiddenListener(OnFragmentHiddenListener onFragmentHiddenListener) {
        this.onFragmentHiddenListener = onFragmentHiddenListener;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (onFragmentHiddenListener != null) {
            onFragmentHiddenListener.onHidden(hidden);
        }
    }


    /**
     * 2020.04.15
     * 新增游戏预约日历提醒
     *
     * @param item
     */
    protected void showGameAppointmentCalendarReminder(GameAppointmentVo item, String toast) {
        String[] permissions = new String[]{
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR
        };
        if (PermissionHelper.hasPermissions(permissions)) {
            setGameAppointmentCalendarReminder(null, item, toast);
        } else {
            showCalendarReminderDialog(item, toast);
        }
    }


    /**
     * 显示日历提醒弹窗
     *
     * @param item
     */
    private void showCalendarReminderDialog(GameAppointmentVo item, String toast) {
        CustomDialog dialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_game_appointment_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        AppCompatButton mBtnCalendarReminder = dialog.findViewById(R.id.btn_calendar_reminder);
        AppCompatTextView mTvCancel = dialog.findViewById(R.id.tv_cancel);

        mBtnCalendarReminder.setOnClickListener(view -> {
            showCalendarPermissionsTipDialog(dialog, item, toast);
        });

        mTvCancel.setOnClickListener(view -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @SuppressLint("WrongConstant")
    public void showCalendarPermissionsTipDialog(CustomDialog dialog, GameAppointmentVo item, String toast) {
        if (_mActivity != null) {
            CustomDialog authorityDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.dialog_calendar_authority_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            authorityDialog.setCancelable(false);
            authorityDialog.setCanceledOnTouchOutside(false);

            TextView mTvCancel = authorityDialog.findViewById(R.id.tv_cancel);
            TextView mTvConfirm = authorityDialog.findViewById(R.id.tv_confirm);

            mTvCancel.setOnClickListener(v -> {
                if (authorityDialog != null && authorityDialog.isShowing()) {
                    authorityDialog.dismiss();
                }
            });
            mTvConfirm.setOnClickListener(v -> {
                if (authorityDialog != null && authorityDialog.isShowing()) {
                    authorityDialog.dismiss();
                }
                PermissionHelper.checkPermissions(new PermissionHelper.OnPermissionListener() {
                    @Override
                    public void onGranted() {
                        setGameAppointmentCalendarReminder(dialog, item, toast);
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        //ToastT.warning(_mActivity, "请授权后再尝试操作哦~");
                        Toaster.show("请授权后再尝试操作哦");
                        new Handler().postDelayed(() -> {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri aPackage = Uri.fromParts("package", AppUtils.getAppPackageName(), null);
                            intent.setData(aPackage);
                            startActivity(intent);
                        }, 800);
                    }
                }, PermissionConstants.CALENDAR);
            });
            authorityDialog.show();
        }
    }

    /**
     * 设置游戏预约日历提醒
     *
     * @param item
     */
    private void setGameAppointmentCalendarReminder(CustomDialog dialog, GameAppointmentVo item, String toast) {
        try {
            if (item != null) {
                String title = item.getCalendarTitle();
                String description = "";
                long reminderTime = item.getAppointment_begintime() * 1000;
                CalendarReminderUtils.addCalendarEvent(_mActivity, title, description, reminderTime, 10, 5);
            }

            //ToastT.success(_mActivity, toast);
            Toaster.show(toast);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2020.04.15
     * 删除游戏预约日历提醒
     *
     * @param gameAppointmentVo
     */
    protected void cancelGameAppointmentCalendarReminder(GameAppointmentVo gameAppointmentVo) {
        String[] permissions = new String[]{
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR
        };
        if (PermissionHelper.hasPermissions(permissions)) {
            try {
                if (gameAppointmentVo != null) {
                    String title = gameAppointmentVo.getCalendarTitle();
                    boolean result = CalendarReminderUtils.deleteCalendarEvent(_mActivity, title);
                    Logs.e("cancelGameAppointmentCalendarReminder--- title = " + title + "----result = " + result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showCalendarPermissionsTipDialog(gameAppointmentVo);
        }
    }

    @SuppressLint("WrongConstant")
    public void showCalendarPermissionsTipDialog(GameAppointmentVo gameAppointmentVo) {
        if (_mActivity != null) {
            CustomDialog authorityDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.dialog_calendar_authority_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            authorityDialog.setCancelable(false);
            authorityDialog.setCanceledOnTouchOutside(false);

            TextView mTvCancel = authorityDialog.findViewById(R.id.tv_cancel);
            TextView mTvConfirm = authorityDialog.findViewById(R.id.tv_confirm);

            mTvCancel.setOnClickListener(v -> {
                if (authorityDialog != null && authorityDialog.isShowing()) {
                    authorityDialog.dismiss();
                }
            });
            mTvConfirm.setOnClickListener(v -> {
                if (authorityDialog != null && authorityDialog.isShowing()) {
                    authorityDialog.dismiss();
                }
                PermissionHelper.checkPermissions(new PermissionHelper.OnPermissionListener() {
                    @Override
                    public void onGranted() {
                        try {
                            if (gameAppointmentVo != null) {
                                String title = gameAppointmentVo.getCalendarTitle();
                                boolean result = CalendarReminderUtils.deleteCalendarEvent(_mActivity, title);
                                Logs.e("cancelGameAppointmentCalendarReminder--- title = " + title + "----result = " + result);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        //ToastT.warning(_mActivity, "请授权后再尝试操作哦~");
                        Toaster.show("请授权后再尝试操作哦");
                        new Handler().postDelayed(() -> {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri aPackage = Uri.fromParts("package", AppUtils.getAppPackageName(), null);
                            intent.setData(aPackage);
                            startActivity(intent);
                        }, 800);
                    }
                }, PermissionConstants.CALENDAR);
            });
            authorityDialog.show();
        }
    }


    /**
     * 2020.05.20 动态替换strings.xml值
     */
    public String getStringResByXML(int stringRes, Object... args) {
        try {
            return String.format(_mActivity.getResources().getString(stringRes), args);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 动态替换应用名
     *
     * @param stringRes
     * @return
     */
    public String getAppNameByXML(int stringRes) {
        String applicationName = getString(R.string.app_name);
        return getStringResByXML(stringRes, applicationName);
    }

    /**
     * 动态替换应用名简称
     *
     * @param stringRes
     * @return
     */
    public String getAppReferNameByXML(int stringRes) {
        String applicationName = getString(R.string.app_refer_name);
        return getStringResByXML(stringRes, applicationName);
    }

    /**
     * 获取应用名简称
     *
     * @return
     */
    public String getAppVipMonthName() {
        String applicationName = getString(R.string.app_refer_name);
        return applicationName;
    }
}
