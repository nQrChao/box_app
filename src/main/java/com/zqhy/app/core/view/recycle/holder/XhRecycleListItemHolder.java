package com.zqhy.app.core.view.recycle.holder;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.recycle.XhGameRecycleVo;
import com.zqhy.app.core.data.model.recycle.XhRecycleItemVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.recycle.XhRecycleMainFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class XhRecycleListItemHolder extends AbsItemHolder<XhGameRecycleVo, XhRecycleListItemHolder.ViewHolder> {

    private float density;

    public XhRecycleListItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_xh_recycle;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull XhGameRecycleVo item) {
        holder.mLlListXhAccount.removeAllViews();

        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mIvGameIcon);
        holder.mTvGameName.setText(item.getGamename());
        holder.mTvXhRecycleRate.setText("回收比例：" + item.getRecycle_ratio());

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(ContextCompat.getColor(mContext, R.color.color_fff8f1));
        float radius = density * 5;
        gd.setCornerRadii(new float[]{0, 0, radius, radius, radius, radius, 0, 0});
        holder.mLlXhRecycleTop.setBackground(gd);

        if (item.getXh_list() != null) {
            for (int i = 0; i < item.getXh_list().size(); i++) {
                holder.mLlListXhAccount.addView(createXhAccountView(item, item.getXh_list().get(i)));
            }
        }
    }

    private View createXhAccountView(XhGameRecycleVo item, XhRecycleItemVo xhRecycleItemVo) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_game_xh_recycle_list, null);
        TextView mTvXhAccount = view.findViewById(R.id.tv_xh_account);
        TextView mTvXhRecharge = view.findViewById(R.id.tv_xh_recharge);
        TextView mTvXhRecyclePrice = view.findViewById(R.id.tv_xh_recycle_price);

        mTvXhAccount.setText(xhRecycleItemVo.getXh_showname());
        mTvXhRecharge.setText("实际充值：" + xhRecycleItemVo.getRmb_total() + "元");
        mTvXhRecyclePrice.setText(xhRecycleItemVo.getRecycle_gold());
        view.setOnClickListener(v -> {
            if (_mFragment != null && _mFragment instanceof XhRecycleMainFragment) {
                ((XhRecycleMainFragment) _mFragment).recycleXh(item, xhRecycleItemVo);
            }
        });
        return view;
    }

    public class ViewHolder extends AbsHolder {
        private ImageView mIvGameIcon;
        private TextView mTvGameName;
        private TextView mTvXhRecycleRate;
        private LinearLayout mLlListXhAccount;
        private RelativeLayout mLlXhRecycleTop;

        public ViewHolder(View view) {
            super(view);
            mIvGameIcon = findViewById(R.id.iv_game_icon);
            mTvGameName = findViewById(R.id.tv_game_name);
            mTvXhRecycleRate = findViewById(R.id.tv_xh_recycle_rate);
            mLlListXhAccount = findViewById(R.id.ll_list_xh_account);
            mLlXhRecycleTop = findViewById(R.id.ll_xh_recycle_top);

        }
    }
}
