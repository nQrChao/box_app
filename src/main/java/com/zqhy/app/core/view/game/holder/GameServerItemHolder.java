package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.adapter.abs.EmptyAdapter;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.detail.GameServerListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/20
 */

public class GameServerItemHolder extends AbsItemHolder<GameServerListVo, GameServerItemHolder.ViewHolder> {
    public GameServerItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameServerListVo item) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setSmoothScrollbarEnabled(true);
        holder.mRecyclerViewServer.setNestedScrollingEnabled(false);
        holder.mRecyclerViewServer.setLayoutManager(layoutManager);
        if (item != null && item.getServerlist() != null && !item.getServerlist().isEmpty()) {
            holder.mRecyclerViewServer.setAdapter(new ServerListAdapter(mContext, item.getServerlist()));
        } else {
            List<EmptyDataVo> emptyDataVoList = new ArrayList<>();
            emptyDataVoList.add(new EmptyDataVo(R.mipmap.img_empty_data_2));
            holder.mRecyclerViewServer.setAdapter(new EmptyAdapter(mContext, emptyDataVoList));
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_detail_server;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    private BaseRecyclerAdapter mAdapter;

    public class ViewHolder extends AbsHolder {

        private LinearLayout mLlListServer;
        private RecyclerView mRecyclerViewServer;
        private ImageView    mIvNoDataServer;
        private LinearLayout mLlServerMore;
        private TextView     mTvServerMoreTextAction;
        private ImageView    mIvServerMoreTextAction;

        public ViewHolder(View view) {
            super(view);
            mLlListServer = findViewById(R.id.ll_list_server);
            mRecyclerViewServer = findViewById(R.id.recyclerView_server);
            mIvNoDataServer = findViewById(R.id.iv_no_data_server);
            mLlServerMore = findViewById(R.id.ll_server_more);
            mTvServerMoreTextAction = findViewById(R.id.tv_server_more_text_action);
            mIvServerMoreTextAction = findViewById(R.id.iv_server_more_text_action);

            mLlServerMore.setVisibility(View.GONE);
        }
    }


    class ServerListAdapter extends AbsAdapter<GameInfoVo.ServerListBean> {

        public ServerListAdapter(Context context, List<GameInfoVo.ServerListBean> labels) {
            super(context, labels);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, GameInfoVo.ServerListBean item, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.mTvServer.setText(item.getServername());
            try {
                long ms = item.getBegintime() * 1000;
                float density = ScreenUtil.getScreenDensity(mContext);
                if (CommonUtils.isTodayOrTomorrow(ms) != 0) {
                    //非今天
                    viewHolder.mTvTime.setText(CommonUtils.formatTimeStamp(ms, "MM-dd HH:mm"));

                    viewHolder.mViewTag.setBackgroundResource(R.drawable.drawable_game_detail_server_2_concentric_circles);
                    viewHolder.mTvTime.setTextColor(ContextCompat.getColor(mContext, R.color.color_666666));
                    viewHolder.mTvServer.setTextColor(ContextCompat.getColor(mContext, R.color.color_666666));

                    GradientDrawable gd = new GradientDrawable();
                    gd.setCornerRadius(5 * density);
                    gd.setStroke((int) (1 * density), ContextCompat.getColor(mContext, R.color.color_868686));
                    viewHolder.mTvServer.setBackground(gd);
                } else {
                    viewHolder.mTvTime.setText(CommonUtils.formatTimeStamp(ms, "MM-dd HH:mm    今日"));
                    //今天
                    viewHolder.mViewTag.setBackgroundResource(R.drawable.drawable_game_detail_server_concentric_circles);
                    viewHolder.mTvTime.setTextColor(ContextCompat.getColor(mContext, R.color.color_3478f6));
                    viewHolder.mTvServer.setTextColor(ContextCompat.getColor(mContext, R.color.color_3478f6));

                    GradientDrawable gd = new GradientDrawable();
                    gd.setCornerRadius(5 * density);
                    gd.setStroke((int) (1 * density), ContextCompat.getColor(mContext, R.color.color_3478f6));
                    viewHolder.mTvServer.setBackground(gd);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_game_list_server;
        }

        @Override
        public AbsViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }

        class ViewHolder extends AbsAdapter.AbsViewHolder {

            private View     mViewTag;
            private TextView mTvTime;
            private TextView mTvServer;

            public ViewHolder(View view) {
                super(view);

                mViewTag = findViewById(R.id.view_tag);
                mTvTime = findViewById(R.id.tv_time);
                mTvServer = findViewById(R.id.tv_server);

            }
        }
    }
}
