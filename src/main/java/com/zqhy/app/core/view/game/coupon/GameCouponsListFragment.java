package com.zqhy.app.core.view.game.coupon;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.coupon.GameCouponsListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.user.welfare.MyCouponsListFragment;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author leeham2734
 * @date 2020/8/25-9:51
 * @description
 */
public class GameCouponsListFragment extends BaseListFragment<GameViewModel> {

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "领券中心";
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("领券中心");
        setListViewBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f6f6f6));
        addHeaderView();
        initData();
    }


    public void addHeaderView() {
        ImageView mHeaderView = new ImageView(_mActivity);
        mHeaderView.setAdjustViewBounds(true);
        mHeaderView.setImageResource(R.mipmap.img_game_coupon);
        mHeaderView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ScreenUtil.getScreenWidth(_mActivity), ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = ScreenUtil.dp2px(_mActivity, 15);
        mHeaderView.setLayoutParams(params);
        addHeaderView(mHeaderView);
    }

    @Override
    protected View getTitleRightView() {
        TextView view = new TextView(_mActivity);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ScreenUtil.dp2px(_mActivity, 72), ScreenUtil.dp2px(_mActivity, 28));
        view.setLayoutParams(params);

        view.setText("我的礼券");
        view.setTextSize(13);
        view.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff6005));
        view.setGravity(Gravity.CENTER);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(ScreenUtil.dp2px(_mActivity, 28));
        gd.setColor(Color.parseColor("#FFFCED"));
        gd.setStroke(ScreenUtil.dp2px(_mActivity, 1), Color.parseColor("#FF6005"));
        view.setBackground(gd);

        view.setOnClickListener(v -> {
            if (checkLogin()) {
                //startFragment(GameWelfareFragment.newInstance(2));
                startFragment(new MyCouponsListFragment());
            }
        });
        return view;
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(GameCouponsListVo.DataBean.class, new GameCouponsItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        onRefresh();
    }


    private int page = 1;

    @Override
    public void onRefresh() {
        super.onRefresh();
        page = 1;
        initData();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (page < 0) {
            return;
        }
        page++;
        initData();
    }

    private int pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    private void initData() {
        if (mViewModel != null) {
            mViewModel.getGameCoupon(page, pageCount, new OnBaseCallback<GameCouponsListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(GameCouponsListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null && !data.getData().isEmpty()) {
                                if (page == 1) {
                                    clearData();
                                }
                                addAllData(data.getData());
                                notifyData();
                            } else {
                                if (page == 1) {
                                    //show empty
                                    addData(new EmptyDataVo());
                                } else {
                                    page = -1;
                                }
                                setListNoMore(true);
                                notifyData();
                            }
                        } else {
                            //ToastT.error(_mActivity, data.getMsg());
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    public void getCoupon(int coupon_id, int gameid) {
        if (checkLogin() && checkUserBindPhoneTips1()) {
            if (mViewModel != null) {
                mViewModel.getCoupon(coupon_id, new OnBaseCallback() {
                    @Override
                    public void onSuccess(BaseVo data) {
                        if (data != null) {
                            if (data.isStateOK()) {
                                //ToastT.success(_mActivity, "领取成功");
                                Toaster.show("领取成功");

                                List<GameCouponsListVo.DataBean> list = mDelegateAdapter.getData();
                                if (list != null && !list.isEmpty()) {
                                    for (GameCouponsListVo.DataBean dataBean : list) {
                                        if (dataBean.getItemId() == gameid) {
                                            List<GameCouponsListVo.CouponVo> couponVoList = dataBean.getCoupon_list();
                                            for (GameCouponsListVo.CouponVo couponVo : couponVoList) {
                                                if (coupon_id == couponVo.getCoupon_id()) {
                                                    couponVo.setStatus(10);
                                                }
                                            }
                                        } else {
                                            continue;
                                        }
                                    }
                                }
                                notifyData();
                            } else {
                                Toaster.show(data.getMsg());
                                //ToastT.error(_mActivity, data.getMsg());
                            }
                        }
                    }
                });
            }
        }
    }
}
