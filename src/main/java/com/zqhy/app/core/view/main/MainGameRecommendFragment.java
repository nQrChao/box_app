package com.zqhy.app.core.view.main;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.mainpage.MainGameRecommendVo;
import com.zqhy.app.core.data.model.mainpage.recommend.MainGameRecommendItemVo1;
import com.zqhy.app.core.data.model.mainpage.recommend.MainGameRecommendItemVo2;
import com.zqhy.app.core.data.model.mainpage.recommend.MainGameRecommendItemVo3;
import com.zqhy.app.core.data.model.mainpage.recommend.MainGameRecommendItemVo4;
import com.zqhy.app.core.data.model.nodata.MoreGameDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.main.holder.MainPagerMoreGameItemHolder;
import com.zqhy.app.core.view.main.holder.MainRecommendGameItemHolder1;
import com.zqhy.app.core.view.main.holder.MainRecommendGameItemHolder2;
import com.zqhy.app.core.view.main.holder.MainRecommendGameItemHolder3;
import com.zqhy.app.core.view.main.holder.MainRecommendGameItemHolder4;
import com.zqhy.app.core.vm.main.MainViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2021/6/7-9:12
 * @description
 */
public class MainGameRecommendFragment extends AbsMainGameListFragment<MainViewModel> {

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(MainGameRecommendItemVo1.class, new MainRecommendGameItemHolder1(_mActivity))
                .bind(MainGameRecommendItemVo2.class, new MainRecommendGameItemHolder2(_mActivity))
                .bind(MainGameRecommendItemVo3.class, new MainRecommendGameItemHolder3(_mActivity))
                .bind(MainGameRecommendItemVo4.class, new MainRecommendGameItemHolder4(_mActivity))
                .bind(MoreGameDataVo.class, new MainPagerMoreGameItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    public Object getStateEventKey() {
        return "首页-推荐";
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        setListViewBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));
        setPullRefreshEnabled(true);
        setLoadingMoreEnabled(false);

        initData();
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

    private void initData() {
        if (mViewModel != null) {
            mViewModel.getMainRecommendData(new OnBaseCallback<MainGameRecommendVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(MainGameRecommendVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                //开始整合数据
                                clearData();
                                //item 1
                                addData(MainGameRecommendItemVo1.createItem(data.getData().getIcon_menu(), data.getData().getSlider()));
                                //item 2
                                addData(MainGameRecommendItemVo2.createItem(data.getData().getHot_recommen()));
                                //item 3
                                addData(MainGameRecommendItemVo3.createItem(data.getData().getCoupon_game(), data.getData().getIllstration()));
                                //item4
                                addData(MainGameRecommendItemVo4.createItem(data.getData().getBt_new(), data.getData().getBt_hot()));

                                addData(new MoreGameDataVo(1));
                            } else {
                                showEmptyData();
                            }
                        } else {
                            Toaster.show(data.getMsg());
                            showErrorTag1();
                        }
                    } else {
                        showEmptyData();
                    }
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    showErrorTag1();
                }
            });
        }
    }
}
