package com.zqhy.app.core.data.model.nodata;

import android.text.SpannableStringBuilder;

import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder1;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder2;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class EmptyDataVo1 {

    public static final int LAYOUT_MATCH_PARENT = 1;
    public static final int LAYOUT_WRAP_CONTENT = 2;

    private boolean isBgWhite = false;

    private int emptyResourceId;

    private int layoutWidth;
    private int paddingTop;

    private SpannableStringBuilder emptyWord;
    private int resEmptyWordColor;

    public EmptyDataVo1() {
    }

    public EmptyDataVo1(int emptyResourceId) {
        this.emptyResourceId = emptyResourceId;
        this.layoutWidth = LAYOUT_MATCH_PARENT;
    }

    public int getEmptyResourceId() {
        return emptyResourceId;
    }

    public EmptyDataVo1 setLayout(int layoutWidth){
        this.layoutWidth = layoutWidth;
        return this;
    }

    public EmptyDataVo1 setPaddingTop(int paddingTop){
        this.paddingTop = paddingTop;
        return this;
    }

    public EmptyDataVo1 setEmptyWord(SpannableStringBuilder emptyWord){
        this.emptyWord = emptyWord;
        return this;
    }

    public EmptyDataVo1 setEmptyWordColor(int resEmptyWordColor){
        this.resEmptyWordColor = resEmptyWordColor;
        return this;
    }

    public EmptyDataVo1 setWhiteBg(boolean isBgWhite){
        this.isBgWhite = isBgWhite;
        return this;
    }

    public CharSequence getEmptyWord() {
        return emptyWord;
    }

    public int getResEmptyWordColor() {
        return resEmptyWordColor;
    }

    public int getLayoutWidth() {
        return layoutWidth;
    }

    public boolean isBgWhite() {
        return isBgWhite;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    private OnLayoutListener onLayoutListener;

    public void addOnLayoutListener(OnLayoutListener onLayoutListener) {
        this.onLayoutListener = onLayoutListener;
    }

    public OnLayoutListener getOnLayoutListener() {
        return onLayoutListener;
    }

    public interface OnLayoutListener {

        /**
         * 自定义布局
         *
         * @param holder
         */
        void onLayout(EmptyItemHolder.ViewHolder holder);
        void onLayout(EmptyItemHolder1.ViewHolder holder);
        void onLayout(EmptyItemHolder2.ViewHolder holder);
    }
}
