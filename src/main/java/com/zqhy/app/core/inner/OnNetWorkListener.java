package com.zqhy.app.core.inner;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author Administrator
 * @date 2018/12/4
 */

public interface OnNetWorkListener<T extends BaseVo> {

    void onBefore();

    void onFailure(String message);

    void onSuccess(T data);

    void onAfter();
}
