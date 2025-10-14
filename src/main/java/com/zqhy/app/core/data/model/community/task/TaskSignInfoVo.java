package com.zqhy.app.core.data.model.community.task;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;

import java.util.List;

/**
 * @author Administrator
 */
public class TaskSignInfoVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean{

        /**
         * continued_days : 0
         * got_intergral : 0
         * intergral : 0
         * sign_intergral : 10
         * sign_list : [{"day_time":"02-04","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-05","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-06","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-07","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-08","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-09","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-10","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-11","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-12","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-13","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-14","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-15","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-16","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-17","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-18","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-19","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-20","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-21","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-22","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-23","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-24","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-25","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-26","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-27","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"02-28","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"03-01","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"03-02","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"03-03","intergral":0,"is_sign":-1,"is_today":0},{"day_time":"今天","intergral":0,"is_sign":-1,"is_today":1},{"day_time":"明天","intergral":0,"is_sign":-1,"is_today":2}]
         * signed_days : 0
         */

        private int continued_days;
        private int got_integral;
        private int integral;
        private int sign_integral;
        private int signed_days;
        private List<SignListBean> sign_list;

        public int getContinued_days() {
            return continued_days;
        }

        public void setContinued_days(int continued_days) {
            this.continued_days = continued_days;
        }

        public int getGot_integral() {
            return got_integral;
        }

        public void setGot_integral(int got_integral) {
            this.got_integral = got_integral;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public int getSign_integral() {
            return sign_integral;
        }

        public void setSign_integral(int sign_integral) {
            this.sign_integral = sign_integral;
        }

        public int getSigned_days() {
            return signed_days;
        }

        public void setSigned_days(int signed_days) {
            this.signed_days = signed_days;
        }

        public List<SignListBean> getSign_list() {
            return sign_list;
        }

        public void setSign_list(List<SignListBean> sign_list) {
            this.sign_list = sign_list;
        }

        private TaskAppJumpInfoBean sign_illustration;

        public TaskAppJumpInfoBean getSign_illustration() {
            return sign_illustration;
        }

        public void setSign_illustration(TaskAppJumpInfoBean sign_illustration) {
            this.sign_illustration = sign_illustration;
        }
    }

    public static class TaskAppJumpInfoBean extends AppJumpInfoBean{

        public TaskAppJumpInfoBean(String page_type, ParamBean param) {
            super(page_type, param);
        }

        private String illustration_pic;

        private String title;
        private String title2;

        public String getAd_pic() {
            return illustration_pic;
        }

        public void setAd_pic(String ad_pic) {
            this.illustration_pic = ad_pic;
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

    }

    public static class SignListBean {
        /**
         * day_time : 02-04
         * intergral : 0
         * is_sign : -1
         * is_today : 0
         */

        private String day_time;
        private int integral;
        private int is_sign;
        private int is_today;

        public String getDay_time() {
            return day_time;
        }

        public void setDay_time(String day_time) {
            this.day_time = day_time;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public int getIs_sign() {
            return is_sign;
        }

        public void setIs_sign(int is_sign) {
            this.is_sign = is_sign;
        }

        public int getIs_today() {
            return is_today;
        }

        public void setIs_today(int is_today) {
            this.is_today = is_today;
        }

        public boolean isToday() {
            return is_today == 1;
        }
        public boolean isTomorrow() {
            return is_today == 2;
        }
    }
}
