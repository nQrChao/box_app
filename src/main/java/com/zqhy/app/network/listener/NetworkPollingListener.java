package com.zqhy.app.network.listener;

/**
 *
 * @author Administrator
 * @date 2018/11/13
 */

public interface NetworkPollingListener {
    public final int POLLING_SUCCESS = 0;
    public final int POLLING_FAILURE = 1;

    void onSuccess();
    void onFailure();
}
