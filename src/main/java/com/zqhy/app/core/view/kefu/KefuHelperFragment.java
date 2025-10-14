package com.zqhy.app.core.view.kefu;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.kefu.KefuQuestionInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.vm.kefu.KefuViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class KefuHelperFragment extends BaseFragment<KefuViewModel> implements View.OnClickListener {

    private static List<KefuCacheBean> kefuCacheBeanList = new ArrayList<>();

    private final int delayMillis = 250;

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_kefu_helper;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("客服中心");
        bindViews();
        initData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    /**
     * 0,休息中，1，空闲，2繁忙
     */
    private int status;

    private int level;


    private LinearLayout mLlKefuContent;
    private ScrollView mScrollView;
    private TextView mTvKefuTabTxt;
    private HorizontalScrollView mHsvKefuTab;
    private LinearLayout mLlKefuTab;
    private TextView mTvFeedback;

    private void bindViews() {
        mLlKefuContent = findViewById(R.id.ll_kefu_content);
        mHsvKefuTab = findViewById(R.id.hsv_kefu_tab);
        mLlKefuTab = findViewById(R.id.ll_kefu_tab);
        mScrollView = findViewById(R.id.scrollView);
        mTvKefuTabTxt = findViewById(R.id.tv_kefu_tab_txt);


        mTvFeedback = findViewById(R.id.tv_feedback);
        mTvFeedback.setOnClickListener(this);


        GradientDrawable gd = new GradientDrawable();
        mTvFeedback.setPadding((int) (8 * density), 0, (int) (8 * density), 0);
        gd.setCornerRadius(24 * density);
        gd.setStroke((int) (1 * density), ContextCompat.getColor(_mActivity, R.color.color_999999));
        mTvFeedback.setBackground(gd);
        mTvFeedback.setGravity(Gravity.CENTER);
        mTvFeedback.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_999999));

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (24 * density));
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        params.setMargins(0, 0, (int) (16 * density), 0);
        mTvFeedback.setLayoutParams(params);
    }


    private void setKefuCache() {
        if (kefuCacheBeanList != null && !kefuCacheBeanList.isEmpty()) {
            //赋临时值
            List<KefuCacheBean> tempKefuCacheBeanList = new ArrayList<>();
            tempKefuCacheBeanList.addAll(kefuCacheBeanList);
            //清空缓存列表
            kefuCacheBeanList.clear();
            for (int i = 0; i < tempKefuCacheBeanList.size(); i++) {
                KefuCacheBean kefuCacheBean = tempKefuCacheBeanList.get(i);
                switch (kefuCacheBean.getType()) {
                    case 0:
                        addHeaderTitleView();
                        break;
                    case 1:
                        addKefuTabViewWithNoDelay(kefuCacheBean.getKefuTitleList());
                        break;
                    case 2:
                        addKefuTitleDetailWithNoDelay(kefuCacheBean.getTitle(), kefuCacheBean.getContent());
                        break;
                    case 3:
                        addKefuTitleView(kefuCacheBean.getTitle());
                        break;
                    default:
                        break;
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_feedback:
                if (checkLogin()) {
                    start(new FeedBackFragment());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (askUserIconViewList != null) {
            askUserIconViewList.clear();
        }
    }


    private void pointingToBottom() {
        mScrollView.post(() -> mScrollView.fullScroll(View.FOCUS_DOWN));
    }

    /**
     * 清除Layout
     */
    private void clearAllView() {
        mLlKefuContent.removeAllViews();
        addHeaderTitleView();
        kefuCacheBeanList.clear();
    }

    /**
     * 添加头部标题 （左边）
     * <p>
     * type = 0
     */
    private void addHeaderTitleView() {
        boolean isVip = level == 2;
        View headerTitleView;
        if (!isVip) {
            headerTitleView = createItemLayout1("hi，你好，请在底部选择你要咨询的问题！", null);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("尊敬的VIP用户，请在底部选择你要咨询的问题；或者直接联系");
            int startIndex = sb.length();
            sb.append("VIP客服");
            int endIndex = sb.length();
            sb.append("!");
            SpannableString ss = new SpannableString(sb.toString());
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_0595e8)),
                    startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            headerTitleView = createItemLayout1(ss, v -> goKefuCenter());
        }
        if (headerTitleView != null) {
            mLlKefuContent.addView(headerTitleView);
        }
        pointingToBottom();
    }

    /**
     * 添加客服标题列表 （左边）
     * type = 1
     *
     * @param kefuTitleList
     */
    private void addKefuTabView(List<KefuQuestionInfoVo.ItemBean> kefuTitleList) {
        mLlKefuContent.postDelayed(() -> {
            View tipsView = createItemLayout1("您是想问以下问题吗？可以直接点击查看哦！", null);
            if (tipsView != null) {
                mLlKefuContent.addView(tipsView);
            }
            View kefuListView = createKefuTitleListView(kefuTitleList);
            if (kefuListView != null) {
                mLlKefuContent.addView(kefuListView);
                kefuCacheBeanList.add(new KefuCacheBean(kefuTitleList));
            }
            pointingToBottom();
        }, delayMillis);
    }

    /**
     * 添加客服标题列表 （左边）
     * type = 1
     *
     * @param kefuTitleList
     */
    private void addKefuTabViewWithNoDelay(List<KefuQuestionInfoVo.ItemBean> kefuTitleList) {
        View tipsView = createItemLayout1("您是想问以下问题吗？可以直接点击查看哦！", null);
        if (tipsView != null) {
            mLlKefuContent.addView(tipsView);
        }
        View kefuListView = createKefuTitleListView(kefuTitleList);
        if (kefuListView != null) {
            mLlKefuContent.addView(kefuListView);
            kefuCacheBeanList.add(new KefuCacheBean(kefuTitleList));
        }
        pointingToBottom();
    }

    /**
     * 添加客服详情信息（左边）
     * type = 2
     *
     * @param title
     * @param description
     */
    private void addKefuTitleDetail(CharSequence title, CharSequence description) {
        mLlKefuContent.postDelayed(() -> {
            View kefuDetailView = createItemLayout2(title, description);
            if (kefuDetailView != null) {
                mLlKefuContent.addView(kefuDetailView);
            }
            pointingToBottom();
            kefuCacheBeanList.add(new KefuCacheBean(title, description));
        }, delayMillis);
    }

    /**
     * 添加客服详情信息（左边）
     * type = 2
     *
     * @param title
     * @param description
     */
    private void addKefuTitleDetailWithNoDelay(CharSequence title, CharSequence description) {
        View kefuDetailView = createItemLayout2(title, description);
        if (kefuDetailView != null) {
            mLlKefuContent.addView(kefuDetailView);
        }
        pointingToBottom();
        kefuCacheBeanList.add(new KefuCacheBean(title, description));
    }

    /**
     * 添加客服标题（提问，右边）
     * type = 3
     *
     * @param kefuTitle
     */
    private void addKefuTitleView(CharSequence kefuTitle) {
        View kefuTitleView = createItemLayout3(kefuTitle);
        if (kefuTitleView != null) {
            mLlKefuContent.addView(kefuTitleView);
        }
        pointingToBottom();
        kefuCacheBeanList.add(new KefuCacheBean(kefuTitle));
    }

    /**
     * 创建客服标题列表View
     *
     * @param kefuTitleList
     * @return
     */
    private View createKefuTitleListView(List<KefuQuestionInfoVo.ItemBean> kefuTitleList) {
        LinearLayout mLLRootView = new LinearLayout(_mActivity);
        mLLRootView.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = (int) (16 * density);
        params.rightMargin = (int) (16 * density);
        params.topMargin = (int) (8 * density);
        params.bottomMargin = (int) (8 * density);
        mLLRootView.setLayoutParams(params);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(6 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.white));
        gd.setStroke(1, Color.parseColor("#DADADA"));
        mLLRootView.setBackground(gd);

        if (kefuTitleList != null && kefuTitleList.size() > 0) {
            mLLRootView.setVisibility(View.VISIBLE);

            int index = 0;
            for (KefuQuestionInfoVo.ItemBean itemsBean : kefuTitleList) {
                View titleView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_kefu_helper_item_kefu_title, null);

                LinearLayout mLlKefuTitle = titleView.findViewById(R.id.ll_kefu_title);
                TextView mTvKefuTitle = titleView.findViewById(R.id.tv_kefu_title);
                View mViewLine = titleView.findViewById(R.id.view_line);

                String title = itemsBean.getQuestion();
                String description = itemsBean.getAnswer();

                mTvKefuTitle.setText(title);
                mLlKefuTitle.setOnClickListener(v -> {
                    addKefuTitleView(title);
                    addKefuTitleDetail(title, description);
                });
                if (index == kefuTitleList.size() - 1) {
                    mViewLine.setVisibility(View.GONE);
                } else {
                    mViewLine.setVisibility(View.VISIBLE);
                }

                LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                titleView.setLayoutParams(titleParams);

                mLLRootView.addView(titleView);
                index++;
            }
        } else {
            mLLRootView.setVisibility(View.GONE);
        }

        return mLLRootView;
    }

    /**
     * 创建Layout1布局
     *
     * @param content         内容
     * @param onClickListener
     * @return
     */
    private View createItemLayout1(CharSequence content, View.OnClickListener onClickListener) {
        View view = LayoutInflater.from(_mActivity).inflate(R.layout.layout_kefu_helper_item_1, null);
        TextView mText = view.findViewById(R.id.text);
        mText.setText(content);
        mText.setOnClickListener(onClickListener);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return view;
    }

    /**
     * 创建Layout2布局
     *
     * @param title   标题
     * @param content 内容
     * @return
     */
    private View createItemLayout2(CharSequence title, CharSequence content) {
        View view = LayoutInflater.from(_mActivity).inflate(R.layout.layout_kefu_helper_item_2, null);
        TextView mTvTitle = view.findViewById(R.id.tv_title);
        TextView mText = view.findViewById(R.id.tv_text);
        TextView mTvKefuStatus = view.findViewById(R.id.tv_kefu_status);

        mTvTitle.setText(title);
        mText.setText(content);

        StringBuilder sb = new StringBuilder();
        sb.append("人工客服");
        sb.append("(状态：");
        int startIndex = sb.toString().length();
        if (status == 0) {
            sb.append("休息中");
        } else if (status == 1) {
            sb.append("空闲");
        } else if (status == 2) {
            sb.append("繁忙");
        }
        int endIndex = sb.toString().length();
        sb.append(")");

        SpannableString ss = new SpannableString(sb.toString());
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_0595e8)),
                0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_ff180f)),
                startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvKefuStatus.setText(ss);
        mTvKefuStatus.setOnClickListener(v -> goKefuCenter());
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return view;
    }


    private List<ImageView> askUserIconViewList;

    /**
     * 创建Layout3布局
     *
     * @param content 内容
     * @return
     */
    private View createItemLayout3(CharSequence content) {
        View view = LayoutInflater.from(_mActivity).inflate(R.layout.layout_kefu_helper_item_3, null);

        ImageView mIvKefuIcon = view.findViewById(R.id.iv_kefu_icon);
        TextView mText = view.findViewById(R.id.text);
        if (askUserIconViewList == null) {
            askUserIconViewList = new ArrayList<>();
        }
        askUserIconViewList.add(mIvKefuIcon);

        mText.setText(content);
        if (UserInfoModel.getInstance().isLogined()) {
            String userIcon = UserInfoModel.getInstance().getUserInfo().getUser_icon();
            GlideUtils.loadRoundImage(_mActivity, userIcon, mIvKefuIcon, R.mipmap.ic_user_login);
        } else {
            mIvKefuIcon.setImageResource(R.mipmap.ic_kefu_help_user);
        }

        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return view;
    }


    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        replaceUserIcon();
    }

    private void replaceUserIcon() {
        if (askUserIconViewList != null && !askUserIconViewList.isEmpty()) {
            for (ImageView mIvKefuIcon : askUserIconViewList) {
                if (UserInfoModel.getInstance().isLogined()) {
                    String userIcon = UserInfoModel.getInstance().getUserInfo().getUser_icon();
                    GlideUtils.loadRoundImage(_mActivity, userIcon, mIvKefuIcon, R.mipmap.ic_user_login);
                } else {
                    mIvKefuIcon.setImageResource(R.mipmap.ic_kefu_help_user);
                }
            }
        }
    }

    private List<TextView> kefuTabViewList;

    private void setKefuTab() {
        addHeaderTitleView();
        if (kefuInfoVoList != null && kefuInfoVoList.size() > 0) {
            if (kefuTabViewList == null) {
                kefuTabViewList = new ArrayList<>();
            }
            kefuTabViewList.clear();
            mLlKefuTab.removeAllViews();

            int index = 10000;
            for (KefuQuestionInfoVo.ListItemBean kefuInfoVo : kefuInfoVoList) {
                View kefuTabView = createKefuTab(index, kefuInfoVo);
                if (kefuTabView != null) {
                    mLlKefuTab.addView(kefuTabView);
                }
                index++;
            }
        }
    }

    private View createKefuTab(int index, KefuQuestionInfoVo.ListItemBean kefuInfoVo) {
        int height = (int) (24 * density);
        FrameLayout mFrameLayout = new FrameLayout(_mActivity);
        HorizontalScrollView.LayoutParams params = new HorizontalScrollView.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, height);
        mFrameLayout.setLayoutParams(params);

        TextView mText = new TextView(_mActivity);

        int width = (ScreenUtil.getScreenWidth(_mActivity)
                - mTvKefuTabTxt.getMeasuredWidth()
                - mTvKefuTabTxt.getPaddingLeft()
                - mTvKefuTabTxt.getPaddingRight()
                - (4 * 2 * (int) (5 * density))) / 4;

        FrameLayout.LayoutParams mTextParams = new FrameLayout.LayoutParams(width, height);
        mTextParams.gravity = Gravity.CENTER;
        mTextParams.leftMargin = (int) (5 * density);
        mTextParams.rightMargin = (int) (5 * density);
        mText.setLayoutParams(mTextParams);

        mText.setId(index);
        mText.setText(kefuInfoVo.getName());
        mText.setTextSize(13);
        mText.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_201e1e));
        mText.setGravity(Gravity.CENTER);

        kefuTabViewList.add(mText);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(24 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.white));
        gd.setStroke(1, ContextCompat.getColor(_mActivity, R.color.color_dadada));
        mText.setBackground(gd);

        mText.setOnClickListener(v -> {
            selectKefuTabView(v.getId());
//            clearAllView();
            addKefuTitleView(kefuInfoVo.getName());
            addKefuTabView(kefuInfoVo.getList());

        });
        mFrameLayout.addView(mText);
        return mFrameLayout;
    }

    private void selectKefuTabView(int selectedId) {
        if (kefuTabViewList != null && kefuTabViewList.size() > 0) {
            for (TextView item : kefuTabViewList) {
                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(24 * density);
                gd.setColor(ContextCompat.getColor(_mActivity, R.color.white));
                if (item.getId() == selectedId) {
                    gd.setStroke(1, ContextCompat.getColor(_mActivity, R.color.color_fbb028));
                    item.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_fbb028));
                } else {
                    gd.setStroke(1, ContextCompat.getColor(_mActivity, R.color.color_dadada));
                    item.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_201e1e));
                }
                item.setBackground(gd);
            }
        }
    }

    List<KefuQuestionInfoVo.ListItemBean> kefuInfoVoList;

    private void initData() {
        if (mViewModel != null) {
            mViewModel.getKefuQuestionInfo(new OnBaseCallback<KefuQuestionInfoVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }

                @Override
                public void onSuccess(KefuQuestionInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                if (data.getData().getList() != null) {
                                    kefuInfoVoList = data.getData().getList();
                                }
                                status = data.getData().getStatus();
                                if (data.getData().getVipinfo() != null) {
                                    level = data.getData().getVipinfo().getLevel();
                                }
                                setKefuTab();
                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                    setKefuCache();
                }
            });
        }
    }

    public static class KefuCacheBean {
        private int id;
        /**
         * 0 -- 欢迎语 （左边）
         * 1 -- 简单文字 （左边） +客服标题列表 （居中）
         * 2 -- 客服标题 + 详情 （左边）
         * 3 -- 客服Tab/标题 (右边)
         */
        private int type;
        private CharSequence title;
        private CharSequence content;
        private int kefuStatus;
        private List<KefuQuestionInfoVo.ItemBean> kefuTitleList;

        public KefuCacheBean(List<KefuQuestionInfoVo.ItemBean> kefuTitleList) {
            this.type = 1;
            this.kefuTitleList = kefuTitleList;
        }

        public KefuCacheBean(CharSequence title, CharSequence content) {
            this.type = 2;
            this.title = title;
            this.content = content;
            this.kefuStatus = kefuStatus;
        }

        public KefuCacheBean(CharSequence title) {
            this.type = 3;
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public CharSequence getTitle() {
            return title;
        }

        public CharSequence getContent() {
            return content;
        }

        public List<KefuQuestionInfoVo.ItemBean> getKefuTitleList() {
            return kefuTitleList;
        }

    }
}
