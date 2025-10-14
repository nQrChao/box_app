package com.zqhy.app.core.view.user;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.box.other.hjq.toast.Toaster;
import com.google.android.flexbox.FlexboxLayout;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.community.CommunityUserVo;
import com.zqhy.app.core.data.model.kefu.KefuInfoDataVo;
import com.zqhy.app.core.data.model.message.MessageInfoVo;
import com.zqhy.app.core.data.model.message.MessageListVo;
import com.zqhy.app.core.data.model.share.InviteDataVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.model.user.UserVipInfoVo;
import com.zqhy.app.core.data.model.user.UserVoucherVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.activity.MainActivityFragment;
import com.zqhy.app.core.view.community.comment.UserCommentCenterFragment;
import com.zqhy.app.core.view.community.qa.UserQaCollapsingCenterFragment;
import com.zqhy.app.core.view.community.task.TaskCenterFragment;
import com.zqhy.app.core.view.community.task.TaskSignInFragment;
import com.zqhy.app.core.view.community.user.CommunityUserFragment;
import com.zqhy.app.core.view.invite.InviteFriendFragment;
import com.zqhy.app.core.view.message.MessageMainFragment;
import com.zqhy.app.core.view.rebate.RebateMainFragment;
import com.zqhy.app.core.view.recycle_new.XhNewRecycleMainFragment;
import com.zqhy.app.core.view.setting.SettingManagerFragment;
import com.zqhy.app.core.view.strategy.DiscountStrategyFragment;
import com.zqhy.app.core.view.user.newvip.NewUserVipFragment;
import com.zqhy.app.core.view.user.provincecard.NewProvinceCardFragment;
import com.zqhy.app.core.view.user.vip.UserVipLevelPrivilegeFragment;
import com.zqhy.app.core.view.user.welfare.MyCardListFragment;
import com.zqhy.app.core.view.user.welfare.MyCouponsListFragment;
import com.zqhy.app.core.vm.kefu.KefuViewModel;
import com.zqhy.app.db.table.message.MessageDbInstance;
import com.zqhy.app.db.table.message.MessageVo;
import com.zqhy.app.glide.GlideCircleTransform;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.share.ShareHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pc
 * @time 2019/11/12 15:20
 * @description
 */
public class UserMineFragment extends BaseFragment<KefuViewModel> {

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_USER_MINE_STATE;
    }

    @Override
    protected String getUmengPageName() {
        return "我的";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user_mine;
    }

    @Override
    public int getContentResId() {
        return R.id.container;
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        bindViews();
        getKefuInfoData();
        getCommunityUserData();
        getUserVoucherCount();
        getKefuMessageData();
        getUserVipInfoData();
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NestedScrollView   mContainer;
    private LinearLayout       mLlUserMine;
    private LinearLayout       mLlUserHeader;
    private AppCompatImageView mProfileImage;
    private LinearLayout       mLlLayoutLogin;
    private TextView           mTvUserNickname;
    private TextView           mTvUsername;
    private TextView           mTvBindPhone;
    private LinearLayout       mLlLayoutNoLogin;
    private LinearLayout       mLlItemContainer;
    private TextView           mTvPtbAmount;
    private TextView           mTvIntegralCount;
    private FlexboxLayout      mFlexKefuCenter;
    private FlexboxLayout      mFlexFuliCenter;
    private TextView           mTvTsEmail;
    private LinearLayout       mLlCommentCount;
    private TextView           mTvCommentCount;
    private LinearLayout       mLlQaCount;
    private TextView           mTvQaCount;
    private LinearLayout       mLlLikeCount;
    private TextView           mTvLikeCount;
    private FlexboxLayout      mFlexMore;
    private RelativeLayout     mRlEarnMoney;
    private RelativeLayout     mRlUserIntegral;
    private RelativeLayout     mRlUserPtb;
    private AppCompatTextView  mTvUserMineSignIn;
    private AppCompatImageView mIvUserMineMessage;
    private AppCompatImageView mIvUserMineSetting;
    private ImageView          mIvUserLevel;
    private TextView           mTvUserLevel;

    private RelativeLayout    mRlPtbNormal;
    private LinearLayout      mLlPtbRefund;
    private TextView          mTvPtbRefundTotal;
    private TextView          mTvPtbRefundByRecharge;
    private TextView          mTvPtbRefundInfo;
    private TextView          mTvPtbRefundByOthers;
    private TextView          mTvApp;
    private LinearLayout      mLlLayoutCommentQa;
    private FrameLayout       mFlUserMonthCard;
    private View              mViewMessageTips;
    private AppCompatTextView mTvUserMinePage;
    private FrameLayout       mRlVipMonthCard;
    private TextView          mTvApp1;
    private RelativeLayout    mRlUserVip;
    private TextView          mTvUserVipInfoLevel;
    private TextView          mTvUserVipCount;
    private TextView          mTvUserVipLevel;
    private ImageView         mIvUserVipLevel;
    private TextView          mTvText;
    private LinearLayout      mLlUserVipReward;

    private void bindViews() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mContainer = findViewById(R.id.container);
        mLlUserMine = findViewById(R.id.ll_user_mine);
        mLlUserHeader = findViewById(R.id.ll_user_header);
        mProfileImage = findViewById(R.id.profile_image);
        mLlLayoutLogin = findViewById(R.id.ll_layout_login);
        mTvUserNickname = findViewById(R.id.tv_user_nickname);
        mTvUsername = findViewById(R.id.tv_username);
        mTvBindPhone = findViewById(R.id.tv_bind_phone);
        mLlLayoutNoLogin = findViewById(R.id.ll_layout_no_login);
        mLlItemContainer = findViewById(R.id.ll_item_container);
        mTvPtbAmount = findViewById(R.id.tv_ptb_amount);
        mTvIntegralCount = findViewById(R.id.tv_integral_count);
        mFlexKefuCenter = findViewById(R.id.flex_kefu_center);
        mFlexFuliCenter = findViewById(R.id.flex_fuli_center);
        mTvTsEmail = findViewById(R.id.tv_ts_email);

        mRlUserIntegral = findViewById(R.id.rl_user_integral);
        mRlUserPtb = findViewById(R.id.rl_user_ptb);

        mRlUserIntegral.setOnClickListener(view -> {
            //赚取积分
            startFragment(TaskCenterFragment.newInstance());
        });
        mRlUserPtb.setOnClickListener(view -> {
            //我的平台币
            if (checkLogin()) {
                startFragment(TopUpFragment.newInstance());
            }
        });

        mFlUserMonthCard = findViewById(R.id.fl_user_month_card);

        mLlLayoutCommentQa = findViewById(R.id.ll_layout_comment_qa);
        mLlCommentCount = findViewById(R.id.ll_comment_count);
        mTvCommentCount = findViewById(R.id.tv_comment_count);
        mLlQaCount = findViewById(R.id.ll_qa_count);
        mTvQaCount = findViewById(R.id.tv_qa_count);
        mLlLikeCount = findViewById(R.id.ll_like_count);
        mTvLikeCount = findViewById(R.id.tv_like_count);
        mFlexMore = findViewById(R.id.flex_more);
        mRlEarnMoney = findViewById(R.id.rl_earn_money);

        if (AppConfig.isHideCommunity()) {
            mLlLayoutCommentQa.setVisibility(View.GONE);
        } else {
            mLlLayoutCommentQa.setVisibility(View.VISIBLE);
        }

        mRlEarnMoney.setOnClickListener(v -> {
            if (checkLogin()) {
                startFragment(TaskCenterFragment.newInstance());
            }
        });

        mTvUserMineSignIn = findViewById(R.id.tv_user_mine_sign_in);
        mIvUserMineMessage = findViewById(R.id.iv_user_mine_message);
        mIvUserMineSetting = findViewById(R.id.iv_user_mine_setting);

        mTvUserMineSignIn.setOnClickListener(view -> {
            //签到
            if (checkLogin()) {
                startFragment(new TaskSignInFragment());
            }
        });
        mIvUserMineMessage.setOnClickListener(view -> {
            //消息
            if (checkLogin()) {
                startFragment(new MessageMainFragment());
                showMessageTip(false);
            }
        });
        mIvUserMineSetting.setOnClickListener(view -> {
            //设置
            startFragment(new SettingManagerFragment());
        });

        mIvUserLevel = findViewById(R.id.iv_user_level);
        mTvUserLevel = findViewById(R.id.tv_user_level);

        mSwipeRefreshLayout.setProgressViewOffset(true, -20, 100);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_3478f6,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (mViewModel != null) {
                mViewModel.refreshUserDataWithNotification(data -> {
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    if (data != null && data.isNoLogin()) {
                        UserInfoModel.getInstance().logout();
                        checkLogin();
                    }
                });
            }
        });

        //2020.05.09 新增
        mRlPtbNormal = findViewById(R.id.rl_ptb_normal);
        mLlPtbRefund = findViewById(R.id.ll_ptb_refund);
        mTvPtbRefundTotal = findViewById(R.id.tv_ptb_refund_total);
        mTvPtbRefundByRecharge = findViewById(R.id.tv_ptb_refund_by_recharge);
        mTvPtbRefundInfo = findViewById(R.id.tv_ptb_refund_info);
        mTvPtbRefundByOthers = findViewById(R.id.tv_ptb_refund_by_others);

        mTvPtbRefundInfo.setOnClickListener(view -> {
            //平台币说明的弹窗
            showRefundPtbInfoDialog();
        });


        //2020.05.20 新增
        mTvApp = findViewById(R.id.tv_app);
        mTvApp.setText(getAppNameByXML(R.string.string_dyx_club));

        mContainer.setOnScrollChangeListener((NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) -> {
            if (scrollY == 0) {
                mSwipeRefreshLayout.setEnabled(true);
            } else {
                mSwipeRefreshLayout.setEnabled(false);
            }
        });

        setMineDialogUserView();

        post(() -> {
            setMenuCenter(0);
            setMoreList();
        });

        if (AppConfig.isRefundChannel()) {
            mRlPtbNormal.setVisibility(View.GONE);
            mLlPtbRefund.setVisibility(View.VISIBLE);
        } else {
            mRlPtbNormal.setVisibility(View.VISIBLE);
            mLlPtbRefund.setVisibility(View.GONE);
        }
        mViewMessageTips = findViewById(R.id.view_message_tips);


        mTvUserMinePage = findViewById(R.id.tv_user_mine_page);
        mTvUserMinePage.setOnClickListener(view -> {
            if (checkLogin()) {
                int uid = UserInfoModel.getInstance().getUserInfo().getUid();
                startFragment(CommunityUserFragment.newInstance(uid));
            }
        });

        mRlVipMonthCard = findViewById(R.id.rl_vip_month_card);
        mTvApp1 = findViewById(R.id.tv_app_1);
        mRlVipMonthCard.setOnClickListener(view -> {
            /*if (checkLogin()) {
                startFragment(new VipMemberFragment());
            }*/
            startFragment(new NewProvinceCardFragment());
        });
        //巴兔月卡
        mTvApp1.setText(getAppVipMonthName() + "月卡");
        //巴兔VIP
        mRlUserVip = findViewById(R.id.rl_user_vip);
        mRlUserVip.setOnClickListener(view -> {
            /*if (checkLogin()) {
                startFragment(new UserVipFragment());
            }*/
            startFragment(new NewUserVipFragment());
        });

        //2020.11.25新增
        mTvUserVipInfoLevel = findViewById(R.id.tv_user_vip_info_level);
        mTvUserVipCount = findViewById(R.id.tv_user_vip_count);


        mTvUserVipLevel = findViewById(R.id.tv_user_vip_level);
        mIvUserVipLevel = findViewById(R.id.iv_user_vip_level);

        mTvText = findViewById(R.id.tv_text);
        mLlUserVipReward = findViewById(R.id.ll_user_vip_reward);

    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        setMineDialogUserView();
        getCommunityUserData();
        getUserVoucherCount();
        getKefuMessageData();
        getUserVipInfoData();
    }

    /**
     * 设置用户信息
     */
    private void setMineDialogUserView() {
        UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
        if (userInfoBean != null) {
            mLlLayoutLogin.setVisibility(View.VISIBLE);
            mLlLayoutNoLogin.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(userInfoBean.getUser_icon())) {
                GlideApp.with(_mActivity)
                        .asBitmap()
                        .load(userInfoBean.getUser_icon())
                        .placeholder(R.mipmap.ic_user_login)
                        .error(R.mipmap.ic_user_login)
                        .transform(new GlideCircleTransform(_mActivity, (int) (3 * ScreenUtil.getScreenDensity(_mActivity))))
                        .into(mProfileImage);
            } else {
                mProfileImage.setImageResource(R.mipmap.ic_user_login);
            }

            mTvUserNickname.setText(userInfoBean.getUser_nickname());
            mTvUsername.setText("用户名：" + userInfoBean.getUsername());

            String phone = userInfoBean.getMobile();
            if (TextUtils.isEmpty(phone)) {
                SpannableString ss = new SpannableString("(有风险) 前往安全绑定 >");
                ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_ff0000)), 0, 5, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                mTvBindPhone.setText(ss);
                mTvBindPhone.setOnClickListener(view -> {
                    startFragment(BindPhoneFragment.newInstance(false, ""));
                });
            } else {
                mTvBindPhone.setText("绑定手机：" + phone);
                mTvBindPhone.setOnClickListener(null);
            }

            mLlUserHeader.setOnClickListener(view -> {
                //用户个人资料 玩家主页
                if (checkLogin()) {
                    //if(BuildConfig.APP_TEMPLATE != 9999){
                        startFragment(new UserInfoFragment());
                    //}
                }
            });
            //设置平台币
            String fGold = String.valueOf(userInfoBean.getPingtaibi());
            mTvPtbAmount.setText(fGold);

            mTvPtbRefundTotal.setText(fGold);
            mTvPtbRefundByRecharge.setText(String.valueOf(userInfoBean.getPtb_rmb()));
            mTvPtbRefundByOthers.setText(String.valueOf(userInfoBean.getPtb_dc()));

            //设置积分
            String strIntegral = String.valueOf(userInfoBean.getIntegral());
            mTvIntegralCount.setText(strIntegral);

            boolean isVipMember = UserInfoModel.getInstance().isVipMember();
            UserInfoModel.setUserLevel(userInfoBean.getUser_level(), mIvUserLevel, mTvUserLevel);
            UserInfoModel.setUserMonthCard(isVipMember, mFlUserMonthCard);
        } else {
            mLlLayoutLogin.setVisibility(View.GONE);
            mLlLayoutNoLogin.setVisibility(View.VISIBLE);
            mProfileImage.setImageResource(R.mipmap.ic_user_un_login);
            mLlUserHeader.setOnClickListener(view -> {
                //登录
                checkLogin();
            });

            String strGold = "0.00";
            mTvPtbAmount.setText(strGold);
            mTvPtbRefundTotal.setText(strGold);
            mTvPtbRefundByRecharge.setText(strGold);
            mTvPtbRefundByOthers.setText(strGold);


            String strIntegral = String.valueOf(0);
            mTvIntegralCount.setText(strIntegral);
        }
    }

    /**
     * 平台币说明（FAQ）弹窗
     */
    private void showRefundPtbInfoDialog() {
        CustomDialog dialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_transaction_count_down_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        Button mBtnGotIt = dialog.findViewById(R.id.btn_got_it);
        ImageView mIvImage = dialog.findViewById(R.id.iv_image);
        CheckBox mCbButton = dialog.findViewById(R.id.cb_button);

        mCbButton.setText("我已阅读平台币说明");
        mIvImage.setImageResource(R.mipmap.img_refund_tips_ptb);

        GradientDrawable gd2 = new GradientDrawable();
        gd2.setCornerRadius(30 * density);
        gd2.setColor(Color.parseColor("#C1C1C1"));
        mBtnGotIt.setBackground(gd2);
        mBtnGotIt.setEnabled(false);
        mBtnGotIt.setOnClickListener(view -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        });

        mCbButton.setOnCheckedChangeListener((compoundButton, b) -> {
            GradientDrawable gd1 = new GradientDrawable();
            gd1.setCornerRadius(30 * density);
            if (b) {
                gd1.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                gd1.setColors(new int[]{Color.parseColor("#22A8FD"), Color.parseColor("#5963FC")});
            } else {
                gd1.setColor(Color.parseColor("#C1C1C1"));
            }
            mBtnGotIt.setBackground(gd1);
            mBtnGotIt.setText("我已知晓");
            mBtnGotIt.setEnabled(b);
        });
        dialog.show();
    }

    /**
     * 点击事件
     *
     * @param v
     */
    public void onClick(View v) {
        switch (v.getId()) {
            //            case R.id.tv_ptb_recharge:
            //                //平台币充值
            //                if (checkLogin()) {
            //                    startFragment(TopUpFragment.newInstance());
            //                }
            //                break;
            //            case R.id.tv_integral_mall:
            //                //积分商城
            //                startFragment(new CommunityIntegralMallFragment());
            //                break;
            //            case R.id.item_user_mine_kefu_cloud:
            //                //常见问题
            //                startFragment(new KefuHelperFragment());
            //                break;
            //
            //            case R.id.item_user_mine_kefu_qq_group:
            //                //玩家Q群
            //                joinQQGroup(qq_group_number);
            //                break;
            //            case R.id.item_user_mine_kefu_feedback:
            //            case R.id.item_user_mine_more_feedback:
            //                //意见反馈
            //                if (checkLogin()) {
            //                    startFragment(new FeedBackFragment());
            //                }
            //                break;
            //            case R.id.item_user_mine_fuli_recharge_detail:
            //            case R.id.item_user_mine_setting_recharge:
            //            case R.id.item_user_mine_more_recharge_detail:
            //                //充值明细
            //                if (checkLogin()) {
            //                    startFragment(new CurrencyMainFragment());
            //                }
            //                break;
            case R.id.item_user_mine_menu_user_game:
                //游戏/礼包
                if (checkLogin()) {
                    //startFragment(new GameWelfareFragment());
                    startFragment(new MyCardListFragment());
                }
                break;
            case R.id.item_user_mine_menu_vouchers:
                //代金券
                if (checkLogin()) {
                    //startFragment(GameWelfareFragment.newInstance(2));
                    startFragment(new MyCouponsListFragment());
                }
                break;
            case R.id.item_user_mine_menu_rebate:
                //申请返利
                if (checkLogin()) {
                    startFragment(new RebateMainFragment());
                }
                break;
            case R.id.item_user_mine_menu_kefu_feedback:
                //客服反馈
                goKefuCenter();
                break;
            case R.id.item_user_mine_more_real_name_authentication:
                //实名认证
                if (checkLogin()) {
                    startFragment(new CertificationFragment());
                }
                break;
            case R.id.item_user_mine_more_activity:
                //活动&公告
                startFragment(MainActivityFragment.newInstance("公告快讯"));
                break;
            case R.id.item_user_mine_more_zhuanyou:
                //转游福利
                //                if (checkLogin()) {
                //                    startFragment(new TransferMainFragment());
                //                }
                Toaster.show("即将上线，敬请期待");
                break;
            case R.id.item_user_mine_more_strategy:
                //省钱攻略
                startFragment(new DiscountStrategyFragment());
                break;
            case R.id.item_user_mine_more_invite:
                //邀请赚钱
                if (checkLogin()) {
                    UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
                    if (userInfo.getInvite_type() == 1) {
                        if (mViewModel != null) {
                            mViewModel.getShareData("1", new OnBaseCallback<InviteDataVo>() {

                                @Override
                                public void onSuccess(InviteDataVo data) {
                                    if (data != null && data.isStateOK() && data.getData() != null){
                                        if (data.getData().getInvite_info() != null){
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
                }
                break;
            case R.id.item_user_mine_more_cooperation:
                //商务合作
                startFragment(new BusinessCooperationFragment());
                break;
            case R.id.item_user_mine_more_open_platform:
                //开放平台
                startFragment(new OpenPlatformFragment());
                break;
            case R.id.item_user_mine_xh_new_recycle:
                //小号回收
                if (checkLogin()) {
                    startFragment(new XhNewRecycleMainFragment());
                }
                break;
            default:
                break;
        }
    }

    private void setMenuCenter(int userVoucherCount) {
        List<ItemMenuVo> itemMenuVoList = new ArrayList<>();

        itemMenuVoList.add(new ItemMenuVo(R.id.item_user_mine_menu_user_game, R.mipmap.ic_mine_menu_user_game, "游戏/礼包", "足迹"));
        itemMenuVoList.add(new ItemMenuVo(R.id.item_user_mine_menu_vouchers, R.mipmap.ic_mine_menu_vouchers, "代金券", "福利", userVoucherCount));
        itemMenuVoList.add(new ItemMenuVo(R.id.item_user_mine_menu_rebate, R.mipmap.ic_mine_menu_rebate, "自助返利", "申请"));
        itemMenuVoList.add(new ItemMenuVo(R.id.item_user_mine_menu_kefu_feedback, R.mipmap.ic_mine_menu_kefu_feedback, "客服反馈", "答疑"));

        mFlexFuliCenter.removeAllViews();
        for (ItemMenuVo itemMenuVo : itemMenuVoList) {
            View itemView = createItemMenuView(itemMenuVo);
            int itemViewWidth = (mFlexFuliCenter.getRight() - mFlexFuliCenter.getLeft()) / 4;
            int itemViewHeight = (int) (94 * density);
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(itemViewWidth, itemViewHeight);
            mFlexFuliCenter.addView(itemView, params);
        }
    }

    private View createItemMenuView(ItemMenuVo itemMenuVo) {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.item_mine_menu, null);
        ImageView mIvIcon = itemView.findViewById(R.id.iv_icon);
        TextView mTvTitle = itemView.findViewById(R.id.tv_title);
        TextView mTvSubTitle = itemView.findViewById(R.id.tv_sub_title);
        TextView mTvMessageCount = itemView.findViewById(R.id.tv_message_count);

        itemView.setId(itemMenuVo.id);
        mIvIcon.setImageResource(itemMenuVo.iconRes);
        mTvTitle.setText(itemMenuVo.title);
        mTvSubTitle.setText(itemMenuVo.subTitle);

        if (itemMenuVo.messageCount > 0) {
            mTvMessageCount.setVisibility(View.VISIBLE);
            String strCount = itemMenuVo.messageCount > 99 ? "99+" : String.valueOf(itemMenuVo.messageCount);
            mTvMessageCount.setText(strCount);
        } else {
            mTvMessageCount.setVisibility(View.GONE);
        }

        itemView.setOnClickListener(view -> onClick(view));
        return itemView;
    }

    private void setMoreList() {
        List<ItemSettingVo> itemSettingVoList = new ArrayList<>();

        itemSettingVoList.add(new ItemSettingVo(R.id.item_user_mine_more_real_name_authentication, R.mipmap.ic_mine_more_real_name_authentication, "实名认证"));
        itemSettingVoList.add(new ItemSettingVo(R.id.item_user_mine_more_activity, R.mipmap.ic_mine_more_activity, "公告快讯"));
        itemSettingVoList.add(new ItemSettingVo(R.id.item_user_mine_xh_new_recycle, R.mipmap.ic_mine_xh_new_recycle, "小号回收"));
        //        itemSettingVoList.add(new ItemSettingVo(R.id.item_user_mine_more_zhuanyou, R.mipmap.ic_mine_more_zhuanyou, "转游福利"));
        itemSettingVoList.add(new ItemSettingVo(R.id.item_user_mine_more_strategy, R.mipmap.ic_mine_more_strategy, "省钱攻略"));
        if (!AppConfig.isGonghuiChannel()) {
            itemSettingVoList.add(new ItemSettingVo(R.id.item_user_mine_more_invite, R.mipmap.ic_mine_more_invite, "邀请赚钱"));
            //            itemSettingVoList.add(new ItemSettingVo(R.id.item_user_mine_more_cooperation, R.mipmap.ic_mine_more_cooperation, "商务合作"));
            //            itemSettingVoList.add(new ItemSettingVo(R.id.item_user_mine_more_open_platform, R.mipmap.ic_mine_more_open_platform, "开放平台"));
        }


        for (ItemSettingVo itemSettingVo : itemSettingVoList) {
            View itemView = createMoreItemView(itemSettingVo);
            int itemViewWidth = (mFlexMore.getRight() - mFlexMore.getLeft()) / 4;
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(itemViewWidth, FlexboxLayout.LayoutParams.WRAP_CONTENT);
            mFlexMore.addView(itemView, params);
        }
    }

    private View createMoreItemView(ItemSettingVo itemSettingVo) {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.item_mine_more_setting, null);
        ImageView mIvIcon = itemView.findViewById(R.id.iv_icon);
        TextView mTvTitle = itemView.findViewById(R.id.tv_title);

        itemView.setId(itemSettingVo.id);
        mIvIcon.setImageResource(itemSettingVo.iconRes);
        mTvTitle.setText(itemSettingVo.title);

        itemView.setOnClickListener(view -> onClick(view));
        return itemView;
    }

    class ItemMenuVo {
        @IdRes
        private int id;

        @DrawableRes
        private int iconRes;

        private String title;

        private String subTitle;

        private int messageCount;

        public ItemMenuVo(int id, int iconRes, String title, String subTitle) {
            this.id = id;
            this.iconRes = iconRes;
            this.title = title;
            this.subTitle = subTitle;
        }

        public ItemMenuVo(int id, int iconRes, String title, String subTitle, int messageCount) {
            this.id = id;
            this.iconRes = iconRes;
            this.title = title;
            this.subTitle = subTitle;
            this.messageCount = messageCount;
        }
    }

    class ItemSettingVo {
        @IdRes
        private int id;

        @DrawableRes
        private int iconRes;

        private String title;

        private String subTitle;

        @ColorRes
        private int subTitleColor;

        public ItemSettingVo(int id, int iconRes, String title) {
            this.id = id;
            this.iconRes = iconRes;
            this.title = title;
        }

    }


    private void joinQQGroup(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            _mActivity.startActivity(intent);
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            e.printStackTrace();
            Toaster.show( "未安装手Q或安装的版本不支持");
        }
    }

    private String qq_group_number = "";

    private void getKefuInfoData() {
        if (mViewModel != null) {
            mViewModel.getKefuInfo(new OnBaseCallback<KefuInfoDataVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onSuccess(KefuInfoDataVo kefuInfoDataVo) {
                    if (kefuInfoDataVo != null) {
                        if (kefuInfoDataVo.isStateOK() && kefuInfoDataVo.getData() != null) {
                            try {
                                qq_group_number = kefuInfoDataVo.getData().getJy_kf().getQq_qun_key();
                                String ts_email = kefuInfoDataVo.getData().getTs_email();
                                if (!TextUtils.isEmpty(ts_email)) {
                                    mTvTsEmail.setVisibility(View.VISIBLE);
                                    mTvTsEmail.setText("投诉邮箱：" + ts_email);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        }
    }

    private void getCommunityUserData() {
        if (UserInfoModel.getInstance().isLogined()) {
            if (mViewModel != null) {
                mSwipeRefreshLayout.setEnabled(true);
                int uid = UserInfoModel.getInstance().getUserInfo().getUid();
                String user_nickname = UserInfoModel.getInstance().getUserInfo().getUser_nickname();
                mViewModel.getCommunityUserData(uid, new OnBaseCallback<CommunityUserVo>() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onSuccess(CommunityUserVo data) {
                        if (data != null && data.isStateOK() && data.getData() != null) {
                            //更多动态
                            CommunityUserVo.CommunityStatBean communityStatBean = data.getData().getCommunity_stat();
                            if (communityStatBean != null) {
                                mTvCommentCount.setText(String.valueOf(communityStatBean.getComment_verify_count()));
                                mTvQaCount.setText(String.valueOf(communityStatBean.getAnswer_verify_count()));
                                mTvLikeCount.setText(String.valueOf(communityStatBean.getBe_praised_count()));

                                mLlLikeCount.setTag(communityStatBean.getBe_praised_count());

                                mLlCommentCount.setOnClickListener(v -> {
                                    if (checkLogin()) {
                                        startFragment(UserCommentCenterFragment.newInstance(uid, user_nickname));
                                    }
                                });
                                mLlQaCount.setOnClickListener(v -> {
                                    if (checkLogin()) {
                                        startFragment(UserQaCollapsingCenterFragment.newInstance(uid, user_nickname));
                                    }
                                });
                                mLlLikeCount.setOnClickListener(v -> {
                                    if (checkLogin()) {
                                        int count = (int) v.getTag();
                                        if (count > 0) {
                                            Toaster.show( "被赞" + count + "次，真棒！");
                                        } else {
                                            Toaster.show( "暂未收到赞哦~");
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            }
        } else {
            mSwipeRefreshLayout.setEnabled(false);
            mTvCommentCount.setText(String.valueOf(0));
            mTvQaCount.setText(String.valueOf(0));
            mTvLikeCount.setText(String.valueOf(0));
            mLlCommentCount.setOnClickListener(null);
            mLlQaCount.setOnClickListener(null);
            mLlLikeCount.setOnClickListener(null);
        }
    }

    private void getUserVoucherCount() {
        if (mViewModel != null) {
            mViewModel.getUserVoucherCount(new OnBaseCallback<UserVoucherVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onSuccess(UserVoucherVo data) {
                    if (data != null) {
                        if (data.isStateOK() && data.getData() != null) {
                            setMenuCenter(data.getData().getVoucher_unused());
                        } else {
                            setMenuCenter(0);
                        }
                    }
                }
            });
        }
    }

    private void getKefuMessageData() {
        if (mViewModel != null && UserInfoModel.getInstance().isLogined()) {
            Runnable runnable1 = () -> {
                MessageVo messageVo = MessageDbInstance.getInstance().getMaxIdMessageVo(1);
                _mActivity.runOnUiThread(() -> {
                    int maxID = 0;
                    if (messageVo != null) {
                        maxID = messageVo.getMessage_id();
                    }
                    mViewModel.getKefuMessageData(getStateEventKey().toString(), maxID, new OnBaseCallback<MessageListVo>() {
                        @Override
                        public void onSuccess(MessageListVo messageListVo) {
                            if (messageListVo != null) {
                                if (messageListVo.isStateOK()) {
                                    saveMessageToDb(messageListVo.getData());
                                }
                            }
                        }
                    });
                });
            };
            new Thread(runnable1).start();
        }
    }

    private void saveMessageToDb(List<MessageInfoVo> messageInfoVoList) {
        Runnable runnable = () -> {
            if (messageInfoVoList != null) {
                for (MessageInfoVo messageInfoVo : messageInfoVoList) {
                    MessageVo messageVo = messageInfoVo.transformIntoMessageVo();
                    MessageDbInstance.getInstance().saveMessageVo(messageVo);
                }
                int messageCount = MessageDbInstance.getInstance().getUnReadMessageCount(1);
                _mActivity.runOnUiThread(() -> {
                    showMessageTip(messageCount > 0);
                });
            }
        };
        new Thread(runnable).start();
    }

    /**
     * 消息红点提示
     *
     * @param isShow
     */
    private void showMessageTip(boolean isShow) {
        if (mViewMessageTips != null) {
            mViewMessageTips.setVisibility(isShow && UserInfoModel.getInstance().isLogined() ? View.VISIBLE : View.GONE);
        }
    }


    /**
     * 获取用户Vip信息
     */
    private void getUserVipInfoData() {
        if (UserInfoModel.getInstance().isLogined()) {
            mViewModel.getUserVipInfo(new OnBaseCallback<UserVipInfoVo>() {
                @Override
                public void onSuccess(UserVipInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                mTvUserVipInfoLevel.setText(String.valueOf(data.getData().getVip_level()));
                                mTvUserVipCount.setVisibility(View.VISIBLE);
                                mTvUserVipCount.setText("成长值" + data.getData().getVip_score());
                                UserInfoModel.setUserVipLevel(data.getData().getVip_level(), mIvUserVipLevel, mTvUserVipLevel);

                                if (data.getData().getHas_coupon() != null && !data.getData().getHas_coupon().isEmpty()) {
                                    mTvText.setVisibility(View.GONE);
                                    mLlUserVipReward.setVisibility(View.VISIBLE);
                                    mLlUserVipReward.setOnClickListener(view -> {
                                        int itemId = 0;
                                        switch (data.getData().getHas_coupon().get(0)) {
                                            case "memor":
                                                itemId = 3;
                                                break;
                                            case "month":
                                                itemId = 5;
                                                break;
                                            case "upgrade":
                                                itemId = 6;
                                                break;
                                            case "birthday":
                                                itemId = 7;
                                                break;
                                            case "holiday":
                                                itemId = 8;
                                                break;
                                        }
                                        startFragment(UserVipLevelPrivilegeFragment.newInstance(itemId));
                                    });
                                } else {
                                    mTvText.setVisibility(View.VISIBLE);
                                    mLlUserVipReward.setVisibility(View.GONE);
                                    mLlUserVipReward.setOnClickListener(null);
                                }

                            }
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        } else {
            mTvUserVipInfoLevel.setText("");
            mTvUserVipCount.setVisibility(View.GONE);
            UserInfoModel.setUserVipLevel(0, mIvUserVipLevel, mTvUserVipLevel);
        }
    }
}
