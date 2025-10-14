package com.zqhy.app.core.view.game;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.box.common.utils.floattoast.XToast;
import com.box.other.blankj.utilcode.util.ActivityUtils;
import com.box.other.blankj.utilcode.util.AppUtils;
import com.box.other.blankj.utilcode.util.Logs;
import com.box.other.blankj.utilcode.util.StringUtils;
import com.box.other.hjq.toast.Toaster;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzf.easyfloat.EasyFloat;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.utils.IOUtils;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.lzy.okserver.download.DownloadTask;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zqhy.app.Setting;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.config.SpConstants;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.community.comment.CommentTypeListVo;
import com.zqhy.app.core.data.model.forum.ForumCategoryVo;
import com.zqhy.app.core.data.model.forum.ForumListVo;
import com.zqhy.app.core.data.model.forum.ForumReplyTopLikeVo;
import com.zqhy.app.core.data.model.game.GameAppointmentOpVo;
import com.zqhy.app.core.data.model.game.GameDataVo;
import com.zqhy.app.core.data.model.game.GameDownloadLogVo;
import com.zqhy.app.core.data.model.game.GameDownloadTimeExtraVo;
import com.zqhy.app.core.data.model.game.GameDownloadUrlVo;
import com.zqhy.app.core.data.model.game.GameExtraVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.GameListVo;
import com.zqhy.app.core.data.model.game.GameShortCommentVo;
import com.zqhy.app.core.data.model.game.GameToolBoxListVo;
import com.zqhy.app.core.data.model.game.GetCardInfoVo;
import com.zqhy.app.core.data.model.game.NewGameCouponItemVo;
import com.zqhy.app.core.data.model.game.detail.GameActivityVo;
import com.zqhy.app.core.data.model.game.detail.GameCardListVo;
import com.zqhy.app.core.data.model.game.detail.GameDesVo;
import com.zqhy.app.core.data.model.game.detail.GameLikeListVo;
import com.zqhy.app.core.data.model.game.detail.GameRebateVo;
import com.zqhy.app.core.data.model.game.detail.GameRecommendListVo;
import com.zqhy.app.core.data.model.game.detail.GameRefundVo;
import com.zqhy.app.core.data.model.game.detail.GameServerListVo;
import com.zqhy.app.core.data.model.game.detail.GameWelfareVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo1;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoListVo1;
import com.zqhy.app.core.data.model.transaction.TradeGoodInfoVo1;
import com.zqhy.app.core.data.model.tryplay.TryGameItemVo;
import com.zqhy.app.core.data.model.user.AdSwiperListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.tool.AppUtil;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.AppsUtils;
import com.zqhy.app.core.tool.utilcode.FileUtils;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.view.browser.BrowserGameActivity;
import com.zqhy.app.core.view.community.comment.WriteCommentsFragment;
import com.zqhy.app.core.view.community.comment.holder.NewGameShortCommentItemHolder;
import com.zqhy.app.core.view.community.integral.CommunityIntegralMallFragment;
import com.zqhy.app.core.view.easy_play.EasyToPlayFragment;
import com.zqhy.app.core.view.game.dialog.CardDialogHelper;
import com.zqhy.app.core.view.game.forum.ForumDetailFragment;
import com.zqhy.app.core.view.game.forum.ForumLongDetailFragment;
import com.zqhy.app.core.view.game.forum.ForumPostFragment;
import com.zqhy.app.core.view.game.forum.holder.CategoryDetailItemHolder;
import com.zqhy.app.core.view.game.forum.holder.ForumListItemHolder;
import com.zqhy.app.core.view.game.forum.holder.StickyDetailItemHolder;
import com.zqhy.app.core.view.game.holder.GameActivityItemHolder;
import com.zqhy.app.core.view.game.holder.GameCardItemHolder;
import com.zqhy.app.core.view.game.holder.GameDesItemHolder;
import com.zqhy.app.core.view.game.holder.GameGiftItemHolder;
import com.zqhy.app.core.view.game.holder.GameInfoItemHolder;
import com.zqhy.app.core.view.game.holder.GameLikeItemHolder;
import com.zqhy.app.core.view.game.holder.GameManufacturerInformationItemHolder;
import com.zqhy.app.core.view.game.holder.GameRefundItemHolder;
import com.zqhy.app.core.view.game.holder.GameServerItemHolder;
import com.zqhy.app.core.view.game.holder.GameToolBoxItemHolder;
import com.zqhy.app.core.view.game.holder.GameTryItemHolder;
import com.zqhy.app.core.view.game.holder.GameWelfareItemHolder;
import com.zqhy.app.core.view.game.holder.NewCustomRecommendItemHolder;
import com.zqhy.app.core.view.game.holder.NewGameActiviItemHolder;
import com.zqhy.app.core.view.game.holder.NewGameCouponItemHolder;
import com.zqhy.app.core.view.game.holder.NewGameCouponListItemHolder;
import com.zqhy.app.core.view.game.holder.NewGameGiftItemHolder;
import com.zqhy.app.core.view.game.holder.NewGameRebateItemHolder;
import com.zqhy.app.core.view.game.holder.NewGameServerItemHolder;
import com.zqhy.app.core.view.main.MainActivity;
import com.zqhy.app.core.view.main.holder.GameNoMoreBigItemHolder;
import com.zqhy.app.core.view.main.holder.GameNoMoreItemHolder;
import com.zqhy.app.core.view.transaction.TransactionGoodDetailFragment;
import com.zqhy.app.core.view.transaction.holder.TradeItemHolder1;
import com.zqhy.app.core.view.transaction.util.CustomPopWindow;
import com.zqhy.app.core.view.user.CertificationFragment;
import com.zqhy.app.core.view.user.newvip.NewUserVipFragment;
import com.zqhy.app.core.view.user.welfare.MyCardListFragment;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder2;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.BuildConfig;
import com.zqhy.app.newproject.R;
import com.zqhy.app.notify.DownloadNotifyManager;
import com.zqhy.app.share.ShareHelper;
import com.zqhy.app.subpackage.Util;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.DownloadUtils;
import com.zqhy.app.utils.LifeUtil;
import com.zqhy.app.utils.RecyclerViewNoBugLinearLayoutManager;
import com.zqhy.app.utils.sdcard.SdCardManager;
import com.zqhy.app.utils.sp.SPUtils;
import com.box.mod.view.FloatViewHelper;
import com.zqhy.mod.game.GameLauncher;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import cn.jzvd.Jzvd;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;


/**
 * @author Administrator
 * @date 2018/11/20
 */

public class GameDetailInfoFragment extends BaseFragment<GameViewModel> implements View.OnClickListener {

    private String TAG = GameDetailInfoFragment.class.getSimpleName();

    private String[] bgColorList = new String[]{"#323E58", "#936785", "#171848", "#424981", "#392734", "#555368", "#5C6B7F", "#678279", "#A66B6B", "#221814", "#4D4542", "#4A4A47", "#3B0902", "#563422", "#64472F", "#573422", "#1E2639", "#805236", "#3C1E1C", "#000002", "#060808", "#4B3269", "#563E46", "#A66A6B", "#693C36", "#3D241D", "#863816", "#7A634A", "#252530", "#496C26", "#263747", "#091B3F", "#23232A", "#1E2738", "#18191F", "#0C2131", "#2E90E1", "#091C30", "#0C0A10", "#304467", "#1182E7", "#A73E1B", "#3D5D76", "#763D3D", "#763D50", "#413D76", "#27AA75", "#AA2769", "#AA6027", "#5727AA", "#0E1826", "#243164", "#040221", "#121B42", "#0F0F0F", "#0D161B", "#210B07", "#070A0F", "#230F06", "#533D76", "#230A14", "#280915", "#7E1706", "#AA2738", "#141211", "#130201", "#241B0D", "#734C45", "#83415F", "#773F56", "#3470FF", "#3F0102", "#2E1B37", "#410102", "#400E35", "#173F64", "#1F1F45", "#9F449C", "#91635E", "#7E5BBE", "#342E34", "#1E0C18", "#1F0D19", "#2B1808", "#12153E", "#87328D", "#425101", "#981103", "#4D4333", "#59442E"};

    public static GameDetailInfoFragment newInstance(int gameid, int game_type) {
        return newInstance(gameid, game_type, false);
    }

    public static GameDetailInfoFragment newInstance(int gameid, int game_type, String action) {
        GameDetailInfoFragment fragment = new GameDetailInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("gameid", gameid);
        bundle.putInt("game_type", game_type);
        bundle.putString("action", action);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static GameDetailInfoFragment newInstanceAdvert(int gameid, GameInfoVo gameInfoVo) {
        GameDetailInfoFragment fragment = new GameDetailInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("gameid", gameid);
        bundle.putSerializable("advertGameinfo", gameInfoVo);

        fragment.setArguments(bundle);
        return fragment;
    }

    public static GameDetailInfoFragment newInstance(int gameid, int game_type, boolean autoDownload) {
        return newInstance(gameid, game_type, false, "", autoDownload, false);
    }

    public static GameDetailInfoFragment newInstance(int gameid, int game_type, boolean isFromSDK, String SDKPackageName) {
        return newInstance(gameid, game_type, isFromSDK, SDKPackageName, false, false);
    }

    public static GameDetailInfoFragment newInstance(int gameid, int game_type, boolean isFromSDK, String SDKPackageName, boolean autoDownload, boolean isFromLocalGame) {
        GameDetailInfoFragment fragment = new GameDetailInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("gameid", gameid);
        bundle.putInt("game_type", game_type);
        bundle.putBoolean("isFromSDK", isFromSDK);
        bundle.putString("SDKPackageName", SDKPackageName);
        bundle.putBoolean("autoDownload", autoDownload);
        bundle.putBoolean("isFromLocalGame", isFromLocalGame);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_GAME_DETAIL_STATE;
    }


    @Override
    protected String getStateEventTag() {
        return String.valueOf(gameid);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_game_detail_info;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    protected int game_type;
    protected int gameid;
    protected boolean isFromSDK;
    protected String SDKPackageName;
    protected boolean autoDownload;

    protected boolean isFromLocalGame;

    private int transactionPage, transactionPageCount = 12;
    private final int ACTION_TRANSACTION_GOOD_DETAIL = 0x566;
    private final int ACTION_WRITE_COMMENT = 0x444;
    private final int ACTION_COUPON_LIST = 0x461;

    private int commentPage = 1, commentPageCount = 12;
    private int delay = 0;
    private GameInfoVo advertGameinfo;
    private String action;

    private String selectBgColor;

    public String getSelectBgColor() {
        return selectBgColor;
    }

    private XToast mLocalGameFloat;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gameid = getArguments().getInt("gameid");
            game_type = getArguments().getInt("game_type");
            action = getArguments().getString("action");
            isFromSDK = getArguments().getBoolean("isFromSDK", false);
            SDKPackageName = getArguments().getString("SDKPackageName");
            TAG += "_" + gameid;
            autoDownload = getArguments().getBoolean("autoDownload", false);
            isFromLocalGame = getArguments().getBoolean("isFromLocalGame", false);
            advertGameinfo = (GameInfoVo) getArguments().getSerializable("advertGameinfo");
            if (advertGameinfo != null) {
                delay = 250;
            }
        }

        if (BuildConfig.APP_TEMPLATE == 9999) {
            if (isFromLocalGame) {
                if (mLocalGameFloat == null) {
                    if (!StringUtils.equals(BuildConfig.APP_UPDATE_ID, "1")
                            && !StringUtils.equals(BuildConfig.APP_UPDATE_ID, "11")
                            && !StringUtils.equals(BuildConfig.APP_UPDATE_ID, "16")
                            && !StringUtils.equals(BuildConfig.APP_UPDATE_ID, "43")) {
                        mLocalGameFloat = FloatViewHelper.INSTANCE.createModFloatView(_mActivity, AppUtils.getAppIcon(), new Function0<Unit>() {
                            @Override
                            public Unit invoke() {
                                GameLauncher.INSTANCE.startLocalGame(_mActivity, GameLauncher.GAME_URL);
                                return null;
                            }
                        });
                    }
                }
            }
        }
        selectBgColor = bgColorList[new Random().nextInt(bgColorList.length - 1)];
        super.initView(state);
        bindViews();
        new Handler().postDelayed(() -> {
            getNewWorkData();
            getCommentTypeV2();
//            getCommentList();
            getList2TopData();
        }, delay);
        setImmersiveStatusBar(false);
    }

    private void forceAppBarExpanded() {
        /*mAppBarLayout.postDelayed(() -> {
            mAppBarLayout.setExpanded(true, true);
        }, 0);*/
    }


    private SwipeRefreshLayout mSwipeRefreshLayout;
    //private AppBarLayout            mAppBarLayout;
    //private CollapsingToolbarLayout mCollapsing;
    private LinearLayout mLlGameTitle;
    private FixedIndicatorView mTabIndicator;
    private ViewPager mViewpager;
    private FrameLayout mFlWriteCommentTips;
    //private RelativeLayout          mLlLayoutCollapsing;
    private ImageView mIvTryGameReward;
    //private ImageView               mIvBgImage1;
    //private ImageView               mIvBgImage2;
    private ImageView mIvBack;
    private ImageView mIvMore;
    private ConstraintLayout con_forum;

    private void bindViews() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        //mAppBarLayout = findViewById(R.id.appBarLayout);
        //mCollapsing = findViewById(R.id.collapsing);
        con_forum = findViewById(R.id.con_forum);
        mLlGameTitle = findViewById(R.id.ll_game_title);
        mLlGameTitle.setBackgroundColor(Color.parseColor(selectBgColor));
        //mLlLayoutCollapsing = findViewById(R.id.ll_layout_collapsing);

        //mIvBgImage1 = findViewById(R.id.iv_bg_image_1);
        //mIvBgImage2 = findViewById(R.id.iv_bg_image_2);

        mTabIndicator = findViewById(R.id.tab_indicator);
        mViewpager = findViewById(R.id.viewpager);

        mFlWriteCommentTips = findViewById(R.id.fl_write_comment_tips);
        mIvTryGameReward = findViewById(R.id.iv_try_game_reward);

        mIvBack = findViewById(R.id.iv_back);
        findViewById(R.id.iv_forum_post).setOnClickListener(v -> {
            if (checkLogin()) {
                if (UserInfoModel.getInstance().isSetCertification()) {
                    start(ForumPostFragment.newInstance(gameid + "", gameInfoVo.getGameicon(), gameInfoVo.getAllGamename()));
                } else {
                    startFragment(CertificationFragment.newInstance());
                    //ToastT.success("请先实名后发帖");
                    Toaster.show("请先实名后发帖");
                }
            }
        });
        findViewById(R.id.iv_to_top).setOnClickListener(v -> {
            mRecyclerView2.smoothScrollToPosition(0);
        });
        mIvBack.setOnClickListener(v -> pop());

        mIvMore = findViewById(R.id.iv_more);
        mIvMore.setOnClickListener(v -> {
            showMoreDialog();
        });

        initActionBackBarAndTitle("");
        setActionBackBar(trecyclerview.com.mvvm.R.mipmap.ic_actionbar_back);
        bindGameTitleViews();
        //bindGameInfoViews();
        bindGameDownloadViews();

        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_ff8f19,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //设置样式刷新显示的位置
        mSwipeRefreshLayout.setProgressViewOffset(true, -20, 100);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            getNewWorkData();
        });
        mSwipeRefreshLayout.setEnabled(false);
        initViewPager();
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0) {
                slideTryGameFloatView();
            }
            if (dy < 0) {
                showTryGameFloatView();
            }
        }
    };

    private boolean isFloatViewShow = true;
    private boolean isShowingFloatView = false;
    private boolean isHidingFloatView = false;


    private void slideTryGameFloatView() {
        if (mIvTryGameReward != null && mIvTryGameReward.getVisibility() == View.VISIBLE) {
            if (isFloatViewShow && !isHidingFloatView) {
                isHidingFloatView = true;

                int rotation = -45;
                int translationX = (int) (48 * ScreenUtil.getScreenDensity(_mActivity));

                PropertyValuesHolder proTranslationX = PropertyValuesHolder.ofFloat("translationX", 0f, translationX);
                PropertyValuesHolder proRotation = PropertyValuesHolder.ofFloat("rotation", 0f, rotation);

                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mIvTryGameReward, proTranslationX, proRotation);
                animator.setDuration(getResources().getInteger(R.integer.duration_main_toolbar));
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isFloatViewShow = false;
                        isHidingFloatView = false;
                    }
                });
                animator.start();

            }
        }
    }

    private void showTryGameFloatView() {
        if (mIvTryGameReward != null && mIvTryGameReward.getVisibility() == View.VISIBLE) {
            if (!isFloatViewShow && !isShowingFloatView) {
                isShowingFloatView = true;

                int rotation = -45;
                int translationX = (int) (48 * ScreenUtil.getScreenDensity(_mActivity));

                //小狐狸旋转 x轴位移
                PropertyValuesHolder proTranslationX = PropertyValuesHolder.ofFloat("translationX", translationX, 0f);
                PropertyValuesHolder proRotation = PropertyValuesHolder.ofFloat("rotation", rotation, 0f);

                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mIvTryGameReward, proTranslationX, proRotation);
                animator.setDuration(getResources().getInteger(R.integer.duration_main_toolbar));
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isFloatViewShow = true;
                        isShowingFloatView = false;
                    }
                });
                animator.start();

            }
        }
    }


    private List<TabInfoVo> mTabInfoVoList;
    private TabAdapter mTabAdapter;
    private IndicatorViewPager indicatorViewPager;

    private void initViewPager() {
        List<View> viewList = new ArrayList<>();
        mTabInfoVoList = new ArrayList<>();


        initList1();
        viewList.add(mRecyclerView1);
        TabInfoVo tabInfoVo1 = new TabInfoVo();
        tabInfoVo1.setTitle("详情");
        tabInfoVo1.setSubTitle("省钱回馈");
        mTabInfoVoList.add(tabInfoVo1);

        /*initList2_0();
        viewList.add(mRecyclerView2_0);
        TabInfoVo tabInfoVo2_0 = new TabInfoVo();
        tabInfoVo2_0.setTitle("领福利");
        tabInfoVo2_0.setSubTitle("免费领取");
        mTabInfoVoList.add(tabInfoVo2_0);

        initList3();
        viewList.add(mRecyclerView3);
        TabInfoVo tabInfoVo3 = new TabInfoVo();
        tabInfoVo3.setTitle("开服");
        tabInfoVo3.setSubTitle("安全快捷");
        mTabInfoVoList.add(tabInfoVo3);*/

        if (!AppConfig.isHideCommunity()) {
            initList2();
            viewList.add(mRecyclerView2);
            TabInfoVo tabInfoVo2 = new TabInfoVo();
            tabInfoVo2.setTitle("社区");
            tabInfoVo2.setSubTitle("吐槽大会");
            mTabInfoVoList.add(tabInfoVo2);
        }

//        if (!BuildConfig.IS_CHANGE_TRANSACTION && !BuildConfig.IS_REPORT) {
//            if (game_type != 3) {
//                initList4();
//                viewList.add(mRecyclerView4);
//                TabInfoVo tabInfoVo4 = new TabInfoVo();
//                tabInfoVo4.setTitle("交易");
//                tabInfoVo4.setSubTitle("安全快捷");
//                mTabInfoVoList.add(tabInfoVo4);
//            }
//        }

        /*if (!BuildConfig.NEED_BIPARTITION) {
            if (game_type != 3) {
                initList6();
                viewList.add(mRecyclerView6);
                TabInfoVo tabInfoVo6 = new TabInfoVo();
                tabInfoVo6.setTitle("工具箱");
                tabInfoVo6.setSubTitle("安全快捷");
                mTabInfoVoList.add(tabInfoVo6);
            }
        }*/

        indicatorViewPager = new IndicatorViewPager(mTabIndicator, mViewpager);
        mViewpager.setOffscreenPageLimit(viewList.size());
        mTabAdapter = new TabAdapter(mTabInfoVoList, viewList);
        indicatorViewPager.setOnIndicatorPageChangeListener((preItem, currentItem) -> {
            if (currentItem == 0) {
                selectTabPager(mTabAdapter, currentItem);
                setImmersiveStatusBar(false);
            } else {
                selectTabPager1(mTabAdapter, currentItem);
                setImmersiveStatusBar(true);
            }
        });
        indicatorViewPager.setAdapter(mTabAdapter);
        mTabIndicator.post(() -> {
            if (!TextUtils.isEmpty(action) && "forum".equals(action)) {
                indicatorViewPager.setCurrentItem(1, false);
                selectTabPager(mTabAdapter, 1);
            } else {
                indicatorViewPager.setCurrentItem(0, false);
                selectTabPager(mTabAdapter, 0);
            }
        });
    }

    public void changeTab(int index) {
        indicatorViewPager.setCurrentItem(index, false);
        if (index == 0) {
            selectTabPager(mTabAdapter, index);
        } else {
            selectTabPager1(mTabAdapter, index);
        }
    }

    private void selectTabPager(TabAdapter mTabAdapter, int currentItem) {
        con_forum.setVisibility(View.GONE);
        HashMap<Integer, View> map = mTabAdapter.getTabViewMap();
        if (map != null && !map.isEmpty()) {
            for (Integer key : mTabAdapter.getTabViewMap().keySet()) {
                View itemView = mTabAdapter.getTabViewMap().get(key);
                //                GradientDrawable gd = new GradientDrawable();
                //                gd.setCornerRadius(6 * density);
                //                gd.setColor(ContextCompat.getColor(_mActivity, key == currentItem ? R.color.color_0052ef : R.color.color_f2f2f2));
                //                mTvSubName.setBackground(gd);
                View mViewTabLine = itemView.findViewById(R.id.view_tab_line);
                if (mViewTabLine != null) {
                    mViewTabLine.setVisibility(key == currentItem ? View.VISIBLE : View.INVISIBLE);
                }
                mViewTabLine.setVisibility(View.GONE);
                TextView mTvName = itemView.findViewById(R.id.tv_tab_name);
                mTvName.setTypeface(null, key == currentItem ? Typeface.BOLD : Typeface.NORMAL);
                mTvName.setTextSize(16);
                mTvName.setTextColor(Color.parseColor("#FFFFFF"));
                TextView mTvTabCount = itemView.findViewById(R.id.tv_tab_count);
                mTvTabCount.setTextColor(Color.parseColor("#FFFFFF"));

            }
        }
        mLlGameTitle.setBackgroundColor(Color.parseColor(selectBgColor));
        mIvBack.setImageResource(R.mipmap.ic_actionbar_back_white);
        mIvMore.setImageResource(R.mipmap.ic_game_detail_more_action);
    }

    private void selectTabPager1(TabAdapter mTabAdapter, int currentItem) {
        con_forum.setVisibility(View.GONE);
        HashMap<Integer, View> map = mTabAdapter.getTabViewMap();
        if (map != null && !map.isEmpty()) {
            for (Integer key : mTabAdapter.getTabViewMap().keySet()) {
                View itemView = mTabAdapter.getTabViewMap().get(key);
                //                GradientDrawable gd = new GradientDrawable();
                //                gd.setCornerRadius(6 * density);
                //                gd.setColor(ContextCompat.getColor(_mActivity, key == currentItem ? R.color.color_0052ef : R.color.color_f2f2f2));
                //                mTvSubName.setBackground(gd);
                View mViewTabLine = itemView.findViewById(R.id.view_tab_line);
                if (mViewTabLine != null) {
                    mViewTabLine.setVisibility(key == currentItem ? View.VISIBLE : View.INVISIBLE);
                }
                mViewTabLine.setVisibility(View.GONE);
                TextView mTvName = itemView.findViewById(R.id.tv_tab_name);
                if (key == currentItem && "社区".equals(mTvName.getText())) {
                    con_forum.setVisibility(View.VISIBLE);
                    commentPage = 1;
                    getCommentList();
                }
                mTvName.setTypeface(null, key == currentItem ? Typeface.BOLD : Typeface.NORMAL);
                mTvName.setTextSize(key == currentItem ? 16 : 16);
                mTvName.setTextColor(Color.parseColor(key == currentItem ? "#232323" : "#232323"));

                TextView mTvTabCount = itemView.findViewById(R.id.tv_tab_count);
                mTvTabCount.setTextColor(Color.parseColor(key == currentItem ? "#232323" : "#232323"));
            }
        }
        mLlGameTitle.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mIvBack.setImageResource(trecyclerview.com.mvvm.R.mipmap.ic_actionbar_back);
        mIvMore.setImageResource(R.mipmap.ic_game_detail_more_action_black);
    }

    public void setGameTitleColor(String color) {
        mLlGameTitle.setBackgroundColor(Color.parseColor(color));
    }

    class TabInfoVo {
        private String title;
        private String subTitle;
        private int tabCount;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public void setTabCount(int tabCount) {
            this.tabCount = tabCount;
        }
    }

    class TabAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {

        private List<TabInfoVo> mTabInfoVos;
        private List<View> mViewList;

        private HashMap<Integer, View> mTabViewMap;

        public TabAdapter(List<TabInfoVo> tabInfoVos, List<View> viewList) {
            mTabInfoVos = tabInfoVos;
            mViewList = viewList;
            mTabViewMap = new HashMap<>();
        }

        @Override
        public int getCount() {
            return mTabInfoVos == null ? 0 : mTabInfoVos.size();
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_ts_game_detail_tab, null);
            }
            TextView mTvTabName = convertView.findViewById(R.id.tv_tab_name);
            TextView mTvTabCount = convertView.findViewById(R.id.tv_tab_count);

            TabInfoVo tabInfoVo = mTabInfoVos.get(position);
            /*if (tabInfoVo.subTitle.equals("吐槽大会")){
                if (tabInfoVo.tabCount > 99){
                    SpannableStringBuilder spannableString = new SpannableStringBuilder(tabInfoVo.title + " 99+");
                    spannableString.setSpan(new AbsoluteSizeSpan(32), tabInfoVo.title.length(), spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    mTvTabName.setText(spannableString);
                }else {
                    mTvTabName.setText(tabInfoVo.title);
                }
            }else {
                mTvTabName.setText(tabInfoVo.title);
            }*/
            mTvTabName.setText(tabInfoVo.title);

            if ("工具箱".equals(tabInfoVo.title)) {
                mTvTabCount.setVisibility(View.VISIBLE);
                mTvTabCount.setText(String.valueOf(tabInfoVo.tabCount));
            } else {
                mTvTabCount.setVisibility(View.GONE);
            }

            mTabViewMap.put(position, convertView);
            return convertView;
        }

        public HashMap<Integer, View> getTabViewMap() {
            return mTabViewMap;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = mViewList.get(position);
            }
            return convertView;
        }
    }

    private BaseRecyclerAdapter mAdapter1;
    private XRecyclerView mRecyclerView1;

    private void initList1() {
        mRecyclerView1 = new XRecyclerView(_mActivity);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRecyclerView1.setLayoutParams(params);

        mRecyclerView1.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));

        RecyclerViewNoBugLinearLayoutManager layoutManager = new RecyclerViewNoBugLinearLayoutManager(_mActivity);
        mRecyclerView1.setLayoutManager(layoutManager);

        mAdapter1 = new BaseRecyclerAdapter.Builder<>()
                .bind(GameWelfareVo.class, new GameWelfareItemHolder(_mActivity))
                //.bind(GameRebateVo.class, new GameRebateItemHolder(_mActivity))
                .bind(GameActivityVo.class, new GameActivityItemHolder(_mActivity))
                .bind(GameDesVo.class, new GameDesItemHolder(_mActivity))
                .bind(GameServerListVo.class, new GameServerItemHolder(_mActivity))
                .bind(GameCardListVo.class, new GameCardItemHolder(_mActivity))
                .bind(GameLikeListVo.class, new GameLikeItemHolder(_mActivity))
                .bind(NoMoreDataVo.class, new GameNoMoreItemHolder(_mActivity))
                .bind(TryGameItemVo.DataBean.class, new GameTryItemHolder(_mActivity))
                .bind(GameShortCommentVo.class, new NewGameShortCommentItemHolder(_mActivity))
                .bind(GameRefundVo.class, new GameRefundItemHolder(_mActivity))
                .bind(GameInfoVo.class, new GameInfoItemHolder(_mActivity))
                .bind(GameRebateVo.class, new NewGameRebateItemHolder(_mActivity))
                .bind(GameRecommendListVo.class, new NewCustomRecommendItemHolder(_mActivity))
                .bind(GameInfoVo.VendorInfo.class, new GameManufacturerInformationItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);

        mRecyclerView1.setAdapter(mAdapter1);
        mRecyclerView1.setPullRefreshEnabled(false);

        mRecyclerView1.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        /*if (game_type == 1){
            View mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_ts_game_detail_detail_head, null);
            mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mHeaderView.setOnClickListener(v -> {
                if (checkLogin()) {
                    startFragment(GameReportFragment.newInstance(gameInfoVo.getAllGamename(), gameInfoVo.getGameid()));
                }
            });
            mRecyclerView1.addHeaderView(mHeaderView);
        }*/

        mRecyclerView1.addOnScrollListener(mOnScrollListener);
    }


    private BaseRecyclerAdapter mAdapter2;
    private XRecyclerView mRecyclerView2;

    private void initList2() {
        mRecyclerView2 = new XRecyclerView(_mActivity);
        mRecyclerView2.setBackgroundColor(Color.parseColor("#ffffff"));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRecyclerView2.setLayoutParams(params);
        RecyclerViewNoBugLinearLayoutManager layoutManager = new RecyclerViewNoBugLinearLayoutManager(_mActivity);
        mRecyclerView2.setLayoutManager(layoutManager);
        mRecyclerView2.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));
        ForumListItemHolder forumListItemHolder = new ForumListItemHolder(_mActivity);
        mRecyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 处理 RecyclerView 停止滑动的逻辑
                    boolean isAtTop = isRecyclerViewAtTop(mRecyclerView2);
                    if (isAtTop) {
                        // 若在顶端，执行相应操作
                        findViewById(R.id.iv_to_top).setVisibility(View.GONE);
                    } else {
                        findViewById(R.id.iv_to_top).setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        EmptyItemHolder2 emptyItemHolder1 = new EmptyItemHolder2(_mActivity);
        emptyItemHolder1.setMClick(() -> {
            if (checkLogin()) {
                if (UserInfoModel.getInstance().isSetCertification()) {
                    start(ForumPostFragment.newInstance(gameid + "", gameInfoVo.getGameicon(), gameInfoVo.getAllGamename()));
                } else {
                    startFragment(CertificationFragment.newInstance());
                    //ToastT.success("请先实名后发帖");
                    Toaster.show("请先实名后发帖");
                }
            }
        });
        mAdapter2 = new BaseRecyclerAdapter.Builder<>()
                .bind(EmptyDataVo1.class, emptyItemHolder1)
                .bind(ForumListVo.DataBean.class, forumListItemHolder)
                .bind(NoMoreDataVo.class, new GameNoMoreBigItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
        forumListItemHolder.setClickInterface(new ForumListItemHolder.OnClickInterface() {
            @Override
            public void onReport(String tid, String name) {
                //举报
                showReport(tid, name);
            }

            @Override
            public void onLike(String tid, ForumListVo.DataBean bean) {
                //点赞
                forumReplyTopLike(tid, bean);
            }
        });
        mRecyclerView2.setAdapter(mAdapter2);
        mRecyclerView2.setPullRefreshEnabled(false);

        View mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_ts_game_detail_qa, null);
        mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mAdapter2.setOnItemClickListener((v, position, data) -> {
            if (data instanceof ForumListVo.DataBean) {
                int tid = ((ForumListVo.DataBean) data).getTid();
                //特征, 1:图文分开; 2:图文并茂
                if (((ForumListVo.DataBean) data).getFeature() == 1) {
                    startFragment(ForumDetailFragment.newInstance(tid + ""));
                } else if (((ForumListVo.DataBean) data).getFeature() == 2) {
                    startFragment(ForumLongDetailFragment.newInstance(tid + ""));
                }
//                start(ForumLongDetailFragment.newInstance(tid + ""));
            }
        });
        bindCommentQaLayoutView(mHeaderView);
        mRecyclerView2.addHeaderView(mHeaderView);
        mRecyclerView2.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                commentPage = 1;
            }

            @Override
            public void onLoadMore() {
                if (commentPage < 0) {
                    return;
                }
                commentPage++;
                getCommentList();
            }
        });

        mRecyclerView2.addOnScrollListener(mOnScrollListener);
    }

    private boolean isRecyclerViewAtTop(RecyclerView recyclerView) {
        //判断是否在顶端
        if (recyclerView.getLayoutManager() instanceof RecyclerViewNoBugLinearLayoutManager) {
            RecyclerViewNoBugLinearLayoutManager layoutManager = (RecyclerViewNoBugLinearLayoutManager) recyclerView.getLayoutManager();
            // 获取第一个可见项的位置
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
            if (firstVisibleItemPosition == 1) {
                // 获取第一个可见视图
                RecyclerView.ViewHolder firstViewHolder = recyclerView.findViewHolderForAdapterPosition(0);
                if (firstViewHolder != null) {
                    // 判断第一个可见视图的顶部是否与 RecyclerView 的顶部对齐
                    return firstViewHolder.itemView.getTop() == recyclerView.getPaddingTop();
                }
            }
        }
        return false;
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
        spannable.setSpan(clickableSpan, 2, prefix.length() + 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_title.setText(spannable);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio5) {
                et_other.setVisibility(View.VISIBLE);
            } else {
                et_other.setVisibility(View.GONE);
            }
        });
        tipsDialog.findViewById(R.id.tv_save).setOnClickListener(view -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId != -1) {
                switch (selectedId) {
                    case R.id.radio1:
                        commit[0] = "有垃圾、广告或拉人信息";
                        break;
                    case R.id.radio2:
                        commit[0] = "有政治敏感、暴力色情信息";
                        break;
                    case R.id.radio3:
                        commit[0] = "辱骂、歧视、恶意引战";
                        break;
                    case R.id.radio4:
                        commit[0] = "涉及抄袭或侵权";
                        break;
                    case R.id.radio5:
                        commit[0] = et_other.getText().toString();
                        break;
                }
                if (commit[0].isEmpty()) {
                    //ToastT.warning("请填写其他原因后提交");
                    Toaster.show("请填写其他原因后提交");
                } else {
                    reportCommit(tid, commit[0]);
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
        if (mViewModel != null) {
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

    RecyclerView list2_category;
    RecyclerView list2_sticky;
    TextView tv_list2_sort;
    Banner list2_banner;
    private BaseRecyclerAdapter categoryAdapter;
    private BaseRecyclerAdapter stickyAdapter;
    int cate_id = -1;
    String order_type = "default";//排序方式：default：默认; reply:回复排序; release:发布排序
    List<ForumCategoryVo.DataBean> categoryList = new ArrayList<>();

    private void bindCommentQaLayoutView(View mHeaderView) {
        list2_banner = mHeaderView.findViewById(R.id.list2_banner);
        ViewGroup.LayoutParams layoutParams = list2_banner.getLayoutParams();
        layoutParams.width = (ScreenUtil.getScreenWidth(_mActivity) - ScreenUtil.dp2px(_mActivity, 40));
        layoutParams.height = (ScreenUtil.getScreenWidth(_mActivity) - ScreenUtil.dp2px(_mActivity, 40)) * 152 / 670;
        list2_category = mHeaderView.findViewById(R.id.list2_category);
        tv_list2_sort = mHeaderView.findViewById(R.id.tv_list2_sort);
        list2_sticky = mHeaderView.findViewById(R.id.list2_sticky);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        list2_category.setLayoutManager(linearLayoutManager);
        list2_sticky.setLayoutManager(new LinearLayoutManager(_mActivity));
        categoryAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(ForumCategoryVo.DataBean.class, new CategoryDetailItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);

        stickyAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(ForumListVo.DataBean.class, new StickyDetailItemHolder(_mActivity))
                .build().setTag(R.id.tag_fragment, _mActivity)
                .setTag(R.id.tag_fragment, this);

        list2_category.setAdapter(categoryAdapter);
        list2_sticky.setAdapter(stickyAdapter);
        categoryAdapter.setOnItemClickListener((v, position, data) -> {
            for (ForumCategoryVo.DataBean dataBean : categoryList) {
                dataBean.setClick(false);
            }
            cate_id = categoryList.get(position).getCate_id();
            categoryList.get(position).setClick(true);
            categoryAdapter.setDatas(categoryList);
            categoryAdapter.notifyDataSetChanged();
            commentPage = 1;
            getCommentList();
        });

        mHeaderView.findViewById(R.id.iv_list2_sort).setOnClickListener(v -> {
            //排序
            showPopListView(tv_list2_sort, R.layout.pop_forum_order_type_select);
        });
        tv_list2_sort.setOnClickListener(v -> {
            showPopListView(tv_list2_sort, R.layout.pop_forum_order_type_select);
        });
    }

    private TextView tv_item1;
    private TextView tv_item2;
    private TextView tv_item3;
    private int selected;

    /**
     * 指定view下方弹出
     * 方法名@：showAsDropDown(v, 0, 10)
     * resource:PopWindow 布局
     */
    private void showPopListView(View v, int resource) {
        if (resource == 0) return;
        View contentView = LayoutInflater.from(_mActivity).inflate(resource, null);
        tv_item1 = contentView.findViewById(R.id.tv_item1);
        tv_item2 = contentView.findViewById(R.id.tv_item2);
        tv_item3 = contentView.findViewById(R.id.tv_item3);

        switch (selected) {
            case 1:
                tv_item1.setTextColor(Color.parseColor("#5571FE"));
                tv_item2.setTextColor(Color.parseColor("#333333"));
                tv_item3.setTextColor(Color.parseColor("#333333"));
                break;
            case 2:
                tv_item1.setTextColor(Color.parseColor("#333333"));
                tv_item2.setTextColor(Color.parseColor("#5571FE"));
                tv_item3.setTextColor(Color.parseColor("#333333"));
                break;
            case 3:
                tv_item1.setTextColor(Color.parseColor("#333333"));
                tv_item2.setTextColor(Color.parseColor("#333333"));
                tv_item3.setTextColor(Color.parseColor("#5571FE"));
                break;

        }
        CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(_mActivity)
                .setView(contentView)
                .setFocusable(true)
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) //显示大小
                .setOutsideTouchable(true)
                .create();
//        popWindow.setBackgroundAlpha(0.7f);
        tv_item1.setOnClickListener(view -> {
            commentPage = 1;
            selected = 1;
            order_type = "default";
            tv_list2_sort.setText("默认排序");
            popWindow.dissmiss();
            getCommentList();
        });
        tv_item2.setOnClickListener(view -> {
            commentPage = 1;
            selected = 2;
            order_type = "reply";
            tv_list2_sort.setText("回复排序");
            popWindow.dissmiss();
            getCommentList();
        });
        tv_item3.setOnClickListener(view -> {
            commentPage = 1;
            selected = 3;
            order_type = "release";
            tv_list2_sort.setText("发布排序");
            popWindow.dissmiss();
            getCommentList();
        });

        popWindow.showAsDropDown(v, (-v.getWidth() / 2) + 50, 0);//指定view正下方
    }

    public void toShortCommentDetail() {
        if (gameInfoVo.isShow_short_comment_list()) {
//            startFragment(ShortCommentDetailFragment.newInstance(gameid, gameInfoVo.getGamename()));
        } else {
            if (mTabAdapter.getCount() > 1) {
                indicatorViewPager.setCurrentItem(1, false);
                selectTabPager1(mTabAdapter, 1);
            }
        }
    }

    @Override
    public void pop() {
        if (isFromLocalGame) {
            startMain();
        } else {
            super.pop();
        }
    }

    private void startMain() {
        Intent intent = new Intent(_mActivity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("detailJump", "detailJump");
        ActivityUtils.startActivity(intent);
    }

    private int lastCommentTypeCheckId = 0;

    private void addCommentTypeButton(List<CommentTypeListVo.DataBean> list) {

    }

    private BaseRecyclerAdapter mAdapter3;
    private XRecyclerView mRecyclerView3;

    private void initList3() {
        mRecyclerView3 = new XRecyclerView(_mActivity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRecyclerView3.setLayoutParams(params);
        mRecyclerView3.setBackgroundColor(Color.parseColor("#F2F2F2"));

        mRecyclerView3.setLayoutManager(new GridLayoutManager(_mActivity, 2));
        mAdapter3 = new BaseRecyclerAdapter.Builder()
                .bind(GameInfoVo.ServerListBean.class, new NewGameServerItemHolder(_mActivity))
                .bind(NoMoreDataVo.class, new GameNoMoreItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
        mRecyclerView3.setAdapter(mAdapter3);
        mRecyclerView3.addHeaderView(LayoutInflater.from(_mActivity).inflate(R.layout.layout_game_detail_server_head, null));

        mRecyclerView3.setPullRefreshEnabled(false);
        mRecyclerView3.addOnScrollListener(mOnScrollListener);
    }

    private BaseRecyclerAdapter mAdapter4;
    private XRecyclerView mRecyclerView4;

    private void initList4() {
        mRecyclerView4 = new XRecyclerView(_mActivity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRecyclerView4.setLayoutParams(params);
        mRecyclerView4.setBackgroundColor(Color.parseColor("#F2F2F2"));

        RecyclerViewNoBugLinearLayoutManager layoutManager = new RecyclerViewNoBugLinearLayoutManager(_mActivity);
        mRecyclerView4.setLayoutManager(layoutManager);

        mAdapter4 = new BaseRecyclerAdapter.Builder<>()
                .bind(TradeGoodInfoVo1.class, new TradeItemHolder1(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(NoMoreDataVo.class, new GameNoMoreItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);


        mRecyclerView4.setAdapter(mAdapter4);

        mRecyclerView4.setPullRefreshEnabled(false);
        mRecyclerView4.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                transactionPage = 1;
            }

            @Override
            public void onLoadMore() {
                if (transactionPage < 0) {
                    return;
                }
                transactionPage++;
                getTransactionList();
            }
        });

        mAdapter4.setOnItemClickListener((v, position, data) -> {
            if (data != null && data instanceof TradeGoodInfoVo1) {
                TradeGoodInfoVo1 tradeGoodInfoVo = (TradeGoodInfoVo1) data;
                startFragment(TransactionGoodDetailFragment.newInstance(tradeGoodInfoVo.getGid(), tradeGoodInfoVo.getGameid()));
            }
        });
        mRecyclerView4.addOnScrollListener(mOnScrollListener);
    }

    private BaseRecyclerAdapter mAdapter6;
    private XRecyclerView mRecyclerView6;

    private void initList6() {
        mRecyclerView6 = new XRecyclerView(_mActivity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRecyclerView6.setLayoutParams(params);
        mRecyclerView6.setBackgroundColor(Color.parseColor("#F8F8F8"));

        RecyclerViewNoBugLinearLayoutManager layoutManager = new RecyclerViewNoBugLinearLayoutManager(_mActivity);
        mRecyclerView6.setLayoutManager(layoutManager);
        mAdapter6 = new BaseRecyclerAdapter.Builder<>()
                .bind(GameToolBoxListVo.class, new GameToolBoxItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(NoMoreDataVo.class, new GameNoMoreItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
        mRecyclerView6.setAdapter(mAdapter6);

        mRecyclerView6.setPullRefreshEnabled(false);

        mRecyclerView6.addOnScrollListener(mOnScrollListener);
    }

    private RecyclerView mRecyclerView2_0;
    private BaseRecyclerAdapter mAdapter2_0;

    private void initList2_0() {
        mRecyclerView2_0 = new RecyclerView(_mActivity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRecyclerView2_0.setLayoutParams(params);

        mRecyclerView2_0.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));

        RecyclerViewNoBugLinearLayoutManager layoutManager = new RecyclerViewNoBugLinearLayoutManager(_mActivity);
        mRecyclerView2_0.setLayoutManager(layoutManager);
        mAdapter2_0 = new BaseRecyclerAdapter.Builder()
                .bind(GameInfoVo.CardlistBean.class, new GameGiftItemHolder(_mActivity))
                .bind(GameCardListVo.class, new NewGameGiftItemHolder(_mActivity))
                .bind(NewGameCouponItemVo.class, new NewGameCouponItemHolder(_mActivity))
                .bind(GameInfoVo.class, new NewGameActiviItemHolder(_mActivity))
                .bind(GameRebateVo.class, new NewGameRebateItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);

        mRecyclerView2_0.setAdapter(mAdapter2_0);

        mRecyclerView2_0.addOnScrollListener(mOnScrollListener);
    }

    public void showMyGift() {
        //查看我的礼包
        if (checkLogin()) {
            startFragment(new MyCardListFragment());
        }
    }

    public void showGiftDetail(GameInfoVo.CardlistBean cardlistBean) {
        CustomDialog cardDetailDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_card_detail, null),
                ScreenUtils.getScreenWidth(_mActivity),
                WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

        TextView tvGiftContent = cardDetailDialog.findViewById(R.id.tv_gift_content);
        TextView tvGiftUsage = cardDetailDialog.findViewById(R.id.tv_gift_usage);
        TextView tvGiftTime = cardDetailDialog.findViewById(R.id.tv_gift_time);
        TextView mTvGiftRequirement = cardDetailDialog.findViewById(R.id.tv_gift_requirement);
        LinearLayout mLlGiftRequirement = cardDetailDialog.findViewById(R.id.ll_gift_requirement);

        TextView tvClose2 = cardDetailDialog.findViewById(R.id.tv_close);
        tvClose2.setOnClickListener((v) -> {
            if (cardDetailDialog != null && cardDetailDialog.isShowing()) {
                cardDetailDialog.dismiss();
            }
        });

        tvGiftContent.setText(cardlistBean.getCardcontent());
        if (!TextUtils.isEmpty(cardlistBean.getCardusage())) {
            tvGiftUsage.setText(cardlistBean.getCardusage());
        } else {
            tvGiftUsage.setText("请在游戏内兑换使用");
        }
        if (cardlistBean.isRechargeGift()) {
            mLlGiftRequirement.setVisibility(View.VISIBLE);
            mTvGiftRequirement.setText(cardlistBean.getGiftRequirement());
        } else {
            mLlGiftRequirement.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(cardlistBean.getYouxiaoqi())) {
            tvGiftTime.setText(cardlistBean.getYouxiaoqi());
        } else {
            tvGiftTime.setText("无限制");
        }

        cardDetailDialog.show();
    }

    private void bindGameTitleViews() {
        //mIvDownloadManager = findViewById(R.id.iv_download_manager);
        //mIvShare = findViewById(R.id.iv_share);
        /*int topPadding = (int) (48 * density + ScreenUtil.getStatusBarHeight(_mActivity));
        mLlLayoutCollapsing.setPadding(0, topPadding, 0, 0);

        //分享图标
        if (mIvShare != null) {
            mIvShare.setVisibility(AppConfig.isGonghuiChannel() ? View.GONE : View.VISIBLE);
            mIvShare.setOnClickListener(v -> {
                //分享
                if (checkLogin()) {
                    if (InviteConfig.isJustShare()) {
                        if (mShareHelper != null && inviteDataInfoVo != null) {
                            mShareHelper.showShareInviteFriend(inviteDataInfoVo);
                        } else {
                            if (mViewModel != null) {
                                loading();
                                mViewModel.getShareInviteData((String) getStateEventKey());
                            }
                        }
                    } else {
                        start(new InviteFriendFragment());
                    }
                }
            });
        }
        //下载管理图标
        if (mIvDownloadManager != null) {
            mIvDownloadManager.setOnClickListener(v -> {
                if (BuildConfig.IS_DOWNLOAD_GAME_FIRST || checkLogin()) {
                    start(new GameDownloadManagerFragment());
                }
            });
        }*/
    }

    private final String SP_GAME_DETAIL_WRITE_COMMENT_TIPS = "SP_GAME_DETAIL_WRITE_COMMENT_TIPS";

    private void setCommentTipsView() {
        SPUtils spUtils = new SPUtils(_mActivity, Constants.SP_COMMON_NAME);
        boolean writeCommentTips = spUtils.getBoolean(SP_GAME_DETAIL_WRITE_COMMENT_TIPS, false);
        boolean isOnlineGame = false;
        if (gameInfoVo != null) {
            isOnlineGame = gameInfoVo.isGameAppointment();
        }
        mFlWriteCommentTips.setVisibility(writeCommentTips && !isOnlineGame && !AppConfig.isHideCommunity() ? View.VISIBLE : View.GONE);
        mFlWriteCommentTips.setOnClickListener(v -> {
            spUtils.putBoolean(SP_GAME_DETAIL_WRITE_COMMENT_TIPS, true);
            mFlWriteCommentTips.setVisibility(View.GONE);
        });
    }

    private LinearLayout mLlGameAppointment;
    private TextView mTvAppointmentMessage;
    private Button mBtnGameAppointment;
    private LinearLayout mLlGameDownload;
    private TextView mTvGameDetailFavorite;
    private FrameLayout mFlDownload;
    private ProgressBar mDownloadProgress;
    private TextView mTvDownload;
    private ImageView mIvDownload;
    private TextView mTvWriteComment;
    private FrameLayout mFlAppointmentDownload;
    private TextView mTvAppointmentDownload;
    private View fl_cloud_layout;

    private void bindGameDownloadViews() {
        fl_cloud_layout = findViewById(R.id.fl_cloud_layout);
        mLlGameAppointment = findViewById(R.id.ll_game_appointment);
        mTvAppointmentMessage = findViewById(R.id.tv_appointment_message);
        mBtnGameAppointment = findViewById(R.id.btn_game_appointment);
        mTvWriteComment = findViewById(R.id.tv_write_comment);
        mLlGameDownload = findViewById(R.id.ll_game_download);
        mTvGameDetailFavorite = findViewById(R.id.tv_game_detail_favorite);
        mTvWriteComment.setVisibility(AppConfig.isHideCommunity() ? View.GONE : View.VISIBLE);

        mFlDownload = findViewById(R.id.fl_download);
        mDownloadProgress = findViewById(R.id.download_progress);
        mIvDownload = findViewById(R.id.iv_download);
        mTvDownload = findViewById(R.id.tv_download);

        mFlAppointmentDownload = findViewById(R.id.fl_appointment_download);
        mTvAppointmentDownload = findViewById(R.id.tv_appointment_download);

        mLlGameAppointment.setVisibility(View.GONE);
        mLlGameDownload.setVisibility(View.GONE);

        if (mFlDownload != null && mDownloadProgress != null) {
            mFlDownload.setOnClickListener(this);
            mDownloadProgress.setOnClickListener(this);
        }

        mFlAppointmentDownload.setOnClickListener(this);
        mTvAppointmentDownload.setOnClickListener(this);


        if (mTvDownload != null) {
            if (game_type == 3 || game_type == 4) {
                mIvDownload.setVisibility(View.GONE);
                mTvDownload.setText("开始玩");
            } else {
                setGameTag();
            }
        }

        mTvGameDetailFavorite.setOnClickListener(view -> {
            //收藏游戏
            if (checkLogin()) {
                if (gameInfoVo != null) {
                    if (isGameFavorite) {
                        setGameUnFavorite(gameid);
                    } else {
                        setGameFavorite(true, gameid, 1);
                    }
                }
            }
        });
        setGameViewFavorite(isGameFavorite);

        mTvWriteComment.setOnClickListener(view -> {
            //2019.03.06更新为点评
            //点评
            if (checkLogin()) {
                if (UserInfoModel.getInstance().isSetCertification()) {
                    start(ForumPostFragment.newInstance(gameid + "", gameInfoVo.getGameicon(), gameInfoVo.getAllGamename()));
                } else {
                    startFragment(CertificationFragment.newInstance());
                    //ToastT.success("请先实名后发帖");
                    Toaster.show("请先实名后发帖");
                }
         /*       if (gameInfoVo != null) {
                    startForResult(WriteCommentsFragment.newInstance(String.valueOf(gameid), gameInfoVo.getGamename()), ACTION_WRITE_COMMENT);
                }*/
            }
        });
    }

    /*@Override
    public void setInviteData(InviteDataVo.DataBean data) {
        super.setInviteData(data);
    }

    @Override
    public void onShareSuccess() {
        super.onShareSuccess();
        if (mViewModel != null) {
            mViewModel.addInviteData(1);
        }
    }*/

    @Override
    public void onEvent(EventCenter eventCenter) {
        super.onEvent(eventCenter);
        if (eventCenter.getEventCode() == EventConfig.ACTION_ADD_DOWNLOAD_EVENT_CODE) {

        } else if (eventCenter.getEventCode() == EventConfig.ACTION_READ_DOWNLOAD_EVENT_CODE) {

        }

        if (eventCenter.getEventCode() == EventConfig.APP_INSTALL_EVENT_CODE) {
            setGameDownloadStatus();
        }
        if (eventCenter.getEventCode() == EventConfig.FORUM_UPDATE_CODE) {
            //更新社区
            commentPage = 1;
            getCommentList();
        }
    }

    boolean isGameFavorite = false;

    private void setGameViewFavorite(boolean isFavorite) {
        if (mTvGameDetailFavorite != null) {
            isGameFavorite = isFavorite;
            Drawable drawable = getResources().getDrawable(isFavorite ? R.mipmap.ic_audit_transaction_good_detail_2 : R.mipmap.ic_audit_transaction_good_detail_1);
            mTvGameDetailFavorite.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            mTvGameDetailFavorite.setText(isFavorite ? "已收藏" : "收藏");
        }
    }

    /**
     * 即将下架
     */
    private void setGameTag() {
        if (mTvDownload != null) {
            //mTvDownload.setText("立即下载");
            if (gameInfoVo != null && gameInfoVo.getGame_type() != 3) {
                SpannableStringBuilder spanBuilder = new SpannableStringBuilder("立即下载(" + gameInfoVo.getClient_size() + "M)");
                spanBuilder.setSpan(new RelativeSizeSpan(0.6f), 4, spanBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTvDownload.setText(spanBuilder);
            } else {
                mTvDownload.setText("立即下载");
            }
            mTvAppointmentDownload.setText("预下载");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_download:
            case R.id.download_progress:
            case R.id.fl_appointment_download:
            case R.id.tv_appointment_download:
                /*SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME + "_FIRST_DOWNLOAD");
                boolean easyPlay = spUtils.getBoolean("FIRST_DOWNLOAD",false);
                if (!easyPlay){
                    doFirstDownload();
                }else {
                    clickDownload();
                }*/
                clickDownload();
                break;
            default:
                break;
        }
    }

    private void clickDownload() {
        if (gameInfoVo == null) {
            return;
        }
        GameInfoVo.DownloadControl download_control = gameInfoVo.getDownload_control();
        if (download_control != null && download_control.getDownload_control() == 1) {
            showExclusiveBenefitDialog(download_control);
            return;
        }
        //下载游戏
        Logs.e("游戏下载地址为：" + gameInfoVo.getGame_download_url());
        if (game_type == 3) {
            //H5游戏开始玩
            if (checkLogin()) {
                BrowserGameActivity.newInstance(_mActivity, gameInfoVo.getGame_download_url(), true, gameInfoVo.getGamename(), String.valueOf(gameid));
            }
        }else if(game_type == 4){
            if (checkLogin()) {
                BrowserGameActivity.newInstance(_mActivity, gameInfoVo.getGame_download_url(), true, gameInfoVo.getGamename(), String.valueOf(gameid));
                //GameLauncher.INSTANCE.startLocalGame(_mActivity, gameInfoVo.getGame_download_url());
            }
        } else {
            //下载游戏
            if (BuildConfig.IS_DOWNLOAD_GAME_FIRST || checkLogin())
                download();
        }
        if (UserInfoModel.getInstance().isLogined() && gameInfoVo.getIs_favorite() == 0) {
            setGameFavorite(false, gameInfoVo.getGameid(), 2);
        }
    }

    private void downLoadCheck(Progress progress, File targetFile) {
        GameExtraVo extra1 = (GameExtraVo) progress.extra1;
        GameDownloadTimeExtraVo gameDownloadTimeExtraVo = (GameDownloadTimeExtraVo) progress.extra2;

        if (gameDownloadTimeExtraVo.getType() == 2) {//判断是否由本地写入渠道信息
            if (DownloadUtils.checkCompletenessForGameExtraVo(true, targetFile, extra1)) {
                AppUtil.install(_mActivity, targetFile);
            } else {
                //ToastT.error("游戏安装文件损坏，请重新下载");
                Toaster.show("游戏安装文件损坏，请重新下载");
                deleteItem(progress);
            }
        } else {
            if (DownloadUtils.checkCompletenessForGameExtraVo(false, targetFile, extra1)) {
                AppUtil.install(_mActivity, targetFile);
            } else {
                //ToastT.error("游戏安装文件损坏，请重新下载");
                Toaster.show("游戏安装文件损坏，请重新下载");
                deleteItem(progress);
            }
        }
    }

    public void deleteItem(Progress progress) {
        String tag = progress.tag;
        String path = progress.filePath;
        DownloadTask task = OkDownload.getInstance().getTask(tag);
        if (task != null) {
            task.unRegister(tag);
            task.remove(true);
        }
        DownloadManager.getInstance().delete(tag);
        OkDownload.getInstance().removeTask(tag);
        GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;
        if (gameInfoVo != null) {
            DownloadNotifyManager.getInstance().cancelNotify(gameInfoVo.getGameid());
        }
        FileUtils.deleteFile(path);
        setGameDownloadStatus();
    }

    private void showFristDownloadDialog(Progress progress, File targetFile) {
        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME + "_FIRST_DOWNLOAD");
        if (Setting.SHOW_TIPS == 1) {
            boolean firstDownloadTips = spUtils.getBoolean("FIRST_DOWNLOAD_TIPS", false);
            if (firstDownloadTips) {
                downLoadCheck(progress, targetFile);
                //AppUtil.install(_mActivity, targetFile);
            } else {
                doFirstDownload1(targetFile);
            }
        } else {
            boolean firstDownload = spUtils.getBoolean("FIRST_DOWNLOAD", false);
            if (firstDownload) {
                downLoadCheck(progress, targetFile);
                //AppUtil.install(_mActivity, targetFile);
            } else {
                doFirstDownload(targetFile);
            }

        }
    }

    private void doFirstDownload(File targetFile) {
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_download_default, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        TextView mTvContent = tipsDialog.findViewById(R.id.tv_content);
        SpannableString spannableString = new SpannableString("个别机型受系统影响，可能会出现：\n1.安装时若显示未经检验等提示，请勾选了解点击“继续安装”即可\n2.游戏可能会被手机商店提示更新成普通版，请勿更新以免无法享受游戏福利");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#4E76FF")), 41, 45, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 41, 45, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF450A")), 70, 74, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 70, 74, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvContent.setText(spannableString);
        tipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME + "_FIRST_DOWNLOAD");
            spUtils.putBoolean("FIRST_DOWNLOAD", true);
            AppUtil.install(_mActivity, targetFile);
        });
        tipsDialog.show();
    }

    private boolean isCheck = false;

    private void doFirstDownload1(File targetFile) {
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_download_default_1, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        isCheck = false;
        TextView mTvCheck = tipsDialog.findViewById(R.id.tv_check);
        mTvCheck.setOnClickListener(view -> {
            isCheck = !isCheck;
            if (isCheck) {
                mTvCheck.setCompoundDrawablesWithIntrinsicBounds(_mActivity.getResources().getDrawable(R.mipmap.ic_login_checked), null, null, null);
            } else {
                mTvCheck.setCompoundDrawablesWithIntrinsicBounds(_mActivity.getResources().getDrawable(R.mipmap.ic_login_un_check), null, null, null);
            }
        });
        tipsDialog.findViewById(R.id.tv_next).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            if (isCheck) {
                SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME + "_FIRST_DOWNLOAD");
                spUtils.putBoolean("FIRST_DOWNLOAD_TIPS", isCheck);
            }
            AppUtil.install(_mActivity, targetFile);
        });
        tipsDialog.show();
    }

    private void setGameDownloadStatus() {
        if (gameInfoVo == null) {
            return;
        }
        /*if (!UserInfoModel.getInstance().isLogined()) {
            return;
        }*/
        Progress progress = DownloadManager.getInstance().get(gameInfoVo.getGameDownloadTag());
        mIvDownload.setVisibility(View.GONE);
        if (progress == null) {
            mDownloadProgress.setVisibility(View.GONE);
            if (game_type == 3 || game_type == 4) {
                mTvDownload.setText("开始玩");
            } else {
                //mTvDownload.setText("立即下载");
                File targetFile = null;
                try {
                    targetFile = new File(progress.filePath);
                } catch (Exception e) {
                }
                //安装
                if (targetFile != null && targetFile.exists()) {
                    mTvDownload.setText("安装");
                    mTvAppointmentDownload.setText("安装");
                } else {
                    if (gameInfoVo != null && gameInfoVo.getGame_type() != 3) {
                        SpannableStringBuilder spanBuilder = new SpannableStringBuilder("立即下载(" + gameInfoVo.getClient_size() + "M)");
                        spanBuilder.setSpan(new RelativeSizeSpan(0.6f), 4, spanBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mTvDownload.setText(spanBuilder);
                    } else {
                        mTvDownload.setText("立即下载");
                    }
                    mTvAppointmentDownload.setText("预下载");
                }
            }
        } else {
            refresh(progress);
            DownloadTask task = OkDownload.restore(progress);
            if (task != null) {
                task.register(downloadListener);
            }
        }
        if (gameInfoVo != null && !TextUtils.isEmpty(gameInfoVo.getClient_package_name())) {
            if (AppsUtils.isInstallApp(_mActivity, gameInfoVo.getClient_package_name())) {
                mTvDownload.setText("打开");
                mTvAppointmentDownload.setText("打开");
            }
        }

        GameInfoVo.DownloadControl download_control = gameInfoVo.getDownload_control();
        if (download_control != null) {
            if (download_control.getDownload_control() == 1) {
                if (!TextUtils.isEmpty(download_control.getBtn_text())) {
                    mTvDownload.setText(download_control.getBtn_text());
                } else {
                    mTvDownload.setText("内部福利领取");
                }
            }
        }
    }

    /**
     * 下载游戏
     */
    private void download() {
        if (gameInfoVo.getIs_deny() == 1) {
            //ToastT.warning(_mActivity, "(T ^ T) 亲亲，此游戏暂不提供下载服务呢！");
            Toaster.show("此游戏暂不提供下载服务");
            return;
        }

        if (gameInfoVo.isIOSGameOnly()) {
            //ToastT.warning(_mActivity, "此为苹果游戏，请使用苹果手机下载哦！");
            Toaster.show("请使用苹果手机下载");
            return;
        }

        String downloadErrorInfo = gameInfoVo.getGame_download_error();
        if (!TextUtils.isEmpty(downloadErrorInfo)) {
            //ToastT.warning(_mActivity, downloadErrorInfo);
            Toaster.show(downloadErrorInfo);
            return;
        }

        if (gameInfoVo != null && !TextUtils.isEmpty(gameInfoVo.getClient_package_name())) {
            if (AppsUtils.isInstallApp(_mActivity, gameInfoVo.getClient_package_name())) {
                AppUtil.open(_mActivity, gameInfoVo.getClient_package_name());
                return;
            }
        }

        Progress progress = DownloadManager.getInstance().get(gameInfoVo.getGameDownloadTag());
        if (progress == null) {
            checkWiFiType(() -> {
                if (!TextUtils.isEmpty(gameInfoVo.getChannel())) {
                    getDownloadUrl();
                } else {
                    gameDownloadLog(0, 1, 0);
                }
            });
        } else {
            DownloadTask task = OkDownload.restore(progress);
            task.register(downloadListener);
            if (progress.status == Progress.NONE || progress.status == Progress.ERROR || progress.status == Progress.PAUSE || progress.status == Progress.WAITING) {
                checkWiFiType(() -> {
                    if (task != null) {
                        task.start();
                    }
                });
            } else if (progress.status == Progress.LOADING) {
                //暂停
                if (task != null) {
                    task.pause();
                }
            } else if (progress.status == Progress.FINISH) {
                //下载完成
                GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;
                String packageName = gameInfoVo == null ? "" : gameInfoVo.getClient_package_name();
                //打开
                if (!TextUtils.isEmpty(packageName) && AppUtil.isAppAvailable(_mActivity, packageName)) {
                    AppUtil.open(_mActivity, packageName);
                } else {
                    File targetFile = new File(progress.filePath);
                    //安装
                    if (targetFile.exists()) {
                        if (!EasyFloat.isShow("float_download")) {
                            //AppUtil.install(_mActivity, targetFile);
                            showFristDownloadDialog(progress, targetFile);
                        }
                    } else {
                        checkWiFiType(() -> {
                            //重新下载
                            checkWiFiType(() -> {
                                if (task != null) {
                                    task.start();
                                }
                            });
                        });
                    }
                }
            }
            refresh(progress);
        }
    }

    private void getDownloadUrl() {
        OkGo.<String>post("https://appapi-ns1.xiaodianyouxi.com/index.php/download/game")
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.NO_CACHE)
                .params("params", gameInfoVo.getChannel())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("aaa", response.body());
                        try {
                            Gson gson = new Gson();
                            Type type = new TypeToken<GameDownloadUrlVo>() {
                            }.getType();

                            GameDownloadUrlVo gameDownloadUrlVo = gson.fromJson(response.body(), type);
                            if (gameDownloadUrlVo != null) {
                                if (gameDownloadUrlVo.isStateOK()) {
                                    if (gameDownloadUrlVo.getData() != null) {
                                        gameInfoVo.setGame_download_url(gameDownloadUrlVo.getData().getApk_url());
                                        //gameInfoVo.setChannel(gameDownloadUrlVo.getData().getChannel());
                                        gameDownloadLog(0, 2, 0);
                                    }
                                } else {
                                    //ToastT.error(gameDownloadUrlVo.getMsg());
                                    Toaster.show(gameDownloadUrlVo.getMsg());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    private void refresh(Progress progress) {
        if (progress == null) {
            return;
        }
        GameInfoVo.DownloadControl download_control = gameInfoVo.getDownload_control();
        if (download_control != null && download_control.getDownload_control() == 1) {
            return;
        }
        mIvDownload.setVisibility(View.GONE);
        if (progress.status == Progress.LOADING || progress.status == Progress.WAITING) {
            float fProgress = progress.fraction;
            mDownloadProgress.setVisibility(View.VISIBLE);
            mDownloadProgress.setMax(100);
            mDownloadProgress.setProgress((int) (fProgress * 100));
            DecimalFormat df = new DecimalFormat("#0.00");
            if (fProgress * 100 == 0.0) {
                mTvDownload.setText("正在为您准备资源");
                mTvAppointmentDownload.setText("正在准备资源");
            } else {
                mTvDownload.setText("已下载" + df.format(fProgress * 100) + "%");
                mTvAppointmentDownload.setText("已下载" + df.format(fProgress * 100) + "%");
            }
        } else if (progress.status == Progress.NONE) {
            float fProgress = progress.fraction;
            mDownloadProgress.setVisibility(View.VISIBLE);
            mDownloadProgress.setMax(100);
            mDownloadProgress.setProgress((int) (fProgress * 100));
            mTvDownload.setText("继续下载");
            mTvAppointmentDownload.setText("继续下载");
        } else if (progress.status == Progress.PAUSE) {
            float fProgress = progress.fraction;
            mDownloadProgress.setVisibility(View.VISIBLE);
            mDownloadProgress.setMax(100);
            mDownloadProgress.setProgress((int) (fProgress * 100));
            mTvDownload.setText("暂停中...");
            mTvAppointmentDownload.setText("暂停中...");
        } else if (progress.status == Progress.ERROR) {
            mDownloadProgress.setVisibility(View.VISIBLE);
            mTvDownload.setText("下载暂停，点击继续");
            mTvAppointmentDownload.setText("下载暂停，点击继续");
        } else if (progress.status == Progress.FINISH) {
            GameExtraVo gameExtraVo = (GameExtraVo) progress.extra1;
            String packageName = gameExtraVo == null ? "" : gameExtraVo.getClient_package_name();
            mDownloadProgress.setVisibility(View.GONE);
            //打开
            if (!TextUtils.isEmpty(packageName) && AppUtil.isAppAvailable(_mActivity, packageName)) {
                mTvDownload.setText("打开");
                mTvAppointmentDownload.setText("打开");
            } else {
                File targetFile = new File(progress.filePath);
                //安装
                if (targetFile.exists()) {
                    mTvDownload.setText("安装");
                    mTvAppointmentDownload.setText("安装");
                } else {//下载
                    //mTvDownload.setText("立即下载");
                    if (gameInfoVo != null && gameInfoVo.getGame_type() != 3) {
                        SpannableStringBuilder spanBuilder = new SpannableStringBuilder("立即下载(" + gameInfoVo.getClient_size() + "M)");
                        spanBuilder.setSpan(new RelativeSizeSpan(0.6f), 4, spanBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mTvDownload.setText(spanBuilder);
                    } else {
                        mTvDownload.setText("立即下载");
                    }
                    mTvAppointmentDownload.setText("预下载");
                }
            }
        }

        GameDownloadTimeExtraVo gameDownloadTimeExtraVo = (GameDownloadTimeExtraVo) progress.extra2;
        if (gameDownloadTimeExtraVo != null && (progress.status == Progress.LOADING || progress.status == Progress.WAITING)) {
            long downloadTime = gameDownloadTimeExtraVo.getDownload_time();
            //间隔小于一秒则算作有效时间 否则认为是暂停时间不计入总时长
            if (System.currentTimeMillis() - gameDownloadTimeExtraVo.getLast_refresh_time() < 1000)
                gameDownloadTimeExtraVo.setDownload_time(downloadTime + (System.currentTimeMillis() - gameDownloadTimeExtraVo.getLast_refresh_time()));
            gameDownloadTimeExtraVo.setLast_refresh_time(System.currentTimeMillis());

            //实现实时修改下载时长记录
            ContentValues values = new ContentValues();
            values.put("extra2", IOUtils.toByteArray(progress.extra2));
            DownloadManager.getInstance().update(values, progress.tag);

            Log.e("progress", ((GameDownloadTimeExtraVo) progress.extra2).getDownload_time() + "");
        }
    }

    private void fileDownload(int id, int type) {
        String downloadUrl = gameInfoVo.getGame_download_url();
        if (!CommonUtils.downloadUrlVerification(downloadUrl)) {
            //ToastT.warning(_mActivity, "_(:з」∠)_ 下载异常，请重登或联系客服看看哟~");
            Toaster.show("下载异常，请重登或联系客服");
            return;
        }
        GetRequest<File> request = OkGo.get(gameInfoVo.getGame_download_url());
        request.headers("content-type", "application/vnd.android.package-archive");
        GameDownloadTimeExtraVo gameDownloadTimeExtraVo = new GameDownloadTimeExtraVo();
        gameDownloadTimeExtraVo.setDownload_time(0);
        gameDownloadTimeExtraVo.setLast_refresh_time(System.currentTimeMillis());
        gameDownloadTimeExtraVo.setId(id);
        gameDownloadTimeExtraVo.setType(type);
        //        request.setMimeType("application/vnd.android.package-archive");
        DownloadTask task = OkDownload.request(gameInfoVo.getGameDownloadTag(), request)
                .folder(SdCardManager.getInstance().getDownloadApkDir().getPath())
                .fileName(gameInfoVo.getGamename())
                .extra1(gameInfoVo.getGameExtraVo())
                .extra2(gameDownloadTimeExtraVo)
                .register(downloadListener)
                .save();
        task.start();
        EventBus.getDefault().post(new EventCenter(EventConfig.ACTION_ADD_DOWNLOAD_EVENT_CODE));
    }

    DownloadListener downloadListener = new DownloadListener("download") {
        @Override
        public void onStart(Progress progress) {
            setGameDownloadedPoint(1, progress);
        }

        @Override
        public void onProgress(Progress progress) {
            refresh(progress);
            DownloadNotifyManager.getInstance().doNotify(progress);
        }

        @Override
        public void onError(Progress progress) {
            progress.exception.printStackTrace();
            GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;
            DownloadNotifyManager.getInstance().cancelNotify(gameInfoVo.getGameid());
            Toast.makeText(_mActivity, R.string.string_download_game_fail, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish(File file, Progress progress) {
            setGameDownloadedPoint(10, progress);
            File targetFile = new File(progress.filePath);
            if (targetFile.exists()) {
                try {
                    GameDownloadTimeExtraVo gameDownloadTimeExtraVo = (GameDownloadTimeExtraVo) progress.extra2;
                    gameDownloadLog(gameDownloadTimeExtraVo.getId(), gameDownloadTimeExtraVo.getType(), gameDownloadTimeExtraVo.getDownload_time());

                    if (gameDownloadTimeExtraVo.getType() == 2) {
                        Setting.INPUT_CHANNEL_GAME_ID = gameInfoVo.getGameid();
                        Util.writeChannel(targetFile, gameInfoVo.getChannel(), false, false);
                        String s = Util.readChannel(targetFile);
                        Log.e("Channel", s);
                    }/*else {
                        Setting.INPUT_CHANNEL_GAME_ID = gameInfoVo.getGameid();
                        String readChannel = Util.readChannel(targetFile);
                        if (TextUtils.isEmpty(readChannel)){
                            if (!TextUtils.isEmpty(gameInfoVo.getChannel())){
                                Util.writeChannel(targetFile, gameInfoVo.getChannel(), false, false);
                            }else {
                                String channelStr =  "{" + '"' + "tgid" + '"' + ":" + '"' + AppUtils.getTgid() + '"' + "}";
                                String encode = Base64.encode(channelStr.getBytes());
                                Util.writeChannel(targetFile, encode, false, false);
                            }
                        }
                        String s = Util.readChannel(targetFile);
                        Log.e("Channel", s);
                    }*/
                } catch (Exception e) {
                } finally {
                    Log.e("Channel", "end");
                    if (!EasyFloat.isShow("float_download")) {
                        //AppUtil.install(_mActivity, targetFile);
                        showFristDownloadDialog(progress, targetFile);
                    }
                }
            }
        }//androidid=4cca4b84765cb17e&api=game_download_log&api_version=20231122&appid=1&client_id=0a1c71b31193375a3133f4819626e4e2&client_type=1&device_id=4cca4b84765cb17eunknown&duration=21767&gameid=12818&imei=&ip=10.29.8.213&is_special=0&mac=please+open+wifi&oaid=4bb94826885363ce&oldtgid=ea8888888&tgid=ea8888888&token=f1bff058c14839a13ba727519900c584&ts_device_brand=Xiaomi&ts_device_model=2106118C&ts_device_version=14&ts_device_version_code=34&type=1&ua=Mozilla%2F5.0+%28Linux%3B+Android+14%3B+2106118C+Build%2FUKQ1.231207.002%3B+wv%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Version%2F4.0+Chrome%2F120.0.6099.193+Mobile+Safari%2F537.36&uid=1825472&vc=1&version=84800Df1&#%$WT9sGc%^urZO0!XkjglAv!Vel


        @Override
        public void onRemove(Progress progress) {
            GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;
            DownloadNotifyManager.getInstance().cancelNotify(gameInfoVo.getGameid());
        }
    };

    /**
     * @param state    1-开始下载  10-下载完成
     * @param progress
     */
    public void setGameDownloadedPoint(int state, Progress progress) {
        if (mViewModel != null) {
            if (progress.extra1 != null && progress.extra1 instanceof GameExtraVo) {
                GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;
                Map<String, String> param = new TreeMap<>();
                param.put("gameid", String.valueOf(gameInfoVo.getGameid()));
                switch (state) {
                    case 1:
                        mViewModel.setPoint("trace_game_start_download", param);
                        break;
                    case 10:
                        mViewModel.setPoint("trace_game_downloaded", param);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        getGameInfoPartFl();
        if (UserInfoModel.getInstance().isLogined() && BuildConfig.IS_REPORT && advertGameinfo != null)
            download();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setGameDownloadStatus();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setGameDownloadStatus();
        deleteLocalData();//删除本地下载文件
    }

    @Override
    public void onStop() {
        super.onStop();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void deleteLocalData() {
        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
        boolean download_switch = spUtils.getBoolean("download_switch", true);
        if (download_switch) {
            if (gameInfoVo != null && !TextUtils.isEmpty(gameInfoVo.getClient_package_name())) {
                if (AppsUtils.isInstallApp(_mActivity, gameInfoVo.getClient_package_name())) {
                    Progress progress = DownloadManager.getInstance().get(gameInfoVo.getGameDownloadTag());
                    if (progress != null) {
                        DownloadTask task = OkDownload.getInstance().getTask(progress.tag);
                        if (task != null) {
                            task.unRegister(progress.tag);
                            task.remove(true);
                        }
                        DownloadManager.getInstance().delete(progress.tag);
                        OkDownload.getInstance().removeTask(progress.tag);
                        GameExtraVo gameInfoVo = (GameExtraVo) progress.extra1;
                        if (gameInfoVo != null) {
                            DownloadNotifyManager.getInstance().cancelNotify(gameInfoVo.getGameid());
                        }
                        FileUtils.deleteFile(progress.filePath);
                    }
                }
            }
        }
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getNewWorkData();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ACTION_TRANSACTION_GOOD_DETAIL:
                    transactionPage = 1;
                    getTransactionList();
                    break;
                case ACTION_WRITE_COMMENT:
                    commentPage = 1;
                    getCommentList();
                    gameInfoVo.setUser_already_commented(1);
                    gameInfoVo.getGameCardListVo();
                    if (mAdapter2_0 != null) mAdapter2_0.notifyDataSetChanged();
                    break;
                case ACTION_COUPON_LIST:
                    getGameInfoPartFl();
                    break;
                default:
                    break;
            }
        }
    }

    private GameInfoVo gameInfoVo;

    private void setGameMainInfo() {
        if (gameInfoVo != null) {
            if (gameInfoVo.getCloud_game_id() != null && !gameInfoVo.getCloud_game_id().isEmpty()) {
                if (!BuildConfig.NEED_BIPARTITION) {//头条包不显示云游戏
                    fl_cloud_layout.setVisibility(View.VISIBLE);
                } else {
                    fl_cloud_layout.setVisibility(View.GONE);
                }
                fl_cloud_layout.setOnClickListener(view -> {
                    if (checkLogin() && gameInfoVo != null) {
                        //云游
//                        CloudVeGameActivity.newInstance(_mActivity,gameInfoVo.getUid(),gameInfoVo.getGameid()+"",gameInfoVo.getCloud_game_id(),gameInfoVo.getGameicon(),gameInfoVo.getGamename(),gameInfoVo.getOtherGameName());
                        //云试玩
                        //getDemoToken();
                    }
                });
            } else {
                fl_cloud_layout.setVisibility(View.GONE);
            }


            //收藏游戏
            setGameViewFavorite(gameInfoVo.getIs_favorite() == 1);

            //交易数&点评数
            setTabCount();

            //设置新游预约信息
            setAppointmentLayout();

            //设置点评提示
            setCommentTipsView();

            //开服
            //setServerView();

            //现实试玩游戏
            if (gameInfoVo.getTrial_info() != null) {
               /* mIvTryGameReward.setVisibility(View.VISIBLE);
                mIvTryGameReward.setOnClickListener(view -> {
                    startFragment(TryGameTaskFragment.newInstance(gameInfoVo.getTrial_info().getTid()));
                });*/
                mIvTryGameReward.setVisibility(View.GONE);
                mIvTryGameReward.setOnClickListener(null);
            } else {
                mIvTryGameReward.setVisibility(View.GONE);
                mIvTryGameReward.setOnClickListener(null);
            }

            post(() -> {
                if (autoDownload && game_type != 3) {
                    clickDownload();
                }
            });
            if (advertGameinfo != null) {
                try {
                    showSingleGameDialog(advertGameinfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*private void getDemoToken() {
        mViewModel.getTokenDemo(gameInfoVo.getGameid() + "", new OnNetWorkListener<VeTokenVo>() {
            @Override
            public void onBefore() {

            }

            @Override
            public void onFailure(String message) {
                ToastT.warning(message);
            }

            @Override
            public void onSuccess(VeTokenVo data) {
                if (data.isStateOK()) {
                    CloudVeGameDemoActivity.newInstance(_mActivity, gameInfoVo.getUid(), gameInfoVo.getGameid() + "", gameInfoVo.getCloud_game_id(), gameInfoVo.getGameicon(), gameInfoVo.getGamename(), gameInfoVo.getOtherGameName(), data);
                } else if ("no_cert".equals(data.getState())) {
                    ToastT.warning(data.getMsg());
                    startFragment(new CertificationFragment());
                } else {
                    ToastT.warning(data.getMsg());
                }
            }

            @Override
            public void onAfter() {

            }
        });
    }*/

    ServerAdapter mServerAdapter;
    CustomDialog dialog;

    private class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.ServerHolder> {

        private List<GameInfoVo.ServerListBean> mList;
        private Context mContext;

        public ServerAdapter(Context context, List<GameInfoVo.ServerListBean> list) {
            mList = list;
            mContext = context;
            refreshList();
        }

        public void notifyData(List<GameInfoVo.ServerListBean> mList) {
            if (this.mList == mList) {
            } else {
                this.mList.clear();
                this.mList.addAll(mList);
            }
            refreshList();
            notifyDataSetChanged();
        }

        private void refreshList() {
            boolean fristServer = false;
            for (GameInfoVo.ServerListBean serverListBean : mList) {
                if (serverListBean.getBegintime() * 1000 > System.currentTimeMillis()) {
                    if (!fristServer) {
                        fristServer = true;
                        serverListBean.setTheNewest(true);
                    } else {
                        serverListBean.setTheNewest(false);
                    }
                } else {
                    serverListBean.setTheNewest(false);
                }
            }
        }


        @NonNull
        @Override
        public ServerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_game_detail_item_server_list, null);
            return new ServerHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ServerHolder holder, int position) {
            GameInfoVo.ServerListBean serverItem = mList.get(position);
            holder.mTvServerName.setText(serverItem.getServername());
            long ms = serverItem.getBegintime() * 1000;
            holder.mTvServerTime.setText(CommonUtils.friendlyTime2(ms));
            holder.mTvServerTime.setTextColor(ContextCompat.getColor(mContext, serverItem.isTheNewest() ? R.color.color_0052ef : R.color.color_232323));
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        class ServerHolder extends RecyclerView.ViewHolder {
            private TextView mTvServerName;
            private TextView mTvServerTime;

            public ServerHolder(View itemView) {
                super(itemView);
                mTvServerName = itemView.findViewById(R.id.tv_server_name);
                mTvServerTime = itemView.findViewById(R.id.tv_server_time);

            }
        }
    }


    private void setAppointmentLayout() {
        if (gameInfoVo != null) {
            int game_status = gameInfoVo.getGame_status();
            if (!gameInfoVo.isGameAppointment()) {
                mLlGameAppointment.setVisibility(View.GONE);
                mLlGameDownload.setVisibility(View.VISIBLE);
            } else {
                mLlGameAppointment.setVisibility(View.VISIBLE);
                mLlGameDownload.setVisibility(View.GONE);
                mFlAppointmentDownload.setVisibility(View.GONE);
                mTvAppointmentMessage.setText(gameInfoVo.getOnline_text());
                mTvAppointmentMessage.setTextColor(ContextCompat.getColor(_mActivity, R.color.color_ff0000));

                if (game_status == 0) {
                    mBtnGameAppointment.setBackgroundResource(R.drawable.shape_55c0fe_5571fe_big_radius);
                    mBtnGameAppointment.setText("预约");
                } else if (game_status == 1) {
                    mBtnGameAppointment.setBackgroundResource(R.drawable.ts_shape_big_radius_cccccc);
                    mBtnGameAppointment.setText("已预约");
                }

                mBtnGameAppointment.setOnClickListener(view -> {
                    if (checkLogin()) {
                        setGameAppointment(gameid);
                    }
                });

                if (gameInfoVo.getDownload_control() != null) {
                    if ("1".equals(gameInfoVo.getDownload_control().getAdvance_download())) {
                        mFlAppointmentDownload.setVisibility(View.VISIBLE);
                    } else {
                        mFlAppointmentDownload.setVisibility(View.GONE);
                    }
                }
            }

        }
    }

    private void setTabCount() {
        if (gameInfoVo != null) {
            //礼包数量
            int cardCount = gameInfoVo.getCardlist() == null ? 0 : gameInfoVo.getCardlist().size();
            if (cardCount > 0) {
                try {
                    //mTabInfoVoList.get(1).setTabCount(cardCount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //点评数
            int commentCount = gameInfoVo.getComment_count();
            if (commentCount > 0) {
                try {
                    mTabInfoVoList.get(1).setTabCount(commentCount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //交易数
            int goodsCount = gameInfoVo.getGoods_count();
            if (goodsCount > 0) {
                try {
                    mTabInfoVoList.get(2).setTabCount(goodsCount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //工具箱
//            if (!BuildConfig.NEED_BIPARTITION) {//头条包不显示工具箱
//                if (game_type != 3) {
//                    List<GameToolBoxListVo> gameToolBoxListVos = new ArrayList<>();
//                    if (gameInfoVo.getAccelerate_status() != 0) {
//                        gameToolBoxListVos.add(new GameToolBoxListVo(1, false));
//                    }
//                    if (gameInfoVo.getCloud() > 0) {
//                        gameToolBoxListVos.add(new GameToolBoxListVo(2, false));
//                    }
//                    if (AppUtil.isNotArmArchitecture()) {//满足使用双开空间的条件
//                        gameToolBoxListVos.add(new GameToolBoxListVo(3, false));
//                        gameToolBoxListVos.add(new GameToolBoxListVo(6, false));
//                        gameToolBoxListVos.add(new GameToolBoxListVo(4, false));
//                        gameToolBoxListVos.add(new GameToolBoxListVo(5, false));
//                    }
//
//                    mTabInfoVoList.get(mTabInfoVoList.size() - 1).setTabCount(gameToolBoxListVos.size());
//                }
//            }
            mTabAdapter.notifyDataSetChanged();
        }
    }

    private void getNewWorkData() {
        //getChatMessage();
        getGameInfoPartBase();

        transactionPage = 1;
        getTransactionList();

        //        commentPage = 1;
        //getShortCommentList();
    }

    private GameInfoVo.CardlistBean cgCard = null;

    /**
     * 获取游戏信息
     */
    private void getGameInfoPartBase() {
        if (mViewModel != null) {
            mViewModel.getGameInfoPartBase(gameid, new OnBaseCallback<GameDataVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onSuccess(GameDataVo gameDataVo) {
                    if (gameDataVo != null) {
                        if (gameDataVo.isStateOK()) {
                            GameInfoVo infoVo = gameDataVo.getData();
                            if (infoVo != null) {
                                gameInfoVo = infoVo;
                                getGameInfoPartFl();

                                game_type = gameInfoVo.getGame_type();
                                setGameMainInfo();

                                setGameDownloadStatus();

                                forceAppBarExpanded();
                            }
                        } else {
                            //Toaster.show( gameDataVo.getMsg());
                            Toaster.show(gameDataVo.getMsg());
                        }
                    }
                }
            });

        }
    }

    /**
     * 游戏详情-福利相关
     */
    private void getGameInfoPartFl() {
        if (mViewModel != null) {
            mViewModel.getGameInfoPartFl(gameid, new OnBaseCallback<GameDataVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    showSuccess();
                }

                @Override
                public void onSuccess(GameDataVo gameDataVo) {
                    if (gameDataVo != null) {
                        if (gameDataVo.isStateOK()) {
                            GameInfoVo infoVo = gameDataVo.getData();
                            if (infoVo != null) {
                                gameInfoVo.setCoupon_list(infoVo.getCoupon_list());
                                gameInfoVo.setCoupon_amount((int) infoVo.getCoupon_amount());
                                gameInfoVo.setCoupon_count(infoVo.getCoupon_count());
                                gameInfoVo.setActivity(infoVo.getActivity());
                                gameInfoVo.setCardlist(infoVo.getCardlist());
                                if (!TextUtils.isEmpty(infoVo.getBenefit_content()))
                                    gameInfoVo.setBenefit_content(infoVo.getBenefit_content());
                                gameInfoVo.setRebate_flash_begin(infoVo.getRebate_flash_begin());
                                gameInfoVo.setRebate_flash_content(infoVo.getRebate_flash_content());
                                gameInfoVo.setRebate_flash_end(infoVo.getRebate_flash_end());
                                gameInfoVo.setRebate_content(infoVo.getRebate_content());
                                gameInfoVo.setUser_already_commented(infoVo.getUser_already_commented());
                                gameInfoVo.setVip_news(infoVo.getVip_news());
                                gameInfoVo.setLsb_card_info(infoVo.getLsb_card_info());

                                mAdapter1.clear();

                                mAdapter1.addData(gameInfoVo);

                                GameRecommendListVo gameRecommendListVo = new GameRecommendListVo();
                                gameRecommendListVo.setGdm(gameInfoVo.getGdm());
                                gameRecommendListVo.setGdm_url(gameInfoVo.getGdm_url());
                                List<GameInfoVo.RecommendInfo> recommendInfos = new ArrayList<>();
                                if (gameInfoVo.getRecommend_info() != null && gameInfoVo.getRecommend_info().size() > 0) {
                                    recommendInfos.addAll(gameInfoVo.getRecommend_info());
                                }
                                if (gameInfoVo.getRefund() == 1) {
                                    GameInfoVo.RecommendInfo recommendInfo1 = new GameInfoVo.RecommendInfo();
                                    recommendInfo1.setType("sxw");
                                    recommendInfos.add(recommendInfo1);
                                }
                                if (gameInfoVo.isFlb_usage()) {
                                    GameInfoVo.RecommendInfo recommendInfo = new GameInfoVo.RecommendInfo();
                                    recommendInfo.setType("ptb");
                                    recommendInfos.add(recommendInfo);
                                }
                                /*if (!BuildConfig.NEED_BIPARTITION) {//头条包不显示加速器
                                    if (Setting.HIDE_FIVE_FIGURE != 1) {
                                        if (gameInfoVo.getAccelerate_status() != 0) {//加速器
                                            GameInfoVo.RecommendInfo recommendInfo = new GameInfoVo.RecommendInfo();
                                            recommendInfo.setType("accelerate");
                                            recommendInfos.add(recommendInfo);
                                        }
                                    }
                                }*/
                                /*if (!BuildConfig.NEED_BIPARTITION){//头条包不显示双开
                                    GameInfoVo.RecommendInfo bipartitionRecommendInfo = new GameInfoVo.RecommendInfo();
                                    bipartitionRecommendInfo.setType("bipartition");
                                    recommendInfos.add(bipartitionRecommendInfo);
                                }*/
                                if (gameInfoVo.getTrial_info() != null) {//试玩赚金
                                    GameInfoVo.RecommendInfo recommendInfo = new GameInfoVo.RecommendInfo();
                                    recommendInfo.setType("trial");
                                    recommendInfo.setTrial_info(gameInfoVo.getTrial_info());
                                    recommendInfos.add(recommendInfo);
                                }
                                if (gameInfoVo.getYouhui() != null) {//百亿补贴
                                    GameInfoVo.RecommendInfo recommendInfo = new GameInfoVo.RecommendInfo();
                                    recommendInfo.setType("subsidy");
                                    recommendInfo.setYouhui(gameInfoVo.getYouhui());
                                    recommendInfos.add(recommendInfo);
                                }
                                if (gameInfoVo.isUse_discount_coupon()) {//特惠卡  可用省钱卡
                                    GameInfoVo.RecommendInfo recommendInfo = new GameInfoVo.RecommendInfo();
                                    recommendInfo.setType("special");
                                    recommendInfos.add(recommendInfo);
                                }

                                if (recommendInfos.size() > 0 || (gameInfoVo.getGdm() == 1 && !TextUtils.isEmpty(gameInfoVo.getGdm_url()))) {
                                    gameRecommendListVo.setRecommend_info(recommendInfos);
                                    mAdapter1.addData(gameRecommendListVo);
                                }

                                if (AppConfig.isRefundChannel() && infoVo.isCanRefund()) {
                                    mAdapter1.addData(new GameRefundVo());
                                }

                                //变态福利
                                if (!TextUtils.isEmpty(gameInfoVo.getBenefit_content())) {
                                    mAdapter1.addData(gameInfoVo.getGameWelfareVo());
                                }

                                //充值返利
                                if (!TextUtils.isEmpty(gameInfoVo.getRebate_content()) || !TextUtils.isEmpty(gameInfoVo.getRebate_flash_content())) {
                                    mAdapter1.addData(gameInfoVo.getGameRebateVo());
                                }

                                //游戏简介
                                mAdapter1.addData(gameInfoVo.getGameDesVo());

                                if (gameInfoVo.getVendor_info() != null) {
                                    gameInfoVo.getVendor_info().setClient_version_name(gameInfoVo.getClient_version_name());
                                    gameInfoVo.getVendor_info().setRecord_number(gameInfoVo.getRecord_number());
                                    mAdapter1.addData(gameInfoVo.getVendor_info());
                                }

                                mAdapter1.notifyDataSetChanged();

                                getLikeList(true);

                                /*if (!BuildConfig.NEED_BIPARTITION) {//头条包不显示工具箱
                                    if (game_type != 3) {
                                        mAdapter6.clear();
                                        List<GameToolBoxListVo> gameToolBoxListVos = new ArrayList<>();
                                        if (gameInfoVo.getAccelerate_status() != 0) {
                                            gameToolBoxListVos.add(new GameToolBoxListVo(1, false));
                                        }
                                        if (gameInfoVo.getCloud() > 0) {
                                            gameToolBoxListVos.add(new GameToolBoxListVo(2, false));
                                        }
                                        if (AppUtil.isNotArmArchitecture()) {//满足使用双开空间的条件
                                            gameToolBoxListVos.add(new GameToolBoxListVo(3, false));
                                            gameToolBoxListVos.add(new GameToolBoxListVo(6, false));
                                            gameToolBoxListVos.add(new GameToolBoxListVo(4, false));
                                            gameToolBoxListVos.add(new GameToolBoxListVo(5, false));
                                        }
                                        mAdapter6.setDatas(gameToolBoxListVos);
                                        mAdapter6.notifyDataSetChanged();
                                        mRecyclerView6.setNoMore(true);
                                    }
                                }*/
                            }
                        } else {
                            //Toaster.show( gameDataVo.getMsg());
                            Toaster.show(gameDataVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    /**
     * 获取猜你喜欢数据
     */
    public void getLikeList(boolean isFrist) {
        if (mViewModel != null) {
            mViewModel.getLikeGameList(new OnBaseCallback<GameListVo>() {
                @Override
                public void onSuccess(GameListVo data) {
                    if (data != null && data.getData() != null && data.getData().size() > 0) {
                        GameLikeListVo gameLikeListVo = new GameLikeListVo();
                        gameLikeListVo.setLike_game_list(data.getData());
                        gameInfoVo.setGameLikeListVo(gameLikeListVo);
                        gameInfoVo.setLike_game_list(data.getData());
                        if (isFrist) {
                            mAdapter1.addData(gameInfoVo.getGameLikeListVo());
                        } else {
                            mAdapter1.getData().set(mAdapter1.getData().size() - 1, gameLikeListVo);
                        }
                        mAdapter1.notifyItemChanged(mAdapter1.getData().size());
                    }
                }
            });
        }
    }

    /**
     * 领取648礼包
     */
    public void getLsbCard() {
        if (mViewModel != null) {
            mViewModel.getLsbCard(gameid, new OnBaseCallback<GetCardInfoVo>() {
                @Override
                public void onSuccess(GetCardInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK() && data.getData() != null) {
                            if (gameInfoVo.getLsb_card_info() != null) {
                                gameInfoVo.getLsb_card_info().setCard(data.getData().getCard());
                            }
                            mAdapter1.notifyDataSetChanged();
                            showLSBTipsDialog();
                            download();
                        } else {
                            //ToastT.error(data.getMsg());
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    /**
     * 获取交易数据
     */
    private void getTransactionList() {
        if (mViewModel != null) {
            if (transactionPage == 1) {
                if (mRecyclerView4 != null) {
                    mRecyclerView4.setNoMore(false);
                }
            }
            mViewModel.getTransactionListData(gameid, transactionPage, transactionPageCount, new OnBaseCallback<TradeGoodInfoListVo1>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    if (mRecyclerView4 != null) {
                        mRecyclerView4.loadMoreComplete();
                    }
                }

                @Override
                public void onSuccess(TradeGoodInfoListVo1 data) {
                    setTransactionList(data);
                }
            });
        }
    }

    private String type_id;
    private String comment_type = "1";
    private String sort = "hottest";

    /**
     * 获取点评数据
     */
    boolean firshGetCommentList = true;

    private void getCommentList() {
        if (firshGetCommentList) {
            loading("");
            firshGetCommentList = false;
        }
        if (AppConfig.isHideCommunity()) {
            return;
        }
        if (mViewModel != null && !AppConfig.isHideCommunity()) {
            if (commentPage == 1) {
                if (mRecyclerView2 != null) {
                    mRecyclerView2.setNoMore(false);
                }
            }

            TreeMap<String, String> stringStringTreeMap = new TreeMap<>();
            stringStringTreeMap.put("gameid", gameid + "");
//            stringStringTreeMap.put("gameid", "11109");
            stringStringTreeMap.put("page", commentPage + "");
            if (cate_id != -1) {
                stringStringTreeMap.put("cate_id", cate_id + "");
            }
            stringStringTreeMap.put("order_type", order_type);
            mViewModel.forumList(stringStringTreeMap, new OnBaseCallback<ForumListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    if (mRecyclerView2 != null) {
                        mRecyclerView2.loadMoreComplete();
                    }
                }

                @Override
                public void onSuccess(ForumListVo data) {
                    loadingComplete();
                    setCommentList(data);
                }
            });
        }
    }

    private void forumReplyTopLike(String tid, ForumListVo.DataBean mInfo) {
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

                }

                @Override
                public void onFailure(String message) {

                }

                @Override
                public void onSuccess(ForumReplyTopLikeVo data) {
                    if (data.isStateOK()) {
                        List items = mAdapter2.getItems();
                        for (Object item : items) {
                            if (item instanceof ForumListVo.DataBean) {
                                ForumListVo.DataBean o = (ForumListVo.DataBean) item;
                                if (mInfo.getTid() == o.getTid()) {
                                    if ("hit".equals(data.getData().getOperation())) {
                                        o.setLike_count(o.getLike_count() + 1);
                                        o.setLike_status(1);
                                    } else {
                                        o.setLike_status(0);
                                        o.setLike_count((o.getLike_count() - 1) < 0 ? 0 : (o.getLike_count() - 1));
                                    }
                                    mAdapter2.notifyDataSetChanged();
                                }
                            }
                        }

                    } else {
                        //Toaster.show( data.getMsg());
                        Toaster.show( data.getMsg());
                    }
                }

                @Override
                public void onAfter() {

                }
            });
        }
    }

    private void getList2TopData() {
        if (AppConfig.isHideCommunity()) {
            return;
        }
        if (mViewModel != null) {
            //话题分类
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
                            ForumCategoryVo.DataBean dataBean = new ForumCategoryVo.DataBean();
                            dataBean.setName("全部");
                            dataBean.setClick(true);
                            dataBean.setCate_id(-1);
                            categoryList.add(dataBean);
                            categoryList.addAll(data.getData());

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

        if (mViewModel != null && !AppConfig.isHideCommunity()) {
            //置顶话题列表
            TreeMap<String, String> stringStringTreeMap = new TreeMap<>();
            stringStringTreeMap.put("gameid", gameid + "");
//            stringStringTreeMap.put("gameid", "11109");
            mViewModel.stickyList(stringStringTreeMap, new OnBaseCallback<ForumListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                }

                @Override
                public void onSuccess(ForumListVo data) {
                    if (data.isStateOK()) {
                        if (!data.getData().isEmpty()) {
                            list2_sticky.setVisibility(View.VISIBLE);
                            stickyAdapter.addAllData(data.getData());
                        } else {
                            list2_sticky.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }
        if (mViewModel != null && !AppConfig.isHideCommunity()) {
            //广告图
            TreeMap<String, String> stringStringTreeMap = new TreeMap<>();
            stringStringTreeMap.put("gameid", gameid + "");
            mViewModel.topicAdvert(stringStringTreeMap, new OnBaseCallback<AdSwiperListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                }

                @Override
                public void onSuccess(AdSwiperListVo data) {
                    if (data != null) {
                        if (data.isStateOK() && data.getData() != null) {
                            if (data.getData() != null && data.getData().size() > 0) {
                                list2_banner.setVisibility(View.VISIBLE);
                                ViewGroup.LayoutParams params = list2_banner.getLayoutParams();
                                if (params != null) {
                                    params.height = (ScreenUtil.getScreenWidth(_mActivity) - ScreenUtil.dp2px(_mActivity, 20)) * 180 / 710;
                                    list2_banner.setLayoutParams(params);
                                }
                                int bannerSize = data.getData().size();
                                //设置banner样式
                                list2_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                                //设置图片加载器
                                list2_banner.setImageLoader(new ImageLoader() {
                                    @Override
                                    public void displayImage(Context context, Object path, ImageView imageView) {
                                        AdSwiperListVo.AdSwiperBean bannerVo = (AdSwiperListVo.AdSwiperBean) path;
                                        GlideUtils.loadRoundImage(_mActivity, bannerVo.getPic(), imageView, R.mipmap.img_placeholder_v_load);
                            /*            GlideApp.with(_mActivity)
                                                .load(bannerVo.getPic())
                                                .placeholder(R.mipmap.img_placeholder_v_load)
                                                .error(R.mipmap.img_placeholder_v_load)
                                                .transform(new GlideRoundTransformNew(_mActivity, 10))
                                                .into(imageView);*/
                                    }
                                });
                                //设置图片集合
                                list2_banner.setImages(data.getData());
                                //设置banner动画效果
                                list2_banner.setBannerAnimation(Transformer.Default);
                                //设置自动轮播，默认为true
                                if (bannerSize > 1) {
                                    //设置轮播时间
                                    list2_banner.setDelayTime(5000);
                                    list2_banner.isAutoPlay(true);
                                } else {
                                    list2_banner.isAutoPlay(false);
                                }
                                list2_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                                //设置指示器位置（当banner模式中有指示器时）
                                list2_banner.setIndicatorGravity(BannerConfig.RIGHT);
                                list2_banner.setOnBannerListener(new OnBannerListener() {
                                    @Override
                                    public void OnBannerClick(int position) {
                                        AdSwiperListVo.AdSwiperBean adSwiperBean = data.getData().get(position);
                                        if (adSwiperBean != null) {
                                            if (_mActivity != null) {
                                                AppBaseJumpInfoBean appBaseJumpInfoBean = new AppBaseJumpInfoBean(adSwiperBean.getPage_type(), adSwiperBean.getParam());
                                                AppJumpAction appJumpAction = new AppJumpAction(_mActivity);
                                                appJumpAction.jumpAction(appBaseJumpInfoBean);
                                            }
                                        }
                                    }
                                });
                                //banner设置方法全部调用完毕时最后调用
                                list2_banner.start();
                            } else {
                                list2_banner.setVisibility(View.GONE);
                            }
                        } else {
                            list2_banner.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }
    }


    /**
     * 获取点评分类
     */
    private void getCommentTypeV2() {
        if (mViewModel != null && !AppConfig.isHideCommunity()) {
/*            mViewModel.getCommentTypeV2(gameid, new OnBaseCallback<CommentTypeListVo>() {
                @Override
                public void onSuccess(CommentTypeListVo data) {
                    if (data != null && data.isStateOK()) {
                        addCommentTypeButton(data.getData());
                    }
                }
            });*/
        }
    }

    /**
     * 交易Tab数据
     *
     * @param data
     */
    private void setTransactionList(TradeGoodInfoListVo1 data) {
        if (data != null) {
            if (data.isStateOK()) {
                if (data.getData() != null && !data.getData().isEmpty()) {
                    if (transactionPage == 1) {
                        mAdapter4.clear();
                    }
                    mAdapter4.addAllData(data.getData());
                    if (data.getData().size() < transactionPageCount) {
                        //无更多数据
                        transactionPage = -1;
                        mRecyclerView4.setNoMore(true);
                        if (transactionPage > 1) {
                            mAdapter4.addData(new NoMoreDataVo());
                        }
                    }
                } else {
                    if (transactionPage == 1) {
                        mAdapter4.clear();
                        EmptyDataVo emptyDataVo = new EmptyDataVo(R.mipmap.img_empty_data_2)
                                .setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT)
                                .setPaddingTop((int) (24 * density))
                                .setWhiteBg(true);
                        mAdapter4.addData(emptyDataVo);
                    } else {
                        mAdapter4.addData(new NoMoreDataVo());
                    }
                    //无更多数据
                    transactionPage = -1;
                    mRecyclerView4.setNoMore(true);
                }
                mAdapter4.notifyDataSetChanged();
            } else {
                //Toaster.show( data.getMsg());
                Toaster.show( data.getMsg());
            }
        }
    }

    /**
     * 点评Tab数据
     *
     * @param data
     */
    private void setCommentList(ForumListVo data) {
        if (data != null) {
            if (data.isStateOK()) {
                if (data.getData() != null && !data.getData().isEmpty()) {
                    if (commentPage == 1) {
                        mAdapter2.clear();
                    }
                    mAdapter2.addAllData(data.getData());
                    if (data.getData().size() < transactionPageCount) {
                        //无更多数据
                        mRecyclerView2.setNoMore(true);
                        mAdapter2.addData(new NoMoreDataVo());
                    }
                } else {
                    if (commentPage == 1) {
                        mAdapter2.clear();
                        SpannableStringBuilder spannable = new SpannableStringBuilder("暂无帖子，去发布 >");
                        ClickableSpan clickableSpan = new ClickableSpan() {
                            @Override
                            public void updateDrawState(@NonNull TextPaint ds) {
                                ds.setColor(Color.parseColor("#5571FE"));
                            }

                            @Override
                            public void onClick(View widget) {

                            }
                        };
                        spannable.setSpan(clickableSpan, 5, 10, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        EmptyDataVo1 emptyDataVo = new EmptyDataVo1(R.mipmap.img_empty_data_3)
                                .setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT)
                                .setEmptyWord(spannable)
                                .setPaddingTop((int) (24 * density))
                                .setWhiteBg(true);
                        mAdapter2.addData(emptyDataVo);
                    } else {
                        mAdapter2.addData(new NoMoreDataVo());
                    }
                    //无更多数据
                    commentPage = -1;
                    mRecyclerView2.setNoMore(true);
                }
                mAdapter2.notifyDataSetChanged();
            } else {
                //Toaster.show( data.getMsg());
                Toaster.show(data.getMsg());
            }
        }
    }

    private boolean isShowGameFavoriteTips;

    /**
     * 收藏游戏
     *
     * @param isShowTips
     * @param gameid
     * @param type       收藏类型 1:直接收藏游戏, 2:下载收藏
     */
    private void setGameFavorite(boolean isShowTips, int gameid, int type) {
        if (mViewModel != null) {
            isShowGameFavoriteTips = isShowTips;
            mViewModel.setGameFavorite(gameid, type, new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            setGameViewFavorite(true);
                            if (gameInfoVo != null) {
                                gameInfoVo.setIs_favorite(1);
                            }
                            if (isShowGameFavoriteTips) {
                               // ToastT.success(_mActivity, R.string.string_game_favorite_success);
                                Toaster.show("收藏成功！可在我的页面[我的游戏]处查看");
                            }
                        } else {
                            if (isShowGameFavoriteTips) {
                                //Toaster.show( data.getMsg());
                                Toaster.show(data.getMsg());
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * 取消收藏游戏
     *
     * @param gameid
     */
    private void setGameUnFavorite(int gameid) {
        if (mViewModel != null) {
            mViewModel.setGameUnFavorite(gameid, new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            setGameViewFavorite(false);
                            if (gameInfoVo != null) {
                                gameInfoVo.setIs_favorite(0);
                            }
                            //ToastT.success(_mActivity, R.string.string_game_cancel_favorite_success);
                            Toaster.show("已取消收藏");
                        }
                    } else {
                        //Toaster.show( data.getMsg());
                        Toaster.show( data.getMsg());
                    }
                }
            });
        }
    }

    private void setGameAppointment(int gameid) {
        if (mViewModel != null) {
            mViewModel.gameAppointment(gameid, new OnBaseCallback<GameAppointmentOpVo>() {

                @Override
                public void onSuccess(GameAppointmentOpVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                String op = data.getData().getOp();
                                switch (op) {
                                    case "reserve":
                                        String toast = data.getMsg();
                                        if (gameInfoVo != null) {
                                            showGameAppointmentCalendarReminder(gameInfoVo.getGameAppointmentVo(), toast);
                                        }
                                        break;
                                    case "cancel":
                                        if (gameInfoVo != null) {
                                            cancelGameAppointmentCalendarReminder(gameInfoVo.getGameAppointmentVo());
                                        }
                                        //ToastT.success(_mActivity, data.getMsg());
                                        Toaster.show( data.getMsg());
                                        break;
                                }
                            }
                            EventBus.getDefault().post(new EventCenter(EventConfig.ACTION_GAME_APPOINTMENT_EVENT_CODE));
                            getGameInfoPartBase();
                        } else {
                            //Toaster.show( data.getMsg());
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    /**
     * 跳转点评页面
     */
    public void getCommentGift(int cardid) {
        if (gameInfoVo != null && checkLogin()) {
            if (!gameInfoVo.isUserAlreadyCommented()) {
                //点评
                if (gameInfoVo != null) {
                    startForResult(WriteCommentsFragment.newInstance(String.valueOf(gameid), gameInfoVo.getGamename()), ACTION_WRITE_COMMENT);
                }
            } else {
                getCardInfo(cardid);
            }
        }
    }

    private CardDialogHelper cardDialogHelper;

    public void getCardInfo(int cardid) {
        if (mViewModel != null) {
            mViewModel.getCardInfo(gameid, cardid, new OnBaseCallback<GetCardInfoVo>() {
                @Override
                public void onSuccess(GetCardInfoVo getCardInfoVo) {
                    if (getCardInfoVo != null) {
                        if (getCardInfoVo.isStateOK()) {
                            if (getCardInfoVo.getData() != null) {
                                if (cardDialogHelper == null) {
                                    cardDialogHelper = new CardDialogHelper(GameDetailInfoFragment.this);
                                }
                                cardDialogHelper.showGiftDialog(getCardInfoVo.getData().getCard(), isFromSDK, SDKPackageName);
                                refreshCardList(cardid, getCardInfoVo.getData().getCard());
                            }
                        } else {
                            //Toaster.show( getCardInfoVo.getMsg());
                            Toaster.show(getCardInfoVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void refreshCardList(int cardid, String card) {
        if (gameInfoVo != null) {
            GameCardListVo gameCardListVo = gameInfoVo.getGameCardListVo();
            for (GameInfoVo.CardlistBean cardlistBean : gameCardListVo.getCardlist()) {
                if (cardlistBean.getCardid() == cardid) {
                    cardlistBean.setIs_get_card(1);
                    cardlistBean.setCard(card);
                    break;
                }
            }
            if (mAdapter2_0 != null) mAdapter2_0.notifyDataSetChanged();
        }
    }

    public void getTaoCardInfo(int cardid) {
        if (mViewModel != null) {
            mViewModel.getTaoCardInfo(gameid, cardid, new OnBaseCallback<GetCardInfoVo>() {
                @Override
                public void onSuccess(GetCardInfoVo getCardInfoVo) {
                    if (getCardInfoVo != null) {
                        if (getCardInfoVo.isStateOK()) {
                            if (getCardInfoVo.getData() != null) {
                                if (cardDialogHelper == null) {
                                    cardDialogHelper = new CardDialogHelper(GameDetailInfoFragment.this);
                                }
                                cardDialogHelper.showSearchCardDialog(getCardInfoVo.getData().getCard(), isFromSDK, SDKPackageName);
                            }
                        } else {
                            //Toaster.show( getCardInfoVo.getMsg());
                            Toaster.show(getCardInfoVo.getMsg());
                        }
                    }
                }
            });
        }
    }

    public void setCommentReply(Dialog mDialog, View clickView, String strContent, String rid,
                                int cid) {
/*        if (mViewModel != null) {
            Map<String, String> params = new TreeMap<>();

            mViewModel.forumList(params, new OnBaseCallback() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    clickView.setEnabled(true);
                    loadingComplete();
                }

                @Override
                public void onBefore() {
                    super.onBefore();
                    clickView.setEnabled(false);
                    loading();
                }

                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (mDialog != null && mDialog.isShowing()) {
                                mDialog.dismiss();
                            }
                            ToastT.success(_mActivity, "回复成功");
                            //2018.07.16增加回复成功之后的局部刷新
                            refreshCommentReply(strContent, cid);
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }*/
    }

    /**
     * 点赞评论
     *
     * @param cid
     */
    public void setCommentLike(int cid) {

    }

    /**
     * 局部刷新
     *
     * @param cid
     * @param like_shift
     */
    private void refreshCommentList(int cid, int like_shift) {

    }

    @Override
    public boolean onBackPressedSupport() {
        if (Jzvd.backPress()) {
            return true;
        }
        pop();
        return true;
    }


    private void showSingleGameDialog(GameInfoVo gameInfoVo) {
        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
        boolean hasShow = spUtils.getBoolean(SpConstants.SP_HAS_SHOW_SINGLE_GAME);
        if (hasShow) {
            return;
        }
        Context mContext = _mActivity;
        float density = mContext.getResources().getDisplayMetrics().density;
        CustomDialog dialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.dialog_show_single_game, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        AppCompatImageView mGameIcon = dialog.findViewById(R.id.game_icon);
        AppCompatTextView mGameName = dialog.findViewById(R.id.game_name);
        AppCompatTextView mGameTag = dialog.findViewById(R.id.game_tag);
        FlexboxLayout mFlexBoxLayout = dialog.findViewById(R.id.flex_box_layout);
        TextView mTvGameStatusContent = dialog.findViewById(R.id.tv_game_status_content);

        Button mBtnConfirm = dialog.findViewById(R.id.btn_confirm);
        TextView mBtnCancel = dialog.findViewById(R.id.btn_cancel);

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        GradientDrawable gdBtn = new GradientDrawable();
        gdBtn.setCornerRadius(30 * density);
        gdBtn.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        gdBtn.setColors(new int[]{Color.parseColor("#077AFF"), Color.parseColor("#0052FE")});
        mBtnConfirm.setBackground(gdBtn);

        if (gameInfoVo.getGame_labels() != null && gameInfoVo.getGame_labels().size() > 0) {
            for (int i = 0; i < gameInfoVo.getGame_labels().size(); i++) {
                GameInfoVo.GameLabelsBean gameLabelsBean = gameInfoVo.getGame_labels().get(i);
                TextView view = new TextView(mContext);

                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(4 * density);
                gd.setColor(Color.parseColor(gameLabelsBean.getBgcolor()));

                int x = (int) (8 * density);
                int y = (int) (4 * density);
                view.setPadding(x, y, x, y);
                view.setTextSize(12);
                try {
                    view.setTextColor(Color.parseColor(gameLabelsBean.getText_color()));
                } catch (Exception e) {
                }
                view.setText(gameLabelsBean.getLabel_name());
                view.setBackground(gd);

                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.topMargin = (int) (6 * density);
                params.leftMargin = (int) (3 * density);
                params.rightMargin = (int) (3 * density);
                mFlexBoxLayout.addView(view, params);
            }
        }

        GlideUtils.loadRoundImage(mContext, gameInfoVo.getGameicon(), mGameIcon);
        mGameName.setText(gameInfoVo.getGamename());
        StringBuilder sbTag = new StringBuilder();
        sbTag.append(gameInfoVo.getGenre_str());

        //        if (gameInfoVo.getStatus() == 0) {
        //            mTvGameStatusContent.setText("所浏览广告游戏已失效，为你推荐新游");
        //        } else {
        //        }
        mTvGameStatusContent.setText("这是您刚才浏览的游戏");

        if (gameInfoVo.getServerInfo() != null) {
            GameInfoVo.ServerInfoVo serverInfo = gameInfoVo.getServerInfo();
            sbTag.append("  |  ")
                    .append(CommonUtils.friendlyTime2(serverInfo.getBegintime() * 1000))
                    .append("  ")
                    .append(serverInfo.getServername());
        }
        mGameTag.setText(sbTag);

        final int gameid = gameInfoVo.getGameid();
        final int game_type = gameInfoVo.getGame_type();
        mBtnCancel.setOnClickListener(view -> dialog.dismiss());
        mBtnConfirm.setOnClickListener(view -> {
            //            mRlFloatShowSingleGame.setVisibility(View.GONE);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            //跳转游戏详情页
            //            goGameDetail(gameid, game_type);
        });

        dialog.setOnDismissListener(dialog1 -> {
            if (BuildConfig.IS_DOWNLOAD_GAME_FIRST) {
                download();
            } else if (BuildConfig.IS_REPORT) {
                if (checkLogin()) {
                    download();
                }
            }
        });

        if (LifeUtil.isAlive(_mActivity)) {
            //            mRlFloatShowSingleGame.postDelayed(() -> {
            //                mRlFloatShowSingleGame.setVisibility(View.VISIBLE);
            //                GlideUtils.loadCircleImage(_mActivity, gameInfoVo.getGameicon(), mIvGameIcon, R.mipmap.ic_placeholder, 1, R.color.white);
            //            }, 500);
            //            mRlFloatShowSingleGame.setTag(gameInfoVo);
            dialog.show();
            spUtils.putBoolean(SpConstants.SP_HAS_SHOW_SINGLE_GAME, true);
        }
    }

    private CustomDialog commentTipsDialog;

    private CustomDialog vipTipsDialog;

    public void showVipTipsDialog() {
        if (vipTipsDialog == null) {
            vipTipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_game_detail_vip_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        }
        vipTipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            if (vipTipsDialog != null && vipTipsDialog.isShowing()) {
                vipTipsDialog.dismiss();
            }
        });
        vipTipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if (vipTipsDialog != null && vipTipsDialog.isShowing()) {
                vipTipsDialog.dismiss();
            }
            dismissCouponDialog();
            startFragment(new NewUserVipFragment());
        });
        vipTipsDialog.show();
    }

    private CustomDialog couponListDialog;
    private BaseRecyclerAdapter couponListAdapter;
    private List<GameInfoVo.CouponListBean> gameCouponList = new ArrayList<>();
    private List<GameInfoVo.CouponListBean> shopCouponList = new ArrayList<>();

    public void showCouponListDialog(int type) {//type: 0 游戏券 1 商城券
        if (commentTipsDialog == null) {
            couponListDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_game_detail_coupon_list, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        }
        LinearLayout mLlEmpty = couponListDialog.findViewById(R.id.ll_empty);
        RecyclerView couponListRecyclerView = couponListDialog.findViewById(R.id.recycler_view);
        RecyclerViewNoBugLinearLayoutManager layoutManager = new RecyclerViewNoBugLinearLayoutManager(_mActivity);
        couponListRecyclerView.setLayoutManager(layoutManager);
        couponListAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.CouponListBean.class, new NewGameCouponListItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
        couponListRecyclerView.setAdapter(couponListAdapter);

        TextView mTvGameCouponName = couponListDialog.findViewById(R.id.tv_game_coupon_name);
        TextView mTvGameCouponTips = couponListDialog.findViewById(R.id.tv_game_coupon_tips);
        TextView mTvMallCouponName = couponListDialog.findViewById(R.id.tv_mall_coupon_name);
        TextView mTvMallCouponTips = couponListDialog.findViewById(R.id.tv_mall_coupon_tips);

        //设置代金券数据
        gameCouponList.clear();
        shopCouponList.clear();
        for (int i = 0; i < gameinfoPartCouponList.size(); i++) {
            if ("game_coupon".equals(gameinfoPartCouponList.get(i).getCoupon_type())) {
                gameCouponList.add(gameinfoPartCouponList.get(i));
            } else if ("shop_goods".equals(gameinfoPartCouponList.get(i).getCoupon_type())) {
                shopCouponList.add(gameinfoPartCouponList.get(i));
            }
        }

        if (gameCouponList.size() > 0) {
            DecimalFormat decimalFormat = new DecimalFormat("0.0");
            String format = decimalFormat.format(gameInfoVo.getCoupon_amount());
            String amount = (format.indexOf(".0") != -1) ? format.substring(0, format.indexOf(".0")) : format;
            SpannableString spannableString = new SpannableString(amount + "元券");
            mTvGameCouponTips.setText(spannableString);
        } else {
            mTvGameCouponTips.setText("暂无");
        }

        if (type == 0) {
            couponListAdapter.clear();
            couponListAdapter.setDatas(gameCouponList);
            couponListAdapter.notifyDataSetChanged();
            couponListDialog.findViewById(R.id.ll_other).setVisibility(View.VISIBLE);
            mTvGameCouponName.setTextColor(Color.parseColor("#5571FE"));
            mTvGameCouponTips.setTextColor(Color.parseColor("#5571FE"));
            mTvGameCouponTips.setBackgroundResource(R.drawable.shape_e2e2e2_big_radius);
            mTvMallCouponName.setTextColor(Color.parseColor("#232323"));
            mTvMallCouponTips.setTextColor(Color.parseColor("#9B9B9B"));
            mTvMallCouponTips.setBackgroundResource(R.drawable.shape_1a5571fe_big_radius);
            if (gameCouponList.size() > 0) {
                couponListRecyclerView.setVisibility(View.VISIBLE);
                mLlEmpty.setVisibility(View.GONE);
            } else {
                couponListRecyclerView.setVisibility(View.GONE);
                mLlEmpty.setVisibility(View.VISIBLE);
            }
        } else if (type == 1) {
            couponListAdapter.clear();
            couponListAdapter.setDatas(shopCouponList);
            couponListAdapter.notifyDataSetChanged();
            couponListDialog.findViewById(R.id.ll_other).setVisibility(View.GONE);
            mTvGameCouponName.setTextColor(Color.parseColor("#232323"));
            mTvGameCouponTips.setTextColor(Color.parseColor("#9B9B9B"));
            mTvMallCouponTips.setBackgroundResource(R.drawable.shape_1a5571fe_big_radius);
            mTvMallCouponName.setTextColor(Color.parseColor("#5571FE"));
            mTvMallCouponTips.setTextColor(Color.parseColor("#5571FE"));
            mTvMallCouponTips.setBackgroundResource(R.drawable.shape_e2e2e2_big_radius);
            if (shopCouponList.size() > 0) {
                couponListRecyclerView.setVisibility(View.VISIBLE);
                mLlEmpty.setVisibility(View.GONE);
            } else {
                couponListRecyclerView.setVisibility(View.GONE);
                mLlEmpty.setVisibility(View.VISIBLE);
            }
        }

        couponListDialog.findViewById(R.id.ll_game_coupon).setOnClickListener(v -> {
            couponListDialog.findViewById(R.id.ll_other).setVisibility(View.VISIBLE);
            couponListAdapter.setDatas(gameCouponList);
            couponListAdapter.notifyDataSetChanged();
            mTvGameCouponName.setTextColor(Color.parseColor("#5571FE"));
            mTvGameCouponTips.setTextColor(Color.parseColor("#5571FE"));
            mTvGameCouponTips.setBackgroundResource(R.drawable.shape_1a5571fe_big_radius);
            mTvMallCouponName.setTextColor(Color.parseColor("#232323"));
            mTvMallCouponTips.setTextColor(Color.parseColor("#9B9B9B"));
            mTvMallCouponTips.setBackgroundResource(R.drawable.shape_e2e2e2_big_radius);
            if (gameCouponList.size() > 0) {
                couponListRecyclerView.setVisibility(View.VISIBLE);
                mLlEmpty.setVisibility(View.GONE);
            } else {
                couponListRecyclerView.setVisibility(View.GONE);
                mLlEmpty.setVisibility(View.VISIBLE);
            }
        });
        couponListDialog.findViewById(R.id.ll_mall_coupon).setOnClickListener(v -> {
            couponListDialog.findViewById(R.id.ll_other).setVisibility(View.GONE);
            couponListAdapter.setDatas(shopCouponList);
            couponListAdapter.notifyDataSetChanged();
            mTvGameCouponName.setTextColor(Color.parseColor("#232323"));
            mTvGameCouponTips.setTextColor(Color.parseColor("#9B9B9B"));
            mTvGameCouponTips.setBackgroundResource(R.drawable.shape_e2e2e2_big_radius);
            mTvMallCouponName.setTextColor(Color.parseColor("#5571FE"));
            mTvMallCouponTips.setTextColor(Color.parseColor("#5571FE"));
            mTvMallCouponTips.setBackgroundResource(R.drawable.shape_1a5571fe_big_radius);
            if (shopCouponList.size() > 0) {
                couponListRecyclerView.setVisibility(View.VISIBLE);
                mLlEmpty.setVisibility(View.GONE);
            } else {
                couponListRecyclerView.setVisibility(View.GONE);
                mLlEmpty.setVisibility(View.VISIBLE);
            }
        });

        couponListDialog.findViewById(R.id.iv_close).setOnClickListener(v -> {
            if (couponListDialog != null && couponListDialog.isShowing()) {
                couponListDialog.dismiss();
            }
        });
        couponListDialog.findViewById(R.id.tv_store).setOnClickListener(v -> {
            if (couponListDialog != null && couponListDialog.isShowing()) {
                couponListDialog.dismiss();
            }
            startFragment(new CommunityIntegralMallFragment());
        });
        couponListDialog.findViewById(R.id.tv_vip).setOnClickListener(v -> {
            if (couponListDialog != null && couponListDialog.isShowing()) {
                couponListDialog.dismiss();
            }
            startFragment(new NewUserVipFragment());
        });
        couponListDialog.show();
    }

    public void dismissCouponDialog() {
        if (couponListDialog != null && couponListDialog.isShowing()) {
            couponListDialog.dismiss();
        }
    }

    public void getCoupon(int coupon_id) {
        if (checkLogin() && checkUserBindPhoneTips1()) {
            if (mViewModel != null) {
                mViewModel.getCoupon(coupon_id, new OnBaseCallback() {
                    @Override
                    public void onSuccess(BaseVo data) {
                        if (data != null) {
                            if (data.isStateOK()) {
                                //ToastT.success(_mActivity, "领取成功");
                                Toaster.show("领取成功");
                                List<GameInfoVo.CouponListBean> coupon_list = gameInfoVo.getCoupon_list();
                                for (int i = 0; i < coupon_list.size(); i++) {
                                    if (Integer.parseInt(coupon_list.get(i).getId()) == coupon_id) {
                                        coupon_list.get(i).setStatus(10);
                                    }
                                }
                                //设置代金券数据
                                for (int i = 0; i < gameinfoPartCouponList.size(); i++) {
                                    if (Integer.parseInt(gameinfoPartCouponList.get(i).getId()) == coupon_id) {
                                        gameinfoPartCouponList.get(i).setStatus(10);
                                    }
                                }
                                for (int i = 0; i < gameCouponList.size(); i++) {
                                    if (Integer.parseInt(gameCouponList.get(i).getId()) == coupon_id) {
                                        gameCouponList.get(i).setStatus(10);
                                    }
                                }
                                for (int i = 0; i < shopCouponList.size(); i++) {
                                    if (Integer.parseInt(shopCouponList.get(i).getId()) == coupon_id) {
                                        shopCouponList.get(i).setStatus(10);
                                    }
                                }
                                couponListAdapter.notifyDataSetChanged();
                            } else {
                               // Toaster.show( data.getMsg());
                                Toaster.show(data.getMsg());
                            }
                        }
                    }
                });
            }
        }
    }

    private List<GameInfoVo.CouponListBean> gameinfoPartCouponList = new ArrayList<>();
    private boolean isShow = false;

    public void gameinfoPartCoupon() {
        if (mViewModel != null) {
            mViewModel.gameinfoPartCoupon(gameid, new OnBaseCallback<NewGameCouponItemVo>() {
                @Override
                public void onSuccess(NewGameCouponItemVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                gameCouponList.clear();
                                for (int i = 0; i < data.getData().size(); i++) {
                                    if ("game_coupon".equals(data.getData().get(i).getCoupon_type())) {
                                        gameCouponList.add(data.getData().get(i));
                                    }
                                }
                                if (gameCouponList.size() > 0) {
                                    startFragment(GameDetailCouponListFragment.newInstance(gameInfoVo.getGameid()));
                                } else {
                                    //ToastT.error("暂无可领取代金券");
                                    Toaster.show("暂无可领取代金券");
                                }
                            } else {
                                //ToastT.error("暂无可领取代金券");
                                Toaster.show("暂无可领取代金券");
                            }
                        } else {
                            //Toaster.show( data.getMsg());
                            Toaster.show(data.getMsg());
                        }
                    }
                }
            });
        }
    }

    public void gameDownloadLog(int id, int type, long duration) {
        if (mViewModel != null) {
            Map<String, String> params = new HashMap<>();
            if (id != 0) {
                params.put("id", String.valueOf(id));
            }
            params.put("type", String.valueOf(type));
            if (duration != 0) {
                params.put("duration", String.valueOf(duration));
            }
            params.put("gameid", String.valueOf(gameid));
            mViewModel.gameDownloadLog(params, new OnBaseCallback<GameDownloadLogVo>() {
                @Override
                public void onSuccess(GameDownloadLogVo data) {
                    if (data != null && data.isStateOK()) {
                        if (id == 0) {
                            fileDownload(data.getData(), type);
                        }
                    }
                }
            });
        }
    }


    public void showExclusiveBenefitDialog(GameInfoVo.DownloadControl download_control) {
        CustomDialog exclusiveBenefitDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_game_detail_exclusive_benefit, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

        if (download_control != null && !TextUtils.isEmpty(download_control.getDialog_content())) {
            ((TextView) exclusiveBenefitDialog.findViewById(R.id.tv_content)).setText(Html.fromHtml(download_control.getDialog_content()));
        } else if (gameInfoVo != null && !TextUtils.isEmpty(gameInfoVo.getBenefit_content())) {
            ((TextView) exclusiveBenefitDialog.findViewById(R.id.tv_content)).setText(Html.fromHtml(gameInfoVo.getBenefit_content()));
        } else {
            ((TextView) exclusiveBenefitDialog.findViewById(R.id.tv_content)).setText("");
        }
        if (download_control.getOpen_download() == 1) {
            exclusiveBenefitDialog.findViewById(R.id.tv_download).setVisibility(View.VISIBLE);
        } else {
            exclusiveBenefitDialog.findViewById(R.id.tv_download).setVisibility(View.GONE);
        }

        exclusiveBenefitDialog.findViewById(R.id.iv_close).setOnClickListener(v -> {
            if (exclusiveBenefitDialog != null && exclusiveBenefitDialog.isShowing()) {
                exclusiveBenefitDialog.dismiss();
            }
        });
        exclusiveBenefitDialog.findViewById(R.id.tv_download).setOnClickListener(v -> {
            if (exclusiveBenefitDialog != null && exclusiveBenefitDialog.isShowing()) {
                exclusiveBenefitDialog.dismiss();
            }
            download();
            start(new GameDownloadManagerFragment());
        });
        exclusiveBenefitDialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if (exclusiveBenefitDialog != null && exclusiveBenefitDialog.isShowing()) {
                exclusiveBenefitDialog.dismiss();
            }
            if (!TextUtils.isEmpty(download_control.getCustomer())) {
                toBrowser(download_control.getCustomer());
            }
        });
        exclusiveBenefitDialog.show();
    }

    public void showMoreDialog() {
        CustomDialog moreDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_game_detail_more, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        moreDialog.findViewById(R.id.tv_action_download).setOnClickListener(v -> {
            if (moreDialog != null && moreDialog.isShowing()) {
                moreDialog.dismiss();
            }
            start(new GameDownloadManagerFragment());
        });
        /*if (gameInfoVo.getGame_type() == 1){
            moreDialog.findViewById(R.id.tv_action_report).setVisibility(View.VISIBLE);
        }else {
            moreDialog.findViewById(R.id.tv_action_report).setVisibility(View.GONE);
        }
        moreDialog.findViewById(R.id.tv_action_report).setOnClickListener(v -> {
            if (moreDialog != null && moreDialog.isShowing()){
                moreDialog.dismiss();
            }
            if (checkLogin()) {
                startFragment(GameReportFragment.newInstance(gameInfoVo.getAllGamename(), gameInfoVo.getGameid()));
            }
        });*/
        /*moreDialog.findViewById(R.id.tv_action_feedback).setOnClickListener(v -> {
            if (moreDialog != null && moreDialog.isShowing()){
                moreDialog.dismiss();
            }
            start(GameFeedbackFragment.newInstance(gameInfoVo.getAllGamename(), gameInfoVo.getGameid()));
        });*/
        moreDialog.findViewById(R.id.tv_action_kefu).setOnClickListener(v -> {
            if (moreDialog != null && moreDialog.isShowing()) {
                moreDialog.dismiss();
            }
            goKefuCenter();
        });
        moreDialog.findViewById(R.id.tv_action_share).setOnClickListener(v -> {
            if (moreDialog != null && moreDialog.isShowing()) {
                moreDialog.dismiss();
            }
            //分享
            if (checkLogin()) {
                if (!TextUtils.isEmpty(gameInfoVo.getShare_url())) {
                    new ShareHelper(_mActivity).shareToAndroidSystem(gameInfoVo.getAllGamename(), gameInfoVo.getGame_summary(), gameInfoVo.getShare_url());
                }

            }
        });
        moreDialog.findViewById(R.id.tv_action_copy).setOnClickListener(v -> {
            if (moreDialog != null && moreDialog.isShowing()) {
                moreDialog.dismiss();
            }
            if (!TextUtils.isEmpty(gameInfoVo.getShare_url())) {
                String copyStr = gameInfoVo.getAllGamename();
                if (!TextUtils.isEmpty(gameInfoVo.getGame_summary())) {
                    copyStr += "\n" + gameInfoVo.getGame_summary();
                }
                copyStr += "\n" + gameInfoVo.getShare_url();
                if (CommonUtils.copyString(_mActivity, copyStr)) {
                    //ToastT.success(_mActivity, "链接已复制");
                    Toaster.show("链接已复制");
                }
            }
        });
        moreDialog.show();
    }

    /**
     * 跳转外部浏览器
     *
     * @param url
     */
    private void toBrowser(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }

    /**
     * 648礼包领取成功弹窗
     */
    public void showLSBTipsDialog() {
        CustomDialog vipTipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_game_detail_draw_648, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        vipTipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if (vipTipsDialog != null && vipTipsDialog.isShowing()) {
                vipTipsDialog.dismiss();
            }
        });
        vipTipsDialog.show();
    }

    /**
     * 648礼包详情弹窗
     */
    public void showLSBDetailDialog() {
        if (gameInfoVo == null) return;
        if (gameInfoVo.getLsb_card_info() == null) return;
        CustomDialog detailDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_game_detail_648_detail, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        TextView mTvCode = detailDialog.findViewById(R.id.tv_code);
        TextView mTvCopy = detailDialog.findViewById(R.id.tv_copy);
        TextView mTvContent = detailDialog.findViewById(R.id.tv_content);
        TextView mTvInstruction = detailDialog.findViewById(R.id.tv_instruction);
        TextView mTvValidity = detailDialog.findViewById(R.id.tv_validity);

        mTvCode.setText("礼包码：" + gameInfoVo.getLsb_card_info().getCard());
        mTvContent.setText("礼包内容：" + gameInfoVo.getLsb_card_info().getCardcontent());
        mTvInstruction.setText("使用说明：" + gameInfoVo.getLsb_card_info().getCardusage());
        mTvValidity.setText("有效期：" + gameInfoVo.getLsb_card_info().getYouxiaoqi());

        mTvCopy.setOnClickListener(v -> {
            if (CommonUtils.copyString(_mActivity, gameInfoVo.getLsb_card_info().getCard())) {
                //ToastT.success("复制成功");
                Toaster.show("复制成功");
            }
        });

        detailDialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if (detailDialog != null && detailDialog.isShowing()) {
                detailDialog.dismiss();
            }
        });
        detailDialog.show();
    }

    public void showDiscountTipsDialog() {
        CustomDialog downloadTipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.dialog_game_detail_discount_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        if (gameInfoVo.getBuilt_in_discount() <= 0 || gameInfoVo.getBuilt_in_discount() >= 10) {//是否有内置折扣
            SpannableString ss = new SpannableString("游戏第一笔充值，任意金额享受" + gameInfoVo.getDiscount() + "折，此后每笔续充享受" + gameInfoVo.getDiscount() + "折。");
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF5447")), 14, 15 + String.valueOf(gameInfoVo.getDiscount()).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF5447")), ss.length() - 2 - String.valueOf(gameInfoVo.getDiscount()).length(), ss.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            ((TextView) downloadTipsDialog.findViewById(R.id.tv_content)).setText(ss);
        } else {
            SpannableString ss = new SpannableString("游戏内置" + gameInfoVo.getBuilt_in_discount() + "折，具体打折形式以游戏内充值档位为准");
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF5447")), 4, 5 + String.valueOf(gameInfoVo.getBuilt_in_discount()).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            ((TextView) downloadTipsDialog.findViewById(R.id.tv_content)).setText(ss);
        }

        downloadTipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if (downloadTipsDialog != null && downloadTipsDialog.isShowing()) {
                downloadTipsDialog.dismiss();
            }
        });
        downloadTipsDialog.show();
    }

    public void clickSXW() {
        String v1 = "", v2 = "", v3 = "";
        if (gameInfoVo != null) {
            v1 = gameInfoVo.getGamename();
            v2 = gameInfoVo.getOtherGameName();
            v3 = gameInfoVo.getGameicon();
        }
        if (!checkLogin()) {
            return;
        }
        startFragment(EasyToPlayFragment.newInstance(v1, v2, v3, gameid + ""));
    }

    /*public List<ChatMsgListVo.DataBean> chatList;

    private void getChatMessage() {
        if (mViewModel != null) {
            mViewModel.getChatMessage(String.valueOf(gameid), new OnBaseCallback<ChatMsgListVo>() {
                @Override
                public void onSuccess(ChatMsgListVo data) {
                    if (data != null && data.isStateOK()) {
                        if (data.getData() != null && data.getData().size() > 0) {
                            chatList = data.getData();
                        }
                    }
                }
            });
        }
    }*/

    /*public void addChat() {
        if (checkLogin()) {
            UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
            if ((TextUtils.isEmpty(userInfo.getReal_name()) || TextUtils.isEmpty(userInfo.getIdcard()))) {
                FragmentHolderActivity.startFragmentInActivity(_mActivity, CertificationFragment.newInstance());
                return;
            }
            if (mViewModel != null) {
                showLoading();
                mViewModel.addChat(String.valueOf(gameid), new OnBaseCallback<AddChatVo>() {

                    @Override
                    public void onSuccess(AddChatVo data) {
                        if (data != null && data.isStateOK()) {
                            if (data.getData() != null) {
                                getNetWorkData(String.valueOf(data.getData().getTid()), false);
                            }
                        }else {
                            showSuccess();
                            ToastT.error(data.getMsg());
                        }
                    }
                });
            }
        }
    }*/

    /*private void chatActivityRecommend(String teamId, boolean showLoad) {
        if (mViewModel != null) {
            if (showLoad) showLoading();
            mViewModel.chatActivityRecommend(teamId, new OnBaseCallback<ChatActivityRecommendVo>() {
                @Override
                public void onSuccess(ChatActivityRecommendVo data) {
                    if (data != null && data.isStateOK()) {
                        Setting.activityList = data.getData();
                        Setting.tid = teamId;
                        chatTeamNotice(teamId);
                    }else {
                        showSuccess();
                    }
                }
            });
        }
    }*/

    /*private void chatTeamNotice(String teamId) {
        if (mViewModel != null) {
            mViewModel.chatTeamNotice(teamId, "1", new OnBaseCallback<ChatTeamNoticeListVo>() {
                @Override
                public void onSuccess(ChatTeamNoticeListVo data) {
                    showSuccess();
                    if (data != null && data.isStateOK()) {
                        Setting.noticeList = data.getData();
                        Team team = NIMClient.getService(TeamService.class).queryTeamBlock(teamId);
                        if (team != null) {
                            //跳转到群聊界面
                            XKitRouter.withKey(RouterConstant.PATH_CHAT_TEAM_PAGE).withParam(RouterConstant.CHAT_KRY, team).withParam("gameid", gameid).withParam("gametype", game_type).withContext(_mActivity).navigate();
                        }
                    }
                }
            });
        }
    }*/

   /* private CountDownLatch countDownLatch;
    private void getNetWorkData(String teamId, boolean showLoad) {
        if (mViewModel != null) {
            if (showLoad) showLoading();
            countDownLatch = new CountDownLatch(2);

            mViewModel.chatActivityRecommend(teamId, new OnBaseCallback<ChatActivityRecommendVo>() {
                @Override
                public void onSuccess(ChatActivityRecommendVo data) {
                    if (data != null && data.isStateOK()) {
                        Setting.activityList = data.getData();
                        Setting.tid = teamId;
                    }
                    countDownLatch.countDown();
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    countDownLatch.countDown();
                }
            });
            mViewModel.chatTeamNotice(teamId, "1", new OnBaseCallback<ChatTeamNoticeListVo>() {
                @Override
                public void onSuccess(ChatTeamNoticeListVo data) {
                    if (data != null && data.isStateOK()) {
                        Setting.noticeList = data.getData();
                    }
                    countDownLatch.countDown();
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    countDownLatch.countDown();
                }
            });

            new Thread(() -> {
                try {
                    countDownLatch.await();
                    new Handler(Looper.getMainLooper()).post(() -> {
                        Team team = NIMClient.getService(TeamService.class).queryTeamBlock(teamId);
                        if (team != null){
                            //跳转到群聊界面
                            XKitRouter.withKey(RouterConstant.PATH_CHAT_TEAM_PAGE).withParam(RouterConstant.CHAT_KRY, team).withParam("gameid", gameInfoVo.getGameid()).withParam("gametype", gameInfoVo.getGame_type()).withContext(_mActivity).navigate();
                        }
                    });
                    showSuccess();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        if (gameInfoVo != null) {
            Progress progress = DownloadManager.getInstance().get(gameInfoVo.getGameDownloadTag());
            if (progress != null) {
                DownloadTask task = OkDownload.restore(progress);
                task.unRegister(downloadListener);
            }
        }
    }
}
