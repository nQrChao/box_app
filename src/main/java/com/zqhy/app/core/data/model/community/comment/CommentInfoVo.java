package com.zqhy.app.core.data.model.community.comment;

import android.text.TextUtils;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.community.CommunityInfoVo;
import com.zqhy.app.utils.CommonUtils;

import java.util.List;

/**
 * @author Administrator
 */
public class CommentInfoVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {

        /**
         * cid : 4
         * uid : 28300
         * content : 5ri45oiP5LiN6ZSZ77yM5q U6L6D6ICQ546p77yM6aKY5p2Q5Y v5Lul5LiN5a655piT5Y6M54Om
         * release_time : 1551837083
         * like_count : 0
         * reply_count : 0
         * reward_intergral_amount : 0
         * pics :
         * community_info : {"user_nickname":"手机用户1713","user_icon":"","act_num":"0","user_level":1}
         * replylist : null
         * me_like : 0
         */

        private int cid;
        private int uid;
        private String content;
        private int gameid;
        private String gamename;
        private String release_time;
        private String type_id;//评论类型，配合reward_integral字段显示勋章，1 点评  2 攻略
        private int like_count;
        private int reply_count;
        private int reward_integral;
        private List<PicInfoVo> pics;
        private CommunityInfoVo community_info;
        private List<ReplyInfoVo> reply_list;
        private int me_like;
        private int view_count;
        /**
         * cid : 4
         * uid : 28300
         * gameid : 1641
         * like_count : 4
         * reply_count : 3
         * reward_integral : 100
         * pics : null
         * game_type : 1
         * gameicon : http://p1.jiuyao666.com/2019/02/26/5c74f7a168fc4.png
         * genre_str : 卡牌·武侠
         */

        private int game_type;
        private String gameicon;
        private String genre_str;

        /**
         * 审核状态 1:通过; 0 未审核; -1: 未通过
         */
        private int verify_status;

        private String fail_reason;

        /**
         * 表示评论修改次数
         */
        private int modify_count;

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRelease_time() {
            return release_time;
        }

        public void setRelease_time(String release_time) {
            this.release_time = release_time;
        }

        public int getLike_count() {
            return like_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public int getReply_count() {
            return reply_count;
        }

        public void setReply_count(int reply_count) {
            this.reply_count = reply_count;
        }

        public int getGameid() {
            return gameid;
        }

        public int getReward_integral() {
            return reward_integral;
        }

        public List<PicInfoVo> getPics() {
            return pics;
        }

        public void setPics(List<PicInfoVo> pics) {
            this.pics = pics;
        }

        public CommunityInfoVo getCommunity_info() {
            return community_info;
        }

        public void setCommunity_info(CommunityInfoVo community_info) {
            this.community_info = community_info;
        }

        public List<ReplyInfoVo> getReply_list() {
            return reply_list;
        }

        public void setReply_list(List<ReplyInfoVo> replylist) {
            this.reply_list = replylist;
        }

        public int getMe_like() {
            return me_like;
        }

        public void setMe_like(int me_like) {
            this.me_like = me_like;
        }

        /**
         * 是否优质点评 2:是; 其他:否
         */
        private int hq_status;

        public int getHq_status() {
            if (reward_integral > 0) {
                hq_status = 2;
            }
            return hq_status;
        }

        public void setHq_status(int hq_status) {
            this.hq_status = hq_status;
        }

        public String getGamename() {
            return CommonUtils.getGamename(gamename);
        }

        public String getOtherGameName(){
            return CommonUtils.getOtherGameName(gamename);
        }

        public void setGamename(String gamename) {
            this.gamename = gamename;
        }

        public int getView_count() {
            return view_count;
        }

        public int getGame_type() {
            return game_type;
        }

        public void setGame_type(int game_type) {
            this.game_type = game_type;
        }

        public String getGameicon() {
            return gameicon;
        }

        public void setGameicon(String gameicon) {
            this.gameicon = gameicon;
        }

        public String getGenre_str() {
            return genre_str;
        }

        public void setGenre_str(String genre_str) {
            this.genre_str = genre_str;
        }

        public int getVerify_status() {
            return verify_status;
        }

        public void setVerify_status(int verify_status) {
            this.verify_status = verify_status;
        }

        public String getFail_reason() {
            return fail_reason;
        }

        public void setFail_reason(String fail_reason) {
            this.fail_reason = fail_reason;
        }

        public int getModify_count() {
            return modify_count;
        }

        public void setModify_count(int modify_count) {
            this.modify_count = modify_count;
        }
    }

    public static class PicInfoVo {

        /**
         * pic_id : 1
         * high_pic_path : http://p2user.jiuyao666.com/2019/03/06/5c7f271129d17.jpg
         * pic_path : http://p2user.jiuyao666.com/2019/03/06/5c7f271129d17_thumb.jpg
         */

        private int pic_id;
        private String high_pic_path;
        private String pic_path;

        public int getPic_id() {
            return pic_id;
        }

        public void setPic_id(int pic_id) {
            this.pic_id = pic_id;
        }

        public String getHigh_pic_path() {
            return high_pic_path;
        }

        public void setHigh_pic_path(String high_pic_path) {
            this.high_pic_path = high_pic_path;
        }

        public String getPic_path() {
            return pic_path;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }
    }

    public static class ReplyInfoVo {

        /**
         * rid : 1
         * uid : 28300
         * touid : 28300
         * content : 5ri45oiP5LiN6ZSZ77yM5q U6L6D6ICQ546p77yM6aKY5p2Q5Y v5Lul5LiN5a655piT5Y6M54Om
         * community_info : {"user_nickname":"手机用户1713","user_icon":"","act_num":"0","user_level":1}
         */

        private int cid;
        private int rid;
        private int uid;
        private int touid;
        private String content;
        private CommunityInfoVo community_info;
        private CommunityInfoVo to_community_info;

        private int like_count;
        private long reply_time;
        private int me_like;

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public int getRid() {
            return rid;
        }

        public int getUid() {
            return uid;
        }

        public int getTouid() {
            return touid;
        }

        public String getContent() {
            return content;
        }

        public CommunityInfoVo getCommunity_info() {
            return community_info;
        }

        public CommunityInfoVo getTo_community_info() {
            return to_community_info;
        }

        public int getLike_count() {
            return like_count;
        }

        public long getReply_time() {
            return reply_time;
        }

        public int getMe_like() {
            return me_like;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setCommunity_info(CommunityInfoVo community_info) {
            this.community_info = community_info;
        }

        public void setRid(int rid) {
            this.rid = rid;
        }

        public void setTouid(int touid) {
            this.touid = touid;
        }

        public void setTo_community_info(CommunityInfoVo to_community_info) {
            this.to_community_info = to_community_info;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public void setReply_time(long reply_time) {
            this.reply_time = reply_time;
        }

        public void setMe_like(int me_like) {
            this.me_like = me_like;
        }
    }
}
