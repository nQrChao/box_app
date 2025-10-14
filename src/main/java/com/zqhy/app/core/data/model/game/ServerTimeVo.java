package com.zqhy.app.core.data.model.game;

/**
 * @author pc
 * @date 2019/12/3-10:18
 * @description
 */
public class ServerTimeVo {

    /**
     * 秒级
     */
    private long time;

    private int indexTimer;

    public ServerTimeVo(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public int getIndexTimer() {
        return indexTimer;
    }

    public void setIndexTimer(int indexTimer) {
        this.indexTimer = indexTimer;
    }
}
