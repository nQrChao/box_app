package com.zqhy.app.core.data.model.transfer;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class TransferRecordListVo extends BaseVo {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean {
        /**
         * id : 193
         * uid : 9
         * username : 13129963275
         * gameid : 3075
         * type : 2
         * apply_id : 10
         * points : -1
         * balance : 499
         * add_time : 1514859537
         * grant_time : 0
         * status : 10
         * remark :
         * gamename : 光明之巅
         */

        private String id;
        private String uid;
        private String username;
        private int gameid;

        /**
         * type = 1 下架补偿
         * type = 2 兑换奖励
         * type = 3 后台添加
         */
        private int type;
        private String apply_id;
        private float points;
        private String balance;
        private long add_time;
        private String grant_time;
        private String status;
        private String remark;
        private String gamename;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getGameid() {
            return gameid;
        }

        public void setGameid(int gameid) {
            this.gameid = gameid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getApply_id() {
            return apply_id;
        }

        public void setApply_id(String apply_id) {
            this.apply_id = apply_id;
        }

        public float getPoints() {
            return points;
        }

        public void setPoints(float points) {
            this.points = points;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public String getGrant_time() {
            return grant_time;
        }

        public void setGrant_time(String grant_time) {
            this.grant_time = grant_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getGamename() {
            return gamename;
        }

        public void setGamename(String gamename) {
            this.gamename = gamename;
        }
    }

}
