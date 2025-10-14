package com.zqhy.app.core.view.user.newvip.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.user.newvip.SignLuckVo;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class VipSignItemHolder extends AbsItemHolder<SignLuckVo.DataBean, VipSignItemHolder.ViewHolder> {
    public VipSignItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SignLuckVo.DataBean item) {
        if (item.getType().equals("ptb")){
            holder.mIvIcon.setImageResource(R.mipmap.ic_new_vip_sign_item_ptb);
        } else if (item.getType().equals("integral")){
            holder.mIvIcon.setImageResource(R.mipmap.ic_new_vip_sign_item_jb);
        } else if (item.getType().equals("coupon")){
            holder.mIvIcon.setImageResource(R.mipmap.ic_new_vip_sign_item_yhq);
        }
        holder.mTvPrice.setText(item.getTitle());
        holder.mTvType.setText(item.getType_label());
        if (item.isSelected()){
            holder.mIvSelect.setVisibility(View.VISIBLE);
            holder.mLlRootBg.setBackgroundResource(R.drawable.shape_white_radius_with_line);
        }else{
            holder.mIvSelect.setVisibility(View.GONE);
            holder.mLlRootBg.setBackgroundResource(R.drawable.shape_white_radius);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_new_vip_sign;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private ImageView mIvIcon;
        private TextView  mTvPrice;
        private TextView mTvType;
        private ImageView mIvSelect;
        private ConstraintLayout mLlRootBg;

        public ViewHolder(View view) {
            super(view);
            mIvIcon = itemView.findViewById(R.id.iv_icon);
            mTvPrice = itemView.findViewById(R.id.tv_price);
            mTvType = itemView.findViewById(R.id.tv_type);
            mIvSelect = itemView.findViewById(R.id.iv_select);
            mLlRootBg = itemView.findViewById(R.id.ll_root_bg);
        }
    }
}
