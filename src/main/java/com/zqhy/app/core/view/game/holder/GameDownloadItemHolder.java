package com.zqhy.app.core.view.game.holder;

import android.content.ContentValues;
import android.content.Context;
import androidx.annotation.NonNull;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.box.other.hjq.toast.Toaster;
import com.lzf.easyfloat.EasyFloat;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.utils.IOUtils;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.lzy.okserver.download.DownloadTask;
import com.zqhy.app.Setting;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.game.GameDownloadTimeExtraVo;
import com.zqhy.app.core.data.model.game.GameDownloadVo;
import com.zqhy.app.core.data.model.game.GameExtraVo;
import com.zqhy.app.core.tool.AppUtil;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.core.tool.utilcode.SizeUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.game.GameDownloadManagerFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.notify.DownloadNotifyManager;
import com.zqhy.app.subpackage.Util;
import com.zqhy.app.utils.DownloadUtils;
import com.zqhy.app.utils.sp.SPUtils;

import java.io.File;

/**
 * @author Administrator
 * @date 2018/11/23
 */

public class GameDownloadItemHolder extends AbsItemHolder<GameDownloadVo, GameDownloadItemHolder.ViewHolder> {

    public GameDownloadItemHolder(Context context) {
        super(context);
    }

    boolean isManager;

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameDownloadVo item) {
        try {
            isManager = item.isManager();
            String tag = item.getDownloadTag();
            Progress progress = DownloadManager.getInstance().get(item.getDownloadTag());
            DownloadTask task = OkDownload.restore(progress);

            GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;

            holder.mTvDownload.setOnClickListener(view -> {
                if (isManager) {
                    showDeleteDialog(progress);
                } else {
                    downloadGame(gameInfoVo);
                }
            });

            refresh(holder, progress);

            if (task != null) {
                task.register(new DownloadListener(tag) {
                    @Override
                    public void onStart(Progress progress) {
                        if (_mFragment != null && _mFragment instanceof GameDownloadManagerFragment) {
                            ((GameDownloadManagerFragment) _mFragment).setGameDownloadedPoint(1, progress);
                        }
                    }

                    @Override
                    public void onProgress(Progress progress) {
                        refresh(holder, progress);
                        DownloadNotifyManager.getInstance().doNotify(progress);
                    }

                    @Override
                    public void onError(Progress progress) {
                        progress.exception.printStackTrace();
                        GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;
                        DownloadNotifyManager.getInstance().cancelNotify(gameInfoVo.getGameid());
                        Toast.makeText(mContext, R.string.string_download_game_fail, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish(File file, Progress progress) {
                        if (_mFragment != null && _mFragment instanceof GameDownloadManagerFragment) {
                            ((GameDownloadManagerFragment) _mFragment).setGameDownloadedPoint(10, progress);
                        }
                        File targetFile = new File(progress.filePath);
                        if (targetFile.exists()) {
                            try {
                                GameDownloadTimeExtraVo gameDownloadTimeExtraVo = (GameDownloadTimeExtraVo) progress.extra2;
                                ((GameDownloadManagerFragment) _mFragment).gameDownloadLog(gameDownloadTimeExtraVo.getId(), gameDownloadTimeExtraVo.getType(), gameDownloadTimeExtraVo.getDownload_time(), gameInfoVo.getGameid());

                                if (gameDownloadTimeExtraVo.getType() == 2){
                                    Setting.INPUT_CHANNEL_GAME_ID = gameInfoVo.getGameid();
                                    Util.writeChannel(targetFile, gameInfoVo.getChannel(), false, false);
                                    String s = Util.readChannel(targetFile);
                                    Log.e("Channel", s);
                                }
                            }catch (Exception e){}finally {
                                if (!EasyFloat.isShow("float_download")){
                                    //AppUtil.install(mContext, targetFile);
                                    showFristDownloadDialog(progress,targetFile);
                                }
                            }
                        }
                    }

                    @Override
                    public void onRemove(Progress progress) {
                        GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;
                        DownloadNotifyManager.getInstance().cancelNotify(gameInfoVo.getGameid());
                    }
                });
                holder.restoreDownloadTask(tag, task);
            }
            if (gameInfoVo != null) {
                GlideUtils.loadRoundImage(mContext, gameInfoVo.getGameicon(), holder.mIcon);
                holder.mName.setText(gameInfoVo.getGamename());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.setTaskUnRegister();
    }

    private void refresh(ViewHolder holder, Progress progress) {
        if (progress == null) {
            return;
        }
        String downloadLength = Formatter.formatFileSize(mContext, progress.currentSize);
        String totalLength = Formatter.formatFileSize(mContext, progress.totalSize);
        holder.mDownloadSize.setText(downloadLength + "/" + totalLength);

        if (progress.status == Progress.LOADING || progress.status == Progress.WAITING) {
            String networkSpeed = Formatter.formatFileSize(mContext, progress.speed);
            holder.mNetSpeed.setText(networkSpeed + "/s");
            holder.mTvDownload.setText("暂停");
        } else if (progress.status == Progress.NONE) {
            holder.mNetSpeed.setText("停止");
            holder.mTvDownload.setText("继续");
            holder.mTvDownload.setBackgroundResource(R.drawable.ic_main_circles);
            holder.mTvDownload.setTextColor(mContext.getResources().getColor(R.color.color_ff8f19));
        } else if (progress.status == Progress.PAUSE) {
            holder.mNetSpeed.setText("暂停中");
            holder.mTvDownload.setText("继续");
            holder.mTvDownload.setBackgroundResource(R.drawable.ic_main_circles);
            holder.mTvDownload.setTextColor(mContext.getResources().getColor(R.color.color_ff8f19));
        } else if (progress.status == Progress.ERROR) {
            holder.mNetSpeed.setText("下载中断");
            holder.mTvDownload.setText("中断");
            holder.mTvDownload.setBackgroundResource(R.drawable.ic_main_circles);
            holder.mTvDownload.setTextColor(mContext.getResources().getColor(R.color.color_ff8f19));
        } else if (progress.status == Progress.FINISH) {
            GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;
            String packageName = gameInfoVo.getClient_package_name();
            if (!TextUtils.isEmpty(packageName) && AppUtil.isAppAvailable(mContext, packageName)) {
                //打开
                holder.mTvDownload.setText("打开");
                holder.mNetSpeed.setText("下载完成");
                holder.mTvDownload.setBackgroundResource(R.drawable.drawable_dot_ff8f19);
                holder.mTvDownload.setTextColor(mContext.getResources().getColor(R.color.white));
            } else {
                File targetFile = new File(progress.filePath);
                if (targetFile.exists()) {
                    //安装
                    holder.mTvDownload.setText("安装");
                    holder.mNetSpeed.setText("下载完成");
                } else {
                    holder.mTvDownload.setText("下载");
                    holder.mNetSpeed.setText("文件已删除");
                }
                holder.mTvDownload.setBackgroundResource(R.drawable.drawable_dot_ff8f19);
                holder.mTvDownload.setTextColor(mContext.getResources().getColor(R.color.white));
            }
        }
        holder.mTvProgress.setText((Math.round(progress.fraction * 10000) * 1.0f / 100) + "%");
        holder.mPbProgress.setMax((int) progress.totalSize);
        holder.mPbProgress.setProgress((int) progress.currentSize);

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

        if (isManager) {
            holder.mTvDownload.setText("删除");
            holder.mTvDownload.setBackgroundResource(R.drawable.ic_red_circles);
            holder.mTvDownload.setTextColor(mContext.getResources().getColor(R.color.color_red));
        }
    }

    private void downloadGame(GameExtraVo gameInfoVo) {
        if (gameInfoVo == null) {
            return;
        }
        Progress progress = DownloadManager.getInstance().get(gameInfoVo.getGame_download_tag());
        if (progress != null) {
            DownloadTask task = OkDownload.restore(progress);
            if (progress.status == Progress.NONE || progress.status == Progress.PAUSE || progress.status == Progress.ERROR || progress.status == Progress.WAITING) {
                if (_mFragment != null) {
                    _mFragment.checkWiFiType(() -> {
                        //下载
                        if (task != null) {
                            task.start();
                        }
                    });
                }
            } else if (progress.status == Progress.LOADING) {
                //暂停
                if (task != null) {
                    task.pause();
                }
            } else if (progress.status == Progress.FINISH) {
                //下载完成
                String packageName = gameInfoVo.getClient_package_name();
                //打开
                if (!TextUtils.isEmpty(packageName) && AppUtil.isAppAvailable(mContext, packageName)) {
                    AppUtil.open(mContext, packageName);
                } else {
                    File targetFile = new File(progress.filePath);
                    //安装
                    if (targetFile.exists()) {
                        if (!EasyFloat.isShow("float_download")){
                            //AppUtil.install(mContext, targetFile);
                            showFristDownloadDialog(progress,targetFile);
                        }
                    } else {
                        if (_mFragment != null) {
                            _mFragment.checkWiFiType(() -> {
                                //重新下载
                                task.restart();
                            });
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_download;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    private CustomDialog deleteDialog;

    public void showDeleteDialog(Progress progress) {
        if (deleteDialog == null) {
            deleteDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_game_delete, null),
                    ScreenUtils.getScreenWidth(mContext) - SizeUtils.dp2px(mContext, 24),
                    WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            deleteDialog.setCanceledOnTouchOutside(false);
            deleteDialog.findViewById(R.id.btn_cancel).setOnClickListener(v -> {
                if (deleteDialog != null && deleteDialog.isShowing()) {
                    deleteDialog.dismiss();
                }
            });
        }
        deleteDialog.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            if (deleteDialog != null && deleteDialog.isShowing()) {
                deleteDialog.dismiss();
            }
            if (_mFragment != null) {
                ((GameDownloadManagerFragment) _mFragment).deleteItem(progress);
            }
        });
        deleteDialog.show();
    }

    private void showFristDownloadDialog(Progress progress,File targetFile){
        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME + "_FIRST_DOWNLOAD");
        if (Setting.SHOW_TIPS == 1){
            boolean firstDownloadTips = spUtils.getBoolean("FIRST_DOWNLOAD_TIPS",false);
            if (firstDownloadTips){
                downLoadCheck(progress,targetFile);
                //AppUtil.install(mContext, targetFile);
            }else {
                doFirstDownload1(targetFile);
            }
        }else {
            boolean firstDownload = spUtils.getBoolean("FIRST_DOWNLOAD",false);
            if (firstDownload){
                downLoadCheck(progress,targetFile);
                //AppUtil.install(mContext, targetFile);
            }else {
                doFirstDownload(targetFile);
            }
        }
    }

    private void downLoadCheck(Progress progress, File targetFile){
        GameExtraVo extra1 = (GameExtraVo) progress.extra1;
        GameDownloadTimeExtraVo gameDownloadTimeExtraVo = (GameDownloadTimeExtraVo) progress.extra2;

        if (gameDownloadTimeExtraVo.getType() == 2){//判断是否由本地写入渠道信息
            if (DownloadUtils.checkCompletenessForGameExtraVo(true, targetFile, extra1)){
                AppUtil.install(mContext, targetFile);
            } else {
                Toaster.show("游戏安装文件损坏，请重新下载");
                if (_mFragment != null) ((GameDownloadManagerFragment)_mFragment).deleteItem(progress);
            }
        }else {
            if (DownloadUtils.checkCompletenessForGameExtraVo(false, targetFile, extra1)){
                AppUtil.install(mContext, targetFile);
            } else {
                Toaster.show("游戏安装文件损坏，请重新下载");
                if (_mFragment != null) ((GameDownloadManagerFragment)_mFragment).deleteItem(progress);
            }
        }
    }



    private void doFirstDownload(File targetFile) {
        CustomDialog tipsDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_download_default, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        TextView mTvContent = tipsDialog.findViewById(R.id.tv_content);
        SpannableString spannableString = new SpannableString("个别机型受系统影响，可能会出现：\n1.安装时若显示未经检验等提示，请勾选了解点击“继续安装”即可\n2.游戏可能会被手机商店提示更新成普通版，请勿更新以免无法享受游戏福利");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#4E76FF")), 41, 45, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 41, 45, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF450A")), 70, 74, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 70, 74, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvContent.setText(spannableString);
        tipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME + "_FIRST_DOWNLOAD");
            spUtils.putBoolean("FIRST_DOWNLOAD", true);
            AppUtil.install(mContext, targetFile);
        });
        tipsDialog.show();
    }

    private boolean isCheck = false;
    private void doFirstDownload1(File targetFile) {
        CustomDialog tipsDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_download_default_1, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        isCheck = false;
        TextView mTvCheck = tipsDialog.findViewById(R.id.tv_check);
        mTvCheck.setOnClickListener(view -> {
            isCheck = !isCheck;
            if (isCheck){
                mTvCheck.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_login_checked), null, null, null);
            }else {
                mTvCheck.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_login_un_check), null, null, null);
            }
        });
        tipsDialog.findViewById(R.id.tv_next).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            if (isCheck){
                SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME + "_FIRST_DOWNLOAD");
                spUtils.putBoolean("FIRST_DOWNLOAD_TIPS", isCheck);
            }
            AppUtil.install(mContext, targetFile);
        });
        tipsDialog.show();
    }

    public class ViewHolder extends AbsHolder {

        private LinearLayout mLlItemDownload;
        private ImageView    mIcon;
        private TextView     mName;
        private TextView     mTvProgress;
        private ProgressBar  mPbProgress;
        private TextView     mDownloadSize;
        private TextView     mNetSpeed;
        private TextView     mTvDownload;

        public ViewHolder(View view) {
            super(view);
            mLlItemDownload = findViewById(R.id.ll_item_download);
            mIcon = findViewById(R.id.icon);
            mName = findViewById(R.id.name);
            mTvProgress = findViewById(R.id.tvProgress);
            mPbProgress = findViewById(R.id.pbProgress);
            mDownloadSize = findViewById(R.id.downloadSize);
            mNetSpeed = findViewById(R.id.netSpeed);
            mTvDownload = findViewById(R.id.tv_download);

        }

        private String       tag;
        private DownloadTask task;

        public void restoreDownloadTask(String tag, DownloadTask task) {
            this.tag = tag;
            this.task = task;
        }

        public void setTaskUnRegister() {
            if (tag != null && task != null) {
                task.unRegister(tag);
            }
        }
    }
}
