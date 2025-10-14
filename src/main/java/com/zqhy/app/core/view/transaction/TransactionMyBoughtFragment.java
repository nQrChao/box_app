package com.zqhy.app.core.view.transaction;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.data.model.transaction.TradeZeroBuyGoodInfoListVo1;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.core.view.main.holder.GameNoMoreItemHolder;
import com.zqhy.app.core.view.transaction.holder.TradeZeroBuyItemHolder1;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.newproject.R;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 */
public class TransactionMyBoughtFragment extends BaseFragment<TransactionViewModel> implements TradeZeroBuyItemHolder1.OnClickButtenListener {
    private XRecyclerView recyclerview;
    private FrameLayout mFlTitleRight;
    private TextView title_bottom_line;
    private TextView tv_look;
    BaseRecyclerAdapter adapter;

    @Override
    public Object getStateEventKey() {
        return null;
    }


    private final int ACTION_TRY_GAME_REQUEST_CODE = 10001;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transaction_my_bought;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        mFlTitleRight = findViewById(R.id.fl_title_right);
        title_bottom_line = findViewById(R.id.title_bottom_line);
        recyclerview = findViewById(R.id.recyclerview);
        tv_look = findViewById(R.id.tv_look);
        title_bottom_line.setVisibility(View.GONE);
        initActionBackBarAndTitle("我的淘号");

        tv_look.setOnClickListener(view -> {
            showTransactionTipDialog();
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setNestedScrollingEnabled(false);
        adapter = new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(TradeZeroBuyGoodInfoListVo1.TradeZeroBuyGoodInfo.class, new TradeZeroBuyItemHolder1(_mActivity, this))
                .bind(NoMoreDataVo.class, new GameNoMoreItemHolder(_mActivity))
                .build();

        recyclerview.setAdapter(adapter);

        recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initData();
            }

            @Override
            public void onLoadMore() {
                if (page < 0) {
                    return;
                }
                page++;
                getZeroBuyGoodList();
            }
        });
        initData();
    }

    private int page = 1, pageCount = 12;

    private void initData() {
        page = 1;
        getZeroBuyGoodList();
    }

    private void getZeroBuyGoodList() {
        if (page == 1) {
//            showLoading();
        }
        Map<String, String> params = new TreeMap<>();

        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        if (mViewModel != null) {
            mViewModel.getMyZeroBuyGoodList(params, new OnBaseCallback<TradeZeroBuyGoodInfoListVo1>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    recyclerview.loadMoreComplete();
                    recyclerview.refreshComplete();
                }

                @Override
                public void onSuccess(TradeZeroBuyGoodInfoListVo1 data) {
                    showSuccess();
                    if (data.getData() != null) {
                        if (page == 1) {
                            adapter.clear();
                        }
                        adapter.addAllData(data.getData());
                        adapter.notifyDataSetChanged();
                    } else {
                        if (page == 1) {
                            adapter.clear();
                            //empty data
                            adapter.addData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                        } else {
                            page = -1;
                            //no more data
                            adapter.addData(new NoMoreDataVo());
                        }
                        adapter.notifyDataSetChanged();
                        recyclerview.setNoMore(true);
                    }
                    if (page == 1) {
                        recyclerview.smoothScrollToPosition(0);
                    }
                }
            });
        }

    }

    private CustomDialog transactionTipDialog;

    private void showTransactionTipDialog() {
        if (transactionTipDialog == null) {
            transactionTipDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_transaction_zero_login, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            transactionTipDialog.setCancelable(false);
            transactionTipDialog.setCanceledOnTouchOutside(false);

            TextView btn_got_it = (TextView) transactionTipDialog.findViewById(R.id.btn_got_it);

            btn_got_it.setOnClickListener(view -> {
                if (transactionTipDialog != null) {
                    transactionTipDialog.dismiss();
                    transactionTipDialog = null;
                }
            });
            transactionTipDialog.show();
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ACTION_TRY_GAME_REQUEST_CODE) {

            }
        }
    }


    @Override
    public void onClickButten(View view, String gameid,String game_type) {
        startFragment(GameDetailInfoFragment.newInstance(Integer.parseInt(gameid), Integer.parseInt(game_type)));
    }
}
