package com.zqhy.app.core.vm.user;

import android.app.Application;
import androidx.annotation.NonNull;

import com.zqhy.app.core.data.repository.user.VipMemberRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.inner.OnResponseListener;
import com.zqhy.app.core.vm.BaseViewModel;

/**
 * @author leeham
 * @date 2020/3/30-12:49
 * @description
 */
public class VipMemberViewModel extends BaseViewModel<VipMemberRepository> {

    public VipMemberViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 获取开通会员的信息
     *
     * @param onNetWorkListener
     */
    public void getVipMemberInfo(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getVipMemberInfo(onNetWorkListener);
        }
    }

    /**
     * 开通会员
     *
     * @param vip_member_type
     * @param vip_member_pay_type
     * @param onNetWorkListener
     */
    public void buyVipMember(int vip_member_type, int vip_member_pay_type, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.buyVipMember(vip_member_type, vip_member_pay_type, onNetWorkListener);
        }
    }

    /**
     * 会员用户领取无门卡代金券
     *
     * @param onNetWorkListener
     */
    public void getVipMemberVoucher(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getVipMemberVoucher(onNetWorkListener);
        }
    }


    /**
     * 获取会员购买类型
     *
     * @param onNetWorkListener
     */
    public void getVipTypes(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getVipTypes(onNetWorkListener);
        }
    }

    public void getUserInfoCallBack1(int uid, String token, String username, OnResponseListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getUserInfoCallBack(uid, token, username, onNetWorkListener);
        }
    }

    /**
     * 获取超级省钱卡基本信息
     *
     * @param onNetWorkListener
     */
    public void getMoneyCardBaseInfo(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getMoneyCardBaseInfo(onNetWorkListener);
        }
    }

    /**
     * 开通超级省钱卡
     */
    public void buySuperVipMember(int card_type, int pay_type, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.buySuperVipMember(card_type, pay_type, onNetWorkListener);
        }
    }

    /**
     * 超级省钱卡用户领取每日奖励
     *
     * @param onNetWorkListener
     */
    public void getCardReward(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCardReward(onNetWorkListener);
        }
    }

    /**
     * 购买折扣省钱卡
     */
    public void buyDiscountCard(int card_type, int pay_type, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.buyDiscountCard(card_type, pay_type, onNetWorkListener);
        }
    }

    /**
     * 折扣省钱卡用户领取每日奖励
     *
     * @param onNetWorkListener
     */
    public void getConpon(int discount_coupon_id, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getConpon(discount_coupon_id, onNetWorkListener);
        }
    }

    /**
     * 可用通用券游戏列表
     *
     * @param onNetWorkListener
     */
    public void getCanUsGameList(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCanUsGameList(onNetWorkListener);
        }
    }

    /**
     * 不可用通用券游戏列表
     *
     * @param onNetWorkListener
     */
    public void getCannotUsGameList(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCannotUsGameList(onNetWorkListener);
        }
    }

    /**
     * 省钱卡订单记录
     *
     * @param onNetWorkListener
     */
    public void getProvinceCardRecord(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.ProvinceCardRecord(onNetWorkListener);
        }
    }


    /**
     * 省钱卡订单记录
     *
     * @param onNetWorkListener
     */
    public void getProvinceCardRecordChange(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.ProvinceCardRecordChange(onNetWorkListener);
        }
    }

    /**
     * 省钱卡兑换代金券页面信息
     *
     * @param onNetWorkListener
     */
    public void getExchangeCouponInfo(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getExchangeCouponInfo(onNetWorkListener);
        }
    }

    /**
     * 兑换省钱卡代金券
     *
     * @param onNetWorkListener
     */
    public void exchangeCoupon(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.exchangeCoupon(onNetWorkListener);
        }
    }
}
