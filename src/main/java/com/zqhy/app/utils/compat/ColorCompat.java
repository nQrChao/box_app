package com.zqhy.app.utils.compat;

import android.graphics.Color;
import androidx.annotation.ColorRes;

/**
 * [功能说明]
 *
 * @author 韩国桐
 * @version [0.1, 2020/7/23]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ColorCompat {
    public static int parseColor(String color, @ColorRes int resId){
        int temp;
        try{
            temp=Color.parseColor(color);
        }catch (Exception e){
            temp=ResCompat.getColor(resId);
        }
        return temp;
    }
}
