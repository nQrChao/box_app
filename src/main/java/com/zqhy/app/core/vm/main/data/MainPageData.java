package com.zqhy.app.core.vm.main.data;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.zqhy.app.core.data.model.video.VideoParamVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leeham2734
 * @date 2021/3/25-17:02
 * @description
 */
public class MainPageData {

    @SerializedName(value = "slider_list")
    public List<BannerData> sliderList;

    @SerializedName(value = "chaping_list")
    public List<BannerData> chaping_list;

    @SerializedName(value = "tutui_list")
    public List<BannerData> adList;

    @SerializedName(value = "hot_list")
    public List<GameItemData> hotList;

    @SerializedName(value = "coupon_list")
    public List<GameItemData> couponList;

    @SerializedName(value = "trialnew_list")
    public List<GameItemData> tryList;

    @SerializedName(value = "now_hot_list")
    public List<GameItemData> newList;

    @SerializedName(value = "best_list")
    public List<GameItemData> rankList;

    public static class BannerData {
        public int                           game_type;
        @SerializedName(value = "pic", alternate = "ad_pic")
        public String                        pic;
        public String                        title;
        public String                        jump_target;
        public String                        page_type;
        public String                        genre_str;
        public String                        gamename;
        public String                        gameicon;
        public GameData                      param;
        public int                           lb_sort;
        @SerializedName(value = "label_name", alternate = "label")
        public String                        label;
        @SerializedName(value = "num", alternate = "has_get")
        public String                        num;
        @SerializedName(value = "head_labels")
        public List<GameItemData.ColorLabel> colorLabels;
        @SerializedName(value = "head_label")
        public GameItemData.ColorLabel       headLabel;
        @SerializedName(value = "begintime_label")
        public GameItemData.Label            labelTime;
        @SerializedName(value = "game_labels_v2")
        public List<GameItemData.Label>      labelGame;
        public String                        servername;

        public static class ColorLabel {
            public String label_name;
            @SerializedName(value = "c1")
            public String textColor;
            @SerializedName(value = "c2")
            public String beginColor;
            @SerializedName(value = "c3")
            public String endColor;
        }

        public static class Label {
            public String label_name;
            @SerializedName(value = "bgcolor")
            public String bgColor;
        }

        public String getTime() {
            if (labelTime != null) {
                return labelTime.label_name;
            }
            if (!TextUtils.isEmpty(servername)) {
                return servername;
            }
            return "";
        }

        public static class GameData {
            public  int          gameid;
            public  int          game_type;
            public  String       newsid;
            public  String       target_url;
            public  String       game_list_id;
            private VideoParamVo video_param;

            public VideoParamVo getVideo_param() {
                return video_param;
            }
        }
    }

    public static class GameItemData {
        public String           tid;
        public int              game_type;
        public String           pic;
        public String           game_summary;
        public String           genre_str;
        public String           gamename;
        public String           gameicon;
        public int              gameid;
        @SerializedName(value = "label_name", alternate = "label")
        public String           label;
        @SerializedName(value = "num", alternate = "has_get")
        public String           num;
        @SerializedName(value = "head_labels_v2")
        public List<ColorLabel> colorLabels;
        @SerializedName(value = "head_labels_v3")
        public List<ColorLabel> moreColorLabels;
        @SerializedName(value = "head_label")
        public ColorLabel       headLabel;
        @SerializedName(value = "begintime_label")
        public Label            labelTime;
        @SerializedName(value = "game_labels_v2")
        public List<Label>      labelGame;
        @SerializedName(value = "game_labels")
        public List<Label>      labelGameOld;
        public String           servername;

        @SerializedName(value = "vip_label")
        public ColorLabel vip;
        @SerializedName(value = "coin_label")
        public ColorLabel coin;

        public static class ColorLabel {
            public String label_name;
            @SerializedName(value = "c1")
            public String textColor;
            @SerializedName(value = "c2")
            public String beginColor;
            @SerializedName(value = "c3")
            public String endColor;
        }

        public static class Label {
            public String label_name;
            @SerializedName(value = "c1")
            public String textColor;
            @SerializedName(value = "c2")
            public String beginColor;
            @SerializedName(value = "c3")
            public String endColor;
            @SerializedName(value = "bgcolor")
            public String bgColor;
        }

        public String getTime() {
            if (labelTime != null) {
                return labelTime.label_name;
            }
            if (!TextUtils.isEmpty(servername)) {
                return servername;
            }
            return "";
        }

        public List<String> getWords() {
            List<String> temp = new ArrayList<>();
            if (labelGame != null && labelGame.size() > 0) {
                for (int i = 0; i < labelGame.size(); i++) {
                    temp.add(labelGame.get(i).label_name);
                }
            }
            return temp;
        }
    }

    public static class BannerSort {
        public BannerData banner1;
        public BannerData banner2;
        public BannerData banner3;
        public BannerData banner4;
    }
}
