package com.zqhy.app.core.view.community.comment;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.community.comment.CommentInfoVo;
import com.zqhy.app.core.data.model.community.comment.ReplyListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.community.comment.holder.NewCommentDetailInfoHolder;
import com.zqhy.app.core.view.community.comment.holder.NewCommentDetailReplyHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.community.comment.CommentViewModel;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author Administrator
 */
public class CommentDetailFragment extends BaseListFragment<CommentViewModel> implements View.OnClickListener {


    public static CommentDetailFragment newInstance(int cid) {
        return newInstance(cid, -1);
    }

    public static CommentDetailFragment newInstance(int cid, int position) {
        CommentDetailFragment fragment = new CommentDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("cid", cid);
        bundle.putInt("replyPosition", position);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(CommentInfoVo.DataBean.class, new NewCommentDetailInfoHolder(_mActivity))
                .bind(CommentInfoVo.ReplyInfoVo.class, new NewCommentDetailReplyHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    private LinearLayoutManager layoutManager;

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        layoutManager = new LinearLayoutManager(_mActivity);
        return layoutManager;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    private int cid;
    private int replyPosition;

    private int rid;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            cid = getArguments().getInt("cid");
            replyPosition = getArguments().getInt("replyPosition", -1);
        }
        super.initView(state);
        initActionBackBarAndTitle("");
        setTitleLayout(LAYOUT_ON_CENTER);
        setListViewBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));
        setTitleBottomLine(View.GONE);
        setBottomView();
        initData();
    }


    private TextView  mTvTextCommentReply;
    private ImageView mIvSendCommentReply;


    private void setBottomView() {
        View bottomView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_comment_detail_bottom, null);

        mTvTextCommentReply = bottomView.findViewById(R.id.tv_text_comment_reply);
        mIvSendCommentReply = bottomView.findViewById(R.id.iv_send_comment_reply);

        mIvSendCommentReply.setOnClickListener(this);
        mTvTextCommentReply.setOnClickListener(this);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        bottomView.setLayoutParams(params);


        if (mFlListBottom != null) {
            mFlListBottom.setVisibility(View.VISIBLE);
            mFlListBottom.addView(bottomView);
        }
    }

    private void setViewValue(CommentInfoVo.DataBean dataBean) {
        setTitleText(dataBean.getGamename());
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
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
        if (page < 0) {
            return;
        }
        page++;
        getCommentReplyData();
    }

    private int page = 1, pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    private boolean isSetReplyPosition = false;

    private boolean isScroll = false;

    private void setReplyPosition() {
        if (replyPosition != -1) {
            notifyData();
            layoutManager.scrollToPositionWithOffset(2 + replyPosition, 0);
        }
        isSetReplyPosition = true;
    }

    private void initData() {
        page = 1;
        getCommentDetailData();
    }


    private void getCommentDetailData() {
        if (mViewModel != null) {
            page = 1;
            mViewModel.getCommentDetailData(cid, page, pageCount, new OnBaseCallback<CommentInfoVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(CommentInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            clearData();
                            if (data.getData() != null) {
                                CommentInfoVo.DataBean dataBean = data.getData();
                                setViewValue(dataBean);
                                addData(dataBean);
                                List<CommentInfoVo.ReplyInfoVo> replyInfoVoList = dataBean.getReply_list();
                                if (replyInfoVoList != null && replyInfoVoList.size() > 0) {
                                    addAllData(replyInfoVoList);
                                } else {
                                    addData(new EmptyDataVo(R.mipmap.img_empty_data_2)
                                            .setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT)
                                            .setPaddingTop((int) (24 * density)));
                                    setListNoMore(true);
                                }
                                if (page == 1 && !isSetReplyPosition) {
                                    setReplyPosition();
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

    private void getCommentReplyData() {
        if (mViewModel != null) {
            mViewModel.getCommentReplyData(cid, page, pageCount, new OnBaseCallback<ReplyListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(ReplyListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                addAllData(data.getData());
                            } else {
                                setListNoMore(true);
                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_comment_release:
                String strContent = mEtComment.getText().toString().trim();
                if (TextUtils.isEmpty(strContent)) {
                   Toaster.show("请输入内容");
                    return;
                }
                if (strContent.length() > 150) {
                   Toaster.show("亲，字数超过了~");
                    return;
                }
                setCommentReply(strContent);
                break;
            case R.id.iv_send_comment_reply:
            case R.id.tv_text_comment_reply:
                if (checkLogin()) {
                    rid = 0;
                    showEditDialog("回复Ta");
                }
                break;
            default:
                break;
        }
    }


    public void setCommentLike(int cid) {
        if (checkLogin()) {
            if (mViewModel != null) {
                mViewModel.setCommentLike(cid, new OnBaseCallback() {
                    @Override
                    public void onSuccess(BaseVo data) {
                        if (data != null) {
                            if (data.isStateOK()) {
                                initData();
                            } else {
                                Toaster.show( data.getMsg());
                            }
                        }
                    }
                });
            }
        }

    }

    /**
     * 评论-回复点赞
     *
     * @param rid
     */
    public void setReplyLike(int rid) {
        if (checkLogin()) {
            if (mViewModel != null) {
                mViewModel.setReplyLike(rid, new OnBaseCallback() {
                    @Override
                    public void onSuccess(BaseVo data) {
                        if (data != null) {
                            if (data.isStateOK()) {
                                refreshCommentList(rid, 1);
                            } else {
                                Toaster.show( data.getMsg());
                            }
                        }
                    }
                });
            }
        }
    }

    /**
     * 回复评论
     *
     * @param content
     */
    public void setCommentReply(String content) {
        if (checkLogin()) {
            if (mViewModel != null) {
                mViewModel.setCommentReply(cid, content, rid, new OnBaseCallback() {
                    @Override
                    public void onBefore() {
                        super.onBefore();
                        mTvCommentRelease.setEnabled(false);
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        mTvCommentRelease.setEnabled(true);
                    }

                    @Override
                    public void onSuccess(BaseVo data) {
                        if (data != null) {
                            if (data.isStateOK()) {
                                Toaster.show( "回复成功");
                                initData();
                                if (mEditDialog != null && mEditDialog.isShowing()) {
                                    mEditDialog.dismiss();
                                }
                                if (mEtComment != null) {
                                    mEtComment.getText().clear();
                                }
                            } else {
                                Toaster.show( data.getMsg());
                            }
                        }
                    }
                });
            }
        }
    }

    /**
     * 局部刷新
     *
     * @param rid
     * @param like_shift
     */
    private void refreshCommentList(int rid, int like_shift) {
        if (mDelegateAdapter != null) {
            List<Object> replyBeans = mDelegateAdapter.getData();
            for (Object o : replyBeans) {
                if (o instanceof CommentInfoVo.ReplyInfoVo) {
                    CommentInfoVo.ReplyInfoVo replyBean = (CommentInfoVo.ReplyInfoVo) o;
                    if (replyBean.getRid() == rid) {
                        if (like_shift == 1) {
                            int newCount = replyBean.getLike_count() + 1;
                            replyBean.setLike_count(newCount);
                            replyBean.setMe_like(1);
                            notifyData();
                        }
                        break;
                    }
                }

            }
        }

    }

    /**
     * 回复-评论
     *
     * @param item
     */
    public void replyComment(CommentInfoVo.ReplyInfoVo item) {
        if (item == null) {
            return;
        }
        if (checkLogin()) {
            rid = item.getRid();
            if (item.getCommunity_info() != null) {
                showEditDialog("回复@" + item.getCommunity_info().getUser_nickname());
            }
        }
    }


    private CustomDialog mEditDialog;

    private EditText mEtComment;
    private TextView mTvCommentRelease;

    public void showEditDialog(String mEditHint) {
        if (mEditDialog == null) {
            View mEditView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_edit_comment, null);
            mEditDialog = new CustomDialog(_mActivity, mEditView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
            mEtComment = mEditView.findViewById(R.id.et_comment);
            mTvCommentRelease = mEditView.findViewById(R.id.tv_comment_release);

            mEtComment.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String strComment = mEtComment.getText().toString().trim();
                    if (strComment.length() > 150) {
                        mEtComment.setText(strComment.substring(0, 150));
                        mEtComment.setSelection(mEtComment.getText().toString().length());
                        Toaster.show( "亲，字数超过啦~");
                    }
                    if (strComment.length() == 0) {
                        mTvCommentRelease.setEnabled(false);
                        mTvCommentRelease.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_b7b7b7));
                    } else {
                        mTvCommentRelease.setEnabled(true);
                        mTvCommentRelease.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_007aff));
                    }
                }
            });
            mTvCommentRelease.setOnClickListener(this);
            mEditDialog.setOnDismissListener(dialogInterface -> hideSoftInput());
        }
        mEtComment.setHint(mEditHint);
        showSoftInput(mEtComment);
        mEditDialog.show();
    }

    private CustomDialog commentTipsDialog;
    public void showCommentTipsDialog() {
        if (commentTipsDialog == null) {
            commentTipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_game_detail_comment_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        }
        commentTipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if (commentTipsDialog != null && commentTipsDialog.isShowing()){
                commentTipsDialog.dismiss();
            }
        });
        commentTipsDialog.show();
    }
}
