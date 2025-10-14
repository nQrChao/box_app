package com.zqhy.app.core.view.transfer;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.transfer.TransferActionVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.vm.transfer.TransferViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class TransferActionFragment extends BaseFragment<TransferViewModel> implements View.OnClickListener {

    public static TransferActionFragment newInstance(int index_id, String gamename, String transfer_reward, String transfer_requirements) {
        TransferActionFragment fragment = new TransferActionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index_id", index_id);
        bundle.putString("gamename", gamename);
        bundle.putString("transfer_reward", transfer_reward);
        bundle.putString("transfer_requirements", transfer_requirements);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transfer_action;
    }

    @Override
    public int getContentResId() {
        return R.id.fl_container;
    }

    private String index_id;
    private String gamename;
    private String transfer_reward, transfer_requirements;


    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            index_id = String.valueOf(getArguments().getInt("index_id"));
            gamename = getArguments().getString("gamename");
            transfer_reward = getArguments().getString("transfer_reward");
            transfer_requirements = getArguments().getString("transfer_requirements");
        }
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle(gamename);
        bindViews();
        getTransferRewardInfoData();
    }

    private FrameLayout mFlContainer;
    private TextView mTvTransferReward;
    private TextView mTvTransferIssue;
    private LinearLayout mLlTransferRequirements;
    private TextView mTvTransferRequirements;
    private TextView mTvTitle1;
    private TextView mTvAccount;
    private TextView mTvXhAccount;
    private ImageView mIvChooseXhAccount;
    private EditText mEtGameServer;
    private EditText mEtGameRoleName;
    private EditText mEtGameRoleId;
    private LinearLayout mLlKefu;
    private Button mBtnCommit;

    private void bindViews() {
        mFlContainer = findViewById(R.id.fl_container);
        mTvTransferReward = findViewById(R.id.tv_transfer_reward);
        mTvTransferIssue = findViewById(R.id.tv_transfer_issue);
        mLlTransferRequirements = findViewById(R.id.ll_transfer_requirements);
        mTvTransferRequirements = findViewById(R.id.tv_transfer_requirements);
        mTvTitle1 = findViewById(R.id.tv_title_1);
        mTvAccount = findViewById(R.id.tv_account);
        mTvXhAccount = findViewById(R.id.tv_xh_account);
        mIvChooseXhAccount = findViewById(R.id.iv_choose_xh_account);
        mEtGameServer = findViewById(R.id.et_game_server);
        mEtGameRoleName = findViewById(R.id.et_game_role_name);
        mEtGameRoleId = findViewById(R.id.et_game_role_id);
        mLlKefu = findViewById(R.id.ll_kefu);
        mBtnCommit = findViewById(R.id.btn_commit);

        mTvTitle1.setText(Html.fromHtml(_mActivity.getResources().getString(R.string.string_transfer_notice)));
        mTvTransferReward.setText(transfer_reward);

        UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
        if (userInfoBean != null) {
            mTvAccount.setText(userInfoBean.getUsername());
        }

        if (!TextUtils.isEmpty(transfer_requirements)) {
            mTvTransferRequirements.setText(transfer_requirements);
            mLlTransferRequirements.setVisibility(View.VISIBLE);
        } else {
            mLlTransferRequirements.setVisibility(View.GONE);
        }

        mBtnCommit.setOnClickListener(this);
        mLlKefu.setOnClickListener(this);
    }


    private String xh_uid, xh_username;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                String serverName = mEtGameServer.getText().toString().trim();
                if (TextUtils.isEmpty(serverName)) {
                    Toaster.show( mEtGameServer.getHint());
                    return;
                }
                String roleName = mEtGameRoleName.getText().toString().trim();
                if (TextUtils.isEmpty(roleName)) {
                    Toaster.show( mEtGameRoleName.getHint());
                    return;
                }
                String role_id = mEtGameRoleId.getText().toString().trim();


                if (TextUtils.isEmpty(xh_username) || TextUtils.isEmpty(xh_uid)) {
                    Toaster.show( mTvXhAccount.getHint());
                    return;
                }
                applyTransferReward(index_id, serverName, roleName, role_id, xh_username);
                break;
            case R.id.ll_kefu:
                goKefuMain();
                break;
            default:
                break;
        }
    }

    private void showChooseXhDialog(List<TransferActionVo.XhAccount> list) {
        if (list == null || list.size() == 0) {
            Toaster.show( _mActivity.getResources().getString(R.string.string_no_xh_account));
            return;
        }

        String[] xhs = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            xhs[i] = list.get(i).getXh_showname();
        }

        AlertDialog dialog = new AlertDialog.Builder(_mActivity)
                .setTitle("请选择小号")
                .setItems(xhs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        mTvXhAccount.setText(list.get(i).getXh_showname());
                        xh_uid = list.get(i).getUid();
                        xh_username = list.get(0).getXh_username();
                    }
                }).create();
        dialog.show();
    }

    private void getTransferRewardInfoData() {
        if (mViewModel != null) {
            mViewModel.getTransferRewardInfoData(index_id, new OnBaseCallback<TransferActionVo>() {
                @Override
                public void onSuccess(TransferActionVo transferActionVo) {
                    if (transferActionVo != null) {
                        if (transferActionVo.isStateOK()) {
                            if (transferActionVo.getData() != null) {
                                TransferActionVo.DataBean dataBean = transferActionVo.getData();
                                mTvTransferIssue.setText(dataBean.getReward_provide());

                                if (dataBean.getXh_list() != null && dataBean.getXh_list().size() > 0) {
                                    mTvXhAccount.setText(dataBean.getXh_list().get(0).getXh_showname());
                                    xh_username = dataBean.getXh_list().get(0).getXh_username();
                                    xh_uid = dataBean.getXh_list().get(0).getUid();
                                }

                                mIvChooseXhAccount.setOnClickListener(view -> {
                                    showChooseXhDialog(dataBean.getXh_list());
                                });
                            }
                        } else {
                           Toaster.show( transferActionVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void applyTransferReward(String index_id, String servername, String rolename, String role_id, String xh_username) {
        if (mViewModel != null) {
            mViewModel.applyTransferReward(index_id, servername, rolename, role_id, xh_username, xh_uid, new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            Toaster.show( "提交成功");
                            setFragmentResult(202, null);
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
