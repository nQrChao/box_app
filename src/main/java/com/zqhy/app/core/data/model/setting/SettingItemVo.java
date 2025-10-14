package com.zqhy.app.core.data.model.setting;

/**
 * @author Administrator
 * @date 2018/11/14
 */

public class SettingItemVo {

    private int viewID;

    private String txt;

    private String subTxt;

    public SettingItemVo(int viewID, String txt, String subTxt) {
        this.viewID = viewID;
        this.txt = txt;
        this.subTxt = subTxt;
    }

    public int getViewID() {
        return viewID;
    }

    public String getTxt() {
        return txt;
    }

    public String getSubTxt() {
        return subTxt;
    }

    public void setViewID(int viewID) {
        this.viewID = viewID;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public void setSubTxt(String subTxt) {
        this.subTxt = subTxt;
    }
}
