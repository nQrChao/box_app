package com.zqhy.app.core.data.model.user.newvip;

/**
 * @author leeham2734
 * @date 2021/2/3-12:11
 * @description
 */
public class VipMenuVo {
    private int type;
    private String title;
    private String content;

    public VipMenuVo(int type, String title, String content) {
        this.type = type;
        this.title = title;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
