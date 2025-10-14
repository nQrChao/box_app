package com.zqhy.app.core.data.model.game;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author Administrator
 * @date 2018/11/23
 */

public class GameDownloadUrlVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private String channel;
        private String apk_url;

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getApk_url() {
            return apk_url;
        }

        public void setApk_url(String apk_url) {
            this.apk_url = apk_url;
        }
    }
}
