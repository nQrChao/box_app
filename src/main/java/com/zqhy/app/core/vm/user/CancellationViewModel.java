package com.zqhy.app.core.vm.user;

import android.app.Application;
import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.repository.user.BindPhoneRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;

/**
 *
 * @author Administrator
 * @date 2018/11/12
 */

public class CancellationViewModel extends AbsViewModel<BindPhoneRepository> {

    public CancellationViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 检测用户帐号是否符合注销条件
     */
    public void userCancelCheck(int uid, String token,OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.userCancelCheck(uid, token, onNetWorkListener);
        }
    }

    /**
     * 通过用户身份获取验证码
     */
    public void getCodeByUser(int uid, String token,OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCodeByUser(uid, token, onNetWorkListener);
        }
    }

    /**
     * 验证验证码
     */
    public void checkCode(int uid, String token, String code, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.checkCode(uid, token, code, onNetWorkListener);
        }
    }

    /**
     * 通过验证码注销用户
     */
    public void userCancel(int uid, String token, String code, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.userCancel(uid, token, code, onNetWorkListener);
        }
    }
}
