package com.zqhy.app.core.view.rebate;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import com.chaoji.other.hjq.toast.Toaster;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.rebate.RebateInfoVo;
import com.zqhy.app.core.data.model.rebate.RebateItemVo;
import com.zqhy.app.core.data.model.rebate.RebateRevokeListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.utilcode.SizeUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.rebate.helper.RebateHelper;
import com.zqhy.app.core.vm.rebate.RebateViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.TimeUtils;
import com.zqhy.app.widget.listener.MyAppBarStateChangeListener;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/16
 */

public class RebateRecordItemFragment extends BaseFragment<RebateViewModel> implements View.OnClickListener {

    public static RebateRecordItemFragment newInstance(int rebate_type, String apply_id) {
        RebateRecordItemFragment fragment = new RebateRecordItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("rebate_type", rebate_type);
        bundle.putString("apply_id", apply_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static RebateRecordItemFragment newInstance(int rebate_type, int status) {
        RebateRecordItemFragment fragment = new RebateRecordItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("rebate_type", rebate_type);
        bundle.putInt("rebate_status", status);
        fragment.setArguments(bundle);
        return fragment;
    }


    private final int REBATE_STATUS_PENDING    = 1;
    private final int REBATE_STATUS_ACCEPTING  = 2;
    private final int REBATE_STATUS_COMPLETE   = 3;
    private final int REBATE_STATUS_FAILURE    = 4;
    private final int REBATE_STATUS_REVOCATION = 5;

    private int REBATE_STATUS = REBATE_STATUS_PENDING;

    private final int GAME_REBATE_RE_APPLY = 5007;

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_REBATE_RECORD_ITEM_STATE;
    }

    @Override
    protected String getUmengPageName() {
        return "返利详情页";
    }


    @Override
    protected String getStateEventTag() {
        return apply_id;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_rebate_record_item;
    }

    @Override
    public int getContentResId() {
        return R.id.rootView;
    }

    private String apply_id;
    private int    rebate_type;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            apply_id = getArguments().getString("apply_id");
            REBATE_STATUS = getArguments().getInt("rebate_status", REBATE_STATUS);
            rebate_type = getArguments().getInt("rebate_type");
        }
        super.initView(state);
        bindViews();
        initActionBackBarAndTitle("申请详情");
        getNetWordData();
    }

    private FrameLayout             mRootView;
    private AppBarLayout            mAppBarLayout;
    private CollapsingToolbarLayout mCollapsing;
    private ImageView               mIvImageRound;
    private ImageView               mIvImageSquare;
    private FrameLayout             mFlContainer;
    private LinearLayout            mLlGameTitle;
    private TextView                mTvCommonProblems;

    private TextView     mTvGameName;
    private TextView     mTvXhAccount;
    private TextView     mTvRechargeTime;
    private TextView     mTvRechargeAmount;
    private TextView     mTvGameServer;
    private TextView     mTvRoleName;
    private TableRow     mTrRoleId;
    private TextView     mTvStrRoleId;
    private TextView     mTvRoleId;
    private TextView     mTvApplyReward;
    private TextView     mTvMark;
    private TextView     mTvApplyTime;
    private Button       mBtnAction;
    private LinearLayout mLlTwoButtons;
    private Button       mBtnAction1;
    private Button       mBtnAction2;
    private TextView     mTitleBottomLine;

    private void bindViews() {
        mRootView = findViewById(R.id.rootView);
        mAppBarLayout = findViewById(R.id.appBarLayout);
        mCollapsing = findViewById(R.id.collapsing);
        mIvImageRound = findViewById(R.id.iv_image_round);
        mIvImageSquare = findViewById(R.id.iv_image_square);
        mFlContainer = findViewById(R.id.fl_container);
        mLlGameTitle = findViewById(R.id.ll_game_title);
        mTvCommonProblems = findViewById(R.id.tv_common_problems);
        mTitleBottomLine = findViewById(R.id.title_bottom_line);
        mTitleBottomLine.setVisibility(View.GONE);

        mTvGameName = findViewById(R.id.tv_game_name);
        mTvXhAccount = findViewById(R.id.tv_xh_account);
        mTvRechargeTime = findViewById(R.id.tv_recharge_time);
        mTvRechargeAmount = findViewById(R.id.tv_recharge_amount);
        mTvGameServer = findViewById(R.id.tv_game_server);
        mTvRoleName = findViewById(R.id.tv_role_name);
        mTrRoleId = findViewById(R.id.tr_role_id);
        mTvStrRoleId = findViewById(R.id.tv_str_role_id);
        mTvRoleId = findViewById(R.id.tv_role_id);
        mTvApplyReward = findViewById(R.id.tv_apply_reward);
        mTvMark = findViewById(R.id.tv_mark);
        mTvApplyTime = findViewById(R.id.tv_apply_time);
        mBtnAction = findViewById(R.id.btn_action);
        mLlTwoButtons = findViewById(R.id.ll_two_buttons);
        mBtnAction1 = findViewById(R.id.btn_action_1);
        mBtnAction2 = findViewById(R.id.btn_action_2);

        setCollapsingListener();
        setLayoutViews();

        mTvCommonProblems.setOnClickListener(this);
    }

    private RebateHelper rebateHelper;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_common_problems:
                if (rebateHelper == null) {
                    rebateHelper = new RebateHelper();
                }
                rebateHelper.showBTRebateProDialog(_mActivity);
                break;
            default:
                break;
        }
    }

    private void setCollapsingListener() {
        mAppBarLayout.addOnOffsetChangedListener(new MyAppBarStateChangeListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                float totalRange = appBarLayout.getTotalScrollRange();
                float verticalOffset = Math.abs(i);

                int alpha = Math.round(verticalOffset / totalRange * 255);
                String hex = Integer.toHexString(alpha).toUpperCase();
                if (hex.length() == 1) {
                    hex = "0" + hex;
                }
                String srtColorRes = "#" + hex + "FFFFFF";

                try {
                    mLlGameTitle.setBackgroundColor(Color.parseColor(srtColorRes));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.onOffsetChanged(appBarLayout, i);
            }

            @Override
            public void onStateChanged(AppBarLayout appBarLayout, MyAppBarStateChangeListener.State state) {
                switch (state) {
                    case EXPANDED:
                        setExpandedTitleView();
                        break;
                    case COLLAPSED:
                        setCollapsedTitleView();
                        break;
                    case IDLE:
                        setTitleColor(ContextCompat.getColor(_mActivity, R.color.color_818181));
                        mTvCommonProblems.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_818181));
                        break;
                    default:
                        break;
                }
            }
        });
        setExpandedTitleView();
    }

    private void setExpandedTitleView() {
        setTitleColor(ContextCompat.getColor(_mActivity, R.color.white));
        mTvCommonProblems.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
        mLlGameTitle.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.transparent));
        setActionBackBar(R.mipmap.ic_actionbar_back_white);
        setStatusBar(0x00cccccc);

        if (REBATE_STATUS == REBATE_STATUS_FAILURE || REBATE_STATUS == REBATE_STATUS_REVOCATION) {
            setTitleColor(ContextCompat.getColor(_mActivity, R.color.color_1b1b1b));
            mTvCommonProblems.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_1b1b1b));
            setActionBackBar(trecyclerview.com.mvvm.R.mipmap.ic_actionbar_back);
        }
    }

    private void setCollapsedTitleView() {
        setTitleColor(ContextCompat.getColor(_mActivity, R.color.color_1b1b1b));
        mTvCommonProblems.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_1b1b1b));
        mLlGameTitle.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.white));
        setActionBackBar(trecyclerview.com.mvvm.R.mipmap.ic_actionbar_back);
        setStatusBar(0xffcccccc);
    }


    private void setLayoutViews() {
        mFlContainer.removeAllViews();
        mFlContainer.addView(getContainerView());

        mBtnAction.setVisibility(View.VISIBLE);
        mLlTwoButtons.setVisibility(View.GONE);


        switch (REBATE_STATUS) {
            case REBATE_STATUS_PENDING:
                setLayoutStatusPending();
                break;
            case REBATE_STATUS_ACCEPTING:
                setLayoutStatusAccepting();
                break;
            case REBATE_STATUS_COMPLETE:
                setLayoutStatusComplete();
                break;
            case REBATE_STATUS_FAILURE:
                setLayoutStatusFailure();
                break;
            case REBATE_STATUS_REVOCATION:
                setLayoutStatusRevocation();
                break;
            default:
                mIvImageRound.setImageResource(R.mipmap.img_rebate_detail_round_1);
                mIvImageSquare.setImageResource(R.mipmap.img_rebate_detail_square_1);
                break;
        }
    }

    private void setLayoutStatusPending() {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(24 * density);
        mIvImageRound.setImageResource(R.mipmap.img_rebate_detail_round_1);
        mIvImageSquare.setImageResource(R.mipmap.img_rebate_detail_square_1);
        gd.setColor(Color.parseColor("#F6F5FF"));
        gd.setStroke((int) (1 * density), Color.parseColor("#8691F2"));
        mBtnAction.setTextColor(Color.parseColor("#737DF0"));
        mBtnAction.setText("如需重新申请，点击撤回");
        mBtnAction.setBackground(gd);
        mBtnAction.setOnClickListener(view -> {
            //撤回操作
            if (rebateRevokeList != null) {
                showRebateRecallDialog(apply_id, rebateRevokeList);
            }
        });
    }

    private void setLayoutStatusAccepting() {
        mIvImageRound.setImageResource(R.mipmap.img_rebate_detail_round_2);
        mIvImageSquare.setImageResource(R.mipmap.img_rebate_detail_square_2);
        mBtnAction.setVisibility(View.GONE);
    }

    private void setLayoutStatusComplete() {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(24 * density);
        mIvImageRound.setImageResource(R.mipmap.img_rebate_detail_round_3);
        mIvImageSquare.setImageResource(R.mipmap.img_rebate_detail_square_3);
        gd.setColor(Color.parseColor("#FFF3E7"));
        gd.setStroke((int) (1 * density), Color.parseColor("#FF8F19"));
        mBtnAction.setTextColor(Color.parseColor("#FF8F19"));
        mBtnAction.setText("有疑问，咨询客服");
        mBtnAction.setBackground(gd);
        mBtnAction.setOnClickListener(view -> {
            //咨询客服
            goKefuMain();
        });
    }

    private void setLayoutStatusFailure() {
        mIvImageRound.setImageResource(R.mipmap.img_rebate_detail_round_4);
        mIvImageSquare.setImageResource(R.mipmap.img_rebate_detail_square_4);
        mBtnAction.setVisibility(View.GONE);
        mLlTwoButtons.setVisibility(View.VISIBLE);

        GradientDrawable gd1 = new GradientDrawable();
        gd1.setCornerRadius(20 * density);
        gd1.setColor(Color.parseColor("#F3F3F3"));
        gd1.setStroke((int) (1 * density), Color.parseColor("#C0C0C0"));
        mBtnAction1.setBackground(gd1);

        GradientDrawable gd2 = new GradientDrawable();
        gd2.setCornerRadius(20 * density);
        gd2.setColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
        mBtnAction2.setBackground(gd2);

        mBtnAction1.setOnClickListener(view -> {
            //咨询客服
            goKefuMain();
        });
        mBtnAction2.setOnClickListener(view -> {
            //重新申请
            startForResult(ApplyRebateFragment.newInstance(rebate_type, data, apply_id), GAME_REBATE_RE_APPLY);
        });
        if (mTvRebateReason != null && data != null) {
            mTvRebateReason.setText(data.getRemark());
        }
    }

    private void setLayoutStatusRevocation() {
        mIvImageRound.setImageResource(R.mipmap.img_rebate_detail_round_5);
        mIvImageSquare.setImageResource(R.mipmap.img_rebate_detail_square_5);
        mBtnAction.setVisibility(View.GONE);
        mLlTwoButtons.setVisibility(View.VISIBLE);

        GradientDrawable gd2 = new GradientDrawable();
        gd2.setCornerRadius(20 * density);
        gd2.setColor(Color.parseColor("#F3F3F3"));
        gd2.setStroke((int) (1 * density), Color.parseColor("#C0C0C0"));
        mBtnAction1.setBackground(gd2);

        mBtnAction1.setOnClickListener(view -> {
            //咨询客服
            goKefuMain();
        });
        mBtnAction2.setOnClickListener(view -> {
            //重新申请
            startForResult(ApplyRebateFragment.newInstance(rebate_type, data, apply_id), GAME_REBATE_RE_APPLY);
        });
    }

    private TextView mTvRebateReason;

    @SuppressLint("MissingInflatedId")
    private View getContainerView() {
        int layoutResId = 0;
        switch (REBATE_STATUS) {
            case REBATE_STATUS_PENDING:
                layoutResId = R.layout.layout_rebate_status_pending;
                break;
            case REBATE_STATUS_ACCEPTING:
                layoutResId = R.layout.layout_rebate_status_accepting;
                break;
            case REBATE_STATUS_COMPLETE:
                layoutResId = R.layout.layout_rebate_status_complete;
                break;
            case REBATE_STATUS_FAILURE:
                layoutResId = R.layout.layout_rebate_status_failure;
                break;
            case REBATE_STATUS_REVOCATION:
                layoutResId = R.layout.layout_rebate_status_revocation;
                break;
            default:
                layoutResId = R.layout.layout_rebate_status_pending;
                break;
        }

        View view = LayoutInflater.from(_mActivity).inflate(layoutResId, null);
        mTvRebateReason = view.findViewById(R.id.tv_failure_reason);

        return view;
    }

    RebateInfoVo data;

    private void setViewData(RebateInfoVo data) {
        this.data = data;
        switch (data.getStatus()) {
            case 1:
                REBATE_STATUS = REBATE_STATUS_PENDING;
                break;
            case 2:
                REBATE_STATUS = REBATE_STATUS_ACCEPTING;
                break;
            case 10:
                REBATE_STATUS = REBATE_STATUS_COMPLETE;
                break;
            case -2:
                REBATE_STATUS = REBATE_STATUS_FAILURE;
                break;
            case -1:
                REBATE_STATUS = REBATE_STATUS_REVOCATION;
                break;
            default:
                break;
        }
        setLayoutViews();
        mTvGameName.setText(data.getGamename());

        String username = "";
        try {
            username = UserInfoModel.getInstance().getUserInfo().getUsername();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTvXhAccount.setText(data.getXh_showname() + "(" + username + ")");
        mTvRechargeTime.setText(data.getDay_time());
        mTvRechargeAmount.setText(String.valueOf(data.getDefault_total()) + "元");
        mTvGameServer.setText(data.getServername());
        mTvRoleName.setText(data.getRole_name());
        if (!TextUtils.isEmpty(data.getRole_id_title())) {
            mTrRoleId.setVisibility(View.VISIBLE);
            mTvStrRoleId.setText(data.getRole_id_title());
            mTvRoleId.setText(data.getRole_id());
        } else {
            mTrRoleId.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(data.getUser_beizhu())) {
            mTvMark.setText("无");
        } else {
            mTvMark.setText(data.getUser_beizhu());
        }

        if (TextUtils.isEmpty(data.getProp_beizhu())) {
            mTvApplyReward.setText("无");
        } else {
            mTvApplyReward.setText(data.getProp_beizhu());
        }
        mTvApplyTime.setText(TimeUtils.formatTimeStamp(data.getAddtime() * 1000, "yyyy-MM-dd HH:mm:ss"));

    }


    List<RebateRevokeListVo.DataBean> rebateRevokeList;

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GAME_REBATE_RE_APPLY) {
                //重新申请返利
                mAppBarLayout.setExpanded(true, true);
                getNetWordData();
                setFragmentResult(RESULT_OK, null);
            }
        }
    }


    private void getNetWordData() {
        if (mViewModel != null) {
            mViewModel.getRebateItemDetail(apply_id, new OnBaseCallback<RebateItemVo>() {
                @Override
                public void onSuccess(RebateItemVo rebateItemVo) {
                    if (rebateItemVo != null) {
                        if (rebateItemVo.isStateOK()) {
                            if (rebateItemVo.getData() != null) {
                                setViewData(rebateItemVo.getData());
                            }
                        } else {
                            Toaster.show( rebateItemVo.getMsg());
                        }
                    }
                }
            });
            mViewModel.getRebateRevokeRemark(apply_id, new OnBaseCallback<RebateRevokeListVo>() {
                @Override
                public void onSuccess(RebateRevokeListVo rebateRevokeListVo) {
                    if (rebateRevokeListVo != null) {
                        if (rebateRevokeListVo.isStateOK() && rebateRevokeListVo.getData() != null) {
                            rebateRevokeList = rebateRevokeListVo.getData();
                        }
                    }
                }
            });
        }
    }

    /**
     * 返利撤回弹窗
     *
     * @param apply_id
     * @param dataBeans
     */
    protected void showRebateRecallDialog(String apply_id, List<RebateRevokeListVo.DataBean> dataBeans) {
        if (dataBeans == null) {
            return;
        }
        CustomDialog rebateRecallDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_rebate_recall, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        RadioGroup mRgRecallReason = rebateRecallDialog.findViewById(R.id.rg_recall_reason);
        TextView mRecallBtnCancel = rebateRecallDialog.findViewById(R.id.btn_cancel);
        TextView mRecallBtnConfirm = rebateRecallDialog.findViewById(R.id.btn_confirm);

        for (RebateRevokeListVo.DataBean revokeInfoBean : dataBeans) {

            RadioButton radioButton = new RadioButton(_mActivity);
            radioButton.setId(revokeInfoBean.getRmk_id());
            radioButton.setText(revokeInfoBean.getRmk_content());

            radioButton.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f5f5f5));
            radioButton.setButtonDrawable(R.drawable.radio_button_style);
            radioButton.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_666666));
            radioButton.setTextSize(12);

            radioButton.setTag(revokeInfoBean);

            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(_mActivity, 40));
            int margin = (int) (10 * density);
            params.setMargins(0, margin, 0, 0);

            mRgRecallReason.addView(radioButton, params);
        }

        mRecallBtnCancel.setOnClickListener(view -> {
            if (rebateRecallDialog != null && rebateRecallDialog.isShowing()) {
                rebateRecallDialog.dismiss();
            }
        });
        mRecallBtnConfirm.setOnClickListener(view -> {
            int resId = mRgRecallReason.getCheckedRadioButtonId();
            RadioButton radioButton = mRgRecallReason.findViewById(resId);
            if (radioButton == null) {
                Toaster.show( "请选择撤回原因");
                return;
            }
            RebateRevokeListVo.DataBean revokeInfoBean = (RebateRevokeListVo.DataBean) radioButton.getTag();
            if (revokeInfoBean != null) {
                Toaster.show( revokeInfoBean.getRmk_id() + "\n" + revokeInfoBean.getRmk_content());
                //返利撤回
                recallRebateApply(apply_id, String.valueOf(revokeInfoBean.getRmk_id()));
            }

            if (rebateRecallDialog != null && rebateRecallDialog.isShowing()) {
                rebateRecallDialog.dismiss();
            }
        });
        rebateRecallDialog.show();
    }


    private void recallRebateApply(String apply_id, String rmk_id) {
        if (mViewModel != null) {
            mViewModel.recallRebateApply(apply_id, rmk_id, new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            Toaster.show( "撤回成功");
                            setFragmentResult(RESULT_OK, null);
                            pop();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }
}
