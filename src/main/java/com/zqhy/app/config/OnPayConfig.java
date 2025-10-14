package com.zqhy.app.config;

/**
 * @author Administrator
 */
public class OnPayConfig {
    private static String orderId;
    private static String amount;
    private static String payType;

    public static final String PAY_TYPE_ALI = "alipay";
    public static final String PAY_TYPE_WECHAT = "wechat";

    private static int TOUTIAO_REPORT_AMOUNT_LIMIT;

    public static void setPayConfig(String orderId, String amount, String payType) {
        OnPayConfig.orderId = orderId;
        OnPayConfig.amount = amount;
        OnPayConfig.payType = payType;
    }

    public static String getOrderId() {
        return orderId;
    }

    public static String getAmount() {
        return amount;
    }

    public static String getPayType() {
        return payType;
    }

    public static void setToutiaoReportAmountLimit(int amount){
        TOUTIAO_REPORT_AMOUNT_LIMIT = amount;
    }

    public static int getToutiaoReportAmountLimit() {
        return TOUTIAO_REPORT_AMOUNT_LIMIT;
    }
}
