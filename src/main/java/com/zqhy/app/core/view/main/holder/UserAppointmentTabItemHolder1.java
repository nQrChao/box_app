package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.appointment.UserAppointmentVo;
import com.zqhy.app.core.view.user.NewUserMineFragment1;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author leeham
 * @date 2020/3/30-10:49
 * @description
 */
public class UserAppointmentTabItemHolder1 extends BaseItemHolder<UserAppointmentVo, UserAppointmentTabItemHolder1.ViewHolder> {

    public UserAppointmentTabItemHolder1(Context context) {
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
        return R.layout.item_user_appointment_tab1;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull UserAppointmentVo item) {
        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mGameIconIV);
        holder.mTvGameName.setText(item.getGamename());

        if (TextUtils.isEmpty(item.getOnline_time()) || "0".equals(item.getOnline_time())) {
            holder.mTvGameAppointmentDate.setText("敬请期待");
        } else {
            holder.mTvGameAppointmentDate.setText(CommonUtils.friendlyTime4(Long.parseLong(item.getOnline_time()) * 1000));
        }

        holder.mIvMore.setOnClickListener(view -> {
            showPopUp(holder.mIvMore, item);
        });

        holder.mGameIconIV.setOnClickListener(view -> {
            try{
                _mFragment.goGameDetail(Integer.parseInt(item.getGameid()), Integer.parseInt(item.getGame_type()));
            }catch (Exception e){}
        });
        holder.mTvGameName.setOnClickListener(view -> {
            try{
                _mFragment.goGameDetail(Integer.parseInt(item.getGameid()), Integer.parseInt(item.getGame_type()));
            }catch (Exception e){}
        });
        holder.mTvGameAppointmentDate.setOnClickListener(view -> {
            try{
                _mFragment.goGameDetail(Integer.parseInt(item.getGameid()), Integer.parseInt(item.getGame_type()));
            }catch (Exception e){}
        });
    }

    public class ViewHolder extends AbsHolder {
        private ImageView    mGameIconIV;
        private TextView     mTvGameName;
        private TextView     mTvGameAppointmentDate;
        private ImageView    mIvMore;

        public ViewHolder(View view) {
            super(view);
            mGameIconIV = findViewById(R.id.gameIconIV);
            mTvGameName = findViewById(R.id.tv_game_name);
            mTvGameAppointmentDate = findViewById(R.id.tv_game_appointment_date);
            mIvMore = findViewById(R.id.iv_more);
        }
    }

    private View popupView;
    private PopupWindow popWindow;
    private void showPopUp(View view, UserAppointmentVo item){
        if (popupView == null){
            popupView = LayoutInflater.from(mContext).inflate(R.layout.dialog_popup_reserve_tips1, null, false);
            popWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        }
        popupView.findViewById(R.id.tv_cancel).setOnClickListener(view1 -> {
            if (popWindow != null && popWindow.isShowing()) popWindow.dismiss();
            AlertDialog dialog = new AlertDialog.Builder(mContext)
                    .setTitle("提示")
                    .setMessage("是否取消预约？")
                    .setPositiveButton("是", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        //取消预约
                        if (_mSubFragment != null && _mSubFragment instanceof NewUserMineFragment1) {
                            if (_mSubFragment.checkLogin()) {
                                ((NewUserMineFragment1) _mSubFragment).gameAppointment(Integer.parseInt(item.getGameid()), item);
                            }
                        }
                    })
                    .setNegativeButton("否", (dialogInterface, i) -> dialogInterface.dismiss()).create();
            dialog.show();
        });
        popWindow.setTouchable(true);
        popWindow.showAsDropDown(view, -view.getWidth() / 2, 10);
    }
}
