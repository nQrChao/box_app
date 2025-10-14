package com.zqhy.app.core.view.community.comment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.collapsing.BaseCollapsingListFragment;
import com.zqhy.app.core.data.model.community.CommunityInfoVo;
import com.zqhy.app.core.data.model.community.comment.UserCommentInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.vm.community.comment.CommentViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.imageview.ClipRoundImageView;
import com.zqhy.app.widget.listener.MyAppBarStateChangeListener;

/**
 * @author Administrator
 */
public class UserCommentCenterFragment extends BaseCollapsingListFragment<CommentViewModel> {


    public static UserCommentCenterFragment newInstance(int user_id, String user_nickname) {
        UserCommentCenterFragment fragment = new UserCommentCenterFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", user_id);
        bundle.putString("user_nickname", user_nickname);
        fragment.setArguments(bundle);
        return fragment;
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
        mTvQaCount.setVisibility(View.GONE);

        return mCollapsingView;
    }


    private LinearLayout mLlCollapsingTitle;

    @NonNull
    @Override
    protected View getToolBarView() {
        View mToolBarView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_common_collapsing_title, null);
        View mTitleBottomLine = mToolBarView.findViewById(R.id.title_bottom_line);
        mLlCollapsingTitle = mToolBarView.findViewById(R.id.ll_collapsing_title);

        mTitleBottomLine.setVisibility(View.GONE);
        return mToolBarView;
    }

    @NonNull
    @Override
    protected BaseListFragment getListView() {
        return UserCommentListFragment.newInstance(user_id);
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
    protected String getUmengPageName() {
        return "我的点评页";
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
        showSuccess();
        initData();
    }

    private void setViewValue(CommunityInfoVo community_info) {
        //设置用户信息
        GlideUtils.loadCircleImage(_mActivity, community_info.getUser_icon(), mProfileImage, R.mipmap.ic_user_login_new_sign, 3, R.color.white);
        user_nickname = community_info.getUser_nickname();
        mTvUserNickname.setText(user_nickname);
        mFlUserLevel.setVisibility(View.VISIBLE);
        UserInfoModel.setUserLevel(community_info.getUser_level(), mIvUserLevel, mTvUserLevel);

        mTvUserPraiseCount.setText(_mActivity.getResources().getString(R.string.string_user_praise_count, String.valueOf(community_info.getBe_praised_count())));
        mTvUserCommentsCount.setText(_mActivity.getResources().getString(R.string.string_user_comments_count, String.valueOf(community_info.getComment_count())));

        mProfileImage.setOnClickListener(v -> {
            ShowOnePicDetail(1, community_info.getUser_icon());
        });
    }

    private int page = 1, pageCount = 12;

    private void initData() {
        if (mViewModel != null) {
            mViewModel.getUserCommentData(user_id, page, pageCount, new OnBaseCallback<UserCommentInfoVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }

                @Override
                public void onSuccess(UserCommentInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null && data.getData().getCommunity_info() != null) {
                                setViewValue(data.getData().getCommunity_info());
                            }
                        }
                    }
                }
            });
        }
    }

}
