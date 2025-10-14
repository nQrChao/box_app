package com.zqhy.app.core.data.model.community.qa;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 */
public class QaCenterQuestionListVo extends BaseVo {

    private List<UserQaCenterInfoVo.QaCenterQuestionVo> data;

    public List<UserQaCenterInfoVo.QaCenterQuestionVo> getData() {
        return data;
    }
}
