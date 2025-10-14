package com.zqhy.app.core.vm.main;

import android.app.Application;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.CheckedTextView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.model.sdk.SdkAction;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.repository.main.MainRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.inner.OnResponseListener;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.listener.NetworkPollingListener;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.sdcard.SdCardManager;

import java.io.File;
import java.util.Map;


/**
 * @author Administrator
 * @date 2018/11/6
 */
public class MainViewModel extends AbsViewModel<MainRepository> {

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 设置底部样式
     */
    public void setBottomTabSelector(Context mContext, CheckedTextView targetRadioButton, String drawableDefault, String drawableChecked) {
        StateListDrawable tabHomeDrawable = new StateListDrawable();

        BitmapDrawable tabHomeDrawableDefault = new BitmapDrawable(decodeBitmap(mContext, getMenuFilePath(mContext, drawableDefault)));
        BitmapDrawable tabHomeDrawableChecked = new BitmapDrawable(decodeBitmap(mContext, getMenuFilePath(mContext, drawableChecked)));

        tabHomeDrawable.addState(new int[]{android.R.attr.state_checked},
                tabHomeDrawableChecked);
        tabHomeDrawable.addState(new int[]{-android.R.attr.state_checked},
                tabHomeDrawableDefault);
        tabHomeDrawable.addState(new int[]{android.R.attr.state_selected},
                tabHomeDrawableChecked);
        tabHomeDrawable.addState(new int[]{-android.R.attr.state_selected},
                tabHomeDrawableDefault);

        targetRadioButton.setCompoundDrawablePadding(0);
        targetRadioButton.setCompoundDrawablesWithIntrinsicBounds(null, tabHomeDrawable, null, null);
    }


    public void setDefaultBottomTabSelector(Context mContext, CheckedTextView targetRadioButton, int drawableDefault, int drawableChecked) {
        StateListDrawable tabHomeDrawable = new StateListDrawable();

        tabHomeDrawable.addState(new int[]{android.R.attr.state_checked},
                mContext.getResources().getDrawable(drawableChecked));
        tabHomeDrawable.addState(new int[]{-android.R.attr.state_checked},
                mContext.getResources().getDrawable(drawableDefault));
        tabHomeDrawable.addState(new int[]{android.R.attr.state_selected},
                mContext.getResources().getDrawable(drawableChecked));
        tabHomeDrawable.addState(new int[]{-android.R.attr.state_selected},
                mContext.getResources().getDrawable(drawableDefault));

        float density = ScreenUtil.getScreenDensity(mContext);
        targetRadioButton.setCompoundDrawablePadding((int) (3 * density));
        targetRadioButton.setCompoundDrawablesWithIntrinsicBounds(null, tabHomeDrawable, null, null);
    }


    /**
     * 设置底部颜色样式
     */
    public void setBottomTabColor(CheckedTextView targetRadioButton, int normalColor, int selectedColor) {
        try {
            ColorStateList stateList = new ColorStateList(new int[][]{{android.R.attr.state_checked, android.R.attr.state_enabled},
                    {android.R.attr.state_selected, android.R.attr.state_enabled},
                    {android.R.attr.state_pressed, android.R.attr.state_enabled},
                    {}},
                    new int[]{selectedColor, selectedColor, selectedColor, normalColor});
            targetRadioButton.setTextColor(stateList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param mContext
     * @param fileName
     * @return
     */
    public String getMenuFilePath(Context mContext, String fileName) {
        File file = SdCardManager.getFileMenuDir(mContext);

        if (!file.exists()) {
            file.mkdirs();
        }

        File targetFile = new File(file, fileName);
        return targetFile.getPath();
    }

    /**
     * 转Bitmap
     *
     * @param mContext
     * @param path
     * @return
     */
    public Bitmap decodeBitmap(Context mContext, String path) {
        Bitmap originalBitmap = BitmapFactory.decodeFile(path);
        int originalWidth = originalBitmap.getWidth();
        int originalHeight = originalBitmap.getHeight();
        float density = ScreenUtil.getScreenDensity(mContext);
        int newWidth = (int) (originalWidth / 2 * density * density);
        int newHeight = (int) (originalHeight / 2 * density * density);

        float scaleX = ((float) newWidth) / originalWidth;
        float scaleY = ((float) newHeight) / originalHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        Bitmap changedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0,
                originalWidth, originalHeight, matrix, true);

        return changedBitmap;
    }

    /**
     * 转Bitmap
     *
     * @param mContext
     * @param path
     * @return
     */
    public Bitmap decodeCenterBitmap(Context mContext, String path) {
        Bitmap originalBitmap = BitmapFactory.decodeFile(path);
        int originalWidth = originalBitmap.getWidth();
        int originalHeight = originalBitmap.getHeight();
        float density = ScreenUtil.getScreenDensity(mContext);
        int newWidth = (int) (originalWidth / 2 * density);
        int newHeight = (int) (originalHeight / 2 * density);

        float scaleX = ((float) newWidth) / originalWidth;
        float scaleY = ((float) newHeight) / originalHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        Bitmap changedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0,
                originalWidth, originalHeight, matrix, true);

        return changedBitmap;
    }


    public void getShareData(String tag, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getShareData(tag, onNetWorkListener);
        }
    }

    public void addInviteShare(int type) {
        if (mRepository != null) {
            mRepository.addInviteShare(type);
        }
    }

    public interface OnSwitchUserCallback {
        void onSuccess();

    }


    public void switchUser2(int uid, String token, String username, OnResponseListener onResponseListener) {
        if (mRepository != null) {
            mRepository.getUserInfoCallBack(uid, token, username, onResponseListener);
        }
    }


    public void showSwitchUserDialog(Context mContext, SdkAction sdkAction, OnSwitchUserCallback onSwitchUserCallback) {
        int uid = sdkAction.getUid();
        String token = sdkAction.getToken();
        String username = sdkAction.getUsername();

        CustomDialog switchUserDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_switch_user, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        TextView mTvSwitchUser = switchUserDialog.findViewById(R.id.tv_switch_user);
        TextView mTvCancel = switchUserDialog.findViewById(R.id.tv_cancel);
        TextView mTvSwitch = switchUserDialog.findViewById(R.id.tv_switch);
        switchUserDialog.setCanceledOnTouchOutside(false);
        switchUserDialog.setCancelable(false);

        float density = ScreenUtil.getScreenDensity(mContext);

        GradientDrawable gd1 = new GradientDrawable();
        gd1.setCornerRadius(1 * density);
        gd1.setColor(ContextCompat.getColor(mContext, R.color.color_f9f9f9));
        gd1.setStroke((int) (1 * density), ContextCompat.getColor(mContext, R.color.color_e8e8e8));
        mTvCancel.setBackground(gd1);

        GradientDrawable gd2 = new GradientDrawable();
        gd2.setCornerRadius(1 * density);
        gd2.setColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
        mTvSwitch.setBackground(gd2);


        mTvCancel.setOnClickListener(v -> {
            if (switchUserDialog != null && switchUserDialog.isShowing()) {
                switchUserDialog.dismiss();
            }
            if (onSwitchUserCallback != null) {
                onSwitchUserCallback.onSuccess();
            }
        });
        mTvSwitch.setOnClickListener(v -> {
            if (switchUserDialog != null && switchUserDialog.isShowing()) {
                switchUserDialog.dismiss();
            }
            switchUser2(uid, token, username, (result) -> {
                if (onSwitchUserCallback != null) {
                    onSwitchUserCallback.onSuccess();
                }
            });
        });

        String appUsername = UserInfoModel.getInstance().getUserInfo().getUsername();
        String sdkUsername = sdkAction.getUsername();
        mTvSwitchUser.setText(Html.fromHtml(mContext.getResources().getString(R.string.string_switch_user, appUsername, sdkUsername)));

        switchUserDialog.show();
    }


    public void getAppVersion(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getAppVersion(onNetWorkListener);
        }
    }

    public void sendAdActive(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.sendAdActive(onNetWorkListener);
        }
    }

    public void getKefuMessageData(String tag, int maxID, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getKefuMessageData(tag, maxID, onNetWorkListener);
        }
    }

    public void getDynamicGameMessageData(long logTime,OnNetWorkListener onNetWorkListener) {
        if(mRepository != null){
            mRepository.getDynamicGameMessageData(logTime,onNetWorkListener);
        }
    }

    public void refreshUser() {
        if (mRepository != null) {
            if (UserInfoModel.getInstance().isLogined()) {
                UserInfoVo.DataBean dataBean = UserInfoModel.getInstance().getUserInfo();
                int uid = dataBean.getUid();
                String username = dataBean.getUsername();
                String token = dataBean.getToken();
                mRepository.getUserInfo(uid, token, username);
            }
        }
    }


    /**
     * 获取首页浮标数据信息
     *
     * @param onNetWorkListener
     */
    public void getGameFloatData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getGameFloatData(onNetWorkListener);
        }
    }


    /**
     * 获取首页pop数据
     *
     * @param onNetWorkListener
     */
    public void getAppPopData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getAppPopData(onNetWorkListener);
        }
    }


    /**
     * uid，token登陆账号
     *
     * @param uid
     * @param username
     * @param token
     */
    public void getUserInfo(int uid, String username, String token) {
        if (mRepository != null) {
            mRepository.getUserInfo(uid, username, token);
        }
    }

    /**
     * 获取用户新游预约的信息
     *
     * @param onNetWorkListener
     */
    public void getUserGameAppointmentInfo(String timestamp, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getUserGameAppointmentInfo(timestamp, onNetWorkListener);
        }
    }

    /**
     * 推荐首页数据接口
     *
     * @param onNetWorkListener
     */
    public void getMainRecommendData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getMainRecommendData(onNetWorkListener);
        }
    }

    /**
     * 获取游戏数据
     *
     * @param gameid
     * @param onNetWorkListener
     */
    public void getChannelGameData(int gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getChannelGameData(gameid, onNetWorkListener);
        }
    }

    public void getAdTest(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getAdTest(onNetWorkListener);
        }
    }


    /**
     * 新人福利弹窗
     */
    public void getXinRenPopData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getXinRenPopData(onNetWorkListener);
        }
    }

    /**
     * 获取会员生日奖励状态
     */
    public void getSuperBirthdayRewardStatus(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getSuperBirthdayRewardStatus(onNetWorkListener);
        }
    }

    /**
     * 获取会员生日奖励状态
     */
    public void getBirthdayReward(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getBirthdayReward(onNetWorkListener);
        }
    }

    /**
     * 公会Vip客服弹窗
     *
     * @param onNetWorkListener
     */
    public void getUnionVipPop(String uid, String token,OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getUnionVipPop(uid, token, onNetWorkListener);
        }
    }

    /**
     * 查询用户是否在大 R 维护列表
     */
    public void rmbusergive(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.rmbusergive(onNetWorkListener);
        }
    }

    /**
     * 领取大R奖励
     */
    public void rmbusergiveGetReward(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.rmbusergiveGetReward(onNetWorkListener);
        }
    }

    /**
     * 用户是否有回归奖励
     */
    public void getComeBack(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getComeBack(onNetWorkListener);
        }
    }

    /**
     * 获取首页所有弹窗数据
     */
    public void getAllPop(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getAllPop(onNetWorkListener);
        }
    }

    /**
     * 首页浮标
     */
    public void appFloatIcon(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.appFloatIcon(onNetWorkListener);
        }
    }

    /**
     * 轮询地址
     * @param networkPollingListener
     */
    public void pollingUrls(NetworkPollingListener networkPollingListener){
        if(mRepository != null){
            mRepository.pollingUrls(networkPollingListener);
        }
    }

    /**
     * 应用市场审核初始化
     * @param onNetWorkListener
     */
    public void getMarketInit(OnNetWorkListener onNetWorkListener){
        if(mRepository != null){
            mRepository.getMarketInit(onNetWorkListener);
        }
    }

    public void getNewInit(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getNewInit(onNetWorkListener);
        }
    }

    public void getRefundGames(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getRefundGames(onNetWorkListener);
        }
    }

    public void errLog(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.errLog(params, onNetWorkListener);
        }
    }

    public void chatGetToken(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.chatGetToken(onNetWorkListener);
        }
    }

    public void getUidByAccount(String accid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getUidByAccount(accid, onNetWorkListener);
        }
    }
}
