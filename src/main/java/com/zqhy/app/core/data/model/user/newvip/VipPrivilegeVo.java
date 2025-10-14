package com.zqhy.app.core.data.model.user.newvip;

/**
 * @author leeham2734
 * @date 2021/2/3-12:11
 * @description
 */
public class VipPrivilegeVo {
    private int icon;
    private String tips;
    private String title;
    private String content;

    public VipPrivilegeVo(int icon, String tips, String title, String content) {
        this.icon = icon;
        this.tips = tips;
        this.title = title;
        this.content = content;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
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
