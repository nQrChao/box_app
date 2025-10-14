package com.zqhy.app.core.view.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.box.common.glide.GlideApp;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.browser.BrowserActivity1;
import com.zqhy.app.core.vm.main.MainViewModel;
import com.zqhy.app.newproject.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @date 2018/11/6
 */

public class BrowseModeImageFragment extends BaseFragment<MainViewModel> {

    public static BrowseModeImageFragment newInstance(String imageUrl) {
        BrowseModeImageFragment fragment = new BrowseModeImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("imageUrl", imageUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected String getUmengPageName() {
        return "首页";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_image_browse_mode;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private String imageUrl;
    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            imageUrl = getArguments().getString("imageUrl");
        }
        super.initView(state);
        showSuccess();
        bindView();
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_USER_MINE_STATE;
    }

    private ImageView mIvContent;
    private void bindView() {
        mIvContent = findViewById(R.id.iv_content);
        GlideApp.with(_mActivity).asBitmap()
                .load(imageUrl)
                .placeholder(R.mipmap.img_placeholder_h)
                .error(R.mipmap.img_placeholder_h)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (resource != null){
                            ViewGroup.LayoutParams layoutParams = mIvContent.getLayoutParams();
                            layoutParams.width = ScreenUtil.getScreenWidth(_mActivity);
                            layoutParams.height = resource.getHeight() * layoutParams.width / resource.getWidth();
                            mIvContent.setLayoutParams(layoutParams);
                            mIvContent.setImageBitmap(resource);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable drawable) {

                    }
                });
        findViewById(R.id.tv_quit).setOnClickListener(v -> {
            showRankingDialog();
        });
        mIvContent.setOnClickListener(v -> {
            showRankingDialog();
        });
    }


    private static Map<Integer, String> mTagKeys = new HashMap<>();
    static {
        mTagKeys.put(1, "app_private_yes");
        mTagKeys.put(2, "app_audit_private_yes");
    }
    private CustomDialog rankDialog;
    private void showRankingDialog(){
        if (rankDialog == null) rankDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_ts_private_exit_visitors, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        SpannableString ss = new SpannableString("亲，浏览模式下部分功能将受限，为了您能够体验更好的服务，我们建议您点击【同意协议并退出浏览模式】确认《隐私协议》，并登录使用我们的产品。若您不同意协议，可以选择继续使用浏览模式。浏览模式下，仅能为您提供部分内容的浏览功能。");
        ss.setSpan(new StyleSpan(Typeface.BOLD), 35, 47, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#F51A07"));
            }

            @Override
            public void onClick(@NonNull View widget) {
                //隐私协议
                Intent intent = new Intent(_mActivity, BrowserActivity1.class);
                intent.putExtra("url", AppConfig.APP_AUDIT_PRIVACY_PROTOCOL);
                startActivity(intent);
            }
        }, 50, 56, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ((TextView) rankDialog.findViewById(R.id.tv_integral_balance)).setText(ss);
        ((TextView) rankDialog.findViewById(R.id.tv_integral_balance)).setMovementMethod(LinkMovementMethod.getInstance());

        rankDialog.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            if (rankDialog != null && rankDialog.isShowing()) rankDialog.dismiss();
            _mActivity.finish();
        });
        rankDialog.findViewById(R.id.btn_cancel).setOnClickListener(v -> {
            if (rankDialog != null && rankDialog.isShowing()) rankDialog.dismiss();
        });
        if (rankDialog != null && !rankDialog.isShowing()) rankDialog.show();
    }
}
