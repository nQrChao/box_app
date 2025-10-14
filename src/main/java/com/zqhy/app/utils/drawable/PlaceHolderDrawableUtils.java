package com.zqhy.app.utils.drawable;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;

import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class PlaceHolderDrawableUtils {


    private static int[] colors = new int[]{
            R.color.color_f5f5f5, R.color.color_eeeeee,
            R.color.color_e2e2e2, R.color.color_ebebeb,
            R.color.color_cccccc, R.color.color_9c9c9c,
            R.color.color_818181, R.color.color_c0c0c0,
            R.color.color_2acfc8, R.color.color_ff9628,
            R.color.color_ffd7db, R.color.color_3478f6,
            R.color.color_ff4949, R.color.color_f16c00,
    };


    /**
     * 获取随机填充色背景Drawable
     * @param mContext
     * @return
     */
    public static Drawable getPlaceHolderDrawable(Context mContext) {
        int color = colors[(int) (Math.random() * colors.length)];
        return new ColorDrawable(ContextCompat.getColor(mContext,color));
    }
}
