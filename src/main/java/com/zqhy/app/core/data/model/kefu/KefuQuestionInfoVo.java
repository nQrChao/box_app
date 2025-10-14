package com.zqhy.app.core.data.model.kefu;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.List;

/**
 * @author Administrator
 */
public class KefuQuestionInfoVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean{
        private List<ListItemBean> list;
        private int status;

        private VipKefuInfoDataVo.DataBean vipinfo;

        public List<ListItemBean> getList() {
            return list;
        }

        public void setList(List<ListItemBean> list) {
            this.list = list;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public VipKefuInfoDataVo.DataBean getVipinfo() {
            return vipinfo;
        }

        public void setVipinfo(VipKefuInfoDataVo.DataBean vipinfo) {
            this.vipinfo = vipinfo;
        }
    }

    public static class ListItemBean{
        private String name;
        private List<ItemBean> list;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ItemBean> getList() {
            return list;
        }

        public void setList(List<ItemBean> list) {
            this.list = list;
        }
    }

    public static class ItemBean {
        /**
         * id : 19
         * typeid : 3
         * question : 游戏内悬浮球
         * answer : 如何开启游戏内悬浮球: 方法1:手机【设置】--【应用】--当前游戏名称--【权限管理】--悬浮窗打勾 方法2:手机【安全中心】--【授权管理】--【应用权管理】--【应用管理】--当前所玩游戏--悬浮窗打勾
         * addtime : 1558519195
         */

        private String id;
        private String typeid;
        private String question;
        private String answer;
        private String addtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
