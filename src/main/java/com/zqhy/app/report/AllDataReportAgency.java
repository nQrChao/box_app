package com.zqhy.app.report;

import android.content.Context;

import com.zqhy.app.App;
import com.zqhy.app.core.pay.PayResultVo;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class AllDataReportAgency {

    private static volatile AllDataReportAgency ourInstance;

    private List<AbsReportAgency> reportAgencyList;

    private AllDataReportAgency() {
        //2019.06.13 如果以后新增加上报渠道，直接在这里添加
        reportAgencyList = new ArrayList<>();
        reportAgencyList.add(TtDataReportAgency.getInstance());
        reportAgencyList.add(UmDataReportAgency.getInstance());
        //2021.08.26新增
        reportAgencyList.add(KsDataReportAgency.getInstance());

        //2021.09.02新增
        reportAgencyList.add(TencentDataAgency.getInstance());

        //2021.12.21新增
        reportAgencyList.add(HuiChuanDataReportAgency.getInstance());
    }

    public static AllDataReportAgency getInstance() {
        if (ourInstance == null) {
            synchronized (AllDataReportAgency.class) {
                if (ourInstance == null) {
                    ourInstance = new AllDataReportAgency();
                }
            }
        }
        return ourInstance;
    }

    public void init(Context mContext) {
        if (reportAgencyList == null || reportAgencyList.isEmpty()) {
            return;
        }
        for (AbsReportAgency reportAgency : reportAgencyList) {
            reportAgency.init(mContext);
        }
    }


    /**
     * 上报App启动
     */
    public void startApp(Context context) {
        if (reportAgencyList == null || reportAgencyList.isEmpty()) {
            return;
        }
        for (AbsReportAgency reportAgency : reportAgencyList) {
            reportAgency.startApp(context);
        }
    }


    public void login(String accountID, String username, String tgid) {
        login("mobile", accountID, username, tgid);
    }

    /**
     * 上报登录事件
     */
    public void login(String client, String accountID, String username, String tgid) {
        if (reportAgencyList == null || reportAgencyList.isEmpty()) {
            return;
        }
        for (AbsReportAgency reportAgency : reportAgencyList) {
            reportAgency.login(client, accountID, username, tgid);
        }
    }


    /**
     * 上报注册事件
     *
     * @param accountID
     * @param username
     * @param tgid
     */
    public void register(String client, String accountID, String username, String tgid) {
        if (reportAgencyList == null || reportAgencyList.isEmpty()) {
            return;
        }
        for (AbsReportAgency reportAgency : reportAgencyList) {
            reportAgency.register(client, accountID, username, tgid);
        }
    }

    /**
     * 上报注册事件
     *
     * @param accountID
     * @param username
     */
    public void register(String accountID, String username, String tgid) {
        register("mobile", accountID, username, tgid);
    }


    /**
     * 上报付费事件
     * app用
     *
     * @param pay_type
     * @param payResultVo
     */
    public void purchase(String pay_type, PayResultVo payResultVo) {
        String uid = String.valueOf(UserInfoModel.getInstance().getUID());
        String tgid = AppUtils.getTgid();
        purchase("app_" + App.getContext().getPackageName(), pay_type, payResultVo, uid, tgid);
    }

    /**
     * 上报付费事件
     * sdk用
     *
     * @param pay_type
     */
    public void purchase(String client, String pay_type, PayResultVo payResultVo, String uid, String tgid) {
        if (reportAgencyList == null || reportAgencyList.isEmpty()) {
            return;
        }
        for (AbsReportAgency reportAgency : reportAgencyList) {
            reportAgency.purchase(client, pay_type, payResultVo, uid, tgid);
        }
    }

    /**
     * 允许隐私权限
     *
     * @param isAllowed
     */
    public void setPrivacyStatus(boolean isAllowed) {
        if (reportAgencyList == null || reportAgencyList.isEmpty()) {
            return;
        }
        for (AbsReportAgency reportAgency : reportAgencyList) {
            reportAgency.setPrivacyStatus(isAllowed);
        }
    }
}
