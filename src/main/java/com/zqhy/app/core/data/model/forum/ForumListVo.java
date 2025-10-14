package com.zqhy.app.core.data.model.forum;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/15
 *
 * tid	int	主题ID
 * title	string	标题
 * summary	string	正文简介
 * feature	int	特征, 1:图文分开; 2:图文并茂
 * pic	array	图片
 * view_count	int	浏览次数
 * reply_count	int	回复次数
 * like_count	int	点赞次数
 * sticky	int	是否置顶 1：是; 0:否
 * nickname	string	发布者昵称
 * icon	string	发布者图像
 * time_description	string	时间描述
 * like_status	int	点赞状态：0：未点赞, 1:已点赞
 */

public class ForumListVo extends BaseVo{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean {
        int tid;
        int uid;
        int plat_id;
        int cate_id;
        int gameid;
        int feature;
        int view_count;
        int reply_count;
        int like_count;
        int sticky;
        int like_status;
        String title;
        String summary;
        String nickname;
        String game_duration;
        String time_description;
        String icon;
        List <String>  pic;
        long add_time;

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getGame_duration() {
            return game_duration;
        }

        public void setGame_duration(String game_duration) {
            this.game_duration = game_duration;
        }

        public int getPlat_id() {
            return plat_id;
        }

        public void setPlat_id(int plat_id) {
            this.plat_id = plat_id;
        }

        public int getCate_id() {
            return cate_id;
        }

        public void setCate_id(int cate_id) {
            this.cate_id = cate_id;
        }

        public int getGameid() {
            return gameid;
        }

        public void setGameid(int gameid) {
            this.gameid = gameid;
        }

        public int getFeature() {
            return feature;
        }

        public void setFeature(int feature) {
            this.feature = feature;
        }

        public int getView_count() {
            return view_count;
        }

        public void setView_count(int view_count) {
            this.view_count = view_count;
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

        public int getSticky() {
            return sticky;
        }

        public void setSticky(int sticky) {
            this.sticky = sticky;
        }

        public int getLike_status() {
            return like_status;
        }

        public void setLike_status(int like_status) {
            this.like_status = like_status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<String> getPic() {
            return pic;
        }

        public void setPic(List<String> pic) {
            this.pic = pic;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }
    }

}
