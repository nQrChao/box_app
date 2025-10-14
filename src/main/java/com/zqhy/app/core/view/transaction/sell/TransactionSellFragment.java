package com.zqhy.app.core.view.transaction.sell;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chaoji.other.hjq.toast.Toaster;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.data.model.transaction.TradeMySellInfoVo;
import com.zqhy.app.core.data.model.transaction.TradeMySellListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.StringUtil;
import com.zqhy.app.core.view.main.holder.GameNoMoreItemHolder;
import com.zqhy.app.core.view.recycle_new.XhNewRecycleMainFragment;
import com.zqhy.app.core.view.transaction.holder.TradeMySellItemHolder;
import com.zqhy.app.core.view.transaction.util.CustomPopWindow;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.newproject.R;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 */
public class TransactionSellFragment extends BaseFragment<TransactionViewModel> implements View.OnClickListener {

    public static final int REQUEST_CODE = 0x00000011;

    public static TransactionSellFragment newInstance() {
        return newInstance("");
    }

    public static TransactionSellFragment newInstance(String gid) {
        TransactionSellFragment fragment = new TransactionSellFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gid", gid);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int core;

    public static TransactionSellFragment newInstance(int core) {
        TransactionSellFragment fragment = new TransactionSellFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("core", core);
        fragment.setArguments(bundle);
        return fragment;
    }

    public TransactionSellFragment() {
        // Required empty public constructor
    }

    private static final int action_choose_game = 0x7771;
    private static final int action_choose_game_xh = 0x7774;
    private static final int action_write_title = 0x7772;
    private static final int action_write_description = 0x7773;
    private static final int action_write_secondary_password = 0x7775;


    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "卖号页";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transaction_sell;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    private FrameLayout mFlTitleRight;
    private TextView title_bottom_line;
    private TextView tv_title;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            core = getArguments().getInt("core");
        }
        super.initView(state);
        initActionBackBarAndTitle("我要卖号");
        title_bottom_line = findViewById(R.id.title_bottom_line);
        tv_title = findViewById(R.id.tv_title);
        title_bottom_line.setVisibility(View.GONE);
        setActionBackBar(R.mipmap.ic_actionbar_back_white);
        mFlTitleRight = findViewById(R.id.fl_title_right);
        mFlTitleRight.addView(getTitleRightView());
        tv_title.setTextColor(Color.parseColor("#ffffff"));
        bindViews();
        showSuccess();
    }


    private View getTitleRightView() {
        LinearLayout linearLayout = new LinearLayout(_mActivity);
        TextView tv = new TextView(_mActivity);
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_audit_transaction_sell_3);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
        tv.setCompoundDrawablePadding(5);
        tv.setCompoundDrawables(drawable, null, null, null);
        tv.setText("无法出售");
        tv.setPadding((int) (8 * density), 0, (int) (8 * density), 0);
        tv.setTextSize(12);
        tv.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));

        tv.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (24 * density));
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(0, 0, (int) (16 * density), 0);
        tv.setLayoutParams(params);


        tv.setOnClickListener(view -> {
            showPopListViewDicker();
        });


        linearLayout.addView(tv);
        return linearLayout;
    }

    CustomPopWindow popWindowDicker;

    /**
     * 屏幕底部弹出
     * 方法名@：showAtLocation(contentView, Gravity.BOTTOM, 0, 0)
     * resource:PopWindow 布局
     */
    private void showPopListViewDicker() {
        View contentView = LayoutInflater.from(_mActivity).inflate(R.layout.pop_transaction_mysell_1, null);
        TextView tv_close = contentView.findViewById(R.id.tv_close);
        TextView tv_do = contentView.findViewById(R.id.tv_do);

        //处理popWindow 显示内容
        //创建并显示popWindow
        popWindowDicker = new CustomPopWindow.PopupWindowBuilder(_mActivity)
                .enableOutsideTouchableDissmiss(false)
                .enableBackgroundDark(true)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                .create();
        popWindowDicker.setBackgroundAlpha(0.5f);
        popWindowDicker.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        tv_close.setOnClickListener(view -> {
            popWindowDicker.dissmiss();
            goKefuCenter();
        });

        tv_do.setOnClickListener(view -> popWindowDicker.dissmiss());
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
    }

    private XRecyclerView mXrecyclerView;
    private LinearLayout layout_select;
    private LinearLayout layout_recovery;
    private TextView tv_select;
    private EditText et_search;

    private void bindViews() {
        mXrecyclerView = findViewById(R.id.xrecyclerView);
        layout_recovery = findViewById(R.id.layout_recovery);
        layout_select = findViewById(R.id.layout_select);
        tv_select = findViewById(R.id.tv_select);
        et_search = findViewById(R.id.et_search);
        layout_select.setOnClickListener(view -> {
            showPopListView(layout_select, R.layout.pop_transaction_mysell_select);
        });
        layout_recovery.setOnClickListener(view -> {
            if (checkLogin()) {
                startFragment(new XhNewRecycleMainFragment());
            }
        });
        initList();
        initData();

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    fristTime = 0;
                    count = 0;
                    kw = "";
                    initData();
                    return;
                }
                Log.d("MysellF", "count :" + count);
                kw = editable.toString().trim();
                if (count == 0) {
                    Log.d("MysellF", "count == 0");
                    fristTime = System.currentTimeMillis();
                    Log.d("MysellF", "fristTime:" + fristTime);
                }
                if (count == 1) {
                    Log.d("MysellF", "count == 1");
                    searchTime = System.currentTimeMillis();
                    Log.d("MysellF", "searchTime:" + searchTime);
                }
                count++;
                Log.d("MysellF", "count :" + count);
                if (count == 1) {
                    Log.d("MysellF", "count++ 后 count == 1 第一次查询");
                    searchTime = System.currentTimeMillis();
                    Log.d("MysellF", "调接口 searchTime:" + searchTime);
                    initData();
                    return;
                }
                if (count == 2) {
                    Log.d("MysellF", "连续查询");
                    if ((searchTime - fristTime) > 500) {
                        Log.d("MysellF", "searchTime:" + searchTime);
                        Log.d("MysellF", "fristTime:" + fristTime);
                        Log.d("MysellF", "连续查询间隔>500");
                        //下一个字间隔>500ms,重置
                        initData();
                        count = 0;
                        fristTime = 0;
                        searchTime = 0;
                        return;
                    } else {
                        Log.d("MysellF", "连续查询间隔<500");
                        //下一个字间隔<500ms
                        endTime = System.currentTimeMillis();
                        Log.d("MysellF", "endTime:" + endTime);
                    }
                }
                Log.d("MysellF", "endTime - fristTime:" + (endTime - fristTime));
                if ((endTime - fristTime) > 500) {
                    Log.d("MysellF", "endTime:" + endTime);
                    Log.d("MysellF", "fristTime:" + fristTime);
                    count = 0;
                    fristTime = 0;
                    searchTime = 0;
                    endTime = 0;
                    initData();
                } else {
                    endTime = System.currentTimeMillis();
                }
            }
        });
    }

    long fristTime = 0;
    long searchTime = 0;
    long endTime = 0;
    int count = 0;

    private TextView tv_item1;
    private TextView tv_item2;
    int selected = 1;

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

        switch (selected) {
            case 1:
                tv_item1.setTextColor(Color.parseColor("#232323"));
                tv_item2.setTextColor(Color.parseColor("#9b9b9b"));
                break;
            case 2:
                tv_item1.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item2.setTextColor(Color.parseColor("#232323"));
                break;
        }
        //1 默认顺序 2 创建顺序

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
            orderby = "normal";
            tv_select.setText("默认顺序");
            popWindow.dissmiss();
            getTradeMySellList();
        });
        tv_item2.setOnClickListener(view -> {
            selected = 2;
            page = 1;
            orderby = "create";
            tv_select.setText("创建顺序");
            popWindow.dissmiss();
            getTradeMySellList();
        });
        popWindow.showAsDropDown(v, -v.getWidth() / 2, ScreenUtil.dp2px(_mActivity, 15));//指定view正下方
    }


    BaseRecyclerAdapter mTransactionListAdapter;

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        mXrecyclerView.setLayoutManager(layoutManager);

        mTransactionListAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(TradeMySellInfoVo.class, new TradeMySellItemHolder(_mActivity))
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
                getTradeMySellList();
            }
        });
        mTransactionListAdapter.setOnItemClickListener((v, position, data) -> {
            if( !(data instanceof TradeMySellInfoVo)){
                return;
            }
            TradeMySellInfoVo mysellInfo = (TradeMySellInfoVo) data;
            if (mysellInfo.getBan_trade_info() != null) {
                if (!StringUtil.isEmpty(mysellInfo.getBan_trade_info())) {
                    Toaster.show(mysellInfo.getBan_trade_info());
                    return;
                }
            }

            if (core == TransactionSellFragment2.RESTART_SELL) {
                //重新选择账号,保留填写信息
                if (getPreFragment() == null) {
                    Intent intent = new Intent();
                    intent.putExtra("gameid", mysellInfo.getGameid());
                    intent.putExtra("gameicon", mysellInfo.getGameicon());
                    intent.putExtra("gamename", mysellInfo.getGamename());
                    intent.putExtra("otherGamename", mysellInfo.getOtherGameName());
                    intent.putExtra("xh_pay_total", mysellInfo.getXh_pay_game_total());
                    intent.putExtra("xh_reg_day", mysellInfo.getXh_reg_day() + "");
                    intent.putExtra("xh_showname", mysellInfo.getXh_showname());
                    intent.putExtra("xh_username", mysellInfo.getXh_username());
                    intent.putExtra("game_type", mysellInfo.getGame_type());
                    intent.putExtra("rmb_total", mysellInfo.getRmb_total());
                    _mActivity.setResult(Activity.RESULT_OK, intent);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("gameid", mysellInfo.getGameid());
                    bundle.putString("gameicon", mysellInfo.getGameicon());
                    bundle.putString("gamename", mysellInfo.getGamename());
                    bundle.putString("otherGamename", mysellInfo.getOtherGameName());
                    bundle.putString("xh_pay_total", mysellInfo.getXh_pay_game_total()+"");
                    bundle.putString("xh_reg_day", mysellInfo.getXh_reg_day() + "");
                    bundle.putString("xh_showname", mysellInfo.getXh_showname());
                    bundle.putString("xh_username", mysellInfo.getXh_username());
                    bundle.putString("game_type", mysellInfo.getGame_type());
                    bundle.putString("rmb_total", String.valueOf(mysellInfo.getRmb_total()));
                    setFragmentResult(RESULT_OK, bundle);
                }
                pop();
            } else {
                //第一次进入卖号页
                transactionSellFragment2 = TransactionSellFragment2.newInstance(mysellInfo.getGameid(), mysellInfo.getGameicon(), mysellInfo.getGamename(), mysellInfo.getOtherGameName(),
                        mysellInfo.getXh_pay_game_total()+"", mysellInfo.getXh_reg_day() + "", mysellInfo.getXh_showname(), mysellInfo.getXh_username(), mysellInfo.getGame_type(), String.valueOf(mysellInfo.getRmb_total()));
                startForResult(transactionSellFragment2, FRIST_COMMIT_GOODS_SUCCESS);
            }

        });
    }

    TransactionSellFragment2 transactionSellFragment2 = null;

    private int page = 1, pageCount = 12;

    private void initData() {
        page = 1;
        getTradeMySellList();
    }

    private String orderby = "normal";
    private String kw;
    private String listTag = "";

    private void getTradeMySellList() {
        Map<String, String> params = new TreeMap<>();
        params.clear();

        if (!TextUtils.isEmpty(orderby)) {
            params.put("order", orderby);
        }

        params.put("kw", kw);

        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        if (page == 1) {
//            showLoading();
            mXrecyclerView.setNoMore(false);
        } else {
            if (!TextUtils.isEmpty(listTag)) {
                params.put("r_time", listTag);
            }
        }

        if (mViewModel != null) {
            mViewModel.getTradeMySellList(params, new OnBaseCallback<TradeMySellListVo>() {

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
                public void onSuccess(TradeMySellListVo data) {
                    showSuccess();
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null&&data.getData().size()!=0) {
                                if (page == 1) {
                                    mTransactionListAdapter.clear();
                                }
//                                data.getData().get(0).setBan_trade_info("不可售!");
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
                            }
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    public static final int FRIST_COMMIT_GOODS_SUCCESS = 0x8777;

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == FRIST_COMMIT_GOODS_SUCCESS) {
                pop();
            }
        }

//        if (resultCode == RESULT_OK) {
//            if (requestCode == FRIST_COMMIT_GOODS_SUCCESS) {
//                //商品第一次提交成功后回到交易首页
//                pop();
//            }
//        }
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//
//                default:
//                    break;
//            }
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, state);
    }

    @Override
    public void onDestroyView() {
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

}
