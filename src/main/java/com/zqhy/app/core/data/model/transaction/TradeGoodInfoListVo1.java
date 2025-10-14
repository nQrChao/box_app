package com.zqhy.app.core.data.model.transaction;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 */
public class TradeGoodInfoListVo1 extends BaseVo {

    private List<TradeGoodInfoVo1> data;

    public List<TradeGoodInfoVo1> getData() {
        if (data!=null){
            if (data.size()==0) {
                data=null;
            }
        }
        return data;
    }
}
