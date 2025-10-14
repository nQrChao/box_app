package com.zqhy.app.core.view.bipartition;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class BipartitionListFragment extends BaseFragment<GameViewModel> {

    /*public static BipartitionListFragment newInstance() {
        BipartitionListFragment fragment = new BipartitionListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static BipartitionListFragment newInstance(int gameid) {
        BipartitionListFragment fragment = new BipartitionListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("gameid", gameid);
        fragment.setArguments(bundle);
        return fragment;
    }*/

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_bipartition_list;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    protected String getUmengPageName() {
        return "双开应用列表";
    }

    /*private int mGameid;
    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            mGameid = getArguments().getInt("gameid");
        }
        super.initView(state);
        showSuccess();
        bindView();
        initData();
        getGameList();
        // 注册事件
        GmSpaceObject.registerGmSpaceReceivedEventListener(receivedEventListener);
        multipleLaunchers(0, "begin");

        if (mGameid > 0){
            new Handler().postDelayed(() -> startFragment(BipartitionInstallFragment.newInstance(mGameid, !EasyFloat.isShow("float_download"))), 300);
        }
    }

    private LinearLayout mLlTopTips;
    private ImageView mIvTopClose;
    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerViewHot;
    private BaseRecyclerAdapter mHotAdapter;
    private void bindView() {
        findViewById(R.id.iv_back).setOnClickListener(v -> pop());
        findViewById(R.id.iv_download).setOnClickListener(view -> {
            //下载
            if (BuildConfig.IS_DOWNLOAD_GAME_FIRST || checkLogin()) {
                startFragment(new GameDownloadManagerFragment());
            }
        });

        mLlTopTips = findViewById(R.id.ll_top_tips);
        mIvTopClose = findViewById(R.id.iv_top_close);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerViewHot = findViewById(R.id.recyclerview_hot);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(_mActivity, 4);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewHot.setLayoutManager(linearLayoutManager);

        mAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(BipartitionGameVo.class, new PackageInfoItemHolder(_mActivity))
                .bind(String.class, new AddPackageInfoHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
        mRecyclerView.setAdapter(mAdapter);

        mHotAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.class, new GameItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
        mRecyclerViewHot.setAdapter(mHotAdapter);

        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
        boolean show_bipartition_tips = spUtils.getBoolean("show_bipartition_tips", false);
        if (show_bipartition_tips){
            mLlTopTips.setVisibility(View.GONE);
        }else {
            mLlTopTips.setVisibility(View.VISIBLE);
        }

        mIvTopClose.setOnClickListener(view -> {
            mLlTopTips.setVisibility(View.GONE);
            spUtils.putBoolean("show_bipartition_tips", true);
        });
    }

    private void initData() {
        mAdapter.clear();
        List<BipartitionGameVo> bipartitionGameList = BipartitionGameDbInstance.getInstance().getBipartitionGameList();

        if (bipartitionGameList != null && bipartitionGameList.size() > 0){
            mAdapter.setDatas(bipartitionGameList);
        }
        mAdapter.addData("Add");
        mAdapter.notifyDataSetChanged();
    }

    private void getGameList(){
        if (mViewModel != null) {
            mViewModel.getGameList(new OnBaseCallback<GameListVo>() {
                @Override
                public void onSuccess(GameListVo data) {
                    if (data != null && data.isStateOK()){
                        if(data.getData() != null){
                            mRecyclerViewHot.setVisibility(View.VISIBLE);
                            mHotAdapter.setDatas(data.getData());
                            mHotAdapter.notifyDataSetChanged();
                        }else {
                            mRecyclerViewHot.setVisibility(View.GONE);
                        }
                    }else {
                        mRecyclerViewHot.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void getGameList(String kw){
        if (mViewModel != null) {
            mViewModel.getGameList(kw, page, new OnBaseCallback<GameListVo>() {
                @Override
                public void onSuccess(GameListVo data) {
                    mGameRecyclerView.refreshComplete();
                    mGameRecyclerView.loadMoreComplete();
                    if (data != null && data.isStateOK()){
                        if (data.getData() != null && !data.getData().isEmpty()) {
                            if (page <= 1) {
                                gameAdapter.setDatas(data.getData());
                            }else {
                                gameAdapter.addAllData(data.getData());
                            }
                            if (data.getData().size() < 12) {
                                //has no more data
                                page = -1;
                                mGameRecyclerView.setNoMore(true);
                            }
                            gameAdapter.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                gameAdapter.setDatas(new ArrayList());
                                gameAdapter.addData(new EmptyDataVo(R.mipmap.img_empty_data_1)
                                        .setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT)
                                        .setEmptyWord("支持双开游戏持续更新中，敬请期待...")
                                        .setEmptyWordColor(R.color.color_9b9b9b));
                            } else {
                                //has no more data
                            }
                            page = -1;
                            mGameRecyclerView.setNoMore(true);
                            gameAdapter.notifyDataSetChanged();
                        }
                    }else {
                        gameAdapter.setDatas(new ArrayList());
                        gameAdapter.addData(new EmptyDataVo(R.mipmap.img_empty_data_1)
                                .setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT)
                                .setEmptyWord("支持双开游戏持续更新中，敬请期待...")
                                .setEmptyWordColor(R.color.color_9b9b9b));
                        gameAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    private final OnGmSpaceReceivedEventListener receivedEventListener = new OnGmSpaceReceivedEventListener() {
        *//**
         * 接收到事件
         * @param type 事件类型
         * @param extras 事件额外信息
         *//*
        @Override
        public void onReceivedEvent(int type, @NonNull Bundle extras) {
            if (GmSpaceEvent.TYPE_PACKAGE_INSTALLED == type){
                // 有应用安装 返回安装的应用信息 （KEY_PACKAGE_COMPATIBLE_STATUS 可以获取安装是否成功）
                AppItemEnhance appItemEnhance = extras.getParcelable(GmSpaceEvent.KEY_PACKAGE_COMPATIBLE_INFO);
                _mActivity.runOnUiThread(() -> Toast.makeText(_mActivity, "安装成功", Toast.LENGTH_SHORT).show());
                initData();
            }else if (GmSpaceEvent.TYPE_PACKAGE_INSTALL_FAILURE == type){
                String string = extras.getString(GmSpaceEvent.KEY_MESSAGE);
                _mActivity.runOnUiThread(() -> Toast.makeText(_mActivity, string, Toast.LENGTH_SHORT).show());
            }else if (GmSpaceEvent.TYPE_PACKAGE_UNINSTALLED == type){
                _mActivity.runOnUiThread(() -> Toast.makeText(_mActivity, "卸载完成", Toast.LENGTH_SHORT).show());
                initData();
            }
        }
    };

    *//**
     * 异步启动app
     *//*
    @SuppressLint("StaticFieldLeak")
    public void asyncLaunchApp(BipartitionGameVo item) {
        new AsyncTask<Void, Drawable, Void>() {
            @Override
            protected void onProgressUpdate(Drawable... values) {
                // 有windowBackground
                final Drawable windowBackground = (Drawable) values[0];
                if (windowBackground == null || windowBackground instanceof ColorDrawable) {
                    *//*findViewById(R.id.cl_bottom).setVisibility(View.VISIBLE);
                    ((ImageView)findViewById(R.id.iv_app_icon)).setImageDrawable(item.getIcon());
                    ((TextView)findViewById(R.id.tv_app_name)).setText(item.getAppName());
                    ((TextView)findViewById(R.id.tv_app_package_name)).setText(item.getPackageName());
                    ((TextView)findViewById(R.id.tv_app_version)).setText(String.format("版本：%s（%s）", item.getVersionName(), item.getVersionCode()));*//*
                } else {
                    //findViewById(R.id.cl_bottom).setVisibility(View.GONE);
                }
            }

            @Override
            protected void onPreExecute() {}

            @Override
            protected Void doInBackground(Void... voids) {
                final Drawable drawable = GmSpaceObject.getGmSpaceLaunchActivityWindowBackground(item.getPackage_name());
                publishProgress(drawable);

                // 启动app
                GmSpaceObject.startApp(item.getPackage_name());
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                //finish();
                //findViewById(R.id.cl_bottom).setVisibility(View.GONE);
                item.setAdd_time(System.currentTimeMillis());
                boolean b = BipartitionGameDbInstance.getInstance().changeBipartitionGame(item);
                if (b) initData();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @SuppressLint("StaticFieldLeak")
    private void asyncUninstallAppAndRemoveItem(String packageName, int gameid) {
        new AsyncTask<Void, Drawable, Void>() {
            @Override
            protected void onProgressUpdate(Drawable... values) {}

            @Override
            protected void onPreExecute() {}

            @Override
            protected Void doInBackground(Void... voids) {
                GmSpaceObject.uninstallGmSpacePackage(packageName);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                boolean b = BipartitionGameDbInstance.getInstance().deleteBipartitionGameItem(packageName);
                if (b){
                    ToastT.success("双开空间卸载成功！");
                    initData();
                    multipleLaunchers(gameid, "uninstall");
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }



    private CustomDialog selectGameDialog;
    private XRecyclerView mGameRecyclerView;
    private BaseRecyclerAdapter gameAdapter;
    private int page = 1;
    public void showSelectGameDialog(){
        if (selectGameDialog == null) {
            selectGameDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_cloud_select_game, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

            EditText mEtSearch = selectGameDialog.findViewById(R.id.et_search);
            ImageView mIvDelete = selectGameDialog.findViewById(R.id.iv_delete);
            TextView mTvSearch = selectGameDialog.findViewById(R.id.tv_search);
            mGameRecyclerView = selectGameDialog.findViewById(R.id.recyclerView);
            mGameRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));

            gameAdapter = new BaseRecyclerAdapter.Builder<>()
                    .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                    .bind(GameInfoVo.class, new BipartitionGameItemHolder(_mActivity))
                    .build().setTag(R.id.tag_fragment, _mActivity)
                    .setTag(R.id.tag_fragment, this);

            mGameRecyclerView.setAdapter(gameAdapter);

            mGameRecyclerView.setLoadingMoreEnabled(true);
            mGameRecyclerView.setPullRefreshEnabled(false);

            mGameRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    page = 1;
                    getGameList("");
                }

                @Override
                public void onLoadMore() {
                    if (page < 0) {
                        return;
                    }
                    page++;
                    getGameList("");
                }
            });

            gameAdapter.setOnItemClickListener((v, position, data) -> {
                if (ButtonClickUtils.isFastClick()) {
                    return;
                }
                if (selectGameDialog != null && selectGameDialog.isShowing()) selectGameDialog.dismiss();
                if (data instanceof GameInfoVo) goGameDetail(((GameInfoVo) data).getGameid(), ((GameInfoVo) data).getGame_type());
            });

            mEtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s.toString().trim())) {
                        mIvDelete.setVisibility(View.GONE);
                    }else {
                        mIvDelete.setVisibility(View.VISIBLE);
                    }
                }
            });

            mIvDelete.setOnClickListener(v -> {
                mEtSearch.setText("");
                page = 1;
                getGameList("");
            });

            selectGameDialog.findViewById(R.id.iv_close).setOnClickListener(v -> {
                if (selectGameDialog != null && selectGameDialog.isShowing()) selectGameDialog.dismiss();
            });

            mTvSearch.setOnClickListener(v -> {
                String str = mEtSearch.getText().toString().toString();
                if (TextUtils.isEmpty(str)){
                    ToastT.error("请输入游戏名");
                    return;
                }
                page = 1;
                getGameList(str);
            });
            page = 1;
            getGameList("");
        }
        if (!selectGameDialog.isShowing()) selectGameDialog.show();
    }

    public void dismissSelectGameDialog(){
        if (selectGameDialog != null && selectGameDialog.isShowing()) selectGameDialog.dismiss();
    }

    public void showActionDialog(BipartitionGameVo item){
        CustomDialog actionDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_bipartition_action, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        TextView mTvDelete = actionDialog.findViewById(R.id.tv_delete);
        TextView mTvClear = actionDialog.findViewById(R.id.tv_clear);
        TextView mTvCancel = actionDialog.findViewById(R.id.tv_cancel);

        mTvDelete.setOnClickListener(view -> {
            if (actionDialog != null && actionDialog.isShowing()) actionDialog.dismiss();
            asyncUninstallAppAndRemoveItem(item.getPackage_name(), item.getGameid());
        });

        mTvClear.setOnClickListener(view -> {
            if (actionDialog != null && actionDialog.isShowing()) actionDialog.dismiss();
            GmSpaceObject.clearGmSpaceApplicationUserData(item.getPackage_name());
            ToastT.success("清除缓存成功");
        });

        mTvCancel.setOnClickListener(view -> {
            if (actionDialog != null && actionDialog.isShowing()) actionDialog.dismiss();
        });

        if (!actionDialog.isShowing()) actionDialog.show();
    }

    @Override
    public void onEvent(EventCenter eventCenter) {
        super.onEvent(eventCenter);
        if (eventCenter.getEventCode() == EventConfig.BIPARTITION_APP_INSTALL_EVENT_CODE) {
            initData();
            try {
                int gameid = (int) eventCenter.getData();
                multipleLaunchers(gameid, "install");
            }catch (Exception e){}
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //initData();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        GmSpaceObject.unregisterGmSpaceReceivedEventListener(receivedEventListener);
    }

    private void multipleLaunchers(int gameid, String event) {
        if (mViewModel != null) {
            if (UserInfoModel.getInstance().isLogined()){
                UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
                if (userInfo == null){
                    return;
                }
                Map<String, String> params = new HashMap<>();
                params.put("event", event);
                params.put("uid", String.valueOf(userInfo.getUid()));
                params.put("token", String.valueOf(userInfo.getToken()));
                if (gameid != 0) params.put("gameid", String.valueOf(gameid));
                mViewModel.multipleLaunchers(params, new OnBaseCallback() {
                    @Override
                    public void onSuccess(BaseVo data) {}
                });
            }
        }
    }*/
}
