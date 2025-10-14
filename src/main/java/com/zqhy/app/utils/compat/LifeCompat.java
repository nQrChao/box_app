package com.zqhy.app.utils.compat;

import android.app.Activity;
import android.os.Build;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;

/**
 * 生命周期工具
 * @author 韩国桐
 * @version [0.1, 2020/1/14]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LifeCompat {

    public static boolean isAlive(Activity activity){
        if(activity!=null){
            if(Build.VERSION.SDK_INT >= JELLY_BEAN_MR1){
                return !activity.isDestroyed() && !activity.isFinishing();
            }else {
                return !activity.isFinishing();
            }
        }else{
            return false;
        }
    }
}
