package com.zqhy.app.core.view.main.holder.bt;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.bt.MainBTPageMenuVo;
import com.zqhy.app.core.view.community.task.TaskCenterFragment;
import com.zqhy.app.core.view.game.coupon.GameCouponsListFragment;
import com.zqhy.app.core.view.main.new_game.NewGameMainFragment;
import com.zqhy.app.core.view.user.provincecard.NewProvinceCardFragment;
import com.zqhy.app.core.view.user.provincecard.ProvinceCardFragment;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/8/28-15:50
 * @description
 */
public class GameBTMenuItemHolder extends AbsItemHolder<MainBTPageMenuVo, GameBTMenuItemHolder.ViewHolder> {
    public GameBTMenuItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_bt_menu;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainBTPageMenuVo item) {
        holder.mRlMainBtMenu1.setOnClickListener(view -> {
            //新游首发
            if (_mFragment != null) {
                _mFragment.startFragment(new NewGameMainFragment());
            }
        });
        holder.mRlMainBtMenu2.setOnClickListener(view -> {
            //领券中心
            if (_mFragment != null) {
                _mFragment.startFragment(new GameCouponsListFragment());
            }
        });
        holder.mRlMainBtMenu3.setOnClickListener(view -> {
            //任务赚金
            if (_mFragment != null) {
                _mFragment.startFragment(TaskCenterFragment.newInstance());
            }
        });
        holder.mRlMainBtMenu4.setOnClickListener(view -> {
            //会员福利
            /*if (_mFragment != null && _mFragment.checkLogin()) {
                _mFragment.startFragment(new VipMemberFragment());
            }*/
            if (_mFragment != null) _mFragment.startFragment(new NewProvinceCardFragment());
        });

    }

    public class ViewHolder extends AbsHolder {
        private RelativeLayout mRlMainBtMenu1;
        private RelativeLayout mRlMainBtMenu2;
        private RelativeLayout mRlMainBtMenu3;
        private RelativeLayout mRlMainBtMenu4;
        private TextView       mTvApp;

        public ViewHolder(View view) {
            super(view);
            mRlMainBtMenu1 = findViewById(R.id.rl_main_bt_menu_1);
            mRlMainBtMenu2 = findViewById(R.id.rl_main_bt_menu_2);
            mRlMainBtMenu3 = findViewById(R.id.rl_main_bt_menu_3);
            mRlMainBtMenu4 = findViewById(R.id.rl_main_bt_menu_4);
            mTvApp = findViewById(R.id.tv_app);

            if (_mFragment != null) {
                mTvApp.setText(_mFragment.getAppVipMonthName() + "月卡");
            }
        }
    }
}
