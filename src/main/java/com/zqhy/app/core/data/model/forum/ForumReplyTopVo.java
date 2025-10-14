package com.zqhy.app.core.data.model.forum;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/15
 *
 * rid	int	回复ID
 * uid	int	发布者UID
 * plat_id	int	发布者平台ID
 * content	string	回复内容
 * pic	array	图片 最多只有一个
 * floor	int	楼层
 * reply_count	int	回复次数
 * like_count	int	点赞次数
 * nickname	string	发布者昵称
 * icon	string	发布者图像
 * time_description	string	时间描述
 * like_status	int	点赞状态：0：未点赞, 1:已点赞
 * explicit	array	外显二级回复,详细见如下explicit说明
 * game_duration	string	发布者游戏时长 为空不显示

 */

public class ForumReplyTopVo extends BaseVo {
    private List<ForumReplyTopVo> data;

    public List<ForumReplyTopVo> getData() {
        return data;
    }
    boolean showMore = false;
    int page = 1;
    int rid;
    int uid;
    int floor;
    int plat_id;
    int like_status;
    int reply_count;
    int like_count;
    String content;
    String icon;
    String nickname;
    String time_description;
    String game_duration;
    List <String> pic;
    List <ForumReplyTopExplicitVo> explicit;

    public void setData(List<ForumReplyTopVo> data) {
        this.data = data;
    }

    public boolean isShowMore() {
        return showMore;
    }

    public void setShowMore(boolean showMore) {
        this.showMore = showMore;
    }

    public int getRid() {
        return rid;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getLike_status() {
        return like_status;
    }

    public void setLike_status(int like_status) {
        this.like_status = like_status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTime_description() {
        return time_description;
    }

    public void setTime_description(String time_description) {
        this.time_description = time_description;
    }

    public String getGame_duration() {
        return game_duration;
    }

    public void setGame_duration(String game_duration) {
        this.game_duration = game_duration;
    }

    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }

    public List<ForumReplyTopExplicitVo> getExplicit() {
        return explicit;
    }

    public void setExplicit(List<ForumReplyTopExplicitVo> explicit) {
        this.explicit = explicit;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }
}
