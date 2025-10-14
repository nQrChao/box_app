package com.zqhy.app.core.data.model.user;

import com.zqhy.app.core.data.model.BaseVo;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/2
 */

public class UserInfoVo extends BaseVo {

    private DataBean data;

    public static class DataBean {


        /**
         * uid : 13
         * username : lihan2734
         * is_special : 0
         * token : e17fe80aa77ed2b510cb5b2692017831
         * auth : 9d8bcac7ed564e7ae22bf9456fbc2e63
         */

        private int    uid;
        private String username;
        private int    is_special;
        private String token;
        private String auth;
        private int    is_regular;


        /**
         * uid : 13
         * mobile : null
         * tgid : 999
         * pingtaibi : 0
         * integral : 0
         * real_name : null
         * idcard : null
         */

        private String mobile;
        private String tgid;
        private float  pingtaibi;
        private int    integral;
        private String real_name;
        private String idcard;


        private int invite_type;

        /**
         * 平台币中的人民币部分
         */
        private float ptb_rmb;
        /**
         * 平台币中的非人民币部分
         */
        private float ptb_dc;

        private boolean is_oversea_mobile;//回收时是否需要验证手机号

        public boolean isIs_oversea_mobile() {
            return is_oversea_mobile;
        }

        public void setIs_oversea_mobile(boolean is_oversea_mobile) {
            this.is_oversea_mobile = is_oversea_mobile;
        }

        private String password;

        private boolean can_bind_password;

        private String act;//当前登录类型 register注册 login登录

        private int elevate; //是否上报注册行为 1上报

        public int getElevate() {
            return elevate;
        }

        public void setElevate(int elevate) {
            this.elevate = elevate;
        }

        public String getAct() {
            return act;
        }

        public void setAct(String act) {
            this.act = act;
        }

        public boolean isCan_bind_password() {
            return can_bind_password;
        }

        public void setCan_bind_password(boolean can_bind_password) {
            this.can_bind_password = can_bind_password;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "uid='" + uid + '\'' +
                    ", username='" + username + '\'' +
                    '}';
        }

        public int getUid() {
            return uid;
        }

        public String getUsername() {
            return username;
        }

        public int getIs_special() {
            return is_special;
        }

        public String getToken() {
            return token;
        }

        public String getAuth() {
            return auth;
        }

        public String getMobile() {
            return mobile;
        }

        public String getTgid() {
            return tgid;
        }

        public float getPingtaibi() {
            return pingtaibi;
        }

        public int getIntegral() {
            return integral;
        }

        public String getReal_name() {
            return real_name;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setIs_special(int is_special) {
            this.is_special = is_special;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setTgid(String tgid) {
            this.tgid = tgid;
        }

        public void setPingtaibi(float pingtaibi) {
            this.pingtaibi = pingtaibi;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public int getInvite_type() {
            //半分享测试
            //            if("jiuyao001".equals(username)){
            //                return 1;
            //            }
            return invite_type;
        }

        public void setInvite_type(int invite_type) {
            this.invite_type = invite_type;
        }

        public float getPtb_rmb() {
            return ptb_rmb;
        }

        public float getPtb_dc() {
            return ptb_dc;
        }

        public int getIs_regular() {
            return is_regular;
        }

        public boolean isOlderUserByVersion9() {
            return is_regular == 1;
        }

        private int recall_pop;

        public boolean isReCallUser() {
            return recall_pop == 1;
        }

        public void setRecall_pop(int recall_pop) {
            this.recall_pop = recall_pop;
        }

        public int getRecall_pop() {
            return recall_pop;
        }

        /******2019.03.05 新增字段**************************************************************************************************/

        /**
         * 用户昵称
         */
        private String user_nickname;

        /**
         * 用户头像
         */
        private String user_icon;

        /**
         * 用户活跃数
         */
        private int act_num;

        /**
         * 用户等级,例如 1,2,3…
         */
        private int user_level;

        public String getUser_nickname() {
            return user_nickname;
        }

        public String getUser_icon() {
            return user_icon;
        }

        public int getAct_num() {
            return act_num;
        }

        public int getUser_level() {
            return user_level;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public void setUser_icon(String user_icon) {
            this.user_icon = user_icon;
        }

        public void setAct_num(int act_num) {
            this.act_num = act_num;
        }

        public void setUser_level(int user_level) {
            this.user_level = user_level;
        }

        private VipMemberVo vip_member;

        public VipMemberVo getVip_member() {
            return vip_member;
        }

        public void setVip_member(VipMemberVo vip_member) {
            this.vip_member = vip_member;
        }

        private VipInfoVo vip_info;

        public VipInfoVo getVip_info() {
            return vip_info;
        }

        public void setVip_info(VipInfoVo vip_info) {
            this.vip_info = vip_info;
        }

        private SuperUser super_user;
        private MoneyCard money_card;
        private String adult;

        public String getAdult() {
            return adult;
        }

        public void setAdult(String adult) {
            this.adult = adult;
        }

        public SuperUser getSuper_user() {
            return super_user;
        }

        public void setSuper_user(SuperUser super_user) {
            this.super_user = super_user;
        }

        public MoneyCard getMoney_card() {
            return money_card;
        }

        public void setMoney_card(MoneyCard money_card) {
            this.money_card = money_card;
        }
    }

    public static class VipMemberVo {

        /**
         * 会员状态，1 月卡 0 非月卡
         */
        private int vip_member_status;

        /**
         * 会员到期时间，精确到秒
         */
        private long vip_member_expire;

        private int received;

        public int getReceived() {
            return received;
        }

        public void setReceived(int received) {
            this.received = received;
        }

        public int getVip_member_status() {
            return vip_member_status;
        }

        public boolean isVipMember() {
            return vip_member_status == 1;
        }

        public long getVip_member_expire() {
            return vip_member_expire;
        }
    }

    public static class VipInfoVo implements Serializable {

        /**
         * vip_level : 1
         * vip_score : 220
         */

        /**
         * 用户VIP等级
         */
        private int vip_level;
        /**
         * 用户Vip成长值
         */
        private int vip_score;

        /**
         * 用户vip下一等级
         */
        private int next_level;
        /**
         * 用户vip下一等级所需要的总成长值
         */
        private int next_level_score;

        /**
         * 用户当前vip等级所需的成长值
         */
        private int current_level_score;

        /**
         * 当值为1时表示有Vip特权礼券可领; 当值为0是表示没有礼券可领取
         */
        private List<String> has_coupon;

        public List<String> getHas_coupon() {
            return has_coupon;
        }

        public int getVip_level() {
            return vip_level;
        }

        public int getVip_score() {
            return vip_score;
        }

        public int getNext_level() {
            return next_level;
        }


        public int getNext_level_score() {
            return next_level_score;
        }

        public int getCurrent_level_score() {
            return current_level_score;
        }

        /**
         * 下一等级所需成长值
         *
         * @return
         */
        public int getScoreForNextLevel() {
            return next_level_score - vip_score;
        }

        /**
         * 下一等级所需成长值所需百分比
         *
         * @return
         */
        public int getPercentageScoreForNextLevel() {
            return next_level_score - current_level_score == 0 ? 100 : (vip_score - current_level_score) * 100 / (next_level_score - current_level_score);
        }
    }

    public DataBean getData() {
        return data;
    }

    private String loginAccount;

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public static class SuperUser{
        private String status;
        private String sign;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }

    public static class MoneyCard{
        private String status;
        private String get_reward;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGet_reward() {
            return get_reward;
        }

        public void setGet_reward(String get_reward) {
            this.get_reward = get_reward;
        }
    }
}
