package com.zqhy.app.core.data.model.user.newvip;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.new0809.XinRenPopDataVo;
import com.zqhy.app.core.data.model.version.AppPopDataVo;
import com.zqhy.app.core.data.model.version.UnionVipDataVo;
import com.zqhy.app.core.data.model.version.VersionVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2021/2/3-12:11
 * @description
 */
public class AllPopVo extends BaseVo {

    private DataBean data;

    public void setData(DataBean data) {
        this.data = data;
    }

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        private VersionVo.DataBean get_version;//更新弹窗
        private XinRenPopDataVo.DataBean xinren_pop;//新人福利弹窗
        private ComeBackVo.DataBean come_back;//回归弹窗
        private RmbusergiveVo.DataBean rmbusergive;//大R奖励弹窗
        private SuperBirthdayRewardVo.DataBean super_user_birthday_reward_status;//生日弹窗
        private UnionVipDataVo.DataBean kefu_cps_vip_pop;//公会客服弹窗
        private List<AppPopDataVo.DataBean> home_page_pop;//自定义弹窗

        public VersionVo.DataBean getGet_version() {
            return get_version;
        }

        public void setGet_version(VersionVo.DataBean get_version) {
            this.get_version = get_version;
        }

        public XinRenPopDataVo.DataBean getXinren_pop() {
            return xinren_pop;
        }

        public void setXinren_pop(XinRenPopDataVo.DataBean xinren_pop) {
            this.xinren_pop = xinren_pop;
        }

        public ComeBackVo.DataBean getCome_back() {
            return come_back;
        }

        public void setCome_back(ComeBackVo.DataBean come_back) {
            this.come_back = come_back;
        }

        public RmbusergiveVo.DataBean getRmbusergive() {
            return rmbusergive;
        }

        public void setRmbusergive(RmbusergiveVo.DataBean rmbusergive) {
            this.rmbusergive = rmbusergive;
        }

        public SuperBirthdayRewardVo.DataBean getSuper_user_birthday_reward_status() {
            return super_user_birthday_reward_status;
        }

        public void setSuper_user_birthday_reward_status(SuperBirthdayRewardVo.DataBean super_user_birthday_reward_status) {
            this.super_user_birthday_reward_status = super_user_birthday_reward_status;
        }

        public UnionVipDataVo.DataBean getKefu_cps_vip_pop() {
            return kefu_cps_vip_pop;
        }

        public void setKefu_cps_vip_pop(UnionVipDataVo.DataBean kefu_cps_vip_pop) {
            this.kefu_cps_vip_pop = kefu_cps_vip_pop;
        }

        public List<AppPopDataVo.DataBean> getHome_page_pop() {
            return home_page_pop;
        }

        public void setHome_page_pop(List<AppPopDataVo.DataBean> home_page_pop) {
            this.home_page_pop = home_page_pop;
        }
    }
}
