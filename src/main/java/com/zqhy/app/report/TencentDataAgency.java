package com.zqhy.app.report;

import android.content.Context;
import android.text.TextUtils;

import com.qq.gdt.action.ActionType;
import com.qq.gdt.action.ActionUtils;
import com.qq.gdt.action.GDTAction;
import com.zqhy.app.config.OnPayConfig;
import com.zqhy.app.core.pay.PayResultVo;
import com.zqhy.app.newproject.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * @author Administrator
 */
public class TencentDataAgency extends AbsReportAgency {

    private static final String TAG = TencentDataAgency.class.getSimpleName();

    private static volatile TencentDataAgency ourInstance;

    private TencentDataAgency() {
        isReportChannel = true;
        isReportChannel = isReportChannel && hasIdKey();
    }

    public static TencentDataAgency getInstance() {
        if (ourInstance == null) {
            synchronized (TencentDataAgency.class) {
                if (ourInstance == null) {
                    ourInstance = new TencentDataAgency();
                }
            }
        }
        return ourInstance;
    }

    //    private static final String userActionSetID = "1110855017";
    //
    //    private static final String appSecretKey = "d6d80581845a5b828eb489c3655e2dc1";

    private static final String userActionSetID = BuildConfig.TENCENT_APP_ID;

    private static final String appSecretKey = BuildConfig.TENCENT_APP_KEY;

    @Override
    public void init(Context mContext) {
        if (isReportChannel) {
            // 第一个参数是Context上下文，第二个参数是您在DMP上获得的行为数据源ID，第三个参数是您在DMP上获得AppSecretKey
            GDTAction.init(mContext, userActionSetID, appSecretKey);
            isFinishInit = true;
            log(TAG, "init success");
        }
    }

    /**
     * 上报App启动
     */
    @Override
    public void startApp(Context context) {
        if (isFinishInit && isReportChannel) {
            GDTAction.logAction(ActionType.START_APP);
            log(TAG, "startApp");
        }
    }

    /**
     * 上报注册事件
     *
     * @param accountID
     * @param username
     */
    @Override
    public void register(String client, String accountID, String username, String tgid) {
        if (isFinishInit && isReportChannel) {
            JSONObject actionParam = new JSONObject();
            try {
                actionParam.put("accountID", accountID);
                actionParam.put("username", username);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            GDTAction.logAction(ActionType.REGISTER, actionParam);
            ActionUtils.onRegister(accountID, true);
            log(TAG, "register\n" + printJSONObject(actionParam));
            sendReportRegisterData(client, accountID, tgid);
        }
    }

    @Override
    protected void login(String client, String accountID, String username, String tgid) {
        ActionUtils.onLogin("accountID", true);
    }

    /**
     * 付费事件
     *
     * @param payType
     */
    @Override
    public void purchase(String client, String payType, PayResultVo payResultVo, String uid, String tgid) {
        if (isFinishInit && isReportChannel) {
            String orderId = OnPayConfig.getOrderId();
            String out_trade_no = payResultVo.getOut_trade_no();
            int amount = 0;
            try {
                amount = (int)(payResultVo.getFloatAmount() * 100);
            } catch (Exception e) {}
            JSONObject actionParam = new JSONObject();
            try {
                actionParam.put("orderId", orderId);
                actionParam.put("out_trade_no", out_trade_no);
                actionParam.put("value", amount);
                actionParam.put("payType", payType);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            GDTAction.logAction(ActionType.PURCHASE, actionParam);
            ActionUtils.onPurchase("充值", "充值", orderId, 1, payType, "CNY", amount, true);
            log(TAG, "purchase\n" + printJSONObject(actionParam));
            sendReportAdData(client, out_trade_no, payType, amount, uid, tgid);
        }
    }

    @Override
    protected String getReportWay() {
        return "tencent";
    }

    private boolean hasIdKey() {
        return !TextUtils.isEmpty(userActionSetID) && !TextUtils.isEmpty(appSecretKey);
    }


    private String printJSONObject(JSONObject actionParam) {
        if (actionParam == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();

        Iterator<String> keys = actionParam.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            String value = "";
            try {
                value = actionParam.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            sb.append(key).append("=").append(value).append("\n");
        }
        return sb.toString();
    }
}
