package com.zqhy.app.core.view.transfer;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.transfer.TransferGameListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.transfer.holder.TransferMainHolder;
import com.zqhy.app.core.vm.transfer.TransferViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.TimeUtils;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/17
 */

public class TransferMainFragment extends BaseListFragment<TransferViewModel> {
    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_TRANSFER_MAIN_STATE;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(TransferGameListVo.DataBean.class, new TransferMainHolder(_mActivity))
                .build();
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new GridLayoutManager(_mActivity, 2);
    }


    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    private int page = 1;
    private int pageCount = 24;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("停运转游");
        addHeaderView();
        setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof TransferGameListVo.DataBean) {
                TransferGameListVo.DataBean dataBean = (TransferGameListVo.DataBean) data;
                startForResult(TransferGameListFragment.newInstance(dataBean.getGameid(), dataBean.getGame_type()), 200);
            }

        });

        if (mRecyclerView != null) {
            mRecyclerView.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f5f5f5));
        }
    }

    private View mHeaderView;

    private AppCompatImageView mCivPortrait;
    private TextView mTvTransferCount;
    private TextView mTvTransferDetail;
    private LinearLayout mLlMyTransfers;
    private LinearLayout mLlMyTransfersList;

    private void addHeaderView() {
        mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_transfer_main_header, null);

        mCivPortrait = mHeaderView.findViewById(R.id.civ_portrait);
        mTvTransferCount = mHeaderView.findViewById(R.id.tv_transfer_count);
        mTvTransferDetail = mHeaderView.findViewById(R.id.tv_transfer_detail);
        mLlMyTransfers = mHeaderView.findViewById(R.id.ll_my_transfers);
        mLlMyTransfersList = mHeaderView.findViewById(R.id.ll_my_transfers_list);

        mTvTransferDetail.setOnClickListener(v -> {
            //转游明细
            start(new TransferRecordListFragment());
        });

        if (UserInfoModel.getInstance().isLogined()) {
            mCivPortrait.setImageResource(R.mipmap.ic_user_login);
        } else {
            mCivPortrait.setImageResource(R.mipmap.ic_user_un_login);
        }
        addHeaderView(mHeaderView);
    }

    private void setMyTransfersList(List<TransferGameListVo.DataBean> transfersList) {
        if (transfersList == null || transfersList.size() == 0) {
            mLlMyTransfers.setVisibility(View.GONE);
        } else {
            mLlMyTransfers.setVisibility(View.VISIBLE);
            mLlMyTransfersList.removeAllViews();
            for (int i = 0; i < transfersList.size(); i++) {
                TransferGameListVo.DataBean transferGameBean = transfersList.get(i);
                mLlMyTransfersList.addView(createTransferView(transferGameBean));
            }
        }
    }

    private View createTransferView(TransferGameListVo.DataBean transferGameBean) {
        View view = LayoutInflater.from(_mActivity).inflate(R.layout.item_layout_my_tranfers, null);

        LinearLayout mRootView = view.findViewById(R.id.rootView);
        ImageView mGameIconIV = view.findViewById(R.id.gameIconIV);
        TextView mTvGameName = view.findViewById(R.id.tv_game_name);
        TextView mTvEndTime = view.findViewById(R.id.tv_end_time);

        float density = ScreenUtil.getScreenDensity(_mActivity);


        GradientDrawable rootGd = new GradientDrawable();
        rootGd.setColor(ContextCompat.getColor(_mActivity, R.color.white));
        rootGd.setCornerRadius(density * 5);
        rootGd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_cccccc));
        mRootView.setBackground(rootGd);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, (int) (density * 10), 0);
        view.setLayoutParams(params);

        GlideApp.with(_mActivity)
                .asBitmap()
                .load(transferGameBean.getGameicon())
                .placeholder(R.mipmap.ic_placeholder)
                .centerCrop()
                .into(mGameIconIV);


        mTvGameName.setText(transferGameBean.getGamename());


        String time = TimeUtils.formatTimeStamp(transferGameBean.getEndtime() * 1000, "yyyy-MM-dd HH:mm");
        mTvEndTime.setText("结束时间：" + time);

        mRootView.setOnClickListener(v -> {
            //转游记录
            startForResult(TransferRecordFragment.newInstance(transferGameBean.getIndex_id(), transferGameBean.getGamename()), 200);
        });

        return view;
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 202) {
            getNetWorkData();
        }
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
        if (page < 0) {
            return;
        }
        getMoreNetWorkData();
    }

    private void getNetWorkData() {
        if (mViewModel != null) {
            page = 1;
            getTransferGameMainData();
        }
    }

    private void getMoreNetWorkData() {
        if (mViewModel != null) {
            page = 1;
            getTransferGameMainData();
        }
    }

    private void getTransferGameMainData() {
        mViewModel.getTransferGameMainData(page, pageCount, new OnBaseCallback<TransferGameListVo>() {
            @Override
            public void onAfter() {
                super.onAfter();
                refreshAndLoadMoreComplete();
            }

            @Override
            public void onSuccess(TransferGameListVo transferGameListVo) {
                if (transferGameListVo != null) {
                    if (transferGameListVo.isStateOK()) {
                        if (transferGameListVo.getData() != null) {
                            TransferGameListVo.TransferVo transferVo = transferGameListVo.getData();
                            if (page == 1) {
                                if (mTvTransferCount != null) {
                                    mTvTransferCount.setText(String.valueOf(transferVo.getUser_points()));
                                }
                                setMyTransfersList(transferVo.getApply_log());

                                clearData();
                                if (transferVo.getTransfer_reward_list() != null) {
                                    addAllData(transferVo.getTransfer_reward_list());
                                } else {
                                    //empty data
                                }
                            } else {
                                if (transferVo.getTransfer_reward_list() != null) {
                                    addAllData(transferVo.getTransfer_reward_list());
                                } else {
                                    // no more data
                                    page = -1;
                                    setListNoMore(true);
                                }
                            }
                        }
                    } else {
                        Toaster.show( transferGameListVo.getMsg());
                    }
                }
            }
        });
    }
}
