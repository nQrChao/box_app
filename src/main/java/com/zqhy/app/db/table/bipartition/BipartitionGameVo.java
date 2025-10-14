package com.zqhy.app.db.table.bipartition;

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
public class BipartitionGameVo extends BaseModel implements Serializable{

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
    public String gameicon;
    @Column
    public String genre_str;
    @Column
    public String package_name;
    @Column
    public long add_time;
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

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }
}