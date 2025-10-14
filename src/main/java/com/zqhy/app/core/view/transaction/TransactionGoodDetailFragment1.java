package com.zqhy.app.core.view.transaction;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.box.other.hjq.toast.Toaster;
import com.donkingliang.imageselector.entry.Image;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.transaction.TradeGoodDetailInfoVo;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoListVo;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.StringUtil;
import com.zqhy.app.core.view.transaction.base.TransactionGoodItemActionHelper;
import com.zqhy.app.core.view.transaction.buy.TransactionBuyFragment;
import com.zqhy.app.core.view.transaction.holder.TradeItemHolder;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**2022 6 16弃用
 * @author Administrator
 */
public class TransactionGoodDetailFragment1 extends BaseFragment<TransactionViewModel> implements View.OnClickListener {

    public static TransactionGoodDetailFragment1 newInstance(String goodid, String gameid, String good_pic) {
        TransactionGoodDetailFragment1 fragment = new TransactionGoodDetailFragment1();
        Bundle bundle = new Bundle();
        bundle.putString("goodid", goodid);
        bundle.putString("gameid", gameid);
        bundle.putString("good_pic", good_pic);
        fragment.setArguments(bundle);
        return fragment;
    }

    private final int action_buy = 0x7451;
    private final int action_modify_good = 0x754;

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "交易详情页";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transaction_good_detail1;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private String goodid, gameid, gamename;
    private String good_pic;

    private boolean isAnyDataChange = false;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            goodid = getArguments().getString("goodid");
            gameid = getArguments().getString("gameid");
            good_pic = getArguments().getString("good_pic");
        }
        super.initView(state);
        initActionBackBarAndTitle("商品详情");
        showSuccess();
        bindViews();
        initData();
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout mLlRootview;
    private ImageView mIvGameImage;
    private TextView mTvGameName;
    private TextView mTvGameSize;
    private View mViewMidLine;
    private TextView mTvGameType;
    private TextView mBtnGameDetail;
    private LinearLayout mLlGoodShelves;
    private TextView mTvTradingOrTraded;
    private TextView mTvOnlineTime;
    private TextView mTvGoodUnShelves;
    private TextView mTvXhAccount;
    private TextView mTvServerName;
    private TextView mTvGoodPrice;
    private TextView mTvGoodValue;
    private TextView mTvGoodTag;
    private TextView mTvGoodTitle;
    private TextView mTvGoodDescription;
    private LinearLayout mLlSecondaryPassword;
    private TextView mTvSecondaryPassword;
    private LinearLayout mLlGoodScreenshotList;
    private LinearLayout mLlRelatedGoodList;
    private TextView mTvGameName2;
    private LinearLayout mLlMoreGame;
    private RecyclerView mMoreRecycler;
    private FrameLayout mFlGoodBottomView;
    private LinearLayout mFlBtnBuyGood;
    private Button mBtnBuyGood;
    private FrameLayout mFlGoodStatus;
    private TextView mTvGoodStatus;
    private TextView mTvBtnAction1;
    private TextView mTvBtnAction2;
    private FrameLayout mFlGoodFailReason;
    private TextView mTvGoodFailReason;

    private Button btn_dicker_good;
    private TextView tv_collection;
    private TextView tv_kefu;
    private TextView tv_platform;

    private void bindViews() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mLlRootview = findViewById(R.id.ll_rootview);
        mIvGameImage = findViewById(R.id.iv_game_image);
        mTvGameName = findViewById(R.id.tv_game_name);
        mTvGameSize = findViewById(R.id.tv_game_size);
        mViewMidLine = findViewById(R.id.view_mid_line);
        mTvGameType = findViewById(R.id.tv_game_type);
        mBtnGameDetail = findViewById(R.id.btn_game_detail);
        mLlGoodShelves = findViewById(R.id.ll_good_shelves);
        mTvTradingOrTraded = findViewById(R.id.tv_trading_or_traded);
        mTvOnlineTime = findViewById(R.id.tv_online_time);
        mTvGoodUnShelves = findViewById(R.id.tv_good_un_shelves);
        mTvXhAccount = findViewById(R.id.tv_xh_account);
        mTvServerName = findViewById(R.id.tv_server_name);
        mTvGoodPrice = findViewById(R.id.tv_good_price);
        mTvGoodValue = findViewById(R.id.tv_good_value);
        mTvGoodTag = findViewById(R.id.tv_good_tag);
        mTvGoodTitle = findViewById(R.id.tv_good_title);
        mTvGoodDescription = findViewById(R.id.tv_good_description);
        mLlSecondaryPassword = findViewById(R.id.ll_secondary_password);
        mTvSecondaryPassword = findViewById(R.id.tv_secondary_password);
        mLlGoodScreenshotList = findViewById(R.id.ll_good_screenshot_list);
        mLlRelatedGoodList = findViewById(R.id.ll_related_good_list);
        mTvGameName2 = findViewById(R.id.tv_game_name_2);
        mLlMoreGame = findViewById(R.id.ll_more_game);
        mMoreRecycler = findViewById(R.id.more_recycler);
        mFlGoodBottomView = findViewById(R.id.fl_good_bottom_view);
        mFlBtnBuyGood = findViewById(R.id.fl_btn_buy_good);
        mBtnBuyGood = findViewById(R.id.btn_buy_good);
        mFlGoodStatus = findViewById(R.id.fl_good_status);
        mTvGoodStatus = findViewById(R.id.tv_good_status);
        mTvBtnAction1 = findViewById(R.id.tv_btn_action_1);
        mTvBtnAction2 = findViewById(R.id.tv_btn_action_2);
        mFlGoodFailReason = findViewById(R.id.fl_good_fail_reason);
        mTvGoodFailReason = findViewById(R.id.tv_good_fail_reason);

        btn_dicker_good = findViewById(R.id.btn_dicker_good);
        tv_collection = findViewById(R.id.tv_collection);
        tv_kefu = findViewById(R.id.tv_kefu);
        tv_platform = findViewById(R.id.tv_platform);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_ff8f19,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //设置样式刷新显示的位置
        mSwipeRefreshLayout.setProgressViewOffset(true, -20, 100);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            initData();
        });

        initList();

        setListeners();
        setLayoutViews();
    }

    BaseRecyclerAdapter mMoreAdapter;

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        layoutManager.setSmoothScrollbarEnabled(true);
        mMoreRecycler.setLayoutManager(layoutManager);
        mMoreRecycler.setNestedScrollingEnabled(false);

        mMoreAdapter = new BaseRecyclerAdapter.Builder()
                .bind(TradeGoodInfoVo.class, new TradeItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
        mMoreRecycler.setAdapter(mMoreAdapter);

        mMoreAdapter.setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof TradeGoodInfoVo) {
                TradeGoodInfoVo tradeGoodInfoBean = (TradeGoodInfoVo) data;
                start(TransactionGoodDetailFragment1.newInstance(tradeGoodInfoBean.getGid(), tradeGoodInfoBean.getGameid(), tradeGoodInfoBean.getGoods_pic()));
            }
        });

    }

    private void initData() {
        getGoodDetailData();
    }

    private void setLayoutViews() {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(54 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.white));
        gd.setStroke((int) (0.8 * density), ContextCompat.getColor(_mActivity, R.color.color_cccccc));
        mBtnGameDetail.setBackground(gd);
        mBtnGameDetail.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));


        GradientDrawable gd2 = new GradientDrawable();
        gd2.setCornerRadius(25 * density);
        gd2.setColor(ContextCompat.getColor(_mActivity, R.color.white));
        gd2.setStroke((int) (0.8 * density), ContextCompat.getColor(_mActivity, R.color.color_007aff));
        mTvGoodTag.setBackground(gd2);
        mTvGoodTag.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_007aff));
        mTvGoodTag.setVisibility(View.GONE);


        GradientDrawable gd3 = new GradientDrawable();
        gd3.setCornerRadius(25 * density);
        gd3.setColor(ContextCompat.getColor(_mActivity, R.color.white));
        gd3.setStroke((int) (0.8 * density), ContextCompat.getColor(_mActivity, R.color.color_cccccc));
        mTvBtnAction1.setBackground(gd3);

        GradientDrawable gd4 = new GradientDrawable();
        gd4.setCornerRadius(25 * density);
        gd4.setColor(ContextCompat.getColor(_mActivity, R.color.white));
        gd4.setStroke((int) (0.8 * density), ContextCompat.getColor(_mActivity, R.color.color_cccccc));
        mTvBtnAction2.setBackground(gd4);

    }

    private void setListeners() {
        mBtnBuyGood.setOnClickListener(this);
        mLlMoreGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_more_game:
                //更多相关商品
                if (!TextUtils.isEmpty(gamename) && !TextUtils.isEmpty(gameid)) {
                    start(TransactionMainFragment1.newInstance(gamename, gameid));
                }
                break;
            case R.id.btn_buy_good:
                if (checkLogin()) {
                    //购买商品
                    if (tradeGoodDetailInfoBean != null) {
                        startForResult(TransactionBuyFragment.newInstance(goodid,
                                good_pic,
                                tradeGoodDetailInfoBean.getGoods_title(),
                                tradeGoodDetailInfoBean.getGamename(),
                                tradeGoodDetailInfoBean.getGoods_price(),
                                tradeGoodDetailInfoBean.getGameid(),
                                tradeGoodDetailInfoBean.getGame_type(), tradeGoodDetailInfoBean.getGoods_type()), action_buy);
                    }
                }
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
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case action_buy:
                case action_modify_good:
                    //刷新页面
                    initData();
                    isAnyDataChange = true;
                    break;
                default:
                    break;
            }
        }
    }


    private TradeGoodDetailInfoVo.DataBean tradeGoodDetailInfoBean;

    private void getGoodDetailData() {
        if (mViewModel != null) {
            mViewModel.getTradeGoodDetail(goodid, "", new OnBaseCallback<TradeGoodDetailInfoVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onSuccess(TradeGoodDetailInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            setViewValue(data.getData());
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void setViewValue(TradeGoodDetailInfoVo.DataBean data) {
        if (data != null) {
            TransactionGoodItemActionHelper goodItemActionHelper = new TransactionGoodItemActionHelper(this);
            tradeGoodDetailInfoBean = data;
            gamename = data.getGamename();
            gameid = data.getGameid();

            getRelatedGoodList();

            //关联游戏的信息
            GlideUtils.loadRoundImage(_mActivity, data.getGameicon(), mIvGameImage, R.mipmap.ic_placeholder);
            mTvGameName.setText(data.getGamename());
            mTvGameName2.setText(data.getGamename());

            tv_platform.setVisibility(View.VISIBLE);
            if (data.getData_exchange() == 1){
                tv_platform.setText("（适用于双端）");
            }else {
                if (data.getXh_client() == 3){
                    tv_platform.setText("（适用于双端）");
                }else if (data.getXh_client() == 1){
                    tv_platform.setText("（仅适用于安卓）");
                }else if(data.getXh_client() == 2){
                    tv_platform.setText("（仅适用于IOS）");
                }else {
                    tv_platform.setVisibility(View.GONE);
                }
            }

            GradientDrawable gd2 = new GradientDrawable();
            if (data.getPackage_size() == 0) {
                mViewMidLine.setVisibility(View.GONE);
                gd2.setCornerRadius(12 * density);
                gd2.setStroke((int) (0.8 * density), ContextCompat.getColor(_mActivity, R.color.color_cccccc));
                mTvGameSize.setPadding((int) (6 * density), (int) (1 * density), (int) (6 * density), (int) (1 * density));
                mTvGameSize.setBackground(gd2);
                mTvGameSize.setText("H5游戏");
            } else {
                mViewMidLine.setVisibility(View.VISIBLE);
                mTvGameSize.setText(data.getPackage_size() + "M");
                mTvGameSize.setPadding(0, 0, 0, 0);
                mTvGameSize.setBackground(gd2);
            }
            mTvGameType.setText(data.getGenre_list());
            mBtnGameDetail.setOnClickListener(view -> {
                if (data.getGame_is_close() != 1) {
                    if (data.getClient_type() == 2) {
                        //iOS端
                        Toaster.show( "请使用iOS设备下载");
                    } else {
                        try {
                            goGameDetail(Integer.parseInt(data.getGameid()), Integer.parseInt(data.getGame_type()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            if (data.getGame_is_close() == 1) {
                //游戏已下架
                mBtnGameDetail.setText("已下架");
                mBtnGameDetail.setVisibility(View.GONE);
            } else {
                mBtnGameDetail.setText("下载");
                mBtnGameDetail.setVisibility(View.VISIBLE);
            }

            //小号信息
            mTvTradingOrTraded.setText("上架：");
            mTvOnlineTime.setText(CommonUtils.formatTimeStamp(data.getVerify_time() * 1000, "MM-dd HH:mm"));
            mTvXhAccount.setText(data.getXh_username());
            mTvServerName.setText(data.getServer_info());
            mTvGoodPrice.setText(String.valueOf(data.getGoods_price()));

            StringBuilder sb = new StringBuilder();
            sb.append("此小号已创建").append(String.valueOf(data.getCdays())).append("天，累计充值").append(data.getXh_pay_game_total()).append("元");
            mTvGoodValue.setText(sb.toString());
            mTvGoodValue.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_007aff));

            findViewById(R.id.iv_1).setOnClickListener(v -> {
                StringBuilder tipsSb = new StringBuilder();
                tipsSb.append("实际充值为该游戏小号所有区服的总充值！");
                int startIndex = tipsSb.toString().length();
                tipsSb.append("(仅供参考)");
                int endIndex = tipsSb.toString().length();
                SpannableString ss = new SpannableString(tipsSb.toString());
                ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_666666)),
                        startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                AlertDialog builder = new AlertDialog.Builder(_mActivity)
                        .setTitle("温馨提示")
                        .setMessage(ss)
                        .setPositiveButton("我知道了", (dialog, which) -> dialog.dismiss()).show();
                CommonUtils.setAlertDialogTextSize(builder, 16, 14);
            });

            if (data.getGoods_status() == 3 || data.getGoods_status() == 4 || data.getGoods_status() == 5 || data.getGoods_status() == 10) {
                String tag = "信息已审核";
                if (data.getGoods_status() == 10) {
                    tag = "已出售";
                }
                mTvGoodTag.setText(tag);
                mTvGoodTag.setVisibility(View.VISIBLE);
            }

            if (data.getGoods_type() == 2){//捡漏
                findViewById(R.id.ll_detail_tips).setVisibility(View.GONE);
                findViewById(R.id.ll_leakage_tips).setVisibility(View.VISIBLE);
                findViewById(R.id.tv_good_title_tips).setVisibility(View.GONE);
                TextView tv_leakage_tips_2 = findViewById(R.id.tv_leakage_tips_2);
                TextView tv_leakage_tips_5 = findViewById(R.id.tv_leakage_tips_5);

                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("2、捡漏号有几率买到霸服极品号，也有可能买到废号，已转游被封的号，能捡漏到什么号，纯属运气");
                spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), spannableStringBuilder.length() - 12, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_leakage_tips_2.setText(spannableStringBuilder);

                SpannableStringBuilder spannableStringBuilder1 = new SpannableStringBuilder("5、捡漏号无法售后，一经售出概不退还，请谨慎购买！若您所购买的小号存在封号及空号情况，请联系客服核实再做处理！");
                spannableStringBuilder1.setSpan(new StyleSpan(Typeface.BOLD), spannableStringBuilder1.length() - 30, spannableStringBuilder1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_leakage_tips_5.setText(spannableStringBuilder1);

                mTvGoodTitle.setVisibility(View.VISIBLE);
                mTvGoodTitle.setText("此号为超值捡漏号，购前请详细阅读交易提示");
            }else{
                findViewById(R.id.ll_detail_tips).setVisibility(View.VISIBLE);
                findViewById(R.id.ll_leakage_tips).setVisibility(View.GONE);
                findViewById(R.id.tv_good_title_tips).setVisibility(View.VISIBLE);

                if (data.getGoods_title() != null && !StringUtil.isEmpty(data.getGoods_title())) {
                    mTvGoodTitle.setVisibility(View.VISIBLE);
                    mTvGoodTitle.setText("【" + data.getGoods_title() + "】");
                } else {
                    mTvGoodTitle.setVisibility(View.GONE);
                }
                mTvGoodTitle.setVisibility(View.GONE);
            }
            mTvGoodTitle.setVisibility(View.GONE);
            if (TextUtils.isEmpty(data.getGoods_description())) {
                mTvGoodDescription.setVisibility(View.GONE);
            } else {
                mTvGoodDescription.setVisibility(View.VISIBLE);
                mTvGoodDescription.setText(data.getGoods_description());
            }

            //商品图片列表
            mLlGoodScreenshotList.removeAllViews();
            List<TradeGoodDetailInfoVo.PicListBean> picListBeanList = data.getPic_list();
            if (picListBeanList == null || picListBeanList.size() == 0) {
                mLlGoodScreenshotList.setVisibility(View.GONE);
            } else {
                mLlGoodScreenshotList.setVisibility(View.VISIBLE);

                //预览图片
                ArrayList<Image> images = new ArrayList();
                for (TradeGoodDetailInfoVo.PicListBean picListBean : picListBeanList) {
                    Image image = new Image();
                    image.setType(1);
                    image.setPath(picListBean.getPic_path());
                    images.add(image);
                }
                for (int i = 0; i < picListBeanList.size(); i++) {
                    TradeGoodDetailInfoVo.PicListBean picListBean = picListBeanList.get(i);
                    View itemView = createGoodPicView(picListBean);
                    mLlGoodScreenshotList.addView(itemView);

                    int finalI = i;
                    itemView.setOnClickListener(view -> {
                        showPicListDetail(images, finalI);
                    });
                }
            }

            mTvBtnAction1.setVisibility(View.GONE);
            mTvBtnAction2.setVisibility(View.GONE);

            mFlGoodFailReason.setVisibility(View.GONE);

            mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_333333));

            mLlGoodShelves.setVisibility(View.VISIBLE);
            mTvGoodUnShelves.setVisibility(View.GONE);


            if (data.getHas_xh_passwd() == 1) {
                mLlSecondaryPassword.setVisibility(View.VISIBLE);
                mTvSecondaryPassword.setText("购买后查看");
            } else {
                mLlSecondaryPassword.setVisibility(View.GONE);
            }
            mTvGoodStatus.setOnClickListener(null);
            //商品状态
            if (data.getIs_seller() == 1) {
                //卖家
                mFlBtnBuyGood.setVisibility(View.GONE);
                mFlGoodStatus.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(data.getXh_passwd())) {
                    mTvSecondaryPassword.setText(data.getXh_passwd());
                }
                String value = "";
                switch (data.getGoods_status()) {
                    case 1:
                        value = "待审核";
                        mTvBtnAction1.setVisibility(View.GONE);
                        mTvBtnAction1.setText("修改");
                        mTvBtnAction1.setOnClickListener(view -> {
                            //修改商品属性
                            modifyGoodItem(goodItemActionHelper, data.getGid());
                        });
                        mTvBtnAction2.setVisibility(View.VISIBLE);
                        mTvBtnAction2.setText("下架");
                        mTvBtnAction2.setOnClickListener(view -> {
                            //商品下架
                            stopSelling(goodItemActionHelper, data.getGid());
                        });
                        if (data.getGame_is_close() == 1) {
                            value = "该游戏暂不支持账号交易";
                            mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff0000));
                            mTvBtnAction1.setVisibility(View.GONE);
                        }
                        //2018.04.23 待审核商品 隐藏上架时间
                        mLlGoodShelves.setVisibility(View.GONE);
                        mTvGoodUnShelves.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        value = "审核中";
                        mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_999999));
                        //2018.04.23 待审核商品 隐藏上架时间
                        mLlGoodShelves.setVisibility(View.GONE);
                        mTvGoodUnShelves.setVisibility(View.VISIBLE);

                        if (data.getGame_is_close() == 1) {
                            value = "该游戏暂不支持账号交易";
                            mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff0000));

                            mTvBtnAction1.setVisibility(View.VISIBLE);
                            mTvBtnAction1.setText("下架");
                            mTvBtnAction1.setOnClickListener(view -> {
                                //商品下架
                                stopSelling(goodItemActionHelper, data.getGid());
                            });
                            GradientDrawable gd1 = new GradientDrawable();
                            gd1.setCornerRadius(32 * density);
                            gd1.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
                            mTvBtnAction1.setBackground(gd1);
                            mTvBtnAction1.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
                        }

                        break;
                    case 3:
                        value = "出售中";
                        mTvBtnAction1.setVisibility(View.VISIBLE);
                        mTvBtnAction1.setText("改价");
                        mTvBtnAction1.setOnClickListener(view -> {
                            //修改价格
                            changeGoodPrice(goodItemActionHelper, data.getGid(), data.getGoods_price(), Integer.parseInt(data.getCan_bargain()), data.getAuto_price());
                        });
                        mTvBtnAction2.setVisibility(View.VISIBLE);
                        mTvBtnAction2.setText("下架");
                        mTvBtnAction2.setOnClickListener(view -> {
                            //商品下架
                            stopSellingWithTips(goodItemActionHelper, data.getGid());
                        });
                        if (data.getGame_is_close() == 1) {
                            value = "该游戏暂不支持账号交易";
                            mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff0000));
                            mTvBtnAction1.setVisibility(View.GONE);
                        }
                        break;
                    case 4:
                        value = "交易中";
                        mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_007aff));
                        if (data.getGame_is_close() == 1) {
                            value = "该游戏暂不支持账号交易";
                            mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff0000));

                            mTvBtnAction1.setVisibility(View.VISIBLE);
                            mTvBtnAction1.setText("下架");
                            mTvBtnAction1.setOnClickListener(view -> {
                                //商品下架
                                stopSelling(goodItemActionHelper, data.getGid());
                            });
                            GradientDrawable gd1 = new GradientDrawable();
                            gd1.setCornerRadius(32 * density);
                            gd1.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
                            mTvBtnAction1.setBackground(gd1);
                            mTvBtnAction1.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
                        }
                        break;
                    case 10:
                        //卖家已出售
                        value = "已出售";
                        mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff4949));

                        //成交时间
                        mTvTradingOrTraded.setText("成交：");
                        mTvOnlineTime.setText(CommonUtils.formatTimeStamp(data.getTrade_time() * 1000, "MM-dd HH:mm"));

                        break;
                    case -1:
                        value = "审核未通过";
                        //下划线
                        mTvGoodStatus.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                        mFlGoodFailReason.setVisibility(View.GONE);
                        mTvGoodFailReason.setText(data.getFail_reason());

                        mTvGoodStatus.setOnClickListener(view -> {
                            showTransactionRule();
                        });

                        mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff4949));

                        mTvBtnAction1.setVisibility(View.VISIBLE);
                        mTvBtnAction1.setText("修改");
                        mTvBtnAction1.setOnClickListener(view -> {
                            //修改商品属性
                            modifyGoodItem(goodItemActionHelper, data.getGid());
                        });
                        break;
                    case -2:
                        value = "已下架";
                        mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_999999));
                        mTvBtnAction1.setVisibility(View.VISIBLE);
                        mTvBtnAction1.setText("修改");
                        mTvBtnAction1.setOnClickListener(view -> {
                            //修改商品详情
                            modifyGoodItem(goodItemActionHelper, data.getGid());
                        });
                        break;

                    default:
                        break;
                }
                mTvGoodStatus.setText(value);
            } else {
                //买家
                if (data.getGoods_status() == 5) {
                    //买家已购买
                    mFlBtnBuyGood.setVisibility(View.GONE);
                    mFlGoodStatus.setVisibility(View.VISIBLE);
                    mTvGoodStatus.setText("已购买");

                    if (!TextUtils.isEmpty(data.getXh_passwd())) {
                        mTvSecondaryPassword.setText(data.getXh_passwd());
                    }

                    mTvBtnAction1.setVisibility(View.GONE);
                    mTvBtnAction1.setText("如何使用");
                    mTvBtnAction1.setOnClickListener(view -> {
                        //如何使用
                        howToUseGoods(goodItemActionHelper);
                    });

                    //成交时间
                    mTvTradingOrTraded.setText("成交：");
                    mTvOnlineTime.setText(CommonUtils.formatTimeStamp(data.getTrade_time() * 1000, "MM-dd HH:mm"));
                } else if (data.getGoods_status() == 4) {
                    //买家购买中
                    mFlBtnBuyGood.setVisibility(View.GONE);
                    mFlGoodStatus.setVisibility(View.VISIBLE);
                    mTvGoodStatus.setText("交易中");
                    mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_007aff));

                    mTvBtnAction1.setVisibility(View.VISIBLE);
                    mTvBtnAction1.setText("立即付款");
                    mTvBtnAction1.setOnClickListener(view -> {
                        //立即付款
                        String gid = data.getGid();
                        String good_title = data.getGoods_title();
                        String gamename = data.getGamename();
                        String good_price = data.getGoods_price();
                        String gameid = data.getGameid();
                        String game_type = data.getGame_type();
                        int goods_type = data.getGoods_type();
                        startForResult(TransactionBuyFragment.newInstance(gid, good_pic, good_title, gamename, good_price, gameid, game_type, 1, goods_type), action_buy);
                    });

                } else {
                    mFlGoodStatus.setVisibility(View.GONE);
                    mFlBtnBuyGood.setVisibility(View.VISIBLE);
                    if (data.getGoods_status() == 10) {
                        mBtnBuyGood.setEnabled(false);
                        GradientDrawable gd = new GradientDrawable();
                        gd.setCornerRadius(45 * density);
                        gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_cccccc));
                        mBtnBuyGood.setBackground(gd);
                        mBtnBuyGood.setText("角色已售出");

                        //成交时间
                        mTvTradingOrTraded.setText("成交：");
                        mTvOnlineTime.setText(CommonUtils.formatTimeStamp(data.getTrade_time() * 1000, "MM-dd HH:mm"));
                    } else {
                        mBtnBuyGood.setEnabled(true);
                        mBtnBuyGood.setBackgroundResource(R.drawable.ts_shape_gradient_55c0fe_5571fe);
                        mBtnBuyGood.setText("立即购买");
                    }
                }
            }

            if (0 == data.getIs_favorite()){
                tv_collection.setText("收藏");
                Drawable drawable = getResources().getDrawable(R.mipmap.ic_audit_transaction_good_detail_1);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
                tv_collection.setCompoundDrawables(null,drawable,null,null);
                tv_collection.setCompoundDrawablePadding(-2);
            }else {
                Drawable drawable = getResources().getDrawable(R.mipmap.ic_audit_transaction_good_detail_2);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
                tv_collection.setCompoundDrawables(null,drawable,null,null);
                tv_collection.setCompoundDrawablePadding(-2);
                tv_collection.setText("已收藏");
            }

            if ("2".equals(data.getCan_bargain())){
                btn_dicker_good.setVisibility(View.VISIBLE);
            }else {
                btn_dicker_good.setVisibility(View.GONE);
            }

            tv_collection.setOnClickListener(view -> {

            });
        }
        setBottomViewPadding();
    }

    private void howToUseGoods(TransactionGoodItemActionHelper goodItemActionHelper) {
        goodItemActionHelper.howToUseGoods();
    }


    private void changeGoodPrice(TransactionGoodItemActionHelper goodItemActionHelper, String gid, String goods_price, int can_bargain, int auto_price) {
        goodItemActionHelper.changeGoodPrice(gameid, gid, goods_price, can_bargain, auto_price, () -> {
            initData();
            isAnyDataChange = true;
        });
    }

    private void stopSelling(TransactionGoodItemActionHelper goodItemActionHelper, String gid) {
        goodItemActionHelper.stopSelling(gid, () -> {
            initData();
            isAnyDataChange = true;
        });
    }

    private void stopSellingWithTips(TransactionGoodItemActionHelper goodItemActionHelper, String gid) {
        goodItemActionHelper.stopSellingWithTips(gid, () -> {
            initData();
            isAnyDataChange = true;
        });
    }

    private void modifyGoodItem(TransactionGoodItemActionHelper goodItemActionHelper, String gid) {
        goodItemActionHelper.modifyGoodItem(gid, action_modify_good);
    }


    private void setBottomViewPadding() {
        if (mFlBtnBuyGood.getVisibility() == View.VISIBLE && mFlGoodStatus.getVisibility() == View.GONE) {
            mLlRootview.setPadding(0, 0, 0, (int) (80 * density));
        } else if (mFlBtnBuyGood.getVisibility() == View.GONE && mFlGoodStatus.getVisibility() == View.VISIBLE) {
            mLlRootview.setPadding(0, 0, 0, (int) (60 * density));
        }
    }

    private ImageView createGoodPicView(TradeGoodDetailInfoVo.PicListBean picListBean) {
        float scale = picListBean.getPic_height() / picListBean.getPic_width();

        int targetHeight = (int) (scale * (density * 328));

        ImageView imageView = new ImageView(_mActivity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, targetHeight);
        imageView.setLayoutParams(params);
        params.setMargins(0, (int) (24 * density), 0, 0);
        GlideUtils.loadNormalImage(_mActivity, picListBean.getPic_path(), imageView, R.mipmap.img_placeholder_v_2);
        imageView.setAdjustViewBounds(true);

        return imageView;
    }

    private void getRelatedGoodList() {
        Map<String, String> params = new TreeMap<>();
        params.clear();

        params.put("scene", "normal");
        params.put("gameid", gameid);
        params.put("rm_gid", goodid);
        params.put("goods_type", String.valueOf(tradeGoodDetailInfoBean.getGoods_type()));

        params.put("page", String.valueOf(1));
        params.put("pagecount", String.valueOf(3));

        if (mViewModel != null) {
            mViewModel.getTradeGoodList(params, new OnBaseCallback<TradeGoodInfoListVo>() {
                @Override
                public void onSuccess(TradeGoodInfoListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            setRelatedGoodList(data.getData());
                        }
                    }
                }
            });
        }
    }


    private void setRelatedGoodList(List<TradeGoodInfoVo> tradeGoodInfoBeanList) {
        if (tradeGoodInfoBeanList == null || tradeGoodInfoBeanList.isEmpty()) {
            mLlRelatedGoodList.setVisibility(View.GONE);
        } else {
            mLlRelatedGoodList.setVisibility(View.VISIBLE);

            mMoreAdapter.clear();
            mMoreAdapter.addAllData(tradeGoodInfoBeanList);
            mMoreAdapter.notifyDataSetChanged();
        }
    }

//
//    /**
//     * 修改商品属性
//     *
//     * @param gid
//     */
//    public void modifyGoodItem(String gid) {
//        startForResult(TransactionSellFragment.newInstance(gid), action_modify_good);
//    }
//
//    /**
//     * 修改价格
//     *
//     * @param gid
//     */
//    public void changeGoodPrice(String gid, String goods_price) {
//        showChangePriceDialog(gid, goods_price);
//    }
//
//    /**
//     * 下架商品
//     *
//     * @param gid
//     */
//    public void stopSelling(String gid) {
//        AlertDialog dialog = new AlertDialog.Builder(_mActivity).create();
//        dialog.setTitle("提示");
//        dialog.setMessage("确定下架商品吗？");
//        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "下架", (DialogInterface dialogInterface, int i) -> {
//            dialogInterface.dismiss();
//            stopSellingAction(gid);
//        });
//        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "暂不下架", (DialogInterface dialogInterface, int i) -> {
//            dialogInterface.dismiss();
//        });
//        dialog.show();
//        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
//        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
//
//        setDefaultSystemDialogTextSize(dialog);
//    }
//
//    public void stopSellingWithTips(String gid) {
//        AlertDialog dialog = new AlertDialog.Builder(_mActivity).create();
//        dialog.setTitle("确定要下架商品吗？");
//        dialog.setMessage("①下架商品后，再次提交出售需等待24小时,请慎重操作！\n" +
//                "②若只需修改出售价格，可点击“改价”操作。");
//        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "下架", (DialogInterface dialogInterface, int i) -> {
//            dialogInterface.dismiss();
//            stopSellingAction(gid);
//        });
//        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "暂不下架", (DialogInterface dialogInterface, int i) -> {
//            dialogInterface.dismiss();
//        });
//        dialog.show();
//        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
//        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
//
//        setDefaultSystemDialogTextSize(dialog);
//    }
//
//    private void stopSellingAction(String gid) {
//        if (mViewModel != null) {
//            mViewModel.offLineTradeGood(gid, new OnBaseCallback() {
//                @Override
//                public void onSuccess(BaseVo data) {
//                    if (data != null) {
//                        if (data.isStateOK()) {
//                            ToastT.success(_mActivity, "下架成功");
//                            initData();
//                            isAnyDataChange = true;
//                        } else {
//                            Toaster.show( data.getMsg());
//                        }
//                    }
//                }
//            });
//
//        }
//    }
//
//
//    /**
//     * 如何使用
//     */
//    public void howToUseGoods() {
//        start(new TransactionInstructionsFragment());
//    }
//
//    /**
//     * 删除记录
//     *
//     * @param gid
//     */
//    public void deleteTradeGood(String gid) {
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
//                                ToastT.success(_mActivity, "删除成功");
//                                initData();
//                                isAnyDataChange = true;
//                            } else {
//                                ToastT.success(_mActivity, data.getMsg());
//                            }
//                        }
//                    }
//                });
//            }
//
//        });
//        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", (DialogInterface dialogInterface, int i) -> {
//            dialogInterface.dismiss();
//        });
//        dialog.show();
//        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
//        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
//
//    }
//
//    /**
//     * 删除记录
//     *
//     * @param gid
//     */
//    public void cancelTradeGood(String gid) {
//        AlertDialog dialog = new AlertDialog.Builder(_mActivity).create();
//        dialog.setTitle("提示");
//        dialog.setMessage("删除后该记录无法恢复，是否确认删除？");
//        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定", (DialogInterface dialogInterface, int i) -> {
//            dialogInterface.dismiss();
//            if (mViewModel != null) {
//                mViewModel.cancelTradeRecord(gid, new OnBaseCallback() {
//                    @Override
//                    public void onSuccess(BaseVo data) {
//                        if (data != null) {
//                            if (data.isStateOK()) {
//                                ToastT.success(_mActivity, "删除成功");
//                                initData();
//                                isAnyDataChange = true;
//                            } else {
//                                ToastT.success(_mActivity, data.getMsg());
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
//    }
//
//
//    private CustomDialog changePriceDialog;
//    private LinearLayout mLlSendCode;
//    private EditText mEtChangePrice;
//    private TextView mTvGetPrice;
//    private Button mBtnDialogCancel;
//    private Button mBtnDialogConfirm;
//    private EditText mEtVerificationCode;
//    private TextView mTvSendCode;
//
//    private void showChangePriceDialog(String gid, String goods_price) {
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
//            actionChangeGoodPrice(gid, price, code);
//        });
//        changePriceDialog.show();
//        showSoftInput(mEtChangePrice);
//    }
//
//    private void setBtnConfirmEnable(boolean isEnable) {
//        GradientDrawable gd2 = new GradientDrawable();
//        gd2.setCornerRadius(32 * density);
//        if (isEnable) {
//            gd2.setColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
//        } else {
//            gd2.setColor(ContextCompat.getColor(_mActivity, R.color.color_c1c1c1));
//        }
//        mBtnDialogConfirm.setBackground(gd2);
//        mBtnDialogConfirm.setEnabled(isEnable);
//    }
//
//    private void actionChangeGoodPrice(String gid, int goods_price, String code) {
//        if (mViewModel != null) {
//            mViewModel.getModifyGoodPrice(gid, goods_price, code, new OnBaseCallback() {
//                @Override
//                public void onSuccess(BaseVo data) {
//                    if (data != null) {
//                        if (data.isStateOK()) {
//                            ToastT.success(_mActivity, "修改成功");
//                            initData();
//                            isAnyDataChange = true;
//                            if (changePriceDialog != null && changePriceDialog.isShowing()) {
//                                changePriceDialog.dismiss();
//                            }
//                        } else {
//                            Toaster.show( data.getMsg());
//                        }
//                    }
//                }
//            });
//        }
//    }
//
//    private void getTradeCode(String targetGameid) {
//        if (TextUtils.isEmpty(targetGameid)) {
//            return;
//        }
//
//        if (mViewModel != null) {
//            mViewModel.getTradeCode(targetGameid, new OnBaseCallback() {
//                @Override
//                public void onSuccess(BaseVo data) {
//                    if (data != null) {
//                        if (data.isStateOK()) {
//                            sendCode();
//                        } else {
//                            Toaster.show( data.getMsg());
//                        }
//                    }
//                }
//            });
//        }
//    }
//
//    private void sendCode() {
//        GradientDrawable gd1 = new GradientDrawable();
//        gd1.setCornerRadius(30 * density);
//        gd1.setColor(ContextCompat.getColor(_mActivity, R.color.color_c1c1c1));
//        mTvSendCode.setBackground(gd1);
//
//        new CountDownTimer(60 * 1000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                mTvSendCode.setEnabled(false);
//                mTvSendCode.setText("重新发送" + (millisUntilFinished / 1000) + "秒");
//            }
//
//            @Override
//            public void onFinish() {
//                GradientDrawable gd1 = new GradientDrawable();
//                gd1.setCornerRadius(30 * density);
//                gd1.setColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
//                mTvSendCode.setBackground(gd1);
//                mTvSendCode.setText("发送验证码");
//                mTvSendCode.setEnabled(true);
//            }
//        }.start();
//    }
}
