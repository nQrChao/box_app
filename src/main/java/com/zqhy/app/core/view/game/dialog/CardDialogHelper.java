package com.zqhy.app.core.view.game.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.core.tool.utilcode.SizeUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 * @date 2018/12/4
 */

public class CardDialogHelper {


    private Activity     _mActivity;
    private BaseFragment _mFragment;
    private float        density;

    public CardDialogHelper(BaseFragment fragment) {
        _mFragment = fragment;
        this._mActivity = _mFragment.getActivity();
        density = ScreenUtil.getScreenDensity(_mActivity);
    }


    public void showGiftDialog(String card, boolean isFromSDK, String SDKPackageName) {
        CustomDialog cardDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_card, null),
                ScreenUtils.getScreenWidth(_mActivity),
                WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        cardDialog.setCanceledOnTouchOutside(false);
        //        TextView tvGift = cardDialog.findViewById(R.id.tv_gift);
        TextView cardCode = cardDialog.findViewById(R.id.card_code);
        TextView tvCopy = cardDialog.findViewById(R.id.tv_copy);
        TextView tvClose = cardDialog.findViewById(R.id.tv_close);

        /******2017.10.13更新******************************************************/

        View mViewLine = cardDialog.findViewById(R.id.view_line);
        TextView mTvOpenGame = cardDialog.findViewById(R.id.tv_open_game);

        if (isFromSDK) {
            mViewLine.setVisibility(View.GONE);
            mTvOpenGame.setVisibility(View.VISIBLE);

            mTvOpenGame.setOnClickListener(v -> {
                try {
                    Intent intent = _mActivity.getPackageManager().getLaunchIntentForPackage(SDKPackageName);
                    _mActivity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        cardCode.setText(card);

        //        tvGift.setOnClickListener((v) -> {
        //            if (cardDialog != null && cardDialog.isShowing()) {
        //                cardDialog.dismiss();
        //            }
        //            _mFragment.start(GameWelfareFragment.newInstance(1));
        //        });
        tvCopy.setOnClickListener((v) -> {
            if (CommonUtils.copyString(_mActivity, card)) {
                //ToastT.success(_mActivity, "礼包码已复制");
                Toaster.show("礼包码已复制");
                if (cardDialog != null && cardDialog.isShowing()) {
                    cardDialog.dismiss();
                }
            }
        });
        tvClose.setOnClickListener((v) -> {
            if (cardDialog != null && cardDialog.isShowing()) {
                cardDialog.dismiss();
            }
        });
        cardDialog.show();
    }

    public void showSearchCardDialog(String card, boolean isFromSDK, String SDKPackageName) {
        CustomDialog searchCardDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_search_card, null),
                ScreenUtils.getScreenWidth(_mActivity) - SizeUtils.dp2px(_mActivity, 24), WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        TextView mTvCardCode = searchCardDialog.findViewById(R.id.tv_card_code);
        TextView mTvCopy = searchCardDialog.findViewById(R.id.tv_copy);
        TextView mTvCancel = searchCardDialog.findViewById(R.id.tv_cancel);

        View mViewTaoLine = searchCardDialog.findViewById(R.id.view_tao_line);
        TextView mTvTaoOpenGame = searchCardDialog.findViewById(R.id.tv_tao_open_game);

        mTvCancel.setOnClickListener(v -> {
            if (searchCardDialog != null && searchCardDialog.isShowing()) {
                searchCardDialog.dismiss();
            }
        });

        if (isFromSDK) {
            mViewTaoLine.setVisibility(View.GONE);
            mTvTaoOpenGame.setVisibility(View.VISIBLE);

            mTvTaoOpenGame.setOnClickListener(v -> {
                try {
                    Intent intent = _mActivity.getPackageManager().getLaunchIntentForPackage(SDKPackageName);
                    _mActivity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        mTvCopy.setOnClickListener(v -> {
            if (CommonUtils.copyString(_mActivity, card)) {
                //ToastT.success(_mActivity, "复制成功");
                Toaster.show("复制成功");
            }
        });

        mTvCardCode.setText(card);
        searchCardDialog.show();
    }
}
