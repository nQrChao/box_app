package com.zqhy.app.core.data.model.game;

import java.io.Serializable;

/**
 * @author Administrator
 * @date 2018/11/30
 */

public class GameDownloadTimeExtraVo implements Serializable {
    private long last_refresh_time;//最后一次刷新的时间
    private long download_time;//下载总时长
    private int id;//下载事件id
    private int type;//分包模式

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLast_refresh_time() {
        return last_refresh_time;
    }

    public void setLast_refresh_time(long last_refresh_time) {
        this.last_refresh_time = last_refresh_time;
    }

    public long getDownload_time() {
        return download_time;
    }

    public void setDownload_time(long download_time) {
        this.download_time = download_time;
    }
}
