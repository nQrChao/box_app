package com.zqhy.app.core.view.community.integral;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.box.other.hjq.toast.Toaster;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.community.UserIntegralVo;
import com.zqhy.app.core.data.model.community.integral.IntegralMallListVo;
import com.zqhy.app.core.data.model.community.integral.IntegralMallTitleVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.user.AdSwiperListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.community.integral.holder.IntegralMallItemHolder;
import com.zqhy.app.core.view.community.integral.holder.IntegralMallTitleItemHolder;
import com.zqhy.app.core.view.user.welfare.MyCouponsListFragment;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.community.CommunityViewModel;
import com.zqhy.app.glide.GlideRoundTransformNew;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class CommunityIntegralMallFragment extends BaseListFragment<CommunityViewModel> implements View.OnClickListener {

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(IntegralMallListVo.ProductsListVo.class, new IntegralMallItemHolder(_mActivity))
                .bind(IntegralMallTitleVo.class, new IntegralMallTitleItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "积分商城";
    }

    private int userIntegralCount;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("积分商城");
        setTitleBottomLine(View.GONE);
        addHeaderView();
        //        if (mRecyclerView != null) {
        //            mRecyclerView.addItemDecoration(new DividerGridItemDecoration(_mActivity, ContextCompat.getColor(_mActivity, R.color.line_color)));
        //            mRecyclerView.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f5f5f5));
        //        }
        setLoadingMoreEnabled(false);
        setOnItemClickListener((view, position, data) -> {
            if (data != null && data instanceof IntegralMallListVo.ProductsListVo) {
                IntegralMallListVo.ProductsListVo dataBean = (IntegralMallListVo.ProductsListVo) data;
                showIntegralMallExchangeDialog(dataBean);
            }
        });
        initData();
        setUserIntegralCount();
        getAdSwiperList();
    }

    private void setUserIntegralCount() {
        if (UserInfoModel.getInstance().isLogined()) {
            setUserIntegral(UserInfoModel.getInstance().getUserInfo().getIntegral());
        }

    }

    private void setUserIntegral(int count) {
        userIntegralCount = count;
        mTvIntegralCount.setText(String.valueOf(userIntegralCount));
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        initData();
    }

    private void initData() {
        getIntegralMallData();
    }


    private LinearLayout mLlIntegralMall;
    private TextView     mTvIntegralCount;
    private ImageView    mIvIntegralCountRefresh;
    private TextView     mTvIntegralDetail;
    private TextView mTvUserMineCoupon;
    private Banner   mBanner;

    private void addHeaderView() {
        View mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_header_integral_mall, null);

        mLlIntegralMall = mHeaderView.findViewById(R.id.ll_integral_mall);
        mTvIntegralCount = mHeaderView.findViewById(R.id.tv_integral_count);
        mIvIntegralCountRefresh = mHeaderView.findViewById(R.id.iv_integral_count_refresh);
        mTvIntegralDetail = mHeaderView.findViewById(R.id.tv_integral_detail);
        mTvUserMineCoupon = mHeaderView.findViewById(R.id.tv_user_mine_coupon);

        mBanner = mHeaderView.findViewById(R.id.banner);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(14 * density);
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_3478f6));
        mLlIntegralMall.setBackground(gd);

        mIvIntegralCountRefresh.setOnClickListener(this);
        mTvIntegralDetail.setOnClickListener(this);
        mTvUserMineCoupon.setOnClickListener(this);

        mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        addHeaderView(mHeaderView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_integral_count_refresh:
                //积分刷新
                if (checkLogin()) {
                    getCurrentUserIntegral();
                }
                break;
            case R.id.tv_integral_detail:
                //积分明细
                if (checkLogin()) {
                    start(new IntegralDetailFragment());
                }
                break;
            case R.id.tv_user_mine_coupon:
                //我的礼券
                if (checkLogin()) {
                    //start(GameWelfareFragment.newInstance(2));
                    startFragment(new MyCouponsListFragment());
                }
                break;
            default:
                break;
        }
    }


    private CustomDialog integralMallExchangeDialog;

    private ImageView    mIvGoodImage;
    private TextView     mTvGoodTitle;
    private TextView     mTvGoodIntegral;
    private LinearLayout mLlNotEnoughPoints;
    private TextView     mTvProductContent;
    private Button       mBtnCancel;
    private Button       mBtnConfirm;


    private void showIntegralMallExchangeDialog(IntegralMallListVo.ProductsListVo integralGoodInfoBean) {
        if (integralMallExchangeDialog == null) {
            integralMallExchangeDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_integral_mall, null),
                    WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
            mIvGoodImage = integralMallExchangeDialog.findViewById(R.id.iv_good_image);
            mTvGoodTitle = integralMallExchangeDialog.findViewById(R.id.tv_good_title);
            mTvGoodIntegral = integralMallExchangeDialog.findViewById(R.id.tv_good_integral);
            mLlNotEnoughPoints = integralMallExchangeDialog.findViewById(R.id.ll_not_enough_points);
            mTvProductContent = integralMallExchangeDialog.findViewById(R.id.tv_product_content);
            mBtnCancel = integralMallExchangeDialog.findViewById(R.id.btn_cancel);
            mBtnConfirm = integralMallExchangeDialog.findViewById(R.id.btn_confirm);

            mBtnCancel.setOnClickListener(view -> {
                if (integralMallExchangeDialog != null && integralMallExchangeDialog.isShowing()) {
                    integralMallExchangeDialog.dismiss();
                }
            });

            GradientDrawable gd2 = new GradientDrawable();
            gd2.setColor(ContextCompat.getColor(_mActivity, R.color.color_f5f5f5));
            gd2.setCornerRadius(48 * density);
            mLlNotEnoughPoints.setBackground(gd2);
        }

        setDialogViewData(integralGoodInfoBean);
        integralMallExchangeDialog.show();
    }

    private void setDialogViewData(IntegralMallListVo.ProductsListVo integralGoodInfoBean) {
        GlideUtils.loadNormalImage(_mActivity, integralGoodInfoBean.getProduct_pic(), mIvGoodImage);
        mTvGoodTitle.setText(integralGoodInfoBean.getProduct_name());
        mTvGoodIntegral.setText("需要消耗" + integralGoodInfoBean.getPrice() + "积分");

        if (integralGoodInfoBean.getProduct_content() != null) {
            StringBuilder sb = new StringBuilder();
            int count = integralGoodInfoBean.getProduct_content().size();
            int[][] index = new int[count][2];
            for (int i = 0; i < count; i++) {
                IntegralMallListVo.ProductContentVo productContentVo = integralGoodInfoBean.getProduct_content().get(i);
                sb.append(productContentVo.getTitle()).append("：");
                index[i][0] = sb.length();
                sb.append(productContentVo.getContent());
                index[i][1] = sb.length();
                sb.append("\n");
            }

            SpannableString ss = new SpannableString(sb);
            for (int i = 0; i < index.length; i++) {
                ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_9b9b9b)), index[i][0], index[i][1], Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            mTvProductContent.setText(ss);
        }

        GradientDrawable gd2 = new GradientDrawable();
        gd2.setCornerRadius(48 * density);
        int targetIntegralCount = integralGoodInfoBean.getPrice();
        if (userIntegralCount < targetIntegralCount) {
            mLlNotEnoughPoints.setVisibility(View.VISIBLE);
            gd2.setColor(ContextCompat.getColor(_mActivity, R.color.color_c1c1c1));
            mBtnConfirm.setEnabled(false);
        } else {
            mLlNotEnoughPoints.setVisibility(View.GONE);
            gd2.setColor(ContextCompat.getColor(_mActivity, R.color.color_0052ef));
            mBtnConfirm.setEnabled(true);
        }
        mBtnConfirm.setBackground(gd2);

        mBtnConfirm.setOnClickListener(view -> {
            if (integralMallExchangeDialog != null && integralMallExchangeDialog.isShowing()) {
                integralMallExchangeDialog.dismiss();
            }
            productExchange(integralGoodInfoBean.getProduct_id());
        });
    }


    private void getCurrentUserIntegral() {
        if (mViewModel != null) {
            mViewModel.getUserIntegral(new OnBaseCallback<UserIntegralVo>() {

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
                public void onSuccess(UserIntegralVo data) {
                    if (data != null) {
                        if (data.isStateOK() && data.getData() != null) {
                            setUserIntegral(data.getData().getIntegral());
                        }
                    }
                }
            });
        }
    }

    private void getIntegralMallData() {
        if (mViewModel != null) {
            mViewModel.getIntegralMallData(new OnBaseCallback<IntegralMallListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(IntegralMallListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            clearData();
                            if (data.getData() != null) {
                                setUserIntegral(data.getData().getIntegral());
                                if (data.getData().getProducts() != null && !data.getData().getProducts().isEmpty()) {
                                    setLayoutSpanCount(2);

                                    for (IntegralMallListVo.ProductsVo productsVo : data.getData().getProducts()) {
                                        if (productsVo.getProduct_list() != null && !productsVo.getProduct_list().isEmpty()) {
                                            addData(new IntegralMallTitleVo(productsVo.getType_title(), productsVo.getType_description()));
                                            addAllData(productsVo.getProduct_list());
                                        }
                                    }
                                    //                                    addAllData(data.getData().getCoupon_list());
                                } else {
                                    setLayoutSpanCount(1);
                                    addData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                                }
                                notifyData();
                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void getAdSwiperList(){
        if (mViewModel != null) {
            mViewModel.getAdSwiperList(new OnBaseCallback<AdSwiperListVo>() {
                @Override
                public void onSuccess(AdSwiperListVo data) {
                    if (data != null) {
                        if (data.isStateOK() && data.getData() != null) {
                            if (data.getData() != null && data.getData().size() > 0){
                                mBanner.setVisibility(View.VISIBLE);
                                ViewGroup.LayoutParams params = mBanner.getLayoutParams();
                                if (params != null) {
                                    params.height = ScreenUtil.dp2px(_mActivity, 86);
                                    mBanner.setLayoutParams(params);
                                }
                                int bannerSize = data.getData().size();
                                //设置banner样式
                                mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                                //设置图片加载器
                                mBanner.setImageLoader(new ImageLoader() {
                                    @Override
                                    public void displayImage(Context context, Object path, ImageView imageView) {
                                        AdSwiperListVo.AdSwiperBean bannerVo = (AdSwiperListVo.AdSwiperBean) path;
                                        GlideApp.with(_mActivity)
                                                .load(bannerVo.getPic())
                                                .placeholder(R.mipmap.img_placeholder_v_load)
                                                .error(R.mipmap.img_placeholder_v_load)
                                                .transform(new GlideRoundTransformNew(_mActivity, 10))
                                                .into(imageView);
                                    }
                                });
                                //设置图片集合
                                mBanner.setImages(data.getData());
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
                                mBanner.setOnBannerListener(new OnBannerListener() {
                                    @Override
                                    public void OnBannerClick(int position) {
                                        AdSwiperListVo.AdSwiperBean adSwiperBean = data.getData().get(position);
                                        if (adSwiperBean != null) {
                                            if (_mActivity != null) {
                                                AppBaseJumpInfoBean appBaseJumpInfoBean = new AppBaseJumpInfoBean(adSwiperBean.getPage_type(), adSwiperBean.getParam());
                                                AppJumpAction appJumpAction = new AppJumpAction(_mActivity);
                                                appJumpAction.jumpAction(appBaseJumpInfoBean);
                                            }
                                        }
                                    }
                                });
                                //banner设置方法全部调用完毕时最后调用
                                mBanner.start();
                            }else {
                                mBanner.setVisibility(View.GONE);
                            }
                        }else {
                            mBanner.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }
    }

    private void setLayoutSpanCount(int spanCount) {
        if (mRecyclerView != null) {
            ((StaggeredGridLayoutManager) mRecyclerView.getLayoutManager()).setSpanCount(spanCount);
        }
    }

    private void productExchange(int coupon_id) {
        if (mViewModel != null) {
            mViewModel.productExchange(coupon_id, new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            Toaster.show( "兑换成功");
                            initData();
                            mViewModel.refreshUserData();
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }

    protected void getUnEnableGames() {
        showUnEnableGamesDialog(getAppNameByXML(R.string.string_un_limit_game_tips));
    }

    private void showUnEnableGamesDialog(String txt) {
        CustomDialog unEnableGamesDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_un_enable_games, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        ImageView mIvClose = unEnableGamesDialog.findViewById(R.id.iv_close);
        TextView mTvTxt = unEnableGamesDialog.findViewById(R.id.tv_txt);

        mIvClose.setOnClickListener(view -> {
            if (unEnableGamesDialog != null && unEnableGamesDialog.isShowing()) {
                unEnableGamesDialog.dismiss();
            }
        });
        mTvTxt.setText(txt);
        unEnableGamesDialog.show();
    }
}
