package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameCouponListVo;
import com.zqhy.app.core.view.game.GameCouponListFragment;
import com.zqhy.app.newproject.R;

/**
 *
 * @author Administrator
 * @date 2018/11/21
 */

public class GameCouponItemHolder extends AbsItemHolder<GameCouponListVo.DataBean,GameCouponItemHolder.ViewHolder>{
    public GameCouponItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameCouponListVo.DataBean item) {

        holder.mTvAmount.setText(String.valueOf(item.getAmount()));
        holder.mSbTxt1.setText(item.getUse_cdt());

        holder.mTvText1.setText(item.getRange());
        holder.mTvText2.setText(item.getExpiry());

        if (item.getStatus() == 1) {
            holder.mBtnAction.setText("领取");
            holder.mBtnAction.setEnabled(true);
            holder.mBtnAction.setShapeSolidColor(ContextCompat.getColor(mContext, R.color.color_ff4f4f)).setUseShape();
        } else if (item.getStatus() == 10) {
            holder.mBtnAction.setText("已领取");
            holder.mBtnAction.setEnabled(false);
            holder.mBtnAction.setShapeSolidColor(ContextCompat.getColor(mContext, R.color.color_b2b2b2)).setUseShape();
        } else if (item.getStatus() == -1) {
            holder.mBtnAction.setText("已领完");
            holder.mBtnAction.setEnabled(false);
            holder.mBtnAction.setShapeSolidColor(ContextCompat.getColor(mContext, R.color.color_b2b2b2)).setUseShape();
        }

        holder.mBtnAction.setOnClickListener(view -> {
            if(_mFragment != null){
                ((GameCouponListFragment)_mFragment).getCoupon(item.getCoupon_id());
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_coupon;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvAmount;
        private SuperButton mSbTxt1;
        private TextView mTvText1;
        private TextView mTvText2;
        private SuperButton mBtnAction;

        public ViewHolder(View view) {
            super(view);

            mTvAmount = view.findViewById(R.id.tv_amount);
            mSbTxt1 = view.findViewById(R.id.sb_txt_1);
            mTvText1 = view.findViewById(R.id.tv_text_1);
            mTvText2 = view.findViewById(R.id.tv_text_2);
            mBtnAction = view.findViewById(R.id.btn_action);

        }
    }
}
