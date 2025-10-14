package com.zqhy.app.core.view;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class BlankTxtFragment extends BaseFragment implements View.OnClickListener {

    public static BlankTxtFragment newInstance(String title, String hint, String txt, int minTxtLength, int maxTxtLength) {
        return newInstance(title, hint, txt, minTxtLength, maxTxtLength, false);
    }

    public static BlankTxtFragment newInstance(String title, String hint, String txt, int minTxtLength, int maxTxtLength, boolean isCanEmpty) {
        BlankTxtFragment fragment = new BlankTxtFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("hint", hint);
        bundle.putString("txt", txt);
        bundle.putInt("minTxtLength", minTxtLength);
        bundle.putInt("maxTxtLength", maxTxtLength);
        bundle.putBoolean("isCanEmpty", isCanEmpty);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_blank_txt;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private String title;
    private String hint;
    private String txt;
    private int minTxtLength;
    private int maxTxtLength;

    private boolean isCanEmpty;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            title = getArguments().getString("title");
            hint = getArguments().getString("hint");

            txt = getArguments().getString("txt");

            minTxtLength = getArguments().getInt("minTxtLength");
            maxTxtLength = getArguments().getInt("maxTxtLength");

            isCanEmpty = getArguments().getBoolean("isCanEmpty");
        }
        super.initView(state);
        initActionBackBarAndTitle(title);
        setTitleBottomLine(View.GONE);
        showSuccess();
        bindViews();
    }

    private TextView mTvSave;
    private EditText mEtComment;

    private void bindViews() {
        mTvSave = findViewById(R.id.tv_save);
        mEtComment = findViewById(R.id.et_comment);

        if (!TextUtils.isEmpty(txt)) {
            mEtComment.setText(txt);
            mEtComment.setSelection(mEtComment.getText().toString().length());
        }
        mEtComment.setHint(hint);
        mTvSave.setOnClickListener(this);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(16 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
        mTvSave.setBackground(gd);


        mEtComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String strComment = mEtComment.getText().toString().trim();
                if (strComment.length() > maxTxtLength) {
                    mEtComment.setText(strComment.substring(0, maxTxtLength));
                    mEtComment.setSelection(mEtComment.getText().toString().length());
                    //ToastT.warning(_mActivity,"亲，字数超过啦~");
                    Toaster.show("亲，字数超过啦");
                }
            }
        });

        showSoftInput(mEtComment);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_save:
                String strComment = mEtComment.getText().toString().trim();
                if (TextUtils.isEmpty(strComment)) {
                    if (!isCanEmpty) {
                        //ToastT.warning(_mActivity,"请输入内容");
                        Toaster.show("请输入内容");
                        return;
                    }
                } else {
                    if (strComment.length() < minTxtLength) {
                        //ToastT.warning(_mActivity,"您输入的内容小于" + minTxtLength + "个字");
                        Toaster.show("您输入的内容小于" + minTxtLength + "个字");
                        return;
                    }
                    if (strComment.length() > maxTxtLength) {
                        Toaster.show("您输入的内容大于" + maxTxtLength + "个字");
                        //ToastT.warning(_mActivity,"您输入的内容大于" + maxTxtLength + "个字");
                        return;
                    }
                }

                if (strComment == null) {
                    strComment = "";
                }

                Bundle bundle = new Bundle();
                bundle.putString("data", strComment);
                setFragmentResult(RESULT_OK, bundle);
                pop();

                break;
            default:
                break;
        }
    }
}
