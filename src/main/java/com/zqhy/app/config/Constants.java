package com.zqhy.app.config;

import com.zqhy.app.App;
import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/1
 */

public class Constants {

    private static final int MAX_HEAP_SIZE         = (int) Runtime.getRuntime().maxMemory();
    public static final  int MAX_CACHE_MEMORY_SIZE = MAX_HEAP_SIZE / 4;
    public static final  int MAX_CACHE_DISK_SIZE   = 250 * 1024 * 1024;


    public static final String SP_COMMON_NAME = "SP_COMMON_NAME";


    public static final Object EVENT_KEY_MAIN_ACTIVITY_PAGE_STATE = "EVENT_KEY_MAIN_ACTIVITY_PAGE_STATE";


    public static final Object EVENT_KEY_REFRESH_API_SERVICE = "EVENT_KEY_REFRESH_API_SERVICE";

    public static final Object EVENT_KEY_APP_VERSION = "EVENT_KEY_APP_VERSION";

    public static final Object EVENT_KEY_WX_PAY_PLUG = "EVENT_KEY_WX_PAY_PLUG";

    public static final Object EVENT_KEY_USER_LOGIN = "EVENT_KEY_USER_LOGIN";

    public static final Object EVENT_KEY_SHARE_ACTIVITY = "EVENT_KEY_SHARE_ACTIVITY";
    public static final Object EVENT_KEY_SHARE_FRAGMENT = "EVENT_KEY_SHARE_FRAGMENT";

    public static final Object EVENT_KEY_USER_TOP_UP = "EVENT_KEY_USER_TOP_UP";

    public static final Object EVENT_KEY_INVITE_FRIEND = "EVENT_KEY_INVITE_FRIEND";

    public static final Object EVENT_KEY_LOGIN       = "EVENT_KEY_LOGIN";
    public static final Object EVENT_KEY_LOGIN_STATE = "EVENT_KEY_LOGIN_STATE";

    public static final Object EVENT_KEY_REGISTER_STATE       = "EVENT_KEY_REGISTER_STATE";
    public static final Object EVENT_KEY_REGISTER_BY_PHONE    = "EVENT_KEY_REGISTER_BY_PHONE";
    public static final Object EVENT_KEY_REGISTER_BY_USERNAME = "EVENT_KEY_REGISTER_BY_USERNAME";


    public static final Object EVENT_KEY_RESET_PASSWORD      = "EVENT_KEY_RESET_PASSWORD";
    public static final Object EVENT_KEY_LOGIN_TOKEN_OVERDUE = "EVENT_KEY_LOGIN_TOKEN_OVERDUE";

    public static final Object EVENT_KEY_SPLASH      = "EVENT_KEY_SPLASH";
    public static final Object EVENT_KEY_SPLASH_DATA = "EVENT_KEY_SPLASH_DATA";

    public static final Object EVENT_KEY_NETWORK_DEMO       = "EVENT_KEY_NETWORK_DEMO";
    public static final Object EVENT_KEY_NETWORK_DEMO_STATE = "EVENT_KEY_NETWORK_DEMO_STATE";

    public static final Object EVENT_KEY_MAIN_PAGE_LIST       = "EVENT_KEY_MAIN_PAGE_LIST";
    public static final Object EVENT_KEY_MAIN_PAGE_STATE      = "EVENT_KEY_MAIN_PAGE_STATE";
    public static final Object EVENT_KEY_MAIN_PAGE_GENRE_LIST = "EVENT_KEY_MAIN_PAGE_GENRE_LIST";

    public static final Object EVENT_KEY_SERVER_PAGE_STATE = "EVENT_KEY_SERVER_PAGE_STATE";

    public static final Object EVENT_KEY_SERVER_LIST       = "EVENT_KEY_SERVER_LIST";
    public static final Object EVENT_KEY_SERVER_LIST_STATE = "EVENT_KEY_SERVER_LIST_STATE";

    public static final Object EVENT_KEY_GAME_CLASSIFICATION       = "EVENT_KEY_GAME_CLASSIFICATION";
    public static final Object EVENT_KEY_GAME_LIST                 = "EVENT_KEY_GAME_LIST";
    public static final Object EVENT_KEY_GAME_SEARCH_LIST          = "EVENT_KEY_GAME_SEARCH_LIST";
    public static final Object EVENT_KEY_GAME_CLASSIFICATION_STATE = "EVENT_KEY_GAME_CLASSIFICATION_STATE";


    public static final Object EVENT_KEY_FEEDBACK_TYPE   = "EVENT_KEY_FEEDBACK_TYPE";
    public static final Object EVENT_KEY_COMMIT_FEEDBACK = "EVENT_KEY_COMMIT_FEEDBACK";


    public static final Object EVENT_KEY_REFRESH_USERINFO_DATA = "EVENT_KEY_REFRESH_USERINFO_DATA";

    public static final Object EVENT_KEY_SEND_VERIFICATION_CODE = "EVENT_KEY_SEND_VERIFICATION_CODE";
    public static final Object EVENT_KEY_USER_BIND_PHONE        = "EVENT_KEY_USER_BIND_PHONE";
    public static final Object EVENT_KEY_USER_UN_BIND_PHONE     = "EVENT_KEY_USER_UN_BIND_PHONE";

    public static final Object EVENT_KEY_USER_CERTIFICATION         = "EVENT_KEY_USER_CERTIFICATION";
    public static final Object EVENT_KEY_USER_MODIFY_LOGIN_PASSWORD = "EVENT_KEY_USER_MODIFY_LOGIN_PASSWORD";


    public static final Object EVENT_KEY_MESSAGE_STATE = "EVENT_KEY_MESSAGE_STATE";

    public static final Object EVENT_KEY_MESSAGE_BANNER_AD      = "EVENT_KEY_MESSAGE_BANNER_AD";
    public static final Object EVENT_KEY_MESSAGE_KEFU           = "EVENT_KEY_MESSAGE_KEFU";
    public static final Object EVENT_KEY_MESSAGE_DYNAMIC_GAME   = "EVENT_KEY_MESSAGE_DYNAMIC_GAME";
    public static final Object EVENT_KEY_MESSAGE_KEFU_MAIN_PAGE = "EVENT_KEY_MESSAGE_KEFU_MAIN_PAGE";


    public static final Object EVENT_KEY_MESSAGE_LIST_STATE = "EVENT_KEY_MESSAGE_LIST_STATE";
    public static final Object EVENT_KEY_MESSAGE_LIST_DATA  = "EVENT_KEY_MESSAGE_LIST_DATA";

    public static final Object EVENT_KEY_MY_FAVOURITE_GAME_STATE = "EVENT_KEY_MY_FAVOURITE_GAME_STATE";
    public static final Object EVENT_KEY_MY_FAVOURITE_GAME_LIST  = "EVENT_KEY_MY_FAVOURITE_GAME_LIST";

    public static final Object EVENT_KEY_MY_GIFT_CARD_STATE = "EVENT_KEY_MY_GIFT_CARD_STATE";
    public static final Object EVENT_KEY_MY_GIFT_CARD_LIST  = "EVENT_KEY_MY_GIFT_CARD_LIST";

    public static final Object EVENT_KEY_MY_COUPONS_STATE       = "EVENT_KEY_MY_COUPONS_STATE";
    public static final Object EVENT_KEY_MY_COUPONS_LIST        = "EVENT_KEY_MY_COUPONS_LIST";
    public static final Object EVENT_KEY_MY_COUPONS_GET_BY_CODE = "EVENT_KEY_MY_COUPONS_GET_BY_CODE";

    public static final Object EVENT_KEY_REBATE_LIST_STATE = "EVENT_KEY_REBATE_LIST_STATE";
    public static final Object EVENT_KEY_REBATE_LIST_DATA  = "EVENT_KEY_REBATE_LIST_STATE";

    public static final Object EVENT_KEY_REBATE_RECORD_LIST_STATE = "EVENT_KEY_REBATE_RECORD_LIST_STATE";
    public static final Object EVENT_KEY_REBATE_RECORD_LIST_DATA  = "EVENT_KEY_REBATE_RECORD_LIST_DATA";

    public static final Object EVENT_KEY_REBATE_RECORD_ITEM_STATE = "EVENT_KEY_REBATE_RECORD_ITEM_STATE";
    public static final Object EVENT_KEY_REBATE_RECORD_ITEM_DATA  = "EVENT_KEY_REBATE_RECORD_ITEM_DATA";
    public static final Object EVENT_KEY_REBATE_REVOKE_LIST_DATA  = "EVENT_KEY_REBATE_REVOKE_LIST_DATA";
    public static final Object EVENT_KEY_REBATE_REVOKE_ACTION     = "EVENT_KEY_REBATE_REVOKE_ACTION";

    public static final Object EVENT_KEY_REBATE_COMMIT_ACTION = "EVENT_KEY_REBATE_COMMIT_ACTION";

    public static final Object EVENT_KEY_REBATE_SERVER_HISTORY_DATA = "EVENT_KEY_REBATE_SERVER_HISTORY_DATA";


    public static final Object EVENT_KEY_TRANSFER_MAIN_STATE     = "EVENT_KEY_TRANSFER_MAIN_STATE";
    public static final Object EVENT_KEY_TRANSFER_MAIN_LIST_DATA = "EVENT_KEY_TRANSFER_MAIN_LIST_DATA";


    public static final Object EVENT_KEY_TRANSFER_GAME_STATE = "EVENT_KEY_TRANSFER_GAME_STATE";
    public static final Object EVENT_KEY_TRANSFER_GAME_DATA  = "EVENT_KEY_TRANSFER_GAME_DATA";

    public static final Object EVENT_KEY_TRANSFER_ACTION_DATA   = "EVENT_KEY_TRANSFER_ACTION_DATA";
    public static final Object EVENT_KEY_TRANSFER_ACTION_COMMIT = "EVENT_KEY_TRANSFER_ACTION_COMMIT";

    public static final Object EVENT_KEY_TRANSFER_RECORD_STATE = "EVENT_KEY_TRANSFER_RECORD_STATE";
    public static final Object EVENT_KEY_TRANSFER_RECORD_DATA  = "EVENT_KEY_TRANSFER_RECORD_DATA";

    public static final Object EVENT_KEY_TRANSFER_RECORD_DETAIL_STATE = "EVENT_KEY_TRANSFER_RECORD_DETAIL_STATE";
    public static final Object EVENT_KEY_TRANSFER_RECORD_DETAIL_DATA  = "EVENT_KEY_TRANSFER_RECORD_DETAIL_DATA";


    public static final Object EVENT_KEY_ACTIVITY_LIST_STATE = "EVENT_KEY_ACTIVITY_LIST_STATE";
    public static final Object EVENT_KEY_ACTIVITY_LIST_DATA  = "EVENT_KEY_ACTIVITY_LIST_DATA";


    public static final Object EVENT_KEY_GAME_DETAIL_DATA  = "EVENT_KEY_GAME_DETAIL_DATA";
    public static final Object EVENT_KEY_GAME_DETAIL_STATE = "EVENT_KEY_GAME_DETAIL_STATE";

    public static final Object EVENT_KEY_GAME_SET_FAVORITE    = "EVENT_KEY_GAME_SET_FAVORITE";
    public static final Object EVENT_KEY_GAME_SET_UN_FAVORITE = "EVENT_KEY_GAME_SET_UN_FAVORITE";

    public static final Object EVENT_KEY_GAME_SET_MY_FAVORITE    = "EVENT_KEY_GAME_SET_MY_FAVORITE";
    public static final Object EVENT_KEY_GAME_SET_UN_MY_FAVORITE = "EVENT_KEY_GAME_SET_UN_MY_FAVORITE";

    public static final Object EVENT_KEY_GAME_GET_CARD     = "EVENT_KEY_GAME_GET_CARD";
    public static final Object EVENT_KEY_GAME_GET_TAO_CARD = "EVENT_KEY_GAME_GET_TAO_CARD";

    public static final Object EVENT_KEY_GAME_COUPON_LIST_STATE = "EVENT_KEY_GAME_COUPON_LIST_STATE";
    public static final Object EVENT_KEY_GAME_COUPON_LIST_DATA  = "EVENT_KEY_GAME_COUPON_LIST_DATA";

    public static final Object EVENT_KEY_GAME_USER_GET_COUPON_DATA = "EVENT_KEY_GAME_USER_GET_COUPON_DATA";

    public static final Object EVENT_KEY_KEFU_CENTER_DATA      = "EVENT_KEY_KEFU_CENTER_DATA";
    public static final Object EVENT_KEY_KEFU_PERSON_LIST_DATA = "EVENT_KEY_KEFU_PERSON_LIST_DATA";
    public static final Object EVENT_KEY_KEFU_PERSON_EVALUATE  = "EVENT_KEY_KEFU_PERSON_EVALUATE";

    public static final Object EVENT_KEY_SHARE_INVITE_ACTIVITY = "EVENT_KEY_SHARE_INVITE_ACTIVITY";
    public static final Object EVENT_KEY_SHARE_INVITE_FRAGMENT = "EVENT_KEY_SHARE_INVITE_FRAGMENT";

    public static final Object EVENT_KEY_USER_CURRENCY_STATE     = "EVENT_KEY_USER_CURRENCY_STATE";
    public static final Object EVENT_KEY_USER_CURRENCY_LIST_DATA = "EVENT_KEY_USER_CURRENCY_LIST_DATA";

    public static final Object EVENT_KEY_GAME_COLLECTION_LIST_STATE = "EVENT_KEY_GAME_COLLECTION_LIST_STATE";
    public static final Object EVENT_KEY_GAME_COLLECTION_LIST_DATA  = "EVENT_KEY_GAME_COLLECTION_LIST_DATA";

    public static final Object EVENT_KEY_BROWSER_SHARE_CALLBACK = "EVENT_KEY_BROWSER_SHARE_CALLBACK";


    public static final Object EVENT_KEY_AUDIT_MAIN_STATE        = "EVENT_KEY_AUDIT_MAIN_STATE";
    public static final Object EVENT_KEY_AUDIT_RECOMMENDED_STATE = "EVENT_KEY_AUDIT_RECOMMENDED_STATE";
    public static final Object EVENT_KEY_AUDIT_GAME_LIST_STATE   = "EVENT_KEY_AUDIT_GAME_LIST_STATE";

    public static final Object EVENT_KEY_AUDIT_INFORMATION_LIST_STATE = "EVENT_KEY_AUDIT_INFORMATION_LIST_STATE";
    public static final Object EVENT_KEY_AUDIT_GAME_ITEM_LIST_STATE   = "EVENT_KEY_AUDIT_GAME_ITEM_LIST_STATE";

    public static final Object EVENT_KEY_AUDIT_GAME_MAIN_LIST_STATE       = "EVENT_KEY_AUDIT_GAME_MAIN_LIST_STATE";
    public static final Object EVENT_KEY_AUDIT_GAME_COLLECTION_LIST_STATE = "EVENT_KEY_AUDIT_GAME_COLLECTION_LIST_STATE";
    public static final Object EVENT_KEY_AUDIT_GAME_DETAIL_STATE          = "EVENT_KEY_AUDIT_GAME_DETAIL_STATE";

    public static final Object EVENT_KEY_TASK_CENTER_STATE  = "EVENT_KEY_TASK_CENTER_STATE";
    public static final Object EVENT_KEY_TASK_SIGN_IN_STATE = "EVENT_KEY_TASK_SIGN_IN_STATE";

    public static final Object EVENT_KEY_USER_MINE_STATE = "EVENT_KEY_USER_MINE_STATE";

    public static final int SEARCH_GAME_TYPE_NORMAL            = 1;
    public static final int SEARCH_GAME_TYPE_AUDIT_TRANSACTION = 2;
    public static final int SEARCH_GAME_TYPE_TRANSACTION       = 3;


    public static final String URL_SPECIFICATION_TRADE         = App.getContext().getResources().getString(R.string.url_specification_trade_page) + "?tgid=" + AppUtils.getChannelFromApk();
    public static final String URL_SPECIFICATION_COMMENT       = App.getContext().getResources().getString(R.string.url_specification_comment_page) + "?tgid=" + AppUtils.getChannelFromApk();
    public static final String URL_SPECIFICATION_QA            = App.getContext().getResources().getString(R.string.url_specification_qa_page) + "?tgid=" + AppUtils.getChannelFromApk();
    public static final String URL_SPECIFICATION_HEAD_PORTRAIT = App.getContext().getResources().getString(R.string.url_specification_head_portrait_page) + "?tgid=" + AppUtils.getChannelFromApk();
    public static final String URL_SPECIFICATION_USER_LEVEL    = App.getContext().getResources().getString(R.string.url_specification_user_level_page) + "?tgid=" + AppUtils.getChannelFromApk();
    public static final String URL_SPECIFICATION_H_COMMENT     = App.getContext().getResources().getString(R.string.url_specification_h_comment_page) + "?tgid=" + AppUtils.getChannelFromApk();
    public static final String URL_ZHUANPANFULI                = "https://hd.xiaodianyouxi.com/index.php/Draw?uid=";
    public static final String URL_YONGHUZHAOHUIA              = "https://wap.xiaodianyouxi.com/index.php/recall?uid=";
    public static final String URL_XH_RECYCLE_RULE             = "https://wap.xiaodianyouxi.com/index.php/recycle/rule?uid=";


    public static final int REQ_CROP         = 2000;
    public static final int REQ_FROM_GALLERY = 1;
    public static final int REQ_FROM_CAM     = 2;

}
