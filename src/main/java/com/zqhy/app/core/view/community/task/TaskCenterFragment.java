package com.zqhy.app.core.view.community.task;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.box.common.sdk.ApkUtils;
import com.box.other.hjq.toast.Toaster;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.zqhy.app.adapter.ViewPagerAdapter;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.community.task.TaskInfoVo;
import com.zqhy.app.core.data.model.community.task.TaskSignInfoV2Vo;
import com.zqhy.app.core.data.model.community.task.TaskSignInfoVo;
import com.zqhy.app.core.data.model.community.task.TaskSignResultVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.data.model.share.InviteDataVo;
import com.zqhy.app.core.data.model.tryplay.TryGameItemVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.community.integral.CommunityIntegralMallFragment;
import com.zqhy.app.core.view.community.integral.IntegralDetailFragment;
import com.zqhy.app.core.view.community.qa.UserQaCollapsingCenterFragment;
import com.zqhy.app.core.view.community.task.adapter.TaskMenuAdapter;
import com.zqhy.app.core.view.community.task.adapter.TaskSignAdapter;
import com.zqhy.app.core.view.invite.InviteFriendFragment;
import com.zqhy.app.core.view.kefu.FeedBackFragment;
import com.zqhy.app.core.view.kefu.KefuCenterFragment;
import com.zqhy.app.core.view.login.LoginActivity;
import com.zqhy.app.core.view.main.MainActivity;
import com.zqhy.app.core.view.tryplay.TryGamePlayListFragment;
import com.zqhy.app.core.view.tryplay.TryGameTaskFragment;
import com.zqhy.app.core.view.user.newvip.NewUserVipFragment;
import com.zqhy.app.core.view.user.provincecard.NewProvinceCardFragment;
import com.zqhy.app.core.view.user.welfare.MyFavouriteGameListFragment;
import com.zqhy.app.core.vm.community.task.TaskViewModel;
import com.zqhy.app.glide.GlideRoundTransformNew;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.statistics.JiuYaoStatisticsApi;
import com.zqhy.app.newproject.R;
import com.zqhy.app.share.ShareHelper;
import com.zqhy.app.utils.sp.SPUtils;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Administrator
 */
public class TaskCenterFragment extends BaseFragment<TaskViewModel> implements View.OnClickListener {

    public static TaskCenterFragment newInstance() {
        TaskCenterFragment fragment = new TaskCenterFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static TaskCenterFragment newInstance(boolean noTitle) {
        TaskCenterFragment fragment = new TaskCenterFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("noTitle", noTitle);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_TASK_CENTER_STATE;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_task_center;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void start(ISupportFragment toFragment) {
        if (_mActivity instanceof MainActivity) {
            FragmentHolderActivity.startFragmentInActivity(_mActivity, (SupportFragment) toFragment);
        } else {
            if (getParentFragment() != null && getParentFragment() instanceof SupportFragment) {
                ((SupportFragment) getParentFragment()).start(toFragment);
            } else {
                super.start(toFragment);
            }
        }
    }

    @Override
    public void startForResult(ISupportFragment toFragment, int requestCode) {
        if (_mActivity instanceof MainActivity) {
            FragmentHolderActivity.startFragmentForResult(_mActivity, (SupportFragment) toFragment, requestCode);
        } else {
            if (getParentFragment() != null && getParentFragment() instanceof SupportFragment) {
                ((SupportFragment) getParentFragment()).startForResult(toFragment, requestCode);
            } else {
                super.startForResult(toFragment, requestCode);
            }
        }
    }

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            noTitle = getArguments().getBoolean("noTitle", false);
        }
        super.initView(state);
        showSuccess();
        setStatusBar(0x00cccccc);
        bindViews();
        setUserInfo();
        initData();
        /*SPUtils spUtils = new SPUtils(_mActivity, Constants.SP_COMMON_NAME);
        if (!spUtils.getBoolean("isTaskCenterDialog", false)){
            showTipsDialog();
        }*/
    }

    private boolean noTitle;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mTvIntegralInstruction;
    private Banner mBanner;
    private ImageView mIvCouple;
    private TextView mTvDayTaskStatus;
    private TextView mTvTryIntegral;
    private TextView mTvProvinceCount;

    private void bindViews() {
        if (noTitle) {
            findViewById(R.id.iv_back).setVisibility(View.GONE);
        } else {
            findViewById(R.id.iv_back).setVisibility(View.VISIBLE);
        }
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mTvIntegralInstruction = findViewById(R.id.tv_integral_instruction);
        mBanner = findViewById(R.id.banner);
        mIvCouple = findViewById(R.id.iv_couple);

        mTvDayTaskStatus = findViewById(R.id.tv_day_task_status);

        mTvTryIntegral = findViewById(R.id.tv_try_integral);
        mTvProvinceCount = findViewById(R.id.tv_province_count);

        findViewById(R.id.tv_login_tips).setOnClickListener(v -> startActivity(new Intent(_mActivity, LoginActivity.class)));

        bindTopViews();
        bindMiddleViews();
        String str = "如果你愿意对平台福利体系的完善提出宝贵建议，请联系平台福利策划亲哦~立即联系 >";
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#FF2B3F"));
            }

            @Override
            public void onClick(@NonNull View view) {
                //43 个人中心-客服反馈
                if (checkLogin()) {
                    startFragment(new KefuCenterFragment());
                }
            }
        }, str.length() - 6, str.length(), 0);
        mTvIntegralInstruction.setText(spannableString);

        findViewById(R.id.iv_back).setOnClickListener(view -> {
            pop();
        });
        findViewById(R.id.ll_vip_sign).setOnClickListener(view -> {
            startFragment(new NewUserVipFragment());
        });

        findViewById(R.id.ll_try_game).setOnClickListener(v -> {
            if (checkLogin()) {
                UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
                String url = "https://promotion.xiaodianyouxi.com/sample/index?rt=1&source=webApp";
                url = url + "&uid=" + userInfo.getUid() + "&token=" + userInfo.getToken() + "&tgid=" + ApkUtils.getTgid();
                BrowserActivity.newInstance(_mActivity, url);
            }

            //试玩有奖
//            if (taskignInfoV2Vo != null && !TextUtils.isEmpty(taskignInfoV2Vo.getTrial_url())) {
//                BrowserActivity.newInstance(_mActivity, taskignInfoV2Vo.getTrial_url());
//            } else {
//                start(TryGamePlayListFragment.newInstance());
//            }
        });
        findViewById(R.id.ll_daily_task).setOnClickListener(v -> {
            //每日任务
            start(DailyTaskFragment.newInstance());
        });
        mIvCouple.setOnClickListener(view -> {
            //新人任务
            start(new NewbieTaskListFragment());
        });
        findViewById(R.id.ll_review).setOnClickListener(v -> {
            //点评活动
            //点击弹窗——任务说明弹窗
            showTaskTipDialog(R.mipmap.ic_task_dialog_game_comment, "游戏点评", "优质点评有奖", "500积分/日",
                    "选择一款游戏发布点评：围绕游戏画面、 玩法、操作、氪金等方面点评游戏，评 为优质点评随机获得30-100积分奖励。 每日最多发布5篇，最高可获得500积分。",
                    "",
                    "我知道了", "", 1, 1);
        });
        findViewById(R.id.ll_province).setOnClickListener(v -> {
            //省钱卡福利
            start(new NewProvinceCardFragment());
        });
        findViewById(R.id.ll_prefecture).setOnClickListener(v -> {
            //648专区
            if (taskignInfoV2Vo != null && taskignInfoV2Vo.getHd_block() != null && taskignInfoV2Vo.getHd_block().size() > 0) {
                TaskSignInfoV2Vo.DataBean.HdBlockBean hdBlockBean = taskignInfoV2Vo.getHd_block().get(0);
                AppBaseJumpInfoBean appBaseJumpInfoBean = new AppBaseJumpInfoBean(hdBlockBean.getPage_type(), hdBlockBean.getParam());
                if (_mActivity != null) {
                    AppJumpAction appJumpAction = new AppJumpAction(_mActivity);
                    appJumpAction.jumpAction(appBaseJumpInfoBean);
                }
            }
        });
        findViewById(R.id.ll_large_coupon).setOnClickListener(v -> {
            //0元领券区
            if (taskignInfoV2Vo != null && taskignInfoV2Vo.getHd_block() != null && taskignInfoV2Vo.getHd_block().size() > 1) {
                TaskSignInfoV2Vo.DataBean.HdBlockBean hdBlockBean = taskignInfoV2Vo.getHd_block().get(1);
                AppBaseJumpInfoBean appBaseJumpInfoBean = new AppBaseJumpInfoBean(hdBlockBean.getPage_type(), hdBlockBean.getParam());
                if (_mActivity != null) {
                    AppJumpAction appJumpAction = new AppJumpAction(_mActivity);
                    appJumpAction.jumpAction(appBaseJumpInfoBean);
                }
            }
        });

        mTvIntegralInstruction.setOnClickListener(this);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            initData();
        });
    }

    private void refreshComplete() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private TextView mTvIntegralBalance;
    private TextView mTvIntegralDetail;
    private RecyclerView mMenuRecyclerView;
    private ImageView mIvTaskBg;

    private void bindTopViews() {
        mTvIntegralBalance = findViewById(R.id.tv_integral_balance);
        mTvIntegralDetail = findViewById(R.id.tv_integral_detail);
        mMenuRecyclerView = findViewById(R.id.recycler_view_menu);
        mIvTaskBg = findViewById(R.id.iv_task_bg);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mIvTaskBg.getLayoutParams();
        layoutParams.height = ScreenUtil.dp2px(_mActivity, 225f) + ScreenUtil.getStatusBarHeight(_mActivity);
        mIvTaskBg.setLayoutParams(layoutParams);

        List<TaskInfoVo> taskInfoVos = new ArrayList<>();
        taskInfoVos.add(new TaskInfoVo(11, R.mipmap.ic_task_menu_item_zp, "每日转盘", "", "积分抽好礼 >"));
        taskInfoVos.add(new TaskInfoVo(10, R.mipmap.ic_task_menu_item_jf, "积分商城", "", "兑换福利 >"));
        taskInfoVos.add(new TaskInfoVo(13, R.mipmap.ic_task_menu_item_hy, "会员每日礼", "", "专属兑换 >"));
        mMenuRecyclerView.setLayoutManager(new GridLayoutManager(_mActivity, 3));
        TaskMenuAdapter taskMenuAdapter = new TaskMenuAdapter(_mActivity, taskInfoVos);
        mMenuRecyclerView.setAdapter(taskMenuAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(_mActivity, DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_white_bg));
        mMenuRecyclerView.addItemDecoration(dividerItemDecoration);
        mTvIntegralDetail.setOnClickListener(this);

        taskMenuAdapter.setOnItemClickListener((v, position, data) -> {
            taskItemClick((TaskInfoVo) data);
        });
    }

    private TextView mIvTaskSignInQuestion;
    private ImageView mIvSignPageLeft;
    private ViewPager mViewPager;
    private ImageView mIvSignPageRight;
    private TextView mTvSignInIntegral;
    private TextView mTvSignIn;
    private RecyclerView mSignRecyclerView;
    private TaskSignAdapter mTaskSignAdapter;
    private List<TaskSignInfoV2Vo.DataBean.SignListBean> mSignList = new ArrayList<>();

    private void bindMiddleViews() {
        mIvTaskSignInQuestion = findViewById(R.id.iv_task_sign_in_question);
        mIvSignPageLeft = findViewById(R.id.iv_sign_page_left);
        mViewPager = findViewById(R.id.mViewPager);
        mIvSignPageRight = findViewById(R.id.iv_sign_page_right);
        mTvSignInIntegral = findViewById(R.id.tv_sign_in_integral);
        mTvSignIn = findViewById(R.id.tv_sign_in);

        mSignRecyclerView = findViewById(R.id.recycler_view_sign);
        mSignRecyclerView.setLayoutManager(new GridLayoutManager(_mActivity, 7));
        mTaskSignAdapter = new TaskSignAdapter(_mActivity, mSignList);
        mSignRecyclerView.setAdapter(mTaskSignAdapter);

        mIvTaskSignInQuestion.setOnClickListener(this);
        mIvSignPageLeft.setOnClickListener(this);
        mIvSignPageRight.setOnClickListener(this);
        mTvSignIn.setOnClickListener(this);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    public void taskItemClick(TaskInfoVo taskInfoVo) {
        switch (taskInfoVo.getTaskId()) {
            case 1:
                //跳转--每日签到
                start(new TaskSignInFragment());
                JiuYaoStatisticsApi.getInstance().eventStatistics(8, 116);
                break;
            case 2:
                //点击弹窗——任务说明弹窗
                //充值任务 任意金额
                showTaskTipDialog(R.mipmap.ic_task_dialog_daily_recharge, "每日充值", "游戏内充值", "50积分/日",
                        "游戏内使用，支付宝/微信实际支付一笔，即可自动获得积分奖励。",
                        "VIP用户完成每日充值可额外获得25积分。",
                        "我知道了", null, 0, 0);
                JiuYaoStatisticsApi.getInstance().eventStatistics(8, 117);
                break;
            case 8:
                //充值任务 100元起
                showTaskTipDialog(R.mipmap.ic_task_dialog_recharge, "充值任务", "游戏内累计充满100元", "200积分/日",
                        "游戏内使用，支付宝/微信实际支付金额 满100元，即可自动获得积分奖励。",
                        "VIP用户完成每日充值可额外获得100积分。 ",
                        "我知道了", null, 0, 0);
                break;
            case 3:
                //点击跳转——新手任务
                start(new NewbieTaskListFragment());
                JiuYaoStatisticsApi.getInstance().eventStatistics(8, 115);
                break;
            case 4:
                //点击跳转——邀请好友（登录后根据情况显示任务）
                if (checkLogin()) {
                    UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
                    if (userInfo.getInvite_type() == 1) {
                        if (mViewModel != null) {
                            mViewModel.getShareData("1", new OnBaseCallback<InviteDataVo>() {

                                @Override
                                public void onSuccess(InviteDataVo data) {
                                    if (data != null && data.isStateOK() && data.getData() != null) {
                                        if (data.getData().getInvite_info() != null) {
                                            InviteDataVo.InviteDataInfoVo inviteInfo = data.getData().getInvite_info();
                                            new ShareHelper(_mActivity).shareToAndroidSystem(inviteInfo.getCopy_title(), inviteInfo.getCopy_description(), inviteInfo.getUrl());
                                        }
                                    }
                                }
                            });
                        }
                    } else {
                        startFragment(new InviteFriendFragment());
                    }
                    JiuYaoStatisticsApi.getInstance().eventStatistics(8, 118);
                }
                break;
            case 5:
                //点击弹窗——任务说明弹窗
                showTaskTipDialog(R.mipmap.ic_task_dialog_game_comment, "游戏点评", "优质点评有奖", "500积分/日",
                        "选择一款游戏发布点评：围绕游戏画面、 玩法、操作、氪金等方面点评游戏，评 为优质点评随机获得30-100积分奖励。 每日最多发布5篇，最高可获得500积 分，",
                        "VIP用户每发布一篇优质点评可额 外获得15积分。",
                        "前往参与", "取消", 2, 1);
                JiuYaoStatisticsApi.getInstance().eventStatistics(8, 119);
                break;
            case 6:
                //点击弹窗——任务说明弹窗
                showTaskTipDialog(R.mipmap.ic_task_dialog_game_qa, "游戏问答", "解答游戏问题", "100积分/日",
                        " 受邀为其他玩家解答游戏问题，通过即可 获得10积分，每日最多回答10次， 最高可获得100积分。",
                        "VIP用户每回答一个游戏问题可额外获 得5积分。",
                        "前往参与", "取消", 3, 1);
                JiuYaoStatisticsApi.getInstance().eventStatistics(8, 120);
                break;
            case 7:
                //限时任务
            case 10:
                //积分商城
                start(new CommunityIntegralMallFragment());
                break;
            case 11:
                //每日转盘
                BrowserActivity.newInstance(_mActivity, Constants.URL_ZHUANPANFULI);
                break;
            case 12:
                //试玩有奖
                //FragmentHolderActivity.startFragmentInActivity(_mActivity, TryGamePlayListFragment.newInstance());
                start(TryGamePlayListFragment.newInstance());
                JiuYaoStatisticsApi.getInstance().eventStatistics(8, 113);
                break;
            case 13:
                //会员每日礼
                start(new NewUserVipFragment());
            default:
                break;
        }
    }

    public void taskSubItemClick(TryGameItemVo.DataBean dataBean, int position) {
        //        FragmentHolderActivity.startFragmentInActivity(_mActivity,
        //                TryGameDetailFragment.newInstance(dataBean.getTid()));

        FragmentHolderActivity.startFragmentInActivity(_mActivity,
                TryGameTaskFragment.newInstance(dataBean.getTid()));
        JiuYaoStatisticsApi.getInstance().eventStatistics(8, 113, (position + 1));

    }

    private void setUserInfo() {
        UserInfoVo.DataBean dataBean = UserInfoModel.getInstance().getUserInfo();
        if (dataBean != null) {
            findViewById(R.id.ll_integral).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_login_tips).setVisibility(View.GONE);
            mTvIntegralBalance.setText(String.valueOf(dataBean.getIntegral()));
        } else {
            findViewById(R.id.ll_integral).setVisibility(View.GONE);
            findViewById(R.id.tv_login_tips).setVisibility(View.VISIBLE);
            mTvIntegralBalance.setText("0");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_integral_instruction:
                //客服中心-建议反馈
                if (checkLogin()) {
                    startFragment(new FeedBackFragment());
                }
                break;
            case R.id.iv_sign_page_left:
                viewPagerPrevious();
                break;
            case R.id.iv_sign_page_right:
                viewPagerNext();
                break;
            case R.id.tv_sign_in:
                //今日签到
                if (checkLogin()) {
                    sign();
                }
                break;
            case R.id.tv_integral_detail:
                //积分明细
                if (checkLogin()) {
                    start(new IntegralDetailFragment());
                    JiuYaoStatisticsApi.getInstance().eventStatistics(8, 111);
                }
                break;
            case R.id.iv_task_sign_in_question:
                //每日签到说明
                showSignInDialog(mIvTaskSignInQuestion);
                break;
            default:
                break;
        }
    }

    private void showSignInDialog(View anchor) {
        /*View layoutView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_pop_float_task_sign_in, null);
        PopupWindow floatPop = new PopupWindow(layoutView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        floatPop.setOutsideTouchable(false);
        ColorDrawable colorDrawable = new ColorDrawable(0x00000000);
        floatPop.setBackgroundDrawable(colorDrawable);
        floatPop.showAsDropDown(anchor);*/
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_task_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    List<View> pagerViews;

    private void setSignInViews(List<TaskSignInfoVo.SignListBean> taskInfoBeanList) {
        if (taskInfoBeanList == null) {
            return;
        }
        try {
            if (pagerViews == null) {
                pagerViews = new ArrayList<>();
            }
            pagerViews.clear();
            List<TaskSignInfoVo.SignListBean> pagerSignInList = null;
            for (int i = 0; i < taskInfoBeanList.size(); i++) {
                if (i % 5 == 0) {
                    pagerSignInList = new ArrayList<>();
                }
                pagerSignInList.add(taskInfoBeanList.get(i));
                if (i % 5 == 4) {
                    if (pagerSignInList != null) {
                        pagerViews.add(createSignInPager(pagerSignInList));
                    }
                }
            }

            ViewPagerAdapter mAdapter = new ViewPagerAdapter(pagerViews);
            mViewPager.setAdapter(mAdapter);
            mViewPager.setCurrentItem(pagerViews.size() - 1, true);

            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int currentPage = 0;

    private void viewPagerNext() {
        currentPage++;
        if (currentPage > pagerViews.size() - 1) {
            currentPage = pagerViews.size() - 1;
        }
        mViewPager.setCurrentItem(currentPage, true);
    }

    private void viewPagerPrevious() {
        currentPage--;
        if (currentPage < 0) {
            currentPage = 0;
        }
        mViewPager.setCurrentItem(currentPage, true);
    }

    private View createSignInPager(List<TaskSignInfoVo.SignListBean> signInInfoBeanList) {
        View pager = LayoutInflater.from(_mActivity).inflate(R.layout.layout_sign_in_page, null);
        LinearLayout mLlContainer = pager.findViewById(R.id.ll_container);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pager.setLayoutParams(params);


        if (signInInfoBeanList != null) {
            for (TaskSignInfoVo.SignListBean signInInfoBean : signInInfoBeanList) {
                mLlContainer.addView(createSignInItem(signInInfoBean));
            }
        }
        return pager;
    }

    private View createSignInItem(TaskSignInfoVo.SignListBean signInInfoBean) {

        int width = mViewPager.getRight() - mViewPager.getLeft();
        int itemWidth = width / 5;

        View itemPager = LayoutInflater.from(_mActivity).inflate(R.layout.layout_sign_in_item, null);

        TextView mTvSignInDate = itemPager.findViewById(R.id.tv_sign_in_date);
        ImageView mIvSignedIn = itemPager.findViewById(R.id.iv_signed_in);
        TextView mTvSignInEarnings = itemPager.findViewById(R.id.tv_sign_in_earnings);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemPager.setLayoutParams(params);

        mTvSignInDate.setText(signInInfoBean.getDay_time());
        mTvSignInDate.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));

        if (signInInfoBean.isToday()) {
            mTvSignInDate.setBackgroundResource(R.mipmap.ic_task_sign_in_date_today);
        } else if (signInInfoBean.isTomorrow()) {
            mTvSignInDate.setBackgroundResource(R.mipmap.ic_task_sign_in_date_tomorrow);
        } else {
            mTvSignInDate.setBackgroundResource(R.mipmap.ic_task_sign_in_date_before);
        }

        StringBuilder sb = new StringBuilder();
        if (signInInfoBean.getIs_today() == 1) {
            //今天
            if (signInInfoBean.getIs_sign() == 1) {
                //已签
                mIvSignedIn.setImageResource(R.mipmap.ic_task_signed_in);
                sb.append("已签");
                int startIndex = sb.length();
                sb.append("+").append(signInInfoBean.getIntegral());
                SpannableString ss = new SpannableString(sb.toString());
                ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_fd4415)),
                        startIndex, sb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mTvSignInEarnings.setText(ss);
            } else {
                //待签
                mIvSignedIn.setImageResource(R.mipmap.ic_task_to_sign_in);
                sb.append("待签");
                SpannableString ss = new SpannableString(sb.toString());
                mTvSignInEarnings.setText(ss);
            }

        } else if (signInInfoBean.getIs_today() == 2) {
            //明天及以后
            //待签
            mIvSignedIn.setImageResource(R.mipmap.ic_task_tomorrow_sign_in);
            sb.append("待签");
            SpannableString ss = new SpannableString(sb.toString());
            mTvSignInEarnings.setText(ss);

        } else if (signInInfoBean.getIs_today() == 0) {
            //今天以前
            if (signInInfoBean.getIs_sign() == 1) {
                //已签
                mIvSignedIn.setImageResource(R.mipmap.ic_task_signed_in);
                sb.append("已签");
                int startIndex = sb.length();
                sb.append("+").append(signInInfoBean.getIntegral());
                SpannableString ss = new SpannableString(sb.toString());
                ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_fd4415)),
                        startIndex, sb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mTvSignInEarnings.setText(ss);
            } else {
                //未签
                mIvSignedIn.setImageResource(R.mipmap.ic_task_unsigned_in);
                sb.append("未签");
                int startIndex = sb.length();
                sb.append("+").append(signInInfoBean.getIntegral());
                SpannableString ss = new SpannableString(sb.toString());
                ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_fd9e15)),
                        startIndex, sb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mTvSignInEarnings.setText(ss);
            }
        }
        return itemPager;
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        setUserInfo();
        getSignData();
    }


    private void showTaskTipDialog(int iconRes, String taskTitle, String taskSubTitle, String reward, String taskDescription, String taskSubDescription, String btn1Text, String btn2Text, int btn1Action, int btn2Action) {
        CustomDialog taskTipDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_task_tip, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        TextView mTvTaskTitle = taskTipDialog.findViewById(R.id.tv_task_title);
        TextView mTvTaskSubTitle = taskTipDialog.findViewById(R.id.tv_task_sub_title);
        TextView mTvTaskReward = taskTipDialog.findViewById(R.id.tv_task_reward);
        TextView mTvTaskProcess = taskTipDialog.findViewById(R.id.tv_task_process);
        TextView mBtnTxt1 = taskTipDialog.findViewById(R.id.btn_txt_1);
        TextView mBtnTxt2 = taskTipDialog.findViewById(R.id.btn_txt_2);
        ImageView mIcon = taskTipDialog.findViewById(R.id.icon);


        mIcon.setImageResource(iconRes);
        mTvTaskTitle.setText(taskTitle);
        mTvTaskSubTitle.setText(taskSubTitle);
        mTvTaskReward.setText(reward);

        SpannableString ss = new SpannableString(taskDescription + taskSubDescription);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_ff0000)),
                taskDescription.length(), taskDescription.length() + taskSubDescription.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvTaskProcess.setText(ss);

        if (TextUtils.isEmpty(btn1Text)) {
            mBtnTxt1.setVisibility(View.GONE);
        } else {
            mBtnTxt1.setVisibility(View.VISIBLE);
            mBtnTxt1.setText(btn1Text);
            mBtnTxt1.setOnClickListener(view -> {
                taskDialogBtnClick(btn1Action);
                if (taskTipDialog != null && taskTipDialog.isShowing()) {
                    taskTipDialog.dismiss();
                }
            });
        }

        if (TextUtils.isEmpty(btn2Text)) {
            mBtnTxt2.setVisibility(View.GONE);
        } else {
            mBtnTxt2.setVisibility(View.VISIBLE);
            mBtnTxt2.setText(btn2Text);
            mBtnTxt2.setOnClickListener(view -> {
                taskDialogBtnClick(btn2Action);
                if (taskTipDialog != null && taskTipDialog.isShowing()) {
                    taskTipDialog.dismiss();
                }
            });
        }

        taskTipDialog.show();
    }

    /**
     * @param btnAction 1 关闭弹窗
     *                  2 参与游戏点评
     *                  3 参与游戏问答
     */
    private void taskDialogBtnClick(int btnAction) {
        switch (btnAction) {
            case 1:

                break;
            case 2:
                //我的游戏/我的游戏
                if (checkLogin()) {
                    //start(GameWelfareFragment.newInstance(0));
                    startFragment(new MyFavouriteGameListFragment());
                }
                break;
            case 3:
                //我的问答
                if (checkLogin()) {
                    int uid = UserInfoModel.getInstance().getUserInfo().getUid();
                    String user_nickname = UserInfoModel.getInstance().getUserInfo().getUser_nickname();
                    start(UserQaCollapsingCenterFragment.newInstance(uid, user_nickname));
                }
                break;
            default:
                break;
        }
    }

    private void initData() {
        getUserData();
        getSignData();
    }

    private void getUserData() {
        if (mViewModel != null) {
            mViewModel.refreshUserDataWithoutNotification(new OnBaseCallback<UserInfoVo>() {
                @Override
                public void onSuccess(UserInfoVo data) {
                    setUserInfo();
                }
            });
        }
    }

    private void getSignData() {
        if (mViewModel != null) {
            mViewModel.getSignDataV2(new OnBaseCallback<TaskSignInfoV2Vo>() {

                @Override
                public void onBefore() {
                    super.onBefore();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                    refreshComplete();
                }

                @Override
                public void onSuccess(TaskSignInfoV2Vo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            //setViewData(data.getData());
                            taskignInfoV2Vo = data.getData();
                            setViewDataV2(data.getData());
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private TaskSignInfoV2Vo.DataBean taskignInfoV2Vo;

    private void setViewDataV2(TaskSignInfoV2Vo.DataBean data) {
        if (data.getBanner_list() != null && data.getBanner_list().size() > 0) {
            mBanner.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params = mBanner.getLayoutParams();
            if (params != null) {
                params.height = (ScreenUtil.getScreenWidth(_mActivity) - ScreenUtil.dp2px(_mActivity, 28)) * 180 / 710;
                mBanner.setLayoutParams(params);
            }
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置图片加载器
            mBanner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    TaskSignInfoV2Vo.DataBean.BannerListBean bannerListBean = (TaskSignInfoV2Vo.DataBean.BannerListBean) path;
                    GlideApp.with(_mActivity).asBitmap()
                            .load(bannerListBean.getPic())
                            .placeholder(R.mipmap.img_placeholder_v_load)
                            .error(R.mipmap.img_placeholder_v_load)
                            .transform(new GlideRoundTransformNew(_mActivity, 10))
                            .into(imageView);
                }
            });
            //设置图片集合
            mBanner.setImages(data.getBanner_list());
            //设置banner动画效果
            mBanner.setBannerAnimation(Transformer.Default);
            //设置自动轮播，默认为true
            mBanner.isAutoPlay(true);
            //设置轮播时间
            mBanner.setDelayTime(1500);
            //设置指示器位置（当banner模式中有指示器时）
            mBanner.setIndicatorGravity(BannerConfig.CENTER);
            mBanner.setOnBannerListener(position -> {
                TaskSignInfoV2Vo.DataBean.BannerListBean bannerListBean = data.getBanner_list().get(position);
                AppBaseJumpInfoBean appBaseJumpInfoBean = new AppBaseJumpInfoBean(bannerListBean.getPage_type(), bannerListBean.getParam());
                if (_mActivity != null) {
                    AppJumpAction appJumpAction = new AppJumpAction(_mActivity);
                    appJumpAction.jumpAction(appBaseJumpInfoBean);
                }
            });
            //banner设置方法全部调用完毕时最后调用
            mBanner.start();
        } else {
            mBanner.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(data.getXr_banner()) && !data.isXr_task_end()) {
            mIvCouple.setVisibility(View.VISIBLE);
            GlideApp.with(_mActivity).asBitmap()
                    .load(data.getXr_banner())
                    .placeholder(R.mipmap.img_placeholder_v_load)
                    .error(R.mipmap.img_placeholder_v_load)
                    .transform(new GlideRoundTransformNew(_mActivity, 10))
                    .into(mIvCouple);
        } else {
            mIvCouple.setVisibility(View.GONE);
        }
        if (data.getSign_list() != null && data.getSign_list().size() > 0) {
            mSignList.clear();
            mSignList.addAll(data.getSign_list());
            mTaskSignAdapter.notifyDataSetChanged();
        }
        if (data.isToday_is_signed()) {//已签
            mTvSignIn.setEnabled(false);
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(ScreenUtil.dp2px(_mActivity, 100));
            gd.setColor(Color.parseColor("#D6D5DC"));
            mTvSignIn.setBackground(gd);
            mTvSignIn.setText("已签到");
        } else {
            //未签，待签
            mTvSignIn.setEnabled(true);
            mTvSignIn.setBackgroundResource(R.drawable.ts_shape_ffa530_big_radius);
            mTvSignIn.setText("每日签到");
        }
        mTvTryIntegral.setText(data.getTry_game_reward());
        mTvProvinceCount.setText(data.getMember_total());

        if (data.getHd_block() != null && data.getHd_block().size() > 0) {
            TaskSignInfoV2Vo.DataBean.HdBlockBean hdBlockBean = data.getHd_block().get(0);
            GlideApp.with(_mActivity).asBitmap()
                    .load(hdBlockBean.getPic())
                    .placeholder(R.mipmap.img_placeholder_v_load)
                    .error(R.mipmap.img_placeholder_v_load)
                    .into((ImageView) findViewById(R.id.iv_prefecture));
            ((TextView) findViewById(R.id.tv_prefecture)).setText(hdBlockBean.getTitle());
            ((TextView) findViewById(R.id.tv_prefecture_tips)).setText(hdBlockBean.getDescription());
        }
        if (data.getHd_block() != null && data.getHd_block().size() > 1) {
            TaskSignInfoV2Vo.DataBean.HdBlockBean hdBlockBean = data.getHd_block().get(1);
            GlideApp.with(_mActivity).asBitmap()
                    .load(hdBlockBean.getPic())
                    .placeholder(R.mipmap.img_placeholder_v_load)
                    .error(R.mipmap.img_placeholder_v_load)
                    .into((ImageView) findViewById(R.id.iv_large_coupon));
            ((TextView) findViewById(R.id.tv_large_coupon)).setText(hdBlockBean.getTitle());
            ((TextView) findViewById(R.id.tv_large_coupon_tips)).setText(hdBlockBean.getDescription());
        }
        if ("yes".equals(data.getDay_task_finished())) {
            mTvDayTaskStatus.setVisibility(View.GONE);
        } else {
            mTvDayTaskStatus.setVisibility(View.VISIBLE);
        }
    }

    private TaskSignInfoVo.SignListBean todaySignInInfoBean;

    private void setViewData(TaskSignInfoVo.DataBean data) {
        if (data == null) {
            return;
        }
        for (TaskSignInfoVo.SignListBean signInInfoBean : data.getSign_list()) {
            if (signInInfoBean.isToday()) {
                todaySignInInfoBean = signInInfoBean;
                break;
            }
        }

        setSignInViews(data.getSign_list());

        mTvSignInIntegral.setVisibility(View.VISIBLE);
        if (todaySignInInfoBean != null) {
            if (todaySignInInfoBean.getIs_sign() != 1) {
                //未签，待签
                mTvSignIn.setEnabled(true);
                mTvSignIn.setBackgroundResource(R.drawable.ts_shape_gradient_ff9900_ff4e00);
                mTvSignIn.setText("每日签到");

                mTvSignInIntegral.setText("今日+" + data.getSign_integral());
            } else {
                //已签
                mTvSignIn.setEnabled(false);
                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(ScreenUtil.dp2px(_mActivity, 100));
                gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_b5b5b5));
                mTvSignIn.setBackground(gd);
                mTvSignIn.setText("已签到");

                mTvSignInIntegral.setText("明日+" + data.getSign_integral());
            }
        }
    }

    private void sign() {
        if (mViewModel != null) {
            mViewModel.userSignV2(new OnBaseCallback<TaskSignResultVo>() {
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
                public void onSuccess(TaskSignResultVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            //                            Toaster.show(_mActivity, "签到成功");
                            //showSignInSuccessDialog(data.getData());
                            //showSignDialog(data.getData());
                            Toaster.show("签到成功，恭喜获得" + data.getData().getIntegral() + "积分！");
                            getSignData();
                            mViewModel.refreshUserData();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void showSignInSuccessDialog(TaskSignResultVo.DataBean data) {
        if (data == null) {
            return;
        }

        CustomDialog dialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_sign_in_success, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        TextView mTvSignInDays = dialog.findViewById(R.id.tv_sign_in_days);
        TextView mTvSignInIntegral = dialog.findViewById(R.id.tv_sign_in_integral);
        ImageView mIvClosed = dialog.findViewById(R.id.iv_closed);

        int days = data.getContinued_days();
        SpannableString ss1 = new SpannableString("已连续签到" + days + "天");
        ss1.setSpan(new ForegroundColorSpan(Color.parseColor("#FCB707")), 5, 5 + String.valueOf(days).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvSignInDays.setText(ss1);

        int integral = data.getIntegral();
        SpannableString ss2 = new SpannableString("+" + integral + "积分");
        ss2.setSpan(new AbsoluteSizeSpan(30, true), 1, 1 + String.valueOf(integral).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvSignInIntegral.setText(ss2);

        mIvClosed.setOnClickListener(view -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showTipsDialog() {
        CustomDialog dialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_sign_tips, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.findViewById(R.id.iv_cancel).setOnClickListener(v -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        });
        dialog.show();
        SPUtils spUtils = new SPUtils(_mActivity, Constants.SP_COMMON_NAME);
        spUtils.putBoolean("isTaskCenterDialog", true);
    }

    private void showSignDialog(TaskSignResultVo.DataBean data) {
        CustomDialog dialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_sign_tips1, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        TextView mTvIntegral = dialog.findViewById(R.id.tv_integral);
        mTvIntegral.setText(data.getIntegral() + "积分");
        dialog.findViewById(R.id.ll_vip).setOnClickListener(v -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            startFragment(new NewUserVipFragment());
        });
        dialog.findViewById(R.id.ll_task).setOnClickListener(v -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            startFragment(new DailyTaskFragment());
        });
        dialog.findViewById(R.id.ll_try).setOnClickListener(v -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            startFragment(TryGamePlayListFragment.newInstance());
        });
        dialog.findViewById(R.id.iv_cancel).setOnClickListener(v -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
