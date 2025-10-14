package com.zqhy.app.core.view.game;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadTask;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.core.data.model.game.GameDownloadLogVo;
import com.zqhy.app.core.data.model.game.GameDownloadVo;
import com.zqhy.app.core.data.model.game.GameExtraVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.utilcode.AppsUtils;
import com.zqhy.app.core.tool.utilcode.FileUtils;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.view.game.holder.GameDownloadItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.notify.DownloadNotifyManager;
import com.zqhy.app.utils.sp.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author Administrator
 * @date 2018/11/23
 */

public class GameDownloadManagerFragment extends BaseListFragment<GameViewModel> {

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(GameDownloadVo.class, new GameDownloadItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }


    private boolean isManager = false;

    @Override
    protected View getTitleRightView() {
        TextView tvManager = new TextView(_mActivity);
        tvManager.setText("管理");
        int padding = (int) (8 * density);
        tvManager.setPadding(padding, 0, padding, 0);
        tvManager.setTextSize(14);
        tvManager.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_1b1b1b));

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (24 * density));
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(0, 0, (int) (6 * density), 0);
        tvManager.setLayoutParams(params);

        tvManager.setOnClickListener(view -> {
            isManager = !isManager;
            tvManager.setText(isManager ? "取消" : "管理");
            //管理下载
            if (gameDownloadVoList != null && !gameDownloadVoList.isEmpty()) {
                for (GameDownloadVo downloadVo : gameDownloadVoList) {
                    downloadVo.setManager(isManager);
                }
                notifyData();
            }
        });
        return tvManager;
    }


    private List<GameDownloadVo> gameDownloadVoList;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("我的下载");
        setLoadingMoreEnabled(false);
        setPullRefreshEnabled(false);
        refreshDownloadList();

        EventBus.getDefault().post(new EventCenter(EventConfig.ACTION_READ_DOWNLOAD_EVENT_CODE));
    }

    @Override
    public void onResume() {
        super.onResume();
        notifyData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            notifyData();
        }
    }

    @Override
    public void onEvent(EventCenter eventCenter) {
        super.onEvent(eventCenter);
        if (eventCenter.getEventCode() == EventConfig.APP_INSTALL_EVENT_CODE) {
            notifyData();
        }
    }

    private void refreshDownloadList() {
        List<Progress> progressList = DownloadManager.getInstance().getAll();
        //倒序排列
        Collections.reverse(progressList);
        clearData();
        if (progressList == null || progressList.isEmpty()) {
            addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_1));
        } else {
            if (gameDownloadVoList == null) {
                gameDownloadVoList = new ArrayList<>();
            }
            gameDownloadVoList.clear();
            for (Progress progress : progressList) {
                SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
                boolean download_switch = spUtils.getBoolean("download_switch", true);
                if (download_switch){
                    GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;
                    if (!AppsUtils.isInstallApp(_mActivity, gameInfoVo.getClient_package_name())){
                        GameDownloadVo downloadVo = new GameDownloadVo();
                        downloadVo.setManager(isManager);
                        downloadVo.setDownloadTag(progress.tag);
                        gameDownloadVoList.add(downloadVo);
                    }else {
                        deleteItem1(progress);
                    }
                }else {
                    GameDownloadVo downloadVo = new GameDownloadVo();
                    downloadVo.setManager(isManager);
                    downloadVo.setDownloadTag(progress.tag);
                    gameDownloadVoList.add(downloadVo);
                }
            }
            clearData();
            addAllData(gameDownloadVoList);
        }
    }

    public void deleteItem(Progress progress) {
        String tag = progress.tag;
        String path = progress.filePath;
        DownloadTask task = OkDownload.getInstance().getTask(tag);
        if(task != null){
            task.unRegister(tag);
            task.remove(true);
        }
        DownloadManager.getInstance().delete(tag);
        OkDownload.getInstance().removeTask(tag);
        refreshDownloadList();
        GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;
        if (gameInfoVo != null) {
            DownloadNotifyManager.getInstance().cancelNotify(gameInfoVo.getGameid());
        }
        if (FileUtils.deleteFile(path)) {
            Toaster.show("本地文件删除成功");
            //Toaster.show( "本地文件删除成功");
        }
    }

    public void deleteItem1(Progress progress) {
        String tag = progress.tag;
        String path = progress.filePath;
        DownloadTask task = OkDownload.getInstance().getTask(tag);
        if(task != null){
            task.unRegister(tag);
            task.remove(true);
        }
        DownloadManager.getInstance().delete(tag);
        OkDownload.getInstance().removeTask(tag);
        GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;
        if (gameInfoVo != null) {
            DownloadNotifyManager.getInstance().cancelNotify(gameInfoVo.getGameid());
        }
        FileUtils.deleteFile(path);
    }

    /**
     *
     * @param state 1-开始下载  10-下载完成
     * @param progress
     */
    public void setGameDownloadedPoint(int state,Progress progress) {
        if (mViewModel != null) {
            if (progress.extra1 != null && progress.extra1 instanceof GameExtraVo) {
                GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;
                Map<String, String> param = new TreeMap<>();
                param.put("gameid", String.valueOf(gameInfoVo.getGameid()));
                switch (state){
                    case 1:
                        mViewModel.setPoint("trace_game_start_download", param);
                        break;
                    case 10:
                        mViewModel.setPoint("trace_game_downloaded", param);
                        break;
                    default:break;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        unRegisterDownloadListener();
    }

    private void unRegisterDownloadListener() {
        List<Progress> progressList = DownloadManager.getInstance().getAll();
        if (progressList == null || progressList.isEmpty()) {
            return;
        }
        for (Progress progress : progressList) {
            String tag = progress.tag;
            DownloadTask task = OkDownload.getInstance().getTask(tag);
            task.unRegister(tag);
        }
    }

    public void gameDownloadLog(int id, int type, long duration, int gameid) {
        ISupportFragment preFragment = getPreFragment();
        if (preFragment != null && preFragment instanceof GameDetailInfoFragment){
            return;
        }
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
                    if (data != null && data.isStateOK()) {}
                }
            });
        }
    }
}
