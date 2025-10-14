package com.zqhy.app.core.view.user.newvip.holder;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.user.newvip.SuperUserInfoVo;
import com.zqhy.app.core.view.user.newvip.NewUserVipFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class VipRewardItemHolder extends AbsItemHolder<SuperUserInfoVo.DayRewardVo, VipRewardItemHolder.ViewHolder> {
    public VipRewardItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SuperUserInfoVo.DayRewardVo item) {
        GlideUtils.loadGameIcon(mContext, item.getIcon(), holder.mIvIcon);
        holder.mTvName.setText(item.getTitle());
        String str = item.getPrice_label() + "/每日" + item.getBuy_count() + "次";
        if ("1".equals(item.getCycle())){
            str = item.getPrice_label() + "/每日" + item.getBuy_count() + "次";
        }else if ("2".equals(item.getCycle())){
            str = item.getPrice_label() + "/每周" + item.getBuy_count() + "次";
        }else if ("3".equals(item.getCycle())){
            str = item.getPrice_label() + "/每月" + item.getBuy_count() + "次";
        }
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF450A")), 0, item.getPrice_label().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.mTvContent.setText(ss);
        holder.mTvConfirm.setOnClickListener(view -> {
            if (_mFragment != null && _mFragment instanceof NewUserVipFragment) {
                if (_mFragment.checkLogin()) {
                    if ("兑换".equals(item.getLabel())){
                        ((NewUserVipFragment) _mFragment).showBuyTipsDialog(0, item);
                    }else if ("领取".equals(item.getLabel())){
                        ((NewUserVipFragment) _mFragment).showBuyTipsDialog(1, item);
                    }else if ("购买".equals(item.getLabel())){
                        ((NewUserVipFragment) _mFragment).showBuyTipsDialog(2, item);
                    }
                }
            }
        });
        if (item.getHas_get() < Integer.parseInt(item.getBuy_count())){
            holder.mTvConfirm.setText(item.getLabel());
            holder.mTvConfirm.setTextColor(Color.parseColor("#FF3D63"));
            holder.mTvConfirm.setBackgroundResource(R.drawable.shape_ff3d63_big_radius_with_line);
            holder.mTvConfirm.setClickable(true);
        }else{
            /*if ("兑换".equals(item.getLabel())){
                holder.mTvConfirm.setText("已兑");
            }else if ("领取".equals(item.getLabel())){
                holder.mTvConfirm.setText("已领");
            }else if ("购买".equals(item.getLabel())){
                holder.mTvConfirm.setText("已购");
            }*/
            holder.mTvConfirm.setText(item.getLabel());
            holder.mTvConfirm.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mTvConfirm.setBackgroundResource(R.drawable.ts_shape_big_radius_ababab_8a8a8a);
            holder.mTvConfirm.setClickable(false);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_vip_reward;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private ImageView mIvIcon;
        private TextView mTvName;
        private TextView mTvContent;
        private TextView mTvConfirm;

        public ViewHolder(View view) {
            super(view);
            mIvIcon = itemView.findViewById(R.id.iv_icon);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvContent = itemView.findViewById(R.id.tv_content);
            mTvConfirm = itemView.findViewById(R.id.tv_confirm);
        }
    }
}
