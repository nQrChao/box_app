package com.zqhy.app.core.view.user.provincecard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.box.common.ui.activity.CommonActivityBrowser;
import com.box.other.hjq.toast.Toaster;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.data.model.user.CanUsGameListInfoVo;
import com.zqhy.app.core.data.model.user.PayInfoVo;
import com.zqhy.app.core.data.model.user.SuperRewardVo;
import com.zqhy.app.core.data.model.user.SuperVipMemberInfoVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.AbsPayBuyFragment;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.core.view.login.LoginActivity;
import com.zqhy.app.core.view.user.CertificationFragment;
import com.zqhy.app.core.view.user.provincecard.holder.NewProvinceGameItemHolder;
import com.zqhy.app.core.view.user.provincecard.holder.NewProvinceItemHolder;
import com.zqhy.app.core.view.user.provincecard.holder.NewProvinceItemHolder1;
import com.zqhy.app.core.view.user.provincecard.holder.ProvinceGameItemHolder;
import com.zqhy.app.core.view.user.welfare.MyCouponsListFragment;
import com.zqhy.app.core.vm.user.VipMemberViewModel;
import com.zqhy.app.glide.GlideCircleTransform;
import com.zqhy.app.glide.GlideRoundTransformNew;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.sp.SPUtils;

import java.util.List;

/**
 * @author leeham
 * @date 2020/3/25-19:37
 * @description 2020.11.09 此页面改名未巴兔月卡
 */
public class NewProvinceCardFragment extends AbsPayBuyFragment<VipMemberViewModel> {

    public static NewProvinceCardFragment newInstance(int type) {
        NewProvinceCardFragment fragment = new NewProvinceCardFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_province_card_new;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "省钱卡";
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private int type;
    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
        super.initView(state);
        bindViews();
        initSuperData();

        SPUtils spUtils = new SPUtils(_mActivity, Constants.SP_COMMON_NAME);
        /*boolean isShowDiscountCardTip = spUtils.getBoolean("isShowDiscountCardTip", false);
        if (!isShowDiscountCardTip){
            spUtils.putBoolean("isShowDiscountCardTip", true);
            showDiscountTipDialog();
        }*/

        if (UserInfoModel.getInstance().isLogined()) {
            long showCanUsGameListDialog = spUtils.getLong("showCanUsGameListDialog", 0);
            if (!CommonUtils.isToday(showCanUsGameListDialog)){
                getCanUsGameList();
            }
        }
    }

    private boolean isRelogin = false;
    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        if (UserInfoModel.getInstance().isLogined()) {
            needRefrash = true;
            isRelogin = true;
            initSuperData();

            SPUtils spUtils = new SPUtils(_mActivity, Constants.SP_COMMON_NAME);
            long showCanUsGameListDialog = spUtils.getLong("showCanUsGameListDialog", 0);
            if (!CommonUtils.isToday(showCanUsGameListDialog)){
                getCanUsGameList();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserInfoModel.getInstance().isLogined()) {
            initSuperData();
        }
    }

    @Override
    protected int getPayAction() {
        return PAY_ACTION_VIP_MEMBER;
    }

    private long fristCardExpiryTime = -1;
    private long fristDiscountCardExpiryTime = -1;
    private String pageType = "province";

    private ImageView mIvBack;
    private TextView mTvInstructionsTop;
    //private ImageView mIvTopBg;
    private LinearLayout mLlProvinceCard;
    private TextView mTvProvinceCard;
    private TextView mTvProvinceCardTips;
    private LinearLayout mLlDiscountCard;
    private TextView mTvDiscountCard;
    private TextView mTvDiscountCardTips;
    //private TextView mTvTips1;
    //private LinearLayout mLlTips2;
    private RecyclerView mRecyclerViewMenu;
    private LinearLayout mLlPayAlipay;
    private ImageView mIvPayAlipay;
    private LinearLayout mLlPayWechat;
    private ImageView mIvPayWechat;
    private TextView mTvPay;
    private TextView mTvInstruction;
    private TextView mTvRecord;
    private View mViewCouponLine;
    private TextView mTvCoupon;
    private LinearLayout mLlBanner;
    private Banner mBanner;
    private LinearLayout mLlGame;
    private TextView mTvGameTitle;
    private RecyclerView mRecyclerViewRecommend;
    private TextView mTvGameMore;

    private BaseRecyclerAdapter mProvinceAdapter;
    private BaseRecyclerAdapter gameAdapter;

    private ViewPager mViewPager;

    private ImageView mIvIcon;
    private TextView mTvName;
    private TextView mTvTips;
    private LinearLayout mLlTips;
    private TextView mTvWefare;
    private TextView mTvWefareData;
    private TextView mTvOpen;

    private void bindViews() {
        mIvBack = findViewById(R.id.iv_back);
        mTvInstructionsTop = findViewById(R.id.tv_instructions_top);
        //mIvTopBg = findViewById(R.id.iv_top_bg);
        mLlProvinceCard = findViewById(R.id.ll_province_card);
        mTvProvinceCard = findViewById(R.id.tv_province_card);
        mTvProvinceCardTips = findViewById(R.id.tv_province_card_tips);
        mLlDiscountCard = findViewById(R.id.ll_discount_card);
        mTvDiscountCard = findViewById(R.id.tv_discount_card);
        mTvDiscountCardTips = findViewById(R.id.tv_discount_card_tips);
        //mTvTips1 = findViewById(R.id.tv_tips_1);
        //mLlTips2 = findViewById(R.id.ll_tips_2);
        mRecyclerViewMenu = findViewById(R.id.recycler_view_menu);
        mLlPayAlipay = findViewById(R.id.ll_pay_alipay);
        mIvPayAlipay = findViewById(R.id.iv_pay_alipay);
        mLlPayWechat = findViewById(R.id.ll_pay_wechat);
        mIvPayWechat = findViewById(R.id.iv_pay_wechat);
        mLlGame = findViewById(R.id.ll_game);
        mTvGameTitle = findViewById(R.id.tv_game_title);
        mRecyclerViewRecommend = findViewById(R.id.recycler_view_recommend);
        mTvRecord = findViewById(R.id.tv_record);
        mViewCouponLine = findViewById(R.id.view_coupon_line);
        mTvCoupon = findViewById(R.id.tv_coupon);
        mTvPay = findViewById(R.id.tv_pay);
        mTvInstruction = findViewById(R.id.tv_instruction);
        mTvGameMore = findViewById(R.id.tv_game_more);

        mLlBanner = findViewById(R.id.ll_banner);
        mBanner = findViewById(R.id.banner);

        mViewPager = findViewById(R.id.view_pager);

        mIvIcon = findViewById(R.id.iv_icon);
        mTvName = findViewById(R.id.tv_name);
        mTvTips = findViewById(R.id.tv_tips);
        mLlTips = findViewById(R.id.ll_tips);
        mTvWefare = findViewById(R.id.tv_welfare);
        mTvWefareData = findViewById(R.id.tv_welfare_data);
        mTvOpen = findViewById(R.id.tv_open);

        //mIvTopBg.setBackgroundResource(R.mipmap.ic_province_card_information_bg_logined);

        PAY_TYPE = PAY_TYPE_ALIPAY;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewMenu.setLayoutManager(linearLayoutManager);
        mProvinceAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(SuperVipMemberInfoVo.DataBean.CardType.class, new NewProvinceItemHolder(_mActivity, false))
                .bind(SuperVipMemberInfoVo.DataBean.DiscountCardType.class, new NewProvinceItemHolder1(_mActivity, false))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
        mRecyclerViewMenu.setAdapter(mProvinceAdapter);

        mProvinceAdapter.setOnItemClickListener((v, position, data) -> {
            if ("province".equals(pageType)){
                for (int i = 0; i < mProvinceAdapter.getData().size(); i++) {
                    if (position == i) {
                        ((SuperVipMemberInfoVo.DataBean.CardType) mProvinceAdapter.getData().get(i)).setSelected(true);
                    } else {
                        ((SuperVipMemberInfoVo.DataBean.CardType) mProvinceAdapter.getData().get(i)).setSelected(false);
                    }
                }
            }else {
                for (int i = 0; i < mProvinceAdapter.getData().size(); i++) {
                    if (position == i) {
                        ((SuperVipMemberInfoVo.DataBean.DiscountCardType) mProvinceAdapter.getData().get(i)).setSelected(true);
                    } else {
                        ((SuperVipMemberInfoVo.DataBean.DiscountCardType) mProvinceAdapter.getData().get(i)).setSelected(false);
                    }
                }
            }
            mProvinceAdapter.notifyDataSetChanged();
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(_mActivity, 4);
        mRecyclerViewRecommend.setLayoutManager(gridLayoutManager);
        gameAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.class, new NewProvinceGameItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);

        mRecyclerViewRecommend.setAdapter(gameAdapter);
        gameAdapter.setOnItemClickListener((v, position, data) -> {
            if ("province".equals(pageType)){
                startFragment(GameDetailInfoFragment.newInstance(superVipMemberInfoVo.getFlb_usage_game().get(position).getGameid(), superVipMemberInfoVo.getFlb_usage_game().get(position).getGame_type()));
            }else {
                startFragment(GameDetailInfoFragment.newInstance(superVipMemberInfoVo.getDiscount_coupon_usage_game().get(position).getGameid(), superVipMemberInfoVo.getDiscount_coupon_usage_game().get(position).getGame_type()));
            }
        });


        mIvBack.setOnClickListener(v -> {
            pop();
        });
        mTvInstructionsTop.setOnClickListener(v -> {
            //BrowserActivity.newInstance(_mActivity, "https://mobile.xiaodianyouxi.com/index.php/Index/view/?id=100000051", true, "", "", false);
            goKefuCenter(2);
        });
        /*mTvTips1.setOnClickListener(v -> {
            if ("province".equals(pageType)){
                BrowserActivity.newInstance(_mActivity, "https://hd.xiaodianyouxi.com/index.php/usage/gold_game", false);
            }else {
                if (superVipMemberInfoVo != null && !TextUtils.isEmpty(superVipMemberInfoVo.getCoupon_game_query_url())){
                    BrowserActivity.newInstance(_mActivity, superVipMemberInfoVo.getCoupon_game_query_url(),false);
                }
            }
        });*/
        mTvRecord.setOnClickListener(view -> {
            if (checkLogin()){
                startFragment(new ProvinceCardRecordFragment());
            }
        });
        mTvInstruction.setOnClickListener(v -> {
            CommonActivityBrowser.Companion.start(_mActivity,"https://mobile.xiaodianyouxi.com/index.php/Index/view/?id=100000051");
            //BrowserActivity.newInstance(_mActivity, "https://mobile.xiaodianyouxi.com/index.php/Index/view/?id=100000051", true, "", "", false);
        });
        mTvCoupon.setOnClickListener(v -> {
            if (checkLogin()) {
                startFragment(new MyCouponsListFragment());
            }
        });
        mTvPay.setOnClickListener(v -> {
            if (checkLogin()) {
                UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
                if (TextUtils.isEmpty(userInfoBean.getReal_name()) || TextUtils.isEmpty(userInfoBean.getIdcard())){
                    Toaster.show("请您先完成实名认证！");
                    startFragment(CertificationFragment.newInstance());
                }else {
                    if ("province".equals(pageType)){
                        SuperVipMemberInfoVo.DataBean.CardType selectDataBean = null;
                        for (int i = 0; i < mProvinceAdapter.getData().size(); i++) {
                            SuperVipMemberInfoVo.DataBean.CardType cardType = (SuperVipMemberInfoVo.DataBean.CardType) mProvinceAdapter.getData().get(i);
                            if (cardType.isSelected()) {
                                selectDataBean = cardType;
                            }
                        }
                        if (selectDataBean != null) buySuperVipMember(selectDataBean.getId(), PAY_TYPE);
                    }else {
                        SuperVipMemberInfoVo.DataBean.DiscountCardType selectDataBean = null;
                        for (int i = 0; i < mProvinceAdapter.getData().size(); i++) {
                            SuperVipMemberInfoVo.DataBean.DiscountCardType cardType = (SuperVipMemberInfoVo.DataBean.DiscountCardType) mProvinceAdapter.getData().get(i);
                            if (cardType.isSelected()) {
                                selectDataBean = cardType;
                            }
                        }
                        if (selectDataBean != null) buyDiscountCard(selectDataBean.getId(), PAY_TYPE);
                    }
                }
            }
        });

        mLlPayAlipay.setOnClickListener(v -> {
            if ("province".equals(pageType)){
                mIvPayAlipay.setImageResource(R.mipmap.ic_vip_member_pay_check);
                mIvPayWechat.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
            }else {
                mIvPayAlipay.setImageResource(R.mipmap.ic_vip_member_pay_check1);
                mIvPayWechat.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
            }
            PAY_TYPE = PAY_TYPE_ALIPAY;
        });
        mLlPayWechat.setOnClickListener(v -> {
            if ("province".equals(pageType)){
                mIvPayAlipay.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
                mIvPayWechat.setImageResource(R.mipmap.ic_vip_member_pay_check);
            }else {
                mIvPayAlipay.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
                mIvPayWechat.setImageResource(R.mipmap.ic_vip_member_pay_check1);
            }

            PAY_TYPE = PAY_TYPE_WECHAT;
        });
        mLlProvinceCard.setOnClickListener(v -> {
            pageType = "province";
            setSelect();
        });
        mLlDiscountCard.setOnClickListener(v -> {
            pageType = "discount";
            setSelect();
        });
        mTvGameMore.setOnClickListener(v -> {
            if ("province".equals(pageType)){
                BrowserActivity.newInstance(_mActivity, "https://hd.xiaodianyouxi.com/index.php/usage/gold_game", true);
            }else {
                if (superVipMemberInfoVo != null && !TextUtils.isEmpty(superVipMemberInfoVo.getCoupon_game_query_url())){
                    BrowserActivity.newInstance(_mActivity, superVipMemberInfoVo.getCoupon_game_query_url(),true);
                }
            }
        });

        mTvOpen.setOnClickListener(v -> {
            if (checkLogin()) {
                if (superVipMemberInfoVo != null) {
                    if (superVipMemberInfoVo.getOpen_money_card().equals("no")) {
                        showBuyVipMemberDialog();
                    } else {
                        getCardReward();
                    }
                }
            }
        });
    }

    private void setUserInfo(){
        if (UserInfoModel.getInstance().isLogined()) {
            UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
            if (!TextUtils.isEmpty(userInfo.getUser_icon())) {
                GlideApp.with(_mActivity)
                        .asBitmap()
                        .load(userInfo.getUser_icon())
                        .placeholder(R.mipmap.ic_user_login_new_sign)
                        .error(R.mipmap.ic_user_login_new_sign)
                        .transform(new GlideCircleTransform(_mActivity, (int) (1 * ScreenUtil.getScreenDensity(_mActivity))))
                        .into(mIvIcon);
            } else {
                mIvIcon.setImageResource(R.mipmap.ic_user_login_new_sign);
            }
            mTvName.setText(userInfo.getUser_nickname());
            mTvName.setTextColor(Color.parseColor("#A6A1DE"));
            mTvName.setOnClickListener(v -> {});

            if (superVipMemberInfoVo != null) {
                if (superVipMemberInfoVo.getOpen_money_card().equals("yes")){
                    mTvWefareData.setText(CommonUtils.formatTimeStamp(superVipMemberInfoVo.getUser_card_expiry_time() * 1000, "yyyy.MM.dd") + " 到期");
                    if (superVipMemberInfoVo.getHas_get_reward().equals("yes")) {
                        mTvTips.setVisibility(View.GONE);
                        mLlTips.setVisibility(View.VISIBLE);
                        mTvOpen.setText("已领取");
                        mTvOpen.setClickable(false);
                        mTvOpen.setTextColor(Color.parseColor("#3E75BC"));
                        mTvOpen.setBackgroundResource(R.mipmap.ic_province_card_top_card_button_1);
                    }else {
                        mTvTips.setVisibility(View.GONE);
                        mLlTips.setVisibility(View.VISIBLE);
                        mTvOpen.setText("领取");
                        mTvOpen.setClickable(true);
                        mTvOpen.setTextColor(Color.parseColor("#FFFFFF"));
                        mTvOpen.setBackgroundResource(R.mipmap.ic_province_card_top_card_button_1);
                    }
                }else {
                    mTvTips.setVisibility(View.VISIBLE);
                    mLlTips.setVisibility(View.GONE);
                    mTvWefareData.setText("未开通");
                    mTvOpen.setText("立即开通");
                    mTvOpen.setTextColor(Color.parseColor("#7D4719"));
                    mTvOpen.setClickable(true);
                    mTvOpen.setBackgroundResource(R.mipmap.ic_province_card_top_card_button_2);
                }
            }else {
                mTvTips.setVisibility(View.VISIBLE);
                mLlTips.setVisibility(View.GONE);
                mTvWefareData.setText("未开通");
                mTvOpen.setText("立即开通");
                mTvOpen.setTextColor(Color.parseColor("#7D4719"));
                mTvOpen.setClickable(true);
                mTvOpen.setBackgroundResource(R.mipmap.ic_province_card_top_card_button_2);
            }
        }else {
            mTvTips.setVisibility(View.VISIBLE);
            mLlTips.setVisibility(View.GONE);
            mIvIcon.setImageResource(R.mipmap.ic_user_login_new);
            mTvName.setText("立即登录");
            mTvName.setTextColor(Color.parseColor("#FFFFFF"));
            mTvName.setOnClickListener(v -> {
                startActivity(new Intent(_mActivity, LoginActivity.class));
            });
        }
    }

    private void initViewPager(){
        needRefrash = false;
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        if ("province".equals(pageType)){
            mViewPager.setCurrentItem(0);
        }else {
            mViewPager.setCurrentItem(1);
        }

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    //mIvTopBg.setImageResource(R.mipmap.ic_province_card_top_bg_logined);
                    pageType = "province";
                    setSelect();
                } else {
                    //mIvTopBg.setImageResource(R.mipmap.ic_province_card_top_bg_logined_1);
                    pageType = "discount";
                    setSelect();
                }
            }
        });
    }

    private void setSelect(){
        if ("province".equals(pageType)){
            mViewPager.setCurrentItem(0);
            mTvProvinceCard.setTextColor(Color.parseColor("#4E76FF"));
            mTvProvinceCardTips.setTextColor(Color.parseColor("#FFFFFF"));
            mTvProvinceCardTips.setBackgroundResource(R.drawable.ts_shape_4e76ff_big_radius);
            mTvDiscountCard.setTextColor(Color.parseColor("#232323"));
            mTvDiscountCardTips.setTextColor(Color.parseColor("#232323"));
            mTvDiscountCardTips.setBackgroundResource(R.drawable.ts_shape_f2f2f2_big_radius);
            //mLlTips2.setVisibility(View.GONE);
            //mTvTips1.setText("先查询支持福利币的游戏");
            if (PAY_TYPE == PAY_TYPE_ALIPAY){
                mIvPayAlipay.setImageResource(R.mipmap.ic_vip_member_pay_check);
                mIvPayWechat.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
            }else {
                mIvPayAlipay.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
                mIvPayWechat.setImageResource(R.mipmap.ic_vip_member_pay_check);
            }
            mTvPay.setBackgroundResource(R.drawable.shape_55c0fe_5571fe_big_radius);
            mTvInstruction.setText("省钱卡说明");
            mViewCouponLine.setVisibility(View.GONE);
            mTvCoupon.setVisibility(View.GONE);
            mTvGameTitle.setText("点击“更多”查询福利币支持游戏");

            if (superVipMemberInfoVo != null && superVipMemberInfoVo.getFlb_usage_game().size()!=0) {
                mLlGame.setVisibility(View.VISIBLE);
                gameAdapter.setDatas(superVipMemberInfoVo.getFlb_usage_game());
                gameAdapter.notifyDataSetChanged();

            }else {
                mLlGame.setVisibility(View.GONE);
            }
            if (superVipMemberInfoVo != null && superVipMemberInfoVo.getCard_type_list()!=null&&superVipMemberInfoVo.getCard_type_list().size()!=0){
                for (int i = 0; i < superVipMemberInfoVo.getCard_type_list().size(); i++) {
                    superVipMemberInfoVo.getCard_type_list().get(i).setSelected(false);
                }
                superVipMemberInfoVo.getCard_type_list().get(0).setSelected(true);
                mProvinceAdapter.setDatas(superVipMemberInfoVo.getCard_type_list());
                mProvinceAdapter.notifyDataSetChanged();
            }
        }else {
            mViewPager.setCurrentItem(1);
            mTvProvinceCard.setTextColor(Color.parseColor("#232323"));
            mTvProvinceCardTips.setTextColor(Color.parseColor("#232323"));
            mTvProvinceCardTips.setBackgroundResource(R.drawable.ts_shape_f2f2f2_big_radius);
            mTvDiscountCard.setTextColor(Color.parseColor("#FF6A36"));
            mTvDiscountCardTips.setTextColor(Color.parseColor("#FFFFFF"));
            mTvDiscountCardTips.setBackgroundResource(R.drawable.ts_shape_ff6a36_big_radius);

            //mTvTips1.setText("先查询支持本代金券的游戏");
            if (PAY_TYPE == PAY_TYPE_ALIPAY){
                mIvPayAlipay.setImageResource(R.mipmap.ic_vip_member_pay_check1);
                mIvPayWechat.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
            }else {
                mIvPayAlipay.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
                mIvPayWechat.setImageResource(R.mipmap.ic_vip_member_pay_check1);
            }
            mTvPay.setBackgroundResource(R.drawable.shape_ffa835_ff6a36_big_radius);
            mTvInstruction.setText("特惠卡说明");
            mViewCouponLine.setVisibility(View.VISIBLE);
            mTvCoupon.setVisibility(View.VISIBLE);
            mTvGameTitle.setText("点击“更多”查询特惠卡支持游戏");

            if (superVipMemberInfoVo != null && superVipMemberInfoVo.getDiscount_card_type_list()!=null&&superVipMemberInfoVo.getDiscount_card_type_list().size()!=0){
                for (int i = 0; i < superVipMemberInfoVo.getDiscount_card_type_list().size(); i++) {
                    superVipMemberInfoVo.getDiscount_card_type_list().get(i).setSelected(false);
                }
                superVipMemberInfoVo.getDiscount_card_type_list().get(0).setSelected(true);
                mProvinceAdapter.setDatas(superVipMemberInfoVo.getDiscount_card_type_list());
                mProvinceAdapter.notifyDataSetChanged();
            }
            if (superVipMemberInfoVo != null && superVipMemberInfoVo.getDiscount_coupon_usage_game().size()!=0) {
                mLlGame.setVisibility(View.VISIBLE);
                gameAdapter.setDatas(superVipMemberInfoVo.getDiscount_coupon_usage_game());
                gameAdapter.notifyDataSetChanged();
            }else {
                mLlGame.setVisibility(View.GONE);
            }

            /*if (superVipMemberInfoVo != null && superVipMemberInfoVo.getDiscount_card_info() != null && superVipMemberInfoVo.getDiscount_card_info().getConfig() != null){
                if ("0".equals(superVipMemberInfoVo.getDiscount_card_info().getConfig().getOpen_exchange())){
                    mLlTips2.setVisibility(View.GONE);
                }else {
                    mLlTips2.setVisibility(View.VISIBLE);
                    mLlTips2.setOnClickListener(v -> {
                        if (checkLogin()){
                            startFragment(new ProvinceCardExchangeFragment());
                        }
                    });
                }
            }*/
        }
    }

    @Override
    protected void onPaySuccess() {
        super.onPaySuccess();
        if (mViewModel != null) {
            mViewModel.refreshUserData();
        }
    }

    @Override
    protected void onPayCancel() {
        super.onPayCancel();
        Log.e("onPayCancel", "onPayCancel");
    }

    private void getCardReward() {
        if (mViewModel != null) {
            mViewModel.getCardReward(new OnBaseCallback<SuperRewardVo>() {
                @Override
                public void onSuccess(SuperRewardVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            needRefrash = true;
                            initSuperData();
                            showSuperDialog(data);
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void getConpon(int discount_coupon_id) {
        if (mViewModel != null) {
            mViewModel.getConpon(discount_coupon_id, new OnBaseCallback<SuperRewardVo>() {
                @Override
                public void onSuccess(SuperRewardVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            needRefrash = true;
                            initSuperData();
                            Toaster.show("优惠券领取成功");
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private SuperVipMemberInfoVo.DataBean superVipMemberInfoVo;
    private boolean isLoad = false;
    private boolean needRefrash = false;
    private void initSuperData() {
        if (mViewModel != null) {
            mViewModel.getMoneyCardBaseInfo(new OnBaseCallback<SuperVipMemberInfoVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }
                @Override
                public void onSuccess(SuperVipMemberInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                superVipMemberInfoVo = data.getData();
                                if (!isLoad){
                                    isLoad = true;
                                    if (superVipMemberInfoVo.getFlb_usage_game().size()!=0) {
                                        mLlGame.setVisibility(View.VISIBLE);
                                        gameAdapter.setDatas(superVipMemberInfoVo.getFlb_usage_game());
                                        gameAdapter.notifyDataSetChanged();
                                    }else {
                                        mLlGame.setVisibility(View.GONE);
                                    }
                                    if (superVipMemberInfoVo.getCard_type_list()!=null&&superVipMemberInfoVo.getCard_type_list().size()!=0){
                                        superVipMemberInfoVo.getCard_type_list().get(0).setSelected(true);
                                        mProvinceAdapter.setDatas(superVipMemberInfoVo.getCard_type_list());
                                        mProvinceAdapter.notifyDataSetChanged();
                                    }

                                    if (superVipMemberInfoVo.getDiscount_card_type_list()!=null&&superVipMemberInfoVo.getDiscount_card_type_list().size()!=0){
                                        superVipMemberInfoVo.getDiscount_card_type_list().get(0).setSelected(true);
                                    }

                                    initViewPager();
                                    setBanner();
                                    setUserInfo();

                                    if (type == 2){
                                        mLlDiscountCard.performClick();
                                    }

                                    fristCardExpiryTime = superVipMemberInfoVo.getUser_card_expiry_time();
                                    if (superVipMemberInfoVo.getDiscount_card_info() != null) fristDiscountCardExpiryTime = superVipMemberInfoVo.getDiscount_card_info().getUser_card_expiry_time();
                                }

                                if (isRelogin){
                                    isRelogin = false;
                                    fristCardExpiryTime = superVipMemberInfoVo.getUser_card_expiry_time();
                                    if (superVipMemberInfoVo.getDiscount_card_info() != null) fristDiscountCardExpiryTime = superVipMemberInfoVo.getDiscount_card_info().getUser_card_expiry_time();
                                }

                                if (UserInfoModel.getInstance().isLogined()) {
                                    if (superVipMemberInfoVo.getUser_card_expiry_time() != 0 && fristCardExpiryTime != superVipMemberInfoVo.getUser_card_expiry_time()) {
                                        if (fristCardExpiryTime == 0) {
                                            showPaySuccessDialog(false);
                                        } else {
                                            showPaySuccessDialog(true);
                                        }
                                        fristCardExpiryTime = superVipMemberInfoVo.getUser_card_expiry_time();
                                        needRefrash = true;
                                    }

                                    if (superVipMemberInfoVo.getDiscount_card_info() != null && superVipMemberInfoVo.getDiscount_card_info().getUser_card_expiry_time() != 0 && fristDiscountCardExpiryTime != superVipMemberInfoVo.getDiscount_card_info().getUser_card_expiry_time()) {
                                        if (fristDiscountCardExpiryTime == 0) {
                                            showPaySuccessDialog(false);
                                        } else {
                                            showPaySuccessDialog(true);
                                        }
                                        fristDiscountCardExpiryTime = superVipMemberInfoVo.getDiscount_card_info().getUser_card_expiry_time();
                                        needRefrash = true;
                                    }
                                    if (needRefrash){
                                        initViewPager();
                                        setUserInfo();
                                    }
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

    private void buySuperVipMember(int card_type, int pay_type) {
        if (mViewModel != null) {
            mViewModel.buySuperVipMember(card_type, pay_type, new OnBaseCallback<PayInfoVo>() {
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
                public void onSuccess(PayInfoVo payInfoVo) {
                    if (payInfoVo != null) {
                        if (payInfoVo.isStateOK()) {
                            if (payInfoVo.getData() != null) {
                                doPay(payInfoVo.getData());
                            }
                        } else {
                            Toaster.show( payInfoVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void buyDiscountCard(int card_type, int pay_type) {
        if (mViewModel != null) {
            mViewModel.buyDiscountCard(card_type, pay_type, new OnBaseCallback<PayInfoVo>() {
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
                public void onSuccess(PayInfoVo payInfoVo) {
                    if (payInfoVo != null) {
                        if (payInfoVo.isStateOK()) {
                            if (payInfoVo.getData() != null) {
                                doPay(payInfoVo.getData());
                            }
                        } else {
                            Toaster.show( payInfoVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void setBanner(){
        if (superVipMemberInfoVo != null && superVipMemberInfoVo.getBanner_list() != null && superVipMemberInfoVo.getBanner_list().size() > 0){
            mLlBanner.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params = mBanner.getLayoutParams();
            if (params != null) {
                params.height = (ScreenUtil.getScreenWidth(_mActivity) - ScreenUtil.dp2px(_mActivity, 30)) * 180 / 710;
                mBanner.setLayoutParams(params);
            }
            int bannerSize = superVipMemberInfoVo.getBanner_list().size();
            //设置banner样式
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置图片加载器
            mBanner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    SuperVipMemberInfoVo.DataBean.MemberRewardBanner bannerVo = (SuperVipMemberInfoVo.DataBean.MemberRewardBanner) path;
                    GlideApp.with(_mActivity)
                            .load(bannerVo.getPic())
                            .placeholder(R.mipmap.img_placeholder_v_load)
                            .error(R.mipmap.img_placeholder_v_load)
                            .transform(new GlideRoundTransformNew(_mActivity, 10))
                            .into(imageView);
                }
            });
            //设置图片集合
            mBanner.setImages(superVipMemberInfoVo.getBanner_list());
            //设置banner动画效果
            mBanner.setBannerAnimation(Transformer.Default);
            //设置自动轮播，默认为true
            if (bannerSize > 1) {
                //设置轮播时间
                mBanner.setDelayTime(5000);
                mBanner.isAutoPlay(true);
            } else {
                mBanner.isAutoPlay(false);
            }
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置指示器位置（当banner模式中有指示器时）
            mBanner.setIndicatorGravity(BannerConfig.RIGHT);
            mBanner.setOnBannerListener(position -> {
                SuperVipMemberInfoVo.DataBean.MemberRewardBanner memberRewardBanner = superVipMemberInfoVo.getBanner_list().get(position);
                if (memberRewardBanner != null) {
                    if (_mActivity != null) {
                        AppBaseJumpInfoBean appBaseJumpInfoBean = new AppBaseJumpInfoBean(memberRewardBanner.getPage_type(), memberRewardBanner.getParam());
                        AppJumpAction appJumpAction = new AppJumpAction(_mActivity);
                        appJumpAction.jumpAction(appBaseJumpInfoBean);
                    }
                }
            });
            //banner设置方法全部调用完毕时最后调用
            mBanner.start();
        }else {
            mLlBanner.setVisibility(View.GONE);
        }
    }

    private void showBuyVipMemberDialog() {
        CustomDialog buyVipMemberDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_buy_province_new, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

        TextView mTvTitle = buyVipMemberDialog.findViewById(R.id.tv_title);
        TextView mTvTitleTwo = buyVipMemberDialog.findViewById(R.id.tv_title_two);
        RecyclerView mRecyclerViewProvince = buyVipMemberDialog.findViewById(R.id.recycler_view_province_dialog);
        LinearLayout mLlPayAlipayDialog = buyVipMemberDialog.findViewById(R.id.ll_pay_alipay);
        ImageView mIvPayAlipayDialog = buyVipMemberDialog.findViewById(R.id.iv_pay_alipay);
        LinearLayout mLlPayWechatDialog = buyVipMemberDialog.findViewById(R.id.ll_pay_wechat);
        ImageView mIvPayWechatDialog = buyVipMemberDialog.findViewById(R.id.iv_pay_wechat);
        TextView mTvConfirm = buyVipMemberDialog.findViewById(R.id.tv_confirm);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewProvince.setLayoutManager(linearLayoutManager);
        BaseRecyclerAdapter mBaseAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(SuperVipMemberInfoVo.DataBean.CardType.class, new NewProvinceItemHolder(_mActivity, false))
                .bind(SuperVipMemberInfoVo.DataBean.DiscountCardType.class, new NewProvinceItemHolder1(_mActivity, false))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
        mRecyclerViewProvince.setAdapter(mBaseAdapter);

        mBaseAdapter.setOnItemClickListener((v, position, data) -> {
            if ("province".equals(pageType)){
                for (int i = 0; i < mBaseAdapter.getData().size(); i++) {
                    if (position == i) {
                        ((SuperVipMemberInfoVo.DataBean.CardType) mBaseAdapter.getData().get(i)).setSelected(true);
                    } else {
                        ((SuperVipMemberInfoVo.DataBean.CardType) mBaseAdapter.getData().get(i)).setSelected(false);
                    }
                }
            }else {
                for (int i = 0; i < mBaseAdapter.getData().size(); i++) {
                    if (position == i) {
                        ((SuperVipMemberInfoVo.DataBean.DiscountCardType) mBaseAdapter.getData().get(i)).setSelected(true);
                    } else {
                        ((SuperVipMemberInfoVo.DataBean.DiscountCardType) mBaseAdapter.getData().get(i)).setSelected(false);
                    }
                }
            }
            mBaseAdapter.notifyDataSetChanged();
        });

        if ("province".equals(pageType)){
            mTvTitle.setText("省钱卡");
            String str = "（指定游戏使用，开通前查询 >）";
            SpannableString spannableString = new SpannableString(str);
            spannableString.setSpan(new ClickableSpan(){
                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    ds.setColor(Color.parseColor("#FF2D3F"));
                }
                @Override
                public void onClick(@NonNull View widget) {
                    BrowserActivity.newInstance(_mActivity, "https://hd.xiaodianyouxi.com/index.php/usage/gold_game", true);
                }
            }, 8, str.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mTvTitleTwo.setText(spannableString);
            mTvTitleTwo.setMovementMethod(LinkMovementMethod.getInstance());
            if (superVipMemberInfoVo.getCard_type_list()!=null&&superVipMemberInfoVo.getCard_type_list().size()!=0){
                for (int i = 0; i < superVipMemberInfoVo.getCard_type_list().size(); i++) {
                    superVipMemberInfoVo.getCard_type_list().get(i).setSelected(false);
                }
                superVipMemberInfoVo.getCard_type_list().get(0).setSelected(true);
                mBaseAdapter.setDatas(superVipMemberInfoVo.getCard_type_list());
                mBaseAdapter.notifyDataSetChanged();
            }
        }else {
            mTvTitle.setText("特惠卡");
            String str = "（指定0.1折游戏使用，开通前查询 >）";
            SpannableString spannableString = new SpannableString(str);
            spannableString.setSpan(new ClickableSpan(){
                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    ds.setColor(Color.parseColor("#FF2D3F"));
                }
                @Override
                public void onClick(@NonNull View widget) {
                    if (superVipMemberInfoVo != null && !TextUtils.isEmpty(superVipMemberInfoVo.getCoupon_game_query_url())){
                        BrowserActivity.newInstance(_mActivity, superVipMemberInfoVo.getCoupon_game_query_url(),true);
                    }
                }
            }, 12, str.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mTvTitleTwo.setText(spannableString);
            mTvTitleTwo.setMovementMethod(LinkMovementMethod.getInstance());
            if (superVipMemberInfoVo.getDiscount_card_type_list()!=null&&superVipMemberInfoVo.getDiscount_card_type_list().size()!=0){
                for (int i = 0; i < superVipMemberInfoVo.getDiscount_card_type_list().size(); i++) {
                    superVipMemberInfoVo.getDiscount_card_type_list().get(i).setSelected(false);
                }
                superVipMemberInfoVo.getDiscount_card_type_list().get(0).setSelected(true);
                mBaseAdapter.setDatas(superVipMemberInfoVo.getDiscount_card_type_list());
                mBaseAdapter.notifyDataSetChanged();
            }
        }

        buyVipMemberDialog.findViewById(R.id.iv_back).setOnClickListener(v -> {
            if (buyVipMemberDialog != null && buyVipMemberDialog.isShowing()) buyVipMemberDialog.dismiss();
        });

        mLlPayAlipayDialog.setOnClickListener(view -> {
            PAY_TYPE = PAY_TYPE_ALIPAY;
            if ("province".equals(pageType)){
                mIvPayAlipayDialog.setImageResource(R.mipmap.ic_vip_member_pay_check);
                mIvPayWechatDialog.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
            }else {
                mIvPayAlipayDialog.setImageResource(R.mipmap.ic_vip_member_pay_check1);
                mIvPayWechatDialog.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
            }
        });
        mLlPayWechatDialog.setOnClickListener(view -> {
            PAY_TYPE = PAY_TYPE_WECHAT;
            if ("province".equals(pageType)){
                mIvPayAlipayDialog.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
                mIvPayWechatDialog.setImageResource(R.mipmap.ic_vip_member_pay_check);
            }else {
                mIvPayAlipayDialog.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
                mIvPayWechatDialog.setImageResource(R.mipmap.ic_vip_member_pay_check1);
            }
        });

        mTvConfirm.setOnClickListener(view -> {
            if (buyVipMemberDialog != null && buyVipMemberDialog.isShowing()) buyVipMemberDialog.dismiss();
            if ("province".equals(pageType)){
                SuperVipMemberInfoVo.DataBean.CardType selectDataBean = null;
                for (int i = 0; i < mBaseAdapter.getData().size(); i++) {
                    SuperVipMemberInfoVo.DataBean.CardType cardType = (SuperVipMemberInfoVo.DataBean.CardType) mBaseAdapter.getData().get(i);
                    if (cardType.isSelected()) {
                        selectDataBean = cardType;
                    }
                }
                if (selectDataBean != null) buySuperVipMember(selectDataBean.getId(), PAY_TYPE);
            }else {
                SuperVipMemberInfoVo.DataBean.DiscountCardType selectDataBean = null;
                for (int i = 0; i < mBaseAdapter.getData().size(); i++) {
                    SuperVipMemberInfoVo.DataBean.DiscountCardType cardType = (SuperVipMemberInfoVo.DataBean.DiscountCardType) mBaseAdapter.getData().get(i);
                    if (cardType.isSelected()) {
                        selectDataBean = cardType;
                    }
                }
                if (selectDataBean != null) buyDiscountCard(selectDataBean.getId(), PAY_TYPE);
            }
        });

        mLlPayAlipay.performClick();
        buyVipMemberDialog.setOnDismissListener(dialog -> {
            if ("province".equals(pageType)){
                if (PAY_TYPE == PAY_TYPE_ALIPAY){
                    mIvPayAlipay.setImageResource(R.mipmap.ic_vip_member_pay_check);
                    mIvPayWechat.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
                }else {
                    mIvPayAlipay.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
                    mIvPayWechat.setImageResource(R.mipmap.ic_vip_member_pay_check);
                }
            }else {
                if (PAY_TYPE == PAY_TYPE_ALIPAY){
                    mIvPayAlipay.setImageResource(R.mipmap.ic_vip_member_pay_check1);
                    mIvPayWechat.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
                }else {
                    mIvPayAlipay.setImageResource(R.mipmap.ic_vip_member_pay_uncheck);
                    mIvPayWechat.setImageResource(R.mipmap.ic_vip_member_pay_check1);
                }
            }
            mProvinceAdapter.notifyDataSetChanged();
        });
        buyVipMemberDialog.show();
    }

    private void showPaySuccessDialog(boolean isRenewal) {
        CustomDialog voucherDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_province_pay_success, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        TextView tvTitle = voucherDialog.findViewById(R.id.tv_title);
        if (isRenewal) {
            tvTitle.setText("续费成功");
        } else {
            tvTitle.setText("开通成功");
        }
        voucherDialog.findViewById(R.id.btn_confirm).setOnClickListener(view -> {
            if (voucherDialog != null && voucherDialog.isShowing()) voucherDialog.dismiss();
        });
        voucherDialog.show();
    }

    private void showSuperDialog(SuperRewardVo data) {
        CustomDialog voucherDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_super_province_voucher_success, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        RelativeLayout mRlAdvertising = voucherDialog.findViewById(R.id.rl_advertising);
        ImageView mIvAdvertising = voucherDialog.findViewById(R.id.iv_advertising);
        TextView mTvPrice = voucherDialog.findViewById(R.id.tv_price);
        if (data != null && data.getData() != null) {
            if (data.getData().getReward_type().equals("ptb")) {
                mTvPrice.setText(data.getData().getReward() + "福利币");
            } else if (data.getData().getReward_type().equals("integral")) {
                mTvPrice.setText(data.getData().getReward() + "积分");
            } else if (data.getData().getReward_type().equals("coupon")) {
                mTvPrice.setText(data.getData().getReward() + "代金券");
            }
        }
        if (superVipMemberInfoVo != null && superVipMemberInfoVo.getAd_banner() != null) {
            mIvAdvertising.setVisibility(View.VISIBLE);
            mRlAdvertising.setVisibility(View.VISIBLE);
            GlideApp.with(_mActivity).asBitmap().load(superVipMemberInfoVo.getAd_banner().getPic()).transform(new GlideRoundTransformNew(_mActivity, 8)).into(mIvAdvertising);
            mIvAdvertising.setOnClickListener(v -> {
                new AppJumpAction(_mActivity).jumpAction(new AppBaseJumpInfoBean(superVipMemberInfoVo.getAd_banner().getPage_type(), superVipMemberInfoVo.getAd_banner().getParam()));
            });
            if (voucherDialog != null && voucherDialog.isShowing()) voucherDialog.dismiss();
        }
        voucherDialog.findViewById(R.id.btn_confirm).setOnClickListener(view -> {
            if (voucherDialog != null && voucherDialog.isShowing()) voucherDialog.dismiss();
        });
        voucherDialog.show();
    }

    private void showDiscountTipDialog() {
        CustomDialog voucherDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_super_province_inform, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        SpannableString ss = new SpannableString("　　自3月28日起特惠卡正式合并到省钱卡中，省钱卡已支持在0.1折游戏中使用。\n· 即日起特惠卡将无法开通；\n· 已开通特惠卡老用户剩余时间，将正常转移添加到省钱卡剩余时间上；（已转换记录可在省钱卡开通记录里查询）\n　　有其他疑问，可联系客服咨询。\n　　本活动最终解释权归平台所有。");
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 88, ss.length() - 31, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(new StyleSpan(Typeface.BOLD), 41, ss.length() - 31, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ((TextView) voucherDialog.findViewById(R.id.tv_content)).setText(ss);
        ((TextView) voucherDialog.findViewById(R.id.tv_content)).setMovementMethod(LinkMovementMethod.getInstance());
        voucherDialog.findViewById(R.id.btn_confirm).setOnClickListener(view -> {
            if (voucherDialog != null && voucherDialog.isShowing()) voucherDialog.dismiss();
        });
        voucherDialog.show();
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //加载vp的布局
            View inflate = View.inflate(_mActivity, R.layout.item_province_card_user_top, null);
            LinearLayout mLlUserBg = inflate.findViewById(R.id.ll_user_bg);
            ImageView mIvIcon = inflate.findViewById(R.id.iv_icon);
            TextView mTvNickname = inflate.findViewById(R.id.tv_nickname);
            TextView mTvValidity = inflate.findViewById(R.id.tv_validity);
            TextView mTvTips = inflate.findViewById(R.id.tv_tips);
            TextView mTvTips1 = inflate.findViewById(R.id.tv_tips1);
            TextView mTvRenew = inflate.findViewById(R.id.tv_renew);
            TextView mTvPrice = inflate.findViewById(R.id.tv_price);
            TextView mTvPriceType = inflate.findViewById(R.id.tv_price_type);

            if (position == 0){
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mLlUserBg.getLayoutParams();
                layoutParams.setMargins(ScreenUtil.dp2px(_mActivity, 18), 0, ScreenUtil.dp2px(_mActivity, 10), 0);
                mLlUserBg.setLayoutParams(layoutParams);

                mLlUserBg.setBackgroundResource(R.mipmap.ic_province_card_information_bg_logined);
                mTvPrice.setText("5");
                mTvPriceType.setText("福利币");
                mTvTips.setText("·每日可领，福利币永久有效");
                mTvTips.setTextColor(Color.parseColor("#3C6294"));
                mTvTips1.setText("·福利币使用范围查看 >");
                mTvTips1.setTextColor(Color.parseColor("#2A3FFF"));
            }else {
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mLlUserBg.getLayoutParams();
                layoutParams.setMargins(0, 0, ScreenUtil.dp2px(_mActivity, 18), 0);
                mLlUserBg.setLayoutParams(layoutParams);

                mLlUserBg.setBackgroundResource(R.mipmap.ic_province_card_information_bg_logined_1);
                mTvPrice.setText("600");
                mTvPriceType.setText("无门槛代金券");
                mTvTips.setText("·每日可领，领取后24小时内有效");
                mTvTips.setTextColor(Color.parseColor("#946A3C"));
                mTvTips1.setText("·仅支持0.1折游戏，查询支持游戏 >");
                mTvTips1.setTextColor(Color.parseColor("#AE0000"));
            }
            mTvTips1.setOnClickListener(v -> {
                if (position == 0){
                    BrowserActivity.newInstance(_mActivity, "https://hd.xiaodianyouxi.com/index.php/usage/gold_game", true);
                }else {
                    if (superVipMemberInfoVo != null && !TextUtils.isEmpty(superVipMemberInfoVo.getCoupon_game_query_url())){
                        BrowserActivity.newInstance(_mActivity, superVipMemberInfoVo.getCoupon_game_query_url(),true);
                    }
                }
            });
            if (position == 0){
                if (superVipMemberInfoVo != null) {
                    if (superVipMemberInfoVo.getOpen_money_card().equals("yes")){
                        mTvValidity.setText("有效期至：" + CommonUtils.formatTimeStamp(superVipMemberInfoVo.getUser_card_expiry_time() * 1000, "yyyy-MM-dd"));
                        if (superVipMemberInfoVo.getHas_get_reward().equals("yes")) {
                            mTvRenew.setText("今日\n已领");
                            mTvRenew.setClickable(false);
                            mTvRenew.setTextColor(Color.parseColor("#999999"));
                        }else {
                            mTvRenew.setText("领取\n奖励");
                            mTvRenew.setClickable(true);
                            mTvRenew.setTextColor(Color.parseColor("#4E76FF"));
                        }
                    }else {
                        mTvValidity.setText("有效期至：未开通");
                        mTvRenew.setText("立即\n开通");
                        mTvRenew.setClickable(true);
                        mTvRenew.setTextColor(Color.parseColor("#4E76FF"));
                    }
                }else {
                    mTvValidity.setText("有效期至：未开通");
                    mTvRenew.setText("立即\n开通");
                    mTvRenew.setClickable(true);
                    mTvRenew.setTextColor(Color.parseColor("#4E76FF"));
                }
                mTvRenew.setOnClickListener(v -> {
                    if (checkLogin()) {
                        if (superVipMemberInfoVo != null) {
                            if (superVipMemberInfoVo.getOpen_money_card().equals("no")) {
                                showBuyVipMemberDialog();
                            } else {
                                getCardReward();
                            }
                        }
                    }
                });
            }else {
                if (superVipMemberInfoVo != null && superVipMemberInfoVo.getDiscount_card_info() != null) {
                    if (superVipMemberInfoVo.getDiscount_card_info().getIs_active().equals("yes")){
                        mTvValidity.setText("有效期至：" + CommonUtils.formatTimeStamp(superVipMemberInfoVo.getDiscount_card_info().getUser_card_expiry_time() * 1000, "yyyy-MM-dd"));
                        if (superVipMemberInfoVo.getDiscount_card_info().getHas_get_reward().equals("yes")) {
                            mTvRenew.setText("今日\n已领");
                            mTvRenew.setClickable(false);
                            mTvRenew.setTextColor(Color.parseColor("#999999"));
                        }else {
                            mTvRenew.setText("领取\n奖励");
                            mTvRenew.setClickable(true);
                            mTvRenew.setTextColor(Color.parseColor("#FF6A36"));
                        }
                    }else {
                        mTvValidity.setText("有效期至：未开通");
                        mTvRenew.setText("立即\n开通");
                        mTvRenew.setClickable(true);
                        mTvRenew.setTextColor(Color.parseColor("#FF6A36"));
                    }
                }else {
                    mTvValidity.setText("有效期至：未开通");
                    mTvRenew.setText("立即\n开通");
                    mTvRenew.setClickable(true);
                    mTvRenew.setTextColor(Color.parseColor("#FF6A36"));
                }
                mTvRenew.setOnClickListener(v -> {
                    if (checkLogin()) {
                        if (superVipMemberInfoVo != null && superVipMemberInfoVo.getDiscount_card_info() != null) {
                            if (superVipMemberInfoVo.getDiscount_card_info().getIs_active().equals("no")) {
                                showBuyVipMemberDialog();
                            } else {
                                getConpon(superVipMemberInfoVo.getDiscount_coupon_id());
                            }
                        }
                    }
                });
            }
            if (UserInfoModel.getInstance().isLogined()) {
                UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
                if (!TextUtils.isEmpty(userInfo.getUser_icon())) {
                    GlideApp.with(_mActivity)
                            .asBitmap()
                            .load(userInfo.getUser_icon())
                            .placeholder(R.mipmap.ic_user_login_new_sign)
                            .error(R.mipmap.ic_user_login_new_sign)
                            .transform(new GlideCircleTransform(_mActivity, (int) (1 * ScreenUtil.getScreenDensity(_mActivity))))
                            .into(mIvIcon);
                } else {
                    mIvIcon.setImageResource(R.mipmap.ic_user_login_new_sign);
                }
                mTvNickname.setText(userInfo.getUser_nickname());
            }else {
                mIvIcon.setImageResource(R.mipmap.ic_user_login_new);
                mTvNickname.setText("登录后查看");
            }

            container.addView(inflate);
            return inflate;
        }

        @Override
        public float getPageWidth(int position) {
            return 0.88f;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private void getCanUsGameList() {
        if (mViewModel != null) {
            mViewModel.getCanUsGameList(new OnBaseCallback<CanUsGameListInfoVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                }

                @Override
                public void onSuccess(CanUsGameListInfoVo gameListInfoVo) {
                    if (gameListInfoVo != null) {
                        if (gameListInfoVo.isStateOK()) {
                            if (gameListInfoVo.getData() != null && gameListInfoVo.getData().size() > 0) {
                                showCanUsGameDialog(gameListInfoVo.getData());
                            }
                        }
                    }
                }
            });
        }
    }

    private void showCanUsGameDialog(List<GameInfoVo> gameInfoVos){
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_province_can_use_game, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        RecyclerView gameRecylerView = tipsDialog.findViewById(R.id.recycler_view_game);
        gameRecylerView.setLayoutManager(new GridLayoutManager(_mActivity, 4));
        BaseRecyclerAdapter mGameDialogAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.class, new ProvinceGameItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
        gameRecylerView.setAdapter(mGameDialogAdapter);
        mGameDialogAdapter.setDatas(gameInfoVos);
        mGameDialogAdapter.notifyDataSetChanged();
        mGameDialogAdapter.setOnItemClickListener((v, position, data) -> {
            startFragment(GameDetailInfoFragment.newInstance(((GameInfoVo)data).getGameid(), ((GameInfoVo)data).getGame_type()));
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.findViewById(R.id.btn_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.show();
        SPUtils spUtils = new SPUtils(_mActivity, Constants.SP_COMMON_NAME);
        spUtils.putLong("showCanUsGameListDialog", System.currentTimeMillis());
    }
}