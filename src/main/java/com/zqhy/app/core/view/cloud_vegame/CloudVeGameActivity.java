package com.zqhy.app.core.view.cloud_vegame;

/**
 * 云游页面
 *
 * @author Administrator
 * @date 2018/11/12
 */

public class CloudVeGameActivity{}/* extends BaseActivity<CloudViewModel> implements IStreamListener, IGamePlayerListener, VeGameFloatManager.OnClick {
    private final String TAG = "VeGameEngineActivity";
    private TextView network_state;
    private TextView network_state1;
    private TextView tv_hd;
    private long expireTime;

    public static void newInstance(Activity activity, String uid, String ts_gameId, String gameId, String icon, String gameName, String otherName) {
        Intent intent = new Intent(activity, CloudVeGameActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("ts_gameId", ts_gameId);
        intent.putExtra("gameId", gameId);
        intent.putExtra("icon", icon);
        intent.putExtra("gameName", gameName);
        intent.putExtra("otherName", otherName);
        intent.putExtra("otherName", otherName);
        activity.startActivity(intent);
    }

    protected String uid;
    protected String ts_gameId;
    protected String gameId;
    protected String icon;
    protected String gameName;
    protected String otherName;

    @Override
    public Object getStateEventKey() {
        return null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        uid = getIntent().getStringExtra("uid");
        ts_gameId = getIntent().getStringExtra("ts_gameId");
        gameId = getIntent().getStringExtra("gameId");
        icon = getIntent().getStringExtra("icon");
        gameName = getIntent().getStringExtra("gameName");
        otherName = getIntent().getStringExtra("otherName");
        super.onCreate(savedInstanceState);
        setImmersiveStatusBar(true);
        showSuccess();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        bindView();
        initVeGameEngine();
        initData();
    }
    private void initVeGameEngine() {
        if (Setting.VE_CLOUD_IS_INIT){
            return;
        }
        VeGameEngine gameEngine = VeGameEngine.getInstance();
        *//**
         * 请使用prepare()方法来初始化VeGameEngine，init()方法已废弃。
         *//*
        gameEngine.prepare(this.getApplication());
        gameEngine.addCloudCoreManagerListener(new ICloudCoreManagerStatusListener() {
            *//**
             * 请在onPrepared()回调中监听VeGameEngine的生命周期，onInitialed()回调已废弃
             *//*
            @Override
            public void onInitialed() {

            }

            *//**
             * 1. (0x0001)	未初始化状态
             * 2. (0x0002)	初始化成功
             * 3. (0x0004)	正常调用 start 接口
             * 5. (0x0008)	收到云端首帧
             * 6. (0x0010)	停止拉取云端视音频流，但不会改变云端的运行状态
             *//*
            @Override
            public void onPrepared() {
                // SDK初始化是一个异步过程，在这个回调中监听初始化完成状态
                if (gameEngine.getStatus()==2){
                    Setting.VE_CLOUD_IS_INIT = true;
                }else {
                    Setting.VE_CLOUD_IS_INIT = false;
                }
                AcLog.d("VeGameEngine", "onPrepared :" + gameEngine.getStatus());
            }
        });
        VeGameEngine.setDebug(true);
    }
    private void initData() {
        mViewModel.getTokenPlay(ts_gameId, new OnNetWorkListener<VeTokenVo>() {
            @Override
            public void onBefore() {
                showSuccess();
            }

            @Override
            public void onFailure(String message) {

            }

            @Override
            public void onSuccess(VeTokenVo data) {
                initGamePlayConfig(data);
            }

            @Override
            public void onAfter() {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        popWindowDicker = null;
        VeGameFloatManager.getInstance(this).destroyFloat(this);
    }

    private FrameLayout mContainer;
    private GamePlayConfig mGamePlayConfig;
    private GamePlayConfig.Builder mBuilder;
    private StreamProfileManager mClarityService;

    String ws_url;
    JWebSocketClient client;
    Timer wsTimer;
    Map <String,String> wsMap = new HashMap<>();
    String sendHeart = "";

    private void initGamePlayConfig(VeTokenVo bean) {
        *//**
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         * ak/sk/token用于用户鉴权，需要从火山官网上获取，具体步骤详见README[鉴权相关]。
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *
         * ak/sk/token/gameId的值从assets目录下的sts.json文件中读取，该目录及文件需要自行创建。
         * sts.json的格式形如
         * {
         *     "gameId": "your_game_id",
         *     "ak": "your_ak",
         *     "sk": "your_sk",
         *     "token": "your_token"
         * }
         *//*

        if (bean.isStateOK()) {
            if (bean.getData() == null && bean.getData().getResult() == null) {
                ToastT.error("数据异常,稍后重试");
                if (timer != null) {
                    timer.cancel();
                }
                ll_content_layout.setVisibility(View.VISIBLE);
                tv_bar.setText("数据异常,退出后重试");
                return;
            }
        } else {
            if (timer != null) {
                timer.cancel();
            }
            ll_content_layout.setVisibility(View.VISIBLE);
            tv_bar.setText(bean.getMsg());
            return;
        }
        ws_url = bean.getData().getWs_url();

        expireTime = bean.getData().getExpire_time();
        long currentTime = System.currentTimeMillis() / 1000;

        long secondsUntilExpire = expireTime - currentTime;
        Log.d(TAG, "还有" + secondsUntilExpire + "s后过期");
        String ak = bean.getData().getResult().getAk(),
                sk = bean.getData().getResult().getSk(),
                token = bean.getData().getResult().getToken();
//        String sts = AssetsUtil.getTextFromAssets(getApplicationContext(), "sts.json");
//        try {
//            JSONObject stsJObj = new JSONObject(sts);
//            ak = stsJObj.getString("ak");
//            sk = stsJObj.getString("sk");
//            token = stsJObj.getString("token");
//            gameId = stsJObj.getString("gameId");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        String roundId = "roundId_";//当次游戏生命周期标识符
        String userId = bean.getData().getUid();
        String get_extra = "";
        //初始传值
        Map<String, String> extraMap = new HashMap<>();
        if (UserInfoModel.getInstance().isLogined()) {
            UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
            roundId = roundId + userInfoBean.getUid();
//            get_extra = userInfoBean.getUid() + "_" + userInfoBean.getToken() + "_ds";//折上折平台需要加
            get_extra = userInfoBean.getUid() + "_" + userInfoBean.getToken();
        }
        extraMap.put("enable_browser", "true");
        Log.d(TAG, "roundId: " + roundId);
        Log.d(TAG, "userId: " + userId);
        Log.d(TAG, "get_extra: " + get_extra);
        Log.d(TAG, "gameId: " + gameId);
        extraMap.put("get_extra", get_extra);
        mBuilder = new GamePlayConfig.Builder();
        mBuilder.userId(userId)
                .ak(ak)
                .sk(sk)
                .token(token)
                .container(mContainer)
                .extra(extraMap)
                .roundId(roundId)
                .gameId(gameId)
                .queuePriority(99)//云游默认99
                .videoStreamProfileId(14)//高清
                .streamListener(this);

        mGamePlayConfig = mBuilder.build();
        VeGameEngine.getInstance().start(mGamePlayConfig, this);

    }

//    long secondsUntilExpire;
//    private void secondsUntilExpiration(long expirationTime) {
//        // 获取当前时间的秒级时间戳
//        long currentTime = System.currentTimeMillis() / 1000;
//        // 计算差值
//        secondsUntilExpire = expirationTime - currentTime;
//        if (tktimer!=null) {
//            tktimer.cancel();
//        }
//        tktimer = new Timer();
//        tktimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                secondsUntilExpire=secondsUntilExpire-10;
//                if (secondsUntilExpire < 60) {
//                    tktimer.cancel();
//                    //刷新token
//                    mViewModel.getTokenDemo(gameId,new OnNetWorkListener<VeTokenVo>() {
//                        @Override
//                        public void onBefore() {
//                            showSuccess();
//                        }
//
//                        @Override
//                        public void onFailure(String message) {
//
//                        }
//
//                        @Override
//                        public void onSuccess(VeTokenVo data) {
//                            if (data.isStateOK()&&data.getData().getResult()!=null) {
//                                expireTime = data.getData().getExpire_time();
//                                secondsUntilExpiration(expireTime);
//                                String ak = data.getData().getResult().getAk(),
//                                        sk = data.getData().getResult().getSk(),
//                                        token = data.getData().getResult().getToken();
//                                VeGameEngine.getInstance().resetToken(ak,sk,token);
//                            }else {
//                                ll_content_layout.setVisibility(View.VISIBLE);
//                                tv_bar.setText("登录信息获取失败,请退出界面后重试!");
//                            }
//                        }
//
//                        @Override
//                        public void onAfter() {
//
//                        }
//                    });
//                }
//            }
//        }, 0,10000);//10秒去检查一下token是否过期
//    }

    @Override
    public void onResume() {
        super.onResume();
        VeGameEngine.getInstance().resume();
        if (mMessageChannel != null) {
            mMessageChannel.setMessageListener(new IMessageChannel.IMessageReceiver() {
                *//**
                 * 消息接收回调
                 *
                 * @param iChannelMessage 接收的消息实体
                 *//*
                @Override
                public void onReceiveMessage(IMessageChannel.IChannelMessage iChannelMessage) {
                    String payload = iChannelMessage.getPayload();
                    AcLog.d(TAG, "[onReceiveMessage] message: " + iChannelMessage);
                    AcLog.d(TAG, "[onReceiveMessage] message: " + payload);
                    boolean isNewMessage = true;
                    if (!payload.isEmpty()) {
                        List<CloudPayMessage> allHistory = LitePal.findAll(CloudPayMessage.class);
                        if (allHistory != null && !allHistory.isEmpty()) {
                            for (CloudPayMessage cloudPayMessage : allHistory) {
                                if (payload.equals(cloudPayMessage.getMessage())) {
                                    isNewMessage = false;
                                    break;
                                }
                            }
                        }
                    }
                    if (isNewMessage){
                        //如果本地没有此支付订单则网页打开
                        BrowserCloudPayActivity.newInstance(CloudVeGameActivity.this, payload, true, true);
                        CloudPayMessage bean = new CloudPayMessage(payload);
                        bean.save();
                    }
                }

                @Override
                public void onReceiveBinaryMessage(IMessageChannel.IChannelBinaryMessage iChannelBinaryMessage) {

                }

                *//**
                 * 发送消息结果回调
                 *
                 * @param success 是否发送成功
                 * @param messageId 消息ID
                 *//*
                @Override
                public void onSentResult(boolean success, String messageId) {
                    AcLog.d(TAG, "[onSentResult] success: " + success + ", messageId: " + messageId);
                }

                *//**
                 * 已弃用，可忽略
                 *//*
                @Override
                public void ready() {
                    AcLog.d(TAG, "[ready]");
                }

                *//**
                 * 错误信息回调
                 *
                 * @param errorCode 错误码
                 * @param errorMessage 错误信息
                 *//*
                @Override
                public void onError(int errorCode, String errorMessage) {
                    AcLog.d(TAG, "[onError] errorCode: " + errorCode + ", errorMessage: " + errorMessage);
                }

                *//**
                 * 云端游戏在线回调，建议在发送消息前监听该回调检查通道是否已连接
                 *
                 * @param channelUid 云端游戏的用户ID
                 *//*
                @Override
                public void onRemoteOnline(String channelUid) {
                    AcLog.d(TAG, "[onRemoteOnline] channelUid: " + channelUid);
                }

                *//**
                 * 云端游戏离线回调
                 *
                 * @param channelUid 云端游戏的用户ID
                 *//*
                @Override
                public void onRemoteOffline(String channelUid) {
                    AcLog.d(TAG, "[onRemoteOffline] channelUid: " + channelUid);
                }
            });
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onPause() {
        super.onPause();
        VeGameEngine.getInstance().pause();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cloud_vegame;
    }

    @Override
    public void finish() {
        closeConnect();
        if (wsTimer!=null) {
            wsTimer.cancel();
        }
        VeGameEngine.getInstance().stop();
        super.finish();
    }


    int myOrientation = 1;

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        AcLog.d(TAG, "[onConfigurationChanged] newConfig: " + newConfig.orientation);
        myOrientation = newConfig.orientation;
        VeGameEngine.getInstance().rotate(newConfig.orientation);
        super.onConfigurationChanged(newConfig);
        VeGameFloatManager.getInstance(this).createFloat();
        VeGameFloatManager.getInstance(this).setImgResource(R.mipmap.ic_fragment_cloud_vegame_2);
        VeGameFloatManager.getInstance(this).setText("0ms");
        VeGameFloatManager.getInstance(this).showFloat();
        VeGameFloatManager.getInstance(this).setMyClickListener(this);
        contentView = LayoutInflater.from(this).inflate(R.layout.pop_cloud_vegame_menu, null);
        network_state = contentView.findViewById(R.id.network_state);
        tv_hd = contentView.findViewById(R.id.tv_hd);
        contentView.findViewById(R.id.kefu).setOnClickListener(v -> {
            FragmentHolderActivity.startFragmentInActivity(this, new KefuCenterFragment(), true);
        });
        tv_hd.setOnClickListener(view -> {
            View inflate = LayoutInflater.from(this).inflate(R.layout.pop_cloud_vegame_hdmenu, null);
            CustomPopWindow hdDicker = new CustomPopWindow.PopupWindowBuilder(this)
                    .enableOutsideTouchableDissmiss(true)
                    .enableBackgroundDark(false)
                    .setView(inflate)
                    .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                    .create();
            inflate.findViewById(R.id.tv_hd1).setOnClickListener(view1 -> {
                if (mClarityService != null) {
                    mClarityService.switchVideoStreamProfileId(2);
                }
                if (hdDicker.getmPopupWindow().isShowing()) {
                    hdDicker.dissmiss();
                }
            });
            inflate.findViewById(R.id.tv_hd2).setOnClickListener(view1 -> {
                if (mClarityService != null) {
                    mClarityService.switchVideoStreamProfileId(6);
                }
                if (hdDicker.getmPopupWindow().isShowing()) {
                    hdDicker.dissmiss();
                }
            });
            inflate.findViewById(R.id.tv_hd3).setOnClickListener(view1 -> {
                if (mClarityService != null) {
                    mClarityService.switchVideoStreamProfileId(14);
                }
                if (hdDicker.getmPopupWindow().isShowing()) {
                    hdDicker.dissmiss();
                }
            });
            inflate.findViewById(R.id.tv_hd4).setOnClickListener(view1 -> {
                if (mClarityService != null) {
                    mClarityService.switchVideoStreamProfileId(16);
                }
                if (hdDicker.getmPopupWindow().isShowing()) {
                    hdDicker.dissmiss();
                }
            });
            hdDicker.showAtLocation(inflate, Gravity.CENTER, 0, 0);
        });
        //处理popWindow 显示内容
        //创建并显示popWindow
        popWindowDicker = new CustomPopWindow.PopupWindowBuilder(this)
                .enableOutsideTouchableDissmiss(true)
                .enableBackgroundDark(false)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                .create();
        contentView.findViewById(R.id.iv_back).setOnClickListener(v -> {
            if (VeGameEngine.getInstance().isPlaying()) {
                VeGameEngine.getInstance().stop();
            }
            closeConnect();
            finish();
        });
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        if (UserInfoModel.getInstance().isLogined()) {
            if (VeGameEngine.getInstance().isPlaying()) {
                VeGameEngine.getInstance().stop();
            }
            closeConnect();
            finish();
        }
    }

    View contentView;
    LinearLayoutCompat lin_line_up;
    TextView tv_lineup;
    ConstraintLayout ll_content_layout;//加载页面
    TextView tv_bar;
    ProgressBar progress_bar;
    CustomPopWindow popWindowDicker;


    int hd_type = 14;//6（标清，默认档位）  2（流畅）  14（高清） 16（超清）
    int progress = 0;

    private void bindView() {
        mContainer = findViewById(R.id.container);
        lin_line_up = findViewById(R.id.lin_line_up);
        tv_lineup = findViewById(R.id.tv_lineup);
        ll_content_layout = findViewById(R.id.ll_content_layout);
        progress_bar = findViewById(R.id.progress_bar);
        tv_bar = findViewById(R.id.tv_bar);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (progress < 80) {
                    progress = progress + 2;
                    CloudVeGameActivity.this.runOnUiThread(() -> {
                        tv_bar.setText("游戏加载中...(" + progress + "%)");
                        progress_bar.setProgress(progress);
                    });
                } else {
                    CloudVeGameActivity.this.runOnUiThread(() -> {
                        tv_bar.setText("游戏加载中...(" + progress + "%)");
                        progress_bar.setProgress(progress);
                    });
                    timer.cancel();
                }
            }
        }, 0, 100);

        ShapeableImageView iconView = findViewById(R.id.icon);
        TextView tv_name = findViewById(R.id.tv_name);
        TextView tv_other = findViewById(R.id.tv_other);
        tv_name.setText(gameName);
        tv_other.setText(otherName);
        GlideApp.with(this)
                .load(icon)
                .placeholder(R.mipmap.ic_placeholder)
                .error(R.mipmap.ic_placeholder)
                .into(iconView);
        VeGameFloatManager.getInstance(this).createFloat();
        VeGameFloatManager.getInstance(this).setImgResource(R.mipmap.ic_fragment_cloud_vegame_2);
        VeGameFloatManager.getInstance(this).setText("0ms");
        VeGameFloatManager.getInstance(this).showFloat();
        VeGameFloatManager.getInstance(this).setMyClickListener(this);
        contentView = LayoutInflater.from(this).inflate(R.layout.pop_cloud_vegame_menu, null);
        network_state = contentView.findViewById(R.id.network_state);
        tv_hd = contentView.findViewById(R.id.tv_hd);
        contentView.findViewById(R.id.kefu).setOnClickListener(v -> {
            FragmentHolderActivity.startFragmentInActivity(this, new KefuCenterFragment(), true);
        });
        tv_hd.setOnClickListener(view -> {
            View inflate = LayoutInflater.from(this).inflate(R.layout.pop_cloud_vegame_hdmenu, null);
            CustomPopWindow hdDicker = new CustomPopWindow.PopupWindowBuilder(this)
                    .enableOutsideTouchableDissmiss(true)
                    .enableBackgroundDark(false)
                    .setView(inflate)
                    .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                    .create();
            inflate.findViewById(R.id.tv_hd1).setOnClickListener(view1 -> {
                if (mClarityService != null) {
                    mClarityService.switchVideoStreamProfileId(2);
                }
                if (hdDicker.getmPopupWindow().isShowing()) {
                    hdDicker.dissmiss();
                }
            });
            inflate.findViewById(R.id.tv_hd2).setOnClickListener(view1 -> {
                if (mClarityService != null) {
                    mClarityService.switchVideoStreamProfileId(6);
                }
                if (hdDicker.getmPopupWindow().isShowing()) {
                    hdDicker.dissmiss();
                }
            });
            inflate.findViewById(R.id.tv_hd3).setOnClickListener(view1 -> {
                if (mClarityService != null) {
                    mClarityService.switchVideoStreamProfileId(14);
                }
                if (hdDicker.getmPopupWindow().isShowing()) {
                    hdDicker.dissmiss();
                }
            });
            inflate.findViewById(R.id.tv_hd4).setOnClickListener(view1 -> {
                if (mClarityService != null) {
                    mClarityService.switchVideoStreamProfileId(16);
                }
                if (hdDicker.getmPopupWindow().isShowing()) {
                    hdDicker.dissmiss();
                }
            });
            hdDicker.showAtLocation(inflate, Gravity.CENTER, 0, 0);
        });
        //处理popWindow 显示内容
        //创建并显示popWindow
        popWindowDicker = new CustomPopWindow.PopupWindowBuilder(this)
                .enableOutsideTouchableDissmiss(true)
                .enableBackgroundDark(false)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                .create();
        contentView.findViewById(R.id.iv_back).setOnClickListener(v -> {
            if (VeGameEngine.getInstance().isPlaying()) {
                VeGameEngine.getInstance().stop();
            }
            closeConnect();
            finish();
        });


    }

    Timer tktimer;
    Timer timer;

    @Override
    public void onMyClick(View view) {
        if (popWindowDicker == null) {
            return;
        }
        //关闭软键盘
        if (mLocalInputManager != null && mLocalInputManager.getKeyboardEnable()) {
            mLocalInputManager.setKeyBoardEnable(false);
        }
        //菜单点击事件
        if (popWindowDicker.getmPopupWindow().isShowing()) {
            popWindowDicker.dissmiss();
        } else {
            showMenu(view);
        }
    }

    int popWidth = 645;

    private void showMenu(View parent) {
        if (popWindowDicker == null) {
            return;
        }

        if (tv_hd != null) {
            switch (hd_type) {
                case 2:
                    tv_hd.setText("流畅");
                    break;
                case 14:
                    tv_hd.setText("高清");
                    break;
                case 16:
                    tv_hd.setText("超清");
                    break;
                default:
                    tv_hd.setText("标清");
                    break;
            }
        }

        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        Log.d(TAG, "showMenu: " + contentView.getWidth());
        if (contentView.getWidth() != 0) {
            popWidth = contentView.getWidth();
        }

        int y = location[1]; // 可以选择view的顶部、中心或底部作为PopupWindow的y坐标

        try {


            switch (VeGameFloatManager.getInstance(this).getNowState()) {
                case VeGameFloatManager.LEFT:
                    popWindowDicker.showAtLocation(parent.getRootView(), Gravity.NO_GRAVITY, parent.getWidth() + 20, y);
                    break;
                case VeGameFloatManager.RIGHT:
//
                    popWindowDicker.showAtLocation(parent.getRootView(), Gravity.NO_GRAVITY, SizeUtils.getScreenWidth(this) - popWidth - parent.getWidth() - 20, y);
                    break;
            }
        } catch (Exception e) {

        }
    }

    *//**
     * 播放成功回调
     *
     * @param roundId    当次游戏生命周期标识符
     * @param clarityId  当前游戏画面的清晰度，首帧渲染到画面时触发该回调
     * @param extraMap   自定义的扩展参数
     * @param gameId     游戏ID
     * @param reservedId 资源预锁定ID
     *//*

    @Override
    public void onPlaySuccess(String roundId, int clarityId, Map<String, String> extraMap, String gameId, String reservedId) {
        VeGameFloatManager.getInstance(this).setImgResource(R.mipmap.ic_fragment_cloud_vegame_2);
        hd_type = clarityId;
        if (timer != null) {
            timer.cancel();
        }
        wsMap.put("Msgkey","Heart");
        UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
        wsMap.put("uid",userInfoBean.getUid()+"");
        wsMap.put("reserved_id",reservedId);
        Gson gson = new Gson();
        sendHeart = gson.toJson(wsMap);

        timer = null;
        //开始云游
        Map<String, String> params = new TreeMap<>();
        params.put("uid", uid);//本平台id
        params.put("gameid", ts_gameId);//本平台id
        params.put("reserved_id", reservedId);
        mViewModel.getVolcenginePlayStart(params, new OnNetWorkListener() {
            @Override
            public void onBefore() {

            }

            @Override
            public void onFailure(String message) {
                if (timer != null) {
                    timer.cancel();
                }
                tv_bar.setText("开启云游失败,请退出后重试!");
                ll_content_layout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(BaseVo data) {
                if (data != null && "ok".equals(data.getState())) {
                    timer = new Timer();
                    progress = 80;
                    tv_bar.setText("游戏加载中...(" + progress + "%)");
                    progress_bar.setProgress(progress);
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (progress < 100) {
                                progress = progress + 1;
                                CloudVeGameActivity.this.runOnUiThread(() -> {
                                    tv_bar.setText("游戏加载中...(" + progress + "%)");
                                    progress_bar.setProgress(progress);
                                });
                            } else {
                                CloudVeGameActivity.this.runOnUiThread(() -> {
                                    tv_bar.setText("游戏加载中...(" + progress + "%)");
                                    progress_bar.setProgress(progress);
                                    ll_content_layout.setVisibility(View.GONE);
                                });
                                timer.cancel();
                            }
                        }
                    }, 0, 50);

                    //心跳包

                    //ws:\/\/111.229.192.138:8081\/
                    URI uri = URI.create(ws_url);
                    client = new JWebSocketClient(uri) {
                        @Override
                        public void onMessage(String message) {
                            //message就是接收到的消息
                            Log.e("JWebSClientService", message);
                        }

                        @Override
                        public void onOpen(ServerHandshake handShakeData) {
                            super.onOpen(handShakeData);
                            Log.e("JWebSClientService", "onOpen");
                        }

                        @Override
                        public void onClose(int code, String reason, boolean remote) {
                            super.onClose(code, reason, remote);
                            Log.e("JWebSClientService", "onClose");
                            try {
                                if (timer != null) {
                                    timer.cancel();
                                }
                                tv_bar.setText("服务器断开链接,请稍后再试!");
                                ll_content_layout.setVisibility(View.VISIBLE);
                                VeGameEngine.getInstance().stop();
                            }catch (Exception e) {

                            }
                        }

                        @Override
                        public void onError(Exception ex) {
                            super.onError(ex);
                            if (timer != null) {
                                timer.cancel();
                            }
                            tv_bar.setText("服务器链接失败,请稍后再试!onError");
                            ll_content_layout.setVisibility(View.VISIBLE);
                            VeGameEngine.getInstance().stop();
                            Log.e("JWebSClientService", "onError");
                        }
                    };
                    try {
                        client.connectBlocking();
                        wsTimer = new Timer();
                        wsTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Log.d(TAG, "wsTimer run: 发送心跳");
                                Log.d(TAG, sendHeart);
                                if (client != null && client.isOpen()) {
                                    client.send(sendHeart);
                                }
                            }
                        }, 0, 5000);
                    } catch (Exception e) {
                        if (timer != null) {
                            timer.cancel();
                        }
                        tv_bar.setText("服务器链接失败,请稍后再试!");
                        ll_content_layout.setVisibility(View.VISIBLE);
                        VeGameEngine.getInstance().stop();
                    }
                } else {
                    if (timer != null) {
                        timer.cancel();
                    }
                    tv_bar.setText("开启云游失败,请退出后重试! msg:" + data.getMsg());
                    ll_content_layout.setVisibility(View.VISIBLE);
                    VeGameEngine.getInstance().stop();
                    closeConnect();
                }
            }

            @Override
            public void onAfter() {

            }
        });

        AcLog.d(TAG, "[onPlaySuccess] roundId " + roundId + " clarityId " + clarityId + "extra:" + extraMap +
                "gameId : " + gameId + " reservedId" + reservedId);
    }

    *//**
     * SDK内部产生的错误回调
     *
     * @param errorCode    错误码
     * @param errorMessage 错误详情
     *//*
    @Override
    public void onError(int errorCode, String errorMessage) {
        AcLog.e(TAG, "IGamePlayerListener [onError] errorCode: " + errorCode + ", errorMessage: " + errorMessage);
        if (timer != null) {
            timer.cancel();
        }
        closeConnect();
        ll_content_layout.setVisibility(View.VISIBLE);
        tv_bar.setText("加载失败,请退出后重试或联系客服 错误代码:" + errorCode);
    }

    *//**
     * SDK内部产生的警告回调
     *
     * @param warningCode    警告码
     * @param warningMessage 警告详情
     *//*
    @Override
    public void onWarning(int warningCode, String warningMessage) {
        AcLog.d(TAG, "[onWarning] warningCode: " + warningCode + ", warningMessage: " + warningMessage);
    }

    *//**
     * 网络连接类型和状态切换回调
     *
     * @param networkType 当前的网络类型
     *                    -1 -- 网络连接类型未知
     *                    0 -- 网络连接已断开
     *                    1 -- 网络类型为 LAN
     *                    2 -- 网络类型为 Wi-Fi（包含热点）
     *                    3 -- 网络类型为 2G 移动网络
     *                    4 -- 网络类型为 3G 移动网络
     *                    5 -- 网络类型为 4G 移动网络
     *                    6 -- 网络类型为 5G 移动网络
     *//*
    @Override
    public void onNetworkChanged(int networkType) {
        AcLog.d(TAG, "[onNetworkChanged] networkType: " + networkType);
    }

    *//**
     * 加入房间前回调，用于获取并初始化各个功能服务，例如设置各种事件监听回调。
     *//*
    private IMessageChannel mMessageChannel;
    private LocalInputManager mLocalInputManager;//软键盘管理

    @Override
    public void onServiceInit() {
        AcLog.d(TAG, "[onServiceInit]");
        mMessageChannel = VeGameEngine.getInstance().getMessageChannel();
        if (mMessageChannel != null) {
            *//**
             * 设置消息接收回调监听
             *
             * @param listener 消息接收回调监听器
             *//*
            mMessageChannel.setMessageListener(new IMessageChannel.IMessageReceiver() {
                *//**
                 * 消息接收回调
                 *
                 * @param iChannelMessage 接收的消息实体
                 *//*
                @Override
                public void onReceiveMessage(IMessageChannel.IChannelMessage iChannelMessage) {
                    String payload = iChannelMessage.getPayload();
                    AcLog.d(TAG, "[onReceiveMessage] message: " + iChannelMessage);
                    AcLog.d(TAG, "[onReceiveMessage] message: " + payload);
                    boolean isNewMessage = true;
                    if (!payload.isEmpty()) {
                        List<CloudPayMessage> allHistory = LitePal.findAll(CloudPayMessage.class);
                        if (allHistory != null && !allHistory.isEmpty()) {
                            for (CloudPayMessage cloudPayMessage : allHistory) {
                                if (payload.equals(cloudPayMessage.getMessage())) {
                                    isNewMessage = false;
                                    break;
                                }
                            }
                        }
                    }
                    if (isNewMessage){
                        //如果本地没有此支付订单则网页打开
                        BrowserCloudPayActivity.newInstance(CloudVeGameActivity.this, payload, true, true);
                        CloudPayMessage bean = new CloudPayMessage(payload);
                        bean.save();
                    }
                }

                @Override
                public void onReceiveBinaryMessage(IMessageChannel.IChannelBinaryMessage iChannelBinaryMessage) {

                }

                *//**
                 * 发送消息结果回调
                 *
                 * @param success 是否发送成功
                 * @param messageId 消息ID
                 *//*
                @Override
                public void onSentResult(boolean success, String messageId) {
                    AcLog.d(TAG, "[onSentResult] success: " + success + ", messageId: " + messageId);
                }

                *//**
                 * 已弃用，可忽略
                 *//*
                @Override
                public void ready() {
                    AcLog.d(TAG, "[ready]");
                }

                *//**
                 * 错误信息回调
                 *
                 * @param errorCode 错误码
                 * @param errorMessage 错误信息
                 *//*
                @Override
                public void onError(int errorCode, String errorMessage) {
                    AcLog.d(TAG, "[onError] errorCode: " + errorCode + ", errorMessage: " + errorMessage);
                }

                *//**
                 * 云端游戏在线回调，建议在发送消息前监听该回调检查通道是否已连接
                 *
                 * @param channelUid 云端游戏的用户ID
                 *//*
                @Override
                public void onRemoteOnline(String channelUid) {
                    AcLog.d(TAG, "[onRemoteOnline] channelUid: " + channelUid);
                }

                *//**
                 * 云端游戏离线回调
                 *
                 * @param channelUid 云端游戏的用户ID
                 *//*
                @Override
                public void onRemoteOffline(String channelUid) {
                    AcLog.d(TAG, "[onRemoteOffline] channelUid: " + channelUid);
                }
            });
        }

        mClarityService = VeGameEngine.getInstance().getClarityService();
        if (mClarityService != null) {
            mClarityService.setStreamProfileChangeListener(new StreamProfileChangeCallBack() {
                @Override
                public void onVideoStreamProfileChange(boolean success, int from, int to) {
                    if (success) {
                        hd_type = to;
                        if (tv_hd != null) {
                            switch (hd_type) {
                                case 2:
                                    tv_hd.setText("流畅");
                                    ToastT.success(CloudVeGameActivity.this, "切换至流畅");
                                    break;
                                case 14:
                                    tv_hd.setText("高清");
                                    ToastT.success(CloudVeGameActivity.this, "切换至高清");
                                    break;
                                case 16:
                                    tv_hd.setText("超清");
                                    ToastT.success(CloudVeGameActivity.this, "切换至超清");
                                    break;
                                default:
                                    tv_hd.setText("标清");
                                    ToastT.success(CloudVeGameActivity.this, "切换至标清");
                                    break;
                            }
                        }
                    } else {
                        ToastT.error(CloudVeGameActivity.this, "清晰度切换失败");
                    }
                }

                @Override
                public void onError(int errorCode, String errorMessage) {
                    ToastT.error(CloudVeGameActivity.this, "清晰度切换失败");
                }
            });
        }

        mLocalInputManager = VeGameEngine.getInstance().getLocalInputManager();
        if (mLocalInputManager != null) {
            mLocalInputManager.setRemoteInputCallBack(new LocalInputManager.RemoteInputCallBack() {
                *//**
                 * 云端输入法准备阶段的一些状态回调
                 *
                 * @param hintText 提示文本
                 * @param inputType 输入格式
                 *//*
                @Override
                public void onPrepare(String hintText, int inputType) {
                    AcLog.d(TAG, "[onPrepare] hintText: " + hintText + ", inputType: " + inputType);
                }

                *//**
                 * 云端输入法软键盘请求弹出的回调，该回调会触发多次
                 *//*
                @Override
                public void onCommandShow() {
                    AcLog.d(TAG, "[onCommandShow]");
                }

                *//**
                 * 云端输入法软键盘请求收起的回调
                 *//*
                @Override
                public void onCommandHide() {
                    AcLog.d(TAG, "[onCommandHide]");
                }

                *//**
                 * 云端输入框内容变化的回调
                 *
                 * @param text 输入框内容发生变化后的文本信息
                 *//*
                @Override
                public void onTextChange(String text) {
                    AcLog.d(TAG, "[onTextChange] text: " + text);
                }

                *//**
                 * 云端输入法状态更新的回调
                 *
                 * @param enable  true -- 云端输入法已开启
                 *               false -- 云端输入法已关闭
                 *//*
                @Override
                public void onRemoteKeyBoardEnabled(boolean enable) {
                    AcLog.d(TAG, "[keyBoardEnable] enable: " + enable);
                }

                *//**
                 * 不适用于云游戏场景，可忽略
                 *
                 * @param enable
                 *//*
                @Override
                public void onTextInputEnableStateChanged(boolean enable) {
                    AcLog.d(TAG, "[onTextInputEnableStateChanged] enable: " + enable);
                }
            });
        }
    }

    *//**
     * 排队信息更新回调
     *
     * @param queueInfoList 当前的排队队列信息
     *//*
    @Override
    public void onQueueUpdate(List<QueueInfo> queueInfoList) {
        lin_line_up.setVisibility(View.VISIBLE);
        QueueInfo queueInfo = queueInfoList.get(0);
        tv_lineup.setText("前面还有" + queueInfo.userPosition + "位玩家");
        AcLog.d(TAG, "[onQueueUpdate] list: " + queueInfoList);
    }

    *//**
     * 排队结束，开始申请资源的回调
     *
     * @param remainTime 当用户排到第0位时申请服务的等待时间，超过时间未进入会被移出队列
     *//*
    @Override
    public void onQueueSuccessAndStart(int remainTime) {
        lin_line_up.setVisibility(View.GONE);
        AcLog.d(TAG, "[onQueueSuccessAndStart] remainTime: " + remainTime);
    }

    *//**
     * 收到音频首帧时的回调
     *
     * @param audioStreamId 远端实例音频流的ID
     *//*
    @Override
    public void onFirstAudioFrame(String audioStreamId) {
        AcLog.d(TAG, "[onFirstAudioFrame] audioStreamId: " + audioStreamId);
    }

    *//**
     * 收到视频首帧时的回调
     *
     * @param videoStreamId 远端实例视频流的ID
     *//*
    @Override
    public void onFirstRemoteVideoFrame(String videoStreamId) {
        AcLog.d(TAG, "[onFirstRemoteVideoFrame] videoStreamId: " + videoStreamId);
    }

    *//**
     * 开始播放的回调
     *//*
    @Override
    public void onStreamStarted() {
        AcLog.d(TAG, "[onStreamStarted]");
    }

    *//**
     * 暂停播放后的回调，调用{@link VeGameEngine#pause()}后会触发
     *//*
    @Override
    public void onStreamPaused() {
        AcLog.d(TAG, "[onStreamPaused]");
    }

    *//**
     * 恢复播放后的回调，调用{@link VeGameEngine#resume()} 或 VeGameEngine#muteAudio(false) 后会触发
     *//*
    @Override
    public void onStreamResumed() {
        AcLog.d(TAG, "[onStreamResumed]");
    }

    *//**
     * 周期为2秒的音视频网络状态的回调，可用于内部数据分析或监控
     *
     * @param streamStats 远端视频流的性能状态
     *//*
    @Override
    public void onStreamStats(StreamStats streamStats) {
        AcLog.d(TAG, "[onStreamStats] streamStats: " + streamStats);
    }

    *//**
     * 周期为2秒的本地推送的音视频流的状态回调
     *
     * @param localStreamStats 本地音视频流的性能状态
     *//*
    @Override
    public void onLocalStreamStats(LocalStreamStats localStreamStats) {
        AcLog.d(TAG, "[onLocalStreamStats] localStreamStats: " + localStreamStats);
    }

    *//**
     * 视频流连接状态变化
     *
     * @param state 视频流连接状态
     *              1 -- 连接断开
     *              2 -- 首次连接，正在连接中
     *              3 -- 首次连接成功
     *              4 -- 连接断开后，重新连接中
     *              5 -- 连接断开后，重新连接成功
     *              6 -- 连接断开超过10秒，但仍然会继续连接
     *              7 -- 连接失败，不会继续连接
     *//*
    @Override
    public void onStreamConnectionStateChanged(int state) {
        AcLog.d(TAG, "[onStreamConnectionStateChanged] connectionState: " + state);
    }

    *//**
     * 操作延迟回调
     *
     * @param elapse 操作延迟的具体值，单位:毫秒
     *//*
    @Override
    public void onDetectDelay(long elapse) {
        AcLog.d(TAG, "[onDetectDelay] detectDelay: " + elapse);
        if (network_state != null) {
            network_state.setText(elapse + "ms");
        }
        VeGameFloatManager.getInstance(this).setText(elapse + "ms");
    }

    *//**
     * 客户端的旋转回调
     * <p>
     * 远端实例通过该回调向客户端发送视频流的方向(横屏或竖屏)，为保证视频流方向与Activity方向一致，
     * 需要在该回调中根据rotation参数，调用  BasePlayActivity#setRotation(int) 来调整Activity的方向，
     * 0/180需将Activity调整为竖屏，90/270则将Activity调整为横屏；
     * 同时，需要在 MessageChannelActivity#onConfigurationChanged(Configuration)} 回调中，
     * 根据当前Activity的方向，调用 {@link VeGameEngine#rotate(int)} 来调整视频流的方向。
     *
     * @param rotation 旋转方向
     *                 0, 180 -- 竖屏
     *                 90, 270 -- 横屏
     *//*
    @Override
    public void onRotation(int rotation) {
        AcLog.d(TAG, "[onRotation] rotation: " + rotation);
        setRotation(rotation);
    }

    protected void setRotation(int rotation) {
        switch (rotation) {
            case 0:
            case 180:
                setRequestedOrientation(SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                break;
            case 90:
            case 270:
                setRequestedOrientation(SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                break;
        }
    }

    *//**
     * 远端实例退出回调
     *
     * @param reasonCode    退出的原因码
     * @param reasonMessage 退出的原因详情
     *//*
    @Override
    public void onPodExit(int reasonCode, String reasonMessage) {
        AcLog.d(TAG, "[onPodExit] reasonCode: " + reasonCode + ", reasonMessage: " + reasonMessage);
        closeConnect();
        try {
            if (reasonCode == 40004) {
                if (timer != null) {
                    timer.cancel();
                }
                ll_content_layout.setVisibility(View.VISIBLE);
                tv_bar.setText("长时间无操作,强制下线!");
                takenOffline();
            } else if (reasonCode == 40006) {
                if (timer != null) {
                    timer.cancel();
                }
                ll_content_layout.setVisibility(View.VISIBLE);
                tv_bar.setText("云游时间已用完,感谢您的游玩!续费后可继续云游~");
                timeOver();
            } else {
                if (timer != null) {
                    timer.cancel();
                }
                ll_content_layout.setVisibility(View.VISIBLE);
                tv_bar.setText("云游异常退出,请退出重试 reasonCode: " + reasonCode + ", reasonMessage: " + reasonMessage);
            }

        } catch (Exception e) {

        }
    }

    *//**
     * 周期为2秒的游戏中的网络质量回调
     *
     * @param quality 网络质量评级
     *                0 -- 网络状况未知，无法判断网络质量
     *                1 -- 网络状况极佳，能够高质量承载当前业务
     *                2 -- 当前网络状况良好，能够较好地承载当前业务
     *                3 -- 当前网络状况有轻微劣化，但不影响正常使用
     *                4 -- 当前网络质量欠佳，会影响当前业务的主观体验
     *                5 -- 当前网络已经无法承载当前业务的媒体流，需要采取相应策略，
     *                比如降低媒体流的码率或者更换网络
     *                6 -- 当前网络完全无法正常通信
     *//*
    @Override
    public void onNetworkQuality(int quality) {
        AcLog.d(TAG, "[onNetworkQuality] quality: " + quality);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            //返回键处理
            confirmExitGame();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void confirmExitGame() {
        CustomDialog tipsDialog = new CustomDialog(this, LayoutInflater.from(this).inflate(R.layout.layout_dialog_cloud_ve_exit, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (VeGameEngine.getInstance().isPlaying()) {
                VeGameEngine.getInstance().stop();
            }
            closeConnect();
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            finish();
        });
        tipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        try {
            tipsDialog.show();
        } catch (Exception e) {

        }
    }

    private void takenOffline() {
        CustomDialog tipsDialog = new CustomDialog(this, LayoutInflater.from(this).inflate(R.layout.layout_dialog_cloud_ve_taken_offline, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (VeGameEngine.getInstance().isPlaying()) {
                VeGameEngine.getInstance().stop();
            }
            closeConnect();
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            finish();
        });
        try {
            tipsDialog.show();
        } catch (Exception e) {

        }
    }

    private void timeOver() {
        CustomDialog tipsDialog = new CustomDialog(this, LayoutInflater.from(this).inflate(R.layout.layout_dialog_cloud_ve_time_over, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (VeGameEngine.getInstance().isPlaying()) {
                VeGameEngine.getInstance().stop();
            }
            closeConnect();
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            finish();
        });
        try {
            tipsDialog.show();
        } catch (Exception e) {

        }
    }

    private void closeConnect() {
        try {
            if (null != client) {
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client = null;
        }
    }

}*/
