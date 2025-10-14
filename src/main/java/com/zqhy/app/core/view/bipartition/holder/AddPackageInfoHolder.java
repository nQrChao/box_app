package com.zqhy.app.core.view.bipartition.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.newproject.R;

/**
 *
 * @author Administrator
 * @date 2018/11/24
 */

public class AddPackageInfoHolder extends AbsItemHolder<String, AddPackageInfoHolder.ViewHolder> {

    public AddPackageInfoHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull String item) {
        holder.mIvAdd.setOnClickListener(view -> {
            if (_mFragment != null){
                /*if (MemoryInfoUtils.checkBipartition(mContext)){
                    ((BipartitionListFragment) _mFragment).showSelectGameDialog();
                }else {
                    ToastT.error("当前设备不支持此功能!");
                }*/
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_package_info_add;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private ImageView mIvAdd;
        public ViewHolder(View view) {
            super(view);
            mIvAdd = view.findViewById(R.id.iv_add);
        }

    }
}
