package com.zqhy.app.core.view.community.task;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.adapter.ViewPagerAdapter;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.community.task.TaskSignInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.community.integral.CommunityIntegralMallFragment;
import com.zqhy.app.core.vm.community.task.TaskViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class TaskSignInFragment extends BaseFragment<TaskViewModel> implements View.OnClickListener {
    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_TASK_SIGN_IN_STATE;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_task_sign_in;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("签到");
        setActionBackBar(R.mipmap.ic_actionbar_back_white);
        setTitleColor(ContextCompat.getColor(_mActivity, R.color.white));
        setTitleBottomLine(View.GONE);
        bindViews();

        initData();
    }

    private ImageView    mIvTaskInfoBg;
    private LinearLayout mLlTaskTag;
    private ImageView    mIvTaskTag;
    private TextView     mTvTaskType;
    private FrameLayout  mLlTaskLayout;

    private ImageView    mIvSignPageLeft;
    private ViewPager    mViewPager;
    private ImageView    mIvSignPageRight;
    private TextView     mTvSignInInfo;
    private TextView     mTvIntegralCount;
    private ImageView    mIvRefreshIntegral;
    private FrameLayout  mFlIntegralMall;
    private TextView     mTvIntegralMall;
    private ImageView    mIvButtonSignIn;
    private TextView     mTvSignInIntegral;
    private LinearLayout mLlSignInAdView;
    private ImageView    mIvSignInAdPic;
    private TextView     mIvSignInAdTitle;
    private TextView     mIvSignInAdSubTitle;

    private void bindViews() {
        mIvTaskInfoBg = findViewById(R.id.iv_task_info_bg);
        mLlTaskTag = findViewById(R.id.ll_task_tag);
        mIvTaskTag = findViewById(R.id.iv_task_tag);
        mTvTaskType = findViewById(R.id.tv_task_type);
        mLlTaskLayout = findViewById(R.id.ll_task_layout);
        mIvSignPageLeft = findViewById(R.id.iv_sign_page_left);
        mViewPager = findViewById(R.id.mViewPager);
        mIvSignPageRight = findViewById(R.id.iv_sign_page_right);
        mTvSignInInfo = findViewById(R.id.tv_sign_in_info);
        mTvIntegralCount = findViewById(R.id.tv_integral_count);
        mIvRefreshIntegral = findViewById(R.id.iv_refresh_integral);
        mFlIntegralMall = findViewById(R.id.fl_integral_mall);
        mTvIntegralMall = findViewById(R.id.tv_integral_mall);
        mIvButtonSignIn = findViewById(R.id.iv_button_sign_in);
        mTvSignInIntegral = findViewById(R.id.tv_sign_in_integral);
        mLlSignInAdView = findViewById(R.id.ll_sign_in_ad_view);
        mIvSignInAdPic = findViewById(R.id.iv_sign_in_ad_pic);
        mIvSignInAdTitle = findViewById(R.id.iv_sign_in_ad_title);
        mIvSignInAdSubTitle = findViewById(R.id.iv_sign_in_ad_sub_title);

        setLayoutViews();
        setLayoutListeners();

        String signInfo = _mActivity.getResources().getString(R.string.string_sign_in_detail, String.valueOf(0), String.valueOf(0));
        mTvSignInInfo.setText(Html.fromHtml(signInfo));
    }

    private void setLayoutViews() {
        GradientDrawable taskTagGd = new GradientDrawable();
        float radius = density * 16;
        taskTagGd.setCornerRadii(new float[]{0, 0, radius, radius, radius, radius, 0, 0});
        taskTagGd.setColor(Color.parseColor("#8A6293FF"));
        mLlTaskTag.setBackground(taskTagGd);


        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(16 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_f5f5f5));
        mFlIntegralMall.setBackground(gd);
    }

    private void setLayoutListeners() {
        mIvSignPageLeft.setOnClickListener(this);
        mIvSignPageRight.setOnClickListener(this);
        mIvButtonSignIn.setOnClickListener(this);

        mFlIntegralMall.setOnClickListener(this);
        mIvRefreshIntegral.setOnClickListener(this);
    }

    private void initData() {
        getSignData();
    }


    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        getSignData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_sign_page_left:
                viewPagerPrevious();
                break;
            case R.id.iv_sign_page_right:
                viewPagerNext();
                break;
            case R.id.iv_button_sign_in:
                //今日签到
                if (checkLogin()) {
                    sign();
                }
                break;
            case R.id.fl_integral_mall:
                //点击跳转--积分商城
                start(new CommunityIntegralMallFragment());
                break;
            case R.id.iv_refresh_integral:
                //刷新积分
                if (checkLogin()) {
                    getSignData();
                }
                break;
            default:
                break;
        }
    }

    List<View> pagerViews;

    private void setSignInViews(List<TaskSignInfoVo.SignListBean> taskInfoBeanList) {
        if (taskInfoBeanList == null) {
            return;
        }
        try {
            if (pagerViews == null) {
                pagerViews = new ArrayList<>();
            }
            pagerViews.clear();
            List<TaskSignInfoVo.SignListBean> pagerSignInList = null;
            for (int i = 0; i < taskInfoBeanList.size(); i++) {
                if (i % 5 == 0) {
                    pagerSignInList = new ArrayList<>();
                }
                pagerSignInList.add(taskInfoBeanList.get(i));
                if (i % 5 == 4) {
                    if (pagerSignInList != null) {
                        pagerViews.add(createSignInPager(pagerSignInList));
                    }
                }
            }

            ViewPagerAdapter mAdapter = new ViewPagerAdapter(pagerViews);
            mViewPager.setAdapter(mAdapter);
            mViewPager.setCurrentItem(pagerViews.size() - 1, true);

            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int currentPage = 0;

    private void viewPagerNext() {
        currentPage++;
        if (currentPage > pagerViews.size() - 1) {
            currentPage = pagerViews.size() - 1;
        }
        mViewPager.setCurrentItem(currentPage, true);
    }

    private void viewPagerPrevious() {
        currentPage--;
        if (currentPage < 0) {
            currentPage = 0;
        }
        mViewPager.setCurrentItem(currentPage, true);
    }

    private View createSignInPager(List<TaskSignInfoVo.SignListBean> signInInfoBeanList) {
        View pager = LayoutInflater.from(_mActivity).inflate(R.layout.layout_sign_in_page, null);
        LinearLayout mLlContainer = pager.findViewById(R.id.ll_container);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pager.setLayoutParams(params);


        if (signInInfoBeanList != null) {
            for (TaskSignInfoVo.SignListBean signInInfoBean : signInInfoBeanList) {
                mLlContainer.addView(createSignInItem(signInInfoBean));
            }
        }
        return pager;
    }

    private View createSignInItem(TaskSignInfoVo.SignListBean signInInfoBean) {
        int itemWidth = (int) (320 * density / 5);

        View itemPager = LayoutInflater.from(_mActivity).inflate(R.layout.layout_sign_in_item, null);

        TextView mTvSignInDate = itemPager.findViewById(R.id.tv_sign_in_date);
        ImageView mIvSignedIn = itemPager.findViewById(R.id.iv_signed_in);
        TextView mTvSignInEarnings = itemPager.findViewById(R.id.tv_sign_in_earnings);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemPager.setLayoutParams(params);


        mTvSignInDate.setText(signInInfoBean.getDay_time());
        mTvSignInDate.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));

        if (signInInfoBean.isToday()) {
            mTvSignInDate.setBackgroundResource(R.mipmap.ic_task_sign_in_date_today);
        } else if (signInInfoBean.isTomorrow()) {
            mTvSignInDate.setBackgroundResource(R.mipmap.ic_task_sign_in_date_tomorrow);
        } else {
            mTvSignInDate.setBackgroundResource(R.mipmap.ic_task_sign_in_date_before);
        }

        StringBuilder sb = new StringBuilder();
        if (signInInfoBean.getIs_today() == 1) {
            //今天
            if (signInInfoBean.getIs_sign() == 1) {
                //已签
                mIvSignedIn.setImageResource(R.mipmap.ic_task_signed_in);
                sb.append("已签");
                int startIndex = sb.length();
                sb.append("+").append(signInInfoBean.getIntegral());
                SpannableString ss = new SpannableString(sb.toString());
                ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_fd4415)),
                        startIndex, sb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mTvSignInEarnings.setText(ss);
            } else {
                //待签
                mIvSignedIn.setImageResource(R.mipmap.ic_task_to_sign_in);
                sb.append("待签");
                SpannableString ss = new SpannableString(sb.toString());
                mTvSignInEarnings.setText(ss);
            }

        } else if (signInInfoBean.getIs_today() == 2) {
            //明天及以后
            //待签
            mIvSignedIn.setImageResource(R.mipmap.ic_task_tomorrow_sign_in);
            sb.append("待签");
            SpannableString ss = new SpannableString(sb.toString());
            mTvSignInEarnings.setText(ss);

        } else if (signInInfoBean.getIs_today() == 0) {
            //今天以前
            if (signInInfoBean.getIs_sign() == 1) {
                //已签
                mIvSignedIn.setImageResource(R.mipmap.ic_task_signed_in);
                sb.append("已签");
                int startIndex = sb.length();
                sb.append("+").append(signInInfoBean.getIntegral());
                SpannableString ss = new SpannableString(sb.toString());
                ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_fd4415)),
                        startIndex, sb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mTvSignInEarnings.setText(ss);
            } else {
                //未签
                mIvSignedIn.setImageResource(R.mipmap.ic_task_unsigned_in);
                sb.append("未签");
                int startIndex = sb.length();
                sb.append("+").append(signInInfoBean.getIntegral());
                SpannableString ss = new SpannableString(sb.toString());
                ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(_mActivity, R.color.color_fd9e15)),
                        startIndex, sb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mTvSignInEarnings.setText(ss);
            }
        }
        return itemPager;
    }


    private TaskSignInfoVo.SignListBean todaySignInInfoBean;

    private void setViewData(TaskSignInfoVo.DataBean data) {
        if (data == null) {
            return;
        }
        for (TaskSignInfoVo.SignListBean signInInfoBean : data.getSign_list()) {
            if (signInInfoBean.isToday()) {
                todaySignInInfoBean = signInInfoBean;
                break;
            }
        }

        setSignInViews(data.getSign_list());

        String signInfo = _mActivity.getResources().getString(R.string.string_sign_in_detail, String.valueOf(data.getContinued_days()), String.valueOf(data.getSigned_days()));
        mTvSignInInfo.setText(Html.fromHtml(signInfo));

        mTvIntegralCount.setText(String.valueOf(data.getIntegral()));
        mTvSignInIntegral.setVisibility(View.VISIBLE);
        if (todaySignInInfoBean != null) {
            if (todaySignInInfoBean.getIs_sign() != 1) {
                //未签，待签
                mIvButtonSignIn.setImageResource(R.mipmap.ic_task_button_sign_in);
                mIvButtonSignIn.setEnabled(true);
                mTvSignInIntegral.setText("今日+" + data.getSign_integral());
            } else {
                //已签
                mIvButtonSignIn.setImageResource(R.mipmap.ic_task_button_signed_in);
                mIvButtonSignIn.setEnabled(false);
                mTvSignInIntegral.setText("明日+" + data.getSign_integral());
            }
        }

        if (data.getSign_illustration() != null) {
            mLlSignInAdView.setVisibility(View.VISIBLE);
            setSignAd(data.getSign_illustration());
        } else {
            mLlSignInAdView.setVisibility(View.GONE);
        }
    }

    private void setSignAd(TaskSignInfoVo.TaskAppJumpInfoBean signAd) {
        if (signAd != null) {
            GlideUtils.loadNormalImage(_mActivity, signAd.getAd_pic(), mIvSignInAdPic, R.mipmap.img_placeholder_v_1);
            mIvSignInAdTitle.setText(signAd.getTitle2());
            mIvSignInAdSubTitle.setText(signAd.getTitle());
            mLlSignInAdView.setOnClickListener(view -> {
                appJumpAction(signAd);
            });
        }
    }

    private void getSignData() {
        if (mViewModel != null) {
            mViewModel.getSignData(new OnBaseCallback<TaskSignInfoVo>() {


                @Override
                public void onBefore() {
                    super.onBefore();
                    loading();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    loadingComplete();
                }

                @Override
                public void onSuccess(TaskSignInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            setViewData(data.getData());
                        } else {
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void sign() {
        if (mViewModel != null) {
            mViewModel.userSign(new OnBaseCallback() {
                @Override
                public void onBefore() {
                    super.onBefore();
                    loading();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            Toaster.show( "签到成功");
                            getSignData();
                            mViewModel.refreshUserData();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }
}
