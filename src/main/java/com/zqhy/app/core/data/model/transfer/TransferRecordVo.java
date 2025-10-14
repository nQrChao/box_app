package com.zqhy.app.core.data.model.transfer;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class TransferRecordVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {


        /**
         * applytime : 1541642400
         * c1 : 10
         * c2 : 20
         * c2_more : 新游消费：20元
         * cardcontent : 7级生命魔核*1，超级进阶宝盒*2，超级声望宝盒*2，云贝碎片*80
         * cardusage : 点击游戏右下角【系统】图标，点击【礼物】按钮后输入兑换码（只输入16位数字兑换码）即可！ 输入兑换码后，礼包将以邮件形式发送，请点击聊天框【邮件】图标后在附件中领取
         * endtime : 1543642400
         * ex_more : 提交申请后，20小时内游戏充值满20元
         * ex_sx : 20
         * ex_total : 20
         * gameid : 13
         * gamename : 丰都夜行路BT版
         * id : 1
         * pay_total : 0
         * reward_card_id : 31
         * reward_id : 1
         * reward_provide : 奖励通过游戏内邮件，或发放到申请的角色内
         * reward_provide2 : 满足奖励要求后，48小时内自动发放奖励，如有延迟，请联系客服。
         * reward_rate : 0
         * reward_sx : 0
         * reward_type : 2
         * role_id : 0
         * rolename : fq
         * servername : 1
         * status : 10
         * time_between : 11-08 10:00   至   12-01 13:33
         * time_tip : 活动时间
         * togame_total : 10.00
         * uid : 1
         * xh_username : 小号1
         */

        private long applytime;
        private int c1;
        private int c2;
        private String c2_more;
        private String cardcontent;
        private String cardusage;
        private long endtime;
        private String ex_more;
        private int ex_sx;
        private int ex_total;
        private int gameid;
        private String gamename;
        private int id;
        private int pay_total;
        private int reward_card_id;
        private String reward_id;
        private String reward_provide;
        private String reward_provide2;
        private int reward_rate;
        private int reward_sx;
        private int reward_type;
        private String role_id;
        private String rolename;
        private String servername;
        private int status;
        private String time_between;
        private String time_tip;
        private float togame_total;
        private int uid;
        private String xh_username;

        private String reward_content;
        private String card_id;

        public String getCard_id() {
            return card_id;
        }

        public void setCard_id(String card_id) {
            this.card_id = card_id;
        }

        public String getReward_content() {
            return reward_content;
        }

        public void setReward_content(String reward_content) {
            this.reward_content = reward_content;
        }

        public long getApplytime() {
            return applytime;
        }

        public void setApplytime(long applytime) {
            this.applytime = applytime;
        }

        public int getC1() {
            return c1;
        }

        public void setC1(int c1) {
            this.c1 = c1;
        }

        public int getC2() {
            return c2;
        }

        public void setC2(int c2) {
            this.c2 = c2;
        }

        public String getC2_more() {
            return c2_more;
        }

        public void setC2_more(String c2_more) {
            this.c2_more = c2_more;
        }

        public String getCardcontent() {
            return cardcontent;
        }

        public void setCardcontent(String cardcontent) {
            this.cardcontent = cardcontent;
        }

        public String getCardusage() {
            return cardusage;
        }

        public void setCardusage(String cardusage) {
            this.cardusage = cardusage;
        }

        public long getEndtime() {
            return endtime;
        }

        public void setEndtime(long endtime) {
            this.endtime = endtime;
        }

        public String getEx_more() {
            return ex_more;
        }

        public void setEx_more(String ex_more) {
            this.ex_more = ex_more;
        }

        public int getEx_sx() {
            return ex_sx;
        }

        public void setEx_sx(int ex_sx) {
            this.ex_sx = ex_sx;
        }

        public int getEx_total() {
            return ex_total;
        }

        public void setEx_total(int ex_total) {
            this.ex_total = ex_total;
        }

        public int getGameid() {
            return gameid;
        }

        public void setGameid(int gameid) {
            this.gameid = gameid;
        }

        public String getGamename() {
            return gamename;
        }

        public void setGamename(String gamename) {
            this.gamename = gamename;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPay_total() {
            return pay_total;
        }

        public void setPay_total(int pay_total) {
            this.pay_total = pay_total;
        }

        public int getReward_card_id() {
            return reward_card_id;
        }

        public void setReward_card_id(int reward_card_id) {
            this.reward_card_id = reward_card_id;
        }

        public String getReward_id() {
            return reward_id;
        }

        public void setReward_id(String reward_id) {
            this.reward_id = reward_id;
        }

        public String getReward_provide() {
            return reward_provide;
        }

        public void setReward_provide(String reward_provide) {
            this.reward_provide = reward_provide;
        }

        public String getReward_provide2() {
            return reward_provide2;
        }

        public void setReward_provide2(String reward_provide2) {
            this.reward_provide2 = reward_provide2;
        }

        public int getReward_rate() {
            return reward_rate;
        }

        public void setReward_rate(int reward_rate) {
            this.reward_rate = reward_rate;
        }

        public int getReward_sx() {
            return reward_sx;
        }

        public void setReward_sx(int reward_sx) {
            this.reward_sx = reward_sx;
        }

        public int getReward_type() {
            return reward_type;
        }

        public void setReward_type(int reward_type) {
            this.reward_type = reward_type;
        }

        public String getRole_id() {
            return role_id;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public String getRolename() {
            return rolename;
        }

        public void setRolename(String rolename) {
            this.rolename = rolename;
        }

        public String getServername() {
            return servername;
        }

        public void setServername(String servername) {
            this.servername = servername;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTime_between() {
            return time_between;
        }

        public void setTime_between(String time_between) {
            this.time_between = time_between;
        }

        public String getTime_tip() {
            return time_tip;
        }

        public void setTime_tip(String time_tip) {
            this.time_tip = time_tip;
        }

        public float getTogame_total() {
            return togame_total;
        }

        public void setTogame_total(float togame_total) {
            this.togame_total = togame_total;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getXh_username() {
            return xh_username;
        }

        public void setXh_username(String xh_username) {
            this.xh_username = xh_username;
        }
    }
}
