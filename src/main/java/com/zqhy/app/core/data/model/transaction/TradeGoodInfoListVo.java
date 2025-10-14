package com.zqhy.app.core.data.model.transaction;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 */
public class TradeGoodInfoListVo extends BaseVo {

    private List<TradeGoodInfoVo> data;

    public List<TradeGoodInfoVo> getData() {
        if (data!=null){
            if (data.size()==0) {
                data = null;
            }
        }
        return data;
    }
}
