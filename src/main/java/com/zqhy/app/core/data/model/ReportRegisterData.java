package com.zqhy.app.core.data.model;

import com.zqhy.app.App;
import com.zqhy.app.core.tool.utilcode.DeviceUtils;

public class ReportRegisterData {
    public String deviceinfo;
    public String packageinfo;
    public String username;
    public String user_tgid;
    public String device_id;
    public String client;
    public String oaid;

    public static String getDeviceInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("imei:").append(DeviceUtils.getIMEI(App.instance())).append("-")
                    .append("mac:").append(DeviceUtils.getMacAddress()).append("-")
                    .append("uid:").append(DeviceUtils.getUniqueId(App.instance())).append("-")
                    .append("android_id;").append(DeviceUtils.getAndroidID(App.instance())).append("-");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
