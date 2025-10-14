package com.zqhy.app.core.view.community.qa;

import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.community.qa.QAListVo;
import com.zqhy.app.core.data.model.community.qa.QuestionInfoVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.community.qa.holder.QuestionItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.community.qa.QaViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author Administrator
 */
public class GameQaListFragment extends BaseListFragment<QaViewModel> implements View.OnClickListener {

    public static GameQaListFragment newInstance(int gameid) {
        GameQaListFragment fragment = new GameQaListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("gameid", gameid);
        fragment.setArguments(bundle);
        return fragment;
    }

    private static final int ACTION_GAME_QA_DETAIL = 0x456;

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(QuestionInfoVo.class, new QuestionItemHolder(_mActivity))
                .build();
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    private int gameid;

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gameid = getArguments().getInt("gameid");
        }
        super.initView(state);
        initActionBackBarAndTitle("游戏问答");

        bindViews();
        addBottomView();
        initData();

        setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof QuestionInfoVo) {
                QuestionInfoVo questionInfoVo = (QuestionInfoVo) data;
                startForResult(GameQaDetailFragment.newInstance(questionInfoVo.getQid()), ACTION_GAME_QA_DETAIL);
            }
        });
    }

    private LinearLayout mContentLayout;

    private void bindViews() {
        mContentLayout = findViewById(R.id.content_layout);
        if(mContentLayout != null){
            View mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_game_qa_list_header, null);
            mIvGameIcon = mHeaderView.findViewById(R.id.iv_game_icon);
            mTvGameName = mHeaderView.findViewById(R.id.tv_game_name);
            mTvUserCountPlayedGame = mHeaderView.findViewById(R.id.tv_user_count_played_game);
            mTvQuestionsCount = mHeaderView.findViewById(R.id.tv_questions_count);

            mTvUserCountPlayedGame.setVisibility(View.GONE);

            mHeaderView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            mContentLayout.addView(mHeaderView, 0);
        }
    }

    @Override
    protected View getTitleRightView() {
        ImageView mIvQuestion = new ImageView(_mActivity);
        mIvQuestion.setImageResource(R.mipmap.ic_question_task);
        int padding = (int) (6 * density);
        mIvQuestion.setPadding(padding, padding, padding, padding);

        mIvQuestion.setOnClickListener(v -> showAnswerRuleDialog());

        return mIvQuestion;
    }

    private void addBottomView() {
        Button btnAskQuestion = new Button(_mActivity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int) (40 * density));
        params.rightMargin = (int) (36 * density);
        params.leftMargin = (int) (36 * density);
        params.topMargin = (int) (22 * density);
        params.bottomMargin = (int) (14 * density);

        btnAskQuestion.setLayoutParams(params);

        btnAskQuestion.setText("我要请教");
        btnAskQuestion.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(48 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
        btnAskQuestion.setBackground(gd);

        btnAskQuestion.setOnClickListener(v -> {
            if (checkLogin()) {
                if (!isCanAskQuestion) {
                    Toaster.show( "今天的问题已经够多了，明天再来吧！");
                    return;
                }
                showAskQuestionDialog();
            }
        });

        if (mFlListBottom != null) {
            mFlListBottom.setVisibility(View.VISIBLE);
            mFlListBottom.addView(btnAskQuestion);
        }
    }

    private ImageView mIvGameIcon;
    private TextView mTvGameName;
    private TextView mTvUserCountPlayedGame;
    private TextView mTvQuestionsCount;

    private int game_played_count;
    private boolean isCanAskQuestion = false;

    private void setGameViewInfo(GameInfoVo gameInfoVo) {
        isCanAskQuestion = gameInfoVo.getCan_question() == 1;

        GlideUtils.loadNormalImage(_mActivity, gameInfoVo.getGameicon(), mIvGameIcon);
        mTvGameName.setText(gameInfoVo.getGamename());
        game_played_count = gameInfoVo.getPlay_count();
        mTvQuestionsCount.setText(_mActivity.getResources().getString(R.string.string_game_question_and_answer, String.valueOf(gameInfoVo.getQuestion_count()), String.valueOf(gameInfoVo.getAnswer_count())));
    }

    private int page = 1, pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        initData();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        page++;
        getQaListData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_btn_confirm:
                if (checkLogin()) {
                    String strContent = mEtAnswerCommit.getText().toString().trim();
                    if (TextUtils.isEmpty(strContent)) {
                        Toaster.show( "请输入内容");
                        return;
                    }
                    if (strContent.length() < 5) {
                        Toaster.show( "亲，请描述的更详细点哦！");
                        return;
                    }
                    if (strContent.length() > 500) {
                        Toaster.show( "亲，字数超过了~");
                        return;
                    }
                    uploadUserQuestion(strContent);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == ACTION_GAME_QA_DETAIL && resultCode == RESULT_OK) {
            initData();
        }
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        initData();
    }

    private void initData() {
        page = 1;
        getQaListData();
    }

    private void getQaListData() {
        if (mViewModel != null) {
            mViewModel.getQaListData(gameid, page, pageCount, new OnBaseCallback<QAListVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(QAListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            GameInfoVo gameInfoVo = data.getData();
                            if (gameInfoVo != null) {
                                if (page == 1) {
                                    setGameViewInfo(gameInfoVo);
                                }
                                List<QuestionInfoVo> questionInfoVoList = gameInfoVo.getQuestion_list();
                                if (questionInfoVoList != null) {
                                    if (page == 1) {
                                        clearData();
                                    }
                                    addAllData(questionInfoVoList);
                                } else {
                                    if (page == 1) {
                                        clearData();
                                        addData(new EmptyDataVo(R.mipmap.img_empty_data_2)
                                                .setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT)
                                                .setPaddingTop((int) (24*density)));
                                    }
                                    setListNoMore(true);
                                    notifyData();
                                }
                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void uploadUserQuestion(String content) {
        if (mViewModel != null) {
            mViewModel.useAskQuestion(gameid, content, new OnBaseCallback() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    if (askQuestionDialog != null && askQuestionDialog.isShowing()) {
                        askQuestionDialog.dismiss();
                    }
                }

                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            Toaster.show( "您的问题已经发出，请关注【我的问答】");
                            initData();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private CustomDialog askQuestionDialog;

    private TextView mTvTitle;
    private TextView mTvBtnConfirm;
    private EditText mEtAnswerCommit;

    private void showAskQuestionDialog() {
        if (askQuestionDialog == null) {
            askQuestionDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_edit_ask_question, null),
                    WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

            mTvTitle = askQuestionDialog.findViewById(R.id.tv_title);
            mTvBtnConfirm = askQuestionDialog.findViewById(R.id.tv_btn_confirm);
            mEtAnswerCommit = askQuestionDialog.findViewById(R.id.et_answer_commit);

            askQuestionDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    mEtAnswerCommit.getText().clear();
                    hideSoftInput();
                }
            });
            mTvBtnConfirm.setOnClickListener(this);
            showDialogInit();
        }
        /** 3.自动弹出软键盘 **/
        showSoftInput(mEtAnswerCommit);
        askQuestionDialog.show();
    }

    private void showDialogInit() {
        Spanned str = Html.fromHtml(_mActivity.getResources().getString(R.string.string_ask_question, String.valueOf(game_played_count)));
        mTvTitle.setText("向玩过该游戏的人请教");
        mTvBtnConfirm.setText("提问");

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(16 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_ff8f19));
        mTvBtnConfirm.setBackground(gd);

        mEtAnswerCommit.setHint("5-100字范围内，请准确描述您的问题吧~");

        mEtAnswerCommit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String strComment = mEtAnswerCommit.getText().toString().trim();
                if (strComment.length() > 499) {
                    mEtAnswerCommit.setText(strComment.substring(0, 499));
                    mEtAnswerCommit.setSelection(mEtAnswerCommit.getText().toString().length());
                    Toaster.show( "亲，字数超过啦~");
                }
            }
        });
    }

}
