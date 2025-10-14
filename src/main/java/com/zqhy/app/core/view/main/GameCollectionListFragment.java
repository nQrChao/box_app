package com.zqhy.app.core.view.main;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.game.GameCollectionHeader2Vo;
import com.zqhy.app.core.data.model.game.GameCollectionHeaderVo;
import com.zqhy.app.core.data.model.game.GameCollectionVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.game.holder.GameCollectionHeader2ItemHolder;
import com.zqhy.app.core.view.game.holder.GameCollectionHeaderItemHolder;
import com.zqhy.app.core.view.main.holder.GameNormalItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/26
 */

public class GameCollectionListFragment extends BaseListFragment<GameViewModel> {

    /**
     * container_id = 58
     *
     * @param container_id
     * @return
     */
    public static GameCollectionListFragment newInstance(int container_id) {
        GameCollectionListFragment fragment = new GameCollectionListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("container_id", container_id);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    protected String getUmengPageName() {
        return "合集详情页";
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_GAME_COLLECTION_LIST_STATE;
    }

    @Override
    protected String getStateEventTag() {
        return String.valueOf(container_id);
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.class, new GameNormalItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(GameCollectionHeaderVo.class, new GameCollectionHeaderItemHolder(_mActivity))
                .bind(GameCollectionHeader2Vo.class, new GameCollectionHeader2ItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }


    @Override
    protected boolean isAddStatusBarLayout() {
        return false;
    }

    int container_id;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            container_id = getArguments().getInt("container_id");
        }
        super.initView(state);
        //        initActionBackBarAndTitle("");
        setLoadingMoreEnabled(false);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getNetWorkData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getNetWorkData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getNetWorkData();
    }

    private void getNetWorkData() {
        if (mViewModel != null) {
            mViewModel.getGameContainer(container_id, new OnBaseCallback<GameCollectionVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(GameCollectionVo gameCollectionVo) {
                    if (gameCollectionVo != null) {
                        clearData();
                        if (gameCollectionVo.isStateOK() && gameCollectionVo.getData() != null) {
                            if (gameCollectionVo.getData().getList() != null) {
                                //                                setTitle(gameCollectionVo.getData().getTitle());
                                GameCollectionHeaderVo gameCollectionHeaderVo = new GameCollectionHeaderVo();
                                gameCollectionHeaderVo.setDescription(gameCollectionVo.getData().getDescription());
                                //                                addData(gameCollectionHeaderVo);

                                GameCollectionHeader2Vo gameCollectionHeader2Vo = new GameCollectionHeader2Vo();
                                gameCollectionHeader2Vo.setImage(gameCollectionVo.getData().getPic());
                                gameCollectionHeader2Vo.setTitle(gameCollectionVo.getData().getTitle());
                                gameCollectionHeader2Vo.setDescription(gameCollectionVo.getData().getDescription());
                                addData(gameCollectionHeader2Vo);

                                addAllData(gameCollectionVo.getData().getList());
                            } else {
                                addData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                            }
                        } else {
                            addData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                        }
                    }
                }
            });
        }
    }
}
