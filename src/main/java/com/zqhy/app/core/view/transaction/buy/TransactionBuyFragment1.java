package com.zqhy.app.core.view.transaction.buy;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.config.SpConstants;
import com.zqhy.app.config.WxControlConfig;
import com.zqhy.app.core.data.model.RealNameCheckVo;
import com.zqhy.app.core.data.model.transaction.PayBeanVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.AbsPayBuyFragment;
import com.zqhy.app.core.view.user.CertificationFragment;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

/**
 * @author 弃用2022 06 24
 */
public class TransactionBuyFragment1 extends AbsPayBuyFragment<TransactionViewModel> implements View.OnClickListener {

    public static TransactionBuyFragment1 newInstance(String gid, String good_pic, String good_title, String gamename,
                                                      String good_price, String gameid, String game_type) {
        return newInstance(gid, good_pic, good_title, gamename, good_price, gameid, game_type, 0);
    }

    public static TransactionBuyFragment1 newInstance(String gid, String good_pic, String good_title, String gamename,
                                                      String good_price, String gameid, String game_type, int buyAgain) {
        TransactionBuyFragment1 fragment = new TransactionBuyFragment1();
        Bundle bundle = new Bundle();
        bundle.putString("gid", gid);
        bundle.putString("good_pic", good_pic);
        bundle.putString("good_title", good_title);
        bundle.putString("gamename", gamename);
        bundle.putString("good_price", good_price);
        bundle.putString("gameid", gameid);
        bundle.putString("game_type", game_type);
        bundle.putInt("buyAgain", buyAgain);
        fragment.setArguments(bundle);

        return fragment;
    }

    private static final int action_transaction_buy_success = 0x789;

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "交易支付页(买号)";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transaction_buy1;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private boolean isAnyDataChange = false;

    private String gid;
    private String good_pic;
    private String good_title;
    private String gamename;
    private String good_price;
    private String gameid;
    private String game_type;

    private int    buyAgain;
    private String sp_key;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gid = getArguments().getString("gid");
            good_pic = getArguments().getString("good_pic");
            good_title = getArguments().getString("good_title");
            gamename = getArguments().getString("gamename");
            good_price = getArguments().getString("good_price");
            gameid = getArguments().getString("gameid");
            game_type = getArguments().getString("game_type");

            buyAgain = getArguments().getInt("buyAgain");
        }
        super.initView(state);
        sp_key = "TRANSACTION_BUY" + SpConstants.SP_REAL_NAME_STATE + UserInfoModel.getInstance().getUID();
        initActionBackBarAndTitle("购买商品");
        showSuccess();

        bindViews();
        setGoodInfo();
    }

    @Override
    protected int getPayAction() {
        return PAY_ACTION_TRANSACTION;
    }


    private ImageView    mIvTransactionImage;
    private TextView     mTvTransactionTitle;
    private TextView     mTvTransactionGameName;
    private TextView     mTvTransactionPrice;
    private LinearLayout mLlTransactionPayWayAlipay;
    private TextView     mTvPriceAlipay;
    private ImageView    mIvSelectAlipay;
    private LinearLayout mLlTransactionPayWayWechat;
    private TextView     mTvPriceWechat;
    private ImageView    mIvSelectWechat;
    private TextView     mTvTips;
    private Button       mBtnConfirmPay;
    private View         mMidLine;

    private void bindViews() {
        mIvTransactionImage = (ImageView) findViewById(R.id.iv_transaction_image);
        mTvTransactionTitle = (TextView) findViewById(R.id.tv_transaction_title);
        mTvTransactionGameName = (TextView) findViewById(R.id.tv_transaction_game_name);
        mTvTransactionPrice = (TextView) findViewById(R.id.tv_transaction_price);
        mLlTransactionPayWayAlipay = (LinearLayout) findViewById(R.id.ll_transaction_pay_way_alipay);
        mTvPriceAlipay = (TextView) findViewById(R.id.tv_price_alipay);
        mIvSelectAlipay = (ImageView) findViewById(R.id.iv_select_alipay);
        mLlTransactionPayWayWechat = (LinearLayout) findViewById(R.id.ll_transaction_pay_way_wechat);
        mTvPriceWechat = (TextView) findViewById(R.id.tv_price_wechat);
        mIvSelectWechat = (ImageView) findViewById(R.id.iv_select_wechat);
        mTvTips = (TextView) findViewById(R.id.tv_tips);
        mBtnConfirmPay = (Button) findViewById(R.id.btn_confirm_pay);
        mMidLine = findViewById(R.id.mid_line);

        setLayoutViews();
        setListeners();

        mTvTips.setText(Html.fromHtml(getAppNameByXML(R.string.string_transaction_pay_tips)));

        showTransactionBuyingTipDialog();
    }

    private void setLayoutViews() {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(16 * density);
        gd.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_eeeeee));
        mTvTransactionGameName.setBackground(gd);

        if (WxControlConfig.isWxControl()) {
            mLlTransactionPayWayWechat.setVisibility(View.VISIBLE);
            mMidLine.setVisibility(View.VISIBLE);
        } else {
            mLlTransactionPayWayWechat.setVisibility(View.GONE);
            mMidLine.setVisibility(View.GONE);
        }
    }

    private void setListeners() {
        mLlTransactionPayWayAlipay.setOnClickListener(this);
        mLlTransactionPayWayWechat.setOnClickListener(this);
        mBtnConfirmPay.setOnClickListener(this);
    }

    private void setGoodInfo() {
        GlideUtils.loadRoundImage(_mActivity, good_pic, mIvTransactionImage, R.mipmap.ic_placeholder);
        mTvTransactionGameName.setText(gamename);
        mTvTransactionTitle.setText(good_title);
        mTvTransactionPrice.setText(good_price);
        mTvPriceWechat.setText("-" + good_price);
        mTvPriceAlipay.setText("-" + good_price);

        onClick(mLlTransactionPayWayAlipay);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_transaction_pay_way_alipay:
                mTvPriceAlipay.setVisibility(View.VISIBLE);
                mTvPriceWechat.setVisibility(View.GONE);

                mIvSelectAlipay.setImageResource(R.mipmap.ic_transaction_pay_select);
                mIvSelectWechat.setImageResource(R.mipmap.ic_transaction_pay_normal);
                PAY_TYPE = PAY_TYPE_ALIPAY;
                break;
            case R.id.ll_transaction_pay_way_wechat:
                mTvPriceAlipay.setVisibility(View.GONE);
                mTvPriceWechat.setVisibility(View.VISIBLE);

                mIvSelectAlipay.setImageResource(R.mipmap.ic_transaction_pay_normal);
                mIvSelectWechat.setImageResource(R.mipmap.ic_transaction_pay_select);
                PAY_TYPE = PAY_TYPE_WECHAT;
                break;
            case R.id.btn_confirm_pay:
                if (PAY_TYPE == 0) {
                    Toaster.show( "请选择正确的支付方式");
                    return;
                }
                doRechargeAction(PAY_TYPE);
                break;
            default:
                break;
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
        super.pop();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == action_transaction_buy_success && resultCode == RESULT_OK) {
            pop();
        }
    }


    private void doRechargeAction(final int paytype) {
        int code = AppConfig.getSbData(sp_key, 3);
        if (code == 2 || code == 1) {
            //only once
            actionBuyTradeGood(paytype);
        } else if (code == 3) {
            //always
            checkRealName(paytype);
        }

    }

    private void actionBuyTradeGood(int pay_type) {
        if (mViewModel != null) {
            mViewModel.buyTradeGood(gid, pay_type, new OnBaseCallback<PayBeanVo>() {
                @Override
                public void onSuccess(PayBeanVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                aliOutTradeNo = data.getData().getOut_trade_no();
                                doPay(data.getData(), good_price);
                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void checkRealName(int paytype) {
        if (mViewModel != null) {
            mViewModel.realNameCheck(new OnBaseCallback<RealNameCheckVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    loading();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                }

                @Override
                public void onSuccess(RealNameCheckVo realNameCheckVo) {
                    if (realNameCheckVo != null) {
                        if (realNameCheckVo.isStateOK()) {
                            actionBuyTradeGood(paytype);

                            AppConfig.putSbData(sp_key, 1);
                        } else if (realNameCheckVo.isStateTip()) {
                            if (realNameCheckVo.getData() != null) {
                                int code = realNameCheckVo.getData().realname_state;
                                AppConfig.putSbData(sp_key, code);
                                startFragment(CertificationFragment.newInstance());
                            }
                        } else {
                            Toaster.show( realNameCheckVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    private String aliOutTradeNo;

    @Override
    protected void onPaySuccess() {
        super.onPaySuccess();
        isAnyDataChange = true;
        setFragmentResult(RESULT_OK, null);
        startForResult(TransactionSuccessFragment.newInstance(gameid, game_type), action_transaction_buy_success);
    }

    @Override
    protected void onPayFailure(String resultStatus) {
        super.onPayFailure(resultStatus);
        buyingGoodCancelAction(aliOutTradeNo);
    }

    @Override
    protected void onPayCancel() {
        super.onPayCancel();
        buyingGoodCancelAction(aliOutTradeNo);
    }

    private void buyingGoodCancelAction(String orderid) {
        isAnyDataChange = true;
        if (mViewModel != null) {
            mViewModel.cancelTradePayOrder(orderid);
        }
    }


    private CustomDialog transactionBuyingTipDialog;

    private Button    mBtnGotIt;
    private ImageView mIvImage;
    private CheckBox  mCbButton;

    private void showTransactionBuyingTipDialog() {
        if (transactionBuyingTipDialog == null) {
            transactionBuyingTipDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_transaction_count_down_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            transactionBuyingTipDialog.setCancelable(false);
            transactionBuyingTipDialog.setCanceledOnTouchOutside(false);

            mBtnGotIt = (Button) transactionBuyingTipDialog.findViewById(R.id.btn_got_it);
            mIvImage = (ImageView) transactionBuyingTipDialog.findViewById(R.id.iv_image);
            mCbButton = (CheckBox) transactionBuyingTipDialog.findViewById(R.id.cb_button);

            mCbButton.setText("我已阅读买家必读");
            mIvImage.setImageResource(R.mipmap.img_transaction_tips_buy);

            GradientDrawable gd2 = new GradientDrawable();
            gd2.setCornerRadius(30 * density);
            gd2.setColor(Color.parseColor("#C1C1C1"));
            mBtnGotIt.setBackground(gd2);
            mBtnGotIt.setEnabled(false);
            mBtnGotIt.setOnClickListener(view -> {
                if (transactionBuyingTipDialog != null && transactionBuyingTipDialog.isShowing()) {
                    transactionBuyingTipDialog.dismiss();
                }
            });

            mCbButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
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
                }
            });
        }
        transactionBuyingTipDialog.show();
    }
}
