package com.zqhy.app.core.view.community.qa;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.collapsing.BaseCollapsingViewPagerFragment;
import com.zqhy.app.core.data.model.community.CommunityInfoVo;
import com.zqhy.app.core.data.model.community.qa.UserQaCenterInfoVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.vm.community.qa.QaViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.imageview.ClipRoundImageView;
import com.zqhy.app.widget.listener.MyAppBarStateChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class UserQaCenterFragment extends BaseCollapsingViewPagerFragment<QaViewModel> {

    public static UserQaCenterFragment newInstance(int user_id, String user_nickname) {
        UserQaCenterFragment fragment = new UserQaCenterFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", user_id);
        bundle.putString("user_nickname", user_nickname);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected String[] createPageTitle() {
        if (UserInfoModel.getInstance().checkLoginUser(user_id)) {
            return new String[]{"我来回答", "我的提问"};
        } else {
            return new String[]{"TA来回答", "TA的提问"};
        }
    }

    private UserQaListFragment qaListFragment1, qaListFragment2;

    @Override
    protected List<Fragment> createFragments() {
        List<Fragment> fragmentList = new ArrayList<>();
        qaListFragment1 = UserQaListFragment.newInstance(1, user_id);
        fragmentList.add(qaListFragment1);
        qaListFragment2 = UserQaListFragment.newInstance(2, user_id);
        fragmentList.add(qaListFragment2);
        return fragmentList;
    }

    private ImageView mIvUserPortrait;
    private ClipRoundImageView mProfileImage;
    private FrameLayout mFlUserLevel;
    private ImageView mIvUserLevel;
    private TextView mTvUserLevel;
    private TextView mTvUserNickname;
    private TextView mTvUserPraiseCount;
    private TextView mTvUserCommentsCount;
    private TextView mTvQaCount;
    private LinearLayout mLlLayoutCommentCount;

    @NonNull
    @Override
    protected View getCollapsingView() {
        View mCollapsingView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_collapsing_user_comment_qa_center, null);
        mIvUserPortrait = mCollapsingView.findViewById(R.id.iv_user_portrait);
        mProfileImage = mCollapsingView.findViewById(R.id.profile_image);
        mFlUserLevel = mCollapsingView.findViewById(R.id.fl_user_level);
        mIvUserLevel = mCollapsingView.findViewById(R.id.iv_user_level);
        mTvUserLevel = mCollapsingView.findViewById(R.id.tv_user_level);
        mTvUserNickname = mCollapsingView.findViewById(R.id.tv_user_nickname);
        mTvUserPraiseCount = mCollapsingView.findViewById(R.id.tv_user_praise_count);
        mTvUserCommentsCount = mCollapsingView.findViewById(R.id.tv_user_comments_count);
        mTvQaCount = mCollapsingView.findViewById(R.id.tv_qa_count);
        mLlLayoutCommentCount = mCollapsingView.findViewById(R.id.ll_layout_comment_count);

        mLlLayoutCommentCount.setVisibility(View.GONE);
        return mCollapsingView;
    }

    private LinearLayout mLlCollapsingTitle;

    @NonNull
    @Override
    protected View getToolBarView() {
        View mToolBarView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_common_collapsing_title, null);
        View mTitleBottomLine = mToolBarView.findViewById(R.id.title_bottom_line);
        mLlCollapsingTitle = mToolBarView.findViewById(R.id.ll_collapsing_title);
        FrameLayout mFlTitleRight = mToolBarView.findViewById(R.id.fl_title_right);
        View titleRightView = getTitleRightView();
        if (titleRightView != null) {
            mFlTitleRight.addView(titleRightView);
        }
        mTitleBottomLine.setVisibility(View.GONE);
        return mToolBarView;
    }

    @NonNull
    @Override
    protected BaseListFragment getListView() {
        return null;
    }

    private View getTitleRightView() {
        ImageView mIvQuestion = new ImageView(_mActivity);
        mIvQuestion.setImageResource(R.mipmap.ic_question);
        int padding = (int) (6 * density);
        mIvQuestion.setPadding(padding, padding, padding, padding);

        mIvQuestion.setOnClickListener(v -> showAnswerRuleDialog());

        //2019.03.15 暂时屏蔽
        mIvQuestion.setVisibility(View.GONE);

        return mIvQuestion;
    }

    @Override
    public Object getStateEventKey() {
        return null;
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
        setStatusBar(0x00cccccc);
        setTitleText("");
    }

    private void setCollapsedTitleView() {
        mLlCollapsingTitle.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.white));
        setActionBackBar(trecyclerview.com.mvvm.R.mipmap.ic_actionbar_back);
        setStatusBar(0xffcccccc);
        setTitleColor(ContextCompat.getColor(_mActivity, R.color.color_1b1b1b));
        setTitleText(user_nickname);
    }

    private int user_id;
    private String user_nickname;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            user_id = getArguments().getInt("user_id");
            user_nickname = getArguments().getString("user_nickname");
        }
        super.initView(state);
        initActionBackBarAndTitle("");
        setIndicatorSize();
        setAdapter();
        initData();
    }

    private void setIndicatorSize() {
        if (mDynamicPagerIndicator != null) {
            mDynamicPagerIndicator.mTabNormalTextSize = 14 * density;
            mDynamicPagerIndicator.mTabSelectedTextSize = 14 * density;
            mDynamicPagerIndicator.updateIndicator();
        }
    }

    private void setViewValue(UserQaCenterInfoVo.DataBean data) {
        CommunityInfoVo communityInfoVo = data.getCommunity_info();
        if (communityInfoVo != null) {
            user_nickname = communityInfoVo.getUser_nickname();
            mTvUserNickname.setText(communityInfoVo.getUser_nickname());
            GlideUtils.loadCircleImage(_mActivity, communityInfoVo.getUser_icon(), mProfileImage, R.mipmap.ic_user_login, 3, R.color.white);
            mProfileImage.setOnClickListener(v -> {
                ShowOnePicDetail(1, communityInfoVo.getUser_icon());
            });
            UserInfoModel.setUserLevel(communityInfoVo.getUser_level(), mIvUserLevel, mTvUserLevel);
            mTvQaCount.setText(Html.fromHtml(_mActivity.getResources().getString(R.string.string_user_question_answer_count, String.valueOf(communityInfoVo.getQuestion_verify_count()), String.valueOf(communityInfoVo.getAnswer_verify_count()))));
        }
        setMyCanAnswerGameList(data.getCan_answer_game_list(), data.getMore_answer_game_list(),data.getUser_answer_question_list());
    }

    private void setMyCanAnswerGameList(List<GameInfoVo> gameInfoVos, int more_answer_game_list, List<UserQaCenterInfoVo.QaCenterQuestionVo> user_answer_question_list) {
        if (gameInfoVos != null && qaListFragment1 != null) {
            int user_answer_question_list_size = 0;
            if(user_answer_question_list != null){
                user_answer_question_list_size = user_answer_question_list.size();
            }
            qaListFragment1.setCanAnswerGameList(gameInfoVos, more_answer_game_list,user_answer_question_list_size);
        }
    }


    private void initData() {
        getUserQaCenterData();
    }


    private void getUserQaCenterData() {
        if (mViewModel != null) {
            mViewModel.getUserQaCenterData(user_id, new OnBaseCallback<UserQaCenterInfoVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }

                @Override
                public void onSuccess(UserQaCenterInfoVo data) {
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
}
