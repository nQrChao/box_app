package com.zqhy.app.core.view.user.welfare;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.welfare.FavouriteGameHeadVo;
import com.zqhy.app.core.data.model.welfare.MyFavouriteGameListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.FavouriteGameHeaderItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.FavouriteGameItemHolder;
import com.zqhy.app.core.vm.user.welfare.MyFavouriteGameViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class MyFavouriteGameListFragment extends BaseListFragment<MyFavouriteGameViewModel> {
    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_MY_FAVOURITE_GAME_STATE;
    }

    @Override
    protected String getUmengPageName() {
        return "我的游戏（子）";
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        BaseFragment targetFragment = getParentFragment() == null ? this : (BaseFragment) getParentFragment();
        return new BaseRecyclerAdapter.Builder<>()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(GameInfoVo.class, new FavouriteGameItemHolder(_mActivity))
                .bind(FavouriteGameHeadVo.class, new FavouriteGameHeaderItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, targetFragment)
                .setTag(R.id.tag_sub_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("我的游戏");
        setTitleLayout(LAYOUT_ON_CENTER);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        registerObserver(Constants.EVENT_KEY_GAME_SET_UN_MY_FAVORITE, BaseResponseVo.class).observe(this, baseResponseVo -> {

        });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        loadNetWork();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        loadNetWork();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        loadNetWork();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (page < 0) {
            return;
        }
        loadMoreNetWork();
    }

    private int page      = 1;
    private int pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    private void loadNetWork() {
        if (mViewModel != null) {
            page = 1;
            getMyFavouriteGameData();
        }
    }

    private void loadMoreNetWork() {
        if (mViewModel != null) {
            page++;
            getMyFavouriteGameData();
        }
    }

    private void getMyFavouriteGameData() {
        mViewModel.getMyFavouriteGameData(page, pageCount, new OnBaseCallback<MyFavouriteGameListVo>() {
            @Override
            public void onAfter() {
                super.onAfter();
                refreshAndLoadMoreComplete();
            }

            @Override
            public void onSuccess(MyFavouriteGameListVo myFavouriteGameListVo) {
                if (myFavouriteGameListVo != null) {
                    if (myFavouriteGameListVo.isStateOK()) {
                        if (myFavouriteGameListVo.getData() != null && !myFavouriteGameListVo.getData().isEmpty()) {
                            if (page == 1) {
                                clearData();
                                addData(new FavouriteGameHeadVo());
                            }
                            addAllData(myFavouriteGameListVo.getData());
                        } else {
                            if (page == 1) {
                                clearData();
                                addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                            } else {
                                page = -1;
                            }
                            setListNoMore(true);
                        }
                    } else {
                        Toaster.show( myFavouriteGameListVo.getMsg());
                    }
                }
            }
        });
    }

    /**
     * 取消收藏游戏
     *
     * @param gameid
     */
    public void setGameUnFavorite(int gameid) {
        if (mViewModel != null) {
            mViewModel.setGameUnFavorite(gameid, new OnBaseCallback() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    loading();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onSuccess(BaseVo baseResponseVo) {

                    if (baseResponseVo != null) {
                        if (baseResponseVo.isStateOK()) {
                            Toaster.show( R.string.string_game_cancel_favorite_success);
                            setRefresh();
                        } else {
                            Toaster.show( baseResponseVo.getMsg());
                        }
                    }
                }
            });
        }
    }
}
