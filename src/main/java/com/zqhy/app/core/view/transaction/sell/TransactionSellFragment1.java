package com.zqhy.app.core.view.transaction.sell;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.chaoji.other.hjq.toast.Toaster;
import com.donkingliang.imageselector.PreviewActivity;
import com.donkingliang.imageselector.entry.Image;
import com.donkingliang.imageselector.event.PhotoEvent;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.ThumbnailBean;
import com.zqhy.app.core.data.model.transaction.TradeGoodDetailInfoVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.utilcode.ConstUtils;
import com.zqhy.app.core.tool.utilcode.FileUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.BlankTxtFragment;
import com.zqhy.app.core.view.user.BindPhoneFragment;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.CountDownTimerCopyFromAPI26;

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

/***2022 6 16弃用
 * @author Administrator
 */
public class TransactionSellFragment1 extends BaseFragment<TransactionViewModel> implements View.OnClickListener {

    public static final int REQUEST_CODE = 0x00000011;

    public static TransactionSellFragment1 newInstance() {
        return newInstance("");
    }

    public static TransactionSellFragment1 newInstance(String gid) {
        TransactionSellFragment1 fragment = new TransactionSellFragment1();
        Bundle bundle = new Bundle();
        bundle.putString("gid", gid);
        fragment.setArguments(bundle);
        return fragment;
    }

    public TransactionSellFragment1() {
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
        return R.layout.fragment_transaction_sell1;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private String gid;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gid = getArguments().getString("gid");
        }
        super.initView(state);
        initActionBackBarAndTitle("我要卖号");
        bindViews();

        if (TextUtils.isEmpty(gid)) {
            showSuccess();
            showTransactionTipDialog();
        } else {
            getTradeGoodInfo();
        }
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getTradeGoodInfo();
    }

    private LinearLayout mLlContentLayout;
    private LinearLayout mLlAddGame;
    private TextView mTvTransactionGamename;
    private ImageView mIvArrowGame;
    private LinearLayout mLlAddXhName;
    private TextView mTvTransactionXhName;
    private ImageView mIvArrowXhName;
    private EditText mEtTransactionGameServer;
    private EditText mEtTransactionPrice;
    private TextView mTvTransactionGotGold;
    private LinearLayout mLlWriteTitle;
    private TextView mTvTransactionTitle;
    private LinearLayout mLlWriteDescription;
    private TextView mTvTransactionDescription;
    private LinearLayout mLlWriteSecondaryPassword;
    private TextView mTvTransactionSecondaryPassword;
    private RecyclerView mRecyclerViewThumbnail;
    private Button mBtnConfirmSell;

    private void bindViews() {
        mLlContentLayout = findViewById(R.id.ll_content_layout);
        mLlAddGame = findViewById(R.id.ll_add_game);
        mTvTransactionGamename = findViewById(R.id.tv_transaction_gamename);
        mIvArrowGame = findViewById(R.id.iv_arrow_game);
        mLlAddXhName = findViewById(R.id.ll_add_xh_name);
        mTvTransactionXhName = findViewById(R.id.tv_transaction_xh_name);
        mIvArrowXhName = findViewById(R.id.iv_arrow_xh_name);
        mEtTransactionGameServer = findViewById(R.id.et_transaction_game_server);
        mEtTransactionPrice = findViewById(R.id.et_transaction_price);
        mTvTransactionGotGold = findViewById(R.id.tv_transaction_got_gold);
        mLlWriteTitle = findViewById(R.id.ll_write_title);
        mTvTransactionTitle = findViewById(R.id.tv_transaction_title);
        mLlWriteDescription = findViewById(R.id.ll_write_description);
        mTvTransactionDescription = findViewById(R.id.tv_transaction_description);
        mLlWriteSecondaryPassword = findViewById(R.id.ll_write_secondary_password);
        mTvTransactionSecondaryPassword = findViewById(R.id.tv_transaction_secondary_password);
        mRecyclerViewThumbnail = findViewById(R.id.recyclerView_thumbnail);
        mBtnConfirmSell = findViewById(R.id.btn_confirm_sell);

        mIvArrowGame.setVisibility(View.VISIBLE);
        mIvArrowXhName.setVisibility(View.VISIBLE);

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#FF8F19"));
        gd.setCornerRadius(40 * density);
        mBtnConfirmSell.setBackground(gd);

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
                    mTvTransactionGotGold.setText("0.00元");
                    return;
                }
                int goodPrice = Integer.parseInt(strGoodPrice);
                float poundageCost = goodPrice * 0.05f;
                if (poundageCost < 5.00f) {
                    poundageCost = 5.00f;
                }
                float gotGoodPrice = (goodPrice - poundageCost);
                if (gotGoodPrice < 0) {
                    gotGoodPrice = 0;
                }
                mTvTransactionGotGold.setText(String.valueOf(CommonUtils.saveFloatPoint(gotGoodPrice, 2, BigDecimal.ROUND_DOWN)) + "元");
            }
        });

        initList();
        setListeners();
    }

    private int maxPicCount = 9;

    private ThumbnailAdapter mThumbnailAdapter;

    private void initList() {
        GridLayoutManager layoutManager = new GridLayoutManager(_mActivity, 3);
        layoutManager.setSmoothScrollbarEnabled(true);
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
        mLlAddGame.setOnClickListener(this);
        mLlWriteTitle.setOnClickListener(this);
        mLlWriteDescription.setOnClickListener(this);
        mLlWriteSecondaryPassword.setOnClickListener(this);

        mBtnConfirmSell.setOnClickListener(this);
        mLlAddXhName.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_game:
                if (isEditTradeGood()) {
                    Toaster.show("游戏名无法修改");
                } else {
                    startForResult(TransactionChooseGameFragment.newInstance(targetGameid, xh_id), action_choose_game);
                }
                break;
            case R.id.ll_add_xh_name:
                if (isEditTradeGood()) {
                    Toaster.show("小号无法修改");
                } else {
                    if (TextUtils.isEmpty(targetGameid)) {
                        startForResult(TransactionChooseGameFragment.newInstance(targetGameid, xh_id), action_choose_game);
                    } else {
                        startForResult(TransactionChooseXhFragment.newInstance(targetGameid,
                                targetGamename,
                                targetGameicon,
                                xh_id), action_choose_game_xh);
                    }
                }
                break;
            case R.id.ll_write_title:
                String titleTxt = mTvTransactionTitle.getText().toString().trim();
                if (titleTxt == null) {
                    titleTxt = "";
                }
                startForResult(BlankTxtFragment.newInstance("标题", "说说账号亮点，6-20字", titleTxt, 6, 20), action_write_title);
                break;
            case R.id.ll_write_description:
                String descriptionTxt = mTvTransactionDescription.getText().toString().trim();
                if (descriptionTxt == null) {
                    descriptionTxt = "";
                }
                startForResult(BlankTxtFragment.newInstance("商品描述", "可以按照角色等级、装备、道具等，10到100字以内，填写描述，可以更快速的完成交易喔！", descriptionTxt, 10, 100, true), action_write_description);
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
                                showTransactionConfirmDialog();
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
                getTradeCode();
                break;
            case R.id.btn_confirm:
                //确认出售2
                if (validateParameter()) {
                    confirmAddGood();
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case action_choose_game:
                    if (data != null) {
                        targetGameid = data.getString("gameid");
                        targetGamename = data.getString("gamename");
                        targetGameicon = data.getString("gameicon");

                        targetXh_name = data.getString("xh_name");
                        String targetXh_nickname = data.getString("xh_nickname");

                        xh_id = data.getInt("xh_id", -1);

                        targetGame_type = data.getString("game_type");

                        mTvTransactionGamename.setText(targetGamename);
                        mTvTransactionXhName.setText(targetXh_nickname);

                        StringBuilder sb = new StringBuilder();

                        sb.append("targetGameid = ").append(targetGameid).append("\n");
                        sb.append("targetGamename = ").append(targetGamename).append("\n");
                        sb.append("targetGameicon = ").append(targetGameicon).append("\n");
                        sb.append("targetXh_name = ").append(targetXh_name).append("\n");
                        sb.append("targetXh_nickname = ").append(targetXh_nickname).append("\n");
                        sb.append("xh_id = ").append(xh_id);

                        Logs.e(sb.toString());

                        if ("3".equals(targetGame_type)) {
                            xh_client = "3";
                        } else {
                            xh_client = "1";
                        }
                    }
                    break;
                case action_choose_game_xh:
                    targetXh_name = data.getString("xh_name");

                    String targetXh_nickname = data.getString("xh_nickname");
                    mTvTransactionXhName.setText(targetXh_nickname);

                    xh_id = data.getInt("xh_id", -1);
                    break;
                case action_write_title:
                    if (data != null) {
                        String strData = data.getString("data");
                        mTvTransactionTitle.setText(strData);
                    }
                    break;
                case action_write_description:
                    if (data != null) {
                        String strData = data.getString("data");
                        mTvTransactionDescription.setText(strData);
                    }
                    break;
                case action_write_secondary_password:
                    if (data != null) {
                        String strData = data.getString("data");
                        mTvTransactionSecondaryPassword.setText(strData);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

    private void sendCode() {
        GradientDrawable gd1 = new GradientDrawable();
        gd1.setCornerRadius(30 * density);
        gd1.setColor(Color.parseColor("#C1C1C1"));
        mTvSendCode.setBackground(gd1);

        new CountDownTimerCopyFromAPI26(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvSendCode.setEnabled(false);
                mTvSendCode.setText("重新发送" + (millisUntilFinished / 1000) + "秒");
            }

            @Override
            public void onFinish() {
                GradientDrawable gd1 = new GradientDrawable();
                gd1.setCornerRadius(30 * density);
                gd1.setColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
                mTvSendCode.setBackground(gd1);
                mTvSendCode.setText("发送验证码");
                mTvSendCode.setEnabled(true);
            }
        }.start();
    }

    private boolean validateParameter() {
        if (TextUtils.isEmpty(targetGameid)) {
            Toaster.show( mTvTransactionGamename.getHint());
            return false;
        }
        if (TextUtils.isEmpty(targetXh_name)) {
            Toaster.show( mTvTransactionXhName.getHint());
            return false;
        }

        String strSeverInfo = mEtTransactionGameServer.getText().toString().trim();
        if (TextUtils.isEmpty(strSeverInfo)) {
            Toaster.show( mEtTransactionGameServer.getHint());
            return false;
        }

        if ("0".equals(strSeverInfo)) {
            Toaster.show( "请输入正确的区服信息");
            return false;
        }


        String strGoodPrice = mEtTransactionPrice.getText().toString().trim();
        if (TextUtils.isEmpty(strGoodPrice)) {
            Toaster.show( mEtTransactionPrice.getHint());
            return false;
        }

        int goodPrice = Integer.parseInt(strGoodPrice);
        if (goodPrice < 6) {
            Toaster.show( "出售价不低于6元");
            return false;
        }

        if (TextUtils.isEmpty(xh_client)) {
            Toaster.show( "请选择客户端");
            return false;
        }

        String srtGoodTitle = mTvTransactionTitle.getText().toString().trim();
        if (TextUtils.isEmpty(srtGoodTitle)) {
            Toaster.show( "请设置标题");
            return false;
        }

        String srtGoodDescription = mTvTransactionDescription.getText().toString().trim();

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
        String strGoodPrice = mEtTransactionPrice.getText().toString().trim();
        String srtGoodTitle = mTvTransactionTitle.getText().toString().trim();
        String srtGoodDescription = mTvTransactionDescription.getText().toString().trim();

        Map<String, String> paramsMap = new TreeMap<>();

        paramsMap.put("gameid", targetGameid);
        paramsMap.put("xh_username", targetXh_name);
        paramsMap.put("server_info", strSeverInfo);
        paramsMap.put("gameid", targetGameid);

        paramsMap.put("xh_client", xh_client);
        paramsMap.put("goods_price", strGoodPrice);
        paramsMap.put("goods_title", srtGoodTitle);
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
                //新增商品
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
        mIvArrowGame.setVisibility(View.GONE);
        mIvArrowXhName.setVisibility(View.GONE);

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

            targetGame_type = transactionGoodInfo.getGame_type();

            mTvTransactionGamename.setText(transactionGoodInfo.getGamename());

            String xh_username = "";
            if (transactionGoodInfo.getXh_username()!=null&&transactionGoodInfo.getXh_username().length()>=8){
                xh_username = transactionGoodInfo.getXh_username().substring(transactionGoodInfo.getXh_username().length() - 8);
            }else {
                xh_username = transactionGoodInfo.getXh_username();
            }
            mTvTransactionXhName.setText(xh_username);

            mEtTransactionGameServer.setText(transactionGoodInfo.getServer_info());
            mEtTransactionGameServer.setSelection(mEtTransactionGameServer.getText().toString().length());

            mEtTransactionPrice.setText(transactionGoodInfo.getGoods_price());
            mEtTransactionPrice.setSelection(mEtTransactionPrice.getText().toString().length());

            if ("3".equals(targetGame_type)) {
                xh_client = "3";
            } else {
                xh_client = "1";
            }


            mTvTransactionTitle.setText(transactionGoodInfo.getGoods_title());
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
            mEtVerificationCode = (EditText) transactionConfirmDialog.findViewById(R.id.et_verification_code);
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

        String strDialogGoodGotPrice = "本次出售可得" + mTvTransactionGotGold.getText().toString();
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
                holder.mIvThumbnail.setImageResource(R.mipmap.ic_comment_add_pic);
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

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int) (96 * density), (int) (96 * density));
            int margin = (int) (10 * density);
            params.setMargins(margin, margin, margin, margin);
            mIvThumbnail.setLayoutParams(params);
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
}
