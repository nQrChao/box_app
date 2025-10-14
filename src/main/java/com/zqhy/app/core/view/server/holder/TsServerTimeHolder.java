package com.zqhy.app.core.view.server.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.ServerTimeVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author pc
 * @date 2019/12/3-10:19
 * @description
 */
public class TsServerTimeHolder extends AbsItemHolder<ServerTimeVo, TsServerTimeHolder.ViewHolder> {
    public TsServerTimeHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_ts_gameinfo_server_time;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ServerTimeVo item) {
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        if (item.getIndexTimer() == 1) {
            params.topMargin = (int) (10 * ScreenUtil.getScreenDensity(mContext));
        }
        holder.mLayout.setLayoutParams(params);
        try {
            boolean isToday = CommonUtils.isTodayOrTomorrow(item.getTime() * 1000) == 0;
            holder.mTvServerTime.setText(CommonUtils.formatTimeStamp(item.getTime() * 1000, isToday ? "今日 | HH:mm" : "MM-dd | HH:mm"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ViewHolder extends AbsHolder {
        private RelativeLayout mLayout;
        private TextView       mTvServerTime;

        public ViewHolder(View view) {
            super(view);
            mLayout = findViewById(R.id.layout);
            mTvServerTime = findViewById(R.id.tv_server_time);
        }
    }
}
