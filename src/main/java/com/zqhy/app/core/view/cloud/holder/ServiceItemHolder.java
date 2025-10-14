package com.zqhy.app.core.view.cloud.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.cloud.CloudCourseVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.view.server.ServerFragment;
import com.zqhy.app.newproject.R;

/**
 *
 * @author Administrator
 * @date 2018/11/24
 */

public class ServiceItemHolder extends AbsItemHolder<EmptyDataVo, ServiceItemHolder.ViewHolder> {

    public ServiceItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull EmptyDataVo item) {
        holder.mTvService.setOnClickListener(v -> {
           if (_mFragment != null) _mFragment.goKefuCenter();
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_cloud_service;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvService;

        public ViewHolder(View view) {
            super(view);
            mTvService = findViewById(R.id.tv_service);

        }
    }
}
