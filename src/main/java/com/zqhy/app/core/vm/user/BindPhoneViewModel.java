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

public class BindPhoneViewModel extends AbsViewModel<BindPhoneRepository> {

    public BindPhoneViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * @param mobilePhone 11位手机号
     * @param isCheck     检测, 1:手机号未注册可以发送短信; 2: 手机号注册了才会发送短信
     */
    public void getCode(String mobilePhone, int isCheck,OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCode(mobilePhone, isCheck, onNetWorkListener);
        }
    }

    public void bindPhone(String mob, String verificationCode,OnNetWorkListener onNetWorkListener) {
        if(mRepository != null){
            mRepository.bindPhone(mob,verificationCode,onNetWorkListener);
        }
    }

    public void unBindPhone(String mob,String verificationCode,OnNetWorkListener onNetWorkListener) {
        if(mRepository != null){
            mRepository.unBindPhone(mob,verificationCode,onNetWorkListener);
        }
    }
}
