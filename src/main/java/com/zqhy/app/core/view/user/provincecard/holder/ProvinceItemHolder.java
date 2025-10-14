package com.zqhy.app.core.view.user.provincecard.holder;

import android.content.Context;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.user.VipMemberTypeVo;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class ProvinceItemHolder extends AbsItemHolder<VipMemberTypeVo.DataBean, ProvinceItemHolder.ViewHolder> {
    private boolean isDialog;
    public ProvinceItemHolder(Context context, boolean isDialog) {
        super(context);
        this.isDialog = isDialog;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull VipMemberTypeVo.DataBean item) {
        if (item.isNewBenefit()){
            holder.mTvVipMemberFreeDays.setText("限购1次");
            holder.mTvVipMemberFreeDays.setVisibility(View.VISIBLE);
            holder.mTvVipMemberFreeDays.setBackgroundResource(R.drawable.ts_shape_gradient_radius_606060_1d1d1d_1);
        } else if (item.getFree_days() != 0) {
            holder.mTvVipMemberFreeDays.setText("已额外赠" + item.getFree_days() + "天");
            holder.mTvVipMemberFreeDays.setVisibility(View.VISIBLE);
            holder.mTvVipMemberFreeDays.setBackgroundResource(R.drawable.ts_shape_gradient_ff5b0a_ff150d);
        } else {
            holder.mTvVipMemberFreeDays.setText("");
            holder.mTvVipMemberFreeDays.setVisibility(View.GONE);
            holder.mTvVipMemberFreeDays.setBackgroundResource(R.drawable.ts_shape_gradient_ff5b0a_ff150d);
        }
        holder.mTvVipMemberName.setText(item.getName());
        holder.mTvVipMemberDays.setText("共领" + (item.getDays() + item.getFree_days()) + "张券");
        holder.mTvVipMemberAmount.setText(String.valueOf(item.getAmount()));
        holder.mTvVipMemberTotalAmount.setText("原价" + item.getTotalAmount() + "元");
        holder.mTvVipMemberTotalAmount.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        if (item.isSelected()){
            holder.mLlRootBg.setBackgroundResource(R.drawable.shape_radius_stroke_fd892d_selected);
        }else {
            if (isDialog){
                holder.mLlRootBg.setBackgroundResource(R.drawable.shape_fff8e9_radius);
            }else {
                holder.mLlRootBg.setBackgroundResource(R.drawable.shape_radius_stroke_fd892d);
            }
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_province_card;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private AppCompatTextView mTvVipMemberFreeDays;
        private AppCompatTextView mTvVipMemberName;
        private AppCompatTextView mTvVipMemberDays;
        private AppCompatTextView mTvVipMemberAmount;
        private AppCompatTextView mTvVipMemberTotalAmount;
        private LinearLayout mLlRootBg;

        public ViewHolder(View view) {
            super(view);
            mTvVipMemberFreeDays = itemView.findViewById(R.id.tv_vip_member_free_days);
            mTvVipMemberName = itemView.findViewById(R.id.tv_vip_member_name);
            mTvVipMemberDays = itemView.findViewById(R.id.tv_vip_member_days);
            mTvVipMemberAmount = itemView.findViewById(R.id.tv_vip_member_amount);
            mTvVipMemberTotalAmount = itemView.findViewById(R.id.tv_vip_member_total_amount);
            mLlRootBg = itemView.findViewById(R.id.ll_root_bg);
        }
    }
}
