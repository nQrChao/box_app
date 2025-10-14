package com.zqhy.app;

import com.zqhy.app.core.data.model.InitDataVo;
import com.zqhy.app.core.data.model.chat.ChatActivityRecommendVo;
import com.zqhy.app.core.data.model.chat.ChatTeamNoticeListVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leeham2734
 * @date 2021/3/25-15:43
 * @description
 */
public class Setting {
    public static String USER_AGENT = "";
    public static String OAID = "";
    public static boolean IS_AOP;
    public static int TYPE = 1;
    public static int TRADE_TYPE = 1;
    public static String SHOW_DOWNLOAD = "";
    public static int SHOW_TYPE = 0;
    public static String DOWN_PIC = "";
    public static String HELLO_TXT = "";
    public static int IS_REAL = 0;


    public static boolean HAS_SPLASH_JUMP = false;
    public static boolean VE_CLOUD_IS_INIT = false;
    public static InitDataVo.ProfileSettingVo PROFILE_SETTING;

    public static final boolean IS_DEBUGING = false;

    public static int HIDE_FIVE_FIGURE = 0;
    public static int CLOUD_STATUS = 0;
    public static String POP_GAMEID = "";//是否跳转详情页并显示弹窗
    public static int SHOW_TIPS = 1;//下载弹窗提示

    public static String REFUND_GAME_LIST_TGID = "";
    public static List<String> REFUND_GAME_LIST = new ArrayList<String>();

    //渠道号写入的游戏ID
    public static int INPUT_CHANNEL_GAME_ID = 0;

    //网页模板用
    public static String UUID = null;
    public static String ANDROID_ID = null;
    public static String IMEI = null;
    public static String MAC_ADD = null;
    public static String DEVICE_SIGN = null;
    public static String UNIQUE_ID = null;
    public static String DEVICE_IMEI = null;

    //群聊
    public static String tid = null;
    public static List<ChatActivityRecommendVo.DataBean> activityList = null;
    public static List<ChatTeamNoticeListVo.DataBean> noticeList = null;

    public static int simulator = 0;//是否是真机环境（1 模拟器）
    //false.走网络切包   true.不走网络切包，配合APP_STATUS
    public static boolean APP_LOCAL_STATUS_ENABLE = false;
    //当APP_LOCAL_STATUS_ENABLE为true可用，1.正式包，0.马甲包
    //public static int APP_STATUS = 0;
//    public static boolean APP_LOCAL_STATUS_ENABLE = true;
//    public static int APP_STATUS = 1;
}
