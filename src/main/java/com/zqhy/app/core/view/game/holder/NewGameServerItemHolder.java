package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author leeham2734
 * @date 2020/5/9-15:09
 * @description
 */
public class NewGameServerItemHolder extends AbsItemHolder<GameInfoVo.ServerListBean, NewGameServerItemHolder.ViewHolder> {

    public NewGameServerItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_server;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameInfoVo.ServerListBean item) {
        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        if (holder.getLayoutPosition() % 2 == 0){
            layoutParams.setMargins(ScreenUtil.dp2px(mContext, 20f), ScreenUtil.dp2px(mContext, 10f), ScreenUtil.dp2px(mContext, 5f), 0);
        }else{
            layoutParams.setMargins(ScreenUtil.dp2px(mContext, 5f), ScreenUtil.dp2px(mContext, 10f), ScreenUtil.dp2px(mContext, 20f), 0);
        }
        holder.itemView.setLayoutParams(layoutParams);
        if ("000".equals(item.getServerid())){
            holder.mTvTime.setText("动态开服");
            holder.mTvName.setText("请以游戏内实际开服为准");
        }else {
            long ms = item.getBegintime() * 1000;
            holder.mTvTime.setText(CommonUtils.friendlyTime2(ms));
            holder.mTvName.setText(item.getServername());
            try {
                /*if (item.isTheNewest()){
                    holder.mTvTime.setTextColor(Color.parseColor("#FF6A36"));
                    holder.mTvName.setTextColor(Color.parseColor("#FF6A36"));
                }else {
                    //非今天
                    holder.mTvTime.setTextColor(Color.parseColor("#333333"));
                    holder.mTvName.setTextColor(Color.parseColor("#9B9B9B"));
                }*/
                if(CommonUtils.isTodayOrTomorrow(item.getBegintime() * 1000) == 0){
                    holder.mTvTime.setTextColor(Color.parseColor("#5571FE"));
                    holder.mTvName.setTextColor(Color.parseColor("#5571FE"));
                }else {
                    //非今天
                    holder.mTvTime.setTextColor(Color.parseColor("#333333"));
                    holder.mTvName.setTextColor(Color.parseColor("#9B9B9B"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class ViewHolder extends AbsHolder {

        private TextView mTvTime;
        private TextView mTvName;

        public ViewHolder(View view) {
            super(view);
            mTvTime = findViewById(R.id.tv_time);
            mTvName = findViewById(R.id.tv_name);
        }
    }
}
