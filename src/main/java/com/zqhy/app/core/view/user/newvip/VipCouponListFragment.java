package com.zqhy.app.core.view.user.newvip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.user.VipCouponListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.user.newvip.holder.NewVipCouponItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.user.UserViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/21
 */

public class VipCouponListFragment extends BaseListFragment<UserViewModel> {

    public static VipCouponListFragment newInstance(int type) {
        VipCouponListFragment fragment = new VipCouponListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_GAME_COUPON_LIST_STATE;
    }

    @Override
    protected String getStateEventTag() {
        return String.valueOf(type);
    }


    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.class, new NewVipCouponItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }


    private int type = 0;
    private int page = 1, pageCount = 12;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
        super.initView(state);
        if (type == 0){
            initActionBackBarAndTitle("会员专属·游戏代金券");
        }else {
            initActionBackBarAndTitle("会员专属·游戏礼包");
        }
        setTitleLayout(LAYOUT_ON_CENTER);
        setListViewBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));
        addHeaderView();

        initData();
    }

    private View mHeaderView;
    private void addHeaderView() {
        mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_vip_coupon_head, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ScreenUtil.getScreenWidth(_mActivity), ViewGroup.LayoutParams.WRAP_CONTENT);
        mHeaderView.setLayoutParams(params);
        ImageView mIvHead = mHeaderView.findViewById(R.id.iv_head);
        if (type == 0){
            mIvHead.setImageResource(R.mipmap.ic_vip_coupon_top_coupon);
        }else {
            mIvHead.setImageResource(R.mipmap.ic_vip_coupon_top_gift_bag);
        }
        addHeaderView(mHeaderView);
    }

    @Override
    public int getPageCount() {
        return pageCount;
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        initData();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (page < 0) {
            return;
        }
        page++;
        getNetWorkData();
    }

    private void initData() {
        page = 1;
        getNetWorkData();
    }

    private void getNetWorkData() {
        if (mViewModel != null) {
            mViewModel.getVipCouponListData(type, page, pageCount, new OnBaseCallback<VipCouponListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    refreshAndLoadMoreComplete();
                    showSuccess();
                }

                @Override
                public void onSuccess(VipCouponListVo gameCouponListVo) {
                    if (gameCouponListVo != null) {
                        if (gameCouponListVo.isStateOK()) {
                            if (gameCouponListVo.getData() != null && gameCouponListVo.getData().size() > 0) {
                                if (page == 1) {
                                    clearData();
                                }
                                addAllData(gameCouponListVo.getData());
                                if (gameCouponListVo.getData().size() < pageCount) {
                                    //has no more data
                                    page = -1;
                                    setListNoMore(true);
                                }
                                notifyData();
                            } else {
                                if (page == 1) {
                                    addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                                }
                                page = -1;
                                setListNoMore(true);
                                notifyData();
                            }
                        } else {
                            Toaster.show( gameCouponListVo.getMsg());
                        }
                    }
                }
            });
        }
    }
}
