package com.zqhy.app.core.view.transaction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.hjq.toast.Toaster;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.jcodecraeer.xrecyclerview.AppBarStateChangeListener;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoListVo;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.main.MainActivity;
import com.zqhy.app.core.view.main.holder.GameNoMoreItemHolder;
import com.zqhy.app.core.view.transaction.adapter.TypeChooseAdapter;
import com.zqhy.app.core.view.transaction.holder.TradeItemHolder;
import com.zqhy.app.core.view.transaction.record.TransactionRecordFragment;
import com.zqhy.app.core.view.transaction.sell.TransactionSellFragment;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.network.statistics.JiuYaoStatisticsApi;
import com.zqhy.app.newproject.R;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**2022 6 16弃用
 * @author Administrator
 */
public class TransactionMainFragment extends BaseFragment<TransactionViewModel> implements View.OnClickListener {

    public static TransactionMainFragment newInstance(String gamename, String gameid) {
        TransactionMainFragment fragment = new TransactionMainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gamename", gamename);
        bundle.putString("gameid", gameid);
        fragment.setArguments(bundle);
        return fragment;
    }

    private final int Transaction_Price_Normal = 0;
    private final int Transaction_Price_Up = 1;
    private final int Transaction_Price_Down = 2;

    private final int action_transaction_goods = 0x546;
    private final int action_transaction_record = 0x556;
    private final int action_transaction_good_detail = 0x566;

    private int page = 1, pageCount = 12;
    private String gamename, gameid;
    private String scene = "normal";
    private String orderby;
    private String listTag = "";

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "交易列表页";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transaction_main;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gamename = getArguments().getString("gamename");
            gameid = getArguments().getString("gameid");
        }
        super.initView(state);

        initActionBackBarAndTitle("账号交易", !(_mActivity instanceof MainActivity));
        bindView();
        initList();

        doDefault();
    }

    private CoordinatorLayout mLlContentLayout;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsing;
    private LinearLayout mLlTransactionIvs;
    private LinearLayout mLlMenuIcons;
    private ImageView mIvTransactionNeedToKnow;
    private ImageView mIvTransactionSellAccount;
    private ImageView mIvTransactionRecord;
    private ImageView mIvContactKefu;
    private LinearLayout mLlTransactionList;
    private LinearLayout mLlTransactionNeedToKnow;
    private LinearLayout mLlTransactionSellAccount;
    private LinearLayout mLlTransactionRecord;
    private LinearLayout mLlContactKefu;
    private XRecyclerView mXrecyclerView;

    private TextView mTabTransactionNew;
    private TextView mTabTransactionScreening;
    private TextView mTabTransactionScreeningPrice;
    private TextView mTvTransactionNeedToKnow;
    private LinearLayout mLlTransactionTabs;

    private void bindView() {
        mLlContentLayout = findViewById(R.id.ll_content_layout);
        mAppBarLayout = findViewById(R.id.appBarLayout);
        mCollapsing = findViewById(R.id.collapsing);
        mLlTransactionIvs = findViewById(R.id.ll_transaction_ivs);
        mLlMenuIcons = findViewById(R.id.ll_menu_icons);
        mIvTransactionNeedToKnow = findViewById(R.id.iv_transaction_need_to_know);
        mIvTransactionSellAccount = findViewById(R.id.iv_transaction_sell_account);
        mIvTransactionRecord = findViewById(R.id.iv_transaction_record);
        mIvContactKefu = findViewById(R.id.iv_contact_kefu);
        mLlTransactionList = findViewById(R.id.ll_transaction_list);
        mLlTransactionNeedToKnow = findViewById(R.id.ll_transaction_need_to_know);
        mLlTransactionSellAccount = findViewById(R.id.ll_transaction_sell_account);
        mLlTransactionRecord = findViewById(R.id.ll_transaction_record);
        mLlContactKefu = findViewById(R.id.ll_contact_kefu);
        mXrecyclerView = findViewById(R.id.xrecyclerView);

        mTvTransactionNeedToKnow = findViewById(R.id.tv_transaction_need_to_know);
        mTvTransactionNeedToKnow.setOnClickListener(this);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(36 * density);
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_cccccc));
        mTvTransactionNeedToKnow.setBackground(gd);


        mLlTransactionNeedToKnow.setOnClickListener(this);
        mLlTransactionSellAccount.setOnClickListener(this);
        mLlTransactionRecord.setOnClickListener(this);
        mLlContactKefu.setOnClickListener(this);

        mIvTransactionNeedToKnow.setOnClickListener(this);
        mIvTransactionSellAccount.setOnClickListener(this);
        mIvTransactionRecord.setOnClickListener(this);
        mIvContactKefu.setOnClickListener(this);

        mTabTransactionNew = findViewById(R.id.tab_transaction_new);
        mTabTransactionScreening = findViewById(R.id.tab_transaction_screening);
        mTabTransactionScreeningPrice = findViewById(R.id.tab_transaction_screening_price);

        mLlTransactionTabs = findViewById(R.id.ll_transaction_tabs);

        mTabTransactionNew.setOnClickListener(this);
        mTabTransactionScreening.setOnClickListener(this);
        mTabTransactionScreeningPrice.setOnClickListener(this);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    showToolbar();
                }
            }
        });
    }


    private int currentPrice = Transaction_Price_Normal;


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_transaction_need_to_know:
                //交易须知
                start(new TransactionNoticeFragment());
                JiuYaoStatisticsApi.getInstance().eventStatistics(7, 95);
                break;
            case R.id.tab_transaction_new:
                showFilterSortPop();
                break;
            case R.id.tab_transaction_screening:
                startForResult(new TransactionSearchFragment(), action_transaction_goods);
                JiuYaoStatisticsApi.getInstance().eventStatistics(7, 102);
                break;
            case R.id.tab_transaction_screening_price:
                //价格
                currentPrice++;
                setPriceType(currentPrice);
                if (currentPrice >= Transaction_Price_Down) {
                    currentPrice = (Transaction_Price_Normal - 1);
                }
                getTradeGoodList();
                JiuYaoStatisticsApi.getInstance().eventStatistics(7, 103);
                break;
            case R.id.ll_transaction_need_to_know:
            case R.id.iv_transaction_need_to_know:
                //成交动态
                //start(new TransactionNoticeFragment());
                BrowserActivity.newInstance(_mActivity, "https://mobile.tsyule.cn/index.php/Index/view/?id=103331");
                JiuYaoStatisticsApi.getInstance().eventStatistics(7, 96);
                break;
            case R.id.ll_transaction_sell_account:
            case R.id.iv_transaction_sell_account:
                //我要卖账号
                if (checkLogin()) {
                    checkTransaction();
                    JiuYaoStatisticsApi.getInstance().eventStatistics(7, 97);
                }
                break;
            case R.id.ll_transaction_record:
            case R.id.iv_transaction_record:
                //交易记录
                if (checkLogin()) {
                    startForResult(new TransactionRecordFragment(), action_transaction_record);
                    JiuYaoStatisticsApi.getInstance().eventStatistics(7, 98);
                }
                break;
            case R.id.ll_contact_kefu:
            case R.id.iv_contact_kefu:
                //客服
                goKefuMain();
                JiuYaoStatisticsApi.getInstance().eventStatistics(7, 99);
                break;
            default:
                break;
        }
    }

    private void initData() {
        page = 1;
        getTradeGoodList();
    }

    private void doDefault() {
        if (TextUtils.isEmpty(gamename) || TextUtils.isEmpty(gameid)) {
            NewTabClick();
            return;
        }
        itemTabClick(gamename, gameid);
    }

    BaseRecyclerAdapter mTransactionListAdapter;

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        mXrecyclerView.setLayoutManager(layoutManager);

        mTransactionListAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(TradeGoodInfoVo.class, new TradeItemHolder(_mActivity))
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
            if (data != null && data instanceof TradeGoodInfoVo) {
                TradeGoodInfoVo tradeGoodInfoVo = (TradeGoodInfoVo) data;
                startForResult(TransactionGoodDetailFragment.newInstance(tradeGoodInfoVo.getGid(), tradeGoodInfoVo.getGameid(), tradeGoodInfoVo.getGoods_pic()), action_transaction_good_detail);
            }
        });

        mXrecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int newState = recyclerView.getScrollState();

                if (newState != RecyclerView.SCROLL_STATE_DRAGGING) {
                    if (dy > 0) {
                        hideToolbar();
                    }

                    if (dy < 0) {
                        showToolbar();
                    }
                }

            }
        });
    }

    private void NewTabClick() {
        mTabTransactionNew.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_333333));


        mTabTransactionScreening.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_007aff));
        mTabTransactionScreening.setText("游戏筛选");
        mTabTransactionScreening.setCompoundDrawablesWithIntrinsicBounds(null, null, _mActivity.getResources().getDrawable(R.mipmap.ic_arrow_right), null);

        page = 1;
        if (TextUtils.isEmpty(scene)) {
            scene = "normal";
        }
        gameid = "";
        setPriceType(Transaction_Price_Normal);
        getTradeGoodList();

    }

    private void itemTabClick(String gamename, String gameid) {
        mTabTransactionNew.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_999999));

        mTabTransactionScreening.setText(gamename);
        mTabTransactionScreening.setCompoundDrawablesWithIntrinsicBounds(null, null, _mActivity.getResources().getDrawable(R.mipmap.ic_arrow_right), null);

        page = 1;
        this.gameid = gameid;
        setPriceType(Transaction_Price_Normal);
        getTradeGoodList();
    }

    private void setPriceType(int type) {
        switch (type) {
            case 0:
                mTabTransactionScreeningPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, _mActivity.getResources().getDrawable(R.mipmap.ic_transaction_price_order), null);
                orderby = "";
                break;
            case 1:
                mTabTransactionScreeningPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, _mActivity.getResources().getDrawable(R.mipmap.ic_transaction_price_order_up), null);
                orderby = "price_up";
                break;
            case 2:
                mTabTransactionScreeningPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, _mActivity.getResources().getDrawable(R.mipmap.ic_transaction_price_order_down), null);
                orderby = "price_down";
                break;
            default:
                break;
        }
        page = 1;
    }

    private RecyclerView sortRecyclerView = null;
    private TypeChooseAdapter sortAdapter;
    private PopupWindow sortPop;

    private void showFilterSortPop() {
        if (sortPop == null) {
            View popView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_pop, null);
            if (sortRecyclerView == null) {
                sortRecyclerView = popView.findViewById(R.id.recyclerView);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                sortRecyclerView.setLayoutManager(layoutManager);

                int sortArrayRes = R.array.sort_list_transaction;

                String[] litters = getActivity().getResources().getStringArray(sortArrayRes);

                sortAdapter = new TypeChooseAdapter(getActivity(), Arrays.asList(litters));
                sortRecyclerView.setAdapter(sortAdapter);
                sortAdapter.setOnItemClickListenter((View v, int position) -> {
                    String[] split = sortAdapter.getItemString(position).split("@");

                    mTabTransactionNew.setText(split[0]);
                    if (split.length > 1) {
                        scene = split[1];
                    }
                    NewTabClick();

                    if (position == 0) {
                        //最新出售
                        JiuYaoStatisticsApi.getInstance().eventStatistics(7, 100);
                    } else if (position == 1) {
                        //最新成交
                        JiuYaoStatisticsApi.getInstance().eventStatistics(7, 101);
                    }

                    sortPop.dismiss();
                });
                sortAdapter.selectItem(0);
            }
            popView.findViewById(R.id.id_blank).setOnClickListener((v) -> {
                sortPop.dismiss();
            });
            sortPop = new PopupWindow(popView, ScreenUtils.getScreenWidth(_mActivity), WindowManager.LayoutParams.MATCH_PARENT, true) {
                @Override
                public void showAsDropDown(View anchor) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Rect rect = new Rect();
                        anchor.getGlobalVisibleRect(rect);
                        int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
                        setHeight(h);
                    }
                    super.showAsDropDown(anchor);
                }
            };
            sortPop.setOutsideTouchable(false);
            ColorDrawable colorDrawable = new ColorDrawable(0x76000000);
            sortPop.setBackgroundDrawable(colorDrawable);
        }
        sortPop.showAsDropDown(mLlTransactionTabs);
    }

    @Override
    public void start(ISupportFragment toFragment) {
        if (_mActivity instanceof MainActivity) {
            FragmentHolderActivity.startFragmentInActivity(_mActivity, (SupportFragment) toFragment);
        } else {
            if (getParentFragment() != null && getParentFragment() instanceof SupportFragment) {
                ((SupportFragment) getParentFragment()).start(toFragment);
            } else {
                super.start(toFragment);
            }
        }
    }

    @Override
    public void startForResult(ISupportFragment toFragment, int requestCode) {
        if (_mActivity instanceof MainActivity) {
            FragmentHolderActivity.startFragmentForResult(_mActivity, (SupportFragment) toFragment, requestCode);
        } else {
            if (getParentFragment() != null && getParentFragment() instanceof SupportFragment) {
                ((SupportFragment) getParentFragment()).startForResult(toFragment, requestCode);
            } else {
                super.startForResult(toFragment, requestCode);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case action_transaction_record:
                case action_transaction_good_detail:
                    initData();
                    break;
                case action_transaction_goods:
                    String gamename = data.getStringExtra("gamename");
                    String gameid = data.getStringExtra("gameid");
                    itemTabClick(gamename, gameid);
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
            switch (requestCode) {
                case action_transaction_record:
                case action_transaction_good_detail:
                    initData();
                    break;
                case action_transaction_goods:
                    String gamename = data.getString("gamename");
                    String gameid = data.getString("gameid");
                    itemTabClick(gamename, gameid);
                    break;
                default:
                    break;
            }
        }
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
        if (!TextUtils.isEmpty(gameid)) {
            params.put("gameid", gameid);
        }

        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        if (page == 1) {
            mXrecyclerView.setNoMore(false);
        } else {
            if (!TextUtils.isEmpty(listTag)) {
                params.put("r_time", listTag);
            }
        }

        if (mViewModel != null) {
            mViewModel.getTradeGoodList(params, new OnBaseCallback<TradeGoodInfoListVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    mXrecyclerView.loadMoreComplete();
                    mXrecyclerView.refreshComplete();
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    showErrorTag1();
                }

                @Override
                public void onSuccess(TradeGoodInfoListVo data) {
                    showSuccess();
                    setTradeGoodList(data);
                }
            });
        }

    }

    private void setTradeGoodList(TradeGoodInfoListVo data) {
        if (data != null) {
            if (data.isStateOK()) {
                if (data.getData() != null) {
                    if (page == 1) {
                        mTransactionListAdapter.clear();
                    }
                    for (TradeGoodInfoVo tradeGoodInfoBean : data.getData()) {
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
                    mAppBarLayout.setExpanded(true, true);
                }
            } else {
                Toaster.show(data.getMsg());
            }
        }
    }

    private void checkTransaction() {
        if (mViewModel != null) {
            mViewModel.checkTransaction(new OnBaseCallback() {
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
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (checkLogin()) {
                                start(TransactionSellFragment.newInstance());
                            }
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    Toaster.show( message);
                }
            });
        }
    }
}
