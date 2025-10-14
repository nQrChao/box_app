package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.appointment.GameAppointmentVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.game.GameAppointmentListFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class GameAppointmentItemHolder extends AbsItemHolder<GameAppointmentVo, GameAppointmentItemHolder.ViewHolder> {
    public GameAppointmentItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_appointment;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameAppointmentVo item) {
        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mGameIconIV);

        holder.mTvGameName.setText(item.getGamename());

        StringBuilder sb = new StringBuilder();

        switch (item.getGame_type()) {
            case 1:
                sb.append("变态手游");
                break;
            case 2:
                sb.append("折扣手游");
                break;
            case 3:
                sb.append("H5手游");
                break;
            case 4:
                sb.append("单机游戏");
                break;
            default:
                break;
        }
        sb.append(" | ");
        int startIndex = sb.toString().length();
        sb.append(item.getOnline_text());
        int endIndex = sb.toString().length();

        SpannableString ss = new SpannableString(sb.toString());
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_ff8f19)),
                startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.mTvOnlineTime.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
        holder.mTvOnlineTime.setText(ss);

        /*if (item.showDiscount() == 1) {
            float density = ScreenUtil.getScreenDensity(mContext);
            holder.mTvGameDiscount.setVisibility(View.VISIBLE);

            holder.mTvGameDiscount.setText(String.valueOf(item.getDiscount()) + "折");
            holder.mTvGameDiscount.setTextSize(12);
            holder.mTvGameDiscount.setPadding((int) (density * 6), (int) (density * 2), (int) (density * 6), (int) (density * 2));

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(density * 36);
            gd.setColor(ContextCompat.getColor(mContext, R.color.color_ff4949));
            holder.mTvGameDiscount.setBackground(gd);
            holder.mTvGameDiscount.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            holder.mTvGameDiscount.setVisibility(View.GONE);
        }*/

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        if (item.getGame_status() == 0) {
            //可点击关注
            gd.setColor(ContextCompat.getColor(mContext, R.color.white));
            gd.setStroke(1, ContextCompat.getColor(mContext, R.color.color_3478f6));
            gd.setColor(ContextCompat.getColor(mContext, R.color.white));
            holder.mTvGameFocusOn.setBackground(gd);

            holder.mTvGameFocusOn.setTextColor(ContextCompat.getColor(mContext, R.color.color_3478f6));
            holder.mTvGameFocusOn.setText("关注");
            holder.mTvGameFocusOn.setOnClickListener(v -> {
                //关注
                if (_mFragment != null && _mFragment instanceof GameAppointmentListFragment) {
                    if (_mFragment.checkLogin()) {
                        ((GameAppointmentListFragment) _mFragment).gameAppointment(item.getGameid(), item);
                    }
                }
            });
        } else if (item.getGame_status() == 1) {
            //取消关注
            gd.setColor(ContextCompat.getColor(mContext, R.color.color_f2f2f2));
            holder.mTvGameFocusOn.setBackground(gd);

            holder.mTvGameFocusOn.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
            holder.mTvGameFocusOn.setText("已关注");
            holder.mTvGameFocusOn.setOnClickListener(v -> {
                //取消关注
                if (_mFragment != null && _mFragment instanceof GameAppointmentListFragment) {
                    if (_mFragment.checkLogin()) {
                        ((GameAppointmentListFragment) _mFragment).gameAppointment(item.getGameid(), item);
                    }
                }
            });

        } else if (item.getGame_status() == 10) {
            //已结束
            gd.setColor(ContextCompat.getColor(mContext, R.color.color_f2f2f2));
            holder.mTvGameFocusOn.setBackground(gd);
            holder.mTvGameFocusOn.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));

            holder.mTvGameFocusOn.setText("已上线");
            holder.mTvGameFocusOn.setOnClickListener(v -> {
                if (_mFragment != null) {
                    //进详情页
                    _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
                }
            });
        }

        holder.mGameIconIV.setOnClickListener(v -> {
            if (_mFragment != null) {
                if (item.getGame_status() == 0 || item.getGame_status() == 1) {
                    //敬请期待
//                    ToastT.success("预约成功，上线时我们将通知您噢！");
                } else if (item.getGame_status() == 10) {
                    //进详情页
                    _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
                }
            }
        });
    }

    private void showComingSoonDialog() {
        CustomDialog dialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_coming_soon, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        LinearLayout mLlItem = dialog.findViewById(R.id.ll_item);
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5 * ScreenUtil.getScreenDensity(mContext));
        gd.setColor(Color.parseColor("#9A000000"));
        mLlItem.setBackground(gd);

        dialog.show();
    }

    public static class ViewHolder extends AbsHolder {

        private ImageView mGameIconIV;
        private TextView mTvGameName;
        private TextView mTvGameDiscount;
        private TextView mTvGameType;
        private TextView mTvOnlineTime;
        private TextView mTvGameFocusOn;

        public ViewHolder(View view) {
            super(view);
            mGameIconIV = findViewById(R.id.gameIconIV);
            mTvGameName = findViewById(R.id.tv_game_name);
            mTvGameDiscount = findViewById(R.id.tv_game_discount);
            mTvGameType = findViewById(R.id.tv_game_type);
            mTvOnlineTime = findViewById(R.id.tv_online_time);
            mTvGameFocusOn = findViewById(R.id.tv_game_focus_on);
        }
    }
}
