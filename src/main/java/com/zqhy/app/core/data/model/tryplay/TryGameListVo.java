package com.zqhy.app.core.data.model.tryplay;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 */
public class TryGameListVo extends BaseVo {

    private List<TryGameItemVo.DataBean> data;

    public List<TryGameItemVo.DataBean> getData() {
        return data;
    }
}
