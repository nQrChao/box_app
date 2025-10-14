package com.zqhy.app.core.view.refund;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.adapter.abs.AbsMultiChooseAdapter;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.refund.RefundOrderListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.vm.refund.RefundViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leeham2734
 * @date 2020/5/9-16:10
 * @description
 */
public class RefundMainFragment extends BaseFragment<RefundViewModel> {

    private static final int ORDER_REFUND     = 1;
    private static final int ORDER_NON_REFUND = 2;

    private int order_type = ORDER_REFUND;


    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_refund_main;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("退款中心");
        bindViews();
        initData();
    }


    private void initData() {
        getRefundGameList();
    }

    private TextView     mTvRefundDetail;
    private TextView     mTvPtbRefundDetail;
    private TextView     mTvPtbRefundSelectGame;
    private FrameLayout  mFlEmptyOrders;
    private TextView     mTvOrderRefund;
    private TextView     mTvOrderNoRefund;
    private TextView     mTvPtbRefundTips;
    private ImageView    mIvGameIcon;
    private TextView     mTvOrderAllRefund;
    private RecyclerView mRecyclerView;
    private FrameLayout  mFlRefundTotalAmount;
    private TextView     mTvRefundAmount;
    private TextView     mBtnRefundAction;
    private LinearLayout mLlRefundOrders;
    private TextView     mTvGameName;
    private LinearLayout mLlGameInfo;

    private void bindViews() {
        mTvRefundDetail = findViewById(R.id.tv_refund_detail);
        mTvPtbRefundDetail = findViewById(R.id.tv_ptb_refund_detail);
        mTvPtbRefundSelectGame = findViewById(R.id.tv_ptb_refund_select_game);
        mFlEmptyOrders = findViewById(R.id.fl_empty_orders);
        mTvOrderRefund = findViewById(R.id.tv_order_refund);
        mTvOrderNoRefund = findViewById(R.id.tv_order_no_refund);
        mTvPtbRefundTips = findViewById(R.id.tv_ptb_refund_tips);
        mIvGameIcon = findViewById(R.id.iv_game_icon);
        mTvOrderAllRefund = findViewById(R.id.tv_order_all_refund);
        mRecyclerView = findViewById(R.id.recycler_view);
        mFlRefundTotalAmount = findViewById(R.id.fl_refund_total_amount);
        mTvRefundAmount = findViewById(R.id.tv_refund_amount);
        mBtnRefundAction = findViewById(R.id.btn_refund_action);
        mLlRefundOrders = findViewById(R.id.ll_refund_orders);
        mTvGameName = findViewById(R.id.tv_game_name);
        mLlGameInfo = findViewById(R.id.ll_game_info);


        mTvRefundDetail.setOnClickListener(view -> {
            //退款明细
            if (checkLogin()) {
                startFragment(new RefundRecordListFragment());
            }
        });

        mTvPtbRefundDetail.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mTvPtbRefundDetail.getPaint().setAntiAlias(true);
        mTvPtbRefundDetail.setOnClickListener(view -> {
            //点击查看详细说明
            showRefundProcessDetailDialog();
        });

        mTvPtbRefundSelectGame.setOnClickListener(view -> {
            //选择游戏
            if (refundGameList == null || refundGameList.isEmpty()) {
                return;
            }
            showGameListPop();
        });

        mTvOrderRefund.setOnClickListener(view -> {
            order_type = ORDER_REFUND;
            //可退款订单
            onTabRefundSelect();
            fillOrderList();
        });
        mTvOrderNoRefund.setOnClickListener(view -> {
            order_type = ORDER_NON_REFUND;
            //不可退款订单
            onTabNoRefundSelect();
            fillOrderList();
        });

        mTvOrderAllRefund.setOnClickListener(view -> {
            //全选订单
            orderAdapter.selectAllPosition();
            calculateTotalAmount();
        });

        mBtnRefundAction.setOnClickListener(view -> {
            //去退款(弹窗)
            if (targetGameInfoVo != null) {
                showRefundOrderDialog(targetGameInfoVo.getGameid(), targetGameInfoVo.getGamename());
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(layoutManager);

        orderAdapter = new OrderAdapter(_mActivity, orderList);
        mRecyclerView.setAdapter(orderAdapter);

        orderAdapter.setOnItemClickListener((v, position, data) -> {
            calculateTotalAmount();
        });

        fillOrderList();
    }

    OrderAdapter orderAdapter;

    class OrderAdapter extends AbsMultiChooseAdapter<RefundOrderListVo.DataBean> {

        public OrderAdapter(Context context, List<RefundOrderListVo.DataBean> labels) {
            super(context, labels);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, RefundOrderListVo.DataBean data, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;

            viewHolder.mTvOrderId.setText("订单号：" + data.getOrderid());
            viewHolder.mTvOrderTotalAmount.setText("现金支付：" + data.getRmb_total() + "元");
            viewHolder.mTvOrderTime.setText(CommonUtils.formatTimeStamp(data.getLogtime() * 1000, "MM月dd日 HH:MM"));

            if (data.isCanRefund()) {
                viewHolder.itemView.setEnabled(true);
                viewHolder.mIcon.setImageResource(R.mipmap.ic_refund_order);
                viewHolder.mTvOrderId.setTextColor(ContextCompat.getColor(mContext, R.color.color_1e1e1e));
                viewHolder.mTvOrderTotalAmount.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff0000));
                viewHolder.mTvOrderTime.setTextColor(ContextCompat.getColor(mContext, R.color.color_9b9b9b));
                viewHolder.mCbSelect.setVisibility(View.VISIBLE);
                viewHolder.mCbSelect.setChecked(isSelectItem(position));
            } else {
                holder.itemView.setEnabled(false);
                viewHolder.mIcon.setImageResource(R.mipmap.ic_refund_order_unable);
                viewHolder.mTvOrderId.setTextColor(ContextCompat.getColor(mContext, R.color.color_8f8f8f));
                viewHolder.mTvOrderTotalAmount.setTextColor(ContextCompat.getColor(mContext, R.color.color_9c9c9c));
                viewHolder.mTvOrderTime.setTextColor(ContextCompat.getColor(mContext, R.color.color_c6c6c6));
                viewHolder.mCbSelect.setVisibility(View.GONE);
            }
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_refund_order;
        }

        @Override
        public AbsViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }


        /**
         * 获取选中的总金额
         *
         * @return
         */
        public float getSelectedTotalAmount() {
            List<RefundOrderListVo.DataBean> list = orderAdapter.getSelectItems();
            float totalAmount = 0.00f;
            if (list != null && !list.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (RefundOrderListVo.DataBean dataBean : list) {
                    totalAmount += dataBean.getRmb_total();
                    sb.append(dataBean.getId()).append(",");
                }
            }
            return totalAmount;
        }

        /**
         * 获取选中的id
         *
         * @return
         */
        public String getSelectedIds() {
            List<RefundOrderListVo.DataBean> list = getSelectItems();
            if (list != null && !list.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (RefundOrderListVo.DataBean dataBean : list) {
                    sb.append(dataBean.getId()).append(",");
                }
                return sb.toString().substring(0, sb.length() - 1);
            }
            return "";
        }


        class ViewHolder extends AbsViewHolder {

            private AppCompatImageView mIcon;
            private TextView           mTvOrderId;
            private TextView           mTvOrderTotalAmount;
            private TextView           mTvOrderTime;
            private CheckBox           mCbSelect;


            public ViewHolder(View itemView) {
                super(itemView);

                mIcon = findViewById(R.id.icon);
                mTvOrderId = findViewById(R.id.tv_order_id);
                mTvOrderTotalAmount = findViewById(R.id.tv_order_total_amount);
                mTvOrderTime = findViewById(R.id.tv_order_time);
                mCbSelect = findViewById(R.id.cb_select);
            }
        }
    }


    private List<RefundOrderListVo.DataBean> orderList = new ArrayList<>();


    private void showRefundOrderDialog(int gameid, String gamename) {
        CustomDialog dialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_refund_action, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);

        TextView mTvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView mTvGameName = dialog.findViewById(R.id.tv_game_name);
        TextView mTvUserName = dialog.findViewById(R.id.tv_user_name);
        TextView mTvTotalAmount = dialog.findViewById(R.id.tv_total_amount);
        AppCompatEditText mEdRemark = dialog.findViewById(R.id.et_remark);
        Button mBtnConfirm = dialog.findViewById(R.id.btn_confirm);

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        mTvCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });

        mTvGameName.setText(gamename);
        if (UserInfoModel.getInstance().isLogined()) {
            mTvUserName.setText(UserInfoModel.getInstance().getUserInfo().getUsername());
        }
        if (orderAdapter != null) {
            mTvTotalAmount.setText(String.valueOf(orderAdapter.getSelectedTotalAmount() + "元"));
        }

        mBtnConfirm.setOnClickListener(view -> {
            String remark = mEdRemark.getText().toString().trim();

            if (TextUtils.isEmpty(remark)) {
                Toaster.show( "请填写你的退款理由");
                return;
            }
            String ids = orderAdapter.getSelectedIds();

            if (TextUtils.isEmpty(ids)) {
                Toaster.show( "请选择退款订单号");
                return;
            }
            refundOrder(dialog, gameid, ids, remark);
        });
        dialog.show();


    }


    /**
     * 填充订单信息
     */
    private void fillOrderList() {
        if (orderList == null || orderList.isEmpty()) {
            mFlEmptyOrders.setVisibility(View.VISIBLE);
            mLlRefundOrders.setVisibility(View.GONE);
            mFlRefundTotalAmount.setVisibility(View.GONE);

        } else {
            mFlEmptyOrders.setVisibility(View.GONE);
            mLlRefundOrders.setVisibility(View.VISIBLE);

            orderAdapter.clear();
            List<RefundOrderListVo.DataBean> targetList = new ArrayList<>();
            if (order_type == ORDER_REFUND) {
                mTvOrderAllRefund.setVisibility(View.VISIBLE);
                mTvPtbRefundTips.setVisibility(View.GONE);
                mFlRefundTotalAmount.setVisibility(View.VISIBLE);

                for (RefundOrderListVo.DataBean dataBean : orderList) {
                    if (dataBean.isCanRefund()) {
                        orderAdapter.addData(dataBean);
                    }
                }
            } else if (order_type == ORDER_NON_REFUND) {
                mTvOrderAllRefund.setVisibility(View.GONE);
                mTvPtbRefundTips.setVisibility(View.VISIBLE);
                mFlRefundTotalAmount.setVisibility(View.GONE);

                for (RefundOrderListVo.DataBean dataBean : orderList) {
                    if (!dataBean.isCanRefund()) {
                        orderAdapter.addData(dataBean);
                    }
                }
            }
            orderAdapter.notifyDataSetChanged();
        }
        calculateTotalAmount();
    }


    /**
     * 计算金额
     */
    private void calculateTotalAmount() {
        if (orderAdapter != null) {
            mTvRefundAmount.setText(String.valueOf(orderAdapter.getSelectedTotalAmount()));
        }
    }


    /**
     * 选择游戏pop
     */
    private void showGameListPop() {
        int anchorWidth = mTvPtbRefundSelectGame.getMeasuredWidth();
        int anchorHeight = mTvPtbRefundSelectGame.getMeasuredHeight();

        int width = anchorWidth;
        int height = (int) (155 * density);

        int[] location = new int[2];
        mTvPtbRefundSelectGame.getLocationOnScreen(location);

        PopupWindow popupWindow = new PopupWindow();
        popupWindow.setContentView(createListView(popupWindow));

        popupWindow.setWidth(width);
        popupWindow.setHeight(height);

        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(mTvPtbRefundSelectGame, 0, 0);
    }

    private View createListView(PopupWindow popupWindow) {
        RecyclerView recyclerView = new RecyclerView(_mActivity);

        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));

        GameAdapter gameAdapter = new GameAdapter(_mActivity, refundGameList);
        recyclerView.setAdapter(gameAdapter);

        gameAdapter.setOnItemClickListener((v, position, data) -> {
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
            if (data != null && data instanceof GameInfoVo) {
                GameInfoVo gameInfoVo = (GameInfoVo) data;
                selectGameInfoVo(gameInfoVo);
            }
        });
        return recyclerView;
    }

    private GameInfoVo targetGameInfoVo;

    private void selectGameInfoVo(GameInfoVo gameInfoVo) {
        targetGameInfoVo = gameInfoVo;
        getRefundOrdersById(gameInfoVo);
    }


    class GameAdapter extends AbsAdapter<GameInfoVo> {

        public GameAdapter(Context context, List<GameInfoVo> labels) {
            super(context, labels);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, GameInfoVo data, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.mTv.setText(data.getGamename());
        }

        @Override
        public int getLayoutResId() {
            return R.layout.layout_pop_item_refund_game;
        }

        @Override
        public AbsViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }

        class ViewHolder extends AbsViewHolder {

            private TextView mTv;

            public ViewHolder(View itemView) {
                super(itemView);
                mTv = findViewById(R.id.tv);
            }
        }
    }

    private void onTabRefundSelect() {
        mTvOrderRefund.setBackground(_mActivity.getResources().getDrawable(R.drawable.ts_shape_refund_tab_selected));
        mTvOrderRefund.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mTvOrderRefund.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_0052ef));

        mTvOrderNoRefund.setBackground(null);
        mTvOrderNoRefund.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        mTvOrderNoRefund.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_5a5a5a));
    }

    private void onTabNoRefundSelect() {
        mTvOrderNoRefund.setBackground(_mActivity.getResources().getDrawable(R.drawable.ts_shape_refund_tab_selected));
        mTvOrderNoRefund.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mTvOrderNoRefund.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_0052ef));


        mTvOrderRefund.setBackground(null);
        mTvOrderRefund.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        mTvOrderRefund.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_5a5a5a));
    }


    private void showRefundProcessDetailDialog() {

        CustomDialog dialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_transaction_count_down_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        Button mBtnGotIt = dialog.findViewById(R.id.btn_got_it);
        ImageView mIvImage = dialog.findViewById(R.id.iv_image);
        CheckBox mCbButton = dialog.findViewById(R.id.cb_button);

        mCbButton.setText("我已阅读退款规则");
        mIvImage.setImageResource(R.mipmap.img_refund_tips_process);

        GradientDrawable gd2 = new GradientDrawable();
        gd2.setCornerRadius(30 * density);
        gd2.setColor(Color.parseColor("#C1C1C1"));
        mBtnGotIt.setBackground(gd2);
        mBtnGotIt.setEnabled(false);
        mBtnGotIt.setOnClickListener(view -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        });

        mCbButton.setOnCheckedChangeListener((compoundButton, b) -> {
            GradientDrawable gd1 = new GradientDrawable();
            gd1.setCornerRadius(30 * density);
            if (b) {
                gd1.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                gd1.setColors(new int[]{Color.parseColor("#22A8FD"), Color.parseColor("#5963FC")});
            } else {
                gd1.setColor(Color.parseColor("#C1C1C1"));
            }
            mBtnGotIt.setBackground(gd1);
            mBtnGotIt.setText("我已知晓");
            mBtnGotIt.setEnabled(b);
        });
        dialog.show();
    }

    private List<GameInfoVo> refundGameList;

    /**
     * 获取退款游戏列表
     */
    private void getRefundGameList() {
        if (mViewModel != null) {
            mViewModel.getRefundGameList(new OnBaseCallback<GameListVo>() {
                @Override
                public void onSuccess(GameListVo data) {
                    if (data != null && data.isStateOK()) {
                        refundGameList = data.getData();
                    }
                }
            });

        }
    }

    /**
     * 根据游戏id获取订单列表
     *
     * @param gameInfoVo
     */
    private void getRefundOrdersById(GameInfoVo gameInfoVo) {
        if (mViewModel != null && gameInfoVo != null) {
            mViewModel.getRefundOrdersById(gameInfoVo.getGameid(), new OnBaseCallback<RefundOrderListVo>() {
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
                public void onSuccess(RefundOrderListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            mTvPtbRefundSelectGame.setText(gameInfoVo.getGamename());
                            GlideUtils.loadRoundImage(_mActivity, gameInfoVo.getGameicon(), mIvGameIcon);
                            mTvGameName.setText(gameInfoVo.getGamename() + "订单：");
                            if (orderAdapter != null) {
                                orderAdapter.releaseSelected();
                            }
                            orderList = data.getData();
                            fillOrderList();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }


    /**
     * 退款操作
     *
     * @param dialog
     * @param gameid
     * @param ids
     * @param remark
     */
    private void refundOrder(Dialog dialog, int gameid, String ids, String remark) {
        if (mViewModel != null) {
            mViewModel.refundOrder(gameid, ids, remark, new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                            getRefundOrdersById(targetGameInfoVo);
                            Toaster.show( "退款成功");
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

}
