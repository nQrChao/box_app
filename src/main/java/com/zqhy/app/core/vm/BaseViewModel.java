package com.zqhy.app.core.vm;

import android.app.Application;
import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.inner.OnResponseListener;
import com.zqhy.app.model.UserInfoModel;

/**
 * @author Administrator
 */
public class BaseViewModel<T extends BaseRepository> extends AbsViewModel<T> {


    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 刷新用户数据
     */
    public void refreshUserData() {
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
     * 刷新用户（单独回调，无全局通知）
     *
     * @param onResponseListener
     */
    public void refreshUserDataWithoutNotification(OnNetWorkListener onResponseListener) {
        if (mRepository != null) {
            if (UserInfoModel.getInstance().isLogined()) {
                UserInfoVo.DataBean dataBean = UserInfoModel.getInstance().getUserInfo();
                int uid = dataBean.getUid();
                String username = dataBean.getUsername();
                String token = dataBean.getToken();
                mRepository.getUserInfoWithoutNotification(uid, token, username, onResponseListener);
            }
        }
    }

    /**
     * 刷新用户（单独回调，有全局通知）
     *
     * @param onNetWorkListener
     */
    public void refreshUserDataWithNotification(OnResponseListener onNetWorkListener) {
        if (mRepository != null) {
            if (UserInfoModel.getInstance().isLogined()) {
                UserInfoVo.DataBean dataBean = UserInfoModel.getInstance().getUserInfo();
                int uid = dataBean.getUid();
                String username = dataBean.getUsername();
                String token = dataBean.getToken();
                mRepository.getUserInfoCallBack(uid, token, username, onNetWorkListener);
            }
        }
    }

    /**
     * 获取用户积分（已登录用户）
     *
     * @param onNetWorkListener
     */
    public void getUserIntegral(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getUserIntegral(onNetWorkListener);
        }
    }

    /**
     * 获取邀请信息
     *
     * @param tag
     */
    public void getShareData(String tag, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getShareData(tag, onNetWorkListener);
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
}
