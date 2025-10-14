package com.zqhy.app.core.data.model.game;

import android.text.TextUtils;

import com.zqhy.app.core.data.model.community.comment.CommentInfoVo;
import com.zqhy.app.core.data.model.community.qa.QuestionInfoVo;
import com.zqhy.app.core.data.model.game.appointment.GameAppointmentVo;
import com.zqhy.app.core.data.model.game.detail.GameActivityVo;
import com.zqhy.app.core.data.model.game.detail.GameCardListVo;
import com.zqhy.app.core.data.model.game.detail.GameDesVo;
import com.zqhy.app.core.data.model.game.detail.GameLikeListVo;
import com.zqhy.app.core.data.model.game.detail.GameRebateVo;
import com.zqhy.app.core.data.model.game.detail.GameServerListVo;
import com.zqhy.app.core.data.model.game.detail.GameWelfareVo;
import com.zqhy.app.core.data.model.game.search.GameSimpleInfoVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.data.model.mainpage.figurepush.GameFigurePushVo;
import com.zqhy.app.core.data.model.mainpage.gamealbum.GameAlbumListVo;
import com.zqhy.app.core.data.model.mainpage.gamealbum.GameAlbumVo;
import com.zqhy.app.core.data.model.tryplay.TryGameItemVo;
import com.zqhy.app.core.data.model.video.GameVideoInfoVo;
import com.zqhy.app.utils.CommonUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/7
 */

public class GameInfoVo implements Serializable {

    /**
     * 游戏ID
     */
    private int    gameid;
    /**
     * 游戏名称
     */
    private String gamename;

    /**
     * 游戏品类
     */
    private int    game_type;
    /**
     * 游戏类型字符串, (游戏列表数据)
     */
    private String gameicon;

    /**
     * 游戏类型字符串, (游戏列表数据)
     */
    private String genre_str;

    /**
     * 游戏标签 (游戏列表和bt游戏详情数据)
     */
    private List<GameLabelsBean> game_labels;

    /**
     * 首发标签
     */
    private GameLabelsBean first_label;

    /**
     * 百亿补贴标签
     */
    private YouhuiBean youhui;

    /**
     * 是否允许使用通用券
     */
    private String whole_enable;

    /**
     * 自定义标签
     */
    private List<CustomLabelBean> custom_label;

    /**
     * 优惠券列表
     */
    private List<CouponListBean> coupon_list;

    /**
     * 是否单独显示短评，如果为fase，则详情页短评轮播跳转到正常评论列表，且评论列表隐藏短评入口
     */
    private boolean show_short_comment_list;

    /**
     * 短评列表
     */
    private List<CommentInfoVo.DataBean> short_comment_list;

    /**
     * 最高返利比例
     */
    private int max_rate;

    /**
     * 游戏包大小,单位M
     */
    private float  client_size;
    private String client_package_name;
    private int    client_version_code;

    /**
     * 是否隐藏折扣标签, 1:隐藏; 0:不隐藏
     */
    private int hide_discount_label = 1;


    /*****2019.03.06新增*********************************************************************************************************************************************/

    /**
     * client_type : 3
     * client_size : 5.3
     * is_online : 0
     * play_count : 0
     * question_count : 0
     */

    /**
     * 游戏包类型, 1:android包, 2:ios包, 3:双端
     */
    private int client_type;
    /**
     * 游戏上线状态, 1:上线状态, 0:下线状态
     */
    private int is_online;
    /**
     * 玩过此游戏的人数
     */
    private int play_count;
    private int refund;//省心玩
    /**
     * 此游戏的提问数
     */
    private int question_count;
    /**
     * 此游戏的回答数
     */
    private int answer_count;

    /**
     * 玩家是否可以继续对此游戏提问 1:可以; 0:否
     */
    private int can_question;

    private List<QuestionInfoVo> question_list;

    public int getHide_discount_label() {
        return hide_discount_label;
    }

    public int getRefund() {
        return refund;
    }

    public void setRefund(int refund) {
        this.refund = refund;
    }

    /**
     * @return 0 隐藏全部折扣  1 显示普通折扣  2显示限时折扣
     */
    public int showDiscount() {
        if (getBuilt_in_discount() > 0 && getBuilt_in_discount() < 10){
            return 1;
        }
        if (getHide_discount_label() == 1) {
            return 0;
        } else {
            if (is_flash == 1) {
                return 2;
            } else {
                return 1;
            }
        }
    }


    /**
     * 是否有限时折扣, 1:是; 0:否
     */
    private int is_flash;

    /**
     * 常规折扣
     */
    private float discount;

    /**
     * 限时折扣
     */
    private float flash_discount;
    /**
     * 内置折扣
     */
    private float built_in_discount;

    /**
     * 游戏简介
     */
    private String game_summary;

    /**
     * 游戏介绍
     */
    private String game_description;

    /**
     * 游戏截屏,多个
     */
    private List<String> screenshot;

    /**
     * 视频图片
     */
    private String video_pic;

    /**
     * 视频播放地址
     */
    private String video_url;

    /**
     * 详情页头部标签
     */
    private List<GameLabelsBean> top_labels;

    private GameLabelsBean game_top_label;

    /**
     * 活动列表数据
     */
    private List<NewslistBean> activity;

    /**
     * 攻略列表数据
     */
    private Object strategy;

    /**
     * 可领取优惠券总额
     */
    private int coupon_amount;

    /**
     * 会员优惠券总额
     */
    private int coupon_total;//会员优惠券总额

    /**
     * 可领取优惠券数量
     */
    private int coupon_count;

    /**
     * 会员礼包数量
     */
    private String card_count;

    /**
     * 游戏近期开服表
     */
    private List<ServerListBean> serverlist;

    /**
     * 游戏礼包列表
     */
    private List<CardlistBean> cardlist;


    /**
     * 是否已经收藏此游戏, 1:是; 0:否
     */
    private int is_favorite;

    /**
     * 猜你喜欢游戏数据
     */
    private List<GameInfoVo> like_game_list;

    /**
     * 是否禁止下载游戏,1:是; 0:否
     */
    private int is_deny;

    /**
     * 游戏下载地址
     */
    private String game_download_url;


    /**
     * 游戏下载错误提示
     */
    private String game_download_error;
    /**
     * 游戏列表中包括三类插屏数据 1:图片插屏; 2:集合内显插屏; 3: 集合外显插屏; 4:视频插屏
     * <p>
     * 2020.03.30，新增 5:新游预约
     */
    private int    tp_type;

    private String bottom_label;

    /**
     * 显示折扣截止时间
     */
    private long flash_discount_endtime;

    /**
     * 2019.11.28 新增标签
     */
    private String label_name;
    /**
     * 火山云游需要 进云游id
     */
    private String cloud_game_id;
    private String uid;//火山云游需要
    private int ranking_select;//新游榜排名
    private int ranking_hot;//热游榜排名
    private boolean is_reserve_status;//是否显示新游标签

    private String channel;//写入sdk的渠道信息字符串

    private int selected_game;//是否是精选游戏 1 是  2 不是

    private String gamecode;//云挂机游戏标识

    private int cloud;//是否支持云挂机

    private int unshare;//区分是不是专服游戏
    private int free;//区分是不是免费游戏

    private int play_duration; //游戏时长

    private int chat_status;//1开启群聊 2表示没有

    private int gdm;//1表示GM游戏
    private String gdm_url;

    private int double_status;//是否开启双开，1，开启，2关闭

    private String demo_task_url;//试玩任务网址

    public String getDemo_task_url() {
        return demo_task_url;
    }

    public void setDemo_task_url(String demo_task_url) {
        this.demo_task_url = demo_task_url;
    }

    public int getDouble_status() {
        return double_status;
    }

    public void setDouble_status(int double_status) {
        this.double_status = double_status;
    }

    public String getGdm_url() {
        return gdm_url;
    }

    public void setGdm_url(String gdm_url) {
        this.gdm_url = gdm_url;
    }

    public int getGdm() {
        if (!TextUtils.isEmpty(genre_str) && (genre_str.contains("GM") || genre_str.contains("gm") || genre_str.contains("Gm") || genre_str.contains("gM"))){
            return 1;
        }
        return 0;
    }

    public void setGdm(int gdm) {
        this.gdm = gdm;
    }

    public int getChat_status() {
        return chat_status;
    }

    public void setChat_status(int chat_status) {
        this.chat_status = chat_status;
    }

    public int getPlay_duration() {
        return play_duration;
    }

    public void setPlay_duration(int play_duration) {
        this.play_duration = play_duration;
    }

    public int getFree() {
        return free;
    }

    public void setFree(int free) {
        this.free = free;
    }

    public int getUnshare() {
        return unshare;
    }

    public void setUnshare(int unshare) {
        this.unshare = unshare;
    }

    public int getCloud() {
        return cloud;
    }

    public void setCloud(int cloud) {
        this.cloud = cloud;
    }

    public String getCloud_game_id() {
        return cloud_game_id;
    }

    public void setCloud_game_id(String cloud_game_id) {
        this.cloud_game_id = cloud_game_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGamecode() {
        return gamecode;
    }

    public void setGamecode(String gamecode) {
        this.gamecode = gamecode;
    }

    public float getBuilt_in_discount() {
        return built_in_discount;
    }

    public void setBuilt_in_discount(float built_in_discount) {
        this.built_in_discount = built_in_discount;
    }

    public int getSelected_game() {
        return selected_game;
    }

    public void setSelected_game(int selected_game) {
        this.selected_game = selected_game;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCard_count() {
        return card_count;
    }

    public void setCard_count(String card_count) {
        this.card_count = card_count;
    }

    public boolean isIs_reserve_status() {
        return is_reserve_status;
    }

    public void setIs_reserve_status(boolean is_reserve_status) {
        this.is_reserve_status = is_reserve_status;
    }

    public int getRanking_select() {
        return ranking_select;
    }

    public void setRanking_select(int ranking_select) {
        this.ranking_select = ranking_select;
    }

    public int getRanking_hot() {
        return ranking_hot;
    }

    public void setRanking_hot(int ranking_hot) {
        this.ranking_hot = ranking_hot;
    }

    public String getLabel_name() {
        return label_name;
    }

    public int getClient_type() {
        return client_type;
    }

    public boolean isIOSGameOnly() {
        return client_type == 2;
    }

    public int getIs_online() {
        return is_online;
    }

    public boolean isGameOnline() {
        return is_online == 1;
    }

    public int getPlay_count() {
        return play_count;
    }

    public String getPlayCountStr() {
        return CommonUtils.formatNumber(play_count);
    }

    public int getQuestion_count() {
        return question_count;
    }

    public int getAnswer_count() {
        return answer_count;
    }

    public int getCan_question() {
        return can_question;
    }

    public List<QuestionInfoVo> getQuestion_list() {
        return question_list;
    }

    public static class GameLabelsBean implements Serializable {
        /**
         * bgcolor : #59bcf6
         * biaoqian : 官方变态服
         */

        private String bgcolor;
        private String text_color;
        private String label_name;

        public String getBgcolor() {
            return bgcolor;
        }

        public void setBgcolor(String bgcolor) {
            this.bgcolor = bgcolor;
        }

        public String getLabel_name() {
            return label_name;
        }

        public void setLabel_name(String label_name) {
            this.label_name = label_name;
        }

        public String getText_color() {
            return text_color;
        }

        public void setText_color(String text_color) {
            this.text_color = text_color;
        }

        public GameLabelsBean() {
        }

        public GameLabelsBean(String label_name, String text_color, String bgcolor) {
            this.bgcolor = bgcolor;
            this.text_color = text_color;
            this.label_name = label_name;
        }
    }

    public static class YouhuiBean implements Serializable {
        /**
         *"label": "百亿补贴·立减50%",//补贴label
         *"end_time": 12312312//补贴结束时间，秒级unix时间戳
         */

        private String label;
        private long end_time;
        private double ratio;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public long getEnd_time() {
            return end_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
        }

        public double getRatio() {
            return ratio;
        }

        public void setRatio(double ratio) {
            this.ratio = ratio;
        }

        public YouhuiBean() {
        }

        public YouhuiBean(String label, long end_time) {
            this.label = label;
            this.end_time = end_time;
        }
    }

    public static class CustomLabelBean {
        /**
         "label": "自定义标签",
         "jump_target": "http:\/\/baidu.com",
         "page_type": "url",
         "game_type": "1,2,3",
         "sort": "0",
         "param": {
         "target_url": "http:\/\/baidu.com"
         }
         */

        private String label;
        private String jump_target;
        private String page_type;
        private String game_type;
        private String sort;
        private AppBaseJumpInfoBean.ParamBean param;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getJump_target() {
            return jump_target;
        }

        public void setJump_target(String jump_target) {
            this.jump_target = jump_target;
        }

        public String getPage_type() {
            return page_type;
        }

        public void setPage_type(String page_type) {
            this.page_type = page_type;
        }

        public String getGame_type() {
            return game_type;
        }

        public void setGame_type(String game_type) {
            this.game_type = game_type;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public AppBaseJumpInfoBean.ParamBean getParam() {
            return param;
        }

        public void setParam(AppBaseJumpInfoBean.ParamBean param) {
            this.param = param;
        }
    }

    public static class CouponListBean {
        /**
         "id": "101286",
         "condition": "满30可用",
         "gameid": "6496",//gameid大于0，点击跳转优惠券列表，小于0点击跳转积分商城
         "amount": 12,
         "is_get": false //用户是否已领取该优惠券
         */

        private String id;
        private String condition;
        private int gameid;
        private double amount;
        private int status;

        private int sign;//是否是会员专享，1 是  2 不是
        private int m_price;//商城券销售价格，int类型
        private String coupon_type;//game_coupon：游戏券、shop_goods：商城券

        private String range;//适用于
        private String can_get_label;//可领取时间
        private String expiry_label;//领取后有效期
        private String user_get_count;//新增字段 0可多领 否则不可多领
        private String frequency;//day 每天一次

        public String getFrequency() {
            return frequency;
        }

        public void setFrequency(String frequency) {
            this.frequency = frequency;
        }

        public String getUser_get_count() {
            return user_get_count;
        }

        public void setUser_get_count(String user_get_count) {
            this.user_get_count = user_get_count;
        }

        public String getRange() {
            return CommonUtils.getGamename(range);
        }

        public String getOhterRange() {
            return CommonUtils.getOtherGameName(range);
        }

        public void setRange(String range) {
            this.range = range;
        }

        public String getCan_get_label() {
            return can_get_label;
        }

        public void setCan_get_label(String can_get_label) {
            this.can_get_label = can_get_label;
        }

        public String getExpiry_label() {
            return expiry_label;
        }

        public void setExpiry_label(String expiry_label) {
            this.expiry_label = expiry_label;
        }

        public int getSign() {
            return sign;
        }

        public void setSign(int sign) {
            this.sign = sign;
        }

        public int getM_price() {
            return m_price;
        }

        public void setM_price(int m_price) {
            this.m_price = m_price;
        }

        public String getCoupon_type() {
            return coupon_type;
        }

        public void setCoupon_type(String coupon_type) {
            this.coupon_type = coupon_type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public int getGameid() {
            return gameid;
        }

        public void setGameid(int gameid) {
            this.gameid = gameid;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    /**
     * 评论数量
     */
    private int comment_count;

    public int getComment_count() {
        return comment_count;
    }

    /**
     * 交易数量
     */
    private int goods_count;

    public int getGoods_count() {
        return goods_count;
    }


    /******2019.04.17 新增试玩积分*******************************/

    private TryGameItemVo.DataBean trial_info;

    public TryGameItemVo.DataBean getTrial_info() {
        return trial_info;
    }


    /******2019.06.19 新增字段*******************************/


    /**
     * 1--即将下架
     */
    private int offline;

    public boolean isOffline() {
        return offline == 1;
    }


    /**
     * 推荐文字
     */
    private String recommend_text;

    /**
     * 1,热；2，新；3，推荐
     */
    private int t_type;

    public int getT_type() {
        return t_type;
    }

    public String getRecommend_text() {
        return recommend_text;
    }

    /*************精品游戏********************************************************************************************************************/


    /*******游戏详情字段*************************************************/
    private String benefit_content;

    /**
     * BT游戏福利
     *
     * @return
     */
    public GameWelfareVo getGameWelfareVo() {
        GameWelfareVo gameWelfareVo = new GameWelfareVo(gameid, benefit_content, coupon_amount);
        gameWelfareVo.setGame_labels(game_labels);
        gameWelfareVo.setGame_type(game_type);
        gameWelfareVo.setGame_model(game_mode);
        gameWelfareVo.setData_exchange(data_exchange);
        return gameWelfareVo;
    }


    private String rebate_content;
    private String rebate_flash_content;

    /**
     * 充值返利
     *
     * @return
     */
    public GameRebateVo getGameRebateVo() {
        GameRebateVo gameRebateVo = new GameRebateVo();
        gameRebateVo.setRebate_content(rebate_content);
        gameRebateVo.setRebate_flash_content(rebate_flash_content);
        gameRebateVo.setMax_rate(max_rate);
        gameRebateVo.setGame_type(game_type);

        return gameRebateVo;
    }

    private long rebate_flash_begin;
    private long rebate_flash_end;

    /**
     * 游戏活动
     *
     * @return
     */
    public GameActivityVo getGameActivityVo() {
        GameActivityVo gameActivityVo = new GameActivityVo();

        gameActivityVo.setGameid(gameid);
        gameActivityVo.setDiscount(discount);
        gameActivityVo.setFlash_discount(flash_discount);
        gameActivityVo.setShowDiscount(showDiscount());
        gameActivityVo.setFlash_discount_endtime(flash_discount_endtime);
        gameActivityVo.setCoupon_amount(coupon_amount);
        gameActivityVo.setGame_type(game_type);
        gameActivityVo.setRebate_flash_begin(rebate_flash_begin);
        gameActivityVo.setRebate_flash_end(rebate_flash_end);
        gameActivityVo.setGame_type(game_type);
        gameActivityVo.setActivity(getActivity());
        gameActivityVo.setTrial_info(getTrial_info());
        return gameActivityVo;
    }


    /**
     * 游戏简介
     *
     * @return
     */
    public GameDesVo getGameDesVo() {
        GameDesVo gameDesVo = new GameDesVo();
        gameDesVo.setScreenshot(getScreenshot());
        gameDesVo.setGame_description(game_description);
        gameDesVo.setVideo_pic(video_pic);
        gameDesVo.setVideo_url(video_url);
        gameDesVo.setVipNews(vip_news);
        if (TextUtils.isEmpty(getBenefit_content())){
            gameDesVo.setGame_model(game_mode);
            gameDesVo.setData_exchange(data_exchange);
        }
        return gameDesVo;
    }

    public boolean hasGameVideo() {
        return !TextUtils.isEmpty(getVideo_pic()) && !TextUtils.isEmpty(getVideo_url());
    }


    /**
     * 开服表列表
     *
     * @return
     */
    public GameServerListVo getGameServerListVo() {
        GameServerListVo gameServerVo = new GameServerListVo();
        gameServerVo.setServerlist(getServerlist());

        return gameServerVo;
    }


    /**
     * 礼包列表
     *
     * @return
     */
    private GameCardListVo gameCardListVo = null;
    public GameCardListVo getGameCardListVo() {
        if (gameCardListVo == null){
            gameCardListVo = new GameCardListVo();
        }
        gameCardListVo.setCardlist(getCardlist());
        gameCardListVo.setUser_already_commented(user_already_commented);
        return gameCardListVo;
    }


    /**
     * 猜你喜欢
     *
     * @return
     */
    private GameLikeListVo gameLikeListVo;
    public GameLikeListVo getGameLikeListVo() {
        gameLikeListVo = new GameLikeListVo();
        gameLikeListVo.setLike_game_list(getLike_game_list());
        return gameLikeListVo;
    }

    public void setGameLikeListVo(GameLikeListVo gameLikeListVo){
        this.gameLikeListVo = gameLikeListVo;
    }


    /********************************************************************************************************************************/

    /**
     ****************************************************************************************************************
     ***************华丽风分割线***************************************************************************************
     ****************************************************************************************************************
     */

    /********tp_type = 1*******图片插屏start**********************************************************/

    private String title2;
    private String title2_color;
    private String pic;
    private String new_pic;
    private String pic_20231122;
    private String page_type;

    private AppBaseJumpInfoBean.ParamBean param;

    public GameFigurePushVo getGameFigurePushVo() {
        GameFigurePushVo gameFigurePushVo = new GameFigurePushVo();
        if (tp_type == 1) {
            gameFigurePushVo.setGameid(gameid);
            gameFigurePushVo.setGamename(gamename);
            gameFigurePushVo.setGame_type(game_type);
            gameFigurePushVo.setPage_type(page_type);
            gameFigurePushVo.setPic(pic);
            gameFigurePushVo.setParam(param);
            gameFigurePushVo.setTitle(title);
            gameFigurePushVo.setTitle2(title2);
            gameFigurePushVo.setTitle2_color(title2_color);
            gameFigurePushVo.setEventPosition(eventPosition);
            //            if(getEventList() != null && getEventList().size() > 0){
            //                for (Integer integer : getEventList()){
            //                    gameFigurePushVo.addEvent(integer);
            //                }
            //            }
        }
        return gameFigurePushVo;
    }

    /***************图片插屏end*******************************************************************************/

    /**
     ****************************************************************************************************************
     ***************华丽风分割线***************************************************************************************
     ****************************************************************************************************************
     */

    /********tp_type = 2*******集合内显插屏start**********************************************************/

    private String description;
    private String labels;

    public GameAlbumVo getGameAlbumVo() {
        GameAlbumVo gameAlbumVo = new GameAlbumVo();
        if (tp_type == 2) {
            gameAlbumVo.setPage_type(page_type);
            gameAlbumVo.setPic(pic);
            gameAlbumVo.setDescription(description);
            gameAlbumVo.setLabels(labels);
            gameAlbumVo.setParam(param);
            gameAlbumVo.setTitle(title);
        }
        return gameAlbumVo;
    }

    /***************集合内显插屏end**********************************************************/

    /**
     ****************************************************************************************************************
     ***************华丽风分割线***************************************************************************************
     ****************************************************************************************************************
     */

    /********tp_type = 3*******集合外显插屏start**********************************************************/
    private String background;
    private String title;
    private String title_color;

    private List<GameAlbumListVo.GameItem> game_items;


    public GameAlbumListVo getGameAlbumListVo() {
        GameAlbumListVo gameAlbumListVo = new GameAlbumListVo();
        if (tp_type == 3) {
            gameAlbumListVo.setBackground(background);
            gameAlbumListVo.setTitle(title);
            gameAlbumListVo.setTitle_color(title_color);
            gameAlbumListVo.setGame_type(game_type);
            gameAlbumListVo.setGame_items(game_items);
        }
        return gameAlbumListVo;
    }

    /***************集合外显插屏end**********************************************************/

    /********tp_type = 4*******视频插屏start**********************************************************/
    public GameVideoInfoVo getGameVideo() {
        GameVideoInfoVo gameVideoInfoVo = new GameVideoInfoVo();
        if (tp_type == 4) {
            gameVideoInfoVo.setGameid(gameid);
            gameVideoInfoVo.setGamename(gamename);
            gameVideoInfoVo.setGame_type(game_type);
            gameVideoInfoVo.setPage_type(page_type);
            gameVideoInfoVo.setPic(pic);
            gameVideoInfoVo.setParam(param);
            gameVideoInfoVo.setTitle(title);
            gameVideoInfoVo.setTitle2(title2);
            gameVideoInfoVo.setTitle2_color(title2_color);
        }
        return gameVideoInfoVo;
    }


    /***************视频插屏end**********************************************************/

    /**
     * ***************************************************************************************************************
     * **************华丽风分割线***************************************************************************************
     * ***************************************************************************************************************
     */

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public String getGamename() {
        return CommonUtils.getGamename(gamename);
    }

    public String getAllGamename() {
        return CommonUtils.getGamename(gamename) + "\n" + CommonUtils.getOtherGameName(gamename);
    }

    public String getAllGamename1() {
        return gamename;
    }

    public String getOtherGameName(){
        return CommonUtils.getOtherGameName(gamename);
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }

    public int getGame_type() {
        return game_type;
    }

    public String getGameicon() {
        return gameicon;
    }

    public List<GameLabelsBean> getGame_labels() {
        return game_labels;
    }

    public GameLabelsBean getFirst_label() {
        return first_label;
    }

    public int getMax_rate() {
        return max_rate;
    }

    public float getClient_size() {
        return client_size;
    }

    public int getIs_flash() {
        return is_flash;
    }

    public float getDiscount() {
        if (getBuilt_in_discount() > 0 && getBuilt_in_discount() < 10){
            return getBuilt_in_discount();
        }
        return discount;
    }

    public float getFlash_discount() {
        if (getBuilt_in_discount() > 0 && getBuilt_in_discount() < 10){
            return getBuilt_in_discount();
        }
        return flash_discount;
    }

    public String getGame_summary() {
        return game_summary;
    }

    public String getGame_description() {
        return game_description;
    }

    public List<String> getScreenshot() {
        return screenshot;
    }

    public List<GameLabelsBean> getTop_labels() {
        return top_labels;
    }

    public GameLabelsBean getGame_top_label() {
        return game_top_label;
    }

    public List<NewslistBean> getActivity() {
        return activity;
    }

    public Object getStrategy() {
        return strategy;
    }

    public float getCoupon_amount() {
        return coupon_amount;
    }

    public int getCoupon_total() {
        return coupon_total;
    }

    public void setCoupon_total(int coupon_total) {
        this.coupon_total = coupon_total;
    }

    public int getCoupon_count() {
        return coupon_count;
    }

    public List<ServerListBean> getServerlist() {
        return serverlist;
    }

    public List<CardlistBean> getCardlist() {
        return cardlist;
    }

    public int getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(int is_favorite) {
        this.is_favorite = is_favorite;
    }

    public List<GameInfoVo> getLike_game_list() {
        return like_game_list;
    }

    public int getIs_deny() {
        return is_deny;
    }

    public String getGame_download_url() {
        return game_download_url;
    }

    public String getGame_download_error() {
        return game_download_error;
    }

    public int getTp_type() {
        return tp_type;
    }

    public String getTitle2() {
        return title2;
    }

    public String getTitle2_color() {
        return title2_color;
    }

    public String getPic() {
        return pic;
    }

    public String getPic_20231122() {
        return pic_20231122;
    }

    public void setPic_20231122(String pic_20231122) {
        this.pic_20231122 = pic_20231122;
    }

    public String getNew_pic() {
        return new_pic;
    }

    public void setNew_pic(String new_pic) {
        this.new_pic = new_pic;
    }

    public String getPage_type() {
        return page_type;
    }

    public AppBaseJumpInfoBean.ParamBean getParam() {
        return param;
    }

    public String getDescription() {
        return description;
    }

    public String getLabels() {
        return labels;
    }

    public String getBackground() {
        return background;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle_color() {
        return title_color;
    }

    public List<GameAlbumListVo.GameItem> getGame_items() {
        return game_items;
    }

    public String getGenre_str() {
        return genre_str;
    }

    public String getBenefit_content() {
        return CommonUtils.getGamename(benefit_content);
    }

    public String getRebate_content() {
        return rebate_content;
    }

    public String getRebate_flash_content() {
        return rebate_flash_content;
    }

    public long getRebate_flash_begin() {
        return rebate_flash_begin;
    }

    public long getRebate_flash_end() {
        return rebate_flash_end;
    }

    public long getFlash_discount_endtime() {
        return flash_discount_endtime;
    }

    public String getBottom_label() {
        return bottom_label;
    }

    public void setGameicon(String gameicon) {
        this.gameicon = gameicon;
    }

    public void setGenre_str(String genre_str) {
        this.genre_str = genre_str;
    }

    public void setGame_labels(List<GameLabelsBean> game_labels) {
        this.game_labels = game_labels;
    }

    public void setFirst_label(GameLabelsBean first_label) {
        this.first_label = first_label;
    }

    public void setMax_rate(int max_rate) {
        this.max_rate = max_rate;
    }

    public void setClient_size(float client_size) {
        this.client_size = client_size;
    }

    public void setClient_package_name(String client_package_name) {
        this.client_package_name = client_package_name;
    }

    public void setClient_version_code(int client_version_code) {
        this.client_version_code = client_version_code;
    }

    public void setHide_discount_label(int hide_discount_label) {
        this.hide_discount_label = hide_discount_label;
    }

    public void setClient_type(int client_type) {
        this.client_type = client_type;
    }

    public void setIs_online(int is_online) {
        this.is_online = is_online;
    }

    public void setPlay_count(int play_count) {
        this.play_count = play_count;
    }

    public void setQuestion_count(int question_count) {
        this.question_count = question_count;
    }

    public void setAnswer_count(int answer_count) {
        this.answer_count = answer_count;
    }

    public void setCan_question(int can_question) {
        this.can_question = can_question;
    }

    public void setQuestion_list(List<QuestionInfoVo> question_list) {
        this.question_list = question_list;
    }

    public void setIs_flash(int is_flash) {
        this.is_flash = is_flash;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public void setFlash_discount(float flash_discount) {
        this.flash_discount = flash_discount;
    }

    public void setGame_summary(String game_summary) {
        this.game_summary = game_summary;
    }

    public void setGame_description(String game_description) {
        this.game_description = game_description;
    }

    public void setScreenshot(List<String> screenshot) {
        this.screenshot = screenshot;
    }

    public void setVideo_pic(String video_pic) {
        this.video_pic = video_pic;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public void setTop_labels(List<GameLabelsBean> top_labels) {
        this.top_labels = top_labels;
    }

    public void setGame_top_label(GameLabelsBean game_top_label) {
        this.game_top_label = game_top_label;
    }

    public void setActivity(List<NewslistBean> activity) {
        this.activity = activity;
    }

    public void setStrategy(Object strategy) {
        this.strategy = strategy;
    }

    public void setCoupon_amount(int coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public void setCoupon_count(int coupon_count) {
        this.coupon_count = coupon_count;
    }

    public void setServerlist(List<ServerListBean> serverlist) {
        this.serverlist = serverlist;
    }

    public void setCardlist(List<CardlistBean> cardlist) {
        this.cardlist = cardlist;
    }

    public void setLike_game_list(List<GameInfoVo> like_game_list) {
        this.like_game_list = like_game_list;
    }

    public void setIs_deny(int is_deny) {
        this.is_deny = is_deny;
    }

    public void setGame_download_url(String game_download_url) {
        this.game_download_url = game_download_url;
    }

    public void setGame_download_error(String game_download_error) {
        this.game_download_error = game_download_error;
    }

    public void setTp_type(int tp_type) {
        this.tp_type = tp_type;
    }

    public void setBottom_label(String bottom_label) {
        this.bottom_label = bottom_label;
    }

    public void setFlash_discount_endtime(long flash_discount_endtime) {
        this.flash_discount_endtime = flash_discount_endtime;
    }

    public void setLabel_name(String label_name) {
        this.label_name = label_name;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public void setGoods_count(int goods_count) {
        this.goods_count = goods_count;
    }

    public void setTrial_info(TryGameItemVo.DataBean trial_info) {
        this.trial_info = trial_info;
    }

    public void setOffline(int offline) {
        this.offline = offline;
    }

    public void setRecommend_text(String recommend_text) {
        this.recommend_text = recommend_text;
    }

    public void setT_type(int t_type) {
        this.t_type = t_type;
    }

    public void setBenefit_content(String benefit_content) {
        this.benefit_content = benefit_content;
    }

    public void setRebate_content(String rebate_content) {
        this.rebate_content = rebate_content;
    }

    public void setRebate_flash_content(String rebate_flash_content) {
        this.rebate_flash_content = rebate_flash_content;
    }

    public void setRebate_flash_begin(long rebate_flash_begin) {
        this.rebate_flash_begin = rebate_flash_begin;
    }

    public void setRebate_flash_end(long rebate_flash_end) {
        this.rebate_flash_end = rebate_flash_end;
    }

    public void setGameCardListVo(GameCardListVo gameCardListVo) {
        this.gameCardListVo = gameCardListVo;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public void setTitle2_color(String title2_color) {
        this.title2_color = title2_color;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setPage_type(String page_type) {
        this.page_type = page_type;
    }

    public void setParam(AppBaseJumpInfoBean.ParamBean param) {
        this.param = param;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitle_color(String title_color) {
        this.title_color = title_color;
    }

    public void setGame_items(List<GameAlbumListVo.GameItem> game_items) {
        this.game_items = game_items;
    }

    public void setEventList(List<Integer> eventList) {
        this.eventList = eventList;
    }

    public void setRanking_label(String ranking_label) {
        this.ranking_label = ranking_label;
    }

    public void setOnline_text(String online_text) {
        this.online_text = online_text;
    }

    public void setAppointment_begintime(long appointment_begintime) {
        this.appointment_begintime = appointment_begintime;
    }

    public void setOnline_time(long online_time) {
        this.online_time = online_time;
    }

    public void setGame_status(int game_status) {
        this.game_status = game_status;
    }

    public void setAppointment_count(int appointment_count) {
        this.appointment_count = appointment_count;
    }

    public void setGame_appointment_info(String game_appointment_info) {
        this.game_appointment_info = game_appointment_info;
    }

    public void setIs_refund(int is_refund) {
        this.is_refund = is_refund;
    }

    public void setIs_first_free(int is_first_free) {
        this.is_first_free = is_first_free;
    }

    public void setIs_first(int is_first) {
        this.is_first = is_first;
    }

    public void setBg_pic(String bg_pic) {
        this.bg_pic = bg_pic;
    }

    public void setServerinfo(ServerInfoVo serverinfo) {
        this.serverinfo = serverinfo;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setVip_label(String vip_label) {
        this.vip_label = vip_label;
    }

    public void setServer_str(String server_str) {
        this.server_str = server_str;
    }

    public void setJingxuan_label(String jingxuan_label) {
        this.jingxuan_label = jingxuan_label;
    }

    public void setCoin_label(String coin_label) {
        this.coin_label = coin_label;
    }

    public static class NewslistBean implements Serializable {
        private String id;
        private String title;
        private String title2;

        private String endtime;
        private String typeid;
        private String begintime;
        private String fabutime;

        private int is_newest;
        private int rebate_apply_type;

        private String url;
        private String rebate_url;

        private String bg_color;
        private String text_color;

        private String news_status;

        public String getNews_status() {
            return news_status;
        }

        public void setNews_status(String news_status) {
            this.news_status = news_status;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public String getBegintime() {
            return begintime;
        }

        public void setBegintime(String begintime) {
            this.begintime = begintime;
        }

        public String getFabutime() {
            return fabutime;
        }

        public void setFabutime(String fabutime) {
            this.fabutime = fabutime;
        }

        public int getRebate_apply_type() {
            return rebate_apply_type;
        }

        public void setRebate_apply_type(int rebate_apply_type) {
            this.rebate_apply_type = rebate_apply_type;
        }

        public String getRebate_url() {
            return rebate_url;
        }

        public void setRebate_url(String rebate_url) {
            this.rebate_url = rebate_url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle2() {
            return title2;
        }

        public void setTitle2(String title2) {
            this.title2 = title2;
        }

        public int getIs_newest() {
            return is_newest;
        }

        public void setIs_newest(int is_newest) {
            this.is_newest = is_newest;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBg_color() {
            return bg_color;
        }

        public String getText_color() {
            return text_color;
        }
    }

    public static class ServerListBean implements Serializable {
        private String  serverid;
        private String  servername;
        private long    begintime;
        private boolean isTheNewest;

        public String getServerid() {
            return serverid;
        }

        public void setServerid(String serverid) {
            this.serverid = serverid;
        }

        public String getServername() {
            return servername;
        }

        public void setServername(String servername) {
            this.servername = servername;
        }

        public long getBegintime() {
            return begintime;
        }

        public void setBegintime(long begintime) {
            this.begintime = begintime;
        }

        public boolean isTheNewest() {
            return isTheNewest;
        }

        public void setTheNewest(boolean theNewest) {
            isTheNewest = theNewest;
        }
    }

    public static class CardlistBean implements Serializable {
        private int    cardid;
        private int    gameid;
        private int    serverid;
        private String cardname;
        private String youxiaoqi;
        private String sort;
        private String cardimage;
        private String cardusage;
        private String cardcontent;
        private int    cardcountall;
        private int    cardkucun;
        private String is_recommend;
        private String is_gh;
        private String is_gw;
        private String gamename;
        private String libaokucun;

        //1:普通礼包; 2:充值礼包; 3:转游礼包; 4:评论礼包
        private int    card_type;
        //1:累计充值; 2:单笔充值
        private int    need_pay_type;
        //限时起始日期
        private long   need_pay_begin;
        //限时起始日期
        private long   need_pay_end;
        private String label;
        private String need_pay_total;

        //0未领   1已领取
        private int    is_get_card;
        private String card;

        private int sign; //是否是会员专享，1 是  2 不是

        public int getSign() {
            return sign;
        }

        public void setSign(int sign) {
            this.sign = sign;
        }

        public int getCardid() {
            return cardid;
        }

        public void setCardid(int cardid) {
            this.cardid = cardid;
        }

        public int getGameid() {
            return gameid;
        }

        public void setGameid(int gameid) {
            this.gameid = gameid;
        }

        public int getServerid() {
            return serverid;
        }

        public void setServerid(int serverid) {
            this.serverid = serverid;
        }

        public String getCardname() {
            return cardname;
        }

        public void setCardname(String cardname) {
            this.cardname = cardname;
        }

        public String getYouxiaoqi() {
            return youxiaoqi;
        }

        public void setYouxiaoqi(String youxiaoqi) {
            this.youxiaoqi = youxiaoqi;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getCardimage() {
            return cardimage;
        }

        public void setCardimage(String cardimage) {
            this.cardimage = cardimage;
        }

        public String getCardusage() {
            return cardusage;
        }

        public void setCardusage(String cardusage) {
            this.cardusage = cardusage;
        }

        public String getCardcontent() {
            return cardcontent;
        }

        public void setCardcontent(String cardcontent) {
            this.cardcontent = cardcontent;
        }

        public int getCardkucun() {
            return cardkucun;
        }

        public void setCardkucun(int cardkucun) {
            this.cardkucun = cardkucun;
        }

        public String getIs_recommend() {
            return is_recommend;
        }

        public void setIs_recommend(String is_recommend) {
            this.is_recommend = is_recommend;
        }

        public String getIs_gh() {
            return is_gh;
        }

        public void setIs_gh(String is_gh) {
            this.is_gh = is_gh;
        }

        public String getIs_gw() {
            return is_gw;
        }

        public void setIs_gw(String is_gw) {
            this.is_gw = is_gw;
        }

        public String getGamename() {
            return CommonUtils.getGamename(gamename);
        }

        public void setGamename(String gamename) {
            this.gamename = gamename;
        }

        public String getLibaokucun() {
            return libaokucun;
        }

        public void setLibaokucun(String libaokucun) {
            this.libaokucun = libaokucun;
        }

        public int getCard_type() {
            return card_type;
        }

        public void setCard_type(int card_type) {
            this.card_type = card_type;
        }

        public int getNeed_pay_type() {
            return need_pay_type;
        }

        public void setNeed_pay_type(int need_pay_type) {
            this.need_pay_type = need_pay_type;
        }

        public long getNeed_pay_begin() {
            return need_pay_begin;
        }

        public void setNeed_pay_begin(long need_pay_begin) {
            this.need_pay_begin = need_pay_begin;
        }

        public long getNeed_pay_end() {
            return need_pay_end;
        }

        public void setNeed_pay_end(long need_pay_end) {
            this.need_pay_end = need_pay_end;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getNeed_pay_total() {
            return need_pay_total;
        }

        public void setNeed_pay_total(String need_pay_total) {
            this.need_pay_total = need_pay_total;
        }

        public boolean isCommentGift() {
            return card_type == 4;
        }

        public boolean isRechargeGift() {
            return card_type == 2;
        }

        public int getCardcountall() {
            return cardcountall;
        }

        public void setIs_get_card(int is_get_card) {
            this.is_get_card = is_get_card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public boolean isGetCard() {
            return is_get_card == 1;
        }

        public String getCard() {
            return card;
        }

        public String getGiftRequirement() {
            StringBuilder sb = new StringBuilder();
            if (getNeed_pay_type() == 1) {
                sb.append("1.活动期间累计充值达到" + getNeed_pay_total() + "元，可领取该礼包");
            } else {
                sb.append("1.活动期间单笔充值达到" + getNeed_pay_total() + "元，可领取该礼包");
            }
            sb.append("\n\n");
            if (getNeed_pay_end() == 0) {
                sb.append("2.活动长期为：长期有效");
            } else {
                String startTime = CommonUtils.formatTimeStamp(getNeed_pay_begin() * 1000, "yyyy-MM-dd HH:mm");
                String endTime = CommonUtils.formatTimeStamp(getNeed_pay_end() * 1000, "yyyy-MM-dd HH:mm");
                sb.append("2.活动长期为：" + startTime + "~" + endTime);
            }
            sb.append("\n\n");
            sb.append("3.该礼包领完即止，达到要求的亲亲请尽快领取哦");
            return sb.toString();
        }
    }


    public String getGame_type_name() {
        String game_type_name = "";
        switch (game_type) {
            case 1:
                game_type_name = "变态版";
                break;
            case 2:
                game_type_name = "折扣游戏";
                break;
            case 3:
                game_type_name = "H5游戏";
                break;
            case 4:
                game_type_name = "单机游戏";
                break;
            default:
                break;
        }


        return game_type_name;
    }

    public String getClient_package_name() {
        return client_package_name;
    }

    public int getClient_version_code() {
        return client_version_code;
    }

    public String getVideo_pic() {
        return video_pic;
    }

    public String getVideo_url() {
        return video_url;
    }

    private static final String tag = "GAME_DOWNLOAD_";

    public String getGameDownloadTag() {
        return tag + gameid;
    }


    public GameExtraVo getGameExtraVo() {
        GameExtraVo gameExtraVo = new GameExtraVo();
        gameExtraVo.setGameid(gameid);
        gameExtraVo.setGame_type(game_type);
        gameExtraVo.setGame_download_tag(getGameDownloadTag());
        gameExtraVo.setGameicon(gameicon);
        gameExtraVo.setGamename(gamename);
        gameExtraVo.setClient_package_name(client_package_name);
        gameExtraVo.setGame_download_url(game_download_url);
        gameExtraVo.setChannel(channel);

        return gameExtraVo;
    }


    /**
     * 埋点列表
     */
    private List<Integer> eventList;

    public void addEvent(int event) {
        if (eventList == null) {
            eventList = new ArrayList<>();
        }
        eventList.add(event);
    }

    public List<Integer> getEventList() {
        return eventList;
    }

    /**
     * 埋点事件 position
     */
    private int eventPosition;

    public int getEventPosition() {
        return eventPosition;
    }

    public void setEventPosition(int eventPosition) {
        this.eventPosition = eventPosition;
    }


    /*****2019.06.19 新增*******************************/


    /**
     * search simple game vo
     *
     * @return
     */
    public GameSimpleInfoVo getGameSimpleVo() {
        GameSimpleInfoVo gameSimpleInfoVo = new GameSimpleInfoVo();

        gameSimpleInfoVo.setDiscount(discount);
        gameSimpleInfoVo.setGame_type(game_type);
        gameSimpleInfoVo.setGameid(gameid);
        gameSimpleInfoVo.setGamename(gamename);
        gameSimpleInfoVo.setShowDiscount(showDiscount());
        gameSimpleInfoVo.setGameInfoVo(this);

        return gameSimpleInfoVo;
    }


    private int indexPosition;

    public int getIndexPosition() {
        return indexPosition;
    }

    public void setIndexPosition(int indexPosition) {
        this.indexPosition = indexPosition;
    }


    /**
     * 2019.12.17 新增
     */

    /**
     * 主页排行榜tag
     */
    private String ranking_label;

    public String getRanking_label() {
        return ranking_label;
    }


    /** 2020.01.13 新增游戏礼包*/
    /**
     * 当前用户是否已经点评此游戏  1：点评了； 0：未点评
     */
    private int user_already_commented;

    public boolean isUserAlreadyCommented() {
        return user_already_commented == 1;
    }

    public void setUser_already_commented(int user_already_commented) {
        this.user_already_commented = user_already_commented;
    }

    public int getUser_already_commented() {
        return user_already_commented;
    }
    /**2020.03.27新增
     *
     */
    /**
     * 预约信息
     */
    private String online_text;
    /**
     * 上线时间
     */
    private long   appointment_begintime;
    private long   online_time;
    /**
     * 状态:
     * 0:初始状态,点击可以关注,
     * 1:已关注,点击可以取消关注,
     * 10:已结束,点击进入游戏详情
     */
    private int    game_status = -1;

    /**
     * 预约人数
     */
    private int appointment_count;

    public String getOnline_text() {
        return online_text;
    }

    public long getAppointment_begintime() {
        return appointment_begintime;
    }

    public long getOnline_time() {
        return online_time;
    }

    public int getGame_status() {
        return game_status;
    }

    public boolean isGameAppointment() {
        return game_status == 0 || game_status == 1;
    }

    public int getAppointment_count() {
        return appointment_count;
    }

    /**
     * 新游预约文字信息
     */
    private String game_appointment_info;

    public String getGame_appointment_info() {
        return game_appointment_info;
    }

    /**
     * 获取gameAppointmentVo
     *
     * @return
     */
    public GameAppointmentVo getGameAppointmentVo() {
        GameAppointmentVo gameAppointmentVo = new GameAppointmentVo();
        gameAppointmentVo.setGameid(gameid);
        gameAppointmentVo.setGamename(gamename);
        gameAppointmentVo.setGame_type(game_type);
        gameAppointmentVo.setGameicon(gameicon);
        gameAppointmentVo.setTop_labels(top_labels);
        gameAppointmentVo.setClient_size(client_size);
        gameAppointmentVo.setAppointment_count(appointment_count);
        gameAppointmentVo.setGame_status(game_status);
        gameAppointmentVo.setOnline_text(online_text);
        gameAppointmentVo.setAppointment_begintime(appointment_begintime);
        return gameAppointmentVo;
    }


    /**
     * 2020.03.27新增
     */

    /**
     * is_refund=1时表示此游戏充值可以规定时间内无条件退款;
     * is_refund=0时表示充值不可退款
     */
    private int is_refund;

    public boolean isCanRefund() {
        return is_refund == 1 && game_type == 1;
    }


    /**
     * 2020.08.28新增
     */

    private int is_first_free;

    public int getIs_first_free() {
        return is_first_free;
    }


    private int is_first;

    public int getIs_first() {
        return is_first;
    }


    /**
     * 游戏大图
     */
    private String bg_pic;

    public String getBg_pic() {
        return bg_pic;
    }

    /****2021.06.08***************************************/

    public static class ServerInfoVo implements Serializable {
        private int    serverid;
        private String servername;
        private long   begintime;

        public int getServerid() {
            return serverid;
        }

        public void setServerid(int serverid) {
            this.serverid = serverid;
        }

        public String getServername() {
            return servername;
        }

        public void setServername(String servername) {
            this.servername = servername;
        }

        public long getBegintime() {
            return begintime;
        }

        public void setBegintime(long begintime) {
            this.begintime = begintime;
        }
    }

    private ServerInfoVo serverinfo;

    public ServerInfoVo getServerInfo() {
        return serverinfo;
    }

    /**
     * 1: 广告游戏状态正常;
     * 0:失效
     */
    private int status;

    public int getStatus() {
        return status;
    }

    private String vip_label;

    public String getVip_label() {
        return vip_label;
    }

    public String server_str;

    public long next_server_time;

    public long getNext_server_time() {
        return next_server_time;
    }

    public void setNext_server_time(long next_server_time) {
        this.next_server_time = next_server_time;
    }

    public String getServer_str() {
        return server_str;
    }

    private String jingxuan_label;

    public String getJingxuan_label() {
        return jingxuan_label;
    }

    private String coin_label;

    public String getCoin_label() {
        return coin_label;
    }

    public YouhuiBean getYouhui() {
        return youhui;
    }

    public void setYouhui(YouhuiBean youhui) {
        this.youhui = youhui;
    }

    public String getWhole_enable() {
        return whole_enable;
    }

    public void setWhole_enable(String whole_enable) {
        this.whole_enable = whole_enable;
    }

    public List<CustomLabelBean> getCustom_label() {
        return custom_label;
    }

    public void setCustom_label(List<CustomLabelBean> custom_label) {
        this.custom_label = custom_label;
    }

    public List<CouponListBean> getCoupon_list() {
        return coupon_list;
    }

    public void setCoupon_list(List<CouponListBean> coupon_list) {
        this.coupon_list = coupon_list;
    }

    public boolean isShow_short_comment_list() {
        return show_short_comment_list;
    }

    public void setShow_short_comment_list(boolean show_short_comment_list) {
        this.show_short_comment_list = show_short_comment_list;
    }

    public GameShortCommentVo getShort_comment_list() {
        GameShortCommentVo gameShortCommentVo = new GameShortCommentVo();
        gameShortCommentVo.setShort_comment_list(short_comment_list);
        return gameShortCommentVo;
    }

    public void setShort_comment_list(List<CommentInfoVo.DataBean> short_comment_list) {
        this.short_comment_list = short_comment_list;
    }

    private DownloadControl download_control;

    public DownloadControl getDownload_control() {
        return download_control;
    }

    public void setDownload_control(DownloadControl download_control) {
        this.download_control = download_control;
    }

    public static class DownloadControl implements Serializable{
        private int download_control;//是否启用下载控制，1 是，0 不是
        private String btn_text;//下载按钮文字
        private String dialog_content;//弹窗内容文案
        private int open_download;//是否开启下载，1 是，0 不是
        private String customer;//客服信息

        private String advance_download; //新增字段 1 开启预下载 0 关闭预下载
        private String advance_download_start_time; //开启预下载开始时间
        private String advance_download_end_time; //开启预下载结束时间

        public String getAdvance_download() {
            return advance_download;
        }

        public void setAdvance_download(String advance_download) {
            this.advance_download = advance_download;
        }

        public String getAdvance_download_start_time() {
            return advance_download_start_time;
        }

        public void setAdvance_download_start_time(String advance_download_start_time) {
            this.advance_download_start_time = advance_download_start_time;
        }

        public String getAdvance_download_end_time() {
            return advance_download_end_time;
        }

        public void setAdvance_download_end_time(String advance_download_end_time) {
            this.advance_download_end_time = advance_download_end_time;
        }

        public int getDownload_control() {
            return download_control;
        }

        public void setDownload_control(int download_control) {
            this.download_control = download_control;
        }

        public String getBtn_text() {
            return btn_text;
        }

        public void setBtn_text(String btn_text) {
            this.btn_text = btn_text;
        }

        public String getDialog_content() {
            return dialog_content;
        }

        public void setDialog_content(String dialog_content) {
            this.dialog_content = dialog_content;
        }

        public int getOpen_download() {
            return open_download;
        }

        public void setOpen_download(int open_download) {
            this.open_download = open_download;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }
    }

    private boolean show_item = true;

    public boolean isShow_item() {
        return show_item;
    }

    public void setShow_item(boolean show_item) {
        this.show_item = show_item;
    }

    private int game_mode;//1 横屏 2 竖屏   0 未知

    public int getGame_model() {
        return game_mode;
    }

    public void setGame_model(int game_mode) {
        this.game_mode = game_mode;
    }

    private RebateCustomLabel rebate_custom_label;//推荐标签

    private String data_exchange;//双端数据互通 0 未设置 1 互通 2 不互通

    private List<RecommendInfo> recommend_info;//自定义推荐

    private boolean flb_usage;//是否可使用福利币

    private int lsb_status;//648礼包状态 0 没有648礼包；1 可以用  10 已领取

    private String client_version_name;//游戏版本

    private VendorInfo vendor_info;//游戏厂商信息

    private VipNews vip_news;//vip等级表信息
    private LsbCardInfo lsb_card_info;//如果不为null，说明有648礼包

    private String share_url;//分享地址

    private boolean use_discount_coupon;//是否可以使用特惠卡优惠券

    private int accelerate_status;//是否支持加速
    private String record_number;//备案号

    public String getRecord_number() {
        return record_number;
    }

    public void setRecord_number(String record_number) {
        this.record_number = record_number;
    }

    public int getAccelerate_status() {
        return accelerate_status;
    }

    public void setAccelerate_status(int accelerate_status) {
        this.accelerate_status = accelerate_status;
    }

    public boolean isUse_discount_coupon() {
        return use_discount_coupon;
    }

    public void setUse_discount_coupon(boolean use_discount_coupon) {
        this.use_discount_coupon = use_discount_coupon;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public RebateCustomLabel getRebate_custom_label() {
        return rebate_custom_label;
    }

    public void setRebate_custom_label(RebateCustomLabel rebate_custom_label) {
        this.rebate_custom_label = rebate_custom_label;
    }

    public String getData_exchange() {
        return data_exchange;
    }

    public void setData_exchange(String data_exchange) {
        this.data_exchange = data_exchange;
    }

    public List<RecommendInfo> getRecommend_info() {
        return recommend_info;
    }

    public void setRecommend_info(List<RecommendInfo> recommend_info) {
        this.recommend_info = recommend_info;
    }

    public boolean isFlb_usage() {
        return flb_usage;
    }

    public void setFlb_usage(boolean flb_usage) {
        this.flb_usage = flb_usage;
    }

    public int getLsb_status() {
        return lsb_status;
    }

    public void setLsb_status(int lsb_status) {
        this.lsb_status = lsb_status;
    }

    public String getClient_version_name() {
        return client_version_name;
    }

    public void setClient_version_name(String client_version_name) {
        this.client_version_name = client_version_name;
    }

    public VipNews getVip_news() {
        return vip_news;
    }

    public void setVip_news(VipNews vip_news) {
        this.vip_news = vip_news;
    }

    public LsbCardInfo getLsb_card_info() {
        return lsb_card_info;
    }

    public void setLsb_card_info(LsbCardInfo lsb_card_info) {
        this.lsb_card_info = lsb_card_info;
    }

    public VendorInfo getVendor_info() {
        return vendor_info;
    }

    public void setVendor_info(VendorInfo vendor_info) {
        this.vendor_info = vendor_info;
    }

    public static class RebateCustomLabel{
        private String tip;
        private String label;
        private String color;

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public String getLable() {
            return label;
        }

        public void setLable(String label) {
            this.label = label;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public static class RecommendInfo{
        private String type;
        private String pic;
        private String title;
        private String description;
        private String page_type;
        private AppBaseJumpInfoBean.ParamBean param;
        private TryGameItemVo.DataBean trial_info;
        private YouhuiBean youhui;
        private String demo_task_url;//试玩任务网址

        public String getDemo_task_url() {
            return demo_task_url;
        }

        public void setDemo_task_url(String demo_task_url) {
            this.demo_task_url = demo_task_url;
        }

        public TryGameItemVo.DataBean getTrial_info() {
            return trial_info;
        }

        public void setTrial_info(TryGameItemVo.DataBean trial_info) {
            this.trial_info = trial_info;
        }

        public YouhuiBean getYouhui() {
            return youhui;
        }

        public void setYouhui(YouhuiBean youhui) {
            this.youhui = youhui;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPage_type() {
            return page_type;
        }

        public void setPage_type(String page_type) {
            this.page_type = page_type;
        }

        public AppBaseJumpInfoBean.ParamBean getParam() {
            return param;
        }

        public void setParam(AppBaseJumpInfoBean.ParamBean param) {
            this.param = param;
        }
    }

    public static class VendorInfo{
        private String vid;
        private String cpname;
        private String power_url;
        private String privacy_url;

        private String client_version_name;
        private String record_number;

        public String getRecord_number() {
            return record_number;
        }

        public void setRecord_number(String record_number) {
            this.record_number = record_number;
        }

        public String getClient_version_name() {
            return client_version_name;
        }

        public void setClient_version_name(String client_version_name) {
            this.client_version_name = client_version_name;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getCpname() {
            return CommonUtils.getGamename(cpname);
        }

        public void setCpname(String cpname) {
            this.cpname = cpname;
        }

        public String getPower_url() {
            return power_url;
        }

        public void setPower_url(String power_url) {
            this.power_url = power_url;
        }

        public String getPrivacy_url() {
            return privacy_url;
        }

        public void setPrivacy_url(String privacy_url) {
            this.privacy_url = privacy_url;
        }
    }

    public static class VipNews{
        private String id;
        private String title;
        private String title2;
        private String endtime;
        private String typeid;
        private String begintime;
        private String fabutime;
        private String laiyuan;
        private String is_newest;
        private String rebate_apply_type;
        private String url;
        private String news_status;
        private String rebate_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle2() {
            return title2;
        }

        public void setTitle2(String title2) {
            this.title2 = title2;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public String getBegintime() {
            return begintime;
        }

        public void setBegintime(String begintime) {
            this.begintime = begintime;
        }

        public String getFabutime() {
            return fabutime;
        }

        public void setFabutime(String fabutime) {
            this.fabutime = fabutime;
        }

        public String getLaiyuan() {
            return laiyuan;
        }

        public void setLaiyuan(String laiyuan) {
            this.laiyuan = laiyuan;
        }

        public String getIs_newest() {
            return is_newest;
        }

        public void setIs_newest(String is_newest) {
            this.is_newest = is_newest;
        }

        public String getRebate_apply_type() {
            return rebate_apply_type;
        }

        public void setRebate_apply_type(String rebate_apply_type) {
            this.rebate_apply_type = rebate_apply_type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getNews_status() {
            return news_status;
        }

        public void setNews_status(String news_status) {
            this.news_status = news_status;
        }

        public String getRebate_url() {
            return rebate_url;
        }

        public void setRebate_url(String rebate_url) {
            this.rebate_url = rebate_url;
        }
    }

    public static class LsbCardInfo{
        private String cardid;
        private String gameid;
        private String cardname;
        private String begintime;
        private String endtime;
        private String sort;
        private String cardusage;
        private String cardcontent;
        private String cardcountall;
        private String cardkucun;
        private String need_pay_total;
        private String card_type;
        private String need_pay_type;
        private String need_pay_begin;
        private String need_pay_end;
        private String sign;
        private String youxiaoqi;
        private String libaokucun;
        private String raw_card_type;
        private String label;
        private String is_get_card;
        private String card;//如果不是空字符串，则领过

        public String getCardid() {
            return cardid;
        }

        public void setCardid(String cardid) {
            this.cardid = cardid;
        }

        public String getGameid() {
            return gameid;
        }

        public void setGameid(String gameid) {
            this.gameid = gameid;
        }

        public String getCardname() {
            return cardname;
        }

        public void setCardname(String cardname) {
            this.cardname = cardname;
        }

        public String getBegintime() {
            return begintime;
        }

        public void setBegintime(String begintime) {
            this.begintime = begintime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getCardusage() {
            return cardusage;
        }

        public void setCardusage(String cardusage) {
            this.cardusage = cardusage;
        }

        public String getCardcontent() {
            return cardcontent;
        }

        public void setCardcontent(String cardcontent) {
            this.cardcontent = cardcontent;
        }

        public String getCardcountall() {
            return cardcountall;
        }

        public void setCardcountall(String cardcountall) {
            this.cardcountall = cardcountall;
        }

        public String getCardkucun() {
            return cardkucun;
        }

        public void setCardkucun(String cardkucun) {
            this.cardkucun = cardkucun;
        }

        public String getNeed_pay_total() {
            return need_pay_total;
        }

        public void setNeed_pay_total(String need_pay_total) {
            this.need_pay_total = need_pay_total;
        }

        public String getCard_type() {
            return card_type;
        }

        public void setCard_type(String card_type) {
            this.card_type = card_type;
        }

        public String getNeed_pay_type() {
            return need_pay_type;
        }

        public void setNeed_pay_type(String need_pay_type) {
            this.need_pay_type = need_pay_type;
        }

        public String getNeed_pay_begin() {
            return need_pay_begin;
        }

        public void setNeed_pay_begin(String need_pay_begin) {
            this.need_pay_begin = need_pay_begin;
        }

        public String getNeed_pay_end() {
            return need_pay_end;
        }

        public void setNeed_pay_end(String need_pay_end) {
            this.need_pay_end = need_pay_end;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getYouxiaoqi() {
            return youxiaoqi;
        }

        public void setYouxiaoqi(String youxiaoqi) {
            this.youxiaoqi = youxiaoqi;
        }

        public String getLibaokucun() {
            return libaokucun;
        }

        public void setLibaokucun(String libaokucun) {
            this.libaokucun = libaokucun;
        }

        public String getRaw_card_type() {
            return raw_card_type;
        }

        public void setRaw_card_type(String raw_card_type) {
            this.raw_card_type = raw_card_type;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getIs_get_card() {
            return is_get_card;
        }

        public void setIs_get_card(String is_get_card) {
            this.is_get_card = is_get_card;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }
    }
}
