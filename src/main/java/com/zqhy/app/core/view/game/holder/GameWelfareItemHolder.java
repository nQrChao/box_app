package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.detail.GameWelfareVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/20
 */

public class GameWelfareItemHolder extends AbsItemHolder<GameWelfareVo, GameWelfareItemHolder.ViewHolder> {

    float density;

    public GameWelfareItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameWelfareVo item) {
        String strBTWelfare = item.getBenefit_content();
        if (!TextUtils.isEmpty(strBTWelfare)) {
            holder.mTvContent.setText(Html.fromHtml(strBTWelfare));
            holder.mTvContent.post(() -> {
                if (holder.mTvContent.getLineCount() > 6){
                    holder.mTvContent.setMaxLines(6);
                    holder.mTvFold.setVisibility(View.VISIBLE);
                }else {
                    holder.mTvContent.setMaxLines(6);
                    holder.mTvFold.setVisibility(View.GONE);
                }
                holder.mTvFold.setOnClickListener(view -> {
                    if (holder.mTvContent.getLineCount() == 6){
                        holder.mTvContent.setMaxLines(Integer.MAX_VALUE);
                        holder.mTvFold.setText("收起");
                        holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_rebate_arrow_up), null);
                    }else{
                        holder.mTvContent.setMaxLines(6);
                        holder.mTvFold.setText("展开");
                        holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_rebate_arrow_down), null);
                    }
                });
            });
        }else{
            holder.mTvContent.setVisibility(View.GONE);
            holder.mTvFold.setVisibility(View.GONE);
        }

        holder.mTvCouponCount.setOnClickListener(v -> {
            if (_mFragment != null) {
                ((GameDetailInfoFragment) _mFragment).changeTab(1);
            }
        });

        if (item.getGame_model() == 1){//横屏
            holder.mTvGameModel.setVisibility(View.VISIBLE);
            holder.mTvGameModel.setText("横屏游戏");
        }else if (item.getGame_model() == 2){//竖屏
            holder.mTvGameModel.setVisibility(View.VISIBLE);
            holder.mTvGameModel.setText("竖屏游戏");
        }else {
            holder.mTvGameModel.setVisibility(View.GONE);
        }

        if ("1".equals(item.getData_exchange())){
            holder.mTvType.setVisibility(View.VISIBLE);
            holder.mIvTips.setVisibility(View.VISIBLE);
            holder.mTvType.setText("双端互通");
        }else if ("2".equals(item.getData_exchange())){
            holder.mTvType.setVisibility(View.VISIBLE);
            holder.mIvTips.setVisibility(View.VISIBLE);
            holder.mTvType.setText("双端不互通");
        }else {
            holder.mTvType.setVisibility(View.GONE);
            holder.mIvTips.setVisibility(View.GONE);
        }

        holder.mIvTips.setOnClickListener(v -> {
            showWelfareDialog(item.getData_exchange());
        });
        holder.mTvType.setOnClickListener(v -> {
            showWelfareDialog(item.getData_exchange());
        });
    }

    private void showWelfareDialog(String data_exchange) {
        CustomDialog dialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_game_detail_welfare_tips, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        if ("1".equals(data_exchange)){
            ((TextView) dialog.findViewById(R.id.tv_content)).setText("安卓端和苹果端角色数据互通。你使用安卓设备或苹果设备玩的角色是相同的。");
        }else if ("2".equals(data_exchange)){
            ((TextView) dialog.findViewById(R.id.tv_content)).setText("安卓端和苹果端角色数据不互通。例如，你使用安卓设备只能获取安卓角色数据，无法获取苹果角色数据。");
        }
        dialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_detail_welfare;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private TextView       mTvCouponCount;
        private TextView       mTvContent;
        private TextView       mTvFold;
        private TextView mTvGameModel;
        private TextView mTvType;
        private ImageView mIvTips;

        public ViewHolder(View view) {
            super(view);
            mTvCouponCount = findViewById(R.id.tv_coupon_count);
            mTvContent = findViewById(R.id.tv_content);
            mTvFold = findViewById(R.id.tv_fold);
            mTvGameModel = view.findViewById(R.id.tv_game_model);
            mTvType = view.findViewById(R.id.tv_type);
            mIvTips = view.findViewById(R.id.iv_tips);
        }
    }
}
