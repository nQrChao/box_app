package com.zqhy.app.core.view.transfer.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.transfer.TransferRecordListVo;
import com.zqhy.app.core.view.transfer.TransferRecordFragment;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.TimeUtils;

/**
 *
 * @author Administrator
 * @date 2018/11/17
 */

public class TransferRecordItemHolder extends AbsItemHolder<TransferRecordListVo.DataBean,TransferRecordItemHolder.ViewHolder>{

    public TransferRecordItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TransferRecordListVo.DataBean item) {
        holder.mTvTime.setText(TimeUtils.formatTimeStamp(item.getAdd_time() * 1000, "yyyy-MM-dd\nHH:mm"));

        if (item.getPoints() > 0) {
            holder.mTvBalancePayments.setTextColor(ContextCompat.getColor(mContext, R.color.color_007aff));
        } else {
            holder.mTvBalancePayments.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff0000));
        }
        holder.mTvBalancePayments.setText(String.valueOf((int)item.getPoints()));
        holder.mTvBalance.setText(item.getBalance());
        holder.mTvGameName.setText(item.getGamename());

        holder.mTvGameName.setOnClickListener(view -> {
            if (item.getType() == 2) {
                _mFragment.start(TransferRecordFragment.newInstance(item.getApply_id(), item.getGamename()));
            } else {
                _mFragment.showFloatView(item.getRemark(), holder.mTvGameName);
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_transfer_record_list;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private TextView mTvTime;
        private TextView mTvBalancePayments;
        private TextView mTvBalance;
        private TextView mTvGameName;

        public ViewHolder(View view) {
            super(view);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mTvBalancePayments = itemView.findViewById(R.id.tv_balance_payments);
            mTvBalance = itemView.findViewById(R.id.tv_balance);
            mTvGameName = itemView.findViewById(R.id.tv_game_name);

        }
    }
}
