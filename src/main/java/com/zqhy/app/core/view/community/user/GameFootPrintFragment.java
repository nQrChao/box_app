package com.zqhy.app.core.view.community.user;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.community.user.holder.GameFootPrintItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.community.CommunityViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class GameFootPrintFragment extends BaseListFragment<CommunityViewModel> {

    public static GameFootPrintFragment newInstance(int uid) {
        GameFootPrintFragment fragment = new GameFootPrintFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("uid", uid);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(GameInfoVo.class, new GameFootPrintItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment,this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    private int uid;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            uid = getArguments().getInt("uid");
        }
        super.initView(state);
        initActionBackBarAndTitle("游戏足迹");
        initData();
    }


    private void initData() {
        page = 1;
        getUserGameFootprintData();
    }


    @Override
    public void onRefresh() {
        super.onRefresh();
        initData();
    }


    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (page < 0) {
            return;
        }
        page++;
        getUserGameFootprintData();
    }


    private int page = 1, pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }


    private void getUserGameFootprintData() {
        if (mViewModel != null) {

            mViewModel.getUserGameFootprintData(uid, page, pageCount, new OnBaseCallback<GameListVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(GameListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                if (page == 1) {
                                    clearData();
                                }
                                addAllData(data.getData());
                            } else {
                                if (page == 1) {
                                    clearData();
                                    addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                                } else {
                                    setListNoMore(true);
                                }
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

}
