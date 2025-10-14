package com.zqhy.app.core.view.transfer.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.transfer.TransferCountVo;
import com.zqhy.app.core.view.transfer.TransferRecordListFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class TransferCountHolder extends AbsItemHolder<TransferCountVo, TransferCountHolder.ViewHolder> {


    public TransferCountHolder(Context context) {
        super(context);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TransferCountVo item) {
        holder.mTvTransferCount.setText(String.valueOf(item.getTransferCount()));
        holder.mLlMyTransfers.setOnClickListener(view -> {
            if (_mFragment != null) {
                //转游明细
                _mFragment.start(new TransferRecordListFragment());
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_transfer_count;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private TextView mTvTransferCount;
        private LinearLayout mLlMyTransfers;

        public ViewHolder(View view) {
            super(view);
            mTvTransferCount = itemView.findViewById(R.id.tv_transfer_count);
            mLlMyTransfers = findViewById(R.id.ll_my_transfers);

        }
    }
}
