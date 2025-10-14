package com.zqhy.app.core.vm.splash;

import android.app.Application;
import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.repository.splash.SplashRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.network.listener.NetworkPollingListener;

import java.util.Map;


public class SplashViewModel extends AbsViewModel<SplashRepository>{

    public SplashViewModel(@NonNull Application application) {
        super(application);
    }

    
    /**
     * 获取网络数据
     */
    public void getNetWorkData() {
        if(mRepository != null){
            mRepository.getNetWorkData();
        }
    }

    /**
     * 轮询地址
     */
    public void pollingUrls(NetworkPollingListener networkPollingListener){
        if(mRepository != null){
            mRepository.pollingUrls(networkPollingListener);
        }
    }

    /**
     * 应用市场审核初始化
     */
    public void getMarketInit(OnNetWorkListener onNetWorkListener){
        if(mRepository != null){
            mRepository.getMarketInit(onNetWorkListener);
        }
    }

    public void getMarketInfo(OnNetWorkListener onNetWorkListener){
        if(mRepository != null){
            mRepository.getMarketInfo(onNetWorkListener);
        }
    }

    /**
     * 设置point
     */
    public void setPoint(String api_point, Map<String,String> params){
        if(mRepository != null){
            mRepository.setPoint(api_point,params);
        }
    }

    public void getNewInit(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getNewInit(onNetWorkListener);
        }
    }

    public void sendAdActive(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.sendAdActive(onNetWorkListener);
        }
    }

    public void getRefundGames(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getRefundGames(onNetWorkListener);
        }
    }

    public void getStatus(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getStatus(onNetWorkListener);
        }
    }
}
