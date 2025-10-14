package com.zqhy.app.core.view.server.holder;

import android.content.Context;
import androidx.annotation.NonNull;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.ServerListVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.TimeUtils;

/**
 * @author pc
 * @date 2019/12/3-10:14
 * @description
 */
public class TsServerListHolder extends AbsItemHolder<ServerListVo.DataBean, TsServerListHolder.ViewHolder> {


    private float density;

    public TsServerListHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_ts_gameinfo_server;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ServerListVo.DataBean item) {
        //holder.mItemView.setBackgroundResource(R.drawable.shape_ffffff_f8f9ff_15_radius);
        holder.mTvGameName.setText(item.getGamename());
        holder.mTvServer.setText(item.getServername());

        GlideUtils.loadGameIcon(mContext, item.getGameicon(), holder.mGameIconIV);

        //holder.mTvInfoMiddle.setText(item.getGenre_str());
        holder.mTvInfoMiddle.setVisibility(View.VISIBLE);
        holder.mTvInfoMiddle.setTextColor(Color.parseColor("#999999"));
        String genreStr = item.getGenre_str();
        String str = genreStr.replace("•", " ");
        if (!TextUtils.isEmpty(item.getOtherGameName())){
            str = str + "•" + item.getOtherGameName();
        }
        holder.mTvInfoMiddle.setText(str);

        if (TextUtils.isEmpty(item.getOtherGameName())){
            if (item.getPlay_count() > 0){
                holder.mTvPlayCount.setVisibility(View.VISIBLE);
                holder.mTvPlayCount.setText(CommonUtils.formatNumberType2(item.getPlay_count()) + "人玩过");
            }else {
                holder.mTvPlayCount.setVisibility(View.GONE);
            }
        }else {
            holder.mTvPlayCount.setVisibility(View.GONE);
        }

        long ms = item.getBegintime() * 1000;
        int day = TimeUtils.isTodayOrTomorrow(ms);
        if (day == 0) {
            holder.mTvDate.setText(TimeUtils.formatTimeStamp(ms, "今日 HH:mm"));
        } else if (day == 1) {
            holder.mTvDate.setText(TimeUtils.formatTimeStamp(ms, "明日 HH:mm"));
        } else {
            holder.mTvDate.setText(TimeUtils.formatTimeStamp(ms, "MM-dd HH:mm"));
        }
        holder.mTvServer.setText(item.getServername());

        //显示折扣
        setDiscountLabel(holder, item);

        holder.mLlRootview.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
            }
        });
    }

    private void setDiscountLabel(@NonNull TsServerListHolder.ViewHolder viewHolder, @NonNull ServerListVo.DataBean gameInfoVo){
        viewHolder.mLlDiscount1.setVisibility(View.GONE);
        viewHolder.mLlDiscount2.setVisibility(View.GONE);
        viewHolder.mLlDiscount3.setVisibility(View.GONE);
        viewHolder.mLlDiscount4.setVisibility(View.GONE);
        viewHolder.mLlDiscount5.setVisibility(View.GONE);
        viewHolder.mLlDiscount6.setVisibility(View.GONE);
        viewHolder.mTvDiscount3.setVisibility(View.GONE);
        viewHolder.mTvDiscount6.setVisibility(View.GONE);
        //显示折扣
        if (gameInfoVo.getGdm() == 1){//头条包不显示GM标签
            viewHolder.mTvDiscount6.setVisibility(View.VISIBLE);
        }else {
            //显示折扣
            int showDiscount = gameInfoVo.showDiscount();
            if (showDiscount == 1 || showDiscount == 2) {
                if (showDiscount == 1){
                    if (gameInfoVo.getDiscount() <= 0 || gameInfoVo.getDiscount() >= 10) {
                        if (gameInfoVo.getFree() == 1){//免费游戏
                            viewHolder.mLlDiscount4.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount4.setVisibility(View.GONE);
                        }else if (gameInfoVo.getUnshare() == 1) {//专服
                            viewHolder.mLlDiscount6.setVisibility(View.VISIBLE);
                        }else if (gameInfoVo.getSelected_game() == 1){//是否是精选游戏
                            viewHolder.mLlDiscount2.setVisibility(View.VISIBLE);
                        }else {
                            viewHolder.mTvDiscount3.setVisibility(View.VISIBLE);
                        }
                    }else {
                        if (gameInfoVo.getFree() == 1){//免费游戏
                            viewHolder.mLlDiscount4.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount4.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount4.setText(gameInfoVo.getDiscount() + "折");
                        }else if (gameInfoVo.getUnshare() == 1) {//专服
                            viewHolder.mLlDiscount5.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount5.setText(String.valueOf(gameInfoVo.getDiscount()));
                        }else if (gameInfoVo.getSelected_game() == 1){//是否是精选游戏
                            viewHolder.mLlDiscount3.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount2.setText(String.valueOf(gameInfoVo.getDiscount()));
                            viewHolder.mTvDiscount3.setVisibility(View.GONE);
                        }else {
                            viewHolder.mLlDiscount1.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount1.setText(String.valueOf(gameInfoVo.getDiscount()));
                        }
                    }
                }else if (showDiscount == 2){
                    if (gameInfoVo.getFlash_discount() <= 0 || gameInfoVo.getFlash_discount() >= 10) {
                        if (gameInfoVo.getFree() == 1){//免费游戏
                            viewHolder.mLlDiscount4.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount4.setVisibility(View.GONE);
                        }else if (gameInfoVo.getUnshare() == 1) {//专服
                            viewHolder.mLlDiscount6.setVisibility(View.VISIBLE);
                        }else if (gameInfoVo.getSelected_game() == 1){//是否是精选游戏
                            viewHolder.mLlDiscount2.setVisibility(View.VISIBLE);
                        }else {
                            viewHolder.mTvDiscount3.setVisibility(View.VISIBLE);
                        }
                    }else {
                        if (gameInfoVo.getFree() == 1){//免费游戏
                            viewHolder.mLlDiscount4.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount4.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount4.setText(gameInfoVo.getDiscount() + "折");
                        }else if (gameInfoVo.getUnshare() == 1) {//专服
                            viewHolder.mLlDiscount5.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount5.setText(String.valueOf(gameInfoVo.getFlash_discount()));
                        }else if (gameInfoVo.getSelected_game() == 1){//是否是精选游戏
                            viewHolder.mLlDiscount3.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount2.setText(String.valueOf(gameInfoVo.getFlash_discount()));
                            viewHolder.mTvDiscount3.setVisibility(View.GONE);
                        }else {
                            viewHolder.mLlDiscount1.setVisibility(View.VISIBLE);
                            viewHolder.mTvDiscount1.setText(String.valueOf(gameInfoVo.getFlash_discount()));
                        }
                    }
                }
            }else {
                if (gameInfoVo.getFree() == 1){//免费游戏
                    viewHolder.mLlDiscount4.setVisibility(View.VISIBLE);
                    viewHolder.mTvDiscount4.setVisibility(View.GONE);
                }else if (gameInfoVo.getUnshare() == 1) {//专服
                    viewHolder.mLlDiscount6.setVisibility(View.VISIBLE);
                }else if (gameInfoVo.getSelected_game() == 1){//是否是精选游戏
                    viewHolder.mLlDiscount2.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.mTvDiscount3.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public class ViewHolder extends AbsHolder {
        private ImageView    mGameIconIV;
        private TextView     mTvGameName;
        private TextView     mTvServer;
        private  TextView      mTvGameSuffix;
        public  LinearLayout   mLlRootview;
        public  LinearLayout   mLlDiscount1;
        public  LinearLayout   mLlDiscount2;
        public  LinearLayout   mLlDiscount3;
        public  LinearLayout   mLlDiscount4;
        public  LinearLayout   mLlDiscount5;
        public  LinearLayout   mLlDiscount6;
        public  TextView   mTvDiscount1;
        public  TextView   mTvDiscount2;
        public  TextView   mTvDiscount3;
        public  TextView   mTvDiscount4;
        public  TextView   mTvDiscount5;
        public  TextView   mTvDiscount6;
        public  TextView   mTvInfoMiddle;
        public  TextView   mTvPlayCount;
        public  TextView   mTvDate;
        public LinearLayout mItemView;

        public ViewHolder(View view) {
            super(view);
            mGameIconIV = findViewById(R.id.gameIconIV);
            mTvGameName = findViewById(R.id.tv_game_name);
            mTvGameSuffix = view.findViewById(R.id.tv_game_suffix);

            mLlRootview = findViewById(R.id.ll_rootview);

            mTvInfoMiddle = findViewById(R.id.tv_info_middle);
            mTvGameSuffix = view.findViewById(R.id.tv_game_suffix);
            mTvPlayCount = view.findViewById(R.id.tv_play_count);

            mLlDiscount1 = view.findViewById(R.id.ll_discount_1);
            mLlDiscount2 = view.findViewById(R.id.ll_discount_2);
            mLlDiscount3 = view.findViewById(R.id.ll_discount_3);
            mLlDiscount4 = view.findViewById(R.id.ll_discount_4);
            mLlDiscount5 = view.findViewById(R.id.ll_discount_5);
            mLlDiscount6 = view.findViewById(R.id.ll_discount_6);
            mTvDiscount1 = view.findViewById(R.id.tv_discount_1);
            mTvDiscount2 = view.findViewById(R.id.tv_discount_2);
            mTvDiscount3 = view.findViewById(R.id.tv_discount_3);
            mTvDiscount4 = view.findViewById(R.id.tv_discount_4);
            mTvDiscount5 = view.findViewById(R.id.tv_discount_5);
            mTvDiscount6 = view.findViewById(R.id.tv_discount_6);

            mTvDate = view.findViewById(R.id.tv_data);
            mTvServer = view.findViewById(R.id.tv_server);

            mItemView = view.findViewById(R.id.item_view);
        }
    }
}
