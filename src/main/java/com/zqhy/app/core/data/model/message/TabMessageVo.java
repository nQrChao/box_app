package com.zqhy.app.core.data.model.message;

/**
 * Created by Administrator on 2018/11/13.
 */

public class TabMessageVo {

    private int tabId;
    private int iconRes;

    private String title;
    private String subTitle;

    private int unReadCount;

    private int isShowUnReadCount;

    public int getTabId() {
        return tabId;
    }

    public void setTabId(int tabId) {
        this.tabId = tabId;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }

    public void addUnReadCount(int unReadCount){
        this.unReadCount += unReadCount;
    }

    public int getIsShowUnReadCount() {
        return isShowUnReadCount;
    }

    public void setIsShowUnReadCount(int isShowUnReadCount) {
        this.isShowUnReadCount = isShowUnReadCount;
    }
}
