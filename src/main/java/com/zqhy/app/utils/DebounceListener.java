package com.zqhy.app.utils;

import android.view.View;

public abstract class DebounceListener implements View.OnClickListener  {
    private long lastClickTime;
    private long interval = 1000L;
    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > interval) {
            onClick();
            lastClickTime = currentTime;
        }
    }
    protected abstract void onClick();
}
