package com.zqhy.app.core.view.main.new0809;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.box.common.sdk.ImSDK;
import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.Setting;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.banner.BannerListVo;
import com.zqhy.app.core.data.model.game.bt.MainBTPageGameVo;
import com.zqhy.app.core.data.model.game.new0809.MainCommonDataVo;
import com.zqhy.app.core.data.model.game.new0809.MainJingXuanDataVo;
import com.zqhy.app.core.data.model.game.new0809.item.CommonStyle1DataBeanVo;
import com.zqhy.app.core.data.model.game.new0809.item.LunboDataBeanVo;
import com.zqhy.app.core.data.model.game.new0809.item.LunboGameDataBeanVo;
import com.zqhy.app.core.data.model.game.new0809.item.MainMenuVo;
import com.zqhy.app.core.data.model.game.new0809.item.MainPageItemVo;
import com.zqhy.app.core.data.model.mainpage.figurepush.GameFigurePushVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.main.AbsMainGameListFragment;
import com.zqhy.app.core.view.main.holder.GameFigurePushItemHolder;
import com.zqhy.app.core.view.main.holder.bt.NewJXGameBTPageItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.MainJingXuanHuaDoingItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.MainJxTabGameListItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.MainStyle2ItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.MainTuiJianDanTuiItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.MainTuiJianHuoDongTuTuiItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.MainTuiJingMoreTuiJianItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.NewJXMainBannerItemView;
import com.zqhy.app.core.view.main.new0809.holder.NewJXMainMenuItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.NewJXMainPageJingXuanGameBannerHolder;
import com.zqhy.app.core.view.main.new0809.holder.NewNoDataItemHolder1;
import com.zqhy.app.core.view.main.new0809.holder.TagGameNormalItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.main.BtGameViewModel;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/12 0012-13:41
 * @description
 */
public class MainPageJingXuanFragment extends AbsMainGameListFragment<BtGameViewModel> {
    public static MainPageJingXuanFragment newInstance() {
        return new MainPageJingXuanFragment();
    }
    @Override
    protected String getUmengPageName() {
        return "首页-精选";
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        // 此处无需修改，保持原样即可
        return new BaseRecyclerAdapter.Builder()
                .bind(LunboGameDataBeanVo.class, new NewJXMainPageJingXuanGameBannerHolder(_mActivity))
                .bind(MainMenuVo.class, new NewJXMainMenuItemHolder(_mActivity, 5))
                .bind(BannerListVo.class, new NewJXMainBannerItemView(_mActivity, 86))
                .bind(MainBTPageGameVo.class, new NewJXGameBTPageItemHolder(_mActivity))
                .bind(CommonStyle1DataBeanVo.class, new MainStyle2ItemHolder(_mActivity))
                .bind(MainJingXuanDataVo.HuaDongDataBeanVo.class, new MainJingXuanHuaDoingItemHolder(_mActivity))
                .bind(MainPageItemVo.class, new TagGameNormalItemHolder(_mActivity))
                .bind(MainJingXuanDataVo.DanTuiItemDataBeanVo.class, new MainTuiJianDanTuiItemHolder(_mActivity))
                .bind(MainJingXuanDataVo.HuoDongTuTuiItemDataBeanVo.class, new MainTuiJianHuoDongTuTuiItemHolder(_mActivity))
                .bind(MainJingXuanDataVo.MoreTuiJianDataBeanVo.class, new MainTuiJingMoreTuiJianItemHolder(_mActivity))
                .bind(MainJingXuanDataVo.JXTabGameListDataBeanVo.class, new MainJxTabGameListItemHolder(_mActivity))
                .bind(GameFigurePushVo.class, new GameFigurePushItemHolder(_mActivity))
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
    public void initView(Bundle state) {
        super.initView(state);
        setRootViewColor("#00000000");
        setPullRefreshEnabled(false);
        setLoadingMoreEnabled(false);

        mRecyclerView.setItemViewCacheSize(10);

        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_3478f6,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            initData();

        });

        if(BuildConfig.APP_TEMPLATE == 9999 || BuildConfig.APP_TEMPLATE == 9998){
            ImSDK.eventViewModelInstance.getScrollToTabGameList().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean shouldScroll) {
                    if (shouldScroll != null && shouldScroll) {
                        scrollToTabGameList();
                        ImSDK.eventViewModelInstance.getScrollToTabGameList().postValue(false);
                    }
                }
            });
        }

        initData();
    }

    public void scrollToMoreTuiJian() {
        scrollToViewType(MainJingXuanDataVo.MoreTuiJianDataBeanVo.class, "更多推荐");
    }

    public void scrollToTabGameList() {
        scrollToViewType(MainJingXuanDataVo.JXTabGameListDataBeanVo.class, "Tab游戏列表");
    }

    private void scrollToViewType(Class<?> targetType, String moduleName) {
        if (mDelegateAdapter == null || mRecyclerView == null || mRecyclerView.getLayoutManager() == null) {
            Toaster.show("列表尚未初始化");
            return;
        }

        List<?> dataList = mDelegateAdapter.getItems();
        if (dataList == null || dataList.isEmpty()) {
            Toaster.show("列表为空，无法定位");
            return;
        }

        int targetPosition = -1;
        for (int i = 0; i < dataList.size(); i++) {
            Object dataItem = dataList.get(i);
            if (dataItem != null && targetType.isInstance(dataItem)) {
                targetPosition = i;
                break;
            }
        }

        if (targetPosition != -1) {
            final int finalAdapterPosition = targetPosition;

            mRecyclerView.post(() -> {
                // 创建一个自定义的 LinearSmoothScroller
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(mRecyclerView.getContext()) {
                    @Override
                    protected int getVerticalSnapPreference() {
                        // 返回 SNAP_TO_START 会让目标视图的顶部和 RecyclerView 的顶部对齐
                        return LinearSmoothScroller.SNAP_TO_START;
                    }
                };

                // 因为 XRecyclerView 会自动添加一个刷新头（Header），所以所有 adapter 的位置在 RecyclerView 中都需要 +1。
                int finalLayoutPosition = finalAdapterPosition + 1;

                // 设置要滚动的目标位置
                smoothScroller.setTargetPosition(finalLayoutPosition);

                // 启动平滑滚动
                mRecyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
            });

        } else {
            Toaster.show("当前页面没有“" + moduleName + "”模块");
        }
    }


    @Override
    protected void lazyLoad() {
        super.lazyLoad();

    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    private void initData() {
        if (mViewModel != null) {
            mViewModel.refreshUserData(new OnBaseCallback<UserInfoVo>() {
                @Override
                public void onSuccess(UserInfoVo data) {
                    ImSDK.eventViewModelInstance.getFuliBi().postValue(data.getData().getPtb_dc()+"");
                }
            });

            mViewModel.getJxHomePageData(new OnBaseCallback<MainCommonDataVo>() {
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
                                        hasModule = true;
                                        String key = module.get(i);
                                        String api = apiModule.get(i);

                                        if ("jx_gamelunbo".equals(api)) {
                                            LunboGameDataBeanVo jx_gamelunbo = data.data.getItemData(LunboGameDataBeanVo.class, key);
                                            if (jx_gamelunbo != null && checkCollectionNotEmpty(jx_gamelunbo.data)) {
                                                Collections.shuffle(jx_gamelunbo.data);
                                                addData(jx_gamelunbo);
                                            }
                                        } else if ("jx_menu".equals(api)) {
                                            MainMenuVo jx_menu = data.data.getItemData(MainMenuVo.class, key);
                                            if (jx_menu != null && checkCollectionNotEmpty(jx_menu.data)) {
                                                if (jx_menu.data != null) {
                                                    Iterator<MainMenuVo.DataBean> iterator = jx_menu.data.iterator();
                                                    while (iterator.hasNext()) {
                                                        MainMenuVo.DataBean next = iterator.next();
                                                        if (Setting.CLOUD_STATUS != 1 && "cloud".equals(next.getPage_type())) {
                                                            iterator.remove();
                                                        }
                                                    }
                                                }
                                                addData(jx_menu);
                                            }
                                        } else if ("jx_lunbo".equals(api)) {
                                            LunboDataBeanVo jx_lunbo = data.data.getItemData(LunboDataBeanVo.class, key);
                                            if (jx_lunbo != null && checkCollectionNotEmpty(jx_lunbo.data)) {
                                                addData(new BannerListVo(jx_lunbo.data, 86));
                                            }
                                        } else if ("jx_topjingxuan".equals(api)) {
                                            CommonStyle1DataBeanVo jx_topjingxuan = data.data.getItemData(CommonStyle1DataBeanVo.class, key);
                                            if (jx_topjingxuan != null && checkCollectionNotEmpty(jx_topjingxuan.data)) {
                                                jx_topjingxuan.type = CommonStyle1DataBeanVo.STYLE_2;
                                                addData(jx_topjingxuan);
                                            }
                                        } else if ("jx_huadong".equals(api)) {
                                            MainJingXuanDataVo.HuaDongDataBeanVo jx_huadong = data.data.getItemData(MainJingXuanDataVo.HuaDongDataBeanVo.class, key);
                                            if (jx_huadong != null && checkCollectionNotEmpty(jx_huadong.data)) {
                                                addData(jx_huadong);
                                            }
                                        } else if ("jx_haoyoutuijian".equals(api)) {
                                            MainJingXuanDataVo.HaoYouTuiJianDataBeanVo jx_haoyoutuijian = data.data.getItemData(MainJingXuanDataVo.HaoYouTuiJianDataBeanVo.class, key);
                                            if (jx_haoyoutuijian != null && checkCollectionNotEmpty(jx_haoyoutuijian.data)) {
                                                MainBTPageGameVo vo = new MainBTPageGameVo();
                                                vo.setRowSize(3);
                                                vo.setMainTitle(jx_haoyoutuijian.module_title);
                                                vo.setMainTitleColor(jx_haoyoutuijian.module_title_color);
                                                Collections.shuffle(jx_haoyoutuijian.data);
                                                vo.setGameInfoVoList(jx_haoyoutuijian.data);
                                                vo.jx_haoyoutuijian = jx_haoyoutuijian;
                                                addData(vo);
                                            }
                                        } else if ("jx_chaping".equals(api)) {
                                            LunboDataBeanVo jx_chaping = data.data.getItemData(LunboDataBeanVo.class, key);
                                            if (jx_chaping != null && checkCollectionNotEmpty(jx_chaping.data)) {
                                                BannerListVo bannerListVo = new BannerListVo(jx_chaping.data);
                                                bannerListVo.itemHeightDp = 156;
                                                addData(bannerListVo);
                                            }
                                        } else if ("jx_zidingyi".equals(api)) {
                                            MainPageItemVo jx_zidingyi = data.data.getItemData(MainPageItemVo.class, key);
                                            if (jx_zidingyi != null && checkCollectionNotEmpty(jx_zidingyi.data)) {
                                                jx_zidingyi.has_tag = false;
                                                addData(jx_zidingyi);
                                            }
                                        } else if ("jx_dantui".equals(api)) {
                                            MainJingXuanDataVo.DanTuiDataBeanVo jx_dantui = data.data.getItemData(MainJingXuanDataVo.DanTuiDataBeanVo.class, key);
                                            if (jx_dantui != null && checkCollectionNotEmpty(jx_dantui.data)) {
                                                MainJingXuanDataVo.DanTuiItemDataBeanVo beanVo = new MainJingXuanDataVo.DanTuiItemDataBeanVo();
                                                beanVo.mGameInfoVo = jx_dantui.data.get(0);
                                                beanVo.mGameLisVo = jx_dantui.data;

                                                beanVo.module_alias = jx_dantui.module_alias;
                                                beanVo.id = jx_dantui.id;
                                                beanVo.module_additional_id = jx_dantui.module_additional_id;
                                                beanVo.module_title = jx_dantui.module_title;
                                                beanVo.module_title_color = jx_dantui.module_title_color;
                                                beanVo.module_title_two = jx_dantui.module_title_two;
                                                beanVo.module_title_two_color = jx_dantui.module_title_two_color;
                                                beanVo.module_sub_title = jx_dantui.module_sub_title;
                                                beanVo.module_sub_title_color = jx_dantui.module_sub_title_color;
                                                beanVo.additional = jx_dantui.additional;

                                                addData(beanVo);
                                            }
                                        } else if ("jx_huodong".equals(api)) {
                                            MainJingXuanDataVo.HuoDongTuTuiDataBeanVo jx_huodong = data.data.getItemData(MainJingXuanDataVo.HuoDongTuTuiDataBeanVo.class, key);
                                            if (jx_huodong != null && checkCollectionNotEmpty(jx_huodong.data)) {
                                                MainJingXuanDataVo.HuoDongTuTuiItemDataBeanVo beanVo = new MainJingXuanDataVo.HuoDongTuTuiItemDataBeanVo();
                                                beanVo.data = jx_huodong.data;

                                                beanVo.module_alias = jx_huodong.module_alias;
                                                beanVo.id = jx_huodong.id;
                                                beanVo.module_additional_id = jx_huodong.module_additional_id;
                                                beanVo.module_title = jx_huodong.module_title;
                                                beanVo.module_title_color = jx_huodong.module_title_color;
                                                beanVo.module_title_two = jx_huodong.module_title_two;
                                                beanVo.module_title_two_color = jx_huodong.module_title_two_color;
                                                beanVo.module_sub_title = jx_huodong.module_sub_title;
                                                beanVo.module_sub_title_color = jx_huodong.module_sub_title_color;
                                                beanVo.additional = jx_huodong.additional;

                                                addData(beanVo);
                                            }
                                        } else if ("jx_moretuijian".equals(api)) {
                                            MainJingXuanDataVo.MoreTuiJianDataBeanVo jx_moretuijian = data.data.getItemData(MainJingXuanDataVo.MoreTuiJianDataBeanVo.class, key);
                                            if (jx_moretuijian != null && checkCollectionNotEmpty(jx_moretuijian.data)) {
                                                addData(jx_moretuijian);
                                            }
                                        } else if ("jx_tab_gamelist".equals(api)) {
                                            MainJingXuanDataVo.JXTabGameListDataBeanVo jx_tab_gamelist = data.data.getItemData(MainJingXuanDataVo.JXTabGameListDataBeanVo.class, key);
                                            if (jx_tab_gamelist != null && checkCollectionNotEmpty(jx_tab_gamelist.data)) {
                                                addData(jx_tab_gamelist);
                                            }
                                        } else {
                                            hasModule = false;
                                        }
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
