package com.zqhy.app.core.view.main.new_game;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.coupon.GameCouponsListVo;
import com.zqhy.app.core.data.model.game.coupon.GameStartingListVo;
import com.zqhy.app.core.data.model.new_game.NewGameMoreVo;
import com.zqhy.app.core.data.model.new_game.NewGameStartingHeaderVo;
import com.zqhy.app.core.data.model.new_game.NewGameTitleVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.main.new_game.holder.NewGameMoreItemHolder;
import com.zqhy.app.core.view.main.new_game.holder.NewGameStartingHeaderItemHolder;
import com.zqhy.app.core.view.main.new_game.holder.NewGameStartingItemHolder;
import com.zqhy.app.core.view.main.new_game.holder.NewGameStartingTitleItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/8/24-12:06
 * @description
 */
public class NewGameStartingFragment extends BaseListFragment<GameViewModel> {

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(NewGameStartingHeaderVo.class, new NewGameStartingHeaderItemHolder(_mActivity))
                .bind(NewGameTitleVo.class, new NewGameStartingTitleItemHolder(_mActivity))
                .bind(GameInfoVo.class, new NewGameStartingItemHolder(_mActivity))
                .bind(NewGameMoreVo.class, new NewGameMoreItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this)
                .setTag(R.id.tag_sub_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "新游首发";
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("近期上新游戏");
        setTitleLayout(LAYOUT_ON_CENTER);
        setLoadingMoreEnabled(false);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        initData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        initData();
    }

    private NewGameStartingHeaderVo mHeaderVo = new NewGameStartingHeaderVo();

    private void initData() {
        if (mViewModel != null) {
            mViewModel.getNewGameStartingList(new OnBaseCallback<GameStartingListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(GameStartingListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                clearData();
                                /*List<GameCouponsListVo.DataBean> couponList = data.getData().getCoupon_data();
                                if (couponList != null && !couponList.isEmpty()) {
                                    mHeaderVo.setList(couponList);
                                    addData(mHeaderVo);
                                }*/
                                boolean hasGames = false;
                                List<GameStartingListVo.GameDataVo> gameDataVoList = data.getData().getGame_data();
                                if (gameDataVoList != null && !gameDataVoList.isEmpty()) {
                                    for (GameStartingListVo.GameDataVo dataVo : gameDataVoList) {
                                        addData(new NewGameTitleVo().setTitle(dataVo.getName()));
                                        addAllData(dataVo.getList());
                                    }
                                    hasGames = true;
                                }
                                if (!hasGames) {
                                    addData(new EmptyDataVo());
                                }
                                //addData(new NewGameMoreVo());
                                notifyData();
                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    public void getCoupon(int coupon_id, int gameid) {
        if (checkLogin() && checkUserBindPhoneTips1()) {
            if (mViewModel != null) {
                mViewModel.getCoupon(coupon_id, new OnBaseCallback() {
                    @Override
                    public void onSuccess(BaseVo data) {
                        if (data != null) {
                            if (data.isStateOK()) {
                                Toaster.show( "领取成功");
                                if (mHeaderVo.getList() != null) {
                                    List<GameCouponsListVo.DataBean> list = mHeaderVo.getList();
                                    if (list != null && !list.isEmpty()) {
                                        for (GameCouponsListVo.DataBean dataBean : list) {
                                            if (dataBean.getItemId() == gameid) {
                                                List<GameCouponsListVo.CouponVo> couponVoList = dataBean.getCoupon_list();
                                                for (GameCouponsListVo.CouponVo couponVo : couponVoList) {
                                                    if (coupon_id == couponVo.getCoupon_id()) {
                                                        couponVo.setStatus(10);
                                                    }
                                                }
                                            } else {
                                                continue;
                                            }
                                        }
                                    }
                                }
                                notifyData();
                            } else {
                                Toaster.show( data.getMsg());
                            }
                        }
                    }
                });
            }
        }
    }
}
