package com.zqhy.app.core.view.game;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.game.GameAppointmentListVo;
import com.zqhy.app.core.data.model.game.GameAppointmentOpVo;
import com.zqhy.app.core.data.model.game.appointment.GameAppointmentVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.game.holder.GameAppointmentItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class GameAppointmentListFragment extends BaseListFragment<GameViewModel> {

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(GameAppointmentListVo.DataBean.class, new GameAppointmentItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
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

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("新游预告");
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
        if (page < 0) {
            return;
        }
        page++;
        getAppointmentGameList();
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        initData();
    }

    private int page = 1, pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }


    private void initData() {
        page = 1;
        getAppointmentGameList();
    }


    /**
     * 获取新游列表数据
     */
    private void getAppointmentGameList() {
        if (mViewModel != null) {
            mViewModel.getAppointmentGameList(page, pageCount, new OnBaseCallback<GameAppointmentListVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(GameAppointmentListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null && !data.getData().isEmpty()) {
                                if (page == 1) {
                                    clearData();
                                }
                                addAllData(data.getData());
                            } else {
                                if (page == 1) {
                                    //show empty
                                    addData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                                } else {
                                    page = -1;
                                }
                                setListNoMore(true);
                                notifyData();
                            }
                        } else {
                            Toaster.show(data.getMsg());
                            //Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    /**
     * 操作说明, reserve: 预约; cancel: 取消预约
     *
     * @param gameid
     * @param item
     */
    public void gameAppointment(int gameid, GameAppointmentVo item) {
        if (mViewModel != null) {
            mViewModel.gameAppointment(gameid, new OnBaseCallback<GameAppointmentOpVo>() {
                @Override
                public void onSuccess(GameAppointmentOpVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                String op = data.getData().getOp();
                                switch (op) {
                                    case "reserve":
                                        String toast = data.getMsg();
                                        item.setGame_status(1);
                                        showGameAppointmentCalendarReminder(item,toast);
                                        break;
                                    case "cancel":
                                        item.setGame_status(0);
                                        cancelGameAppointmentCalendarReminder(item);
                                        Toaster.show(data.getMsg());
                                        //ToastT.success(_mActivity, data.getMsg());
                                        break;
                                    default:
                                        break;
                                }
                                notifyData();
                            }
                        } else {
                            Toaster.show(data.getMsg());
                            //Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }
}
