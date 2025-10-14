package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.appointment.GameAppointmentVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.main.MainGamePageFragment;
import com.zqhy.app.core.view.main.new_game.NewGameAppointmentFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author leeham
 * @date 2020/3/30-10:49
 * @description
 */
public class GameAppointmentTabItemHolder extends BaseItemHolder<GameAppointmentVo, GameAppointmentTabItemHolder.ViewHolder> {

    public GameAppointmentTabItemHolder(Context context) {
        super(context);
    }

    private BaseFragment _mSubFragment;

    @Override
    protected void initView(View view) {
        super.initView(view);
        _mSubFragment = (BaseFragment) view.getTag(R.id.tag_sub_fragment);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_appointment_tab;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameAppointmentVo item) {
        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mGameIconIV);
        holder.mTvGameName.setText(item.getGamename());

        String date = item.getOnline_text();
        if (TextUtils.isEmpty(date)) {
            holder.mTvGameAppointmentDate.setText("敬请期待");
            holder.mTvGameAppointmentDate.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff4747));
        } else {
            holder.mTvGameAppointmentDate.setText(date);
            holder.mTvGameAppointmentDate.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff7500));
        }

        holder.mTvGameAppointmentCount.setText(CommonUtils.formatNumber(item.getAppointment_count()) + "人已预约");

        /*if (!TextUtils.isEmpty(item.getOtherGameName())){//游戏后缀
            holder.mTvGameSuffix.setVisibility(View.VISIBLE);
            holder.mTvGameSuffix.setText(item.getOtherGameName());
        }else {
            holder.mTvGameSuffix.setVisibility(View.GONE);
        }*/

        String genreStr = item.getGenre_str();
        String str = genreStr.replace("•", " ");
        if (!TextUtils.isEmpty(item.getOtherGameName())){
            str = str + "•" + item.getOtherGameName();
        }
        holder.mTvInfoMiddle.setText(str);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        if (item.getGame_status() == 0) {
            //可点击关注
            holder.mTvGameFocusOn.setText("预约");
            holder.mTvGameFocusOn.setBackgroundResource(R.drawable.shape_fe5914_big_radius);
            holder.mTvGameFocusOn.setOnClickListener(v -> {
                //关注
                if (_mFragment != null && _mFragment instanceof MainGamePageFragment) {
                    if (_mFragment.checkLogin()) {
                        ((MainGamePageFragment) _mFragment).gameAppointment(item.getGameid(), item);
                    }
                }
                if (_mSubFragment != null && _mSubFragment instanceof NewGameAppointmentFragment) {
                    if (_mSubFragment.checkLogin()) {
                        ((NewGameAppointmentFragment) _mSubFragment).gameAppointment(item.getGameid(), item);
                    }
                }
            });
        } else if (item.getGame_status() == 1) {
            //取消关注
            holder.mTvGameFocusOn.setText("已预约");
            holder.mTvGameFocusOn.setBackgroundResource(R.drawable.shape_999999_big_radius);
            holder.mTvGameFocusOn.setOnClickListener(v -> {
                //取消关注
                if (_mFragment != null && _mFragment instanceof MainGamePageFragment) {
                    if (_mFragment.checkLogin()) {
                        ((MainGamePageFragment) _mFragment).gameAppointment(item.getGameid(), item);
                    }
                }
                if (_mSubFragment != null && _mSubFragment instanceof NewGameAppointmentFragment) {
                    if (_mSubFragment.checkLogin()) {
                        ((NewGameAppointmentFragment) _mSubFragment).gameAppointment(item.getGameid(), item);
                    }
                }
            });

        } else if (item.getGame_status() == 10) {
            //已结束
            holder.mTvGameFocusOn.setText("已上线");
            holder.mTvGameFocusOn.setBackgroundResource(R.drawable.shape_999999_big_radius);
            holder.mTvGameFocusOn.setOnClickListener(v -> {
                if (_mFragment != null) {
                    //进详情页
                    _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
                }
            });
        }
        holder.mLlRootView.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
            }
        });
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mLlRootView;
        private ImageView    mGameIconIV;
        private TextView     mTvGameName;
        private TextView     mTvInfoMiddle;
        private  TextView      mTvGameSuffix;
        private TextView     mTvGameAppointmentDate;
        private TextView     mTvGameAppointmentCount;
        private TextView     mTvGameFocusOn;

        public ViewHolder(View view) {
            super(view);
            mLlRootView = findViewById(R.id.ll_rootView);
            mGameIconIV = findViewById(R.id.gameIconIV);
            mTvGameName = findViewById(R.id.tv_game_name);
            mTvInfoMiddle = findViewById(R.id.tv_info_middle);
            mTvGameAppointmentDate = findViewById(R.id.tv_game_appointment_date);
            mTvGameAppointmentCount = findViewById(R.id.tv_game_appointment_count);
            mTvGameFocusOn = findViewById(R.id.tv_game_focus_on);

            mTvGameSuffix = view.findViewById(R.id.tv_game_suffix);

        }
    }
}
