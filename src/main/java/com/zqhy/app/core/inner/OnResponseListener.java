package com.zqhy.app.core.inner;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author Administrator
 * @date 2018/12/3
 */

public interface OnResponseListener<T extends BaseVo> {
    void onData(T data);
}
