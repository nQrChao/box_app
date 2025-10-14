package com.zqhy.app.core.view.rebate;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/22
 */

public class RewardEditFragment extends BaseFragment {

    public static RewardEditFragment newInstance(String reward, int maxLength) {
        RewardEditFragment fragment = new RewardEditFragment();
        Bundle bundle = new Bundle();
        bundle.putString("reward", reward);
        bundle.putInt("maxLength", maxLength);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_edit_content;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        bindView();
    }

    private Button mBtnConfirm;
    private EditText mEtGameReward;


    private void bindView() {
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mEtGameReward = findViewById(R.id.et_game_reward);

        initActionBackBarAndTitle("道具奖励");

        String reward = "";
        int maxLength = 0;
        if (getArguments() != null) {
            reward = getArguments().getString("reward", reward);
            maxLength = getArguments().getInt("maxLength");
        }
        mBtnConfirm.setOnClickListener(view -> {
            String mStr = mEtGameReward.getText().toString().trim();
            if (TextUtils.isEmpty(mStr)) {
                mStr = "";
            }

            Bundle bundle = new Bundle();
            bundle.putString("data", mStr);
            setFragmentResult(RESULT_OK, bundle);
            pop();
        });

        mEtGameReward.setText(reward);
        mEtGameReward.setSelection(reward.length());
        if (maxLength > 0) {
            mEtGameReward.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }
    }
}
