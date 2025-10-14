package com.zqhy.app.core.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.google.gson.Gson;
import com.zqhy.app.model.PostAdData;
import com.zqhy.app.core.vm.MainListRequest;
import com.zqhy.app.config.OnPayConfig;
import com.zqhy.app.core.data.model.ReportSdkDataBeanVo;
import com.zqhy.app.core.pay.PayResultVo;
import com.zqhy.app.report.AllDataReportAgency;

/**
 * @author pc
 * @date 2019/12/2-16:17
 * @description
 */
public class ReportTouTiaoDataActivity extends AppCompatActivity {

    private MainListRequest request;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        request = new MainListRequest();
        handleIntent(getIntent());
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }


    /**
     * 处理intent数据
     *
     * @param intent
     */
    private void handleIntent(Intent intent) {
        Logs.e("handleIntent=====>" + intent.toString());

        String sdk_action = intent.getStringExtra("action");
        String sdk_tag = intent.getStringExtra("sdk_tag");
        String sdk_tgid = intent.getStringExtra("sdk_tgid");
        String sdk_account_id = intent.getStringExtra("sdk_account_id");
        String sdk_username = intent.getStringExtra("sdk_username");
        Logs.e("action = " + sdk_action);
        Logs.e("sdk_tag = " + sdk_tag);
        Logs.e("sdk_tgid = " + sdk_tgid);
        Logs.e("sdk_account_id = " + sdk_account_id);
        Logs.e("sdk_username = " + sdk_username);
        if (sdk_action != null) {
            //sdk_action only equals purchase in alipay
            ReportSdkDataBeanVo sdkDataBeanVo = new ReportSdkDataBeanVo();
            ReportSdkDataBeanVo.DataBean bean = new ReportSdkDataBeanVo.DataBean();
            bean.sdk_action = sdk_action;
            bean.sdk_tag = sdk_tag;
            bean.sdk_tgid = sdk_tgid;
            bean.sdk_account_id = sdk_account_id;
            bean.sdk_username = sdk_username;
            sdkDataBeanVo.setEvent(sdk_action);
            switch (sdk_action) {
                case "login":
                    AllDataReportAgency.getInstance().login(sdk_tag, sdk_account_id, sdk_username, "");
                    break;
                case "register":
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

                    bean.pay_info = new ReportSdkDataBeanVo.PayInfo(pay_way, purchase_order_id, purchase_order_amount, limitAmount);
                    break;
                case "search_h5_payback":

                    break;
            }
            sdkDataBeanVo.setData(bean);
            Gson gson = new Gson();
            String jsonData = gson.toJson(sdkDataBeanVo);
            request.postAdEvent(jsonData, new MainListRequest.OnMainDataGetCallBack<PostAdData>() {
                @Override
                public void onSuccess(PostAdData data) {
                    String uid = String.valueOf(data.uid);
                    String tgid = data.tgid;
                    if ("purchase".equals(sdk_action)) {
                        String pay_way = intent.getStringExtra("pay_way");
                        String purchase_order_id = intent.getStringExtra("purchase_order_id");
                        String purchase_order_amount = intent.getStringExtra("purchase_order_amount");
                        PayResultVo payResultVo = new PayResultVo(purchase_order_id, purchase_order_amount);
                        AllDataReportAgency.getInstance().purchase(sdk_tag, pay_way, payResultVo, uid, tgid);
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        } else {
            //2021.05.18新增
            String jsonData = intent.getStringExtra("json_data");
            request.postAdEvent(jsonData);
            postData(sdk_tag, jsonData);
        }
        finish();
    }


    /**
     * 上报数据
     *
     * @param sdk_tag   发送数据主体标识
     * @param json_data 服务器返回数据
     */
    public static void postData(String sdk_tag, String json_data) {
        //2021.05.24新增
        ReportSdkDataBeanVo beanVo = ReportSdkDataBeanVo.parse(json_data);
        if (beanVo != null) {
            Log.d("ReportTouTiaoData", beanVo.toString());
            String event = beanVo.getEvent();
            ReportSdkDataBeanVo.DataBean data = beanVo.getData();
            if (data != null) {
                switch (event) {
                    case "login":
                        AllDataReportAgency.getInstance().login(sdk_tag, data.uid, data.username, data.tgid);
                        break;
                    case "register":
                        AllDataReportAgency.getInstance().register(sdk_tag, data.uid, data.username, data.tgid);
                        break;
                    case "pay":
                        ReportSdkDataBeanVo.PayInfo payInfo = data.pay_info;
                        if (payInfo != null) {
                            int limitAmount = payInfo.getLimit_amount();
                            OnPayConfig.setToutiaoReportAmountLimit(limitAmount);

                            String pay_way = payInfo.getPay_way();
                            String purchase_order_id = payInfo.getPurchase_order_id();
                            String purchase_order_amount = payInfo.getPurchase_order_amount();

                            PayResultVo payResultVo = new PayResultVo(purchase_order_id, purchase_order_amount);
                            AllDataReportAgency.getInstance().purchase(sdk_tag, pay_way, payResultVo, data.uid, data.tgid);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
