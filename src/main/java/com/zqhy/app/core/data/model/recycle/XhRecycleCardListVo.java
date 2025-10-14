package com.zqhy.app.core.data.model.recycle;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author leeham2734
 * @date 2021/7/13-13:55
 * @description
 */
public class XhRecycleCardListVo extends BaseVo {
    RecycleCouponListVo data;


    public RecycleCouponListVo getData() {
        return data;
    }

    public void setData(RecycleCouponListVo data) {
        this.data = data;
    }

    public class RecycleCouponListVo{
        List<DataBean> list;

        String normal_url;
        String discount_url;

        public List<DataBean> getList() {
            return list;
        }

        public void setList(List<DataBean> list) {
            this.list = list;
        }

        public String getNormal_url() {
            return normal_url;
        }

        public void setNormal_url(String normal_url) {
            this.normal_url = normal_url;
        }

        public String getDiscount_url() {
            return discount_url;
        }

        public void setDiscount_url(String discount_url) {
            this.discount_url = discount_url;
        }
    }

    public static class DataBean {
        private int    card_id;
        private String name;
        private String expir;
        private String des;
        private int    amount;
        private int    num;

        public int getCard_id() {
            return card_id;
        }

        public void setCard_id(int card_id) {
            this.card_id = card_id;
        }

        public String getExpir() {
            return expir;
        }

        public void setExpir(String expir) {
            this.expir = expir;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
