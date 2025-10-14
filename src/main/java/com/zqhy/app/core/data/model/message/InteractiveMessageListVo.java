package com.zqhy.app.core.data.model.message;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.community.CommunityInfoVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/14
 */

public class InteractiveMessageListVo extends BaseVo {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean {


        /**
         * id : 1
         * cid : 3
         * uid : 28300
         * touid : 28300
         * rid : 0
         * type : 1
         * add_time : 1524523651
         * content : 5ri45oiP5LiN6ZSZ77yM5q U6L6D6ICQ546p77yM6aKY5p2Q5Y v5Lul5LiN5a655piT5Y6M54Om
         * community_info : {"uid":1,"user_nickname":"手机用户1713","user_icon":"","act_num":"0","user_level":1}
         */

        private int id;
        private int cid;
        private int uid;
        private int touid;
        private int rid;

        /**
         * 1、评论点赞；2、回复；3、回复点赞；4，答案点赞
         */
        private int type;
        private long add_time;
        private String content;
        private int qid;

        private CommunityInfoVo community_info;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getQid() {
            return qid;
        }

        public void setQid(int qid) {
            this.qid = qid;
        }

        public int getTouid() {
            return touid;
        }

        public void setTouid(int touid) {
            this.touid = touid;
        }

        public int getRid() {
            return rid;
        }

        public void setRid(int rid) {
            this.rid = rid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public CommunityInfoVo getCommunity_info() {
            return community_info;
        }

        public void setCommunity_info(CommunityInfoVo community_info) {
            this.community_info = community_info;
        }
    }
}
