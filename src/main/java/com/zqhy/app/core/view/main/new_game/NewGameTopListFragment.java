package com.zqhy.app.core.view.main.new_game;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.main.new_game.holder.NewGameTopItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/8/28-17:00
 * @description
 */
public class NewGameTopListFragment extends BaseListFragment<GameViewModel> {
    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(GameInfoVo.class, new NewGameTopItemHolder(_mActivity, true))
                .build()
                .setTag(R.id.tag_fragment, getParentFragment());
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
    protected String getUmengPageName() {
        return "一周新游Top10";
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
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

    private void initData() {
        if (mViewModel != null) {
            mViewModel.getNewGameTopList(new OnBaseCallback<GameListVo>() {

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
                            if (data.getData() != null && !data.getData().isEmpty()) {
                                int i = 0;
                                for (GameInfoVo gameInfoVo : data.getData()) {
                                    gameInfoVo.setIndexPosition(i);
                                    i++;
                                }
                                addAllData(data.getData());
                            } else {
                                addData(new EmptyDataVo());
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
