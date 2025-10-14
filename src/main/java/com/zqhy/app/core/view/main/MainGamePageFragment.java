package com.zqhy.app.core.view.main;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.box.other.hjq.toast.Toaster;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.core.data.model.banner.BannerListVo;
import com.zqhy.app.core.data.model.game.GameAppointmentOpVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.game.GameMainPageTodayVo;
import com.zqhy.app.core.data.model.game.GameNavigationVo;
import com.zqhy.app.core.data.model.game.GameSearchVo;
import com.zqhy.app.core.data.model.game.appointment.GameAppointmentVo;
import com.zqhy.app.core.data.model.game.bt.MainBTPageGameVo;
import com.zqhy.app.core.data.model.game.search.GameSearchDataVo;
import com.zqhy.app.core.data.model.mainpage.ChoiceListVo;
import com.zqhy.app.core.data.model.mainpage.GameTagVo;
import com.zqhy.app.core.data.model.mainpage.H5PlayedVo;
import com.zqhy.app.core.data.model.mainpage.HomeBTGameIndexVo;
import com.zqhy.app.core.data.model.mainpage.HomeGameIndexVo;
import com.zqhy.app.core.data.model.mainpage.figurepush.GameFigurePushVo;
import com.zqhy.app.core.data.model.mainpage.navigation.GameNavigationListVo;
import com.zqhy.app.core.data.model.mainpage.tabgame.TabGameInfoVo;
import com.zqhy.app.core.data.model.nodata.MoreGameDataVo;
import com.zqhy.app.core.data.model.tryplay.TryGameItemVo;
import com.zqhy.app.core.data.model.video.GameVideoInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.vm.main.BtGameViewModel;
import com.zqhy.app.newproject.R;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import cn.jzvd.Jzvd;

/**
 * @author Administrator
 * @date 2018/11/7
 */
public class MainGamePageFragment extends MainGameListFragment<BtGameViewModel> {


    public static MainGamePageFragment newInstance(int game_type) {
        MainGamePageFragment fragment = new MainGamePageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("game_type", game_type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected String getUmengPageName() {
        if (game_type == 1) {
            return "首页-BT页";
        } else if (game_type == 2) {
            return "首页-折扣页";
        } else if (game_type == 3) {
            return "首页-H5页";
        }
        return super.getUmengPageName();
    }

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            game_type = getArguments().getInt("game_type");
        }
        super.initView(state);
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        if (game_type == 3) {
            getNetData();
        }
    }

    @Override
    public void onEvent(EventCenter eventCenter) {
        super.onEvent(eventCenter);
        if (eventCenter.getEventCode() == EventConfig.ACTION_ADD_DOWNLOAD_EVENT_CODE) {
            if (gameSearchVo != null) {
                gameSearchVo.setDownloadImageRes(R.mipmap.ic_download_game_marked);
            }
            notifyData();
        } else if (eventCenter.getEventCode() == EventConfig.ACTION_READ_DOWNLOAD_EVENT_CODE) {
            if (gameSearchVo != null) {
                gameSearchVo.setDownloadImageRes(R.mipmap.ic_download_game_unmarked);
            }
            notifyData();
        } else if (eventCenter.getEventCode() == EventConfig.ACTION_GAME_APPOINTMENT_EVENT_CODE) {
            //            getGameAppointmentList(tabGameInfoVo);
        }
    }

    private List<GameNavigationVo> gameNavigationVoList;

    @Override
    protected void dataObserver() {
        super.dataObserver();
    }

    private int gameSelectedPosition;

    GameSearchVo  gameSearchVo;
    TabGameInfoVo tabGameInfoVo;

    private void handleIndexGameData(@Nullable HomeGameIndexVo homeGameIndexVo) {
        if (homeGameIndexVo != null) {
            if (homeGameIndexVo.isStateOK()) {
                HomeGameIndexVo.DataBean dataBean = homeGameIndexVo.getData();
                if (dataBean != null) {
                    if (page == 1) {
                        gameSelectedPosition = 1;
                        clearData();
                        //search game
                        //                        gameSearchVo = new GameSearchVo(game_type);
                        //                        addData(gameSearchVo);

                        if (dataBean.getSlider_list() != null && !dataBean.getSlider_list().isEmpty()) {
                            //banner
                            addData(new BannerListVo(dataBean.getSlider_list()));
                        }

                        if (game_type == 1) {
                            addData(new GameTagVo(new String[]{"充值返利", "官方授权", "绿色无广告"}));
                        } else if (game_type == 2) {
                            addData(new GameTagVo(new String[]{"终身打折", "充值福利", "官方授权"}));
                        } else if (game_type == 3) {
                            addData(new GameTagVo(new String[]{"免下载、免安装", "充值福利", "官方授权"}));
                        }

                        //try game
                        if (dataBean.getTrial_list() != null && dataBean.getTrial_list().size() > 0) {
                            TryGameItemVo tryGameItemVo = new TryGameItemVo();
                            tryGameItemVo.addTryGameList(dataBean.getTrial_list());
                            addData(tryGameItemVo);
                        }

                        //h5 played list
                        if (dataBean.getPlay_list() != null && !dataBean.getPlay_list().isEmpty()) {
                            H5PlayedVo h5PlayedVo = new H5PlayedVo(dataBean.getPlay_list());
                            addData(h5PlayedVo);
                        }

                        //BoutiqueGame
                        //                        if (dataBean.getRecommend_list() != null && !dataBean.getRecommend_list().isEmpty()) {
                        //                            BoutiqueGameListVo boutiqueGameListVo = new BoutiqueGameListVo(dataBean.getRecommend_list());
                        //                            boutiqueGameListVo.setGame_type(game_type);
                        //                            addData(boutiqueGameListVo);
                        //                        }

                        //精选合辑
                        if (dataBean.getChoice_list() != null && !dataBean.getChoice_list().isEmpty()) {
                            List<ChoiceListVo.DataBean> list = dataBean.getChoice_list();
                            ChoiceListVo choiceListVo = new ChoiceListVo();
                            for (ChoiceListVo.DataBean data : list) {
                                data.setGame_type(game_type);
                            }
                            choiceListVo.setData(list);
                            choiceListVo.setGame_type(game_type);

                            addData(choiceListVo);
                        }
                        // ad banner
                        setAppAdBanner();

                        //tabGame
                        //                        tabGameInfoVo = new TabGameInfoVo(dataBean.getHot_list(), dataBean.getNewest_list(), dataBean.getMax_gameid());
                        //                        tabGameInfoVo.setGame_type(game_type);
                        //                        addData(tabGameInfoVo);
                        //                        getGameAppointmentList(tabGameInfoVo);


                        //最近热门 仅限game_type = 1
                        if (dataBean.getHot_list() != null && !dataBean.getHot_list().isEmpty()) {
                            addData(new MainBTPageGameVo()
                                    .setMainTitle("最近热门")
                                    .setGameInfoVoList(dataBean.getHot_list())
                                    .setRowSize(3));
                        }

                        //今日上新
                        if (dataBean.getToday_list() != null && !dataBean.getToday_list().isEmpty()) {
                            GameMainPageTodayVo gameMainPageTodayVo = new GameMainPageTodayVo().setMainTitle("今日上新").setGameInfoVoList(dataBean.getToday_list());
                            gameMainPageTodayVo.setGenre_id(-2);
                            gameMainPageTodayVo.setGame_type(game_type);
                            gameMainPageTodayVo.setShowMore(true);
                            addData(gameMainPageTodayVo);
                        }
                        HomeBTGameIndexVo.TablePlaque tablePlaque = dataBean.getTable_plaque();
                        //position_1
                        if (tablePlaque != null && tablePlaque.getPosition_1() != null) {
                            HomeBTGameIndexVo.TablePlaque.DataBean item = tablePlaque.getPosition_1();
                            addData(createGameFigurePushVo(item, true));
                        }

                    }
                    if (dataBean.getAll_list() != null && !dataBean.getAll_list().isEmpty()) {
                        for (GameInfoVo gameInfoVo : dataBean.getAll_list()) {
                            switch (game_type) {
                                case 1:
                                    gameInfoVo.addEvent(7);
                                    break;
                                case 2:
                                    gameInfoVo.addEvent(26);
                                    break;
                                case 3:
                                    gameInfoVo.addEvent(44);
                                    break;
                                case 4:
                                    gameInfoVo.addEvent(60);
                                    break;
                                default:
                                    break;
                            }
                            gameInfoVo.setEventPosition(gameSelectedPosition);
                            if (gameInfoVo.getTp_type() == 1) {
                                GameFigurePushVo gameFigurePushVo = gameInfoVo.getGameFigurePushVo();
                                switch (game_type) {
                                    case 1:
                                        gameFigurePushVo.addEvent(10);
                                        break;
                                    case 2:
                                        gameFigurePushVo.addEvent(29);
                                        break;
                                    case 3:
                                        gameFigurePushVo.addEvent(47);
                                        break;
                                    case 4:
                                        gameFigurePushVo.addEvent(61);
                                        break;
                                    default:
                                        break;
                                }
                                addData(gameFigurePushVo);
                            } else if (gameInfoVo.getTp_type() == 2) {
                                addData(gameInfoVo.getGameAlbumVo());
                            } else if (gameInfoVo.getTp_type() == 3) {
                                addData(gameInfoVo.getGameAlbumListVo());
                            } else if (gameInfoVo.getTp_type() == 4) {
                                addData(gameInfoVo.getGameVideo());
                            } else {
                                addData(gameInfoVo);
                            }
                            gameSelectedPosition++;
                        }
                    } else {
                        page = -1;
                        if (gameNavigationVoList != null && !gameNavigationVoList.isEmpty()) {
                            GameNavigationListVo navigationListVo = new GameNavigationListVo(gameNavigationVoList);
                            navigationListVo.setGame_type(game_type);
                            addData(navigationListVo);
                        }
                        setListNoMore(true);
                        addData(new MoreGameDataVo(game_type));
                        //                        addData(new NoMoreDataVo());
                        //                        addData(new AntiAddictionVo());
                    }
                    notifyData();
                }
            } else {
                Toaster.show( homeGameIndexVo.getMsg());
            }
        }
    }


    /**
     * 获取新游预约列表
     *
     * @param tabGameInfoVo
     */
    private void getGameAppointmentList(TabGameInfoVo tabGameInfoVo) {
        if (mViewModel != null) {
            mViewModel.getAppointmentGameList(new OnBaseCallback<GameListVo>() {
                @Override
                public void onSuccess(GameListVo data) {
                    if (data != null && data.isStateOK() && data.getData() != null && !data.getData().isEmpty()) {
                        if (tabGameInfoVo != null) {
                            tabGameInfoVo.setGame_appointment_list(data.getData());
                        }
                    } else {
                        if (tabGameInfoVo != null) {
                            tabGameInfoVo.setGame_appointment_list(null);
                        }
                    }
                    notifyData();
                }
            });

        }
    }


    private GameVideoInfoVo getGameVideoItem() {
        try {
            InputStream is = _mActivity.getAssets().open("video.json");
            int length = is.available();
            byte[] buffer = new byte[length];
            is.read(buffer);
            String json = new String(buffer, "utf8");

            Type type = new TypeToken<GameVideoInfoVo>() {
            }.getType();
            Gson gson = new Gson();
            GameVideoInfoVo gameVideoInfoVo = gson.fromJson(json, type);

            return gameVideoInfoVo;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void getNetData() {
        if (mViewModel != null) {
            page = 1;
            getIndexData();
            //            getGameNavigationData();
        }
    }

    @Override
    protected void getMoreNewData() {
        if (mViewModel != null) {
            page++;
            getIndexMoreData();
        }
    }

    private void getIndexData() {
        mViewModel.getIndexGameData(game_type, page, new OnNetWorkListener<HomeGameIndexVo>() {
            @Override
            public void onBefore() {

            }

            @Override
            public void onFailure(String message) {

            }

            @Override
            public void onSuccess(HomeGameIndexVo data) {
                handleIndexGameData(data);
                if (page == 1) {
                    getSearchGame();
                }
            }

            @Override
            public void onAfter() {
                refreshAndLoadMoreComplete();
            }
        });
    }

    private void getIndexMoreData() {
        if (mViewModel != null) {
            mViewModel.getIndexMoreGameData(game_type, page, pageCount, new OnBaseCallback<GameListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(GameListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null && !data.getData().isEmpty()) {
                                addAllData(data.getData());
                            } else {
                                page = -1;
                                if (gameNavigationVoList != null && !gameNavigationVoList.isEmpty()) {
                                    GameNavigationListVo navigationListVo = new GameNavigationListVo(gameNavigationVoList);
                                    navigationListVo.setGame_type(game_type);
                                    addData(navigationListVo);
                                }
                                setListNoMore(true);
                                addData(new MoreGameDataVo(game_type));
                            }
                            notifyData();
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void getGameNavigationData() {
        mViewModel.getGameNavigationData(new OnBaseCallback<GameNavigationListVo>() {

            @Override
            public void onSuccess(GameNavigationListVo data) {
                if (data != null) {
                    gameNavigationVoList = data.getData();
                }
            }
        });
    }

    private void getSearchGame() {
        if (mViewModel != null) {
            mViewModel.getGameSearchData(new OnBaseCallback<GameSearchDataVo>() {
                @Override
                public void onSuccess(GameSearchDataVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                String gameSearch = data.getData().getS_best_title();
                                String gameSearchShow = data.getData().getS_best_title_show();
                                if (gameSearchVo != null) {
                                    gameSearchVo.setGameSearch(gameSearchShow);
                                    notifyData();
                                }
                            }
                        }
                    }
                }
            });
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        Jzvd.releaseAllVideos();
    }


    /**
     * 操作说明, reserve: 预约; cancel: 取消预约
     *
     * @param gameid
     * @param item
     */
    public void gameAppointment(int gameid, GameAppointmentVo item) {
        if (mViewModel != null) {
            mViewModel.gameAppointment(gameid, new OnBaseCallback<GameAppointmentOpVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    loading();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onSuccess(GameAppointmentOpVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                String op = data.getData().getOp();
                                switch (op) {
                                    case "reserve":
                                        String toast = data.getMsg();
                                        showGameAppointmentCalendarReminder(item, toast);
                                        break;
                                    case "cancel":
                                        cancelGameAppointmentCalendarReminder(item);
                                        Toaster.show( data.getMsg());
                                        break;
                                }
                            }
                            EventBus.getDefault().post(new EventCenter(EventConfig.ACTION_GAME_APPOINTMENT_EVENT_CODE));
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }
}
