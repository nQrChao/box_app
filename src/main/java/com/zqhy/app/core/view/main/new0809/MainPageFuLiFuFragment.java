package com.zqhy.app.core.view.main.new0809;

import android.graphics.Color;
import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.banner.BannerListVo;
import com.zqhy.app.core.data.model.game.new0809.MainCommonDataVo;
import com.zqhy.app.core.data.model.game.new0809.MainFuLiFuDataVo;
import com.zqhy.app.core.data.model.game.new0809.MainFuliStyle1Vo;
import com.zqhy.app.core.data.model.game.new0809.item.LunboDataBeanVo;
import com.zqhy.app.core.data.model.mainpage.figurepush.GameFigurePushVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.main.AbsMainGameListFragment;
import com.zqhy.app.core.view.main.holder.GameFigurePushItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.MainBannerItemView;
import com.zqhy.app.core.view.main.new0809.holder.MainPageFuliStyle1ItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.NewNoDataItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.main.BtGameViewModel;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author Administrator
 * @date 2021/8/11 0011-16:11
 * @description
 */
public class MainPageFuLiFuFragment extends AbsMainGameListFragment<BtGameViewModel> {

    @Override
    protected String getUmengPageName() {
        return "首页-福利服";
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(MainFuliStyle1Vo.class, new MainPageFuliStyle1ItemHolder(_mActivity))
                .bind(GameFigurePushVo.class, new GameFigurePushItemHolder(_mActivity))
                //                .bind(BannerListVo.class, new GameBTBannerItemHolder(_mActivity, 86))
                .bind(BannerListVo.class, new MainBannerItemView(_mActivity, 86))
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
    public void initView(Bundle state) {
        super.initView(state);
        setListViewBackgroundColor(Color.parseColor("#F8F8F8"));
        DividerItemDecoration decoration = new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.main_pager_item_decoration));
        mRecyclerView.addItemDecoration(decoration);
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
            mViewModel.getFlfHomePageData(new OnBaseCallback<MainCommonDataVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(MainCommonDataVo data) {
                    clearData();
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

                                        if ("flf_lunbo".equals(api)) {
                                            LunboDataBeanVo flf_lunbo = data.data.getItemData(LunboDataBeanVo.class, key);
                                            if (flf_lunbo != null && checkCollectionNotEmpty(flf_lunbo.data)) {
                                                addData(new BannerListVo(flf_lunbo.data, 86));
                                            }
                                        } else if ("flf_game".equals(api)) {
                                            MainFuLiFuDataVo.GameDataBeanVo flf_game = data.data.getItemData(MainFuLiFuDataVo.GameDataBeanVo.class, key);
                                            if (flf_game != null && checkCollectionNotEmpty(flf_game.data)) {
                                                addAllData(flf_game.data);
                                            }
                                        }
                                    }
                                    if (hasModule) {
                                        addData(new NoMoreDataVo());
                                    } else {
                                        addData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                                    }
                                }


/*                                MainFuLiFuDataVo.DataVo dataVo = data.data.data;
                                if (module != null && dataVo != null) {
                                    for (String id : module) {
                                        if ("flf_lunbo".equals(id)) {
                                            if (dataVo.flf_lunbo != null && checkCollectionNotEmpty(dataVo.flf_lunbo.data)) {
                                                addData(new BannerListVo(dataVo.flf_lunbo.data));
                                            }
                                        } else if ("flf_game".equals(id)) {
                                            if (dataVo.flf_game != null && checkCollectionNotEmpty(dataVo.flf_game.data)) {
                                                addAllData(dataVo.flf_game.data);
                                            }
                                        }
                                    }
                                    addData(new NoMoreDataVo());
                                } else {
                                    addData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                                }*/

                            } else {
                                addData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                            }
                            notifyData();
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                    notifyData();
                }
            });
        }


    }
}
