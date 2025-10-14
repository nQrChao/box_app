package com.zqhy.app.core.view.transaction;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chaoji.other.hjq.toast.Toaster;
import com.google.android.material.appbar.AppBarLayout;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoListVo1;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoVo1;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.main.holder.GameNoMoreItemHolder;
import com.zqhy.app.core.view.transaction.holder.TradeItemHolder1;
import com.zqhy.app.core.view.transaction.util.AppBarStateChangeListener;
import com.zqhy.app.core.view.transaction.util.CustomPopWindow;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.newproject.R;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 */
public class TransactionOneBuyFragment extends BaseFragment<TransactionViewModel> {
    private final int action_transaction_good_detail = 0x566;
    private FrameLayout mFlTitleRight;
    private FrameLayout mFlTitleRight1;
    private TextView title_bottom_line;
    private ImageView iv_back_writer;
    private TextView tv_select;
    private LinearLayout layout_select;
    private LinearLayout top_layout;
    private FrameLayout layout_top;
    private XRecyclerView mXrecyclerView;
    BaseRecyclerAdapter mTransactionListAdapter;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    TextView mTitle;
    @Override
    public Object getStateEventKey() {
        return null;
    }


    private final int ACTION_TRY_GAME_REQUEST_CODE = 10001;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transaction_onebuy;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        mFlTitleRight = findViewById(R.id.fl_title_right);
        mFlTitleRight1 = findViewById(R.id.fl_title_right1);
        title_bottom_line = findViewById(R.id.title_bottom_line);
        title_bottom_line.setVisibility(View.GONE);
        layout_select = findViewById(R.id.layout_select);
        top_layout = findViewById(R.id.top_layout);
        tv_select = findViewById(R.id.tv_select);
        iv_back_writer = findViewById(R.id.iv_back_writer);
        layout_top = findViewById(R.id.layout_top);
        mXrecyclerView = findViewById(R.id.xrecyclerView);
        layout_select.setOnClickListener(view -> {
            showPopListView(layout_select, R.layout.pop_transaction_one_select);
        });
//        setActionBackBar(R.mipmap.ic_actionbar_back_white);
        mTitle = findViewById(R.id.tv_title);
        initActionBackBarAndTitle("1折捡漏");
        mFlTitleRight.addView(getTitleRightView());
        mFlTitleRight1.addView(getTitleRightView1());
        iv_back_writer.setOnClickListener(view -> {
          pop();
        });

        toolbar = findViewById(R.id.toolbar);
        appBarLayout = findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    toolbar.setVisibility(View.INVISIBLE);
                    top_layout.setPadding(ScreenUtil.dp2px(activity, 20),0,ScreenUtil.dp2px(activity, 20), ScreenUtil.dp2px(activity, 15));
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    top_layout.setPadding(ScreenUtil.dp2px(activity, 20),ScreenUtil.dp2px(activity, 15),ScreenUtil.dp2px(activity, 20), ScreenUtil.dp2px(activity, 15));
                    toolbar.setVisibility(View.VISIBLE);
                } else {
                    //中间状态
                    top_layout.setPadding(ScreenUtil.dp2px(activity, 20),0,ScreenUtil.dp2px(activity, 20), ScreenUtil.dp2px(activity, 15));
                    toolbar.setVisibility(View.INVISIBLE);
                }
            }
        });

        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            float v =(-verticalOffset * 1.0f) / (appBarLayout.getTotalScrollRange()-150);
            if(v>1.0f){
                v = 1.0f;
            }
            layout_top.setAlpha(1.5f-v);
        });


        initList();
        initData();
    }


    public int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }


    private void initData() {
        page = 1;
        getTradeGoodList();
    }

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        mXrecyclerView.setLayoutManager(layoutManager);

        mTransactionListAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(TradeGoodInfoVo1.class, new TradeItemHolder1(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(NoMoreDataVo.class, new GameNoMoreItemHolder(_mActivity))
                .build();
        mXrecyclerView.setAdapter(mTransactionListAdapter);

        mXrecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
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
                getTradeGoodList();
            }
        });
        mTransactionListAdapter.setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof TradeGoodInfoVo1) {
                TradeGoodInfoVo1 tradeGoodInfoVo = (TradeGoodInfoVo1) data;
                startForResult(TransactionGoodDetailFragment.newInstance(tradeGoodInfoVo.getGid(), tradeGoodInfoVo.getGameid(), tradeGoodInfoVo.getPic().get(0).getPic_path()), action_transaction_good_detail);
            }
        });


    }


    //1 最新上架 2 价格升序 3 价格降序 4 按售价比
    int selected = 1;
    private int page = 1, pageCount = 12;
    private TextView tv_item1;
    private TextView tv_item2;
    private TextView tv_item3;
    private TextView tv_item4;
    private String scene = "normal";
    private String orderby;
    private String listTag = "";
    /**
     * 指定view下方弹出
     * 方法名@：showAsDropDown(v, 0, 10)
     * resource:PopWindow 布局
     */
    private void showPopListView(View v, int resource) {
        if (resource == 0) return;
        View contentView = LayoutInflater.from(_mActivity).inflate(resource, null);
        tv_item1 = ((TextView) contentView.findViewById(R.id.tv_item1));
        tv_item2 = ((TextView) contentView.findViewById(R.id.tv_item2));
        tv_item3 = ((TextView) contentView.findViewById(R.id.tv_item3));
        tv_item4 = ((TextView) contentView.findViewById(R.id.tv_item4));

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
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
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
            orderby = "profit_rate_asc";
            scene = "normal";
            tv_select.setText("售价比");
            popWindow.dissmiss();
            getTradeGoodList();
        });
        popWindow.showAsDropDown(v, -v.getWidth() / 2, 0);//指定view正下方
    }

    private void getTradeGoodList() {
        Map<String, String> params = new TreeMap<>();
        params.clear();

        if (!TextUtils.isEmpty(scene)) {
            params.put("scene", scene);
        }
        if (!TextUtils.isEmpty(orderby)) {
            params.put("orderby", orderby);
        }

        params.put("pic", "multiple");
        params.put("one_discount", "yes");

        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        if (page == 1) {
//            showLoading();
            mXrecyclerView.setNoMore(false);
        }else {
            if (!TextUtils.isEmpty(listTag)) {
                params.put("r_time", listTag);
            }
        }

        if (mViewModel != null) {
            mViewModel.getTradeGoodList1(params, new OnBaseCallback<TradeGoodInfoListVo1>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    mXrecyclerView.loadMoreComplete();
                    mXrecyclerView.refreshComplete();
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    showSuccess();
                    showErrorTag1();
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
                        mTransactionListAdapter.clear();
                    }
                    for (TradeGoodInfoVo1 tradeGoodInfoBean : data.getData()) {
                        if (scene.equals("normal")) {
                            tradeGoodInfoBean.setIsSelled(1);
                        } else if (scene.equals("trends")) {
                            tradeGoodInfoBean.setIsSelled(2);
                        }
                    }
                    mTransactionListAdapter.addAllData(data.getData());
                    mTransactionListAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        mTransactionListAdapter.clear();
                        //empty data
                        mTransactionListAdapter.addData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                    } else {
                        page = -1;
                        //no more data
                        mTransactionListAdapter.addData(new NoMoreDataVo());
                    }
                    mTransactionListAdapter.notifyDataSetChanged();
                    mXrecyclerView.setNoMore(true);
                }
                if (page == 1) {
                    listTag = data.getMsg();
                    mXrecyclerView.smoothScrollToPosition(0);
//                    mAppBarLayout.setExpanded(true, true);
                }
            } else {
                Toaster.show(data.getMsg());
            }
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

    private CustomDialog transactionTipDialog;

    private void showTransactionTipDialog() {
        if (transactionTipDialog == null) {
            transactionTipDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_transaction_one_thxz, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
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

    private View getTitleRightView() {
        LinearLayout linearLayout = new LinearLayout(_mActivity);
        TextView tv = new TextView(_mActivity);
        tv.setText("捡漏须知");
        tv.setPadding((int) (8 * density), 0, (int) (8 * density), 0);
        tv.setTextSize(12);
        tv.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));


        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(60 * density);


        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.white));

        tv.setGravity(Gravity.CENTER);
        tv.setBackground(gd);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (24 * density));
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(0, 0, (int) (16 * density), 0);
        tv.setLayoutParams(params);


        tv.setOnClickListener(view -> {
            showTransactionTipDialog();
        });

        linearLayout.addView(tv);

        return linearLayout;
    }
    private View getTitleRightView1() {
        LinearLayout linearLayout = new LinearLayout(_mActivity);
        TextView tv = new TextView(_mActivity);
        tv.setText("捡漏须知");
        tv.setPadding((int) (8 * density), 0, (int) (8 * density), 0);
        tv.setTextSize(12);
        tv.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));


        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(60 * density);
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_232323));

        tv.setGravity(Gravity.CENTER);
        tv.setBackground(gd);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (24 * density));
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(0, 0, (int) (16 * density), 0);
        tv.setLayoutParams(params);


        tv.setOnClickListener(view -> {
            showTransactionTipDialog();
        });

        linearLayout.addView(tv);

        return linearLayout;
    }

}
