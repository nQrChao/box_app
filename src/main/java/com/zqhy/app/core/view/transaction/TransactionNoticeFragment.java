package com.zqhy.app.core.view.transaction;

import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class TransactionNoticeFragment extends BaseFragment {
    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transaction_notice;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    protected String getUmengPageName() {
        return "交易须知页";
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("交易须知");
        setTitleColor(ContextCompat.getColor(_mActivity, R.color.white));
        setActionBackBar(R.mipmap.ic_actionbar_back_white);
        setTitleBottomLine(View.GONE);
    }
}
