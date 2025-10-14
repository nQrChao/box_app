package com.zqhy.app.core.data.model.video;

/**
 * @author Administrator
 */
public class VideoParamVo {

    /**
     * video_url : https://......
     * video_title : 视频标题
     * video_description : 视频描述
     * video_preview : https://xxx.pic.xxx
     * video_width : 视频长度
     * video_height : 视频高度
     */

    private String video_url;
    private String video_title;
    private String video_description;
    private String video_preview;
    private int video_width;
    private int video_height;

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getVideo_description() {
        return video_description;
    }

    public void setVideo_description(String video_description) {
        this.video_description = video_description;
    }

    public String getVideo_preview() {
        return video_preview;
    }

    public void setVideo_preview(String video_preview) {
        this.video_preview = video_preview;
    }

    public int getVideo_width() {
        return video_width;
    }

    public void setVideo_width(int video_width) {
        this.video_width = video_width;
    }

    public int getVideo_height() {
        return video_height;
    }

    public void setVideo_height(int video_height) {
        this.video_height = video_height;
    }
}
