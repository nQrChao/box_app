package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameSearchVo;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.game.GameSearchFragment;
import com.zqhy.app.core.view.main.AbsMainGameListFragment;
import com.zqhy.app.core.view.user.provincecard.NewProvinceCardFragment;
import com.zqhy.app.core.view.user.provincecard.ProvinceCardFragment;
import com.zqhy.app.network.statistics.JiuYaoStatisticsApi;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/25
 */

public class GameSearchItemHolder extends AbsItemHolder<GameSearchVo, GameSearchItemHolder.ViewHolder> {

    public GameSearchItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameSearchVo item) {
        if (!TextUtils.isEmpty(item.getGameSearch())) {
            holder.mTvGameSearch.setText(item.getGameSearch());
        } else {
            holder.mTvGameSearch.setText("搜索游戏");
        }
        holder.mFlSearchView.setOnClickListener(view -> {
            if (_mFragment != null) {
                FragmentHolderActivity.startFragmentInActivity(_mFragment.getActivity(), new GameSearchFragment());
            }
            switch (item.getGame_type()) {
                case 1:
                    JiuYaoStatisticsApi.getInstance().eventStatistics(1, 1);
                    break;
                case 2:
                    JiuYaoStatisticsApi.getInstance().eventStatistics(2, 19);
                    break;
                case 3:
                    JiuYaoStatisticsApi.getInstance().eventStatistics(3, 38);
                    break;
                case 4:
                    JiuYaoStatisticsApi.getInstance().eventStatistics(4, 56);
                    break;
                default:
                    break;
            }

        });
        holder.mTvModuleVip.setOnClickListener(v -> {
            //2020.03.25会员入口
            if (_mFragment != null) {
                /*if (_mFragment.checkLogin()) {
                    FragmentHolderActivity.startFragmentInActivity(_mFragment.getActivity(), new VipMemberFragment());
                }*/
                FragmentHolderActivity.startFragmentInActivity(_mFragment.getActivity(), new NewProvinceCardFragment());
            }
        });
        holder.mTvModuleGameClassification.setOnClickListener(v -> {
            //2020.03.25游戏分类
            if (_mFragment != null && _mFragment instanceof AbsMainGameListFragment) {
                //主界面-游戏-分类
                ((AbsMainGameListFragment) _mFragment).goToMainGamePage(0);
            }
        });
    }


    @Override
    public int getLayoutResId() {
        return R.layout.item_game_search;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout mFlSearchView;
        private TextView     mTvGameSearch;
        private TextView     mTvModuleVip;
        private TextView     mTvModuleGameClassification;

        public ViewHolder(View view) {
            super(view);
            mFlSearchView = findViewById(R.id.fl_game_search_view);
            mTvGameSearch = findViewById(R.id.tv_game_search);
            mTvModuleVip = findViewById(R.id.tv_module_vip);
            mTvModuleGameClassification = findViewById(R.id.tv_module_game_classification);
        }
    }
}
