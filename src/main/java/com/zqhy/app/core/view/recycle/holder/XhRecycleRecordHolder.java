package com.zqhy.app.core.view.recycle.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.recycle.XhRecycleRecordVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.recycle.XhRecycleRecordListFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 */
public class XhRecycleRecordHolder extends AbsItemHolder<XhRecycleRecordVo, XhRecycleRecordHolder.ViewHolder> {

    public XhRecycleRecordHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_xh_recycle_record;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull XhRecycleRecordVo item) {
        holder.mTvRecycleTime.setText(CommonUtils.formatTimeStamp(item.getAdd_time() * 1000, "yyyy/MM/dd HH:mm"));
        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mIvGameIcon);
        holder.mTvGameName.setText(item.getGamename());

        if (!TextUtils.isEmpty(item.getOtherGameName())){//游戏后缀
            holder.mTvGameSuffix.setVisibility(View.VISIBLE);
            holder.mTvGameSuffix.setText(item.getOtherGameName());
        }else {
            holder.mTvGameSuffix.setVisibility(View.GONE);
        }

        holder.mTvGameGenre.setText(item.getGenre_str());
        holder.mTvXhAccount.setText(item.getXh_showname());
        holder.mTvRewardReal.setText("回收福利币总额：" + item.getHs_gold_total());

        holder.mTvXhAccountRedemption.setVisibility(item.isAble_cancel() ? View.VISIBLE : View.GONE);
        holder.mTvXhAccountRedemption.setOnClickListener(view -> {
            if (_mFragment != null && _mFragment instanceof XhRecycleRecordListFragment) {
                ((XhRecycleRecordListFragment) _mFragment).redemptionRecord2(item);
            }
        });
    }

    public class ViewHolder extends AbsHolder {
        private TextView  mTvRecycleTime;
        private ImageView mIvGameIcon;
        private TextView  mTvGameName;
        private TextView  mTvXhAccount;
        private TextView  mTvRewardReal;
        private TextView  mTvGameGenre;
        private TextView  mTvXhAccountRedemption;
        private TextView mTvGameSuffix;

        public ViewHolder(View view) {
            super(view);
            mTvRecycleTime = findViewById(R.id.tv_recycle_time);
            mIvGameIcon = findViewById(R.id.iv_game_icon);
            mTvGameName = findViewById(R.id.tv_game_name);
            mTvXhAccount = findViewById(R.id.tv_xh_account);
            mTvRewardReal = findViewById(R.id.tv_reward_real);
            mTvGameGenre = findViewById(R.id.tv_game_genre);
            mTvXhAccountRedemption = findViewById(R.id.tv_xh_account_redemption);
            mTvGameSuffix = view.findViewById(R.id.tv_game_suffix);

            GradientDrawable gd = new GradientDrawable();
            gd.setColor(Color.parseColor("#FFF5F4"));
            float radius = ScreenUtil.dp2px(mContext, 6);
            gd.setCornerRadii(new float[]{0, 0, 0, 0, radius, radius, 0, 0});
            mTvRewardReal.setBackground(gd);
        }
    }
}
