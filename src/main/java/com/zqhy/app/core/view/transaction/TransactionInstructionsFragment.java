package com.zqhy.app.core.view.transaction;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.view.main.MainActivity;
import com.zqhy.app.newproject.R;


/**
 * A simple {@link Fragment} subclass.
 *
 * @author Administrator
 */
public class TransactionInstructionsFragment extends BaseFragment {
    /**
     * @param title
     * @param type  0 不显示去首页 1显示去首页按钮
     * @return
     */
    public static TransactionInstructionsFragment newInstance(String title, int type) {
        TransactionInstructionsFragment fragment = new TransactionInstructionsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private TextView tv_kefu;
    private TextView tv_back_home;

    public TransactionInstructionsFragment() {
        // Required empty public constructor
    }


    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transaction_instructions;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    String title = null;
    int type = 0;
    private static final int BACK_HOME = 0x999;
    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        if (getArguments() != null) {
            title = getArguments().getString("title");
            type = getArguments().getInt("type");
        }
        if (title != null) {
            initActionBackBarAndTitle(title);
        } else {
            initActionBackBarAndTitle("登录说明");
        }
        tv_kefu = ((TextView) findViewById(R.id.tv_kefu));
        tv_back_home = ((TextView) findViewById(R.id.tv_back_home));

        switch (type) {
            case 0:
                tv_back_home.setVisibility(View.GONE);
                break;
            case 1:
                tv_back_home.setVisibility(View.VISIBLE);
                break;
        }
        tv_back_home.setOnClickListener(view -> {
//            startFragment(new TransactionMainFragment1());
            Intent intent = new Intent(_mActivity, MainActivity.class);
            intent.putExtra("page_type", 6);
            _mActivity.startActivity(intent);
        });
        tv_kefu.setOnClickListener(view -> {
            goKefuCenter();
        });
    }
}
