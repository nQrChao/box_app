package com.zqhy.app.core.data.model.rebate;

import com.zqhy.app.core.data.model.BaseVo;

/**
 *
 * @author Administrator
 * @date 2018/11/22
 */

public class RebateCommitVo extends BaseVo{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean{
        int apply_id;

        public int getApply_id() {
            return apply_id;
        }
    }
}
