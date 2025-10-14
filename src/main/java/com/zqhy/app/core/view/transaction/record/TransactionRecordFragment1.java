package com.zqhy.app.core.view.transaction.record;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.transaction.TradeGoodsWaitPayInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.StringUtil;
import com.zqhy.app.core.view.transaction.TransactionGoodDetailFragment;
import com.zqhy.app.core.view.transaction.base.BaseViewPagerFragment1;
import com.zqhy.app.core.view.transaction.buy.TransactionBuyFragment;
import com.zqhy.app.core.view.transaction.util.CustomPopWindow;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.imageview.ClipRoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class TransactionRecordFragment1 extends BaseViewPagerFragment1<TransactionViewModel> {
    private PagerAdapter adapter;

    @Override
    protected String[] createPageTitle() {
        return new String[]{"收藏", "全部", "已购买", "出售中", "已出售"};
    }

    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected List<Fragment> createFragments() {
        fragmentList.add(TransactionRecordListFragment1.newInstance("user_collect"));
        fragmentList.add(TransactionRecordListFragment.newInstance("user_all"));
        fragmentList.add(TransactionRecordListFragment.newInstance("user_buy"));
        fragmentList.add(TransactionRecordListFragment.newInstance("user_selling"));
        fragmentList.add(TransactionRecordListFragment.newInstance("user_sold"));
        return fragmentList;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "我的交易页";
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    private boolean isAnyDataChange = false;

    private int selectedPosition = 0;

    private TextView title_bottom_line;
    private LinearLayout topLinearLayout;
    private final int action_buy = 0x7451;
    TextView tv_overtime;
    private TextView mTvGameSuffix;
    TextView tv_nopay;
    TextView tv_pay;
    private ClipRoundImageView mIvTransactionImage;
    private ClipRoundImageView mIvTransactionImage1;
    private ClipRoundImageView mIvTransactionImage2;
    private TextView mTvTransactionGameName;
    private TextView mTvTransactionPrice;
    private TextView mTvTransactionXhRecharge;
    private TextView mTvPercent;
    private TextView mTvPercent1;
    private TextView tv_genre_str;
    private TextView tv_play_count;
    private TextView tv_server_info;
    private LinearLayout mlayoutPercent;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("我的交易");
        title_bottom_line = findViewById(R.id.title_bottom_line);
        title_bottom_line.setVisibility(View.GONE);

        setAdapter();
        topLinearLayout = getTopLinearLayout();

        View contentView = LayoutInflater.from(_mActivity).inflate(R.layout.item_transaction_record_top, null);
        tv_nopay = contentView.findViewById(R.id.tv_nopay);
        tv_pay = contentView.findViewById(R.id.tv_pay);
        tv_overtime = contentView.findViewById(R.id.tv_overtime);
        mTvGameSuffix = contentView.findViewById(R.id.tv_game_suffix);
        mIvTransactionImage = contentView.findViewById(R.id.iv_transaction_image);
        mIvTransactionImage1 = contentView.findViewById(R.id.iv_transaction_image1);
        mIvTransactionImage2 = contentView.findViewById(R.id.iv_transaction_image2);
        mTvTransactionGameName = contentView.findViewById(R.id.tv_transaction_game_name);
        mTvTransactionPrice = contentView.findViewById(R.id.tv_transaction_price);
        mTvTransactionXhRecharge = contentView.findViewById(R.id.tv_transaction_xh_recharge);
        mTvPercent = contentView.findViewById(R.id.tv_percent);
        mTvPercent1 = contentView.findViewById(R.id.tv_percent1);
        mlayoutPercent = contentView.findViewById(R.id.layout_percent);
        tv_genre_str = contentView.findViewById(R.id.tv_genre_str);
        tv_play_count = contentView.findViewById(R.id.tv_play_count);
        tv_server_info = contentView.findViewById(R.id.tv_server_info);

        topLinearLayout.addView(contentView);
        topLinearLayout.setVisibility(View.GONE);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedPosition = position;
                Log.d("TransactionFragment1", "position-----" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initData();

    }

    private void initData() {
        getTradeGoodsWaitPay();
    }

    /**
     * 屏幕底部弹出
     * 方法名@：showAtLocation(contentView, Gravity.BOTTOM, 0, 0)
     * resource:PopWindow 布局
     */
    CustomPopWindow popWindow;

    private void showPopListViewCloseOrder(TradeGoodsWaitPayInfoVo data) {
        View contentView = LayoutInflater.from(_mActivity).inflate(R.layout.pop_transaction_record_close_order, null);
        TextView tv_close = contentView.findViewById(R.id.tv_close);
        TextView tv_cancel = contentView.findViewById(R.id.tv_cancel);

        //处理popWindow 显示内容
        //创建并显示popWindow
        popWindow = new CustomPopWindow.PopupWindowBuilder(_mActivity)
                .enableOutsideTouchableDissmiss(false)
                .enableBackgroundDark(true)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                .create();
        popWindow.setBackgroundAlpha(0.5f);
        popWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow.dissmiss();
            }
        });

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mViewModel != null) {
                    if (data.getData().getOrderid() != null && !StringUtil.isEmpty(data.getData().getOrderid())) {
                        mViewModel.cancelTradePayOrder(data.getData().getOrderid(), new OnBaseCallback() {
                            @Override
                            public void onSuccess(BaseVo data) {
                                if (data.isStateOK()) {
                                    Toaster.show("订单取消成功");
                                    topLinearLayout.setVisibility(View.GONE);
                                    popWindow.dissmiss();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private CountDownTimer countDownTimer;

    private void getTradeGoodsWaitPay() {
        if (mViewModel != null) {
            mViewModel.getTradeGoodsWaitPay(new OnBaseCallback<TradeGoodsWaitPayInfoVo>() {
                @Override
                public void onSuccess(TradeGoodsWaitPayInfoVo data) {
                    showSuccess();
                    if (data.getData() != null) {
                        if (data.getData().getPic().size() >= 2) {
                            GlideUtils.loadRoundImage(_mActivity, data.getData().getPic().get(0).getPic_path(), mIvTransactionImage, R.mipmap.ic_placeholder);
                            GlideUtils.loadRoundImage(_mActivity, data.getData().getPic().get(1).getPic_path(), mIvTransactionImage1, R.mipmap.ic_placeholder);
                        } else {
                            if (data.getData().getPic().size() > 0) {
                                GlideUtils.loadRoundImage(_mActivity, data.getData().getPic().get(0).getPic_path(), mIvTransactionImage, R.mipmap.ic_placeholder);
                                mIvTransactionImage1.setVisibility(View.INVISIBLE);
                            } else {
                                mIvTransactionImage.setVisibility(View.INVISIBLE);
                                mIvTransactionImage1.setVisibility(View.INVISIBLE);
                            }
                        }
                        GlideUtils.loadRoundImage(_mActivity, data.getData().getGameicon(), mIvTransactionImage2, R.mipmap.ic_placeholder);
                        mTvTransactionGameName.setText(data.getData().getGamename());
                        mTvTransactionPrice.setText(data.getData().getGoods_price());

                        if (!TextUtils.isEmpty(data.getData().getGame_suffix())){//游戏后缀
                            mTvGameSuffix.setVisibility(View.VISIBLE);
                            mTvGameSuffix.setText(data.getData().getGame_suffix());
                        }else {
                            mTvGameSuffix.setVisibility(View.GONE);
                        }

                        mTvTransactionXhRecharge.setText(CommonUtils.saveTwoSizePoint(data.getData().getXh_pay_game_total()));
                        if ("1".equals(data.getData().getGame_type())){
                            if (data.getData().getProfit_rate() <= 0.1 && data.getData().getProfit_rate() > 0.01) {
                                mlayoutPercent.setVisibility(View.VISIBLE);
                                mTvPercent.setText("0" + CommonUtils.saveTwoSizePoint(data.getData().getProfit_rate() * 10) + "折");
                                mTvPercent1.setText("抄底");
                            } else if (data.getData().getProfit_rate() <= 0.2 && data.getData().getProfit_rate() > 0.1) {
                                mlayoutPercent.setVisibility(View.VISIBLE);
                                mTvPercent.setText(CommonUtils.saveTwoSizePoint(data.getData().getProfit_rate() * 10) + "折");
                                mTvPercent1.setText("捡漏");
                            } else {
                                mlayoutPercent.setVisibility(View.INVISIBLE);
                            }
                        }else {
                            mlayoutPercent.setVisibility(View.INVISIBLE);
                        }
                        tv_genre_str.setText(data.getData().getGenre_str());
//                        tv_play_count.setText(" • " + data.getData().getPlay_count() + "人在玩");
                        tv_server_info.setText("区服: " + data.getData().getServer_info());

                        topLinearLayout.setVisibility(View.VISIBLE);
                        topLinearLayout.setOnClickListener(v -> {
                            startFragment(TransactionGoodDetailFragment.newInstance(data.getData().getGid(), data.getData().getGameid()));
                        });

                        tv_nopay.setOnClickListener(view -> {
                            showPopListViewCloseOrder(data);
                        });

                        tv_pay.setOnClickListener(view -> {
                            startForResult(TransactionBuyFragment.newInstance(data.getData().getGid(), data.getData().getGamename(),data.getData().getGame_suffix(), data.getData().getGameicon(),
                                    data.getData().getGenre_str(), data.getData().getPlay_count(),
                                    data.getData().getXh_showname(), data.getData().getServer_info(), data.getData().getProfit_rate(),
                                    data.getData().getGoods_price(), data.getData().getGameid(), data.getData().getGame_type(), 1, data.getData().getGoods_type()), action_buy);
                        });
                        long order_close_time = data.getData().getCount_down() * 1000;
                        if (order_close_time>1000) {
                            countDownTimer = new CountDownTimer(order_close_time, 1000) {
                                @Override
                                public void onTick(long l) {
                                    long time = l / 1000;
                                    long d = time / 24 / 60 / 60 % 60;
                                    long h;
                                    if (d > 0) {
                                        h = (time / 60 / 60 % 60) - 24;
                                    } else {
                                        h = time / 60 / 60 % 60;
                                    }
                                    long m = time / 60 % 60;
                                    long s = time % 60;

                                    String h0 = "";
                                    String m0 = "";
                                    String s0 = "";
                                    if (h < 10) {
                                        h0 = "0";
                                    }
                                    if (m < 10) {
                                        m0 = "0";
                                    }

                                    if (s < 10) {
                                        s0 = "0";
                                    }

                                    tv_overtime.setText(m0 + m + ":" + s0 + s);
                                }

                                @Override
                                public void onFinish() {
                                    topLinearLayout.setVisibility(View.GONE);
                                }
                            };
                            countDownTimer.start();
                        }else {
                            topLinearLayout.setVisibility(View.GONE);
                        }
                    } else {
                        topLinearLayout.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    @Override
    public void pop() {
        if (isAnyDataChange) {
            if (getPreFragment() == null) {
                _mActivity.setResult(Activity.RESULT_OK);
            } else {
                setFragmentResult(RESULT_OK, null);
            }
        }
        _mActivity.finish();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            isAnyDataChange = true;
        }
        if (fragmentList != null) {
            try {
                BaseFragment fragment = (BaseFragment) fragmentList.get(selectedPosition);
                fragment.onFragmentResult(requestCode, resultCode, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
