package com.zqhy.app.core.view.recycle_new;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.recycle.XhGameNewRecycleListVo;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2021/6/17-14:33
 * @description
 */
public class XhNewDataItemHolder extends BaseItemHolder<XhGameNewRecycleListVo.DataBean, XhNewDataItemHolder.ViewHolder> {

    public XhNewDataItemHolder(Context context) {
        super(context);
    }

    public static final int XH_RECYCLE_REQUEST_CODE = 0x0001;

    @Override
    public int getLayoutResId() {
        return R.layout.item_xh_new_layout;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull XhGameNewRecycleListVo.DataBean item) {
        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mIvGameIcon);
        holder.mTvGameName.setText(item.getGamename());
        holder.mTvXhAccount.setText("小号：" + item.getXh_showname());
        if (!TextUtils.isEmpty(item.getOtherGameName())){//游戏后缀
            holder.mTvGameSuffix.setVisibility(View.VISIBLE);
            holder.mTvGameSuffix.setText(item.getOtherGameName());
        }else {
            holder.mTvGameSuffix.setVisibility(View.GONE);
        }
        holder.mTvXhCouponAmount.setText(String.valueOf(item.getTotal()));

        holder.mTvAction.setOnClickListener(view -> {
            if (_mFragment != null) {
                _mFragment.startFragmentForResult(XhNewRecycleDetailFragment.newInstance(item), XH_RECYCLE_REQUEST_CODE);
            }
        });
    }

    public class ViewHolder extends AbsHolder {
        private ImageView      mIvGameIcon;
        private TextView       mTvGameName;
        private TextView       mTvXhAccount;
        private TextView       mTvXhCouponAmount;
        private TextView mTvGameSuffix;
        private TextView mTvAction;
        public ViewHolder(View view) {
            super(view);
            mTvGameSuffix = view.findViewById(R.id.tv_game_suffix);
            mIvGameIcon = view.findViewById(R.id.iv_game_icon);
            mTvGameName = view.findViewById(R.id.tv_game_name);
            mTvXhAccount = view.findViewById(R.id.tv_xh_account);
            mTvXhCouponAmount = view.findViewById(R.id.tv_xh_coupon_amount);
            mTvAction = view.findViewById(R.id.tv_action);
        }
    }
}
