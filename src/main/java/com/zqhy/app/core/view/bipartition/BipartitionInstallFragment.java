package com.zqhy.app.core.view.bipartition;

import android.os.Bundle;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class BipartitionInstallFragment extends BaseFragment<GameViewModel> {

    public static BipartitionInstallFragment newInstance(int gameid, boolean needChangeDownload) {
        BipartitionInstallFragment fragment = new BipartitionInstallFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("gameid", gameid);
        bundle.putBoolean("needChangeDownload", needChangeDownload);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_bipartition_install;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    protected String getUmengPageName() {
        return "双开应用列表";
    }

    /*private int gameid;
    private boolean needChangeDownload;
    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gameid = getArguments().getInt("gameid");
            needChangeDownload = getArguments().getBoolean("needChangeDownload", true);
        }
        super.initView(state);
        bindView();
        getGameInfoPartBase();
        // 注册事件
        GmSpaceObject.registerGmSpaceReceivedEventListener(receivedEventListener);
        applyConfiguration();
    }

    private TextView mTvBackstage;
    private ImageView mIvGameIcon;
    private TextView mTvGameName;
    private ProgressBar mProgressBar;
    private TextView mTvProgress;
    private TextView mTvAction;
    private void bindView() {
        mTvBackstage = findViewById(R.id.tv_backstage);
        mIvGameIcon = findViewById(R.id.iv_game_icon);
        mTvGameName = findViewById(R.id.tv_game_name);
        mProgressBar = findViewById(R.id.download_progress);
        mTvProgress = findViewById(R.id.tv_progress);
        mTvAction = findViewById(R.id.tv_action);

        mTvBackstage.setOnClickListener(view -> {
            if (PermissionUtils.checkPermission(_mActivity)){
                Progress progress = DownloadManager.getInstance().get(gameInfoVo.getGameDownloadTag());
                if(progress != null){
                    DownloadTask task = OkDownload.restore(progress);
                    if (progress.status == Progress.LOADING){
                        FloatDownloadUtils.showDownloadFloat(_mActivity, gameInfoVo);
                    }
                }
                pop();
            }else {
                showSystemWIindowPermissionsTipDialog();
            }
        });

        mTvAction.setOnClickListener(view -> {
            download(true);
        });
    }

    *//**
     * 获取游戏信息
     *//*
    private GameInfoVo gameInfoVo;
    private void getGameInfoPartBase() {
        if (mViewModel != null) {
            mViewModel.getGameInfoPartBase(gameid, new OnBaseCallback<GameDataVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }

                @Override
                public void onSuccess(GameDataVo gameDataVo) {
                    if (gameDataVo != null) {
                        if (gameDataVo.isStateOK()) {
                            GameInfoVo infoVo = gameDataVo.getData();
                            if (infoVo != null) {
                                gameInfoVo = infoVo;
                                GlideUtils.loadGameIcon(_mActivity, gameInfoVo.getGameicon(), mIvGameIcon);
                                mTvGameName.setText(gameInfoVo.getGamename());
                                download(needChangeDownload);
                            }
                        } else {
                            ToastT.error(_mActivity, gameDataVo.getMsg());
                        }
                    }
                }
            });

        }
    }

    *//**
     * 下载游戏
     *//*
    private void download(boolean needChange) {
        if (gameInfoVo == null) return;
        if (gameInfoVo.getIs_deny() == 1) {
            Toaster.show( "(T ^ T) 亲亲，此游戏暂不提供下载服务呢！");
            return;
        }

        if (gameInfoVo.isIOSGameOnly()) {
            Toaster.show( "此为苹果游戏，请使用苹果手机下载哦！");
            return;
        }

        String downloadErrorInfo = gameInfoVo.getGame_download_error();
        if (!TextUtils.isEmpty(downloadErrorInfo)) {
            Toaster.show( downloadErrorInfo);
            return;
        }

        if (gameInfoVo != null && !TextUtils.isEmpty(gameInfoVo.getClient_package_name()) && BipartitionGameDbInstance.getInstance().checkBipartitionGame(gameInfoVo.getClient_package_name())){
            mTvProgress.setText("启动中0%");
            new Handler().postDelayed(() -> {
                mTvProgress.setText("启动中100%");
                mProgressBar.setProgress(100);
                asyncLaunchApp();
            }, 1000);
            return;
        }
        Progress progress = DownloadManager.getInstance().get(gameInfoVo.getGameDownloadTag());
        if (progress == null){
            checkWiFiType(() -> {
                if (!TextUtils.isEmpty(gameInfoVo.getChannel())) {
                    getDownloadUrl();
                } else {
                    gameDownloadLog(0, 1, 0);
                }
            });
        }else {
            DownloadTask task = OkDownload.restore(progress);
            task.register(downloadListener);
            if (needChange) {
                if (progress.status == Progress.NONE || progress.status == Progress.ERROR || progress.status == Progress.PAUSE || progress.status == Progress.WAITING) {
                    checkWiFiType(() -> {
                        if (task != null) {
                            task.start();
                            mTvAction.setText("暂停加载");
                            mTvAction.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_bipartition_stop), null, null, null);
                        }
                    });
                } else if (progress.status == Progress.LOADING) {
                    //暂停
                    if (task != null) {
                        task.pause();
                        mTvAction.setText("继续下载");
                        mTvAction.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_bipartition_start), null, null, null);
                    }
                } else if (progress.status == Progress.FINISH) {
                    //下载完成
                    GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;
                    String packageName = gameInfoVo == null ? "" : gameInfoVo.getClient_package_name();
                    //打开
                    if (!TextUtils.isEmpty(packageName) && checkHasPackage()) {
                        asyncLaunchApp();
                    } else {
                        File targetFile = new File(progress.filePath);
                        //安装
                        if (targetFile.exists()) {
                            asyncInstallApkFile(targetFile);
                        } else {
                            checkWiFiType(() -> {
                                //重新下载
                                checkWiFiType(() -> {
                                    if (task != null) {
                                        task.start();
                                    }
                                });
                            });
                        }
                    }
                }
            }
            refresh(progress);
        }
    }

    private void getDownloadUrl(){
        OkGo.<String>post("https://appapi-ns1.tsyule.cn/index.php/download/game")
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.NO_CACHE)
                .params("params", gameInfoVo.getChannel())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try{
                            Gson gson = new Gson();
                            Type type = new TypeToken<GameDownloadUrlVo>() {}.getType();

                            GameDownloadUrlVo gameDownloadUrlVo = gson.fromJson(response.body(), type);
                            if (gameDownloadUrlVo != null){
                                if (gameDownloadUrlVo.isStateOK()){
                                    if (gameDownloadUrlVo.getData() != null){
                                        gameInfoVo.setGame_download_url(gameDownloadUrlVo.getData().getApk_url());
                                        gameInfoVo.setChannel(gameDownloadUrlVo.getData().getChannel());
                                        gameDownloadLog(0, 2, 0);
                                    }
                                }else {
                                    ToastT.error(gameDownloadUrlVo.getMsg());
                                }
                            }
                        }catch (Exception e){ e.printStackTrace(); }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    public void gameDownloadLog(int id, int type, long duration) {
        if (mViewModel != null) {
            Map<String, String> params = new HashMap<>();
            if (id != 0){
                params.put("id", String.valueOf(id));
            }
            params.put("type", String.valueOf(type));
            if (duration != 0){
                params.put("duration", String.valueOf(duration));
            }
            params.put("gameid", String.valueOf(gameid));
            mViewModel.gameDownloadLog(params, new OnBaseCallback<GameDownloadLogVo>(){
                @Override
                public void onSuccess(GameDownloadLogVo data) {
                    if (data != null && data.isStateOK()) {
                        if(id == 0){
                            fileDownload(data.getData(), type);
                        }
                    }
                }
            });
        }
    }

    private void refresh(Progress progress) {
        if (progress == null) {
            return;
        }
        GameInfoVo.DownloadControl download_control = gameInfoVo.getDownload_control();
        if (download_control != null && download_control.getDownload_control() == 1){
            return;
        }
        float fProgress = progress.fraction;
        mProgressBar.setMax(100);
        mProgressBar.setProgress((int)(fProgress * 100));
        DecimalFormat df = new DecimalFormat("#0.00");
        if (progress.status == Progress.LOADING || progress.status == Progress.WAITING) {
            if (fProgress * 100 == 0.0){
                mTvProgress.setText("下载中0%");
            }else {
                mTvProgress.setText("下载中" + df.format(fProgress * 100) + "%");
            }
            mTvAction.setText("暂停加载");
            mTvAction.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_bipartition_stop), null, null, null);
        } else if (progress.status == Progress.NONE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setMax(100);
            mTvProgress.setText("暂停中，请重试点击继续下载");
            mTvAction.setText("继续下载");
            mTvAction.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_bipartition_start), null, null, null);
        } else if (progress.status == Progress.PAUSE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setMax(100);
            mTvProgress.setText("暂停中，请重试点击继续下载");
            mTvAction.setText("继续下载");
            mTvAction.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_bipartition_start), null, null, null);
        } else if (progress.status == Progress.ERROR) {
            mProgressBar.setVisibility(View.VISIBLE);
            mTvProgress.setText("下载失败，请重试点击继续下载");
            mTvAction.setText("继续下载");
            mTvAction.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_bipartition_start), null, null, null);
        } else if (progress.status == Progress.FINISH) {
            mTvProgress.setText("安装中");
        }

        GameDownloadTimeExtraVo gameDownloadTimeExtraVo = (GameDownloadTimeExtraVo) progress.extra2;
        if (gameDownloadTimeExtraVo != null && (progress.status == Progress.LOADING || progress.status == Progress.WAITING)){
            long downloadTime = gameDownloadTimeExtraVo.getDownload_time();
            //间隔小于一秒则算作有效时间 否则认为是暂停时间不计入总时长
            if (System.currentTimeMillis() - gameDownloadTimeExtraVo.getLast_refresh_time() < 1000) gameDownloadTimeExtraVo.setDownload_time(downloadTime + (System.currentTimeMillis() - gameDownloadTimeExtraVo.getLast_refresh_time()));
            gameDownloadTimeExtraVo.setLast_refresh_time(System.currentTimeMillis());

            //实现实时修改下载时长记录
            ContentValues values = new ContentValues();
            values.put("extra2", IOUtils.toByteArray(progress.extra2));
            DownloadManager.getInstance().update(values, progress.tag);

            Log.e("progress", ((GameDownloadTimeExtraVo) progress.extra2).getDownload_time() + "");
        }
    }

    private void fileDownload(int id, int type) {
        String downloadUrl = gameInfoVo.getGame_download_url();
        if (!CommonUtils.downloadUrlVerification(downloadUrl)) {
            Toaster.show( "_(:з」∠)_ 下载异常，请重登或联系客服看看哟~");
            return;
        }
        GetRequest<File> request = OkGo.get(gameInfoVo.getGame_download_url());
        request.headers("content-type", "application/vnd.android.package-archive");
        GameDownloadTimeExtraVo gameDownloadTimeExtraVo = new GameDownloadTimeExtraVo();
        gameDownloadTimeExtraVo.setDownload_time(0);
        gameDownloadTimeExtraVo.setLast_refresh_time(System.currentTimeMillis());
        gameDownloadTimeExtraVo.setId(id);
        gameDownloadTimeExtraVo.setType(type);
        //        request.setMimeType("application/vnd.android.package-archive");
        DownloadTask task = OkDownload.request(gameInfoVo.getGameDownloadTag(), request)
                .folder(SdCardManager.getInstance().getDownloadApkDir().getPath())
                .fileName(gameInfoVo.getGamename())
                .extra1(gameInfoVo.getGameExtraVo())
                .extra2(gameDownloadTimeExtraVo)
                .register(downloadListener)
                .save();
        task.start();
        EventBus.getDefault().post(new EventCenter(EventConfig.ACTION_ADD_DOWNLOAD_EVENT_CODE));
        mTvAction.setText("暂停加载");
        mTvAction.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_bipartition_stop), null, null, null);
    }

    DownloadListener downloadListener = new DownloadListener("download_bipartition") {
        @Override
        public void onStart(Progress progress) {}

        @Override
        public void onProgress(Progress progress) {
            refresh(progress);
            DownloadNotifyManager.getInstance().doNotify(progress);
        }

        @Override
        public void onError(Progress progress) {
            progress.exception.printStackTrace();
            GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;
            DownloadNotifyManager.getInstance().cancelNotify(gameInfoVo.getGameid());
            Toast.makeText(_mActivity, R.string.string_download_game_fail, Toast.LENGTH_SHORT).show();
            mTvAction.setText("继续下载");
            mTvAction.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_bipartition_start), null, null, null);
        }

        @Override
        public void onFinish(File file, Progress progress) {
            File targetFile = new File(progress.filePath);
            if (targetFile.exists()) {
                try {
                    GameDownloadTimeExtraVo gameDownloadTimeExtraVo = (GameDownloadTimeExtraVo) progress.extra2;
                    gameDownloadLog(gameDownloadTimeExtraVo.getId(), gameDownloadTimeExtraVo.getType(), gameDownloadTimeExtraVo.getDownload_time());

                    if (gameDownloadTimeExtraVo.getType() == 2){
                        Setting.INPUT_CHANNEL_GAME_ID = gameInfoVo.getGameid();
                        Util.writeChannel(targetFile, gameInfoVo.getChannel(), false, false);
                        String s = Util.readChannel(targetFile);
                        Log.e("Channel", s);
                    }
                }catch (Exception e){}finally {
                    asyncInstallApkFile(targetFile);
                }
            }
        }

        @Override
        public void onRemove(Progress progress) {
            GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;
            DownloadNotifyManager.getInstance().cancelNotify(gameInfoVo.getGameid());
        }
    };

    private final OnGmSpaceReceivedEventListener receivedEventListener = new OnGmSpaceReceivedEventListener() {
        *//**
         * 接收到事件
         * @param type 事件类型
         * @param extras 事件额外信息
         *//*
        @Override
        public void onReceivedEvent(int type, @NonNull Bundle extras) {
            if (GmSpaceEvent.TYPE_PACKAGE_INSTALLED == type){
                _mActivity.runOnUiThread(() -> Toast.makeText(_mActivity, "安装成功", Toast.LENGTH_SHORT).show());
                mTvProgress.setText("启动中");
            }else if (GmSpaceEvent.TYPE_PACKAGE_INSTALL_FAILURE == type){
                String string = extras.getString(GmSpaceEvent.KEY_MESSAGE);
                _mActivity.runOnUiThread(() -> Toast.makeText(_mActivity, string, Toast.LENGTH_SHORT).show());
            }
        }
    };

    private boolean checkHasPackage() {
        List<String> installedPackages = GmSpaceObject.getGmSpaceRunningPackageNames();
        for (String pkg : installedPackages) {
            try {
                if (pkg.equals(gameInfoVo.getClient_package_name())) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @SuppressLint("StaticFieldLeak")
    private void applyConfiguration() {
        new AsyncTask<Void, Object, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Log.d("iichen",">>>>>>>准备接入setGmSpacePackageConfiguration");
                final File localTmp = new File(_mActivity.getExternalCacheDir(), "/data/local/tmp");
                if (!localTmp.exists()) localTmp.mkdirs();
                GmSpaceConfigContextBuilder builder = new GmSpaceConfigContextBuilder();
                builder.setGmSpaceForcePictureInPicture(true);
                builder.setGmSpaceUseInternalSdcard(true);
                builder.setGmSpaceIsolatedHost(true);
                builder.setGmSpaceKeepPackageSessionCache(true);
                GmSpaceObject.setGmSpaceConfigurationContext(builder);

                GmSpacePackageBuilder gmSpacePackageBuilder = new GmSpacePackageBuilder();
                gmSpacePackageBuilder.setGmSpaceEnableTraceAnr(true);
                gmSpacePackageBuilder.setGmSpaceAllowCreateShortcut(true);
                gmSpacePackageBuilder.setGmSpaceAllowCreateDynamicShortcut(true);
                gmSpacePackageBuilder.setGmSpaceEnableTraceNativeCrash(true);
                GmSpaceObject.setGmSpacePackageConfiguration(gmSpacePackageBuilder);
                Log.d("iichen",">>>>>>>接入setGmSpacePackageConfiguration");
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    *//**
     * 异步安装apk
     *
     * @param src
     *//*
    @SuppressLint("StaticFieldLeak")
    private void asyncInstallApkFile(File src) {
        new DialogAsyncTask<String, String, GmSpaceResultParcel>(_mActivity) {

            @Override
            protected void onPreExecute() {
                //super.showProgressDialog("正在安装");
            }

            @Override
            protected void onProgressUpdate(String... values) {
                //super.updateProgressDialog(values[0]);
            }

            @Override
            protected GmSpaceResultParcel doInBackground(String... uris) {
                String uri = uris[0];
                return SampleUtils.installApk(_mActivity,uri,false);
            }
            @Override
            protected void onPostExecute(GmSpaceResultParcel result) {
                super.onPostExecute(result);
                if (result.isSucceed()) {
                    //保存记录
                    BipartitionGameVo bipartitionGameVo = new BipartitionGameVo();
                    bipartitionGameVo.setGameid(gameInfoVo.getGameid());
                    bipartitionGameVo.setGamename(gameInfoVo.getGamename());
                    bipartitionGameVo.setGameicon(gameInfoVo.getGameicon());
                    bipartitionGameVo.setGame_type(gameInfoVo.getGame_type());
                    bipartitionGameVo.setGenre_str(gameInfoVo.getGenre_str());
                    bipartitionGameVo.setPackage_name(gameInfoVo.getClient_package_name());
                    bipartitionGameVo.setAdd_time(System.currentTimeMillis());
                    BipartitionGameDbInstance.getInstance().changeBipartitionGame(bipartitionGameVo);
                    EventBus.getDefault().post(new EventCenter(EventConfig.BIPARTITION_APP_INSTALL_EVENT_CODE, gameid));
                    //打开双开空间
                    asyncLaunchApp();
                } else {
                    String message = result.getMessage();
                    if (message.contains("32位架构")){
                        message = "当前游戏不支持双开";
                    }
                    String toastString = message;
                    ToastT.error("应用安装失败: " + toastString);
                    pop();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, src.getAbsolutePath());
    }

    *//**
     * 异步启动app
     *//*
    @SuppressLint("StaticFieldLeak")
    private void asyncLaunchApp() {
        new AsyncTask<Void, Drawable, Void>() {
            @Override
            protected void onProgressUpdate(Drawable... values) {}

            @Override
            protected void onPreExecute() {}

            @Override
            protected Void doInBackground(Void... voids) {
                //修改记录
                BipartitionGameVo bipartitionGameVo = new BipartitionGameVo();
                bipartitionGameVo.setGameid(gameInfoVo.getGameid());
                bipartitionGameVo.setGamename(gameInfoVo.getGamename());
                bipartitionGameVo.setGameicon(gameInfoVo.getGameicon());
                bipartitionGameVo.setGame_type(gameInfoVo.getGame_type());
                bipartitionGameVo.setGenre_str(gameInfoVo.getGenre_str());
                bipartitionGameVo.setPackage_name(gameInfoVo.getClient_package_name());
                bipartitionGameVo.setAdd_time(System.currentTimeMillis());
                BipartitionGameDbInstance.getInstance().changeBipartitionGame(bipartitionGameVo);
                // 启动app
                GmSpaceObject.startApp(gameInfoVo.getClient_package_name());
                //mTvProgress.setText("启动中");
                multipleLaunchers(gameInfoVo.getGameid(), "startup");
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                pop();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @SuppressLint("WrongConstant")
    public void showSystemWIindowPermissionsTipDialog(){
        if (_mActivity != null) {
            CustomDialog authorityDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.dialog_system_window_authority_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            authorityDialog.setCancelable(false);
            authorityDialog.setCanceledOnTouchOutside(false);

            TextView mTvCancel = authorityDialog.findViewById(R.id.tv_cancel);
            TextView mTvConfirm = authorityDialog.findViewById(R.id.tv_confirm);

            mTvCancel.setOnClickListener(v -> {
                if (authorityDialog != null && authorityDialog.isShowing()){
                    authorityDialog.dismiss();
                }
                ToastT.error("此功能需要开启悬浮窗权限");
            });
            mTvConfirm.setOnClickListener(v -> {
                if (authorityDialog != null && authorityDialog.isShowing()){
                    authorityDialog.dismiss();
                }
                PermissionUtils.requestPermission(_mActivity, b -> {
                    if (b){
                        FloatDownloadUtils.showDownloadFloat(_mActivity, gameInfoVo);
                        pop();
                    }else {
                        ToastT.error("此功能需要开启悬浮窗权限");
                    }
                });
            });
            authorityDialog.show();
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        if (gameInfoVo != null){
            Progress progress = DownloadManager.getInstance().get(gameInfoVo.getGameDownloadTag());
            if (progress != null){
                DownloadTask task = OkDownload.restore(progress);
                task.unRegister(downloadListener);
                task.pause();
            }
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (receivedEventListener != null) {
            GmSpaceObject.unregisterGmSpaceReceivedEventListener(receivedEventListener);
        }
        if (gameInfoVo != null) {
            Progress progress = DownloadManager.getInstance().get(gameInfoVo.getGameDownloadTag());
            if (progress != null) {
                DownloadTask task = OkDownload.restore(progress);
                task.unRegister(downloadListener);
            }
        }
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
