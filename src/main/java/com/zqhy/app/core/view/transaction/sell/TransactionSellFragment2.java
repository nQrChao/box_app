package com.zqhy.app.core.view.transaction.sell;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.hjq.toast.Toaster;
import com.donkingliang.imageselector.PreviewActivity;
import com.donkingliang.imageselector.entry.Image;
import com.donkingliang.imageselector.event.PhotoEvent;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.ThumbnailBean;
import com.zqhy.app.core.data.model.transaction.TradeGoodDetailInfoVo;
import com.zqhy.app.core.data.model.transaction.TradeSellConfigVo;
import com.zqhy.app.core.data.model.transaction.TradeXhInfoVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.StringUtil;
import com.zqhy.app.core.tool.utilcode.ConstUtils;
import com.zqhy.app.core.tool.utilcode.FileUtils;
import com.zqhy.app.core.tool.utilcode.KeyboardUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.BlankTxtFragment;
import com.zqhy.app.core.view.transaction.util.CustomPopWindow;
import com.zqhy.app.core.view.user.BindPhoneFragment;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.CountDownTimerCopyFromAPI26;
import com.zqhy.app.widget.imageview.ClipRoundImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;

/**
 * @author Administrator
 */
public class TransactionSellFragment2 extends BaseFragment<TransactionViewModel> implements View.OnClickListener {

    public static final int REQUEST_CODE = 0x00000011;

    public static TransactionSellFragment2 newInstance(String gid) {
        TransactionSellFragment2 fragment = new TransactionSellFragment2();
        Bundle bundle = new Bundle();
        bundle.putString("gid", gid);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static TransactionSellFragment2 newInstance(String gameid, String gameicon, String gamename, String otherGamename, String xh_pay_total, String xh_reg_day, String xh_showname, String xh_username, String game_type, String rmb_total) {
        TransactionSellFragment2 fragment = new TransactionSellFragment2();
        Bundle bundle = new Bundle();
        bundle.putString("gameid", gameid);
        bundle.putString("gameicon", gameicon);
        bundle.putString("gamename", gamename);
        bundle.putString("otherGamename", otherGamename);
        bundle.putString("xh_pay_total", xh_pay_total);
        bundle.putString("xh_reg_day", xh_reg_day);
        bundle.putString("xh_showname", xh_showname);
        bundle.putString("xh_username", xh_username);
        bundle.putString("game_type", game_type);
        bundle.putString("rmb_total", rmb_total);
        fragment.setArguments(bundle);
        return fragment;
    }

    public TransactionSellFragment2() {
        // Required empty public constructor
    }

    private static final int action_choose_game = 0x7771;
    private static final int action_choose_game_xh = 0x7774;
    private static final int action_write_title = 0x7772;
    private static final int action_write_description = 0x7773;
    private static final int action_write_secondary_password = 0x7775;


    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "卖号页";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transaction_sell2;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private String gid;
    private String gameid;
    private String xh_username;
    private String gameicon;
    private String gamename;
    private String otherGamename;
    private String xh_pay_total;
    private String xh_reg_day;
    private String xh_showname;
    private String game_type;
    private String rmb_total;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gid = getArguments().getString("gid");
            gameid = getArguments().getString("gameid");
            gameicon = getArguments().getString("gameicon");
            gamename = getArguments().getString("gamename");
            otherGamename = getArguments().getString("otherGamename");
            xh_pay_total = getArguments().getString("xh_pay_total");
            xh_username = getArguments().getString("xh_username");
            xh_reg_day = getArguments().getString("xh_reg_day");
            xh_showname = getArguments().getString("xh_showname");
            game_type = getArguments().getString("game_type");
            rmb_total = getArguments().getString("rmb_total");
        }
        if ("3".equals(game_type)) {
            xh_client = "3";
        } else {
            xh_client = "1";
        }
        super.initView(state);
        initActionBackBarAndTitle("填写商品信息");
        bindViews();

        showLoading();
        getTradeSellConfig();

        if (TextUtils.isEmpty(gid)) {
            targetGameid = gameid;
            targetXh_name = xh_username;
            initTopData();
//            showTransactionTipDialog();
            //tradeXhInfo();
        } else {
            getTradeGoodInfo();
        }
        setActionBackBarClickListener(v -> {
//            showPopSellNoticeView();
            showPopListView(R.layout.pop_transaction_sell_back);
        });

    }

    @Override
    public boolean onBackPressedSupport() {
        showPopListView(R.layout.pop_transaction_sell_back);
        return true;
    }

    private CustomDialog transactionTipDialog1;


    private void showTransactionTipDialog1() {
        if (transactionTipDialog1 == null) {
            transactionTipDialog1 = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_transaction_vip_explain, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            transactionTipDialog1.setCancelable(false);
            transactionTipDialog1.setCanceledOnTouchOutside(false);

            TextView btn_got_it = (TextView) transactionTipDialog1.findViewById(R.id.btn_got_it);
            TextView tv_content_1 = (TextView) transactionTipDialog1.findViewById(R.id.tv_content_1);
            String string11 = "出售成功将收取5%手续费，最低5元；扣除手续费剩余收益将以平台币形式转至您的账号内，可充值平台任意游戏。";
            tv_content_1.setText(string11);

            btn_got_it.setOnClickListener(view -> {
                if (transactionTipDialog1 != null) {
                    transactionTipDialog1.dismiss();
                    transactionTipDialog1 = null;
                }
            });
            transactionTipDialog1.show();
        }
    }

    /**
     * 屏幕底部弹出
     * 方法名@：showAtLocation(contentView, Gravity.BOTTOM, 0, 0)
     * resource:PopWindow 布局
     */
    private void showPopListView(int resource) {
        if (resource == 0) {
            return;
        }
        View contentView = LayoutInflater.from(_mActivity).inflate(resource, null);
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
//            setFragmentResult(RESULT_OK, null);
//            _mActivity.setResult(TransactionSellFragment2.FRIST_COMMIT_GOODS_SUCCESS,null);
            pop();
        });

    }

    private TextView tv_count;
    private EditText mEtComment;

    private void showPopWriteDescriptionView(int resource, String write) {
        int maxTxtLength = 100;
        int minTxtLength = 5;
        if (resource == 0) {
            return;
        }
        View contentView = LayoutInflater.from(_mActivity).inflate(resource, null);
        TextView tv_close = contentView.findViewById(R.id.tv_close);
        TextView tv_do = contentView.findViewById(R.id.tv_do);
        mEtComment = contentView.findViewById(R.id.et_comment);
        tv_count = contentView.findViewById(R.id.tv_count);
        if (!TextUtils.isEmpty(write)) {
            mEtComment.setText(write);
            mEtComment.setSelection(mEtComment.getText().toString().length());
        }
        tv_count.setText("字数  (" + mEtComment.getText().toString().trim().length() + ")");
        mEtComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String strComment = mEtComment.getText().toString().trim();
                if (strComment.length() > maxTxtLength) {
                    mEtComment.setText(strComment.substring(0, maxTxtLength));
                    mEtComment.setSelection(mEtComment.getText().toString().length());
                    Toaster.show( "亲，字数超过啦~");
                }
                tv_count.setText("字数  (" + mEtComment.getText().toString().trim().length() + ")");
            }
        });

        //处理popWindow 显示内容
        //创建并显示popWindow
        CustomDialog customPopWindow = new CustomDialog(_mActivity, contentView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        /*CustomPopWindow customPopWindow = new CustomPopWindow.PopupWindowBuilder(_mActivity)
                .enableOutsideTouchableDissmiss(false)
                .enableBackgroundDark(true)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                .create();
        customPopWindow.setBackgroundAlpha(0.5f);
        customPopWindow.getPopupWindow().setSoftInputMode (SOFT_INPUT_ADJUST_RESIZE);//软键盘弹出自动调整位置
        customPopWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);*/
        tv_close.setOnClickListener(v -> {
            if (customPopWindow != null && customPopWindow.isShowing()) customPopWindow.dismiss();
        });

        tv_do.setOnClickListener(v -> {
            String strComment = mEtComment.getText().toString().trim();
            if (TextUtils.isEmpty(strComment)) {
                Toaster.show( "请输入内容");
                return;
            } else {
                if (strComment.length() < minTxtLength) {
                    Toaster.show( "您输入的内容小于" + minTxtLength + "个字");
                    return;
                }
                if (strComment.length() > maxTxtLength) {
                    Toaster.show( "您输入的内容大于" + maxTxtLength + "个字");
                    return;
                }
            }

            mTvTransactionDescription.setText(strComment);
            if (customPopWindow != null && customPopWindow.isShowing()) customPopWindow.dismiss();
        });
        customPopWindow.show();
    }

    private ClipRoundImageView mIvTransactionImage2;
    private TextView mTvTransactionGameName;
    private TextView tv_xh_showname;
    private TextView mTvGameSuffix;
    private TextView tv_xh_username;
    private TextView tv_xh_reg_day;
    private TextView tv_xh_pay_total;
    private ImageView iv_ban_trade_info;
    private LinearLayout rootView;


    public static final int RESTART_SELL = 0x666;
    public static final int FRIST_COMMIT_GOODS_SUCCESS = 0x8777;

    private void initTopData() {
        if (TextUtils.isEmpty(gid)) {
            //新发布商品
            rootView.setOnClickListener(view -> {
//                showPopListView(R.layout.pop_transaction_sell_back);
                startForResult(TransactionSellFragment.newInstance(RESTART_SELL), RESTART_SELL);
            });
            GlideUtils.loadRoundImage(_mActivity, gameicon, mIvTransactionImage2, R.mipmap.ic_placeholder);
            iv_ban_trade_info.setVisibility(View.VISIBLE);
            mTvTransactionGameName.setText(gamename);

            tv_xh_username.setText(xh_showname + "，游戏充值" + xh_pay_total + "元");
            tv_xh_reg_day.setText("创建" + xh_reg_day + "天");
            tv_xh_pay_total.setText(rmb_total);
            if (!TextUtils.isEmpty(otherGamename)){//游戏后缀
                mTvGameSuffix.setVisibility(View.VISIBLE);
                mTvGameSuffix.setText(otherGamename);
            }else {
                mTvGameSuffix.setVisibility(View.GONE);
            }
        }
    }


    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getTradeGoodInfo();
    }

    private LinearLayout mLlContentLayout;


    private EditText mEtTransactionGameServer;
    private LinearLayout mLlWriteDescription;
    private TextView mTvTransactionDescription;
    private EditText mTvTransactionSecondaryPassword;
    private RecyclerView mRecyclerViewThumbnail;
    private Button mBtnConfirmSell;
    private TextView tv_set_price;
    private TextView tv_set_price_tips;

    private void bindViews() {
        mLlContentLayout = findViewById(R.id.ll_content_layout);

        mIvTransactionImage2 = findViewById(R.id.iv_transaction_image2);
        mTvTransactionGameName = findViewById(R.id.tv_transaction_game_name);
        tv_xh_showname = findViewById(R.id.tv_xh_showname);
        tv_xh_username = findViewById(R.id.tv_xh_username);
        tv_xh_pay_total = findViewById(R.id.tv_xh_pay_total);
        tv_xh_reg_day = findViewById(R.id.tv_xh_reg_day);
        iv_ban_trade_info = findViewById(R.id.iv_ban_trade_info);
        rootView = findViewById(R.id.rootView);
        mTvGameSuffix = findViewById(R.id.tv_game_suffix);

        mEtTransactionGameServer = findViewById(R.id.et_transaction_game_server);

        mLlWriteDescription = findViewById(R.id.ll_write_description);
        mTvTransactionDescription = findViewById(R.id.tv_transaction_description);
        mTvTransactionSecondaryPassword = findViewById(R.id.tv_transaction_secondary_password);
        mRecyclerViewThumbnail = findViewById(R.id.recyclerView_thumbnail);
        mBtnConfirmSell = findViewById(R.id.btn_confirm_sell);

        tv_set_price = findViewById(R.id.tv_set_price);
        tv_set_price_tips = findViewById(R.id.tv_set_price_tips);
        tv_set_price_tips.setVisibility(View.GONE);

        tv_set_price.setOnClickListener(v -> {
            setPriceDialog();
        });

        mEtTransactionGameServer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                findViewById(R.id.tv_transaction_game_server_tips).setVisibility(View.GONE);
                if (!TextUtils.isEmpty(s.toString())){
                    findViewById(R.id.iv_transaction_game_server_delete).setVisibility(View.VISIBLE);
                }else {
                    findViewById(R.id.iv_transaction_game_server_delete).setVisibility(View.GONE);
                }
            }
        });
        findViewById(R.id.iv_transaction_game_server_delete).setOnClickListener(v -> {
            mEtTransactionGameServer.setText("");
        });

        initList();
        setListeners();
    }

    private int maxPicCount = 9;
    //允许还价 2 不允许还价 1
    private int allowedBargain = 2;
    //不自动降价 2 自动降价 1
    private int autoPrice = 1;

    private ThumbnailAdapter mThumbnailAdapter;

    private void initList() {
        GridLayoutManager layoutManager = new GridLayoutManager(_mActivity, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
//        layoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerViewThumbnail.setLayoutManager(layoutManager);
        mRecyclerViewThumbnail.setNestedScrollingEnabled(false);

        List<ThumbnailBean> list = new ArrayList<>();

        ThumbnailBean thumbnailBean = new ThumbnailBean();
        thumbnailBean.setType(1);
        list.add(thumbnailBean);

        mThumbnailAdapter = new ThumbnailAdapter(_mActivity, list, maxPicCount);
        mRecyclerViewThumbnail.setAdapter(mThumbnailAdapter);
    }

    private void setListeners() {
        mLlWriteDescription.setOnClickListener(this);
        mBtnConfirmSell.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_write_description:
                String descriptionTxt = mTvTransactionDescription.getText().toString().trim();
                if (descriptionTxt == null) {
                    descriptionTxt = "";
                }
                showPopWriteDescriptionView(R.layout.pop_transaction_sell_write_description, descriptionTxt);
                break;
            case R.id.ll_write_secondary_password:
                String secondaryPasswordTxt = mTvTransactionSecondaryPassword.getText().toString().trim();
                if (secondaryPasswordTxt == null) {
                    secondaryPasswordTxt = "";
                }
                startForResult(BlankTxtFragment.newInstance("二级密码", "若有二级密码必须填写。填写规范：仓库密码 123456。该密码仅审核人员及最终买家可见", secondaryPasswordTxt, 5, 50, true), action_write_secondary_password);

                break;
            case R.id.btn_confirm_sell:
                try {
                    if (!UserInfoModel.getInstance().isBindMobile()) {
                        start(BindPhoneFragment.newInstance(false, ""));
                    } else {
                        if (validateParameter()) {
                            if (TextUtils.isEmpty(gid)) {
                                //确认出售1
                                showPopSellNoticeView();
//                                showTransactionConfirmDialog();
                            } else {
                                //修改商品属性，不需要弹出验证码弹窗
                                confirmAddGood();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_send_code:
                //发送验证码
//                getTradeCode();
                break;
            case R.id.btn_confirm:
                //确认出售2
//                if (validateParameter()) {
//                    confirmAddGood();
//                }
                break;

            /*case R.id.tv_vip_config:
                //开通vip
                refreshData = true;
                startFragment(new NewUserVipFragment1());
                break;*/
            default:
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (UserInfoModel.getInstance().isVipMember()) {
                getTradeSellConfig();
            }
        }
    }

    boolean refreshData = false;

    Float minCharge = 5.00f;//手续费最少5元
    Float minSuperCharge = 3.00f;//vip最少3元
    Float minRate = 0.05f;//费率5%
    Float minSuperRate = 0.02f;//费率2%
    Float taxRate = 0.03f;//费率差3%

    int isViprate = 0;
    int isVipcharge = 0;
    int notViprate = 0;
    int notVipcharge = 0;
    boolean isVip = false;

    private void getTradeSellConfig() {
        Map<String, String> params = new TreeMap<>();
        if (!StringUtil.isEmpty(gid)) {
            params.put("gid", gid);
        }
        if (mViewModel != null) {
            mViewModel.getTradeSellConfig(params, new OnBaseCallback<TradeSellConfigVo>() {
                @Override
                public void onSuccess(TradeSellConfigVo data) {
                    showSuccess();
                    if (data.isStateOK()) {
                        TradeSellConfigVo.SellConfig sellConfig = data.getData();
                        float v = sellConfig.getRate() - sellConfig.getSuper_user_rate();
                        taxRate = (v) / 100;
                        isViprate = (int) sellConfig.getSuper_user_rate();
                        isVipcharge = (int) sellConfig.getSuper_user_min_charge();
                        notViprate = (int) sellConfig.getRate();
                        notVipcharge = (int) sellConfig.getMin_charge();
                        //tv_commission_charge1.setText((int) sellConfig.getRate() + "%手续费");
                        //tv_commission_charge2.setText("，最低" + (int) sellConfig.getMin_charge() + "元");
                        if (sellConfig.isIs_super_user()) {
                            //会员
                            isVip = false;
                            //tv_layout_vip.setVisibility(View.VISIBLE);
                            minCharge = sellConfig.getMin_charge();
                            minRate = sellConfig.getRate() / 100;
                            minSuperRate = sellConfig.getSuper_user_rate() / 100;
                            minSuperCharge = sellConfig.getSuper_user_min_charge();
//                            tv_commission_charge1.setText((int) sellConfig.getSuper_user_rate() + "%手续费");
//                            tv_commission_charge2.setText("，最低" + (int) sellConfig.getSuper_user_min_charge() + "元");
                            //tv_vip_config.setVisibility(View.GONE);
                            //layout_vip.setVisibility(View.VISIBLE);
                        } else {
                            //非会员
                            isVip = false;
                            //layout_vip.setVisibility(View.GONE);
                            //tv_layout_vip.setVisibility(View.GONE);
                            //tv_vip_config.setVisibility(View.GONE);
                            //tv_vip_config.setOnClickListener(TransactionSellFragment2.this);
                            minCharge = sellConfig.getMin_charge();
                            minRate = sellConfig.getRate() / 100;
//                            tv_commission_charge1.setText((int) sellConfig.getRate() + "%手续费");
//                            tv_commission_charge2.setText("，最低" + (int) sellConfig.getMin_charge() + "元");
                            //tv_vip_config.setText("开通会员立减" + (int) v + "%手续费  >");
                        }

                        if (!StringUtil.isEmpty(gid)) {
                            TradeSellConfigVo.SellConfig.GoodsConfig trade_goods = data.getData().getTrade_goods();
                            //修改商品
                            if (trade_goods != null) {
                                GlideUtils.loadRoundImage(_mActivity, trade_goods.getGameicon(), mIvTransactionImage2, R.mipmap.ic_placeholder);
                                iv_ban_trade_info.setVisibility(View.INVISIBLE);
                                mTvTransactionGameName.setText(trade_goods.getGamename());
                                if (!TextUtils.isEmpty(trade_goods.getOtherGameName())){//游戏后缀
                                    mTvGameSuffix.setVisibility(View.VISIBLE);
                                    mTvGameSuffix.setText(trade_goods.getOtherGameName());
                                }else {
                                    mTvGameSuffix.setVisibility(View.GONE);
                                }
                                tv_xh_username.setText(trade_goods.getXh_showname() + "，游戏充值" + trade_goods.getXh_pay_game_total() + "元");
                                tv_xh_reg_day.setText("创建" + trade_goods.getXh_reg_day() + "天");
                                tv_xh_pay_total.setText(trade_goods.getRmb_total() + "");

                                targetGameid = trade_goods.getGameid();
                                targetXh_name = trade_goods.getXh_username();
                            }
                        }

                    } else {
                        Toaster.show("商品数据请求异常,请联系客服!");
                    }
                }
            });
        }

    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESTART_SELL) {
                if (data != null) {
                    gameid = data.getString("gameid");
                    gameicon = data.getString("gameicon");
                    gamename = data.getString("gamename");
                    otherGamename = data.getString("otherGamename");
                    xh_pay_total = data.getString("xh_pay_total");
                    xh_username = data.getString("xh_username");
                    xh_reg_day = data.getString("xh_reg_day");
                    xh_showname = data.getString("xh_showname");
                    game_type = data.getString("game_type");
                    targetGameid = gameid;
                    targetXh_name = xh_username;
                    initTopData();
                }
            }


            switch (requestCode) {

                case action_choose_game:
//                    if (data != null) {
//                        targetGameid = data.getString("gameid");
//                        targetGamename = data.getString("gamename");
//                        targetGameicon = data.getString("gameicon");
//
//                        targetXh_name = data.getString("xh_name");
//                        String targetXh_nickname = data.getString("xh_nickname");
//
//                        xh_id = data.getInt("xh_id", -1);
//
//                        targetGame_type = data.getString("game_type");
//
//                        StringBuilder sb = new StringBuilder();
//
//                        sb.append("targetGameid = ").append(targetGameid).append("\n");
//                        sb.append("targetGamename = ").append(targetGamename).append("\n");
//                        sb.append("targetGameicon = ").append(targetGameicon).append("\n");
//                        sb.append("targetXh_name = ").append(targetXh_name).append("\n");
//                        sb.append("targetXh_nickname = ").append(targetXh_nickname).append("\n");
//                        sb.append("xh_id = ").append(xh_id);
//
//                        Logs.e(sb.toString());
//
//                        if ("3".equals(targetGame_type)) {
//                            xh_client = "3";
//                        } else {
//                            xh_client = "1";
//                        }
//                    }
                    break;
                case action_write_description:
                    if (data != null) {
                        String strData = data.getString("data");
                        mTvTransactionDescription.setText(strData);
                    }
                    break;
//                case action_write_secondary_password:
//                    if (data != null) {
//                        String strData = data.getString("data");
//                        mTvTransactionSecondaryPassword.setText(strData);
//                    }
//                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESTART_SELL && data != null) {
            gameid = data.getStringExtra("gameid");
            gameicon = data.getStringExtra("gameicon");
            gamename = data.getStringExtra("gamename");
            otherGamename = data.getStringExtra("otherGamename");
            xh_pay_total = data.getStringExtra("xh_pay_total");
            xh_username = data.getStringExtra("xh_username");
            xh_reg_day = data.getStringExtra("xh_reg_day");
            xh_showname = data.getStringExtra("xh_showname");
            game_type = data.getStringExtra("game_type");
            targetGameid = gameid;
            targetXh_name = xh_username;
            initTopData();
        }

        /*if (requestCode == REQUEST_CODE && data != null) {
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
            List<ThumbnailBean> thumbnailBeanList = new ArrayList<>();
            for (String image : images) {
                ThumbnailBean thumbnailBean = new ThumbnailBean();
                thumbnailBean.setType(0);
                thumbnailBean.setImageType(0);
                thumbnailBean.setLocalUrl(image);

                thumbnailBeanList.add(thumbnailBean);
            }

            if (mThumbnailAdapter != null) {
                mThumbnailAdapter.addAllFirst(thumbnailBeanList);
                mThumbnailAdapter.notifyDataSetChanged();

                mThumbnailAdapter.refreshThumbnails();
            }
        }*/
    }


    private void getTradeCode() {
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

    private void tradeXhInfo() {
        if (TextUtils.isEmpty(targetXh_name)) return;
        if (mViewModel != null) {
            mViewModel.tradeXhInfo(targetXh_name, new OnBaseCallback<TradeXhInfoVo>() {
                @Override
                public void onSuccess(TradeXhInfoVo data) {
                    if (data != null && data.isStateOK()) {
                        if (data.getData() != null && !TextUtils.isEmpty(data.getData().getServername())){
                            mEtTransactionGameServer.setText(data.getData().getServername());
                            findViewById(R.id.tv_transaction_game_server_tips).setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }
    }

    private void sendCode() {
        if (tv_code != null) {
            tv_code.setTextColor(Color.parseColor("#9B9B9B"));
            if (customPopWindow != null && mEtVerificationCode != null) {
                mEtVerificationCode.setFocusable(true);
                mEtVerificationCode.post(() -> KeyboardUtils.showSoftInput(_mActivity, mEtVerificationCode));
            }
            new CountDownTimerCopyFromAPI26(60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tv_code.setEnabled(false);
                    tv_code.setText("重新发送" + (millisUntilFinished / 1000) + "秒");
                }

                @Override
                public void onFinish() {
                    tv_code.setTextColor(Color.parseColor("#5571fe"));
                    tv_code.setText("获取验证码");
                    tv_code.setEnabled(true);
                }
            }.start();
        }
    }

    private boolean validateParameter() {

        String strSeverInfo = mEtTransactionGameServer.getText().toString().trim();
        if (TextUtils.isEmpty(strSeverInfo)) {
            Toaster.show( mEtTransactionGameServer.getHint());
            return false;
        }

        if ("0".equals(strSeverInfo)) {
            Toaster.show( "请输入正确的区服信息");
            return false;
        }


        String strGoodPrice = tv_set_price.getText().toString().trim();
        if (TextUtils.isEmpty(strGoodPrice) || strGoodPrice.equals("设置价格")) {
            Toaster.show( "请先设置价格");
            return false;
        }

        int goodPrice = (int) Double.parseDouble(strGoodPrice);
        if (goodPrice < 6) {
            Toaster.show( "出售价不低于6元");
            return false;
        }

        if (autoPrice == 2 && goodPrice <= 6){
            Toaster.show("已设置最低价，无法使用自动降价功能");
            return false;
        }

        /*if (TextUtils.isEmpty(mTvTransactionDescription.getText().toString().trim())) {
            Toaster.show( "请填写商品描述");
            return false;
        }*/

        /*if (TextUtils.isEmpty(xh_client)) {
            Toaster.show( "请选择客户端");
            return false;
        }*/


        if (mThumbnailAdapter != null) {
            if (mThumbnailAdapter.getItemCount() < 4) {
                Toaster.show( "游戏截图不少于3张");
                return false;
            }
        }
        return true;
    }

    private void confirmAddGood() {
        String code = "";
        try {
            if (mEtVerificationCode != null) {
                code = mEtVerificationCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    if (TextUtils.isEmpty(gid)) {
                        Toaster.show( "请输入验证码");
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String strSeverInfo = mEtTransactionGameServer.getText().toString().trim();
        String strGoodPrice = String.valueOf((int)Double.parseDouble(tv_set_price.getText().toString().trim()));
        String srtGoodDescription = mTvTransactionDescription.getText().toString().trim();
        if (TextUtils.isEmpty(srtGoodDescription)) srtGoodDescription = "详情看图";

        Map<String, String> paramsMap = new TreeMap<>();

        paramsMap.put("gameid", targetGameid);
        paramsMap.put("xh_username", targetXh_name);
        paramsMap.put("server_info", strSeverInfo);
        paramsMap.put("gameid", targetGameid);
        paramsMap.put("can_bargain", allowedBargain + "");
        paramsMap.put("auto_price", autoPrice + "");

        paramsMap.put("xh_client", xh_client);
        paramsMap.put("goods_price", strGoodPrice);
        if (!TextUtils.isEmpty(srtGoodDescription)) {
            paramsMap.put("goods_description", srtGoodDescription);
        }
        if (!TextUtils.isEmpty(code)) {
            paramsMap.put("code", code);
        }

        /****2018.05.03 增加二级密码******************************/
        String strSecondaryPassword = mTvTransactionSecondaryPassword.getText().toString().trim();
        if (!TextUtils.isEmpty(strSecondaryPassword)) {
            paramsMap.put("xh_passwd", strSecondaryPassword);
        }


        if (mThumbnailAdapter != null) {
            List<ThumbnailBean> thumbnailBeanList = mThumbnailAdapter.getDatas();
            List<File> localPathList = new ArrayList<>();

            for (int i = 0; i < thumbnailBeanList.size(); i++) {
                ThumbnailBean thumbnailBean = thumbnailBeanList.get(i);
                if (thumbnailBean.getType() == 1 || thumbnailBean.getImageType() == 1) {
                    continue;
                }
                File file = new File(thumbnailBean.getLocalUrl());
                int fileSize = (int) FileUtils.getFileSize(file, ConstUtils.MemoryUnit.MB);
                if (fileSize > 3) {
                    Toaster.show( "第" + (i + 1) + "张图片大小超过了3MB，请选择小于3MB的图片");
                    return;
                }
                localPathList.add(file);
            }
            compressAction(paramsMap, localPathList);
        }
    }

    private void compressAction(Map<String, String> params, List<File> localPathList) {
        if (localPathList == null) {
            return;
        }
        if (localPathList.isEmpty()) {
            addTradeGood(params, localPathList);
        } else {
            loading("图片压缩中...");
            Luban.compress(_mActivity, localPathList)
                    .putGear(Luban.THIRD_GEAR)
                    .setMaxSize(200)
                    .launch(new OnMultiCompressListener() {
                        @Override
                        public void onStart() {
                            Logs.e("compress start");
                        }

                        @Override
                        public void onSuccess(List<File> fileList) {
                            addTradeGood(params, fileList);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Logs.e("compress error");
                            e.printStackTrace();
                            Toaster.show( "图片压缩失败,请联系客服");
                        }
                    });
        }

    }

    private void addTradeGood(Map<String, String> params, List<File> localPathList) {
        if (params == null) {
            params = new TreeMap<>();
        }
        int apiType = 1;
//        params.put("api", "trade_goods_add");
        if (!TextUtils.isEmpty(gid)) {
            params.put("gid", gid);
//            params.put("api", "trade_goods_edit");
            apiType = 2;
        }
        if (d_picids != null && d_picids.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < d_picids.size(); i++) {
                sb.append(d_picids.get(i)).append("_");
            }
            params.put("del_pic_ids", sb.substring(0, sb.length() - 1));
        }

        Map<String, File> fileParams = new TreeMap<>();
        for (int i = 0; i < localPathList.size(); i++) {
            fileParams.put("upload_pic" + (i + 1), localPathList.get(i));
        }
        switch (apiType) {
            case 1:
                if (mViewModel != null) {
                    mViewModel.transactionSell(params, fileParams, new OnBaseCallback() {
                        @Override
                        public void onAfter() {
                            super.onAfter();
                            loadingComplete();
                        }

                        @Override
                        public void onBefore() {
                            super.onBefore();
                            loading("上传中...");
                        }

                        @Override
                        public void onSuccess(BaseVo data) {
                            if (data.isStateOK()) {
                                Toaster.show( "提交成功，将于1~2个工作日内完成审核。");
//                        if (transactionConfirmDialog != null && transactionConfirmDialog.isShowing()) {
//                            transactionConfirmDialog.dismiss();
//                        }
                                if (customPopWindow != null) {
                                    customPopWindow.dismiss();
                                }
                                setFragmentResult(RESULT_OK, null);
                                pop();
                            } else {
                                Toaster.show( data.getMsg());
                            }
                        }
                    });
                }
                break;
            case 2:
                //修改商品
                if (mViewModel != null) {
                    mViewModel.transactionEdit(params, fileParams, new OnBaseCallback() {
                        @Override
                        public void onAfter() {
                            super.onAfter();
                            loadingComplete();
                        }

                        @Override
                        public void onBefore() {
                            super.onBefore();
                            loading("上传中...");
                        }

                        @Override
                        public void onSuccess(BaseVo data) {
                            if (data.isStateOK()) {
                                Toaster.show( "提交成功，将于1~2个工作日内完成审核。");
                                if (transactionConfirmDialog != null && transactionConfirmDialog.isShowing()) {
                                    transactionConfirmDialog.dismiss();
                                }
                                setFragmentResult(RESULT_OK, null);
                                pop();
                            } else {
                                Toaster.show( data.getMsg());
                            }
                        }
                    });
                }
                break;

        }
    }


    private String targetGameid, targetGamename, targetGameicon, targetXh_name;
    private String targetGame_type;
    private String xh_client;
    private int xh_id = -1;

    private boolean isEditTradeGood() {
        return !(TextUtils.isEmpty(gid));
    }


    private void getTradeGoodInfo() {
        if (!isEditTradeGood()) {
            return;
        }
        //修改逻辑
        UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
        if (userInfoBean != null) {

            if (mViewModel != null) {
                mViewModel.getTradeGoodDetail(gid, "modify", new OnBaseCallback<TradeGoodDetailInfoVo>() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        showSuccess();
                    }

                    @Override
                    public void onFailure(String message) {
                        super.onFailure(message);
                        showErrorTag1();
                    }

                    @Override
                    public void onSuccess(TradeGoodDetailInfoVo data) {
                        showSuccess();
                        if (data != null) {
                            if (data.isStateOK()) {
                                setTransactionGoodInfo(data.getData());
                            } else {
                                Toaster.show( data.getMsg());
                            }
                        }
                    }
                });
            }
        }
    }

    private void setTransactionGoodInfo(TradeGoodDetailInfoVo.DataBean transactionGoodInfo) {
        if (transactionGoodInfo != null) {
            targetGameid = transactionGoodInfo.getGameid();
            targetXh_name = transactionGoodInfo.getXh_username();
            switch (transactionGoodInfo.getCan_bargain()) {
                case "1":
                    //不允许还价
                    allowedBargain = 1;
                    break;

                case "2":
                    //允许还价
                    allowedBargain = 2;
                    break;
            }
            autoPrice = transactionGoodInfo.getAuto_price();
            targetGame_type = transactionGoodInfo.getGame_type();
            mEtTransactionGameServer.setText(transactionGoodInfo.getServer_info());
            mEtTransactionGameServer.setSelection(mEtTransactionGameServer.getText().toString().length());

            if (!TextUtils.isEmpty(transactionGoodInfo.getGoods_price())){
                tv_set_price.setText(transactionGoodInfo.getGoods_price());
                tv_set_price.setTextColor(Color.parseColor("#FF0000"));
                tv_set_price.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tv_set_price_tips.setVisibility(View.VISIBLE);

                float goodPrice = Float.parseFloat(transactionGoodInfo.getGoods_price());
                float poundageCost = goodPrice * minRate;
                if (poundageCost < minCharge) {
                    poundageCost = minCharge;
                }
                float gotGoodPrice = (goodPrice - poundageCost);
                if (gotGoodPrice < 0) {
                    gotGoodPrice = 0;
                }

                tv_set_price_tips.setText("手续费5%(最低5元，售出可得" + CommonUtils.saveFloatPoint(gotGoodPrice, 2, BigDecimal.ROUND_UP) + "平台币)");
            }
            if (mEtTransactionPrice != null){
                mEtTransactionPrice.setText(transactionGoodInfo.getGoods_price());
                mEtTransactionPrice.setSelection(mEtTransactionPrice.getText().toString().length());
            }

            if ("3".equals(targetGame_type)) {
                xh_client = "3";
            } else {
                xh_client = "1";
            }

            mTvTransactionDescription.setText(transactionGoodInfo.getGoods_description());
            mTvTransactionSecondaryPassword.setText(transactionGoodInfo.getXh_passwd());

            List<ThumbnailBean> thumbnailBeanList = new ArrayList<>();

            if (transactionGoodInfo.getPic_list() != null) {
                for (TradeGoodDetailInfoVo.PicListBean picBean : transactionGoodInfo.getPic_list()) {
                    ThumbnailBean thumbnailBean = new ThumbnailBean();
                    thumbnailBean.setType(0);
                    thumbnailBean.setImageType(1);
                    thumbnailBean.setHttpUrl(picBean.getPic_path());
                    thumbnailBean.setPic_id(picBean.getPid());
                    thumbnailBeanList.add(thumbnailBean);
                }

                if (mThumbnailAdapter != null) {
                    mThumbnailAdapter.addAllFirst(thumbnailBeanList);
                    mThumbnailAdapter.notifyDataSetChanged();
                    mThumbnailAdapter.refreshThumbnails();
                }
            }
        }
    }

    CustomDialog customPopWindow;
    TextView tv_close;
    TextView tv_code;
    TextView tv_do;
    TextView iv_3;
    TextView iv_price;
    ImageView iv_check;
    LinearLayout layout_check;
    boolean isCheck = false;

    private void showPopSellNoticeView() {
        View contentView = LayoutInflater.from(_mActivity).inflate(R.layout.pop_transaction_sell_notice, null);
        tv_close = contentView.findViewById(R.id.tv_close);
        tv_code = contentView.findViewById(R.id.tv_code);
        tv_do = contentView.findViewById(R.id.tv_do);
        iv_3 = contentView.findViewById(R.id.iv_3);
        iv_price = contentView.findViewById(R.id.iv_price);
        iv_check = contentView.findViewById(R.id.iv_check);
        layout_check = contentView.findViewById(R.id.layout_check);
        mEtVerificationCode = (EditText) contentView.findViewById(R.id.et_new_price);

        isCheck = false;

        String string2 = "3、出售成功将收取5%手续费，最低5元；扣除手续费剩余收益将以平台币形式转至您的账号内，可充值平台任意游戏。暂不支持提现！";
        SpannableString ss = new SpannableString(string2);
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF6337")), 54, 61, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        iv_3.setText(ss);
        String price = mTvTransactionGotGold.getText().toString().replace("元", "");
        iv_price.setText(price);
        //处理popWindow 显示内容
        //创建并显示popWindow
        customPopWindow = new CustomDialog(_mActivity, contentView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        /*customPopWindow = new CustomPopWindow.PopupWindowBuilder(_mActivity)
                .enableOutsideTouchableDissmiss(true)
                .enableBackgroundDark(true)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                .create();
        customPopWindow.setBackgroundAlpha(0.5f);
        customPopWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);*/


        layout_check.setOnClickListener(v -> {
            if (!isCheck) {
                iv_check.setImageDrawable(getResources().getDrawable(R.mipmap.ic_login_checked));
                isCheck = true;
            } else {
                iv_check.setImageDrawable(getResources().getDrawable(R.mipmap.ic_login_un_check));
                isCheck = false;
            }
        });


        tv_code.setOnClickListener(v -> {
            if (isCheck) {
                getTradeCode();
            } else {
                Toaster.show("请阅读并勾选卖家须知!");
            }
        });

        tv_close.setOnClickListener(v -> {
            if (customPopWindow != null && customPopWindow.isShowing()) customPopWindow.dismiss();
        });

        tv_do.setOnClickListener(v -> {
            if (isCheck) {
                if (validateParameter()) {
                    confirmAddGood();
                }
            } else {
                Toaster.show("请阅读并勾选卖家须知!");
            }
        });
        customPopWindow.show();
    }

    private CustomDialog transactionConfirmDialog;
    private CheckBox mCbAgreement;
    private EditText mEtVerificationCode;
    private TextView mTvSendCode;
    private TextView mTvTransactionPrice;
    private Button mBtnCancel;
    private Button mBtnConfirm;

    private void showTransactionConfirmDialog() {
        if (transactionConfirmDialog == null) {
            transactionConfirmDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_confim_transaction, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

            mCbAgreement = (CheckBox) transactionConfirmDialog.findViewById(R.id.cb_agreement);
//            mEtVerificationCode = (EditText) transactionConfirmDialog.findViewById(R.id.et_verification_code);
            mTvSendCode = (TextView) transactionConfirmDialog.findViewById(R.id.tv_send_code);
            mTvTransactionPrice = (TextView) transactionConfirmDialog.findViewById(R.id.tv_transaction_price);
            mBtnCancel = (Button) transactionConfirmDialog.findViewById(R.id.btn_cancel);
            mBtnConfirm = (Button) transactionConfirmDialog.findViewById(R.id.btn_confirm);


            TextView mTvTips4 = transactionConfirmDialog.findViewById(R.id.tv_tips_4);
            mTvTips4.setText(Html.fromHtml(_mActivity.getResources().getString(R.string.string_transfer_confirm_tip_4)));

            GradientDrawable gd1 = new GradientDrawable();
            gd1.setCornerRadius(30 * density);
            gd1.setColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
            mTvSendCode.setBackground(gd1);

            GradientDrawable gd2 = new GradientDrawable();
            gd2.setCornerRadius(30 * density);
            gd2.setColor(ContextCompat.getColor(_mActivity, R.color.color_c1c1c1));
            mBtnCancel.setBackground(gd2);

            GradientDrawable gd3 = new GradientDrawable();
            gd3.setCornerRadius(30 * density);
            gd3.setColor(ContextCompat.getColor(_mActivity, R.color.color_c1c1c1));
            mBtnConfirm.setBackground(gd3);

            mBtnConfirm.setEnabled(false);
            mCbAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    GradientDrawable gd = new GradientDrawable();
                    gd.setCornerRadius(30 * density);
                    if (b) {
                        gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
                    } else {
                        gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_c1c1c1));
                    }
                    mBtnConfirm.setBackground(gd);
                    mBtnConfirm.setEnabled(b);
                }
            });

            mBtnCancel.setOnClickListener(view -> {
                if (transactionConfirmDialog != null && transactionConfirmDialog.isShowing()) {
                    transactionConfirmDialog.dismiss();
                }
            });

            transactionConfirmDialog.setOnDismissListener(dialogInterface -> {
                mEtVerificationCode.getText().clear();
            });

            mTvSendCode.setOnClickListener(this);
            mBtnConfirm.setOnClickListener(this);
        }

        String strDialogGoodGotPrice =  mTvTransactionGotGold.getText().toString() + "平台币";
        mTvTransactionPrice.setText(strDialogGoodGotPrice);

        transactionConfirmDialog.show();
    }


    private CustomDialog transactionTipDialog;

    private Button mBtnGotIt;
    private ImageView mIvImage;
    private CheckBox mCbButton;

    private void showTransactionTipDialog() {
        if (transactionTipDialog == null) {
            transactionTipDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_transaction_count_down_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            transactionTipDialog.setCancelable(false);
            transactionTipDialog.setCanceledOnTouchOutside(false);

            mBtnGotIt = (Button) transactionTipDialog.findViewById(R.id.btn_got_it);
            mIvImage = (ImageView) transactionTipDialog.findViewById(R.id.iv_image);
            mCbButton = (CheckBox) transactionTipDialog.findViewById(R.id.cb_button);

            mCbButton.setText("我已阅读交易细则");
            mIvImage.setImageResource(R.mipmap.img_transaction_tips_sell);

            GradientDrawable gd2 = new GradientDrawable();
            gd2.setCornerRadius(30 * density);
            gd2.setColor(Color.parseColor("#C1C1C1"));
            mBtnGotIt.setBackground(gd2);
            mBtnGotIt.setEnabled(false);
            mBtnGotIt.setOnClickListener(view -> {
                if (transactionTipDialog != null && transactionTipDialog.isShowing()) {
                    transactionTipDialog.dismiss();
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
        transactionTipDialog.show();
    }


    private List<String> d_picids;

    class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailHolder> {

        private List<ThumbnailBean> thumbnailBeanList;
        private Activity mActivity;
        private int maxCount;

        public ThumbnailAdapter(Activity mActivity, List<ThumbnailBean> thumbnailBeanList, int maxCount) {
            this.thumbnailBeanList = thumbnailBeanList;
            this.mActivity = mActivity;
            this.maxCount = maxCount;
        }

        public void addAllFirst(List<ThumbnailBean> data) {
            thumbnailBeanList.addAll(0, data);
        }

        public void add(ThumbnailBean data) {
            thumbnailBeanList.add(data);
        }

        public boolean isContainsAdd() {
            for (ThumbnailBean thumbnailBean : thumbnailBeanList) {
                if (thumbnailBean.getType() == 1) {
                    return true;
                }
            }
            return false;
        }

        public void deleteItem(int position) {
            thumbnailBeanList.remove(position);
        }

        public List<ThumbnailBean> getDatas() {
            return thumbnailBeanList;
        }

        @Override
        public ThumbnailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_pic_thumbnail, null);
            return new ThumbnailHolder(view);
        }

        @Override
        public void onBindViewHolder(ThumbnailHolder holder, int position) {
            setData(holder, thumbnailBeanList.get(position), position);
        }

        @Override
        public int getItemCount() {
            return thumbnailBeanList == null ? 0 : thumbnailBeanList.size();
        }

        public void setData(ThumbnailHolder holder, ThumbnailBean thumbnailBean, int position) {
            if (thumbnailBean.getType() == 1) {
                //加号键
                holder.mIvDelete.setVisibility(View.GONE);
                holder.mIvThumbnail.setImageResource(R.mipmap.ic_audit_transaction_sell_8);
            } else {
                if (thumbnailBean.getImageType() == 0) {
                    GlideUtils.loadLocalImage(_mActivity, thumbnailBean.getLocalUrl(), holder.mIvThumbnail);
                } else {
                    GlideUtils.loadNormalImage(_mActivity, thumbnailBean.getHttpUrl(), holder.mIvThumbnail);
                }
                holder.mIvDelete.setVisibility(View.VISIBLE);
            }
            holder.mIvThumbnail.setOnClickListener(v -> {
                if (thumbnailBean.getType() == 1) {
                    //添加图片
                    //多选(最多6张)
                    if ((getItemCount() - 1) < maxCount) {
                        int selectMaxCount = maxCount - (getItemCount() - 1);
                        ImageSelectorUtils.openPhoto(mActivity, REQUEST_CODE, false, selectMaxCount);
                    } else {
                        Toaster.show( "亲，最多只能选取" + maxCount + "张图片哦~");
                    }
                } else {
                    //预览图片
                    ArrayList<Image> images = new ArrayList();
                    List<ThumbnailBean> thumbnailBeanList = getDatas();
                    for (ThumbnailBean currentThumbnailBean : thumbnailBeanList) {
                        if (currentThumbnailBean.getType() == 1) {
                            continue;
                        }
                        Image image = new Image();
                        if (currentThumbnailBean.getImageType() == 0) {
                            //本地图片
                            image.setType(0);
                            image.setPath(currentThumbnailBean.getLocalUrl());
                        } else if (currentThumbnailBean.getImageType() == 1) {
                            //网络图片
                            image.setType(1);
                            image.setPath(currentThumbnailBean.getHttpUrl());
                        }

                        images.add(image);
                    }
                    PreviewActivity.openActivity(mActivity, images, true, position, true);
                }
            });
            holder.mIvDelete.setOnClickListener(v -> {
                if (thumbnailBean != null) {
                    deleteThumbnailItem(position);
                    refreshThumbnails();

                    if (thumbnailBean.getImageType() == 1) {
                        if (d_picids == null) {
                            d_picids = new ArrayList<>();
                        }
                        d_picids.add(thumbnailBean.getPic_id());
                    }
                }
            });
        }

        public void refreshThumbnails() {
            if (getItemCount() >= (maxPicCount + 1)) {
                //去掉加号键
                deleteThumbnailItem(getItemCount() - 1);
            } else {
                if (!isContainsAdd()) {
                    //加上加号键
                    ThumbnailBean thumbnailBean = new ThumbnailBean();
                    thumbnailBean.setType(1);
                    add(thumbnailBean);
                    notifyDataSetChanged();
                }
            }
        }

        private void deleteThumbnailItem(int position) {
            deleteItem(position);
            notifyDataSetChanged();
        }
    }

    class ThumbnailHolder extends RecyclerView.ViewHolder {
        public ImageView mIvThumbnail;
        public ImageView mIvDelete;

        public ThumbnailHolder(View itemView) {
            super(itemView);
            mIvThumbnail = (ImageView) itemView.findViewById(R.id.iv_thumbnail);
            mIvDelete = (ImageView) itemView.findViewById(R.id.iv_delete);

//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int) (96 * density), (int) (96 * density));
//            int margin = (int) (10 * density);
//            params.setMargins(margin, margin, margin, margin);
//            mIvThumbnail.setLayoutParams(params);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, state);
    }

    @Override
    public void onDestroyView() {
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void OnEvent(PhotoEvent photoEvent) {
        List<ThumbnailBean> thumbnailBeanList = new ArrayList<>();
        for (String image : photoEvent.getImages()) {
            ThumbnailBean thumbnailBean = new ThumbnailBean();
            thumbnailBean.setType(0);
            thumbnailBean.setImageType(0);
            thumbnailBean.setLocalUrl(image);

            thumbnailBeanList.add(thumbnailBean);
        }

        if (mThumbnailAdapter != null) {
            mThumbnailAdapter.addAllFirst(thumbnailBeanList);
            mThumbnailAdapter.notifyDataSetChanged();

            mThumbnailAdapter.refreshThumbnails();
        }
    }

    private CustomDialog setPriceDialog;
    private EditText mEtTransactionPrice;
    private TextView mTvTransactionGotGold;
    private ImageView iv_counter_offer;
    private ImageView iv_counter_offer_tips;
    private ImageView iv_price_reduction;
    private ImageView iv_price_reduction_tips;
    private void setPriceDialog() {
        if (setPriceDialog == null) {
            setPriceDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_transaction_set_price, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
            setPriceDialog.setCancelable(true);
            setPriceDialog.setCanceledOnTouchOutside(true);

            mEtTransactionPrice = setPriceDialog.findViewById(R.id.et_transaction_price);
            mTvTransactionGotGold = setPriceDialog.findViewById(R.id.tv_transaction_got_gold);
            iv_counter_offer = setPriceDialog.findViewById(R.id.iv_counter_offer);
            iv_counter_offer_tips = setPriceDialog.findViewById(R.id.iv_counter_offer_tips);
            iv_price_reduction = setPriceDialog.findViewById(R.id.iv_price_reduction);
            iv_price_reduction_tips = setPriceDialog.findViewById(R.id.iv_price_reduction_tips);
            TextView mTvPriceTips = setPriceDialog.findViewById(R.id.tv_price_tips);

            if (allowedBargain == 2){
                iv_counter_offer.setImageResource(R.mipmap.ic_login_checked);
            }else {
                iv_counter_offer.setImageResource(R.mipmap.ic_login_un_check);
            }

            iv_price_reduction.setImageResource(R.mipmap.ic_login_un_check);
            mTvPriceTips.setText("每次设置价格默认关闭自动降价，需要请手动开启");
            mTvPriceTips.setTextColor(Color.parseColor("#9B9B9B"));

            mEtTransactionPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String strGoodPrice = mEtTransactionPrice.getText().toString().trim();
                    if (TextUtils.isEmpty(strGoodPrice)) {
                        mTvTransactionGotGold.setText("-平台币");
                        tv_set_price.setText("设置价格");
                        tv_set_price.setTextColor(Color.parseColor("#9B9B9B"));
                        tv_set_price.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        tv_set_price_tips.setVisibility(View.GONE);
                        /*if (isVip) {
                            tv_layout_vip.setText("最低3元手续费");
                        }*/
                        autoPrice = 1;
                        iv_price_reduction.setImageResource(R.mipmap.ic_login_un_check);
                        mTvPriceTips.setText("每次设置价格默认关闭自动降价，需要请手动开启");
                        mTvPriceTips.setTextColor(Color.parseColor("#9B9B9B"));
                        return;
                    }
                    int goodPrice = Integer.parseInt(strGoodPrice);
                    /*if (isVip) {
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
                        //tv_layout_vip.setText("减免" + CommonUtils.saveFloatPoint((v2 - v1), 2, BigDecimal.ROUND_DOWN) + "元手续费");
                        mTvTransactionGotGold.setText(String.valueOf(CommonUtils.saveFloatPoint(gotGoodPrice, 2, BigDecimal.ROUND_DOWN)) + "元");
                    } else {*/
                        float poundageCost = goodPrice * minRate;
                        if (poundageCost < minCharge) {
                            poundageCost = minCharge;
                        }
                        float gotGoodPrice = (goodPrice - poundageCost);
                        if (gotGoodPrice < 0) {
                            gotGoodPrice = 0;
                        }
                        mTvTransactionGotGold.setText(CommonUtils.saveFloatPoint(gotGoodPrice, 2, BigDecimal.ROUND_UP) + "平台币");
                        tv_set_price.setText(String.valueOf(CommonUtils.saveFloatPoint(goodPrice, 2, BigDecimal.ROUND_UP)));
                        tv_set_price.setTextColor(Color.parseColor("#FF0000"));
                        tv_set_price.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        tv_set_price_tips.setVisibility(View.VISIBLE);
                        tv_set_price_tips.setText("手续费5%(最低5元，售出可得" + CommonUtils.saveFloatPoint(gotGoodPrice, 2, BigDecimal.ROUND_UP) + "平台币)");

                        if (autoPrice == 2){
                            if (goodPrice > 6){
                                double doublePrice = goodPrice;
                                int amplitude = (int) (doublePrice / 10 + 0.9);

                                int after = goodPrice - amplitude * 5;
                                if (after <= 6){
                                    mTvPriceTips.setText("每12小时降:" + amplitude + "元（最低可降至:6元）");
                                }else {
                                    mTvPriceTips.setText("每12小时降:" + amplitude + "元（最低可降至:" + after + "元）");
                                }
                                mTvPriceTips.setTextColor(Color.parseColor("#FF3333"));
                            }else {
                                autoPrice = 1;
                                iv_price_reduction.setImageResource(R.mipmap.ic_login_un_check);
                                mTvPriceTips.setText("每次设置价格默认关闭自动降价，需要请手动开启");
                                mTvPriceTips.setTextColor(Color.parseColor("#9B9B9B"));
                            }
                        }
                    //}
                }
            });

            iv_counter_offer.setOnClickListener(v -> {
                if (allowedBargain == 2){
                    //不允许还价
                    allowedBargain = 1;
                }else {
                    //允许还价
                    allowedBargain = 2;
                }
                if (allowedBargain == 2){
                    iv_counter_offer.setImageResource(R.mipmap.ic_login_checked);
                }else {
                    iv_counter_offer.setImageResource(R.mipmap.ic_login_un_check);
                }
            });
            iv_counter_offer_tips.setOnClickListener(v -> {
                showCounterOfferTips();
            });
            iv_price_reduction.setOnClickListener(v -> {
                String trim = mEtTransactionPrice.getText().toString().trim();
                if (!TextUtils.isEmpty(trim)){
                    int price = Integer.parseInt(trim);
                    if (price <= 6){
                        Toaster.show("已设置最低价，无法使用自动降价功能");
                        autoPrice = 1;
                        iv_price_reduction.setImageResource(R.mipmap.ic_login_un_check);
                        mTvPriceTips.setText("每次设置价格默认关闭自动降价，需要请手动开启");
                        mTvPriceTips.setTextColor(Color.parseColor("#9B9B9B"));
                        return;
                    }
                }else {
                    return;
                }
                if (autoPrice == 2){
                    //自动降价
                    autoPrice = 1;
                }else {
                    //不自动降价
                    autoPrice = 2;
                }
                if (autoPrice == 2){
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
            iv_price_reduction_tips.setOnClickListener(v -> {
                showPriceReduction();
            });

            setPriceDialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
                if (setPriceDialog != null && setPriceDialog.isShowing()) setPriceDialog.dismiss();
            });
        }
        setPriceDialog.show();
    }

    private PopupWindow counterOfferPopupWindow;
    private void showCounterOfferTips() {
        if (counterOfferPopupWindow == null){
            View contentView = LayoutInflater.from(_mActivity).inflate(R.layout.pop_transaction_counter_offer, null);
            //处理popWindow 显示内容
            //创建并显示popWindow
            counterOfferPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        }
        counterOfferPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        counterOfferPopupWindow.setFocusable(false);
        counterOfferPopupWindow.setOutsideTouchable(true);
        counterOfferPopupWindow.showAsDropDown(iv_counter_offer_tips,0, ScreenUtil.dp2px(_mActivity, -90));
    }

    private PopupWindow priceReductionPopupWindow;
    private void showPriceReduction() {
        if (priceReductionPopupWindow == null){
            View contentView = LayoutInflater.from(_mActivity).inflate(R.layout.pop_transaction_price_reduction, null);
            //处理popWindow 显示内容
            //创建并显示popWindow
            priceReductionPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        }
        priceReductionPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        priceReductionPopupWindow.setFocusable(false);
        priceReductionPopupWindow.setOutsideTouchable(true);
        priceReductionPopupWindow.showAsDropDown(iv_price_reduction_tips,0, ScreenUtil.dp2px(_mActivity, -100));
    }
}
