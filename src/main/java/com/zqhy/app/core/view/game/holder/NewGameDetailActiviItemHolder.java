package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.detail.GameActivityVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.game.GameCommentRewardFragment;
import com.zqhy.app.core.view.game.GameCouponListFragment;
import com.zqhy.app.core.view.kefu.KefuCenterFragment;
import com.zqhy.app.core.view.strategy.DiscountStrategyFragment;
import com.zqhy.app.core.view.tryplay.TryGameTaskFragment;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

/**
 *
 * @author Administrator
 * 开服表
 */

public class NewGameDetailActiviItemHolder extends AbsItemHolder<GameActivityVo.ItemBean, NewGameDetailActiviItemHolder.ViewHolder> {

    private float density;
    public NewGameDetailActiviItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameActivityVo.ItemBean item) {
        if (item.getType() == 1) {
            GameInfoVo.NewslistBean newslistBean = item.getNewslistBean();
            if (newslistBean != null) {
                holder.mTvActivityName.setText(newslistBean.getTitle());
                //holder.mTvActivityContent.setText(CommonUtils.timeStamp2Date(Long.parseLong(newslistBean.getFabutime() + "000"), "yyyy-MM-dd HH:mm"));
                if (newslistBean.getIs_newest() == 1){
                    if ("normal".equals(newslistBean.getNews_status())){
                        holder.mTvActivityContent.setText("限时活动·进行中");
                    }else if ("end".equals(newslistBean.getNews_status())){
                        holder.mTvActivityContent.setText("限时活动·已结束");
                    }
                }else {
                    if ("normal".equals(newslistBean.getNews_status())){
                        holder.mTvActivityContent.setText("永久活动·进行中");
                    }else if ("end".equals(newslistBean.getNews_status())){
                        holder.mTvActivityContent.setText("永久活动·已结束");
                    }
                }
                holder.mTvActivityTag.setVisibility(View.GONE);
                if (newslistBean.getRebate_apply_type() == 0){
                    holder.mTvActivityAction.setText("查看");
                    holder.mLlRootView.setOnClickListener(view -> {
                        if (_mFragment != null) {
                            BrowserActivity.newInstance(_mFragment.getActivity(), item.getNewslistBean().getUrl());
                        }
                    });
                }else if (newslistBean.getRebate_apply_type() == 1){
                    holder.mTvActivityAction.setText("申请");
                    holder.mLlRootView.setOnClickListener(view -> {
                        if (_mFragment != null) {
                            if (_mFragment.checkLogin()) {
                                UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
                                String url = item.getNewslistBean().getRebate_url();
                                url = url.split("#")[0] + "&uid=" + userInfo.getUid() + "&token=" + userInfo.getToken() + "#" + url.split("#")[1];
                                BrowserActivity.newInstance(_mFragment.getActivity(), url, true, "", "", false);
                            }
                        }
                    });
                }else if (newslistBean.getRebate_apply_type() == 2){
                    holder.mTvActivityAction.setText("自动发放");
                    holder.mLlRootView.setOnClickListener(view -> {
                        if (_mFragment != null) {
                            BrowserActivity.newInstance(_mFragment.getActivity(), item.getNewslistBean().getUrl());
                        }
                    });
                }else if (newslistBean.getRebate_apply_type() == 3){
                    holder.mTvActivityAction.setText("联系客服");
                    holder.mLlRootView.setOnClickListener(view -> {
                        if (_mFragment != null) {
                            _mFragment.startFragment(new KefuCenterFragment());
                        }
                    });
                }
            }
        } else if (item.getType() == 2) {
            GameActivityVo.TopMenuInfoBean topMenuInfoBean = item.getMenuInfoBean();
            if (topMenuInfoBean != null) {
                holder.mTvActivityName.setText(topMenuInfoBean.getTag());
                holder.mTvActivityContent.setText(topMenuInfoBean.getMessage());
            }
        }
        holder.mTvActivityAction.setOnClickListener(v -> {
            if (item.getType() == 1) {
                if (item.getNewslistBean() != null) {
                    if (item.getNewslistBean().getRebate_apply_type() == 0){
                        if (_mFragment != null) {
                            BrowserActivity.newInstance(_mFragment.getActivity(), item.getNewslistBean().getUrl());
                        }
                    }else if (item.getNewslistBean().getRebate_apply_type() == 1){
                        if (_mFragment != null) {
                            if (_mFragment.checkLogin()) {
                                UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
                                String url = item.getNewslistBean().getRebate_url();
                                url = url.split("#")[0] + "&uid=" + userInfo.getUid() + "&token=" + userInfo.getToken() + "#" + url.split("#")[1];
                                BrowserActivity.newInstance(_mFragment.getActivity(), url, true, "", "", false);
                            }
                        }
                    }else if (item.getNewslistBean().getRebate_apply_type() == 2){
                        if (_mFragment != null) {
                            BrowserActivity.newInstance(_mFragment.getActivity(), item.getNewslistBean().getUrl());
                        }
                    }else if (item.getNewslistBean().getRebate_apply_type() == 3){
                        if (_mFragment != null) {
                            _mFragment.startFragment(new KefuCenterFragment());
                        }
                    }
                }
            } else if (item.getType() == 2) {
                if (item.getMenuInfoBean() != null) {
                    if (_mFragment != null) {
                        menuItemClick(item.getMenuInfoBean().getId(), item);
                    }
                }
            }
        });
    }

    private void menuItemClick(int menuId, GameActivityVo.ItemBean item) {
        switch (menuId) {
            case 1:
            case 5:
                //自动折扣
                //限时折扣
                if (_mFragment != null) {
                    _mFragment.start(new DiscountStrategyFragment());
                }
                break;
            case 2:
                //限时任务
                break;
            case 3:
                //代金券(单游戏优惠券列表)
                if (_mFragment != null) {
                    _mFragment.start(GameCouponListFragment.newInstance(item.getGemeId()));
                }
                break;
            case 4:
                //账号回收
                break;
            case 6:
                //试玩赚钱
                if (_mFragment != null) {
                    _mFragment.start(TryGameTaskFragment.newInstance(item.getTid()));
                }
                break;
            case 7:
                //点评送礼
                if (_mFragment != null) {
                    _mFragment.startFragment(new GameCommentRewardFragment());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_detail_item_activi_list_new;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvActivityName;
        private TextView mTvActivityTag;
        private TextView mTvActivityContent;
        private TextView mTvActivityAction;
        private LinearLayout mLlRootView;

        public ViewHolder(View view) {
            super(view);

            mTvActivityName = view.findViewById(R.id.tv_activity_name);
            mTvActivityTag = view.findViewById(R.id.tv_activity_tag);
            mTvActivityContent = view.findViewById(R.id.tv_activity_content);
            mTvActivityAction = view.findViewById(R.id.tv_activity_action);
            mLlRootView = view.findViewById(R.id.ll_rootView);
        }
    }
}
