package com.zqhy.app.core.view.community.qa;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.community.CommunityInfoVo;
import com.zqhy.app.core.data.model.community.qa.AnswerInfoVo;
import com.zqhy.app.core.data.model.community.qa.QaDetailInfoVo;
import com.zqhy.app.core.data.model.nodata.BlankDataVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.view.BlankItemHolder;
import com.zqhy.app.core.view.community.qa.holder.AnswerItemHolder;
import com.zqhy.app.core.view.community.user.CommunityUserFragment;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.community.qa.QaViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.imageview.ClipRoundImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @author Administrator
 */
public class GameQaDetailFragment extends BaseListFragment<QaViewModel> implements View.OnClickListener {

    public static GameQaDetailFragment newInstance(int qid) {
        GameQaDetailFragment fragment = new GameQaDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("qid", qid);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(AnswerInfoVo.class, new AnswerItemHolder(_mActivity))
                .bind(BlankDataVo.class, new BlankItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    private int qid;

    private FrameLayout mFlListContent;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            qid = getArguments().getInt("qid");
        }
        super.initView(state);
        initActionBackBarAndTitle("问答详情");
        setTitleBottomLine(View.GONE);
        mFlListContent = findViewById(R.id.fl_list_content);
        addHeaderView();
        addBottomView();
        addFloatView();
        initData();
    }


    @Override
    protected View getTitleRightView() {
        /*ImageView mIvQuestion = new ImageView(_mActivity);
        mIvQuestion.setImageResource(R.mipmap.ic_message_common);
        int padding = (int) (6 * density);
        mIvQuestion.setPadding(padding, padding, padding, padding);

        mIvQuestion.setOnClickListener(v -> goMessageCenter());*/

        TextView mTvUserQa = new TextView(_mActivity);
        mTvUserQa.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_232323));
        mTvUserQa.setTextSize(12);
        mTvUserQa.setText("我的\n问答");
        int padding = (int) (6 * density);
        mTvUserQa.setPadding(padding, padding, padding, padding);

        mTvUserQa.setOnClickListener(v -> {
            if (checkLogin()) {
                start(UserQaCollapsingCenterFragment.newInstance());
            }
        });

        mTvUserQa.setVisibility(View.GONE);
        return mTvUserQa;
    }


    private RelativeLayout mLlContainer;
    private TextView mTvAskQuestions, mTvFloatText;

    private void addFloatView() {
        View mFloatLayoutView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_collapsing_game_qa_float, null);
        mLlContainer = mFloatLayoutView.findViewById(R.id.ll_container);
        mTvAskQuestions = mFloatLayoutView.findViewById(R.id.tv_ask_questions);
        mTvFloatText = mFloatLayoutView.findViewById(R.id.tv_float_text);

        mTvFloatText.setText("大神，求解答~");
        mTvAskQuestions.setText("受邀回答");

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(48 * density);
        gd.setColor(Color.parseColor("#DA333333"));
        mLlContainer.setBackground(gd);

        mTvAskQuestions.setOnClickListener(v -> {
            if (checkLogin()) {
                //回答问题
                showAskQuestionDialog();
            }
        });

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        params.setMargins(0, 0, 0, (int) (36 * density));
        mFloatLayoutView.setLayoutParams(params);

        mLlContainer.setVisibility(View.GONE);

        if (mFlListContent != null) {
            mFlListContent.addView(mFloatLayoutView, 1);
        }

    }

    private ClipRoundImageView mProfileImage;
    private TextView mTvUserNickname;
    private TextView mTvTime;
    private TextView mTvGameQuestionTitle;
    private TextView mTvCountAnswerTotal;

    private LinearLayout mLlGameName;
    private TextView mTvGameName;

    private void addHeaderView() {
        View mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_qa_detail_header, null);
        mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mProfileImage = mHeaderView.findViewById(R.id.profile_image);
        mTvUserNickname = mHeaderView.findViewById(R.id.tv_user_nickname);
        mTvTime = mHeaderView.findViewById(R.id.tv_time);
        mTvGameQuestionTitle = mHeaderView.findViewById(R.id.tv_game_question_title);
        mTvCountAnswerTotal = mHeaderView.findViewById(R.id.tv_count_answer_total);

        mLlGameName = mHeaderView.findViewById(R.id.ll_game_name);
        mTvGameName = mHeaderView.findViewById(R.id.tv_game_name);

        addHeaderView(mHeaderView);
    }

    private RelativeLayout mRlRootView;
    private ImageView mIv;
    private TextView mTv;
    private TextView mBtnAction1;
    private TextView mBtnAction2;

    private void addBottomView() {
        View mBottomView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_qa_detail_bottom, null);
        mBottomView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (48 * density)));
        mRlRootView = mBottomView.findViewById(R.id.rl_root_view);
        mIv = mBottomView.findViewById(R.id.iv);
        mTv = mBottomView.findViewById(R.id.tv);
        mBtnAction1 = mBottomView.findViewById(R.id.btn_action_1);
        mBtnAction2 = mBottomView.findViewById(R.id.btn_action_2);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(0);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_f9f9f9));
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));
        mRlRootView.setBackground(gd);
        mRlRootView.setVisibility(View.GONE);

        if (mFlListBottom != null) {
            mFlListBottom.setVisibility(View.VISIBLE);
            mFlListBottom.addView(mBottomView);
        }
    }

    private int isCanAnswer = 0;
    private String questionContent;
    private int uid;
    private

    QaDetailInfoVo.DataBean qaDetailInfoVo;

    private void setQaGameAndQuestionInfo(QaDetailInfoVo.DataBean qaDetailInfoVo) {
        this.qaDetailInfoVo = qaDetailInfoVo;
        isCanAnswer = qaDetailInfoVo.getCan_answer();
        uid = qaDetailInfoVo.getUid();
        setBottomAnswerView();

        mTvGameName.setText(qaDetailInfoVo.getGamename());
        mLlGameName.setOnClickListener(v -> goGameDetail(qaDetailInfoVo.getGameid(), qaDetailInfoVo.getGame_type()));

        CommunityInfoVo communityInfoVo = qaDetailInfoVo.getCommunity_info();
        if (communityInfoVo != null) {
            GlideUtils.loadCircleImage(_mActivity, communityInfoVo.getUser_icon(), mProfileImage, R.mipmap.ic_user_login);
            mTvUserNickname.setText(communityInfoVo.getUser_nickname());

            View.OnClickListener onClickListener = v -> start(CommunityUserFragment.newInstance(communityInfoVo.getUser_id()));
            mProfileImage.setOnClickListener(onClickListener);
            mTvUserNickname.setOnClickListener(onClickListener);
        }

        questionContent = qaDetailInfoVo.getContent();
        mTvGameQuestionTitle.setText(qaDetailInfoVo.getContent());
        mTvTime.setText(CommonUtils.formatTimeStamp(qaDetailInfoVo.getAdd_time() * 1000, "MM月dd日"));

        mTvCountAnswerTotal.setVisibility(View.VISIBLE);
        if (qaDetailInfoVo.getA_count() > 0) {
            mTvCountAnswerTotal.setText(Html.fromHtml(_mActivity.getResources().getString(R.string.string_game_question_info_answer_count, String.valueOf(qaDetailInfoVo.getA_count()))));
            mTvCountAnswerTotal.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.white));
            mRecyclerView.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.white));
        } else if (qaDetailInfoVo.getA_count() == 0) {
            mTvCountAnswerTotal.setText("各路大神赶来中...");
            mTvCountAnswerTotal.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f9f9f9));
            mRecyclerView.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f9f9f9));
        } else {
            mTvCountAnswerTotal.setVisibility(View.GONE);
        }
    }

    private boolean isShowFloatView = false;

    private void setBottomAnswerView() {
        mLlContainer.setVisibility(View.GONE);
        mRlRootView.setVisibility(View.GONE);

        isShowFloatView = false;
        if (UserInfoModel.getInstance().isLogined()) {
            int loginedUid = UserInfoModel.getInstance().getUserInfo().getUid();
            if (uid == loginedUid) {
                //提问者
                int a_count = qaDetailInfoVo.getA_count();
                int status = qaDetailInfoVo.getStatus();
                int can_solve = qaDetailInfoVo.getCan_solve();

                if (status == 0) {
                    //未解决
                    if (can_solve == 0) {
                        //不能点已解决；
                        if (a_count == 0) {
                            //最新回复，请关注我的问答
                            setLayoutBottomView(R.mipmap.ic_game_qa_detail_3, "最新回复，请关注我的问答~", "", null, "", null);
                        } else {

                        }
                    } else if (can_solve == 1) {
                        //可以点解决
                        //是否解决你的问题？
                        setLayoutBottomView(R.mipmap.ic_game_qa_detail_4, "是否解决你的问题？", "是的", v -> {
                            setQaSolve();
                        }, "", null);
                    }
                } else if (status == 1) {
                    //已解决
                    //更多玩法讨论尽在游戏问答哦！
                    setLayoutBottomView(R.mipmap.ic_game_qa_detail_2, "更多玩法讨论尽在游戏问答哦！", "我还想问", v -> goAskQuestion(), "", null);
                }
            } else {
                //回答者
                if (isCanAnswer == 1) {
                    //大神，求解答
                    mLlContainer.setVisibility(View.VISIBLE);
                    isShowFloatView = true;
                } else if (isCanAnswer == -2) {
                    //已为TA解答！感谢！
                    setLayoutBottomView(R.mipmap.ic_game_qa_detail_1, "已为TA解答！感谢！", "更多问答", v -> {
                        //我的问答
                        int uid = UserInfoModel.getInstance().getUserInfo().getUid();
                        String user_nickname = UserInfoModel.getInstance().getUserInfo().getUser_nickname();
                        start(UserQaCollapsingCenterFragment.newInstance(uid, user_nickname));
                    }, "", null);
                } else {
                    //不能回答(我也要问大神)
                    setLayoutBottomView(R.mipmap.ic_game_qa_detail_2, "我也要问大神", "关于问答", v -> showAnswerRuleDialog(), "去提问", v -> goAskQuestion());
                }
            }
        } else {
            //我也要问大神
            setLayoutBottomView(R.mipmap.ic_game_qa_detail_2, "我也要问大神", "关于问答", v -> {
                showAnswerRuleDialog();
            }, "去提问", v -> goAskQuestion());
        }
    }

    /**
     * 跳转提问页面
     */
    private void goAskQuestion() {
        if (checkLogin() && qaDetailInfoVo != null) {
            boolean isCanAskQuestion = qaDetailInfoVo.getCan_question() == 1;
            if (isCanAskQuestion) {
                int gameid = qaDetailInfoVo.getGameid();
                String gamename = qaDetailInfoVo.getGamename();
                int gamePlayCount = qaDetailInfoVo.getPlay_count();
                start(GameQuestionEditFragment.newInstance(gameid, gamename, gamePlayCount));
            } else {
                Toaster.show( "让大神休息会儿，稍等再来问问呢");
            }
        }
    }


    private void setLayoutBottomView(int ivRes, String text, String btn1Text, View.OnClickListener onClickListenerBtn1, String btn2Text, View.OnClickListener onClickListenerBtn2) {
        mRlRootView.setVisibility(View.VISIBLE);
        mIv.setImageResource(ivRes);
        mTv.setText(text);

        if (TextUtils.isEmpty(btn1Text)) {
            mBtnAction1.setVisibility(View.GONE);
        } else {
            mBtnAction1.setVisibility(View.VISIBLE);
            mBtnAction1.setText(btn1Text);
            mBtnAction1.setOnClickListener(onClickListenerBtn1);
        }

        if (TextUtils.isEmpty(btn2Text)) {
            mBtnAction2.setVisibility(View.GONE);
        } else {
            mBtnAction2.setVisibility(View.VISIBLE);
            mBtnAction2.setText(btn2Text);
            mBtnAction2.setOnClickListener(onClickListenerBtn2);
        }
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
        getQaDetailData();
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
                    if (strContent.length() > 100) {
                        Toaster.show( "亲，字数超过了~");
                        return;
                    }
                    uploadUserAnswer(strContent);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        initData();
    }

    private int page = 1, pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    private void initData() {
        page = 1;
        getQaDetailData();
    }

    private void getQaDetailData() {
        if (mViewModel != null) {
            mViewModel.getQaDetailData(qid, page, pageCount, new OnBaseCallback<QaDetailInfoVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(QaDetailInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                if (page == 1) {
                                    setQaGameAndQuestionInfo(data.getData());
                                }
                                List<AnswerInfoVo> answerInfoVoList = data.getData().getAnswerlist();
                                if (answerInfoVoList != null) {
                                    if (page == 1) {
                                        clearData();
                                    }
                                    addAllData(answerInfoVoList);
                                    if (answerInfoVoList.size() < getPageCount()) {
                                        //has no more items
                                        if (isShowFloatView) {
                                            addData(new BlankDataVo());
                                        }
                                    }
                                } else {
                                    if (page == 1) {
                                        clearData();
                                        addData(new EmptyDataVo(R.mipmap.img_empty_data_1)
                                                .setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT)
                                                .setPaddingTop((int) (12 * density)));
                                    } else {
                                        //has no more items
                                        if (isShowFloatView) {
                                            addData(new BlankDataVo());
                                        }
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

    private void uploadUserAnswer(String content) {
        if (mViewModel != null) {
            mViewModel.userAnswerQuestion(qid, content, new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            Toaster.show( "回答成功，感谢您的热心快肠！");
                            if (askQuestionDialog != null && askQuestionDialog.isShowing()) {
                                askQuestionDialog.dismiss();
                            }
                            if (mEtAnswerCommit != null) {
                                mEtAnswerCommit.getText().clear();
                            }
                            initData();
                            setFragmentResult(RESULT_OK, null);
                            EventBus.getDefault().post(new EventCenter(EventConfig.POST_NEW_GAME_ANSWER_EVENT_CODE));
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
    private TextView mTvDialogQuestion;

    private void showAskQuestionDialog() {
        if (askQuestionDialog == null) {
            askQuestionDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_edit_ask_question, null),
                    WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

            mTvTitle = askQuestionDialog.findViewById(R.id.tv_title);
            mTvBtnConfirm = askQuestionDialog.findViewById(R.id.tv_btn_confirm);
            mEtAnswerCommit = askQuestionDialog.findViewById(R.id.et_answer_commit);
            mTvDialogQuestion = askQuestionDialog.findViewById(R.id.tv_dialog_question);

            askQuestionDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    hideSoftInput();
                }
            });
            mTvBtnConfirm.setOnClickListener(this);
            showDialogInit();
        }
        /** 3.自动弹出软键盘 **/
        showSoftInput(mEtAnswerCommit);
        mTvDialogQuestion.setText(questionContent);
        askQuestionDialog.show();
    }

    private void showDialogInit() {
        StringBuilder sb = new StringBuilder();
        sb.append("回答通过有奖，单日最高奖");
        int startIndex = sb.toString().length();
        sb.append("100积分");
        int endIndex = sb.toString().length();
        sb.append("~");

        SpannableString ss = new SpannableString(sb.toString());
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_ff5400)),
                startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        mTvTitle.getPaint().setFakeBoldText(true);
        mTvTitle.setTextSize(15);
        mTvTitle.setText(ss);

        mTvBtnConfirm.setText("回答");
        mTvBtnConfirm.setBackgroundResource(R.drawable.shape_btn_gradient_default);

        mEtAnswerCommit.setHintTextColor(ContextCompat.getColor(_mActivity, R.color.color_d6d6d6));
        mEtAnswerCommit.setHint("大神，帮帮我呀！（来自萌新的凝视）");
        mEtAnswerCommit.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f9f9f9));

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
                if (strComment.length() > 100) {
                    mEtAnswerCommit.setText(strComment.substring(0, 100));
                    mEtAnswerCommit.setSelection(mEtAnswerCommit.getText().toString().length());

                    Toaster.show( "亲，字数超过啦~");
                }
            }
        });
    }

    public void setAnswerLike(View clickView, int aid) {
        if (mViewModel != null) {
            mViewModel.setAnswerLike(aid, new OnBaseCallback() {

                @Override
                public void onBefore() {
                    super.onBefore();
                    loading();
                    clickView.setEnabled(false);

                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                    clickView.setEnabled(true);
                }

                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            refreshAnswerList(aid, 1);
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    /**
     * 局部刷新
     *
     * @param aid
     * @param like_shift
     */
    private void refreshAnswerList(int aid, int like_shift) {
        if (mDelegateAdapter != null && UserInfoModel.getInstance().isLogined()) {
            try {
                int position = 0;
                List<Object> list = mDelegateAdapter.getData();
                for (Object object : list) {
                    position++;
                    if (object instanceof AnswerInfoVo) {
                        AnswerInfoVo answerInfoVo = (AnswerInfoVo) object;
                        if (answerInfoVo.getAid() == aid) {
                            answerInfoVo.setMe_like(like_shift);
                            answerInfoVo.setLike_count(answerInfoVo.getLike_count() + 1);
                            mDelegateAdapter.notifyItemChanged(position + 1);
                            return;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setQaSolve() {
        if (mViewModel != null) {
            mViewModel.setQaSolve(qid, new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            initData();
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }
}
