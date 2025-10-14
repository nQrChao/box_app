package com.zqhy.app.core.view.game.forum;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.hjq.toast.Toaster;
import com.donkingliang.imageselector.event.PhotoEvent;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.google.android.material.imageview.ShapeableImageView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.ThumbnailBean;
import com.zqhy.app.core.data.model.forum.ForumDetailTitleVo;
import com.zqhy.app.core.data.model.forum.ForumDetailVo;
import com.zqhy.app.core.data.model.forum.ForumImageUploadVo;
import com.zqhy.app.core.data.model.forum.ForumReplyTopExplicitVo;
import com.zqhy.app.core.data.model.forum.ForumReplyTopLikeVo;
import com.zqhy.app.core.data.model.forum.ForumReplyTopVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.tool.MResource;
import com.zqhy.app.core.tool.utilcode.KeyboardUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.community.user.CommunityUserFragment;
import com.zqhy.app.core.view.game.forum.holder.DetailTitleView;
import com.zqhy.app.core.view.game.forum.holder.EmoItemHolder;
import com.zqhy.app.core.view.game.forum.holder.ForumReplyItemHolder;
import com.zqhy.app.core.view.game.forum.tool.SpanMerger;
import com.zqhy.app.core.view.main.holder.GameNoMoreItemHolder;
import com.zqhy.app.core.view.user.CertificationFragment;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder1;
import com.zqhy.app.core.vm.game.ForumViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;

/**
 * @author Administrator
 * @date 2018/11/23
 */

public class ForumLongDetailFragment extends BaseFragment<ForumViewModel> implements ForumReplyItemHolder.OnClickInterface {
    public static ForumLongDetailFragment newInstance(String tid) {
        ForumLongDetailFragment fragment = new ForumLongDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tid", tid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_GAME_DETAIL_STATE;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_forum_detail_long;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    String tid;
    String order_type = "earliest";//newest：最新; hottest:最热; earliest:最早
    int page = 1;
    int view_publisher = 0;//只看楼主 1：是; 0：否

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            tid = getArguments().getString("tid");
        }
        super.initView(state);
        showSuccess();
        find();
        initDetailData();
    }

    public static final int REQUEST_CODE = 0x00000012;
    ShapeableImageView iv_icon;
    TextView tv_nickname;
    TextView tv_like;
    TextView tv_edit;
    TextView tv_send;
    LinearLayout ll_edit;
    LinearLayout ll_editor;
    FrameLayout fl_add_ig;
    ImageView add_image;
    ImageView add_emo;
    ImageView iv_delete;
    ImageView iv_thumbnail;
    private LinearLayout rootLayout;
    EditText et_long;
    XRecyclerView recyclerview;
    RecyclerView emo_recycler;
    private BaseRecyclerAdapter mAdapter;
    private BaseRecyclerAdapter emoAdapter;
    int keyboardHeight = 0;
    private int usableHeightPrevious;
    List<String> emoList = new ArrayList<>();
    int selectionStart;
    List<ForumTextPicBean> forumPicBeans = new ArrayList<>();//表情插入
    SpannableStringBuilder spannableString;

    boolean clickEMO = true;
    //    boolean need
    private void find() {
        findViewById(R.id.iv_back).setOnClickListener(v -> pop());
        iv_icon = findViewById(R.id.iv_icon);
        tv_like = findViewById(R.id.tv_like);
        tv_nickname = findViewById(R.id.tv_nickname);
        ll_editor = findViewById(R.id.ll_editor);
        add_image = findViewById(R.id.add_image);
        ll_edit = findViewById(R.id.ll_edit);
        add_emo = findViewById(R.id.add_emo);
        emo_recycler = findViewById(R.id.emo_recycler);
        iv_delete = findViewById(R.id.iv_delete);
        fl_add_ig = findViewById(R.id.fl_add_ig);
        iv_thumbnail = findViewById(R.id.iv_thumbnail);
        tv_edit = findViewById(R.id.tv_edit);
        tv_send = findViewById(R.id.tv_send);
        et_long = findViewById(R.id.et_long);
        rootLayout = findViewById(R.id.root_layout);
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(_mActivity));
        recyclerview.setFocusable(false);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
        ll_editor.setVisibility(View.GONE);

        add_image.setOnClickListener(v -> {
            ImageSelectorUtils.openPhoto(_mActivity, REQUEST_CODE, false, 1);
        });
        add_emo.setOnClickListener(v -> {
            if (clickEMO) {
                KeyboardUtils.hideSoftInput(_mActivity);
//                add_emo.setImageResource(R.mipmap.ic_forum_post_9_1);
//                ll_editor.setVisibility(View.VISIBLE);
//                emo_recycler.setVisibility(View.VISIBLE);
//                et_long.requestFocus();
            }else {
//                add_emo.setImageResource(R.mipmap.ic_forum_post_9);
//                emo_recycler.setVisibility(View.GONE);
                KeyboardUtils.showSoftInput(_mActivity, et_long);
            }
            clickEMO = !clickEMO;
        });

        iv_delete.setOnClickListener(v -> {
            imgLocalUrl = "";
            fl_add_ig.setVisibility(View.GONE);
            ll_editor.setVisibility(View.VISIBLE);
            KeyboardUtils.showSoftInput(_mActivity, et_long);
        });


        ll_edit.setOnClickListener(v -> {
            ll_editor.setVisibility(View.VISIBLE);
            add_image.setVisibility(View.VISIBLE);
            mRid = 0;
            mAt = 0;
            et_long.setHint("我来说几句");
            KeyboardUtils.showSoftInput(_mActivity, et_long);
        });

        tv_send.setOnClickListener(v -> {
            if (checkLogin()) {
                if (UserInfoModel.getInstance().isSetCertification()) {
                    if (mRid == 0) {
                        scanResource();
                    } else {
                        forumReplyRelease();
                    }
                } else {
                    startFragment(CertificationFragment.newInstance());
                    //ToastT.success("请先实名后评论");
                    Toaster.show("请先实名后评论");
                }
            }
        });
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            rootLayout.getWindowVisibleDisplayFrame(r);
            int screenHeight = rootLayout.getRootView().getHeight();
            keyboardHeight = screenHeight - r.bottom;
            int usableHeightNow = screenHeight - keyboardHeight;
            if (usableHeightPrevious == 0) { // 首次调用，初始化usableHeightPrevious
                Log.i("ForumPostLongFragment", "et_long usableHeightNow: " + usableHeightNow);
                usableHeightPrevious = usableHeightNow;
                return;
            }

            if (usableHeightNow != usableHeightPrevious) {
                int heightDifference = usableHeightPrevious - usableHeightNow;
                if (heightDifference > screenHeight / 4) { // 如果高度变化超过屏幕高度的1/4，认为是键盘弹出
                    // 键盘弹出，调整布局高度
                    adjustLayoutHeight(heightDifference);

                } else if (usableHeightNow > usableHeightPrevious) { // 高度恢复，认为是键盘隐藏
                    // 键盘隐藏，恢复布局高度
                    Log.i("ForumPostLongFragment", "et_long resetLayoutHeight: ");
                    resetLayoutHeight();
                }
                usableHeightPrevious = usableHeightNow;
            }
            if (clickEMO) {
                add_emo.setImageResource(R.mipmap.ic_forum_post_9);
                emo_recycler.setVisibility(View.GONE);
            }else {
                add_emo.setImageResource(R.mipmap.ic_forum_post_9_1);
                ll_editor.setVisibility(View.VISIBLE);
                emo_recycler.setVisibility(View.VISIBLE);
                et_long.requestFocus();
            }
        });

        ForumReplyItemHolder forumReplyItemHolder = new ForumReplyItemHolder(_mActivity);
        forumReplyItemHolder.setClickInterface(this);

        mAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(EmptyDataVo.class, new EmptyItemHolder1(_mActivity))
                .bind(ForumReplyTopVo.class, forumReplyItemHolder)
                .bind(NoMoreDataVo.class, new GameNoMoreItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this)
                .setTag(R.id.tag_sub_fragment, this);
        emoAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(String.class, new EmoItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this)
                .setTag(R.id.tag_sub_fragment, this);

        emo_recycler.setLayoutManager(new GridLayoutManager(_mActivity, 7));
        emo_recycler.setAdapter(emoAdapter);

        for (int i = 0; i <= 68; i++) {
            if (i < 10) {
                emoList.add("emoji_0" + i);
            } else {
                emoList.add("emoji_" + i);
            }
        }
        emoAdapter.setDatas(emoList);
        emoAdapter.setOnItemClickListener((v, position, info) -> {
            if (info instanceof String) {
                String data = (String) info;
                selectionStart = et_long.getSelectionEnd();
                String url = data.replace("[", "").replace("]", "").trim();
                ForumTextPicBean forumTextBean = new ForumTextPicBean();
                forumTextBean.setSpan("[" + data + forumPicBeans.size() + "]");//文本标记，递增去重
                forumTextBean.setUrl(url);
                // 记录表情的起始和结束位置
                forumTextBean.setStart(selectionStart);
                forumTextBean.setEnd(selectionStart + forumTextBean.span.length());

                forumPicBeans.add(forumTextBean);
                spannableString = new SpannableStringBuilder(et_long.getEditableText());
                if (selectionStart >= 0 && selectionStart <= et_long.length()) {
                    spannableString.insert(selectionStart, forumTextBean.span);
                } else {
                    spannableString.insert(spannableString.length(), forumTextBean.span);
                }
                selectionStart = selectionStart + forumTextBean.span.length();
                upDateEditUI(spannableString);
            }
        });
        recyclerview.setAdapter(mAdapter);

        recyclerview.setPullRefreshEnabled(true);
        recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mAdapter.clear();
                page = 1;
                forumReplyTopList();
            }

            @Override
            public void onLoadMore() {
                if (page < 0) {
                    return;
                }
                page++;
                forumReplyTopList();
            }
        });

    }

    private void upDateEditUI(SpannableStringBuilder spannableString) {
        for (int i = 0; i < forumPicBeans.size(); i++) {
            if (forumPicBeans.get(i).canShow) {
                if (spannableString.toString().contains(forumPicBeans.get(i).getSpan())) {
                    Drawable drawable = _mActivity.getResources().getDrawable(MResource.getResourceId(_mActivity, "mipmap", forumPicBeans.get(i).getUrl()));
                    if (drawable != null) {
                        ImageSpan imageSpan;
                        float intrinsicWidth = drawable.getIntrinsicWidth();
                        float intrinsicHeight = drawable.getIntrinsicHeight();
                        drawable.setBounds(0, 0, (int) intrinsicWidth, (int) intrinsicHeight);
                        imageSpan = new ImageSpan(drawable, forumPicBeans.get(i).getUrl(), ImageSpan.ALIGN_BOTTOM);
//
                        int start = spannableString.toString().indexOf(forumPicBeans.get(i).getSpan());
                        int end = spannableString.toString().indexOf(forumPicBeans.get(i).getSpan()) + forumPicBeans.get(i).getSpan().length();
                        spannableString.setSpan(imageSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                } else {
                    forumPicBeans.get(i).canShow = false;
                }
            }
        }
        et_long.setText(spannableString);
        et_long.requestFocus();
        et_long.setSelection(selectionStart);
    }

    private void adjustLayoutHeight(int keyboardHeight) {
        ll_editor.setVisibility(View.VISIBLE);
        if (imgLocalUrl.isEmpty()) {
            fl_add_ig.setVisibility(View.GONE);
        } else {
            GlideUtils.loadLocalImage(_mActivity, imgLocalUrl, iv_thumbnail);
            fl_add_ig.setVisibility(View.VISIBLE);
        }
        emo_recycler.setVisibility(View.GONE);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rootLayout.getLayoutParams();
        params.height = rootLayout.getHeight() - keyboardHeight;
        rootLayout.setLayoutParams(params);
    }

    private void resetLayoutHeight() {
        ll_editor.setVisibility(View.GONE);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rootLayout.getLayoutParams();
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        rootLayout.setLayoutParams(params);
        if (!imgLocalUrl.isEmpty()) {
            tv_edit.setText(et_long.getText().toString() + "[图片]");
        } else {
            if (et_long.getText().toString().isEmpty()) {
                tv_edit.setText("来说几句...");
            } else {
                tv_edit.setText(et_long.getText().toString());
            }
        }
    }

    ForumDetailVo mInfo;

    private void initDetailData() {
        if (mViewModel != null) {
            Map<String, String> params = new TreeMap<>();
            params.put("tid", tid);
            mViewModel.forumDetailList(params, new OnNetWorkListener<ForumDetailVo>() {
                @Override
                public void onBefore() {

                }

                @Override
                public void onFailure(String message) {

                }

                @Override
                public void onSuccess(ForumDetailVo data) {
                    forumReplyTopList();

                    if (data.isStateOK()) {
                        if (data.getData() != null) {
                            mInfo = data.getData();
                            upDataUI(mInfo);
                        } else {
                            //ToastT.error("数据错误,稍后重试");
                            Toaster.show("数据错误,稍后重试");
                        }
                    } else {
                        //ToastT.error(data.getMsg());
                        Toaster.show(data.getMsg());
                    }
                }

                @Override
                public void onAfter() {

                }
            });
        }
    }

    List<ForumReplyTopVo> replyList = new ArrayList<>();
    private void forumReplyTopList() {
        if (mViewModel != null) {
            if (page == 1) {
                if (recyclerview != null) {
                    recyclerview.setNoMore(false);
                }
            }
            Map<String, String> params = new TreeMap<>();
            params.put("tid", tid);
            params.put("page", page + "");
            params.put("order_type", order_type);
            params.put("view_publisher", view_publisher + "");//只看楼主 1：是; 0：否
            mViewModel.forumReplyTopList(params, new OnNetWorkListener<ForumReplyTopVo>() {

                @Override
                public void onBefore() {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (recyclerview != null) {
                                recyclerview.loadMoreComplete();
                            }
                        }
                    });

                }

                @Override
                public void onFailure(String message) {

                }

                @Override
                public void onSuccess(ForumReplyTopVo data) {
                    if (recyclerview != null) {
                        recyclerview.refreshComplete();
                    }
                    if (data.isStateOK()) {
                        if (data.getData() != null && !data.getData().isEmpty()) {
//                            replyList.addAll(data.getData());
                            if (page==1){
                                mAdapter.clear();
                            }
                            mAdapter.addAllData(data.getData());
                            if (data.getData().size() < 12) {
                                //无更多数据
                                recyclerview.setNoMore(true);
                                mAdapter.addData(new NoMoreDataVo());
                            }
                        } else {
                            if (page == 1) {
                                mAdapter.clear();
                                EmptyDataVo emptyDataVo = new EmptyDataVo(R.mipmap.img_empty_data_3)
                                        .setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT)
                                        .setEmptyWord("还没有评论哦")
                                        .setEmptyWordColor(R.color.color_999999)
                                        .setPaddingTop((int) (24 * density))
                                        .setWhiteBg(true);
                                mAdapter.addData(emptyDataVo);
                            } else {
                                mAdapter.addData(new NoMoreDataVo());
                            }
                            //无更多数据
                            page = -1;
                            recyclerview.setNoMore(true);
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        //ToastT.error(_mActivity, data.getMsg());
                        Toaster.show(data.getMsg());
                    }
                }

                @Override
                public void onAfter() {

                }
            });
        }
    }

    private void forumReplyTopLike() {
        //点赞
        if (!checkLogin()) {
            return;
        }

        if (mViewModel != null) {
            Map<String, String> params = new TreeMap<>();
            params.put("tid", tid);
            mViewModel.forumReplyTopLike(params, new OnNetWorkListener<ForumReplyTopLikeVo>() {

                @Override
                public void onBefore() {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (recyclerview != null) {
                                recyclerview.loadMoreComplete();
                            }
                        }
                    });
                }

                @Override
                public void onFailure(String message) {

                }

                @Override
                public void onSuccess(ForumReplyTopLikeVo data) {
                    if (data.isStateOK()) {
                        if ("hit".equals(data.getData().getOperation())) {
                            mInfo.setLike_count(mInfo.getLike_count() + 1);
                            mInfo.setLike_status(1);
                        } else {
                            mInfo.setLike_status(0);
                            mInfo.setLike_count(mInfo.getLike_count() - 1);
                        }
                        if (mInfo.getLike_count() > 99) {
                            tv_like.setText("99+");
                        } else {
                            tv_like.setText(mInfo.getLike_count() + "");
                        }
                        if (mInfo.getLike_status() == 0) {
                            tv_like.setTextColor(Color.parseColor("#333333"));
                            tv_like.setCompoundDrawablesWithIntrinsicBounds(_mActivity.getResources().getDrawable(R.mipmap.ic_fragment_forum_detail_long_like_1), null, null, null);
                        } else {
                            tv_like.setTextColor(Color.parseColor("#5571FE"));
                            tv_like.setCompoundDrawablesWithIntrinsicBounds(_mActivity.getResources().getDrawable(R.mipmap.ic_fragment_forum_detail_long_like_2), null, null, null);
                        }
                    } else {
                        //ToastT.error(_mActivity, data.getMsg());
                        Toaster.show(data.getMsg());
                    }
                }

                @Override
                public void onAfter() {

                }
            });
        }
    }

    private void forumReplyLike(String rid, TextView textView) {
        //一级回复点赞
        if (!checkLogin()) {
            return;
        }

        if (mViewModel != null) {
            Map<String, String> params = new TreeMap<>();
            params.put("rid", rid);
            mViewModel.forumReplyLike(params, new OnNetWorkListener<ForumReplyTopLikeVo>() {

                @Override
                public void onBefore() {

                }

                @Override
                public void onFailure(String message) {

                }

                @Override
                public void onSuccess(ForumReplyTopLikeVo data) {
                    if (data.isStateOK()) {
                        int count = Integer.parseInt(textView.getText().toString());
                        if ("hit".equals(data.getData().getOperation())) {
                            count = count + 1;
                            textView.setTextColor(Color.parseColor("#5571FE"));
                            textView.setCompoundDrawablesWithIntrinsicBounds(_mActivity.getResources().getDrawable(R.mipmap.ic_item_forum_list_1_t), null, null, null);
                        } else {
                            textView.setTextColor(Color.parseColor("#999999"));
                            textView.setCompoundDrawablesWithIntrinsicBounds(_mActivity.getResources().getDrawable(R.mipmap.ic_item_forum_list_1), null, null, null);
                            count = count - 1;
                        }
                        if (count > 99) {
                            textView.setText("99+");
                        } else {
                            textView.setText(count + "");
                        }

                    } else {
                        //ToastT.error(_mActivity, data.getMsg());
                        Toaster.show(data.getMsg());
                    }
                }

                @Override
                public void onAfter() {

                }
            });
        }
    }

    public String convertUnicode(String input) {
        // 正则表达式匹配 &#数字; 的模式
        Pattern pattern = Pattern.compile("&#(\\d+);");
        Matcher matcher = pattern.matcher(input);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            // 获取匹配的十进制数字
            String unicodeStr = matcher.group(1);
            int unicode = Integer.parseInt(unicodeStr);
            // 将十进制数字转换为对应的 Unicode 字符
            char unicodeChar = (char) unicode;
            // 替换匹配的部分为对应的字符
            matcher.appendReplacement(result, String.valueOf(unicodeChar));
        }
        matcher.appendTail(result);

        return result.toString();
    }

    Timer timer;
    String htmlString;
    List<File> localPathList = new ArrayList<>();
    List<String> flagList = new ArrayList<>();

    private void scanResource() {
        if (!checkLogin()) {
            return;
        }
        if (timeOutCount !=0) {
            return;
        }
        Editable editable = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            editable = SpanMerger.mergeSpans(et_long.getEditableText());
        }else {
            editable = et_long.getEditableText();
        }
        htmlString = convertUnicode(Html.toHtml(editable).trim());//Unicode 转汉字再传后台
        Log.e("mergeSpans","之后----------------");
        Log.e("mergeSpans", htmlString);
        //先上传图片
        localPathList.clear();
        flagList.clear();
        if (!imgLocalUrl.isEmpty()) {

            File file;
            //筛选需要上传的图片
            file = new File(imgLocalUrl);
            localPathList.add(file);
            flagList.add(imgLocalUrl);

            Luban.compress(_mActivity, localPathList)
                    .putGear(Luban.THIRD_GEAR)
                    .setMaxSize(2000)
                    .launch(new OnMultiCompressListener() {
                        @Override
                        public void onStart() {
                            Logs.e("compress start");
                        }

                        @Override
                        public void onSuccess(List<File> fileList) {
                            _mActivity.runOnUiThread(() -> {
                                loading("上传图片中...");
                            });
                            if (fileList.size() == flagList.size()) {
                                for (int i = 0; i < fileList.size(); i++) {
                                    imageUpload(flagList.get(i), fileList.get(i));
                                }
                            } else {
                                //ToastT.warning(_mActivity, "压缩失败code:001");
                                Toaster.show("压缩失败code:001");
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Logs.e("compress error");
                            e.printStackTrace();
                            //ToastT.warning(_mActivity, "图片压缩失败,请联系客服");
                            Toaster.show("图片压缩失败,请联系客服");
                        }
                    });

        }else {
            hasPic = false;//没有图片要传
        }
        if (timeOutCount == 0) {
            timer = new Timer();
            //查询图片上传任务进度
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (timeOutCount == 20) {
                        timer.cancel();
                        timeOutCount = 0;
                        _mActivity.runOnUiThread(() -> {
                            loadingComplete();
                            //ToastT.error("上传超时请重试");
                            Toaster.show("上传超时请重试");
                        });
                        return;
                    }
                    if (!hasPic) {
                        // 所有图片上传完成
                        try {
                            timer.cancel();
                            forumReply();
                            timeOutCount = 0;
                        } catch (Exception e) {
                            e.printStackTrace();
                            timeOutCount = 0;
                        }
                    }
                    timeOutCount++;
                }
            }, 0, 500);
        }
    }
    int timeOutCount = 0;
    String filename = "";
    private final Object lock = new Object();
    boolean hasPic = true;
    private void imageUpload(String flag, File file) {
        if (mViewModel != null) {
            mViewModel.imageUpload(flag, file, new OnNetWorkListener<ForumImageUploadVo>() {
                @Override
                public void onBefore() {

                }

                @Override
                public void onFailure(String message) {

                }

                @Override
                public void onSuccess(ForumImageUploadVo bean) {
                    if (bean.isStateOK()) {
                        ForumImageUploadVo data = bean.getData();
                        filename = data.getFilename();
                        hasPic = false;
                    } else {
                        //ToastT.error(bean.getMsg());
                        Toaster.show(bean.getMsg());
                    }
                }

                @Override
                public void onAfter() {

                }
            });
        }
    }

    private void forumReply() {
        timeOutCount = 0;
        //回复点评
        if (mViewModel != null) {
            Map<String, String> params = new TreeMap<>();
            params.put("tid", tid);
            params.put("pic", filename);
            params.put("content", htmlString);
            mViewModel.forumReply(params, new OnNetWorkListener<BaseVo>() {

                @Override
                public void onBefore() {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (recyclerview != null) {
                                recyclerview.loadMoreComplete();
                            }
                        }
                    });
                }

                @Override
                public void onFailure(String message) {
                    loadingComplete();
                }

                @Override
                public void onSuccess(BaseVo data) {
                    loadingComplete();
                    if (data.isStateOK()) {
                        if (timer!=null) {
                            timer.cancel();
                        }
                        hasPic = true;
                        imgLocalUrl = "";
                        fl_add_ig.setVisibility(View.GONE);
                        //ToastT.success("发布成功，审核通过后显示");
                        Toaster.show("发布成功，审核通过后显示");
                        timeOutCount = 0;
                        et_long.setText("");
                        filename = "";
                        htmlString = "";
                        tv_edit.setText("来说几句...");
                        ll_editor.setVisibility(View.GONE);
                        if (mInfo != null) {
                            int replyCount = mInfo.getReply_count() + 1;
                            mInfo.setReply_count(replyCount);
                            mAdapter.clear();
                            page = 1;
                            forumReplyTopList();
                        }
                    } else {
                        //ToastT.error(_mActivity, data.getMsg());
                        Toaster.show(data.getMsg());
                    }
                }

                @Override
                public void onAfter() {

                }
            });
        }
    }

    /**
     * rid	int	是	一级回复ID
     * content	string	是	正文
     * at	int	否	@的回复ID(rid)
     */
    int mRid = 0;
    int mAt = 0;

    private void forumReplyRelease() {
        //回复点评
        if (mViewModel != null) {
            Map<String, String> params = new TreeMap<>();
            params.put("rid", mRid + "");
            params.put("at", mAt + "");
            htmlString = convertUnicode(Html.toHtml(et_long.getEditableText()).trim());//Unicode 转汉字再传后台
            params.put("content", htmlString);
            mViewModel.forumReplyRelease(params, new OnNetWorkListener<BaseVo>() {

                @Override
                public void onBefore() {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (recyclerview != null) {
                                recyclerview.loadMoreComplete();
                            }
                        }
                    });
                }

                @Override
                public void onFailure(String message) {
                    loadingComplete();
                }

                @Override
                public void onSuccess(BaseVo data) {
                    loadingComplete();
                    if (data.isStateOK()) {
                        //ToastT.success("回复成功");
                        Toaster.show("回复成功");
                        et_long.setText("");
                        htmlString = "";
                        mRid = 0;
                        mAt = 0;
                        tv_edit.setText("来说几句...");
                        ll_editor.setVisibility(View.GONE);
                        if (mInfo != null) {
                            mAdapter.clear();
                            forumReplyTopList();
                        }
                    } else {
                        //ToastT.error(_mActivity, data.getMsg());
                        Toaster.show( data.getMsg());
                    }
                }

                @Override
                public void onAfter() {

                }
            });
        }
    }

    boolean needReload = false;
    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        needReload = true;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (needReload) {
            recyclerview.removeAllHeaderView();
            page = 1;
            initDetailData();
            needReload = false;
        }
    }
    private void upDataUI(ForumDetailVo mInfo) {
        tv_nickname.setText(mInfo.getNickname());
        findViewById(R.id.iv_report).setOnClickListener(v ->{
            if (checkLogin()) showReport(tid, mInfo.getNickname());
        } );
        tv_like.setOnClickListener(v -> forumReplyTopLike());
        if (mInfo.getLike_count() > 99) {
            tv_like.setText("99+");
        } else {
            tv_like.setText(mInfo.getLike_count() + "");
        }
        if (mInfo.getLike_status() == 0) {
            tv_like.setTextColor(Color.parseColor("#333333"));
            tv_like.setCompoundDrawablesWithIntrinsicBounds(_mActivity.getResources().getDrawable(R.mipmap.ic_fragment_forum_detail_long_like_1), null, null, null);
        } else {
            tv_like.setTextColor(Color.parseColor("#5571FE"));
            tv_like.setCompoundDrawablesWithIntrinsicBounds(_mActivity.getResources().getDrawable(R.mipmap.ic_fragment_forum_detail_long_like_2), null, null, null);
        }
        GlideApp.with(_mActivity)
                .load(mInfo.getIcon())
                .centerCrop()
                .placeholder(R.mipmap.ic_user_login_new_sign)
                .into(iv_icon);
        iv_icon.setOnClickListener(v -> {
            if (mInfo.getPlat_id()==4) {
                start(CommunityUserFragment.newInstance(mInfo.getUid()));

            }else {
                //ToastT.warning("ta很神秘");
                Toaster.show("ta很神秘");
            }
        });

        tv_nickname.setOnClickListener(v -> {
            if (mInfo.getPlat_id()==4) {
                start(CommunityUserFragment.newInstance(mInfo.getUid()));
            }else {
                //ToastT.warning("ta很神秘");
                Toaster.show("ta很神秘");
            }
        });


        ForumDetailTitleVo forumDetailTitleVo = new ForumDetailTitleVo();
        forumDetailTitleVo.setData(mInfo);
        DetailTitleView detailTitleView = new DetailTitleView(_mActivity, this, forumDetailTitleVo);
        View view = detailTitleView.getView();
        detailTitleView.setClickable(new DetailTitleView.Clickable() {
            @Override
            public void onlyOwner(int i) {
                page = 1;
                view_publisher = i;
                forumReplyTopList();
            }

            @Override
            public void order(String order) {
                page = 1;
                order_type = order;
                forumReplyTopList();
            }

            @Override
            public void openEdit() {
                ll_edit.performClick();
            }
        });
        recyclerview.addHeaderView(view);
    }

    String imgLocalUrl = "";

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void OnEvent(PhotoEvent photoEvent) {
        if (photoEvent.getCore() != REQUEST_CODE) {
            return;
        }
        List<ThumbnailBean> thumbnailBeanList = new ArrayList<>();
        for (String image : photoEvent.getImages()) {
            ThumbnailBean thumbnailBean = new ThumbnailBean();
            thumbnailBean.setType(0);
            thumbnailBean.setImageType(0);
            thumbnailBean.setLocalUrl(image);
            Log.i("ForumPostLongFragment", "imageURL: " + image);
            thumbnailBeanList.add(thumbnailBean);
        }
        if (thumbnailBeanList.size() > 0) {
            imgLocalUrl = thumbnailBeanList.get(0).getLocalUrl();
        }
        ll_editor.setVisibility(View.VISIBLE);
        if (imgLocalUrl.isEmpty()) {
            fl_add_ig.setVisibility(View.GONE);
        } else {
            GlideUtils.loadLocalImage(_mActivity, imgLocalUrl, iv_thumbnail);
            fl_add_ig.setVisibility(View.VISIBLE);
        }
        et_long.postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyboardUtils.showSoftInput(_mActivity, et_long);
            }
        }, 300); // 延迟  毫秒

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onReply(ForumReplyTopVo bean) {
        ll_editor.setVisibility(View.VISIBLE);
        add_image.setVisibility(View.GONE);
        mRid = bean.getRid();
        mAt = 0;
        et_long.setHint("回复" + bean.getNickname() + ":");
        KeyboardUtils.showSoftInput(_mActivity, et_long);
    }

    @Override
    public void onLike(ForumReplyTopVo bean, TextView textView) {

        forumReplyLike(bean.getRid() + "", textView);
    }

    @Override
    public void onSecondReply(int firstRid, ForumReplyTopExplicitVo second) {
        ll_editor.setVisibility(View.VISIBLE);
        add_image.setVisibility(View.GONE);
        mRid = firstRid;
        mAt =  second.getRid();
        et_long.setHint("回复" + second.getNickname() + ":");
        KeyboardUtils.showSoftInput(_mActivity, et_long);
    }


    int moreRid = 0;
    @Override
    public void getMore(int rid, int page,ForumReplyTopVo info,int postion) {
        if (mViewModel!=null){
            //二级回复列表分页
            Map<String, String> params = new TreeMap<>();
            params.put("rid", rid+"");
            params.put("page", page + "");
            mViewModel.forumReplySubList(params, new OnNetWorkListener<ForumReplyTopExplicitVo>() {
                @Override
                public void onBefore() {

                }

                @Override
                public void onFailure(String message) {

                }

                @Override
                public void onSuccess(ForumReplyTopExplicitVo data) {
                    if (data.isStateOK()){
                        if (data.getData() != null && !data.getData().isEmpty()) {
//                            mAdapter.addAllData(data.getData());
                            info.getExplicit().addAll(data.getData());
                            info.setPage(page+1);
                            if (data.getData().size() < 3) {
                                //无更多数据
                                info.setPage(-1);
                            }
                        } else {
                            info.setPage(-1);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onAfter() {

                }
            });
        }
    }

    private void showReport(String tid, String name) {
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_forum_report, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        TextView tv_title = tipsDialog.findViewById(R.id.tv_title);
        EditText et_other = tipsDialog.findViewById(R.id.et_other);
        RadioGroup radioGroup = tipsDialog.findViewById(R.id.radioGroup);
        final String[] commit = {""};
        tv_title.setText("举报发布的内容");
        SpannableStringBuilder spannable = new SpannableStringBuilder(tv_title.getText());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#5571FE"));
            }

            @Override
            public void onClick(View widget) {
                // 在这里处理点击事件
                // 例如，可以弹出一个提示框或者执行其他操作
            }
        };
        String prefix = "@" + name;
        spannable.insert(2, prefix);
        spannable.setSpan(clickableSpan, 2, prefix.length()+2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_title.setText(spannable);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId== R.id.radio5) {
                et_other.setVisibility(View.VISIBLE);
            }else {
                et_other.setVisibility(View.GONE);
            }
        });
        tipsDialog.findViewById(R.id.tv_save).setOnClickListener(view -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId != -1) {
                switch (selectedId) {
                    case R.id.radio1:
                        commit[0] ="有垃圾、广告或拉人信息";
                        break;
                    case R.id.radio2:
                        commit[0] ="有政治敏感、暴力色情信息";
                        break;
                    case R.id.radio3:
                        commit[0] ="辱骂、歧视、恶意引战";
                        break;
                    case R.id.radio4:
                        commit[0] ="涉及抄袭或侵权";
                        break;
                    case R.id.radio5:
                        commit[0] =et_other.getText().toString();
                        break;
                }
                if (commit[0].isEmpty()) {
                    //ToastT.warning("请填写其他原因后提交");
                    Toaster.show("请填写其他原因后提交");
                }else {
                    reportCommit(tid,commit[0]);
                    if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
                }
            } else {
                //ToastT.warning("请选择举报类型后提交");
                Toaster.show("请选择举报类型后提交");
            }

        });

        tipsDialog.show();
    }
    private void reportCommit(String tid, String s) {
        if (mViewModel!=null){
            Map<String, String> params = new TreeMap<>();
            params.put("tid", tid);
            params.put("reason", s);
            mViewModel.forumReport(params, new OnNetWorkListener() {
                @Override
                public void onBefore() {

                }

                @Override
                public void onFailure(String message) {
                    //ToastT.error(message);
                    Toaster.show(message);
                }

                @Override
                public void onSuccess(BaseVo data) {
                    if (data.isStateOK()) {
                        //ToastT.success("举报已提交");
                        Toaster.show("举报已提交");
                    }else {
                        //ToastT.error(data.getMsg());
                        Toaster.show(data.getMsg());
                    }
                }

                @Override
                public void onAfter() {

                }
            });
        }
    }

    private void showReport1(String rid, String name) {
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_forum_report, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        TextView tv_title = tipsDialog.findViewById(R.id.tv_title);
        EditText et_other = tipsDialog.findViewById(R.id.et_other);
        RadioGroup radioGroup = tipsDialog.findViewById(R.id.radioGroup);
        final String[] commit = {""};
        tv_title.setText("举报发布的内容");
        SpannableStringBuilder spannable = new SpannableStringBuilder(tv_title.getText());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#5571FE"));
            }

            @Override
            public void onClick(View widget) {
                // 在这里处理点击事件
                // 例如，可以弹出一个提示框或者执行其他操作

            }
        };
        String prefix = "@" + name;
        spannable.insert(2, prefix);
        spannable.setSpan(clickableSpan, 2, prefix.length()+2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_title.setText(spannable);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId== R.id.radio5) {
                et_other.setVisibility(View.VISIBLE);
            }else {
                et_other.setVisibility(View.GONE);
            }
        });
        tipsDialog.findViewById(R.id.tv_save).setOnClickListener(view -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId != -1) {
                switch (selectedId) {
                    case R.id.radio1:
                        commit[0] ="有垃圾、广告或拉人信息";
                        break;
                    case R.id.radio2:
                        commit[0] ="有政治敏感、暴力色情信息";
                        break;
                    case R.id.radio3:
                        commit[0] ="辱骂、歧视、恶意引战";
                        break;
                    case R.id.radio4:
                        commit[0] ="涉及抄袭或侵权";
                        break;
                    case R.id.radio5:
                        commit[0] =et_other.getText().toString();
                        break;
                }
                if (commit[0].isEmpty()) {
                    //ToastT.warning("请填写其他原因后提交");
                    Toaster.show("请填写其他原因后提交");
                }else {
                    reportCommit1(rid,commit[0]);
                    if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
                }
            } else {
                //ToastT.warning("请选择举报类型后提交");
                Toaster.show("请选择举报类型后提交");
            }
        });

        tipsDialog.show();
    }
    private void reportCommit1(String rid, String s) {
        if (mViewModel!=null){
            Map<String, String> params = new TreeMap<>();
            params.put("rid", rid);
            params.put("reason", s);
            mViewModel.forumReplyReport(params, new OnNetWorkListener() {
                @Override
                public void onBefore() {

                }

                @Override
                public void onFailure(String message) {
                    //ToastT.error(message);
                    Toaster.show(message);
                }

                @Override
                public void onSuccess(BaseVo data) {
                    if (data.isStateOK()) {
                        //ToastT.success("举报已提交");
                        Toaster.show("举报已提交");
                    }else {
                        //ToastT.error(data.getMsg());
                        Toaster.show(data.getMsg());
                    }
                }

                @Override
                public void onAfter() {

                }
            });
        }
    }
    @Override
    public void onReport(String rid, String name) {
        //举报回复
        showReport1(rid,name);
    }

}
