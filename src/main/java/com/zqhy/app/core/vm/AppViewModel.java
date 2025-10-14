package com.zqhy.app.core.vm;

import android.app.Application;
import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.repository.AppRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;

/**
 *
 * @author Administrator
 * @date 2018/11/28
 */

public class AppViewModel extends AbsViewModel<AppRepository>{

    public AppViewModel(@NonNull Application application) {
        super(application);
    }

    public void getAppVersion(OnNetWorkListener onNetWorkListener){
        if(mRepository != null){
            mRepository.getAppVersion(onNetWorkListener);
        }
    }
}
