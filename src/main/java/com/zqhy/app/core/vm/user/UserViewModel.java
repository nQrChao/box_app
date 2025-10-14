package com.zqhy.app.core.vm.user;

import android.app.Application;
import androidx.annotation.NonNull;

import com.zqhy.app.core.data.repository.user.UserRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.vm.BaseViewModel;
import com.zqhy.app.network.listener.NetworkPollingListener;

import java.io.File;

/**
 * @author Administrator
 * @date 2018/11/19
 */

public class UserViewModel extends BaseViewModel<UserRepository> {

    public UserViewModel(@NonNull Application application) {
        super(application);
    }


    public void getCurrencyListData(int type, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCurrencyListData(type, onNetWorkListener);
        }
    }

    /**
     * 用户修改昵称
     *
     * @param nickname
     * @param onNetWorkListener
     */
    public void modifyNickName(String nickname, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.modifyNickName(nickname, onNetWorkListener);
        }
    }

    /**
     * 用户上传头像
     *
     * @param localPathFile
     * @param onNetWorkListener
     */
    public void uploadUserIcon(File localPathFile, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.uploadUserIcon(localPathFile, onNetWorkListener);
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
     * vip成长值明细
     *
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getVipInfoDetail(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getVipInfoDetail(page, pageCount, onNetWorkListener);
        }
    }

    /**
     * 纪念日礼包详情
     *
     * @param onNetWorkListener
     */
    public void getMemoryRewardInfo(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getMemoryRewardInfo(onNetWorkListener);
        }
    }


    /**
     * VIP每月奖励详情
     *
     * @param onNetWorkListener
     */
    public void getMonthRewardInfo(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getMonthRewardInfo(onNetWorkListener);
        }
    }

    /**
     * VIP升级奖励详情
     *
     * @param onNetWorkListener
     */
    public void getUpgradeRewardInfo(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getUpgradeRewardInfo(onNetWorkListener);
        }
    }

    /**
     * VIP生日奖励详情
     *
     * @param onNetWorkListener
     */
    public void getBirthdayRewardInfo(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getBirthdayRewardInfo(onNetWorkListener);
        }
    }

    /**
     * VIP节日奖励详情
     *
     * @param onNetWorkListener
     */
    public void getHolidayRewardInfo(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getHolidayRewardInfo(onNetWorkListener);
        }
    }

    /**
     * 领取VIP特权礼包
     *
     * @param type
     * @param onNetWorkListener
     */
    public void getReceiveVipReward(String type, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getReceiveVipReward(type,onNetWorkListener);
        }
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


    /**
     * 获取页面基本信息
     */
    public void getSuperUserInfo(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getSuperUserInfo(onNetWorkListener);
        }
    }

    /**
     * 超级会员购买
     */
    public void buySuperUser(int card_type, int pay_type, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.buySuperUser(card_type, pay_type, onNetWorkListener);
        }
    }

    /**
     * 领取会员券
     */
    public void getCoupon(String coupon_id, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCoupon(coupon_id, onNetWorkListener);
        }
    }

    /**
     * 签到抽取奖励
     */
    public void signLuck(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.signLuck(onNetWorkListener);
        }
    }

    /**
     * 签到领取奖励
     */
    public void getReward(int reward_id, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getReward(reward_id, onNetWorkListener);
        }
    }

    /**
     * 兑换/领取每日奖励
     */
    public void rewardExchange(int reward_id, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.rewardExchange(reward_id, onNetWorkListener);
        }
    }

    /**
     * 购买每日奖励
     */
    public void rewardBuy(int reward_id, int pay_type, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.rewardBuy(reward_id, pay_type, onNetWorkListener);
        }
    }


    /**
     * 每日奖励一周预览
     */
    public void getSuperDayRewardList(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getSuperDayRewardList(onNetWorkListener);
        }
    }


    public void checkTransaction(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.checkTransaction(onNetWorkListener);
        }
    }

    /**
     * 用户是否可以解绑手机号
     */
    public void canUnbindMobile(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.canUnbindMobile(onNetWorkListener);
        }
    }

    /**
     * 获取会员专属游戏礼包列表/获取会员专属游戏代金券列表
     */
    public void getVipCouponListData(int type, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getVipCouponListData(type, page, pageCount, onNetWorkListener);
        }
    }

    /**
     * 购买每日神券
     */
    public void buyVoucher(String buy_id, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.buyVoucher(buy_id, onNetWorkListener);
        }
    }

    /**
     * 获取广告配置信息
     */
    public void getAdSwiperList(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getAdSwiperList1(onNetWorkListener);
        }
    }
}
