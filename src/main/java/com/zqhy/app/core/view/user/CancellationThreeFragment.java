package com.zqhy.app.core.view.user;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.App;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.core.data.model.user.CancellationVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.main.MainActivity;
import com.zqhy.app.core.vm.user.CancellationViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class CancellationThreeFragment extends BaseFragment<CancellationViewModel> implements View.OnClickListener {

    public static CancellationThreeFragment newInstance(String code) {
        CancellationThreeFragment fragment = new CancellationThreeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_cancellation_three;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    protected String getUmengPageName() {
        return "账号注销";
    }

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            code = getArguments().getString("code");
        }
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle("账号注销");
        bindView();
    }

    private TextView tvAgreement;
    private TextView tvNext;
    private String   code;

    private void bindView() {
        tvAgreement = findViewById(R.id.tv_agreement);
        tvNext = findViewById(R.id.tv_next);
        tvAgreement.setOnClickListener(this);
        tvNext.setOnClickListener(this);

        SpannableString ss = new SpannableString("我已阅读并同意“注销协议”");
        ss.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(ContextCompat.getColor(App.getContext(), R.color.color_2a78f6));
            }

            @Override
            public void onClick(@NonNull View widget) {
                //注销协议
                goCancellationAgreement();
            }
        }, 7, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvAgreement.setText(ss);
        tvAgreement.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_agreement:
                if(tvAgreement.isSelected()){
                    tvAgreement.setSelected(false);
                    tvNext.setSelected(false);
                }else{
                    tvAgreement.setSelected(true);
                    tvNext.setSelected(true);
                }
                break;
            case R.id.tv_next:
                if (!tvAgreement.isSelected()){
                    Toaster.show( _mActivity.getResources().getString(R.string.string_cancellation_agreement_tips));
                    return;
                }
                userCancel();
                break;
            default:
                break;
        }
    }

    /*
     * 注销用户
     */
    private void userCancel(){
        UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
        if (mViewModel != null) {
            mViewModel.userCancel(userInfo.getUid(), userInfo.getToken(), code, new OnBaseCallback<CancellationVo>() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    loading("");
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onSuccess(CancellationVo baseVo) {
                    if (baseVo != null) {
                        if (baseVo.isStateOK()) {
                            showTipsDialog();
                        } else {
                            Toaster.show( baseVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    /**
     * 注销协议
     */
    public void goCancellationAgreement() {
        Intent intent = new Intent(_mActivity, BrowserActivity.class);
        intent.putExtra("url", AppConfig.APP_CANCELLATION_AGREEMENT);
        startActivity(intent);
    }

    private CustomDialog dialog;
    private void showTipsDialog(){
        dialog = new CustomDialog(activity,
                LayoutInflater.from(activity).inflate(R.layout.dialog_cancellation_tips2, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER, com.zqhy.app.core.R.style.sheetdialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        TextView tvTitle = dialog.findViewById(R.id.tv_title);
        TextView tvContentTitle = dialog.findViewById(R.id.tv_content_title);
        TextView tvContent = dialog.findViewById(R.id.tv_content);
        TextView tvConfirm = dialog.findViewById(R.id.tv_confirm);
        tvConfirm.setOnClickListener(view -> {
            dialog.dismiss();
            //退出登录 跳转首页
            UserInfoModel.getInstance().logout();
            startActivity(new Intent(_mActivity, MainActivity.class));
        });
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }
}
