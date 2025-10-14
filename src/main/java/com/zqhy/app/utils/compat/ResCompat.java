package com.zqhy.app.utils.compat;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.res.ResourcesCompat;
import android.util.DisplayMetrics;

import com.zqhy.app.App;
import com.zqhy.app.newproject.R;

public class ResCompat {

    public static Drawable getDrawable(@DrawableRes int resId){
        return ResourcesCompat.getDrawable(App.instance().getResources(),resId,null);
    }

    public static int getColor(@ColorRes int resId){
        return ResourcesCompat.getColor(App.instance().getResources(),resId,null);
    }

    public static ColorStateList getColorStateList(@ColorRes int resId){
        return ResourcesCompat.getColorStateList(App.instance().getResources(),resId,null);
    }

    public static Resources getResources(){
        return App.instance().getResources();
    }

    public static String getString(@StringRes int resId){
        return App.instance().getResources().getString(resId);
    }

    public static String getAppName(){
        return App.instance().getResources().getString(R.string.app_name);
    }

    public static DisplayMetrics getMetrics(){
        return App.instance().getResources().getDisplayMetrics();
    }

    public static float getDensity(){
        return App.instance().getResources().getDisplayMetrics().density;
    }
}
