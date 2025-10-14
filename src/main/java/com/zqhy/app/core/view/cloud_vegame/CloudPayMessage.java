package com.zqhy.app.core.view.cloud_vegame;

import org.litepal.crud.LitePalSupport;

public class CloudPayMessage extends LitePalSupport {
    String message;

    public CloudPayMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
