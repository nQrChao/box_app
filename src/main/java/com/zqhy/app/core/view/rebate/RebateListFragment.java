package com.zqhy.app.core.view.rebate;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.rebate.RebateEmptyDataVo;
import com.zqhy.app.core.data.model.rebate.RebateInfoVo;
import com.zqhy.app.core.data.model.rebate.RebateListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.rebate.helper.RebateHelper;
import com.zqhy.app.core.view.rebate.holder.RebateEmptyItemHolder;
import com.zqhy.app.core.view.rebate.holder.RebateItemHolder;
import com.zqhy.app.core.vm.rebate.RebateViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class RebateListFragment extends BaseListFragment<RebateViewModel> implements View.OnClickListener {

    public static final int GAME_REBATE_APPLY = 5100;


    public static RebateListFragment newInstance(int rebate_type) {
        RebateListFragment fragment = new RebateListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("rebate_type", rebate_type);
        fragment.setArguments(bundle);
        return fragment;
    }


    public static final int REBATE_TYPE_BT = 1;
    public static final int REBATE_TYPE_DISCOUNT = 2;
    public static final int REBATE_TYPE_H5 = 3;


    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_REBATE_LIST_STATE;
    }

    @Override
    protected String getUmengPageName() {
        if(rebate_type == REBATE_TYPE_BT){
            return "BT返利申请列表";
        }else if(rebate_type == REBATE_TYPE_DISCOUNT){
            return "折扣返利申请列表";
        }else if(rebate_type == REBATE_TYPE_H5){
            return "H5返利申请列表";
        }
        return "返利申请列表";
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(RebateEmptyDataVo.class, new RebateEmptyItemHolder(_mActivity))
                .bind(RebateInfoVo.class, new RebateItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    private int rebate_type;
    private String mTitle;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            rebate_type = getArguments().getInt("rebate_type");
            switch (rebate_type) {
                case REBATE_TYPE_BT:
                    mTitle = "BT返利申请";
                    break;
                case REBATE_TYPE_DISCOUNT:
                    mTitle = "折扣返利申请";
                    break;
                case REBATE_TYPE_H5:
                    mTitle = "H5返利申请";
                    break;
                default:
                    break;
            }
        }
        super.initView(state);
        initActionBackBarAndTitle(mTitle);
        setLoadingMoreEnabled(false);
        initHeaderView();
        getNetWorkData();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == GAME_REBATE_APPLY) {
            if (resultCode == RESULT_OK) {
                setRefresh();
            }
        }
    }

    private FrameLayout mFlBtRebate1;
    private FrameLayout mFlBtRebate2;
    private FrameLayout mFlBtRebate3;

    private void initHeaderView() {
        View headerView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_rebate_main_header, null);
        mFlBtRebate1 = headerView.findViewById(R.id.fl_bt_rebate_1);
        mFlBtRebate2 = headerView.findViewById(R.id.fl_bt_rebate_2);
        mFlBtRebate3 = headerView.findViewById(R.id.fl_bt_rebate_3);

        mFlBtRebate1.setOnClickListener(this);
        mFlBtRebate2.setOnClickListener(this);
        mFlBtRebate3.setOnClickListener(this);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ScreenUtil.getScreenWidth(_mActivity), ViewGroup.LayoutParams.WRAP_CONTENT);
        headerView.setLayoutParams(params);

        addHeaderView(headerView);
    }

    RebateHelper rebateHelper;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_bt_rebate_1:
                //图文教学
                start(RebateHelpFragment.newInstance(1));
                break;
            case R.id.fl_bt_rebate_2:
                //常见问题
                if (rebateHelper == null) {
                    rebateHelper = new RebateHelper();
                }
                rebateHelper.showBTRebateProDialog(_mActivity);
                break;
            case R.id.fl_bt_rebate_3:
                //申请记录
                start(RebateRecordListFragment.newInstance(rebate_type));
                break;
            default:
                break;
        }
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        registerObserver(Constants.EVENT_KEY_REBATE_LIST_DATA, String.valueOf(rebate_type), RebateListVo.class).observe(this, btRebateListVo -> {

        });
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
            mViewModel.getRebateListData(rebate_type, new OnBaseCallback<RebateListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    refreshAndLoadMoreComplete();
                }
                @Override
                public void onSuccess(RebateListVo rebateListVo) {
                    if (rebateListVo != null) {
                        if (rebateListVo.isStateOK()) {
                            clearData();
                            if (rebateListVo.getData() != null) {
                                addAllData(rebateListVo.getData());
                            } else {
                                addDataWithNotifyData(new RebateEmptyDataVo());
                            }
                        } else {
                            Toaster.show( rebateListVo.getMsg());
                        }
                    }
                }
            });
        }
    }

}
