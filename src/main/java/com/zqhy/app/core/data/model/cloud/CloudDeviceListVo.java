package com.zqhy.app.core.data.model.cloud;

import com.zqhy.app.core.data.model.BaseVo;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/15
 */

public class CloudDeviceListVo extends BaseVo{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        private int queuePriority;//排队等级 99为最低等级 最高1
        private List<DeviceBean> list;
        private String base_url;
        private int new_user;
        private int freetime;//免费时长

        public int getFreetime() {
            return freetime;
        }

        public void setFreetime(int freetime) {
            this.freetime = freetime;
        }

        public List<DeviceBean> getList() {
            return list;
        }

        public void setList(List<DeviceBean> list) {
            this.list = list;
        }

        public String getBase_url() {
            return base_url;
        }

        public void setBase_url(String base_url) {
            this.base_url = base_url;
        }

        public int getNew_user() {
            return new_user;
        }

        public void setNew_user(int new_user) {
            this.new_user = new_user;
        }

        public int getQueuePriority() {
            return queuePriority;
        }

        public void setQueuePriority(int queuePriority) {
            this.queuePriority = queuePriority;
        }
    }

    public static class DeviceBean implements Serializable {
        private String id;
        private String name;
        private String gameid;
        private String gamecode;
        private String expiry_time;
        private String gamename;
        private String gameicon;
        private String cloud_game_id;//火山云游戏id
        private String reserved_id;//火山云资源id
        private String cloud_type;//gameid不为空且值为1，纸片云挂机中 ，值为2，火山云挂机中

        public String getCloud_type() {
            return cloud_type;
        }

        public void setCloud_type(String cloud_type) {
            this.cloud_type = cloud_type;
        }

        public String getReserved_id() {
            return reserved_id;
        }

        public void setReserved_id(String reserved_id) {
            this.reserved_id = reserved_id;
        }

        public String getCloud_game_id() {
            return cloud_game_id;
        }

        public void setCloud_game_id(String cloud_game_id) {
            this.cloud_game_id = cloud_game_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGameid() {
            return gameid;
        }

        public void setGameid(String gameid) {
            this.gameid = gameid;
        }

        public String getGamecode() {
            return gamecode;
        }

        public void setGamecode(String gamecode) {
            this.gamecode = gamecode;
        }

        public String getExpiry_time() {
            return expiry_time;
        }

        public void setExpiry_time(String expiry_time) {
            this.expiry_time = expiry_time;
        }

        public String getGameicon() {
            return gameicon;
        }

        public void setGameicon(String gameicon) {
            this.gameicon = gameicon;
        }

        public String getGamename() {
            return gamename;
        }

        public void setGamename(String gamename) {
            this.gamename = gamename;
        }
    }

}
