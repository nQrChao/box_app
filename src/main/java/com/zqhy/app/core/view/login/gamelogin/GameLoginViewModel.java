package com.zqhy.app.core.view.login.gamelogin;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.repository.login.LoginRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.view.main.dialog.AppPopDialogHelper;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.utils.AppManager;
import com.zqhy.sdk.db.SdkManager;
import com.zqhy.sdk.db.UserBean;

/**
 * @author Administrator
 */

public class GameLoginViewModel extends AbsViewModel<LoginRepository> {

    public GameLoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void login(String username, String password, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.login(username, password, onNetWorkListener);
        }
    }

    /**
     * @param mobilePhone 11位手机号
     * @param isCheck     检测, 1:手机号未注册可以发送短信; 2: 手机号注册了才会发送短信
     */
    public void getCode(String mobilePhone, int isCheck, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCode(mobilePhone, isCheck, onNetWorkListener);
        }
    }

    public void mobileRegister(String mobile, String code, String password, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.mobileRegister(mobile, code, password, onNetWorkListener);
        }
    }

    public void userNameRegister(String strUsername, String strPassword, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.userNameRegister(strUsername, strPassword, onNetWorkListener);
        }
    }

    public void resetLoginPassword(String mobilePhone, String code, String newpwd, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.resetLoginPassword(mobilePhone, code, newpwd, onNetWorkListener);
        }
    }


    /**
     * 保存用户信息
     *
     * @param dataBean
     */
    public void saveUserInfo(String loginAccount, String password, UserInfoVo.DataBean dataBean) {
        if (dataBean != null) {
            UserInfoModel.getInstance().saveLoggedAccount(loginAccount, password);
            saveAccountToSDK(loginAccount, dataBean.getPassword());
            //            if (mRepository != null) {
            //                mRepository.getUserInfo(dataBean.getUid(), dataBean.getToken(), dataBean.getUsername());
            //            }
            Activity _mActivity = AppManager.getInstance().getSubTopActivity();

            //app更名   懂游戏->巴兔游戏
            //mRepository.showAppChangeNameTipDialog(_mActivity, dataBean);
            //用户召回弹窗
            new AppPopDialogHelper(_mActivity, null).showUserRecallDialog(dataBean);
        }
    }


    /**
     * 保存用户到本地
     *
     * @param username
     * @param password
     */
    public void saveAccountToSDK(String username, String password) {
        SdkManager.getInstance().cacheUsers(new UserBean(username, password, System.currentTimeMillis()), "tsyule");
    }

    public void oneKeyLogin(String one_key_token, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.oneKeyLogin(one_key_token, onNetWorkListener);
        }
    }

    public void mobileAutoLogin(String mobile, String code, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.mobileAutoLogin(mobile, code, onNetWorkListener);
        }
    }

    public void bindPassword(String password, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.bindPassword(password, onNetWorkListener);
        }
    }
}
