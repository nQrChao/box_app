package com.zqhy.app.core.data.model.activity;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/17
 */

public class ActivityInfoListVo extends BaseVo{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean{

        /**
         * fabutime : 1491899200
         * gameicon : http://p1.btgame01.yaohihi.com/2017/04/08/58e891db19921.png
         * gameid : 2
         * gamename : 倚天屠龙记BT
         * id : 284
         * title : 倚天双12活动
         * title2 : 20171211 - 20171220
         */

        private String fabutime;
        private String gameicon;
        private int gameid;
        private String gamename;
        private String id;
        private String title;
        private String title2;
        /**
         * apksize : 93.3
         * content : <p> 说的是电饭锅是否</p>
         * genre_name : 挂机游戏
         * payrate : 300
         */

        private String apksize;
        private String content;
        private String genre_name;
        private String payrate;

        private String pic;
        /**
         * icon : http://p1.btgame01.com/2017/07/11/596476e2e376e.png
         */

        private String icon;
        private String url;


        private int type;

        public String getFabutime() {
            return fabutime;
        }

        public String getGameicon() {
            return gameicon;
        }

        public int getGameid() {
            return gameid;
        }

        public String getGamename() {
            return gamename;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getTitle2() {
            return title2;
        }

        public String getApksize() {
            return apksize;
        }

        public String getContent() {
            return content;
        }

        public String getGenre_name() {
            return genre_name;
        }

        public String getPayrate() {
            return payrate;
        }

        public String getPic() {
            return pic;
        }

        public String getIcon() {
            return icon;
        }

        public String getUrl() {
            return url;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

}
