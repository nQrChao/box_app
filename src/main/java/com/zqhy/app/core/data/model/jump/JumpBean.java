package com.zqhy.app.core.data.model.jump;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.new0809.AppMenuVo;
import com.zqhy.app.core.data.model.video.VideoParamVo;

public class JumpBean extends BaseVo {

    private JumpInfoBean data;

    public void setData(JumpInfoBean data) {
        this.data = data;
    }

    public JumpInfoBean getData() {
        return data;
    }

    public static class JumpInfoBean {


        public String page_type;
        public JumpInfoBean.ParamBean param;

        public JumpInfoBean(String page_type, JumpInfoBean.ParamBean param) {
            this.page_type = page_type;
            this.param = param;
        }

        public String getPage_type() {
            return page_type;
        }

        public JumpInfoBean.ParamBean getParam() {
            return param;
        }

        public static class ParamBean {
            /**
             * gameid : 4222
             * game_type : 1
             */

            private int gameid;
            private int game_type;
            /**
             * newsid : 123
             */

            private String newsid;
            /**
             * target_url : http://www.lehihi.com
             */

            private String target_url;
            /**
             * game_list_id : 4222
             */

            private String game_list_id;
            private String gid;

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            private String container_id;

            public String getContainer_id() {
                return container_id;
            }

            public void setContainer_id(String container_id) {
                this.container_id = container_id;
            }

            public int getGameid() {
                return gameid;
            }

            public void setGameid(int gameid) {
                this.gameid = gameid;
            }

            public int getGame_type() {
                return game_type;
            }

            public void setGame_type(int game_type) {
                this.game_type = game_type;
            }

            public String getNewsid() {
                return newsid;
            }

            public void setNewsid(String newsid) {
                this.newsid = newsid;
            }

            public String getTarget_url() {
                return target_url;
            }

            public void setTarget_url(String target_url) {
                this.target_url = target_url;
            }

            public String getGame_list_id() {
                return game_list_id;
            }

            public void setGame_list_id(String game_list_id) {
                this.game_list_id = game_list_id;
            }


            /****2018.05.08*******************************************/

            private String share_title;
            private String share_text;
            private String share_target_url;
            private String share_image;

            public String getShare_title() {
                return share_title;
            }

            public String getShare_text() {
                return share_text;
            }

            public String getShare_target_url() {
                return share_target_url;
            }

            public String getShare_image() {
                return share_image;
            }

            /****2019.05.28*******************************************/

            private VideoParamVo video_param;

            public VideoParamVo getVideo_param() {
                return video_param;
            }


            /*****2019.07.04 新增************************************/

            /**
             * 是否是列表页
             * <p>
             * 1跳转任务列表，
             * 0跳转单个任务详情页
             */
            private int trial_list;

            /**
             * 试玩任务详情页id
             */
            private String trial_id;

            public int getTrial_list() {
                return trial_list;
            }

            public String getTrial_id() {
                return trial_id;
            }


            /**
             * 游戏/礼包页面
             * tab的位置
             */
            private int tab_position;

            public int getTab_position() {
                return tab_position;
            }

            private int tab_id;

            public int getTab_id() {
                return tab_id;
            }

            private AppMenuVo.ParamsBean heji_params;

            public AppMenuVo.ParamsBean getHeji_params() {
                return heji_params;
            }

            private int type;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            private int tid;
            private int feature;

            public int getFeature() {
                return feature;
            }

            public void setFeature(int feature) {
                this.feature = feature;
            }

            public int getTid() {
                return tid;
            }

            public void setTid(int tid) {
                this.tid = tid;
            }
        }
    }


}
