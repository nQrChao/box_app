package com.zqhy.app.core.view.community.qa;

import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.core.data.model.community.qa.BaseItemVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.inner.SoftKeyBoardListener;
import com.zqhy.app.core.tool.utilcode.KeyboardUtils;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.vm.community.qa.QaViewModel;
import com.zqhy.app.newproject.R;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Administrator
 */
public class GameQuestionEditFragment extends BaseFragment<QaViewModel> {

    public static GameQuestionEditFragment newInstance(int gameid, String gamename, int game_play_count) {
        GameQuestionEditFragment fragment = new GameQuestionEditFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("gameid", gameid);
        bundle.putString("gamename", gamename);
        bundle.putInt("game_play_count", game_play_count);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_question_edit;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    private int gameid, game_play_count;
    private String gamename;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gameid = getArguments().getInt("gameid");
            gamename = getArguments().getString("gamename");
            game_play_count = getArguments().getInt("game_play_count");
        }
        super.initView(state);
        showSuccess();
        bindViews();
    }


    private ImageView mIvBack;
    private TextView mTvAskQuestions;
    private TextView mTvTitle;
    private LinearLayout mLlEditQuestion;
    private TextView mTvGamePlayCount;
    private EditText mEtAskQuestion;
    private TextView mBtnGoKefu;

    private void bindViews() {
        mIvBack = findViewById(R.id.iv_back);
        mTvAskQuestions = findViewById(R.id.tv_ask_questions);
        mTvTitle = findViewById(R.id.tv_title);
        mLlEditQuestion = findViewById(R.id.ll_edit_question);
        mTvGamePlayCount = findViewById(R.id.tv_game_play_count);
        mEtAskQuestion = findViewById(R.id.et_ask_question);
        mBtnGoKefu = findViewById(R.id.btn_go_kefu);

        mTvTitle.setText("我要提问");
        mIvBack.setOnClickListener(v -> pop());

        setViews();
        setEditText();


        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(48 * density);
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_d6d6d6));
        mBtnGoKefu.setBackground(gd);
        mBtnGoKefu.setOnClickListener(v -> goKefuMain());

        mEtAskQuestion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = mEtAskQuestion.getText().toString().trim();
                setAskQuestionBtn(TextUtils.isEmpty(value));
            }
        });
        setAskQuestionBtn(true);

        mTvAskQuestions.setOnClickListener(v -> {
            String value = mEtAskQuestion.getText().toString().trim();
            askQuestion(value);
        });

        post(()->KeyboardUtils.showSoftInput(_mActivity,mEtAskQuestion));
    }

    private void setViews() {
        StringBuilder sb = new StringBuilder();
        sb.append("向");
        String strGamePlatCount = String.valueOf(game_play_count);
        int startIndex = sb.length();
        int endIndex = startIndex + strGamePlatCount.length();
        sb.append(strGamePlatCount)
                .append("位玩过该游戏的人请教！");

        SpannableString ss = new SpannableString(sb.toString());
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_333333)),
                startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(new StyleSpan(Typeface.BOLD), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvGamePlayCount.setText("向玩过该游戏的人请教！");
    }

    private void setEditText() {
        //软键盘显示/隐藏监听
        SoftKeyBoardListener softKeyBoardListener = new SoftKeyBoardListener(_mActivity);
        softKeyBoardListener.setOnSoftKeyBoardChangeListener(new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                mEtAskQuestion.setFocusable(true);
                mEtAskQuestion.setFocusableInTouchMode(true);
                mEtAskQuestion.setCursorVisible(true);
                mEtAskQuestion.requestFocus();
                setLayoutBg(true);
            }

            @Override
            public void keyBoardHide(int height) {
                mEtAskQuestion.setFocusable(false);
                mEtAskQuestion.setFocusableInTouchMode(false);
                mEtAskQuestion.setCursorVisible(false);
                setLayoutBg(false);
            }
        });
        //设置点击事件,显示软键盘
        mEtAskQuestion.setOnClickListener(v -> KeyboardUtils.showSoftInput(_mActivity, mEtAskQuestion));
        setLayoutBg(false);
    }

    private void setLayoutBg(boolean isKeyBoardShow) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.white));
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, isKeyBoardShow ? R.color.color_ffb300 : R.color.color_f2f2f2));

        mLlEditQuestion.setBackground(gd);
    }

    private void setAskQuestionBtn(boolean isEditEmpty) {
        if (isEditEmpty) {
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(48 * density);
            gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_d6d6d6));

            mTvAskQuestions.setBackground(gd);
        } else {
            mTvAskQuestions.setBackgroundResource(R.drawable.shape_btn_gradient_default);
        }
        mTvAskQuestions.setEnabled(!isEditEmpty);
    }

    private void askQuestion(String question) {
        if (checkLogin()) {
            if (TextUtils.isEmpty(question)) {
                Toaster.show( "请输入内容");
                return;
            }
            if (question.length() < 5) {
                Toaster.show( "亲，请描述的更详细点哦！");
                return;
            }
            if (question.length() > 500) {
                Toaster.show( "亲，字数超过了~");
                return;
            }
            uploadUserQuestion(question);
        }
    }

    private void uploadUserQuestion(String content) {
        if (mViewModel != null) {
            mViewModel.useAskQuestion(gameid, content, new OnBaseCallback<BaseItemVo>() {

                @Override
                public void onBefore() {
                    super.onBefore();
                    mTvAskQuestions.setEnabled(false);
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    mTvAskQuestions.setEnabled(true);
                }

                @Override
                public void onSuccess(BaseItemVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            Toaster.show( "您的问题已经发出，请关注【我的问答】");
                            EventBus.getDefault().post(new EventCenter(EventConfig.POST_NEW_GAME_QUESTION_EVENT_CODE));
                            int qid = data.getData();
                            startWithPop(GameQaDetailFragment.newInstance(qid));
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }
}
