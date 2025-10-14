package com.zqhy.app.core.data.model.transaction;

import com.zqhy.app.core.data.model.BaseVo;

import java.util.ArrayList;

/**
 * @author Administrator
 */
public class TradeSearchPageInfoVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        ArrayList< Collection> collection_list;
        ArrayList<Genre> genre_list;

        public ArrayList< Collection> getCollection_list() {
            return collection_list;
        }

        public void setCollection_list(ArrayList< Collection> collection_list) {
            this.collection_list = collection_list;
        }

        public ArrayList<Genre> getGenre_list() {
            return genre_list;
        }

        public void setGenre_list(ArrayList<Genre> genre_list) {
            this.genre_list = genre_list;
        }

        public static class  Collection {

            /**
             *  "gameid": 123, // 对应trade_goods_list gameid参数
             *         "gamename": "王者的荣耀"
             */
            String gameid;
            String gamename;

            public String getGameid() {
                return gameid;
            }

            public void setGameid(String gameid) {
                this.gameid = gameid;
            }

            public String getGamename() {
                return gamename;
            }

            public void setGamename(String gamename) {
                this.gamename = gamename;
            }
        }
        public static class Genre {

            /**
             *   "id": "59", // 对应trade_goods_list genre_id参数
             *   "name": "三国"
             */
            String id;
            String name;

            public String getGenre_id() {
                return id;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGenre_name() {
                return name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

    }

}
