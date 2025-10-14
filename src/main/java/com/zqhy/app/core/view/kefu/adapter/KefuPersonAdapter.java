package com.zqhy.app.core.view.kefu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.adapter.abs.AbsChooseAdapter;
import com.zqhy.app.core.data.model.kefu.KefuPersionListVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/22
 */

public class KefuPersonAdapter extends AbsChooseAdapter<KefuPersionListVo.DataBean> {

    private float density;

    public KefuPersonAdapter(Context context, List<KefuPersionListVo.DataBean> labels) {
        super(context, labels);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, KefuPersionListVo.DataBean item, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;

        holder.mTvKefuName.setText(item.getKf_name());


        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(4 * density);
        if (item.getIs_opinion() == 1) {
            //已评价
            holder.mLlRootview.setEnabled(false);
            holder.mTvGraded.setVisibility(View.VISIBLE);

            gd.setColor(Color.parseColor("#F8F8F8"));
            gd.setStroke((int) (0.8 * density), Color.parseColor("#DDDDDD"));

            GradientDrawable gd2 = new GradientDrawable();
            gd2.setColor(Color.parseColor("#FF8F19"));
            gd2.setCornerRadius(16 * density);
            holder.mTvGraded.setBackground(gd2);

            holder.mIvKefu.setImageResource(R.mipmap.ic_kefu_item_2);
            holder.mTvKefuName.setTextColor(ContextCompat.getColor(mContext, R.color.color_cccccc));
        } else {
            //未评价
            holder.mTvGraded.setVisibility(View.GONE);
            holder.mLlRootview.setEnabled(true);

            holder.mIvKefu.setImageResource(R.mipmap.ic_kefu_item_1);

            if (mSelectedItem == position) {
                //选中
                gd.setColor(Color.parseColor("#FFFAF6"));
                gd.setStroke((int) (0.8 * density), Color.parseColor("#FF8F19"));
                holder.mTvKefuName.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
            } else {
                //未选中
                gd.setColor(Color.parseColor("#FFFFFF"));
                gd.setStroke((int) (0.8 * density), Color.parseColor("#DDDDDD"));
                holder.mTvKefuName.setTextColor(ContextCompat.getColor(mContext, R.color.color_1b1b1b));
            }
        }
        holder.mLlRootview.setBackground(gd);

    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_kefu_person;
    }

    @Override
    public AbsViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    public class ViewHolder extends AbsViewHolder {

        private LinearLayout mLlRootview;
        private ImageView mIvKefu;
        private TextView mTvGraded;
        private TextView mTvKefuName;

        public ViewHolder(View view) {

            super(view);
            mLlRootview = findViewById(R.id.ll_rootview);
            mIvKefu = findViewById(R.id.iv_kefu);
            mTvGraded = findViewById(R.id.tv_graded);
            mTvKefuName = findViewById(R.id.tv_kefu_name);

        }
    }
}
