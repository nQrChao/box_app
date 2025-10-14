package com.zqhy.app.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * @author leeham2734
 * @date 2020/11/9-15:30
 * @description
 */
public class TextFontsUtils {


    /**
     * 设置字体
     *
     * @param mContext     上下文
     * @param target       目标TextView
     * @param charSequence 文本
     * @param fontPath     字体路径
     */
    public static void setTextWithFont(Context mContext, TextView target, CharSequence charSequence, String fontPath) {
        Typeface fontFace = Typeface.createFromAsset(mContext.getAssets(), fontPath);
        target.setTypeface(fontFace);
        target.setText(charSequence);
    }

    /**
     * 设置字体1
     *
     * @param mContext
     * @param target
     * @param charSequence
     */
    public static void setTextWithFont1(Context mContext, TextView target, CharSequence charSequence) {
        setTextWithFont(mContext, target, charSequence, "fonts/tv_font_1.ttf");
    }
}
