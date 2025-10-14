package com.zqhy.app.core.vm.tryplay;

import android.app.Application;
import androidx.annotation.NonNull;

import com.zqhy.app.core.data.repository.tryplay.TryGameRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.vm.BaseViewModel;

import java.util.List;

/**
 * @author Administrator
 */
public class TryGameViewModel extends BaseViewModel<TryGameRepository> {

    public TryGameViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 试玩列表
     *
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getTryGameListData(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTryGameListData(page, pageCount, onNetWorkListener);
        }
    }

    /**
     * 试玩详情数据接口
     *
     * @param tid
     * @param onNetWorkListener
     */
    public void getTryGameDetailData(int tid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTryGameDetailData(tid, onNetWorkListener);
        }
    }

    /**
     * 奖励播报
     *
     * @param onNetWorkListener
     */
    public void getTryGameRewardNoticeData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTryGameRewardNoticeData(onNetWorkListener);
        }
    }

    /**
     * 我的试玩
     *
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getUserTryGameListData(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getUserTryGameListData(page, pageCount, onNetWorkListener);
        }
    }

    /**
     * 领取奖励
     *
     * @param tid
     * @param ttids             任务ID集合. 多个用英文逗号(,)分割 例如: 9,10,11
     * @param onNetWorkListener
     */
    public void getTryGameReward(int tid, List<Integer> ttids, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTryGameReward(tid, ttids, onNetWorkListener);
        }
    }

    /**
     * 领取奖励
     *
     * @param task_id
     * @param onNetWorkListener
     */
    public void getTryGameReward(int task_id, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTryGameReward(task_id, onNetWorkListener);
        }
    }

    /**
     * 申请试玩
     *
     * @param tid
     * @param onNetWorkListener
     */
    public void setJoinTryGame(int tid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.setJoinTryGame(tid, onNetWorkListener);
        }
    }
}
