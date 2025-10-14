package com.zqhy.app.core.data.model;

/**
 *
 * @author Administrator
 * @date 2018/12/17
 */

public class ThumbnailBean {
    /***0 缩略图   1 加号键*****/
    private int type;

    /***0本地图片  1 网络图片 2 资源图片******/
    private int imageType;

    /**
     * 本地图片地址
     */
    private String localUrl;
    /**
     * 网络图片地址
     */
    private String httpUrl;

    /**
     * 资源图片地址
     */
    private int imageResId;

    /**
     * 网络图片 图片id
     */
    private String pic_id;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public String getPic_id() {
        return pic_id;
    }

    public void setPic_id(String pic_id) {
        this.pic_id = pic_id;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
