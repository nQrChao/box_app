package com.zqhy.app.core.view.transaction.record;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoListVo;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoListVo1;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.transaction.TransactionGoodDetailFragment;
import com.zqhy.app.core.view.transaction.holder.TradeRecordItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.newproject.R;

import java.util.Map;
import java.util.TreeMap;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Administrator
 */
public class TransactionRecordListFragment extends BaseListFragment<TransactionViewModel> {

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TransactionFragment1","TransactionRecordListFragment-----scene"+scene);
    }

    public static TransactionRecordListFragment newInstance(String scene) {
        TransactionRecordListFragment fragment = new TransactionRecordListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("scene", scene);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        BaseFragment targetFragment = getParentFragment() == null ? this : (BaseFragment) getParentFragment();
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(TradeGoodInfoVo.class, new TradeRecordItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, targetFragment)
                .setTag(R.id.tag_sub_fragment, this);
    }



    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    private String scene = "user_all";

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            scene = getArguments().getString("scene");
        }
        super.initView(state);
        mRecyclerView.setBackgroundColor(Color.parseColor("#f2f2f2"));
        setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof TradeGoodInfoVo) {
                TradeGoodInfoVo tradeGoodInfoVo = (TradeGoodInfoVo) data;
                start(TransactionGoodDetailFragment.newInstance(tradeGoodInfoVo.getGid(), tradeGoodInfoVo.getGameid(), tradeGoodInfoVo.getGoods_pic()));
            }
        });
    }

    @Override
    public void start(ISupportFragment toFragment) {
        if (getParentFragment() != null && getParentFragment() instanceof SupportFragment) {
            ((SupportFragment) getParentFragment()).start(toFragment);
        } else {
            super.start(toFragment);
        }
    }

    @Override
    public void startForResult(ISupportFragment toFragment, int requestCode) {
        if (getParentFragment() != null && getParentFragment() instanceof SupportFragment) {
            ((SupportFragment) getParentFragment()).startForResult(toFragment, requestCode);
        } else {
            super.startForResult(toFragment, requestCode);
        }
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

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (page < 0) {
            return;
        }
        loadMore();
    }

    private int page = 1, pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    private void initData() {
        page = 1;
        getTradeRecordList();
    }

    private void loadMore() {
        page++;
        getTradeRecordList();
    }

    private void getTradeRecordList() {
        if (mViewModel != null) {
            Map<String, String> params = new TreeMap<>();
            params.clear();

            if (!TextUtils.isEmpty(scene)) {
                params.put("scene", scene);
            }
            params.put("page", String.valueOf(page));
            params.put("pagecount", String.valueOf(pageCount));

            if ("user_collect".equals(scene)){
                mViewModel.getTradeCollectionRecordList(params, new OnBaseCallback<TradeGoodInfoListVo1>() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        refreshAndLoadMoreComplete();
                    }

                    @Override
                    public void onSuccess(TradeGoodInfoListVo1 data) {
                        showSuccess();
                        if (data != null) {
                            if (data.isStateOK()) {
                                if (data.getData() != null) {
                                    if (page == 1) {
                                        clearData();
                                    }
                                    addAllData(data.getData());
                                } else {
                                    if (page == 1) {
                                        clearData();
                                        addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                                    } else {
                                        page = -1;
                                    }
                                    setListNoMore(true);
                                }

                            } else {
                                Toaster.show(data.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        super.onFailure(message);
                        showErrorTag1();
                    }
                });
            }else {
                mViewModel.getTradeRecordList(params, new OnBaseCallback<TradeGoodInfoListVo>() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        refreshAndLoadMoreComplete();
                    }

                    @Override
                    public void onSuccess(TradeGoodInfoListVo data) {
                        showSuccess();
                        if (data != null) {
                            if (data.isStateOK()) {
                                if (data.getData() != null) {
                                    if (page == 1) {
                                        clearData();
                                    }
                                    // 校对倒计时
                                    long curTime = System.currentTimeMillis();
                                    for (TradeGoodInfoVo goodInfoVo : data.getData()) {
                                        goodInfoVo.setEndTime(goodInfoVo.getCount_down() * 1000 + curTime);
                                    }
                                    addAllData(data.getData());
                                } else {
                                    if (page == 1) {
                                        clearData();
                                        addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                                    } else {
                                        page = -1;
                                    }
                                    setListNoMore(true);
                                }

                            } else {
                                Toaster.show(data.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        super.onFailure(message);
                        showErrorTag1();
                    }
                });
            }
        }
    }

    public void refreshData() {
        initData();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TradeRecordItemHolder.action_modify_good:
                case TradeRecordItemHolder.action_buy_good:
                    //刷新页面
                    initData();
                    break;
//                case action_withdrawal:
//                    getUserPtbBalance();
//                    break;
//                case action_good_detail:
//                    isAnyDataChange = true;
//                    break;
                default:
                    break;
            }
        }


    }

}
