package com.zqhy.app.core.view.community.user;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.box.other.hjq.titlebar.OnTitleBarListener;
import com.box.other.hjq.titlebar.TitleBar;
import com.box.other.hjq.toast.Toaster;
import com.donkingliang.imageselector.PreviewActivity;
import com.donkingliang.imageselector.entry.Image;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.community.CommunityInfoVo;
import com.zqhy.app.core.data.model.community.CommunityUserListV2Vo;
import com.zqhy.app.core.data.model.community.CommunityUserVo;
import com.zqhy.app.core.data.model.community.comment.CommentInfoVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.core.tool.utilcode.SizeUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.community.comment.CommentDetailFragment;
import com.zqhy.app.core.view.community.comment.UserCommentCenterFragment;
import com.zqhy.app.core.view.community.qa.UserQaCollapsingCenterFragment;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.core.view.main.holder.GameNoMoreItemHolder;
import com.zqhy.app.core.view.user.UserInfoFragment;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.community.CommunityViewModel;
import com.zqhy.app.glide.GlideCircleTransform;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.imageview.ClipRoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class CommunityUserFragment extends BaseFragment<CommunityViewModel> implements View.OnClickListener {

    public final static int ACTION_USER_INFO = 0x8887;

    public static CommunityUserFragment newInstance(int uid) {
        CommunityUserFragment fragment = new CommunityUserFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("uid", uid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_GAME_DETAIL_STATE;
    }

    @Override
    protected String getStateEventTag() {
        return String.valueOf(uid);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_community_user;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    protected String getUmengPageName() {
        return "我的主页";
    }

    private int     uid;
    private boolean isLoginedUser = false;
    private int page = 1;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            uid = getArguments().getInt("uid");
            isLoginedUser = UserInfoModel.getInstance().checkLoginUser(uid);
        }
        super.initView(state);
        initActionBackBarAndTitle("主页");
        bindViews();
        initData();
    }


    private LinearLayout mLlContentLayout;
    private TitleBar mTitleBar_Layout;
    private ClipRoundImageView mIvUserPortrait;
    private TextView mTvUserName;
    private LinearLayout mLlGameFootprint;
    private LinearLayout mLlCommunityComment;
    private LinearLayout mLlCommunityQa;
    private LinearLayout mLlCommunityLike;
    private TextView mTvCommentCount;
    private TextView mTvQaCount;
    private TextView mTvLikeCount;
    private FrameLayout mFlUserLevel;
    private ImageView mIvUserLevel;
    private TextView mTvUserLevel;
    private ImageView mIvUserLevelRule;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView mIvVipIcon;

    private TextView            mTvCount;
    private XRecyclerView       mBaseRecyclerView;
    private BaseRecyclerAdapter baseRecyclerAdapter;

    private void bindViews() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mBaseRecyclerView =  findViewById(R.id.recycler_view);
        mLlContentLayout = findViewById(R.id.ll_content_layout);
        mTitleBar_Layout = findViewById(R.id.titleBar_Layout);

        baseRecyclerAdapter = new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(CommentInfoVo.DataBean.class, new MyAdapter(_mActivity))
                .bind(NoMoreDataVo.class, new GameNoMoreItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
        mBaseRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mBaseRecyclerView.setAdapter(baseRecyclerAdapter);
        mBaseRecyclerView.setPullRefreshEnabled(false);
        mBaseRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getCommentUserListV2();
            }

            @Override
            public void onLoadMore() {
                page++;
                getCommentUserListV2();
            }
        });

        View mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_community_user_head_view, null);
        mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mIvUserPortrait = mHeaderView.findViewById(R.id.iv_user_portrait);
        mTvUserName =  mHeaderView.findViewById(R.id.tv_user_name);
        mLlGameFootprint =  mHeaderView.findViewById(R.id.ll_game_footprint);
        mTvCommentCount =  mHeaderView.findViewById(R.id.tv_comment_count);
        mTvQaCount =  mHeaderView.findViewById(R.id.tv_qa_count);
        mTvLikeCount =  mHeaderView.findViewById(R.id.tv_like_count);
        mLlCommunityComment =  mHeaderView.findViewById(R.id.ll_community_comment);
        mLlCommunityQa =  mHeaderView.findViewById(R.id.ll_community_qa);
        mLlCommunityLike =  mHeaderView.findViewById(R.id.ll_community_like);
        mFlUserLevel =  mHeaderView.findViewById(R.id.fl_user_level);
        mIvUserLevel =  mHeaderView.findViewById(R.id.iv_user_level);
        mTvUserLevel =  mHeaderView.findViewById(R.id.tv_user_level);
        mIvUserLevelRule =  mHeaderView.findViewById(R.id.iv_user_level_rule);

        mTvCount =  mHeaderView.findViewById(R.id.tv_count);
        mIvVipIcon =  mHeaderView.findViewById(R.id.iv_vip_icon);

        mBaseRecyclerView.addHeaderView(mHeaderView);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            initData();
        });

        mIvUserLevelRule.setOnClickListener(this);

        mTitleBar_Layout.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                _mActivity.finish();
            }
        });
    }


    private String user_nickname;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_user_level_rule:
                showUserLevelRule();
                break;
            case R.id.ll_community_comment:
                if(isLoginedUser){
                    start(UserCommentCenterFragment.newInstance(uid, user_nickname));
                }else{
                    int count = (int) v.getTag();
                    if (count > 0) {
                       Toaster.show( "TA有" + count + "个点评哦");
                    } else {
                       Toaster.show( "暂未点评");
                    }
                }
                break;
            case R.id.ll_community_qa:
                if (isLoginedUser) {
                    start(UserQaCollapsingCenterFragment.newInstance(uid, user_nickname));
                } else {
                    int count = (int) v.getTag();
                    if (count > 0) {
                       Toaster.show( "TA有" + count + "个问答哦");
                    } else {
                       Toaster.show( "暂未问答");
                    }
                }
                break;
            case R.id.ll_community_like:{
                int count = (int) v.getTag();
                if (count > 0) {
                   Toaster.show( "被赞" + count + "次，真棒！");
                } else {
                   Toaster.show( "暂未收到赞哦~");
                }
            }
                break;
            default:
                break;
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == ACTION_USER_INFO && resultCode == UserInfoFragment.ACTION_USER_LOGOUT) {
            //用户登出操作
            pop();
        }
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        getCommunityUserData();
        getCommentUserListV2();
    }

    private void setViewValue(CommunityUserVo.DataBean data) {
        if (data != null) {
            CommunityInfoVo communityInfoBean = data.getCommunity_info();
            if (communityInfoBean != null) {
                //用户信息
                GlideApp.with(_mActivity)
                        .asBitmap()
                        .load(communityInfoBean.getUser_icon())
                        .placeholder(R.mipmap.ic_user_login_new_sign)
                        .error(R.mipmap.ic_user_login_new_sign)
                        .transform(new GlideCircleTransform(_mActivity, (int) (3 * ScreenUtil.getScreenDensity(_mActivity))))
                        .into(mIvUserPortrait);

               //GlideUtils.loadCircleImage(_mActivity, communityInfoBean.getUser_icon(), mIvUserPortrait, R.mipmap.ic_user_login_new_sign, 3, R.color.white);
                user_nickname = communityInfoBean.getUser_nickname();
                mTvUserName.setText(communityInfoBean.getUser_nickname());
                UserInfoModel.setUserLevel(communityInfoBean.getUser_level(), mIvUserLevel, mTvUserLevel);
                if ("yes".equals(data.getIs_super_user())){
                    mIvVipIcon.setImageResource(R.mipmap.ic_vip_open_new);
                }else{
                    mIvVipIcon.setImageResource(R.mipmap.ic_vip_unopen_new);
                }
            }

            List<CommunityUserVo.GameTrackListBean> gameTrackListBeanList = data.getGame_track_list();
            //设置游戏足迹
            setGameFootPrint(gameTrackListBeanList, data.getGame_track_count());

            //更多动态
            CommunityUserVo.CommunityStatBean communityStatBean = data.getCommunity_stat();
            if (communityStatBean != null) {
                mTvCommentCount.setText(String.valueOf(communityStatBean.getComment_verify_count()));
                mTvQaCount.setText(String.valueOf(communityStatBean.getAnswer_verify_count()));
                mTvLikeCount.setText(String.valueOf(communityStatBean.getBe_praised_count()));
                mTvCount.setText(String.valueOf(communityStatBean.getBe_praised_count()));

                mLlCommunityComment.setTag(communityStatBean.getComment_verify_count());
                mLlCommunityQa.setTag(communityStatBean.getAnswer_verify_count());
                mLlCommunityLike.setTag(communityStatBean.getBe_praised_count());

                mLlCommunityComment.setOnClickListener(this);
                mLlCommunityQa.setOnClickListener(this);
                mLlCommunityLike.setOnClickListener(this);
            } else {
                mTvCommentCount.setText(String.valueOf(0));
                mTvQaCount.setText(String.valueOf(0));
                mTvLikeCount.setText(String.valueOf(0));
            }
        }
    }


    int gameFootPrintCount;

    private void setGameFootPrint(List<CommunityUserVo.GameTrackListBean> gameTrackListBeanList, int totalCount) {
        mLlGameFootprint.removeAllViews();
        if (gameTrackListBeanList != null) {
            gameFootPrintCount = totalCount;
            for (int i = 0; i < gameTrackListBeanList.size(); i++) {
                if (i >= 3) {
                    break;
                }
                View gameView = createGameView(gameTrackListBeanList.get(i));
                mLlGameFootprint.addView(gameView);
            }
        }
        View otherView = createOtherView(totalCount);
        mLlGameFootprint.addView(otherView);
    }

    private View createGameView(CommunityUserVo.GameTrackListBean gameTrackListBean) {
        ImageView imageView = new ImageView(_mActivity);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (70 * density), (int) (70 * density));
        params.rightMargin = (int) (18 * density);
        GlideUtils.loadRoundImage(_mActivity, gameTrackListBean.getGameicon(), imageView);
        imageView.setLayoutParams(params);

        imageView.setOnClickListener(v -> {
            if (isLoginedUser) {
                goGameDetail(gameTrackListBean.getGameid(), gameTrackListBean.getGame_type());
            } else {
               Toaster.show( "TA共有" + gameFootPrintCount + "个游戏足迹哦！");
            }
        });
        return imageView;
    }

    private View createOtherView(int gameSize) {
        TextView view = new TextView(_mActivity);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (70 * density), (int) (70 * density));
        params.rightMargin = (int) (18 * density);
        view.setLayoutParams(params);

        view.setTextSize(13);
        view.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_818181));

        String txt = gameSize == 0 ? "暂无" : "共" + gameSize + "款";
        view.setText(txt);

        view.setGravity(Gravity.CENTER);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_eeeeee));

        view.setBackground(gd);

        view.setOnClickListener(v -> {
            if (gameSize > 0) {
                if (isLoginedUser) {
                    start(GameFootPrintFragment.newInstance(uid));
                } else {
                   Toaster.show( "TA共有" + gameFootPrintCount + "个游戏足迹哦！");
                }
            } else {
                Toaster.show("暂无足迹");
            }
        });
        return view;
    }

    private void initData() {
        getCommunityUserData();
        page = 1;
        getCommentUserListV2();
    }

    private void getCommunityUserData() {
        if (mViewModel != null) {
            mViewModel.getCommunityUserData(uid, new OnBaseCallback<CommunityUserVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onSuccess(CommunityUserVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            setViewValue(data.getData());
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void getCommentUserListV2() {
        if (mViewModel != null) {
            mViewModel.getCommentUserListV2(uid, page, new OnBaseCallback<CommunityUserListV2Vo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }

                @Override
                public void onSuccess(CommunityUserListV2Vo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null && !data.getData().isEmpty()) {
                                if (page == 1) {
                                    baseRecyclerAdapter.clear();
                                }
                                baseRecyclerAdapter.addAllData(data.getData());
                                if (data.getData().size() < 12) {
                                    //无更多数据
                                    mBaseRecyclerView.setNoMore(true);
                                    baseRecyclerAdapter.addData(new NoMoreDataVo());
                                }
                            } else {
                                if (page == 1) {
                                    baseRecyclerAdapter.clear();
                                    EmptyDataVo emptyDataVo = new EmptyDataVo(R.mipmap.img_empty_data_2)
                                            .setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT)
                                            .setPaddingTop((int) (24 * density));
                                    baseRecyclerAdapter.addData(emptyDataVo);
                                } else {
                                    baseRecyclerAdapter.addData(new NoMoreDataVo());
                                }
                                //无更多数据
                                page = -1;
                                mBaseRecyclerView.setNoMore(true);
                            }
                            baseRecyclerAdapter.notifyDataSetChanged();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    class MyAdapter extends AbsItemHolder<CommentInfoVo.DataBean, MyAdapter.ViewHolder>{

        public MyAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_comment_user_list;
        }

        @Override
        public ViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }

        @Override
        protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CommentInfoVo.DataBean item) {
            //icon
            GlideUtils.loadGameIcon(_mActivity, item.getGameicon(), holder.mCivPortrait);
            holder.mTvUserNickname.setText(item.getGamename());
            //时间
            try {
                long ms = Long.parseLong(item.getRelease_time()) * 1000;
                holder.mTvTime.setText(CommonUtils.friendlyTime2(ms));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!TextUtils.isEmpty(item.getOtherGameName())){//游戏后缀
                holder.mTvGameSuffix.setVisibility(View.VISIBLE);
                holder.mTvGameSuffix.setText(item.getOtherGameName());
            }else {
                holder.mTvGameSuffix.setVisibility(View.GONE);
            }

            //积分
            holder.mIvCommentIntegral.setVisibility(View.GONE);
            if (item.getReward_integral() > 0) {
                if (item.getType_id().equals("1")){
                    holder.mIvCommentIntegral.setVisibility(View.VISIBLE);
                    holder.mIvCommentIntegral.setImageResource(R.mipmap.ic_game_detail_comment_type_review);
                }else if (item.getType_id().equals("2")){
                    holder.mIvCommentIntegral.setVisibility(View.VISIBLE);
                    holder.mIvCommentIntegral.setImageResource(R.mipmap.ic_game_detail_comment_type_strategy);
                }
            }

            //评论文字
            holder.mTvCommentContent.setText(item.getContent());

            //设置评论图片
            List<CommentInfoVo.PicInfoVo> picBeanList = item.getPics();
            if (picBeanList != null && picBeanList.size() > 0) {
                holder.mLlCommentPics.setVisibility(View.VISIBLE);

                holder.mIvCommentPic1.setVisibility(View.GONE);
                holder.mIvCommentPic2.setVisibility(View.GONE);
                holder.mFlCommentPic3.setVisibility(View.GONE);
                holder.mFlCommentPicShadow.setVisibility(View.GONE);

                if (picBeanList.size() >= 1) {
                    holder.mIvCommentPic1.setVisibility(View.VISIBLE);
                    GlideUtils.loadNormalImage(mContext, picBeanList.get(0).getPic_path(), holder.mIvCommentPic1, R.mipmap.ic_placeholder);
                }

                if (picBeanList.size() >= 2) {
                    holder.mIvCommentPic2.setVisibility(View.VISIBLE);
                    GlideUtils.loadNormalImage(mContext, picBeanList.get(1).getPic_path(), holder.mIvCommentPic2, R.mipmap.ic_placeholder);
                }

                if (picBeanList.size() >= 3) {
                    holder.mFlCommentPic3.setVisibility(View.VISIBLE);
                    holder.mIvCommentPic3.setVisibility(View.VISIBLE);
                    GlideUtils.loadNormalImage(mContext, picBeanList.get(2).getPic_path(), holder.mIvCommentPic3, R.mipmap.ic_placeholder);
                    if (picBeanList.size() > 3) {
                        holder.mFlCommentPicShadow.setVisibility(View.VISIBLE);
                        holder.mTvMoreCommentPic.setText("+" + String.valueOf(picBeanList.size() - 3));
                    }
                }
            } else {
                holder.mLlCommentPics.setVisibility(View.GONE);
            }

            //赞和回复
            holder.mTvCommentLike.setText(String.valueOf(item.getLike_count()));
            holder.mTvComments.setText(String.valueOf(item.getReply_count()));

            if (item.getMe_like() == 1) {
                holder.mTvCommentLike.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_new_game_comment_like_select), null, null, null);
                holder.mTvCommentLike.setEnabled(false);
            } else {
                holder.mTvCommentLike.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_new_game_comment_like), null, null, null);
                holder.mTvCommentLike.setEnabled(true);
            }

            holder.mFlRootView.setOnClickListener(v -> commentToDetail(item.getCid(), -1));

            holder.mIvCommentPic1.setOnClickListener(v -> showCommentPics(item.getPics(), 0));
            holder.mIvCommentPic2.setOnClickListener(v -> showCommentPics(item.getPics(), 1));
            holder.mIvCommentPic3.setOnClickListener(v -> showCommentPics(item.getPics(), 2));
            holder.mFlCommentPicShadow.setOnClickListener(v -> showCommentPics(item.getPics(), 2));

            holder.mIvCommentIntegral.setOnClickListener(v -> {
                //showCommentRuleDialog();
            });

            holder.mLlGameInfo.setOnClickListener(view -> {
                startFragment(GameDetailInfoFragment.newInstance(item.getGameid(), item.getGame_type()));
            });
        }

        private void commentToDetail(int cid, int position) {
            if (_mFragment != null) {
                _mFragment.start(CommentDetailFragment.newInstance(cid, position));
            }
        }

        /**********************点评规则弹窗************************************************************************************/
        public void showCommentRuleDialog() {
            CustomDialog commentDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_comment_rule, null),
                    ScreenUtils.getScreenWidth(_mActivity) - SizeUtils.dp2px(_mActivity, 24), WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            TextView mTvCommentRule1 = commentDialog.findViewById(R.id.tv_comment_rule_1);
            Button mBtnConfirm = commentDialog.findViewById(R.id.btn_confirm);

            mBtnConfirm.setOnClickListener(v -> {
                if (commentDialog != null && commentDialog.isShowing()) {
                    commentDialog.dismiss();
                }
            });

            mTvCommentRule1.setText(Html.fromHtml(_mActivity.getResources().getString(R.string.string_comment_rule_1)));
            commentDialog.show();
        }

        private void showCommentPics(List<CommentInfoVo.PicInfoVo> picBeanList, int position) {
            if (_mFragment != null) {
                if (picBeanList == null) {
                    return;
                }
                //预览图片
                ArrayList<Image> images = new ArrayList();
                for (CommentInfoVo.PicInfoVo picBean : picBeanList) {
                    Image image = new Image();
                    image.setType(1);
                    image.setPath(picBean.getPic_path());
                    image.setHigh_path(picBean.getHigh_pic_path());
                    images.add(image);
                }
                PreviewActivity.openActivity(_mFragment.getActivity(), images, true, position, true);
            }
        }

        public class ViewHolder extends AbsHolder {
            private LinearLayout       mFlRootView;
            private ImageView           mCivPortrait;
            private TextView           mTvUserNickname;
            private TextView           mTvTime;
            private TextView           mTvCommentContent;
            private LinearLayout       mLlCommentPics;
            private ImageView          mIvCommentPic1;
            private ImageView          mIvCommentPic2;
            private FrameLayout        mFlCommentPic3;
            private ImageView          mIvCommentPic3;
            private FrameLayout        mFlCommentPicShadow;
            private TextView           mTvMoreCommentPic;
            private ImageView          mIvCommentIntegral;

            private TextView           mTvComments;
            private TextView           mTvCommentLike;

            private LinearLayout mLlGameInfo;
            private TextView mTvGameSuffix;

            public ViewHolder(View view) {
                super(view);

                mFlRootView = findViewById(R.id.fl_rootView);
                mCivPortrait = findViewById(R.id.civ_portrait);
                mTvUserNickname = findViewById(R.id.tv_user_nickname);

                mTvTime = findViewById(R.id.tv_time);
                mTvCommentContent = findViewById(R.id.tv_comment_content);
                mLlCommentPics = findViewById(R.id.ll_comment_pics);
                mIvCommentPic1 = findViewById(R.id.iv_comment_pic_1);
                mIvCommentPic2 = findViewById(R.id.iv_comment_pic_2);
                mFlCommentPic3 = findViewById(R.id.fl_comment_pic_3);
                mIvCommentPic3 = findViewById(R.id.iv_comment_pic_3);
                mFlCommentPicShadow = findViewById(R.id.fl_comment_pic_shadow);
                mTvMoreCommentPic = findViewById(R.id.tv_more_comment_pic);

                mIvCommentIntegral = findViewById(R.id.iv_comment_integral);

                mTvComments = findViewById(R.id.tv_comments);
                mTvCommentLike = findViewById(R.id.tv_comment_like);

                mLlGameInfo = findViewById(R.id.ll_game_info);
                mTvGameSuffix = view.findViewById(R.id.tv_game_suffix);
            }
        }
    }
}
