package com.zqhy.app.core.view.transaction.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.transaction.TradeSellConfigVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.KeyboardUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.transaction.TransactionInstructionsFragment;
import com.zqhy.app.core.view.transaction.sell.TransactionSellFragment2;
import com.zqhy.app.core.view.transaction.util.CustomPopWindow;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.CountDownTimerCopyFromAPI26;

import java.math.BigDecimal;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author Administrator
 */
public class TransactionGoodItemActionHelper implements TransactionGoodItemAction {

    private TransactionViewModel mViewModel;
    private BaseFragment mFragment;
    private Activity _mActivity;
    private float density;

    public TransactionGoodItemActionHelper(@NonNull BaseFragment mFragment) {
        this.mFragment = mFragment;
        _mActivity = mFragment.getActivity();
        this.mViewModel = (TransactionViewModel) mFragment.getViewModel();
        density = ScreenUtil.getScreenDensity(_mActivity);
    }

    @Override
    public void modifyGoodItem(String gid, int requestCode) {
        startForResult(TransactionSellFragment2.newInstance(gid), requestCode);
    }

    @Override
    public void changeGoodPrice(String gameid, String gid, String goods_price, int can_bargain, int auto_price, OnResultSuccessListener onResultSuccessListener) {
//        showChangePriceDialog(gameid,gid, goods_price,onResultSuccessListener);
        mFragment.loading();
        getTradeSellConfig(gameid, gid, goods_price, can_bargain, auto_price, onResultSuccessListener);
    }

    private void getTradeSellConfig(String gameid, String gid, String goods_price, int can_bargain, int auto_price, OnResultSuccessListener onResultSuccessListener) {
        if (mViewModel != null) {
            mViewModel.getTradeSellConfig(null, new OnBaseCallback<TradeSellConfigVo>() {
                @Override
                public void onSuccess(TradeSellConfigVo data) {
                    mFragment.loadingComplete();
                    if (data.isStateOK()) {
                        TradeSellConfigVo.SellConfig sellConfig = data.getData();
                        showChangePrice(gameid, gid, goods_price, can_bargain, auto_price, onResultSuccessListener, sellConfig);
                    }
                }
            });
        }
    }

    @Override
    public void stopSelling(String gid, OnResultSuccessListener onResultSuccessListener) {
        View contentView = LayoutInflater.from(_mActivity).inflate(R.layout.pop_transaction_soldoutgood, null);
        TextView tv_close = contentView.findViewById(R.id.tv_close);
        TextView tv_do = contentView.findViewById(R.id.tv_do);
        TextView tv_2 = contentView.findViewById(R.id.tv_2);
        SpannableString ss1 = new SpannableString("2、上架状态商品主动下架，需间隔24小时后可再次提交上架申请。");
        ss1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF2B3F")), 6, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_2.setText(ss1);
        //处理popWindow 显示内容
        //创建并显示popWindow
        CustomPopWindow customPopWindow = new CustomPopWindow.PopupWindowBuilder(_mActivity)
                .enableOutsideTouchableDissmiss(false)
                .enableBackgroundDark(true)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                .create();
        customPopWindow.setBackgroundAlpha(0.5f);
        customPopWindow
                .showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        tv_close.setOnClickListener(v -> {
            customPopWindow.dissmiss();
        });

        tv_do.setOnClickListener(v -> {
            customPopWindow.dissmiss();
            stopSellingAction(gid, onResultSuccessListener);
        });
//        AlertDialog dialog = new AlertDialog.Builder(_mActivity).create();
//        dialog.setTitle("提示");
//        dialog.setMessage("确定下架商品吗？");
//        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "下架", (DialogInterface dialogInterface, int i) -> {
//            dialogInterface.dismiss();
//            stopSellingAction(gid,onResultSuccessListener);
//        });
//        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "暂不下架", (DialogInterface dialogInterface, int i) -> {
//            dialogInterface.dismiss();
//        });
//        dialog.show();
//        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
//        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
//
//        mFragment.setDefaultSystemDialogTextSize(dialog);
    }

    @Override
    public void stopSellingWithTips(String gid, OnResultSuccessListener onResultSuccessListener) {
        View contentView = LayoutInflater.from(_mActivity).inflate(R.layout.pop_transaction_soldoutgood, null);
        TextView tv_close = contentView.findViewById(R.id.tv_close);
        TextView tv_do = contentView.findViewById(R.id.tv_do);
        TextView tv_2 = contentView.findViewById(R.id.tv_2);
        SpannableString ss1 = new SpannableString("2、上架状态商品主动下架，需间隔24小时后可再次提交上架申请。");
        ss1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF2B3F")), 6, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_2.setText(ss1);
        //处理popWindow 显示内容
        //创建并显示popWindow
        CustomPopWindow customPopWindow = new CustomPopWindow.PopupWindowBuilder(_mActivity)
                .enableOutsideTouchableDissmiss(false)
                .enableBackgroundDark(true)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                .create();
        customPopWindow.setBackgroundAlpha(0.5f);
        customPopWindow
                .showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        tv_close.setOnClickListener(v -> {
            customPopWindow.dissmiss();
        });

        tv_do.setOnClickListener(v -> {
            customPopWindow.dissmiss();
            stopSellingAction(gid, onResultSuccessListener);
        });

//        AlertDialog dialog = new AlertDialog.Builder(_mActivity).create();
//        dialog.setTitle("确定要下架商品吗？");
//        dialog.setMessage("①下架商品后，再次提交出售需等待24小时,请慎重操作！\n" +
//                "②若只需修改出售价格，可点击“改价”操作。");
//        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "下架", (DialogInterface dialogInterface, int i) -> {
//            dialogInterface.dismiss();
//            stopSellingAction(gid,onResultSuccessListener);
//        });
//        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "暂不下架", (DialogInterface dialogInterface, int i) -> {
//            dialogInterface.dismiss();
//        });
//        dialog.show();
//        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
//        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
//
//        mFragment.setDefaultSystemDialogTextSize(dialog);
    }

    public void stopSellingAction(String gid, OnResultSuccessListener onResultSuccessListener) {
        if (mViewModel != null) {
            mViewModel.offLineTradeGood(gid, new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            Toaster.show( "下架成功");
                            if (onResultSuccessListener != null) {
                                onResultSuccessListener.onResultSuccess();
                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });

        }
    }


    @Override
    public void deleteTradeGood(String gid, OnResultSuccessListener onResultSuccessListener) {
        View contentView = LayoutInflater.from(_mActivity).inflate(R.layout.pop_transaction_deletetradegood, null);
        TextView tv_close = contentView.findViewById(R.id.tv_close);
        TextView tv_do = contentView.findViewById(R.id.tv_do);
        //处理popWindow 显示内容
        //创建并显示popWindow
        CustomPopWindow customPopWindow = new CustomPopWindow.PopupWindowBuilder(_mActivity)
                .enableOutsideTouchableDissmiss(false)
                .enableBackgroundDark(true)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                .create();
        customPopWindow.setBackgroundAlpha(0.5f);
        customPopWindow
                .showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        tv_close.setOnClickListener(v -> {
            customPopWindow.dissmiss();
        });

        tv_do.setOnClickListener(v -> {
            customPopWindow.dissmiss();
            if (mViewModel != null) {
                mViewModel.deleteTradeRecord(gid, new OnBaseCallback() {
                    @Override
                    public void onSuccess(BaseVo data) {
                        if (data != null) {
                            if (data.isStateOK()) {
                                Toaster.show( "删除成功");
                                if (onResultSuccessListener != null) {
                                    onResultSuccessListener.onResultSuccess();
                                }
//                                initData();
//                                isAnyDataChange = true;
                            } else {
                                Toaster.show( data.getMsg());
                            }
                        }
                    }
                });
            }
        });

//        AlertDialog dialog = new AlertDialog.Builder(_mActivity).create();
//        dialog.setTitle("提示");
//        dialog.setMessage("删除后该记录无法恢复，是否确认删除？");
//        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定", (DialogInterface dialogInterface, int i) -> {
//            dialogInterface.dismiss();
//            if (mViewModel != null) {
//                mViewModel.deleteTradeRecord(gid, new OnBaseCallback() {
//                    @Override
//                    public void onSuccess(BaseVo data) {
//                        if (data != null) {
//                            if (data.isStateOK()) {
//                                Toaster.show( "删除成功");
//                                if(onResultSuccessListener != null){
//                                    onResultSuccessListener.onResultSuccess();
//                                }
////                                initData();
////                                isAnyDataChange = true;
//                            } else {
//                                Toaster.show( data.getMsg());
//                            }
//                        }
//                    }
//                });
//            }

//        });
//        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", (DialogInterface dialogInterface, int i) -> {
//            dialogInterface.dismiss();
//        });
//        dialog.show();
//        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
//        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
    }

    @Override
    public void cancelTradeGood(String gid, OnResultSuccessListener onResultSuccessListener) {
        AlertDialog dialog = new AlertDialog.Builder(_mActivity).create();
        dialog.setTitle("提示");
        dialog.setMessage("删除后该记录无法恢复，是否确认删除？");
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定", (DialogInterface dialogInterface, int i) -> {
            dialogInterface.dismiss();
            if (mViewModel != null) {
                mViewModel.cancelTradeRecord(gid, new OnBaseCallback() {
                    @Override
                    public void onSuccess(BaseVo data) {
                        if (data != null) {
                            if (data.isStateOK()) {
                                Toaster.show( "删除成功");
                                if (onResultSuccessListener != null) {
                                    onResultSuccessListener.onResultSuccess();
                                }
//                                initData();
//                                isAnyDataChange = true;
                            } else {
                                Toaster.show( data.getMsg());
                            }
                        }
                    }
                });
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", (DialogInterface dialogInterface, int i) -> {
            dialogInterface.dismiss();
        });
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
    }

    @Override
    public void howToUseGoods() {
        start(new TransactionInstructionsFragment());
    }


    private CustomDialog transactionTipDialog;
    String string1;
    private void showTransactionTipDialog() {
        if (transactionTipDialog == null) {
            transactionTipDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_transaction_vip_explain, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            transactionTipDialog.setCancelable(false);
            transactionTipDialog.setCanceledOnTouchOutside(false);

            TextView btn_got_it = (TextView) transactionTipDialog.findViewById(R.id.btn_got_it);
            TextView tv_content_1 = (TextView) transactionTipDialog.findViewById(R.id.tv_content_1);

            string1 = "出售成功将收取";

            if (notViprate != 0) {
                string1 = string1 + notViprate + "%手续费，最低";
            } else {
                string1 = string1 + "5%手续费，最低";
            }

            if (notVipcharge != 0) {
                string1 = string1 + notVipcharge + "元";
            } else {
                string1 = string1 + "5元";
            }

            if (isVipcharge != 0) {
                string1 = string1  + "。扣除手续费剩余收益将以平台币形式转至您的账号内，可充值平台任意游戏。";
            } else {
                string1 = string1 + "。扣除手续费剩余收益将以平台币形式转至您的账号内，可充值平台任意游戏。";
            }

            SpannableString ss = new SpannableString(string1);

            tv_content_1.setText(ss);

            btn_got_it.setOnClickListener(view -> {
                if (transactionTipDialog != null) {
                    transactionTipDialog.dismiss();
                    transactionTipDialog = null;
                }
            });
            transactionTipDialog.show();
        }
    }

    private EditText mEtVerificationCode;
    private TextView mTvSendCode;
    Float minCharge = 5.00f;
    Float minSuperCharge = 3.00f;
    Float minRate = 0.05f;
    Float minSuperRate = 0.02f;
    Float taxRate = 0.03f;
    int isViprate = 0;
    int isVipcharge = 0;
    int notViprate = 0;
    int notVipcharge = 0;
    boolean isVip = false;
    CustomDialog customPopWindow;

    private int mCanBargain;
    private int mAutoPrice;

    private void showChangePrice(String gameid, String gid, String goods_price, int can_bargain, int auto_price, OnResultSuccessListener onResultSuccessListener, TradeSellConfigVo.SellConfig sellConfig) {
        mCanBargain = can_bargain;
        mAutoPrice = auto_price;

        View contentView = LayoutInflater.from(_mActivity).inflate(R.layout.pop_transaction_changeprice, null);
        TextView tv_close = contentView.findViewById(R.id.tv_close);
        TextView tv_do = contentView.findViewById(R.id.tv_do);
        TextView tv_commission_charge1 = contentView.findViewById(R.id.tv_commission_charge1);
        EditText et_transaction_price = contentView.findViewById(R.id.et_transaction_price);
        TextView mTvTransactionGotGold = contentView.findViewById(R.id.tv_transaction_got_gold);
        ImageView iv_counter_offer = contentView.findViewById(R.id.iv_counter_offer);
        ImageView iv_counter_offer_tips = contentView.findViewById(R.id.iv_counter_offer_tips);
        ImageView iv_price_reduction = contentView.findViewById(R.id.iv_price_reduction);
        ImageView iv_price_reduction_tips = contentView.findViewById(R.id.iv_price_reduction_tips);
        TextView mTvPriceTips = contentView.findViewById(R.id.tv_price_tips);

        mEtVerificationCode = contentView.findViewById(R.id.et_code);
        et_transaction_price.setText(goods_price);
        mTvSendCode = contentView.findViewById(R.id.tv_code);
        //处理popWindow 显示内容
        //创建并显示popWindow
        /*customPopWindow = new CustomPopWindow.PopupWindowBuilder(_mActivity)
                .enableOutsideTouchableDissmiss(false)
                .enableBackgroundDark(true)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                .create();
        customPopWindow.setBackgroundAlpha(0.5f);*/
        customPopWindow = new CustomDialog(_mActivity, contentView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        customPopWindow.setCanceledOnTouchOutside(false);
        customPopWindow.setCancelable(true);

        isViprate = (int) sellConfig.getSuper_user_rate();
        isVipcharge = (int) sellConfig.getSuper_user_min_charge();
        notViprate = (int) sellConfig.getRate();
        notVipcharge = (int) sellConfig.getMin_charge();
        float v = sellConfig.getRate() - sellConfig.getSuper_user_rate();
        taxRate = (v) / 100;
        float v1 = CommonUtils.saveFloatPoint((Float.parseFloat(goods_price) * taxRate), 2, BigDecimal.ROUND_DOWN);
        tv_commission_charge1.setText("（" + sellConfig.getRate() + "%手续费，最低5元）");
        if (false) {
            //会员
            isVip = true;
            minCharge = sellConfig.getMin_charge();
            minRate = sellConfig.getRate() / 100;
            minSuperRate = sellConfig.getSuper_user_rate() / 100;
            minSuperCharge = sellConfig.getSuper_user_min_charge();
            notViprate = (int) sellConfig.getSuper_user_rate();
            notVipcharge = (int) sellConfig.getSuper_user_min_charge();
//            tv_commission_charge1.setText(sellConfig.getSuper_user_rate() + "%手续费");
//            tv_commission_charge2.setText("，最低" + sellConfig.getSuper_user_min_charge() + "元");
            v1 = v1 < sellConfig.getSuper_user_min_charge() ? (sellConfig.getMin_charge() - sellConfig.getSuper_user_min_charge()) : v1;
        } else {
            //非会员
            isVip = false;
            minCharge = sellConfig.getMin_charge();
            minRate = sellConfig.getRate() / 100;
//            isViprate = (int) sellConfig.getRate();
//            notViprate = (int) sellConfig.getMin_charge();
//            tv_commission_charge1.setText(sellConfig.getRate() + "%手续费");
//            tv_commission_charge2.setText("，最低" + sellConfig.getMin_charge() + "元");
        }

        int goodPrice = Integer.parseInt(goods_price);
        float poundageCost = goodPrice * minRate;
        if (poundageCost < minCharge) {
            poundageCost = minCharge;
        }
        float gotGoodPrice = (goodPrice - poundageCost);
        if (gotGoodPrice < 0) {
            gotGoodPrice = 0;
        }
        mTvTransactionGotGold.setText(String.valueOf(CommonUtils.saveFloatPoint(gotGoodPrice, 2, BigDecimal.ROUND_DOWN)) + "元");

        if (mCanBargain == 2){//允许还价
            iv_counter_offer.setImageResource(R.mipmap.ic_login_checked);
        }else {
            iv_counter_offer.setImageResource(R.mipmap.ic_login_un_check);
        }
        iv_counter_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCanBargain == 2){
                    //不允许还价
                    mCanBargain = 1;
                }else {
                    //允许还价
                    mCanBargain = 2;
                }
                if (mCanBargain == 2){
                    iv_counter_offer.setImageResource(R.mipmap.ic_login_checked);
                }else {
                    iv_counter_offer.setImageResource(R.mipmap.ic_login_un_check);
                }
            }
        });
        iv_counter_offer_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCounterOfferTips(iv_counter_offer_tips);
            }
        });

        mAutoPrice = 1;//每次改价默认取消自动降价

        iv_price_reduction.setImageResource(R.mipmap.ic_login_un_check);
        mTvPriceTips.setText("每次设置价格默认关闭自动降价，需要请手动开启");
        mTvPriceTips.setTextColor(Color.parseColor("#9B9B9B"));

        iv_price_reduction.setOnClickListener(v3 -> {
            String trim = et_transaction_price.getText().toString().trim();
            if (!TextUtils.isEmpty(trim)){
                int price = Integer.parseInt(trim);
                if (price <= 6){
                    Toaster.show("已设置最低价，无法使用自动降价功能");
                    mAutoPrice = 1;
                    iv_price_reduction.setImageResource(R.mipmap.ic_login_un_check);
                    mTvPriceTips.setText("每次设置价格默认关闭自动降价，需要请手动开启");
                    mTvPriceTips.setTextColor(Color.parseColor("#9B9B9B"));
                    return;
                }
            }else {
                return;
            }
            if (mAutoPrice == 2){
                //自动降价
                mAutoPrice = 1;
            }else {
                //不自动降价
                mAutoPrice = 2;
            }
            if (mAutoPrice == 2){
                iv_price_reduction.setImageResource(R.mipmap.ic_login_checked);

                int price = Integer.parseInt(trim);
                double doublePrice = price;
                int amplitude = (int) (doublePrice / 10 + 0.9);

                int after = price - amplitude * 5;
                if (after <= 6){
                    mTvPriceTips.setText("每12小时降:" + amplitude + "元（最低可降至:6元）");
                }else {
                    mTvPriceTips.setText("每12小时降:" + amplitude + "元（最低可降至:" + after + "元）");
                }
                mTvPriceTips.setTextColor(Color.parseColor("#FF3333"));
            }else {
                iv_price_reduction.setImageResource(R.mipmap.ic_login_un_check);
                mTvPriceTips.setText("每次设置价格默认关闭自动降价，需要请手动开启");
                mTvPriceTips.setTextColor(Color.parseColor("#9B9B9B"));
            }
        });
        iv_price_reduction_tips.setOnClickListener(v4 -> showPriceReduction(iv_price_reduction_tips));

        et_transaction_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String strGoodPrice = et_transaction_price.getText().toString().trim();
                if (TextUtils.isEmpty(strGoodPrice)) {
                    mTvTransactionGotGold.setText("-平台币");
                    if (isVip) {
                    }
                    return;
                }
                int goodPrice = Integer.parseInt(strGoodPrice);
                if (isVip) {
                    float poundageCost = goodPrice * minSuperRate;
                    if (poundageCost < minSuperCharge) {
                        poundageCost = minSuperCharge;
                    }
                    float gotGoodPrice = (goodPrice - poundageCost);
                    if (gotGoodPrice < 0) {
                        gotGoodPrice = 0;
                    }
                    //vip手续费
                    float v1 = CommonUtils.saveFloatPoint((goodPrice * minSuperRate), 2, BigDecimal.ROUND_UP);
                    float v2 = CommonUtils.saveFloatPoint((goodPrice * minRate), 2, BigDecimal.ROUND_UP);
                    Log.d("sell2", "v1: " + v1);
                    Log.d("sell2", "v2: " + v2);
                    if (v1 < minSuperCharge) {
                        v1 = minSuperCharge;
                    }
                    if (v2 < minCharge) {
                        v2 = minCharge;
                    }
                    Log.d("sell2", "v1:-- " + v1);
                    Log.d("sell2", "v2:-- " + v2);
                    mTvTransactionGotGold.setText(String.valueOf(CommonUtils.saveFloatPoint(gotGoodPrice, 2, BigDecimal.ROUND_DOWN)) + "平台币");
                } else {
                    float poundageCost = goodPrice * minRate;
                    if (poundageCost < minCharge) {
                        poundageCost = minCharge;
                    }
                    float gotGoodPrice = (goodPrice - poundageCost);
                    if (gotGoodPrice < 0) {
                        gotGoodPrice = 0;
                    }
                    mTvTransactionGotGold.setText(String.valueOf(CommonUtils.saveFloatPoint(gotGoodPrice, 2, BigDecimal.ROUND_UP)) + "平台币");
                }
                if (mAutoPrice == 2){
                    if (goodPrice > 6){
                        double doublePrice = goodPrice;
                        int amplitude = (int) (doublePrice / 10 + 0.9);

                        int after = goodPrice - amplitude * 5;
                        if (after <= 6){
                            mTvPriceTips.setText("每12小时降:" + amplitude + "元（最低可降至:6元）");
                        }else {
                            mTvPriceTips.setText("每12小时降:" + amplitude + "元（最低可降至:" + after + "元）");
                        }
                    }else {
                        mAutoPrice = 1;
                        iv_price_reduction.setImageResource(R.mipmap.ic_login_un_check);
                        mTvPriceTips.setText("每次设置价格默认关闭自动降价，需要请手动开启");
                        mTvPriceTips.setTextColor(Color.parseColor("#9B9B9B"));
                    }
                }
            }
        });

        et_transaction_price.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                et_transaction_price.selectAll();
            }
        });

        //customPopWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        tv_close.setOnClickListener(view -> {
            if (customPopWindow != null && customPopWindow.isShowing()) customPopWindow.dismiss();
        });

        mTvSendCode.setOnClickListener(view -> {
            //发送验证码
            getTradeCode(gameid);
        });

        tv_do.setOnClickListener(view -> {
            String code = mEtVerificationCode.getText().toString().trim();
            if (TextUtils.isEmpty(code)) {
                Toaster.show( mEtVerificationCode.getHint());
                return;
            }
            //action 修改价格
            String trim = et_transaction_price.getText().toString().trim();
            if (TextUtils.isEmpty(trim)) {
                Toaster.show( "请填写出售价格");
                return;
            }
            int price = Integer.parseInt(trim);
            if (price < 6) {
                Toaster.show( "出售价不低于6元");
                return;
            }
            if (mAutoPrice == 2 && price <= 6){
                Toaster.show("已设置最低价，无法使用自动降价功能");
                return;
            }
            actionChangeGoodPrice(gid, price, code, mCanBargain, mAutoPrice, onResultSuccessListener);
            mFragment.loading("改价中,请稍后");
        });

        et_transaction_price.setFocusable(true);
        et_transaction_price.post(() -> KeyboardUtils.showSoftInput(_mActivity, et_transaction_price));
        customPopWindow.show();
    }

    private PopupWindow counterOfferPopupWindow;
    private void showCounterOfferTips(View view) {
        if (counterOfferPopupWindow == null){
            View contentView = LayoutInflater.from(_mActivity).inflate(R.layout.pop_transaction_counter_offer, null);
            //处理popWindow 显示内容
            //创建并显示popWindow
            counterOfferPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        }
        counterOfferPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        counterOfferPopupWindow.setFocusable(false);
        counterOfferPopupWindow.setOutsideTouchable(true);
        counterOfferPopupWindow.showAsDropDown(view,0, ScreenUtil.dp2px(_mActivity, -90));
    }

    private PopupWindow priceReductionPopupWindow;
    private void showPriceReduction(View view) {
        if (priceReductionPopupWindow == null){
            View contentView = LayoutInflater.from(_mActivity).inflate(R.layout.pop_transaction_price_reduction, null);
            //处理popWindow 显示内容
            //创建并显示popWindow
            priceReductionPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        }
        priceReductionPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        priceReductionPopupWindow.setFocusable(false);
        priceReductionPopupWindow.setOutsideTouchable(true);
        priceReductionPopupWindow.showAsDropDown(view,0, ScreenUtil.dp2px(_mActivity, -100));
    }

    private CustomDialog changePriceDialog;
    private LinearLayout mLlSendCode;
    private EditText mEtChangePrice;
    private TextView mTvGetPrice;
    private Button mBtnDialogCancel;
    private Button mBtnDialogConfirm;


//    private void showChangePriceDialog(String gameid, String gid, String goods_price, OnResultSuccessListener onResultSuccessListener) {
//        if (changePriceDialog == null) {
//            changePriceDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_transaction_change_price, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
//
//            mEtChangePrice = changePriceDialog.findViewById(R.id.et_change_price);
//            mLlSendCode = changePriceDialog.findViewById(R.id.ll_send_code);
//
//            mTvGetPrice = changePriceDialog.findViewById(R.id.tv_get_price);
//            mBtnDialogCancel = changePriceDialog.findViewById(R.id.btn_dialog_cancel);
//            mBtnDialogConfirm = changePriceDialog.findViewById(R.id.btn_dialog_confirm);
//
//            mEtVerificationCode = changePriceDialog.findViewById(R.id.et_verification_code);
//            mTvSendCode = changePriceDialog.findViewById(R.id.tv_send_code);
//
//            mBtnDialogCancel.setOnClickListener(view -> {
//                if (changePriceDialog != null && changePriceDialog.isShowing()) {
//                    changePriceDialog.dismiss();
//                }
//            });
//
//            GradientDrawable gd2 = new GradientDrawable();
//            gd2.setCornerRadius(30 * density);
//            gd2.setColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
//            mTvSendCode.setBackground(gd2);
//
//            GradientDrawable gd1 = new GradientDrawable();
//            gd1.setCornerRadius(32 * density);
//            gd1.setColor(ContextCompat.getColor(_mActivity, R.color.color_c1c1c1));
//            mBtnDialogCancel.setBackground(gd1);
//
//            GradientDrawable gd3 = new GradientDrawable();
//            gd3.setCornerRadius(5 * density);
//            gd3.setColor(ContextCompat.getColor(_mActivity, R.color.color_eeeeee));
//            mEtChangePrice.setBackground(gd3);
//
//            GradientDrawable gd4 = new GradientDrawable();
//            gd4.setCornerRadius(5 * density);
//            gd4.setColor(ContextCompat.getColor(_mActivity, R.color.color_f8f8f8));
//            mLlSendCode.setBackground(gd3);
//
//            setBtnConfirmEnable(false);
//
//            mEtChangePrice.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//                    String strGoodPrice = mEtChangePrice.getText().toString().trim();
//                    if (strGoodPrice.length() == 0) {
//                        setBtnConfirmEnable(false);
//                    } else {
//                        setBtnConfirmEnable(true);
//                    }
//
//                    if (TextUtils.isEmpty(strGoodPrice)) {
//                        mTvGetPrice.setText("0.00");
//                        return;
//                    }
//                    int goodPrice = Integer.parseInt(strGoodPrice);
//                    float poundageCost = goodPrice * 0.05f;
//                    if (poundageCost < 5.00f) {
//                        poundageCost = 5.00f;
//                    }
//                    float gotGoodPrice = (goodPrice - poundageCost);
//                    if (gotGoodPrice < 0) {
//                        gotGoodPrice = 0;
//                    }
//                    mTvGetPrice.setText(String.valueOf(CommonUtils.saveFloatPoint(gotGoodPrice, 2, BigDecimal.ROUND_DOWN)));
//                }
//            });
//            mEtChangePrice.setOnFocusChangeListener((v, hasFocus) -> {
//                if (hasFocus) {
//                    mEtChangePrice.selectAll();
//                }
//            });
//
//            changePriceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialogInterface) {
//                    mEtChangePrice.getText().clear();
//                }
//            });
//        }
//        if (goods_price != null) {
//            mEtChangePrice.setText(goods_price);
//            mEtChangePrice.setSelection(goods_price.length());
//            mEtChangePrice.selectAll();
//        }
//        mTvSendCode.setOnClickListener(view -> {
//            //发送验证码
//            getTradeCode(gameid);
//        });
//        mBtnDialogConfirm.setOnClickListener(view -> {
//            String code = mEtVerificationCode.getText().toString().trim();
//            if (TextUtils.isEmpty(code)) {
//                Toaster.show( mEtVerificationCode.getHint());
//                return;
//            }
//            //action 修改价格
//            int price = Integer.parseInt(mEtChangePrice.getText().toString().trim());
//            if (price < 6) {
//                Toaster.show( "出售价不低于6元");
//                return;
//            }
//            actionChangeGoodPrice(gid, price, code, onResultSuccessListener);
//        });
//        changePriceDialog.show();
//        mEtChangePrice.selectAll();
//
//        mEtChangePrice.postDelayed(() -> {
//            mEtChangePrice.setFocusable(true);
//            KeyboardUtils.showSoftInput(_mActivity, mEtChangePrice);
//        }, 200);
//
//    }

    private void setBtnConfirmEnable(boolean isEnable) {
        GradientDrawable gd2 = new GradientDrawable();
        gd2.setCornerRadius(32 * density);
        if (isEnable) {
            gd2.setColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
        } else {
            gd2.setColor(ContextCompat.getColor(_mActivity, R.color.color_c1c1c1));
        }
        mBtnDialogConfirm.setBackground(gd2);
        mBtnDialogConfirm.setEnabled(isEnable);
    }

    private void actionChangeGoodPrice(String gid, int goods_price, String code, int can_bargain, int auto_price, OnResultSuccessListener onResultSuccessListener) {
        if (mViewModel != null) {
            mViewModel.getModifyGoodPrice(gid, goods_price, code, can_bargain, auto_price, new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    mFragment.loadingComplete();
                    if (data != null) {
                        if (data.isStateOK()) {
                            Toaster.show( "修改成功");
//                            initData();
//                            isAnyDataChange = true;
                            if (onResultSuccessListener != null) {
                                onResultSuccessListener.onResultSuccess();
                            }
                            if (customPopWindow != null && customPopWindow.isShowing()) customPopWindow.dismiss();
//                            if (changePriceDialog != null && changePriceDialog.isShowing()) {
//                                changePriceDialog.dismiss();
//                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void getTradeCode(String targetGameid) {
        if (TextUtils.isEmpty(targetGameid)) {
            return;
        }

        if (mViewModel != null) {
            mViewModel.getTradeCode(targetGameid, new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            sendCode();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void sendCode() {

        mTvSendCode.setTextColor(Color.parseColor("#C1C1C1"));

        new CountDownTimerCopyFromAPI26(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvSendCode.setEnabled(false);
                mTvSendCode.setText("重新发送" + (millisUntilFinished / 1000) + "秒");
            }

            @Override
            public void onFinish() {
                mTvSendCode.setTextColor(Color.parseColor("#5571FE"));
                mTvSendCode.setText("获取验证码");
                mTvSendCode.setEnabled(true);
            }
        }.start();
    }

    private void start(ISupportFragment toFragment) {
        if (mFragment != null) {
            mFragment.start(toFragment);
        }
    }

    private void startForResult(ISupportFragment toFragment, int requestCode) {
        if (mFragment != null) {
            mFragment.startForResult(toFragment, requestCode);
        }
    }
}
