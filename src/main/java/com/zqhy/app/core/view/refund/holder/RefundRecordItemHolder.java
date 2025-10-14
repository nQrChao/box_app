package com.zqhy.app.core.view.refund.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.refund.RefundRecordListVo;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author leeham2734
 * @date 2020/5/11-17:06
 * @description
 */
public class RefundRecordItemHolder extends AbsItemHolder<RefundRecordListVo.DataBean, RefundRecordItemHolder.ViewHolder> {

    public RefundRecordItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_refund_record;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull RefundRecordListVo.DataBean item) {
        holder.mTvRefundAmount.setText("退款金额：" + item.getTotal() + "元");
        holder.mTvPtbAmount.setText(item.getTotal());
        holder.mTvGameName.setText(item.getGamename());
        holder.mTvOrderTime.setText(CommonUtils.formatTimeStamp(item.getLogtime() * 1000, "MM月dd日 HH:mm"));
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvRefundAmount;
        private TextView mTvGameName;
        private TextView mTvOrderTime;
        private TextView mTvPtbAmount;

        public ViewHolder(View view) {
            super(view);
            mTvRefundAmount = findViewById(R.id.tv_refund_amount);
            mTvGameName = findViewById(R.id.tv_game_name);
            mTvOrderTime = findViewById(R.id.tv_order_time);
            mTvPtbAmount = findViewById(R.id.tv_ptb_amount);

        }
    }
}
