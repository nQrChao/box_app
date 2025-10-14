package com.zqhy.app.core.data.model.message;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/13
 */

public class MessageListVo extends BaseVo{

    private List<MessageInfoVo> data;

    public List<MessageInfoVo> getData() {
        return data;
    }
}
