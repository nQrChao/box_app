package com.zqhy.app.core.view.game.forum;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.chaoji.other.blankj.utilcode.util.Logs;
import com.chaoji.other.hjq.toast.Toaster;
import com.donkingliang.imageselector.event.PhotoEvent;
import com.google.android.material.imageview.ShapeableImageView;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.ThumbnailBean;
import com.zqhy.app.core.data.model.forum.ForumCategoryVo;
import com.zqhy.app.core.data.model.forum.ForumImageUploadVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.tool.MResource;
import com.zqhy.app.core.tool.utilcode.KeyboardUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.view.community.comment.ThumbnailAdapter1;
import com.zqhy.app.core.view.game.forum.holder.CategoryItemHolder;
import com.zqhy.app.core.view.game.forum.holder.EmoItemHolder;
import com.zqhy.app.core.view.game.forum.tool.ForumPopWindow;
import com.zqhy.app.core.view.game.forum.tool.SpanMerger;
import com.zqhy.app.core.vm.game.ForumViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.LitePal;

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

public class ForumPostFragment extends BaseFragment<ForumViewModel> implements View.OnClickListener {
    public static ForumPostFragment newInstance(long baseId) {
        ForumPostFragment fragment = new ForumPostFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("baseId", baseId);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ForumPostFragment newInstance(String gameId, String gameIcon, String gameName) {
        ForumPostFragment fragment = new ForumPostFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gameId", gameId);
        bundle.putString("gameIcon", gameIcon);
        bundle.putString("gameName", gameName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_GAME_DETAIL_STATE;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_forum_post;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private RecyclerView mRecyclerViewThumbnail;
    RecyclerView category_recycle;
    private ThumbnailAdapter1 mThumbnailAdapter;
    RecyclerView emo_recycler;
    private BaseRecyclerAdapter categoryAdapter;
    private BaseRecyclerAdapter emoAdapter;
    ForumDraftsBean forumDraftsBean;
    LinearLayout ll_post;
    LinearLayout ll_send;
    EditText et_title;
    EditText et_long;
    TextView max_title;
    TextView tv_push;
    ImageView del_title;
    TextView tv_game_name;
    ShapeableImageView iv_game_icon;
    TextView max_text;
    NestedScrollView nestedscrollview;
    int maxTitleLength = 40;
    int maxTextLength = 1000;
    int keyboardHeight = 0;
    int selectionStart;
    TextView tv_drafts;
    private LinearLayout rootLayout;
    List<File> localPathList = new ArrayList<>();
    List<String> flagList = new ArrayList<>();
    boolean isClickLongEt = false;
    ImageView add_emo;
    List<String> emoList = new ArrayList<>();

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            baseId = getArguments().getLong("baseId");
            gameid = getArguments().getString("gameId");
            gamename = getArguments().getString("gameName");
            gameIcon = getArguments().getString("gameIcon");
            cid = getArguments().getString("cid");
        }
        super.initView(state);
        showSuccess();
        findViewById(R.id.iv_back).setOnClickListener(v -> pop());
        category_recycle = findViewById(R.id.category_recycle);
        mRecyclerViewThumbnail = findViewById(R.id.recyclerView_thumbnail);
        findViewById(R.id.tv_posting_long).setOnClickListener(v -> {
            if (checkLogin()) {
                needPop = true;
                startWithPop(ForumPostLongFragment.newInstance(gameid, gameIcon, gamename));
            }
        });
        findViewById(R.id.tv_draft).setOnClickListener(v -> {
            if (checkLogin()) {
                start(new ForumDraftsFragment());
            }
        });
        initList();
        emo_recycler = findViewById(R.id.emo_recycler);
        add_emo = findViewById(R.id.add_emo);
        tv_push = findViewById(R.id.tv_push);
        tv_game_name = findViewById(R.id.tv_game_name);
        iv_game_icon = findViewById(R.id.iv_game_icon);
        ll_post = findViewById(R.id.ll_post);
        ll_send = findViewById(R.id.ll_send);
        tv_drafts = findViewById(R.id.tv_drafts);
        nestedscrollview = findViewById(R.id.nestedscrollview);
        category_recycle = findViewById(R.id.category_recycle);
        rootLayout = findViewById(R.id.root_layout);
        et_long = findViewById(R.id.et_long);
        findViewById(R.id.add_image).setOnClickListener(this);
        findViewById(R.id.add_emo).setOnClickListener(this);
        findViewById(R.id.text_style).setOnClickListener(this);
        del_title = findViewById(R.id.del_title);
        max_text = findViewById(R.id.max_text);
        et_title = findViewById(R.id.et_title);
        max_title = findViewById(R.id.max_title);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        category_recycle.setLayoutManager(linearLayoutManager);
        categoryAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(ForumCategoryVo.DataBean.class, new CategoryItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);
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
                forumTextBean.setSpan("[" + data + forumPicBeans1.size() + "]");//文本标记，递增去重
                forumTextBean.setUrl(url);
                // 记录表情的起始和结束位置
                forumTextBean.setStart(selectionStart);
                forumTextBean.setEnd(selectionStart + forumTextBean.span.length());

                forumPicBeans1.add(forumTextBean);
                spannableString = new SpannableStringBuilder(et_long.getEditableText());
                if (selectionStart >= 0 && selectionStart <= et_long.length()) {
                    spannableString.insert(selectionStart, forumTextBean.span);
                } else {
                    spannableString.insert(spannableString.length(), forumTextBean.span);
                }
                selectionStart = selectionStart + forumTextBean.span.length();
                et_long.setText(spannableString);
                et_long.setSelection(selectionStart);
//                upDateEditUI();
            }
        });

        category_recycle.setAdapter(categoryAdapter);
        categoryAdapter.setOnItemClickListener((v, position, data) -> {
            for (ForumCategoryVo.DataBean dataBean : categoryList) {
                dataBean.setClick(false);
            }
            cate_id = categoryList.get(position).getCate_id();
            categoryList.get(position).setClick(true);
            categoryAdapter.setDatas(categoryList);
            categoryAdapter.notifyDataSetChanged();
        });
        del_title.setVisibility(et_title.getText().length() == 0 ? View.GONE : View.VISIBLE);
        del_title.setOnClickListener(v -> {
            et_title.setText("");
        });
        tv_drafts.setOnClickListener(v -> {
            //存草稿
            showTips1();
        });
        tv_push.setOnClickListener(v -> {
            if (et_title.getText().toString().length() > 40) {
                Toaster.show("标题字数最多40字");
                //ToastT.warning("标题字数最多40字");
                return;
            }
            if (et_long.getText().toString().length() > 3) {
                if (et_long.getText().toString().length() > 1000) {
                    Toaster.show("正文字数最多1000字");
                    //ToastT.warning("正文字数最多1000字");
                    return;
                }
                scanResource();
            } else {
                Toaster.show("正文字数至少4字");
                //ToastT.warning("正文字数至少4字");
            }
        });

        findViewById(R.id.tv_draft).setOnClickListener(v -> {
            if (checkLogin()) {
                start(new ForumDraftsFragment());
            }
        });


        et_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 不需要在此处理
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 可以在这里更新UI元素，比如剩余字数提示
            }

            @Override
            public void afterTextChanged(Editable s) {
//                "/"+maxTitleLength
                max_title.setVisibility(s.length() == 0 ? View.GONE : View.VISIBLE);
                del_title.setVisibility(s.length() == 0 ? View.GONE : View.VISIBLE);
                SpannableString ss = new SpannableString(s.length() + "/" + maxTitleLength);
                if (s.length() >= maxTitleLength) {
                    ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF1414")), 0, ss.length() - 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                } else {
                    ss.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), 0, ss.length() - 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                max_title.setText(ss);
            }
        });
        et_long.addTextChangedListener(myWatcher);
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
            int selectionEnd = et_long.getSelectionStart();
            // 获取 Layout 对象
            Layout layout = et_long.getLayout();
            int line1;
            int line;
            if (isAddPic) {
                line = layout.getLineForOffset(lastAddPicLine);
            } else {
                line = layout.getLineForOffset(selectionEnd);
            }
            line1 = layout.getLineBottom(line);
            isClickLongEt = !et_title.hasFocus();
            int lineBottom = line1 - 50 > 0 ? line1 - 50 : line1;
            if (usableHeightNow != usableHeightPrevious) {
                int heightDifference = usableHeightPrevious - usableHeightNow;
                if (heightDifference > screenHeight / 4) { // 如果高度变化超过屏幕高度的1/4，认为是键盘弹出
                    // 键盘弹出，调整布局高度
                    Log.i("ForumPostLongFragment", "et_long usableHeightNow: " + heightDifference);
                    Log.i("ForumPostLongFragment", "et_long selectionStart: " + selectionStart);
                    adjustLayoutHeight(heightDifference);
                    if (et_long.getText().length() == 0) {
                        if (isClickLongEt) {
                            et_long.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    et_long.requestFocus();
                                    et_long.setSelection(selectionEnd);
                                }
                            }, 500); // 延迟 100 毫秒
                        }
                    } else {
                        if (isClickLongEt) {
                            et_long.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    et_long.requestFocus();
                                    nestedscrollview.smoothScrollTo(0, lineBottom);
//                                    et_long.scrollTo(0,lineBottom);
                                    et_long.setSelection(selectionEnd);
                                    findViewById(R.id.add_image).setOnClickListener(ForumPostFragment.this);
                                    if (isAddPic) {
                                        isAddPic = false;
                                    }
                                }
                            }, 500); // 延迟 100 毫秒
                        }
                    }
                } else if (usableHeightNow > usableHeightPrevious) { // 高度恢复，认为是键盘隐藏
                    // 键盘隐藏，恢复布局高度
                    Log.i("ForumPostLongFragment", "et_long resetLayoutHeight: ");
                    resetLayoutHeight();
                    if (isClickLongEt) {
                        et_long.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                et_long.performClick();
                                nestedscrollview.smoothScrollTo(0, lineBottom);
                                et_long.setSelection(selectionEnd);
                                findViewById(R.id.add_image).setOnClickListener(ForumPostFragment.this);
                                if (isAddPic) {
                                    isAddPic = false;
                                }
                            }
                        }, 500); // 延迟 100 毫秒
                    }
                }
                usableHeightPrevious = usableHeightNow;
            }
        });
        initCategory();

        if (baseId != 0) {
            et_long.post(() -> {
                //草稿箱打开
                forumDraftsBean = LitePal.find(ForumDraftsBean.class, baseId);
                if (forumDraftsBean != null) {
                    et_long.removeTextChangedListener(myWatcher);
                    gameid = forumDraftsBean.getGameId();
                    gameIcon = forumDraftsBean.getGameIcon();
                    gamename = forumDraftsBean.getGameName();
                    List<String> hobbies = forumDraftsBean.getHobbies();
                    if (!hobbies.isEmpty()) {
                        List<ThumbnailBean> thumbnailBeanList = new ArrayList<>();
                        for (String hobby : hobbies) {
                            Drawable drawable = loadImageFromPath(hobby);
                            if (drawable != null) {
                                ThumbnailBean thumbnailBean = new ThumbnailBean();
                                thumbnailBean.setType(0);
                                thumbnailBean.setImageType(0);
                                thumbnailBean.setLocalUrl(hobby);
                                thumbnailBeanList.add(thumbnailBean);
                            } else {
                                if (hobby != null && !hobby.contains("emoji_")) {
                                    //ToastT.error("无效路径:" + hobby);
                                    Toaster.show("无效路径:" + hobby);
                                }
                            }
                        }
                        forumPicBeans.clear();
                        for (int i = 0; i < thumbnailBeanList.size(); i++) {
                            ForumTextPicBean forumTextBean = new ForumTextPicBean();
                            forumTextBean.setUrl(thumbnailBeanList.get(i).getLocalUrl());
                            forumTextBean.setSpan("img" + i);
                            forumPicBeans.add(forumTextBean);
                        }
                        mThumbnailAdapter.addAll(thumbnailBeanList);
                        mThumbnailAdapter.notifyDataSetChanged();
                        refreshThumbnails();
                    }
                    tv_game_name.setText(forumDraftsBean.getGameName().replace("\n", " "));
                    et_title.setText(forumDraftsBean.getTitle());
                    Spanned sps = Html.fromHtml(forumDraftsBean.getContent());
                    Log.d("ForumPostLongFragment", "forumDraftsBean.getContent(): " + forumDraftsBean.getContent());
                    Log.d("ForumPostLongFragment", "sps: " + sps);
                    Editable original = new SpannableStringBuilder(sps);//原始数据
                    Editable spanned = new SpannableStringBuilder(sps);//需要替换图片占位
                    SpannedStyleParser.printSpanInfo(spanned);
                    List<ForumTextPicBean> picList = new ArrayList<>();
                    List<Object> allSpans = SpannedStyleParser.getAllSpans(spanned);
                    int cont = 0;
                    for (int i = 0; i < allSpans.size(); i++) {
                        if (allSpans.get(i) instanceof ImageSpan) {
                            ForumTextPicBean forumTextBean = new ForumTextPicBean();
                            int start = spanned.getSpanStart(allSpans.get(i));
                            int end = spanned.getSpanEnd(allSpans.get(i));
                            spanned.replace(start, end, "[IMAGE" + cont + "]");
                            forumTextBean.setSpan("[IMAGE" + cont + "]");//文本标记，递增去重
                            forumTextBean.setStart(start);
                            forumTextBean.setEnd(end);
                            picList.add(forumTextBean);
                            cont++;
                        }
                    }

                    et_long.setText(spanned);
                    StringBuilder finalText = new StringBuilder(et_long.getText().toString());
                    SpannedStyleParser.width = et_long.getWidth() - 100;

                    List<ForumStyleBean> spansStyleList = SpannedStyleParser.getSpansStyleOfType(spanned, finalText);//数据库文字样式模块
                    List<ForumTextPicBean> imageSpans = SpannedStyleParser.getImageSpansOfType(original, picList, _mActivity);//数据库图片模块
                    forumPicBeans1.addAll(imageSpans);
                    meTextStyleList.addAll(spansStyleList);
                    upDateEditUI();
                    SpannableString ss = new SpannableString(finalText.length() + "/" + maxTextLength);
                    if (finalText.length() >= maxTextLength) {
                        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF1414")), 0, ss.length() - 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    } else {
                        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), 0, ss.length() - 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }
                    max_text.setText(ss);
                    GlideApp.with(this)
                            .load(forumDraftsBean.getGameIcon())
                            .placeholder(R.mipmap.ic_placeholder)
                            .error(R.mipmap.ic_placeholder)
                            .into(iv_game_icon);
                } else {
                    //ToastT.error("数据库错误");
                    Toaster.show("数据库错误");
                    pop();
                }
                showSuccess();
            });
        } else {
            //默认
            showSuccess();
            tv_game_name.setText(gamename.replace("\n", " "));
            GlideApp.with(this)
                    .load(gameIcon)
                    .placeholder(R.mipmap.ic_placeholder)
                    .error(R.mipmap.ic_placeholder)
                    .into(iv_game_icon);
        }
    }

    List<ForumTextPicBean> forumPicBeans = new ArrayList<>();
    List<ForumTextPicBean> forumPicBeans1 = new ArrayList<>();//emo表情
    String htmlString;
    Timer timer;

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


    private void scanResource() {
        if (timeOutCount != 0) {
            return;
        }
        Editable editable = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            editable = SpanMerger.mergeSpans(et_long.getEditableText());
        } else {
            editable = et_long.getEditableText();
        }
        htmlString = convertUnicode(Html.toHtml(editable).trim());//Unicode 转汉字再传后台
        Log.e("mergeSpans", "之后----------------");
        Log.e("mergeSpans", htmlString);
        //先上传图片
        needPushCount = 0;//需要上传的图片个数
        localPathList.clear();
        flagList.clear();
        if (!forumPicBeans.isEmpty()) {
            for (ForumTextPicBean forumPicBean : forumPicBeans) {
                File file;
                //筛选需要上传的图片
                if (forumPicBean.isCanShow() && forumPicBean.getUrl() != null) {
                    if (!forumPicBean.getUrl().isEmpty()) {
                        if (forumPicBean.getWebUrl().isEmpty()) {
                            file = new File(forumPicBean.getUrl());
                            localPathList.add(file);
                            flagList.add(forumPicBean.getSpan());
                        }
                    }
                }
            }
            needPushCount = localPathList.size();
            if (localPathList.size() != 0) {
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
                                    loading("开始上传图片...");
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
            }

        }
        //查询图片上传任务进度
        if (timeOutCount == 0) {
            timer = new Timer();
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
                    boolean can;
                    synchronized (lock) {
                        can = true;
                        for (ForumTextPicBean forumPicBean : forumPicBeans) {
                            if (forumPicBean.getWebUrl().isEmpty() && forumPicBean.getUrl() != null) {
                                can = false;
                                break;
                            }
                        }
                    }
                    if (can) {
                        // 所有图片上传完成
                        try {
                            timer.cancel();
                            pushData();
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
    int needPushCount = 0;
    private final Object lock = new Object();
    boolean needWatcher = true;
    SpannableStringBuilder spannableString;
    List<ForumStyleBean> meTextStyleList = new ArrayList<>();
    TextWatcher myWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.i("myWatcher", "meTextStyleList 长度 " + meTextStyleList.size());
            if (count > before) {
                Log.i("myWatcher", "从索引 " + start + " 开始新增了 " + (count - before) + " 个字符   " + s);
                CharSequence insertedText = s.subSequence(start + before, start + count);
                char[] charArray = insertedText.toString().toCharArray();
                if (insertedText.toString().contains("emoji_")) {
                    for (int i = 0; i < charArray.length; i++) {
                        ForumStyleBean forumStyleBean = new ForumStyleBean(charArray[i]);
                        meTextStyleList.add( forumStyleBean);
                    }
                    Log.i("myWatcher", "meTextStyleList 长度 结束 " + meTextStyleList.size());
                    return;
                }
                Log.i("myWatcher", "新增内容:" + insertedText);
                for (int i = 0; i < charArray.length; i++) {
                    ForumStyleBean forumStyleBean = new ForumStyleBean(charArray[i]);
                    forumStyleBean.setB(bold);
                    forumStyleBean.setI(italic);
                    forumStyleBean.setU(underline);
                    meTextStyleList.add( forumStyleBean);
                }
                Log.i("myWatcher", "新增meTextStyleList:" + meTextStyleList.size());
                StringBuilder s2 = new StringBuilder();
                for (ForumStyleBean forumStyleBean : meTextStyleList) {
                    s2.append(forumStyleBean.getC());
                }
                Log.i("myWatcher", "onTextChanged 结束 s2:" + s2);
            } else if (count < before) {
                Log.i("myWatcher", "从索引 " + start + " 开始删除了 " + before + " 个字符   " + s.toString());

                try {
                    for (int i = start + before - 1; i >= start; i--) {
                        Log.i("myWatcher", "i:" + i);
                        Log.i("myWatcher", "  删除" + meTextStyleList.get(i).c);
                        meTextStyleList.remove(meTextStyleList.get(i));
                    }
                } catch (Exception e) {

                }
                Log.i("myWatcher", "删除meTextStyleList:" + meTextStyleList.size());
            }
            Log.i("myWatcher", "meTextStyleList 长度 结束 " + meTextStyleList.size());
        }

        @Override
        public void afterTextChanged(Editable s) {
            max_text.setVisibility(s.length() == 0 ? View.GONE : View.VISIBLE);
            SpannableString ss = new SpannableString(s.length() + "/" + maxTextLength);
            if (s.length() >= maxTextLength) {
                ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF1414")), 0, ss.length() - 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            } else {
                ss.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), 0, ss.length() - 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            max_text.setText(ss);

            Log.i("ForumPostLongFragment", "et_long afterTextChanged: " + s.toString());
            upDateEditUI();
        }
    };

    List<ForumCategoryVo.DataBean> categoryList = new ArrayList<>();

    private void initCategory() {
        if (mViewModel != null) {
            mViewModel.getCategoryData(new OnNetWorkListener<ForumCategoryVo>() {
                @Override
                public void onBefore() {

                }

                @Override
                public void onFailure(String message) {

                }

                @Override
                public void onSuccess(ForumCategoryVo data) {
                    if (data.isStateOK()) {
                        if (!data.getData().isEmpty()) {
                            List<ForumCategoryVo.DataBean> data1 = data.getData();
                            for (ForumCategoryVo.DataBean dataBean : data1) {
                                if (dataBean.getOnly_show() == 0) {
                                    categoryList.add(dataBean);
                                }
                            }
                            if (categoryList.isEmpty()) {
                                return;
                            }
                            categoryList.get(0).setClick(true);
                            cate_id = categoryList.get(0).getCate_id();
                            categoryAdapter.clear();
                            categoryAdapter.addAllData(categoryList);
                        }
                    }
                }

                @Override
                public void onAfter() {

                }
            });
        }
    }

    long baseId = 0;
    private int maxPicCount = 9;

    private void initList() {
//        GridLayoutManager layoutManager = new GridLayoutManager(_mActivity, 4);
        LinearLayoutManager indicatorLayoutManager = new LinearLayoutManager(_mActivity, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerViewThumbnail.setLayoutManager(indicatorLayoutManager);

        List<ThumbnailBean> list = new ArrayList<>();

        ThumbnailBean thumbnailBean = new ThumbnailBean();
        thumbnailBean.setType(1);
        list.add(thumbnailBean);

        mThumbnailAdapter = new ThumbnailAdapter1(this, list, maxPicCount);
        mRecyclerViewThumbnail.setAdapter(mThumbnailAdapter);
    }

    String gameid, gamename, cid, gameIcon;

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

    private void deleteThumbnailItem(int position) {
        mThumbnailAdapter.deleteItem(position);
        List<ThumbnailBean> datas = mThumbnailAdapter.getDatas();
        forumPicBeans.clear();
        for (int i = 0; i < datas.size(); i++) {
            ForumTextPicBean forumTextBean = new ForumTextPicBean();
            forumTextBean.setUrl(datas.get(i).getLocalUrl());
            forumTextBean.setSpan("img" + i);
            forumPicBeans.add(forumTextBean);
        }
        mThumbnailAdapter.notifyDataSetChanged();
    }

    public void refreshThumbnails() {
        if (mThumbnailAdapter.getItemCount() >= (maxPicCount + 1)) {
            //去掉加号键
            deleteThumbnailItem(maxPicCount);
        } else {
            if (!mThumbnailAdapter.isContainsAdd()) {
                //加上加号键
                ThumbnailBean thumbnailBean = new ThumbnailBean();
                thumbnailBean.setType(1);
                thumbnailBean.setLocalUrl("");
                mThumbnailAdapter.add(thumbnailBean);
                mThumbnailAdapter.notifyDataSetChanged();
            }
            List<ThumbnailBean> datas = mThumbnailAdapter.getDatas();
            forumPicBeans.clear();
            for (int i = 0; i < datas.size(); i++) {
                ForumTextPicBean forumTextBean = new ForumTextPicBean();
                forumTextBean.setUrl(datas.get(i).getLocalUrl());
                forumTextBean.setSpan("img" + i);
                forumPicBeans.add(forumTextBean);
            }
        }
    }

    private void deleteCommentPic(String pic_id, int position) {
        if (TextUtils.isEmpty(cid)) {
            return;
        }
        deleteThumbnailItem(position);
        refreshThumbnails();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void OnEvent(PhotoEvent photoEvent) {
        if (photoEvent.getCore() != ThumbnailAdapter1.REQUEST_CODE) {
            return;
        }
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

    boolean clickEMO = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_image:
                //添加图片
                break;
            case R.id.add_emo:
                if (!clickEMO) {
                    add_emo.setImageResource(R.mipmap.ic_forum_post_9_1);
                    emo_recycler.setVisibility(View.VISIBLE);
                    et_long.requestFocus();
                    ll_send.setVisibility(View.GONE);
                    clickEMO = true;
                    KeyboardUtils.hideSoftInput(_mActivity);
                } else {
                    ll_send.setVisibility(View.VISIBLE);
                    add_emo.setImageResource(R.mipmap.ic_forum_post_9);
                    emo_recycler.setVisibility(View.GONE);
                    clickEMO = false;
                    KeyboardUtils.showSoftInput(_mActivity, et_long);
                }

                break;
            case R.id.text_style:
                showTextStylePop(v);
                break;
        }
    }

    boolean isAddPic = false;
    int lastAddPicLine = 0;
    int lastAddPicEnd = 0;
    int cate_id = -1;
    boolean bold = false;
    boolean italic = false;
    boolean underline = false;
    View contentView;
    ImageView iv_bold;
    ImageView iv_italic;
    ImageView iv_underline;
    ForumPopWindow popWindow;

    private void showTextStylePop(View v) {
        if (contentView == null) {
            contentView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_forum, null);
            iv_bold = contentView.findViewById(R.id.iv_bold);
            iv_italic = contentView.findViewById(R.id.iv_italic);
            iv_underline = contentView.findViewById(R.id.iv_underline);
            if (bold) {
                iv_bold.setImageResource(R.mipmap.ic_forum_post_7_11);
            } else {
                iv_bold.setImageResource(R.mipmap.ic_forum_post_7_1);
            }
            if (italic) {
                iv_italic.setImageResource(R.mipmap.ic_forum_post_7_21);
            } else {
                iv_italic.setImageResource(R.mipmap.ic_forum_post_7_2);
            }
            if (underline) {
                iv_underline.setImageResource(R.mipmap.ic_forum_post_7_31);
            } else {
                iv_underline.setImageResource(R.mipmap.ic_forum_post_7_3);
            }
            iv_bold.setOnClickListener(v1 -> {
                bold = !bold;
                if (bold) {
                    iv_bold.setImageResource(R.mipmap.ic_forum_post_7_11);
                } else {
                    iv_bold.setImageResource(R.mipmap.ic_forum_post_7_1);
                }
            });
            iv_italic.setOnClickListener(v1 -> {
                italic = !italic;
                if (italic) {
                    iv_italic.setImageResource(R.mipmap.ic_forum_post_7_21);
                } else {
                    iv_italic.setImageResource(R.mipmap.ic_forum_post_7_2);
                }
            });
            iv_underline.setOnClickListener(v1 -> {
                underline = !underline;
                if (underline) {
                    iv_underline.setImageResource(R.mipmap.ic_forum_post_7_31);
                } else {
                    iv_underline.setImageResource(R.mipmap.ic_forum_post_7_3);
                }
            });

        }
        if (popWindow == null) {
            popWindow = new ForumPopWindow.PopupWindowBuilder(_mActivity)
                    .setView(contentView)
                    .setBgDarkAlpha(0f)
                    .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                    .setFocusable(false)
                    .setOutsideTouchable(true)
                    .create();
            popWindow = popWindow.showAsDropDown(v, 0, (int) (-v.getHeight() * 2));//指定view正上方
            popWindow.getPopupWindow().setOnDismissListener(() -> {
                popWindow = null;
            });
        } else {
            if (popWindow.isShow()) {
                popWindow.dissmiss();
            }
        }
    }

    private void showTips1() {
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_forum_save_drafts, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.findViewById(R.id.tv_save).setOnClickListener(view -> {
            saveDrafts();
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });

        tipsDialog.show();
    }

    private void showTips2() {
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_forum_save_drafts2, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            needPop = true;
            pop();
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.findViewById(R.id.tv_save).setOnClickListener(view -> {
            saveDrafts();
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });

        tipsDialog.show();
    }

    private void saveDrafts() {
        needPop = true;
        if (forumDraftsBean == null) {
            ForumDraftsBean forumDraftsBean = new ForumDraftsBean();
            forumDraftsBean.setTitle(et_title.getText().toString().isEmpty() ? "" : et_title.getText().toString());
            if (spannableString != null) {
                CharSequence subSequence = spannableString.subSequence(0, Math.min(60, spannableString.length()));
                if (subSequence.toString().isEmpty()) {
                    if (forumPicBeans.size() != 0) {
                        if (forumPicBeans.size() != maxPicCount) {
                            forumDraftsBean.setTips("[图片]x" + (forumPicBeans.size() - 1));
                        } else {
                            forumDraftsBean.setTips("[图片]x" + (forumPicBeans.size()));
                        }
                    } else {
                        forumDraftsBean.setTips("");
                    }
                } else {
                    forumDraftsBean.setTips(subSequence.toString());
                }
            } else {
                if (forumPicBeans.size() != 0) {
                    if (forumPicBeans.size() != maxPicCount) {
                        forumDraftsBean.setTips("[图片]x" + (forumPicBeans.size() - 1));
                    } else {
                        forumDraftsBean.setTips("[图片]x" + (forumPicBeans.size()));
                    }
                } else {
                    forumDraftsBean.setTips("");
                }
            }
            forumDraftsBean.setGameId(gameid);
            forumDraftsBean.setGameIcon(gameIcon);
            forumDraftsBean.setGameName(gamename);
            forumDraftsBean.setType(2);
            forumDraftsBean.setUid(UserInfoModel.getInstance().getUID());
            forumDraftsBean.setTime(System.currentTimeMillis());
            forumDraftsBean.setContent(Html.toHtml(et_long.getEditableText()).trim());
            for (ForumTextPicBean forumPicBean : forumPicBeans) {
                if (forumPicBean.getUrl() != null) {
                    picList.add(forumPicBean.getUrl());
                }
            }
            if (!picList.isEmpty()) {
                forumDraftsBean.setHobbies(picList);
            }

            if (forumDraftsBean.save()) {
                EventBus.getDefault().post(new EventCenter(EventConfig.FORUM_UPDATE_CODE));
                //ToastT.success("已保存至草稿箱");
                Toaster.show("已保存至草稿箱");
                pop();
            }
        } else {
            forumDraftsBean.setTitle(et_title.getText().toString().isEmpty() ? "" : et_title.getText().toString());
            forumDraftsBean.setGameId(gameid);
            forumDraftsBean.setGameIcon(gameIcon);
            forumDraftsBean.setGameName(gamename);
            forumDraftsBean.setType(2);
            if (spannableString != null) {
                CharSequence subSequence = spannableString.subSequence(0, Math.min(60, spannableString.length()));
                if (subSequence.toString().isEmpty()) {
                    if (forumPicBeans.size() != 0) {
                        if (forumPicBeans.size() != maxPicCount) {
                            forumDraftsBean.setTips("[图片]x" + (forumPicBeans.size() - 1));
                        } else {
                            forumDraftsBean.setTips("[图片]x" + (forumPicBeans.size()));
                        }
                    } else {
                        forumDraftsBean.setTips("");
                    }
                } else {
                    forumDraftsBean.setTips(subSequence.toString());
                }
            } else {
                if (forumPicBeans.size() != 0) {
                    if (forumPicBeans.size() != maxPicCount) {
                        forumDraftsBean.setTips("[图片]x" + (forumPicBeans.size() - 1));
                    } else {
                        forumDraftsBean.setTips("[图片]x" + (forumPicBeans.size()));
                    }
                } else {
                    forumDraftsBean.setTips("");
                }
            }
            forumDraftsBean.setUid(UserInfoModel.getInstance().getUID());
            forumDraftsBean.setTime(System.currentTimeMillis());
            forumDraftsBean.setContent(Html.toHtml(et_long.getEditableText()).trim());
            for (ForumTextPicBean forumPicBean : forumPicBeans) {
                if (forumPicBean.getUrl() != null) {
                    picList.add(forumPicBean.getUrl());
                }
            }
            if (!picList.isEmpty()) {
                forumDraftsBean.setHobbies(picList);
            }

            if (forumDraftsBean.save()) {
                EventBus.getDefault().post(new EventCenter(EventConfig.FORUM_UPDATE_CODE));
                //ToastT.success("已覆盖保存");
                Toaster.show("已覆盖保存");
                pop();
            }
        }
        if (et_long.getText().toString().length() > 3) {
            tv_push.setBackgroundResource(R.drawable.ts_shape_5571fe_big_radius);
            tv_push.setTextColor(Color.parseColor("#ffffff"));
        } else {
            tv_push.setBackgroundResource(R.drawable.shape_f5f5f5_60_radius);
            tv_push.setTextColor(Color.parseColor("#666666"));
        }
    }


    private void adjustLayoutHeight(int keyboardHeight) {
        ll_post.setVisibility(View.GONE);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rootLayout.getLayoutParams();
        params.height = rootLayout.getHeight() - keyboardHeight;
        rootLayout.setLayoutParams(params);
    }

    private void resetLayoutHeight() {
        ll_post.setVisibility(View.VISIBLE);
        if (clickEMO) {
            ll_send.setVisibility(View.GONE);
        } else {
            ll_send.setVisibility(View.VISIBLE);
        }
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rootLayout.getLayoutParams();
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        rootLayout.setLayoutParams(params);
    }

    private int usableHeightPrevious;
    boolean needPop = false;

    @Override
    public boolean onBackPressedSupport() {
        pop();
        return true;
    }

    @Override
    public void pop() {
        if (needPop) {
            super.pop();
        } else {
            boolean close = true;//true直接退出 f显示弹窗
            for (ForumTextPicBean forumPicBean : forumPicBeans) {
                if (forumPicBean.isCanShow()) {
                    close = false;
                }
            }

            if (!et_long.getText().toString().isEmpty()) {
                close = false;
            }
            if (close) {
                super.pop();
            } else {
                showTips2();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    List<String> picList = new ArrayList<>();

    private void pushData() {
        if (timer != null) {
            timer.cancel();
        }
        loading("上传文字中...");
        if (mViewModel != null) {
            Map<String, String> params = new TreeMap<>();
            params.put("gameid", gameid);
            if (!et_title.getText().toString().trim().isEmpty()) {
                params.put("title", et_title.getText().toString());
            }
            params.put("cate_id", cate_id + "");
            Logs.e("PRETTY_LOGS", "htmlString:\n" + htmlString);
            params.put("content", htmlString);
            params.put("feature", "1");//特征，1：图文分开; 2:图文并茂
            StringBuilder pic = new StringBuilder();
            for (ForumTextPicBean forumPicBean : forumPicBeans) {
                if (!forumPicBean.getPicUrl().isEmpty()) {
                    pic.append(forumPicBean.getPicUrl()).append(",");
                }
            }
            params.put("pic", pic.toString());
            mViewModel.categoryPush(params, new OnNetWorkListener<BaseVo>() {
                @Override
                public void onBefore() {
                    timeOutCount = 0;
                }

                @Override
                public void onFailure(String message) {
                    loadingComplete();
                    timeOutCount = 0;
                    //ToastT.error(message);
                    Toaster.show(message);
                }

                @Override
                public void onSuccess(BaseVo data) {
                    timeOutCount = 0;
                    loadingComplete();
                    if (data.isStateOK()) {
                        //ToastT.success("发布成功，审核通过后显示!");
                        Toaster.show("发布成功，审核通过后显示!");
                        if (forumDraftsBean != null) {
                            forumDraftsBean.delete();
                        }
                        EventBus.getDefault().post(new EventCenter(EventConfig.FORUM_UPDATE_CODE));
                        needPop = true;
                        pop();
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

    private Drawable loadImageFromPath(String imagePath) {
        // 获取应用的Resources对象（通常你可以直接使用context.getResources()）
        Resources resources = _mActivity.getResources();

        // 使用BitmapFactory从文件路径加载Bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

        // 检查Bitmap是否成功加载
        if (bitmap != null) {
            // 将Bitmap转换为Drawable
            Drawable drawable = new BitmapDrawable(resources, bitmap);
            return drawable;
        } else {
            // 如果Bitmap加载失败，可以返回一个null或者一个默认的Drawable
            return null; // 或者 return context.getResources().getDrawable(R.drawable.default_image);
        }
    }

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
                        String flag1 = data.getFlag();
                        for (ForumTextPicBean forumPicBean : forumPicBeans) {
                            if (forumPicBean.getSpan().equals(flag1)) {
                                forumPicBean.setWebUrl(data.getPic_url());
                                forumPicBean.setPicUrl(data.getFilename());
                            }
                        }
                        needPushCount = needPushCount - 1;
                        if (needPushCount != 0) {
                            _mActivity.runOnUiThread(() -> {
                                loading("还有" + needPushCount + "张待上传...");
                            });
                        }
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

    private void applyStyleIfNeeded(SpannableStringBuilder spannable, int start, int end, int style) {
        StyleSpan[] existingSpans = spannable.getSpans(start, end, StyleSpan.class);
        for (StyleSpan span : existingSpans) {
            if (span.getStyle() == style) {
                return; // 已经有相同样式，无需再次设置
            }
        }
        spannable.setSpan(new StyleSpan(style), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void applyUnderlineIfNeeded(SpannableStringBuilder spannable, int start, int end) {
        UnderlineSpan[] existingSpans = spannable.getSpans(start, end, UnderlineSpan.class);
        if (existingSpans.length > 0) {
            return; // 已经有下划线样式，无需再次设置
        }
        spannable.setSpan(new UnderlineSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }


    private void upDateEditUI() {
        needWatcher = false;
        et_long.removeTextChangedListener(myWatcher);
        int selectionEnd = et_long.getSelectionEnd();
        String s = et_long.getText().toString();
        Log.i("ForumPostLongFragment", "upDateEditUI s: " + s);
        spannableString = new SpannableStringBuilder(s);
        for (int i = 0; i < forumPicBeans1.size(); i++) {
            if (forumPicBeans1.get(i).canShow) {
                if (forumPicBeans1.get(i).getUrl().contains("emoji_")) {
                    if (spannableString.toString().contains(forumPicBeans1.get(i).getSpan())) {
                        Drawable drawable = _mActivity.getResources().getDrawable(MResource.getResourceId(_mActivity, "mipmap", forumPicBeans1.get(i).getUrl()));
                        if (drawable != null) {
                            ImageSpan imageSpan;
                            float intrinsicWidth = drawable.getIntrinsicWidth();
                            float intrinsicHeight = drawable.getIntrinsicHeight();
                            drawable.setBounds(0, 0, (int) intrinsicWidth, (int) intrinsicHeight);
                            imageSpan = new ImageSpan(drawable, forumPicBeans1.get(i).getUrl(), ImageSpan.ALIGN_BOTTOM);
//
                            int start = spannableString.toString().indexOf(forumPicBeans1.get(i).getSpan());
                            int end = spannableString.toString().indexOf(forumPicBeans1.get(i).getSpan()) + forumPicBeans1.get(i).getSpan().length();
                            spannableString.setSpan(imageSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    } else {
                        forumPicBeans1.get(i).canShow = false;
                    }
                }
            }
        }

        Log.i("ForumPostLongFragment", "meTextStyleList: " + meTextStyleList.size());
        for (int i = 0; i < meTextStyleList.size(); i++) {
            ForumStyleBean styleInfo = meTextStyleList.get(i);
            if (styleInfo.isB() && !styleInfo.isI()) {
                applyStyleIfNeeded(spannableString, i, i + 1, Typeface.BOLD);
            } else if (!styleInfo.isB() && styleInfo.isI()) {
                applyStyleIfNeeded(spannableString, i, i + 1, Typeface.ITALIC);
            } else if (styleInfo.isB() && styleInfo.isI()) {
                applyStyleIfNeeded(spannableString, i, i + 1, Typeface.BOLD_ITALIC);
            }
            if (styleInfo.isU()) {
                applyUnderlineIfNeeded(spannableString, i, i + 1);
            }
        }

        SpannedStyleParser.printSpanInfo(spannableString);
        Log.i("ForumPostLongFragment", "spannableString:3 " + spannableString);
        Log.i("ForumPostLongFragment", "---------1------------------------------------");
        et_long.setText(spannableString);
        SpannedStyleParser.printSpanInfo(et_long.getText());
//        Log.i("ForumPostLongFragment", "spannableString:4 " + Html.toHtml(et_long.getText()));
        et_long.addTextChangedListener(myWatcher);
        et_long.setSelection(selectionEnd);
//        if (lastAddPicEnd != 0) {
//            et_long.setSelection(lastAddPicEnd);
//            lastAddPicEnd = 0;
//        } else {
//
//        }
        Log.i("ForumPostLongFragment", "Html.toHtml: " + Html.toHtml(spannableString).trim());
        needWatcher = true;

        if (et_long.getText().toString().length() > 3) {
            tv_push.setBackgroundResource(R.drawable.ts_shape_5571fe_big_radius);
            tv_push.setTextColor(Color.parseColor("#ffffff"));
        } else {
            tv_push.setBackgroundResource(R.drawable.shape_f5f5f5_60_radius);
            tv_push.setTextColor(Color.parseColor("#666666"));
        }
    }
}
