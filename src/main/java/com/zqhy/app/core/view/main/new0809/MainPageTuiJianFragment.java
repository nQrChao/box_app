package com.zqhy.app.core.view.main.new0809;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.game.new0809.MainCommonDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.main.new0809.creator.MainPageTuiJianCreator;
import com.zqhy.app.core.vm.main.BtGameViewModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.VerticalSwipeRefreshLayout;
import com.zqhy.app.widget.recycleview.RecyclerScrollView;

/**
 * @author leeham2734
 * @date 2021/8/17-9:33
 * @description
 */
public class MainPageTuiJianFragment extends BaseFragment<BtGameViewModel> {
    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "首页-推荐";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_newtype;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initView();
        showLoading();
    }

    private LinearLayout               mainContent;
    private VerticalSwipeRefreshLayout swipeRefreshLayout;
    private RecyclerScrollView         scrollView;
    private MainPageTuiJianCreator     mMainPageTuiJianCreator;

    private void initView() {
        BaseActivity baseActivity = (BaseActivity) activity;
        mMainPageTuiJianCreator = new MainPageTuiJianCreator(this, baseActivity);
        mainContent = findViewById(R.id.main_content);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        scrollView = findViewById(R.id.main_content_area);
        swipeRefreshLayout.setScrollUpChild(scrollView);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            initGetData();
        });
        scrollView.setOnScrollListener((scrollY, y) -> onRecyclerViewScrolled(null, 0, scrollY));
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        initGetData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initGetData();
    }

    private void initGetData() {
        scrollView.smoothScrollTo(0, 0);
        if (mViewModel != null) {
            mViewModel.getTjHomePageData(new OnBaseCallback<MainCommonDataVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onSuccess(MainCommonDataVo data) {
                    genView(data.data);
                }
            });
        }
    }

    private void genView(MainCommonDataVo.DataBean data) {
        mMainPageTuiJianCreator.initPage(mainContent, data);
    }

    public void scrollToTop(){
        scrollView.scrollTo(0, 0);
        scrollView.smoothScrollTo(0, 0);
    }
}
