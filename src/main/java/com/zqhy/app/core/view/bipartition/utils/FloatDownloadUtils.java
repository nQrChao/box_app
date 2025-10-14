package com.zqhy.app.core.view.bipartition.utils;

public class FloatDownloadUtils{
    /*public static void showDownloadFloat(Activity context, GameInfoVo gameInfoVo){
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_download_float, null);
        ProgressBarView progressBarView = inflate.findViewById(R.id.progressBar);
        ImageView ivGame = inflate.findViewById(R.id.iv_image);
        Progress progress = DownloadManager.getInstance().get(gameInfoVo.getGameDownloadTag());
        DownloadListener downloadListener = new DownloadListener("float_download") {
            @Override
            public void onStart(Progress progress) {

            }

            @Override
            public void onProgress(Progress progress) {
                if (progressBarView != null) {
                    float fProgress = progress.fraction;
                    progressBarView.setProgress((int) (fProgress * 100), 0);
                }
            }

            @Override
            public void onError(Progress progress) {

            }

            @Override
            public void onFinish(File file, Progress progress) {
                File targetFile = new File(progress.filePath);
                if (targetFile.exists()) {
                    try {
                        GameDownloadTimeExtraVo gameDownloadTimeExtraVo = (GameDownloadTimeExtraVo) progress.extra2;
                        if (gameDownloadTimeExtraVo.getType() == 2) {
                            Setting.INPUT_CHANNEL_GAME_ID = gameInfoVo.getGameid();
                            Util.writeChannel(targetFile, gameInfoVo.getChannel(), false, false);
                            String s = Util.readChannel(targetFile);
                            Log.e("Channel", s);
                        }
                    } catch (Exception e) {
                    } finally {
                        asyncInstallApkFile(context, gameInfoVo, targetFile);
                    }
                }
            }

            @Override
            public void onRemove(Progress progress) {

            }
        };
        if (progress != null){
            DownloadTask task = OkDownload.restore(progress);
            task.register(downloadListener);
            task.start();
        }

        GlideApp.with(App.instance()).asBitmap().load(gameInfoVo.getGameicon()).into(ivGame);
        ivGame.setOnClickListener(view -> {
            EasyFloat.dismiss("float_download");
            if (progress != null){
                DownloadTask task = OkDownload.restore(progress);
                if (task != null){
                    task.unRegister(downloadListener);
                }
            }
            FragmentHolderActivity.startFragmentInActivity(context, BipartitionInstallFragment.newInstance(gameInfoVo.getGameid(), false));
        });
        if (EasyFloat.isShow("float_download")){
            EasyFloat.dismiss("float_download");
        }
        EasyFloat.with(context)
                .setShowPattern(ShowPattern.FOREGROUND)
                .setTag("float_download")
                .setSidePattern(SidePattern.RESULT_RIGHT)
                .setImmersionStatusBar(true)
                .setGravity(Gravity.END, 0, ScreenUtil.getScreenHeight(context) - ScreenUtil.dp2px(context, 120))
                .setLayout(inflate).show();
    }

    *//**
     * 异步安装apk
     *
     * @param src
     *//*
    @SuppressLint("StaticFieldLeak")
    private static void asyncInstallApkFile(Context context, GameInfoVo gameInfoVo, File src) {
        new DialogAsyncTask<String, String, GmSpaceResultParcel>(context) {

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
                return SampleUtils.installApk(context,uri,false);
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
                    boolean b = BipartitionGameDbInstance.getInstance().changeBipartitionGame(bipartitionGameVo);
                    if (b) ToastT.success("已安装到双开空间");
                    EventBus.getDefault().post(new EventCenter(EventConfig.BIPARTITION_APP_INSTALL_EVENT_CODE, gameInfoVo.getGameid()));
                } else {
                    String message = result.getMessage();
                    if (message.contains("32位架构")){
                        message = "当前游戏不支持双开";
                    }
                    String toastString = message;
                    ToastT.error("应用安装失败: " + toastString);
                }
                EasyFloat.dismiss("float_download");
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, src.getAbsolutePath());
    }

    *//**
     * 异步启动app
     *//*
    @SuppressLint("StaticFieldLeak")
    private static void asyncLaunchApp(GameInfoVo gameInfoVo) {
        new AsyncTask<Void, Drawable, Void>() {
            @Override
            protected void onProgressUpdate(Drawable... values) {}

            @Override
            protected void onPreExecute() {}

            @Override
            protected Void doInBackground(Void... voids) {
                // 启动app
                GmSpaceObject.startApp(gameInfoVo.getClient_package_name());
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                //pop();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }*/
}
