package com.zqhy.app.core.view.user.newvip.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.user.newvip.SuperUserInfoVo;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class NewVipCouponItemHolder extends AbsItemHolder<GameInfoVo, NewVipCouponItemHolder.ViewHolder> {
    public NewVipCouponItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameInfoVo item) {
        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mIvIcon, R.mipmap.ic_placeholder, 15);
        holder.mTvName.setText(item.getGamename());
        holder.mTvGameSuffix.setText(item.getOtherGameName());
        if (!TextUtils.isEmpty(item.getOtherGameName())){//判断是否有游戏后缀
            if (CommonUtils.isToday(item.getNext_server_time() * 1000)){//判断最新开服时间是不是今天
                holder.mTvInfomiddle.setText(item.getServer_str());
                holder.mTvInfomiddle.setTextColor(Color.parseColor("#59BAF6"));
            }else{
                holder.mTvInfomiddle.setText(item.getGenre_str());
                holder.mTvInfomiddle.setTextColor(Color.parseColor("#999999"));
            }
        }else{
            holder.mTvInfomiddle.setTextColor(Color.parseColor("#999999"));
            String str = item.getGenre_str() + "  " + item.getServer_str();
            SpannableString ss = new SpannableString(str);
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#56BAF6")), item.getGenre_str().length() + 2, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.mTvInfomiddle.setText(ss);
        }
        if (!TextUtils.isEmpty(item.getCard_count())){//礼包
            SpannableString ss = new SpannableString("会员额外享" + item.getCard_count() + "款礼包");
            ss.setSpan(new StyleSpan(Typeface.BOLD), 5, 5 + item.getCard_count().length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.mTvContent.setText(ss);
            holder.mTvReceive.setText("领礼包");
        }else{//代金券
            SpannableString ss = new SpannableString("会员额外赠送" + (int) item.getCoupon_total() + "元代金券");
            ss.setSpan(new StyleSpan(Typeface.BOLD), 6, 6 + String.valueOf((int) item.getCoupon_total()).length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.mTvContent.setText(ss);
            holder.mTvReceive.setText("领券");
        }
        holder.mTvReceive.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_vip_coupon_new;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private ImageView mIvIcon;
        private TextView mTvName;
        private TextView mTvGameSuffix;
        private TextView mTvInfomiddle;
        private TextView mTvContent;
        private TextView mTvReceive;

        public ViewHolder(View view) {
            super(view);
            mIvIcon = itemView.findViewById(R.id.iv_icon);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvGameSuffix = itemView.findViewById(R.id.tv_game_suffix);
            mTvInfomiddle = itemView.findViewById(R.id.tv_info_middle);
            mTvContent = itemView.findViewById(R.id.tv_content);
            mTvReceive = itemView.findViewById(R.id.tv_receive);
        }
    }
}
