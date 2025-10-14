package com.zqhy.app.core.view.chat;

import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.core.vm.chat.ChatViewModel;
import com.zqhy.app.newproject.R;

public class ChatListActivity extends BaseActivity<ChatViewModel> {
    @Override
    protected Object getStateEventKey() {
        return "ChatListActivity";
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat_list;
    }

    /*private ConversationFragment fragment;
    private RecyclerView mRecyclerView;
    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        showSuccess();

        findViewById(R.id.iv_back).setOnClickListener(v -> {
            finish();
        });

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FragmentManager fragmentManager = getSupportFragmentManager();
        //创建ConversationFragment
        fragment = new ConversationFragment();
        //将ConversationFragment加载到Activity中
        fragmentManager.beginTransaction().add(R.id.fl_container, fragment).commit();

        ConversationUIConfig conversationUIConfig = new ConversationUIConfig();
        conversationUIConfig.showTitleBar = false;

        conversationUIConfig.conversationFactory = new IConversationFactory() {

            @Override
            public ConversationBean CreateBean(ConversationInfo conversationInfo) {
                return new ConversationBean(conversationInfo);
            }

            @Override
            public int getItemViewType(ConversationBean conversationBean) {
                return conversationBean.viewType;
            }

            @Override
            public BaseViewHolder<ConversationBean> createViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View inflate = LayoutInflater.from(ChatListActivity.this).inflate(R.layout.item_chat_list_item, null);
                inflate.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                return new BaseViewHolder<ConversationBean>(inflate) {
                    @Override
                    public void onBindData(ConversationBean conversationBean, int i) {
                        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                        layoutParams.width = ScreenUtil.getScreenWidth(ChatListActivity.this);
                        itemView.setLayoutParams(layoutParams);
                        LinearLayout mLlBg = itemView.findViewById(R.id.ll_bg);
                        ImageView mIvIcon = itemView.findViewById(R.id.iv_icon);
                        TextView mTvTitle = itemView.findViewById(R.id.tv_title);
                        TextView mTvContent = itemView.findViewById(R.id.tv_content);
                        ImageView mIvNotice = itemView.findViewById(R.id.iv_notice);
                        TextView mTvCount = itemView.findViewById(R.id.tv_counts);

                        if (conversationBean.infoData != null) {
                            GlideUtils.loadGameIcon(ChatListActivity.this, conversationBean.infoData.getTeamInfo().getIcon(), mIvIcon);
                            mTvTitle.setText(conversationBean.infoData.getTeamInfo().getName());

                            if (TextUtils.isEmpty(conversationBean.infoData.getFromAccount())){
                                mTvContent.setText(ConversationUtils.getConversationText(itemView.getContext(), conversationBean.infoData));
                            }else {
                                MessageHelper.getChatMessageUserInfo(conversationBean.infoData.getFromAccount());
                                String chatMessageUserNameByAccount = MessageHelper.getChatMessageUserNameByAccount(conversationBean.infoData.getFromAccount());
                                if (!TextUtils.isEmpty(chatMessageUserNameByAccount)){
                                    mTvContent.setText(chatMessageUserNameByAccount + ": " + ConversationUtils.getConversationText(itemView.getContext(), conversationBean.infoData));
                                }else {
                                    mTvContent.setText(ConversationUtils.getConversationText(itemView.getContext(), conversationBean.infoData));
                                }
                            }

                            Team teamInfo = conversationBean.infoData.getTeamInfo();
                            if (teamInfo != null && teamInfo.getMessageNotifyType() != null && teamInfo.getMessageNotifyType() == TeamMessageNotifyTypeEnum.All){
                                mIvNotice.setVisibility(View.GONE);
                            }else {
                                mIvNotice.setVisibility(View.VISIBLE);
                            }

                            RecentContact recentContact = NIMClient.getService(MsgService.class).queryRecentContact(conversationBean.infoData.getContactId(), SessionTypeEnum.Team);
                            if (recentContact != null){
                                int unreadCount = recentContact.getUnreadCount();
                                if (unreadCount > 0) {
                                    mTvCount.setVisibility(View.VISIBLE);
                                    if (unreadCount > 99){
                                        mTvCount.setText("99+");
                                    }else {
                                        mTvCount.setText(String.valueOf(unreadCount));
                                    }
                                    if (mIvNotice.getVisibility() == View.VISIBLE){
                                        mTvCount.setBackgroundResource(R.drawable.shape_c7c7c7_big_radius);
                                    }else {
                                        mTvCount.setBackgroundResource(R.drawable.shape_ff1414_big_radius);
                                    }
                                    //mTvCount.setBackgroundResource(R.drawable.shape_ff1414_big_radius);
                                } else {
                                    mTvCount.setVisibility(View.GONE);
                                }
                            }
                            TeamRepo.queryTeamWithMember(teamInfo.getId(), Objects.requireNonNull(IMKitClient.account()), new FetchCallback<TeamWithCurrentMember>() {
                                @Override
                                public void onSuccess(@Nullable TeamWithCurrentMember param) {
                                    if (param.isStickTop()){
                                        mLlBg.setBackgroundResource(R.drawable.shape_fff6ef_10_radius);
                                    }else {
                                        mLlBg.setBackgroundResource(R.drawable.shape_f9f9f9_10_radius);
                                    }
                                }

                                @Override
                                public void onFailed(int code) {}

                                @Override
                                public void onException(@Nullable Throwable exception) {}
                            });
                        }

                        itemView.setOnClickListener(view -> {
                            getNetWorkData1(conversationBean.infoData.getTeamInfo().getId(), true);
                            //XKitRouter.withKey(RouterConstant.PATH_CHAT_TEAM_PAGE).withParam(RouterConstant.CHAT_KRY, conversationBean.infoData.getTeamInfo()).withContext(ChatListActivity.this).navigate();
                        });
                    }
                };
            }
        };
        ConversationKitClient.setConversationUIConfig(conversationUIConfig);

        fragment.setOnDataChangeListener(() -> {
            if ( fragment.getConversationView().getDataSize() > 0){
                findViewById(R.id.fl_container).setVisibility(View.VISIBLE);
                findViewById(R.id.ll_other).setVisibility(View.GONE);
            }else {
                findViewById(R.id.fl_container).setVisibility(View.GONE);
                findViewById(R.id.ll_other).setVisibility(View.VISIBLE);
            }
            showSuccess();
        });

        getChatRecommend();
    }

    private boolean needLoad = false;
    @Override
    public void onResume() {
        super.onResume();
        //第一次进来不用刷新 后续显示都要刷新一下数据
        if (needLoad){
            fragment.getViewModel().fetchConversation();
        }
        needLoad = true;
    }

    private void getChatRecommend() {
        if (mViewModel != null) {
            mViewModel.getChatRecommend(new OnBaseCallback<ChatRecommendVo>() {
                @Override
                public void onSuccess(ChatRecommendVo data) {
                    if (data != null && data.isStateOK()) {

                        if (data.getData() != null && data.getData().size() > 0){
                            mRecyclerView.setAdapter(new RecyclerView.Adapter() {
                                @NonNull
                                @Override
                                public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    return new MyViewHolder(LayoutInflater.from(ChatListActivity.this).inflate(R.layout.item_chat_recommend, parent, false));
                                }

                                @Override
                                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                                    MyViewHolder mHolder = (MyViewHolder)holder;
                                    ChatRecommendVo.DataBean dataBean = data.getData().get(position);
                                    GlideUtils.loadGameIcon(ChatListActivity.this, dataBean.getGameicon(), mHolder.mIvIcon);
                                    //mHolder.mTvTitle.setText(dataBean.getGamename());
                                    mHolder.mTvTitle.setText(dataBean.getTeam_name());
                                    mHolder.mTvContent.setText(CommonUtils.formatNumberType2(dataBean.getPlay_count()) + "人玩过");

                                    mHolder.mTvJoin.setOnClickListener(view -> {
                                        addChat(dataBean.getGameid());
                                    });
                                }

                                @Override
                                public int getItemCount() {
                                    return data.getData().size();
                                }

                                class MyViewHolder extends RecyclerView.ViewHolder{
                                    private ImageView mIvIcon;
                                    private TextView mTvTitle;
                                    private TextView mTvContent;
                                    private TextView mTvJoin;

                                    public MyViewHolder(View view) {
                                        super(view);
                                        mIvIcon = view.findViewById(R.id.iv_icon);
                                        mTvTitle = view.findViewById(R.id.tv_title);
                                        mTvContent = view.findViewById(R.id.tv_content);
                                        mTvJoin = view.findViewById(R.id.tv_join);
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    private void addChat(int gameid) {
        if (UserInfoModel.getInstance().isLogined()){
            UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
            if ((TextUtils.isEmpty(userInfo.getReal_name()) || TextUtils.isEmpty(userInfo.getIdcard()))) {
                FragmentHolderActivity.startFragmentInActivity(ChatListActivity.this, CertificationFragment.newInstance());
                return;
            }
            if (mViewModel != null) {
                showLoading();
                mViewModel.addChat(String.valueOf(gameid), new OnBaseCallback<AddChatVo>() {

                    @Override
                    public void onSuccess(AddChatVo data) {
                        if (data != null && data.isStateOK()) {
                            if (data.getData() != null){

                                getNetWorkData1(String.valueOf(data.getData().getTid()), false);
                                new Handler().postDelayed(() -> {
                                    //刷新会话列表
                                    fragment.getViewModel().fetchConversation();
                                    showSuccess();
                                }, 500);
                            }
                        }else{
                            showSuccess();
                            Toaster.show(data.getMsg());
                        }
                    }
                });
            }
        }
    }

    private void chatActivityRecommend(String teamId, boolean showLoad) {
        if (mViewModel != null) {
            if (showLoad) showLoading();
            mViewModel.chatActivityRecommend(teamId, new OnBaseCallback<ChatActivityRecommendVo>() {
                @Override
                public void onSuccess(ChatActivityRecommendVo data) {
                    if (data != null && data.isStateOK()) {
                        Setting.activityList = data.getData();
                        Setting.tid = teamId;
                        chatTeamNotice(teamId);
                    }else {
                        showSuccess();
                    }
                }
            });
        }
    }

    private void chatTeamNotice(String teamId) {
        if (mViewModel != null) {
            mViewModel.chatTeamNotice(teamId, "1", new OnBaseCallback<ChatTeamNoticeListVo>() {
                @Override
                public void onSuccess(ChatTeamNoticeListVo data) {
                    if (data != null && data.isStateOK()) {
                        Setting.noticeList = data.getData();
                        chatGetGameId(teamId);
                        *//*Team team = NIMClient.getService(TeamService.class).queryTeamBlock(teamId);
                        if (team != null){
                            //跳转到群聊界面
                            XKitRouter.withKey(RouterConstant.PATH_CHAT_TEAM_PAGE).withParam(RouterConstant.CHAT_KRY, team).withParam("gameid", 0).withParam("gametype", 0).withContext(ChatListActivity.this).navigate();
                        }*//*
                    }else {
                        showSuccess();
                    }
                }
            });
        }
    }

    private void chatGetGameId(String teamId) {
        if (mViewModel != null) {
            mViewModel.chatGetGameId(teamId, new OnBaseCallback<GameDataVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }

                @Override
                public void onSuccess(GameDataVo data) {
                    if (data != null && data.isStateOK()) {
                        Team team = NIMClient.getService(TeamService.class).queryTeamBlock(teamId);
                        if (team != null){
                            //跳转到群聊界面
                            XKitRouter.withKey(RouterConstant.PATH_CHAT_TEAM_PAGE).withParam(RouterConstant.CHAT_KRY, team).withParam("gameid", data.getData().getGameid()).withParam("gametype", data.getData().getGame_type()).withContext(ChatListActivity.this).navigate();
                        }
                    }
                }
            });
        }
    }

    private void getNetWorkData(String teamId, boolean showLoad) {
        if (mViewModel != null) {
            if (showLoad) showLoading();
            mViewModel.getNetWorkData(teamId, new OnBaseCallback<GameDataVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }

                @Override
                public void onSuccess(GameDataVo data) {
                    if (data != null && data.isStateOK()) {
                        Team team = NIMClient.getService(TeamService.class).queryTeamBlock(teamId);
                        if (team != null){
                            //跳转到群聊界面
                            XKitRouter.withKey(RouterConstant.PATH_CHAT_TEAM_PAGE).withParam(RouterConstant.CHAT_KRY, team).withParam("gameid", data.getData().getGameid()).withParam("gametype", data.getData().getGame_type()).withContext(ChatListActivity.this).navigate();
                        }
                    }
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    showSuccess();
                    Toaster.show(message);
                }
            });
        }
    }

    private CountDownLatch countDownLatch;
    GameInfoVo gameInfoVo = null;
    private void getNetWorkData1(String teamId, boolean showLoad) {
        if (mViewModel != null) {
            if (showLoad) showLoading();
            countDownLatch = new CountDownLatch(3);

            mViewModel.chatActivityRecommend(teamId, new OnBaseCallback<ChatActivityRecommendVo>() {
                @Override
                public void onSuccess(ChatActivityRecommendVo data) {
                    if (data != null && data.isStateOK()) {
                        Setting.activityList = data.getData();
                        Setting.tid = teamId;
                    }
                    countDownLatch.countDown();
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    countDownLatch.countDown();
                }
            });
            mViewModel.chatTeamNotice(teamId, "1", new OnBaseCallback<ChatTeamNoticeListVo>() {
                @Override
                public void onSuccess(ChatTeamNoticeListVo data) {
                    if (data != null && data.isStateOK()) {
                        Setting.noticeList = data.getData();
                    }
                    countDownLatch.countDown();
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    countDownLatch.countDown();
                }
            });
            mViewModel.chatGetGameId(teamId, new OnBaseCallback<GameDataVo>() {
                @Override
                public void onSuccess(GameDataVo data) {
                    if (data != null && data.isStateOK()) {
                        gameInfoVo = data.getData();
                    }else{
                        Toaster.show(data.getMsg());
                    }
                    countDownLatch.countDown();
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    Toaster.show(message);
                    countDownLatch.countDown();
                }
            });

            new Thread(() -> {
                try {
                    countDownLatch.await();
                    if (gameInfoVo != null){
                        new Handler(Looper.getMainLooper()).post(() -> {
                            Team team = NIMClient.getService(TeamService.class).queryTeamBlock(teamId);
                            if (team != null){
                                //跳转到群聊界面
                                XKitRouter.withKey(RouterConstant.PATH_CHAT_TEAM_PAGE).withParam(RouterConstant.CHAT_KRY, team).withParam("gameid", gameInfoVo.getGameid()).withParam("gametype", gameInfoVo.getGame_type()).withContext(ChatListActivity.this).navigate();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }*/
}
