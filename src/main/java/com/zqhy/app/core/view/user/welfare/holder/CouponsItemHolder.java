package com.zqhy.app.core.view.user.welfare.holder;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.welfare.MyCouponsListVo;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.user.welfare.MyCouponsListFragment;
import com.zqhy.app.newproject.R;

import java.text.DecimalFormat;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class CouponsItemHolder extends AbsItemHolder<MyCouponsListVo.DataBean, CouponsItemHolder.ViewHolder> {

    public CouponsItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull CouponsItemHolder.ViewHolder holder, @NonNull MyCouponsListVo.DataBean item) {
        String couponListType = ((MyCouponsListFragment) _mFragment).couponListType;
        if ("game".equals(couponListType)){
            holder.mLlLeftBg.setBackgroundResource(R.mipmap.ic_game_detail_coupon_list_item_other);
            holder.mIvTipsHistory.setVisibility(View.GONE);
            if (item.getSign() == 1){//判断是不是vip券
                holder.mTvTipsVip.setVisibility(View.VISIBLE);
                holder.mTvTipsVip.setBackgroundResource(R.drawable.shape_ecbf77_f1d6a6_5_radius_1);
                holder.mLlLeftBg.setBackgroundResource(R.mipmap.ic_game_detail_coupon_list_item_vip);
                holder.mTvAmountType.setTextColor(Color.parseColor("#361702"));
                holder.mTvAmount.setTextColor(Color.parseColor("#361702"));
                holder.mTvAccount.setTextColor(Color.parseColor("#361702"));
            }else {
                holder.mTvTipsVip.setVisibility(View.GONE);
                holder.mLlLeftBg.setBackgroundResource(R.mipmap.ic_game_detail_coupon_list_item_other);
                holder.mTvAmountType.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mTvAmount.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mTvAccount.setTextColor(Color.parseColor("#FFFFFF"));
            }
        }else if ("platform".equals(couponListType)){
            holder.mLlLeftBg.setBackgroundResource(R.mipmap.ic_game_detail_coupon_list_item_other);
            holder.mIvTipsHistory.setVisibility(View.GONE);
            if (item.getSign() == 1){//判断是不是vip券
                holder.mTvTipsVip.setVisibility(View.VISIBLE);
                holder.mTvTipsVip.setBackgroundResource(R.drawable.shape_ecbf77_f1d6a6_5_radius_1);
                holder.mLlLeftBg.setBackgroundResource(R.mipmap.ic_game_detail_coupon_list_item_vip);
                holder.mTvAmountType.setTextColor(Color.parseColor("#361702"));
                holder.mTvAmount.setTextColor(Color.parseColor("#361702"));
                holder.mTvAccount.setTextColor(Color.parseColor("#361702"));
            }else {
                holder.mTvTipsVip.setVisibility(View.GONE);
                holder.mLlLeftBg.setBackgroundResource(R.mipmap.ic_game_detail_coupon_list_item_other);
                holder.mTvAmountType.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mTvAmount.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mTvAccount.setTextColor(Color.parseColor("#FFFFFF"));
            }
        }else if ("history".equals(couponListType)){
            if (item.getSign() == 1){//判断是不是vip券
                holder.mTvTipsVip.setVisibility(View.VISIBLE);
                holder.mTvTipsVip.setBackgroundResource(R.drawable.shape_9b9b9b_5_radius_1);
            }else{
                holder.mTvTipsVip.setVisibility(View.GONE);
            }
            holder.mLlLeftBg.setBackgroundResource(R.mipmap.ic_game_detail_coupon_list_item_history);
            holder.mIvTipsHistory.setVisibility(View.VISIBLE);
            if ("expired".equals(item.getBadge())){
                holder.mIvTipsHistory.setImageResource(R.mipmap.ic_coupon_list_history);
            }else if ("is_used".equals(item.getBadge())){
                holder.mIvTipsHistory.setImageResource(R.mipmap.ic_coupon_list_history_usered);
            }
        }

        if ("yes".equals(item.getExpiry_soon())){
            holder.mTvTips.setVisibility(View.VISIBLE);
        }else {
            holder.mTvTips.setVisibility(View.GONE);
        }
        holder.mTvStatus.setVisibility(View.GONE);

        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String format = decimalFormat.format(item.getAmount());
        String amount = (format.indexOf(".0") != -1)? format.substring(0, format.indexOf(".0")): format;
        holder.mTvAmount.setText(amount);

        holder.mTvAccount.setText(item.getUse_cdt());
        holder.mTvContent2.setText("有效截至: " + item.getExpiry());
        holder.mTvGameSuffix.setVisibility(View.GONE);
        if ("game".equals(couponListType)){
            holder.mTvContent1.setText("小号：" + item.getXh_showname());
            holder.mTvContent3.setText(item.getRange());
            holder.mTvContent3.setText("仅限：" + item.getRange());

            if (!TextUtils.isEmpty(item.getOtherRange())){//游戏后缀
                holder.mTvGameSuffix.setVisibility(View.VISIBLE);
                String str = item.getOtherRange() + " 可用";
                SpannableString ss = new SpannableString(str);
                ss.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), item.getOtherRange().length(), str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                holder.mTvGameSuffix.setText(ss);
            }else {
                holder.mTvGameSuffix.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(item.getXh_showname())){
                holder.mIvTips.setVisibility(View.VISIBLE);
                holder.mTvContent1.setText("小号：—");
            }else {
                holder.mIvTips.setVisibility(View.GONE);
            }
            holder.mTvContent3.setClickable(false);
        }else if ("platform".equals(couponListType)){
            holder.mIvTips.setVisibility(View.GONE);
            holder.mTvContent1.setText(item.getCoupon_name());
            if (item.getQuery_info() != null){
                holder.mTvContent3.setText("适用于：" +item.getRange() + " " + item.getQuery_info().getLabel());
                holder.mTvContent3.setOnClickListener(v -> {
                    if (_mFragment != null){
                        BrowserActivity.newInstance(_mFragment.getActivity(), item.getQuery_info().getUrl(),true);
                    }
                });
                holder.mTvContent3.setClickable(true);
            }else {
                holder.mTvContent3.setText("适用于：" +item.getRange());
                holder.mTvContent3.setClickable(false);
            }
        }else if ("history".equals(couponListType)){
            holder.mIvTips.setVisibility(View.GONE);
            if ("expired".equals(item.getBadge())){
                holder.mIvTipsHistory.setImageResource(R.mipmap.ic_coupon_list_history);
            }else if ("is_used".equals(item.getBadge())){
                holder.mIvTipsHistory.setImageResource(R.mipmap.ic_coupon_list_history_usered);
            }
            if (item.getGameid() > 0){//游戏券
                holder.mTvContent1.setText("小号：" + item.getXh_showname());
                holder.mTvContent3.setText(item.getRange());
                if (!TextUtils.isEmpty(item.getOtherRange())){//游戏后缀
                    holder.mTvGameSuffix.setVisibility(View.VISIBLE);
                    String str = item.getOtherRange() + " 可用";
                    SpannableString ss = new SpannableString(str);
                    ss.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), item.getOtherRange().length(), str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    holder.mTvGameSuffix.setText(ss);
                }else {
                    holder.mTvGameSuffix.setVisibility(View.GONE);
                }
                if (TextUtils.isEmpty(item.getXh_showname())){
                    holder.mIvTips.setVisibility(View.VISIBLE);
                    holder.mTvContent1.setText("小号：—");
                }else {
                    holder.mIvTips.setVisibility(View.GONE);
                }
                if ("expired".equals(item.getBadge())){//未使用
                    holder.mTvContent2.setText("有效截至: " + item.getExpiry());
                }else if ("is_used".equals(item.getBadge())) {//已使用
                    holder.mTvContent2.setText("使用时间: " + item.getUsed_time());
                }
            }else{//平台券
                holder.mIvTips.setVisibility(View.GONE);
                holder.mTvContent1.setText(item.getCoupon_name());
                if ("expired".equals(item.getBadge())){//未使用
                    holder.mTvContent2.setText("有效截至: " + item.getExpiry());
                    holder.mTvContent3.setText("适用于：" +item.getRange());
                }else if ("is_used".equals(item.getBadge())){//已使用
                    holder.mTvContent2.setText("使用时间：" + item.getUsed_time());
                    if (TextUtils.isEmpty(item.getXh_showname())){
                        holder.mTvContent3.setText("使用游戏：" + item.getUsed_game_name() + " " + item.getOtherUsed_game_name());
                    }else {
                        holder.mTvContent3.setText(item.getXh_showname() + "  " + item.getUsed_game_name() + " " + item.getOtherUsed_game_name());
                    }
                }
            }
            holder.mTvContent3.setClickable(false);
        }

        holder.itemView.setOnClickListener(view -> {
            showCouponsDialog(item);
        });
        holder.mIvTips.setOnClickListener(v -> {
            ((MyCouponsListFragment) _mFragment).showCommentTipsDialog();
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_coupon_list;
    }

    @Override
    public CouponsItemHolder.ViewHolder createViewHolder(View view) {
        return new CouponsItemHolder.ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlLeftBg;
        private TextView  mTvAmountType;
        private TextView mTvAmount;
        private TextView mTvAccount;
        private TextView mTvContent1;
        private TextView mTvContent2;
        private TextView mTvContent3;
        private TextView mTvStatus;
        private TextView mTvTips;
        private TextView  mTvTipsVip;
        private ImageView mIvTipsHistory;
        private ImageView mIvTips;
        private TextView mTvGameSuffix;

        public ViewHolder(View view) {
            super(view);
            mLlLeftBg = view.findViewById(R.id.ll_left_bg);
            mTvAmountType = view.findViewById(R.id.tv_amount_type);
            mTvAmount = view.findViewById(R.id.tv_amount);
            mTvAccount = view.findViewById(R.id.tv_account);
            mTvContent1 = view.findViewById(R.id.tv_content_1);
            mTvContent2 = view.findViewById(R.id.tv_content_2);
            mTvContent3 = view.findViewById(R.id.tv_content_3);
            mTvStatus = view.findViewById(R.id.tv_status);
            mTvTips = view.findViewById(R.id.tv_tips);
            mTvTipsVip = view.findViewById(R.id.tv_tips_vip);
            mIvTipsHistory = view.findViewById(R.id.iv_tips_history);

            mIvTips = view.findViewById(R.id.iv_tips);
            mTvGameSuffix = view.findViewById(R.id.tv_game_suffix);
        }
    }

    private void showCouponsDialog(MyCouponsListVo.DataBean couponListBean) {
        CustomDialog couponsDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_coupon_usage, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        TextView mTvDialogTitle = couponsDialog.findViewById(R.id.tv_dialog_title);
        TextView mTvAvailableRange = couponsDialog.findViewById(R.id.tv_available_range);
        TextView mTvValidityPeriod = couponsDialog.findViewById(R.id.tv_validity_period);
        TextView mTvGetIt = couponsDialog.findViewById(R.id.tv_get_it);
        TextView mTvGoDownload = couponsDialog.findViewById(R.id.tv_go_download);

        LinearLayout mLlUnEnableGames = couponsDialog.findViewById(R.id.ll_un_enable_games);
        TextView mTvUnEnableGames = couponsDialog.findViewById(R.id.tv_un_enable_games);

        View mViewMidLine = couponsDialog.findViewById(R.id.view_mid_line);

        TextView mTvUsage = couponsDialog.findViewById(R.id.tv_usage);

        mTvGetIt.setOnClickListener(v -> {
            if (couponsDialog != null && couponsDialog.isShowing()) {
                couponsDialog.dismiss();
            }
        });

        mTvDialogTitle.setText("使用方法");
        mTvValidityPeriod.setText(couponListBean.getExpiry());
        mTvUsage.setText(couponListBean.getUse_cdt());

        mTvGoDownload.setOnClickListener(v -> {
            if (couponsDialog != null && couponsDialog.isShowing()) {
                couponsDialog.dismiss();
            }
            if (_mFragment != null) {
                _mFragment.goGameDetail(couponListBean.getGameid(), couponListBean.getGame_type());
            }
        });


        if (couponListBean.getGameid() == 0) {
            mTvGoDownload.setVisibility(View.GONE);
            mViewMidLine.setVisibility(View.GONE);
            String range = couponListBean.getRange();
            String ss = range + "（限制游戏除外）";
            SpannableString spannableString = new SpannableString(ss);

            int startIndex = range.length() + 1;
            int endIndex = ss.length() - 1;

            spannableString.setSpan(new ClickableSpan() {

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(ContextCompat.getColor(mContext, R.color.color_ff0000));
                    super.updateDrawState(ds);
                }

                @Override
                public void onClick(View view) {
                    showUnEnableGamesDialog();
                }
            }, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_ff0000));
            spannableString.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            mTvAvailableRange.setMovementMethod(LinkMovementMethod.getInstance());
            mTvAvailableRange.setText(spannableString);
        } else {
            mTvGoDownload.setVisibility(View.VISIBLE);
            mViewMidLine.setVisibility(View.VISIBLE);
            mTvAvailableRange.setText(couponListBean.getRange());
        }

        couponsDialog.show();
    }

    private void showUnEnableGamesDialog() {
        CustomDialog unEnableGamesDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_un_enable_games, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        ImageView mIvClose = unEnableGamesDialog.findViewById(R.id.iv_close);
        TextView mTvTxt = unEnableGamesDialog.findViewById(R.id.tv_txt);

        if (_mFragment != null) {
            mTvTxt.setText(_mFragment.getAppNameByXML(R.string.string_un_limit_game_tips));
        }
        mIvClose.setOnClickListener(view -> {
            if (unEnableGamesDialog != null && unEnableGamesDialog.isShowing()) {
                unEnableGamesDialog.dismiss();
            }
        });
        unEnableGamesDialog.show();
    }
}
