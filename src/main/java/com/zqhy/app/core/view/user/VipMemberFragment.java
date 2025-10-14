package com.zqhy.app.core.view.user;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.box.other.hjq.toast.Toaster;
import com.google.android.flexbox.FlexboxLayout;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.user.PayInfoVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.model.user.VipMemberInfoVo;
import com.zqhy.app.core.data.model.user.VipMemberTypeVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.AbsPayBuyFragment;
import com.zqhy.app.core.view.kefu.KefuCenterFragment;
import com.zqhy.app.core.view.user.welfare.MyCouponsListFragment;
import com.zqhy.app.core.vm.user.VipMemberViewModel;
import com.zqhy.app.glide.GlideCircleTransform;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.TextFontsUtils;
import com.zqhy.app.widget.GradientColorTextView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author leeham
 * @date 2020/3/25-19:37
 * @description 2020.11.09 此页面改名未巴兔月卡
 */
public class VipMemberFragment extends AbsPayBuyFragment<VipMemberViewModel> implements View.OnClickListener {
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_vip_member;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "月卡";
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private VipMemberTypeVo.DataBean targetVipMemberTypeVo;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle(getAppVipMonthName() + "月卡");
        bindViews();
        initData();
        getVipTypesData();
    }

    @Override
    protected int getPayAction() {
        return PAY_ACTION_VIP_MEMBER;
    }

    private ImageView             mIvBack;
    private AppCompatImageView    mProfileImage;
    private AppCompatTextView     mTvUserName;
    private AppCompatTextView     mTvUserVipStatus;
    private AppCompatTextView     mTvVipMemberCount;
    private Button                mBtnConfirmPayVipMember;
    private AppCompatTextView     mBtnGetVipVouchers;
    private FlexboxLayout         mFlexVipMemberTypeContainer;
    private AppCompatTextView     mTvApp2;
    private GradientColorTextView mTvAppCard;

    private void bindViews() {
        mIvBack = findViewById(R.id.iv_back);
        mProfileImage = findViewById(R.id.profile_image);
        mTvUserName = findViewById(R.id.tv_user_name);
        mTvUserVipStatus = findViewById(R.id.tv_user_vip_status);
        mTvVipMemberCount = findViewById(R.id.tv_vip_member_count);
        mBtnConfirmPayVipMember = findViewById(R.id.btn_confirm_pay_vip_member);
        mBtnGetVipVouchers = findViewById(R.id.btn_get_vip_vouchers);
        mFlexVipMemberTypeContainer = findViewById(R.id.flex_vip_member_type_container);


        mTvAppCard = findViewById(R.id.tv_app_card);
        String text = getAppVipMonthName() + "月卡";
        TextFontsUtils.setTextWithFont1(_mActivity, mTvAppCard, text);

        mTvApp2 = findViewById(R.id.tv_app_2);
        mTvApp2.setText(getAppNameByXML(R.string.string_dyx_vip_liability));

        setUserInfo();

        mIvBack.setOnClickListener(view -> pop());
        mBtnConfirmPayVipMember.setOnClickListener(this);
    }

    private void setUserInfo() {
        if (checkLogin()) {
            UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
            GlideApp.with(_mActivity)
                    .asBitmap()
                    .load(userInfo.getUser_icon())
                    .placeholder(R.mipmap.ic_user_login)
                    .error(R.mipmap.ic_user_login)
                    .transform(new GlideCircleTransform(_mActivity, (int) (0 * ScreenUtil.getScreenDensity(_mActivity))))
                    .into(mProfileImage);
            mTvUserName.setText(userInfo.getUser_nickname());
            boolean isVipMember = UserInfoModel.getInstance().isVipMember();
            mTvUserVipStatus.setText(isVipMember ? "到期：" + UserInfoModel.getInstance().getVipMemberLastDate() : "您暂未开通月卡");
            mBtnConfirmPayVipMember.setText(isVipMember ? "立即续费" : "立即开通");
        }
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        setUserInfo();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_kefu:
                startFragment(new KefuCenterFragment());
                break;
            case R.id.btn_confirm_pay_vip_member:
                if (targetVipMemberTypeVo != null) {
                    showBuyVipMemberDialog(targetVipMemberTypeVo.getType_id());
                }
                break;
            default:
                break;
        }
    }


    private AppCompatTextView mTvVipMemberUsername;
    private AppCompatTextView mTvVipMemberType;
    private AppCompatTextView mTvVipMemberBonus;
    private RelativeLayout    mRlPayAlipay;
    private ImageView         mIvPayAlipay;
    private RelativeLayout    mRlPayWechat;
    private ImageView         mIvPayWechat;
    private Button            mBtnConfirm;
    private CustomDialog      dialog;


    private void showBuyVipMemberDialog(int vipType) {
        if (dialog == null) {
            dialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_buy_vip_member, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
            mTvVipMemberUsername = dialog.findViewById(R.id.tv_vip_member_username);
            mTvVipMemberType = dialog.findViewById(R.id.tv_vip_member_type);
            mTvVipMemberBonus = dialog.findViewById(R.id.tv_vip_member_bonus);
            mRlPayAlipay = dialog.findViewById(R.id.rl_pay_alipay);
            mIvPayAlipay = dialog.findViewById(R.id.iv_pay_alipay);
            mRlPayWechat = dialog.findViewById(R.id.rl_pay_wechat);
            mIvPayWechat = dialog.findViewById(R.id.iv_pay_wechat);
            mBtnConfirm = dialog.findViewById(R.id.btn_confirm);

            mRlPayAlipay.setOnClickListener(view -> {
                PAY_TYPE = PAY_TYPE_ALIPAY;
                mIvPayAlipay.setImageResource(R.mipmap.ic_vip_member_pay_check);
                mIvPayWechat.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
            });
            mRlPayWechat.setOnClickListener(view -> {
                PAY_TYPE = PAY_TYPE_WECHAT;
                mIvPayAlipay.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
                mIvPayWechat.setImageResource(R.mipmap.ic_vip_member_pay_check);
            });

            mBtnConfirm.setOnClickListener(view -> {
                buyVipMember(vipType, PAY_TYPE);
            });

        }
        UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
        mTvVipMemberUsername.setText(userInfo.getUsername());
        if (targetVipMemberTypeVo != null) {
            mTvVipMemberType.setText("购买类型：" + targetVipMemberTypeVo.getName() + "（" + targetVipMemberTypeVo.getDays() + "天）");
            if (targetVipMemberTypeVo.getFree_days() > 0) {
                mTvVipMemberBonus.setVisibility(View.VISIBLE);
                mTvVipMemberBonus.setText("额外赠送" + targetVipMemberTypeVo.getFree_days() + "天");
            } else {
                mTvVipMemberBonus.setVisibility(View.GONE);
            }

        }


        mRlPayAlipay.performClick();
        dialog.show();
    }

    private void initData() {
        if (mViewModel != null) {
            mViewModel.getVipMemberInfo(new OnBaseCallback<VipMemberInfoVo>() {
                @Override
                public void onSuccess(VipMemberInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                mBtnGetVipVouchers.setVisibility(UserInfoModel.getInstance().isVipMember() ? View.VISIBLE : View.GONE);
                                if (data.getData().is_get_vip_member_voucher()) {
                                    //已领取代金券
                                    mBtnGetVipVouchers.setBackgroundResource(R.drawable.ts_shape_big_radius_cccccc);
                                    mBtnGetVipVouchers.setText("已领");
                                    mBtnGetVipVouchers.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
                                    mBtnGetVipVouchers.setOnClickListener(null);
                                } else {
                                    //未领取代金券
                                    mBtnGetVipVouchers.setBackgroundResource(R.drawable.ts_shape_big_radius_1d1302);
                                    mBtnGetVipVouchers.setText("领取");
                                    mBtnGetVipVouchers.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_f2e2c1));
                                    mBtnGetVipVouchers.setOnClickListener(view -> {
                                        getVipMemberVoucher();
                                    });
                                }

                                StringBuilder sb = new StringBuilder();
                                sb.append("已有");
                                int startIndex = sb.length();
                                String count = CommonUtils.formatNumber(data.getData().getVip_member_total_count());
                                sb.append(count);
                                int endIndex = sb.length();
                                sb.append("人开通");

                                SpannableString ss = new SpannableString(sb.toString());
                                ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_fa2f2a)), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                                ss.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                                mTvVipMemberCount.setText(ss);
                                mTvVipMemberCount.setMovementMethod(LinkMovementMethod.getInstance());
                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void getVipTypesData() {
        if (mViewModel != null) {
            mViewModel.getVipTypes(new OnBaseCallback<VipMemberTypeVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }

                @Override
                public void onSuccess(VipMemberTypeVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null && !data.getData().isEmpty()) {
                                post(() -> {
                                    int position = 0;
                                    mFlexVipMemberTypeContainer.removeAllViews();
                                    for (VipMemberTypeVo.DataBean dataBean : data.getData()) {
                                        View itemView = getItemVipMemberTypeView(dataBean);
                                        int width = (mFlexVipMemberTypeContainer.getRight() - mFlexVipMemberTypeContainer.getLeft() - (int) (16 * density)) / 3;
                                        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(width, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                                        params.rightMargin = ((position + 1) % 3 != 0) ? (int) (8 * density) : 0;
                                        params.bottomMargin = ScreenUtil.dp2px(_mActivity, 14);
                                        mFlexVipMemberTypeContainer.addView(itemView, params);
                                        position++;
                                    }
                                    for (Integer type_id : mVipLayoutViewMap.keySet()) {
                                        View targetView = mVipLayoutViewMap.get(type_id);
                                        targetView.performClick();
                                        break;
                                    }
                                });
                            }
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }


    private Map<Integer, View> mVipLayoutViewMap = new HashMap<>();

    private View getItemVipMemberTypeView(VipMemberTypeVo.DataBean dataBean) {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.item_vip_member_type, null);

        LinearLayout mLlVipMemberType = itemView.findViewById(R.id.ll_vip_member_type);
        AppCompatTextView mTvVipMemberFreeDays = itemView.findViewById(R.id.tv_vip_member_free_days);
        AppCompatTextView mTvVipMemberName = itemView.findViewById(R.id.tv_vip_member_name);
        //        AppCompatTextView mTvVipMemberDays = itemView.findViewById(R.id.tv_vip_member_days);
        AppCompatTextView mTvVipMemberAmount = itemView.findViewById(R.id.tv_vip_member_amount);
        AppCompatTextView mTvVipMemberTotalAmount = itemView.findViewById(R.id.tv_vip_member_total_amount);
        AppCompatTextView mTvVipMemberDescription = itemView.findViewById(R.id.tv_vip_member_description);


        mVipLayoutViewMap.put(dataBean.getType_id(), mLlVipMemberType);
        mLlVipMemberType.setTag(dataBean);
        mLlVipMemberType.setOnClickListener(view -> {
            VipMemberTypeVo.DataBean vipMemberTypeVo = (VipMemberTypeVo.DataBean) view.getTag();
            targetVipMemberTypeVo = vipMemberTypeVo;
            int target_type_id = vipMemberTypeVo.getType_id();
            for (Integer type_id : mVipLayoutViewMap.keySet()) {
                View targetView = mVipLayoutViewMap.get(type_id);
                targetView.setBackgroundResource(type_id == target_type_id ? R.drawable.ts_shape_vip_member_item_selected : R.drawable.ts_shape_vip_member_item_normal);
                try {
                    TextView tv = (TextView) targetView.getTag(R.id.tv_vip_member_total_amount);
                    tv.setTextColor(ContextCompat.getColor(_mActivity, type_id == target_type_id ? R.color.color_f26412 : R.color.color_727272));
                    AppCompatImageView mIvArrow = targetView.findViewById(R.id.iv_arrow);
                    mIvArrow.setVisibility(type_id == target_type_id ? View.VISIBLE : View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if (dataBean.isNewBenefit()) {
            mTvVipMemberFreeDays.setText("新人专享");
            mTvVipMemberFreeDays.setVisibility(View.VISIBLE);
            mTvVipMemberFreeDays.setBackgroundResource(R.drawable.ts_shape_vip_member_angle_selected);
        } else if (dataBean.getFree_days() != 0) {
            mTvVipMemberFreeDays.setText("额外送" + dataBean.getFree_days() + "天");
            mTvVipMemberFreeDays.setVisibility(View.VISIBLE);
            mTvVipMemberFreeDays.setBackgroundResource(R.drawable.ts_shape_vip_member_angle_selected);
        } else {
            mTvVipMemberFreeDays.setText("");
            mTvVipMemberFreeDays.setVisibility(View.INVISIBLE);
            mTvVipMemberFreeDays.setBackgroundResource(R.drawable.ts_shape_vip_member_angle_selected);
        }
        mTvVipMemberName.setText(dataBean.getName());
        //        mTvVipMemberDays.setText("(" + dataBean.getDays() + ")张");
        mTvVipMemberAmount.setText(String.valueOf(dataBean.getAmount()));
        mTvVipMemberTotalAmount.setText("价值" + String.valueOf(dataBean.getTotalAmount()) + "元");
        mLlVipMemberType.setTag(R.id.tv_vip_member_total_amount, mTvVipMemberTotalAmount);

        mTvVipMemberDescription.setText(dataBean.getAllDays() + "张代金券\n省" + dataBean.getReducedPrice() + "元");

        return itemView;
    }


    private void getVipMemberVoucher() {
        if (mViewModel != null) {
            mViewModel.getVipMemberVoucher(new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            initData();
                            showVoucherDialog();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void showVoucherDialog() {
        CustomDialog dialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_vip_member_voucher, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        Button mBtnConfirm = dialog.findViewById(R.id.btn_confirm);

        mBtnConfirm.setOnClickListener(view -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            //startFragment(GameWelfareFragment.newInstance(2));
            startFragment(new MyCouponsListFragment());
        });
        dialog.show();
    }


    private void buyVipMember(int vip_member_type, int vip_member_pay_type) {
        if (mViewModel != null) {
            mViewModel.buyVipMember(vip_member_type, vip_member_pay_type, new OnBaseCallback<PayInfoVo>() {
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
                public void onSuccess(PayInfoVo payInfoVo) {
                    if (payInfoVo != null) {
                        if (payInfoVo.isStateOK()) {
                            if (payInfoVo.getData() != null) {
                                showPayResultTipsDialog();
                                doPay(payInfoVo.getData());
                            }
                        } else {
                            Toaster.show( payInfoVo.getMsg());
                        }
                    }
                }
            });
        }
    }


    private void showPayResultTipsDialog() {
        AlertDialog dialog = new AlertDialog.Builder(_mActivity)
                .setTitle("提示")
                .setMessage("请确认支付是否完成")
                .setNegativeButton("重新支付", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .setPositiveButton("已完成支付", (dialogInterface, i) -> {
                    onPaySuccess();
                }).create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
        setDefaultSystemDialogTextSize(dialog);
    }

    @Override
    protected void onPaySuccess() {
        super.onPaySuccess();
        if (dialog != null) {
            dialog.dismiss();
        }
        if (mViewModel != null) {
            mViewModel.refreshUserData();
        }
    }

    @Override
    protected void onPayCancel() {
        super.onPayCancel();
    }
}
