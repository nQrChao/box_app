package com.zqhy.app.core.view.transaction.buy;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.newproject.R;


/**
 * A simple {@link Fragment} subclass.
 *
 * @author Administrator
 */
public class TransactionSuccessFragment extends BaseFragment implements View.OnClickListener {


    public static TransactionSuccessFragment newInstance(String gameid, String game_type) {
        TransactionSuccessFragment fragment = new TransactionSuccessFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gameid", gameid);
        bundle.putString("game_type", game_type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public TransactionSuccessFragment() {
        // Required empty public constructor
    }


    private Button mBtnDownloadGame;
    private TextView mTvReturn;

    private int gameid;
    private int game_type;


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_download_game:
                goGameDetail(gameid, game_type);
                break;
            case R.id.tv_return:
                pop();
                break;
            default:
                break;
        }
    }

    @Override
    public void pop() {
        setFragmentResult(RESULT_OK, null);
        super.pop();
    }


    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_transaction_success;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            try {
                gameid = Integer.parseInt(getArguments().getString("gameid"));
                game_type = Integer.parseInt(getArguments().getString("game_type"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.initView(state);
        initActionBackBarAndTitle("购买成功");
        showSuccess();
        bindViews();
    }

    private void bindViews() {
        mBtnDownloadGame = findViewById(R.id.btn_download_game);
        mTvReturn = findViewById(R.id.tv_return);


        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#FFF5EB"));
        gd.setCornerRadius(40 * density);
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
        mBtnDownloadGame.setBackground(gd);

        mBtnDownloadGame.setOnClickListener(this);
        mTvReturn.setOnClickListener(this);
    }
}
