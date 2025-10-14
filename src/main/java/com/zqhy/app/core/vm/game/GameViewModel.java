package com.zqhy.app.core.vm.game;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.zqhy.app.core.data.repository.game.GameRepository;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.vm.BaseViewModel;
import com.zqhy.app.network.utils.Base64;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/20
 */

public class GameViewModel extends BaseViewModel<GameRepository> {

    public GameViewModel(@NonNull Application application) {
        super(application);
    }

    public void getGameDetailInfo(int gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getGameDetailInfo(gameid, onNetWorkListener);
        }
    }

    public void getGameInfoPartBase(int gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getGameInfoPartBase(gameid, onNetWorkListener);
        }
    }

    public void getGameInfoPartFl(int gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getGameInfoPartFl(gameid, onNetWorkListener);
        }
    }

    public void setGameFavorite(int gameid, int type, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.setGameFavorite(gameid, type, onNetWorkListener);
        }
    }

    public void setGameUnFavorite(int gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.setGameUnFavorite(gameid, onNetWorkListener);
        }
    }

    public void getCardInfo(int gameid, int cardid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCardInfo(gameid, cardid, onNetWorkListener);
        }
    }

    public void getTaoCardInfo(int gameid, int cardid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTaoCardInfo(gameid, cardid, onNetWorkListener);
        }
    }

    public void getGameCouponListData(int gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getGameCouponListData(gameid, onNetWorkListener);
        }
    }

    public void getCoupon(int coupon_id, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCoupon(coupon_id, onNetWorkListener);
        }
    }

    public void getGameContainer(int container_id, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getGameContainer(container_id, onNetWorkListener);
        }
    }

    /**
     * 游戏详情页，交易列表数据
     *
     * @param gameid
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getTransactionListData(int gameid, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTransactionListData(gameid, page, pageCount, onNetWorkListener);
        }
    }

    /**
     * 游戏详情页，评论列表数据
     *
     * @param gameid
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getCommentListData(int gameid, String type_id,String sort,int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCommentListData(gameid, type_id,sort,page, pageCount, onNetWorkListener);
        }
    }

    /**
     * 游戏详情页，评论列表数据
     *
     * @param gameid
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getCommentListDataV2(int gameid, String comment_type, String type_id,String sort,int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCommentListDataV2(gameid, comment_type, type_id,sort,page, pageCount, onNetWorkListener);
        }
    }

    /**
     * 回复评论
     *
     * @param cid
     * @param content
     * @param rid
     * @param onNetWorkListener
     */
    public void setCommentReply(int cid, String content, String rid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.setCommentReply(cid, content, rid, onNetWorkListener);
        }
    }

    /**
     * 评论点赞
     *
     * @param cid
     * @param onNetWorkListener
     */
    public void setCommentLike(int cid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.setCommentLike(cid, onNetWorkListener);
        }
    }


    /**
     * 新游列表接口
     *
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getAppointmentGameList(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getAppointmentGameList(page, pageCount, onNetWorkListener);
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

    /**
     * 获取客服信息--QQ群
     *
     * @param onNetWorkListener
     */
    public void getKefuInfo(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getKefuInfo(onNetWorkListener);
        }
    }


    /**
     * 获取游戏排行榜数据
     *
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getGameRankingList(int game_type, int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getGameRankingList(game_type, page, pageCount, onNetWorkListener);
        }
    }


    /**
     * 设置point
     *
     * @param api_point
     * @param params
     */
    public void setPoint(String api_point, Map<String, String> params) {
        if (mRepository != null) {
            mRepository.setPoint(api_point, params);
        }
    }

    /**
     * 游戏礼包列表页面
     *
     * @param page
     * @param pageCount
     * @param onNetWorkListener
     */
    public void getGameCoupon(int page, int pageCount, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getGameCoupon(page, pageCount, onNetWorkListener);
        }
    }

    /**
     * 新游首发
     *
     * @param onNetWorkListener
     */
    public void getNewGameStartingList(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getNewGameStartingList(onNetWorkListener);
        }
    }

    /**
     * 一周新游top10
     *
     * @param onNetWorkListener
     */
    public void getNewGameTopList(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getNewGameTopList(onNetWorkListener);
        }
    }

    /**
     * 预约列表数据
     *
     * @param onNetWorkListener
     */
    public void getNewGameAppointmentGameList(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getNewGameAppointmentGameList(onNetWorkListener);
        }
    }

    /**
     * 获取点评分类
     *
     * @param onNetWorkListener
     */
    public void getCommentType(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCommentType(onNetWorkListener);
        }
    }

    /**
     * 获取点评分类
     *
     * @param onNetWorkListener
     */
    public void getCommentTypeV2(int gameid,OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCommentTypeV2(gameid, onNetWorkListener);
        }
    }


    /**
     * 获取游戏数据
     *
     * @param gameid
     * @param onNetWorkListener
     */
    public void getChannelGameData(int gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getChannelGameData(gameid, onNetWorkListener);
        }
    }

    /**
     * 获取优惠券列表数据
     *
     * @param gameid
     * @param onNetWorkListener
     */
    public void gameinfoPartCoupon(int gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.gameinfoPartCoupon(gameid, onNetWorkListener);
        }
    }

    /**
     * 新增投诉
     *
     * @param gameid
     * @param onNetWorkListener
     */
    public void addReport(int gameid, String platformName, String mobile, String discount, String downloadUrl, List<File> localPathList, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            Map<String, String> params = new TreeMap<>();

            params.put("api", "report_add");
            params.put("gameid", String.valueOf(gameid));
            params.put("platform_name", platformName);
            params.put("mobile", mobile);
            params.put("discount", discount);
            params.put("download_url", downloadUrl);

            Map<String, File> fileParams = new TreeMap<>();
            if (localPathList != null) {
                for (int i = 0; i < localPathList.size(); i++) {
                    fileParams.put("upload_pic" + (i + 1), localPathList.get(i));
                }
            }

            mRepository.addReport(params, fileParams, onNetWorkListener);
        }
    }

    /**
     * 用户投诉列表
     *
     * @param onNetWorkListener
     */
    public void reportList(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.reportList(onNetWorkListener);
        }
    }

    /**
     * 游戏反馈页面信息
     *
     * @param onNetWorkListener
     */
    public void gameFeedbackInfo(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.gameFeedbackInfo(onNetWorkListener);
        }
    }

    /**
     * 新增投诉
     *
     * @param gameid
     * @param onNetWorkListener
     */
    public void kefuGameFeedback(int gameid, String phoneModel, String mobile, String content, String qqNumber, String cateId, List<File> localPathList, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            Map<String, String> params = new TreeMap<>();

            params.put("api", "kefu_game_feedback");
            params.put("gameid", String.valueOf(gameid));
            params.put("phone_model", phoneModel);
            params.put("qq_number", qqNumber);
            params.put("mobile", mobile);
            params.put("content", content);
            params.put("cate_id", cateId);

            Map<String, File> fileParams = new TreeMap<>();
            if (localPathList != null) {
                for (int i = 0; i < localPathList.size(); i++) {
                    fileParams.put("upload_pic" + (i + 1), localPathList.get(i));
                }
            }

            mRepository.kefuGameFeedback(params, fileParams, onNetWorkListener);
        }
    }

    public void getLikeGameList(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getLikeGameList(onNetWorkListener);
        }
    }

    public void getLsbCard(int gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getLsbCard(gameid, onNetWorkListener);
        }
    }

    /**
     * 游戏页：排行榜
     * @param onNetWorkListener
     */
    public void getRankingData(Map<String, String> params, OnNetWorkListener onNetWorkListener){
        if (mRepository != null) {
            mRepository.getRankingData(params,onNetWorkListener);
        }
    }

    /**
     * 游戏页：排行榜
     * @param onNetWorkListener
     */
    public void getBangTab(OnNetWorkListener onNetWorkListener){
        if (mRepository != null) {
            mRepository.getBangTab(onNetWorkListener);
        }
    }

    /**
     * 游戏下载埋点上报
     * @param onNetWorkListener
     */
    public void gameDownloadLog(Map<String, String> params, OnNetWorkListener onNetWorkListener){
        if (mRepository != null) {
            mRepository.gameDownloadLog(params, onNetWorkListener);
        }
    }

    /**
     * 获取游戏列表
     * @param onNetWorkListener
     */
    public void getGameList(OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getGameList(onNetWorkListener);
        }
    }

    /**
     * 获取游戏列表
     * @param onNetWorkListener
     */
    public void getGameList(String kw, int page, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getGameList(kw, page, onNetWorkListener);
        }
    }

    /**
     * 双开空间上报
     * @param onNetWorkListener
     */
    public void multipleLaunchers(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.multipleLaunchers(params, onNetWorkListener);
        }
    }

    /**
     * 火山云机-鉴权 云试玩
     * @param onNetWorkListener
     */
    public void getTokenDemo(String gameid,OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getTokenDemo(gameid,onNetWorkListener);
        }
    }
    public void forumList(Map<String, String> params,OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.forumList(params,onNetWorkListener);
        }
    }
    public void stickyList(Map<String, String> params,OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.stickyList(params,onNetWorkListener);
        }
    }
    public void getCategoryData( OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getCategoryData(onNetWorkListener);
        }
    }
    public void forumReport(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.forumReport(params,onNetWorkListener);
        }
    }
    public void topicAdvert( Map<String, String> params,OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.topicAdvert(params,onNetWorkListener);
        }
    }

    public void forumReplyTopLike(Map<String, String> params, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.forumReplyTopLike(params,onNetWorkListener);
        }
    }

    public void getChatMessage(String gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.getChatMessage(gameid, onNetWorkListener);
        }
    }

    public void addChat(String gameid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.addChat(gameid, onNetWorkListener);
        }
    }

    public void chatActivityRecommend(String tid, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.chatActivityRecommend(tid, onNetWorkListener);
        }
    }

    public void chatTeamNotice(String tid, String page, OnNetWorkListener onNetWorkListener) {
        if (mRepository != null) {
            mRepository.chatTeamNotice(tid,page, onNetWorkListener);
        }
    }


}
