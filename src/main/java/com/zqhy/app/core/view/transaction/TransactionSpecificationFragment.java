package com.zqhy.app.core.view.transaction;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.newproject.R;


/**
 * A simple {@link Fragment} subclass.
 * @author Administrator
 */
public class TransactionSpecificationFragment extends BaseFragment {


    private TextView tv_kefu;

    public TransactionSpecificationFragment() {
        // Required empty public constructor
    }


    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transaction_specification;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("商品审核规范");
        tv_kefu = ((TextView) findViewById(R.id.tv_kefu));
        tv_kefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goKefuCenter();
            }
        });
    }
}
