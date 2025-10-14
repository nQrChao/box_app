package com.zqhy.app.share;

import com.bytedance.sdk.open.douyin.ShareToContact;

/**
 * @author leeham2734
 * @date 2020/9/23-10:49
 * @description
 */
public class DouYinShareEvent {
    private ShareToContact.Response mResponse;

    public DouYinShareEvent(ShareToContact.Response mResponse) {
        this.mResponse = mResponse;
    }

    public ShareToContact.Response getResponse() {
        return mResponse;
    }
}
