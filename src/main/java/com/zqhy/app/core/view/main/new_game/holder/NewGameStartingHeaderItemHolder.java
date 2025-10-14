package com.zqhy.app.core.view.main.new_game.holder;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.coupon.GameCouponsListVo;
import com.zqhy.app.core.data.model.new_game.NewGameStartingHeaderVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.main.new_game.NewGameStartingFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/8/24-12:19
 * @description
 */
public class NewGameStartingHeaderItemHolder extends AbsItemHolder<NewGameStartingHeaderVo, NewGameStartingHeaderItemHolder.ViewHolder> {
    public NewGameStartingHeaderItemHolder(Context context) {
        super(context);
    }

    private BaseFragment _mSubFragment;

    @Override
    protected void initView(View view) {
        super.initView(view);
        _mSubFragment = (BaseFragment) view.getTag(R.id.tag_sub_fragment);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_new_game_starting_header;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull NewGameStartingHeaderVo item) {
        int count = item.getList().size();
        int index = 0;
        holder.mLlContainer.removeAllViews();
        while (index < count) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, (int) (8 * ScreenUtil.getScreenDensity(mContext)), 0);
            holder.mLlContainer.addView(createVoucherItem(item.getList().get(index)), params);
            index++;
        }

    }


    private View createVoucherItem(GameCouponsListVo.DataBean item) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_new_game_starting_header_item_vouchers, null);
        LinearLayout mLlGameInfo = itemView.findViewById(R.id.ll_game_info);
        ImageView mIvGameIcon = itemView.findViewById(R.id.iv_game_icon);
        TextView mTvGameName = itemView.findViewById(R.id.tv_game_name);
        TextView mTvGameSuffix = itemView.findViewById(R.id.tv_game_suffix);
        LinearLayout mLlGameTagContainer = itemView.findViewById(R.id.ll_game_tag_container);
        LinearLayout mLlVoucherContainer = itemView.findViewById(R.id.ll_voucher_container);
        TextView mTvGamePlayCount = itemView.findViewById(R.id.tv_game_play_count);
        TextView mTvGameOnlineTime = itemView.findViewById(R.id.tv_game_online_time);

        mLlGameTagContainer.removeAllViews();

        GameInfoVo gameInfoVo = item.getGameinfo();

        if (!TextUtils.isEmpty(gameInfoVo.getOtherGameName())){//游戏后缀
            mTvGameSuffix.setVisibility(View.VISIBLE);
            mTvGameSuffix.setText(gameInfoVo.getOtherGameName());
        }else {
            mTvGameSuffix.setVisibility(View.GONE);
        }

        int game_id = 0;
        if (gameInfoVo != null) {
            GlideUtils.loadRoundImage(mContext, gameInfoVo.getGameicon(), mIvGameIcon);
            game_id = gameInfoVo.getGameid();
            mTvGameName.setText(gameInfoVo.getGamename());
            for (int i = 0; i < gameInfoVo.getGame_labels().size(); i++) {
                View targetView;
                targetView = createTagView(gameInfoVo.getGame_labels().get(i));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, (int) (6 * ScreenUtil.getScreenDensity(mContext)), 0);
                mLlGameTagContainer.addView(targetView, params);
            }

            mTvGamePlayCount.setText(CommonUtils.formatNumberType2(gameInfoVo.getPlay_count()) + "人在玩");
            mTvGameOnlineTime.setText(CommonUtils.formatTimeStamp(gameInfoVo.getAppointment_begintime() * 1000, "MM月dd日上线"));

            mLlGameInfo.setOnClickListener(view -> {
                if (_mFragment != null) {
                    _mFragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
                }
            });
        }

        List<GameCouponsListVo.CouponVo> couponVoList = item.getCoupon_list();
        mLlVoucherContainer.removeAllViews();
        if (couponVoList != null && !couponVoList.isEmpty()) {
            int count = couponVoList.size();
            for (int i = 0; i < count; i++) {
                View itemVoucherView = createVoucherView(couponVoList.get(i), game_id);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, i % 2 == 0 ? (int) (6 * ScreenUtil.getScreenDensity(mContext)) : 0, 0);
                mLlVoucherContainer.addView(itemVoucherView, params);
            }
        }

        return itemView;
    }

    private View createVoucherView(GameCouponsListVo.CouponVo item, int game_id) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_new_game_vouchers, null);

        TextView mTvCountVoucher = itemView.findViewById(R.id.tv_count_voucher);
        TextView mTvCountCondition = itemView.findViewById(R.id.tv_count_condition);
        TextView mTvCountLeft = itemView.findViewById(R.id.tv_count_left);
        TextView mTvDrawVoucher = itemView.findViewById(R.id.tv_draw_voucher);


        mTvCountVoucher.setText(String.valueOf(item.getAmount()));
        mTvCountLeft.setText(item.getRemain_days());
        mTvCountCondition.setText(item.getCoupon_name());

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        gd.setColor(ContextCompat.getColor(mContext, R.color.white));
        mTvDrawVoucher.setBackground(gd);

        if (item.getStatus() == 1) {
            itemView.setBackgroundResource(R.mipmap.img_new_game_voucher_normal);
            mTvDrawVoucher.setTextColor(ContextCompat.getColor(mContext, R.color.color_f64e4e));
            mTvDrawVoucher.setText("立即\n领取");
            mTvDrawVoucher.setEnabled(true);
        } else if (item.getStatus() == 10) {
            itemView.setBackgroundResource(R.mipmap.img_new_game_voucher_selected);
            mTvDrawVoucher.setTextColor(ContextCompat.getColor(mContext, R.color.color_9b9b9b));
            mTvDrawVoucher.setText("已领取");
            mTvDrawVoucher.setEnabled(false);
        } else if (item.getStatus() == -1) {
            itemView.setBackgroundResource(R.mipmap.img_new_game_voucher_selected);
            mTvDrawVoucher.setTextColor(ContextCompat.getColor(mContext, R.color.color_9b9b9b));
            mTvDrawVoucher.setText("已领完");
            mTvDrawVoucher.setEnabled(false);
        }

        mTvDrawVoucher.setOnClickListener(view -> {
            //领取代金券
            if (_mSubFragment != null && _mSubFragment instanceof NewGameStartingFragment) {
                ((NewGameStartingFragment) _mSubFragment).getCoupon(item.getCoupon_id(), game_id);
            }
        });
        return itemView;
    }

    //GameInfoVo.GameLabelsBean labelsBean
    private View createTagView(GameInfoVo.GameLabelsBean label) {
        TextView tagBtn = new TextView(mContext);
        tagBtn.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        tagBtn.setIncludeFontPadding(false);
        tagBtn.setGravity(Gravity.CENTER);
        tagBtn.setText(label.getLabel_name());
        tagBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_232323));

        float density = ScreenUtil.getScreenDensity(mContext);

        GradientDrawable gd = new GradientDrawable();
        float radius = 4 * density;
        gd.setStroke((int) (1 * density), ContextCompat.getColor(mContext, R.color.color_9b9b9b));
        gd.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, radius, radius});
        tagBtn.setPadding((int) (6 * density), (int) (2 * density), (int) (6 * density), (int) (2 * density));
        tagBtn.setBackground(gd);
        tagBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (20 * density));
        tagBtn.setLayoutParams(params);
        return tagBtn;
    }

    public class ViewHolder extends AbsHolder {


        private TextView     mTvTitle;
        private LinearLayout mLlContainer;

        public ViewHolder(View view) {
            super(view);


            mTvTitle = findViewById(R.id.tv_title);
            mLlContainer = findViewById(R.id.ll_container);


        }
    }
}
