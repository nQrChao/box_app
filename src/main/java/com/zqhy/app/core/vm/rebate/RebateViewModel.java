package com.zqhy.app.core.vm.rebate;

import android.app.Application;
import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.repository.rebate.RebateRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;

import java.util.Map;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class RebateViewModel extends AbsViewModel<RebateRepository> {

    public RebateViewModel(@NonNull Application application) {
        super(application);
    }

    public void getRebateListData(int rebate_type, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getRebateListData(rebate_type, onNetWorkListener);
        }
    }

    public void getRebateRecordListData(int rebate_type, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getRebateRecordListData(rebate_type, page, pageCount, onNetWorkListener);
        }
    }

    public void getRebateItemDetail(String apply_id, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getRebateItemDetail(apply_id, onNetWorkListener);
        }
    }

    public void getRebateRevokeRemark(String apply_id, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getRebateRevokeRemark(apply_id, onNetWorkListener);
        }
    }

    public void recallRebateApply(String apply_id, String rmk_id, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.recallRebateApply(apply_id, rmk_id, onNetWorkListener);
        }
    }


    public void rebateApply(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.rebateApply(params, onNetWorkListener);
        }
    }

    public void getRebateServerData(int gameid, String xh_username, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getRebateServerHistory(gameid, xh_username, onNetWorkListener);
        }
    }
}
