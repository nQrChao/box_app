package com.zqhy.app.core.view.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.view.game.holder.NewGameServerItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/21
 */

public class GameDetailServerListFragment extends BaseListFragment<GameViewModel> {

    public static GameDetailServerListFragment newInstance(GameInfoVo gameInfoVo) {
        GameDetailServerListFragment fragment = new GameDetailServerListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("gameinfo", gameInfoVo);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_GAME_COUPON_LIST_STATE;
    }

    @Override
    protected String getStateEventTag() {
        return String.valueOf(gameInfoVo.getGameid());
    }


    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.ServerListBean.class, new NewGameServerItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new GridLayoutManager(_mActivity, 2);
    }

    private GameInfoVo gameInfoVo;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gameInfoVo = (GameInfoVo) getArguments().getSerializable("gameinfo");
        }
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("开服表");
        setLoadingMoreEnabled(false);
        setPullRefreshEnabled(false);
        getNetWorkData();
        setListViewBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));
        addHeaderView();

        if (gameInfoVo.getServerlist() != null && gameInfoVo.getServerlist().size() > 0 && !"动态开服".equals(gameInfoVo.getServer_str())){
            List<GameInfoVo.ServerListBean> serverlist = gameInfoVo.getServerlist();
            Comparator<GameInfoVo.ServerListBean> comparator = (details1, details2) -> {
                //排序规则，按照价格由大到小顺序排列("<"),按照价格由小到大顺序排列(">"),
                if(details1.getBegintime() > details2.getBegintime())
                    return 1;
                else {
                    return -1;
                }
            };
            //这里就会自动根据规则进行排序
            Collections.sort(serverlist, comparator);

            boolean fristServer = false;
            for (GameInfoVo.ServerListBean serverListBean:serverlist) {
                if (serverListBean.getBegintime() * 1000 > System.currentTimeMillis()){
                    if (!fristServer){
                        fristServer = true;
                        serverListBean.setTheNewest(true);
                    }else {
                        serverListBean.setTheNewest(false);
                    }
                }else {
                    serverListBean.setTheNewest(false);
                }
            }
            addAllData(serverlist);
        }else {
            ArrayList<GameInfoVo.ServerListBean> serverListBeans = new ArrayList<>();
            GameInfoVo.ServerListBean serverListBean = new GameInfoVo.ServerListBean();
            serverListBean.setServerid("000");
            serverListBean.setBegintime(0);
            serverListBean.setServername("请以游戏内实际开服为准");
            serverListBeans.add(serverListBean);
            addAllData(serverListBeans);
        }
    }

    View mHeaderView;

    private void addHeaderView() {
        mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_game_detail_server_head, null);
        addHeaderView(mHeaderView);
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


    private void getNetWorkData() {
        if (mViewModel != null) {

        }
    }
}
