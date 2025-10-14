package com.zqhy.app.core.view.user.welfare;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.welfare.GiftCardFootVo;
import com.zqhy.app.core.data.model.welfare.MyGiftCardListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.GiftCardFooterItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.GiftCardItemHolder;
import com.zqhy.app.core.vm.user.welfare.MyGiftCardViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class MyCardListFragment extends BaseListFragment<MyGiftCardViewModel> {
    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_MY_GIFT_CARD_STATE;
    }

    @Override
    protected String getUmengPageName() {
        return "我的礼包（子）";
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        BaseFragment targetFragment = getParentFragment() == null ? this : (BaseFragment) getParentFragment();
        return new BaseRecyclerAdapter.Builder<>()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(MyGiftCardListVo.DataBean.class, new GiftCardItemHolder(_mActivity))
                .bind(GiftCardFootVo.class, new GiftCardFooterItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, targetFragment);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("我的礼包");
        setTitleLayout(LAYOUT_ON_CENTER);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
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

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        getMoreNetWorkData();
    }

    private int page = 1;
    private int pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    private void getNetWorkData() {
        if (mViewModel != null) {
            page = 1;
            getMyGiftCardData();
        }
    }

    private void getMoreNetWorkData() {
        if (mViewModel != null) {
            page++;
            getMyGiftCardData();
        }
    }

    private void getMyGiftCardData() {
        mViewModel.getMyGiftCardData(page, pageCount, new OnBaseCallback<MyGiftCardListVo>() {
            @Override
            public void onAfter() {
                super.onAfter();
                refreshAndLoadMoreComplete();
            }

            @Override
            public void onSuccess(MyGiftCardListVo myGiftCardListVo) {
                if (myGiftCardListVo != null) {
                    if (myGiftCardListVo.isStateOK()) {
                        if (myGiftCardListVo.getData() != null && !myGiftCardListVo.getData().isEmpty()) {
                            if (page == 1) {
                                clearData();
                            }
                            addAllData(myGiftCardListVo.getData());
                            if (myGiftCardListVo.getData().size() < getPageCount()) {
                                addDataWithNotifyData(new GiftCardFootVo());
                            }
                        } else {
                            if (page == 1) {
                                clearData();
                                addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                            } else {
                                page = -1;
                                addDataWithNotifyData(new GiftCardFootVo());
                            }
                            setListNoMore(true);
                        }
                    } else {
                        Toaster.show( myGiftCardListVo.getMsg());
                    }
                }
            }
        });
    }
}

