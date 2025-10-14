package com.zqhy.app.core.view.invite;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.share.InviteDataVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.community.integral.IntegralDetailFragment;
import com.zqhy.app.core.vm.invite.InviteViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.share.ShareHelper;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 * @date 2018/11/6
 */

public class InviteFriendFragment extends BaseFragment<InviteViewModel> {

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_INVITE_FRIEND;
    }

    @Override
    protected String getUmengPageName() {
        return "邀请好友";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_invite_friend;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("");
        setActionBackBar(R.mipmap.ic_actionbar_back_white);
        setTitleColor(ContextCompat.getColor(_mActivity, R.color.white));
        setTitleBottomLine(View.GONE);
        setStatusBar(0x00000000);
        bindView();
        getInviteData(false);
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        getInviteData(false);
    }

    private LinearLayout mLlTop;
    private TextView mTvRewardDetail;
    private FrameLayout mFlRule;
    private RelativeLayout mRlTotal;


    private LinearLayout mLlTotal;
    private TextView mTvSumGold;
    private TextView mTvCountPeople;
    private Button mBtnInviteFriends;
    private ImageView mIvQuestion;

    private void bindView() {
        mLlTop = findViewById(R.id.ll_top);
        mTvRewardDetail = findViewById(R.id.tv_reward_detail);
        mFlRule = findViewById(R.id.fl_rule);
        mRlTotal = findViewById(R.id.rl_total);
        mLlTotal = findViewById(R.id.ll_total);
        mTvSumGold = findViewById(R.id.tv_sum_gold);
        mTvCountPeople = findViewById(R.id.tv_count_people);
        mBtnInviteFriends = findViewById(R.id.btn_invite_friends);

        mIvQuestion = findViewById(R.id.iv_question);

        GradientDrawable gd = new GradientDrawable();

        gd.setCornerRadius(5 * density);
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.white));

        mTvRewardDetail.setBackground(gd);


        mBtnInviteFriends.setOnClickListener(view -> {
            if (checkLogin()) {
                /*if (mShareHelper != null) {
                    mShareHelper.showShareInviteFriend(inviteDataInfoVo);
                }*/
                getInviteData(true);
            }
        });
        mTvRewardDetail.setOnClickListener(view -> {
            //奖励明细
            if (checkLogin()) {
                //2019.03.18 跳转积分明细
//                start(new CurrencyMainFragment());
                start(new IntegralDetailFragment());
            }
        });

        mIvQuestion.setOnClickListener(view -> {
            AlertDialog builder = new AlertDialog.Builder(_mActivity)
                    .setTitle("邀请奖励说明")
                    .setMessage("①邀请好友的奖励改为积分啦！新获得的邀请收益将以积分形式发放；\n\n②原获得的邀请收益余额可在【钱包】处查看。")
                    .setPositiveButton("我知道了", (dialog, which) -> {
                        dialog.dismiss();
                    }).show();
            CommonUtils.setAlertDialogTextSize(builder, 16, 14);
        });
    }

    private void getInviteData(boolean showDialog) {
        if (mViewModel != null) {
            UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
            if (mViewModel != null) {
                mViewModel.getShareData(String.valueOf(userInfo.getInvite_type()), new OnBaseCallback<InviteDataVo>() {

                    @Override
                    public void onSuccess(InviteDataVo data) {
                        if (data != null && data.isStateOK() && data.getData() != null){
                            mTvCountPeople.setText(String.valueOf(data.getData().getCount()));
                            mTvSumGold.setText(String.valueOf(data.getData().getIntegral_sum()));
                            if (showDialog){
                                if (data.getData().getInvite_info() != null){
                                    InviteDataVo.InviteDataInfoVo inviteInfo = data.getData().getInvite_info();
                                    new ShareHelper(_mActivity).shareToAndroidSystem(inviteInfo.getCopy_title(), inviteInfo.getCopy_description(), inviteInfo.getUrl());
                                }
                            }
                        }
                    }
                });
            }
        }
    }


    /*@Override
    public void setInviteData(InviteDataVo.DataBean data) {
        super.setInviteData(data);
        if (data != null) {
            mTvCountPeople.setText(String.valueOf(data.getCount()));
            mTvSumGold.setText(String.valueOf(data.getIntegral_sum()));
        }
    }

    @Override
    public void onShareSuccess() {
        super.onShareSuccess();
        if (mViewModel != null) {
            mViewModel.addInviteShare(1);
        }
    }*/
}
