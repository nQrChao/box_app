package com.zqhy.app.utils;

import android.app.Activity;
import android.os.Build;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;

public class LifeUtil {
    public static boolean isAlive(Activity activity){
        if(activity!=null){
            if(Build.VERSION.SDK_INT >= JELLY_BEAN_MR1 ){
                return !activity.isDestroyed()||!activity.isFinishing();
            }else {
                return !activity.isFinishing();
            }
        }
        return false;
    }
}
