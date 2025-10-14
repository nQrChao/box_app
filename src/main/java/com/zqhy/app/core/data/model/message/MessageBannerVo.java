package com.zqhy.app.core.data.model.message;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/13
 */

public class MessageBannerVo extends BaseVo{

    private BannerListVo data;

    public BannerListVo getData() {
        return data;
    }

    public static class BannerListVo{
        List<ADINFO> msg_ad_list;

        public List<ADINFO> getMsg_ad_list() {
            return msg_ad_list;
        }

        public void setMsg_ad_list(List<ADINFO> msg_ad_list) {
            this.msg_ad_list = msg_ad_list;
        }

        public static class ADINFO extends AppBaseJumpInfoBean {

            /**
             * page_type : gameinfo
             * ad_pic : http://p1.btgame01.com/2018/11/01/5bdaa9f732673.png
             * title : ceshi
             * param : {"gameid":"5555","game_type":"2"}
             */

            private String ad_pic;
            private String title;

            public ADINFO(String page_type, ParamBean param) {
                super(page_type, param);
            }

            public String getAd_pic() {
                return ad_pic;
            }

            public void setAd_pic(String ad_pic) {
                this.ad_pic = ad_pic;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
