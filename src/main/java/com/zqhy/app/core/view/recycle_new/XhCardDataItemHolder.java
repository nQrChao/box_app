package com.zqhy.app.core.view.recycle_new;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.recycle.XhRecycleCardListVo;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2021/6/17-14:33
 * @description
 */
public class XhCardDataItemHolder extends BaseItemHolder<XhRecycleCardListVo.DataBean, XhCardDataItemHolder.ViewHolder> {

    public XhCardDataItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_xh_new_card_layout;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull XhRecycleCardListVo.DataBean item) {
        holder.mTvCardPrice.setText(String.valueOf(item.getAmount()));
        holder.mTvCardName.setText(item.getName());
        holder.mTvCardExpiry.setText("有效期：" + item.getExpir());
        holder.mTvCardTips.setText(item.getDes());
        holder.mTvCardCount.setText(item.getNum() + "张");

        holder.mTvCardTips.setOnClickListener(v -> {
            if(_mFragment != null) ((XhNewRecycleDetailFragment)_mFragment).showTipDialog();
        });
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvCardPrice;
        private TextView mTvCardName;
        private TextView mTvCardExpiry;
        private TextView mTvCardTips;
        private TextView mTvCardCount;

        public ViewHolder(View view) {
            super(view);
            mTvCardPrice = view.findViewById(R.id.tv_card_price);
            mTvCardName = view.findViewById(R.id.tv_card_name);
            mTvCardExpiry = view.findViewById(R.id.tv_card_expiry);
            mTvCardTips = view.findViewById(R.id.tv_card_tips);
            mTvCardCount = view.findViewById(R.id.tv_card_count);
        }
    }
}
