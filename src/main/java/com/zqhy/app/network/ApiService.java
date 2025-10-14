package com.zqhy.app.network;

import com.mvvm.http.HttpHelper;


/**
 * @author tqzhang
 */

public class ApiService {

    private static volatile ApiService mApiService = null;

    RxApiBuilder rxApiBuilder;

    private ApiService() {
        rxApiBuilder = new RxApiBuilder();
    }

    public static ApiService getInstance() {
        if (mApiService == null) {
            synchronized (HttpHelper.class) {
                if (mApiService == null) {
                    mApiService = new ApiService();
                }
            }
        }
        return mApiService;
    }

}
