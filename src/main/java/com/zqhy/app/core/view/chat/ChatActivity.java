package com.zqhy.app.core.view.chat;

import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.core.vm.chat.ChatViewModel;
import com.zqhy.app.newproject.R;

public class ChatActivity extends BaseActivity<ChatViewModel> {

    @Override
    protected Object getStateEventKey() {
        return "ChatActivity";
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

    /*private Team teamInfo;
    private int gameId, gameType;
    private LinearLayout mLlArrow;
    private TextView mTvArrow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        showSuccess();
        //setStatusBarFullWindowMode();

        FrameLayout fl_status_bar = findViewById(MResource.getResourceId(this, "id", "fl_status_bar"));
        if (fl_status_bar == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.height = ScreenUtil.getStatusBarHeight(this);
            fl_status_bar.setLayoutParams(params);
        }

        teamInfo = (Team) getIntent().getSerializableExtra(RouterConstant.CHAT_KRY);
        gameId = getIntent().getIntExtra("gameid", 0);
        gameType = getIntent().getIntExtra("gametype", 1);
        if (teamInfo == null) {
            finish();
            return;
        }

        mLlArrow = findViewById(R.id.ll_arrow);
        mTvArrow = findViewById(R.id.tv_arrow);

        FragmentManager fragmentManager = getSupportFragmentManager();
        //创建ChatP2PFragment
        ChatTeamFragment chatFragment = new ChatTeamFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(RouterConstant.CHAT_KRY, teamInfo);
        chatFragment.setArguments(bundle);
        //将ChatP2PFragment加载到Activity中
        fragmentManager.beginTransaction().add(R.id.fl_container, chatFragment).commitAllowingStateLoss();

        setConfig();

        ChatObserverRepo.registerReceiveMessageObserve(teamInfo.getId(), new EventObserver<List<IMMessageInfo>>() {
            @Override
            public void onEvent(@Nullable List<IMMessageInfo> event) {
                mTvArrow.setVisibility(View.VISIBLE);
            }
        });
        mLlArrow.setOnClickListener(v -> {
            if (messageView != null){
                messageView.scrollToEnd();
                mLlArrow.setVisibility(View.GONE);
                mTvArrow.setVisibility(View.GONE);
            }
        });
    }

    private ChatMessageListView messageView;
    private void setConfig(){
        ChatUIConfig chatUIConfig = new ChatUIConfig();

        if (!Setting.tid.equals(teamInfo.getId())){
            Setting.activityList = null;
            Setting.noticeList = null;
        }

        chatUIConfig.chatViewCustom = layout -> {
            if (layout instanceof FunChatView) {
                FunChatView chatLayout = (FunChatView) layout;
                ImageView imageView = chatLayout.getTitleBarLayout().findViewById(R.id.iv_back);
                FrameLayout.LayoutParams layoutParams1 = (FrameLayout.LayoutParams) imageView.getLayoutParams();
                layoutParams1.leftMargin = ScreenUtil.dp2px(ChatActivity.this, 9);
                imageView.setLayoutParams(layoutParams1);

                FrameLayout chatBottomLayout = chatLayout.getChatBottomLayout();
                chatBottomLayout.findViewById(R.id.chatMsgInputSwitchLayout).setVisibility(View.GONE);

                if (BuildConfig.NEED_BIPARTITION){
                    if (Setting.activityList != null && Setting.activityList.size() > 0){
                        List<ChatActivityRecommendVo.DataBean> dataBeans = new ArrayList<>();
                        for (int i = 0; i < Setting.activityList.size(); i++) {
                            ChatActivityRecommendVo.DataBean dataBean = Setting.activityList.get(i);
                            if (!"cloud".equals(dataBean.page_type)){
                                dataBeans.add(dataBean);
                            }
                        }
                        Setting.activityList = dataBeans;
                    }
                }

                if (Setting.activityList != null && Setting.activityList.size() > 0) {
                    FrameLayout frameLayout = chatLayout.getChatBodyBottomLayout();
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.gravity = Gravity.CENTER_VERTICAL;
                    frameLayout.addView(createItem(), layoutParams);
                    frameLayout.setBackground(new ColorDrawable(Color.parseColor("#F5F5F5")));
                }

                if (Setting.noticeList != null && Setting.noticeList.size() > 0) {
                    FrameLayout chatBodyTopLayout = chatLayout.getChatBodyTopLayout();
                    View view = createNoticeItem(Setting.noticeList.get(0));
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.gravity = Gravity.CENTER_VERTICAL;
                    layoutParams.topMargin = ScreenUtil.dp2px(ChatActivity.this, 5);
                    layoutParams.bottomMargin = ScreenUtil.dp2px(ChatActivity.this, 5);

                    chatBodyTopLayout.setBackground(new ColorDrawable(Color.parseColor("#F5F5F5")));
                    chatBodyTopLayout.addView(view, layoutParams);
                }
            } else if (layout instanceof ChatView) {
                ChatView chatLayout = (ChatView) layout;
                ImageView imageView = chatLayout.getTitleBarLayout().findViewById(R.id.iv_back);
                FrameLayout.LayoutParams layoutParams1 = (FrameLayout.LayoutParams) imageView.getLayoutParams();
                layoutParams1.leftMargin = ScreenUtil.dp2px(ChatActivity.this, 9);
                imageView.setLayoutParams(layoutParams1);

                BackTitleBar titleBar = chatLayout.getTitleBar();

                TextView titleTextView = titleBar.getTitleTextView();
                titleTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.ic_chat_title_play), null);
                titleTextView.setCompoundDrawablePadding(ScreenUtil.dp2px(ChatActivity.this, 3));
                titleTextView.setOnClickListener(view -> {
                    FragmentHolderActivity.startFragmentInActivity(ChatActivity.this, GameDetailInfoFragment.newInstance(gameId, gameType));
                });

                ImageView rightImageView = titleBar.getRightImageView();
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) rightImageView.getLayoutParams();
                layoutParams2.width = ScreenUtil.dp2px(ChatActivity.this, 40);
                layoutParams2.rightMargin = ScreenUtil.dp2px(ChatActivity.this, 10);
                rightImageView.setLayoutParams(layoutParams2);

                FrameLayout chatBottomLayout = chatLayout.getChatBottomLayout();
                chatBottomLayout.findViewById(R.id.chatMsgInputSwitchLayout).setVisibility(View.GONE);

                messageView = chatLayout.findViewById(R.id.messageView);
                LinearLayoutManager layoutManager = (LinearLayoutManager) messageView.getLayoutManager();
                messageView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        // dx 表示水平滚动距离，dy 表示垂直滚动距离
                        if (dy > 0) {
                            // 向下滚动
                            int visibleItemCount = layoutManager.getChildCount();
                            int totalItemCount = layoutManager.getItemCount();
                            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                                // 滚动到了底部
                                mLlArrow.setVisibility(View.GONE);
                                mTvArrow.setVisibility(View.GONE);
                            }
                        } else if (dy < 0) {
                            // 向上滚动
                            mLlArrow.setVisibility(View.VISIBLE);
                        }
                    }
                });

                MessageBottomLayout messageBottomLayout = chatBottomLayout.findViewById(R.id.chatBottomInputLayout);
                EditText mEtInput = messageBottomLayout.findViewById(R.id.chat_message_input_et);
                mEtInput.setHeight(ScreenUtil.dp2px(ChatActivity.this, 60));
                mEtInput.setPadding(ScreenUtil.dp2px(ChatActivity.this, 15), ScreenUtil.dp2px(ChatActivity.this, 10), ScreenUtil.dp2px(ChatActivity.this, 15), ScreenUtil.dp2px(ChatActivity.this, 10));

                mEtInput.setOnKeyListener((v, keyCode, event) ->{
                    if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                        String trim = mEtInput.getText().toString().trim();
                        if (!TextUtils.isEmpty(trim)){
                            messageBottomLayout.sendText(messageBottomLayout.replyMessage);
                            return true;
                        }
                    }
                    return false;
                });

                if (BuildConfig.NEED_BIPARTITION){
                    if (Setting.activityList != null && Setting.activityList.size() > 0){
                        List<ChatActivityRecommendVo.DataBean> dataBeans = new ArrayList<>();
                        for (int i = 0; i < Setting.activityList.size(); i++) {
                            ChatActivityRecommendVo.DataBean dataBean = Setting.activityList.get(i);
                            if (!"cloud".equals(dataBean.page_type)){
                                dataBeans.add(dataBean);
                            }
                        }
                        Setting.activityList = dataBeans;
                    }
                }

                if (Setting.activityList != null && Setting.activityList.size() > 0) {
                    FrameLayout frameLayout = chatLayout.getChatBodyBottomLayout();
                    *//*MessageBottomLayout chatBottomLayout = chatLayout.getBottomInputLayout();
                    chatBottomLayout.setBackground(new ColorDrawable(Color.parseColor("#FFFFFF")));*//*
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.gravity = Gravity.CENTER_VERTICAL;
                    frameLayout.addView(createItem(), layoutParams);
                    frameLayout.setBackground(new ColorDrawable(Color.parseColor("#F5F5F5")));
                }

                if (Setting.noticeList != null && Setting.noticeList.size() > 0) {
                    FrameLayout chatBodyTopLayout = chatLayout.getChatBodyTopLayout();
                    View view = createNoticeItem(Setting.noticeList.get(0));
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.gravity = Gravity.CENTER_VERTICAL;
                    layoutParams.topMargin = ScreenUtil.dp2px(ChatActivity.this, 5);
                    layoutParams.bottomMargin = ScreenUtil.dp2px(ChatActivity.this, 5);
                    layoutParams.leftMargin = ScreenUtil.dp2px(ChatActivity.this, 15);
                    layoutParams.rightMargin = ScreenUtil.dp2px(ChatActivity.this, 15);

                    chatBodyTopLayout.setBackground(new ColorDrawable(Color.parseColor("#F5F5F5")));
                    chatBodyTopLayout.addView(view, layoutParams);
                }

            }
        };

        chatUIConfig.messageProperties = new MessageProperties();
        chatUIConfig.messageProperties.chatViewBackground = new ColorDrawable(Color.parseColor("#F5F5F5"));
        chatUIConfig.messageProperties.titleBarRightRes = R.mipmap.ic_chat_title_more;
        chatUIConfig.messageProperties.titleBarRightClick = view -> {
            Intent intent = new Intent(ChatActivity.this, ChatSettingActivity.class);
            intent.putExtra(RouterConstant.CHAT_KRY, teamInfo);
            startActivity(intent);
            //XKitRouter.withKey(PATH_TEAM_SETTING_PAGE).withContext(ChatActivity.this).withParam(KEY_TEAM_ID, teamInfo.getId()).navigate();
        };

        chatUIConfig.chatPopMenu = new IChatPopMenu() {
            @NonNull
            @Override
            public List<ChatPopMenuAction> customizePopMenu(List<ChatPopMenuAction> menuList, ChatMessageBean messageBean) {
                if(menuList != null){
                    Iterator<ChatPopMenuAction> iterator = menuList.iterator();
                    while (iterator.hasNext()){
                        ChatPopMenuAction next = iterator.next();
                        if (TextUtils.equals(next.getAction(), ActionConstants.POP_ACTION_PIN) || TextUtils.equals(next.getAction(), ActionConstants.POP_ACTION_MULTI_SELECT) || TextUtils.equals(next.getAction(), ActionConstants.POP_ACTION_TRANSMIT)){
                            iterator.remove();
                        }
                    }
                }
                return menuList;
            }
        };

        chatUIConfig.chatInputMenu = new IChatInputMenu(){
            @Override
            public List<ActionItem> customizeInputBar(List<ActionItem> actionItemList) {
                if (actionItemList != null && actionItemList.size() > 0){
                    Iterator<ActionItem> iterator = actionItemList.iterator();
                    while (iterator.hasNext()){
                        ActionItem next = iterator.next();
                        if ("ACTION_TYPE_RECORD".equals(next.getAction()) || "ACTION_TYPE_MORE".equals(next.getAction())){
                            iterator.remove();
                        }
                    }
                }
                return actionItemList;
            }

            @Override
            public List<ActionItem> customizeInputMore(List<ActionItem> actionItemList) {
                if (actionItemList != null && actionItemList.size() > 0){
                    Iterator<ActionItem> iterator = actionItemList.iterator();
                    while (iterator.hasNext()){
                        ActionItem next = iterator.next();
                        Log.e("customizeInputMore", next.getAction());
                    }
                }
                return IChatInputMenu.super.customizeInputMore(actionItemList);
            }
        };

        ChatKitClient.setChatUIConfig(chatUIConfig);
    }

    private View createItem() {
        View rootView = LayoutInflater.from(ChatActivity.this).inflate(R.layout.layout_chat_notice_top_title, null);
        LinearLayout mLlContainer = rootView.findViewById(R.id.ll_container);

        for (int i = 0; i < Setting.activityList.size(); i++) {
            ChatActivityRecommendVo.DataBean dataBean = Setting.activityList.get(i);
            View inflate = LayoutInflater.from(ChatActivity.this).inflate(R.layout.item_chat_activity_recommend_item, null);
            ImageView mIvIcon = inflate.findViewById(R.id.iv_icon);
            TextView mTvName = inflate.findViewById(R.id.tv_name);

            GlideUtils.loadNormalImage(ChatActivity.this, dataBean.getIcon(), mIvIcon);
            mTvName.setText(dataBean.getName());
            inflate.setOnClickListener(view -> {
                new AppJumpAction(this).jumpAction(new AppJumpInfoBean(dataBean.getPage_type(), dataBean.getParam()));
            });

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            layoutParams.topMargin = ScreenUtil.dp2px(ChatActivity.this, 5);
            layoutParams.bottomMargin = ScreenUtil.dp2px(ChatActivity.this, 5);
            layoutParams.leftMargin = ScreenUtil.dp2px(ChatActivity.this, 10);
            mLlContainer.addView(inflate, layoutParams);
        }
        return rootView;
    }

    private View createNoticeItem(ChatTeamNoticeListVo.DataBean dataBean) {
        View inflate = LayoutInflater.from(ChatActivity.this).inflate(R.layout.item_chat_notice_top_title_item, null);
        TextView mTvTitle = inflate.findViewById(R.id.tv_title);

        mTvTitle.setText(dataBean.getTitle());
        inflate.setOnClickListener(view -> {
            FragmentHolderActivity.startFragmentInActivity(this, ChatNoticeFragment.newInstance(teamInfo));
        });

        return inflate;
    }*/
}
