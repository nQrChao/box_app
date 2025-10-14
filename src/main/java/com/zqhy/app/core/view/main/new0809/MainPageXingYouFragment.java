package com.zqhy.app.core.view.main.new0809;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.banner.BannerListVo;
import com.zqhy.app.core.data.model.game.bt.MainBTPageGameVo;
import com.zqhy.app.core.data.model.game.new0809.MainCommonDataVo;
import com.zqhy.app.core.data.model.game.new0809.MainXingYouDataVo;
import com.zqhy.app.core.data.model.game.new0809.item.CommonStyle1DataBeanVo;
import com.zqhy.app.core.data.model.game.new0809.item.IconMenuVo;
import com.zqhy.app.core.data.model.game.new0809.item.LunboDataBeanVo;
import com.zqhy.app.core.data.model.game.new0809.item.MainMenuVo;
import com.zqhy.app.core.data.model.game.new0809.item.MainPageItemVo;
import com.zqhy.app.core.data.model.mainpage.figurepush.GameFigurePushVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.main.AbsMainGameListFragment;
import com.zqhy.app.core.view.main.holder.GameFigurePushItemHolder;
import com.zqhy.app.core.view.main.holder.bt.GameBTPageItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.CurrentRecommondItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.GamePicSliderItemView;
import com.zqhy.app.core.view.main.new0809.holder.IconMenuItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.MainBannerItemView;
import com.zqhy.app.core.view.main.new0809.holder.MainMenuItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.MainStyle1ItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.MainStyle2ItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.NewNoDataItemHolder1;
import com.zqhy.app.core.view.main.new0809.holder.TagGameNormalItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.main.BtGameViewModel;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/9 0009-15:17
 * @description
 */
public class MainPageXingYouFragment extends AbsMainGameListFragment<BtGameViewModel> {

    @Override
    protected String getUmengPageName() {
        return "首页-新游";
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(MainMenuVo.class, new MainMenuItemHolder(_mActivity, 5))
                .bind(BannerListVo.class, new MainBannerItemView(_mActivity, 86))
                .bind(MainBTPageGameVo.class, new GameBTPageItemHolder(_mActivity))
                .bind(MainPageItemVo.class, new TagGameNormalItemHolder(_mActivity, false))
                .bind(MainXingYouDataVo.ZuiXingShangJiaDataBeanVo.class, new MainStyle1ItemHolder(_mActivity))
                .bind(GameFigurePushVo.class, new GameFigurePushItemHolder(_mActivity))
                .bind(CommonStyle1DataBeanVo.class, new MainStyle2ItemHolder(_mActivity))
                .bind(MainXingYouDataVo.GamePicSliderDataBeanVo.class, new GamePicSliderItemView(_mActivity))
                .bind(IconMenuVo.class, new IconMenuItemHolder(_mActivity, 3))
                .bind(MainXingYouDataVo.CurrentRecommendDataBeanVo.class, new CurrentRecommondItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(NoMoreDataVo.class, new NewNoDataItemHolder1(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this)
                .setTag(R.id.tag_sub_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        setListViewBackgroundColor(Color.parseColor("#FFFFFF"));
        findViewById(R.id.fl_layout_title).setVisibility(View.GONE);
        setPullRefreshEnabled(false);
        setLoadingMoreEnabled(false);

        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_3478f6,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            initData();
        });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        initData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        initData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    public void initData() {
        if (mViewModel != null) {
            mViewModel.getXyHomePageData(new OnBaseCallback<MainCommonDataVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onSuccess(MainCommonDataVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            clearData();
                            if (data.data != null) {
                                List<String> module = data.data.module;
                                List<String> apiModule = data.data.getApiModule();

                                if (data.data.data != null && !data.data.data.isEmpty()) {
                                    boolean hasModule = false;
                                    for (int i = 0; i < apiModule.size(); i++) {
                                        String key = module.get(i);
                                        String api = apiModule.get(i);

                                        if ("xy_lunbo".equals(api)) {
                                            LunboDataBeanVo xy_lunbo = data.data.getItemData(LunboDataBeanVo.class, key);
                                            if (xy_lunbo != null && checkCollectionNotEmpty(xy_lunbo.data)) {
                                                addData(new BannerListVo(xy_lunbo.data, 86));
                                            }
                                        } else if ("xy_menu".equals(api)) {
                                            MainMenuVo xy_menu = data.data.getItemData(MainMenuVo.class, key);
                                            if (xy_menu != null) {
                                                addData(xy_menu);
                                            }
                                        } else if ("xy_renqi".equals(api)) {
                                            MainXingYouDataVo.RenQiDataBeanVo xy_renqi = data.data.getItemData(MainXingYouDataVo.RenQiDataBeanVo.class, key);
                                            if (xy_renqi != null && checkCollectionNotEmpty(xy_renqi.data)) {
                                                MainBTPageGameVo vo = new MainBTPageGameVo();
                                                vo.setRowSize(2);
                                                vo.setMainTitle(xy_renqi.module_title);
                                                vo.setMainTitleColor(xy_renqi.module_title_color);
                                                vo.setGameInfoVoList(xy_renqi.data);
                                                vo.setAdditional(xy_renqi.additional);
                                                addData(vo);
                                            }
                                        } else if ("xy_zhongbang".equals(api)) {
                                            MainPageItemVo xy_zhongbang = data.data.getItemData(MainPageItemVo.class, key);
                                            if (xy_zhongbang != null && checkCollectionNotEmpty(xy_zhongbang.data)) {
                                                xy_zhongbang.has_tag = false;
                                                addData(xy_zhongbang);
                                            }

                                        } else if ("xy_zuixinshangjia".equals(api)) {
                                            MainXingYouDataVo.ZuiXingShangJiaDataBeanVo xy_zuixinshangjia = data.data.getItemData(MainXingYouDataVo.ZuiXingShangJiaDataBeanVo.class, key);
                                            if (xy_zuixinshangjia != null) {
                                                addData(xy_zuixinshangjia);
                                            }
                                        } else if ("xy_chaping".equals(api)) {
                                            LunboDataBeanVo xy_chaping = data.data.getItemData(LunboDataBeanVo.class, key);
                                            if (xy_chaping != null && checkCollectionNotEmpty(xy_chaping.data)) {
                                                BannerListVo listVo = new BannerListVo(xy_chaping.data);
                                                listVo.itemHeightDp = 156;
                                                //                                                listVo.isLoop = false;
                                                //                                                listVo.INDICATOR_LOCATION = listVo.INDICATOR_LOCATION_OUTSIDE_BOTTOM_CENTER;
                                                addData(listVo);
                                            }

                                        } else if ("xy_haoyoutuijian".equals(api)) {
                                            MainXingYouDataVo.HaoYouTuiJianDataBeanVo xy_haoyoutuijian = data.data.getItemData(MainXingYouDataVo.HaoYouTuiJianDataBeanVo.class, key);
                                            if (xy_haoyoutuijian != null && checkCollectionNotEmpty(xy_haoyoutuijian.data)) {
                                                MainBTPageGameVo vo = new MainBTPageGameVo();
                                                vo.setRowSize(3);
                                                vo.setMainTitle(xy_haoyoutuijian.module_title);
                                                vo.setMainTitleColor(xy_haoyoutuijian.module_title_color);
                                                vo.setGameInfoVoList(xy_haoyoutuijian.data);
                                                addData(vo);
                                            }

                                        } else if ("xy_mianfeiwan".equals(api)) {
                                            CommonStyle1DataBeanVo xy_mianfeiwan = data.data.getItemData(CommonStyle1DataBeanVo.class, key);
                                            if (xy_mianfeiwan != null && checkCollectionNotEmpty(xy_mianfeiwan.data)) {
                                                xy_mianfeiwan.type = CommonStyle1DataBeanVo.STYLE_1;
                                                addData(xy_mianfeiwan);
                                            }

                                        } else if ("xy_gengduohaowan".equals(api)) {
                                            MainPageItemVo xy_gengduohaowan = data.data.getItemData(MainPageItemVo.class, key);
                                            if (xy_gengduohaowan != null && checkCollectionNotEmpty(xy_gengduohaowan.data)) {
                                                xy_gengduohaowan.has_tag = false;
                                                addData(xy_gengduohaowan);
                                            }
                                        } else if ("xy_today_debut".equals(api)) {
                                            MainPageItemVo xy_gengduohaowan = data.data.getItemData(MainPageItemVo.class, key);
                                            if (xy_gengduohaowan != null && checkCollectionNotEmpty(xy_gengduohaowan.data)) {
                                                xy_gengduohaowan.has_tag = false;
                                                addData(xy_gengduohaowan);
                                            }
                                        } else if ("xy_game_pic_slider".equals(api)) {
                                            MainXingYouDataVo.GamePicSliderDataBeanVo gamePicSliderDataBeanVo = data.data.getItemData(MainXingYouDataVo.GamePicSliderDataBeanVo.class, key);
                                            if (gamePicSliderDataBeanVo != null && checkCollectionNotEmpty(gamePicSliderDataBeanVo.data)) {
                                                addData(gamePicSliderDataBeanVo);
                                            }
                                        } else if ("xy_icon_menu".equals(api)) {
                                            IconMenuVo xy_menu = data.data.getItemData(IconMenuVo.class, key);
                                            if (xy_menu != null) {
                                                addData(xy_menu);
                                            }
                                        } else if ("xy_current_recommend".equals(api)) {
                                            MainXingYouDataVo.CurrentRecommendDataBeanVo currentRecommendDataBeanVo = data.data.getItemData(MainXingYouDataVo.CurrentRecommendDataBeanVo.class, key);
                                            if (currentRecommendDataBeanVo != null && checkCollectionNotEmpty(currentRecommendDataBeanVo.data)) {
                                                addData(currentRecommendDataBeanVo);
                                            }
                                        }
                                        hasModule = true;
                                    }
                                    if (hasModule) {
                                        addData(new NoMoreDataVo());
                                    } else {
                                        addData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                                    }
                                } else {
                                    addData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                                }
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
}
