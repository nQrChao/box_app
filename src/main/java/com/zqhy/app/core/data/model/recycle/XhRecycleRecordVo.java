package com.zqhy.app.core.data.model.recycle;

import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 */
public class XhRecycleRecordVo {

    /**
     * id : 1
     * uid : 43
     * xt_uid : 1670775
     * xh_username : x_1552028639gI43
     * xh_showname : 小号1
     * gameid : 1705
     * rmb_total : 8.15
     * hs_gold_total : 0.24
     * add_time : 1554976416
     * edit_time : 0
     * status : 1
     * can_ransom : 1
     * gamename : 王的崛起h5
     * game_type : 3
     * gameicon : http://p1.jiuyao666.com/2019/03/18/5c8f33cf32c67.png
     */

    private String rid;
    private int    uid;
    private int    xt_uid;
    private String xh_username;
    private String xh_showname;
    private int    gameid;
    private String rmb_total;
    private String hs_gold_total;
    private long   add_time;
    private long   create_time;
    private long   edit_time;
    private String status;
    private int    can_ransom;
    private String gamename;
    private int    game_type;
    private String gameicon;
    private String genre_str;
    private int ransom_status;

    private boolean able_cancel = false;

    public int getRansom_status() {
        return ransom_status;
    }

    public void setRansom_status(int ransom_status) {
        this.ransom_status = ransom_status;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public int getXt_uid() {
        return xt_uid;
    }

    public void setXt_uid(int xt_uid) {
        this.xt_uid = xt_uid;
    }

    public String getXh_username() {
        return xh_username;
    }

    public void setXh_username(String xh_username) {
        this.xh_username = xh_username;
    }

    public String getXh_showname() {
        return xh_showname;
    }

    public void setXh_showname(String xh_showname) {
        this.xh_showname = xh_showname;
    }

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public String getRmb_total() {
        return rmb_total;
    }

    public void setRmb_total(String rmb_total) {
        this.rmb_total = rmb_total;
    }

    public String getHs_gold_total() {
        return hs_gold_total;
    }

    public void setHs_gold_total(String hs_gold_total) {
        this.hs_gold_total = hs_gold_total;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public long getEdit_time() {
        return edit_time;
    }

    public void setEdit_time(long edit_time) {
        this.edit_time = edit_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCan_ransom() {
        return can_ransom;
    }

    public void setCan_ransom(int can_ransom) {
        this.can_ransom = can_ransom;
    }

    public String getGamename() {
        return CommonUtils.getGamename(gamename);
    }

    public String getOtherGameName(){
        return CommonUtils.getOtherGameName(gamename);
    }
    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public int getGame_type() {
        return game_type;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }

    public String getGameicon() {
        return gameicon;
    }

    public void setGameicon(String gameicon) {
        this.gameicon = gameicon;
    }

    public String getGenre_str() {
        return genre_str;
    }

    public void setGenre_str(String genre_str) {
        this.genre_str = genre_str;
    }

    public boolean isAble_cancel() {
        return able_cancel;
    }

    public void setAble_cancel(boolean able_cancel) {
        this.able_cancel = able_cancel;
    }
}
