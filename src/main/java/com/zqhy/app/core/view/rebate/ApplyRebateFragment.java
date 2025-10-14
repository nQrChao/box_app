package com.zqhy.app.core.view.rebate;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.adapter.abs.AbsChooseAdapter;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.rebate.RebateCommitVo;
import com.zqhy.app.core.data.model.rebate.RebateInfoVo;
import com.zqhy.app.core.data.model.rebate.RebateServerListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.core.tool.utilcode.SizeUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.vm.rebate.RebateViewModel;
import com.zqhy.app.newproject.R;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/22
 */

public class ApplyRebateFragment extends BaseFragment<RebateViewModel> implements View.OnClickListener {

    public static ApplyRebateFragment newInstance(int rebate_type, RebateInfoVo rebateInfoBean) {
        return newInstance(rebate_type, rebateInfoBean, "");
    }

    public static ApplyRebateFragment newInstance(int rebate_type, RebateInfoVo rebateInfoBean, String apply_id) {
        ApplyRebateFragment fragment = new ApplyRebateFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("rebateInfoBean", rebateInfoBean);
        bundle.putString("apply_id", apply_id);
        bundle.putInt("rebate_type", rebate_type);
        fragment.setArguments(bundle);
        return fragment;
    }


    private static final int GAME_REWARD_REQUEST_CODE = 5001;
    private static final int GAME_REBATE_DETAIL = 5002;


    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_apply_rebate;
    }

    @Override
    public int getContentResId() {
        return R.id.fl_container;
    }

    RebateInfoVo rebateInfoBean;
    String apply_id;
    int rebate_type;

    @Override
    public void initView(Bundle state) {
        String title = "";
        if (getArguments() != null) {
            rebateInfoBean = (RebateInfoVo) getArguments().getSerializable("rebateInfoBean");
            apply_id = getArguments().getString("apply_id");
            rebate_type = getArguments().getInt("rebate_type");

            if (rebate_type == 1) {
                title = "BT返利申请";
            } else if (rebate_type == 2) {
                title = "折扣返利申请";
            } else if (rebate_type == 3) {
                title = "H5返利申请";
            }
        }
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle(title);

        bindViews();
        setData();
    }

    private TextView mTvGameName;
    private TextView mTvXhAccount;
    private TextView mTvRechargeTime;
    private EditText mEtRechargeAmount;
    private ImageView mIvClosed;
    private LinearLayout mLlMaxAmount;
    private TextView mTvMaxAmount;
    private LinearLayout mLlGraphicGuide;
    private EditText mEtGameServer;
    private TextView mTvHistory;
    private EditText mEtGameRoleName;
    private LinearLayout mLlRoleId;
    private TextView mTvGameRoleId;
    private EditText mEtGameRoleId;
    private LinearLayout mLlGameReward;
    private TextView mTvGameReward;
    private ImageView mIvChooseGame;
    private LinearLayout mLlExtentsInfo;
    private EditText mEtExtentsInfo;
    private Button mBtnCommit;
    private Button mBtnPending;
    private Button mBtnAccepting;
    private Button mBtnComplete;
    private Button mBtnFailure;
    private Button mBtnRevocation;

    private void bindViews() {
        mTvGameName = findViewById(R.id.tv_game_name);
        mTvXhAccount = findViewById(R.id.tv_xh_account);
        mTvRechargeTime = findViewById(R.id.tv_recharge_time);
        mEtRechargeAmount = findViewById(R.id.et_recharge_amount);
        mIvClosed = findViewById(R.id.iv_closed);
        mLlMaxAmount = findViewById(R.id.ll_max_amount);
        mTvMaxAmount = findViewById(R.id.tv_max_amount);
        mLlGraphicGuide = findViewById(R.id.ll_graphic_guide);
        mEtGameServer = findViewById(R.id.et_game_server);
        mTvHistory = findViewById(R.id.tv_history);
        mEtGameRoleName = findViewById(R.id.et_game_role_name);
        mLlRoleId = findViewById(R.id.ll_role_id);
        mTvGameRoleId = findViewById(R.id.tv_game_role_id);
        mEtGameRoleId = findViewById(R.id.et_game_role_id);
        mLlGameReward = findViewById(R.id.ll_game_reward);
        mTvGameReward = findViewById(R.id.tv_game_reward);
        mIvChooseGame = findViewById(R.id.iv_choose_game);
        mLlExtentsInfo = findViewById(R.id.ll_extents_info);
        mEtExtentsInfo = findViewById(R.id.et_extents_info);
        mBtnCommit = findViewById(R.id.btn_commit);
        mBtnPending = findViewById(R.id.btn_pending);
        mBtnAccepting = findViewById(R.id.btn_accepting);
        mBtnComplete = findViewById(R.id.btn_complete);
        mBtnFailure = findViewById(R.id.btn_failure);
        mBtnRevocation = findViewById(R.id.btn_revocation);

        mLlGraphicGuide.setOnClickListener(this);
        mTvHistory.setOnClickListener(this);
        mLlGameReward.setOnClickListener(this);
        mBtnCommit.setOnClickListener(this);


        findViewById(R.id.btn_pending).setOnClickListener(view -> {
            start(RebateRecordItemFragment.newInstance(rebate_type, 1));
        });
        findViewById(R.id.btn_accepting).setOnClickListener(view -> {
            start(RebateRecordItemFragment.newInstance(rebate_type, 2));
        });
        findViewById(R.id.btn_complete).setOnClickListener(view -> {
            start(RebateRecordItemFragment.newInstance(rebate_type, 3));
        });
        findViewById(R.id.btn_failure).setOnClickListener(view -> {
            start(RebateRecordItemFragment.newInstance(rebate_type, 4));
        });
        findViewById(R.id.btn_revocation).setOnClickListener(view -> {
            start(RebateRecordItemFragment.newInstance(rebate_type, 5));
        });

    }

    private void setData() {
        if (rebateInfoBean != null) {
            mTvGameName.setText(rebateInfoBean.getGamename());
            mTvXhAccount.setText(rebateInfoBean.getXh_showname());
            mTvRechargeTime.setText(rebateInfoBean.getDay_time());

            mEtRechargeAmount.setSelection(mEtRechargeAmount.getText().toString().trim().length());

            mIvClosed.setOnClickListener(view -> {
                String total = mEtRechargeAmount.getText().toString().trim();
                if (!TextUtils.isEmpty(total)) {
                    mEtRechargeAmount.getText().clear();
                }
            });

            float maxAmount = rebateInfoBean.getUsable_total();

            mEtRechargeAmount.setText(String.valueOf(rebateInfoBean.getUsable_total()));
            mTvMaxAmount.setText(String.valueOf(maxAmount));

            mEtRechargeAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String strAmount = mEtRechargeAmount.getText().toString().trim();
                    float mAmount = 0.0f;
                    if (!TextUtils.isEmpty(strAmount)) {
                        mAmount = Float.parseFloat(strAmount);
                    }
                    if (mAmount > maxAmount) {
                        mEtRechargeAmount.setText(String.valueOf(maxAmount));
                        mEtRechargeAmount.setSelection(mEtRechargeAmount.getText().toString().length());
                    }
                }
            });

            if (!TextUtils.isEmpty(rebateInfoBean.getRole_id_title())) {
                mLlRoleId.setVisibility(View.VISIBLE);
                mTvGameRoleId.setText(String.valueOf(rebateInfoBean.getRole_id_title()));
                mEtGameRoleId.setHint(rebateInfoBean.getRole_id_tip());
            } else {
                mLlRoleId.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(apply_id)) {
                mEtGameServer.setText(rebateInfoBean.getServername());
                mEtGameServer.setSelection(mEtGameServer.getText().toString().trim().length());

                mEtGameRoleName.setText(rebateInfoBean.getRole_name());
                mEtGameRoleName.setSelection(mEtGameRoleName.getText().toString().trim().length());

                mEtGameRoleId.setText(String.valueOf(rebateInfoBean.getRole_id()));
                mEtGameRoleId.setSelection(mEtGameRoleId.getText().toString().trim().length());

                mTvGameReward.setText(rebateInfoBean.getProp_beizhu());
                mEtExtentsInfo.setText(rebateInfoBean.getUser_beizhu());
                mEtExtentsInfo.setSelection(mEtExtentsInfo.getText().toString().trim().length());
            }
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GAME_REWARD_REQUEST_CODE:
                    if (data != null) {
                        String mData = data.getString("data");
                        if (TextUtils.isEmpty(mData)) {
                            mData = "";
                        }
                        mTvGameReward.setText(mData);
                    }
                    break;
                default:
                    break;
            }
        }
        if (requestCode == GAME_REBATE_DETAIL) {
            pop();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_graphic_guide:
                //图文教学
                start(RebateHelpFragment.newInstance(1));
                break;
            case R.id.tv_history:
                getRebateServerData();
                break;
            case R.id.ll_game_reward:
                String mReward = mTvGameReward.getText().toString().trim();
                if (TextUtils.isEmpty(mReward)) {
                    mReward = "";
                }
                startForResult(RewardEditFragment.newInstance(mReward, 100), GAME_REWARD_REQUEST_CODE);
                break;
            case R.id.btn_commit:
                rebateCommit();
                break;
            default:
                break;
        }
    }


    private void rebateCommit() {
        if (rebateInfoBean == null) {
            return;
        }
        Map<String, String> params = new TreeMap<>();

        params.put("sid", String.valueOf(rebateInfoBean.getSid()));
        params.put("xh_username", rebateInfoBean.getXh_username());
        if (!TextUtils.isEmpty(apply_id)) {
            params.put("apply_id", apply_id);
        }

        String mAmount = mEtRechargeAmount.getText().toString().trim();
        float fAmount = 0;
        try {
            fAmount = Float.parseFloat(mAmount);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fAmount <= 0) {
            Toaster.show( "请填写充值金额");
            return;
        }
        params.put("apply_amount", mAmount);

        String mGameServer = mEtGameServer.getText().toString().trim();
        if (TextUtils.isEmpty(mGameServer)) {
            Toaster.show( "请填写游戏区服");
            return;
        }
        params.put("servername", mGameServer);


        String mGameRoleName = mEtGameRoleName.getText().toString().trim();
        if (TextUtils.isEmpty(mGameRoleName)) {
            Toaster.show( "请填写角色名");
            return;
        }
        params.put("role_name", mGameRoleName);

        String mGameRoleId = mEtGameRoleId.getText().toString().trim();
        if (!TextUtils.isEmpty(rebateInfoBean.getRole_id_title())) {
            if (TextUtils.isEmpty(mGameRoleId)) {
                Toaster.show( "请填写" + rebateInfoBean.getRole_id_title());
                return;
            }
            params.put("role_id", mGameRoleId);
        }

        String mGameReward = mTvGameReward.getText().toString().trim();
        if (!TextUtils.isEmpty(mGameReward)) {
            params.put("prop_beizhu", mGameReward);
        }

        String mExtentsInfo = mEtExtentsInfo.getText().toString().trim();
        if (!TextUtils.isEmpty(mExtentsInfo)) {
            params.put("user_beizhu", mExtentsInfo);
        }
        applyRebate(params);
    }

    private void applyRebate(Map<String, String> params) {
        if (mViewModel != null) {
            mViewModel.rebateApply(params, new OnBaseCallback<RebateCommitVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    loading("正在提交...");
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onSuccess(RebateCommitVo rebateCommitVo) {
                    if (rebateCommitVo != null) {
                        if (rebateCommitVo.isStateOK()) {
                            if (rebateCommitVo.getData() != null) {
                                setFragmentResult(RESULT_OK, null);
                                if (TextUtils.isEmpty(apply_id)) {
                                    String apply_id = String.valueOf(rebateCommitVo.getData().getApply_id());
                                    startForResult(RebateRecordItemFragment.newInstance(rebate_type, apply_id), GAME_REBATE_DETAIL);
                                } else {
                                    pop();
                                }
                            }
                            mBtnCommit.setEnabled(false);
                        } else {
                            Toaster.show(rebateCommitVo.getMsg());
                        }
                    }
                }
            });
        }
    }


    private void getRebateServerData() {
        if (mViewModel != null) {
            if (rebateInfoBean != null) {
                mViewModel.getRebateServerData(rebateInfoBean.getGameid(), rebateInfoBean.getXh_username(), new OnBaseCallback<RebateServerListVo>() {

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
                    public void onSuccess(RebateServerListVo rebateServerListVo) {
                        if (rebateServerListVo != null) {
                            if (rebateServerListVo.isStateOK()) {
                                if (rebateServerListVo.getData() != null) {
                                    if (rebateServerListVo.getData().size() > 5) {
                                        rebateServerBeanList = rebateServerListVo.getData().subList(0, 5);
                                    } else {
                                        rebateServerBeanList = rebateServerListVo.getData();
                                    }
                                    for (RebateServerListVo.DataBean rebateServerBean : rebateServerBeanList) {
                                        rebateServerBean.setGamename(rebateInfoBean.getGamename());
                                    }
                                    showChooseServerDialog();
                                } else {
                                    Toaster.show( "暂无历史记录");
                                }
                            } else {
                                Toaster.show(rebateServerListVo.getMsg());
                            }
                        }
                    }
                });
            }
        }
    }


    private List<RebateServerListVo.DataBean> rebateServerBeanList;


    private void showChooseServerDialog() {
        if (rebateServerBeanList == null) {
            return;
        }
        CustomDialog chooseServerDialog;
        RecyclerView chooseServerRecyclerView;
        ServerAdapter chooseServerAdapter;
        TextView mBtnComplete;

        chooseServerDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_rebate_server_history_list, null),
                ScreenUtils.getScreenWidth(_mActivity) - SizeUtils.dp2px(_mActivity, 12),
                WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        TextView tvTitle = chooseServerDialog.findViewById(R.id.tv_title);
        tvTitle.setText("请选择游戏区服");
        chooseServerRecyclerView = chooseServerDialog.findViewById(R.id.recyclerView);
        mBtnComplete = chooseServerDialog.findViewById(R.id.btn_complete);

        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        chooseServerRecyclerView.setLayoutManager(layoutManager);

        chooseServerAdapter = new ServerAdapter(_mActivity, rebateServerBeanList);
        chooseServerRecyclerView.setAdapter(chooseServerAdapter);
        chooseServerDialog.show();
        mBtnComplete.setOnClickListener(view -> {
            RebateServerListVo.DataBean rebateServerBean = chooseServerAdapter.getSelectItem();
            if (rebateServerBean != null) {
                mEtGameServer.setText(rebateServerBean.getServername());
                mEtGameRoleName.setText(rebateServerBean.getRole_name());
                mEtGameRoleName.setSelection(mEtGameRoleName.getText().length());
                mEtGameRoleId.setText(rebateServerBean.getRole_id());
                mEtGameRoleId.setSelection(mEtGameRoleId.getText().toString().trim().length());

                if (chooseServerDialog != null && chooseServerDialog.isShowing()) {
                    chooseServerDialog.dismiss();
                }
            }
        });

    }


    class ServerAdapter extends AbsChooseAdapter<RebateServerListVo.DataBean> {

        public ServerAdapter(Context context, List<RebateServerListVo.DataBean> labels) {
            super(context, labels);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, RebateServerListVo.DataBean item, int position) {
            ViewHolder holder = (ViewHolder) viewHolder;

            holder.mTvGameName.setText(item.getGamename());
            StringBuilder sb = new StringBuilder();
            if (!TextUtils.isEmpty(item.getServername())) {
                sb.append(item.getServername() + "，");
            }
            if (!TextUtils.isEmpty(item.getRole_name())) {
                sb.append(item.getRole_name() + "，");
            }
            if (!TextUtils.isEmpty(item.getRole_id())) {
                sb.append(item.getRole_id() + "，");
            }
            holder.mTv.setText(sb.deleteCharAt(sb.length() - 1));


            int resColor = 0;

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(3 * density);

            if (position == mSelectedItem) {
                resColor = ContextCompat.getColor(_mActivity, R.color.color_666666);
            } else {
                resColor = ContextCompat.getColor(_mActivity, R.color.color_cccccc);
            }
            gd.setColor(resColor);
            holder.mTvGameName.setBackground(gd);
            holder.mCb.setChecked(position == mSelectedItem);

            holder.itemView.setOnClickListener(view -> {
                mSelectedItem = position;
                notifyDataSetChanged();
            });
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_rebate_server_list;
        }

        @Override
        public AbsViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }


        class ViewHolder extends AbsViewHolder {
            private TextView mTvGameName;
            private TextView mTv;
            private CheckBox mCb;
            private View mLine;

            public ViewHolder(View itemView) {
                super(itemView);

                mTvGameName = findViewById(R.id.tv_game_name);
                mTv = findViewById(R.id.tv);
                mCb = findViewById(R.id.cb);
                mLine = findViewById(R.id.line);

            }
        }
    }
}
