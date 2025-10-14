package com.zqhy.app.core.data.model.kefu;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/11
 */

public class FeedBackTypeListVo extends BaseVo{

    private FeedBackTypeVo data;

    public FeedBackTypeVo getData() {
        return data;
    }



    public static class FeedBackTypeVo{
        private List<DataBean> list;
        private int count;

        public List<DataBean> getList() {
            return list;
        }

        public int getCount() {
            return count;
        }
    }


    public static class DataBean{
        private String cate_id;
        private String name;

        public String getCate_id() {
            return cate_id;
        }

        public String getName() {
            return name;
        }
    }
}
