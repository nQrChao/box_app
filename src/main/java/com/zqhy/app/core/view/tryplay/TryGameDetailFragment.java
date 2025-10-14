package com.zqhy.app.core.view.tryplay;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.base.collapsing.BaseCollapsingViewPagerFragment;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.tryplay.TryGameInfoVo;
import com.zqhy.app.core.download.DownloaderHelper;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.tryplay.chlid.ImpactListFragment;
import com.zqhy.app.core.view.tryplay.chlid.RankingListFragment;
import com.zqhy.app.core.view.tryplay.chlid.RewardListFragment;
import com.zqhy.app.core.vm.tryplay.TryGameViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class TryGameDetailFragment extends BaseCollapsingViewPagerFragment<TryGameViewModel> {

    public static TryGameDetailFragment newInstance(int tid) {
        TryGameDetailFragment fragment = new TryGameDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tid", tid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    private LinearLayout mLlItem;
    private ImageView mIvTryGameIcon;
    private TextView mTvTryGameName;
    private TextView mTvTryGameInfo;
    private TextView mTvTryGameIntegral;
    private TextView mTvTryGameTotalIntegral;
    private RecyclerView mRecyclerView;

    @NonNull
    @Override
    protected View getCollapsingView() {
        View mCollapsingView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_collapsing_try_game_detail, null);
        mLlItem = mCollapsingView.findViewById(R.id.ll_item);
        mIvTryGameIcon = mCollapsingView.findViewById(R.id.iv_try_game_icon);
        mTvTryGameName = mCollapsingView.findViewById(R.id.tv_try_game_name);
        mTvTryGameInfo = mCollapsingView.findViewById(R.id.tv_try_game_info);
        mTvTryGameIntegral = mCollapsingView.findViewById(R.id.tv_try_game_integral);
        mTvTryGameTotalIntegral = mCollapsingView.findViewById(R.id.tv_try_game_total_integral);
        mRecyclerView = mCollapsingView.findViewById(R.id.recycler_view);

        setTimeLineLayout();

        mCollapsingView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        return mCollapsingView;
    }


    private TimeLineAdapter mTimeLineAdapter;

    private void setTimeLineLayout() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(layoutManager);

        List<CharSequence> list = new ArrayList<>();

        mTimeLineAdapter = new TimeLineAdapter(_mActivity, list);
        mRecyclerView.setAdapter(mTimeLineAdapter);
    }

    private class TimeLineAdapter extends AbsAdapter<CharSequence> {

        public TimeLineAdapter(Context context, List<CharSequence> labels) {
            super(context, labels);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, CharSequence data, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            if (position == 0) {
                viewHolder.mLineNormalTop.setVisibility(View.INVISIBLE);
                viewHolder.mLineNormalBottom.setVisibility(View.VISIBLE);
            } else if (position == mLabels.size() - 1) {
                viewHolder.mLineNormalTop.setVisibility(View.VISIBLE);
                viewHolder.mLineNormalBottom.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.mLineNormalTop.setVisibility(View.VISIBLE);
                viewHolder.mLineNormalBottom.setVisibility(View.VISIBLE);
            }
            viewHolder.mTvText.setText(data);
        }

        @Override
        public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ((ViewHolder) holder).mLineNormalTop.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            ((ViewHolder) holder).mLineNormalBottom.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }


        @Override
        public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
            super.onViewDetachedFromWindow(holder);

        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_timeline;
        }

        @Override
        public AbsViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }


        private class ViewHolder extends AbsViewHolder {
            private View mViewDot;
            private View mLineNormalTop;
            private View mLineNormalBottom;
            private TextView mTvText;

            public ViewHolder(View itemView) {
                super(itemView);
                mViewDot = findViewById(R.id.view_dot);
                mLineNormalTop = findViewById(R.id.line_normal_top);
                mLineNormalBottom = findViewById(R.id.line_normal_bottom);
                mTvText = findViewById(R.id.tv_text);

            }
        }
    }


    @NonNull
    @Override
    protected View getToolBarView() {
        return null;
    }

    protected View getTitleRightView() {
        TextView tv = new TextView(_mActivity);
        tv.setText("试玩规则");
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
            //试玩规则
            showTryGameRuleDialog();
        });
        return tv;
    }


    private String[] pageTitle = new String[]{};

    @Override
    protected String[] createPageTitle() {
        return pageTitle;
    }

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected List<Fragment> createFragments() {
        return fragmentList;
    }

    private int tid;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            tid = getArguments().getInt("tid");
        }
        super.initView(state);
        setToolbarVisibility(View.GONE);
        setSwipeRefresh(() -> refreshData());
        addTitleView();
        addBottomView();
        initData();
        initActionBackBarAndTitle("");
    }

    @Override
    protected boolean isCanSwipeRefresh() {
        return true;
    }

    private void initData() {
        getTryGameDetailData();
    }

    private void addTitleView() {
        if (mFlListTop != null) {
            mFlListTop.setVisibility(View.VISIBLE);
            View mToolBarView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_common_collapsing_title, null);
            View mTitleBottomLine = mToolBarView.findViewById(R.id.title_bottom_line);
            FrameLayout mFlTitleRight = mToolBarView.findViewById(R.id.fl_title_right);
            TextView mTvTitle = mToolBarView.findViewById(R.id.tv_title);

            View titleRightView = getTitleRightView();
            if (titleRightView != null) {
                mFlTitleRight.addView(titleRightView);
            }
            mTitleBottomLine.setVisibility(View.VISIBLE);
            mFlListTop.addView(mToolBarView);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTvTitle.getLayoutParams();
            params.leftMargin = 0;
            params.rightMargin = 0;
            params.removeRule(RelativeLayout.CENTER_IN_PARENT);
            params.addRule(RelativeLayout.RIGHT_OF, R.id.iv_back);
            params.addRule(RelativeLayout.CENTER_VERTICAL);

            mTvTitle.setLayoutParams(params);
        }
    }

    private FrameLayout mFlDownload;
    private ProgressBar mDownloadProgress;
    private ImageView mIvDownload;
    private TextView mTvDownload;
    private TextView mTvTryGameAward;

    private void addBottomView() {
        if (mFlListBottom != null) {
            mFlListBottom.setVisibility(View.VISIBLE);
            View mBottomView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_try_game_detail_bottom, null);

            mFlDownload = mBottomView.findViewById(R.id.fl_download);
            mDownloadProgress = mBottomView.findViewById(R.id.download_progress);
            mIvDownload = mBottomView.findViewById(R.id.iv_download);
            mTvDownload = mBottomView.findViewById(R.id.tv_download);
            mTvTryGameAward = mBottomView.findViewById(R.id.tv_try_game_award);


            mTvTryGameAward.setVisibility(View.GONE);
//            if (mViewPager != null) {
//                mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                    @Override
//                    public void onPageScrolled(int i, float v, int i1) {
//
//                    }
//
//                    @Override
//                    public void onPageSelected(int position) {
//                        if (position == 0) {
//                            mTvTryGameAward.setVisibility(View.VISIBLE);
//                        } else {
//                            mTvTryGameAward.setVisibility(View.GONE);
//                        }
//                    }
//
//                    @Override
//                    public void onPageScrollStateChanged(int i) {
//
//                    }
//                });
//            }

            mFlListBottom.addView(mBottomView);

            mTvTryGameAward.setOnClickListener(v -> {
                //一键领取
                try {
                    RewardListFragment rewardListFragment = (RewardListFragment) fragmentList.get(0);
                    rewardListFragment.getPageRewardIntegral();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
    }

    private DownloaderHelper downloaderHelper;

    private void setViewValue(boolean isRefreshData, TryGameInfoVo.DataBean value) {
        if (value == null) {
            showErrorTag2();
            return;
        }
        if (value.getGameinfo() != null) {
            GameInfoVo gameInfoVo = value.getGameinfo();
            if (downloaderHelper == null) {
                downloaderHelper = new DownloaderHelper(_mActivity, mDownloadProgress, mTvDownload, gameInfoVo);
            } else {
                downloaderHelper.refreshGameInfoVo(gameInfoVo);
            }
            String gameicon = value.getGameinfo().getGameicon();
            String gamename = value.getGameinfo().getGamename();
            int gameid = value.getGameinfo().getGameid();
            int game_type = value.getGameinfo().getGame_type();

            GlideUtils.loadRoundImage(_mActivity, gameicon, mIvTryGameIcon);
            mTvTryGameName.setText(gamename);

            mFlDownload.setOnClickListener(v -> clickDownload());
            mDownloadProgress.setOnClickListener(v -> clickDownload());
            mLlItem.setOnClickListener(v -> goGameDetail(gameid, game_type));

            downloaderHelper.setGameDownloadStatus();
        }

        int competition_type = 0;
        if (value.getTrial_info() != null) {
            TryGameInfoVo.TrialInfoVo trialInfoVo = value.getTrial_info();
            initActionBackBarAndTitle(trialInfoVo.getTitle());

            competition_type = trialInfoVo.getCompetition_type();

            StringBuilder infoSb = new StringBuilder();
            infoSb.append("试玩时间：")
                    .append(trialInfoVo.getBegintime())
                    .append("--")
                    .append(trialInfoVo.getEndtime())
                    .append("\n")
                    .append("试玩区服：")
                    .append(trialInfoVo.getServer_desc());
            mTvTryGameInfo.setText(infoSb.toString());

            StringBuilder sb = new StringBuilder();
            String total = String.valueOf(trialInfoVo.getTotal());
            float realValue = trialInfoVo.getTotal() / 100f;

            String strRealValue = String.valueOf(realValue);
            if (strRealValue.endsWith(".0")) {
                strRealValue = strRealValue.substring(0, strRealValue.length() - 2);
            } else if (strRealValue.endsWith(".00")) {
                strRealValue = strRealValue.substring(0, strRealValue.length() - 3);
            }

            sb.append("最高奖")
                    .append(total)
                    .append("积分/每人")
                    .append("，价值")
                    .append(strRealValue)
                    .append("元。");
            SpannableString ss = new SpannableString(sb);
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_ff8f19)),
                    2, 3 + total.length() + 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mTvTryGameIntegral.setText(ss);
            mTvTryGameTotalIntegral.setText("已累计发放" + CommonUtils.formatAmount(trialInfoVo.getGot_total()) + "积分奖励");

            if (mTimeLineAdapter != null) {
                List<CharSequence> list = new ArrayList<>();
                list.add("第一步：下载并创建游戏角色");
                list.add("第二步：达成任务，立即回到本页领奖");
                if (trialInfoVo.getUser_status() == 0) {
                    SpannableString ssTimeLine = new SpannableString("我的状态：未参与");
                    list.add(ssTimeLine);
                } else if (trialInfoVo.getUser_status() == 1) {
                    SpannableString ssTimeLine = new SpannableString("我的状态：参与中");
                    ssTimeLine.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_ff4949)), 5, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    list.add(ssTimeLine);
                }

                mTimeLineAdapter.clear();
                mTimeLineAdapter.addAllData(list);
                mTimeLineAdapter.notifyDataSetChanged();
            }
        }

        if (!isRefreshData) {
            fragmentList.clear();
            List<String> tabList = new ArrayList<>();

            int lastLevel = 0;
            int lastAttach = 0;
            //任务奖励
            if (value.getTask_list() != null) {
                fragmentList.add(RewardListFragment.newInstance(value.getTask_list()));
                tabList.add("任务奖励");

                //获取等级最后一挡条件
                List<TryGameInfoVo.TaskListVo.DataBean> levelList = value.getTask_list().getLevel();
                if (levelList != null && !levelList.isEmpty()) {
                    lastLevel = levelList.get(levelList.size() - 1).getTask_val();
                }
                //获取战力最后一挡条件
                List<TryGameInfoVo.TaskListVo.DataBean> attachList = value.getTask_list().getAttach();
                if (attachList != null && !attachList.isEmpty()) {
                    lastAttach = attachList.get(attachList.size() - 1).getTask_val();
                }
            }
            //冲级赛
            if (value.getCompetition_list() != null) {
                ArrayList dataList = new ArrayList<>();
                dataList.addAll(value.getCompetition_list());

                StringBuilder sb = new StringBuilder();
                int count = value.getCompetition_list().size();
                sb.append("活动期间，前")
                        .append(String.valueOf(count))
                        .append("名达到");
                if (competition_type == 1) {
                    tabList.add("等级冲级赛");
                    sb.append(String.valueOf(lastLevel))
                            .append("级");
                } else if (competition_type == 2) {
                    tabList.add("战力冲级赛");
                    sb.append(String.valueOf(lastAttach))
                            .append("战力");
                }
                sb.append("的玩家，可额外获得冲级赛奖励，领取任务奖励时自动获得。");

                fragmentList.add(ImpactListFragment.newInstance(sb.toString(), dataList));
            }
            //排行榜
            if (value.getRanking_list() != null) {
                fragmentList.add(RankingListFragment.newInstance(value.getRanking_list()));
                tabList.add("排行榜");
            }
            pageTitle = new String[tabList.size()];
            for (int i = 0; i < tabList.size(); i++) {
                pageTitle[i] = tabList.get(i);
            }
            setAdapter();
        } else {
            try {
                RewardListFragment rewardListFragment = (RewardListFragment) fragmentList.get(0);
                rewardListFragment.refreshData(value.getTask_list());

                ArrayList dataList = new ArrayList<>();
                if (value.getCompetition_list() != null) {
                    dataList.addAll(value.getCompetition_list());
                }

                ImpactListFragment impactListFragment = (ImpactListFragment) fragmentList.get(1);
                impactListFragment.refreshData(dataList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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
                    setSwipeRefreshing(false);
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


    public void refreshData() {
        getTryGameDetailData(true);
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        refreshData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (downloaderHelper != null) {
                downloaderHelper.setGameDownloadStatus();
            }
        }
    }


    /******************************************************************************/

    /**
     * 点击下载游戏
     */
    private void clickDownload() {
        if (checkLogin()) {
            if (downloaderHelper != null) {
                downloaderHelper.download();
            }
        }
    }
}
