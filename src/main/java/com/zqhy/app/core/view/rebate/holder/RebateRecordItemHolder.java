package com.zqhy.app.core.view.rebate.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.rebate.RebateRecordListVo;
import com.zqhy.app.core.view.rebate.RebateRecordItemFragment;
import com.zqhy.app.core.view.rebate.RebateRecordListFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/16
 */

public class RebateRecordItemHolder extends AbsItemHolder<RebateRecordListVo.DataBean, RebateRecordItemHolder.ViewHolder> {

    private int rebate_type;

    public RebateRecordItemHolder(Context context) {
        super(context);
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        rebate_type = (int) view.getTag(R.id.tag_first);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull RebateRecordListVo.DataBean item) {
        GlideApp.with(mContext)
                .asBitmap()
                .load(item.getGameicon())
                .placeholder(R.mipmap.ic_placeholder)
                .centerCrop()
                .into(holder.mIvGameIcon);

        holder.mTvGameName.setText(item.getGamename());
        holder.mTvRechargeTime.setText("充值时间：" + item.getDay_time());
        holder.mTvRechargeAmount.setText("申请金额：" + item.getUsable_total() + "元" + "（" + item.getXh_showname() + "）");

        String rebateStatus = "";
        holder.mTvRebateStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
        switch (item.getStatus()) {
            case 1:
                rebateStatus = "等待受理";
                break;
            case 2:
                rebateStatus = "受理中";
                holder.mTvRebateStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_3478f6));
                break;
            case 10:
                rebateStatus = "已受理";
                break;
            case -1:
                rebateStatus = "已撤回";
                break;
            case -2:
                rebateStatus = "申请失败";
                break;
            default:
                break;
        }
        holder.mTvRebateStatus.setText(rebateStatus);
        holder.mIvGameIcon.setOnClickListener(view -> {
            //跳转游戏详情
        });
        holder.mLlRebateDetail.setOnClickListener(view -> {
            _mFragment.startForResult(RebateRecordItemFragment.newInstance(rebate_type,item.getApply_id()), RebateRecordListFragment.ACTION_REBATE_DETAIL);
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_rebate_record;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private ImageView mIvGameIcon;
        private TextView mTvGameName;
        private TextView mTvRechargeTime;
        private TextView mTvRechargeAmount;
        private TextView mTvRebateStatus;
        private LinearLayout mLlRebateDetail;

        public ViewHolder(View view) {
            super(view);
            mIvGameIcon = itemView.findViewById(R.id.iv_game_icon);
            mTvGameName = itemView.findViewById(R.id.tv_game_name);
            mTvRechargeTime = itemView.findViewById(R.id.tv_recharge_time);
            mTvRechargeAmount =  itemView.findViewById(R.id.tv_recharge_amount);
            mTvRebateStatus = itemView.findViewById(R.id.tv_rebate_status);
            mLlRebateDetail =  itemView.findViewById(R.id.ll_rebate_detail);
        }
    }
}
