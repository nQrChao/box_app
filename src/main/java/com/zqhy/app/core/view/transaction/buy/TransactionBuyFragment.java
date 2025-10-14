package com.zqhy.app.core.view.transaction.buy;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
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

import androidx.annotation.NonNull;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.config.SpConstants;
import com.zqhy.app.config.WxControlConfig;
import com.zqhy.app.core.data.model.transaction.PayBeanVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.AbsPayBuyFragment;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.transaction.TransactionInstructionsFragment;
import com.zqhy.app.core.view.user.CertificationFragment;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 */
public class TransactionBuyFragment extends AbsPayBuyFragment<TransactionViewModel> implements View.OnClickListener {

    public static TransactionBuyFragment newInstance(String gid, String gameicon, String good_title, String gamename, String good_price, String gameid, String game_type, int goods_type) {
        return newInstance(gid, gameicon, good_title, gamename, good_price, gameid, game_type, 0, goods_type);
    }

    public static TransactionBuyFragment newInstance(String gid, String gameicon, String good_title, String gamename, String good_price, String gameid, String game_type, int buyAgain, int goods_type) {
        TransactionBuyFragment fragment = new TransactionBuyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gid", gid);
        bundle.putString("gameicon", gameicon);
        bundle.putString("good_title", good_title);
        bundle.putString("gamename", gamename);
        bundle.putString("good_price", good_price);
        bundle.putString("gameid", gameid);
        bundle.putString("game_type", game_type);
        bundle.putInt("buyAgain", buyAgain);
        bundle.putInt("goods_type", goods_type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static TransactionBuyFragment newInstance(String gid, String gamename,String gameSuffix, String gameicon, String genre_str, String play_count, String xh_showname, String server_info, float profit_rate, String good_price, String gameid, String game_type, int buyAgain, int goods_type) {
        TransactionBuyFragment fragment = new TransactionBuyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gid", gid);
        bundle.putString("gameicon", gameicon);
        bundle.putFloat("profit_rate", profit_rate);

        bundle.putString("genre_str", genre_str);
        bundle.putString("play_count", play_count);
        bundle.putString("xh_showname", xh_showname);
        bundle.putString("server_info", server_info);

        bundle.putString("gamename", gamename);
        bundle.putString("gameSuffix", gameSuffix);
        bundle.putString("good_price", good_price);
        bundle.putString("gameid", gameid);
        bundle.putString("game_type", game_type);
        bundle.putInt("buyAgain", buyAgain);
        bundle.putInt("goods_type", goods_type);
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
        return R.layout.fragment_transaction_buy;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private boolean isAnyDataChange = false;

    private String gid;
    private String gameicon;
    private String genre_str;
    private String play_count;
    private String xh_showname;
    private String server_info;

    private String gamename;
    private String gameSuffix;
    private String good_price;
    private int goods_type;
    private String gameid;
    private String game_type;
    private float profit_rate;
    private int buyAgain;
    private String sp_key;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gid = getArguments().getString("gid");
            gameicon = getArguments().getString("gameicon");
            genre_str = getArguments().getString("genre_str");
            play_count = getArguments().getString("play_count");
            xh_showname = getArguments().getString("xh_showname");
            server_info = getArguments().getString("server_info");
            profit_rate = getArguments().getFloat("profit_rate");

            gamename = getArguments().getString("gamename");
            gameSuffix = getArguments().getString("gameSuffix");
            good_price = getArguments().getString("good_price");
            gameid = getArguments().getString("gameid");
            game_type = getArguments().getString("game_type");

            goods_type = getArguments().getInt("goods_type", 0);

            buyAgain = getArguments().getInt("buyAgain");
        }
        super.initView(state);
        sp_key = "TRANSACTION_BUY" + SpConstants.SP_REAL_NAME_STATE + UserInfoModel.getInstance().getUID();
        initActionBackBarAndTitle("确认购买");
        showSuccess();

        bindViews();
        setGoodInfo();
    }

    @Override
    protected int getPayAction() {
        return PAY_ACTION_TRANSACTION;
    }


    private ImageView mIvTransactionImage;

    private TextView mTvTransactionGameName;
    private TextView mTvTransactionPrice;
    private LinearLayout mLlTransactionPayWayAlipay;
    private ImageView mIvSelectAlipay;
    private LinearLayout mLlTransactionPayWayWechat;

    private ImageView mIvSelectWechat;
    private TextView mTvTips;
    private Button mBtnConfirmPay;

    private TextView tv_genre_str;
    private TextView tv_play_count;
    private TextView tv_xh_account;
    private TextView tv_server_name;
    private TextView mTvGameSuffix;
    private LinearLayout mlayoutPercent;
    private TextView mTvPercent;
    private TextView mTvPercent1;

    private void bindViews() {
        mIvTransactionImage = (ImageView) findViewById(R.id.iv_transaction_image);
        mTvTransactionGameName = (TextView) findViewById(R.id.tv_transaction_game_name);
        mTvTransactionPrice = (TextView) findViewById(R.id.tv_transaction_price);
        mLlTransactionPayWayAlipay = (LinearLayout) findViewById(R.id.ll_transaction_pay_way_alipay);
        mIvSelectAlipay = (ImageView) findViewById(R.id.iv_select_alipay);
        mLlTransactionPayWayWechat = (LinearLayout) findViewById(R.id.ll_transaction_pay_way_wechat);
        mIvSelectWechat = (ImageView) findViewById(R.id.iv_select_wechat);
        mTvTips = (TextView) findViewById(R.id.tv_tips);
        mBtnConfirmPay = (Button) findViewById(R.id.btn_confirm_pay);

        mlayoutPercent = findViewById(R.id.layout_percent);
        tv_genre_str = (TextView) findViewById(R.id.tv_genre_str);
        tv_play_count = (TextView) findViewById(R.id.tv_play_count);
        tv_xh_account = (TextView) findViewById(R.id.tv_xh_account);
        tv_server_name = (TextView) findViewById(R.id.tv_server_name);
        mTvPercent = findViewById(R.id.tv_percent);
        mTvPercent1 = findViewById(R.id.tv_percent1);
        mTvGameSuffix = findViewById(R.id.tv_game_suffix);

        TextView title_bottom_line = findViewById(R.id.title_bottom_line);
        title_bottom_line.setVisibility(View.GONE);

        setLayoutViews();
        setListeners();

        mTvTips.setText(Html.fromHtml(getAppNameByXML(R.string.string_transaction_pay_tips)));

        showTransactionBuyingTipDialog();
    }

    private void setLayoutViews() {
        if (WxControlConfig.isWxControl()) {
            mLlTransactionPayWayWechat.setVisibility(View.VISIBLE);
        } else {
            mLlTransactionPayWayWechat.setVisibility(View.GONE);
        }
    }

    private void setListeners() {
        mLlTransactionPayWayAlipay.setOnClickListener(this);
        mLlTransactionPayWayWechat.setOnClickListener(this);
        mBtnConfirmPay.setOnClickListener(this);
    }

    private void setGoodInfo() {
        GlideUtils.loadRoundImage(_mActivity, gameicon, mIvTransactionImage, R.mipmap.ic_placeholder);
        mTvTransactionGameName.setText(gamename);
        mTvTransactionPrice.setText(good_price);

        tv_genre_str.setText(genre_str);
//        tv_play_count.setText(" • " + play_count + "人在玩");
        String xh_username = "";
        if (xh_showname!=null&&xh_showname.length()>=8){
            xh_username = xh_showname.substring(xh_showname.length() - 8);
        }else {
            xh_username = xh_showname;
        }
        tv_xh_account.setText("小号-" + xh_username);
        tv_server_name.setText("区服: " + server_info);

        if (!TextUtils.isEmpty(gameSuffix)){//游戏后缀
            mTvGameSuffix.setVisibility(View.VISIBLE);
            mTvGameSuffix.setText(gameSuffix);
        }else {
            mTvGameSuffix.setVisibility(View.GONE);
        }

        if ("1".equals(game_type)) {
            if (profit_rate <= 0.1 && profit_rate > 0.01) {
                mlayoutPercent.setVisibility(View.VISIBLE);
                mTvPercent.setText("0" + CommonUtils.saveTwoSizePoint(profit_rate * 10) + "折");
                mTvPercent1.setText("抄底");
            } else if (profit_rate <= 0.2 && profit_rate > 0.1) {
                mlayoutPercent.setVisibility(View.VISIBLE);
                mTvPercent.setText(CommonUtils.saveTwoSizePoint(profit_rate * 10) + "折");
                mTvPercent1.setText("捡漏");
            } else {
                mlayoutPercent.setVisibility(View.INVISIBLE);
            }
        } else {
            mlayoutPercent.setVisibility(View.INVISIBLE);
        }

//        mTvPriceWechat.setText("-" + good_price);
//        mTvPriceAlipay.setText("-" + good_price);

        onClick(mLlTransactionPayWayAlipay);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_transaction_pay_way_alipay:
                mIvSelectAlipay.setImageResource(R.mipmap.ic_transaction_pay_select1);
                mIvSelectWechat.setImageResource(R.mipmap.ic_transaction_pay_normal1);
                PAY_TYPE = PAY_TYPE_ALIPAY;
                break;
            case R.id.ll_transaction_pay_way_wechat:
                mIvSelectAlipay.setImageResource(R.mipmap.ic_transaction_pay_normal1);
                mIvSelectWechat.setImageResource(R.mipmap.ic_transaction_pay_select1);
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
        if (checkLogin()) {
            if (UserInfoModel.getInstance().isSetCertification()) {
                actionBuyTradeGood(paytype);
                AppConfig.putSbData(sp_key, 1);
            } else {
                startFragment(CertificationFragment.newInstance());
            }
        }
//        if (mViewModel != null) {
//            mViewModel.realNameCheck(new OnBaseCallback<RealNameCheckVo>() {
//                @Override
//                public void onBefore() {
//                    super.onBefore();
//                    loading();
//                }
//
//                @Override
//                public void onAfter() {
//                    super.onAfter();
//                }
//
//                @Override
//                public void onSuccess(RealNameCheckVo realNameCheckVo) {
//                    if (realNameCheckVo != null) {
//                        if (realNameCheckVo.isStateOK()) {
//                            actionBuyTradeGood(paytype);
//
//                            AppConfig.putSbData(sp_key, 1);
//                        } else if (realNameCheckVo.isStateTip()) {
//                            if (realNameCheckVo.getData() != null) {
//                                int code = realNameCheckVo.getData().realname_state;
//                                AppConfig.putSbData(sp_key, code);
//                                startFragment(CertificationFragment.newInstance());
//                            }
//                        } else {
//                            Toaster.show( realNameCheckVo.getMsg());
//                        }
//                    }
//                }
//            });
//        }
    }

    private String aliOutTradeNo;

    @Override
    protected void onPaySuccess() {
        super.onPaySuccess();
        isAnyDataChange = true;
        setFragmentResult(RESULT_OK, null);
        startForResult(TransactionInstructionsFragment.newInstance("购买成功", 1), action_transaction_buy_success);
//        startForResult(TransactionSuccessFragment.newInstance(gameid, game_type), action_transaction_buy_success);
    }

    @Override
    protected void onPayFailure(String resultStatus) {
        super.onPayFailure(resultStatus);
//        buyingGoodCancelAction(aliOutTradeNo);
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

    private TextView mBtnGotIt;
    private TextView tv_close;
    private TextView tv_content_2;
    private TextView tv_content_3;
    private CheckBox mCbButton;
    boolean canClick = false;

    private void showTransactionBuyingTipDialog() {

        if (transactionBuyingTipDialog == null) {
            transactionBuyingTipDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_transaction_count_down_tips12, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
            transactionBuyingTipDialog.setCancelable(false);
            transactionBuyingTipDialog.setCanceledOnTouchOutside(false);

            mBtnGotIt = (TextView) transactionBuyingTipDialog.findViewById(R.id.btn_got_it);
            tv_close = (TextView) transactionBuyingTipDialog.findViewById(R.id.tv_close);
            tv_content_2 = (TextView) transactionBuyingTipDialog.findViewById(R.id.tv_content_2);
            tv_content_3 = (TextView) transactionBuyingTipDialog.findViewById(R.id.tv_content_3);
            mCbButton = (CheckBox) transactionBuyingTipDialog.findViewById(R.id.cb_button);

            if (goods_type == 2){
                SpannableStringBuilder ss = new SpannableStringBuilder("2、捡漏号游戏内情况未知，能捡漏到什么号，纯属运气。捡漏号购后不可交易/不可转区，可以回收；回收仅计算购后再次实付的金额。了解更多交易须知 >");
                ss.setSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        ds.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        ds.setColor(Color.parseColor("#4E76FF"));
                    }
                    @Override
                    public void onClick(@NonNull View widget) {
                        //交易须知
                        BrowserActivity.newInstance(_mActivity, "https://mobile.tsyule.cn/index.php/Index/view/?id=205410");
                    }
                }, ss.length() - 6, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                tv_content_2.setText(ss);
                tv_content_2.setMovementMethod(LinkMovementMethod.getInstance());
            }else {
                SpannableStringBuilder ss = new SpannableStringBuilder("2、时间因素造成的信息变化，不视为信息失实际，具体可能会存在的变化，了解更多交易须知 >");
                ss.setSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        ds.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        ds.setColor(Color.parseColor("#4E76FF"));
                    }
                    @Override
                    public void onClick(@NonNull View widget) {
                        //交易须知
                        BrowserActivity.newInstance(_mActivity, "https://mobile.tsyule.cn/index.php/Index/view/?id=205410");
                    }
                }, ss.length() - 6, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                tv_content_2.setText(ss);
                tv_content_2.setMovementMethod(LinkMovementMethod.getInstance());
            }


            SpannableString ss1 = new SpannableString("3、部分游戏存在设备限制，购前务必先尝试游戏是否可正常登录，若无法进入不建议购买。交易完成后，不支持退换。如因不可抗力因素存在问题，联系客服核实并协助处理");
            ss1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 41, 52, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss1.setSpan(new StyleSpan(Typeface.BOLD), 41, 52, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_content_3.setText(ss1);

            mBtnGotIt.setOnClickListener(view -> {
                if (transactionBuyingTipDialog != null && transactionBuyingTipDialog.isShowing()) {
                    if (canClick) {
                        transactionBuyingTipDialog.dismiss();
                    } else {
                        Toaster.show("请阅读并勾选我已阅读买家须知");
                    }
                }
            });

            tv_close.setOnClickListener(view -> {
                if (transactionBuyingTipDialog != null && transactionBuyingTipDialog.isShowing()) {
                    transactionBuyingTipDialog.dismiss();
                    pop();
                }
            });

            mCbButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    mBtnGotIt.setEnabled(b);
                    canClick = b;
                }
            });
        }
        transactionBuyingTipDialog.show();
    }
}
