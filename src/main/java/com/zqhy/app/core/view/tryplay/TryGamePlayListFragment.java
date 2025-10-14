package com.zqhy.app.core.view.tryplay;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.tryplay.TryGameHeaderVo;
import com.zqhy.app.core.data.model.tryplay.TryGameItemVo;
import com.zqhy.app.core.data.model.tryplay.TryGameListVo;
import com.zqhy.app.core.data.model.tryplay.TryGameRewardListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.tryplay.holder.TryAllGameItemHolder;
import com.zqhy.app.core.view.tryplay.holder.TryGameHeaderItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.tryplay.TryGameViewModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.textbanner.TextBannerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class TryGamePlayListFragment extends BaseListFragment<TryGameViewModel> {

    public static TryGamePlayListFragment newInstance() {
        TryGamePlayListFragment fragment = new TryGamePlayListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(TryGameHeaderVo.class, new TryGameHeaderItemHolder(_mActivity))
                .bind(TryGameItemVo.DataBean.class, new TryAllGameItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
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
    protected String getUmengPageName() {
        return "试玩列表";
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }


    private final int ACTION_TRY_GAME_REQUEST_CODE = 10001;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("试玩有奖");
        setListViewBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));
        createFixTopView();
        initData();
        //        getTryGameRewardData();
        setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof TryGameItemVo.DataBean) {
                TryGameItemVo.DataBean dataBean = (TryGameItemVo.DataBean) data;
                //                                start(TryGameDetailFragment.newInstance(dataBean.getTid()));
                startForResult(TryGameTaskFragment.newInstance(dataBean.getTid()), ACTION_TRY_GAME_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ACTION_TRY_GAME_REQUEST_CODE) {
                initData();
            }
        }
    }

    @Override
    protected View getTitleRightView() {
        TextView tv = new TextView(_mActivity);
        tv.setText("我的试玩");
        tv.setPadding((int) (8 * density), 0, (int) (8 * density), 0);
        tv.setTextSize(12);
        tv.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_868686));

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(24 * density);

        gd.setColor(ContextCompat.getColor(_mActivity, R.color.white));
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_cccccc));

        tv.setGravity(Gravity.CENTER);
        tv.setBackground(gd);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (24 * density));
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(0, 0, (int) (6 * density), 0);
        tv.setLayoutParams(params);

        tv.setOnClickListener(view -> {
            if (checkLogin()) {
                //我的试玩
                start(new MyTryGameListFragment());
            }
        });
        return tv;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        initData();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        page++;
        getTryGameList();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bannerView != null) {
            bannerView.startViewAnimator();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (bannerView != null) {
            bannerView.stopViewAnimator();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (bannerView != null) {
                bannerView.stopViewAnimator();
            }
        } else {
            if (bannerView != null) {
                bannerView.startViewAnimator();
            }
        }
    }

    TextBannerView bannerView;

    private void createFixTopView() {
        if (mFlListFixTop != null) {
            LinearLayout llRootView = new LinearLayout(_mActivity);
            llRootView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            llRootView.setPadding((int) (18 * density), 0, 0, 0);
            llRootView.setOrientation(LinearLayout.HORIZONTAL);
            llRootView.setGravity(Gravity.CENTER_VERTICAL);
            llRootView.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_fff3e6));

            ImageView iconView = new ImageView(_mActivity);
            iconView.setImageResource(R.mipmap.ic_shuffling_try_game);

            bannerView = new TextBannerView(_mActivity);
            bannerView.setTextSize(12);
            bannerView.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_333333));
            bannerView.setHasSetDirection(true);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (38 * density));
            bannerView.setLayoutParams(params);
            bannerView.setPadding((int) (5 * density), 0, 0, 0);

            llRootView.addView(iconView);
            llRootView.addView(bannerView);
            mFlListFixTop.addView(llRootView);
            mFlListFixTop.setVisibility(View.GONE);
        }
    }

    private int page      = 1;
    private int pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }


    private void initData() {
        page = 1;
        getTryGameList();
    }

    private void getTryGameRewardData() {
        if (mViewModel != null) {
            mViewModel.getTryGameRewardNoticeData(new OnBaseCallback<TryGameRewardListVo>() {
                @Override
                public void onSuccess(TryGameRewardListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null && !data.getData().isEmpty()) {
                                if (mFlListFixTop != null) {
                                    mFlListFixTop.setVisibility(View.VISIBLE);
                                }
                                //设置数据
                                List<CharSequence> mList = new ArrayList<>();
                                for (int i = 0; i < data.getData().size(); i++) {
                                    TryGameRewardListVo.DataBean dataBean = data.getData().get(i);
                                    String nickname = dataBean.getUsername();
                                    int integral = dataBean.getReward_integral();
                                    mList.add(Html.fromHtml(_mActivity.getResources().getString(R.string.string_try_game_shuffling, nickname, String.valueOf(integral))));
                                }
                                bannerView.setVisibility(View.VISIBLE);
                                bannerView.setDatas(mList);
                            } else {
                                if (mFlListFixTop != null) {
                                    mFlListFixTop.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    private void getTryGameList() {
        if (mViewModel != null) {
            mViewModel.getTryGameListData(page, pageCount, new OnBaseCallback<TryGameListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(TryGameListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                if (page == 1) {
                                    clearData();
                                    addData(new TryGameHeaderVo());
                                }
                                long curTime = System.currentTimeMillis();
                                for (TryGameItemVo.DataBean dataBean : data.getData()) {
                                    dataBean.setEndTime(curTime + dataBean.getCount_down() * 1000);
                                }
                                addAllData(data.getData());
                            } else {
                                if (page == 1) {
                                    clearData();
                                    addData(new TryGameHeaderVo());
                                    addData(new EmptyDataVo(R.mipmap.img_empty_data_1)
                                            .setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT)
                                            .setPaddingTop(ScreenUtil.dp2px(_mActivity, 36)));
                                }
                                setListNoMore(true);
                                notifyData();
                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }


    /**
     * 刷新数据
     */
    public void refreshListData() {
        initData();
    }
}
