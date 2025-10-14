package com.zqhy.app.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.view.View;

import com.zqhy.app.newproject.R;

public class MemoryInfoUtils {


    public static boolean checkBipartition(Context context){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1 && MemoryInfoUtils.getTotalMemory(context) > 7000){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获取手机运行内存
     * @param context
     * @return
     */
    public static double getTotalMemory(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        double totalMemoryMB = memoryInfo.totalMem / (1024 * 1024);
        return totalMemoryMB;
    }
}
