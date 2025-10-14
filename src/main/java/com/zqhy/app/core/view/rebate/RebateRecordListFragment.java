package com.zqhy.app.core.view.rebate;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
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
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.rebate.RebateRecordListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.rebate.helper.RebateHelper;
import com.zqhy.app.core.view.rebate.holder.RebateRecordItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.rebate.RebateViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/16
 */

public class RebateRecordListFragment extends BaseListFragment<RebateViewModel> {

    public static final int ACTION_REBATE_DETAIL = 0x4425;

    public static RebateRecordListFragment newInstance(int rebate_type) {
        RebateRecordListFragment fragment = new RebateRecordListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("rebate_type", rebate_type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected String getUmengPageName() {
        return "返利申请记录";
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_REBATE_RECORD_LIST_STATE;
    }

    @Override
    protected String getStateEventTag() {
        return String.valueOf(rebate_type);
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(RebateRecordListVo.DataBean.class, new RebateRecordItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_first, rebate_type)
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    private int rebate_type;
    private String mTitle;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            rebate_type = getArguments().getInt("rebate_type");
            switch (rebate_type) {
                case RebateListFragment.REBATE_TYPE_BT:
                    mTitle = "BT返利申请记录";
                    break;
                case RebateListFragment.REBATE_TYPE_DISCOUNT:
                    mTitle = "折扣返利申请记录";
                    break;
                case RebateListFragment.REBATE_TYPE_H5:
                    mTitle = "H5返利申请记录";
                    break;
                default:
                    break;
            }
        }
        super.initView(state);
        initActionBackBarAndTitle(mTitle);
        addHeaderView();
        getNetWorkData();
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    RebateHelper rebateHelper;

    private void addHeaderView() {
        View headerView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_rebate_record_header, null);
        FrameLayout mFlCommonFq = headerView.findViewById(R.id.fl_common_fq);
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(30 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.white));
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
        mFlCommonFq.setBackground(gd);

        mFlCommonFq.setOnClickListener(view -> {
            if (rebateHelper == null) {
                rebateHelper = new RebateHelper();
            }
            rebateHelper.showBTRebateProDialog(_mActivity);
        });

        headerView.setLayoutParams(new ViewGroup.LayoutParams(ScreenUtil.getScreenWidth(_mActivity), ViewGroup.LayoutParams.WRAP_CONTENT));
        addHeaderView(headerView);
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
        if (page < 0) {
            return;
        }
        getMoreNetWorkData();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ACTION_REBATE_DETAIL) {
                getNetWorkData();
            }
        }
    }

    private int page = 1;
    private int pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    private void getNetWorkData() {
        if (mViewModel != null) {
            page = 1;
            getRebateRecordListData();
        }
    }

    private void getMoreNetWorkData() {
        if (mViewModel != null) {
            page++;
            getRebateRecordListData();
        }
    }

    private void getRebateRecordListData() {
        mViewModel.getRebateRecordListData(rebate_type, page, pageCount, new OnBaseCallback<RebateRecordListVo>() {
            @Override
            public void onAfter() {
                super.onAfter();
                refreshAndLoadMoreComplete();
            }

            @Override
            public void onSuccess(RebateRecordListVo rebateRecordListVo) {
                if (rebateRecordListVo != null) {
                    if (rebateRecordListVo.isStateOK()) {
                        if (rebateRecordListVo.getData() != null && !rebateRecordListVo.getData().isEmpty()) {
                            if (page == 1) {
                                clearData();
                            }
                            addAllData(rebateRecordListVo.getData());
                        } else {
                            if (page == 1) {
                                //empty data
                                clearData();
                                addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                            } else {
                                page = -1;
                            }
                            setListNoMore(true);
                        }
                    } else {
                        Toaster.show( rebateRecordListVo.getMsg());
                    }
                }
            }
        });
    }
}
