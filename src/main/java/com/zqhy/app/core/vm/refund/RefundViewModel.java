package com.zqhy.app.core.vm.refund;

import android.app.Application;
import androidx.annotation.NonNull;

import com.zqhy.app.core.data.repository.refund.RefundRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.vm.BaseViewModel;

/**
 * @author leeham2734
 * @date 2020/5/11-9:19
 * @description
 */
public class RefundViewModel extends BaseViewModel<RefundRepository> {

    public RefundViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 退款游戏列表接口
     *
     * @param onNetWorkListener
     */
    public void getRefundGameList(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getRefundGameList(onNetWorkListener);
        }
    }

    /**
     * 退款订单列表
     *
     * @param gameid
     * @param onNetWorkListener
     */
    public void getRefundOrdersById(int gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getRefundOrdersById(gameid, onNetWorkListener);
        }
    }

    /**
     * 退款接口
     *
     * @param gameid
     * @param ids
     * @param remark
     * @param onNetWorkListener
     */
    public void refundOrder(int gameid, String ids, String remark, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.refundOrder(gameid, ids, remark, onNetWorkListener);
        }
    }


    /**
     * 退款记录接口
     *
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getRefundRecordList(int page,int pageCount,OnNetWorkListener onNetWorkListener){
        if (mRepository != null) {
            mRepository.getRefundRecordList(page, pageCount, onNetWorkListener);
        }

    }
}
