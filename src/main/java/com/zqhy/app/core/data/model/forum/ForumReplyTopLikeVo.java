package com.zqhy.app.core.data.model.forum;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/15
 *operation	string	操作, hit:点赞; cancel：取消点赞
 */

public class ForumReplyTopLikeVo extends BaseVo {
    ForumReplyTopLikeVo data;

    public ForumReplyTopLikeVo getData() {
        return data;
    }

    public void setData(ForumReplyTopLikeVo data) {
        this.data = data;
    }

    String operation;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
