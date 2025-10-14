package com.zqhy.app.core.data.model.cloud;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.rebate.RebateInfoVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/15
 */

public class CloudCourseVo extends BaseVo{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean {
        private String title;
        private String content;

        private boolean isUpfold;

        public boolean isUpfold() {
            return isUpfold;
        }

        public void setUpfold(boolean upfold) {
            isUpfold = upfold;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}
