package com.zqhy.app.core.view.game.forum;

import android.text.style.ImageSpan;

public class ForumTextPicBean {
    String span;
    ImageSpan imageSpan;
    boolean canShow = true;
    String url = "";
    String webUrl = "";
    String picUrl = "";//后台追踪图片路径

    int start;
    int end;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public boolean isCanShow() {
        return canShow;
    }

    public void setCanShow(boolean canShow) {
        this.canShow = canShow;
    }

    public String getSpan() {
        return span;
    }

    public void setSpan(String span) {
        this.span = span;
    }

    public ImageSpan getImageSpan() {
        return imageSpan;
    }

    public void setImageSpan(ImageSpan imageSpan) {
        this.imageSpan = imageSpan;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
