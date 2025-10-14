package com.zqhy.app.core.data.model.user.newvip;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author leeham2734
 * @date 2021/2/3-12:11
 * @description
 */
public class SuperBirthdayRewardVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        /**
         * 生日奖励状态说明
         * now 立即领取，并显示奖励弹窗
         * past 过去生日，可以补领弹窗
         * is_get 生日奖励已领取
         * no_super_user 不是会员
         * no_cert 没有实名认证
         * no_yet 生日还没有到，也没有领取奖励
         */
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
