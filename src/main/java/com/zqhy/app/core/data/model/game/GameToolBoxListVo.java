package com.zqhy.app.core.data.model.game;

public class GameToolBoxListVo {

    public GameToolBoxListVo(int type, boolean isUnfold) {
        this.type = type;
        this.isUnfold = isUnfold;
    }

    private int type;
    private boolean isUnfold;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isUnfold() {
        return isUnfold;
    }

    public void setUnfold(boolean unfold) {
        isUnfold = unfold;
    }
}
