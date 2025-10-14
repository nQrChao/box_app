package com.zqhy.app.core.view.main.new0809;

import android.graphics.Color;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.banner.BannerListVo;
import com.zqhy.app.core.data.model.game.new0809.MainCommonDataVo;
import com.zqhy.app.core.data.model.game.new0809.MainXingYouDataVo;
import com.zqhy.app.core.data.model.game.new0809.item.LunboDataBeanVo;
import com.zqhy.app.core.data.model.game.new0809.item.MainMenuVo;
import com.zqhy.app.core.data.model.game.new0809.item.MainPageItemVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.main.AbsMainGameListFragment;
import com.zqhy.app.core.view.main.new0809.holder.MainStyleZkItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.MainZkMenuItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.NewNoDataItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.ZkTagGameNormalItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.main.BtGameViewModel;
import com.zqhy.app.newproject.R;

import java.util.Collections;
import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/11 0011-16:11
 * @description
 */
public class MainPageZKFragment extends AbsMainGameListFragment<BtGameViewModel> {

    @Override
    protected String getUmengPageName() {
        return "首页-折扣";
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(MainMenuVo.class, new MainZkMenuItemHolder(_mActivity, 4))
                .bind(BannerListVo.class, new MainZkBannerItemView(_mActivity, 183))
                .bind(MainPageItemVo.class, new ZkTagGameNormalItemHolder(_mActivity))
                .bind(MainXingYouDataVo.ZuiXingShangJiaDataBeanVo.class, new MainStyleZkItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(NoMoreDataVo.class, new NewNoDataItemHolder(_mActivity))
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
        setListViewBackgroundColor(Color.parseColor("#F8F8F8"));
        setPullRefreshEnabled(true);
        setLoadingMoreEnabled(false);
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

    private void initData() {
        if (mViewModel != null) {
            mViewModel.getZkHomePageData(new OnBaseCallback<MainCommonDataVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
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

                                        if ("cw_lunbo".equals(api)) {
                                            LunboDataBeanVo xy_lunbo = data.data.getItemData(LunboDataBeanVo.class, key);
                                            if (xy_lunbo != null && checkCollectionNotEmpty(xy_lunbo.data)) {
                                                addData(new BannerListVo(xy_lunbo.data, 183));
                                            }
                                        } else if ("cw_menu".equals(api)) {
                                            MainMenuVo xy_menu = data.data.getItemData(MainMenuVo.class, key);
                                            if (xy_menu != null) {
                                                addData(xy_menu);
                                            }
                                        } else if ("cw_tuijian".equals(api)) {
                                            MainXingYouDataVo.ZuiXingShangJiaDataBeanVo xy_zuixinshangjia = data.data.getItemData(MainXingYouDataVo.ZuiXingShangJiaDataBeanVo.class, key);
                                            if (xy_zuixinshangjia != null) {
                                                Collections.shuffle(xy_zuixinshangjia.data);
                                                addData(xy_zuixinshangjia);
                                            }
                                        } else if ("cw_week_game".equals(api)) {
                                            MainXingYouDataVo.ZuiXingShangJiaDataBeanVo xy_zuixinshangjia = data.data.getItemData(MainXingYouDataVo.ZuiXingShangJiaDataBeanVo.class, key);
                                            if (xy_zuixinshangjia != null) {
                                                addData(xy_zuixinshangjia);
                                            }
                                        } else if ("cw_big_pic_game".equals(api)) {
                                            MainPageItemVo xy_zhongbang = data.data.getItemData(MainPageItemVo.class, key);
                                            if (xy_zhongbang != null && checkCollectionNotEmpty(xy_zhongbang.data)) {
                                                xy_zhongbang.has_tag = true;
                                                addData(xy_zhongbang);
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
