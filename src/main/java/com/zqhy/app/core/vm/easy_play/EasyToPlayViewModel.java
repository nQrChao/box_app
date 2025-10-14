package com.zqhy.app.core.vm.easy_play;

import android.app.Application;

import androidx.annotation.NonNull;

import com.zqhy.app.core.data.repository.easy_play.EasyPlayRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.vm.BaseViewModel;

/**
 * @author Administrator
 */
public class EasyToPlayViewModel extends BaseViewModel<EasyPlayRepository> {

    public EasyToPlayViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 申请试玩
     *

     * @param onNetWorkListener
     */
    public void setRefundDetail(String gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.setRefundDetail(gameid, onNetWorkListener);
        }
    }
    public void setRefundDo(String gameid,String code, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.setRefundDo(gameid,code, onNetWorkListener);
        }
    }

    public void getCodeByUser1(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCodeByUser1(onNetWorkListener);
        }
    }
}
