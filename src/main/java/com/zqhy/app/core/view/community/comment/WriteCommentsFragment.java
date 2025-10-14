package com.zqhy.app.core.view.community.comment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.chaoji.other.hjq.toast.Toaster;
import com.donkingliang.imageselector.event.PhotoEvent;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.ThumbnailBean;
import com.zqhy.app.core.data.model.community.comment.CommentInfoVo;
import com.zqhy.app.core.data.model.community.comment.CommentTypeListVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.inner.SoftKeyBoardListener;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.ConstUtils;
import com.zqhy.app.core.tool.utilcode.FileUtils;
import com.zqhy.app.core.tool.utilcode.KeyboardUtils;
import com.zqhy.app.core.vm.community.comment.CommentViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.DebounceListener;
import com.zqhy.app.utils.cache.ACache;
import com.zqhy.app.utils.sdcard.SdCardManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;


/**
 * @author Administrator
 */
public class WriteCommentsFragment extends BaseFragment<CommentViewModel> {

    private String SP_COMMENT_EDIT_KEY = "COMMENT_EDIT_KEY";

    public static WriteCommentsFragment newInstance(String gameid, String gamename) {
        WriteCommentsFragment fragment = new WriteCommentsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gameid", gameid);
        bundle.putString("gamename", gamename);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static WriteCommentsFragment newInstance(String gameid, String gamename, String cid) {
        WriteCommentsFragment fragment = new WriteCommentsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gameid", gameid);
        bundle.putString("gamename", gamename);
        bundle.putString("cid", cid);
        fragment.setArguments(bundle);
        return fragment;
    }

    public WriteCommentsFragment() {
        // Required empty public constructor
        UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
        if (userInfoBean != null) {
            SP_COMMENT_EDIT_KEY += "_" + userInfoBean.getUid();
        }
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_write_comment;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    String gameid, gamename, cid;
    private String type_id;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gameid = getArguments().getString("gameid");
            gamename = getArguments().getString("gamename");
            cid = getArguments().getString("cid");
        }
        super.initView(state);
        showSuccess();
        initActionBackBarAndTitle(gamename);
        bindViews();
        getCommentInfo();
        getCommentType();
    }

    private ImageView    mIvBack;
    private TextView     mTvPostComment;
    private TextView     mTvTitle;
    private LinearLayout mLlEditComment;
    private EditText     mEtComment;
    private RecyclerView mRecyclerViewThumbnail;
    private TextView     mTvCommentRule;

    private RadioGroup  mGrCommentTab;
    private RadioButton mRbTypeComment;
    private RadioButton mRbTypeStrategy;
    private RadioButton mRbTypeSeekHelp;

    private void bindViews() {
        mIvBack = findViewById(R.id.iv_back);
        mTvPostComment = findViewById(R.id.tv_post_comment);
        mTvTitle = findViewById(R.id.tv_title);
        mLlEditComment = findViewById(R.id.ll_edit_comment);
        mEtComment = findViewById(R.id.et_comment);
        mRecyclerViewThumbnail = findViewById(R.id.recyclerView_thumbnail);
        mTvCommentRule = findViewById(R.id.tv_comment_rule);

        mGrCommentTab = findViewById(R.id.gr_comment_tab);
        mRbTypeComment = findViewById(R.id.rb_type_comment);
        mRbTypeStrategy = findViewById(R.id.rb_type_strategy);
        mRbTypeSeekHelp = findViewById(R.id.rb_type_seek_help);

        StringBuilder sb = new StringBuilder();
        sb.append("发表你对游戏的看法！其中优质的评论将获得一定数量的积分奖励，每日最多");
        int startIndex = sb.toString().length();
        sb.append("500");
        int endIndex = sb.toString().length();
        sb.append("积分哦");

        SpannableString ss = new SpannableString(sb.toString());
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_ff0000)),
                startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mEtComment.setHint(ss);

        mIvBack.setOnClickListener(v -> pop());
        mTvTitle.setText("发布点评");


        mTvPostComment.setOnClickListener(new DebounceListener() {
            @Override
            protected void onClick() {
                //验证用户是否绑定手机
                if (checkUserBindPhoneTips1()) {
                    sentCommentAction();
                }
            }
        });

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
                setPostCommentBtn(TextUtils.isEmpty(strComment));
                saveEditComment(gameid, strComment);
                if (strComment.length() > 499) {
                    mEtComment.setText(strComment.substring(0, 499));
                    mEtComment.setSelection(mEtComment.getText().toString().length());
                    Toaster.show( "亲，字数超过啦~");
                }

            }
        });
        mTvCommentRule.setOnClickListener(v -> {
            //如何发布优质点评
            showCommentRule();
        });
        initList();

        String cacheEditCommentContent = getSavedEditComment(gameid);
        if (!TextUtils.isEmpty(cacheEditCommentContent)) {
            mEtComment.setText(cacheEditCommentContent);
            mEtComment.setSelection(cacheEditCommentContent.length());
        }
        setPostCommentBtn(TextUtils.isEmpty(mEtComment.getText().toString().trim()));
        setEditText();

        mLlEditComment.setOnClickListener(v -> KeyboardUtils.showSoftInput(_mActivity, mEtComment));
        post(() -> KeyboardUtils.showSoftInput(_mActivity, mEtComment));
    }

    private void addCommentTypeButton(List<CommentTypeListVo.DataBean> list) {
        if (list == null || list.isEmpty()) {
            mGrCommentTab.setVisibility(View.GONE);
            return;
        }
        mGrCommentTab.setVisibility(View.VISIBLE);
        mGrCommentTab.removeAllViews();
        for (CommentTypeListVo.DataBean item : list) {
            RadioButton button = new RadioButton(_mActivity);
            button.setId(item.getType_id());
            button.setText(item.getName());
            button.setGravity(Gravity.CENTER);
            button.setButtonDrawable(null);
            //            button.setTextColor(R.drawable.selector_rb_tab_game_comment_text_color);
            button.setTextAppearance(_mActivity, R.style.rb_style_tab_game_comment);
            button.setBackgroundResource(R.drawable.selector_rb_tab_game_comment_background_color);

            int left = ScreenUtil.dp2px(_mActivity, 12);
            int top = ScreenUtil.dp2px(_mActivity, 8);
            button.setPadding(left, top, left, top);
            button.setTextSize(13);
            button.setTag(item);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.rightMargin = ScreenUtil.dp2px(_mActivity, 10);
            mGrCommentTab.addView(button, params);
        }
        mGrCommentTab.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton button = group.findViewById(checkedId);
            if (button.getTag() != null) {
                CommentTypeListVo.DataBean item = (CommentTypeListVo.DataBean) button.getTag();
                type_id = item.getType_id() == 0 ? "" : String.valueOf(item.getType_id());
            }
        });
        mGrCommentTab.check(list.get(0).getType_id());
    }

    private void setPostCommentBtn(boolean isEditEmpty) {
        if (isEditEmpty) {
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(6 * density);
            gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_eeeeee));

            mTvPostComment.setBackground(gd);
            mTvPostComment.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_848484));
        } else {
            mTvPostComment.setBackgroundResource(R.drawable.ts_shape_0052fe_radius);
            mTvPostComment.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
        }
        mTvPostComment.setEnabled(!isEditEmpty);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Logs.e("onHiddenChanged = " + hidden);
        if (hidden) {
            removeKeyBoardListener();
        } else {
            addKeyBoardListener();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        removeKeyBoardListener();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    SoftKeyBoardListener softKeyBoardListener;

    private void setEditText() {
        //软键盘显示/隐藏监听
        if (softKeyBoardListener == null) {
            softKeyBoardListener = new SoftKeyBoardListener(_mActivity);
        }
        addKeyBoardListener();
        //设置点击事件,显示软键盘
        mEtComment.setOnClickListener(v -> KeyboardUtils.showSoftInput(_mActivity, mEtComment));
        //        setLayoutBg(false);
    }

    private void addKeyBoardListener() {
        if (softKeyBoardListener != null) {
            softKeyBoardListener.setOnSoftKeyBoardChangeListener(new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
                @Override
                public void keyBoardShow(int height) {
                    mEtComment.setFocusable(true);
                    mEtComment.setFocusableInTouchMode(true);
                    mEtComment.setCursorVisible(true);
                    mEtComment.requestFocus();
                    //                    setLayoutBg(true);
                }

                @Override
                public void keyBoardHide(int height) {
                    mEtComment.setFocusable(false);
                    mEtComment.setFocusableInTouchMode(false);
                    mEtComment.setCursorVisible(false);
                    //                    setLayoutBg(false);
                }
            });
        }
    }

    private void removeKeyBoardListener() {
        if (softKeyBoardListener != null) {
            softKeyBoardListener.removeSoftKeyBoardChangeListener();
        }
    }


    private void setLayoutBg(boolean isKeyBoardShow) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.white));
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, isKeyBoardShow ? R.color.color_ffb300 : R.color.color_f2f2f2));

        mLlEditComment.setBackground(gd);
    }

    private ThumbnailAdapter mThumbnailAdapter;
    private int              maxPicCount = 6;

    private void initList() {
        GridLayoutManager layoutManager = new GridLayoutManager(_mActivity, 4);
        mRecyclerViewThumbnail.setLayoutManager(layoutManager);

        List<ThumbnailBean> list = new ArrayList<>();

        ThumbnailBean thumbnailBean = new ThumbnailBean();
        thumbnailBean.setType(1);
        list.add(thumbnailBean);

        mThumbnailAdapter = new ThumbnailAdapter(this, list, maxPicCount);
        mRecyclerViewThumbnail.setAdapter(mThumbnailAdapter);
    }

    private void setCommentInfo(CommentInfoVo.DataBean commentListBean) {
        if (commentListBean == null) {
            return;
        }
        mEtComment.setText(commentListBean.getContent());
        mEtComment.setSelection(mEtComment.getText().toString().length());

        List<ThumbnailBean> thumbnailBeanList = new ArrayList<>();

        if (commentListBean.getPics() != null) {
            for (CommentInfoVo.PicInfoVo picBean : commentListBean.getPics()) {
                ThumbnailBean thumbnailBean = new ThumbnailBean();
                thumbnailBean.setType(0);
                thumbnailBean.setImageType(1);
                thumbnailBean.setHttpUrl(picBean.getHigh_pic_path());
                thumbnailBean.setPic_id(String.valueOf(picBean.getPic_id()));
                thumbnailBeanList.add(thumbnailBean);
            }

            mThumbnailAdapter.addAll(thumbnailBeanList);
            mThumbnailAdapter.notifyDataSetChanged();
            refreshThumbnails();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == ThumbnailAdapter.REQUEST_CODE && data != null) {
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
            List<ThumbnailBean> thumbnailBeanList = new ArrayList<>();
            for (String image : images) {
                ThumbnailBean thumbnailBean = new ThumbnailBean();
                thumbnailBean.setType(0);
                thumbnailBean.setImageType(0);
                thumbnailBean.setLocalUrl(image);

                thumbnailBeanList.add(thumbnailBean);
            }

            mThumbnailAdapter.addAll(thumbnailBeanList);
            mThumbnailAdapter.notifyDataSetChanged();

            refreshThumbnails();
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, state);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void OnEvent(PhotoEvent photoEvent){
        List<ThumbnailBean> thumbnailBeanList = new ArrayList<>();
        for (String image : photoEvent.getImages()) {
            ThumbnailBean thumbnailBean = new ThumbnailBean();
            thumbnailBean.setType(0);
            thumbnailBean.setImageType(0);
            thumbnailBean.setLocalUrl(image);

            thumbnailBeanList.add(thumbnailBean);
        }

        mThumbnailAdapter.addAll(thumbnailBeanList);
        mThumbnailAdapter.notifyDataSetChanged();

        refreshThumbnails();
    }

    public void refreshThumbnails() {
        if (mThumbnailAdapter.getItemCount() >= (maxPicCount + 1)) {
            //去掉加号键
            deleteThumbnailItem(0);
        } else {
            if (!mThumbnailAdapter.isContainsAdd()) {
                //加上加号键
                ThumbnailBean thumbnailBean = new ThumbnailBean();
                thumbnailBean.setType(1);
                mThumbnailAdapter.add(thumbnailBean);
                mThumbnailAdapter.notifyDataSetChanged();
            }
        }
    }

    private void deleteThumbnailItem(int position) {
        mThumbnailAdapter.deleteItem(position);
        mThumbnailAdapter.notifyDataSetChanged();
    }

    /**
     * 发表点评
     */
    private void sentCommentAction() {
        String strComment = mEtComment.getText().toString().trim();
        if (TextUtils.isEmpty(strComment)) {
            Toaster.show( "亲，评论不可为空哦~");
            return;
        }
        if (TextUtils.isEmpty(type_id)) {
            Toaster.show( "亲，请选择点评类型~");
            return;
        }
        if (mThumbnailAdapter != null) {
            List<ThumbnailBean> thumbnailBeanList = mThumbnailAdapter.getDatas();
            List<File> localPathList = new ArrayList<>();

            for (int i = 0; i < thumbnailBeanList.size(); i++) {
                ThumbnailBean thumbnailBean = thumbnailBeanList.get(i);
                if (thumbnailBean.getType() == 1 || thumbnailBean.getImageType() == 1) {
                    continue;
                }
                File file = new File(thumbnailBean.getLocalUrl());
                int fileSize = (int) FileUtils.getFileSize(file, ConstUtils.MemoryUnit.MB);
                if (fileSize > 3) {
                    Toaster.show( "第" + (i + 1) + "张图片大小超过了3MB，请选择小于3MB的图片");
                    return;
                }
                localPathList.add(file);
            }
            compressAction(strComment, localPathList);
        }
    }

    private void compressAction(String comments, List<File> localPathList) {
        if (localPathList == null) {
            return;
        }
        if (localPathList.isEmpty()) {
            commentUpload(comments, localPathList);
        } else {
            loading("图片压缩中...");
            Luban.compress(_mActivity, localPathList)
                    .putGear(Luban.THIRD_GEAR)
                    .setMaxSize(200)
                    .launch(new OnMultiCompressListener() {
                        @Override
                        public void onStart() {
                            Logs.e("compress start");
                        }

                        @Override
                        public void onSuccess(List<File> fileList) {
                            commentUpload(comments, fileList);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Logs.e("compress error");
                            e.printStackTrace();
                        }
                    });
        }

    }

    private void commentUpload(String comments, List<File> localPathList) {
        if (mViewModel != null) {
            Logs.e("type_id = " + type_id);
            mViewModel.submitComment(type_id, String.valueOf(gameid), comments, cid, localPathList, new OnBaseCallback() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onBefore() {
                    super.onBefore();
                    loading();
                }

                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (TextUtils.isEmpty(cid)) {
                                Toaster.show( "已提交，请等待审核！");
                            } else {
                                Toaster.show( "提交成功！");
                            }
                            setFragmentResult(Activity.RESULT_OK, null);
                            pop();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                    deleteSaveEditComment(gameid);
                }
            });
        }
    }

    private void getCommentInfo() {
        if (TextUtils.isEmpty(cid) && UserInfoModel.getInstance().isLogined()) {
            return;
        }

        if (mViewModel != null) {
            mViewModel.modifyComment(cid, new OnBaseCallback<CommentInfoVo>() {
                @Override
                public void onSuccess(CommentInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            setCommentInfo(data.getData());
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    /**
     * 获取点评分类
     */
    private void getCommentType() {
        if (mViewModel != null) {
            mViewModel.getCommentType(new OnBaseCallback<CommentTypeListVo>() {
                @Override
                public void onSuccess(CommentTypeListVo data) {
                    if (data != null && data.isStateOK()) {
                        addCommentTypeButton(data.getData());
                    }
                }
            });
        }
    }

    /**
     * 保存最后一次编辑内容
     *
     * @param gameid
     * @param commentContent
     */
    private void saveEditComment(String gameid, String commentContent) {
        File filePath = SdCardManager.getInstance().getJsonDataDir(_mActivity);
        ACache.get(filePath).put(SP_COMMENT_EDIT_KEY + gameid, commentContent);
    }

    /**
     * 删除最后一次编辑内容
     *
     * @param gameid
     */
    private void deleteSaveEditComment(String gameid) {
        File filePath = SdCardManager.getInstance().getJsonDataDir(_mActivity);
        ACache.get(filePath).remove(SP_COMMENT_EDIT_KEY + gameid);
    }

    /**
     * 获取最后一次编辑内容
     *
     * @param gameid
     * @return
     */
    private String getSavedEditComment(String gameid) {
        try {
            File filePath = SdCardManager.getInstance().getJsonDataDir(_mActivity);
            return ACache.get(filePath).getAsString(SP_COMMENT_EDIT_KEY + gameid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void picDelete(ThumbnailBean thumbnailBean, int position) {
        if (TextUtils.isEmpty(cid)) {
            deleteThumbnailItem(position);
            refreshThumbnails();
        } else {
            AlertDialog dialog = new AlertDialog.Builder(_mActivity)
                    .setTitle("提示")
                    .setMessage("是否删除该图片")
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            deleteCommentPic(thumbnailBean.getPic_id(), position);
                        }
                    }).create();
            dialog.show();
        }
    }

    private void deleteCommentPic(String pic_id, int position) {
        if (TextUtils.isEmpty(cid)) {
            return;
        }

        if (mViewModel != null) {
            mViewModel.deleteCommentPic(cid, pic_id, new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            deleteThumbnailItem(position);
                            refreshThumbnails();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }
}
