package com.zqhy.app.core.view.user.vip;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.model.user.UserVipInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.user.provincecard.NewProvinceCardFragment;
import com.zqhy.app.core.view.user.provincecard.ProvinceCardFragment;
import com.zqhy.app.core.vm.user.UserViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/11/10-10:32
 * @description
 */
public class UserVipFragment extends BaseFragment<UserViewModel> {
    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user_vip;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    protected String getUmengPageName() {
        return "VIP页面";
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle(getAppVipMonthName() + "VIP");
        setActionBackBar(R.mipmap.ic_actionbar_back_white);
        setTitleBottomLine(View.GONE);
        setTitleColor(ContextCompat.getColor(_mActivity, R.color.white));
        bindViews();
        initData();
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView           mTvUserNickname;
    private TextView           mTvVipCountQa;
    private TextView           mTvUserVipLevelCurrent;
    private ProgressBar        mProgressBarVipCount;
    private TextView           mTvToNextVipLevelCount;
    private RelativeLayout     mRlVipUserInfo2;
    private FlexboxLayout      mFlexBoxLayout1;
    private FlexboxLayout      mFlexBoxLayout2;
    private TextView           mTvKefuEnter;
    private TextView           mTvUserVipLevel;
    private ImageView          mIvUserVipLevel;


    private void bindViews() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mTvUserNickname = findViewById(R.id.tv_user_nickname);
        mTvVipCountQa = findViewById(R.id.tv_vip_count_qa);
        mTvUserVipLevelCurrent = findViewById(R.id.tv_user_vip_level_current);
        mProgressBarVipCount = findViewById(R.id.progress_bar_vip_count);
        mTvToNextVipLevelCount = findViewById(R.id.tv_to_next_vip_level_count);
        mRlVipUserInfo2 = findViewById(R.id.rl_vip_user_info_2);
        mFlexBoxLayout1 = findViewById(R.id.flex_box_layout_1);
        mFlexBoxLayout2 = findViewById(R.id.flex_box_layout_2);
        mTvUserVipLevel = findViewById(R.id.tv_user_vip_level);
        mIvUserVipLevel = findViewById(R.id.iv_user_vip_level);
        mTvKefuEnter = findViewById(R.id.tv_kefu_enter);


        mSwipeRefreshLayout.setProgressViewOffset(true, -20, 100);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_3478f6,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            initData();
        });

        mRlVipUserInfo2.setOnClickListener(view -> {
            if (checkLogin()) {
                startFragment(UserGrowthValueFragment.newInstance(vipInfoVo));
            }
        });
        mTvVipCountQa.setOnClickListener(view -> {
            if (checkLogin()) {
                startFragment(UserGrowthValueFragment.newInstance(vipInfoVo));
            }
        });

        mTvKefuEnter.setOnClickListener(view -> {
            goKefuCenter(true);
        });
    }

    private void initData() {
        if (mViewModel != null) {
            mViewModel.getUserVipInfo(new OnBaseCallback<UserVipInfoVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onSuccess(UserVipInfoVo data) {
                    if (data != null && data.isStateOK()) {
                        setUserInfo();
                        setLevelItemView();
                    }
                }
            });
        }
    }

    private void setLevelItemView() {
        mFlexBoxLayout1.removeAllViews();
        //        mFlexBoxLayout1.addView(createItemView(mFlexBoxLayout1,1, R.mipmap.ic_user_vip_level_1_0, R.mipmap.ic_user_vip_level_1_1, "尊享折扣", 1));
        mFlexBoxLayout1.addView(createItemView(mFlexBoxLayout1, 2, R.mipmap.ic_user_vip_level_2_0, R.mipmap.ic_user_vip_level_2_1, getAppVipMonthName() + "月卡", 0));
        mFlexBoxLayout1.addView(createItemView(mFlexBoxLayout1, 3, R.mipmap.ic_user_vip_level_3_0, R.mipmap.ic_user_vip_level_3_1, "纪念日礼包", 1));
        mFlexBoxLayout1.addView(createItemView(mFlexBoxLayout1, 4, R.mipmap.ic_user_vip_level_4_0, R.mipmap.ic_user_vip_level_4_1, "购号补贴", 1));
        mFlexBoxLayout1.addView(createItemView(mFlexBoxLayout1, 5, R.mipmap.ic_user_vip_level_5_0, R.mipmap.ic_user_vip_level_5_1, "升级奖励", 1));
        mFlexBoxLayout1.addView(createItemView(mFlexBoxLayout1, 6, R.mipmap.ic_user_vip_level_6_0, R.mipmap.ic_user_vip_level_6_1, "会员礼包", 2));
        mFlexBoxLayout1.addView(createItemView(mFlexBoxLayout1, 7, R.mipmap.ic_user_vip_level_7_0, R.mipmap.ic_user_vip_level_7_1, "生日礼包", 5));
        mFlexBoxLayout1.addView(createItemView(mFlexBoxLayout1, 8, R.mipmap.ic_user_vip_level_8_0, R.mipmap.ic_user_vip_level_8_1, "节日礼包", 7));
        mFlexBoxLayout1.addView(createItemView(mFlexBoxLayout1, -1, R.mipmap.ic_user_vip_level_1_0, R.mipmap.ic_user_vip_level_1_1, "尊享折扣", 1));

        mFlexBoxLayout2.removeAllViews();
        mFlexBoxLayout2.addView(createItemView(mFlexBoxLayout2, 9, R.mipmap.ic_user_vip_level_9_0, R.mipmap.ic_user_vip_level_9_1, "专属标识", 1));
        mFlexBoxLayout2.addView(createItemView(mFlexBoxLayout2, 10, R.mipmap.ic_user_vip_level_10_0, R.mipmap.ic_user_vip_level_10_1, "签到特权", 1));
        mFlexBoxLayout2.addView(createItemView(mFlexBoxLayout2, 11, R.mipmap.ic_user_vip_level_11_0, R.mipmap.ic_user_vip_level_11_1, "任务特权", 1));
        mFlexBoxLayout2.addView(createItemView(mFlexBoxLayout2, 12, R.mipmap.ic_user_vip_level_12_0, R.mipmap.ic_user_vip_level_12_1, "等级加速", 1));

    }

    private UserInfoVo.VipInfoVo vipInfoVo;

    private void setUserInfo() {
        if (UserInfoModel.getInstance().isLogined()) {
            mTvUserNickname.setText(UserInfoModel.getInstance().getUserInfo().getUser_nickname());
            vipInfoVo = UserInfoModel.getInstance().getUserInfo().getVip_info();
            if (vipInfoVo != null) {
                mTvVipCountQa.setText("成长值" + vipInfoVo.getVip_score());
                mTvUserVipLevelCurrent.setText("V" + vipInfoVo.getVip_level());

                UserInfoModel.setUserVipLevel(vipInfoVo.getNext_level(), mIvUserVipLevel, mTvUserVipLevel);

                StringBuilder sb = new StringBuilder();
                sb.append("升级");
                int indexStart1 = sb.length();
                sb.append("V").append(String.valueOf(vipInfoVo.getNext_level()));
                int indexEnd1 = sb.length();
                sb.append("   还需要");
                int indexStart2 = sb.length();
                sb.append(String.valueOf(vipInfoVo.getScoreForNextLevel()));
                int indexEnd2 = sb.length();
                sb.append("成长值");

                SpannableString ss = new SpannableString(sb.toString());
                ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_ff0000)), indexStart1, indexEnd1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_ff0000)), indexStart2, indexEnd2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                mTvToNextVipLevelCount.setText(ss);
                mProgressBarVipCount.setMax(100);
                mTvToNextVipLevelCount.setVisibility(View.VISIBLE);
                mProgressBarVipCount.setProgress(vipInfoVo.getPercentageScoreForNextLevel());

                if (vipInfoVo.getVip_level() == 7) {
                    mTvToNextVipLevelCount.setVisibility(View.GONE);
                    mProgressBarVipCount.setProgress(100);
                }

            }
        }
    }

    private View createItemView(ViewGroup root, int itemId, int iconRes0, int iconRes1, String title, int targetLevel) {
        int rowCount = 4;
        int itemViewWidth = (root.getRight() - root.getLeft()) / rowCount;

        LinearLayout layout = new LinearLayout(_mActivity);
        FlexboxLayout.LayoutParams rootParams = new FlexboxLayout.LayoutParams(itemViewWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(rootParams);
        layout.setOrientation(LinearLayout.VERTICAL);

        int userLevel = UserInfoModel.getInstance().getUserVipLevel();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = ScreenUtil.dp2px(_mActivity, 6);
        params.rightMargin = ScreenUtil.dp2px(_mActivity, 6);
        params.gravity = Gravity.CENTER;
        ImageView image = new ImageView(_mActivity);
        image.setImageResource(userLevel >= targetLevel ? iconRes1 : iconRes0);
        layout.addView(image, params);


        TextView tv1 = new TextView(_mActivity);
        tv1.setText(title);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv1.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
        tv1.setTextSize(14);
        tv1.setIncludeFontPadding(false);
        params1.topMargin = ScreenUtil.dp2px(_mActivity, 6);
        params1.gravity = Gravity.CENTER;
        layout.addView(tv1, params1);

        TextView tv2 = new TextView(_mActivity);
        tv2.setText("V" + targetLevel + "解锁");

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv2.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_9b9b9b));
        tv2.setTextSize(14);
        tv2.setIncludeFontPadding(false);
        params2.topMargin = ScreenUtil.dp2px(_mActivity, 4);
        params2.bottomMargin = ScreenUtil.dp2px(_mActivity, 20);
        params2.gravity = Gravity.CENTER;
        layout.addView(tv2, params2);

        if (itemId < 0) {
            layout.setVisibility(View.INVISIBLE);
        }
        layout.setOnClickListener(view -> {
            if (checkLogin()) {
                if (itemId == 2) {
                    //跳转月卡
                    //startFragment(new VipMemberFragment());
                } else {
                    startFragment(UserVipLevelPrivilegeFragment.newInstance(itemId));
                }
            }
            if (itemId == 2) startFragment(new NewProvinceCardFragment());
        });
        return layout;
    }

}
