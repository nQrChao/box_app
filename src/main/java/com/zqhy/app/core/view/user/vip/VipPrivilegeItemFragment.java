package com.zqhy.app.core.view.user.vip;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.user.reward.UserCommonRewardInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.user.CertificationFragment;
import com.zqhy.app.core.vm.user.UserViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.CountDownTimerCopyFromAPI26;

/**
 * @author leeham2734
 * @date 2020/11/11-11:00
 * @description
 */
public class VipPrivilegeItemFragment extends BaseFragment<UserViewModel> {


    public static VipPrivilegeItemFragment newInstance(int pId, String title, String subTitle) {
        VipPrivilegeItemFragment fragment = new VipPrivilegeItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pId", pId);
        bundle.putString("title", title);
        bundle.putString("subTitle", subTitle);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_vip_privilege_item;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_container;
    }

    private int pId;
    String title;
    String subTitle;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            pId = getArguments().getInt("pId");
            title = getArguments().getString("title");
            subTitle = getArguments().getString("subTitle");
        }
        super.initView(state);
        showSuccess();
        bindViews();
        initData();
    }


    private TextView     mTvVipTitle;
    private TextView     mTvVipSubTitle;
    private TextView     mTvUserVipLevel;
    private LinearLayout mLlLayout1;
    private FrameLayout  mFlLayoutContainer;

    private void bindViews() {
        mTvVipTitle = findViewById(R.id.tv_vip_title);
        mTvVipSubTitle = findViewById(R.id.tv_vip_sub_title);
        mTvUserVipLevel = findViewById(R.id.tv_user_vip_level);
        mLlLayout1 = findViewById(R.id.ll_layout_1);
        mFlLayoutContainer = findViewById(R.id.fl_layout_container);

        mTvVipTitle.setText(title);
        mTvVipSubTitle.setText(subTitle);

        addLayoutVipPrivilegeDescription();
        addLayoutVipPrivilegeDetail();
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        addLayoutVipPrivilegeDetail();
    }

    private void addLayoutVipPrivilegeDetail() {
        mFlLayoutContainer.removeAllViews();
        if (pId == 9) {
            mFlLayoutContainer.addView(createDetailLayoutById_9());
        } else if (pId == 10) {
            mFlLayoutContainer.addView(createDetailLayoutById_10());
        } else if (pId == 11) {
            mFlLayoutContainer.addView(createDetailLayoutById_11());
        } else if (pId == 12) {
            mFlLayoutContainer.addView(createDetailLayoutById_12());
        } else if (pId == 3) {
            mFlLayoutContainer.addView(createDetailLayoutById_3());
        } else if (pId == 4) {
            mFlLayoutContainer.addView(createDetailLayoutById_4());
        } else if (pId == 5) {
            mFlLayoutContainer.addView(createDetailLayoutById_5());
        } else if (pId == 6) {
            mFlLayoutContainer.addView(createDetailLayoutById_5());
        } else if (pId == 7) {
            mFlLayoutContainer.addView(createDetailLayoutById_7());
        } else if (pId == 8) {
            mFlLayoutContainer.addView(createDetailLayoutById_8());
        }
    }

    private void addLayoutVipPrivilegeDescription() {
        mLlLayout1.removeAllViews();
        if (pId == 9) {
            mLlLayout1.addView(createDescriptionView("不同VIP等级显示不同的身份标识，彰显您的尊贵身份。"));
            mTvUserVipLevel.setText("VIP 1");
        } else if (pId == 10) {
            mLlLayout1.addView(createDescriptionView("VIP会员每日签到时可获得双倍积分，积分商城好礼加速兑换。"));
            mTvUserVipLevel.setText("VIP 1");
        } else if (pId == 11) {
            mLlLayout1.addView(createDescriptionView("VIP会员完成每日时可额外获得50%积分，积分商城好礼加速兑换。"));
            mTvUserVipLevel.setText("VIP 1");
        } else if (pId == 12) {
            mLlLayout1.addView(createDescriptionView("每日登录app可获得双倍活力值，升级提速"));
            mTvUserVipLevel.setText("VIP 1");
        } else if (pId == 3) {
            mLlLayout1.addView(createDescriptionView("从您在平台注册开始，每一个纪念日，都能收到我们亲切的关怀与温馨的礼物。"));
            mLlLayout1.addView(createDescriptionView("纪念日关怀礼物，将不定期更新，每期礼物可能不同（包括但不限于：代金券、平台币），具体请以页面实际情况为准。"));
            mLlLayout1.addView(createDescriptionView("奖励需在当前页面领取，发放时会有短信或app系统消息通知。"));
            mTvUserVipLevel.setText("VIP 1");
        } else if (pId == 4) {
            mLlLayout1.addView(createDescriptionView("VIP在账号交易系统中购买小号，最高可享受3%的官方补贴。购号补贴将在账号交易成功后，通过平台币的方式发放到VIP买家账户。"));
            mTvUserVipLevel.setText("VIP 1");
        } else if (pId == 5) {
            mLlLayout1.addView(createDescriptionView("每次您升级到新的VIP时，都会奖励您一份升级礼包，升级奖励包含但不限：代金券、平台币等。"));
            mLlLayout1.addView(createDescriptionView("奖励需在当前页面领取，发放时会有短信或app系统消息通知。"));
            mTvUserVipLevel.setText("VIP 1");
        } else if (pId == 6) {
            mLlLayout1.addView(createDescriptionView("每月可获得一份会员专属大礼包。"));
            mLlLayout1.addView(createDescriptionView("奖励需在当前页面领取，发放时会有短信或app系统消息通知。"));
            mTvUserVipLevel.setText("VIP 2");
        } else if (pId == 7) {
            mLlLayout1.addView(createDescriptionView("在您生日时，将会收到我么送出的生日代金券。"));
            mLlLayout1.addView(createDescriptionView("生日是以您实名认证的日期为准，未填写的VIP，可到个人中心点击头像进行实名认证。"));
            mTvUserVipLevel.setText("VIP 5");
        } else if (pId == 8) {
            mLlLayout1.addView(createDescriptionView("在以下节日（元旦、春节、端午、中秋）都会发放价值50～200元的代金券。 "));
            mLlLayout1.addView(createDescriptionView("代金券需在当前页面领取，节日后7天内可领取，发放时会有短信或app系统消息通知。"));
            mTvUserVipLevel.setText("VIP 7");
        }
    }

    private View createDescriptionView(String description) {
        View view = LayoutInflater.from(_mActivity).inflate(R.layout.layout_vip_level_privilege_description, null);
        TextView mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText(description);
        return view;
    }


    private View createDetailLayoutById_9() {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_item_vip_privilege_detail_9, null);
        return itemView;
    }

    private View createDetailLayoutById_10() {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_item_vip_privilege_detail_10, null);
        return itemView;
    }

    private View createDetailLayoutById_11() {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_item_vip_privilege_detail_11, null);
        return itemView;
    }

    private View createDetailLayoutById_12() {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_item_vip_privilege_detail_10, null);
        return itemView;
    }


    private LinearLayout mLlRewardContainer3;
    private Button       mBtnAction3;

    private View createDetailLayoutById_3() {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_item_vip_privilege_detail_3, null);
        mLlRewardContainer3 = itemView.findViewById(R.id.ll_reward_container);
        mBtnAction3 = itemView.findViewById(R.id.btn_action);
        return itemView;
    }

    private void setPid3LayoutData(UserCommonRewardInfoVo.DataBean data) {
        if (mLlRewardContainer3 == null)
            return;
        if (mBtnAction3 == null)
            return;
        mLlRewardContainer3.removeAllViews();
        boolean isCanTap = false;
        if (data != null && data.getReward_data() != null) {
            int index = 0;
            for (UserCommonRewardInfoVo.RewardDataBean rewardDataBean : data.getReward_data()) {
                index++;
                LinearLayout layout = new LinearLayout(_mActivity);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                TextView tv1 = new TextView(_mActivity);
                tv1.setTextSize(13);
                tv1.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_6a6a6a));
                tv1.setGravity(Gravity.CENTER);
                tv1.setText(rewardDataBean.getName());
                LinearLayout.LayoutParams paramsTv1 = new LinearLayout.LayoutParams(0, ScreenUtil.dp2px(_mActivity, 36), 1);
                layout.addView(tv1, paramsTv1);

                View line1 = new View(_mActivity);
                line1.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_ebebeb));
                LinearLayout.LayoutParams paramsLine1 = new LinearLayout.LayoutParams(ScreenUtil.dp2px(_mActivity, 1), ViewGroup.LayoutParams.MATCH_PARENT);
                layout.addView(line1, paramsLine1);

                TextView tv2 = new TextView(_mActivity);
                tv2.setTextSize(13);
                tv2.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_6a6a6a));
                tv2.setGravity(Gravity.CENTER);
                tv2.setText(rewardDataBean.getReward());
                LinearLayout.LayoutParams paramsTv2 = new LinearLayout.LayoutParams(0, ScreenUtil.dp2px(_mActivity, 36), 1);
                layout.addView(tv2, paramsTv2);

                View line2 = new View(_mActivity);
                line2.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_ebebeb));
                LinearLayout.LayoutParams paramsLine2 = new LinearLayout.LayoutParams(ScreenUtil.dp2px(_mActivity, 1), ViewGroup.LayoutParams.MATCH_PARENT);
                layout.addView(line2, paramsLine2);

                TextView tv3 = new TextView(_mActivity);
                tv3.setTextSize(13);
                tv3.setGravity(Gravity.CENTER);
                if (rewardDataBean.getStatus() == 0) {
                    tv3.setText("");
                } else if (rewardDataBean.getStatus() == -1) {
                    tv3.setText("未达到");
                    tv3.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_9b9b9b));
                } else if (rewardDataBean.getStatus() == 10) {
                    tv3.setText("已领取");
                    tv3.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_9b9b9b));
                } else if (rewardDataBean.getStatus() == 1) {
                    tv3.setText("可领取");
                    tv3.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff5400));
                    isCanTap = true;
                }
                LinearLayout.LayoutParams paramsTv3 = new LinearLayout.LayoutParams(0, ScreenUtil.dp2px(_mActivity, 36), 1);
                layout.addView(tv3, paramsTv3);

                mLlRewardContainer3.addView(layout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                if (index != data.getReward_data().size()) {
                    View line = new View(_mActivity);
                    line.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_ebebeb));
                    LinearLayout.LayoutParams paramsLine = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(_mActivity, 1));
                    mLlRewardContainer3.addView(line, paramsLine);
                }
            }
        }

        if (isCanTap) {
            mBtnAction3.setVisibility(View.VISIBLE);
            mBtnAction3.setBackgroundResource(R.drawable.ts_shape_0052fe_radius);
            mBtnAction3.setText("领取");
            mBtnAction3.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
            mBtnAction3.setOnClickListener(view -> {
                if (data != null) {
                    String type = data.getType();
                    getVipReward(type);
                }
            });
        } else {
            mBtnAction3.setVisibility(View.GONE);
        }
    }


    private View createDetailLayoutById_4() {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_item_vip_privilege_detail_4, null);
        return itemView;
    }

    private LinearLayout mLlRewardContainer5;
    private Button       mBtnAction5;

    private View createDetailLayoutById_5() {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_item_vip_privilege_detail_5, null);

        mLlRewardContainer5 = itemView.findViewById(R.id.ll_reward_container);
        mBtnAction5 = itemView.findViewById(R.id.btn_action);

        return itemView;
    }

    private void setPid5LayoutData(UserCommonRewardInfoVo.DataBean data) {
        if (mLlRewardContainer5 == null)
            return;
        if (mBtnAction5 == null)
            return;
        mLlRewardContainer5.removeAllViews();
        if (data == null) {
            return;
        }
        int vipLevel = 0;
        int vipStatus = 0;

        if (data.getReceive_info() != null) {
            vipLevel = data.getReceive_info().getVip_level();
            vipStatus = data.getReceive_info().getStatus();
        }
        if (data.getReward_data() != null) {
            for (UserCommonRewardInfoVo.RewardDataBean rewardDataBean : data.getReward_data()) {
                View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_item_vip_privilege_detail_5_item, null);

                ImageView mIv = itemView.findViewById(R.id.iv);
                LinearLayout mLlContainer = itemView.findViewById(R.id.ll_container);
                TextView mTvVip = itemView.findViewById(R.id.tv_vip);
                TextView mTvVipReward = itemView.findViewById(R.id.tv_vip_reward);

                mTvVip.setText("VIP " + rewardDataBean.getVip_level());
                mTvVipReward.setText(rewardDataBean.getVip_level() - vipLevel > 1 ? "？？？" : rewardDataBean.getReward());

                if (vipLevel == rewardDataBean.getVip_level()) {
                    //当前Vip等级
                    mIv.setVisibility(View.VISIBLE);
                    mTvVip.setBackgroundResource(R.drawable.ts_shape_gradient_big_radius_fe3764_fe994b);
                    mTvVip.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
                    mLlContainer.setBackgroundResource(R.drawable.ts_shape_ffeee4_big_radius);
                } else {
                    mIv.setVisibility(View.INVISIBLE);
                    mTvVip.setBackgroundResource(R.drawable.ts_shape_gradient_1c1201_494133);
                    mTvVip.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ebd3a4));
                    mLlContainer.setBackgroundResource(R.drawable.ts_shape_ebebeb_big_radius);
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.topMargin = ScreenUtil.dp2px(_mActivity, 16);
                mLlRewardContainer5.addView(itemView, params);
            }
        }

        mBtnAction5.setVisibility(View.VISIBLE);
        if (vipStatus == 1) {
            mBtnAction5.setBackgroundResource(R.drawable.ts_shape_0052fe_radius);
            mBtnAction5.setText("领取");
            mBtnAction5.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
            mBtnAction5.setOnClickListener(view -> {
                if (data != null) {
                    String type = data.getType();
                    getVipReward(type);
                }
            });
        } else if (vipStatus == 10) {
            mBtnAction5.setBackgroundResource(R.drawable.ts_shape_eeeeee_radius);
            mBtnAction5.setText("已领取当前奖励");
            mBtnAction5.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_848484));
            mBtnAction5.setOnClickListener(null);
        } else {
            mBtnAction5.setVisibility(View.GONE);
        }
    }

    private Button mBtnAction7;

    private View createDetailLayoutById_7() {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_item_vip_privilege_detail_7, null);
        TextView mTvCheckRealName = itemView.findViewById(R.id.tv_check_real_name);
        mBtnAction7 = itemView.findViewById(R.id.btn_action);
        mTvCheckRealName.setVisibility(UserInfoModel.getInstance().isSetCertification() ? View.GONE : View.VISIBLE);
        mTvCheckRealName.setOnClickListener(view -> {
            if (checkLogin()) {
                startFragment(new CertificationFragment());
            }
        });
        return itemView;
    }

    private void setPid7LayoutData(UserCommonRewardInfoVo.DataBean data) {
        if (mBtnAction7 == null)
            return;
        mBtnAction7.setVisibility(View.GONE);
        if (data == null || data.getReceive_info() == null) {
            return;
        }
        int vipStatus = data.getReceive_info().getStatus();
        if (vipStatus == 1) {
            mBtnAction7.setVisibility(View.VISIBLE);
            mBtnAction7.setBackgroundResource(R.drawable.ts_shape_0052fe_radius);
            mBtnAction7.setText("领取");
            mBtnAction7.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
            mBtnAction7.setOnClickListener(view -> {
                if (data != null) {
                    String type = data.getType();
                    getVipReward(type);
                }
            });
        } else if (vipStatus == 10) {
            mBtnAction7.setVisibility(View.VISIBLE);
            mBtnAction7.setBackgroundResource(R.drawable.ts_shape_eeeeee_radius);
            mBtnAction7.setText("已领取，明年生日再见~");
            mBtnAction7.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_848484));
            mBtnAction7.setOnClickListener(null);
        } else {
            mBtnAction7.setVisibility(View.GONE);
        }
    }


    private Button mBtnAction8;

    private View createDetailLayoutById_8() {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_item_vip_privilege_detail_8, null);
        mBtnAction8 = itemView.findViewById(R.id.btn_action);
        return itemView;
    }

    private CountDownTimerCopyFromAPI26 mDownTimerCopyFromAPI26;

    private void setPid8LayoutData(UserCommonRewardInfoVo.DataBean data) {
        if (mBtnAction8 == null)
            return;
        mBtnAction8.setVisibility(View.GONE);
        if (data == null) {
            return;
        }
        int vipStatus = data.getStatus();
        if (vipStatus == 1) {
            mBtnAction8.setVisibility(View.VISIBLE);
            mBtnAction8.setBackgroundResource(R.drawable.ts_shape_0052fe_radius);
            mBtnAction8.setText("领取");
            mBtnAction8.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
            mBtnAction8.setOnClickListener(view -> {
                if (data != null) {
                    String type = data.getType();
                    getVipReward(type);
                }
            });
            if (mDownTimerCopyFromAPI26 == null) {
                mDownTimerCopyFromAPI26 = new CountDownTimerCopyFromAPI26(data.getLeft_time() * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        millisUntilFinished = millisUntilFinished / 1000;
                        int h = (int) (millisUntilFinished / 3600);
                        int m = (int) ((millisUntilFinished % 3600) / 60);
                        int s = (int) ((millisUntilFinished % 3600) % 60);
                        String count = h + ":" + m + ":" + s;
                        mBtnAction8.setText("领取（" + count + "）");
                    }

                    @Override
                    public void onFinish() {
                        mBtnAction8.setVisibility(View.GONE);
                    }
                };
                mDownTimerCopyFromAPI26.start();
            }

        } else if (vipStatus == 10) {
            mBtnAction8.setVisibility(View.VISIBLE);
            mBtnAction8.setBackgroundResource(R.drawable.ts_shape_eeeeee_radius);
            mBtnAction8.setText("已领取当前奖励");
            mBtnAction8.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_848484));
            mBtnAction8.setOnClickListener(null);
            if (mDownTimerCopyFromAPI26 != null) {
                mDownTimerCopyFromAPI26.cancel();
            }
        } else {
            mBtnAction7.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDownTimerCopyFromAPI26 != null) {
            mDownTimerCopyFromAPI26.cancel();
        }
    }

    private void initData() {
        if (mViewModel == null) {
            return;
        }
        if (pId == 3) {
            //纪念日礼包
            mViewModel.getMemoryRewardInfo(new OnBaseCallback<UserCommonRewardInfoVo>() {
                @Override
                public void onSuccess(UserCommonRewardInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            setPid3LayoutData(data.getData());
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        } else if (pId == 5) {
            //会员升级奖励
            mViewModel.getUpgradeRewardInfo(new OnBaseCallback<UserCommonRewardInfoVo>() {
                @Override
                public void onSuccess(UserCommonRewardInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            setPid5LayoutData(data.getData());
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        } else if (pId == 6) {
            //会员礼包/每月奖励
            mViewModel.getMonthRewardInfo(new OnBaseCallback<UserCommonRewardInfoVo>() {
                @Override
                public void onSuccess(UserCommonRewardInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            setPid5LayoutData(data.getData());
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        } else if (pId == 7) {
            mViewModel.getBirthdayRewardInfo(new OnBaseCallback<UserCommonRewardInfoVo>() {
                @Override
                public void onSuccess(UserCommonRewardInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            setPid7LayoutData(data.getData());
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        } else if (pId == 8) {
            mViewModel.getHolidayRewardInfo(new OnBaseCallback<UserCommonRewardInfoVo>() {
                @Override
                public void onSuccess(UserCommonRewardInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            setPid8LayoutData(data.getData());
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void getVipReward(String type) {
        if (mViewModel != null) {
            mViewModel.getReceiveVipReward(type, new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            Toaster.show("领取成功");
                            initData();
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }
}

