package com.zqhy.app.core.data.model.user.newvip;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author leeham2734
 * @date 2021/2/3-12:11
 * @description
 */
public class ComeBackVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        private String id;
        private String is_come_back;
        private int day;
        private String hd_url;
        private long end_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIs_come_back() {
            return is_come_back;
        }

        public void setIs_come_back(String is_come_back) {
            this.is_come_back = is_come_back;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getHd_url() {
            return hd_url;
        }

        public void setHd_url(String hd_url) {
            this.hd_url = hd_url;
        }

        public long getEnd_time() {
            return end_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
        }
    }
}
