package com.zqhy.app.core.view.setting;

import android.app.Application;
import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.repository.AppRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.listener.NetworkPollingListener;

/**
 *
 * @author Administrator
 * @date 2018/11/14
 */

public class SettingViewModel extends AbsViewModel<AppRepository>{
    public SettingViewModel(@NonNull Application application) {
        super(application);
    }


    public void polling(NetworkPollingListener networkPollingListener){
        if(mRepository != null){
            mRepository.pollingUrls(networkPollingListener);
        }
    }

    public void getAppVersion(OnNetWorkListener onNetWorkListener){
        if(mRepository != null){
            mRepository.getAppVersion(onNetWorkListener);
        }
    }

    public void getWxPayPulg(OnNetWorkListener onNetWorkListener){
        if(mRepository != null){
            mRepository.getWxPayPulg(onNetWorkListener);
        }
    }
}
