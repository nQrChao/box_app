package com.zqhy.app.core.view.tryplay;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.tryplay.TryGameInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.tryplay.holder.TryGameTaskItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.tryplay.TryGameViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author leeham2734
 * @date 2020/11/3-9:38
 * @description
 */
public class TryGameTaskFragment extends BaseFragment<TryGameViewModel> {

    public static TryGameTaskFragment newInstance(int tid) {
        TryGameTaskFragment fragment = new TryGameTaskFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tid", tid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "试玩详情";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_try_game_task;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private int tid;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            tid = getArguments().getInt("tid");
        }
        super.initView(state);
        initActionBackBarAndTitle("试玩任务");
        bindViews();
        initData();
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView          mIvGameIcon;
    private TextView           mTvGameName;
    private TextView           mTvTryGameDeadline;
    private RecyclerView       mRecyclerView;
    private TextView           mTvTryGameAction;
    private LinearLayout       mLlGameInfo;
    private TextView mTvGameSuffix;

    private void bindViews() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mLlGameInfo = findViewById(R.id.ll_game_info);
        mIvGameIcon = findViewById(R.id.iv_game_icon);
        mTvGameName = findViewById(R.id.tv_game_name);
        mTvTryGameDeadline = findViewById(R.id.tv_try_game_deadline);
        mRecyclerView = findViewById(R.id.recycler_view);
        mTvTryGameAction = findViewById(R.id.tv_try_game_action);
        mTvGameSuffix = findViewById(R.id.tv_game_suffix);

        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            initData();
        });
        initList();
        mTvTryGameAction.setOnClickListener(view -> {
            if (checkLogin()) {
                if (isJoinTryGame) {
                    goGameDetail(gameid, game_type);
                } else {
                    setJoinTryGame(tid);
                }
            }
        });
        mLlGameInfo.setOnClickListener(view -> {
            goGameDetail(gameid, game_type);
        });
    }

    private BaseRecyclerAdapter mAdapter;

    private void initList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(TryGameInfoVo.TrialItemInfoVo.class, new TryGameTaskItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setGameViewLayoutVisibility(int visibility) {
        mLlGameInfo.setVisibility(visibility);
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        initData();
    }

    private void initData() {
        getTryGameDetailData();
    }

    private void getTryGameDetailData() {
        getTryGameDetailData(false);
    }

    private void getTryGameDetailData(boolean isRefreshData) {
        if (mViewModel != null) {
            mViewModel.getTryGameDetailData(tid, new OnBaseCallback<TryGameInfoVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onSuccess(TryGameInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            setViewValue(isRefreshData, data.getData());
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private boolean isJoinTryGame = false;
    private int     gameid, game_type;

    private void setViewValue(boolean isRefreshData, TryGameInfoVo.DataBean data) {
        if (data == null) {
            setGameViewLayoutVisibility(View.GONE);
            mAdapter.clear();
            mAdapter.addData(new EmptyDataVo());
            mAdapter.notifyDataSetChanged();
            return;
        }
        if (data.getTop_info() != null) {
            TryGameInfoVo.TryTopInfoVo gameInfoVo = data.getTop_info();
            setGameViewLayoutVisibility(View.VISIBLE);
            gameid = gameInfoVo.getGameid();
            game_type = gameInfoVo.getGame_type();
            GlideUtils.loadRoundImage(_mActivity, gameInfoVo.getGameicon(), mIvGameIcon);
            mTvGameName.setText(gameInfoVo.getGamename());
            mTvTryGameDeadline.setText("试玩截止至" + CommonUtils.formatTimeStamp(gameInfoVo.getEndtime() * 1000, "MM月dd日"));

            if (!TextUtils.isEmpty(gameInfoVo.getOtherGameName())){//游戏后缀
                mTvGameSuffix.setVisibility(View.VISIBLE);
                mTvGameSuffix.setText(gameInfoVo.getOtherGameName());
            }else {
                mTvGameSuffix.setVisibility(View.GONE);
            }

            if (!gameInfoVo.getIs_join()) {
                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(ScreenUtil.dp2px(_mActivity, 6));
                gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_0052ef));
                mTvTryGameAction.setBackground(gd);
                mTvTryGameAction.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
                mTvTryGameAction.setText("报名参加");
                isJoinTryGame = false;
            } else {
                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(ScreenUtil.dp2px(_mActivity, 6));
                gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_e5e5e5));
                mTvTryGameAction.setBackground(gd);
                mTvTryGameAction.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_0052ef));
                mTvTryGameAction.setText("已报名，去下载游戏");
                isJoinTryGame = true;
            }
        } else {
            setGameViewLayoutVisibility(View.GONE);
        }
        mAdapter.clear();
        if (data.getList() != null && !data.getList().isEmpty()) {
            mAdapter.addAllData(data.getList());
        } else {
            mAdapter.addData(new EmptyDataVo());
        }
        mAdapter.notifyDataSetChanged();
    }


    /**
     * 申请试玩
     *
     * @param tid
     */
    private void setJoinTryGame(int tid) {
        if (mViewModel != null) {
            mViewModel.setJoinTryGame(tid, new OnBaseCallback() {

                @Override
                public void onBefore() {
                    super.onBefore();
                    loading();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            initData();
                            setFragmentResult(RESULT_OK,null);
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }


    /**
     * 领取试玩奖励
     *
     * @param task_id
     */
    public void getTryGameTaskReward(int task_id) {
        if (mViewModel != null) {
            mViewModel.getTryGameReward(task_id, new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            initData();
                            Toaster.show("领取成功");
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }
}
