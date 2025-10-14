package com.zqhy.app.core.view.transaction;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.main.MainActivity;
import com.zqhy.app.core.view.recycle_new.XhNewRecycleMainFragment;
import com.zqhy.app.core.view.transaction.record.TransactionRecordFragment1;
import com.zqhy.app.core.view.transaction.sell.TransactionSellFragment;
import com.zqhy.app.core.vm.transaction.TransactionViewModel;
import com.zqhy.app.newproject.R;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Administrator
 */
public class TransactionMenuFragment extends BaseFragment<TransactionViewModel> implements View.OnClickListener {

    private LinearLayoutCompat lin_jyxz;
    private ConstraintLayout con_2;
    private ConstraintLayout con_3;
    private ConstraintLayout con_4;
    private ConstraintLayout con_5;
    private ConstraintLayout con_6;

    public static TransactionMenuFragment newInstance() {
        TransactionMenuFragment fragment = new TransactionMenuFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transaction_main_menu;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void start(ISupportFragment toFragment) {
        if (_mActivity instanceof MainActivity) {
            FragmentHolderActivity.startFragmentInActivity(_mActivity, (SupportFragment) toFragment);
        } else {
            if (getParentFragment() != null && getParentFragment() instanceof SupportFragment) {
                ((SupportFragment) getParentFragment()).start(toFragment);
            } else {
                super.start(toFragment);
            }
        }
    }

    @Override
    public void startForResult(ISupportFragment toFragment, int requestCode) {
        if (_mActivity instanceof MainActivity) {
            FragmentHolderActivity.startFragmentForResult(_mActivity, (SupportFragment) toFragment, requestCode);
        } else {
            if (getParentFragment() != null && getParentFragment() instanceof SupportFragment) {
                ((SupportFragment) getParentFragment()).startForResult(toFragment, requestCode);
            } else {
                super.startForResult(toFragment, requestCode);
            }
        }
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        setImmersiveStatusBar(true);
        bindViews();
        initData();
    }


    private void bindViews() {
        lin_jyxz = (LinearLayoutCompat) findViewById(R.id.lin_jyxz);
        con_2 = (ConstraintLayout) findViewById(R.id.con_2);
        con_3 = (ConstraintLayout) findViewById(R.id.con_3);
        con_4 = (ConstraintLayout) findViewById(R.id.con_4);
        con_5 = (ConstraintLayout) findViewById(R.id.con_5);
        con_6 = (ConstraintLayout) findViewById(R.id.con_6);

        lin_jyxz.setOnClickListener(this);
        con_2.setOnClickListener(this);
        con_3.setOnClickListener(this);
        con_4.setOnClickListener(this);
        con_5.setOnClickListener(this);
        con_6.setOnClickListener(this);
    }


    private void bindTopViews() {

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_jyxz:
                BrowserActivity.newInstance(_mActivity, "https://mobile.tsyule.cn/index.php/Index/view/?id=205410");
                break;
            case R.id.con_2:
                //购买
                startFragment(new TransactionMainFragment1());
                break;
            case R.id.con_3:
                //回收
                if (checkLogin()) {
                    startFragment(new XhNewRecycleMainFragment());
                }
                break;
            case R.id.con_4:
                //捡漏
                startFragment(new TransactionOneBuyFragment());
                break;
            case R.id.con_5:
                //出售
//                if (checkLogin()) {
//                    checkTransaction();
//                }
                if (checkLogin()) {
                    start(TransactionSellFragment.newInstance());
                }
                break;
            case R.id.con_6:
                //明细
                if (checkLogin()) {
                    startFragment(new TransactionRecordFragment1());
                }
                break;
            default:
                break;
        }
    }

    private final int action_transaction_record = 0x556;

    private void initData() {

    }

    private void checkTransaction() {
        if (mViewModel != null) {
            mViewModel.checkTransaction(new OnBaseCallback() {
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
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (checkLogin()) {
                                start(TransactionSellFragment.newInstance());
                            }
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    Toaster.show( message);
                }
            });
        }
    }
}
