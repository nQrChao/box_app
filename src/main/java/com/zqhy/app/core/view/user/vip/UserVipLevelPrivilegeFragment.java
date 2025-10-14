package com.zqhy.app.core.view.user.vip;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.zqhy.app.base.BaseViewPagerFragment;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leeham2734
 * @date 2020/11/11-10:34
 * @description
 */
public class UserVipLevelPrivilegeFragment extends BaseViewPagerFragment {


    public static UserVipLevelPrivilegeFragment newInstance(int pId) {
        UserVipLevelPrivilegeFragment fragment = new UserVipLevelPrivilegeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pId", pId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int[] pIds = new int[]{9, 10, 11, 12, 3, 4, 5, 6, 7, 8};

    private String[] titles    = new String[]{
            "专属标识", "签到特权", "任务特权", "等级加速", "纪念日礼包", "购号补贴", "升级奖励", "会员礼包", "生日礼包", "节日礼包"
    };
    private String[] subTitles = new String[]{
            "尊贵身份标识，与众不同", "签到双倍积分，兑换更多好礼", "每日任务额外积分，兑换更多好礼", "加速升级成为更好的你", "尊贵身份标识，与众不同", "随时随地，开心换新游", "每一次成长，都有惊喜", "每月8日，VIP专属福利任性领", "每一年生日都有专属礼物", "暖心礼包，陪你度过每一个节日"
    };


    @Override
    protected String[] createPageTitle() {
        return titles;
    }

    @Override
    protected List<Fragment> createFragments() {
        List<Fragment> list = new ArrayList<>();
        list.add(VipPrivilegeItemFragment.newInstance(pIds[0], titles[0], subTitles[0]));
        list.add(VipPrivilegeItemFragment.newInstance(pIds[1], titles[1], subTitles[1]));
        list.add(VipPrivilegeItemFragment.newInstance(pIds[2], titles[2], subTitles[2]));
        list.add(VipPrivilegeItemFragment.newInstance(pIds[3], titles[3], subTitles[3]));
        list.add(VipPrivilegeItemFragment.newInstance(pIds[4], titles[4], subTitles[4]));
        list.add(VipPrivilegeItemFragment.newInstance(pIds[5], titles[5], subTitles[5]));
        list.add(VipPrivilegeItemFragment.newInstance(pIds[6], titles[6], subTitles[6]));
        list.add(VipPrivilegeItemFragment.newInstance(pIds[7], titles[7], subTitles[7]));
        list.add(VipPrivilegeItemFragment.newInstance(pIds[8], titles[8], subTitles[8]));
        list.add(VipPrivilegeItemFragment.newInstance(pIds[9], titles[9], subTitles[9]));
        return list;
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    private int pId;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            pId = getArguments().getInt("pId");
        }
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("VIP等级特权");
        setAdapter();
        mViewPager.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));
        setPageChoose();
    }


    @Override
    protected View createPagerLayout() {
        View view = LayoutInflater.from(_mActivity).inflate(R.layout.layout_indicator_vip_level_privilege, null);
        mFlIndicatorLayout = view.findViewById(R.id.fl_indicator_layout);
        mIndicator = view.findViewById(R.id.dynamic_pager_indicator);
        return view;
    }

    private void setPageChoose() {
        int index = -1;
        for (int i = 0; i < pIds.length; i++) {
            if (pIds[i] == pId) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            mViewPager.setCurrentItem(index, true);
        }
    }
}
