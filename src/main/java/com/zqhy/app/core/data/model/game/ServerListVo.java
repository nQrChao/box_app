package com.zqhy.app.core.data.model.game;

import android.text.TextUtils;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.utils.CommonUtils;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/8
 */

public class ServerListVo extends BaseVo {


    private List<DataBean> data;

    public ServerListVo(List<DataBean> data) {
        this.data = data;
    }

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean {


        /**
         * gameid : 23
         * gamename : 鬼畜大作战BT版-全新神器7
         * game_type : 1
         * gameicon : http://p1.btgame01.com/2018/09/07/5b92344f65fc8.png
         * genre_str :
         * servername : 18服
         * serverid : 18
         * begintime : 1542470400
         * payrate : 500
         * client_size : 0
         * is_flash : 0
         * discount : 0
         * flash_discount : 0
         */

        private int gameid;
        private String gamename;
        private int game_type;
        private String gameicon;
        private String genre_str;
        private String servername;
        private int serverid;
        private long begintime;
        private int payrate;
        private float client_size;
        private int is_flash;
        private float discount;
        private float flash_discount;

        private int unshare;//区分是不是专服游戏
        private int free;//区分是不是免费游戏

        private int gdm;//1表示GM游戏

        public int getGdm() {
            if (!TextUtils.isEmpty(genre_str) && (genre_str.contains("GM") || genre_str.contains("gm") || genre_str.contains("Gm") || genre_str.contains("gM"))){
                return 1;
            }
            return 0;
        }

        public void setGdm(int gdm) {
            this.gdm = gdm;
        }

        public int getFree() {
            return free;
        }

        public void setFree(int free) {
            this.free = free;
        }

        public int getUnshare() {
            return unshare;
        }

        public void setUnshare(int unshare) {
            this.unshare = unshare;
        }

        private GameInfoVo.GameLabelsBean first_label;

        private long play_count;

        private int selected_game;//是否是精选游戏 1 是  2 不是

        public int getSelected_game() {
            return selected_game;
        }

        public void setSelected_game(int selected_game) {
            this.selected_game = selected_game;
        }

        public long getPlay_count() {
            return play_count;
        }

        public void setPlay_count(long play_count) {
            this.play_count = play_count;
        }

        public int getGameid() {
            return gameid;
        }

        public String getGamename() {
            return CommonUtils.getGamename(gamename);
        }

        public String getOtherGameName(){
            return CommonUtils.getOtherGameName(gamename);
        }

        public int getGame_type() {
            return game_type;
        }

        public String getGameicon() {
            return gameicon;
        }

        public String getGenre_str() {
            return genre_str;
        }

        public String getServername() {
            return servername;
        }

        public int getServerid() {
            return serverid;
        }

        public long getBegintime() {
            return begintime;
        }

        public int getPayrate() {
            return payrate;
        }


        public int getIs_flash() {
            return is_flash;
        }

        public float getClient_size() {
            return client_size;
        }

        public float getDiscount() {
            return discount;
        }

        public float getFlash_discount() {
            return flash_discount;
        }

        public int hide_discount_label;

        public int getHide_discount_label() {
            if(game_type == 1){
                return 1;
            }
            return hide_discount_label;
        }

        /**
         *
         * @return 0 隐藏全部折扣  1 显示普通折扣  2显示限时折扣
         */
        public int showDiscount(){
            if(getHide_discount_label() == 1){
                return 0;
            }else {
                if(is_flash == 1){
                    return 2;
                }else{
                    return 1;
                }
            }
        }


        public GameInfoVo.GameLabelsBean getFirst_label() {
            return first_label;
        }
    }
}
