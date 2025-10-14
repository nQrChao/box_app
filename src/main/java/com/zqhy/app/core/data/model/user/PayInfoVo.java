package com.zqhy.app.core.data.model.user;

import com.zqhy.app.core.data.model.BaseVo;

/**
 * @author Administrator
 * @date 2018/11/23
 */

public class PayInfoVo extends BaseVo {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {

        /**
         * version : v1
         * pay_str : partner="2088421685539054"&seller_id="2088421685539054"&out_trade_no="201811211819440000000235"&subject=""&body=""&total_fee="1"¬ify_url="http://pay.jiuyao.yuzhuagame.com/index.php/Alipay/notify_url"&service="alipay.wap.create.direct.pay.by.user"&payment_type="1"&_input_charset="utf-8"&it_b_pay="30m"&sign="ORHEcNueVkzzIXNp2TDxqYeWp%2FY%2Fb7saBe4RSRrPL5k57q9CDqhtSGZaQhE2ObfiFqXINScDpgWg3OKXM9PtMyYQn8TjGMZPkVf4yVErxjPKtR%2Bk51G6jkQErTZhAK7tB9YcDzfd1cjG%2FfgiZTRj6swAoAes%2FKPBLRRoXIx7OGE%3D"&sign_type="RSA"
         * out_trade_no : 201811211819440000000235
         */

        private String version;
        private String pay_str;
        private String out_trade_no;
        private String amount;
        /**
         * wx_url : http://pay.jiuyao.yuzhuagame.com/index.php/Wxpay/wxPayCreate/?out_trade_no=201811211825240000000236
         * package : com.zqhy.asia.wxplugin
         * wx_plug_url : http://p1.jiuyao.yuzhuagame.com/android_tools/btgame_wechat_pay_112.apk
         * wx_plug_name : BTGame收银台
         * wx_plug_icon : http://p1.btgame01.com/wx/icon.png
         */

        private String wx_url;
        private int versionCode;
        private String package_name;
        private String classname;
        private String wx_plug_url;
        private String wx_plug_name;
        private String wx_plug_icon;

        private String act;
        private String pay_url;

        public String getVersion() {
            return version;
        }

        public String getPay_str() {
            return pay_str;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public String getWx_url() {
            return wx_url;
        }

        public String getPackage_name() {
            return package_name;
        }

        public String getClassName() {
            return classname;
        }

        public String getWx_plug_url() {
            return wx_plug_url;
        }

        public String getWx_plug_name() {
            return wx_plug_name;
        }

        public String getWx_plug_icon() {
            return wx_plug_icon;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public String getAct() {
            return act;
        }

        public String getPay_url() {
            return pay_url;
        }

        public String getAmount() {
            return amount;
        }
    }
}
