package com.zqhy.app.core.data.model.welfare;

import android.text.TextUtils;

import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.utils.CommonUtils;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/15
 */

public class MyGiftCardListVo extends BaseVo{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean{

        /**
         * gameid : 23
         * gamename : 鬼畜大作战BT版-全新神器7
         * game_type : 1
         * gameicon : http://p1.btgame01.com/2018/09/07/5b92344f65fc8.png
         * genre_str : 角色 • 策略
         * cardname : 新手礼包
         * card : 礼包①
         * gettime : 1542697572
         */

        private int gameid;
        private String gamename;
        private int game_type;
        private String gameicon;
        private String genre_str;
        private String cardname;
        private String card;
        private String gettime;

        public int getGameid() {
            return gameid;
        }

        public String getGamename() {
            return CommonUtils.getGamename(gamename);
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

        public String getCardname() {
            return cardname;
        }

        public String getCard() {
            return card;
        }

        public String getGettime() {
            return gettime;
        }
    }
}
