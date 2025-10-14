package com.zqhy.app.core.view.server;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.game.GameNavigationVo;
import com.zqhy.app.core.data.model.game.ServerListVo;
import com.zqhy.app.core.data.model.game.ServerTimeVo;
import com.zqhy.app.core.data.model.mainpage.navigation.NewGameNavigationListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.main.MainActivity;
import com.zqhy.app.core.view.server.holder.TsServerListHolder;
import com.zqhy.app.core.view.server.holder.TsServerTimeHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.server.ServerViewModel;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author pc
 * @date 2019/12/2-11:56
 * @description
 */
public class TsServerListFragment extends BaseListFragment<ServerViewModel> implements View.OnClickListener {

    public static TsServerListFragment newInstance() {
        TsServerListFragment fragment = new TsServerListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static TsServerListFragment newInstance(String title) {
        TsServerListFragment fragment = new TsServerListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static TsServerListFragment newInstance(int game_type) {
        TsServerListFragment fragment = new TsServerListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("game_type", game_type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(ServerTimeVo.class, new TsServerTimeHolder(_mActivity))
                .bind(ServerListVo.DataBean.class, new TsServerListHolder(_mActivity))
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
    protected String getUmengPageName() {
        return "游戏开服页";
    }


    String dt = "jijiang";
    private String title;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            title = getArguments().getString("title");
        }
        super.initView(state);
        if (!TextUtils.isEmpty(title)) {
            initActionBackBarAndTitle(title);
        }
        if (_mActivity instanceof MainActivity){//判断是不是在主页显示
            hideActionBack(false);
        }
        addFixHeaderView();
        clickTabBt();
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
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return !TextUtils.isEmpty(title);
    }

    private View popupView;
    private PopupWindow popWindow;
    private void showPopUp(){
        if (popupView == null){
            popupView = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_popup_select_date_tips, null, false);
            popWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            TextView mTvToday = popupView.findViewById(R.id.tv_today);
            TextView mTvTomorrow = popupView.findViewById(R.id.tv_tomorrow);
            TextView mTvFuture= popupView.findViewById(R.id.tv_future);
            mTvToday.setOnClickListener(v -> {
                if (popWindow != null && popWindow.isShowing()) popWindow.dismiss();
                mTvToday.setTextColor(Color.parseColor("#E84B4B"));
                mTvTomorrow.setTextColor(Color.parseColor("#232323"));
                mTvFuture.setTextColor(Color.parseColor("#232323"));
                mTvDate.setText("正在开服");
                dt = "jijiang";
                getNetWorkData();
            });
            mTvTomorrow.setOnClickListener(v -> {
                if (popWindow != null && popWindow.isShowing()) popWindow.dismiss();
                mTvToday.setTextColor(Color.parseColor("#232323"));
                mTvTomorrow.setTextColor(Color.parseColor("#E84B4B"));
                mTvFuture.setTextColor(Color.parseColor("#232323"));
                mTvDate.setText("今日新服");
                dt = "jinri";
                getNetWorkData();
            });
            mTvFuture.setOnClickListener(v -> {
                if (popWindow != null && popWindow.isShowing()) popWindow.dismiss();
                mTvToday.setTextColor(Color.parseColor("#232323"));
                mTvTomorrow.setTextColor(Color.parseColor("#232323"));
                mTvFuture.setTextColor(Color.parseColor("#E84B4B"));
                mTvDate.setText("未来7天");
                dt = "qiri2";
                getNetWorkData();
            });
        }
        popWindow.setTouchable(true);
        popWindow.showAsDropDown(mLlTab);
    }

    private TextView mTvSeverListBt;
    private TextView mTvSeverListDiscount;
    private TextView mTvSeverListH5;

    private TextView mTvSeverListSoon;
    private TextView mTvSeverListToday;
    private TextView mTvSeverListTomorrow;
    private TextView mTvSeverListFuture;
    private LinearLayout mLlTab;
    private TextView mTvDate;
    private RecyclerView mRecyclerViewTab;
    private void addFixHeaderView() {
        View mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_ts_server_header, null);
        mLlTab = mHeaderView.findViewById(R.id.ll_tab);
        mTvDate = mHeaderView.findViewById(R.id.tv_date);
        mTvDate.setOnClickListener(v -> {
            showPopUp();
        });
        mRecyclerViewTab = mHeaderView.findViewById(R.id.recycler_view_tab);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewTab.setLayoutManager(linearLayoutManager);

        mTvSeverListSoon = mHeaderView.findViewById(R.id.tv_sever_list_soon);
        mTvSeverListToday = mHeaderView.findViewById(R.id.tv_sever_list_today);
        mTvSeverListTomorrow = mHeaderView.findViewById(R.id.tv_sever_list_tomorrow);
        mTvSeverListFuture = mHeaderView.findViewById(R.id.tv_sever_list_future);

        mTvSeverListBt = mHeaderView.findViewById(R.id.tv_sever_list_Bt);
        mTvSeverListDiscount = mHeaderView.findViewById(R.id.tv_sever_list_discount);
        mTvSeverListH5 = mHeaderView.findViewById(R.id.tv_sever_list_h5);

        if (BuildConfig.IS_CHANGE_TRANSACTION) {//判断是不是修改交易为开服表的临时版本
            mTvSeverListBt.setText("福利");
        }

        mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (mFlListFixTop != null) {
            mFlListFixTop.addView(mHeaderView);
        }


        mTvSeverListBt.setOnClickListener(this);
        mTvSeverListDiscount.setOnClickListener(this);
        mTvSeverListH5.setOnClickListener(this);

        mTvSeverListSoon.setOnClickListener(this);
        mTvSeverListToday.setOnClickListener(this);
        mTvSeverListTomorrow.setOnClickListener(this);
        mTvSeverListFuture.setOnClickListener(this);
    }

    private void clickTabBt() {
        clearTabTypeStatus();
        mTvSeverListBt.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
        mTvSeverListBt.setBackgroundResource(R.drawable.ts_shape_3478f6_small_radius);

    }

    private void clickTabDiscount() {
        clearTabTypeStatus();
        mTvSeverListDiscount.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
        mTvSeverListDiscount.setBackgroundResource(R.drawable.ts_shape_3478f6_small_radius);
    }

    private void clickTabH5() {
        clearTabTypeStatus();
        mTvSeverListH5.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
        mTvSeverListH5.setBackgroundResource(R.drawable.ts_shape_3478f6_small_radius);
    }

    private void clearTabTypeStatus() {
        mTvSeverListBt.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_000000));
        mTvSeverListBt.setBackgroundResource(R.drawable.ts_shape_f2f2f2_small_radius);

        mTvSeverListDiscount.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_000000));
        mTvSeverListDiscount.setBackgroundResource(R.drawable.ts_shape_f2f2f2_small_radius);

        mTvSeverListH5.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_000000));
        mTvSeverListH5.setBackgroundResource(R.drawable.ts_shape_f2f2f2_small_radius);
        clickTabSoon();
    }


    private void clickTabSoon() {
        clearTabStatus();
        mTvSeverListSoon.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
        mTvSeverListSoon.setBackgroundResource(R.drawable.ts_shape_3478f6_small_radius);
    }

    private void clickTabToday() {
        clearTabStatus();
        mTvSeverListToday.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
        mTvSeverListToday.setBackgroundResource(R.drawable.ts_shape_3478f6_small_radius);
    }

    private void clickTabTomorrow() {
        clearTabStatus();
        mTvSeverListTomorrow.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
        mTvSeverListTomorrow.setBackgroundResource(R.drawable.ts_shape_3478f6_small_radius);
    }

    private void clickTabFuture() {
        clearTabStatus();
        mTvSeverListFuture.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
        mTvSeverListFuture.setBackgroundResource(R.drawable.ts_shape_3478f6_small_radius);
    }

    private void clearTabStatus() {
        mTvSeverListSoon.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_000000));
        mTvSeverListSoon.setBackgroundResource(R.drawable.ts_shape_f2f2f2_small_radius);

        mTvSeverListToday.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_000000));
        mTvSeverListToday.setBackgroundResource(R.drawable.ts_shape_f2f2f2_small_radius);

        mTvSeverListTomorrow.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_000000));
        mTvSeverListTomorrow.setBackgroundResource(R.drawable.ts_shape_f2f2f2_small_radius);

        mTvSeverListFuture.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_000000));
        mTvSeverListFuture.setBackgroundResource(R.drawable.ts_shape_f2f2f2_small_radius);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sever_list_Bt:
                clickTabBt();
                dt = "jijiang";
                getNetWorkData();
                break;
            case R.id.tv_sever_list_discount:
                clickTabDiscount();
                dt = "jijiang";
                getNetWorkData();
                break;
            case R.id.tv_sever_list_h5:
                clickTabH5();
                dt = "jijiang";
                getNetWorkData();
                break;
            case R.id.tv_sever_list_soon:
                clickTabSoon();
                dt = "jijiang";
                getNetWorkData();
                break;
            case R.id.tv_sever_list_today:
                clickTabToday();
                dt = "jinri";
                getNetWorkData();
                break;
            case R.id.tv_sever_list_tomorrow:
                clickTabTomorrow();
                dt = "tomorrow";
                getNetWorkData();
                break;
            case R.id.tv_sever_list_future:
                clickTabFuture();
                dt = "qiri";
                getNetWorkData();
                break;
        }
    }


    private int page = 1;

    private final int pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getGameClassificationList();
        getNetWorkData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getNetWorkData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getNetWorkData();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        getMoreNetWorkData();
    }

    private void getNetWorkData() {
        if (mViewModel != null) {
            page = 1;
            getServerList();
        }
    }

    private void getMoreNetWorkData() {
        if (mViewModel != null) {
            page++;
            getServerList();
        }
    }

    private void getServerList() {
        if (mViewModel != null) {
            if (treeParams == null){
                treeParams = new TreeMap<>();
            }
            mViewModel.getServerList(treeParams, dt, page, pageCount, new OnBaseCallback<ServerListVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(ServerListVo serverListVo) {
                    if (serverListVo != null) {
                        if (serverListVo.isStateOK()) {
                            if (serverListVo.getData() != null && !serverListVo.getData().isEmpty()) {
                                if (page == 1) {
                                    clearData();
                                }
                                addAllData(serverListVo.getData());
                                notifyData();
                                //                                addAllData(serverListVo.getData());
                            } else {
                                if (page == 1) {
                                    clearData();
                                    addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                                } else {
                                    page = -1;
                                    setListNoMore(true);
                                }
                                notifyData();
                            }
                        } else {
                            Toaster.show( serverListVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    private List<GameNavigationVo>                       gameNavigationVoList;
    private List<NewGameNavigationListVo.SearchListBean> gameSearchList;
    public void getGameClassificationList() {
        if (mViewModel != null) {
            mViewModel.getGameHallList(new OnBaseCallback<NewGameNavigationListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                }

                @Override
                public void onSuccess(NewGameNavigationListVo gameNavigationListVo) {
                    if (gameNavigationListVo != null && gameNavigationListVo.isStateOK() && gameNavigationListVo.getData() != null) {
                        gameNavigationVoList = gameNavigationListVo.getData().getGenre_list();
                        gameSearchList = gameNavigationListVo.getData().getSearch_list();
                        List<SubTabVo> allTabList = getAllTabList();
                        MyAdapter myAdapter = new MyAdapter(allTabList);
                        mRecyclerViewTab.setAdapter(myAdapter);
                    }
                }
            });
        }
    }

    private List<SubTabVo> getAllTabList() {
        List<SubTabVo> gameTabVoList = new ArrayList<>();
        gameTabVoList.clear();
        //添加全部页面
        SubTabVo jxGameTabVo = getJxGameTabVo();
        jxGameTabVo.setLabelSelect(true);
        gameTabVoList.add(jxGameTabVo);
        //添加自定义关键字列表
        for (int i = 0; i < gameSearchList.size(); i++) {
            NewGameNavigationListVo.SearchListBean searchListBean = gameSearchList.get(i);
            gameTabVoList.add(new SubTabVo(1, searchListBean.getVisible_word()).addParams("kw", searchListBean.getSearch_word()).addParams("order", "hot").addGameType("1", "2", "3", "4"));
        }
        //添加分类列表
        if (gameNavigationVoList != null) {
            for (GameNavigationVo navigationVo : gameNavigationVoList) {
                SubTabVo gameTab = new SubTabVo();
                gameTab.setGenre_id(navigationVo.getGenre_id());
                gameTab.setType(navigationVo.getType());
                gameTab.setGenre_name(navigationVo.getGenre_name());
                gameTab.addParams("genre_id", String.valueOf(navigationVo.getGenre_id()));
                gameTab.addGameType("1", "2", "3", "4");
                gameTabVoList.add(gameTab);
            }
        }
        return gameTabVoList;
    }

    @NonNull
    private SubTabVo getJxGameTabVo() {
        return new SubTabVo(-10, "全部")
                .addParams("order", "hot");
    }

    private Map<String, String> treeParams;
    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        private List<SubTabVo> list;//数据源

        public MyAdapter(List<SubTabVo> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyViewHolder(LayoutInflater.from(_mActivity).inflate(R.layout.item_main_game_list_tab_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            SubTabVo menuDataBean = list.get(position);
            holder.mTvTab.setText(menuDataBean.getGenre_name());

            if (menuDataBean.isLabelSelect()){
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
                    list.get(i).setLabelSelect(false);
                }
                menuDataBean.setLabelSelect(true);
                notifyDataSetChanged();
                treeParams = new TreeMap<>();
                treeParams.clear();
                Map<String, String> params = menuDataBean.getParams();
                for (String key : params.keySet()) {
                    treeParams.put(key, params.get(key));
                }
                getNetWorkData();
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

    public static class SubTabVo extends GameNavigationVo {
        private Map<String, String> params;
        private boolean labelSelect;

        public boolean isLabelSelect() {
            return labelSelect;
        }

        public void setLabelSelect(boolean labelSelect) {
            this.labelSelect = labelSelect;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public SubTabVo() {
        }

        public SubTabVo(int tabIndicatorId, String tabTitle) {
            genre_id = tabIndicatorId;
            setGenre_name(tabTitle);
        }

        /**
         * 添加参数
         *
         * @param key
         * @param value
         */
        public SubTabVo addParams(String key, String value) {
            if (params == null) {
                params = new TreeMap<>();
            }
            params.put(key, value);
            return this;
        }

        public List<String> gameTypeList;

        public SubTabVo addGameType(String... game_type) {
            if (game_type.length > 0) {
                if (gameTypeList == null) {
                    gameTypeList = new ArrayList<>();
                }
                for (int i = 0; i < game_type.length; i++) {
                    gameTypeList.add(game_type[i]);
                }
            }
            return this;
        }

        public boolean isContainsGameType(String game_type) {
            if (gameTypeList == null) {
                return false;
            }
            return gameTypeList.contains(game_type);
        }
    }

}
