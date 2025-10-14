package com.zqhy.app.core.data.model.community.task;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;

import java.util.List;

/**
 * @author Administrator
 */
public class TaskSignInfoV2Vo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean{
        private String          day_task_finished;//是否完成了所有每日任务，yes是，no不是
        private String          try_game_reward;//试玩总积分
        private String          member_total;//省钱卡近期送出福利
        private int             today_week;//今天是周几
        private int              continued_days;//已累计签到多少天
        private int              today_integral;//今天签到应得积分
        private boolean              today_is_signed;//今天是否已签到
        private String              xr_banner;//新人福利banner图片地址
        private boolean              xr_task_end;//是否完成了新人任务
        private List<SignListBean>   sign_list;//签到数据列表
        private List<HdBlockBean>    hd_block;//下方两个活动小块的跳转信息
        private List<BannerListBean> banner_list;
        private String              trial_url;//试玩活动地址

        public String getTrial_url() {
            return trial_url;
        }

        public void setTrial_url(String trial_url) {
            this.trial_url = trial_url;
        }

        public String getDay_task_finished() {
            return day_task_finished;
        }

        public void setDay_task_finished(String day_task_finished) {
            this.day_task_finished = day_task_finished;
        }

        public String getTry_game_reward() {
            return try_game_reward;
        }

        public void setTry_game_reward(String try_game_reward) {
            this.try_game_reward = try_game_reward;
        }

        public String getMember_total() {
            return member_total;
        }

        public void setMember_total(String member_total) {
            this.member_total = member_total;
        }

        public int getToday_week() {
            return today_week;
        }

        public void setToday_week(int today_week) {
            this.today_week = today_week;
        }

        public int getContinued_days() {
            return continued_days;
        }

        public void setContinued_days(int continued_days) {
            this.continued_days = continued_days;
        }

        public int getToday_integral() {
            return today_integral;
        }

        public void setToday_integral(int today_integral) {
            this.today_integral = today_integral;
        }

        public boolean isToday_is_signed() {
            return today_is_signed;
        }

        public void setToday_is_signed(boolean today_is_signed) {
            this.today_is_signed = today_is_signed;
        }

        public String getXr_banner() {
            return xr_banner;
        }

        public void setXr_banner(String xr_banner) {
            this.xr_banner = xr_banner;
        }

        public boolean isXr_task_end() {
            return xr_task_end;
        }

        public void setXr_task_end(boolean xr_task_end) {
            this.xr_task_end = xr_task_end;
        }

        public List<SignListBean> getSign_list() {
            return sign_list;
        }

        public void setSign_list(List<SignListBean> sign_list) {
            this.sign_list = sign_list;
        }

        public List<HdBlockBean> getHd_block() {
            return hd_block;
        }

        public void setHd_block(List<HdBlockBean> hd_block) {
            this.hd_block = hd_block;
        }

        public List<BannerListBean> getBanner_list() {
            return banner_list;
        }

        public void setBanner_list(List<BannerListBean> banner_list) {
            this.banner_list = banner_list;
        }

        public static class SignListBean {
            private int week_num;
            private boolean is_signed;
            private int reward_integral;

            public int getWeek_num() {
                return week_num;
            }

            public void setWeek_num(int week_num) {
                this.week_num = week_num;
            }

            public boolean isIs_signed() {
                return is_signed;
            }

            public void setIs_signed(boolean is_signed) {
                this.is_signed = is_signed;
            }

            public int getReward_integral() {
                return reward_integral;
            }

            public void setReward_integral(int reward_integral) {
                this.reward_integral = reward_integral;
            }
        }

        public static class HdBlockBean {
            private String pic;
            private String title;
            private String description;
            private String page_type;
            private AppBaseJumpInfoBean.ParamBean param;

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

        public static class BannerListBean {
            private String pic;
            private String title;
            private String page_type;
            private AppBaseJumpInfoBean.ParamBean param;

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
    }
}
