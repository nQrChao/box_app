package com.zqhy.app.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.core.data.model.message.TabMessageVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/13
 */

public class MessageAdapter extends AbsAdapter<TabMessageVo> {


    private float density;

    public MessageAdapter(Context context, List<TabMessageVo> labels) {
        super(context, labels);
        density = ScreenUtil.getScreenDensity(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_message_main;
    }

    @Override
    public AbsViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, TabMessageVo data, int position) {
        TabMessageVo tabMessageVo = data;
        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.mIvMessageType.setImageResource(tabMessageVo.getIconRes());

        viewHolder.mTvMessageTitle.setText(tabMessageVo.getTitle());
        viewHolder.mTvMessageSubTitle.setText(tabMessageVo.getSubTitle());

        int unReadCount = tabMessageVo.getUnReadCount();

        int isShowUnReadCount = tabMessageVo.getIsShowUnReadCount();

        if (isShowUnReadCount == 1) {
            viewHolder.mFlLookOut.setVisibility(View.GONE);
            if (unReadCount > 0) {
                viewHolder.mTvMessageCount.setVisibility(View.VISIBLE);

                GradientDrawable gd = new GradientDrawable();
                gd.setColor(mContext.getResources().getColor(R.color.color_ff4949));
                gd.setCornerRadius(18 * density);

                viewHolder.mTvMessageCount.setBackground(gd);
                viewHolder.mTvMessageCount.setTextColor(mContext.getResources().getColor(R.color.white));
                viewHolder.mTvMessageCount.setGravity(Gravity.CENTER);
                if (unReadCount > 99) {
                    viewHolder.mTvMessageCount.setText("99+");
                } else {
                    viewHolder.mTvMessageCount.setText(String.valueOf(tabMessageVo.getUnReadCount()));
                }
            } else {
                viewHolder.mTvMessageCount.setVisibility(View.GONE);
            }
        } else {
            viewHolder.mFlLookOut.setVisibility(View.VISIBLE);
            viewHolder.mTvMessageCount.setVisibility(View.GONE);

            viewHolder.mViewUnread.setVisibility(unReadCount == 0 ? View.GONE : View.VISIBLE);
        }
    }

    public class ViewHolder extends AbsAdapter.AbsViewHolder {
        private LinearLayout mLlItemView;
        private ImageView mIvMessageType;
        private TextView mTvMessageTitle;
        private TextView mTvMessageSubTitle;
        private TextView mTvMessageCount;
        private FrameLayout mFlLookOut;
        private View mViewUnread;

        public ViewHolder(View itemView) {
            super(itemView);
            mLlItemView = itemView.findViewById(R.id.ll_item_view);
            mIvMessageType = itemView.findViewById(R.id.iv_message_type);
            mTvMessageTitle = itemView.findViewById(R.id.tv_message_title);
            mTvMessageSubTitle = itemView.findViewById(R.id.tv_message_sub_title);
            mTvMessageCount = itemView.findViewById(R.id.tv_message_count);
            mFlLookOut = itemView.findViewById(R.id.fl_look_out);
            mViewUnread = itemView.findViewById(R.id.view_unread);

        }
    }

}
