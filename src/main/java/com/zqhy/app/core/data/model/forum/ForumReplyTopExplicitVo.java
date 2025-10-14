package com.zqhy.app.core.data.model.forum;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/15

 * explicit数据说明
 * 字段名	类型	说明
 * rid	int	回复ID
 * uid	int	发布者UID
 * plat_id	int	发布者平台ID
 * content	string	回复内容
 * nickname	string	发布者昵称
 * at_uid	int	@的用户UID
 * at_plat_id	int	@的用户平台ID
 * at_nickname	string	@的用户的昵称
 */

public class ForumReplyTopExplicitVo extends BaseVo {
    List<ForumReplyTopExplicitVo> data;

    public List<ForumReplyTopExplicitVo> getData() {
        return data;
    }

    public void setData(List<ForumReplyTopExplicitVo> data) {
        this.data = data;
    }

    int rid;
    int uid;
    int plat_id;
    int at_plat_id;
    int at_uid;
    String content;
    String nickname;
    String at_nickname = "";

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPlat_id() {
        return plat_id;
    }

    public void setPlat_id(int plat_id) {
        this.plat_id = plat_id;
    }

    public int getAt_plat_id() {
        return at_plat_id;
    }

    public void setAt_plat_id(int at_plat_id) {
        this.at_plat_id = at_plat_id;
    }

    public int getAt_uid() {
        return at_uid;
    }

    public void setAt_uid(int at_uid) {
        this.at_uid = at_uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAt_nickname() {
        return at_nickname;
    }

    public void setAt_nickname(String at_nickname) {
        this.at_nickname = at_nickname;
    }
}
