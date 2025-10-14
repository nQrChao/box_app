package com.zqhy.app.core.vm.transfer;

import android.app.Application;
import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.repository.transfer.TransferRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class TransferViewModel extends AbsViewModel<TransferRepository> {

    public TransferViewModel(@NonNull Application application) {
        super(application);
    }


    public void getTransferGameMainData(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTransferGameMainData(page, pageCount, onNetWorkListener);
        }
    }

    public void getTransferInfoData(int gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTransferInfoData(gameid, onNetWorkListener);
        }
    }

    public void getTransferRewardInfoData(String id, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTransferRewardInfoData(id, onNetWorkListener);
        }
    }

    public void applyTransferReward(String index_id, String servername, String rolename,
                                    String role_id, String xh_username, String xh_uid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.applyTransferReward(index_id, servername, rolename, role_id, xh_username, xh_uid, onNetWorkListener);
        }
    }

    public void getTransferListData(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTransferListData(page, pageCount, onNetWorkListener);
        }
    }

    public void getTransferRecordData(String index_id, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTransferRecordData(index_id, onNetWorkListener);
        }
    }
}
