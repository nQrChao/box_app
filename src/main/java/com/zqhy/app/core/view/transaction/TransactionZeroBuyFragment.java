package com.zqhy.app.core.view.transaction;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.box.other.hjq.toast.Toaster;
import com.google.android.material.appbar.AppBarLayout;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.data.model.transaction.CollectionBeanVo;
import com.zqhy.app.core.data.model.transaction.TradeZeroBuyGoodInfoListVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.StringUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.community.task.TaskCenterFragment;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.core.view.main.holder.GameNoMoreItemHolder;
import com.zqhy.app.core.view.transaction.holder.TradeZeroBuyItemHolder;
import com.zqhy.app.core.view.transaction.util.AppBarStateChangeListener;
import com.zqhy.app.core.view.transaction.util.CustomPopWindow;
import com.zqhy.app.core.view.user.TopUpFragment;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 */
public class TransactionZeroBuyFragment extends BaseFragment<TransactionViewModel> implements TradeZeroBuyItemHolder.OnClickButtenListener {

    private FrameLayout mFlTitleRight;
    private FrameLayout mFlTitleRight1;
    private LinearLayout layout_select;
    private XRecyclerView recyclerview;
    private TextView title_bottom_line;
    private TextView tv_select;
    private FrameLayout layout_top;

    @Override
    public Object getStateEventKey() {
        return null;
    }


    private final int ACTION_TRY_GAME_REQUEST_CODE = 10001;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transaction_zerobuy;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("0元淘号");

        bindView();
        initData();
    }

    BaseRecyclerAdapter adapter;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ImageView iv_back_writer;
    private LinearLayout top_layout;

    private void bindView() {
        recyclerview = findViewById(R.id.recyclerview);
        mFlTitleRight = findViewById(R.id.fl_title_right);
        mFlTitleRight1 = findViewById(R.id.fl_title_right1);
        title_bottom_line = findViewById(R.id.title_bottom_line);
        layout_top = findViewById(R.id.layout_top);
        tv_select = findViewById(R.id.tv_select);
        layout_select = findViewById(R.id.layout_select);
        iv_back_writer = findViewById(R.id.iv_back_writer);
        top_layout = findViewById(R.id.top_layout);
        title_bottom_line.setVisibility(View.GONE);
        mFlTitleRight.addView(getTitleRightView());
        mFlTitleRight1.addView(getTitleRightView1());

        iv_back_writer.setOnClickListener(view -> {
            pop();
        });
        layout_select.setOnClickListener(view -> {
            showPopListView(layout_select, R.layout.pop_transaction_zerobuy_select);
        });

        toolbar = findViewById(R.id.toolbar);
        appBarLayout = findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    toolbar.setVisibility(View.INVISIBLE);
                    top_layout.setPadding(ScreenUtil.dp2px(activity, 20), 0, ScreenUtil.dp2px(activity, 20), ScreenUtil.dp2px(activity, 15));
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    top_layout.setPadding(ScreenUtil.dp2px(activity, 20), ScreenUtil.dp2px(activity, 15), ScreenUtil.dp2px(activity, 20), ScreenUtil.dp2px(activity, 15));
                    toolbar.setVisibility(View.VISIBLE);
                } else {
                    //中间状态
                    top_layout.setPadding(ScreenUtil.dp2px(activity, 20), 0, ScreenUtil.dp2px(activity, 20), ScreenUtil.dp2px(activity, 15));
                    toolbar.setVisibility(View.INVISIBLE);
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerview.setLayoutManager(layoutManager);

        adapter = new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(TradeZeroBuyGoodInfoListVo.TradeZeroBuyGoodInfo.class, new TradeZeroBuyItemHolder(_mActivity, this))
                .bind(NoMoreDataVo.class, new GameNoMoreItemHolder(_mActivity))
                .build();

        recyclerview.setAdapter(adapter);

        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            float v =(-verticalOffset * 1.0f) / (appBarLayout.getTotalScrollRange()-150);
            if(v>1.0f){
                v = 1.0f;
            }
            layout_top.setAlpha(1.5f-v);
        });

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
    }

    private int page = 1, pageCount = 12;

    private void initData() {
        refreshUserData();
        page = 1;
        getZeroBuyGoodList();
    }

    private void refreshUserData(){
        if (UserInfoModel.getInstance().isLogined()){
            if (mViewModel != null) {
                mViewModel.refreshUserDataWithNotification(data -> {
                    if (data != null && data.isNoLogin()) {
                        UserInfoModel.getInstance().logout();
                        checkLogin();
                        initUser();
                    }else {
                        initUser();
                    }
                });
            }
        }
    }


    boolean isBuy = false;

    private void doZeroBuyGoodsBuy(String id, String pay_type) {
        Map<String, String> params = new TreeMap<>();
        params.put("id", id);
        params.put("pay_type", pay_type);

        if (mViewModel != null) {
            mViewModel.doZeroBuyGoodsBuy(params, new OnBaseCallback<CollectionBeanVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                }

                @Override
                public void onSuccess(CollectionBeanVo data) {
                    isBuy = false;
                    if (data != null) {
                        if (data.isStateOK()) {
                            refreshUserData();
                            popWindowDicker.dissmiss();
                            isNotToTop = true;
                            showBuySuccessDialog();
                            //兑换成功后刷新列表
                            if (hasNoMoreView) {
                                //底线出来了
                                page = page-1;
                                if (page <= 0){
                                    page = 1;
                                }
                            }
                            getZeroBuyGoodList();
                        } else {
                            popWindowDicker.dissmiss();
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    //排序   integral_desc：售价降序、integral_asc：售价升序、new：最新、pay_desc：充值金额降序、pay_asc：充值金额升序
    private String order = "new";

    boolean isNotToTop = false;

    private void getZeroBuyGoodList() {
        if (page == 1) {
//            showLoading();
        }
        Map<String, String> params = new TreeMap<>();

        if (!TextUtils.isEmpty(order)) {
            params.put("order", order);
        }

        params.put("page", String.valueOf(page));
        params.put("pagecount", String.valueOf(pageCount));

        if (mViewModel != null) {
            mViewModel.getZeroBuyGoodList(params, new OnBaseCallback<TradeZeroBuyGoodInfoListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    recyclerview.loadMoreComplete();
                    recyclerview.refreshComplete();
                }

                @Override
                public void onSuccess(TradeZeroBuyGoodInfoListVo data) {
                    showSuccess();
                    Log.d("Zerobuy","page:"+page);
                    if (data.getData() != null) {
                        if (isNotToTop) {
                            //兑换成功后刷新列表
                            isNotToTop = false;
                            adapter.clear();
                            adapter.addAllData(data.getData());
                            adapter.notifyDataSetChanged();
                            return;
                        }
                    }

                    if (data.getData() != null) {
                        hasNoMoreView = false;
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
                            if (!hasNoMoreView) {
                                adapter.addData(new NoMoreDataVo());
                                hasNoMoreView = true;
                            }
                        }
                        adapter.notifyDataSetChanged();
                        recyclerview.setNoMore(true);
                    }
                    if (page == 1) {
                        if (isNotToTop) {

                        } else {
                            hasNoMoreView = false;
                            recyclerview.smoothScrollToPosition(0);
                        }
                    }


                }
            });
        }

    }

    private boolean hasNoMoreView = false;

    //1 最新上架 2 价格升序 3 价格降序 4 近期成交
    int selected = 1;
    private TextView tv_item1;
    private TextView tv_item2;
    private TextView tv_item3;
    private TextView tv_item4;
    private TextView tv_item5;

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
        tv_item5 = ((TextView) contentView.findViewById(R.id.tv_item5));

        switch (selected) {
            case 1:
                tv_item1.setTextColor(Color.parseColor("#232323"));
                tv_item2.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item3.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item4.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item5.setTextColor(Color.parseColor("#9b9b9b"));
                break;
            case 2:
                tv_item1.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item2.setTextColor(Color.parseColor("#232323"));
                tv_item3.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item4.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item5.setTextColor(Color.parseColor("#9b9b9b"));
                break;
            case 3:
                tv_item1.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item2.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item3.setTextColor(Color.parseColor("#232323"));
                tv_item4.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item5.setTextColor(Color.parseColor("#9b9b9b"));
                break;
            case 4:
                tv_item1.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item2.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item3.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item4.setTextColor(Color.parseColor("#232323"));
                tv_item5.setTextColor(Color.parseColor("#9b9b9b"));
                break;
            case 5:
                tv_item1.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item2.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item3.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item4.setTextColor(Color.parseColor("#9b9b9b"));
                tv_item5.setTextColor(Color.parseColor("#232323"));
                break;
        }
        //1 最新上架 2 售价升序 3 售价降序 4 值金额升序 5 充值金额降序
        //排序   integral_desc：售价降序、integral_asc：售价升序、new：最新、pay_desc：充值金额降序、pay_asc：充值金额升序
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
            order = "new";
            tv_select.setText("最新上架");
            popWindow.dissmiss();
            getZeroBuyGoodList();
        });
        tv_item2.setOnClickListener(view -> {
            selected = 2;
            page = 1;
            order = "integral_asc";
            tv_select.setText("兑换升序");
            popWindow.dissmiss();
            getZeroBuyGoodList();
        });
        tv_item3.setOnClickListener(view -> {
            selected = 3;
            page = 1;
            order = "integral_desc";
            tv_select.setText("兑换降序");
            popWindow.dissmiss();
            getZeroBuyGoodList();
        });
        tv_item4.setOnClickListener(view -> {
            selected = 4;
            page = 1;
            order = "pay_asc";
            tv_select.setText("充值升序");
            popWindow.dissmiss();
            getZeroBuyGoodList();
        });
        tv_item5.setOnClickListener(view -> {
            selected = 5;
            page = 1;
            order = "pay_desc";
            tv_select.setText("充值降序");
            popWindow.dissmiss();
            getZeroBuyGoodList();
        });
        popWindow.showAsDropDown(v, -v.getWidth() / 2, 0);//指定view正下方
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ACTION_TRY_GAME_REQUEST_CODE) {

            }
        }
    }

    private CustomDialog buySuccessDialog;

    private void showBuySuccessDialog() {
        if (buySuccessDialog == null) {
            buySuccessDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_transaction_zero_thcg, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            buySuccessDialog.setCancelable(false);
            buySuccessDialog.setCanceledOnTouchOutside(false);
            TextView btn_got_it = buySuccessDialog.findViewById(R.id.btn_got_it);

            btn_got_it.setOnClickListener(view -> {
                if (buySuccessDialog != null) {
                    buySuccessDialog.dismiss();
                    buySuccessDialog = null;
                }
            });
            buySuccessDialog.show();
        }
    }

    private CustomDialog transactionTipDialog;

    private void showTransactionTipDialog() {
        if (transactionTipDialog == null) {
            transactionTipDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_transaction_zero_thxz, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            transactionTipDialog.setCancelable(false);
            transactionTipDialog.setCanceledOnTouchOutside(false);

            TextView btn_got_it = (TextView) transactionTipDialog.findViewById(R.id.btn_got_it);
            TextView tv_content_1 = (TextView) transactionTipDialog.findViewById(R.id.tv_content_1);
            TextView tv_content_2 = (TextView) transactionTipDialog.findViewById(R.id.tv_content_2);

            SpannableString ss = new SpannableString("1、淘号为原回收小号，目前以淘号形式回馈给玩家体验。我们仅提供充值最多的区服、小号总累充做参考。所淘号实际情况未知，具体需以实际游戏内情况为准。将有几率淘到极品号，也有几率淘到废号，纯属运气！");
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF6337")), 28, 47, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_content_1.setText(ss);
            SpannableString ss1 = new SpannableString("2、淘号一经兑换成功，不支持退换。请谨慎选择。");
            ss1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF6337")), 6, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_content_2.setText(ss1);

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
        tv.setText("淘号须知");
        tv.setPadding((int) (8 * density), 0, (int) (8 * density), 0);
        tv.setTextSize(12);
        tv.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));

        TextView tv1 = new TextView(_mActivity);
        tv1.setText("我的淘号");
        tv1.setPadding((int) (8 * density), 0, (int) (8 * density), 0);
        tv1.setTextSize(12);
        tv1.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));


        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(60 * density);


        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.white));

        tv.setGravity(Gravity.CENTER);
        tv.setBackground(gd);
        tv1.setGravity(Gravity.CENTER);
        tv1.setBackground(gd);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (24 * density));
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(0, 0, (int) (16 * density), 0);
        tv.setLayoutParams(params);


        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (24 * density));
        params1.gravity = Gravity.CENTER_VERTICAL;
        params1.setMargins(0, 0, (int) (6 * density), 0);
        tv1.setLayoutParams(params1);

        tv.setOnClickListener(view -> {
            showTransactionTipDialog();
        });
        tv1.setOnClickListener(view -> {
            if (checkLogin()) {
                startFragment(new TransactionMyBoughtFragment());
            }
        });

        linearLayout.addView(tv);
        linearLayout.addView(tv1);
        return linearLayout;
    }

    private View getTitleRightView1() {
        LinearLayout linearLayout = new LinearLayout(_mActivity);


        TextView tv1 = new TextView(_mActivity);
        tv1.setText("我的淘号");
        tv1.setPadding((int) (8 * density), 0, (int) (8 * density), 0);
        tv1.setTextSize(12);
        tv1.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));


        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(60 * density);


        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_232323));

        tv1.setGravity(Gravity.CENTER);
        tv1.setBackground(gd);


        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (24 * density));
        params1.gravity = Gravity.CENTER_VERTICAL;
        params1.setMargins(0, 0, (int) (6 * density), 0);
        tv1.setLayoutParams(params1);

        tv1.setOnClickListener(view -> {
            if (checkLogin()) {
                startFragment(new TransactionMyBoughtFragment());
            }
        });

        linearLayout.addView(tv1);
        return linearLayout;
    }

    CustomPopWindow popWindowDicker;
    int mole = 2;
    boolean isChecked = false;

    /**
     * 屏幕底部弹出
     * 方法名@：showAtLocation(contentView, Gravity.BOTTOM, 0, 0)
     * resource:PopWindow 布局
     */
    private void showPopListViewDicker(String needIntegral, String id) {
        isChecked = false;
        float needcurrency = (Float.parseFloat(needIntegral) / 100);
        int needintegral = Integer.parseInt(needIntegral);
        View contentView = LayoutInflater.from(_mActivity).inflate(R.layout.pop_transaction_zero_pay_dicker, null);
        TextView tv_close = contentView.findViewById(R.id.tv_close);
        TextView tv_do = contentView.findViewById(R.id.tv_do);
        CheckBox cb_read = contentView.findViewById(R.id.cb_read);
        cb_read.setOnCheckedChangeListener((compoundButton, b) -> isChecked = b);
        TextView tv_content_1 = contentView.findViewById(R.id.tv_content_1);
        TextView tv_content_2 = contentView.findViewById(R.id.tv_content_2);
        TextView tv_buy_currency = contentView.findViewById(R.id.tv_buy_currency);
        TextView tv_currency = contentView.findViewById(R.id.tv_currency);
        TextView tv_balance_currency = contentView.findViewById(R.id.tv_balance_currency);
        TextView tv_integral = contentView.findViewById(R.id.tv_integral);
        TextView tv_balance_integral = contentView.findViewById(R.id.tv_balance_integral);
        ImageView iv_1 = contentView.findViewById(R.id.iv_1);
        ImageView iv_2 = contentView.findViewById(R.id.iv_2);
        RelativeLayout layout_1 = contentView.findViewById(R.id.layout_1);
        RelativeLayout layout_2 = contentView.findViewById(R.id.layout_2);
        tv_integral.setText(needintegral + "");
        tv_currency.setText(needcurrency + "");
        if (StringUtil.isEmpty(userIIntegral)) {
            tv_balance_integral.setText("余额:  " + "0");
            userIIntegral = "0";
        } else {
            tv_balance_integral.setText("余额:  " + userIIntegral);
        }
        if (StringUtil.isEmpty(userIcurrency)) {
            tv_balance_currency.setText("余额:  " + "0");
            userIcurrency = "0";
        } else {
            if ("0.0".equals(userIcurrency)) {
                tv_balance_currency.setText("余额:  " + "0");
            } else {
                tv_balance_currency.setText("余额:  " + userIcurrency);
            }
        }


        //默认显示
        mole = 2;
        layout_2.setBackground(getResources().getDrawable(R.drawable.shape_white_radius5_stroke_5571fe, null));
        layout_1.setBackground(getResources().getDrawable(R.drawable.shape_white_radius5_stroke_e5e5e5, null));
        iv_2.setVisibility(View.VISIBLE);
        iv_1.setVisibility(View.GONE);

        layout_1.setOnClickListener(view -> {
            mole = 1;
            layout_1.setBackground(getResources().getDrawable(R.drawable.shape_white_radius5_stroke_5571fe, null));
            layout_2.setBackground(getResources().getDrawable(R.drawable.shape_white_radius5_stroke_e5e5e5, null));
            iv_1.setVisibility(View.VISIBLE);
            iv_2.setVisibility(View.GONE);
        });

        layout_2.setOnClickListener(view -> {
            mole = 2;
            layout_2.setBackground(getResources().getDrawable(R.drawable.shape_white_radius5_stroke_5571fe, null));
            layout_1.setBackground(getResources().getDrawable(R.drawable.shape_white_radius5_stroke_e5e5e5, null));
            iv_2.setVisibility(View.VISIBLE);
            iv_1.setVisibility(View.GONE);
        });

        SpannableString ss = new SpannableString("1、淘号为原回收小号，目前以淘号形式回馈给玩家体验。我们仅提供充值最多的区服、小号总累充做参考。所淘号实际情况未知，具体需以实际游戏内情况为准。将有几率淘到极品号，也有几率淘到废号，纯属运气！");
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF6337")), 28, 47, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_content_1.setText(ss);
        SpannableString ss1 = new SpannableString("2、淘号一经兑换成功，不支持退换。请谨慎选择。");
        ss1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF6337")), 6, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_content_2.setText(ss1);


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
        tv_close.setOnClickListener(view -> popWindowDicker.dissmiss());

        tv_buy_currency.setOnClickListener(v -> {
            showpayDialog();
        });

        tv_do.setOnClickListener(view -> {
            if (!isChecked) {
                Toaster.show("请阅读并勾选淘号须知!");
                return;
            }
            switch (mole) {
                case 1:
                    float v = Float.parseFloat(userIcurrency) - needcurrency;
                    if (v < 0) {
                        Toaster.show("平台币余额不足,请充值!");
                        return;
                    }
                    if (!isBuy) {
                        isBuy = true;
                        doZeroBuyGoodsBuy(id, "ptb");
                    }
                    break;
                case 2:
                    int i = Integer.parseInt(userIIntegral) - needintegral;
                    if (i < 0) {
                        Toaster.show("积分余额不足!");
                        return;
                    }
                    if (!isBuy) {
                        isBuy = true;
                        doZeroBuyGoodsBuy(id, "integral");
                    }
                    break;
            }
        });
    }


    private CustomDialog payDialog;

    private void showpayDialog() {
        if (payDialog == null) {
            payDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_transaction_zero_pay, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            payDialog.setCancelable(false);
            payDialog.setCanceledOnTouchOutside(false);
            TextView btn_got_it = payDialog.findViewById(R.id.btn_got_it);
            LinearLayout layout_1 = payDialog.findViewById(R.id.layout_1);
            LinearLayout layout_2 = payDialog.findViewById(R.id.layout_2);

            layout_1.setOnClickListener(view -> {
                if (popWindowDicker != null) {
                    popWindowDicker.dissmiss();
                }
                if (payDialog != null) {
                    payDialog.dismiss();
                    payDialog = null;
                }
                startFragment(TaskCenterFragment.newInstance());
            });

            layout_2.setOnClickListener(view -> {
                if (checkLogin()) {
                    if (popWindowDicker != null) {
                        popWindowDicker.dissmiss();
                    }
                    if (payDialog != null) {
                        payDialog.dismiss();
                        payDialog = null;
                    }
                    startFragment(TopUpFragment.newInstance());
                }
            });
            btn_got_it.setOnClickListener(view -> {
                if (payDialog != null) {
                    payDialog.dismiss();
                    payDialog = null;
                }
            });
            payDialog.show();
        }
    }

    private String userIIntegral = "";
    private String userIcurrency = "";

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        //登录了刷新
        if (UserInfoModel.getInstance().isLogined()) {
            initUser();
        }
    }

    private void initUser() {
        UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
        if (userInfoBean != null) {
            userIcurrency = String.valueOf(userInfoBean.getPingtaibi());
            userIIntegral = String.valueOf(userInfoBean.getIntegral());
        }
    }

    @Override
    public void onClickButten(View view, String s, String id) {
        if (checkLogin()) {
            showPopListViewDicker(s, id);
        }
    }

    @Override
    public void onClickIcon(View view, String gameid, String game_type) {
        startFragment(GameDetailInfoFragment.newInstance(Integer.parseInt(gameid), Integer.parseInt(game_type)));
    }
}
