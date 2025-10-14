package com.zqhy.app.core.view.transaction.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.transaction.GameXhInfoVo;
import com.zqhy.app.core.view.transaction.sell.TransactionChooseXhFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class TradeChooseGameXhItemHolder extends AbsItemHolder<GameXhInfoVo.DataBean, TradeChooseGameXhItemHolder.ViewHolder> {

    public TradeChooseGameXhItemHolder(Context context) {
        super(context);
    }


    @Override
    public int getLayoutResId() {
        return R.layout.item_layout_select_recycle;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameXhInfoVo.DataBean item) {

        holder.mTvXhAccount.setText(item.getXh_showname());
        holder.mTvXhAccountTopUp.setText(mContext.getResources().getString(R.string.string_xh_recharge_count, String.valueOf(item.getTotal())));

        holder.mItemView.setEnabled(true);
        boolean isSelectedItem = false;
        if(_mFragment != null && _mFragment instanceof TransactionChooseXhFragment){
            isSelectedItem = ((TransactionChooseXhFragment)_mFragment).isSelectedItem(item.getId());
        }
        if (isSelectedItem) {
            holder.mItemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_fff8f1));
            holder.mIvSelectable.setImageResource(R.mipmap.ic_recycle_account_selected);
        } else {
            holder.mItemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            holder.mIvSelectable.setImageResource(R.mipmap.ic_recycle_account_enable_selected);
        }
    }

    public class ViewHolder extends AbsHolder {

        private LinearLayout mItemView;
        private ImageView mIvSelectable;
        private TextView mTvXhAccount;
        private TextView mTvXhAccountTopUp;

        public ViewHolder(View view) {
            super(view);

            mItemView = findViewById(R.id.item_view);
            mIvSelectable = findViewById(R.id.iv_selectable);
            mTvXhAccount = findViewById(R.id.tv_xh_account);
            mTvXhAccountTopUp = findViewById(R.id.tv_xh_account_top_up);


        }
    }
}
