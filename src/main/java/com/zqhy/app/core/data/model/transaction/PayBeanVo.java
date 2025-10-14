package com.zqhy.app.core.data.model.transaction;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.user.PayInfoVo;

/**
 * @author Administrator
 */
public class PayBeanVo extends BaseVo {

    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public static class DataBean extends PayInfoVo.DataBean{

    }
}
