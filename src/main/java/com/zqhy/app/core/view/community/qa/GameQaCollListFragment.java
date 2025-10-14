package com.zqhy.app.core.view.community.qa;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.collapsing.BaseCollapsingListFragment;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.core.data.model.community.qa.QAListVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.view.community.qa.list.GameQaChildListFragment;
import com.zqhy.app.core.vm.community.qa.QaViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.listener.MyAppBarStateChangeListener;

/**
 * @author Administrator
 */
public class GameQaCollListFragment extends BaseCollapsingListFragment<QaViewModel> {

    private static final int ACTION_GAME_QA_QUESTION_EDIT = 0x466;

    public static GameQaCollListFragment newInstance(int gameid) {
        GameQaCollListFragment fragment = new GameQaCollListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("gameid", gameid);
        fragment.setArguments(bundle);
        return fragment;
    }

    private LinearLayout mLlCollapsingLayout;
    private ImageView mIvGameIcon;
    private TextView mTvGameName;
    private TextView mTvGamePlayCount;
    private TextView mTvQaCount;
    private TextView mTvAboutGameQa;

    @NonNull
    @Override
    protected View getCollapsingView() {
        View mCollapsingView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_collapsing_game_qa, null);

        mLlCollapsingLayout = mCollapsingView.findViewById(R.id.ll_collapsing_layout);
        mIvGameIcon = mCollapsingView.findViewById(R.id.iv_game_icon);
        mTvGameName = mCollapsingView.findViewById(R.id.tv_game_name);
        mTvGamePlayCount = mCollapsingView.findViewById(R.id.tv_game_play_count);
        mTvQaCount = mCollapsingView.findViewById(R.id.tv_qa_count);
        mTvAboutGameQa = mCollapsingView.findViewById(R.id.tv_about_game_qa);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, (int) (48 * density) + ScreenUtil.getStatusBarHeight(_mActivity), 0, 0);
        mLlCollapsingLayout.setLayoutParams(params);

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#14FF8F19"));
        gd.setCornerRadius(48 * density);
        mTvAboutGameQa.setBackground(gd);
        mTvAboutGameQa.setOnClickListener(v -> {
            showAnswerRuleDialog();
        });

        return mCollapsingView;
    }

    private LinearLayout mLlCollapsingTitle;
    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvSubTitle;
    private ImageView mIvMessage;
    private TextView mTitleBottomLine;
    private TextView mTvUserQa;
    private RelativeLayout mFlUserQa;

    @NonNull
    @Override
    protected View getToolBarView() {
        View mToolBarView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_collapsing_game_qa_appbar, null);

        mLlCollapsingTitle = mToolBarView.findViewById(R.id.ll_collapsing_title);
        mIvBack = mToolBarView.findViewById(R.id.iv_back);
        mTvTitle = mToolBarView.findViewById(R.id.tv_title);
        mTvSubTitle = mToolBarView.findViewById(R.id.tv_sub_title);
        mIvMessage = mToolBarView.findViewById(R.id.iv_message);
        mTitleBottomLine = mToolBarView.findViewById(R.id.title_bottom_line);
        mTvUserQa = mToolBarView.findViewById(R.id.tv_user_qa);
        mFlUserQa = mToolBarView.findViewById(R.id.fl_user_qa);

        mIvBack.setOnClickListener(v -> pop());
        mTvTitle.setText("玩家问答区");
        mIvMessage.setOnClickListener(v -> goMessageCenter());
        mTvUserQa.setOnClickListener(v -> {
            if (checkLogin()) {
                start(UserQaCollapsingCenterFragment.newInstance());
            }
        });

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(4 * density);
        gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        int[] colors = new int[]{ContextCompat.getColor(_mActivity, R.color.color_c8c8c8), ContextCompat.getColor(_mActivity, R.color.color_757575)};
        gd.setColors(colors);
        mFlUserQa.setBackground(gd);

        GradientDrawable gd2 = new GradientDrawable();
        gd2.setCornerRadius(4 * density);
        gd2.setColor(ContextCompat.getColor(_mActivity, R.color.white));
        mTvUserQa.setBackground(gd2);

        return mToolBarView;
    }

    @Override
    protected void onAppBarLayoutOffsetChanged(int i, int resColor) {
        super.onAppBarLayoutOffsetChanged(i, resColor);
        mLlCollapsingTitle.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.white));
    }

    private GameQaChildListFragment qaChildListFragment;

    @NonNull
    @Override
    protected BaseListFragment getListView() {
        qaChildListFragment = new GameQaChildListFragment();
        return qaChildListFragment;
    }

    @Override
    protected void onAppBarLayoutStateChanged(MyAppBarStateChangeListener.State state) {
        super.onAppBarLayoutStateChanged(state);
        switch (state) {
            case EXPANDED:
                mTvSubTitle.setVisibility(View.GONE);
                mTitleBottomLine.setVisibility(View.GONE);
                break;
            case COLLAPSED:
                mTvSubTitle.setVisibility(View.VISIBLE);
                mTitleBottomLine.setVisibility(View.VISIBLE);
                break;
            case IDLE:
                mTvSubTitle.setVisibility(View.GONE);
                mTitleBottomLine.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }


    @Override
    protected View getListLayoutView() {
        return null;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        if (UserInfoModel.getInstance().isLogined()) {
            initData();
        }
    }

    private int gameid;

    private FrameLayout mFlContainer;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gameid = getArguments().getInt("gameid");
        }
        super.initView(state);
        mFlContainer = findViewById(R.id.fl_container);
        setSwipeRefresh(() -> initData());
        addFloatLayout();
    }

    @Override
    protected boolean isCanSwipeRefresh() {
        return true;
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GameQaChildListFragment.ACTION_GAME_QA_DETAIL:
                case ACTION_GAME_QA_QUESTION_EDIT:
                    initData();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onEvent(EventCenter eventCenter) {
        super.onEvent(eventCenter);
        if (eventCenter.getEventCode() == EventConfig.POST_NEW_GAME_QUESTION_EVENT_CODE) {
            initData();
        }
    }

    private RelativeLayout mLlContainer;
    private TextView mTvAskQuestions;

    private void addFloatLayout() {
        View mFloatLayoutView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_collapsing_game_qa_float, null);
        mLlContainer = mFloatLayoutView.findViewById(R.id.ll_container);
        mTvAskQuestions = mFloatLayoutView.findViewById(R.id.tv_ask_questions);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(48 * density);
        gd.setColor(Color.parseColor("#DA333333"));
        mLlContainer.setBackground(gd);

        mTvAskQuestions.setOnClickListener(v -> {
            if (checkLogin()) {
                if (!isCanAskQuestion) {
                    Toaster.show( "让大神休息会儿，稍等再来问问呢");
                    return;
                }
                //2019.06.24 跳转编辑页面
                start(GameQuestionEditFragment.newInstance(gameid, gamename, game_played_count));
            }
        });

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        params.setMargins(0, 0, 0, (int) (36 * density));
        mFloatLayoutView.setLayoutParams(params);

        if (mFlContainer != null) {
            mFlContainer.addView(mFloatLayoutView, 1);
        }
    }

    private int game_played_count;
    private String gamename;
    private boolean isCanAskQuestion = false;

    private void setGameViewInfo(GameInfoVo gameInfoVo) {
        isCanAskQuestion = gameInfoVo.getCan_question() == 1;

        GlideUtils.loadNormalImage(_mActivity, gameInfoVo.getGameicon(), mIvGameIcon);
        mTvGameName.setText(gameInfoVo.getGamename());
        gamename = gameInfoVo.getGamename();
        mTvSubTitle.setText(gameInfoVo.getGamename());
        game_played_count = gameInfoVo.getPlay_count();

        //game play count
        {
            String countStr = CommonUtils.formatNumber(gameInfoVo.getPlay_count());
            SpannableString ssPlayCount = new SpannableString("有" + countStr + "人玩过此游戏");

            int startIndex = 1;
            int endIndex = startIndex + countStr.length();

            ssPlayCount.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_ff5400)),
                    startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mTvGamePlayCount.setText(ssPlayCount);
            mTvGamePlayCount.setVisibility(View.GONE);
        }

        //q&a count
        {
            String qCount = CommonUtils.formatNumber(gameInfoVo.getQuestion_count());
            String aCount = CommonUtils.formatNumber(gameInfoVo.getAnswer_count());

            StringBuilder sb = new StringBuilder();
            sb.append("共");

            int qStartIndex = sb.length();
            int qEndIndex = qStartIndex + qCount.length();

            sb.append(qCount);
            sb.append("条提问，");

            int aStartIndex = sb.length();
            int aEndIndex = aStartIndex + aCount.length();

            sb.append(aCount);
            sb.append("个回答");

            SpannableString ssQaCount = new SpannableString(sb.toString());
            ssQaCount.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_232323)),
                    qStartIndex, qEndIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            ssQaCount.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_232323)),
                    aStartIndex, aEndIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            mTvQaCount.setText(ssQaCount);
        }
    }


    public void initData() {
        if (qaChildListFragment != null) {
            qaChildListFragment.initData();
        }
    }

    public void initData(int pageCount) {
        int page = 1;
        getQaListData(page, pageCount);
    }

    public void getQaListData(int page, int pageCount) {
        if (mViewModel != null) {
            mViewModel.getQaListData(gameid, page, pageCount, new OnBaseCallback<QAListVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    setSwipeRefreshing(false);
                    if (qaChildListFragment != null) {
                        qaChildListFragment.refreshAndLoadMoreComplete();
                    }
                }

                @Override
                public void onSuccess(QAListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            GameInfoVo gameInfoVo = data.getData();
                            if (gameInfoVo != null) {
                                if (page == 1) {
                                    setGameViewInfo(gameInfoVo);
                                }
                                if (qaChildListFragment != null) {
                                    qaChildListFragment.setListData(gameInfoVo);
                                }
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
