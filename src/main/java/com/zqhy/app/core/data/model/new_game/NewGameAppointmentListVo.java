package com.zqhy.app.core.data.model.new_game;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.appointment.GameAppointmentVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/8/28-17:36
 * @description
 */
public class NewGameAppointmentListVo extends BaseVo {

    private List<GameAppointmentVo> data;

    public List<GameAppointmentVo> getData() {
        return data;
    }
}
