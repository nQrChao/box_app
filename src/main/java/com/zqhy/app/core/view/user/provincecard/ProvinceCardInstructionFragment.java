package com.zqhy.app.core.view.user.provincecard;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

import com.box.other.immersionbar.ImmersionBar;
import com.zqhy.app.App;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.user.VipMemberTypeVo;
import com.zqhy.app.core.tool.MResource;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.user.provincecard.holder.ProvinceItemHolder;
import com.zqhy.app.core.vm.user.VipMemberViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class ProvinceCardInstructionFragment extends BaseFragment<VipMemberViewModel> {

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "省钱卡说明";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_province_card_instruction;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        View titleBar = findViewById(MResource.getResourceId(activity, "id", "titleBar_Layout"));
        ImmersionBar.setTitleBar(this,titleBar);
        //initActionBackBarAndTitle("省钱卡说明");
        showSuccess();
        bindView();
    }

    private TextView mTvTips1;
    private TextView mTvTips2;

    private void bindView() {
        mTvTips1 = findViewById(R.id.tv_tips_1);
        mTvTips2 = findViewById(R.id.tv_tips_2);

        String tips1 = mTvTips1.getText().toString().trim();
        SpannableString ss = new SpannableString(tips1);
        ss.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(ContextCompat.getColor(App.getContext(), R.color.color_377aff));
            }

            @Override
            public void onClick(@NonNull View widget) {
                showTipsDialog(0);
            }
        }, ss.length() - 12, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvTips1.setText(ss);
        mTvTips1.setMovementMethod(LinkMovementMethod.getInstance());

        String tips2 = mTvTips2.getText().toString().trim();
        SpannableString ss2 = new SpannableString(tips2);
        ss2.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(ContextCompat.getColor(App.getContext(), R.color.color_f8505a));
            }

            @Override
            public void onClick(@NonNull View widget) {
                showTipsDialog(1);
            }
        }, 17, 28, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvTips2.setText(ss2);
        mTvTips2.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private AppCompatTextView mTvTitle;
    private AppCompatTextView mTvTips;
    private AppCompatTextView mTvCancel;

    private RecyclerView        mDialogRecyclerView;
    private BaseRecyclerAdapter mDialogAdapter;
    private CustomDialog  dialog;
    private void showTipsDialog(int type) {
        if (dialog == null) {
            dialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_province_instruction, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
            mTvTitle = dialog.findViewById(R.id.tv_title);
            mTvTips = dialog.findViewById(R.id.tv_tips);
            mTvCancel = dialog.findViewById(R.id.tv_cancel);
            mDialogRecyclerView = dialog.findViewById(R.id.recycler_view_province_dialog);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mDialogRecyclerView.setLayoutManager(linearLayoutManager);
            mDialogAdapter = new BaseRecyclerAdapter.Builder<>()
                    .bind(VipMemberTypeVo.DataBean.class, new ProvinceItemHolder(_mActivity, true))
                    .build().setTag(R.id.tag_fragment, _mActivity)
                    .setTag(R.id.tag_fragment, this);
            mDialogRecyclerView.setAdapter(mDialogAdapter);

            mTvCancel.setOnClickListener(view -> {
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
            });
        }
        if (type == 0){
            mTvTitle.setText("不可用券名单");
            mTvTips.setVisibility(View.GONE);
        }else {
            mTvTitle.setText("温馨提示");
            mTvTips.setVisibility(View.VISIBLE);
        }
        dialog.show();
    }
}
