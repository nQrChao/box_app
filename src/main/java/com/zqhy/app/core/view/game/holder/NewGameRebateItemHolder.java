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
import com.zqhy.app.core.data.model.game.detail.GameRebateVo;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.rebate.RebateMainFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/20
 */

public class NewGameRebateItemHolder extends AbsItemHolder<GameRebateVo, NewGameRebateItemHolder.ViewHolder> {

    public NewGameRebateItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameRebateVo item) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(item.getRebate_flash_content())) {
            sb.append("<p>■☆【限时活动】☆■（此活动不与其他活动叠加参与）</p>");
            sb.append(item.getRebate_flash_content());
            sb.append("<p>——↑↑↑以上为限时活动全部内容↑↑↑——</p>");
            sb.append("<p>——————————————————————————</p>");
        }
        if (!TextUtils.isEmpty(item.getRebate_content())) {
            sb.append(item.getRebate_content());
        }
        String strBTWelfare = sb.toString();
        if (!TextUtils.isEmpty(strBTWelfare)) {
            holder.mTvContent.setText(Html.fromHtml(strBTWelfare));
        }
        holder.mTvContent.setMaxLines(Integer.MAX_VALUE);
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

        if (item.getMax_rate() > 0) {
            holder.mTvTips.setVisibility(View.GONE);
            holder.mTvApply.setText("申请返利 >");
            holder.mTvType.setText("手动返利");
            holder.mTvApply.setOnClickListener(v -> {
                //申请返利
                if (_mFragment != null) {
                    if (_mFragment.checkLogin()) {
                        _mFragment.start(new RebateMainFragment());
                    }
                }
            });
        } else {
            holder.mTvTips.setVisibility(View.VISIBLE);
            holder.mTvApply.setText("");
            holder.mTvType.setText("自动返利");
            holder.mTvApply.setOnClickListener(view -> {
                showRebateDialog();
            });
        }
        holder.mIvTips.setOnClickListener(v -> {
            showRebateDialog();
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_detail_rebate_new;
    }


    private void showRebateDialog() {
        CustomDialog dialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_game_detail_rebate, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        dialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }


    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private TextView mTvApply;
        private TextView mTvTips;
        private TextView mTvContent;
        private TextView mTvFold;
        private TextView mTvType;
        private ImageView mIvTips;

        public ViewHolder(View view) {
            super(view);
            mTvApply = view.findViewById(R.id.tv_apply);
            mTvTips = view.findViewById(R.id.tv_tips);
            mTvContent = view.findViewById(R.id.tv_content);
            mTvFold = view.findViewById(R.id.tv_fold);
            mTvType = view.findViewById(R.id.tv_type);
            mIvTips = view.findViewById(R.id.iv_tips);
        }
    }

}
