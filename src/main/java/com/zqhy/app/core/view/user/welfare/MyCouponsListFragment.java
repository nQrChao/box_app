package com.zqhy.app.core.view.user.welfare;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.box.other.hjq.toast.Toaster;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.welfare.MyCouponRecordStatVo;
import com.zqhy.app.core.data.model.welfare.MyCouponsListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.user.welfare.holder.CouponsItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.user.welfare.MyCouponsListViewModel;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class MyCouponsListFragment extends BaseFragment<MyCouponsListViewModel> {
    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_MY_COUPONS_STATE;
    }

    @Override
    protected String getUmengPageName() {
        return "我的代金券";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_coupon_list;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private XRecyclerView mRecyclerView;
    private BaseRecyclerAdapter mAdapter;
    private LinearLayout mLlGameCoupon;
    private LinearLayout mLlMallCoupon;
    private LinearLayout mLlHistoryCoupon;
    private TextView     mTvGameCouponName;
    private TextView     mTvGameCouponTips;
    private TextView     mTvMallCouponName;
    private TextView     mTvMallCouponTips;
    private TextView     mTvHistoryCouponName;
    private TextView     mTvHistoryCouponTips;
    private TextView     mTvTips;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("我的代金券",true);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(MyCouponsListVo.DataBean.class, new CouponsItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getNetWorkData();
            }

            @Override
            public void onLoadMore() {
                if (page < 0) {
                    return;
                }
                page++;
                getNetWorkData();
            }
        });
        mRecyclerView.setRefreshTimeVisible(true);

        mLlGameCoupon = findViewById(R.id.ll_game_coupon);
        mLlMallCoupon = findViewById(R.id.ll_mall_coupon);
        mLlHistoryCoupon = findViewById(R.id.ll_history_coupon);
        mTvGameCouponName = findViewById(R.id.tv_game_coupon_name);
        mTvGameCouponTips = findViewById(R.id.tv_game_coupon_tips);
        mTvMallCouponName = findViewById(R.id.tv_mall_coupon_name);
        mTvMallCouponTips = findViewById(R.id.tv_mall_coupon_tips);
        mTvHistoryCouponName = findViewById(R.id.tv_history_coupon_name);
        mTvHistoryCouponTips = findViewById(R.id.tv_history_coupon_tips);
        mTvTips = findViewById(R.id.tv_tips);

        mLlGameCoupon.setOnClickListener(view -> {
            couponListType = "game";
            mTvTips.setText("·游戏内支付时选择“优惠券”，选择对应的代金券抵扣");
            setSelect();
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
            initData();
        });
        mLlMallCoupon.setOnClickListener(view -> {
            couponListType = "platform";
            mTvTips.setText("·游戏内支付时选择“优惠券”，选择对应的代金券抵扣");
            setSelect();
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
            initData();
        });
        mLlHistoryCoupon.setOnClickListener(view -> {
            couponListType = "history";
            mTvTips.setText("·仅展示近期1个月内信息");
            setSelect();
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
            initData();
        });
        findViewById(R.id.tv_instruction).setOnClickListener(view -> {
            BrowserActivity.newInstance(_mActivity, "https://mobile.tsyule.cn/index.php/Index/view/?id=111455");
        });
        getCouponRecordStat();
    }

    private void setSelect(){
        if (couponListType.equals("game")){
            mTvGameCouponName.setTextColor(Color.parseColor("#5571FE"));
            mTvGameCouponTips.setTextColor(Color.parseColor("#5571FE"));
            mTvGameCouponTips.setBackgroundResource(R.drawable.shape_1a5571fe_big_radius);
            mTvMallCouponName.setTextColor(Color.parseColor("#232323"));
            mTvMallCouponTips.setTextColor(Color.parseColor("#9B9B9B"));
            mTvMallCouponTips.setBackgroundResource(R.drawable.shape_e5e5e5_big_radius);
            mTvHistoryCouponName.setTextColor(Color.parseColor("#232323"));
            mTvHistoryCouponTips.setTextColor(Color.parseColor("#9B9B9B"));
            mTvHistoryCouponTips.setBackgroundResource(R.drawable.shape_e5e5e5_big_radius);
        }else if (couponListType.equals("platform")){
            mTvGameCouponName.setTextColor(Color.parseColor("#232323"));
            mTvGameCouponTips.setTextColor(Color.parseColor("#9B9B9B"));
            mTvGameCouponTips.setBackgroundResource(R.drawable.shape_e5e5e5_big_radius);
            mTvMallCouponName.setTextColor(Color.parseColor("#5571FE"));
            mTvMallCouponTips.setTextColor(Color.parseColor("#5571FE"));
            mTvMallCouponTips.setBackgroundResource(R.drawable.shape_1a5571fe_big_radius);
            mTvHistoryCouponName.setTextColor(Color.parseColor("#232323"));
            mTvHistoryCouponTips.setTextColor(Color.parseColor("#9B9B9B"));
            mTvHistoryCouponTips.setBackgroundResource(R.drawable.shape_e5e5e5_big_radius);
        }else if (couponListType.equals("history")){
            mTvGameCouponName.setTextColor(Color.parseColor("#232323"));
            mTvGameCouponTips.setTextColor(Color.parseColor("#9B9B9B"));
            mTvGameCouponTips.setBackgroundResource(R.drawable.shape_e5e5e5_big_radius);
            mTvMallCouponName.setTextColor(Color.parseColor("#232323"));
            mTvMallCouponTips.setTextColor(Color.parseColor("#9B9B9B"));
            mTvMallCouponTips.setBackgroundResource(R.drawable.shape_e5e5e5_big_radius);
            mTvHistoryCouponName.setTextColor(Color.parseColor("#5571FE"));
            mTvHistoryCouponTips.setTextColor(Color.parseColor("#5571FE"));
            mTvHistoryCouponTips.setBackgroundResource(R.drawable.shape_1a5571fe_big_radius);
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

    private int page      = 1;
    private int pageCount = 12;
    public String couponListType = "game";

    private void initData() {
        page = 1;
        getNetWorkData();
    }

    /**
     * 获取优惠券列表数据
     */
    private void getNetWorkData() {
        if (mViewModel != null) {
            mViewModel.getMyCouponListData(page, pageCount, couponListType, new OnBaseCallback<MyCouponsListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    if (mRecyclerView != null) {
                        mRecyclerView.refreshComplete();
                        mRecyclerView.loadMoreComplete();
                    }
                }

                @Override
                public void onSuccess(MyCouponsListVo myCouponsListVo) {
                    if (myCouponsListVo != null) {
                        if (myCouponsListVo.isStateOK()) {
                            if (myCouponsListVo.getData() != null && !myCouponsListVo.getData().isEmpty()) {
                                List<MyCouponsListVo.DataBean> itemListBeanList = new ArrayList<>();
                                for (MyCouponsListVo.DataBean couponListBean : myCouponsListVo.getData()) {
                                    switch (couponListType) {
                                        case "unused": {
                                            couponListBean.setStatus(MyCouponsListVo.mTabUnUsed);
                                        }
                                        break;
                                        case "used": {
                                            couponListBean.setStatus(MyCouponsListVo.mTabUsed);
                                        }
                                        break;
                                        case "expired": {
                                            couponListBean.setStatus(MyCouponsListVo.mTabPastDue);
                                        }
                                        break;
                                        default:
                                            break;
                                    }
                                    itemListBeanList.add(couponListBean);
                                }
                                if (page == 1) {
                                    mAdapter.clear();
                                }
                                mAdapter.addAllData(itemListBeanList);
                                if (myCouponsListVo.getData().size() < pageCount) {
                                    //has no more data
                                    page = -1;
                                    mRecyclerView.setNoMore(true);
                                }
                                mAdapter.notifyDataSetChanged();
                            } else {
                                if (page == 1) {
                                    mAdapter.clear();
                                    mAdapter.addData(new EmptyDataVo(R.mipmap.ic_game_detail_coupon_dialog_list_empty_bg));
                                } else {
                                    page = -1;
                                    mRecyclerView.setNoMore(true);
                                    mAdapter.notifyDataSetChanged();
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toaster.show( myCouponsListVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void getCouponRecordStat(){
        if (mViewModel != null) {
            mViewModel.getCouponRecordStat(new OnBaseCallback<MyCouponRecordStatVo>() {

               @Override
               public void onSuccess(MyCouponRecordStatVo data) {
                   if (data != null) {
                       if (data.isStateOK()) {
                           mTvGameCouponTips.setText(data.getData().getGame() + "张");
                           mTvMallCouponTips.setText(data.getData().getPlatform() + "张");
                       }
                   }
               }
           });
        }

    }

    private CustomDialog commentTipsDialog;
    public void showCommentTipsDialog() {
        if (commentTipsDialog == null) {
            commentTipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_coupon_list_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        }
        commentTipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if (commentTipsDialog != null && commentTipsDialog.isShowing()){
                commentTipsDialog.dismiss();
            }
        });
        commentTipsDialog.show();
    }
}