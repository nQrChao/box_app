package com.zqhy.app.core.data.model.game;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/11/23-14:07
 * @description
 */
public class GameMainPageTodayVo {

    private List<GameInfoVo> mGameInfoVoList;

    private String mainTitle;

    public List<GameInfoVo> getGameInfoVoList() {
        return mGameInfoVoList;
    }

    public GameMainPageTodayVo setGameInfoVoList(List<GameInfoVo> gameInfoVoList) {
        mGameInfoVoList = gameInfoVoList;
        return this;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public GameMainPageTodayVo setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
        return this;
    }

    private int genre_id;
    private int game_type;

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public int getGame_type() {
        return game_type;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }

    private boolean isShowMore;

    public boolean isShowMore() {
        return isShowMore;
    }

    public void setShowMore(boolean showMore) {
        isShowMore = showMore;
    }

    public CustomRouteListener mCustomRouteListener;

    public void setCustomRouteListener(CustomRouteListener customRouteListener) {
        mCustomRouteListener = customRouteListener;
    }

    public interface CustomRouteListener {
        void onRoute();
    }


}
