package com.zqhy.app.core.view.server.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.ServerListVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideRoundTransform;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.TimeUtils;

/**
 * @author Administrator
 * @date 2018/11/8
 */

public class ServerListHolder extends AbsItemHolder<ServerListVo.DataBean, ServerListHolder.ViewHolder> {

    private float density;

    public ServerListHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    protected int gameNameMaxWidth = 194;

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ServerListVo.DataBean item) {
        holder.mTvGameName.setText(item.getGamename());
        holder.mTvGameType.setText(item.getGenre_str());
        holder.mTvRate.setText("1:" + item.getPayrate());

        int tagWidth = 0;

        long ms = item.getBegintime() * 1000;
        int day = TimeUtils.isTodayOrTomorrow(ms);
        if (day == 0) {
            holder.mTvTime.setText(TimeUtils.formatTimeStamp(ms, "今日-" + "HH:mm"));
        } else if (day == 1) {
            holder.mTvTime.setText(TimeUtils.formatTimeStamp(ms, "明日-" + "HH:mm"));
        } else {
            holder.mTvTime.setText(TimeUtils.formatTimeStamp(ms, "MM月dd日" +
                    "" +
                    "-HH:mm"));
        }
        holder.mTvServer.setText(item.getServername());
        GlideApp.with(mContext)
                .asBitmap()
                .load(item.getGameicon())
                .placeholder(R.mipmap.ic_placeholder)
                .transform(new GlideRoundTransform(mContext, 5))
                .centerCrop()
                .into(holder.mGameIconIV);
        holder.mTvDownload.setText("下载");

        int game_type = item.getGame_type();

        if (game_type == 1) {
            holder.mLlLayout1.setVisibility(View.GONE);
            holder.mLlLayout2.setVisibility(View.VISIBLE);
            holder.mLlLayout3.setVisibility(View.GONE);
            holder.mTvGameStarting.setVisibility(View.GONE);
        } else {
            holder.mLlLayout1.setVisibility(View.VISIBLE);
            holder.mLlLayout2.setVisibility(View.GONE);
            holder.mLlLayout3.setVisibility(View.GONE);
            holder.mTvGameStarting.setVisibility(View.VISIBLE);

            if (item.showDiscount() == 0) {
                holder.mTvTag.setVisibility(View.GONE);
            } else {
                holder.mTvTag.setVisibility(View.VISIBLE);
                holder.mTvTag.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.mTvTag.setPadding((int) (4 * density), (int) (1 * density), (int) (4 * density), (int) (1 * density));
                GradientDrawable gd2 = new GradientDrawable();
                gd2.setCornerRadius(24 * density);
                if (item.showDiscount() == 2) {
                    holder.mTvTag.setText(item.getFlash_discount() + "折");
                    gd2.setColors(new int[]{Color.parseColor("#C000FF"),
                            Color.parseColor("#F126D7")});
                    gd2.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                } else if (item.showDiscount() == 1) {
                    holder.mTvTag.setText(item.getDiscount() + "折");
                    gd2.setColor(Color.parseColor("#FF3600"));
                }
                holder.mTvTag.setBackground(gd2);
                tagWidth = 60;
            }

            holder.mTvGameSize.setText(item.getClient_size() + "M");
            if (item.getFirst_label() != null) {
                try {
                    String gameStarting = item.getFirst_label().getLabel_name();
                    if (gameStarting != null) {
                        holder.mTvGameStarting.setText(gameStarting);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    holder.mTvGameStarting.setVisibility(View.GONE);
                }
            } else {
                holder.mTvGameStarting.setVisibility(View.GONE);
            }

            if (game_type == 3) {
                holder.mLlLayout1.setVisibility(View.GONE);
                holder.mLlLayout3.setVisibility(View.VISIBLE);
                holder.mTvDownload.setText("开始");
            }
        }


        //设置游戏名最大宽度
        int tagMaxWidth = gameNameMaxWidth - (holder.mTvTag.getVisibility() == View.VISIBLE ? tagWidth : 0);
        holder.mTvGameName.setMaxWidth((int) (tagMaxWidth * density));

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(ContextCompat.getColor(mContext, R.color.white));
        gd.setCornerRadius(5 * density);
        gd.setStroke((int) (1 * density), ContextCompat.getColor(mContext, R.color.color_3478f6));

        holder.mTvDownload.setTextColor(ContextCompat.getColor(mContext, R.color.color_3478f6));
        holder.mTvDownload.setBackground(gd);

        holder.mTvGameName.setOnClickListener(view -> {
            goGameDetail(item.getGameid(), item.getGame_type());
        });
        holder.mGameIconIV.setOnClickListener(view -> {
            goGameDetail(item.getGameid(), item.getGame_type());
        });
        holder.mTvDownload.setOnClickListener(view -> {
            goGameDetail(item.getGameid(), item.getGame_type());
        });
    }


    private void goGameDetail(int gameid, int game_type) {
        if (_mFragment != null) {
            _mFragment.goGameDetail(gameid, game_type);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_gameinfo_server;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private ImageView    mGameIconIV;
        private TextView     mTvGameName;
        private TextView     mTvTag;
        private TextView     mTvGameType;
        private LinearLayout mLlLayout1;
        private TextView     mTvGameSize;
        private LinearLayout mLlLayout2;
        private TextView     mTvRate;
        private LinearLayout mLlLayout3;
        private TextView     mTvH5GameTag;
        private TextView     mTvGameStarting;
        private TextView     mTvTime;
        private TextView     mTvServer;
        private TextView     mTvDownload;

        public ViewHolder(View view) {
            super(view);
            mGameIconIV = itemView.findViewById(R.id.gameIconIV);
            mTvGameName = itemView.findViewById(R.id.tv_game_name);
            mTvTag = itemView.findViewById(R.id.tv_tag);
            mTvGameType = itemView.findViewById(R.id.tv_game_type);
            mLlLayout1 = itemView.findViewById(R.id.ll_layout_1);
            mTvGameSize = itemView.findViewById(R.id.tv_game_size);
            mLlLayout2 = itemView.findViewById(R.id.ll_layout_2);
            mTvRate = itemView.findViewById(R.id.tv_rate);
            mLlLayout3 = itemView.findViewById(R.id.ll_layout_3);
            mTvH5GameTag = itemView.findViewById(R.id.tv_h5_game_tag);
            mTvGameStarting = itemView.findViewById(R.id.tv_game_starting);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mTvServer = itemView.findViewById(R.id.tv_server);
            mTvDownload = itemView.findViewById(R.id.tv_download);

        }
    }
}
