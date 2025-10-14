package com.zqhy.app.core.view.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bytedance.hume.readapk.HumeSDK;
import com.chaoji.im.glide.GlideApp;
import com.chaoji.im.sdk.ImSDK;
import com.chaoji.other.blankj.utilcode.util.Logs;
import com.chaoji.other.hjq.toast.Toaster;
import com.chaoji.other.immersionbar.ImmersionBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kwai.monitor.payload.TurboHelper;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.zqhy.app.App;
import com.zqhy.app.Setting;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.SpConstants;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.AppMenuBeanVo;
import com.zqhy.app.core.data.model.game.GameDataVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.AppMenuVo;
import com.zqhy.app.core.data.model.game.new0809.XinRenPopDataVo;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;
import com.zqhy.app.core.data.model.mainpage.FloatItemInfoVo;
import com.zqhy.app.core.data.model.message.MessageInfoVo;
import com.zqhy.app.core.data.model.message.MessageListVo;
import com.zqhy.app.core.data.model.splash.AppStyleConfigs;
import com.zqhy.app.core.data.model.splash.SplashVo;
import com.zqhy.app.core.data.model.user.newvip.AppFloatIconVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.inner.OnMainPageChangeListener;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.browser.BrowserFragment;
import com.zqhy.app.core.view.community.task.TaskCenterFragment;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.core.view.game.GameDownloadManagerFragment;
import com.zqhy.app.core.view.game.GameSearchFragment;
import com.zqhy.app.core.view.main.new0809.MainPageFuLiFuFragment;
import com.zqhy.app.core.view.main.new0809.MainPageJingXuanFragment;
import com.zqhy.app.core.view.main.new0809.MainPageTuiJianFragment;
import com.zqhy.app.core.view.main.new0809.MainPageXingYouFragment;
import com.zqhy.app.core.view.main.new0809.MainPageZKFragment;
import com.zqhy.app.core.view.main.new0809.NewGameRankingActivityFragment;
import com.zqhy.app.core.view.main.new0809.NewMainPageGameCollectionFragment;
import com.zqhy.app.core.view.strategy.DiscountStrategyFragment;
import com.zqhy.app.core.view.user.CertificationFragment;
import com.zqhy.app.core.vm.main.MainViewModel;
import com.zqhy.app.db.table.message.MessageDbInstance;
import com.zqhy.app.db.table.message.MessageVo;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.statistics.JiuYaoStatisticsApi;
import com.zqhy.app.network.utils.AppUtils;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.subpackage.ChannelReaderUtil;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.LifeUtil;
import com.zqhy.app.utils.cache.ACache;
import com.zqhy.app.utils.sdcard.SdCardManager;
import com.zqhy.app.utils.sp.SPUtils;
import com.zqhy.mod.fragment.FragmentXiaoGame;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/6
 */

public class MainFragment extends BaseFragment<MainViewModel> {
    private MainPageJingXuanFragment mJingXuanFragment;

    public static final String SP_MESSAGE = "SP_MESSAGE";
    public static final String TAG_DYNAMIC_GAME_MESSAGE_LOG_TIME = "TAG_DYNAMIC_GAME_MESSAGE_LOG_TIME";

    @Override
    protected String getUmengPageName() {
        return "首页";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main;
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
        setAppStyle();
        post(() -> {
            //checkShowSingleGameDialog();
        });
        //getKefuMessageData();
        appFloatIcon();
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_USER_MINE_STATE;
    }


    @Override
    public void onResume() {
        super.onResume();
        hideFloatLayout();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            hideFloatLayout();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FragmentHolderActivity.COMMON_REQUEST_CODE) {
            if (Setting.HAS_SPLASH_JUMP) {
                Setting.HAS_SPLASH_JUMP = false;
                //checkShowSingleGameDialog();
            }
        }
    }

    private int mTopDefaultColor = Color.parseColor("#FFFFFF");

    private int mTopSelectedColor = Color.parseColor("#0079FB");

    private ViewPager mViewPager;
    private ScrollIndicatorView mFragmentTabmainIndicator;
    private ImageView mImgTabMain;

    public ArrayList<Fragment> fragmentList;
    private int lastCurrentPosition = 0;
    private int game_type = 1;


    private ImageView mIvMainHomePageDownload;
    private FrameLayout mFlMessage;
    private TextView mViewMessageTips;
    //    private RelativeLayout     mRlFloatShowSingleGame;
    //    private ImageView          mIvGameIcon;
    private ConstraintLayout mClMainRookiesShow;
    public ConstraintLayout mClMainComeBack;
    public ConstraintLayout mClMainRightFloat;
    public LinearLayout mTitleBar_Layout;
    public ImageView mIvSearch, mIvMessage, mIvLine;
    public ImageView mIvMainRightFloatClose, mIvMainComeBackClose, mIvMainRookiesClose;
    public boolean isCloseRookies, isCloseComeBack, isCloseFloat;

    private void bindView() {
        mTitleBar_Layout = findViewById(R.id.titleBar_Layout);
        ImmersionBar.with(this).fullScreen(true).init();
        ImmersionBar.setTitleBar(_mActivity, mTitleBar_Layout);
        mViewPager = findViewById(R.id.view_pager);
        mFragmentTabmainIndicator = findViewById(R.id.fragment_tabmain_indicator);
        mImgTabMain = findViewById(R.id.img_tab_main);
        mIvSearch = findViewById(R.id.iv_main_home_page_search);
        mIvMessage = findViewById(R.id.iv_message);
        mIvLine = findViewById(R.id.iv_line);
        mImgTabMain.setOnClickListener(v -> {
            //跳转省钱攻略
            startFragment(new DiscountStrategyFragment());
        });

        mIvMainHomePageDownload = findViewById(R.id.iv_main_home_page_download);
        mFlMessage = findViewById(R.id.fl_message);
        mViewMessageTips = findViewById(R.id.view_message_tips);
        mClMainRookiesShow = findViewById(R.id.cl_main_rookies_show);
        mClMainComeBack = findViewById(R.id.cl_main_come_back_show);
        mClMainRightFloat = findViewById(R.id.cl_main_right_float);
        mIvMainRightFloatClose = findViewById(R.id.iv_main_right_float_close);
        mIvMainComeBackClose = findViewById(R.id.iv_main_come_back_close);
        mIvMainRookiesClose = findViewById(R.id.iv_main_rookies_close);

        mIvMainRookiesClose.setOnClickListener(v -> {
            isCloseRookies = true;
            mClMainRookiesShow.setVisibility(View.GONE);
        });
        mIvMainComeBackClose.setOnClickListener(v -> {
            isCloseComeBack = true;
            mClMainComeBack.setVisibility(View.GONE);
        });
        mIvMainRightFloatClose.setOnClickListener(v -> {
            isCloseFloat = true;
            mClMainRightFloat.setVisibility(View.GONE);
        });

        mIvMainHomePageDownload.setOnClickListener(view -> {
            //下载
            if (BuildConfig.IS_DOWNLOAD_GAME_FIRST || checkLogin()) {
                startFragment(new GameDownloadManagerFragment());
            }
        });
        findViewById(R.id.iv_main_home_page_search).setOnClickListener(v -> {
            //搜索
            startFragment(new GameSearchFragment());
        });
        mFlMessage.setOnClickListener(view -> {
            //消息
            //goMessageCenter();
            //showMessageTip(false);
            goKefuCenter();
        });

        setViewPagerContent(mViewPager, mFragmentTabmainIndicator);
        initFloatView();

        int dynamicPagerHeight = 50;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int topMargin = (int) (dynamicPagerHeight * density) + ScreenUtil.getStatusBarHeight(_mActivity);
        params.setMargins(0, topMargin, 0, 0);

        mViewPager.setLayoutParams(params);


        View btnView = findViewById(R.id.btn_action_1);
        AppCompatEditText appCompatEditText = findViewById(R.id.app_edit_text);
        btnView.setOnClickListener(view -> {
            String targetUrl = appCompatEditText.getText().toString();
            BrowserActivity.newInstance(_mActivity, targetUrl);
        });


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

            if (BuildConfig.APP_TEMPLATE == 9999|| BuildConfig.APP_TEMPLATE == 9998) {
                AppMenuVo appMenuVo = new AppMenuVo();
                appMenuVo.api = "xiaoyouxi";
                appMenuVo.id = -1;
                appMenuVo.setLabelSelect(false);
                appMenuVo.name = "小游戏";
                if(BuildConfig.APP_UPDATE_ID == "16" || BuildConfig.APP_UPDATE_ID == "43"){
                    appMenuVo.name = "赚钱";
                }
                appMenuBeanVo.home_menu.add(0, appMenuVo);
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
            //        indicator.setSplitMethod(FixedIndicatorView.SPLITMETHOD_WRAP);
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

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    hideFloatLayout();
                }

                @Override
                public void onPageSelected(int position) {
                    lastCurrentPosition = position;
                    try {
                        Fragment fragment = fragmentList.get(position);
                        Logs.e("onPageSelected:" + position);
                        if (fragment != null && fragment instanceof MainGamePageFragment) {
                            game_type = ((MainGamePageFragment) fragment).getGame_type();
                            Logs.e("game_type:" + game_type);
                            switch (game_type) {
                                case 0:
                                    break;
                                case 1:
                                    Logs.e("onPageSelected1:");
                                    break;
                                case 2:
                                    Logs.e("onPageSelected2:");
                                    JiuYaoStatisticsApi.getInstance().eventStatistics(2, 18);
                                    break;
                                case 3:
                                    Logs.e("onPageSelected3:");
                                    JiuYaoStatisticsApi.getInstance().eventStatistics(3, 37);
                                    break;
                                case 4:
                                    Logs.e("onPageSelected4:");
                                    JiuYaoStatisticsApi.getInstance().eventStatistics(4, 55);
                                    break;
                                default:
                                    break;
                            }
                        }
                        if (fragment instanceof MainGameClassificationFragment) {
                            game_type = 0;
                            Logs.e("onPageSelected0:");
                        }
                        if (onMainPageChangeListener != null) {
                            Logs.e("onPageFragmentSelected:" + fragment.getClass().getSimpleName());
                            onMainPageChangeListener.onPageFragmentSelected(fragment);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        /*TextView indexTextView = mSlidingTabLayout.getTitleView(0);
        if (indexTextView != null) {
            indexTextView.getPaint().setFakeBoldText(true);
        }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImSDK.eventViewModelInstance.getSetMainCurrentItem().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer po) {
                tabCurrentItem = po;
                viewPager.setCurrentItem(po);
                selectTabPager(mTabAdapter, po);
                selectTopTab(po);
            }
        });

//        if (_mActivity.getPackageName().contains("cqxd.jy.game.nearme.gamecenter")) {
//            tabCurrentItem = 1;
//            viewPager.setCurrentItem(1);
//            selectTabPager(mTabAdapter, 1);
//            selectTopTab(1);
//        }

    }

    private void selectTopTab(int position) {
        lastCurrentPosition = position;
        try {
            Fragment fragment = fragmentList.get(position);
            if (fragment != null && fragment instanceof MainGamePageFragment) {
                game_type = ((MainGamePageFragment) fragment).getGame_type();
                switch (game_type) {
                    case 1:
                        break;
                    case 2:
                        JiuYaoStatisticsApi.getInstance().eventStatistics(2, 18);
                        break;
                    case 3:
                        JiuYaoStatisticsApi.getInstance().eventStatistics(3, 37);
                        break;
                    case 4:
                        JiuYaoStatisticsApi.getInstance().eventStatistics(4, 55);
                        break;
                    default:
                        break;
                }
            }
            if (fragment instanceof MainGameClassificationFragment) {
                game_type = 0;
            }
            if (onMainPageChangeListener != null) {
                onMainPageChangeListener.onPageFragmentSelected(fragment);
            }
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
                if (!TextUtils.isEmpty(appMenuBeanVo.home_menu.get(key).icon) && !TextUtils.isEmpty(appMenuBeanVo.home_menu.get(key).icon_active)) {
                    mTvIndicator.setVisibility(View.GONE);
                    mIvIndicator.setVisibility(View.VISIBLE);
                    if (key == currentItem) {
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
                                                int height = ScreenUtil.dp2px(_mActivity, 28);
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
                    } else {
                        if (!TextUtils.isEmpty(appMenuBeanVo.home_menu.get(key).icon)) {
                            GlideApp.with(_mActivity).asBitmap()
                                    .load(appMenuBeanVo.home_menu.get(key).icon)
                                    .placeholder(R.mipmap.img_placeholder_v_2)
                                    .into(new CustomTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            if (resource != null && mIvIndicator.getLayoutParams() != null) {
                                                mIvIndicator.setImageBitmap(resource);
                                                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mIvIndicator.getLayoutParams();
                                                int height = ScreenUtil.dp2px(_mActivity, 24);
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
                } else {
                    mTvIndicator.setVisibility(View.VISIBLE);
                    mIvIndicator.setVisibility(View.GONE);
                }
            }
        }
    }

    class TabAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        private List<Fragment> mFragmentList;
        private List<String> mTitles;

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
                if (!TextUtils.isEmpty(appMenuBeanVo.home_menu.get(position).icon)) {
                    GlideApp.with(_mActivity).asBitmap()
                            .load(appMenuBeanVo.home_menu.get(position).icon)
                            .placeholder(R.mipmap.img_placeholder_v_2)
                            .into(new CustomTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    if (resource != null && mIvIndicator.getLayoutParams() != null) {
                                        mIvIndicator.setImageBitmap(resource);
                                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mIvIndicator.getLayoutParams();
                                        int height = ScreenUtil.dp2px(_mActivity, 24);
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
            } else {
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
                        //fragmentList.add(new MainPageXingYouTabFragment());
                        break;
                    case "txy_home_page":
                        fragmentList.add(new MainPageXingYouFragment());
                        //fragmentList.add(new MainPageXingYouTabFragment());
                        break;
                    case "flf_home_page":
                        fragmentList.add(new MainPageFuLiFuFragment());
                        break;
                    case "hj_home_page":
                        fragmentList.add(NewMainPageGameCollectionFragment.newInstance(appMenuVo.params.container_id));
                        break;
                    case "jx_home_page":
                        mJingXuanFragment = MainPageJingXuanFragment.newInstance();
                        fragmentList.add(mJingXuanFragment); // 添加到列表
                        //fragmentList.add(new MainPageJingXuanFragment());
                        break;
                    case "web_home_page":
                        //网页
                        if (appMenuVo.params != null) {
                            fragmentList.add(BrowserFragment.newInstance(appMenuVo.params.url, false));
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
                if (checkTitleLabels(home_menu.get(i).api)) {
                    labels.add(home_menu.get(i).name);
                }
            }
            return labels;
        }
        return labels;
    }

    private boolean checkTitleLabels(String api) {
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


    private FrameLayout mFlLayoutFloat;
    private ImageView mIvFloatItem, mIvFloatItem_2;
    private RelativeLayout mRlFloat;
    private ImageView mIvFloat;
    private LinearLayout mLlFloatList;
    private RelativeLayout mFlFloatItem;
    private ImageView mIvFloatTip;

    /**
     * 初始化悬浮控件
     */
    private void initFloatView() {
        mFlLayoutFloat = findViewById(R.id.fl_layout_float);
        mIvFloatItem = findViewById(R.id.iv_float_item);
        mIvFloatItem.setVisibility(View.VISIBLE);
        mIvFloatItem_2 = findViewById(R.id.iv_float_item_2);
        mRlFloat = findViewById(R.id.rl_float);
        mRlFloat.setVisibility(View.GONE);
        mIvFloat = findViewById(R.id.iv_float);
        mLlFloatList = findViewById(R.id.ll_float_list);

        mFlFloatItem = findViewById(R.id.fl_float_item);
        mIvFloatTip = findViewById(R.id.iv_float_tip);

        mIvFloatItem.setOnClickListener(v -> {
            showFloatLayout();
            //            startFragment(TaskCenterFragment.newInstance(true));
        });
        mIvFloat.setOnClickListener(v -> {
            hideFloatLayout();
        });
        showFloatItem2();
        getFloatInfoData();
    }

    private void showFloatItem2() {
        boolean isShow = false;
        if (UserInfoModel.getInstance().isSetCertification()) {
            isShow = false;
        }
        mIvFloatItem_2.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mIvFloatItem_2.setOnClickListener(v -> {
            if (checkLogin()) {
                startFragment(new CertificationFragment());
            }
        });
    }


    /**
     * 控制小白云动画
     */
    private void showFloatBubble() {
        if (!isShowFloatAnim) {
            return;
        }
        try {
            AnimationDrawable animation = (AnimationDrawable) _mActivity.getResources().getDrawable(
                    isFloatSlide ? R.drawable.anim_main_float_left : R.drawable.anim_main_float_right);
            mIvFloatTip.setBackground(animation);
            if (isShowFloatBubble) {
                mIvFloatTip.setVisibility(View.VISIBLE);
                animation.start();
            } else {
                animation.stop();
                mIvFloatTip.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mIvFloatTip.setVisibility(View.GONE);
        }
    }

    private void showFloatLayout() {
        if (mFlFloatItem != null && mRlFloat != null) {
            mFlFloatItem.setVisibility(View.GONE);
            mRlFloat.setVisibility(View.VISIBLE);
        }
    }

    private void hideFloatLayout() {
        if (mFlFloatItem != null && mRlFloat != null) {
            mFlFloatItem.setVisibility(View.VISIBLE);
            mRlFloat.setVisibility(View.GONE);
        }
    }

    private boolean isShowFloatBubble = false;
    private boolean isFloatSlide;

    private static final String SP_FLOAT_ITEM_READ_DATE = "SP_FLOAT_ITEM_READ_DATE";
    private static final String SP_FLOAT_ITEM_DAILY_READ = "SP_FLOAT_ITEM_DAILY_READ";
    private static final String SP_FLOAT_ITEM_DAILY_SIGN_READ = "SP_FLOAT_ITEM_DAILY_SIGN_READ";

    private List<View> mRedDotViews = new ArrayList<>();

    private void addLlFloatItemView(FloatItemInfoVo.DataBean dataBean) {
        if (dataBean == null) {
            return;
        }
        if (mLlFloatList != null) {
            mLlFloatList.removeAllViews();
            mRedDotViews.clear();

            SPUtils spUtils = new SPUtils(_mActivity, Constants.SP_COMMON_NAME);
            int lastDate = spUtils.getInt(SP_FLOAT_ITEM_READ_DATE, 0);
            int data = CommonUtils.getCurrentDate();

            {
                boolean dailySignRead = spUtils.getBoolean(SP_FLOAT_ITEM_DAILY_SIGN_READ, false);
                boolean isSign = false;
                if (!dailySignRead) {
                    //未读
                    isSign = false;
                } else {
                    //已读
                    if (lastDate < data) {
                        // new day
                        isSign = false;
                        spUtils.putBoolean(SP_FLOAT_ITEM_DAILY_SIGN_READ, false);
                    } else if (lastDate == data) {
                        // same day
                        isSign = spUtils.getBoolean(SP_FLOAT_ITEM_DAILY_SIGN_READ, false);
                    }
                }
                FloatItemInfoVo.ItemVo itemInfoVo1 = new FloatItemInfoVo.ItemVo(1, R.mipmap.ic_main_float_1, "每日签到", "签到送积分，兑好礼~", isSign);
                mLlFloatList.addView(createFloatItemView(itemInfoVo1));
            }

            FloatItemInfoVo.FloatJumpInfoVo jumpInfoVo = dataBean.getIcon_best();
            if (jumpInfoVo != null) {
                String title = jumpInfoVo.getTitle();
                String subTitle = jumpInfoVo.getTitle2();

                boolean dailyRead = spUtils.getBoolean(SP_FLOAT_ITEM_DAILY_READ, false);
                boolean isRead = false;

                if (!dailyRead) {
                    //未读
                    isRead = false;
                } else {
                    //已读
                    if (lastDate < data) {
                        // new day
                        isRead = false;
                        spUtils.putBoolean(SP_FLOAT_ITEM_DAILY_READ, false);
                    } else if (lastDate == data) {
                        // same day
                        isRead = spUtils.getBoolean(SP_FLOAT_ITEM_DAILY_READ, false);
                    }
                }

                int iconResId = R.mipmap.ic_main_float_2;
                if (jumpInfoVo.getResId() == 1) {
                    iconResId = R.mipmap.ic_main_float_2_1;
                } else if (jumpInfoVo.getResId() == 2) {
                    iconResId = R.mipmap.ic_main_float_2_2;
                }
                FloatItemInfoVo.ItemVo itemInfoVo2 = new FloatItemInfoVo.ItemVo(2, iconResId, title, subTitle, isRead);
                itemInfoVo2.setJumpInfoVo(jumpInfoVo);
                mLlFloatList.addView(createFloatItemView(itemInfoVo2));
            }

            showFloatBubble();
        }
    }

    private View createFloatItemView(FloatItemInfoVo.ItemVo itemInfoVo) {
        View itemView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_item_main_float, null);

        ImageView mIv = itemView.findViewById(R.id.iv);
        View mViewDot = itemView.findViewById(R.id.view_dot);
        TextView mTvText1 = itemView.findViewById(R.id.tv_text_1);
        TextView mTvText2 = itemView.findViewById(R.id.tv_text_2);

        mIv.setImageResource(itemInfoVo.getIconRes());

        //        isShowFloatBubble = isShowFloatBubble || !itemInfoVo.isRead();
        mViewDot.setVisibility(itemInfoVo.isRead() ? View.GONE : View.VISIBLE);
        mRedDotViews.add(mViewDot);

        mTvText1.setText(itemInfoVo.getTitle());
        mTvText2.setText(itemInfoVo.getSubTitle());
        mTvText2.setSingleLine(true);

        itemView.setOnClickListener(v -> {
            SPUtils spUtils = new SPUtils(_mActivity, Constants.SP_COMMON_NAME);
            if (itemInfoVo.getId() == 1) {
                //签到
                FragmentHolderActivity.startFragmentInActivity(_mActivity, TaskCenterFragment.newInstance());
                spUtils.putBoolean(SP_FLOAT_ITEM_DAILY_SIGN_READ, true);
            } else if (itemInfoVo.getId() == 2) {
                //自定义
                if (itemInfoVo.getJumpInfoVo() != null) {
                    appJumpAction(itemInfoVo.getJumpInfoVo());
                    spUtils.putBoolean(SP_FLOAT_ITEM_DAILY_READ, true);
                }
            }
            mViewDot.setVisibility(View.GONE);
            spUtils.putInt(SP_FLOAT_ITEM_READ_DATE, CommonUtils.getCurrentDate());
            setShowFloatBubble();
        });
        return itemView;
    }

    private void setShowFloatBubble() {
        if (mRedDotViews != null && !mRedDotViews.isEmpty()) {
            boolean isShowRedDot = false;
            for (View mRedDotView : mRedDotViews) {
                isShowRedDot = isShowRedDot || mRedDotView.getVisibility() == View.VISIBLE;
            }
            //            isShowFloatBubble = isShowRedDot;
            showFloatBubble();
        }
    }

    /**
     * 设置App主题样式
     * 首页顶部样式
     */
    private void setAppStyle() {
        File file = SdCardManager.getFileMenuDir(_mActivity);
        if (file == null) {
            return;
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            String json = ACache.get(file).getAsString(AppStyleConfigs.JSON_KEY);

            Type listType = new TypeToken<SplashVo.AppStyleVo.DataBean>() {
            }.getType();
            Gson gson = new Gson();
            SplashVo.AppStyleVo.DataBean appStyleVo = gson.fromJson(json, listType);

            if (appStyleVo != null && appStyleVo.getApp_header_info() != null) {
                setAppStyleInfo(appStyleVo);
                String targetUrl = appStyleVo.getApp_header_info().getUrl();
                if (!TextUtils.isEmpty(targetUrl)) {
                    mImgTabMain.setOnClickListener(v -> {
                        //跳转到对应的url
                        BrowserActivity.newInstance(_mActivity, targetUrl);
                    });
                }
            } else {
                setAppDefaultStyle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置App主题默认样式
     */
    private void setAppDefaultStyle() {
        mTopDefaultColor = ContextCompat.getColor(_mActivity, R.color.white_lucency);
        mTopSelectedColor = ContextCompat.getColor(_mActivity, R.color.color_0079fb);
        updateIndicator();
    }

    private void setAppStyleInfo(SplashVo.AppStyleVo.DataBean dataBean) {
        if (dataBean == null) {
            return;
        }
        try {
            setStatusBar(0x00000000);
            if (mImgTabMain != null) {
                mImgTabMain.setVisibility(View.VISIBLE);
            }
            File targetFile = new File(SdCardManager.getFileMenuDir(_mActivity), AppStyleConfigs.IMG_TOP_BG);
            Bitmap originalBitmap = BitmapFactory.decodeFile(targetFile.getPath());
            mImgTabMain.setImageBitmap(originalBitmap);

            try {
                mTopDefaultColor = Color.parseColor(dataBean.getApp_header_info().getDefault_color());
                mTopSelectedColor = Color.parseColor(dataBean.getApp_header_info().getSelected_color());

            } catch (Exception e) {
                e.printStackTrace();
                mTopDefaultColor = ContextCompat.getColor(_mActivity, R.color.white_lucency);
                mTopSelectedColor = ContextCompat.getColor(_mActivity, R.color.color_0079fb);
            }
            updateIndicator();

        } catch (Exception e) {
            e.printStackTrace();
            setAppDefaultStyle();
        }
    }

    private void updateIndicator() {
        selectTabPager(mTabAdapter, tabCurrentItem);
    }

    public void backToRecyclerTop() {
        try {
            BaseListFragment fragment = (BaseListFragment) fragmentList.get(lastCurrentPosition);
            if (fragment != null) {
                fragment.listBackTop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private OnMainPageChangeListener onMainPageChangeListener;

    public void setOnMainPageChangeListener(OnMainPageChangeListener onMainPageChangeListener) {
        this.onMainPageChangeListener = onMainPageChangeListener;
    }


    /**
     * @param dx
     * @param dy
     */
    public void onItemRecyclerViewScrolled(View view, int dx, int dy) {
        if (dx == 0 && dy == 0) {
            return;
        }
        hideFloatLayout();
    }


    /**
     * 右吸附动画开关
     */
    private final boolean isShowFloatAnim = true;

    /**
     * float从旁边居中动画
     */
    public void showFloatAnim() {
        if (!isShowFloatAnim || mFlLayoutFloat == null || mFlLayoutFloat.getVisibility() != View.VISIBLE) {
            return;
        }
        int rotation = -45;
        int translationX = (int) (24 * density);

        //小狐狸旋转 x轴位移
        PropertyValuesHolder proTranslationX = PropertyValuesHolder.ofFloat("translationX", translationX, 0f);
        PropertyValuesHolder proRotation = PropertyValuesHolder.ofFloat("rotation", rotation, 0f);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mIvFloatItem, proTranslationX, proRotation);
        animator.setDuration(getResources().getInteger(R.integer.duration_main_toolbar));
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }
        });
        animator.start();

        isFloatSlide = false;
        //气泡
        if (isShowFloatBubble) {
            int bubbleTranslationX = (int) (25 * density);
            int bubbleTranslationY = (int) (16 * density);

            PropertyValuesHolder holderTranslationX = PropertyValuesHolder.ofFloat("translationX", -bubbleTranslationX, 0f);
            PropertyValuesHolder holderTranslationY = PropertyValuesHolder.ofFloat("translationY", bubbleTranslationY, 0f);
            PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);

            ObjectAnimator bubbleAnimator = ObjectAnimator.ofPropertyValuesHolder(mIvFloatTip,
                    holderTranslationX, holderTranslationY, alpha);
            bubbleAnimator.setDuration(getResources().getInteger(R.integer.duration_main_toolbar));
            bubbleAnimator.start();
        }
        showFloatBubble();
    }

    /**
     * 设置联动
     */
    public void setLayoutFloatParams() {
        try {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mFlLayoutFloat.getLayoutParams();
            params.setMargins(0, 0, 0, (int) (24 * density));
            mFlLayoutFloat.setLayoutParams(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * float吸附到旁边动画
     */
    public void hideFloatAnim() {
        if (!isShowFloatAnim || mFlLayoutFloat == null || mFlLayoutFloat.getVisibility() != View.VISIBLE) {
            return;
        }
        int rotation = -45;
        int translationX = (int) (48 * ScreenUtil.getScreenDensity(_mActivity));

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mFlLayoutFloat.getLayoutParams();
        params.setMargins(0, 0, 0, (int) (72 * density));

        PropertyValuesHolder proTranslationX = PropertyValuesHolder.ofFloat("translationX", 0f, translationX);
        PropertyValuesHolder proRotation = PropertyValuesHolder.ofFloat("rotation", 0f, rotation);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mIvFloatItem, proTranslationX, proRotation);
        animator.setDuration(getResources().getInteger(R.integer.duration_main_toolbar));
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mFlLayoutFloat.setLayoutParams(params);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        animator.start();


        isFloatSlide = true;
        //气泡
        if (isShowFloatBubble) {
            int bubbleTranslationX = (int) (25 * density);
            int bubbleTranslationY = (int) (16 * density);

            PropertyValuesHolder holderTranslationX = PropertyValuesHolder.ofFloat("translationX", 0f, -bubbleTranslationX);
            PropertyValuesHolder holderTranslationY = PropertyValuesHolder.ofFloat("translationY", 0f, bubbleTranslationY);
            PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);

            ObjectAnimator bubbleAnimator = ObjectAnimator.ofPropertyValuesHolder(mIvFloatTip,
                    holderTranslationX, holderTranslationY, alpha);
            bubbleAnimator.setDuration(getResources().getInteger(R.integer.duration_main_toolbar));
            bubbleAnimator.start();
        }
        showFloatBubble();
    }


    private static final String SP_SHOW_FLOAT_DATE = "SP_SHOW_FLOAT_DATE";

    /**
     * 获取Float数据
     */
    private void getFloatInfoData() {
        if (mViewModel != null) {
            mViewModel.getGameFloatData(new OnBaseCallback<FloatItemInfoVo>() {
                @Override
                public void onSuccess(FloatItemInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            isShowFloatBubble = false;
                            addLlFloatItemView(data.getData());
                            post(() -> {
                                SPUtils spUtils = new SPUtils(_mActivity, Constants.SP_COMMON_NAME);
                                int lastDate = spUtils.getInt(SP_SHOW_FLOAT_DATE, 0);
                                int date = CommonUtils.getCurrentDate();
                                if (lastDate < date) {
                                    showFloatLayout();
                                    spUtils.putInt(SP_SHOW_FLOAT_DATE, date);
                                }
                            });
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }


    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        getFloatInfoData();
        showFloatItem2();
        //getKefuMessageData();
        appFloatIcon();
    }


    private void checkShowSingleGameDialog() {
        //        mRlFloatShowSingleGame.setVisibility(View.GONE);
        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
        boolean hasShow = spUtils.getBoolean(SpConstants.SP_HAS_SHOW_SINGLE_GAME);
        if (!hasShow && !Setting.HAS_SPLASH_JUMP) {
            try {
                String channel = "";
                if (!TextUtils.isEmpty(BuildConfig.TENCENT_APP_ID)) {
                    channel = ChannelReaderUtil.getChannel(App.getContext());
                } else if (!TextUtils.isEmpty(BuildConfig.KUAISHOU_APP_ID)) {
                    channel = TurboHelper.getChannel(App.instance());
                } else if (BuildConfig.IS_CONTAINS_TOUTIAO_SDK) {
                    channel = HumeSDK.getChannel(_mActivity.getApplicationContext());
                }

                if (TextUtils.isEmpty(channel)) {
                    channel = AppUtils.getChannelFromApk();
                }
                //channel = "ea0000001_12741";
                String[] array = channel.split("_");
                int gameid;
                if (array.length == 2) {
                    gameid = Integer.parseInt(array[1]);
                    getGameAdvertData(gameid);
                } else {
                    gameid = AppUtils.getChannelGameidFromApk();
                    getGameAdvertData(gameid);
                }
                Log.e(SpConstants.SP_HAS_SHOW_SINGLE_GAME, "gameid = " + gameid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getGameAdvertData(final int gameid) {
        if (gameid > 0) {
            if (mViewModel != null) {
                mViewModel.getChannelGameData(gameid, new OnBaseCallback<GameDataVo>() {
                    @Override
                    public void onSuccess(GameDataVo data) {
                        if (data != null && data.isStateOK() && data.getData() != null) {
                            GameInfoVo gameInfoVo = data.getData();
                            //                            showSingleGameDialog(gameInfoVo);
                            if (gameInfoVo.getStatus() == 1) {
                                startFragment(GameDetailInfoFragment.newInstanceAdvert(gameid, gameInfoVo));
                            } else {
                                Logs.e("gameid = " + gameid + " 已经失效了");
                            }
                        }
                    }
                });
            }
        }
    }

    public void showVsMainRookies(boolean isShow, String url) {
        if (!isCloseRookies) {
            if (isShow) {
                mClMainRookiesShow.setVisibility(View.VISIBLE);
                mClMainRookiesShow.setOnClickListener(view -> {
                    BrowserActivity.newInstance(_mActivity, url);
                });
            } else {
                mClMainRookiesShow.setVisibility(View.GONE);
                mClMainRookiesShow.setOnClickListener(null);
            }
        } else {
            mClMainRookiesShow.setVisibility(View.GONE);
            mClMainRookiesShow.setOnClickListener(null);
        }

    }

    private void showRookiesShowDialog(XinRenPopDataVo.DataBean bean) {
        Context mContext = _mActivity;
        CustomDialog dialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_rookies, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        AppCompatImageView mIvTarget = dialog.findViewById(R.id.iv_target);
        ImageView mIvClosed = dialog.findViewById(R.id.iv_closed);

        dialog.setCanceledOnTouchOutside(false);
        mIvTarget.setOnClickListener(view -> {
            BrowserActivity.newInstance(_mActivity, bean.url);
        });
        mIvClosed.setOnClickListener(v -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        });
        if (LifeUtil.isAlive(_mActivity)) {
            dialog.show();
            SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
            spUtils.putBoolean("IS_SHOW_ROOKIES_DIALOG", true);
        }
    }

    private void getKefuMessageData() {
        if (mViewModel != null && UserInfoModel.getInstance().isLogined()) {
            Runnable runnable1 = () -> {
                int messageCount = MessageDbInstance.getInstance().getUnReadMessageCount();
                _mActivity.runOnUiThread(() -> {
                    showMessageTip(messageCount > 0);
                });
                MessageVo messageVo = MessageDbInstance.getInstance().getMaxIdMessageVo(1);
                _mActivity.runOnUiThread(() -> {
                    int maxID = 0;
                    if (messageVo != null) {
                        maxID = messageVo.getMessage_id();
                    }
                    mViewModel.getKefuMessageData(getStateEventKey().toString(), maxID, new OnBaseCallback<MessageListVo>() {
                        @Override
                        public void onSuccess(MessageListVo messageListVo) {
                            if (messageListVo != null) {
                                if (messageListVo.isStateOK()) {
                                    saveMessageToDb(messageListVo.getData());
                                }
                            }
                        }
                    });
                    SPUtils spUtils = new SPUtils(_mActivity, SP_MESSAGE);
                    long logTime = spUtils.getLong(TAG_DYNAMIC_GAME_MESSAGE_LOG_TIME, 0);

                    mViewModel.getDynamicGameMessageData(logTime, new OnBaseCallback<MessageListVo>() {
                        @Override
                        public void onSuccess(MessageListVo messageListVo) {
                            if (messageListVo != null) {
                                if (messageListVo.isStateOK() && messageListVo.getData() != null) {
                                    saveMessageToDb(4, messageListVo);
                                }
                            }
                            SPUtils spUtils = new SPUtils(_mActivity, SP_MESSAGE);
                            spUtils.putLong(TAG_DYNAMIC_GAME_MESSAGE_LOG_TIME, System.currentTimeMillis() / 1000);
                        }
                    });
                });
            };
            new Thread(runnable1).start();
        }
    }

    private void saveMessageToDb(List<MessageInfoVo> messageInfoVoList) {
        Runnable runnable = () -> {
            if (messageInfoVoList != null) {
                for (MessageInfoVo messageInfoVo : messageInfoVoList) {
                    MessageVo messageVo = messageInfoVo.transformIntoMessageVo();
                    MessageDbInstance.getInstance().saveMessageVo(messageVo);
                }
                int messageCount = MessageDbInstance.getInstance().getUnReadMessageCount();
                _mActivity.runOnUiThread(() -> {
                    showMessageTip(messageCount > 0);
                });
            }
        };
        new Thread(runnable).start();
    }

    private void saveMessageToDb(int type, MessageListVo messageListVo) {
        Runnable runnable = () -> {
            for (MessageInfoVo messageInfoVo : messageListVo.getData()) {
                MessageVo messageVo = messageInfoVo.transformIntoMessageVo();
                MessageDbInstance.getInstance().saveMessageVo(messageVo);
            }
            int messageCount = MessageDbInstance.getInstance().getUnReadMessageCount();
            _mActivity.runOnUiThread(() -> {
                showMessageTip(messageCount > 0);
            });
        };
        new Thread(runnable).start();
    }

    /**
     * 消息红点提示
     *
     * @param isShow
     */
    private void showMessageTip(boolean isShow) {
        if (mViewMessageTips != null) {
            mViewMessageTips.setVisibility(isShow && UserInfoModel.getInstance().isLogined() ? View.VISIBLE : View.GONE);
        }
    }

    private void appFloatIcon() {
        if (mViewModel != null) {
            mViewModel.appFloatIcon(new OnBaseCallback<AppFloatIconVo>() {
                @Override
                public void onSuccess(AppFloatIconVo data) {
                    if (data != null && data.getData() != null) {
                        if (!isCloseFloat) {
                            mClMainRightFloat.setVisibility(View.VISIBLE);
                            GlideUtils.loadNormalImage(_mActivity, data.getData().getPic(), ((AppCompatImageView) findViewById(R.id.iv_main_right_float)), R.mipmap.ic_placeholder);
                            mClMainRightFloat.setOnClickListener(v -> {
                                AppJumpAction jumpAction = new AppJumpAction(_mActivity);
                                jumpAction.jumpAction(new AppJumpInfoBean(data.getData().getPage_type(), data.getData().getParam()));
                            });
                        } else {
                            mClMainRightFloat.setVisibility(View.GONE);
                        }
                    } else {
                        mClMainRightFloat.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}
