package com.zqhy.app.core.view.rebate;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.kefu.KefuInfoDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.vm.kefu.KefuViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class RebateMainFragment extends BaseFragment<KefuViewModel> implements View.OnClickListener {

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "返利申请页";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_rebate_main;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("自助申请");
        showSuccess();
        bindView();
        getKefuInfo();
    }

    private RelativeLayout mFlRewardBt;
    private ImageView      mImgRewardBt;
    private View           mViewRewardBt;
    private TextView       mTvRewardBt;
    private RelativeLayout mFlRewardDiscount;
    private ImageView      mImgRewardDiscount;
    private View           mViewRewardDiscount;
    private TextView       mTvRewardDiscount;
    private RelativeLayout mFlRewardH5;
    private ImageView      mImgRewardH5;
    private View           mViewRewardH5;
    private TextView       mTvRewardH5;
    private RelativeLayout mLlKefu;
    private TextView       mTvKefuQq;
    private TextView       mTvTsEmail;

    private void bindView() {
        mFlRewardBt = findViewById(R.id.fl_reward_bt);
        mImgRewardBt = findViewById(R.id.img_reward_bt);
        mViewRewardBt = findViewById(R.id.view_reward_bt);
        mTvRewardBt = findViewById(R.id.tv_reward_bt);
        mFlRewardDiscount = findViewById(R.id.fl_reward_discount);
        mImgRewardDiscount = findViewById(R.id.img_reward_discount);
        mViewRewardDiscount = findViewById(R.id.view_reward_discount);
        mTvRewardDiscount = findViewById(R.id.tv_reward_discount);
        mFlRewardH5 = findViewById(R.id.fl_reward_h5);
        mImgRewardH5 = findViewById(R.id.img_reward_h5);
        mViewRewardH5 = findViewById(R.id.view_reward_h5);
        mTvRewardH5 = findViewById(R.id.tv_reward_h5);
        mLlKefu = findViewById(R.id.ll_kefu);
        mTvKefuQq = findViewById(R.id.tv_kefu_qq);
        mTvTsEmail = findViewById(R.id.tv_ts_email);


        mFlRewardBt.setOnClickListener(this);
        mFlRewardDiscount.setOnClickListener(this);
        mFlRewardH5.setOnClickListener(this);

        mLlKefu.setOnClickListener(this);


        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(6 * density);
        gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        gd.setColors(new int[]{Color.parseColor("#FF9900"), Color.parseColor("#FF6600")});
        mTvKefuQq.setBackground(gd);

        setRewardBt();
        setRewardDiscount();
        setRewardH5();
    }


    private void setRewardBt() {
        GradientDrawable gd1 = new GradientDrawable();
        float radius = 6 * density;
        gd1.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, radius, radius});
        gd1.setColor(ContextCompat.getColor(_mActivity, R.color.transparent));
        gd1.setStroke((int) (1 * density), Color.parseColor("#D61028"));

        mTvRewardBt.setBackground(gd1);


        GradientDrawable gd2 = new GradientDrawable();
        gd2.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, radius, radius});
        gd2.setColor(Color.parseColor("#F5485B"));

        mViewRewardBt.setBackground(gd2);

    }

    private void setRewardDiscount() {
        GradientDrawable gd1 = new GradientDrawable();
        float radius = 6 * density;
        gd1.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, radius, radius});
        gd1.setColor(ContextCompat.getColor(_mActivity, R.color.transparent));
        gd1.setStroke((int) (1 * density), Color.parseColor("#BC47FF"));

        mTvRewardDiscount.setBackground(gd1);


        GradientDrawable gd2 = new GradientDrawable();
        gd2.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, radius, radius});
        gd2.setColor(Color.parseColor("#9645FE"));

        mViewRewardDiscount.setBackground(gd2);
    }

    private void setRewardH5() {
        GradientDrawable gd1 = new GradientDrawable();
        float radius = 6 * density;
        gd1.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, radius, radius});
        gd1.setColor(ContextCompat.getColor(_mActivity, R.color.transparent));
        gd1.setStroke((int) (1 * density), Color.parseColor("#3E85FF"));

        mTvRewardH5.setBackground(gd1);


        GradientDrawable gd2 = new GradientDrawable();
        gd2.setCornerRadii(new float[]{0, 0, radius, radius, 0, 0, radius, radius});
        gd2.setColor(Color.parseColor("#36AEFF"));

        mViewRewardH5.setBackground(gd2);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_reward_bt:
                start(RebateListFragment.newInstance(RebateListFragment.REBATE_TYPE_BT));
                break;
            case R.id.fl_reward_discount:
                start(RebateListFragment.newInstance(RebateListFragment.REBATE_TYPE_DISCOUNT));
                break;
            case R.id.fl_reward_h5:
                start(RebateListFragment.newInstance(RebateListFragment.REBATE_TYPE_H5));
                break;
            case R.id.ll_kefu:
                //goKefuMain();
                //客服在线
                goKefuCenter();
                break;
            default:
                break;
        }
    }


    private void getKefuInfo() {
        if (mViewModel != null) {
            mViewModel.getKefuInfo(new OnBaseCallback<KefuInfoDataVo>() {
                @Override
                public void onSuccess(KefuInfoDataVo data) {
                    if (data != null && data.isStateOK() && data.getData() != null) {
                        String ts_email = data.getData().getTs_email();
                        if (!TextUtils.isEmpty(ts_email)) {
                            mTvTsEmail.setVisibility(View.VISIBLE);
                            mTvTsEmail.setText("投诉邮箱：" + ts_email);
                        } else {
                            mTvTsEmail.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }
    }
}
