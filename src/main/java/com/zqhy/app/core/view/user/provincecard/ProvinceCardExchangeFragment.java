package com.zqhy.app.core.view.user.provincecard;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.BaseResponseVo;
import com.zqhy.app.core.data.model.user.ExchangeCouponInfoVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.currency.CurrencyMainFragment;
import com.zqhy.app.core.vm.user.VipMemberViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

import java.text.DecimalFormat;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class ProvinceCardExchangeFragment extends BaseFragment<VipMemberViewModel> {


    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_province_card_exchange;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);

        bindView();
        getExchangeCouponInfo();
    }

    private ImageView mIvBack;
    private TextView mTvInstructionsTop;
    private TextView mTvAmount;
    private TextView mTvDetail;
    private TextView mTvExchangePrice;
    private TextView mTvCouponAmount;
    private TextView mTvCouponExpiry;
    private TextView mTvCouponRange;
    private TextView mTvExchange;

    private void bindView() {
        mIvBack = findViewById(R.id.iv_back);
        mTvInstructionsTop = findViewById(R.id.tv_instructions_top);
        mTvAmount = findViewById(R.id.tv_amount);
        mTvDetail = findViewById(R.id.tv_detail);
        mTvExchangePrice = findViewById(R.id.tv_exchange_price);
        mTvCouponAmount = findViewById(R.id.tv_coupon_amount);
        mTvCouponExpiry = findViewById(R.id.tv_coupon_expiry);
        mTvCouponRange = findViewById(R.id.tv_coupon_range);
        mTvExchange = findViewById(R.id.tv_exchange);

        if (UserInfoModel.getInstance().isLogined()){
            UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String ptbPc = decimalFormat.format(userInfo.getPtb_dc());
            if ("0.00".equals(ptbPc)) ptbPc = "0";
            mTvAmount.setText("我的福利币：" + ptbPc);
        }


        mIvBack.setOnClickListener(v -> pop());
        mTvInstructionsTop.setOnClickListener(v -> {
            goKefuCenter();
        });
        mTvDetail.setOnClickListener(v -> {
            if (checkLogin()){
                startFragment(new CurrencyMainFragment());
            }
        });
        mTvExchange.setOnClickListener(v -> {
            if (checkLogin()){
                if (mExchangeCouponInfoVo != null && mExchangeCouponInfoVo.getData() != null && mExchangeCouponInfoVo.getData().getDiscount_card_info() != null){
                    exchangeCoupon();
                }
            }
        });
    }

    private ExchangeCouponInfoVo mExchangeCouponInfoVo;
    private void getExchangeCouponInfo(){
        if (mViewModel != null) {
            mViewModel.getExchangeCouponInfo(new OnBaseCallback<ExchangeCouponInfoVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }

                @Override
                public void onSuccess(ExchangeCouponInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            mExchangeCouponInfoVo = data;
                            if (mExchangeCouponInfoVo.getData() != null){
                                if (mExchangeCouponInfoVo.getData().getDiscount_card_info() != null && mExchangeCouponInfoVo.getData().getDiscount_card_info().getConfig() != null){
                                    mTvExchangePrice.setText("当前兑换比：" + mExchangeCouponInfoVo.getData().getDiscount_card_info().getConfig().getExchange_price() + "福利币");
                                }
                                if (mExchangeCouponInfoVo.getData().getCoupon_info() != null){
                                    ExchangeCouponInfoVo.DataBean.CouponInfo couponInfo = mExchangeCouponInfoVo.getData().getCoupon_info();
                                    mTvCouponAmount.setText(couponInfo.getPft());
                                    mTvCouponExpiry.setText(couponInfo.getExpiry_label());
                                    mTvCouponRange.setText(couponInfo.getRange());
                                }
                                if (mExchangeCouponInfoVo.getData().getDiscount_card_info() != null){
                                    if ("yes".equals(mExchangeCouponInfoVo.getData().getDiscount_card_info().getHas_exchange())){
                                        mTvExchange.setText("今日已兑");
                                        mTvExchange.setClickable(false);
                                        mTvExchange.setBackgroundResource(R.drawable.shape_b5b5b5_big_radius);
                                    }else {
                                        mTvExchange.setText("立即兑换");
                                        mTvExchange.setClickable(true);
                                        mTvExchange.setBackgroundResource(R.drawable.shape_55c0fe_5571fe_big_radius);
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    private void exchangeCoupon(){
        if (mViewModel != null) {
            mViewModel.exchangeCoupon(new OnBaseCallback<BaseResponseVo>() {
                @Override
                public void onSuccess(BaseResponseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            getExchangeCouponInfo();
                            Toaster.show("兑换成功，请到代金券页面查看！");
                        }else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }
}
