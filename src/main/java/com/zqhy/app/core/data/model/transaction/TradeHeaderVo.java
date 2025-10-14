package com.zqhy.app.core.data.model.transaction;

/**
 * @author Administrator
 */
public class TradeHeaderVo {

    private String description;

    public TradeHeaderVo(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
