package com.zqhy.app.core.view.bipartition.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.view.bipartition.BipartitionInstallFragment;
import com.zqhy.app.core.view.bipartition.BipartitionListFragment;
import com.zqhy.app.db.table.bipartition.BipartitionGameVo;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.MemoryInfoUtils;

/**
 *
 * @author Administrator
 * @date 2018/11/24
 */

public class PackageInfoItemHolder extends AbsItemHolder<BipartitionGameVo, PackageInfoItemHolder.ViewHolder> {

    public PackageInfoItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull BipartitionGameVo item) {
        GlideUtils.loadGameIcon(mContext, item.getGameicon(), holder.mIvIcon);
        holder.mTvName.setText(item.getGamename());
        holder.mIvIcon.setOnClickListener(view -> {
            if (_mFragment != null){
                ((BipartitionListFragment) _mFragment).goGameDetail(item.getGameid(), item.getGame_type());
            }
        });
        holder.mTvAction.setOnClickListener(view -> {
            if (_mFragment != null && _mFragment.checkLogin()){
                //((BipartitionListFragment) _mFragment).asyncLaunchApp(item);
                if (MemoryInfoUtils.checkBipartition(mContext)){
                    ((BipartitionListFragment) _mFragment).startFragment(BipartitionInstallFragment.newInstance(item.getGameid(), false));
                }else {
                    //ToastT.error("当前设备不支持此功能!");
                    Toaster.show("当前设备不支持此功能");
                }
            }
        });
        holder.mIvIcon.setOnLongClickListener(view -> {
            if (_mFragment != null && _mFragment.checkLogin()){
                // 卸载apk
                //((BipartitionListFragment) _mFragment).showActionDialog(item);
            }
            return false;
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_package_info;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private ConstraintLayout mClRoot;
        private ImageView mIvIcon;
        private TextView mTvName;
        private TextView mTvAction;

        public ViewHolder(View view) {
            super(view);
            mClRoot = view.findViewById(R.id.cl_root);
            mIvIcon = view.findViewById(R.id.iv_icon);
            mTvName = view.findViewById(R.id.tv_name);
            mTvAction = view.findViewById(R.id.tv_action);
        }
    }
}
