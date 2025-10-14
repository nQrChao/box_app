package com.zqhy.app.core.view.chat;

import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.core.vm.chat.ChatViewModel;
import com.zqhy.app.newproject.R;

public class ChatSettingActivity extends BaseActivity<ChatViewModel> {
    @Override
    protected Object getStateEventKey() {
        return "ChatSettingActivity";
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat_setting;
    }

    /*private Team teamInfo;

    private ImageView mIvIcon;
    private TextView mTvName;
    private TextView mTvCount;
    private SwitchView mSwitchViewSticky;
    private SwitchView mSwitchViewNottice;
    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        showSuccess();

        teamInfo = (Team) getIntent().getSerializableExtra(RouterConstant.CHAT_KRY);

        findViewById(R.id.iv_back).setOnClickListener(v -> {
            finish();
        });

        mIvIcon = findViewById(R.id.iv_icon);
        mTvName = findViewById(R.id.tv_name);
        mTvCount = findViewById(R.id.tv_count);
        mSwitchViewSticky = findViewById(R.id.switch_view_sticky);
        mSwitchViewNottice = findViewById(R.id.switch_view_notice);

        GlideUtils.loadGameIcon(ChatSettingActivity.this, teamInfo.getIcon(), mIvIcon);
        mTvName.setText(teamInfo.getName());
        mTvCount.setText(String.valueOf(teamInfo.getMemberCount()));

        queryTeam(teamInfo.getId());

        mSwitchViewNottice.setOnClickListener(view -> {
            setNotify(!mSwitchViewNottice.isOpened());
        });

        findViewById(R.id.cl_member).setOnClickListener(view -> {
            //BaseTeamMemberListActivity.launch(ChatSettingActivity.this, TeamMemberListActivity.class, teamInfo);
            FragmentHolderActivity.startFragmentInActivity(this, ChatMemberFragment.newInstance(teamInfo));
        });

        findViewById(R.id.cl_history).setOnClickListener(view -> {
            XKitRouter.withKey(RouterConstant.PATH_CHAT_SEARCH_PAGE)
                    .withParam(RouterConstant.CHAT_KRY, teamInfo)
                    .withContext(ChatSettingActivity.this)
                    .navigate();
        });

        findViewById(R.id.cl_ruler).setOnClickListener(view -> {
            chatRuleTxt();
        });

        findViewById(R.id.cl_exit).setOnClickListener(view -> {
            showTipsDialog();
        });

        TeamRepo.queryTeamWithMember(teamInfo.getId(), Objects.requireNonNull(IMKitClient.account()), new FetchCallback<TeamWithCurrentMember>() {
            @Override
            public void onSuccess(@Nullable TeamWithCurrentMember param) {
                mSwitchViewSticky.setOpened(param.isStickTop());
            }

            @Override
            public void onFailed(int code) {

            }

            @Override
            public void onException(@Nullable Throwable exception) {

            }
        });

        mSwitchViewSticky.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                configStick(teamInfo.getId(), true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                configStick(teamInfo.getId(), false);
            }
        });
    }

    private void queryTeam(String teamId){
        NIMClient.getService(TeamService.class).queryTeam(teamId).setCallback(new RequestCallbackWrapper<Team>() {
            @Override
            public void onResult(int code, Team t, Throwable exception) {
                if (code == ResponseCode.RES_SUCCESS) {
                    teamInfo = t;
                    if (teamInfo != null && teamInfo.getMessageNotifyType() != null && teamInfo.getMessageNotifyType() == TeamMessageNotifyTypeEnum.All){
                        mSwitchViewNottice.setOpened(false);
                    }else {
                        mSwitchViewNottice.setOpened(true);
                    }
                } else {
                    // 失败，错误码见code
                }
                if (exception != null) {
                    // error
                }
            }
        });
    }

    private void configStick(String sessionId, boolean stick) {
        if (stick) {
            ConversationRepo.addStickTop(sessionId, SessionTypeEnum.Team, "", new FetchCallback<StickTopSessionInfo>() {
                @Override
                public void onSuccess(@Nullable StickTopSessionInfo param) {
                    mSwitchViewSticky.setOpened(true);
                }

                @Override
                public void onFailed(int code) {}

                @Override
                public void onException(@Nullable Throwable exception) {}
            });
        } else {
            ConversationRepo.removeStickTop(sessionId, SessionTypeEnum.Team, "", new FetchCallback<Void>() {
                @Override
                public void onSuccess(@Nullable Void param) {
                    mSwitchViewSticky.setOpened(false);
                }

                @Override
                public void onFailed(int code) {
                }

                @Override
                public void onException(@Nullable Throwable exception) {
                }
            });
        }
    }

    *//**
     * 设置消息提醒
     *//*
    public void setNotify(boolean notice) {
        ConversationRepo.setNotify(
                teamInfo.getId(),
                SessionTypeEnum.Team,
                notice,
                new FetchCallback<Void>() {
                    @Override
                    public void onSuccess(@Nullable Void param) {
                    }

                    @Override
                    public void onFailed(int code) {
                    }

                    @Override
                    public void onException(@Nullable Throwable exception) {
                    }
                });
    }

    private void showTipsDialog() {
        CustomDialog vipTipsDialog = new CustomDialog(ChatSettingActivity.this, LayoutInflater.from(ChatSettingActivity.this).inflate(R.layout.layout_dialog_chat_exit, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        vipTipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if (vipTipsDialog != null && vipTipsDialog.isShowing()) vipTipsDialog.dismiss();
            TeamRepo.quitTeam(teamInfo.getId(), new FetchCallback<Void>() {
                @Override
                public void onSuccess(@Nullable Void param) {
                    Toaster.show("已退出群聊");
                    setNotify(true);
                    configStick(teamInfo.getId(), false);
                    finish();
                }

                @Override
                public void onFailed(int code) {
                }

                @Override
                public void onException(@Nullable Throwable exception) {
                }
            });
        });
        vipTipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            if (vipTipsDialog != null && vipTipsDialog.isShowing()) vipTipsDialog.dismiss();
        });
        vipTipsDialog.show();
    }

    private String rulerTxt;
    private void chatRuleTxt(){
        if (!TextUtils.isEmpty(rulerTxt)){
            showRulerDialog();
            return;
        }
        if (mViewModel != null) {
            mViewModel.chatRuleTxt(new OnBaseCallback<ChatUserVo>(){
                @Override
                public void onSuccess(ChatUserVo data) {
                    if (data != null && !TextUtils.isEmpty(data.getData())){
                        rulerTxt = data.getData();
                        showRulerDialog();
                    }
                }
            });
        }
    }

    private void showRulerDialog() {
        CustomDialog vipTipsDialog = new CustomDialog(ChatSettingActivity.this, LayoutInflater.from(ChatSettingActivity.this).inflate(R.layout.layout_dialog_chat_ruler, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        ((TextView) vipTipsDialog.findViewById(R.id.tv_content)).setText(Html.fromHtml(rulerTxt));
        vipTipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if (vipTipsDialog != null && vipTipsDialog.isShowing()) vipTipsDialog.dismiss();
        });
        vipTipsDialog.show();
    }*/
}
