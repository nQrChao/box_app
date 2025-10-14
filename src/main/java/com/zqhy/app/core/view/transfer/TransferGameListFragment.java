package com.zqhy.app.core.view.transfer;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.transfer.TransferCountVo;
import com.zqhy.app.core.data.model.transfer.TransferGameItemVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.transfer.holder.TransferCountHolder;
import com.zqhy.app.core.view.transfer.holder.TransferGameHolder;
import com.zqhy.app.core.view.transfer.holder.TransferItemHolder;
import com.zqhy.app.core.vm.transfer.TransferViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class TransferGameListFragment extends BaseListFragment<TransferViewModel> {

    public static TransferGameListFragment newInstance(int gameid, int game_type) {
        TransferGameListFragment fragment = new TransferGameListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("gameid", gameid);
        bundle.putInt("game_type", game_type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_TRANSFER_GAME_STATE;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.class, new TransferGameHolder(_mActivity))
                .bind(TransferCountVo.class, new TransferCountHolder(_mActivity))
                .bind(TransferGameItemVo.TransferRewardVo.class, new TransferItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    private int gameid, game_type;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gameid = getArguments().getInt("gameid");
            game_type = getArguments().getInt("game_type");
        }
        super.initView(state);
        initActionBackBarAndTitle("");
        getNetWorkData();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 202) {
            setFragmentResult(resultCode, null);
            pop();
        }
    }

    private String mTitle;

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


    /**
     * 跳转申请转游
     *
     * @param item
     */
    public void transferApply(TransferGameItemVo.TransferRewardVo item) {
        if (item != null) {
            String transfer_requirements = "";
            if (!TextUtils.isEmpty(item.getC2_more())) {
                transfer_requirements = item.getC2_more();
            }
            startForResult(TransferActionFragment.newInstance(item.getIndex_id(), mTitle, item.getReward_content(), transfer_requirements), 200);
        }
    }


    /**
     * 获取数据
     */
    private void getNetWorkData() {
        if (mViewModel != null) {
            mViewModel.getTransferInfoData(gameid, new OnBaseCallback<TransferGameItemVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(TransferGameItemVo transferGameItemVo) {
                    if (transferGameItemVo != null) {
                        if (transferGameItemVo.isStateOK()) {
                            TransferGameItemVo.DataBean dataBean = transferGameItemVo.getData();
                            clearData();
                            if (dataBean != null) {
                                if (dataBean.getGameinfo() != null) {
                                    addData(dataBean.getGameinfo());
                                    mTitle = dataBean.getGameinfo().getGamename();
                                    setTitleText(mTitle);
                                }
                                addData(new TransferCountVo(dataBean.getUser_points()));

                                if (dataBean.getTransfer_reward_list() != null) {
                                    addAllData(dataBean.getTransfer_reward_list());
                                }
                            }
                        } else {
                            Toaster.show( transferGameItemVo.getMsg());
                        }
                    }
                }
            });
        }
    }

}
