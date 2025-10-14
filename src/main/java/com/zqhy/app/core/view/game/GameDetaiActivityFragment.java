package com.zqhy.app.core.view.game;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.game.GameDataVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.detail.GameActivityVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.game.holder.GameDetailActivityItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动列表
 */
public class GameDetaiActivityFragment extends BaseFragment<GameViewModel> {

    public static GameDetaiActivityFragment newInstance(String name, int gameid) {
        GameDetaiActivityFragment fragment = new GameDetaiActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putInt("gameid", gameid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_GAME_DETAIL_STATE;
    }

    @Override
    protected String getStateEventTag() {
        return String.valueOf(gameid);
    }

    private int gameid;

    private BaseRecyclerAdapter strategyAdapter;
    private BaseRecyclerAdapter normalAdapter;
    private BaseRecyclerAdapter endAdapter;
    private TextView mTvStrategy;
    private RecyclerView mRecyclerViewStrategy;
    private RecyclerView mRecyclerViewNormal;
    private RecyclerView mRecyclerViewEnd;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_game_activi;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        if (getArguments() != null) {
            gameid = getArguments().getInt("gameid");
        }

        initActionBackBarAndTitle("活动中心");

        mTvStrategy = findViewById(R.id.tv_strategy);
        mRecyclerViewStrategy = findViewById(R.id.recycler_view_strategy);
        mRecyclerViewNormal = findViewById(R.id.recycler_view_normal);
        mRecyclerViewEnd = findViewById(R.id.recycler_view_end);
        mRecyclerViewStrategy.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerViewNormal.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerViewEnd.setLayoutManager(new LinearLayoutManager(_mActivity));

        strategyAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(GameActivityVo.ItemBean.class, new GameDetailActivityItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
        normalAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(GameActivityVo.ItemBean.class, new GameDetailActivityItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);

        endAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(GameActivityVo.ItemBean.class, new GameDetailActivityItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);

        mRecyclerViewStrategy.setAdapter(strategyAdapter);
        mRecyclerViewNormal.setAdapter(normalAdapter);
        mRecyclerViewEnd.setAdapter(endAdapter);
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        getNetWorkData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getNetWorkData();
    }


    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getNetWorkData();
    }

    private GameInfoVo.CardlistBean cgCard = null;
    /**
     * 获取游戏信息
     */
    private void getNetWorkData() {
        if (mViewModel != null) {
            mViewModel.getGameInfoPartFl(gameid, new OnBaseCallback<GameDataVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }

                @Override
                public void onSuccess(GameDataVo gameDataVo) {
                    if (gameDataVo != null) {
                        if (gameDataVo.isStateOK()) {
                            GameInfoVo infoVo = gameDataVo.getData();
                            strategyAdapter.clear();
                            normalAdapter.clear();
                            endAdapter.clear();
                            if (infoVo != null) {
                                //独家活动
                                GameActivityVo gameActivityVo = infoVo.getGameActivityVo();
                                gameActivityVo.setUserCommented(false);
                                if (gameActivityVo.getItemCount() > 0) {
                                    mTvStrategy.setVisibility(View.VISIBLE);
                                    mRecyclerViewStrategy.setVisibility(View.VISIBLE);
                                    List<GameActivityVo.ItemBean> itemBeanList0 = new ArrayList<>();
                                    itemBeanList0.addAll(createNewsBeans(gameActivityVo, "strategy"));
                                    strategyAdapter.setDatas(itemBeanList0);

                                    List<GameActivityVo.ItemBean> itemBeanList = new ArrayList<>();
                                    itemBeanList.addAll(createNewsBeans(gameActivityVo, "normal"));
                                    normalAdapter.setDatas(itemBeanList);

                                    List<GameActivityVo.ItemBean> itemBeanList1 = new ArrayList<>();
                                    itemBeanList1.addAll(createNewsBeans(gameActivityVo, "end"));
                                    endAdapter.setDatas(itemBeanList1);

                                    if(itemBeanList0.size() == 0){
                                        mTvStrategy.setVisibility(View.GONE);
                                        mRecyclerViewStrategy.setVisibility(View.GONE);
                                        strategyAdapter.clear();
                                        strategyAdapter.addData(new EmptyDataVo(R.mipmap.ic_game_detail_coupon_dialog_list_empty_bg));
                                    }
                                    if(itemBeanList.size() == 0){
                                        normalAdapter.clear();
                                        normalAdapter.addData(new EmptyDataVo(R.mipmap.ic_game_detail_coupon_dialog_list_empty_bg));
                                    }
                                    if(itemBeanList1.size() == 0){
                                        endAdapter.clear();
                                        endAdapter.addData(new EmptyDataVo(R.mipmap.ic_game_detail_coupon_dialog_list_empty_bg));
                                    }
                                }else {
                                    mTvStrategy.setVisibility(View.GONE);
                                    mRecyclerViewStrategy.setVisibility(View.GONE);
                                    strategyAdapter.clear();
                                    strategyAdapter.addData(new EmptyDataVo(R.mipmap.ic_game_detail_coupon_dialog_list_empty_bg));
                                    normalAdapter.clear();
                                    normalAdapter.addData(new EmptyDataVo(R.mipmap.ic_game_detail_coupon_dialog_list_empty_bg));
                                    endAdapter.clear();
                                    endAdapter.addData(new EmptyDataVo(R.mipmap.ic_game_detail_coupon_dialog_list_empty_bg));
                                }
                            }else {
                                mTvStrategy.setVisibility(View.GONE);
                                mRecyclerViewStrategy.setVisibility(View.GONE);
                                strategyAdapter.clear();
                                strategyAdapter.addData(new EmptyDataVo(R.mipmap.ic_game_detail_coupon_dialog_list_empty_bg));
                                normalAdapter.clear();
                                normalAdapter.addData(new EmptyDataVo(R.mipmap.ic_game_detail_coupon_dialog_list_empty_bg));
                                endAdapter.clear();
                                endAdapter.addData(new EmptyDataVo(R.mipmap.ic_game_detail_coupon_dialog_list_empty_bg));
                            }
                            strategyAdapter.notifyDataSetChanged();
                            normalAdapter.notifyDataSetChanged();
                            endAdapter.notifyDataSetChanged();
                        } else {
                            //ToastT.error(_mActivity, gameDataVo.getMsg());
                            Toaster.show(gameDataVo.getMsg());
                        }
                    }
                }
            });

        }
    }



    /**
     * 创建new item
     *
     * @param gameActivityVo
     * @return
     */
    private List<GameActivityVo.ItemBean> createNewsBeans(GameActivityVo gameActivityVo, String status) {
        List<GameActivityVo.ItemBean> topMenuInfoBeanList = new ArrayList<>();

        if (gameActivityVo.getActivity() == null) {
            return topMenuInfoBeanList;
        }

        for (GameInfoVo.NewslistBean newslistBean : gameActivityVo.getActivity()) {
            if (newslistBean.getNews_status().equals(status)){
                GameActivityVo.ItemBean itemBean = new GameActivityVo.ItemBean();
                itemBean.setType(1);
                itemBean.setGemeId(gameActivityVo.getGameid());
                if (gameActivityVo.getTrial_info() != null){
                    itemBean.setTid(gameActivityVo.getTrial_info().getTid());
                }
                itemBean.setNewslistBean(newslistBean);
                topMenuInfoBeanList.add(itemBean);
            }
        }
        return topMenuInfoBeanList;
    }
}
