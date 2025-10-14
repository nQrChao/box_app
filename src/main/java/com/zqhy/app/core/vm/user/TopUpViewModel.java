package com.zqhy.app.core.vm.user;

import android.app.Application;
import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.repository.user.TopUpRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.model.UserInfoModel;

/**
 * @author Administrator
 * @date 2018/11/13
 */

public class TopUpViewModel extends AbsViewModel<TopUpRepository> {
    public TopUpViewModel(@NonNull Application application) {
        super(application);
    }

    public void doRecharge(String paytype, String amount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.doRecharge(paytype, amount,onNetWorkListener);
        }
    }

    public void realNameCheck(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.realNameCheck("gold",onNetWorkListener);
        }
    }

    public void refreshUserData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            if (UserInfoModel.getInstance().isLogined()) {
                UserInfoVo.DataBean dataBean = UserInfoModel.getInstance().getUserInfo();
                int uid = dataBean.getUid();
                String username = dataBean.getUsername();
                String token = dataBean.getToken();
                mRepository.getUserInfoWithoutNotification(uid, token, username,onNetWorkListener);
            }
        }
    }

    /**
     * 获取广告配置信息
     */
    public void payRebate(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.payRebate(onNetWorkListener);
        }
    }
}
