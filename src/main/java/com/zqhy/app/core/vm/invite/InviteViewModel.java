package com.zqhy.app.core.vm.invite;

import android.app.Application;
import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.repository.invite.InviteRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;

/**
 *
 * @author Administrator
 * @date 2018/11/23
 */

public class InviteViewModel extends AbsViewModel<InviteRepository>{
    public InviteViewModel(@NonNull Application application) {
        super(application);
    }


    public void getShareData(String tag, OnNetWorkListener onNetWorkListener){
        if(mRepository != null){
            mRepository.getShareData(tag, onNetWorkListener);
        }
    }

    public void addInviteShare(int type){
        if(mRepository != null){
            mRepository.addInviteShare(type);
        }
    }
}
