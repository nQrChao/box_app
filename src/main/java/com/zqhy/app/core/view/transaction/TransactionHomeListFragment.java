package com.zqhy.app.core.view.transaction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoListVo1;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoVo1;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.main.holder.GameNoMoreItemHolder;
import com.zqhy.app.core.view.transaction.holder.TradeItemHolder1;
import com.zqhy.app.core.view.transaction.util.CustomPopWindow;
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
public class TransactionHomeListFragment extends BaseListFragment<TransactionViewModel> {

    public static TransactionHomeListFragment newInstance(String goods_type, String gamename, String gameid) {
        TransactionHomeListFragment fragment = new TransactionHomeListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goods_type", goods_type);
        bundle.putString("gamename", gamename);
        bundle.putString("gameid", gameid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        BaseFragment targetFragment = getParentFragment() == null ? this : (BaseFragment) getParentFragment();
        return new BaseRecyclerAdapter.Builder<>()
                .bind(TradeGoodInfoVo1.class, new TradeItemHolder1(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(NoMoreDataVo.class, new GameNoMoreItemHolder(_mActivity))
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

    private String goods_type = "0";
    private String gamename, gameid;
    //1 最新上架 2 价格升序 3 价格降序 4 近期成交
    int selected = 1;
    private final int action_transaction_good_detail = 0x566;
    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            goods_type = getArguments().getString("goods_type");
            gamename = getArguments().getString("gamename");
            gameid = getArguments().getString("gameid");
        }
        super.initView(state);

        View inflate = LayoutInflater.from(_mActivity).inflate(R.layout.item_transaction_home_top, null);

        LinearLayout mLlHeadRoot = inflate.findViewById(R.id.ll_root);
        tv_select = inflate.findViewById(R.id.tv_select);

        mLlHeadRoot.setLayoutParams(new LinearLayout.LayoutParams(ScreenUtil.getScreenWidth(_mActivity), LinearLayout.LayoutParams.WRAP_CONTENT));

        inflate.findViewById(R.id.layout_select).setOnClickListener(v -> {
            showPopListView(inflate.findViewById(R.id.layout_select));
        });

        inflate.findViewById(R.id.layout_search).setOnClickListener(v -> {
            //搜索
            startFragment(new TransactionSearchFragment1());
        });


        addHeaderView(inflate);

        setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof TradeGoodInfoVo1) {
                TradeGoodInfoVo1 tradeGoodInfoVo = (TradeGoodInfoVo1) data;
                startForResult(TransactionGoodDetailFragment.newInstance(tradeGoodInfoVo.getGid(), tradeGoodInfoVo.getGameid()), action_transaction_good_detail);
            }
        });
    }

    private TextView tv_item1;
    private TextView tv_item2;
    private TextView tv_item3;
    private TextView tv_item4;
    private TextView tv_select;
    private void showPopListView(View v) {
        View contentView = LayoutInflater.from(_mActivity).inflate(R.layout.pop_transaction_select, null);
        tv_item1 = ((TextView) contentView.findViewById(R.id.tv_item1));
        tv_item2 = ((TextView) contentView.findViewById(R.id.tv_item2));
        tv_item3 = ((TextView) contentView.findViewById(R.id.tv_item3));
        tv_item4 = ((TextView) contentView.findViewById(R.id.tv_item4));
        if ("2".equals(goods_type)){
            contentView.findViewById(R.id.view_line_3).setVisibility(View.GONE);
            tv_item4.setVisibility(View.GONE);
        }
        switch (selected) {
            case 1:
                tv_item1.setTextColor(Color.parseColor("#232323"));
                tv_item2.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item3.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item4.setTextColor(Color.parseColor("#9b9b9b"));
                break;
            case 2:
                tv_item1.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item2.setTextColor(Color.parseColor("#232323"));
                tv_item3.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item4.setTextColor(Color.parseColor("#9b9b9b"));
                break;
            case 3:
                tv_item1.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item2.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item3.setTextColor(Color.parseColor("#232323"));
                tv_item4.setTextColor(Color.parseColor("#9b9b9b"));
                break;
            case 4:
                tv_item1.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item2.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item3.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item4.setTextColor(Color.parseColor("#232323"));
                break;
        }
        //1 最新上架 2 价格升序 3 价格降序 4 近期成交
        CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(_mActivity)
                .setView(contentView)
                .setFocusable(true)
                .setBgDarkAlpha(0.7F)
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                .setOutsideTouchable(true)
                .create();
        popWindow.setBackgroundAlpha(0.7f);
        tv_item1.setOnClickListener(view -> {
            selected = 1;
            page = 1;
            scene = "normal";
            orderby = null;
            tv_select.setText("最新上架");
            popWindow.dissmiss();
            getTradeGoodList();
        });
        tv_item2.setOnClickListener(view -> {
            selected = 2;
            page = 1;
            scene = "normal";
            tv_select.setText("价格升序");
            orderby = "price_up";
            popWindow.dissmiss();
            getTradeGoodList();
        });
        tv_item3.setOnClickListener(view -> {
            selected = 3;
            page = 1;
            scene = "normal";
            tv_select.setText("价格降序");
            orderby = "price_down";
            popWindow.dissmiss();
            getTradeGoodList();
        });
        tv_item4.setOnClickListener(view -> {
            selected = 4;
            page = 1;
            orderby = null;
            scene = "trends";
            tv_select.setText("近期成交");
            popWindow.dissmiss();
            getTradeGoodList();
        });
        popWindow.showAsDropDown(v, -v.getWidth() / 2, 0);//指定view正下方
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
        getTradeGoodList();
    }

    private void loadMore() {
        page++;
        getTradeGoodList();
    }

    private String scene = "normal";
    private String orderby;
    private String listTag = "";
    private void getTradeGoodList() {
        Map<String, String> params = new TreeMap<>();
        params.clear();

        if (!TextUtils.isEmpty(scene)) {
            params.put("scene", scene);
        }
        if (!TextUtils.isEmpty(orderby)) {
            params.put("orderby", orderby);
        }
        if (!TextUtils.isEmpty(gameid)) {
            params.put("gameid", gameid);
        }
        params.put("goods_type", String.valueOf(goods_type));
        params.put("pic", "multiple");

        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        if (page == 1) {
//            showLoading();
            setListNoMore(false);
        } else {
            if (!TextUtils.isEmpty(listTag)) {
                params.put("r_time", listTag);
            }
        }

        if (mViewModel != null) {
            mViewModel.getTradeGoodList1(params, new OnBaseCallback<TradeGoodInfoListVo1>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    showSuccess();
                }

                @Override
                public void onSuccess(TradeGoodInfoListVo1 data) {
                    showSuccess();
                    setTradeGoodList(data);
                }
            });
        }
    }

    private void setTradeGoodList(TradeGoodInfoListVo1 data) {
        if (data != null) {
            if (data.isStateOK()) {
                if (data.getData() != null) {
                    if (page == 1) {
                        mDelegateAdapter.clear();
                    }
                    for (TradeGoodInfoVo1 tradeGoodInfoBean : data.getData()) {
                        if (scene.equals("normal")) {
                            tradeGoodInfoBean.setIsSelled(1);
                        } else if (scene.equals("trends")) {
                            tradeGoodInfoBean.setIsSelled(2);
                        }
                    }
                    mDelegateAdapter.addAllData(data.getData());
                    mDelegateAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mDelegateAdapter.clear();
                        //empty data
                        mDelegateAdapter.addData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                    } else {
                        page = -1;
                        //no more data
                        mDelegateAdapter.addData(new NoMoreDataVo());
                    }
                    mDelegateAdapter.notifyDataSetChanged();
                    mRecyclerView.setNoMore(true);
                }
                if (page == 1) {
                    listTag = data.getMsg();
                    mRecyclerView.smoothScrollToPosition(0);
//                    mAppBarLayout.setExpanded(true, true);
                }
            } else {
                Toaster.show(data.getMsg());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case action_transaction_good_detail:
                    initData();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                String back = data.getString("back", "");
                if ("back".equals(back)) {
                    Log.d("TranMainFrag", "back  popChild()");
                    popChild();
                }
            }
            switch (requestCode) {
                case action_transaction_good_detail:
                    initData();
                    break;
                default:
                    break;
            }
        }
    }
}
