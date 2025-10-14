package com.zqhy.app.core.data.model.mainpage;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;

/**
 * @author Administrator
 */
public class FloatItemInfoVo extends BaseVo {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {

        private int sign;
        private FloatJumpInfoVo icon_best;

        public int getSign() {
            return sign;
        }

        public FloatJumpInfoVo getIcon_best() {
            return icon_best;
        }
    }

    public static class FloatJumpInfoVo extends AppJumpInfoBean {

        /**
         * client_type : 3
         * jump_target : 1852
         * title : 新游首发
         * title2 : 一剑西来，天外飞仙
         */

        /**
         * 1 实名认证
         * 2 客服中心
         */
        private int resid;
        private String client_type;
        private String jump_target;
        private String title;
        private String title2;

        public FloatJumpInfoVo(String page_type, ParamBean param) {
            super(page_type, param);
        }

        public String getClient_type() {
            return client_type;
        }

        public void setClient_type(String client_type) {
            this.client_type = client_type;
        }

        public String getJump_target() {
            return jump_target;
        }

        public void setJump_target(String jump_target) {
            this.jump_target = jump_target;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle2() {
            return title2;
        }

        public void setTitle2(String title2) {
            this.title2 = title2;
        }

        public int getResId() {
            return resid;
        }
    }

    public static class ItemVo{
        private int id;
        private int iconRes;
        private String title;
        private String subTitle;
        private boolean isRead;


        public ItemVo(int id, int iconRes, String title, String subTitle, boolean isRead) {
            this.id = id;
            this.iconRes = iconRes;
            this.title = title;
            this.subTitle = subTitle;
            this.isRead = isRead;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIconRes() {
            return iconRes;
        }

        public void setIconRes(int iconRes) {
            this.iconRes = iconRes;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public boolean isRead() {
            return isRead;
        }

        public void setRead(boolean read) {
            isRead = read;
        }

        private FloatJumpInfoVo jumpInfoVo;

        public FloatJumpInfoVo getJumpInfoVo() {
            return jumpInfoVo;
        }

        public void setJumpInfoVo(FloatJumpInfoVo jumpInfoVo) {
            this.jumpInfoVo = jumpInfoVo;
        }
    }
}
