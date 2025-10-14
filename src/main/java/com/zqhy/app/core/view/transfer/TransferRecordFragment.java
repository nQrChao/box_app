package com.zqhy.app.core.view.transfer;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.transfer.TransferRecordVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.vm.transfer.TransferViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class TransferRecordFragment extends BaseFragment<TransferViewModel> {

    public static TransferRecordFragment newInstance(String index_id, String gamename) {
        TransferRecordFragment fragment = new TransferRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putString("index_id", index_id);
        bundle.putString("gamename", gamename);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_TRANSFER_RECORD_DETAIL_STATE;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transfer_record_detail;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private String gamename, index_id;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gamename = getArguments().getString("gamename");
            index_id = getArguments().getString("index_id");
        }
        super.initView(state);
        bindViews();
        initActionBackBarAndTitle(gamename);
        getNetWorkData();
    }

    private TextView mTvKefu;
    private TextView mTvTips;
    private LinearLayout mLlCode;
    private TextView mTvCode;
    private SuperButton mTvCodeCopy;
    private TextView mTvCodeUsage;
    private TextView mTvTransferAccount;
    private TextView mTvTransferTotal;
    private TextView mTvTransferXhUsername;
    private TextView mTvTransferServername;
    private TextView mTvTransferRoleName;
    private TextView mTvTransferRoleId;
    private LinearLayout mLlTime;
    private SuperButton mTvTxtTime;
    private TextView mTvTransferEndTime;
    private TextView mTvTxtEnd;
    private TextView mTvTransferReward;
    private TextView mTvTransferIssue;
    private LinearLayout mLlTransferRequirements;
    private TextView mTvTransferRequirements;
    private TextView mIdText1;


    private void bindViews() {
        mTvKefu = findViewById(R.id.tv_kefu);
        mTvTips = findViewById(R.id.tv_tips);
        mLlCode = findViewById(R.id.ll_code);
        mTvCode = findViewById(R.id.tv_code);
        mTvCodeCopy = findViewById(R.id.tv_code_copy);
        mTvCodeUsage = findViewById(R.id.tv_code_usage);
        mTvTransferAccount = findViewById(R.id.tv_transfer_account);
        mTvTransferTotal = findViewById(R.id.tv_transfer_total);
        mTvTransferXhUsername = findViewById(R.id.tv_transfer_xh_username);
        mTvTransferServername = findViewById(R.id.tv_transfer_servername);
        mTvTransferRoleName = findViewById(R.id.tv_transfer_role_name);
        mTvTransferRoleId = findViewById(R.id.tv_transfer_role_id);
        mLlTime = findViewById(R.id.ll_time);
        mTvTxtTime = findViewById(R.id.tv_txt_time);
        mTvTransferEndTime = findViewById(R.id.tv_transfer_end_time);
        mTvTxtEnd = findViewById(R.id.tv_txt_end);
        mTvTransferReward = findViewById(R.id.tv_transfer_reward);
        mTvTransferIssue = findViewById(R.id.tv_transfer_issue);
        mLlTransferRequirements = findViewById(R.id.ll_transfer_requirements);
        mTvTransferRequirements = findViewById(R.id.tv_transfer_requirements);
        mIdText1 = findViewById(R.id.id_text_1);

        mIdText1.setText(Html.fromHtml(_mActivity.getResources().getString(R.string.string_transfer_notice)));

        mTvKefu.setOnClickListener(view -> {
            //跳转客服
            goKefuMain();
        });
    }

    private void setViewData(TransferRecordVo.DataBean data) {
        if (data != null) {
            if (UserInfoModel.getInstance().isLogined()) {
                String username = UserInfoModel.getInstance().getUserInfo().getUsername();
                mTvTransferAccount.setText(username);
            }
            mTvTransferTotal.setText(String.valueOf(data.getPay_total()));


            if (data.getReward_type() == 2) {
                mLlTime.setVisibility(View.GONE);
            } else {
                mLlTime.setVisibility(View.VISIBLE);
                mTvTxtTime.setText(data.getTime_tip());
                mTvTransferEndTime.setText(data.getTime_between());
            }

            mTvTransferXhUsername.setText(data.getXh_username());
            mTvTransferServername.setText(data.getServername());
            mTvTransferRoleName.setText(data.getRolename());
            mTvTransferRoleId.setText(String.valueOf(data.getRole_id()));

            mTvTransferReward.setText(data.getReward_content());
            mTvTransferIssue.setText(data.getReward_provide());

            mTvTips.setText(data.getReward_provide2());

            mTvTxtEnd.setVisibility(data.getEndtime() * 1000 >= System.currentTimeMillis() ? View.GONE : View.VISIBLE);

            String transfer_requirements = "";
            if (!TextUtils.isEmpty(data.getEx_more())) {
                transfer_requirements = data.getEx_more();
            }

            if (!TextUtils.isEmpty(transfer_requirements)) {
                mTvTransferRequirements.setText(transfer_requirements);
                mLlTransferRequirements.setVisibility(View.VISIBLE);
            } else {
                mLlTransferRequirements.setVisibility(View.GONE);
            }

            if (2 == data.getReward_type()) {
                mTvTips.setVisibility(View.GONE);
                mLlCode.setVisibility(View.VISIBLE);
                mTvCode.setText(data.getCard_id());
                mTvCodeUsage.setText("使用方法：" + data.getCardusage());

                mTvCodeCopy.setOnClickListener(view -> {
                    if (CommonUtils.copyString(_mActivity, data.getCard_id())) {
                        Toaster.show( "复制成功");
                    }
                });
            } else {
                mLlCode.setVisibility(View.GONE);
                mTvTips.setVisibility(View.VISIBLE);
            }
        }

    }

    private void getNetWorkData() {
        if (mViewModel != null) {
            mViewModel.getTransferRecordData(index_id, new OnBaseCallback<TransferRecordVo>() {
                @Override
                public void onSuccess(TransferRecordVo transferRecordVo) {
                    if (transferRecordVo != null) {
                        if (transferRecordVo.isStateOK()) {
                            if (transferRecordVo.getData() != null) {
                                setViewData(transferRecordVo.getData());
                            }
                        } else {
                            Toaster.show( transferRecordVo.getMsg());
                        }
                    }
                }
            });
        }
    }
}
