package com.zqhy.app.core.data.model.game;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 */
public class GameAppointmentListVo extends BaseVo {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }


    public static class DataBean extends GameInfoVo {

        private String online_text;
        private long begintime;

        /**
         * 状态:
         * 0:初始状态,点击可以关注,
         * 1:已关注,点击可以取消关注,
         * 10:已结束,点击进入游戏详情
         */
        private int status;

        public String getOnline_text() {
            return online_text;
        }

        public long getBegintime() {
            return begintime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

}
