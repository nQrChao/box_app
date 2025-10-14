package com.zqhy.app.core.data.model.kefu;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/22
 */

public class KefuPersionListVo extends BaseVo{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean{

        /**
         * kid : 1
         * kf_name : 露露
         * is_opinion : 0
         * type : 0
         */

        private String kid;
        private String kf_name;
        private int is_opinion;
        private int type;

        public String getKid() {
            return kid;
        }

        public void setKid(String kid) {
            this.kid = kid;
        }

        public String getKf_name() {
            return kf_name;
        }

        public void setKf_name(String kf_name) {
            this.kf_name = kf_name;
        }

        public int getIs_opinion() {
            return is_opinion;
        }

        public void setIs_opinion(int is_opinion) {
            this.is_opinion = is_opinion;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
