package com.zqhy.app.core.view.main;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chaoji.im.glide.GlideApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.AppMenuBeanVo;
import com.zqhy.app.core.data.model.game.new0809.AppMenuVo;
import com.zqhy.app.core.data.model.splash.AppStyleConfigs;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.browser.BrowserActivity1;
import com.zqhy.app.core.view.browser.BrowserFragment;
import com.zqhy.app.core.view.main.new0809.MainPageFuLiFuFragment;
import com.zqhy.app.core.view.main.new0809.MainPageJingXuanFragment;
import com.zqhy.app.core.view.main.new0809.MainPageTuiJianFragment;
import com.zqhy.app.core.view.main.new0809.MainPageXingYouTabFragment;
import com.zqhy.app.core.view.main.new0809.MainPageZKFragment;
import com.zqhy.app.core.view.main.new0809.NewGameRankingActivityFragment;
import com.zqhy.app.core.view.main.new0809.NewMainPageGameCollectionFragment;
import com.zqhy.app.core.vm.main.MainViewModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.cache.ACache;
import com.zqhy.app.widget.MyLinearLayoutView;
import com.zqhy.mod.fragment.FragmentXiaoGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/6
 */

public class BrowseModeMainFragment extends BaseFragment<MainViewModel> {
    @Override
    protected String getUmengPageName() {
        return "首页";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main_browse_mode;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        showSuccess();
        bindView();
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_USER_MINE_STATE;
    }

    private ViewPager mViewPager;
    private ScrollIndicatorView mFragmentTabmainIndicator;
    public ArrayList<Fragment> fragmentList;
    private void bindView() {
        mViewPager = findViewById(R.id.view_pager);
        mFragmentTabmainIndicator = findViewById(R.id.fragment_tabmain_indicator);

        setViewPagerContent(mViewPager, mFragmentTabmainIndicator);

        int dynamicPagerHeight = 50;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int topMargin = (int) (dynamicPagerHeight * density) + ScreenUtil.getStatusBarHeight(_mActivity);
        params.setMargins(0, topMargin, 0, 0);

        mViewPager.setLayoutParams(params);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) findViewById(R.id.ll_top).getLayoutParams();
        layoutParams.height = ScreenUtil.getStatusBarHeight(_mActivity) + ScreenUtil.dp2px(_mActivity, 50);
        findViewById(R.id.ll_top).setLayoutParams(layoutParams);
        findViewById(R.id.ll_top).setOnClickListener(v -> {
            showRankingDialog();
        });
        findViewById(R.id.tv_quit).setOnClickListener(v -> {
            showRankingDialog();
        });
        ((MyLinearLayoutView)findViewById(R.id.ll_quit)).setMoreAction(() -> showRankingDialog());
    }

    private TabAdapter mTabAdapter;
    public int tabCurrentItem = 0;
    private int selectColor, unSelectColor;
    private float unSelectSize = 16, selectSize = 18;
    private AppMenuBeanVo appMenuBeanVo;
    private void setViewPagerContent(final ViewPager viewPager, final ScrollIndicatorView indicator) {
        Resources res = getResources();
        try {
            String json = ACache.get(_mActivity).getAsString(AppStyleConfigs.APP_MENU_JSON_KEY);
            Gson gson = new Gson();
            appMenuBeanVo = gson.fromJson(json, new TypeToken<AppMenuBeanVo>() {
            }.getType());

            if (appMenuBeanVo == null) {
                return;
            }

            if (fragmentList == null) {
                fragmentList = new ArrayList<>();
            }
            fragmentList.clear();
            fragmentList.addAll(createFragments(appMenuBeanVo));

            selectColor = res.getColor(R.color.color_333333);
            unSelectColor = res.getColor(R.color.color_777777);

            viewPager.setOffscreenPageLimit(fragmentList.size());

            IndicatorViewPager indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
            mTabAdapter = new TabAdapter(getChildFragmentManager(), fragmentList, createTitleLabels(appMenuBeanVo));
            indicator.setOnTransitionListener(new OnTransitionTextListener() {
                @Override
                public TextView getTextView(View tabItemView, int position) {
                    return tabItemView.findViewById(R.id.tv_indicator);
                }
            }.setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
            indicatorViewPager.setOnIndicatorPageChangeListener((preItem, currentItem) -> {
                tabCurrentItem = currentItem;
                selectTabPager(mTabAdapter, currentItem);
            });
            indicatorViewPager.setAdapter(mTabAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * mainTab样式切换
     *
     * @param mTabAdapter
     * @param currentItem
     */
    private void selectTabPager(TabAdapter mTabAdapter, int currentItem) {
        if (mTabAdapter.getTabViewMap() != null) {
            for (Integer key : mTabAdapter.getTabViewMap().keySet()) {
                View itemView = mTabAdapter.getTabViewMap().get(key);
                TextView mTvIndicator = itemView.findViewById(R.id.tv_indicator);
                mTvIndicator.getPaint().setFakeBoldText(key == currentItem);
                RelativeLayout.LayoutParams mTvParams = (RelativeLayout.LayoutParams) mTvIndicator.getLayoutParams();
                if (mTvParams == null) {
                    mTvParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                }
                mTvParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                mTvIndicator.setLayoutParams(mTvParams);

                ImageView mIvIndicator = itemView.findViewById(R.id.iv_indicator);
                if (!TextUtils.isEmpty(appMenuBeanVo.home_menu.get(key).icon) && !TextUtils.isEmpty(appMenuBeanVo.home_menu.get(key).icon_active)){
                    mTvIndicator.setVisibility(View.GONE);
                    mIvIndicator.setVisibility(View.VISIBLE);
                    if (key == currentItem){
                        if (!TextUtils.isEmpty(appMenuBeanVo.home_menu.get(key).icon_active)) {
                            GlideApp.with(_mActivity).asBitmap()
                                    .load(appMenuBeanVo.home_menu.get(key).icon_active)
                                    .placeholder(R.mipmap.img_placeholder_v_2)
                                    .into(new CustomTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            if (resource != null && mIvIndicator.getLayoutParams() != null) {
                                                mIvIndicator.setImageBitmap(resource);
                                                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mIvIndicator.getLayoutParams();
                                                int height = ScreenUtil.dp2px(_mActivity, 18);
                                                params.width = height * resource.getWidth() / resource.getHeight();
                                                params.height = height;
                                                mIvIndicator.setLayoutParams(params);

                                                ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                                                layoutParams.width = params.width + ScreenUtil.dp2px(_mActivity, 20);
                                                itemView.setLayoutParams(layoutParams);
                                            }
                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable drawable) {

                                        }
                                    });
                        }
                    }else {
                        if (!TextUtils.isEmpty(appMenuBeanVo.home_menu.get(key).icon)){
                            GlideApp.with(_mActivity).asBitmap()
                                    .load(appMenuBeanVo.home_menu.get(key).icon)
                                    .placeholder(R.mipmap.img_placeholder_v_2)
                                    .into(new CustomTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            if (resource != null && mIvIndicator.getLayoutParams() != null) {
                                                mIvIndicator.setImageBitmap(resource);
                                                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mIvIndicator.getLayoutParams();
                                                int height = ScreenUtil.dp2px(_mActivity, 16);
                                                params.width = height * resource.getWidth() / resource.getHeight();
                                                params.height = height;
                                                mIvIndicator.setLayoutParams(params);

                                                ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                                                layoutParams.width = params.width + ScreenUtil.dp2px(_mActivity, 20);
                                                itemView.setLayoutParams(layoutParams);
                                            }
                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable drawable) {

                                        }
                                    });
                        }
                    }
                }else {
                    mTvIndicator.setVisibility(View.VISIBLE);
                    mIvIndicator.setVisibility(View.GONE);
                }
            }
        }
    }

    class TabAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        private List<Fragment> mFragmentList;
        private List<String>       mTitles;

        private HashMap<Integer, View> mTabViewMap;


        public TabAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList, List<String> mTitles) {
            super(fragmentManager);
            mFragmentList = fragmentList;
            this.mTitles = mTitles;
            mTabViewMap = new HashMap<>();
        }

        @Override
        public int getCount() {
            return mTitles == null ? 0 : mTitles.size();
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_tab_main, container, false);
            }
            TextView mTvIndicator = convertView.findViewById(R.id.tv_indicator);
            ImageView mIvIndicator = convertView.findViewById(R.id.iv_indicator);
            mTvIndicator.setText(mTitles.get(position));
            mTvIndicator.setMinWidth((int) (32 * density));
            mTabViewMap.put(position, convertView);
            if (!TextUtils.isEmpty(appMenuBeanVo.home_menu.get(position).icon) && !TextUtils.isEmpty(appMenuBeanVo.home_menu.get(position).icon_active)) {
                mTvIndicator.setVisibility(View.GONE);
                mIvIndicator.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(appMenuBeanVo.home_menu.get(position).icon)){
                    GlideApp.with(_mActivity).asBitmap()
                            .load(appMenuBeanVo.home_menu.get(position).icon)
                            .placeholder(R.mipmap.img_placeholder_v_2)
                            .into(new CustomTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    if (resource != null && mIvIndicator.getLayoutParams() != null) {
                                        mIvIndicator.setImageBitmap(resource);
                                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mIvIndicator.getLayoutParams();
                                        int height = ScreenUtil.dp2px(_mActivity, 16);
                                        params.width = height * resource.getWidth() / resource.getHeight();
                                        params.height = height;
                                        mIvIndicator.setLayoutParams(params);
                                    }
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable drawable) {

                                }
                            });
                }
            }else {
                mTvIndicator.setVisibility(View.VISIBLE);
                mIvIndicator.setVisibility(View.GONE);
            }
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            return mFragmentList.get(position);
        }

        public HashMap<Integer, View> getTabViewMap() {
            return mTabViewMap;
        }
    }

    private List<Fragment> createFragments(AppMenuBeanVo appMenuBeanVo) {
        List<AppMenuVo> home_menu = appMenuBeanVo.home_menu;
        List<Fragment> fragmentList = new ArrayList<>();

        if (home_menu != null) {
            for (AppMenuVo appMenuVo : home_menu) {
                switch (appMenuVo.api) {
                    case "xiaoyouxi":
                        fragmentList.add(FragmentXiaoGame.Companion.newInstance());
                        break;
                    case "txy_home_page":
                        //fragmentList.add(new MainPageXingYouFragment());
                        fragmentList.add(new MainPageXingYouTabFragment());
                        break;
                    case "flf_home_page":
                        fragmentList.add(new MainPageFuLiFuFragment());
                        break;
                    case "hj_home_page":
                        fragmentList.add(NewMainPageGameCollectionFragment.newInstance(appMenuVo.params.container_id));
                        break;
                    case "jx_home_page":
                        fragmentList.add(new MainPageJingXuanFragment());
                        break;
                    case "web_home_page":
                        //网页
                        if (appMenuVo.params != null) {
                            fragmentList.add(BrowserFragment.newInstance(appMenuVo.params.url,false));
                        }
                        break;
                    case "tj_home_page":
                        fragmentList.add(new MainPageTuiJianFragment());
                        break;
                    case "cw_home_page":
                        fragmentList.add(new MainPageZKFragment());
                        break;
                    case "bang_page":
                        fragmentList.add(NewGameRankingActivityFragment.newInstance());
                        break;
                    default:
                        break;
                }
            }
        }
        return fragmentList;
    }

    private List<String> createTitleLabels(AppMenuBeanVo appMenuBeanVo) {
        List<AppMenuVo> home_menu = appMenuBeanVo.home_menu;
        List<String> labels = new ArrayList<>();
        if (home_menu != null) {
            for (int i = 0; i < home_menu.size(); i++) {
                if (checkTitleLabels(home_menu.get(i).api)){
                    labels.add(home_menu.get(i).name);
                }
            }
            return labels;
        }
        return labels;
    }

    private boolean checkTitleLabels(String api){
        boolean hasLabels = false;
        switch (api) {
            case "xiaoyouxi":
            case "txy_home_page":
            case "flf_home_page":
            case "hj_home_page":
            case "jx_home_page":
            case "web_home_page":
            case "tj_home_page":
            case "cw_home_page":
            case "bang_page":
                hasLabels = true;
                break;
            default:
                hasLabels = false;
                break;
        }

        return hasLabels;
    }

    private CustomDialog rankDialog;
    private void showRankingDialog(){
        if (rankDialog == null) rankDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_ts_private_exit_visitors, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        SpannableString ss = new SpannableString("亲，浏览模式下部分功能将受限，为了您能够体验更好的服务，我们建议您点击【同意协议并退出浏览模式】确认《隐私协议》，并登录使用我们的产品。若您不同意协议，可以选择继续使用浏览模式。浏览模式下，仅能为您提供部分内容的浏览功能。");
        ss.setSpan(new StyleSpan(Typeface.BOLD), 35, 47, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#F51A07"));
            }

            @Override
            public void onClick(@NonNull View widget) {
                //隐私协议
                Intent intent = new Intent(_mActivity, BrowserActivity1.class);
                intent.putExtra("url", AppConfig.APP_AUDIT_PRIVACY_PROTOCOL);
                startActivity(intent);
            }
        }, 50, 56, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ((TextView) rankDialog.findViewById(R.id.tv_integral_balance)).setText(ss);
        ((TextView) rankDialog.findViewById(R.id.tv_integral_balance)).setMovementMethod(LinkMovementMethod.getInstance());

        rankDialog.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            if (rankDialog != null && rankDialog.isShowing()) rankDialog.dismiss();
            _mActivity.finish();
        });
        rankDialog.findViewById(R.id.btn_cancel).setOnClickListener(v -> {
            if (rankDialog != null && rankDialog.isShowing()) rankDialog.dismiss();
        });
        if (rankDialog != null && !rankDialog.isShowing()) rankDialog.show();
    }
}
