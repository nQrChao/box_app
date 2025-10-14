package com.zqhy.app.core.view.user.provincecard.holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.user.SuperVipMemberInfoVo;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class SuperProvinceItemHolder extends AbsItemHolder<SuperVipMemberInfoVo.DataBean.CardType, SuperProvinceItemHolder.ViewHolder> {
    private boolean isDialog;
    public SuperProvinceItemHolder(Context context, boolean isDialog) {
        super(context);
        this.isDialog = isDialog;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SuperVipMemberInfoVo.DataBean.CardType item) {

        holder.mTvVipMemberName.setText(item.getName());
        holder.mTvVipMemberDays.setText("再赠" + (item.getDay() * item.getReward()) + "币");
        holder.mTvVipMemberAmount.setText(String.valueOf(item.getPrice()));
        holder.mTvVipMemberTotalAmount.setText("立返" + item.getGive() + "福利币");

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
        return R.layout.item_super_province_card;
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
