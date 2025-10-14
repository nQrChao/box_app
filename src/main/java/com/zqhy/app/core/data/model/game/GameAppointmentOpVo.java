package com.zqhy.app.core.data.model.game;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author Administrator
 */
public class GameAppointmentOpVo extends BaseVo {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean{
        private String op;

        public String getOp() {
            return op;
        }
    }
}
