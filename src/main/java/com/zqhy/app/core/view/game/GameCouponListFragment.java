package com.zqhy.app.core.view.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameCouponListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.community.integral.CommunityIntegralMallFragment;
import com.zqhy.app.core.view.game.holder.GameCouponItemHolder;
import com.zqhy.app.core.view.user.newvip.NewUserVipFragment;
import com.zqhy.app.core.view.user.provincecard.NewProvinceCardFragment;
import com.zqhy.app.core.view.user.welfare.MyCouponsListFragment;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/21
 */

public class GameCouponListFragment extends BaseListFragment<GameViewModel> {

    public static GameCouponListFragment newInstance(int gameid) {
        GameCouponListFragment fragment = new GameCouponListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("gameid", gameid);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_GAME_COUPON_LIST_STATE;
    }

    @Override
    protected String getStateEventTag() {
        return String.valueOf(gameid);
    }


    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(GameCouponListVo.DataBean.class, new GameCouponItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }


    private int gameid;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gameid = getArguments().getInt("gameid");
        }
        super.initView(state);
        initActionBackBarAndTitle("游戏代金券");
        setTitleLayout(LAYOUT_ON_LEFT);
        setLoadingMoreEnabled(false);
        getNetWorkData();
        setListViewBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));
        addHeaderView();
    }

    View mHeaderView;

    private void addHeaderView() {
        mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_open_vip_member, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ScreenUtil.getScreenWidth(_mActivity), ViewGroup.LayoutParams.WRAP_CONTENT);
        mHeaderView.setLayoutParams(params);
        LinearLayout mTvGoToOpenVip = mHeaderView.findViewById(R.id.ll_go_to_open_vip);
        TextView mTvApp = mHeaderView.findViewById(R.id.tv_app);
        mTvApp.setText(getAppReferNameByXML(R.string.string_dyx_month_card));
        mTvGoToOpenVip.setOnClickListener(view -> {
            /*if (checkLogin()) {
                startFragment(new VipMemberFragment());
            }*/
            startFragment(new NewProvinceCardFragment());
        });
        mHeaderView.findViewById(R.id.tv_store).setOnClickListener(v -> {
            startFragment(new CommunityIntegralMallFragment());
        });
        mHeaderView.findViewById(R.id.tv_vip).setOnClickListener(v -> {
           startFragment(new NewUserVipFragment());
        });
        addHeaderView(mHeaderView);
    }

    @Override
    protected View getTitleRightView() {
        TextView tv = new TextView(_mActivity);
        tv.setText("我的礼券");
        tv.setIncludeFontPadding(false);
        int paddingLR = (int) (10 * density);
        int paddingTb = (int) (2 * density);
        tv.setPadding(paddingLR, paddingTb, paddingLR, paddingTb);
        tv.setTextSize(15);
        tv.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_868686));


        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (24 * density));
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(0, 0, (int) (4 * density), 0);
        tv.setLayoutParams(params);

        tv.setOnClickListener(view -> {
            if (checkLogin()) {
                //start(GameWelfareFragment.newInstance(2));
                startFragment(new MyCouponsListFragment());
            }
        });
        return tv;
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();

        registerObserver(Constants.EVENT_KEY_GAME_USER_GET_COUPON_DATA, BaseResponseVo.class).observe(this, baseResponseVo -> {

        });
    }


    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        getNetWorkData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getNetWorkData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getNetWorkData();
    }


    private void getNetWorkData() {
        if (mViewModel != null) {
            mViewModel.getGameCouponListData(gameid, new OnBaseCallback<GameCouponListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(GameCouponListVo gameCouponListVo) {
                    if (gameCouponListVo != null) {
                        if (gameCouponListVo.isStateOK()) {
                            clearData();
                            if (gameCouponListVo.getData() != null) {
                                addAllData(gameCouponListVo.getData());
                            } else {
                                addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                            }
                        } else {
                            Toaster.show(gameCouponListVo.getMsg());
                            //ToastT.error(_mActivity, gameCouponListVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    public void getCoupon(int coupon_id) {
        if (checkLogin() && checkUserBindPhoneTips1()) {
            if (mViewModel != null) {
                mViewModel.getCoupon(coupon_id, new OnBaseCallback() {
                    @Override
                    public void onSuccess(BaseVo data) {
                        if (data != null) {
                            if (data.isStateOK()) {
                                //ToastT.success(_mActivity, "领取成功");
                                Toaster.show( "领取成功");
                                setFragmentResult(Activity.RESULT_OK, null);
                                setRefresh();
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

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BIND_PHONE) {
            getNetWorkData();
        }
    }
}
