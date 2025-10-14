package com.zqhy.app.core.vm.community.task;


import android.app.Application;
import androidx.annotation.NonNull;

import com.zqhy.app.core.data.repository.community.task.TaskViewRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.vm.BaseViewModel;

/**
 * @author Administrator
 */
public class TaskViewModel extends BaseViewModel<TaskViewRepository> {

    public TaskViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 获取任务状态
     *
     * @param onNetWorkListener
     */
    public void getTaskStatus(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTaskStatus(onNetWorkListener);
        }

    }


    /**
     * 获取用户签到信息
     *
     * @param onNetWorkListener
     */
    public void getSignData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getSignData(onNetWorkListener);
        }
    }

    /**
     * 获取用户签到信息
     *
     * @param onNetWorkListener
     */
    public void getSignDataV2(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getSignDataV2(onNetWorkListener);
        }
    }


    /**
     * 用户签到
     *
     * @param onNetWorkListener
     */
    public void userSign(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.userSign(onNetWorkListener);
        }
    }

    /**
     * 用户签到
     *
     * @param onNetWorkListener
     */
    public void userSignV2(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.userSignV2(onNetWorkListener);
        }
    }


    /**
     * 获取新手任务信息
     *
     * @param onNetWorkListener
     */
    public void getTaskNewData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTaskNewData(onNetWorkListener);
        }
    }

    /**
     * 领取任务积分奖励
     *
     * @param tid
     * @param onNetWorkListener
     */
    public void getTaskReward(int tid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTaskReward(tid, onNetWorkListener);
        }
    }

    /**
     * 领取了解任务积分奖励
     *
     * @param tid
     * @param onNetWorkListener
     */
    public void getKnowTaskReward(int tid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getKnowTaskReward(tid, onNetWorkListener);
        }
    }


    /**
     * 获取用户VIP信息
     *
     * @param onNetWorkListener
     */
    public void getUserVipInfo(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getVipInfo(onNetWorkListener);
        }
    }

    /**
     * 获取每日任务列表
     *
     * @param onNetWorkListener
     */
    public void getCommunityDayTaskList(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCommunityDayTaskList(onNetWorkListener);
        }
    }

    /**
     * 领取每日任务奖励
     *
     * @param onNetWorkListener
     */
    public void communityDayGetReward(String id, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.communityDayGetReward(id, onNetWorkListener);
        }
    }
}
