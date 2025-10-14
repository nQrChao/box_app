package com.zqhy.app.core.inner;

import com.zqhy.app.core.data.model.BaseVo;

/**
 *
 * @author Administrator
 * @date 2018/12/4
 */

public abstract class OnBaseCallback<T extends BaseVo> implements OnNetWorkListener<T>{
    @Override
    public void onAfter() {

    }

    @Override
    public void onBefore() {

    }

    @Override
    public void onFailure(String message) {

    }
}
