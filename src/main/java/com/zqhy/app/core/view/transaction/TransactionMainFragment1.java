package com.zqhy.app.core.view.transaction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.chaoji.im.utils.floattoast.XToast;
import com.chaoji.other.hjq.toast.Toaster;
import com.google.android.material.appbar.AppBarLayout;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoListVo1;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoVo1;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.main.MainActivity;
import com.zqhy.app.core.view.main.holder.GameNoMoreItemHolder;
import com.zqhy.app.core.view.recycle_new.XhNewRecycleMainFragment;
import com.zqhy.app.core.view.transaction.holder.TradeItemHolder1;
import com.zqhy.app.core.view.transaction.record.TransactionRecordFragment1;
import com.zqhy.app.core.view.transaction.sell.TransactionSellFragment;
import com.zqhy.app.core.view.transaction.util.AppBarStateChangeListener;
import com.zqhy.app.core.view.transaction.util.CustomPopWindow;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.network.statistics.JiuYaoStatisticsApi;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;


public class TransactionMainFragment1 extends BaseFragment<TransactionViewModel> implements View.OnClickListener {
    private static final int BACK_HOME = 0x999;

    public static TransactionMainFragment1 newInstance(String gamename, String gameid) {
        TransactionMainFragment1 fragment = new TransactionMainFragment1();
        Bundle bundle = new Bundle();
        bundle.putString("gamename", gamename);
        bundle.putString("gameid", gameid);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static String TAG = "TransactionFragment";
    private XRecyclerView mXrecyclerView;
    private TextView mTvTransactionNeedToKnow;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private LinearLayout layout_select;
    private LinearLayout layout_search;

    private RelativeLayout layout_zero;
    private RelativeLayout layout_one;
    private RelativeLayout layout_sell;
    private RelativeLayout layout_record;
    private LinearLayout layout_zero_tv;
    private LinearLayout layout_one_tv;
    private LinearLayout layout_sell_tv;
    private LinearLayout layout_record_tv;

    //1 最新上架 2 价格升序 3 价格降序 4 近期成交
    int selected = 1;
    int goods_type = 0;//0 普通交易数据 2 超值捡漏数据

    private String gamename, gameid;
    private int page = 1, pageCount = 12;
    private String scene = "normal";
    private String orderby;
    private String listTag = "";
    private TextView tv_item1;
    private TextView tv_item2;
    private TextView tv_item3;
    private TextView tv_item4;
    private TextView tv_select;

    private TextView tv_strict_selection;
    private ImageView iv_strict_selection;
    private TextView tv_detect_leakage;
    private ImageView iv_detect_leakage;
    private ViewPager mViewPager;
    private XToast logToast;
    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transaction_main1;
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
        showSuccess();
        super.initView(state);
        bindView();
        initList();
        //doDefault();
        //if(BuildConfig.DEBUG){
            //logToast =new XToast(_mActivity).setContentView(new LogcatDialog(_mActivity)).setGravity(Gravity.END);
            //logToast.show();
        //}
    }

    private void bindView() {
        mXrecyclerView = findViewById(R.id.xrecyclerView);
        mTvTransactionNeedToKnow = findViewById(R.id.tv_transaction_need_to_know);
        layout_select = findViewById(R.id.layout_select);
        layout_search = findViewById(R.id.layout_search);
        layout_zero = findViewById(R.id.layout_zero);
        layout_zero_tv = findViewById(R.id.layout_zero_tv);
        layout_one = findViewById(R.id.layout_one);
        layout_one_tv = findViewById(R.id.layout_one_tv);
        layout_sell = findViewById(R.id.layout_sell);
        layout_sell_tv = findViewById(R.id.layout_sell_tv);
        layout_record = findViewById(R.id.layout_record);
        layout_record_tv = findViewById(R.id.layout_record_tv);

        tv_strict_selection = findViewById(R.id.tv_strict_selection);
        iv_strict_selection = findViewById(R.id.iv_strict_selection);
        tv_detect_leakage = findViewById(R.id.tv_detect_leakage);
        iv_detect_leakage = findViewById(R.id.iv_detect_leakage);

        mViewPager = findViewById(R.id.viewpager);

        tv_select = findViewById(R.id.tv_select);
        toolbar = findViewById(R.id.toolbar);
        mTvTransactionNeedToKnow.setOnClickListener(this);
        appBarLayout = findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    toolbar.setVisibility(View.INVISIBLE);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    toolbar.setVisibility(View.VISIBLE);
                } else {
                    //中间状态
                    toolbar.setVisibility(View.INVISIBLE);
                }
            }
        });

        layout_select.setOnClickListener(view -> {
            showPopListView(layout_select, R.layout.pop_transaction_select);
        });

        layout_zero.setOnClickListener(this);
        layout_zero_tv.setOnClickListener(this);
        layout_one.setOnClickListener(this);
        layout_one_tv.setOnClickListener(this);
        layout_sell.setOnClickListener(this);
        layout_sell_tv.setOnClickListener(this);
        layout_record.setOnClickListener(this);
        layout_record_tv.setOnClickListener(this);
        layout_search.setOnClickListener(this);
        tv_strict_selection.setOnClickListener(this);
        tv_detect_leakage.setOnClickListener(this);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(TransactionHomeListFragment.newInstance("0", gamename, gameid));
        fragments.add(TransactionHomeListFragment.newInstance("2", gamename, gameid));

        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() {
                return fragments.size();
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    iv_strict_selection.setVisibility(View.VISIBLE);
                    iv_detect_leakage.setVisibility(View.GONE);
                }else {
                    iv_strict_selection.setVisibility(View.GONE);
                    iv_detect_leakage.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(0);
    }

    /**
     * 屏幕底部弹出
     * 方法名@：showAtLocation(contentView, Gravity.BOTTOM, 0, 0)
     * resource:PopWindow 布局
     */
    private void showPopListView(int resource) {
        if (resource == 0) {
            return;
        }
        View contentView = LayoutInflater.from(_mActivity).inflate(resource, null);
        //处理popWindow 显示内容
        //创建并显示popWindow
        new CustomPopWindow.PopupWindowBuilder(_mActivity)
                .enableOutsideTouchableDissmiss(false)
                .setBgDarkAlpha(0.8F)
                .enableBackgroundDark(true)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                .create()
                .showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

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
            orderby = null;
            scene = "trends";
            tv_select.setText("近期成交");
            popWindow.dissmiss();
            getTradeGoodList();
        });
        popWindow.showAsDropDown(v, -v.getWidth() / 2, 0);//指定view正下方
    }


    BaseRecyclerAdapter mTransactionListAdapter;

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        mXrecyclerView.setLayoutManager(layoutManager);

        mTransactionListAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(TradeGoodInfoVo1.class, new TradeItemHolder1(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(NoMoreDataVo.class, new GameNoMoreItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
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
                startForResult(TransactionGoodDetailFragment.newInstance(tradeGoodInfoVo.getGid(), tradeGoodInfoVo.getGameid()), action_transaction_good_detail);
            }
        });
    }

    private void initData() {
        page = 1;
        getTradeGoodList();
    }

    boolean fristErr = false;
    LinearLayout errLayout = null;

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
            mXrecyclerView.setNoMore(false);
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
                    mXrecyclerView.loadMoreComplete();
                    mXrecyclerView.refreshComplete();
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    showSuccess();
                    Toaster.show("请求数据失败!");
                    if (mTransactionListAdapter.getData().size()>0) return;
                    if (!fristErr) {
                        fristErr = true;
                        errLayout = new LinearLayout(_mActivity);
                        errLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        errLayout.setOrientation(LinearLayout.VERTICAL);
                        errLayout.setGravity(Gravity.CENTER);
                        ImageView imageView = new ImageView(_mActivity);
                        Drawable drawable = getResources().getDrawable(R.mipmap.empty_server);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.gravity = Gravity.CENTER;
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        imageView.setLayoutParams(params);
                        imageView.setImageDrawable(drawable);
                        errLayout.addView(imageView);
                        mXrecyclerView.addHeaderView(errLayout);
                        errLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                initData();
                            }
                        });
                    }
                }

                @Override
                public void onSuccess(TradeGoodInfoListVo1 data) {
                    showSuccess();
                    if (fristErr) {
                        fristErr = false;
                        if (errLayout!=null) {
                            mXrecyclerView.removeHeaderView(errLayout);
                            errLayout = null;
                        }
                    }
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_transaction_need_to_know:
                //交易须知
//                start(new TransactionNoticeFragment());
                BrowserActivity.newInstance(_mActivity, "https://mobile.tsyule.cn/index.php/Index/view/?id=205410");
                JiuYaoStatisticsApi.getInstance().eventStatistics(7, 95);
                break;
            case R.id.layout_search:
                //搜索
                startForResult(new TransactionSearchFragment1(), action_transaction_goods);
                JiuYaoStatisticsApi.getInstance().eventStatistics(7, 102);
                break;
            case R.id.layout_zero:
            case R.id.layout_zero_tv:
                //我要卖号
                if (checkLogin()) {
                    start(TransactionSellFragment.newInstance());
                }
                break;
            case R.id.layout_one:
            case R.id.layout_one_tv:
                //小号回收
                if (checkLogin()) {
                    startFragment(new XhNewRecycleMainFragment());
                }
                break;
            case R.id.layout_sell:
            case R.id.layout_sell_tv:
                //我的交易
                if (checkLogin()) {
                    startForResult(new TransactionRecordFragment1(), action_transaction_record);
                    JiuYaoStatisticsApi.getInstance().eventStatistics(7, 98);
                }
                break;
            case R.id.layout_record:
            case R.id.layout_record_tv:
                //交易须知
                BrowserActivity.newInstance(_mActivity, "https://mobile.tsyule.cn/index.php/Index/view/?id=205410");
                break;
            case R.id.tv_strict_selection:
                iv_strict_selection.setVisibility(View.VISIBLE);
                iv_detect_leakage.setVisibility(View.GONE);
                /*page = 1;
                goods_type = 0;
                getTradeGoodList();*/
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tv_detect_leakage:
                iv_strict_selection.setVisibility(View.GONE);
                iv_detect_leakage.setVisibility(View.VISIBLE);
                /*page = 1;
                goods_type = 2;
                getTradeGoodList();*/
                mViewPager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }

    private void NewTabClick() {
        page = 1;
        if (TextUtils.isEmpty(scene)) {
            scene = "normal";
        }
        gameid = "";
//        setPriceType(Transaction_Price_Normal);
        getTradeGoodList();

    }

    private void doDefault() {
        selected = 1;
        if (TextUtils.isEmpty(gamename) || TextUtils.isEmpty(gameid)) {
            NewTabClick();
            return;
        }
        itemTabClick(gamename, gameid);
    }

    private void itemTabClick(String gamename, String gameid) {
        page = 1;
        this.gameid = gameid;
//        setPriceType(Transaction_Price_Normal);
        getTradeGoodList();
    }

    private final int action_transaction_goods = 0x546;
    private final int action_transaction_record = 0x556;
    private final int action_transaction_good_detail = 0x566;

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
//                    String gamename = data.getStringExtra("gamename");
//                    String gameid = data.getStringExtra("gameid");
//                    itemTabClick(gamename, gameid);
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
