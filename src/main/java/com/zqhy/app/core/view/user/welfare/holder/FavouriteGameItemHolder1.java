package com.zqhy.app.core.view.user.welfare.holder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.tool.AppUtil;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.AppsUtils;
import com.zqhy.app.core.view.user.NewUserMineFragment1;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.MemoryInfoUtils;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class FavouriteGameItemHolder1 extends AbsItemHolder<GameInfoVo, FavouriteGameItemHolder1.ViewHolder> {

    private float density;

    public FavouriteGameItemHolder1(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(context);
    }

    BaseFragment _mSubFragment;
    @Override
    protected void initView(View view) {
        super.initView(view);
        _mSubFragment = (BaseFragment) view.getTag(R.id.tag_sub_fragment);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteGameItemHolder1.ViewHolder holder, @NonNull GameInfoVo item) {
        //图标
        GlideUtils.loadGameIcon(mContext, item.getGameicon(), holder.mGameIconIV);
        holder.mTvGameName.setText(item.getGamename());

        if (item.getPlay_duration() > 0){
            holder.mTvHour.setText(item.getPlay_duration() + "小时");
        }else {
            holder.mTvHour.setText("-");
        }

        if (AppsUtils.isInstallApp(mContext, item.getClient_package_name())){
            holder.mTvPlay.setText("开始游戏");
        }else {
            holder.mTvPlay.setText("下载游戏");
        }

        /*if (BuildConfig.NEED_BIPARTITION){
            holder.mTvCloud.setVisibility(View.GONE);
            holder.mTvSpace.setVisibility(View.GONE);
        }else {
            holder.mTvCloud.setVisibility(View.VISIBLE);
            holder.mTvSpace.setVisibility(View.VISIBLE);
        }*/

        holder.mLlRootview.setOnClickListener(view -> {
            if (_mFragment != null){
                if (!item.isGameOnline()) {
                    Toaster.show("该游戏已退出江湖");
                }else {
                    _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
                }
            }
        });

        holder.mIvMore.setOnClickListener(view -> {
            showPopUp(holder.mIvMore, item);
        });

        holder.mTvCloud.setOnClickListener(view -> {
            if (_mFragment != null){
                if (!item.isGameOnline()) {
                    Toaster.show("该游戏已退出江湖");
                }else {
                    //云挂机
                    //if (_mFragment.checkLogin()) _mFragment.startFragment(CloudVeGuideFragment.newInstance());
                    Toaster.show("功能开发中。。。");
                }
            }
        });

        holder.mTvSpace.setOnClickListener(view -> {
            if (_mFragment != null){
                if (!item.isGameOnline()) {
                    Toaster.show("该游戏已退出江湖");
                }else {
                    //双开
                    if (MemoryInfoUtils.checkBipartition(mContext)) {
                        //_mFragment.startFragment(BipartitionListFragment.newInstance(item.getGameid()));
                    }else {
                        Toaster.show("当前设备不支持此功能！");
                    }
                }
            }
        });

        holder.mGameIconIV.setOnClickListener(view -> {
            if (_mFragment != null){
                if (!item.isGameOnline()) {
                    Toaster.show("该游戏已退出江湖");
                }else {
                    _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
                }
            }
        });
        holder.mTvGameName.setOnClickListener(view -> {
            if (_mFragment != null){
                if (!item.isGameOnline()) {
                    Toaster.show("该游戏已退出江湖");
                }else {
                    _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
                }
            }
        });

        holder.mTvPlay.setOnClickListener(view -> {
            if (_mFragment != null){
                if (!item.isGameOnline()) {
                    Toaster.show("该游戏已退出江湖");
                }else {
                    if (AppsUtils.isInstallApp(mContext, item.getClient_package_name())) {
                        AppUtil.open(mContext, item.getClient_package_name());
                    }else {
                        _mFragment.goGameDetail(item.getGameid(), item.getGame_type(), true);
                    }
                }
            }
        });
    }

    private void setGameUnFavorite(GameInfoVo item) {
        if (_mSubFragment != null && _mSubFragment instanceof NewUserMineFragment1) {
            ((NewUserMineFragment1) _mSubFragment).setGameUnFavorite(item.getGameid());
        }
    }


    @Override
    public int getLayoutResId() {
        return R.layout.item_favourite_game1;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        public LinearLayout mLlRootview;
        public ImageView mGameIconIV;
        public TextView mTvGameName;
        public TextView mTvHour;
        public ImageView mIvMore;
        public TextView mTvCloud;
        public TextView mTvSpace;
        public TextView mTvPlay;

        public ViewHolder(View view) {
            super(view);
            mLlRootview = findViewById(R.id.ll_rootView);
            mGameIconIV = findViewById(R.id.gameIconIV);
            mTvGameName = findViewById(R.id.tv_gamename);
            mTvHour = findViewById(R.id.tv_hour);
            mIvMore = findViewById(R.id.iv_more);
            mTvCloud = findViewById(R.id.tv_cloud);
            mTvSpace = findViewById(R.id.tv_space);
            mTvPlay = findViewById(R.id.tv_play);
        }
    }

    private View popupView;
    private PopupWindow popWindow;
    private void showPopUp(View view, GameInfoVo item){
        if (popupView == null){
            popupView = LayoutInflater.from(mContext).inflate(R.layout.dialog_popup_reserve_tips, null, false);
            popWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        }
        popupView.findViewById(R.id.tv_cancel).setOnClickListener(view1 -> {
            if (popWindow != null && popWindow.isShowing()) popWindow.dismiss();
            AlertDialog dialog = new AlertDialog.Builder(mContext)
                    .setTitle("提示")
                    .setMessage("是否取消收藏该游戏？")
                    .setPositiveButton("是", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        //取消收藏
                        setGameUnFavorite(item);
                    })
                    .setNegativeButton("否", (dialogInterface, i) -> dialogInterface.dismiss()).create();
            dialog.show();
        });
        if (AppsUtils.isInstallApp(mContext, item.getClient_package_name())){
            popupView.findViewById(R.id.tv_unload).setVisibility(View.VISIBLE);
        }else {
            popupView.findViewById(R.id.tv_unload).setVisibility(View.GONE);
        }
        popupView.findViewById(R.id.tv_unload).setOnClickListener(view1 -> {
            if (popWindow != null && popWindow.isShowing()) popWindow.dismiss();
            openAppSettings(item.getClient_package_name());
        });
        popWindow.setTouchable(true);
        popWindow.showAsDropDown(view, -view.getWidth() / 2, 10);
    }

    public void openAppSettings(String packageName) {
        if (packageName != null) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", packageName, null);
            intent.setData(uri);
            mContext.startActivity(intent);
        }
    }
}
