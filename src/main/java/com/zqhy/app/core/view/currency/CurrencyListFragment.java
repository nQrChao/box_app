package com.zqhy.app.core.view.currency;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.user.CurrencyListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.currency.holder.CurrencyItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.user.UserViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/24
 */

public class CurrencyListFragment extends BaseListFragment<UserViewModel> {

    public static CurrencyListFragment newInstance(int type) {
        CurrencyListFragment fragment = new CurrencyListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_USER_CURRENCY_STATE;
    }


    @Override
    protected String getStateEventTag() {
        return String.valueOf(type);
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(CurrencyListVo.DataBean.class, new CurrencyItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }


    private int type;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
        super.initView(state);
        setLoadingMoreEnabled(false);
        addTargetHeaderView();
    }

    private void addTargetHeaderView() {
        View headerView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_currency_header_view, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addHeaderView(headerView);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getNetWorkData();
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

    public void getNetWorkData() {
        if (mViewModel != null) {
            mViewModel.getCurrencyListData(type, new OnBaseCallback<CurrencyListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(CurrencyListVo currencyListVo) {
                    if (currencyListVo != null) {
                        if (currencyListVo.isStateOK()) {
                            clearData();
                            if (currencyListVo.getData() != null && !currencyListVo.getData().isEmpty()) {
                                addAllData(currencyListVo.getData());
                            } else {
                                addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                            }
                        } else {
                            Toaster.show( currencyListVo.getMsg());
                        }
                    }
                }
            });
        }
    }

}
