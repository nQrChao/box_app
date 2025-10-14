package com.zqhy.app.core.data.model.nodata;

import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder1;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class EmptyDataVo {

    public static final int LAYOUT_MATCH_PARENT = 1;
    public static final int LAYOUT_WRAP_CONTENT = 2;

    private boolean isBgWhite = false;

    private int emptyResourceId;

    private int layoutWidth;
    private int paddingTop;

    private CharSequence emptyWord;
    private int resEmptyWordColor;

    public EmptyDataVo() {
    }

    public EmptyDataVo(int emptyResourceId) {
        this.emptyResourceId = emptyResourceId;
        this.layoutWidth = LAYOUT_MATCH_PARENT;
    }

    public int getEmptyResourceId() {
        return emptyResourceId;
    }

    public EmptyDataVo setLayout(int layoutWidth){
        this.layoutWidth = layoutWidth;
        return this;
    }

    public EmptyDataVo setPaddingTop(int paddingTop){
        this.paddingTop = paddingTop;
        return this;
    }

    public EmptyDataVo setEmptyWord(CharSequence emptyWord){
        this.emptyWord = emptyWord;
        return this;
    }

    public EmptyDataVo setEmptyWordColor(int resEmptyWordColor){
        this.resEmptyWordColor = resEmptyWordColor;
        return this;
    }

    public EmptyDataVo setWhiteBg(boolean isBgWhite){
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
    }
}
