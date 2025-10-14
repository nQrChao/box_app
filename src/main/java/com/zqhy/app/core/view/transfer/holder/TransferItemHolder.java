package com.zqhy.app.core.view.transfer.holder;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.transfer.TransferGameItemVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.transfer.TransferGameListFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class TransferItemHolder extends AbsItemHolder<TransferGameItemVo.TransferRewardVo, TransferItemHolder.ViewHolder> {

    private float density;

    public TransferItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TransferGameItemVo.TransferRewardVo item) {
        GradientDrawable gdRoot = new GradientDrawable();

        gdRoot.setColor(ContextCompat.getColor(mContext, R.color.color_f5f5f5));
        gdRoot.setStroke((int) (density * 1), ContextCompat.getColor(mContext, R.color.color_cccccc));
        gdRoot.setCornerRadius(5 * density);
        holder.mRootView.setBackground(gdRoot);


        int rewardAble = item.getRewark_able();

        GradientDrawable gd = new GradientDrawable();
        if (rewardAble == 1) {
            gd.setStroke((int) (density * 1), ContextCompat.getColor(mContext, R.color.color_ff8f19));
            gd.setColor(ContextCompat.getColor(mContext, R.color.white));
            holder.mTvTransferApply.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
        } else {
            gd.setStroke(0, ContextCompat.getColor(mContext, R.color.color_ff8f19));
            gd.setColor(ContextCompat.getColor(mContext, R.color.color_cccccc));
            holder.mTvTransferApply.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        }
        gd.setCornerRadius(12 * density);
        holder.mTvTransferApply.setBackground(gd);

        holder.mTvCostPoints.setText("消耗点数：" + item.getC1());

        if (!TextUtils.isEmpty(item.getC2_more())) {
            holder.mTvCostPoints2.setVisibility(View.VISIBLE);
            holder.mTvCostPoints2.setText(item.getC2_more());
        }else{
            holder.mTvCostPoints2.setVisibility(View.INVISIBLE);
        }

        holder.mTvSubTxt1.setText("奖励：" + item.getReward_content());
        holder.mTvSubTxt2.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(item.getEx_more())) {
            holder.mTvSubTxt2.setText("要求：" + item.getEx_more());
        } else {
            holder.mTvSubTxt2.setVisibility(View.GONE);
        }

        holder.mTvTransferApply.setOnClickListener(view -> {
            if (_mFragment != null && _mFragment instanceof TransferGameListFragment) {
                if (item.getRewark_able() == 1) {
                    ((TransferGameListFragment) _mFragment).transferApply(item);
                } else {
                    Toaster.show("暂未达到申请条件");
                }
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_transfer_list;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mRootView;
        private TextView mTvCostPoints;
        private TextView mTvCostPoints2;
        private TextView mTvTransferApply;
        private TextView mTvSubTxt1;
        private TextView mTvSubTxt2;

        public ViewHolder(View view) {
            super(view);
            mRootView = itemView.findViewById(R.id.rootView);
            mTvCostPoints = itemView.findViewById(R.id.tv_cost_points);
            mTvCostPoints2 = itemView.findViewById(R.id.tv_cost_points_2);
            mTvTransferApply = itemView.findViewById(R.id.tv_transfer_apply);
            mTvSubTxt1 = itemView.findViewById(R.id.tv_sub_txt_1);
            mTvSubTxt2 = itemView.findViewById(R.id.tv_sub_txt_2);

        }
    }
}
