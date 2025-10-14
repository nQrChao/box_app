package com.zqhy.app.core.view.main.new0809;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.MainGameRankDataVo;
import com.zqhy.app.core.data.model.game.new0809.NewMainGameRankDataVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.core.view.game.holder.GameRankingItemHolder;
import com.zqhy.app.core.view.main.holder.GameNoMoreItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.RecyclerViewNoBugLinearLayoutManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 游戏排行榜
 */
public class NewGameRankingActivityFragment extends BaseFragment<GameViewModel> {

    private RecyclerView recyclerView;
    private RecyclerView mRecyclerViewTab;
    private TextView mIvEmptyView;
    //                startFragment(GameRankingActivityFragment.newInstance(null));
    //type new:新游榜 hot：热门榜， discount：折扣榜
    public static NewGameRankingActivityFragment newInstance() {
        NewGameRankingActivityFragment fragment = new NewGameRankingActivityFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }
    public static NewGameRankingActivityFragment newInstance(String type) {
        NewGameRankingActivityFragment fragment = new NewGameRankingActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_GAME_DETAIL_STATE;
    }

    private String pageType;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_game_ranking_new;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private BaseRecyclerAdapter mAdapter;
    private MyAdapter myAdapter;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        if (getArguments() != null) {
            pageType = getArguments().getString("type","");
        }
        findViewById(R.id.iv_back).setOnClickListener(v -> pop());

        recyclerView = findViewById(R.id.recyclerView);
        mRecyclerViewTab = findViewById(R.id.recycler_view_tab);
        mIvEmptyView = findViewById(R.id.iv_empty_bg);

        if (!TextUtils.isEmpty(pageType)){
            findViewById(R.id.ll_title).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_tab).setVisibility(View.GONE);
            mRecyclerViewTab.setVisibility(View.GONE);
            if ("hot".equals(pageType)){
                ((TextView) findViewById(R.id.tv_title)).setText("热门榜");
            }else if ("new".equals(pageType)){
                ((TextView) findViewById(R.id.tv_title)).setText("精品榜");
            }else if ("zhekou_page".equals(pageType)){
                ((TextView) findViewById(R.id.tv_title)).setText("折扣榜");
            }
        }else {
            findViewById(R.id.ll_title).setVisibility(View.GONE);
            findViewById(R.id.ll_tab).setVisibility(View.VISIBLE);
            mRecyclerViewTab.setVisibility(View.VISIBLE);
        }

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_3478f6,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            for (int i = 0; i < myAdapter.list.size(); i++) {
                if (myAdapter.list.get(i).isLabelSelect){
                    getGameRankingData(myAdapter.list.get(i).api);
                }
            }
        });
        RecyclerViewNoBugLinearLayoutManager layoutManager = new RecyclerViewNoBugLinearLayoutManager(_mActivity);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(GameInfoVo.class, new GameRankingItemHolder(_mActivity, false))
                .bind(NoMoreDataVo.class, new GameNoMoreItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((v, position, data) -> {
            if (data instanceof GameInfoVo) {
                startFragment(GameDetailInfoFragment.newInstance(((GameInfoVo) data).getGameid(), ((GameInfoVo) data).getGame_type()));
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerViewTab.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
    }


    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getBangTab();
    }


    private void loadingDataComplete() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    public void getBangTab(){
        if (mViewModel != null) {
            mViewModel.getBangTab(new OnBaseCallback<NewMainGameRankDataVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }

                @Override
                public void onSuccess(NewMainGameRankDataVo data) {
                    if (data.isStateOK() && data != null){
                        if (data.data != null && data.data.ranking != null && data.data.ranking.size() > 0){
                            if (!TextUtils.isEmpty(pageType)){
                                for (int i = 0; i < data.data.ranking.size(); i++) {
                                    if (pageType.equals(data.data.ranking.get(i).type)){
                                        data.data.ranking.get(i).isLabelSelect = true;
                                        getGameRankingData(data.data.ranking.get(i).api);
                                        myAdapter = new MyAdapter(data.data.ranking);
                                        mRecyclerViewTab.setAdapter(myAdapter);
                                    }
                                }
                            }else {
                                data.data.ranking.get(0).isLabelSelect = true;
                                getGameRankingData(data.data.ranking.get(0).api);
                                myAdapter = new MyAdapter(data.data.ranking);
                                mRecyclerViewTab.setAdapter(myAdapter);
                            }
                        }
                    }
                }
            });
        }
    }

    public void getGameRankingData(String api) {
        if (mViewModel != null) {
            Map<String, String> params = new HashMap<>();
            params.put("api", api);
            mViewModel.getRankingData(params, new OnBaseCallback<MainGameRankDataVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    //showLoading();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    loadingDataComplete();
                }

                @Override
                public void onSuccess(MainGameRankDataVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.data != null) {
                                mAdapter.clear();
                                if (data.data.list != null && !data.data.list.isEmpty()) {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    mIvEmptyView.setVisibility(View.GONE);
                                    int index = 1;
                                    for (GameInfoVo gameInfoVo : data.data.list) {
                                        gameInfoVo.setIndexPosition(index);
                                        index++;
                                    }
                                    /*if (data.data.ranking != null && data.data.ranking.size() > 0){
                                        for (int i = 0; i < data.data.ranking.size(); i++) {
                                            if (data.data.type.equals(data.data.ranking.get(i).type)){
                                                data.data.ranking.get(i).isLabelSelect = true;
                                                pageType = data.data.type;
                                                if (!TextUtils.isEmpty(data.data.type) && findViewById(R.id.ll_title).getVisibility() == View.VISIBLE){
                                                    ((TextView)findViewById(R.id.tv_title)).setText(data.data.ranking.get(i).label);
                                                }
                                            }
                                        }
                                    }*/
                                    mAdapter.addAllData(data.data.list);
                                }else {
                                    recyclerView.setVisibility(View.GONE);
                                    mIvEmptyView.setVisibility(View.VISIBLE);
                                }
                                mAdapter.notifyDataSetChanged();
                                //recyclerView.smoothScrollToPosition(0);
                            }
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        private List<NewMainGameRankDataVo.TabBean> list;//数据源

        public MyAdapter(List<NewMainGameRankDataVo.TabBean> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyViewHolder(LayoutInflater.from(_mActivity).inflate(R.layout.item_main_game_list_tab_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            NewMainGameRankDataVo.TabBean menuDataBean = list.get(position);
            holder.mTvTab.setText(menuDataBean.label);

            if (menuDataBean.isLabelSelect){
                holder.mTvTab.setTextColor(Color.parseColor("#232323"));
                holder.mTvTab.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                holder.mIvTab.setVisibility(View.VISIBLE);
            }else {
                holder.mTvTab.setTextColor(Color.parseColor("#666666"));
                holder.mTvTab.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                holder.mIvTab.setVisibility(View.GONE);
            }

            holder.itemView.setOnClickListener(v -> {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).isLabelSelect = false;
                }
                menuDataBean.isLabelSelect = true;
                getGameRankingData(menuDataBean.api);
                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mTvTab;
        private ImageView mIvTab;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTab = itemView.findViewById(R.id.tv_tab);
            mIvTab = itemView.findViewById(R.id.iv_tab);
        }
    }
}
