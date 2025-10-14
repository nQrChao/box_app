package com.zqhy.app.core.view.server;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.adapter.AdapterPool;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.game.ServerListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.vm.server.ServerViewModel;
import com.zqhy.app.newproject.R;

import java.util.TreeMap;

/**
 * @author Administrator
 * @date 2018/11/8
 */

public class ServerListFragment extends BaseListFragment<ServerViewModel> {


    public static ServerListFragment newInstance(int game_type, String dt) {
        ServerListFragment fragment = new ServerListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("game_type", game_type);
        bundle.putString("dt", dt);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int page = 1;

    private final int pageCount = 12;

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_SERVER_LIST_STATE;
    }

    @Override
    protected int getPageCount() {
        return pageCount;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return AdapterPool.newInstance().getServerListAdapter(_mActivity)
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    int game_type;
    String dt;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            game_type = getArguments().getInt("game_type");
            dt = getArguments().getString("dt");
        }
        super.initView(state);
    }

    @Override
    protected String getStateEventTag() {
        String tag = game_type + "&" + dt;
        return tag;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getNetWorkData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getNetWorkData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getNetWorkData();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        getMoreNetWorkData();
    }

    private void getNetWorkData() {
        if (mViewModel != null) {
            page = 1;
            getServerList();
        }
    }

    private void getMoreNetWorkData() {
        if (mViewModel != null) {
            page++;
            getServerList();
        }
    }

    private void getServerList() {
        mViewModel.getServerList(new TreeMap<>(), dt, page, pageCount, new OnBaseCallback<ServerListVo>() {

            @Override
            public void onAfter() {
                super.onAfter();
                refreshAndLoadMoreComplete();
            }

            @Override
            public void onSuccess(ServerListVo serverListVo) {
                if (serverListVo != null) {
                    if (serverListVo.isStateOK()) {
                        if (serverListVo.getData() != null && !serverListVo.getData().isEmpty()) {
                            if (page == 1) {
                                clearData();
                            }
                            addAllData(serverListVo.getData());
                        } else {
                            if (page == 1) {
                                clearData();
                                addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                            } else {
                                page = -1;
                                setListNoMore(true);
                            }
                            notifyData();
                        }
                    } else {
                        Toaster.show( serverListVo.getMsg());
                    }
                }
            }
        });
    }

}
