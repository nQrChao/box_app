package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import android.view.View;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.detail.GameRefundVo;
import com.zqhy.app.core.view.refund.RefundMainFragment;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/5/9-14:45
 * @description
 */
public class GameRefundItemHolder extends AbsItemHolder<GameRefundVo,GameRefundItemHolder.ViewHolder> {
    public GameRefundItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_detail_refund;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameRefundVo item) {
        holder.mIv.setOnClickListener(view -> {
            if(_mFragment != null && _mFragment.checkLogin()){
                //跳转去退款中心
                _mFragment.startFragment(new RefundMainFragment());
            }
        });
    }

    public class ViewHolder extends AbsHolder {

        private AppCompatImageView mIv;

        public ViewHolder(View view) {
            super(view);
            mIv = findViewById(R.id.iv);

        }
    }
}
