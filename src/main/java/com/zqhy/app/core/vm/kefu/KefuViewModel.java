package com.zqhy.app.core.vm.kefu;

import android.app.Application;
import androidx.annotation.NonNull;

import com.zqhy.app.core.data.repository.kefu.KefuRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.vm.BaseViewModel;

/**
 * @author Administrator
 * @date 2018/11/22
 */

public class KefuViewModel extends BaseViewModel<KefuRepository> {
    public KefuViewModel(@NonNull Application application) {
        super(application);
    }

    public void getKefuInfo(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getKefuInfo(onNetWorkListener);
        }
    }

    public void getVipKefuInfo(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getVipKefuInfo(onNetWorkListener);
        }
    }

    public void getKefuList(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getKefuList(onNetWorkListener);
        }
    }

    public void evaluateKefu(String kid, int type, String remark, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.evaluateKefu(kid, type, remark, onNetWorkListener);
        }
    }

    /**
     * 客服问题接口
     *
     * @param onNetWorkListener
     */
    public void getKefuQuestionInfo(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getKefuQuestionInfo(onNetWorkListener);
        }
    }

    /**
     * 分享
     *
     * @param tag
     */
    public void getShareInviteData(String tag, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getShareData(tag, onNetWorkListener);
        }
    }
    /**
     * 获取用户主页数据接口
     * @param uid
     * @param onNetWorkListener
     */
    public void getCommunityUserData(int uid, OnNetWorkListener onNetWorkListener) {
        if(mRepository != null){
            mRepository.getCommunityUserData(uid,onNetWorkListener);
        }

    }

    /**
     * 获取用户代金券具体数量
     * @param onNetWorkListener
     */
    public void getUserVoucherCount(OnNetWorkListener onNetWorkListener){
        if(mRepository != null){
            mRepository.getUserVoucherCount(onNetWorkListener);
        }
    }


    public void getKefuMessageData(String tag, int maxID,OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getKefuMessageData(tag, maxID,onNetWorkListener);
        }
    }

    /**
     * 获取用户VIP信息
     * @param onNetWorkListener
     */
    public void getUserVipInfo(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getVipInfo(onNetWorkListener);
        }
    }

    public void getKefuMessageData(int maxID, OnNetWorkListener onNetWorkListener) {
        if(mRepository != null){
            mRepository.getKefuMessageData(maxID,onNetWorkListener);
        }
    }

    public void getDynamicGameMessageData(long logTime,OnNetWorkListener onNetWorkListener) {
        if(mRepository != null){
            mRepository.getDynamicGameMessageData(logTime,onNetWorkListener);
        }
    }

    /**
     * 获取省钱卡信息
     *
     * @param onNetWorkListener
     */
    public void getVipMemberInfo(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getVipMemberInfo(onNetWorkListener);
        }
    }

    /**
     * 兑换码兑换
     */
    public void redeemCode(String card_str, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.redeemCode(card_str, onNetWorkListener);
        }
    }

    /**
     * 获取广告配置信息
     */
    public void getAdSwiperList(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getAdSwiperList(onNetWorkListener);
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

    public void getMyFavouriteGameData(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if(mRepository != null){
            mRepository.getMyFavouriteGameData(page,pageCount,onNetWorkListener);
        }
    }

    public void setGameUnFavorite(int gameid, OnNetWorkListener onNetWorkListener) {
        if(mRepository != null){
            mRepository.setGameUnFavorite(gameid,onNetWorkListener);
        }
    }

    /**
     * 预约列表数据
     *
     * @param onNetWorkListener
     */
    public void getNewGameAppointmentGameList(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getNewGameAppointmentGameList(page, pageCount, onNetWorkListener);
        }
    }

    /**
     * 预约或取消预约接口
     *
     * @param gameid
     * @param onNetWorkListener
     */
    public void gameAppointment(int gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.gameAppointment(gameid, onNetWorkListener);
        }
    }
}
