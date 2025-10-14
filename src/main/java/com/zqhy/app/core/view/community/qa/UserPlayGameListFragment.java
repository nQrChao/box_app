package com.zqhy.app.core.view.community.qa;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.community.qa.holder.UserPlayGameItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.community.CommunityViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class UserPlayGameListFragment extends BaseListFragment<CommunityViewModel> {

    public static UserPlayGameListFragment newInstance(int user_id) {
        UserPlayGameListFragment fragment = new UserPlayGameListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", user_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(GameInfoVo.class, new UserPlayGameItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new GridLayoutManager(_mActivity, 3);
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    private int user_id;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            user_id = getArguments().getInt("user_id");
        }
        super.initView(state);
        initActionBackBarAndTitle("我玩过的游戏");
        mRecyclerView.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f5f5f5));
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

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        page++;
        getUserPlayGameListData();
    }


    private int page = 1, pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    private void initData() {
        page = 1;
        getUserPlayGameListData();
    }


    private void getUserPlayGameListData() {
        if (mViewModel != null) {
            mViewModel.getUserGameFootprintData(user_id, page, pageCount, new OnBaseCallback<GameListVo>() {
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
                                    addData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                                }
                                setListNoMore(true);
                                notifyData();
                            }
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }
}
