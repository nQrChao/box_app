package com.zqhy.app.core.view.kefu;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.kefu.FeedBackTypeListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.vm.kefu.FeedBackViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/11
 */

public class FeedBackFragment extends BaseFragment<FeedBackViewModel> implements View.OnClickListener {

    public static FeedBackFragment newInstance(Boolean isFromSecurity) {
        FeedBackFragment fragment = new FeedBackFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isFromSecurity", isFromSecurity);
        fragment.setArguments(bundle);

        return fragment;
    }

    public static FeedBackFragment newInstance(Boolean isFromSecurity, Boolean isOtherType) {
        FeedBackFragment fragment = new FeedBackFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isFromSecurity", isFromSecurity);
        bundle.putBoolean("isOtherType", isOtherType);
        fragment.setArguments(bundle);

        return fragment;
    }
    private boolean isFromSecurity = false;

    private boolean isOtherType = false;

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_feedback;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            isFromSecurity = getArguments().getBoolean("isFromSecurity");
            isOtherType = getArguments().getBoolean("isOtherType");
        }
        super.initView(state);
        showSuccess();
        if (isOtherType){
            initActionBackBarAndTitle("意见反馈");
        }else {
            initActionBackBarAndTitle("建议反馈");
        }

        bindView();

        if (isOtherType){
            setFeedBackCount(12);
        }else {
            getFeedBackType();
        }

    }

    private TextView mTvSubmit;
    private LinearLayout mLlContentLayout;
    private RadioGroup mRgFeedback;
    private RadioButton mRbFeedback1;
    private RadioButton mRbFeedback2;
    private RadioButton mRbFeedback3;
    private RadioButton mRbFeedback4;
    private EditText mEtUserFeedback;
    private TextView mTvHowToSuggest;
    private LinearLayout mLlInputQq;
    private EditText mTvInputQq;
    private TextView mTvFeedback;

    private String cate_id;

    private void bindView() {
        mTvSubmit = findViewById(R.id.tv_submit);
        mLlContentLayout = findViewById(R.id.ll_content_layout);
        mRgFeedback = findViewById(R.id.rg_feedback);
        mRbFeedback1 = findViewById(R.id.rb_feedback_1);
        mRbFeedback2 = findViewById(R.id.rb_feedback_2);
        mRbFeedback3 = findViewById(R.id.rb_feedback_3);
        mRbFeedback4 = findViewById(R.id.rb_feedback_4);
        mEtUserFeedback = findViewById(R.id.et_user_feedback);
        mTvHowToSuggest = findViewById(R.id.tv_how_to_suggest);
        mLlInputQq = findViewById(R.id.ll_input_qq);
        mTvInputQq = findViewById(R.id.tv_input_qq);
        mTvFeedback = findViewById(R.id.tv_feedback);

        mTvHowToSuggest.setOnClickListener(this);

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
        gd.setCornerRadius(25 * density);
        mTvSubmit.setBackgroundDrawable(gd);
        mTvSubmit.setVisibility(View.VISIBLE);
        mTvSubmit.setOnClickListener(this);

        mRgFeedback.setOnCheckedChangeListener((radioGroup, resId) -> {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                if (radioButton.getId() == resId) {
                    selectRadioButton(radioButton);
                } else {
                    unSelectRadioButton(radioButton);
                }
            }

            mLlInputQq.setVisibility(View.GONE);
            mTvHowToSuggest.setVisibility(View.GONE);
            switch (resId) {
                case R.id.rb_feedback_1:
                    //显示“如何建议”
                    mTvHowToSuggest.setVisibility(View.VISIBLE);
                    cate_id = "1";
                    mEtUserFeedback.setHint("欢迎您对我们提出各种建议！");
                    break;
                case R.id.rb_feedback_2:
                    //填写联系QQ号
                    mLlInputQq.setVisibility(View.VISIBLE);
                    cate_id = "2";
                    mEtUserFeedback.setHint("不能愉快的使用APP功能？快告诉我们！");
                    break;
                case R.id.rb_feedback_3:
                    cate_id = "3";
                    mEtUserFeedback.setHint("联系人工客服处理会更快哦！");
                    break;
                case R.id.rb_feedback_4:
                    cate_id = "4";
                    mEtUserFeedback.setHint("关于个人信息安全的投诉与举报，我们将在15个工作日内回复您！请注意查收APP内消息。");
                    break;
                default:
                    break;
            }
        });
//        if (isFromSecurity){
//            mRgFeedback.check(R.id.rb_feedback_4);
//        }else{
//            mRgFeedback.check(R.id.rb_feedback_1);
//        }

        if (isOtherType){
            mRbFeedback3.setVisibility(View.GONE);
            mRbFeedback4.setVisibility(View.GONE);

            mRgFeedback.check(R.id.rb_feedback_1);
        }else {
            mRbFeedback3.setVisibility(View.VISIBLE);
            mRbFeedback4.setVisibility(View.VISIBLE);

            if (isFromSecurity){
                mRgFeedback.check(R.id.rb_feedback_4);
            }else{
                mRgFeedback.check(R.id.rb_feedback_1);
            }
        }
    }

    private void selectRadioButton(RadioButton radioButton) {
        if (radioButton == null) {
            return;
        }
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(16 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.white));
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
        radioButton.setBackground(gd);
        radioButton.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
    }

    private void unSelectRadioButton(RadioButton radioButton) {
        if (radioButton == null) {
            return;
        }
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(16 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.white));
        gd.setStroke(0, ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
        radioButton.setBackground(gd);
        radioButton.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_999999));
    }

    private void setFeedBackCount(int count) {
        String strCount = " " + String.valueOf(count) + " ";

        SpannableString spannableString = new SpannableString(_mActivity.getResources().getString(R.string.string_app_opinion, strCount));

        RelativeSizeSpan sizeSpan01 = new RelativeSizeSpan(1.3f);
        spannableString.setSpan(sizeSpan01, 2, 2 + strCount.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
        spannableString.setSpan(colorSpan, 2, 2 + strCount.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        mTvFeedback.setVisibility(View.VISIBLE);
        mTvFeedback.setText(spannableString);
    }

    private void clearPager() {
        mEtUserFeedback.getText().clear();
        mEtUserFeedback.setHint("请选择问题类型");
        mTvInputQq.getText().clear();
        mRgFeedback.clearCheck();
        mTvHowToSuggest.setVisibility(View.GONE);
        mLlInputQq.setVisibility(View.GONE);
        cate_id = "";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                if (TextUtils.isEmpty(cate_id)) {
                    Toaster.show( "请选择反馈类型哦~");
                    return;
                }
                String strComment = mEtUserFeedback.getText().toString().trim();
                if (strComment.length() < 15) {
                    Toaster.show( "亲，能否说的再详细一点呢~（≥15字）");
                    return;
                }
                String strQQNum = mTvInputQq.getText().toString().trim();
                if ("2".equals(cate_id)) {
                    if (TextUtils.isEmpty(strQQNum)) {
                        Toaster.show( "请输入您的QQ号");
                        return;
                    }
                }
                commitFeedBack(strComment, strQQNum, cate_id);
                break;
            case R.id.tv_how_to_suggest:
                showHelpDialog();
                break;
            default:
                break;
        }
    }

    private void showHelpDialog() {
        CustomDialog helpDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_app_opinion, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        helpDialog.findViewById(R.id.tv_btn_confirm).setOnClickListener(view -> {
            if (helpDialog != null && helpDialog.isShowing()) {
                helpDialog.dismiss();
            }
        });
        helpDialog.show();
    }

    public void getFeedBackType() {
        if (mViewModel != null) {
            mViewModel.getFeedBackCount(new OnBaseCallback<FeedBackTypeListVo>() {
                @Override
                public void onSuccess(FeedBackTypeListVo feedBackTypeListVo) {
                    if (feedBackTypeListVo != null) {
                        int totalCount = 0;
                        if (feedBackTypeListVo.getData() != null) {
                            totalCount = feedBackTypeListVo.getData().getCount();
                        }
                        setFeedBackCount(totalCount);
                    }
                }
            });
        }
    }

    public void commitFeedBack(String content, String qq_number, String cate_id) {
        if (isOtherType){
            Toaster.show( "提交成功，感谢您的宝贵建议~");
            clearPager();
            hideSoftInput();
        }else {
            if (mViewModel != null) {
                mViewModel.commitFeedBack(content, qq_number, cate_id, new OnBaseCallback() {
                    @Override
                    public void onSuccess(BaseVo data) {
                        if (data != null) {
                            if (data.isStateOK()) {
                                Toaster.show( "提交成功，感谢您的宝贵建议~");
                                clearPager();
                                getFeedBackType();
                                hideSoftInput();
                            } else {
                                Toaster.show( data.getMsg());
                            }
                        }
                    }
                });
            }
        }


    }
}
