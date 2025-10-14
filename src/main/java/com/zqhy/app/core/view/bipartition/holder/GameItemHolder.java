package com.zqhy.app.core.view.bipartition.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.box.other.hjq.toast.Toaster;
import com.lzf.easyfloat.EasyFloat;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.view.bipartition.BipartitionInstallFragment;
import com.zqhy.app.db.table.bipartition.BipartitionGameDbInstance;
import com.zqhy.app.db.table.bipartition.BipartitionGameVo;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.MemoryInfoUtils;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/24
 */

public class GameItemHolder extends AbsItemHolder<GameInfoVo, GameItemHolder.ViewHolder> {

    public GameItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameInfoVo item) {
        GlideUtils.loadGameIcon(mContext, item.getGameicon(), holder.mIvIcon);
        holder.mTvName.setText(item.getGamename());
        holder.mTvType.setText(item.getGenre_str());

        holder.mIvIcon.setOnClickListener(view -> {
            if (_mFragment != null){
                _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
            }
        });
        holder.mTvAction.setOnClickListener(view -> {
            if (_mFragment != null && _mFragment.checkLogin()){
                if (EasyFloat.isShow("float_download")){
                    if (checkDownload(item)){
                        _mFragment.startFragment(BipartitionInstallFragment.newInstance(item.getGameid(), false));
                        EasyFloat.dismiss("float_download");
                    }else {
                        //ToastT.error("游戏安装中，请完成后再试！");
                        Toaster.show("游戏安装中，请完成后再试！");
                    }
                    return;
                }
                if (BipartitionGameDbInstance.getInstance().checkBipartitionGame(item.getClient_package_name())){
                    BipartitionGameVo bipartitionGameVo = new BipartitionGameVo();
                    bipartitionGameVo.setGameid(item.getGameid());
                    bipartitionGameVo.setGamename(item.getGamename());
                    bipartitionGameVo.setGameicon(item.getGameicon());
                    bipartitionGameVo.setGame_type(item.getGame_type());
                    bipartitionGameVo.setGenre_str(item.getGenre_str());
                    bipartitionGameVo.setPackage_name(item.getClient_package_name());
                    bipartitionGameVo.setAdd_time(System.currentTimeMillis());

                    //((BipartitionListFragment) _mFragment).asyncLaunchApp(bipartitionGameVo);
                    if (MemoryInfoUtils.checkBipartition(mContext)){
                        _mFragment.startFragment(BipartitionInstallFragment.newInstance(item.getGameid(), false));
                    }else {
                        //ToastT.error("当前设备不支持此功能!");
                        Toaster.show("当前设备不支持此功能");
                    }
                }else {
                    if (checkDownload(item)){
                        //ToastT.error("游戏安装中，请完成后再试！");
                        Toaster.show("游戏安装中，请完成后再试");
                        return;
                    }
                    if (MemoryInfoUtils.checkBipartition(mContext)){
                        _mFragment.startFragment(BipartitionInstallFragment.newInstance(item.getGameid(), true));
                    }else {
                        //ToastT.error("当前设备不支持此功能!");
                        Toaster.show("当前设备不支持此功能");
                    }
                }
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_bipartition_game_info;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private ImageView mIvIcon;
        private TextView mTvName;
        private TextView mTvType;
        private TextView mTvAction;

        public ViewHolder(View view) {
            super(view);
            mIvIcon = view.findViewById(R.id.iv_icon);
            mTvName = view.findViewById(R.id.tv_name);
            mTvType = view.findViewById(R.id.tv_type);
            mTvAction = view.findViewById(R.id.tv_action);
        }
    }

    private boolean checkDownload(GameInfoVo item){
        List<Progress> progressList = DownloadManager.getInstance().getAll();
        if (progressList != null && progressList.size() > 0){
            for (int i = 0; i < progressList.size(); i++) {
                Progress progress = progressList.get(i);
                if (progress != null){
                    if (progress.tag.equals(item.getGameDownloadTag())){
                        if (progress.status == Progress.LOADING){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
