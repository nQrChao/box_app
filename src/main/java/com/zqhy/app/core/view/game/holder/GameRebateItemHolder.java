package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.detail.GameRebateVo;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.rebate.RebateMainFragment;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.expand.ExpandTextView;

/**
 * @author Administrator
 * @date 2018/11/20
 */

public class GameRebateItemHolder extends AbsItemHolder<GameRebateVo, GameRebateItemHolder.ViewHolder> {

    public GameRebateItemHolder(Context context) {
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
            holder.mEtv.setContent(Html.fromHtml(strBTWelfare));
            holder.mEtv.setTitleVisibility(View.GONE);
        }

        holder.mTvTip.setOnClickListener(view -> {
            showRebateDialog();
        });

        if (item.getMax_rate() > 0) {
            holder.mTvTip.setVisibility(View.GONE);
            holder.mTvApplyRebate.setText("申请返利");
            holder.mTvApplyRebate.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_game_detail_rebate_text), null, null, null);
            holder.mTvApplyRebate.setOnClickListener(v -> {
                //申请返利
                if (_mFragment != null) {
                    if (_mFragment.checkLogin()) {
                        _mFragment.start(new RebateMainFragment());
                    }
                }
            });
        } else {
            holder.mTvTip.setVisibility(View.VISIBLE);
            holder.mTvApplyRebate.setText("自动返利");
            holder.mTvApplyRebate.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_game_detail_rebate_auto_text), null, null, null);
            holder.mTvApplyRebate.setOnClickListener(view -> {
                showRebateDialog();
            });
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_detail_rebate;
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
        private TextView       mTvApplyRebate;
        private ExpandTextView mEtv;
        private TextView       mTvTip;

        public ViewHolder(View view) {
            super(view);
            mTvApplyRebate = findViewById(R.id.tv_apply_rebate);
            mEtv = findViewById(R.id.etv_2);
            mTvTip = findViewById(R.id.tv_tip);

        }
    }

}
