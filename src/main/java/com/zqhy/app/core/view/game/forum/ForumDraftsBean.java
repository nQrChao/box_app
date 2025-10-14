package com.zqhy.app.core.view.game.forum;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.crud.LitePalSupport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ForumDraftsBean extends LitePalSupport {

    public long getBaseId() {
        return getBaseObjId();
    }
    int type;//1:长评论 2:短评
    String gameId;
    String hobbiesJson;

    String gameIcon;
    String gameName;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameIcon() {
        return gameIcon;
    }

    public void setGameIcon(String gameIcon) {
        this.gameIcon = gameIcon;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    int uid;
    String title;
    String tips;
    long time;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getHobbies() {
        try{
            Gson gson = new Gson();
            Type type = new TypeToken<List<String>>() {}.getType();
            return gson.fromJson(hobbiesJson, type)==null?new ArrayList<>():gson.fromJson(hobbiesJson, type);
        }catch (Exception e){

        }
        return new ArrayList<>();
    }
    public void setHobbies(List<String> hobbies) {
        Gson gson = new Gson();
        this.hobbiesJson = gson.toJson(hobbies);
    }
}
