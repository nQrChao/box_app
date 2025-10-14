package com.zqhy.app.core.view.recycle_new;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.google.android.flexbox.FlexboxLayout;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.recycle.XhGameNewRecycleListVo;
import com.zqhy.app.core.data.model.recycle.XhRecycleCardListVo;
import com.zqhy.app.core.data.model.recycle.XhRecycleCouponListVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.currency.CurrencyMainFragment;
import com.zqhy.app.core.vm.recycle.RecycleViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.CountDownTimerCopyFromAPI26;
import com.zqhy.app.widget.PhoneCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leeham2734
 * @date 2021/6/17-15:18
 * @description
 */
public class XhNewRecycleDetailFragment extends BaseFragment<RecycleViewModel> {

    public static XhNewRecycleDetailFragment newInstance(XhGameNewRecycleListVo.DataBean data) {
        XhNewRecycleDetailFragment fragment = new XhNewRecycleDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "小号详情页";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_xh_new_recycle_detail;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_container;
    }

    private final int LAYOUT_OPERATION = 1;
    private final int LAYOUT_SUCCEED = 2;

    private int LAYOUT_STATE = LAYOUT_OPERATION;

    private XhGameNewRecycleListVo.DataBean data;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            data = (XhGameNewRecycleListVo.DataBean) getArguments().getSerializable("data");
        }
        super.initView(state);
        initActionBackBarAndTitle("小号回收");
        bindViews();
        showLayout();
        if (data == null) {
            showErrorTag1();
        } else {
            initData();
        }
    }

    private LinearLayout mLlXhRecycleOperation;
    private ImageView mIvGameIcon;
    private TextView mTvGameName;
    private TextView mTvGameIntro;
    private TextView mTvXhAccount;
    private TextView tv_djq;
    private TextView tv_game_suffix;
    private TextView mTvXhAccountAmount;
    private FlexboxLayout mFlexBoxLayout;
    private FlexboxLayout mFlexBoxLayout1;
    private LinearLayoutCompat select_1;
    private LinearLayoutCompat select_2;
    private TextView mBtnConfirm;
    private TextView tv_select1;
    private TextView tv_select2;
    private TextView text_more_1;
    private TextView text_more_2;
    private TextView tv_amount;
    private TextView tv_xh_recycle_price;
    private RecyclerView recycler_view;
    private String normal_url = "";
    private String discount_url = "";

    //通用券
    List<XhRecycleCouponListVo.DataBean> list1;
    //0.1折券
    List<XhRecycleCouponListVo.DataBean> list2;
    private LinearLayout mLlXhRecycleSucceed;
    private TextView mBtnBack;

    private int select = 0;
    private BaseRecyclerAdapter mAdapter;

    private void bindViews() {
        mLlXhRecycleOperation = findViewById(R.id.ll_xh_recycle_operation);
        mIvGameIcon = findViewById(R.id.iv_game_icon);
        mTvGameName = findViewById(R.id.tv_game_name);
        mTvGameIntro = findViewById(R.id.tv_game_intro);
        mTvXhAccount = findViewById(R.id.tv_xh_account);
        tv_game_suffix = findViewById(R.id.tv_game_suffix);
        tv_xh_recycle_price = findViewById(R.id.tv_xh_recycle_price);
        tv_djq = findViewById(R.id.tv_djq);
        mTvXhAccountAmount = findViewById(R.id.tv_xh_account_amount);
        mFlexBoxLayout = findViewById(R.id.flex_box_layout);
        mFlexBoxLayout1 = findViewById(R.id.flex_box_layout1);
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mLlXhRecycleSucceed = findViewById(R.id.ll_xh_recycle_succeed);
        mBtnBack = findViewById(R.id.btn_back);
        tv_amount = findViewById(R.id.tv_amount);
        recycler_view = findViewById(R.id.recycler_view);

        tv_select1 = findViewById(R.id.tv_select1);
        tv_select2 = findViewById(R.id.tv_select2);
        select_1 = findViewById(R.id.select_1);
        select_2 = findViewById(R.id.select_2);
        text_more_1 = findViewById(R.id.text_more_1);
        text_more_2 = findViewById(R.id.text_more_2);

        findViewById(R.id.iv_back).setOnClickListener(v -> {
            pop();
        });

        findViewById(R.id.tv_rule).setOnClickListener(v -> {
            BrowserActivity.newInstance(_mActivity, Constants.URL_XH_RECYCLE_RULE);
        });

        text_more_1.setOnClickListener(view -> {
            if (normal_url.isEmpty()) {
                return;
            }
            BrowserActivity.newInstance(_mActivity, normal_url);
        });

        text_more_2.setOnClickListener(view -> {
            if (discount_url.isEmpty()) {
                return;
            }
            BrowserActivity.newInstance(_mActivity, discount_url);
        });


        select_1.setOnClickListener(view -> {
            // 通用券
            select_1.setBackgroundResource(R.drawable.shape_xh_d3cafe_10_radius_line);
            select_2.setBackgroundResource(R.drawable.shape_xh_f2f2f2_10_radius_line);
            text_more_1.setVisibility(View.VISIBLE);
            mFlexBoxLayout.setVisibility(View.VISIBLE);
            mFlexBoxLayout1.setVisibility(View.GONE);
            text_more_2.setVisibility(View.GONE);
            select = 1;
            tv_select1.setText("已选");
            tv_select1.setTextColor(Color.parseColor("#814AF2"));
            tv_select1.setBackgroundResource(R.drawable.shape_xh_e9e4ff_60_radius);
            tv_select2.setTextColor(Color.parseColor("#232323"));
            tv_select2.setText("选择");
            tv_select2.setBackgroundResource(R.drawable.shape_xh_999999_60_radius_line);
        });
        select_2.setOnClickListener(view -> {
            //0.1折券
            select_2.setBackgroundResource(R.drawable.shape_xh_d3cafe_10_radius_line);
            select_1.setBackgroundResource(R.drawable.shape_xh_f2f2f2_10_radius_line);
            mFlexBoxLayout1.setVisibility(View.VISIBLE);
            text_more_2.setVisibility(View.VISIBLE);
            mFlexBoxLayout.setVisibility(View.GONE);
            text_more_1.setVisibility(View.GONE);
            select = 2;
            tv_select2.setText("已选");
            tv_select2.setTextColor(Color.parseColor("#814AF2"));
            tv_select2.setBackgroundResource(R.drawable.shape_xh_e9e4ff_60_radius);
            tv_select1.setTextColor(Color.parseColor("#232323"));
            tv_select1.setText("选择");
            tv_select1.setBackgroundResource(R.drawable.shape_xh_999999_60_radius_line);
        });


        tv_djq.setOnClickListener(view -> {
            if (checkLogin()) {
                startFragment(new CurrencyMainFragment());
            }
        });
        mBtnConfirm.setOnClickListener(view -> {
            if (checkLogin()) {
                UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
                if (userInfoBean.isIs_oversea_mobile()) {
                    //checkRecycle("");
                    showConfirmDialog("");
                } else {
                    if (checkUserBindPhoneTips1()) {
                        showCodeDialog();
                    }
                }
            }
        });
        mBtnBack.setOnClickListener(v -> {
            pop();
        });

        if (data != null) {
            GlideUtils.loadRoundImage(_mActivity, data.getGameicon(), mIvGameIcon);
            mTvGameName.setText(data.getGamename());
            tv_game_suffix.setText(data.getOtherGameName());
            mTvGameIntro.setText(data.getGenre_str());
            mTvXhAccount.setText(data.getXh_showname() + " 有效实付：" + data.getSum_rmb_total());
            StringBuilder sb = new StringBuilder(data.getSum_rmb_total() + "");
            SpannableString ss = new SpannableString(sb);
            //            ss.setSpan(new RelativeSizeSpan(1.2f), 1, 1 + String.valueOf(data.getSum_rmb_total()).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new StyleSpan(Typeface.BOLD), 1, String.valueOf(data.getSum_rmb_total()).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTvXhAccountAmount.setText(ss);


            StringBuilder sb1 = new StringBuilder("回收成功后，将获得" + data.getTotal() + "福利币");
            SpannableString ss1 = new SpannableString(sb1);
            ss1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF5A5A")), 9, ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_xh_recycle_price.setText(ss1);

            tv_amount.setText(String.valueOf(data.getTotal()));
        }

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("确认回收后，该游戏此小号全区服角色将一\n并回收，归属平台所有。24小时内可赎回");
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), spannableStringBuilder.length() - 8, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((TextView)findViewById(R.id.tv_tips)).setText(spannableStringBuilder);

        recycler_view.setLayoutManager(new LinearLayoutManager(_mActivity));

        mAdapter = new BaseRecyclerAdapter.Builder()
                .bind(XhRecycleCardListVo.DataBean.class, new XhCardDataItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);

        recycler_view.setAdapter(mAdapter);
    }

    private void addCouponViews(List<XhRecycleCouponListVo.DataBean> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        mFlexBoxLayout.removeAllViews();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            XhRecycleCouponListVo.DataBean data = list.get(i);

            View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.item_layout_xh_recycle_coupon, null);
            TextView mTvCount = itemView.findViewById(R.id.tv_count);
            TextView mTvCouponAmount = itemView.findViewById(R.id.tv_coupon_amount);
            TextView mTvCouponContent = itemView.findViewById(R.id.tv_coupon_content);

            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin = ScreenUtil.dp2px(_mActivity, 12);
            params.bottomMargin = ScreenUtil.dp2px(_mActivity, 12);

            mTvCouponAmount.setText(String.valueOf(data.getAmount()));
            mTvCouponContent.setText(data.getName());
            mTvCount.setText("x" + data.getCount());

            mFlexBoxLayout.addView(itemView, params);
        }
    }

    private void addCouponViews1(List<XhRecycleCouponListVo.DataBean> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        mFlexBoxLayout1.removeAllViews();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            XhRecycleCouponListVo.DataBean data = list.get(i);

            View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.item_layout_xh_recycle_coupon1, null);
            TextView mTvCount = itemView.findViewById(R.id.tv_count);
            TextView mTvCouponAmount = itemView.findViewById(R.id.tv_coupon_amount);
            TextView mTvCouponContent = itemView.findViewById(R.id.tv_coupon_content);

            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin = ScreenUtil.dp2px(_mActivity, 12);
            params.bottomMargin = ScreenUtil.dp2px(_mActivity, 12);

            mTvCouponAmount.setText(String.valueOf(data.getAmount()));
            mTvCouponContent.setText(data.getName());
            mTvCount.setText("x" + data.getCount());

            mFlexBoxLayout1.addView(itemView, params);
        }
    }

    private void showLayout() {
        if (LAYOUT_STATE == LAYOUT_OPERATION) {
            mLlXhRecycleOperation.setVisibility(View.VISIBLE);
            mBtnConfirm.setVisibility(View.VISIBLE);
            mLlXhRecycleSucceed.setVisibility(View.GONE);
        } else if (LAYOUT_STATE == LAYOUT_SUCCEED) {
            mLlXhRecycleOperation.setVisibility(View.GONE);
            mBtnConfirm.setVisibility(View.GONE);
            mLlXhRecycleSucceed.setVisibility(View.VISIBLE);
            setFragmentResult(RESULT_OK, null);
        }
    }

    CustomDialog codeDialog;
    private TextView mTvCodeTips;
    private PhoneCode mPhoneCode;
    private TextView mTvCodeCountDown;
    private TextView mBtnRetryCode;

    private void showCodeDialog() {
        if (codeDialog == null) {
            codeDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_xh_new_recycle, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            mTvCodeTips = codeDialog.findViewById(R.id.tv_code_tips);
            mPhoneCode = codeDialog.findViewById(R.id.phoneCode);
            mTvCodeCountDown = codeDialog.findViewById(R.id.tv_code_count_down);
            mBtnRetryCode = codeDialog.findViewById(R.id.btn_retry_code);
            codeDialog.setCanceledOnTouchOutside(false);

            mTvCodeTips.setText("将向" + CommonUtils.hideTelNum(UserInfoModel.getInstance().getBindMobile()) + "发送短信");
            mTvCodeCountDown.setVisibility(View.INVISIBLE);

            mPhoneCode.setOnInputListener(new PhoneCode.OnInputListener() {
                @Override
                public void onSuccess(String codes) {
                    //验证验证码
                    //checkRecycle(codes);
                    showConfirmDialog(codes);
                }

                @Override
                public void onInput() {

                }
            });
            mBtnRetryCode.setText("获取验证码");
            mBtnRetryCode.setOnClickListener(v -> {
                getRecycleCode();
            });
        }
        codeDialog.show();
    }

    private void getRecycleCode() {
        if (mViewModel != null && data != null && !isSendCodeCD) {
            int gameid = data.getGameid();
            mViewModel.getRecycleCode(gameid, new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            sendCode();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private boolean isSendCodeCD = false;

    private void sendCode() {
        GradientDrawable gd1 = new GradientDrawable();
        gd1.setCornerRadius(6 * density);
        gd1.setColor(Color.parseColor("#C1C1C1"));
        mBtnRetryCode.setBackground(gd1);

        addCountDownTimer(new CountDownTimerCopyFromAPI26(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isSendCodeCD = true;
                mTvCodeCountDown.setVisibility(View.VISIBLE);
                mTvCodeCountDown.setText((millisUntilFinished / 1000) + "s可重发");
                mBtnRetryCode.setEnabled(false);
            }

            @Override
            public void onFinish() {
                mTvCodeCountDown.setVisibility(View.INVISIBLE);
                GradientDrawable gd1 = new GradientDrawable();
                gd1.setCornerRadius(6 * density);
                gd1.setColor(ContextCompat.getColor(_mActivity, R.color.color_0052ef));
                mBtnRetryCode.setBackground(gd1);
                mBtnRetryCode.setText("重新获取");
                mBtnRetryCode.setEnabled(true);
                isSendCodeCD = false;
            }
        }.start());
    }

    private List<CountDownTimerCopyFromAPI26> countDownTimerList;

    private void addCountDownTimer(CountDownTimerCopyFromAPI26 countDownTimer) {
        if (countDownTimerList == null) {
            countDownTimerList = new ArrayList<>();
        }
        countDownTimerList.add(countDownTimer);
    }

    private void clearCountDownTimerList() {
        if (countDownTimerList != null) {
            for (CountDownTimerCopyFromAPI26 countDownTimer : countDownTimerList) {
                countDownTimer.cancel();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearCountDownTimerList();
    }

    private void initData() {
        if (mViewModel != null && data != null) {
            String xh_username = data.getXh_username();
            showSuccess();
            /*mViewModel.getRecycleCouponList(xh_username, new OnBaseCallback<XhRecycleCouponListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();

                }

                @Override
                public void onSuccess(XhRecycleCouponListVo data) {
                    if (data.getData() != null) {
                        if (data.isStateOK()) {
                            showSuccess();
                            normal_url = data.getData().getNormal_url();
                            discount_url = data.getData().getDiscount_url();
                            list1 = data.getData().getList().getNormal();
                            list2 = data.getData().getList().getOff99();
                            addCouponViews(list1);
                            addCouponViews1(list2);
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });*/
            /*mViewModel.recycleCardList(xh_username, new OnBaseCallback<XhRecycleCardListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();

                }

                @Override
                public void onSuccess(XhRecycleCardListVo data) {
                    if (data.getData() != null) {
                        if (data.isStateOK()) {
                            showSuccess();
                            normal_url = data.getData().getNormal_url();
                            discount_url = data.getData().getDiscount_url();
                            *//*list1 = data.getData().getList().getNormal();
                            list2 = data.getData().getList().getOff99();
                            addCouponViews(list1);
                            addCouponViews1(list2);*//*
                            mAdapter.setDatas(data.getData().getList());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });*/
        }
    }

    private void checkRecycle(String code) {
        if (mViewModel != null && data != null) {
            String xh_username = data.getXh_username();
            mViewModel.xhRecycleAction(xh_username, code, new OnBaseCallback() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onBefore() {
                    super.onBefore();
                    loading();
                }

                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (codeDialog != null) {
                                codeDialog.dismiss();
                            }
                            Toaster.show( "回收成功");
                            mViewModel.refreshUserData();
                            LAYOUT_STATE = LAYOUT_SUCCEED;
                            showLayout();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void showConfirmDialog(String code) {
        CustomDialog confirmDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_xh_new_recycle_confirm, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);

        ImageView iv_icon = confirmDialog.findViewById(R.id.iv_game_icon);
        TextView tv_name = confirmDialog.findViewById(R.id.tv_game_name);
        TextView tv_suffix = confirmDialog.findViewById(R.id.tv_game_suffix);
        TextView tv_account = confirmDialog.findViewById(R.id.tv_xh_account);
        TextView tv_amount = confirmDialog.findViewById(R.id.tv_amount);
        TextView tv_amount_1 = confirmDialog.findViewById(R.id.tv_amount_1);
        TextView tv_tips = confirmDialog.findViewById(R.id.tv_tips);

        if (data != null) {
            GlideUtils.loadRoundImage(_mActivity, data.getGameicon(), iv_icon);
            tv_name.setText(data.getGamename());
            tv_suffix.setText(data.getOtherGameName());
            tv_account.setText("小号：" + data.getXh_showname());

            tv_amount.setText(String.valueOf(data.getTotal()));
            tv_amount_1.setText(data.getTotal() + "福利币");

            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("确认回收后，该游戏此小号全区服角色将一\n并回收，归属平台所有。24小时内可赎回");
            spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), spannableStringBuilder.length() - 8, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_tips.setText(spannableStringBuilder);
        }


        confirmDialog.findViewById(R.id.iv_close).setOnClickListener(v -> {
            if (confirmDialog != null && confirmDialog.isShowing()) confirmDialog.dismiss();
        });
        confirmDialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if (confirmDialog != null && confirmDialog.isShowing()) confirmDialog.dismiss();
            checkRecycle(code);
        });
        confirmDialog.setCanceledOnTouchOutside(false);
        confirmDialog.show();
    }

    public void showTipDialog() {
        CustomDialog dialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_recycle_card_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        Button mBtnCancel = dialog.findViewById(R.id.btn_cancel);

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        mBtnCancel.setOnClickListener(view -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
