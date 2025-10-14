package com.zqhy.app.core.data.model.rebate;

import java.io.Serializable;

/**
 *
 * @author Administrator
 * @date 2018/11/22
 */

public class RebateInfoVo implements Serializable{



    /**
     * sid : 1
     * uid : 7
     * day_time : 20181122
     * xh_username : x_1542174302yC07
     * gameid : 1
     * gamename : 贪玩硬汉豪华版
     * gameicon : http://p1.btgame01.com/2018/11/01/5bda726c030d2.png
     * game_type : 1
     * usable_total : 300
     * rest_time : 219656
     * min_amount : 100
     * role_id_title : 角色ID12
     * role_id_tip : 有则必填，无则填无
     * xh_showname : 小号1
     */

    private String gameicon;
    private int game_type;
    private int rest_time;
    private int min_amount;

    /**
     * apply_id : 1
     * uid : 7
     * xh_username : x_1542174302yC07
     * xh_showname : 小号1
     * gameid : 1
     * day_time : 2018-11-21
     * default_total : 55.00
     * addtime : 1542857323
     * servername : 一区
     * role_name : 叼炸天
     * role_id : 3360
     * user_beizhu :
     * prop_beizhu :
     * remark :
     * sid : 2
     * usable_total : 55.00
     * status : 1
     * gamename : 贪玩硬汉豪华版
     * role_id_title : 角色ID12
     * role_id_tip : 有则必填，无则填无
     */

    private int apply_id;
    private int uid;
    private String xh_username;
    private String xh_showname;
    private int gameid;
    private String day_time;
    private float default_total;
    private long addtime;
    private String servername;
    private String role_name;
    private String role_id;
    private String user_beizhu;
    private String prop_beizhu;
    private String remark;
    private int sid;
    private float usable_total;
    private int status;
    private String gamename;
    private String role_id_title;
    private String role_id_tip;


    public String getGameicon() {
        return gameicon;
    }

    public int getGame_type() {
        return game_type;
    }

    public int getRest_time() {
        return rest_time;
    }

    public int getMin_amount() {
        return min_amount;
    }

    public int getApply_id() {
        return apply_id;
    }

    public int getUid() {
        return uid;
    }

    public String getXh_username() {
        return xh_username;
    }

    public String getXh_showname() {
        return xh_showname;
    }

    public int getGameid() {
        return gameid;
    }

    public String getDay_time() {
        return day_time;
    }

    public float getDefault_total() {
        return default_total;
    }

    public long getAddtime() {
        return addtime;
    }

    public String getServername() {
        return servername;
    }

    public String getRole_name() {
        return role_name;
    }

    public String getRole_id() {
        return role_id;
    }

    public String getUser_beizhu() {
        return user_beizhu;
    }

    public String getProp_beizhu() {
        return prop_beizhu;
    }

    public String getRemark() {
        return remark;
    }

    public int getSid() {
        return sid;
    }

    public float getUsable_total() {
        return usable_total;
    }

    public int getStatus() {
        return status;
    }

    public String getGamename() {
        return gamename;
    }

    public String getRole_id_title() {
        return role_id_title;
    }

    public String getRole_id_tip() {
        return role_id_tip;
    }
}
