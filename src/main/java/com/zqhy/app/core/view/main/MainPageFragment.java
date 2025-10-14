package com.zqhy.app.core.view.main;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.banner.BannerListVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.game.GameMainPageTodayVo;
import com.zqhy.app.core.data.model.game.GameSearchVo;
import com.zqhy.app.core.data.model.game.bt.MainBTBannerListVo;
import com.zqhy.app.core.data.model.game.bt.MainBTPageForecastVo;
import com.zqhy.app.core.data.model.game.bt.MainBTPageGameVo;
import com.zqhy.app.core.data.model.game.bt.MainBTPageMenuVo;
import com.zqhy.app.core.data.model.game.bt.MainBTPageTabVo;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;
import com.zqhy.app.core.data.model.mainpage.ChoiceListVo;
import com.zqhy.app.core.data.model.mainpage.HomeBTGameIndexVo;
import com.zqhy.app.core.data.model.mainpage.MainPageMoreLikeGameVo;
import com.zqhy.app.core.data.model.mainpage.MainPageTryGameVo;
import com.zqhy.app.core.data.model.mainpage.figurepush.GameFigurePushVo;
import com.zqhy.app.core.data.model.mainpage.navigation.GameNavigationListVo;
import com.zqhy.app.core.data.model.nodata.MoreGameDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.main.holder.AdBannerItemHolder;
import com.zqhy.app.core.view.main.holder.GameChoiceItemHolder;
import com.zqhy.app.core.view.main.holder.GameFigurePushItemHolder;
import com.zqhy.app.core.view.main.holder.GameMainPageTodayItemHolder;
import com.zqhy.app.core.view.main.holder.GameSearchItemHolder;
import com.zqhy.app.core.view.main.holder.MainPagerMoreGameItemHolder;
import com.zqhy.app.core.view.main.holder.bt.GameBTBannerItemHolder;
import com.zqhy.app.core.view.main.holder.bt.GameBTForecastItemHolder;
import com.zqhy.app.core.view.main.holder.bt.GameBTGameGenreItemHolder;
import com.zqhy.app.core.view.main.holder.bt.GameBTGameHotGenreItemHolder;
import com.zqhy.app.core.view.main.holder.bt.GameBTMenuItemHolder;
import com.zqhy.app.core.view.main.holder.bt.GameBTPageItemHolder;
import com.zqhy.app.core.view.main.holder.bt.GameBTPageTabItemHolder;
import com.zqhy.app.core.view.main.holder.bt.GameBTRookieCouponItemHolder;
import com.zqhy.app.core.view.main.holder.bt.GameBTSimpleBannerItemHolder;
import com.zqhy.app.core.view.main.holder.bt.MainPageTryGameItemHolder;
import com.zqhy.app.core.view.main.holder.bt.MainPagerMoreLikeItemHolder;
import com.zqhy.app.core.view.main.new_game.NewGameMainFragment;
import com.zqhy.app.core.view.user.welfare.MyCouponsListFragment;
import com.zqhy.app.core.vm.main.BtGameViewModel;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leeham2734
 * @date 2020/8/18-11:41
 * @description
 */
public class MainPageFragment extends AbsMainGameListFragment<BtGameViewModel> {

    public MainPageFragment() {
        game_type = 1;
    }

    @Override
    protected String getUmengPageName() {
        return "首页-精选页";
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(GameSearchVo.class, new GameSearchItemHolder(_mActivity))
                .bind(BannerListVo.class, new GameBTBannerItemHolder(_mActivity))
                //                                .bind(BannerListVo.class, new GameBannerItemView(_mActivity))
                //菜单
                .bind(MainBTPageMenuVo.class, new GameBTMenuItemHolder(_mActivity))
                //新手见面礼
                .bind(HomeBTGameIndexVo.RookiesCouponVo.class, new GameBTRookieCouponItemHolder(_mActivity))
                //2019.11.26 精选合辑
                .bind(ChoiceListVo.class, new GameChoiceItemHolder(_mActivity))
                //tab
                //                .bind(TabGameInfoVo.class, new TabGameItemHolder(_mActivity))
                //new TAB
                .bind(MainBTPageTabVo.class, new GameBTPageTabItemHolder(_mActivity))
                //新游预告
                .bind(MainBTPageForecastVo.class, new GameBTForecastItemHolder(_mActivity))
                //首页游戏样式模板
                .bind(MainBTPageGameVo.class, new GameBTPageItemHolder(_mActivity))
                //position_type_2
                .bind(MainBTBannerListVo.class, new GameBTSimpleBannerItemHolder(_mActivity))
                //game genre
                .bind(HomeBTGameIndexVo.GenreGameVo.class, new GameBTGameGenreItemHolder(_mActivity))
                //热门分类
                .bind(GameNavigationListVo.class, new GameBTGameHotGenreItemHolder(_mActivity))
                //AD Banner
                .bind(AppJumpInfoBean.class, new AdBannerItemHolder(_mActivity))
                //position_type_1
                .bind(GameFigurePushVo.class, new GameFigurePushItemHolder(_mActivity))
                .bind(MoreGameDataVo.class, new MainPagerMoreGameItemHolder(_mActivity))
                //更多你喜欢的游戏
                .bind(MainPageMoreLikeGameVo.class, new MainPagerMoreLikeItemHolder(_mActivity))
                //试玩有奖
                .bind(MainPageTryGameVo.class, new MainPageTryGameItemHolder(_mActivity))
                //今日上新（新样式）
                .bind(GameMainPageTodayVo.class, new GameMainPageTodayItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this)
                .setTag(R.id.tag_sub_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        //        initData();
        setLoadingMoreEnabled(false);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        initData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        initData();
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        initData();
    }


    private List<HomeBTGameIndexVo.GenreGameVo> mGenreGameVoList;

    private void initData() {
        if (mViewModel != null) {
            mViewModel.getIndexBTGameData(new OnBaseCallback<HomeBTGameIndexVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(HomeBTGameIndexVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            clearData();
                            //search game
                            //                            GameSearchVo gameSearchVo = new GameSearchVo(game_type);
                            //                            addData(gameSearchVo);

                            //首页轮播图
                            if (data.getData().getSlider_list() != null && !data.getData().getSlider_list().isEmpty()) {
                                addData(new BannerListVo(data.getData().getSlider_list()));
                            }
                            //menu
                            addData(new MainBTPageMenuVo());
                            //新手礼包
                            //                            if (data.getData().getRookies_coupon_data() != null) {
                            //                                HomeBTGameIndexVo.RookiesCouponVo rookies_coupon_data = data.getData().getRookies_coupon_data();
                            //
                            //                                rookies_coupon_data.setCard648_url(data.getData().getCard648_url());
                            //                                rookies_coupon_data.setCard648list(data.getData().getCard648list());
                            //                                rookies_coupon_data.setGet_now_url(data.getData().getGet_now_url());
                            //                                rookies_coupon_data.setRookies_coupon_url(data.getData().getRookies_coupon_url());
                            //
                            //                                addData(rookies_coupon_data);
                            //                            }
                            //精选合辑
                            if (data.getData().getChoice_list() != null && !data.getData().getChoice_list().isEmpty()) {
                                List<ChoiceListVo.DataBean> list = data.getData().getChoice_list();
                                ChoiceListVo choiceListVo = new ChoiceListVo();
                                for (ChoiceListVo.DataBean item : list) {
                                    item.setGame_type(game_type);
                                }
                                choiceListVo.setData(list);
                                choiceListVo.setGame_type(game_type);

                                addData(choiceListVo);
                            }
                            HomeBTGameIndexVo.TablePlaque tablePlaque = data.getData().getTable_plaque();


                            // ad banner
                            setAppAdBanner();

                            //tabGame
                            //                            MainBTPageTabVo mainBTPageTabVo = new MainBTPageTabVo(data.getData().getHot_list(), data.getData().getNewest_list());
                            //                            mainBTPageTabVo.setGame_type(game_type);
                            //                            addData(mainBTPageTabVo);

                            //本周热门
                            if (data.getData().getHot_list() != null && !data.getData().getHot_list().isEmpty()) {
                                addData(new MainBTPageGameVo()
                                        .setMainTitle("本周热门")
                                        .setGameInfoVoList(data.getData().getHot_list())
                                        .setRowSize(3));
                            }

                            //position_1
                            if (tablePlaque != null && tablePlaque.getPosition_1() != null) {
                                HomeBTGameIndexVo.TablePlaque.DataBean item = tablePlaque.getPosition_1();
                                addData(createGameFigurePushVo(item));
                            }

                            //新游推荐
                            if (data.getData().getNewest_list() != null && !data.getData().getNewest_list().isEmpty()) {
                                GameMainPageTodayVo gameMainPageTodayVo = new GameMainPageTodayVo().setMainTitle("新游推荐").setGameInfoVoList(data.getData().getNewest_list());
                                gameMainPageTodayVo.setShowMore(true);
                                gameMainPageTodayVo.setCustomRouteListener(() -> {
                                    startFragment(new NewGameMainFragment());
                                });
                                addData(gameMainPageTodayVo);
                            }
                            //精品必玩
                            if (data.getData().getQuality_list() != null && !data.getData().getQuality_list().isEmpty()) {
                                addData(new MainBTPageGameVo()
                                        .setMainTitle("精品必玩")
                                        .setGameInfoVoList(data.getData().getQuality_list())
                                        .setRowSize(3));
                            }
                            //试玩有奖
                            if (data.getData().getTrial_list() != null && !data.getData().getTrial_list().isEmpty()) {
                                addData(new MainPageTryGameVo("试玩有奖", data.getData().getTrial_list()));
                            }

                            //新游预告/即将上线
                            if (data.getData().getReserve_list() != null && !data.getData().getReserve_list().isEmpty()) {
                                addData(new MainBTPageForecastVo(data.getData().getReserve_list()));
                            }

                            //position_2
                            if (tablePlaque != null && tablePlaque.getPosition_2() != null) {
                                HomeBTGameIndexVo.TablePlaque.DataBean item = tablePlaque.getPosition_2();
                                addData(createGameFigurePushVo(item));
                            }


                            //recommend_list 人气推荐
                            //                            if (data.getData().getRecommend_list() != null && !data.getData().getRecommend_list().isEmpty()) {
                            //                                addData(new MainBTPageGameVo().setMainTitle("人气推荐").setGameInfoVoList(data.getData().getRecommend_list()).setRowSize(3));
                            //                            }

                            //更多游戏
                            if (data.getData().getMore_like() != null && !data.getData().getMore_like().isEmpty()) {
                                addData(new MainPageMoreLikeGameVo("品质优选", data.getData().getMore_like()));
                            }

                            //position_3
                            if (tablePlaque != null && tablePlaque.getPosition_3() != null) {
                                HomeBTGameIndexVo.TablePlaque.DataBean item = tablePlaque.getPosition_3();
                                addData(createGameFigurePushVo(item));
                            }

                            //recommend_list2 小编推荐
                            //                            if (data.getData().getRecommend_list2() != null && !data.getData().getRecommend_list2().isEmpty()) {
                            //                                addData(new MainBTPageGameVo().setMainTitle("小编推荐").setGameInfoVoList(data.getData().getRecommend_list2()).setRowSize(3));
                            //                            }
                            //position_4
                            //                            if (tablePlaque != null && tablePlaque.getPosition_4() != null) {
                            //                                HomeBTGameIndexVo.TablePlaque.DataBean item = tablePlaque.getPosition_4();
                            //                                addData(new MainBTBannerListVo(item));
                            //                            }

                            if (mGenreGameVoList == null) {
                                mGenreGameVoList = new ArrayList<>();
                            }
                            mGenreGameVoList.clear();
                            //热门分类
                            if (data.getData().getGenre_game_data() != null && !data.getData().getGenre_game_data().isEmpty()) {
                                for (int i = 0; i < data.getData().getGenre_game_data().size(); i++) {
                                    HomeBTGameIndexVo.GenreGameVo genreGameVo = data.getData().getGenre_game_data().get(i);
                                    genreGameVo.setGame_type(game_type);
                                    mGenreGameVoList.add(genreGameVo);
                                    GameMainPageTodayVo gameMainPageTodayVo = new GameMainPageTodayVo().setMainTitle(genreGameVo.getGenre_name()).setGameInfoVoList(genreGameVo.getList());
                                    gameMainPageTodayVo.setGenre_id(genreGameVo.getGenre_id());
                                    gameMainPageTodayVo.setGame_type(genreGameVo.getGame_type());
                                    gameMainPageTodayVo.setShowMore(true);
                                    addData(gameMainPageTodayVo);

                                    //                                    addData(genreGameVo);
                                    //                                    if (i == 0) {
                                    //                                        //position_5
                                    //                                        if (tablePlaque != null && tablePlaque.getPosition_5() != null) {
                                    //                                            HomeBTGameIndexVo.TablePlaque.DataBean item = tablePlaque.getPosition_5();
                                    //                                            addData(new MainBTBannerListVo(item));
                                    //                                        }
                                    //                                    } else if (i == 1) {
                                    //                                        //position_6
                                    //                                        if (tablePlaque != null && tablePlaque.getPosition_6() != null) {
                                    //                                            HomeBTGameIndexVo.TablePlaque.DataBean item = tablePlaque.getPosition_6();
                                    //                                            addData(new MainBTBannerListVo(item));
                                    //                                        }
                                    //                                    } else if (i == 2) {
                                    //                                        //position_7
                                    //                                        if (tablePlaque != null && tablePlaque.getPosition_7() != null) {
                                    //                                            HomeBTGameIndexVo.TablePlaque.DataBean item = tablePlaque.getPosition_7();
                                    //                                            addData(new MainBTBannerListVo(item));
                                    //                                        }
                                    //                                    }
                                }
                            }
                            //热门分类,8个条目
                            if (data.getData().getHot_genre_list() != null && !data.getData().getHot_genre_list().isEmpty()) {
                                GameNavigationListVo navigationListVo = new GameNavigationListVo(data.getData().getHot_genre_list());
                                navigationListVo.setGame_type(game_type);
                                addData(navigationListVo);
                            }
                            //更多游戏
                            addData(new MoreGameDataVo(game_type));

                            notifyData();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                }
            });
        }
    }


    public void getGenreGameByPage(int page, int genre_id) {
        if (mViewModel != null) {
            mViewModel.getGenreGameByPage(page, genre_id, game_type, new OnBaseCallback<GameListVo>() {
                @Override
                public void onSuccess(GameListVo data) {
                    if (data != null) {
                        if (data.getData() != null && !data.getData().isEmpty()) {
                            if (mGenreGameVoList != null && !mGenreGameVoList.isEmpty()) {
                                for (HomeBTGameIndexVo.GenreGameVo genreGameVo : mGenreGameVoList) {
                                    if (genreGameVo.getGenre_id() == genre_id) {
                                        genreGameVo.setList(data.getData());
                                        notifyData();
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        Toaster.show( data.getMsg());
                    }
                }
            });
        }
    }


    public void getRookieVouchers(List<HomeBTGameIndexVo.RookiesCouponVo.DataBean> list) {
        if (mViewModel != null) {
            mViewModel.getRookieCoupons(new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            showVoucherDialog(list);
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }


    public void showVoucherDialog(List<HomeBTGameIndexVo.RookiesCouponVo.DataBean> list) {
        CustomDialog voucherDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_main_page_vouchers, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        TextView mTvTitle = voucherDialog.findViewById(R.id.tv_title);
        TextView mTvDetail = voucherDialog.findViewById(R.id.tv_detail);
        LinearLayout mLlVoucherContainer = voucherDialog.findViewById(R.id.ll_voucher_container);
        voucherDialog.setCanceledOnTouchOutside(false);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(48 * ScreenUtil.getScreenDensity(_mActivity));
        gd.setColors(new int[]{Color.parseColor("#FEC81E"), Color.parseColor("#FBA50E")});
        mTvDetail.setBackground(gd);

        int itemCount = list.size();
        mTvTitle.setText(_mActivity.getResources().getString(R.string.string_dialog_voucher_tips, String.valueOf(itemCount)));

        mLlVoucherContainer.removeAllViews();
        for (int i = 0; i < itemCount; i = i + 2) {
            View layoutItemView = createDoubleItemVoucherItem(list.get(i), list.get(i + 1));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = (int) (6 * ScreenUtil.getScreenDensity(_mActivity));
            layoutItemView.setLayoutParams(params);

            mLlVoucherContainer.addView(layoutItemView);
        }

        mTvDetail.setOnClickListener(view -> {
            if (checkLogin()) {
                //startFragment(GameWelfareFragment.newInstance(2));
                startFragment(new MyCouponsListFragment());
                voucherDialog.dismiss();
            }
        });
        voucherDialog.show();
    }

    private View createDoubleItemVoucherItem(HomeBTGameIndexVo.RookiesCouponVo.DataBean item1, HomeBTGameIndexVo.RookiesCouponVo.DataBean item2) {
        LinearLayout layout = new LinearLayout(_mActivity);
        layout.setOrientation(LinearLayout.VERTICAL);
        View itemView = createItemVoucherItem(item1);
        layout.addView(itemView);
        View itemView2 = createItemVoucherItem(item2);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = (int) (15 * ScreenUtil.getScreenDensity(_mActivity));
        layout.addView(itemView2, params);

        return layout;
    }

    private View createItemVoucherItem(HomeBTGameIndexVo.RookiesCouponVo.DataBean item) {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_main_page_item_vouchers, null);

        TextView mTvVoucherAmount = itemView.findViewById(R.id.tv_voucher_amount);
        TextView mTvVoucherCondition = itemView.findViewById(R.id.tv_voucher_condition);
        TextView mTvVoucherType = itemView.findViewById(R.id.tv_voucher_type);

        mTvVoucherAmount.setText(String.valueOf(item.getAmount()));
        mTvVoucherCondition.setText(item.getRange());
        mTvVoucherType.setText(item.getCoupon_name());

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(30 * ScreenUtil.getScreenDensity(_mActivity));
        gd.setColor(Color.parseColor("#FF4B59"));
        mTvVoucherCondition.setBackground(gd);

        return itemView;
    }
}
