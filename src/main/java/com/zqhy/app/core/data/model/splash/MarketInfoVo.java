package com.zqhy.app.core.data.model.splash;

import com.chaoji.im.data.model.AppletsData;
import com.kunminx.architecture.ui.callback.UnPeekLiveData;
import com.zqhy.app.core.data.model.BaseVo;

public class MarketInfoVo extends BaseVo {

    private UnPeekLiveData<AppletsData> data;

    public UnPeekLiveData<AppletsData> getData() {
        return data;
    }

}
