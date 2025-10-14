package com.zqhy.app.core.vm.community;

import android.app.Application;
import androidx.annotation.NonNull;

import com.zqhy.app.core.data.repository.community.CommunityRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.vm.BaseViewModel;

/**
 * @author Administrator
 */
public class CommunityViewModel extends BaseViewModel<CommunityRepository> {

    public CommunityViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 获取用户主页数据接口
     *
     * @param uid
     * @param onNetWorkListener
     */
    public void getCommunityUserData(int uid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCommunityUserData(uid, onNetWorkListener);
        }

    }

    /**
     * 获取游戏足迹信息
     *
     * @param uid
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getUserGameFootprintData(int uid, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getUserGameFootprintData(uid, page, pageCount, onNetWorkListener);
        }
    }

    /**
     * 获取积分商城数据
     *
     * @param onNetWorkListener
     */
    public void getIntegralMallData(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getIntegralMallData(onNetWorkListener);
        }
    }

    /**
     * 应用场景: 1领取游戏优惠券; 2:商城页面当goods_type = {1,2,3} ;
     *
     * @param coupon_id
     * @param onNetWorkListener
     */
    public void exchangeCoupon(int coupon_id, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.exchangeCoupon(coupon_id, onNetWorkListener);
        }
    }

    /**
     * 积分商城:商品兑换
     *
     * @param product_id
     * @param onNetWorkListener
     */
    public void productExchange(int product_id, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.productExchange(product_id, onNetWorkListener);
        }
    }

    /**
     * 积分明细数据
     *
     * @param type              收支类型, 1:收入, 2:支出
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getIntegralListData(int type, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getIntegralListData(type, page, pageCount, onNetWorkListener);
        }
    }


    /**
     * 活跃数明细
     *
     * @param onNetWorkListener
     */
    public void getActValueListData(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getActValueListData(page, pageCount, onNetWorkListener);
        }
    }

    /**
     * 我的主页-评论列表
     *
     * @param onNetWorkListener
     */
    public void getCommentUserListV2(int user_id, int page, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCommentUserListV2(user_id, page, onNetWorkListener);
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
}
