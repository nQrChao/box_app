package com.zqhy.app.core.data.model.forum;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/15
 */

public class ForumCategoryVo extends BaseVo{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean {
        boolean click =false;

        public boolean isClick() {
            return click;
        }

        public void setClick(boolean click) {
            this.click = click;
        }

        private String name;
        private int sort;
        private int only_show;
        private int cate_id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSort() {
            return sort;
        }

        public int getOnly_show() {
            return only_show;
        }

        public void setOnly_show(int only_show) {
            this.only_show = only_show;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getCate_id() {
            return cate_id;
        }

        public void setCate_id(int cate_id) {
            this.cate_id = cate_id;
        }
    }

}
