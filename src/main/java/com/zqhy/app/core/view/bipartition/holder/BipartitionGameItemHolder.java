package com.zqhy.app.core.view.bipartition.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/24
 */

public class BipartitionGameItemHolder extends AbsItemHolder<GameInfoVo, BipartitionGameItemHolder.ViewHolder> {

    public BipartitionGameItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameInfoVo item) {
        //图标
        GlideUtils.loadGameIcon(mContext, item.getGameicon(), holder.mIvGameIcon);
        holder.mTvGameName.setText(item.getGamename());
        holder.mTvGameSuffix.setText(item.getOtherGameName());
        if (TextUtils.isEmpty(item.getOtherGameName())){
            holder.mTvGameSuffix.setVisibility(View.GONE);
        }else {
            holder.mTvGameSuffix.setVisibility(View.VISIBLE);
        }
        holder.mTvInfoMiddle.setText(item.getGenre_str());
        /*holder.mTvAction.setOnClickListener(view -> {
            if (_mFragment != null && _mFragment.checkLogin()){
                if (EasyFloat.isShow("float_download")){
                    if (checkDownload(item)){
                        ((BipartitionListFragment)_mFragment).dismissSelectGameDialog();
                        _mFragment.startFragment(BipartitionInstallFragment.newInstance(item.getGameid(), false));
                        EasyFloat.dismiss("float_download");
                    }else {
                        ToastT.error("游戏安装中，请完成后再试！");
                    }
                    return;
                }
                ((BipartitionListFragment)_mFragment).dismissSelectGameDialog();
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
                    _mFragment.startFragment(BipartitionInstallFragment.newInstance(item.getGameid(), false));
                }else {
                    if (checkDownload(item)){
                        ToastT.error("游戏安装中，请完成后再试！");
                        return;
                    }
                    if (MemoryInfoUtils.checkBipartition(mContext)){
                        _mFragment.startFragment(BipartitionInstallFragment.newInstance(item.getGameid(), true));
                    }else {
                        ToastT.error("当前设备不支持此功能!");
                    }
                }
            }
        });*/
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_bipartition_item;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private ImageView mIvGameIcon;
        private TextView mTvGameName;
        private TextView mTvGameSuffix;
        private TextView mTvInfoMiddle;
        private TextView mTvAction;

        public ViewHolder(View view) {
            super(view);
            mIvGameIcon = view.findViewById(R.id.iv_game_icon);
            mTvGameName = view.findViewById(R.id.tv_game_name);
            mTvGameSuffix = view.findViewById(R.id.tv_game_suffix);
            mTvInfoMiddle = view.findViewById(R.id.tv_info_middle);
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
