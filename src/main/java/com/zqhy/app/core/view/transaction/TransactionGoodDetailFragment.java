package com.zqhy.app.core.view.transaction;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.hjq.toast.Toaster;
import com.donkingliang.imageselector.entry.Image;
import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.transaction.CollectionBeanVo;
import com.zqhy.app.core.data.model.transaction.TradeGoodDetailInfoVo;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoListVo1;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoVo1;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.StringUtil;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.core.view.transaction.base.TransactionGoodItemActionHelper;
import com.zqhy.app.core.view.transaction.buy.TransactionBuyFragment;
import com.zqhy.app.core.view.transaction.holder.TradeItemHolder1;
import com.zqhy.app.core.view.transaction.holder.TradePicItem;
import com.zqhy.app.core.view.transaction.util.CustomPopWindow;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.share.ShareHelper;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.imageview.ClipRoundImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Administrator
 */
public class TransactionGoodDetailFragment extends BaseFragment<TransactionViewModel> implements View.OnClickListener {

    public static TransactionGoodDetailFragment newInstance(String goodid, String gameid, String good_pic) {
        TransactionGoodDetailFragment fragment = new TransactionGoodDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goodid", goodid);
        bundle.putString("gameid", gameid);
        bundle.putString("good_pic", good_pic);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static TransactionGoodDetailFragment newInstance(String goodid, String gameid) {
        TransactionGoodDetailFragment fragment = new TransactionGoodDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goodid", goodid);
        bundle.putString("gameid", gameid);
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
    private TextView mTvGameName3;
    private LinearLayout mLlMoreGame;
    private RecyclerView mMoreRecycler;
    private RecyclerView recyclerView_iv;
    private FrameLayout mFlGoodBottomView;
    private LinearLayout mFlBtnBuyGood;
    private Button mBtnBuyGood;
    private FrameLayout mFlGoodStatus;
    private TextView mTvGoodStatus;
    private TextView tv_good_time;
    private TextView tv_transaction_good_status1;
    private TextView tv_explain;
    private TextView mTvBtnAction1;
    private TextView mTvBtnAction2;
    private FrameLayout mFlGoodFailReason;
    private TextView mTvGoodFailReason;

    private Button btn_dicker_good;
    private TextView tv_collection;
    private TextView tv_kefu;
    private TextView tv_genre_str;
    private TextView tv_play_count;
    private TextView mTvPercent;
    private TextView mTvPercent1;
    private TextView tv_can_bargain;
    private TextView tv_pay_game_total;
    private ImageView iv_pay_game_total;
    private LinearLayout mlayoutPercent;
    private ImageView iv_search;
    private TextView tv_platform;

    private TextView mTvGameSuffix;
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
        mTvGameName3 = findViewById(R.id.tv_tv_game_name_3);
        mLlMoreGame = findViewById(R.id.ll_more_game);
        mMoreRecycler = findViewById(R.id.more_recycler);
        recyclerView_iv = findViewById(R.id.recyclerView_iv);
        mFlGoodBottomView = findViewById(R.id.fl_good_bottom_view);
        mFlBtnBuyGood = findViewById(R.id.fl_btn_buy_good);
        mBtnBuyGood = findViewById(R.id.btn_buy_good);
        mFlGoodStatus = findViewById(R.id.fl_good_status);
        mTvGoodStatus = findViewById(R.id.tv_good_status);
        tv_good_time = findViewById(R.id.tv_good_time);
        tv_transaction_good_status1 = findViewById(R.id.tv_transaction_good_status1);
        tv_explain = findViewById(R.id.tv_explain);
        mTvBtnAction1 = findViewById(R.id.tv_btn_action_1);
        mTvBtnAction2 = findViewById(R.id.tv_btn_action_2);
        mFlGoodFailReason = findViewById(R.id.fl_good_fail_reason);
        mTvGoodFailReason = findViewById(R.id.tv_good_fail_reason);

        btn_dicker_good = findViewById(R.id.btn_dicker_good);
        tv_collection = findViewById(R.id.tv_collection);
        tv_kefu = findViewById(R.id.tv_kefu);
        iv_search = findViewById(R.id.iv_search);
        tv_genre_str = findViewById(R.id.tv_genre_str);
        tv_play_count = findViewById(R.id.tv_play_count);
        mTvPercent = findViewById(R.id.tv_percent);
        mTvPercent1 = findViewById(R.id.tv_percent1);
        tv_can_bargain = findViewById(R.id.tv_can_bargain);
        tv_pay_game_total = findViewById(R.id.tv_pay_game_total);
        iv_pay_game_total = findViewById(R.id.iv_pay_game_total);
        mlayoutPercent = findViewById(R.id.layout_percent);
        mTvGameSuffix = findViewById(R.id.tv_game_suffix);
        tv_platform = findViewById(R.id.tv_platform);
        iv_search.setImageDrawable(getResources().getDrawable(R.mipmap.ic_audit_transaction_good_detail_4));
        iv_search.setVisibility(View.VISIBLE);
        iv_search.setOnClickListener(this);
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
    BaseRecyclerAdapter picAdapter;

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        layoutManager.setSmoothScrollbarEnabled(true);
        mMoreRecycler.setLayoutManager(layoutManager);
        mMoreRecycler.setNestedScrollingEnabled(false);

        mMoreAdapter = new BaseRecyclerAdapter.Builder()
                .bind(TradeGoodInfoVo1.class, new TradeItemHolder1(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
        mMoreRecycler.setAdapter(mMoreAdapter);

        mMoreAdapter.setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof TradeGoodInfoVo1) {
                TradeGoodInfoVo1 tradeGoodInfoBean = (TradeGoodInfoVo1) data;
                start(TransactionGoodDetailFragment.newInstance(tradeGoodInfoBean.getGid(), tradeGoodInfoBean.getGameid(), tradeGoodInfoBean.getPic().get(0).getPic_path()));
            }
        });
        recyclerView_iv.setLayoutManager(new GridLayoutManager(_mActivity, 4));
        recyclerView_iv.setNestedScrollingEnabled(false);
        picAdapter = new BaseRecyclerAdapter.Builder()
                .bind(TradeGoodDetailInfoVo.PicListBean.class, new TradePicItem(_mActivity))
                .build();
        recyclerView_iv.setAdapter(picAdapter);
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
                        startForResult(TransactionBuyFragment.newInstance(goodid, tradeGoodDetailInfoBean.getGamename(),tradeGoodDetailInfoBean.getGame_suffix(), tradeGoodDetailInfoBean.getGameicon(),
                                tradeGoodDetailInfoBean.getGenre_str(), tradeGoodDetailInfoBean.getPlay_count(),
                                tradeGoodDetailInfoBean.getXh_username(), tradeGoodDetailInfoBean.getServer_info(),
                                tradeGoodDetailInfoBean.getProfit_rate(),
                                tradeGoodDetailInfoBean.getGoods_price(), tradeGoodDetailInfoBean.getGameid(),
                                tradeGoodDetailInfoBean.getGame_type(), 0, tradeGoodDetailInfoBean.getGoods_type()), action_buy);
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
                            showSuccess();
                            setViewValue(data.getData());
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void setCollectionGood() {

        if (mViewModel != null) {
            mViewModel.setCollectionGood(goodid, new OnBaseCallback<CollectionBeanVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onSuccess(CollectionBeanVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            Toaster.show(data.getMsg());
                            if (data.getMsg().contains("成功")) {
                                isClickCollection = true;
                                Drawable drawable = getResources().getDrawable(R.mipmap.ic_audit_transaction_good_detail_2);
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
                                tv_collection.setCompoundDrawables(null, drawable, null, null);
                                tv_collection.setCompoundDrawablePadding(-2);
                                tv_collection.setText("已收藏");
                                return;
                            } else {
                                isClickCollection = true;
                                tv_collection.setText("收藏");
                                Drawable drawable = getResources().getDrawable(R.mipmap.ic_audit_transaction_good_detail_1);
                                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
                                tv_collection.setCompoundDrawables(null, drawable, null, null);
                                tv_collection.setCompoundDrawablePadding(-2);
                                return;
                            }
//                                if (isCollection) {
//                                    isCollection = false;
//                                    tv_collection.setText("收藏");
//                                    Drawable drawable = getResources().getDrawable(R.mipmap.ic_audit_transaction_good_detail_1);
//                                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
//                                    tv_collection.setCompoundDrawables(null, drawable, null, null);
//                                    tv_collection.setCompoundDrawablePadding(-2);
//                                } else {
//                                    isCollection = true;
//                                    Drawable drawable = getResources().getDrawable(R.mipmap.ic_audit_transaction_good_detail_2);
//                                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
//                                    tv_collection.setCompoundDrawables(null, drawable, null, null);
//                                    tv_collection.setCompoundDrawablePadding(-2);
//                                    tv_collection.setText("已收藏");
//                                }


                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                    isClickCollection = true;
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    isClickCollection = true;
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
            //关联游戏的信息
            GlideUtils.loadRoundImage(_mActivity, data.getGameicon(), mIvGameImage, R.mipmap.ic_placeholder);
            mTvGameName.setText(data.getGamename());
//            mTvGameName2.setText(data.getGamename());

            mIvGameImage.setOnClickListener(view -> {
                startFragment(GameDetailInfoFragment.newInstance(Integer.parseInt(data.getGameid()), 2));
            });

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
            if (!TextUtils.isEmpty(data.getGame_suffix())){//游戏后缀
                mTvGameSuffix.setVisibility(View.VISIBLE);
                mTvGameSuffix.setText(data.getGame_suffix());
            }else {
                mTvGameSuffix.setVisibility(View.GONE);
            }

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

            if (data.getShare_info() != null&&data.getShare_info().getParam() != null) {
                iv_search.setVisibility(View.VISIBLE);
                iv_search.setOnClickListener(view -> {
                    String share_title = data.getShare_info().getParam().getShare_title();
                    String share_text = data.getShare_info().getParam().getShare_text();
                    String share_target_url = data.getShare_info().getParam().getShare_target_url();
                    String share_image = data.getShare_info().getParam().getShare_image();

                    Logs.e("share_title = " + share_title + "\n" +
                            "share_text = " + share_text + "\n" +
                            "share_target_url = " + share_target_url + "\n" +
                            "share_image = " + share_image);

                    ShareHelper shareHelper = new ShareHelper(_mActivity, new ShareHelper.OnShareListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(String error) {

                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                    if (_mActivity instanceof BaseActivity) {
                        ((BaseActivity) _mActivity).setShareHelper(shareHelper);
                    }
                    shareHelper.showNormalShare(share_title, share_text, share_target_url, share_image);
                });
            } else {
                iv_search.setVisibility(View.GONE);
            }
            if (data.getGame_is_close() == 1) {
                //游戏已下架
                mBtnGameDetail.setText("已下架");
                mBtnGameDetail.setVisibility(View.GONE);
            } else {
                mBtnGameDetail.setText("下载");
                mBtnGameDetail.setVisibility(View.GONE);
            }

            //小号信息
            mTvTradingOrTraded.setText("上架时间：");
            if (data.getVerify_time()>1000) {
                mTvOnlineTime.setText(CommonUtils.formatTimeStamp(data.getVerify_time() * 1000, "MM-dd HH:mm"));
            }else {
                mTvOnlineTime.setText("暂未上架");
            }
            String xh_username = "";
            if (data.getXh_username()!=null&&data.getXh_username().length()>=8){
                xh_username = data.getXh_username().substring(data.getXh_username().length() - 8);
            }else {
                xh_username = data.getXh_username();
            }
            mTvXhAccount.setText(xh_username);
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

            if (TextUtils.isEmpty(data.getGoods_description())) {
                mTvGoodDescription.setVisibility(View.GONE);
            } else {
                mTvGoodDescription.setVisibility(View.VISIBLE);
                mTvGoodDescription.setText(data.getGoods_description());
            }

            //商品图片列表
//            mLlGoodScreenshotList.removeAllViews();
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

                picAdapter.clear();
                picAdapter.setDatas(picListBeanList);
                picAdapter.notifyDataSetChanged();
                picAdapter.setOnItemClickListener((v, position, data1) -> {
                    showPicListDetail(images, position);
                });

//                for (int i = 0; i < picListBeanList.size(); i++) {
//                    TradeGoodDetailInfoVo.PicListBean picListBean = picListBeanList.get(i);
//                    View itemView = createGoodPicView(picListBean);
//                    mLlGoodScreenshotList.addView(itemView);
//
//                    int finalI = i;
//                    itemView.setOnClickListener(view -> {
//                        showPicListDetail(images, finalI);
//                    });
//                }
            }

            mTvBtnAction1.setVisibility(View.GONE);
            mTvBtnAction2.setVisibility(View.GONE);

            mFlGoodFailReason.setVisibility(View.GONE);

            mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));

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
                            mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
                            mTvBtnAction1.setVisibility(View.GONE);
                        }
                        //2018.04.23 待审核商品 隐藏上架时间
                        mLlGoodShelves.setVisibility(View.GONE);
                        mTvGoodUnShelves.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        value = "审核中";
                        mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
                        //2018.04.23 待审核商品 隐藏上架时间
                        mLlGoodShelves.setVisibility(View.GONE);
                        mTvGoodUnShelves.setVisibility(View.VISIBLE);

                        if (data.getGame_is_close() == 1) {
                            value = "该游戏暂不支持账号交易";
                            mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));

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
                            mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
                            mTvBtnAction1.setVisibility(View.GONE);
                        }
                        break;
                    case 4:
                        value = "交易中";
                        mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
                        if (data.getGame_is_close() == 1) {
                            value = "该游戏暂不支持账号交易";
                            mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));

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
                        mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
                        String yyyy = CommonUtils.formatTimeStamp(System.currentTimeMillis(), "yyyy");
                        String regex = "(yyyy-MM-dd HH:mm)";
                        String s = CommonUtils.formatTimeStamp(data.getTrade_time() * 1000, regex);
                        if (s.contains(yyyy)) {
                            tv_good_time.setText("(" + CommonUtils.formatTimeStamp(data.getTrade_time() * 1000, "MM-dd HH:mm") + ")");
                            mTvOnlineTime.setText(CommonUtils.formatTimeStamp(data.getTrade_time() * 1000, "MM-dd HH:mm"));
                        } else {
                            tv_good_time.setText(s);
                            mTvOnlineTime.setText(CommonUtils.formatTimeStamp(data.getTrade_time() * 1000, "yyyy-MM-dd HH:mm"));
                        }
                        //成交时间
                        mTvTradingOrTraded.setText("成交时间：");


                        tv_good_time.setVisibility(View.VISIBLE);

                        break;
                    case -1:
                        value = "审核未通过";
                        //下划线
                        mTvGoodStatus.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                        mFlGoodFailReason.setVisibility(View.GONE);
                        mTvGoodFailReason.setText(data.getFail_reason());

                        mTvGoodStatus.setOnClickListener(view -> {
//                            showTransactionRule();
                            start(new TransactionSpecificationFragment());
                        });

                        mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));

                        tv_transaction_good_status1.setVisibility(View.VISIBLE);
                        tv_explain.setVisibility(View.VISIBLE);
                        tv_explain.setText(data.getFail_reason());

                        mTvBtnAction1.setVisibility(View.VISIBLE);
                        mTvBtnAction1.setText("修改");
                        mTvBtnAction1.setOnClickListener(view -> {
                            //修改商品属性
                            modifyGoodItem(goodItemActionHelper, data.getGid());
                        });
                        break;
                    case -2:
                        value = "已下架";
                        mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
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
                if ("已出售".equals(value)){
                    tv_can_bargain.setVisibility(View.GONE);
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

                    tv_good_time.setVisibility(View.VISIBLE);
                    tv_good_time.setText("(" + CommonUtils.formatTimeStamp(data.getTrade_time() * 1000, "MM-dd HH:mm") + ")");

                    //成交时间
                    mTvTradingOrTraded.setText("成交时间：");
                    mTvOnlineTime.setText(CommonUtils.formatTimeStamp(data.getTrade_time() * 1000, "MM-dd HH:mm"));
                } else if (data.getGoods_status() == 4) {
                    //买家购买中
                    mFlBtnBuyGood.setVisibility(View.GONE);
                    mFlGoodStatus.setVisibility(View.VISIBLE);
                    mTvGoodStatus.setText("交易中");
                    mTvGoodStatus.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));

                    mTvBtnAction1.setVisibility(View.VISIBLE);
                    mTvBtnAction1.setText("立即付款");
                    mTvBtnAction1.setOnClickListener(view -> {
                        //立即付款
                        String gid = data.getGid();
                        String good_price = data.getGoods_price();
                        String gameid = data.getGameid();
                        String game_type = data.getGame_type();
                        startForResult(TransactionBuyFragment.newInstance(gid, data.getGamename(), data.getGame_suffix(), data.getGameicon(), data.getGenre_str(), data.getPlay_count(),
                                data.getXh_username(), data.getServer_info(), data.getProfit_rate(),
                                good_price, gameid, game_type, 1, data.getGoods_type()), action_buy);
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
                        mBtnBuyGood.setText("已售出");

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

            tv_kefu.setOnClickListener(view -> {
                goKefuCenter();
            });

            tv_genre_str.setText(data.getGenre_str());
//            tv_play_count.setText(" • " + data.getPlay_count() + "人在玩");

            if ("1".equals(data.getGame_type())) {
                if (data.getProfit_rate() <= 0.1 && data.getProfit_rate() > 0.01) {
                    mlayoutPercent.setVisibility(View.VISIBLE);
                    mTvPercent.setText("0" + CommonUtils.saveTwoSizePoint(data.getProfit_rate() * 10) + "折");
                    mTvPercent1.setText("抄底");
                } else if (data.getProfit_rate() <= 0.2 && data.getProfit_rate() > 0.1) {
                    mlayoutPercent.setVisibility(View.VISIBLE);
                    mTvPercent.setText(CommonUtils.saveTwoSizePoint(data.getProfit_rate() * 10) + "折");
                    mTvPercent1.setText("捡漏");
                } else {
                    mlayoutPercent.setVisibility(View.GONE);
                }
            }else {
                mlayoutPercent.setVisibility(View.GONE);
            }

            if (0 == (data.getIs_favorite())) {
                tv_collection.setText("收藏");
                Drawable drawable = getResources().getDrawable(R.mipmap.ic_audit_transaction_good_detail_1);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
                tv_collection.setCompoundDrawables(null, drawable, null, null);
                tv_collection.setCompoundDrawablePadding(-2);
            } else {
                Drawable drawable = getResources().getDrawable(R.mipmap.ic_audit_transaction_good_detail_2);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
                tv_collection.setCompoundDrawables(null, drawable, null, null);
                tv_collection.setCompoundDrawablePadding(-2);
                tv_collection.setText("已收藏");
            }

            tv_pay_game_total.setText("￥ " + CommonUtils.saveTwoSizePoint(data.getXh_pay_game_total()) + "（实付:￥" + CommonUtils.saveTwoSizePoint(Float.parseFloat(data.getXh_pay_total())) + "）");
            iv_pay_game_total.setOnClickListener(view -> {
                showPopListViewGameTotal();
            });

            if (data.getGoods_status() == 10){
                btn_dicker_good.setVisibility(View.GONE);
            }else {
                if ("2".equals(data.getCan_bargain())) {
                    btn_dicker_good.setVisibility(View.VISIBLE);
                    btn_dicker_good.setOnClickListener(view -> {
                        if (checkLogin()) {
                            showPopListViewDicker(String.valueOf(data.getGoods_price()), data.getGid());
                        }
                    });
                    tv_can_bargain.setVisibility(View.GONE);
                } else {
                    btn_dicker_good.setVisibility(View.GONE);
                    tv_can_bargain.setVisibility(View.GONE);
                }
            }

            tv_collection.setOnClickListener(view -> {
                if (checkLogin()) {
                    if (isClickCollection) {
                        setCollectionGood();
                        isClickCollection = false;
                    }
                }
            });
        }
        setBottomViewPadding();
    }


    CustomPopWindow popWindowDicker;

    /**
     * 屏幕底部弹出
     * 方法名@：showAtLocation(contentView, Gravity.BOTTOM, 0, 0)
     * resource:PopWindow 布局
     */
    private void showPopListViewDicker(String old_price, String gid) {
        View contentView = LayoutInflater.from(_mActivity).inflate(R.layout.pop_transaction_good_detail_pay_dicker, null);
        TextView tv_close = contentView.findViewById(R.id.tv_close);
        TextView tv_old_price = contentView.findViewById(R.id.tv_old_price);
        TextView tv_do = contentView.findViewById(R.id.tv_do);
        EditText et_new_price = contentView.findViewById(R.id.et_new_price);
        tv_old_price.setText(old_price);
        int oldPrice = Integer.parseInt(old_price);

        //处理popWindow 显示内容
        //创建并显示popWindow
        popWindowDicker = new CustomPopWindow.PopupWindowBuilder(_mActivity)
                .enableOutsideTouchableDissmiss(false)
                .enableBackgroundDark(true)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                .create();
        popWindowDicker.setBackgroundAlpha(0.5f);
        popWindowDicker.getPopupWindow().setSoftInputMode(SOFT_INPUT_ADJUST_RESIZE);
        popWindowDicker.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindowDicker.dissmiss();
            }
        });

        tv_do.setOnClickListener(view -> {
            Editable newPrice = et_new_price.getText();
            if (TextUtils.isEmpty(newPrice)) {
                Toaster.show("请填写期望金额!");
                return;
            }
            int inewPrice = Integer.parseInt(newPrice.toString());
            if (inewPrice < 6) {
                Toaster.show("不可低于6元!");
                return;
            }
            if (inewPrice >= oldPrice) {
                Toaster.show("超过原价，请直接购买!");
                return;
            }

            doGoodsBargain(gid, inewPrice + "");

        });
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        //登录了刷新
        if (UserInfoModel.getInstance().isLogined()) {
            isClickCollection = true;
            getGoodDetailData();
        }
    }

    /**
     * 屏幕底部弹出
     * 方法名@：showAtLocation(contentView, Gravity.BOTTOM, 0, 0)
     * resource:PopWindow 布局
     */
    private void showPopListViewGameTotal() {
        View contentView = LayoutInflater.from(_mActivity).inflate(R.layout.pop_transaction_good_detail_pay_game_total, null);
        TextView tv_close = contentView.findViewById(R.id.tv_close);

        TextView tv_content = contentView.findViewById(R.id.tv_content);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("1、游戏充值金额为当前小号在游戏全区全服的折扣前总充值金额（含卡券等优惠）；\n\n2、游戏实付金额为当前小号在游戏全区全服的折扣后总充值金额（不含卡券等优惠）；\n\n3、购买前请仔细检查角色信息，核对截图内容");
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), 21, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FF2B3F")), 21, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), 30, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FF2B3F")), 30, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), 61, 64, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FF2B3F")), 61, 64,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), 70, 72, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FF2B3F")), 70, 72,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_content.setText(spannableStringBuilder);

        //处理popWindow 显示内容
        //创建并显示popWindow
        CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(_mActivity)
                .enableOutsideTouchableDissmiss(false)
                .enableBackgroundDark(true)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                .create();
        popWindow.setBackgroundAlpha(0.5f);
        popWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow.dissmiss();
            }
        });
    }

    private boolean isClickCollection = true;

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

        ClipRoundImageView imageView = new ClipRoundImageView(_mActivity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(70, 70);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        params.setMargins(0, (int) (24 * density), 0, 0);
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
        params.put("pic", "multiple");
        params.put("page", String.valueOf(1));
        params.put("pagecount", String.valueOf(3));
        if (mViewModel != null) {
            mViewModel.getTradeGoodList1(params, new OnBaseCallback<TradeGoodInfoListVo1>() {
                @Override
                public void onSuccess(TradeGoodInfoListVo1 data) {
                    if (data != null) {
                        setRelatedGoodList(data.getData());
                    }
                }
            });
        }
    }

    private void doGoodsBargain(String gid, String price) {
        Map<String, String> params = new TreeMap<>();
        params.clear();

        params.put("gid", gid);
        params.put("goods_price", price);

        if (mViewModel != null) {
            mViewModel.doGoodsBargain(params, new OnBaseCallback<CollectionBeanVo>() {
                @Override
                public void onSuccess(CollectionBeanVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            Toaster.show(data.getMsg());
                            Drawable drawable = getResources().getDrawable(R.mipmap.ic_audit_transaction_good_detail_2);
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
                            tv_collection.setCompoundDrawables(null, drawable, null, null);
                            tv_collection.setCompoundDrawablePadding(-2);
                            tv_collection.setText("已收藏");
                            popWindowDicker.dissmiss();
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }

            });
        }
    }

    private void setRelatedGoodList(List<TradeGoodInfoVo1> tradeGoodInfoBeanList) {
        if (tradeGoodInfoBeanList == null || tradeGoodInfoBeanList.isEmpty()) {
            mLlRelatedGoodList.setVisibility(View.GONE);
        } else {
            mLlRelatedGoodList.setVisibility(View.VISIBLE);

            mMoreAdapter.clear();
            mMoreAdapter.addAllData(tradeGoodInfoBeanList);
            mMoreAdapter.notifyDataSetChanged();
            mTvGameName3.setText("(" + tradeGoodInfoBeanList.size() + ")");
        }
    }

}
