package com.zqhy.app.core.data.model.community.comment;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 */
public class CommentListVo extends BaseVo {

    private List<CommentInfoVo.DataBean> data;

    public List<CommentInfoVo.DataBean> getData() {
        return data;
    }
}
