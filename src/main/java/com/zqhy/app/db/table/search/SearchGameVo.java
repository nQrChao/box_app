package com.zqhy.app.db.table.search;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.zqhy.app.db.AppDatabase;

import java.io.Serializable;

/**
 * @author Administrator
 * @date 2018/11/12
 */
@Table(database = AppDatabase.class)
public class SearchGameVo extends BaseModel implements Serializable{

    @PrimaryKey(autoincrement = true)
    @Column
    public int _id;

    @Column
    public int gameid;
    @Column
    public String gamename;
    @Column
    public int game_type;
    @Column
    public long add_time;


    /**
     * 1 游戏搜索
     * <p>
     * 2 交易商品-游戏搜索
     */
    @Column
    public int search_type;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public String getGamename() {
        return gamename;
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

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public int getSearch_type() {
        return search_type;
    }

    public void setSearch_type(int search_type) {
        this.search_type = search_type;
    }
}
