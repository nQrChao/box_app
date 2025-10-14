package com.zqhy.app.core.data.model.community.comment;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 */
public class ReplyListVo extends BaseVo {


    private List<CommentInfoVo.ReplyInfoVo> data;

    public List<CommentInfoVo.ReplyInfoVo> getData() {
        return data;
    }
}
