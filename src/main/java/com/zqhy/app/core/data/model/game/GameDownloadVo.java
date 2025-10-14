package com.zqhy.app.core.data.model.game;

/**
 * @author Administrator
 * @date 2018/11/23
 */

public class GameDownloadVo {

    private boolean isManager;

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    private String downloadTag;

    public String getDownloadTag() {
        return downloadTag;
    }

    public void setDownloadTag(String downloadTag) {
        this.downloadTag = downloadTag;
    }
}
