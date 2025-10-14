package com.zqhy.app.core.data.model.community.comment;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/11/20-14:01
 * @description
 */
public class CommentTypeListVo extends BaseVo {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean {

        /**
         * type_id : 1
         * name : 点评
         */

        private int    type_id;
        private int    comment_count;
        private String name;

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public DataBean(int type_id, int comment_count, String name) {
            this.type_id = type_id;
            this.comment_count = comment_count;
            this.name = name;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
