package com.zqhy.app.model;

import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.zqhy.app.App;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.config.InviteConfig;
import com.zqhy.app.config.WxControlConfig;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.sp.ListDataSave;
import com.zqhy.app.utils.sp.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/8
 */

public class UserInfoModel {

    private static volatile UserInfoModel instance;

    public static final String SP_USER_INFO_MODEL = "SP_USER_INFO_MODEL";

    public static final String KEY_USER_INFO_MODEL_USERNAME = "KEY_USER_INFO_MODEL_USERNAME";

    public static final String KEY_USER_INFO_MODEL_LAST_LOGIN_USERNAME = "KEY_USER_INFO_MODEL_LAST_LOGIN_USERNAME";

    public static final String KEY_USER_INFO_MODEL_LAST_LOGIN_AUTH = "KEY_USER_INFO_MODEL_LAST_LOGIN_AUTH";

    public static final String KEY_USER_INFO_MODEL_LAST_LOGIN_UID = "KEY_USER_INFO_MODEL_LAST_LOGIN_UID";

    public static final String KEY_USER_INFO_MODEL_LAST_LOGIN_TOKEN = "KEY_USER_INFO_MODEL_LAST_LOGIN_TOKEN";

    private final int MaxSaveLoggedAccount = 5;

    private volatile UserInfoVo.DataBean userInfoDataVo;

    private UserInfoModel() {
    }

    public static UserInfoModel getInstance() {
        if (instance == null) {
            synchronized (UserInfoModel.class) {
                if (instance == null) {
                    instance = new UserInfoModel();
                }
            }
        }
        return instance;
    }

    /**
     * 设置用户等级
     *
     * @param user_level
     * @param mIvUserLevel
     * @param mTvUserLevel
     */
    public static void setUserLevel(int user_level, ImageView mIvUserLevel, TextView mTvUserLevel) {
        if (mIvUserLevel != null && mTvUserLevel != null) {
            mTvUserLevel.setText(String.valueOf(user_level));
            if (user_level == 0) {
                mIvUserLevel.setImageResource(R.mipmap.img_user_level_1);
            } else if (user_level > 0 && user_level <= 30) {
                mIvUserLevel.setImageResource(R.mipmap.img_user_level_2);
            } else if (user_level > 30 && user_level <= 60) {
                mIvUserLevel.setImageResource(R.mipmap.img_user_level_3);
            } else if (user_level > 60 && user_level <= 90) {
                mIvUserLevel.setImageResource(R.mipmap.img_user_level_4);
            } else if (user_level > 90) {
                mIvUserLevel.setImageResource(R.mipmap.img_user_level_5);
            }
        }
    }

    /**
     * 设置用户Vip等级
     *
     * @param user_vip_level
     * @param mIvUserVipLevel
     * @param mTvUserVipLevel
     */
    public static void setUserVipLevel(int user_vip_level, ImageView mIvUserVipLevel, TextView mTvUserVipLevel) {
        if (mIvUserVipLevel != null && mTvUserVipLevel != null) {
            if (user_vip_level == 0) {
                mTvUserVipLevel.setVisibility(View.GONE);
                mIvUserVipLevel.setImageResource(R.mipmap.ic_vip_unopen_new);
            } else if (user_vip_level > 0) {
                mTvUserVipLevel.setVisibility(View.GONE);
                mTvUserVipLevel.setText("VIP" + String.valueOf(user_vip_level));
                mIvUserVipLevel.setImageResource(R.mipmap.ic_vip_open_new);
            }
        }
    }

    /**
     * 设置用户月卡标识
     *
     * @param isVipMember
     * @param flUserMouthCard
     */
    public static void setUserMonthCard(boolean isVipMember, FrameLayout flUserMouthCard) {
        if (flUserMouthCard != null) {
            flUserMouthCard.setVisibility(isVipMember ? View.VISIBLE : View.GONE);
        }
    }


    public synchronized UserInfoVo.DataBean getUserInfo() {
        return userInfoDataVo;
    }


    public boolean isLogined() {
        return this.userInfoDataVo != null;
    }

    public boolean isBindMobile() {
        if (this.userInfoDataVo == null) {
            return false;
        }
        return !TextUtils.isEmpty(this.userInfoDataVo.getMobile());
    }

    public String getBindMobile() {
        if (!isBindMobile()) {
            return null;
        }
        return this.userInfoDataVo.getMobile();
    }


    public void login(UserInfoVo.DataBean userInfoDataVo) {
        login(userInfoDataVo, true);
    }

    public void login(UserInfoVo.DataBean userInfoDataVo, boolean isGlobalNotification) {
        this.userInfoDataVo = userInfoDataVo;
        SPUtils spUtils = new SPUtils(App.instance(), SP_USER_INFO_MODEL);
        if (userInfoDataVo != null) {
            Logs.e("KEY_USER_INFO_MODEL_LAST_LOGIN_AUTH:",userInfoDataVo.getAuth());
            spUtils.putString(KEY_USER_INFO_MODEL_LAST_LOGIN_USERNAME, userInfoDataVo.getUsername());
            spUtils.putString(KEY_USER_INFO_MODEL_LAST_LOGIN_AUTH, userInfoDataVo.getAuth());
            spUtils.putInt(KEY_USER_INFO_MODEL_LAST_LOGIN_UID, userInfoDataVo.getUid());
            spUtils.putString(KEY_USER_INFO_MODEL_LAST_LOGIN_TOKEN, userInfoDataVo.getToken());
            InviteConfig.invite_type = userInfoDataVo.getInvite_type();
            //添加测试账号
            if ("jiuyao001".equals(userInfoDataVo.getUsername()) || "testwang".equals(userInfoDataVo.getUsername())) {
                WxControlConfig.wx_control = 1;
            }
        } else {
            spUtils.remove(KEY_USER_INFO_MODEL_LAST_LOGIN_USERNAME);
            spUtils.remove(KEY_USER_INFO_MODEL_LAST_LOGIN_AUTH);
            spUtils.remove(KEY_USER_INFO_MODEL_LAST_LOGIN_UID);
            spUtils.remove(KEY_USER_INFO_MODEL_LAST_LOGIN_TOKEN);
            InviteConfig.invite_type = 0;
        }
        AppUtils.setTgid(userInfoDataVo == null ? AppUtils.getChannelFromApk() : userInfoDataVo.getTgid());
        if (isGlobalNotification) {
            refreshUserInfo();
        }
    }

    /**
     * 刷新用户
     */
    private void refreshUserInfo() {
        EventCenter eventCenter = new EventCenter(EventConfig.USER_LOGIN_EVENT_CODE, userInfoDataVo);
        EventBus.getDefault().post(eventCenter);
    }

    public void logout() {
        login(null);
    }


    /**
     * 判断是否当前登录用户
     *
     * @param user_id
     * @return
     */
    public boolean checkLoginUser(int user_id) {
        if (userInfoDataVo != null) {
            return userInfoDataVo.getUid() == user_id;
        }
        return false;
    }


    /**
     * 判断当前登录用户是否实名
     *
     * @return true 已实名  false 未实名
     */
    public boolean isSetCertification() {
        if (userInfoDataVo != null) {
            return !TextUtils.isEmpty(userInfoDataVo.getReal_name()) && !TextUtils.isEmpty(userInfoDataVo.getIdcard());
        }
        return false;
    }

    /**
     * 添加用户实名制信息
     *
     * @param sfzname
     * @param sfzid
     */
    public void setCertification(String sfzname, String sfzid) {
        if (userInfoDataVo != null) {
            userInfoDataVo.setReal_name(sfzname);
            userInfoDataVo.setIdcard(sfzid);
            refreshUserInfo();
        }
    }


    /**
     * 绑定手机
     *
     * @param mobile
     */
    public void setBindMobile(String mobile) {
        if (userInfoDataVo != null) {
            userInfoDataVo.setMobile(mobile);
            refreshUserInfo();
        }
    }

    /**
     * 解绑手机
     */
    public void setUnBindMobile() {
        if (userInfoDataVo != null) {
            userInfoDataVo.setMobile("");
            refreshUserInfo();
        }
    }

    /**
     * 判断当前用户是否为会员
     *
     * @return
     */
    public boolean isVipMember() {
        if (userInfoDataVo != null && userInfoDataVo.getVip_member() != null) {
            return userInfoDataVo.getVip_member().isVipMember();
        }
        return false;
    }

    /**
     * 获取用户Vip等级
     *
     * @return
     */
    public int getUserVipLevel() {
        if (userInfoDataVo != null && userInfoDataVo.getVip_info() != null) {
            return userInfoDataVo.getVip_info().getVip_level();
        }
        return 0;
    }


    /**
     * 获取会员到期日期
     *
     * @return
     */
    public String getVipMemberLastDate() {
        if (userInfoDataVo != null && userInfoDataVo.getVip_member() != null) {
            long lastDate = userInfoDataVo.getVip_member().getVip_member_expire();
            return CommonUtils.formatTimeStamp(lastDate * 1000, "yyyy-MM-dd");
        }
        return "";
    }


    /**
     * 保存已登录过的用户名或者手机号
     *
     * @param account
     */
    public void saveLoggedAccount(String account, String password) {
        if (TextUtils.isEmpty(account)) {
            return;
        }
        if (TextUtils.isEmpty(password)) {
            return;
        }
        ListDataSave dataSave = new ListDataSave(App.instance(), SP_USER_INFO_MODEL);

        List<String> list = dataSave.getDataList(KEY_USER_INFO_MODEL_USERNAME);
        if (list == null) {
            list = new LinkedList<>();
        }
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            String s = iterator.next();
            if (s.equals(account)) {
                iterator.remove();
                break;
            }
            if (s.startsWith(account + ";")) {
                iterator.remove();
                break;
            }
        }
        list.add(0, account + ";" + password);
        dataSave.setDataList(KEY_USER_INFO_MODEL_USERNAME, list);
    }

    /**
     * 删除已登录过的用户名或者手机号
     *
     * @param account
     */
    public void deleteLoggedAccount(String account) {
        if (TextUtils.isEmpty(account)) {
            return;
        }
        ListDataSave dataSave = new ListDataSave(App.instance(), SP_USER_INFO_MODEL);

        List<String> list = dataSave.getDataList(KEY_USER_INFO_MODEL_USERNAME);
        if (list == null) {
            list = new LinkedList<>();
        }
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            String s = iterator.next();
            if (s.equals(account)) {
                iterator.remove();
                break;
            }
            if (s.startsWith(account + ";")) {
                iterator.remove();
                break;
            }
        }
        //list.add(0, account);
        dataSave.setDataList(KEY_USER_INFO_MODEL_USERNAME, list);
    }

    /**
     * 替换已保存过的用户名或者手机号
     *
     * @param fromAccount
     * @param toAccount
     */
    public void replaceLoggedAccount(String fromAccount, String toAccount) {
        if (TextUtils.isEmpty(fromAccount) || TextUtils.isEmpty(toAccount)) {
            return;
        }
        ListDataSave dataSave = new ListDataSave(App.instance(), SP_USER_INFO_MODEL);

        List<String> list = dataSave.getDataList(KEY_USER_INFO_MODEL_USERNAME);
        if (list == null) {
            list = new LinkedList<>();
        }
        boolean isExitFromAccount = false;
        for (String s : list) {
            if (toAccount.equals(s)) {
                return;
            }
            if (fromAccount.equals(s)) {
                isExitFromAccount = true;
                list.remove(s);
                break;
            }
        }
        if (!isExitFromAccount) {
            return;
        }
        list.add(0, toAccount);
        dataSave.setDataList(KEY_USER_INFO_MODEL_USERNAME, list);
    }

    public List<String> getLoggedAccount() {
        ListDataSave dataSave = new ListDataSave(App.instance(), SP_USER_INFO_MODEL);
        List<String> dataList = dataSave.getDataList(KEY_USER_INFO_MODEL_USERNAME);
        return dataList.size() >= MaxSaveLoggedAccount ? dataList.subList(0, MaxSaveLoggedAccount) : dataList;
    }

    public String getLastLoggedAccount() {
        ListDataSave dataSave = new ListDataSave(App.instance(), SP_USER_INFO_MODEL);
        List<String> dataList = dataSave.getDataList(KEY_USER_INFO_MODEL_USERNAME);
        if (dataList != null && dataList.size() > 0) {
            return dataList.get(0);
        }
        return "";
    }

    /**
     * 获取用户uid
     *
     * @return
     */
    public int getUID() {
        return userInfoDataVo == null ? 0 : userInfoDataVo.getUid();
    }

    /**
     * 获取用户昵称
     * @return
     */
    public String getUserNickname() {
        return userInfoDataVo == null ? null : userInfoDataVo.getUser_nickname();
    }
}
