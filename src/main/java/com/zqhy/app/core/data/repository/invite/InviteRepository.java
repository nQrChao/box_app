package com.zqhy.app.core.data.repository.invite;

import com.mvvm.http.rx.RxSchedulers;
import com.zqhy.app.config.URL;
import com.zqhy.app.core.data.BaseRepository;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.repository.splash.SplashRepository;
import com.zqhy.app.network.rx.RxSubscriber;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Administrator
 * @date 2018/11/23
 */

public class InviteRepository extends SplashRepository {


    /**
     * 分享类型, 1:分享app; 2:分享游戏; 3:分享个性页面
     *
     * @param type
     */
    public void addInviteShare(int type) {
        Map<String, String> params = new TreeMap<>();
        params.put("api", "add_invite");
        params.put("type", String.valueOf(type));

        String data = createPostData(params);
        addDisposable(iApiService.postClaimData(URL.getApiUrl(params),data)
                .compose(RxSchedulers.io_main())
                .subscribeWith(new RxSubscriber<BaseResponseVo>(params) {
                    @Override
                    public void onSuccess(BaseResponseVo baseResponseVo) {
                    }

                    @Override
                    public void onFailure(String msg) {
                    }
                }));
    }
}
