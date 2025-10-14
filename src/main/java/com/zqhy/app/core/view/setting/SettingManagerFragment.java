package com.zqhy.app.core.view.setting;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.config.URL;
import com.zqhy.app.config.WxControlConfig;
import com.zqhy.app.core.data.model.game.GameExtraVo;
import com.zqhy.app.core.data.model.setting.SettingItemVo;
import com.zqhy.app.core.data.model.setting.WxPayPlugVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.model.version.VersionVo;
import com.zqhy.app.core.dialog.VersionDialogHelper;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.AppUtil;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.game.GameDownloadManagerFragment;
import com.zqhy.app.core.view.setting.holder.SettingItemHolder;
import com.zqhy.app.core.view.test.TestFragment;
import com.zqhy.app.glide.GlideModuleConfig;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.listener.NetworkPollingListener;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.notify.DownloadNotifyManager;
import com.zqhy.app.utils.cache.FolderUtils;
import com.zqhy.app.utils.sdcard.SdCardManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/14
 */

public class SettingManagerFragment extends BaseListFragment<SettingViewModel> {
    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "系统设置页";
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder<>().bind(SettingItemVo.class, new SettingItemHolder(_mActivity)).build();
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }


    private void getAppVersion() {
        if (mViewModel != null) {
            mViewModel.getAppVersion(new OnBaseCallback<VersionVo>() {
                @Override
                public void onSuccess(VersionVo versionVo) {
                    if (versionVo != null) {
                        if (versionVo.isStateOK()) {
                            if (versionVo.getData() != null) {
                                VersionDialogHelper versionDialogHelper = new VersionDialogHelper(_mActivity);
                                versionDialogHelper.showVersion(true, versionVo.getData());
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("更多设置");
        setPullRefreshEnabled(false);
        setLoadingMoreEnabled(false);
        setListData();
    }


    List<SettingItemVo> settingItemVoList;

    private void setListData() {
        settingItemVoList = new ArrayList<>();

        settingItemVoList.add(new SettingItemVo(R.id.item_setting_download, "下载管理", "查看下载进度"));
        if (WxControlConfig.isWxControl()) {
            settingItemVoList.add(new SettingItemVo(R.id.item_setting_wechat_pay_plugin, "微信支付插件", "支付收银台"));
        }
        //        settingItemVoList.add(new SettingItemVo(R.id.item_setting_business_cooperation, "商务合作", ""));
        settingItemVoList.add(new SettingItemVo(R.id.item_setting_network_line_switching, "切换线路", "优选"));
        String strCache = getCacheSize();
        settingItemVoList.add(new SettingItemVo(R.id.item_setting_clear_cache, "清理缓存", strCache));
        String versionName = AppUtil.getVersionName(_mActivity);
        settingItemVoList.add(new SettingItemVo(R.id.item_setting_check_update, "检查更新", versionName));
        if (BuildConfig.DEBUG || URL.getApiDebug() || isUserDebug()) {
            settingItemVoList.add(new SettingItemVo(R.id.item_setting_check_test, "测试页面", ""));
        }

        addAllData(settingItemVoList);

        setOnItemClickListener((v, position, data) -> {
            int viewID = settingItemVoList.get(position).getViewID();
            switch (viewID) {
                case R.id.item_setting_download:
                    if (checkLogin()) {
                        start(new GameDownloadManagerFragment());
                    }
                    break;
                case R.id.item_setting_wechat_pay_plugin:
                    installPayPlugin();
                    break;
                case R.id.item_setting_business_cooperation:
                    BrowserActivity.newInstance(_mActivity, AppConfig.APP_BUSINESS_COOPERATION);
                    break;
                case R.id.item_setting_network_line_switching:
                    netWorkPolling();
                    break;
                case R.id.item_setting_clear_cache:
                    clearCache();
                    break;
                case R.id.item_setting_check_update:
                    getAppVersion();
                    break;
                case R.id.item_setting_check_test:
                    start(new TestFragment());
                    break;
                default:
                    break;
            }
        });
    }

    private boolean isUserDebug(){
        UserInfoVo.DataBean bean = UserInfoModel.getInstance().getUserInfo();
        if(bean == null){
            return false;
        }else{
            String username = bean.getUsername();
            return "tsyule001".equals(username);
        }
    }

    /**
     * 获取缓存大小
     *
     * @return
     */
    private String getCacheSize() {
        try {
            File glideCacheDir = GlideModuleConfig.getGlideDiskCachePath(_mActivity);
            long cacheSize = FolderUtils.getFolderSize(glideCacheDir);
            String cache = FolderUtils.getFormatSize(cacheSize);
            return cache;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0K";
    }


    /**
     * 切换网络节点
     */
    private void netWorkPolling() {
        if (mViewModel != null) {
            mViewModel.polling(new NetworkPollingListener() {
                @Override
                public void onSuccess() {
                    Toaster.show( "切换成功");
                }

                @Override
                public void onFailure() {
                    Toaster.show( "网络异常，请稍后再试");
                }
            });
        }
    }

    /**
     * 清理缓存
     */
    private void clearCache() {
        //清除缓存
        AlertDialog dialog = new AlertDialog.Builder(_mActivity)
                .setTitle("提示")
                .setMessage("是否清除缓存")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        File glideCacheDir = GlideModuleConfig.getGlideDiskCachePath(_mActivity);
                        if (FolderUtils.deleteDir(glideCacheDir)) {
                            Toaster.show( "清理缓存成功");
                            for (SettingItemVo itemVo : settingItemVoList) {
                                if (itemVo.getViewID() == R.id.item_setting_clear_cache) {
                                    itemVo.setSubTxt(getCacheSize());
                                    break;
                                }
                            }
                            mDelegateAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        dialog.show();
    }

    /**
     * 安装微信支付插件
     */
    private void installPayPlugin() {
        if (mViewModel != null) {
            mViewModel.getWxPayPulg(new OnBaseCallback<WxPayPlugVo>() {
                @Override
                public void onSuccess(WxPayPlugVo wxPayPlugVo) {
                    if (wxPayPlugVo != null) {
                        if (wxPayPlugVo.isStateOK()) {
                            if (wxPayPlugVo.getData() != null) {
                                installWxPayPlug(wxPayPlugVo.getData());
                            }
                        } else {
                            Toaster.show( wxPayPlugVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void installWxPayPlug(WxPayPlugVo.DataBean wxPayPlugVo) {
        if (wxPayPlugVo != null) {
            AlertDialog dialog = new AlertDialog.Builder(_mActivity)
                    .setTitle("提示")
                    .setMessage("将为您下载安装最新微信收银插件，可以吗？")
                    .setPositiveButton("朕准了", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        downloadWxPayPlug(wxPayPlugVo);
                    })
                    .setNegativeButton("不可以", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    })
                    .create();
            dialog.show();
        }
    }

    private void downloadWxPayPlug(WxPayPlugVo.DataBean wxPayPlugVo) {
        GameExtraVo gameInfoVo = new GameExtraVo();
        gameInfoVo.setGameid(-1);
        gameInfoVo.setGamename(wxPayPlugVo.getWx_plug_name());

        OkGo.<File>get(wxPayPlugVo.getWx_plug_url())
                .execute(new FileCallback(SdCardManager.getInstance().getDownloadApkDir().getPath(),
                        wxPayPlugVo.getWx_plug_name()) {
                    @Override
                    public void onSuccess(Response<File> response) {
                        File targetFile = response.body();
                        if (targetFile.exists()) {
                            AppUtil.install(_mActivity, targetFile);
                        }
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        DownloadNotifyManager.getInstance().cancelNotify(-1);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        progress.extra1 = gameInfoVo;
                        DownloadNotifyManager.getInstance().doNotify(progress);
                    }
                });
    }


}
