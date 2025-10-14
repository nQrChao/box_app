package com.zqhy.app.core.view.user.newvip.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.user.newvip.VipPrivilegeVo1;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class VipPrivilegeItemHolder1 extends AbsItemHolder<VipPrivilegeVo1, VipPrivilegeItemHolder1.ViewHolder> {
    public VipPrivilegeItemHolder1(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull VipPrivilegeVo1 item) {
        holder.mIvIcon.setImageResource(item.getIcon());
        holder.mTvTips.setText(item.getTips());
        holder.mTvTitle.setText(item.getTitle());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_vip_privilege1;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private ImageView mIvIcon;
        private TextView  mTvTips;
        private TextView  mTvTitle;

        public ViewHolder(View view) {
            super(view);
            mIvIcon = itemView.findViewById(R.id.iv_icon);
            mTvTips = itemView.findViewById(R.id.tv_tips);
            mTvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
