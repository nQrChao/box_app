package com.zqhy.app.core.view.user.welfare.holder;

import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.view.View;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.main.holder.GameNormalItemHolder;
import com.zqhy.app.core.view.user.welfare.MyFavouriteGameListFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class FavouriteGameItemHolder extends GameNormalItemHolder {

    private float density;

    public FavouriteGameItemHolder(Context context) {
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
    public void onBindViewHolder(@NonNull GameNormalItemHolder.ViewHolder holder, @NonNull GameInfoVo item) {
        super.onBindViewHolder(holder, item);
        if (!item.isGameOnline()) {
            holder.mLlRootview.setOnClickListener(view -> {
                Toaster.show("该游戏已退出江湖");
            });
        }
        holder.mLlRootview.setOnLongClickListener(view -> {
            AlertDialog dialog = new AlertDialog.Builder(mContext)
                    .setTitle("提示")
                    .setMessage("是否取消收藏该游戏？")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            //取消收藏
                            setGameUnFavorite(item);
                        }
                    })
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create();
            dialog.show();
            return false;
        });
    }


    private void setGameUnFavorite(GameInfoVo item) {
        if (_mSubFragment != null && _mSubFragment instanceof MyFavouriteGameListFragment) {
            ((MyFavouriteGameListFragment) _mSubFragment).setGameUnFavorite(item.getGameid());
        }
    }


    @Override
    public int getLayoutResId() {
        return R.layout.item_favourite_game;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends GameNormalItemHolder.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }
}
