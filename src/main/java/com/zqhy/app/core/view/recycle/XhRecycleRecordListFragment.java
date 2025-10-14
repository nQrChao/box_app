package com.zqhy.app.core.view.recycle;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.recycle.XhRecycleRecordListVo;
import com.zqhy.app.core.data.model.recycle.XhRecycleRecordVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.inner.OnCommonDialogClickListener;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.recycle_new.XhNewDataItemHolder1;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.recycle.RecycleViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

import java.util.List;

/**
 * @author Administrator
 */
public class XhRecycleRecordListFragment extends BaseListFragment<RecycleViewModel> {

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(XhRecycleRecordVo.class, new XhNewDataItemHolder1(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("近期回收记录");
        addHeaderView();
        setPullRefreshEnabled(true);
        setLoadingMoreEnabled(true);
        setListViewBackgroundColor(Color.parseColor("#FFFFFF"));
        initData();
    }

    private TextView mXhRecycledCount;

    private void addHeaderView() {
        View mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_header_xh_recycle_record, null);
        mXhRecycledCount = mHeaderView.findViewById(R.id.xh_recycled_count);
        mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addHeaderView(mHeaderView);
    }


    private int page = 1, pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
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
        getXhRecycleRecordListData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    private void initData() {
        page = 1;
        getXhRecycleRecordListData();
    }

    private void getXhRecycleRecordListData() {
        if (mViewModel != null) {
            mViewModel.getXhRecycleRecord(page, pageCount, new OnBaseCallback<XhRecycleRecordListVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(XhRecycleRecordListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            XhRecycleRecordListVo.DataBean dataBean = data.getData();
                            if (dataBean != null) {
                                if (page == 1) {
                                    mXhRecycledCount.setText("共获得" + dataBean.getTotal() + "元奖励");
                                }
                                List<XhRecycleRecordVo> xh_list = dataBean.getXh_list();
                                if (xh_list != null) {
                                    if (page == 1) {
                                        clearData();
                                    }
                                    addAllData(xh_list);
                                } else {
                                    if (page == 1) {
                                        clearData();
                                        //empty
                                        addData(new EmptyDataVo(R.mipmap.img_empty_data_1).setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT));
                                    } else {
                                        page = -1;
                                    }
                                    setListNoMore(true);
                                    notifyData();
                                }
                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    public void deleteRecord(XhRecycleRecordVo item) {
        CustomDialog dialog = CommonUtils.createCommonDialog(_mActivity,
                "删除记录", "确定要删除这条记录么？",
                "取消", "确认删除",
                ContextCompat.getColor(_mActivity, R.color.color_999999), ContextCompat.getColor(_mActivity, R.color.color_ff8f19),
                new OnCommonDialogClickListener() {
                    @Override
                    public void onCancel(CustomDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onConfirm(CustomDialog dialog) {
                        xhRecycleDelete(item.getRid(), dialog);
                    }
                });
        dialog.show();
    }

    /**
     * 删除小号回收记录
     *
     * @param rid
     * @param dialog
     */
    private void xhRecycleDelete(String rid, CustomDialog dialog) {
        if (mViewModel != null) {
            mViewModel.xhRecycleDelete(rid, new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            Toaster.show( "删除成功");
                            initData();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }


    public void redemptionRecord(XhRecycleRecordVo item) {
        String txt = "赎回小号需要扣除钱包余额";
        String value = String.valueOf(item.getHs_gold_total());

        StringBuilder sb = new StringBuilder();
        sb.append(txt).append("\n\n").append(value);

        SpannableString message = new SpannableString(sb);

        message.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_312d2d)), 0, txt.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        message.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_ff4949)), txt.length(), sb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        message.setSpan(new AbsoluteSizeSpan((int) (22 * density)), txt.length(), sb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);


        CustomDialog dialog = CommonUtils.createCommonDialog(_mActivity,
                "赎回小号", message,
                "取消", "确认赎回",
                ContextCompat.getColor(_mActivity, R.color.color_999999), ContextCompat.getColor(_mActivity, R.color.color_ff8f19),
                new OnCommonDialogClickListener() {
                    @Override
                    public void onCancel(CustomDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onConfirm(CustomDialog dialog) {
                        xhRecycleRansom(item.getXh_username(), dialog);
                    }
                });
        dialog.show();
        TextView mTvDialogMessage = dialog.findViewById(R.id.tv_dialog_message);
        mTvDialogMessage.setGravity(Gravity.CENTER);
    }

    public void redemptionRecord2(XhRecycleRecordVo item) {
        if (item == null) {
            return;
        }
        CustomDialog dialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_redemption_xh_account_1, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        ImageView mIvClosed = dialog.findViewById(R.id.iv_closed);
        ImageView mGameIcon = dialog.findViewById(R.id.game_icon);
        TextView mTvGameName = dialog.findViewById(R.id.tv_game_name);
        TextView mTvXhAccount = dialog.findViewById(R.id.tv_xh_account);
        TextView tv_xh_coupon_amount = dialog.findViewById(R.id.tv_xh_coupon_amount);
        Button mBtnRedemptionXhAccount = dialog.findViewById(R.id.btn_redemption_xh_account);

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);


        GlideUtils.loadRoundImage(_mActivity, item.getGameicon(), mGameIcon);
        mTvGameName.setText(item.getGamename());
        mTvXhAccount.setText(item.getXh_showname());
        tv_xh_coupon_amount.setText(String.valueOf(item.getHs_gold_total()));

        mIvClosed.setOnClickListener(view -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        });
        mBtnRedemptionXhAccount.setOnClickListener(view -> {
            xhRecycleRansom(item.getXh_username(), dialog);
        });

        dialog.show();
    }

    public void showRedemptionSuccess() {
        CustomDialog dialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_redemption_xh_account_success, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        ImageView mIvClosed = dialog.findViewById(R.id.iv_closed);
        Button mBtnCancel = dialog.findViewById(R.id.btn_cancel);

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        mIvClosed.setOnClickListener(view -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        });
        mBtnCancel.setOnClickListener(view -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    /**
     * 回收小号赎回
     *
     * @param xh_username
     * @param dialog
     */
    private void xhRecycleRansom(String xh_username, CustomDialog dialog) {
        if (mViewModel != null) {
            mViewModel.xhRecycleRansom(xh_username, new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            //                            ToastT.success("赎回成功");
                            showRedemptionSuccess();
                            initData();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }


}
