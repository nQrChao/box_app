package com.zqhy.app.core.vm.user.welfare;

import android.app.Application;
import androidx.annotation.NonNull;

import com.mvvm.base.AbsViewModel;
import com.zqhy.app.core.data.repository.user.welfare.MyCouponsListRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class MyCouponsListViewModel extends AbsViewModel<MyCouponsListRepository> {

    public MyCouponsListViewModel(@NonNull Application application) {
        super(application);
    }

    public void getCouponByCode(String code, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCouponByCode(code, onNetWorkListener);
        }
    }

    public void getMyCouponListData(int page, int pageCount, String coupon_type, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getMyCouponListData(page, pageCount, coupon_type, onNetWorkListener);
        }
    }

    public void getCouponRecordStat(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCouponRecordStat(onNetWorkListener);
        }
    }
}
