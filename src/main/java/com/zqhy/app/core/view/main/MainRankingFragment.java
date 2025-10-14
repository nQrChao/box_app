package com.zqhy.app.core.view.main;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.mainpage.TopGameListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.main.holder.GameNormalItemHolder;
import com.zqhy.app.core.view.main.holder.MainRankingItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pc
 * @date 2019/12/16-15:16
 * @description
 */
public class MainRankingFragment extends BaseListFragment<GameViewModel> {

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(GameInfoVo.class, new GameNormalItemHolder(_mActivity))
                .bind(TopGameListVo.class, new MainRankingItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment,this)
                .setTag(R.id.tag_sub_fragment,this);
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
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                onRecyclerViewScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    hideToolbar();
                }

                if (dy < 0) {
                    showToolbar();
                }
            }
        });
        setLoadingMoreEnabled(false);
        addFixHeaderView();
        clickTabBt();
    }


    private RelativeLayout mFlTabBt;
    private TextView       mTvTabBtName;
    private View           mTvTabBtDot;
    private RelativeLayout mFlTabDiscount;
    private TextView       mTvTabDiscountName;
    private View           mTvTabDiscountDot;
    private RelativeLayout mFlTabH5;
    private TextView       mTvTabH5Name;
    private View           mTvTabH5Dot;

    private void addFixHeaderView() {
        if (mFlListFixTop != null) {
            View mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_ts_main_ranking, null);
            mFlTabBt = mHeaderView.findViewById(R.id.fl_tab_bt);
            mTvTabBtName = mHeaderView.findViewById(R.id.tv_tab_bt_name);
            mTvTabBtDot = mHeaderView.findViewById(R.id.tv_tab_bt_dot);
            mFlTabDiscount = mHeaderView.findViewById(R.id.fl_tab_discount);
            mTvTabDiscountName = mHeaderView.findViewById(R.id.tv_tab_discount_name);
            mTvTabDiscountDot = mHeaderView.findViewById(R.id.tv_tab_discount_dot);
            mFlTabH5 = mHeaderView.findViewById(R.id.fl_tab_h5);
            mTvTabH5Name = mHeaderView.findViewById(R.id.tv_tab_h5_name);
            mTvTabH5Dot = mHeaderView.findViewById(R.id.tv_tab_h5_dot);

            mFlTabBt.setOnClickListener(v -> {
                clickTabBt();
                getNetWorkData();
            });
            mFlTabDiscount.setOnClickListener(v -> {
                clickTabDiscount();
                getNetWorkData();
            });
            mFlTabH5.setOnClickListener(v -> {
                clickTabH5();
                getNetWorkData();
            });

            mFlListFixTop.addView(mHeaderView);
        }
    }


    private void clickTabBt() {
        clearTabStatus();
        mTvTabBtName.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
        mTvTabBtName.getPaint().setFakeBoldText(true);
        mTvTabBtDot.setVisibility(View.VISIBLE);
        page = 1;
        game_type = 1;
    }

    private void clickTabDiscount() {
        clearTabStatus();
        mTvTabDiscountName.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
        mTvTabDiscountName.getPaint().setFakeBoldText(true);
        mTvTabDiscountDot.setVisibility(View.VISIBLE);
        page = 1;
        game_type = 2;
    }

    private void clickTabH5() {
        clearTabStatus();
        mTvTabH5Name.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
        mTvTabH5Name.getPaint().setFakeBoldText(true);
        mTvTabH5Dot.setVisibility(View.VISIBLE);
        page = 1;
        game_type = 3;
    }

    private void clearTabStatus() {
        mTvTabBtName.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_7d7d7d));
        mTvTabBtName.getPaint().setFakeBoldText(false);
        mTvTabBtDot.setVisibility(View.INVISIBLE);

        mTvTabDiscountName.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_7d7d7d));
        mTvTabDiscountName.getPaint().setFakeBoldText(false);
        mTvTabDiscountDot.setVisibility(View.INVISIBLE);

        mTvTabH5Name.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_7d7d7d));
        mTvTabH5Name.getPaint().setFakeBoldText(false);
        mTvTabH5Dot.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        page = 1;
        getNetWorkData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        page = 1;
        getNetWorkData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        page = 1;
        getNetWorkData();
    }

    private int game_type = 1;

    private int page = 1, pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    private void getNetWorkData() {
        if (mViewModel != null) {
            mViewModel.getGameRankingList(game_type, page, pageCount, new OnBaseCallback<GameListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    refreshAndLoadMoreComplete();
                    showSuccess();
                }

                @Override
                public void onSuccess(GameListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                if (page == 1) {
                                    clearData();
                                    int topThree = 3;
                                    TopGameListVo topGameListVo = new TopGameListVo();
                                    List<GameInfoVo> leftGameList = new ArrayList<>();
                                    for (int i = 0; i < data.getData().size(); i++) {
                                        GameInfoVo gameInfoVo = data.getData().get(i);
                                        gameInfoVo.setIndexPosition(i);
                                        if (i < topThree) {
                                            topGameListVo.addGameInfoVo(gameInfoVo);
                                        } else {
                                            leftGameList.add(gameInfoVo);
                                        }
                                    }
                                    addData(topGameListVo);
                                    addAllData(leftGameList);
                                } else {
                                    addAllData(data.getData());
                                }
                            } else {
                                if (page == 1) {
                                    //empty data
                                    addData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                                }
                                //no more data
                                page = -1;
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
}
