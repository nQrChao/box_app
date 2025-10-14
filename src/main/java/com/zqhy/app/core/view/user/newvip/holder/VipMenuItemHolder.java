package com.zqhy.app.core.view.user.newvip.holder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.user.newvip.VipMenuVo;
import com.zqhy.app.core.view.community.task.TaskCenterFragment;
import com.zqhy.app.core.view.user.newvip.NewUserVipFragment;
import com.zqhy.app.core.view.user.newvip.VipCouponListFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class VipMenuItemHolder extends AbsItemHolder<VipMenuVo, VipMenuItemHolder.ViewHolder> {
    public VipMenuItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull VipMenuVo item) {
        if (item.getType() == 1){
            holder.mIvIcon.setImageResource(R.mipmap.ic_new_vip_super_province_item);
        }else if (item.getType() == 2){
            holder.mIvIcon.setImageResource(R.mipmap.ic_new_vip_task_item);
        }else if (item.getType() == 3){
            holder.mIvIcon.setImageResource(R.mipmap.ic_new_vip_birthday_item);
        }else if (item.getType() == 4){
            holder.mIvIcon.setImageResource(R.mipmap.ic_new_vip_ios_item);
        }else if (item.getType() == 5){
            holder.mIvIcon.setImageResource(R.mipmap.ic_new_vip_vouchers_item);
        }else if (item.getType() == 6){
            holder.mIvIcon.setImageResource(R.mipmap.ic_new_vip_giftbag_item);
        }
        if (item.getType() == 4){
            holder.mTvConfirm.setText("安装");
            holder.mTvConfirm.setTextColor(Color.parseColor("#FF3D63"));
            holder.mTvConfirm.setBackgroundResource(R.drawable.shape_ff3d63_big_radius_with_line);
        }else if (item.getType() == 1){
            holder.mTvConfirm.setText("立享");
            holder.mTvConfirm.setTextColor(Color.parseColor("#FF3D63"));
            holder.mTvConfirm.setBackgroundResource(R.drawable.shape_ff3d63_big_radius_with_line);
        }else{
            holder.mTvConfirm.setText("领取");
            holder.mTvConfirm.setTextColor(Color.parseColor("#FF3D63"));
            holder.mTvConfirm.setBackgroundResource(R.drawable.shape_ff3d63_big_radius_with_line);
        }
        holder.mTvTitle.setText(item.getTitle());
        holder.mTvContent.setText(item.getContent());
        holder.mTvConfirm.setOnClickListener(view -> {
            if (_mFragment != null && _mFragment instanceof NewUserVipFragment) {
                if (item.getType() == 1){
                    //交易
                    //_mFragment.startFragment(new TransactionMainFragment1());
                    Toaster.show("成功出售小号时，立即享受减免！");
                }else if (item.getType() == 2){
                    //任务赚金
                    _mFragment.startFragment(TaskCenterFragment.newInstance());
                }else if (item.getType() == 3){
                    if (_mFragment.checkLogin()){
                        ((NewUserVipFragment)_mFragment).showBirthdayTipsDialog(true);
                    }
                }else if (item.getType() == 4){
                    Toaster.show("请使用IOS设备安装");
                }else if (item.getType() == 5){
                    _mFragment.startFragment(VipCouponListFragment.newInstance(0));
                }else if (item.getType() == 6){
                    _mFragment.startFragment(VipCouponListFragment.newInstance(1));
                }
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_vip_menu;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private TextView     mTvTitle;
        private TextView mTvContent;
        private TextView mTvConfirm;
        private ImageView mIvIcon;

        public ViewHolder(View view) {
            super(view);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvContent = itemView.findViewById(R.id.tv_content);
            mTvConfirm = itemView.findViewById(R.id.tv_confirm);
            mIvIcon = itemView.findViewById(R.id.iv_icon);
        }
    }
}
