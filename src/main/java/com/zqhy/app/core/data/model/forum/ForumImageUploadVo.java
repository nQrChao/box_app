package com.zqhy.app.core.data.model.forum;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/15
 */

public class ForumImageUploadVo extends BaseVo{
    //flag	string	图标上传标记
    //filename	string	上传成功后图标路径
    //pic_url	string	图片的网络地址
    ForumImageUploadVo data;
    String flag;
    String filename;
    String pic_url;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public ForumImageUploadVo getData() {
        return data;
    }

    public void setData(ForumImageUploadVo data) {
        this.data = data;
    }
}
