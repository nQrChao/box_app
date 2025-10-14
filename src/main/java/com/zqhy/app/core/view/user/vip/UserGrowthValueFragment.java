package com.zqhy.app.core.view.user.vip;

import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.user.TopUpFragment;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author leeham2734
 * @date 2020/11/10-15:53
 * @description
 */
public class UserGrowthValueFragment extends BaseFragment {

    public static UserGrowthValueFragment newInstance(UserInfoVo.VipInfoVo vipInfoVo) {
        UserGrowthValueFragment fragment = new UserGrowthValueFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("vipInfoVo", vipInfoVo);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user_growth_value;
    }

    @Override
    public int getContentResId() {
        return R.id.swipe_refresh_layout;
    }

    private UserInfoVo.VipInfoVo vipInfoVo;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            vipInfoVo = (UserInfoVo.VipInfoVo) getArguments().getSerializable("vipInfoVo");
        }
        super.initView(state);
        initActionBackBarAndTitle(getAppVipMonthName() + "VIP");
        showSuccess();
        bindViews();
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView           mTvUserGrowthValue;
    private TextView           mTvUserGrowthValueDetail;
    private FlexboxLayout      mFlexBoxLayout;
    private TextView           mTv1;
    private TextView           mTv2;
    private FlexboxLayout      mFlexBoxLayout1;
    private FlexboxLayout      mFlexBoxLayout2;
    private FlexboxLayout      mFlexBoxLayout3;


    private void bindViews() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setEnabled(false);
        mTvUserGrowthValue = findViewById(R.id.tv_user_growth_value);
        mTvUserGrowthValueDetail = findViewById(R.id.tv_user_growth_value_detail);
        mFlexBoxLayout = findViewById(R.id.flex_box_layout);
        mTv1 = findViewById(R.id.tv_1);
        mTv2 = findViewById(R.id.tv_2);
        mFlexBoxLayout1 = findViewById(R.id.flex_box_layout_1);
        mFlexBoxLayout2 = findViewById(R.id.flex_box_layout_2);
        mFlexBoxLayout3 = findViewById(R.id.flex_box_layout_3);

        if (vipInfoVo != null) {
            mTvUserGrowthValue.setText(String.valueOf(vipInfoVo.getVip_score()));
        }
        addVipLevelLayout();
        addGrowthValueItemLayout();

        mTvUserGrowthValueDetail.setOnClickListener(view -> {
            startFragment(new UserGrowthValueDetailFragment());
        });
    }

    private void addVipLevelLayout() {
        int userLevel = vipInfoVo == null ? 0 : vipInfoVo.getVip_level();
        mFlexBoxLayout.removeAllViews();
        mFlexBoxLayout.addView(createVipLevelItemView(0, 0, userLevel));
        mFlexBoxLayout.addView(createVipLevelItemView(200, 1, userLevel));
        mFlexBoxLayout.addView(createVipLevelItemView(500, 2, userLevel));
        mFlexBoxLayout.addView(createVipLevelItemView(2000, 3, userLevel));
        mFlexBoxLayout.addView(createVipLevelItemView(4000, 4, userLevel));
        mFlexBoxLayout.addView(createVipLevelItemView(8000, 5, userLevel));
        mFlexBoxLayout.addView(createVipLevelItemView(15000, 6, userLevel));
        mFlexBoxLayout.addView(createVipLevelItemView(30000, 7, userLevel));

    }

    private View createVipLevelItemView(int growthValueCount, int vipLevel, int userLevel) {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.item_vip_level_growth_value, null);

        LinearLayout mLlCursor = itemView.findViewById(R.id.ll_cursor);
        TextView mTvUserVipLevelGrowthValue = itemView.findViewById(R.id.tv_user_vip_level_growth_value);
        View mViewLine = itemView.findViewById(R.id.view_line);
        TextView mTvUserVipLevel = itemView.findViewById(R.id.tv_user_vip_level);

        boolean isUserCurrent = vipLevel == userLevel;
        mLlCursor.setVisibility(isUserCurrent ? View.VISIBLE : View.INVISIBLE);
        if (isUserCurrent) {
            mTvUserVipLevel.setBackgroundResource(R.drawable.ts_shape_big_radius_d8b068);
        } else {
            mTvUserVipLevel.setBackground(null);
        }
        mTvUserVipLevelGrowthValue.setText(CommonUtils.formatNumberType2(growthValueCount));
        mTvUserVipLevel.setText("V" + vipLevel);

        int lineHeight = 0;
        switch (vipLevel) {
            case 0:
                lineHeight = 0;
                break;
            case 1:
                lineHeight = ScreenUtil.dp2px(_mActivity, 11);
                break;
            case 2:
                lineHeight = ScreenUtil.dp2px(_mActivity, 25);
                break;
            case 3:
                lineHeight = ScreenUtil.dp2px(_mActivity, 40);
                break;
            case 4:
                lineHeight = ScreenUtil.dp2px(_mActivity, 60);
                break;
            case 5:
                lineHeight = ScreenUtil.dp2px(_mActivity, 80);
                break;
            case 6:
                lineHeight = ScreenUtil.dp2px(_mActivity, 100);
                break;
            case 7:
                lineHeight = ScreenUtil.dp2px(_mActivity, 120);
                break;
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewLine.getLayoutParams();
        params.height = lineHeight;
        mViewLine.setLayoutParams(params);

        return itemView;
    }

    private void addGrowthValueItemLayout() {
        mFlexBoxLayout1.removeAllViews();
        mFlexBoxLayout1.addView(createGrowthValueItemView(R.mipmap.ic_vip_growth_value_1_1, "新游戏首充", "每天最多+75", "成长值+15"));
        mFlexBoxLayout1.addView(createGrowthValueItemView(R.mipmap.ic_vip_growth_value_1_2, "充值消费", "每日最多1500", "实付1元成长值+1 >", true, view -> {
            if (checkLogin()) {
                //充值平台币
                startFragment(TopUpFragment.newInstance());
            }
        }));
        mFlexBoxLayout1.addView(createGrowthValueItemView(R.mipmap.ic_vip_growth_value_1_3, "第一次充值", "固定成长值", "成长值+30", false));

        mFlexBoxLayout2.removeAllViews();
        mFlexBoxLayout2.addView(createGrowthValueItemView(R.mipmap.ic_vip_growth_value_2_1, "登录游戏", "每天最多+3", "成长值+3"));
        mFlexBoxLayout2.addView(createGrowthValueItemView(R.mipmap.ic_vip_growth_value_2_2, "登录APP", "每天最多+3", "成长值+3"));
        mFlexBoxLayout2.addView(createGrowthValueItemView(R.mipmap.ic_vip_growth_value_2_3, "登录新游戏", "每天最多+3", "成长值+3"));
        mFlexBoxLayout2.addView(createGrowthValueItemView(R.mipmap.ic_vip_growth_value_2_4, "参与活动", "无上限", "参与平台活动，获取成长值", false));

        mFlexBoxLayout3.removeAllViews();
        mFlexBoxLayout3.addView(createGrowthValueItemView(R.mipmap.ic_vip_growth_value_3_1, "绑定手机", "固定成长值", "成长值+20"));
        mFlexBoxLayout3.addView(createGrowthValueItemView(R.mipmap.ic_vip_growth_value_3_2, "实名认证", "固定成长值", "成长值+30", false));

    }

    private View createGrowthValueItemView(int iconRes, String title, String subTitle, String description) {
        return createGrowthValueItemView(iconRes, title, subTitle, description, true, null);
    }

    private View createGrowthValueItemView(int iconRes, String title, String subTitle, String description, boolean showLine) {
        return createGrowthValueItemView(iconRes, title, subTitle, description, showLine, null);
    }

    private View createGrowthValueItemView(int iconRes, String title, String subTitle, String description, boolean showLine, View.OnClickListener onClickListener) {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.item_layout_growth_value_description, null);
        ImageView mIvIcon = itemView.findViewById(R.id.iv_icon);
        TextView mTvTitle = itemView.findViewById(R.id.tv_title);
        TextView mTvSubTitle = itemView.findViewById(R.id.tv_sub_title);
        TextView mTvDescription = itemView.findViewById(R.id.tv_description);
        View mViewLine = itemView.findViewById(R.id.view_line);

        mIvIcon.setImageResource(iconRes);
        mTvTitle.setText(title);
        mTvSubTitle.setText(subTitle);
        mTvDescription.setText(description);
        mViewLine.setVisibility(showLine ? View.VISIBLE : View.INVISIBLE);

        itemView.setOnClickListener(onClickListener);
        return itemView;
    }


}
