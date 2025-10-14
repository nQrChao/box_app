package com.zqhy.app.core.data.model.transaction;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 */
public class TradeMySellListVo extends BaseVo {

    private List<TradeMySellInfoVo> data;

    public List<TradeMySellInfoVo> getData() {
        return data;
    }
}
