package com.zqhy.app.core.data.model;

/**
 * @author Administrator
 */
public abstract class AbsCountDownVo {

    private long endTime;

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
