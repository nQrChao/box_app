package com.zqhy.app.core.data.model.user;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

public class MoneyCardChangeVo extends BaseVo {
    public List<CardChangeInfo> data;

    public List<CardChangeInfo> getData() {
        return data;
    }

    public class CardChangeInfo{
        /**
         *         "id": 1,
         *         "create_time":"1561234567",//记录时间
         *         "edit_expiry_time": "1561234567",//修改后有效期
         */

        private int id;
        private String create_time;
        private String edit_expiry_time;
        private int type; //2 省钱卡；5 折扣省钱卡

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getEdit_expiry_time() {
            return edit_expiry_time;
        }

        public void setEdit_expiry_time(String edit_expiry_time) {
            this.edit_expiry_time = edit_expiry_time;
        }
    }
}
