package com.zqhy.app.core.view.transaction.record;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseViewPagerFragment;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator 弃用 20220627
 */
public class TransactionRecordFragment extends BaseViewPagerFragment<TransactionViewModel> {
    @Override
    protected String[] createPageTitle() {
        return new String[]{"全部", "已购买", "出售中", "已出售"};
    }

    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected List<Fragment> createFragments() {
        fragmentList.add(TransactionRecordListFragment.newInstance("user_all"));
        fragmentList.add(TransactionRecordListFragment.newInstance("user_buy"));
        fragmentList.add(TransactionRecordListFragment.newInstance("user_selling"));
        fragmentList.add(TransactionRecordListFragment.newInstance("user_sold"));
        return fragmentList;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "交易记录页";
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    private boolean isAnyDataChange = false;

    private int selectedPosition = 0;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("交易记录");
        showSuccess();
        setAdapter();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        _mActivity.finish();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            isAnyDataChange = true;
        }
        if (fragmentList != null) {
            try {
                BaseFragment fragment = (BaseFragment) fragmentList.get(selectedPosition);
                fragment.onFragmentResult(requestCode, resultCode, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
