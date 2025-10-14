package com.zqhy.app.core.vm.recycle;

import android.app.Application;
import androidx.annotation.NonNull;

import com.zqhy.app.core.data.repository.recycle.RecycleRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.vm.BaseViewModel;

/**
 * @author Administrator
 */
public class RecycleViewModel extends BaseViewModel<RecycleRepository> {

    public RecycleViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 获取短信验证码
     *
     * @param gameid
     * @param onNetWorkListener
     */
    public void getRecycleCode(int gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getRecycleCode(gameid, onNetWorkListener);
        }
    }

    /**
     * 可回收小号数据列表
     *
     * @param gameid            0 代表全部游戏， >0 单个游戏
     * @param onNetWorkListener
     */
    public void getRecycleXhList(int gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getRecycleXhList(gameid, onNetWorkListener);
        }
    }

    /**
     * 可回收小号数据列表
     *
     * @param onNetWorkListener
     */
    public void getRecycleNewXhList(int can, String order, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getRecycleNewXhList(can, order, page, pageCount, onNetWorkListener);
        }
    }


    /**
     * 小号回收提交
     *
     * @param xh_username
     * @param code
     * @param onNetWorkListener
     */
    public void xhRecycleAction(String xh_username, String code, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.xhRecycleAction(xh_username, code, onNetWorkListener);
        }
    }

    /**
     * 回收小号记录列表
     *
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getXhRecycleRecord(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getXhRecycleRecord(page, pageCount, onNetWorkListener);
        }
    }

    /**
     * 回收小号赎回
     *
     * @param xh_username
     * @param onNetWorkListener
     */
    public void xhRecycleRansom(String xh_username, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.xhRecycleRansom(xh_username, onNetWorkListener);
        }
    }

    /**
     * 回收小号赎回
     *
     * @param rid               回收记录删除
     * @param onNetWorkListener
     */
    public void xhRecycleDelete(String rid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.xhRecycleDelete(rid, onNetWorkListener);
        }
    }

    /**
     * 回收可得到的优惠券数据接口
     *
     * @param xh_username
     * @param onNetWorkListener
     */
    public void getRecycleCouponList(String xh_username, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getRecycleCouponList(xh_username, onNetWorkListener);
        }
    }

}
