package com.zqhy.app.network;


import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.BaseResponseVo;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author Administrator
 * @date 2018/11/2
 */

public interface IApiService {


    @POST("/index.php/Lhhapp")
    @FormUrlEncoded
    Flowable<BaseResponseVo> postOldClaimData(@Field("data") String data);


    @POST("/index.php/Lhhapp")
    @FormUrlEncoded
    Observable<BaseResponseVo> postOldClaimData2(@Field("data") String data);

    /**
     * Flowable 用法 单个接口用
     *
     * @param api
     * @param data
     * @return
     */
    @POST("/index.php/App/index")
    @FormUrlEncoded
    Flowable<BaseResponseVo> postClaimData(@Query(value = "api") String api, @Field("data") String data);

    /**
     * Flowable 用法 单个接口用
     *
     * @param data
     * @return
     */
    @POST("/index.php/App/index")
    @FormUrlEncoded
    Flowable<BaseResponseVo> postClaimData(@Field("data") String data);

    /**
     * Flowable 用法 单个接口用（上传图片）
     *
     * @param api
     * @param data
     * @param parts
     * @return
     */
    @POST("/index.php/App/index")
    @Multipart
    Flowable<BaseResponseVo> postClaimDataWithPic(@Query(value = "api") String api,@Part("data") RequestBody data, @Part() List<MultipartBody.Part> parts);


    /**
     * Flowable 用法 单个接口用（上传图片）
     *
     * @param data
     * @param parts
     * @return
     */
    @POST("/index.php/App/index")
    @Multipart
    Flowable<BaseResponseVo> postClaimDataWithPic(@Part("data") RequestBody data, @Part() List<MultipartBody.Part> parts);

    /**
     * Observable 用法 接口合并时用到
     *
     * @param api
     * @param data
     * @return
     */
    @POST("/index.php/App/index")
    @FormUrlEncoded
    Observable<BaseResponseVo> postClaimData2(@Query(value = "api") String api,@Field("data") String data);

    /**
     * Observable 用法 接口合并时用到
     *
     * @param data
     * @return
     */
    @POST("/index.php/App/index")
    @FormUrlEncoded
    Observable<BaseResponseVo> postClaimData2(@Field("data") String data);




    /**
     *  话题类数据
     *
     * @param data
     * @return
     */
    @POST("/forum.topic/category")
    @FormUrlEncoded
    Flowable<BaseResponseVo> categoryData(@Field("data") String data);

    /**
     *  论坛广告banner数据
     *
     * @param data
     * @return
     */
    @POST("/forum.topic/advert")
    @FormUrlEncoded
    Flowable<BaseResponseVo> topicAdvert(@Field("data") String data);

    /**
     *  发布话题
     *
     * @param data
     * @return
     */
    @POST("/forum.topic/release")
    @FormUrlEncoded
    Flowable<BaseResponseVo> categoryPush(@Field("data") String data);


    /**
     * 论坛上传图片(单张)
     *
     * pic	file	是	图片
     * flag	string	是	图片标记
     * @param data
     * @return
     */
    @POST("/forum.image/upload")
    @Multipart
    Flowable<BaseResponseVo> imageUpload(@Part("flag") RequestBody flag,@Part("data") String data, @Part MultipartBody.Part parts);
    /**
     *  游戏信息
     *
     * @param data
     * @return
     */
    @POST("/forum.topic/game")
    @FormUrlEncoded
    Flowable<BaseResponseVo> forumDetailGame(@Field("data") String data);

    /**
     *  话题列表
     *
     * @param data
     * @return
     */
    @POST("/forum.topic/list")
    @FormUrlEncoded
    Flowable<BaseResponseVo> forumList(@Field("data") String data);
    /**
     *  修改话题
     *
     * @param data
     * @return
     */
    @POST("/forum.topic/edit")
    @FormUrlEncoded
    Flowable<BaseResponseVo> categoryEdit(@Field("data") String data);
    /**
     *  话题详情
     *
     * @param data
     * @return
     */
    @POST("/forum.topic/detail")
    @FormUrlEncoded
    Flowable<BaseResponseVo> forumDetailList(@Field("data") String data);

    /**
     *  话题点赞
     *
     * @param data
     * @return
     */
    @POST("/forum.topic/like")
    @FormUrlEncoded
    Flowable<BaseResponseVo> forumReplyTopLike(@Field("data") String data);

    /**
     *  话题点赞
     *
     * @param data
     * @return
     */
    @POST("/forum.reply/like")
    @FormUrlEncoded
    Flowable<BaseResponseVo> forumReplyLike(@Field("data") String data);

    /**
     *  发布二级回复
     *
     * @param data
     * @return
     */
    @POST("/forum.reply/release")
    @FormUrlEncoded
    Flowable<BaseResponseVo> forumReplyRelease(@Field("data") String data);

    /**
     *  话题回复
     *
     * @param data
     * @return
     */
    @POST("/forum.topic/replay")
    @FormUrlEncoded
    Flowable<BaseResponseVo> forumReply(@Field("data") String data);

    /**
     *  话题一级评论
     *
     * @param data
     * @return
     */
    @POST("/forum.reply/top_list")
    @FormUrlEncoded
    Flowable<BaseResponseVo> forumReplyTopList(@Field("data") String data);

    /**
     *  二级回复列表
     *
     * @param data
     * @return
     */
    @POST("/forum.reply/sub_list")
    @FormUrlEncoded
    Flowable<BaseResponseVo> forumReplySubList(@Field("data") String data);


    /**
     *  置顶话题列表
     *
     * @param data
     * @return
     */
    @POST("/forum.topic/sticky")
    @FormUrlEncoded
    Flowable<BaseResponseVo> stickyList(@Field("data") String data);

    /**
     *  举报
     *
     * @param data
     * @return
     */
    @POST("/forum.topic/report")
    @FormUrlEncoded
    Flowable<BaseResponseVo> forumReport(@Field("data") String data);
    /**
     *  举报 回复
     *
     * @param data
     * @return
     */
    @POST("/forum.reply/report")
    @FormUrlEncoded
    Flowable<BaseResponseVo> forumReplyReport(@Field("data") String data);

    //交易模块 开始

    /**
     * 商品列表
     * @param data
     * @return
     */
    @POST("/trade/goods_list")
    @FormUrlEncoded
    Flowable<BaseResponseVo> tradeGoodsList(@Field("data") String data);

    /**
     * 商品详情
     * @param data
     * @return
     */
    @POST("/trade/goods_info")
    @FormUrlEncoded
    Flowable<BaseResponseVo> tradeGoodsInfo(@Field("data") String data);

    /**
     * 搜索页面
     * @param data
     * @return
     */
    @POST("/trade/search_page")
    @FormUrlEncoded
    Flowable<BaseResponseVo> tradeSearchPage(@Field("data") String data);

    /**
     * 新增商品
     *
     * @param data
     * @param parts
     * @return
     */
    @POST("/trade/goods_add")
    @Multipart
    Flowable<BaseResponseVo> tradeGoodsAdd( @Part("data") RequestBody data, @Part() List<MultipartBody.Part> parts);

    /**
     * 修改商品
     *
     * @param data
     * @param parts
     * @return
     */
    @POST("/trade/goods_edit")
    @Multipart
    Flowable<BaseResponseVo> tradeGoodsEdit( @Part("data") RequestBody data, @Part() List<MultipartBody.Part> parts);

    /**
     * 商品改价
     * @param data
     * @return
     */
    @POST("/trade/price_adjustment")
    @FormUrlEncoded
    Flowable<BaseResponseVo> tradePriceAdjustment(@Field("data") String data);

    /**
     * 获取小号信息
     * @param data
     * @return
     */
    @POST("/trade/xh_info")
    @FormUrlEncoded
    Flowable<BaseResponseVo> tradeXhInfo(@Field("data") String data);

    /**
     * 商品改价
     * @param data
     * @return
     */
    @POST("/trade/goods_off")
    @FormUrlEncoded
    Flowable<BaseResponseVo> tradeGoodsOff(@Field("data") String data);


    /**
     * 购买
     * @param data
     * @return
     */
    @POST("/trade/purchase")
    @FormUrlEncoded
    Flowable<BaseResponseVo> tradePurchase(@Field("data") String data);

    /**
     * 删除商品[记录]
     * @param data
     * @return
     */
    @POST("/trade/goods_del")
    @FormUrlEncoded
    Flowable<BaseResponseVo> tradeGoodsGel(@Field("data") String data);

    /**
     * 收藏[取消收藏]
     * @param data
     * @return
     */
    @POST("/trade/collect")
    @FormUrlEncoded
    Flowable<BaseResponseVo> tradeCollect(@Field("data") String data);

    /**
     * 收藏列表
     * @param data
     * @return
     */
    @POST("/trade/collect_list")
    @FormUrlEncoded
    Flowable<BaseResponseVo> tradeCollectList(@Field("data") String data);

    /**
     * 砍价
     * @param data
     * @return
     */
    @POST("/trade/bargain")
    @FormUrlEncoded
    Flowable<BaseResponseVo> tradeBargain(@Field("data") String data);

    /**
     * 卖号详情
     * 获取手续费
     * @param data
     * @return
     */
    @POST("/trade/config")
    @FormUrlEncoded
    Flowable<BaseResponseVo> tradeConfig(@Field("data") String data);


    /**
     * 待支付
     * @param data
     * @return
     */
    @POST("/trade/unpaid_list")
    @FormUrlEncoded
    Flowable<BaseResponseVo> tradeUnpaidList(@Field("data") String data);

    /**
     * 取消支付
     * @param data
     * @return
     */
    @POST("/trade/cancel_purchase")
    @FormUrlEncoded
    Flowable<BaseResponseVo> tradeCancelPurchase(@Field("data") String data);

    /**
     * 小号列表
     * @param data
     * @return
     */
    @POST("/trade/xh_list")
    @FormUrlEncoded
    Flowable<BaseResponseVo> tradeXHList(@Field("data") String data);

    /**
     * 小号列表
     * @param data
     * @return
     */
    @POST("/trade/get_code")
    @FormUrlEncoded
    Flowable<BaseResponseVo> tradeGetCode(@Field("data") String data);
    //交易模块 结束

    /**
     *  我的徽章
     *
     * @param data
     * @return
     */
    @POST("/forum.badge/my")
    @FormUrlEncoded
    Flowable<BaseResponseVo> myBadge(@Field("data") String data);

    /**
     *  我的可展示徽章
     *
     * @param data
     * @return
     */
    @POST("/forum.badge/show_info")
    @FormUrlEncoded
    Flowable<BaseResponseVo> showInfo(@Field("data") String data);

    /**
     *  设置徽章展示
     *
     * @param data
     * @return
     */
    @POST("/forum.badge/set_show")
    @FormUrlEncoded
    Flowable<BaseResponseVo> setShow(@Field("data") String data);

    /**
     *  设置徽章佩戴
     *
     * @param data
     * @return
     */
    @POST("/forum.badge/set_wear")
    @FormUrlEncoded
    Flowable<BaseResponseVo> setWear(@Field("data") String data);

    /**
     *  徽章介绍
     *
     * @param data
     * @return
     */
    @POST("/forum.badge/dec")
    @FormUrlEncoded
    Flowable<BaseResponseVo> getBadgeDec(@Field("data") String data);

    /**
     *  徽章详情
     *
     * @param data
     * @return
     */
    @POST("/forum.badge/info")
    @FormUrlEncoded
    Flowable<BaseResponseVo> badgeInfo(@Field("data") String data);

    /**
     *  游戏角色列表
     *
     * @param data
     * @return
     */
    @POST("/chat.role/role_list")
    @FormUrlEncoded
    Flowable<BaseResponseVo> chatRoleList(@Field("data") String data);

    /**
     *  游戏角色列表
     *
     * @param data
     * @return
     */
    @POST("/chat.role/role_binding")
    @FormUrlEncoded
    Flowable<BaseResponseVo> roleBinding(@Field("data") String data);

    /**
     *  获取用户游戏角色列表
     *
     * @param data
     * @return
     */
    @POST("/chat.role/role_info")
    @FormUrlEncoded
    Flowable<BaseResponseVo> roleInfo(@Field("data") String data);

    /**
     *  设置用户游戏角色是否展示
     *
     * @param data
     * @return
     */
    @POST("/chat.role/role_show")
    @FormUrlEncoded
    Flowable<BaseResponseVo> roleShow(@Field("data") String data);

    /**
     *  举报
     *
     * @param data
     * @return
     */
    @POST("/chat.msg/report")
    @FormUrlEncoded
    Flowable<BaseResponseVo> chatReport(@Field("data") String data);

    /**
     *  某用户的帖子
     *
     * @param data
     * @return
     */
    @POST("/forum.asset/topic")
    @FormUrlEncoded
    Flowable<BaseResponseVo> myTopic(@Field("data") String data);

    /**
     *  某用户点赞的帖子
     *
     * @param data
     * @return
     */
    @POST("/forum.asset/like")
    @FormUrlEncoded
    Flowable<BaseResponseVo> likeList(@Field("data") String data);

    /**
     *  获取用户信息
     *
     * @param data
     * @return
     */
    @POST("/forum.asset/user_center")
    @FormUrlEncoded
    Flowable<BaseResponseVo> getUserCenter(@Field("data") String data);
}

