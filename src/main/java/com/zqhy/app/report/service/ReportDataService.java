package com.zqhy.app.report.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.zqhy.app.config.OnPayConfig;
import com.zqhy.app.core.pay.PayResultVo;
import com.zqhy.app.report.AllDataReportAgency;

/**
 * @author pc
 * @date 2019/12/2-18:03
 * @description
 */
public class ReportDataService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ReportDataService(String name) {
        super(name);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        handleIntent(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 处理intent数据
     *
     * @param intent
     */
    private void handleIntent(Intent intent) {

        Logs.e("handleIntent=====>" + intent.toString());

        String action = intent.getStringExtra("action");
        String sdk_tag = intent.getStringExtra("sdk_tag");
        String sdk_tgid = intent.getStringExtra("sdk_tgid");
        String sdk_account_id = intent.getStringExtra("sdk_account_id");
        String sdk_username = intent.getStringExtra("sdk_username");
        Logs.e("action = " + action);
        Logs.e("sdk_tag = " + sdk_tag);
        Logs.e("sdk_tgid = " + sdk_tgid);
        Logs.e("sdk_account_id = " + sdk_account_id);
        Logs.e("sdk_username = " + sdk_username);
        if (action != null) {
            switch (action) {
                case "login":
                    //                    TouTiaoSdkAgency.getInstance().setLogin(sdk_tag);
                    AllDataReportAgency.getInstance().login(sdk_tag, sdk_account_id, sdk_username);
                    break;
                case "register":
                    //                    TouTiaoSdkAgency.getInstance().setRegister(sdk_tag);
                    AllDataReportAgency.getInstance().register(sdk_tag, sdk_account_id, sdk_username, "");
                    break;
                case "purchase":
                    String pay_way = intent.getStringExtra("pay_way");
                    String purchase_order_id = intent.getStringExtra("purchase_order_id");
                    String purchase_order_amount = intent.getStringExtra("purchase_order_amount");

                    int limitAmount = intent.getIntExtra("limit_amount", 0);

                    Logs.e("pay_way = " + pay_way);
                    Logs.e("purchase_order_id = " + purchase_order_id);
                    Logs.e("purchase_order_amount = " + purchase_order_amount);
                    Logs.e("limitAmount = " + limitAmount);

                    OnPayConfig.setToutiaoReportAmountLimit(limitAmount);

                    PayResultVo payResultVo = new PayResultVo(purchase_order_id, purchase_order_amount);
                    //                    TouTiaoSdkAgency.getInstance().setPurchase(sdk_tag, pay_way, payResultVo);
                    AllDataReportAgency.getInstance().purchase(sdk_tag, pay_way, payResultVo, sdk_account_id, sdk_tgid);
                    break;
            }
        }
        stopSelf();
    }
}
