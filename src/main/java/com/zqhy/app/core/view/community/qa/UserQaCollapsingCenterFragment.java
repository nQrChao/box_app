package com.zqhy.app.core.view.community.qa;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.base.collapsing.BaseCollapsingListFragment;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.core.data.model.community.CommunityInfoVo;
import com.zqhy.app.core.data.model.community.qa.UserQaCanAnswerInfoVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.view.community.qa.holder.QaCanAnswerItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.community.qa.QaViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.imageview.ClipRoundImageView;
import com.zqhy.app.widget.listener.MyAppBarStateChangeListener;

import java.util.List;

/**
 * @author Administrator
 */
public class UserQaCollapsingCenterFragment extends BaseCollapsingListFragment<QaViewModel> {


    public static UserQaCollapsingCenterFragment newInstance() {
        UserQaCollapsingCenterFragment fragment = new UserQaCollapsingCenterFragment();
        Bundle bundle = new Bundle();
        if (UserInfoModel.getInstance().isLogined()) {
            int user_id = UserInfoModel.getInstance().getUserInfo().getUid();
            String user_nickname = UserInfoModel.getInstance().getUserInfo().getUser_nickname();
            bundle.putInt("user_id", user_id);
            bundle.putString("user_nickname", user_nickname);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    public static UserQaCollapsingCenterFragment newInstance(int user_id, String user_nickname) {
        UserQaCollapsingCenterFragment fragment = new UserQaCollapsingCenterFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", user_id);
        bundle.putString("user_nickname", user_nickname);
        fragment.setArguments(bundle);
        return fragment;
    }

    private LinearLayout mLlCommunity;
    private ClipRoundImageView mProfileImage;
    private TextView mTvUserNickname;
    private FrameLayout mFlUserLevel;
    private ImageView mIvUserLevel;
    private TextView mTvUserLevel;
    private LinearLayout mLlUserQuestion;
    private TextView mTvUserQuestionCount;
    private LinearLayout mLlUserAnswer;
    private TextView mTvUserAnswerCount;
    private TextView mTvSubTitle;
    private TextView mTvUserQuestion;
    private TextView mTvUserAnswer;

    @NonNull
    @Override
    protected View getCollapsingView() {
        View mCollapsingView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_collapsing_user_qa_center, null);

        mLlCommunity = mCollapsingView.findViewById(R.id.ll_community);
        mProfileImage = mCollapsingView.findViewById(R.id.profile_image);
        mTvUserNickname = mCollapsingView.findViewById(R.id.tv_user_nickname);
        mFlUserLevel = mCollapsingView.findViewById(R.id.fl_user_level);
        mIvUserLevel = mCollapsingView.findViewById(R.id.iv_user_level);
        mTvUserLevel = mCollapsingView.findViewById(R.id.tv_user_level);
        mLlUserQuestion = mCollapsingView.findViewById(R.id.ll_user_question);
        mTvUserQuestionCount = mCollapsingView.findViewById(R.id.tv_user_question_count);
        mLlUserAnswer = mCollapsingView.findViewById(R.id.ll_user_answer);
        mTvUserAnswerCount = mCollapsingView.findViewById(R.id.tv_user_answer_count);
        mTvSubTitle = mCollapsingView.findViewById(R.id.tv_sub_title);

        mTvUserQuestion = mCollapsingView.findViewById(R.id.tv_user_question);
        mTvUserAnswer = mCollapsingView.findViewById(R.id.tv_user_answer);

        setTextUser();

        mLlUserQuestion.setOnClickListener(v -> {
            start(UserQaCollapsingListFragment.newInstance(2, user_id));
        });
        mLlUserAnswer.setOnClickListener(v -> {
            start(UserQaCollapsingListFragment.newInstance(1, user_id));
        });

        return mCollapsingView;
    }

    private void setTextUser() {
        if (isLoginedUser) {
            mTvUserQuestion.setText("我的提问");
            mTvUserAnswer.setText("我的回答");
        } else {
            mTvUserQuestion.setText("TA的提问");
            mTvUserAnswer.setText("TA的回答");
        }
    }


    private LinearLayout mLlCollapsingTitle;
    private ImageView titleRightView;

    @NonNull
    @Override
    protected View getToolBarView() {
        View mToolBarView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_common_collapsing_title, null);
        View mTitleBottomLine = mToolBarView.findViewById(R.id.title_bottom_line);
        mLlCollapsingTitle = mToolBarView.findViewById(R.id.ll_collapsing_title);
        FrameLayout mFlTitleRight = mToolBarView.findViewById(R.id.fl_title_right);
//        titleRightView = getTitleRightView();
//        if (titleRightView != null) {
//            mFlTitleRight.addView(titleRightView);
//        }
        mTitleBottomLine.setVisibility(View.GONE);
        return mToolBarView;
    }

    private ImageView getTitleRightView() {
        ImageView mIvQuestion = new ImageView(_mActivity);
        mIvQuestion.setImageResource(R.mipmap.ic_message_common_white);
        int padding = (int) (6 * density);
        mIvQuestion.setPadding(padding, padding, padding, padding);

        mIvQuestion.setOnClickListener(v -> goMessageCenter());
        return mIvQuestion;
    }

    @NonNull
    @Override
    protected BaseListFragment getListView() {
        return null;
    }

    @Override
    protected boolean isSetListFragment() {
        return false;
    }

    private XRecyclerView mXRecyclerView;

    @Override
    protected View getListLayoutView() {
        mXRecyclerView = new XRecyclerView(_mActivity);
        return mXRecyclerView;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "我的问答页";
    }

    private int user_id;
    private String user_nickname;
    private boolean isLoginedUser = false;

    @Override
    protected boolean isCanSwipeRefresh() {
        return true;
    }

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            user_id = getArguments().getInt("user_id");
            user_nickname = getArguments().getString("user_nickname");
            isLoginedUser = UserInfoModel.getInstance().checkLoginUser(user_id);
        }
        super.initView(state);
        initActionBackBarAndTitle("");
        initList();
        setSwipeRefresh(() -> {
            initData();
        });
        initData();
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        if (UserInfoModel.getInstance().isLogined()) {
            initData();
            setTextUser();
        }
    }

    private BaseRecyclerAdapter mAdapter;

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        mXRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(UserQaCanAnswerInfoVo.AnswerInviteInfoVo.class, new QaCanAnswerItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, this);

        mXRecyclerView.setAdapter(mAdapter);
        mXRecyclerView.setLoadingMoreEnabled(false);
        mXRecyclerView.setPullRefreshEnabled(false);
    }

    @Override
    public void onEvent(EventCenter eventCenter) {
        super.onEvent(eventCenter);
        if (EventConfig.POST_NEW_GAME_ANSWER_EVENT_CODE == eventCenter.getEventCode()) {
            initData();
        }
    }

    @Override
    protected void onAppBarLayoutOffsetChanged(int i, int resColor) {
        super.onAppBarLayoutOffsetChanged(i, resColor);
        mLlCollapsingTitle.setBackgroundColor(resColor);
    }

    @Override
    protected void onAppBarLayoutStateChanged(MyAppBarStateChangeListener.State state) {
        super.onAppBarLayoutStateChanged(state);
        switch (state) {
            case EXPANDED:
                setExpandedTitleView();
                break;
            case COLLAPSED:
                setCollapsedTitleView();
                break;
            case IDLE:
                setTitleText("");
                break;
            default:
                break;
        }
    }


    private void setExpandedTitleView() {
        mLlCollapsingTitle.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.transparent));
        setActionBackBar(R.mipmap.ic_actionbar_back_white);
        if (titleRightView != null) {
            titleRightView.setImageResource(R.mipmap.ic_message_common_white);
        }
        setStatusBar(0x00cccccc);
        setTitleText("");
    }

    private void setCollapsedTitleView() {
        mLlCollapsingTitle.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.white));
        setActionBackBar(trecyclerview.com.mvvm.R.mipmap.ic_actionbar_back);
        if (titleRightView != null) {
            titleRightView.setImageResource(R.mipmap.ic_message_common);
        }
        setStatusBar(0xffcccccc);
        setTitleColor(ContextCompat.getColor(_mActivity, R.color.color_1b1b1b));
        setTitleText(user_nickname);
    }

    private void setViewValue(UserQaCanAnswerInfoVo.DataBean data) {
        CommunityInfoVo communityInfoVo = data.getCommunity_info();
        if (communityInfoVo != null) {
            user_nickname = communityInfoVo.getUser_nickname();
            mTvUserNickname.setText(communityInfoVo.getUser_nickname());
            GlideUtils.loadCircleImage(_mActivity, communityInfoVo.getUser_icon(), mProfileImage, R.mipmap.ic_user_login_new_sign, 3, R.color.white);
            mProfileImage.setOnClickListener(v -> {
                ShowOnePicDetail(1, communityInfoVo.getUser_icon());
            });
            UserInfoModel.setUserLevel(communityInfoVo.getUser_level(), mIvUserLevel, mTvUserLevel);

            mTvUserQuestionCount.setText(String.valueOf(communityInfoVo.getQuestion_verify_count()));
            mTvUserAnswerCount.setText(String.valueOf(communityInfoVo.getAnswer_verify_count()));
        }
        List<UserQaCanAnswerInfoVo.AnswerInviteInfoVo> gameInfoVoList = data.getAnswer_invite_list();

        StringBuilder sb = new StringBuilder();

        mAdapter.clear();
        mAdapter.notifyDataSetChanged();
        if (gameInfoVoList != null && !gameInfoVoList.isEmpty()) {
            mTvSubTitle.setText("邀你回答，单日最高奖100积分哦~");
            sb.append("邀你回答");
            mAdapter.addAllData(gameInfoVoList);
        } else {
            sb.append("回答有奖");
            //show empty
            mAdapter.addData(new EmptyDataVo(R.mipmap.img_empty_data_user_qa)
                    .setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT)
                    .setEmptyWord("邀请回答为系统匹配问题自动筛选符合的资深玩家进行回答！建议多多游戏，将有很大几率被邀请回答哟~")
                    .setEmptyWordColor(R.color.color_9b9b9b));
        }
        mAdapter.notifyDataSetChanged();

        sb.append("，单日最高奖");
        int startIndex = sb.toString().length();
        sb.append("100积分");
        int endIndex = sb.toString().length();
        sb.append("哦~");

        SpannableString ss = new SpannableString(sb.toString());
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_ff5400)),
                startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvSubTitle.setText(ss);
    }

    private void initData() {
        getUserCanAnswerQuestionListData();
    }

    private void getUserCanAnswerQuestionListData() {
        if (mViewModel != null) {
            mViewModel.getUserCanAnswerQuestionListData(user_id, new OnBaseCallback<UserQaCanAnswerInfoVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(UserQaCanAnswerInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                setViewValue(data.getData());
                            }
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void refreshAndLoadMoreComplete() {
        setSwipeRefreshing(false);
        if (mXRecyclerView != null) {
            mXRecyclerView.refreshComplete();
            mXRecyclerView.loadMoreComplete();
        }
    }

}
