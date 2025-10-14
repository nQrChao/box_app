package com.zqhy.app.core.vm.user;

import android.app.Application;
import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.repository.user.ModifyPasswordRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;

/**
 *
 * @author Administrator
 * @date 2018/11/12
 */

public class ModifyPasswordViewModel extends AbsViewModel<ModifyPasswordRepository>{
    public ModifyPasswordViewModel(@NonNull Application application) {
        super(application);
    }


    public void getVerificationCode(String mobile, int isCheck, OnNetWorkListener onNetWorkListener) {
        if(mRepository != null){
            mRepository.getCode(mobile,isCheck,onNetWorkListener);
        }
    }

    public void modifyLoginPassword(String mobile,String code, String newpwd,OnNetWorkListener onNetWorkListener) {
        if(mRepository != null){
            mRepository.modifyLoginPassword(mobile,code,newpwd,onNetWorkListener);
        }
    }
}
