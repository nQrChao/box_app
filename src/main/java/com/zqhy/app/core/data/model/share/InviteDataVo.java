package com.zqhy.app.core.data.model.share;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author Administrator
 * @date 2018/11/23
 */

public class InviteDataVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {

        private int count;
        private float sum;
        private int integral_sum;

        private InviteDataInfoVo invite_info;

        public int getCount() {
            return count;
        }

        public float getSum() {
            return sum;
        }

        public int getIntegral_sum() {
            return integral_sum;
        }

        public InviteDataInfoVo getInvite_info() {
            return invite_info;
        }
    }

    public static class InviteDataInfoVo {

        /**
         * wx_title : ☞中国最专业的手游福利平台！
         * wx_description : 手游福利，上线即送满VIP，无限元宝，充值3折起,5千款手游任选！
         * wx_group : 手游福利，上线送满V，无限元宝，充值3折起,5千款手游任选
         * qq_title : 九妖游戏
         * qq_description : 上线送满V+无限元宝，充值3折起,5千款任选!
         * qqzone_title : 中国最专业的手游福利平台！
         * qqzone_description : 上线送满VIP,无限元宝,充值3折起!
         * copy_title : 九妖游戏
         * copy_description : 九妖游戏福利，上线即送满VIP，无限元宝，充值3折起,5千款手游任选！
         * icon : http://p1.jiuyao.yuzhuagame.com/2018/11/22/5bf653c9cffe2.png
         * description :
         * url : /index.php/index/share_reg/vid/1/tgid/a12345678
         */

        private String wx_title;
        private String wx_description;
        private String wx_group;
        private String qq_title;
        private String qq_description;
        private String qqzone_title;
        private String qqzone_description;
        private String copy_title;
        private String copy_description;
        private String icon;
        private String description;
        private String url;

        public String getWx_title() {
            return wx_title;
        }

        public void setWx_title(String wx_title) {
            this.wx_title = wx_title;
        }

        public String getWx_description() {
            return wx_description;
        }

        public void setWx_description(String wx_description) {
            this.wx_description = wx_description;
        }

        public String getWx_group() {
            return wx_group;
        }

        public void setWx_group(String wx_group) {
            this.wx_group = wx_group;
        }

        public String getQq_title() {
            return qq_title;
        }

        public void setQq_title(String qq_title) {
            this.qq_title = qq_title;
        }

        public String getQq_description() {
            return qq_description;
        }

        public void setQq_description(String qq_description) {
            this.qq_description = qq_description;
        }

        public String getQqzone_title() {
            return qqzone_title;
        }

        public void setQqzone_title(String qqzone_title) {
            this.qqzone_title = qqzone_title;
        }

        public String getQqzone_description() {
            return qqzone_description;
        }

        public void setQqzone_description(String qqzone_description) {
            this.qqzone_description = qqzone_description;
        }

        public String getCopy_title() {
            return copy_title;
        }

        public void setCopy_title(String copy_title) {
            this.copy_title = copy_title;
        }

        public String getCopy_description() {
            return copy_description;
        }

        public void setCopy_description(String copy_description) {
            this.copy_description = copy_description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
