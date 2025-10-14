package com.zqhy.app.core.data.model.game.detail;

import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.tryplay.TryGameItemVo;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/20
 */

public class GameActivityVo {

    private int gameid;
    /**
     * 常规折扣
     */
    private float discount;

    /**
     * 限时折扣
     */
    private float flash_discount;

    private long flash_discount_endtime;

    private long rebate_flash_begin;
    private long rebate_flash_end;

    private int showDiscount;

    private int coupon_amount;


    private int game_type;

    private boolean isFold = false;//活动列表是否展开

    public boolean isFold() {
        return isFold;
    }

    public void setFold(boolean fold) {
        isFold = fold;
    }

    public int getGame_type() {
        return game_type;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }

    /**
     * 2019.04.17 新增试玩积分
     */

    private TryGameItemVo.DataBean trial_info;

    public TryGameItemVo.DataBean getTrial_info() {
        return trial_info;
    }

    public void setTrial_info(TryGameItemVo.DataBean trial_info) {
        this.trial_info = trial_info;
    }

    /**
     * 活动列表数据
     */
    private List<GameInfoVo.NewslistBean> activity;

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getFlash_discount() {
        return flash_discount;
    }

    public void setFlash_discount(float flash_discount) {
        this.flash_discount = flash_discount;
    }

    public void setFlash_discount_endtime(long flash_discount_endtime) {
        this.flash_discount_endtime = flash_discount_endtime;
    }

    public long getFlash_discount_endtime() {
        return flash_discount_endtime;
    }

    public int getShowDiscount() {
        return showDiscount;
    }

    public void setShowDiscount(int showDiscount) {
        this.showDiscount = showDiscount;
    }

    public long getRebate_flash_begin() {
        return rebate_flash_begin;
    }

    public void setRebate_flash_begin(long rebate_flash_begin) {
        this.rebate_flash_begin = rebate_flash_begin;
    }

    public long getRebate_flash_end() {
        return rebate_flash_end;
    }

    public void setRebate_flash_end(long rebate_flash_end) {
        this.rebate_flash_end = rebate_flash_end;
    }

    public int getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(int coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public List<GameInfoVo.NewslistBean> getActivity() {
        return activity;
    }

    public void setActivity(List<GameInfoVo.NewslistBean> activity) {
        this.activity = activity;
    }



    /** 20120.01.13 新增***/
    private boolean isUserCommented = false;

    public boolean isUserCommented() {
        return isUserCommented;
    }

    public void setUserCommented(boolean userCommented) {
        isUserCommented = userCommented;
    }

    public static class TopMenuInfoBean {
        private int id;
        private String tag;
        private CharSequence message;
        private int[] resColor;


        public TopMenuInfoBean(int id, String tag, CharSequence message, int[] resColor) {
            this.id = id;
            this.tag = tag;
            this.message = message;
            this.resColor = resColor;
        }


        public int getId() {
            return id;
        }

        public String getTag() {
            return tag;
        }

        public CharSequence getMessage() {
            return message;
        }

        public TopMenuInfoBean setResColor(int... resColor) {
            this.resColor = resColor;
            return this;
        }

        public int[] getResColor() {
            return resColor;
        }
    }

    public static class ItemBean {
        /**
         * 1 活动 跳转活动页url
         * 2 其他根据itemid来判断跳转
         */
        private int type;
        private int gemeId;
        private int tid;

        private GameInfoVo.NewslistBean newslistBean;

        public GameInfoVo.NewslistBean getNewslistBean() {
            return newslistBean;
        }

        public void setNewslistBean(GameInfoVo.NewslistBean newslistBean) {
            this.newslistBean = newslistBean;
        }

        private TopMenuInfoBean menuInfoBean;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getGemeId() {
            return gemeId;
        }

        public void setGemeId(int gemeId) {
            this.gemeId = gemeId;
        }

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public TopMenuInfoBean getMenuInfoBean() {
            return menuInfoBean;
        }

        public void setMenuInfoBean(TopMenuInfoBean menuInfoBean) {
            this.menuInfoBean = menuInfoBean;
        }
    }


    public int getItemCount() {
        int count = 0;

        if(isUserCommented()){
            count++;
        }
        if (getShowDiscount() == 1) {
            count++;
        }
        if (getShowDiscount() == 2) {
            count++;
        }
        if (getCoupon_amount() > 0 && getGame_type() != 1) {
            count++;
        }

        if(getActivity() != null){
            count += getActivity().size();
        }
        return count;
    }


}
