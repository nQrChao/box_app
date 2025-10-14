package com.zqhy.app.core.view.main.new_game;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.core.data.model.game.GameAppointmentOpVo;
import com.zqhy.app.core.data.model.game.appointment.GameAppointmentVo;
import com.zqhy.app.core.data.model.new_game.NewGameAppointmentListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.view.main.holder.GameAppointmentTabItemHolder;
import com.zqhy.app.core.view.main.new0809.holder.NewNoDataItemHolder1;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.newproject.R;

import org.greenrobot.eventbus.EventBus;

/**
 * @author leeham2734
 * @date 2020/8/28-17:31
 * @description
 */
public class NewGameAppointmentFragment extends BaseListFragment<GameViewModel> {
    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(GameAppointmentVo.class, new GameAppointmentTabItemHolder(_mActivity))
                .bind(NoMoreDataVo.class, new NewNoDataItemHolder1(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this)
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
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "新游预约";
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("新游预约");
        setTitleLayout(LAYOUT_ON_CENTER);

        setPullRefreshEnabled(false);
        setLoadingMoreEnabled(false);

        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_3478f6,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            initData();
        });
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
            mViewModel.getNewGameAppointmentGameList(new OnBaseCallback<NewGameAppointmentListVo>() {
                @Override
                public void onSuccess(NewGameAppointmentListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null && !data.getData().isEmpty()) {
                                clearData();
                                addAllData(data.getData());
                                addData(new NoMoreDataVo());
                            } else {
                                addData(new EmptyDataVo());
                            }
                            notifyData();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        }
    }

    @Override
    public void onEvent(EventCenter eventCenter) {
        super.onEvent(eventCenter);
        if (eventCenter.getEventCode() == EventConfig.ACTION_GAME_APPOINTMENT_EVENT_CODE) {
            initData();
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
                public void onSuccess(GameAppointmentOpVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                String op = data.getData().getOp();
                                switch (op) {
                                    case "reserve":
                                        String toast = data.getMsg();
                                        showGameAppointmentCalendarReminder(item, toast);
                                        break;
                                    case "cancel":
                                        cancelGameAppointmentCalendarReminder(item);
                                        Toaster.show( data.getMsg());
                                        break;
                                }
                            }
                            EventBus.getDefault().post(new EventCenter(EventConfig.ACTION_GAME_APPOINTMENT_EVENT_CODE));
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

}
