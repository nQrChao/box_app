package com.zqhy.app.core.data.model.game.appointment;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;

import java.util.List;

/**
 * @author leeham
 * @date 2020/4/12-10:48
 * @description
 */
public class UserGameAppointmentVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean{

        private long last_timestamp;

        private List<GameInfoVo> data;

        public long getLast_timestamp() {
            return last_timestamp;
        }

        public void setLast_timestamp(long last_timestamp) {
            this.last_timestamp = last_timestamp;
        }

        public List<GameInfoVo> getData() {
            return data;
        }

        public void setData(List<GameInfoVo> data) {
            this.data = data;
        }
    }
}
