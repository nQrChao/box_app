package com.zqhy.app.core.data.model.recycle;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 */
public class XhRecycleRecordListVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {

        private float total;

        private List<XhRecycleRecordVo> list;

        public float getTotal() {
            return total;
        }

        public void setTotal(float total) {
            this.total = total;
        }

        public List<XhRecycleRecordVo> getXh_list() {
            return list;
        }

        public void setXh_list(List<XhRecycleRecordVo> xh_list) {
            this.list = xh_list;
        }
    }
}
