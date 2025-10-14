package com.zqhy.app.core.view.main.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaoji.im.glide.GlideApp;
import com.chaoji.other.blankj.utilcode.util.Logs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;
import com.zqhy.app.core.data.model.splash.AppStyleConfigs;
import com.zqhy.app.core.data.model.splash.SplashVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.db.table.message.MessageDbInstance;
import com.zqhy.app.glide.GlideCircleTransform;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.network.statistics.JiuYaoStatisticsApi;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.cache.ACache;
import com.zqhy.app.utils.sdcard.SdCardManager;
import com.zqhy.app.utils.sp.SPUtils;
import com.zqhy.app.widget.DividerGridItemDecoration;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/12
 */

public class MainPagerDialogHelper {

    private Context mContext;
    private OnMainPagerClickListener onMainPagerClickListener;

    public MainPagerDialogHelper(Context mContext, OnMainPagerClickListener onMainPagerClickListener) {
        this.mContext = mContext;
        this.onMainPagerClickListener = onMainPagerClickListener;
    }


    private CustomDialog mainPagerDialog;

    private FrameLayout mVaOutput;
    private LinearLayout mLlUserHeader;
    private AppCompatImageView mProfileImage;
    private LinearLayout mLlLayoutLogin;
    private TextView mTvUserNickname;
    private TextView mTvUsername;
    private TextView mTvBindPhone;
    private LinearLayout mLlLayoutNoLogin;
    private ImageView mIvArrowRight;
    private LinearLayout mLlLayoutLoginBanner;
    private TextView mTvLoginTips;
    private FrameLayout mFlUserGold;
    private TextView mTvUserGold;
    private FrameLayout mFlUserIntegral;
    private TextView mTvUserIntegral;
    private RecyclerView mMenuRecyclerView1;
    private RecyclerView mMenuRecyclerView2;
    private FrameLayout mFlBottomBtn1;
    private TextView mTvBottomBtn1;
    private TextView mTvClose;
    private FrameLayout mFlBottomBtn2;
    private TextView mTvBottomBtn2;

    private TextView mTvUnReceiveIntegralCount;
    private View mViewPointIntegralCount;

    public void showMainPagerDialog() {
        if (mainPagerDialog == null) {
            mainPagerDialog = new CustomDialog(mContext, LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_main_pager, null),
                    WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.BOTTOM);
            initMainMenuView();
            mMenuRecyclerView1 = mainPagerDialog.findViewById(R.id.menu_recyclerView_1);
            mMenuRecyclerView2 = mainPagerDialog.findViewById(R.id.menu_recyclerView_2);
            initMainBottomView();
            initMenuList1();
            initMenuList2();
            setAppAdBanner();
        }
        refreshMainPagerDialog();
        mainPagerDialog.show();
    }

    public void refreshMainPagerDialog() {
        refreshMessageCount();
        setMineDialogUserView();
    }

    /**
     * 设置App主题样式
     * 首页顶部样式
     */
    private void setAppAdBanner() {
        LinearLayout mItemAdBanner = mainPagerDialog.findViewById(R.id.item_ad_banner);
        LinearLayout mLlNewsBanner = mainPagerDialog.findViewById(R.id.ll_news_banner);
        View mViewLine = mainPagerDialog.findViewById(R.id.view_line);
        ImageView mIvNewsBanner = mainPagerDialog.findViewById(R.id.iv_news_banner);

        mItemAdBanner.setVisibility(View.GONE);
        File file = SdCardManager.getFileMenuDir(mContext);
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

            if (appStyleVo != null) {
                if (appStyleVo.getInterstitial() != null) {
                    mItemAdBanner.setVisibility(View.VISIBLE);
                    AppJumpInfoBean item = appStyleVo.getInterstitial();

                    GlideUtils.loadNormalImage(mContext, item.getPic(), mIvNewsBanner, R.mipmap.img_placeholder_v_3);
                    mIvNewsBanner.setOnClickListener(view -> {
                        if (mContext instanceof Activity) {
                            AppJumpAction appJumpAction = new AppJumpAction((Activity) mContext);
                            appJumpAction.jumpAction(item);
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mItemAdBanner.setVisibility(View.GONE);
        }
    }


    /**
     * 刷新消息未读数量
     */
    public void refreshMessageCount() {
        if (mMainMenu1Adapter != null) {
            Runnable runnable = () -> {
                int messageCount = MessageDbInstance.getInstance().getUnReadMessageCount(1);
                ((Activity) mContext).runOnUiThread(() -> {
                    mMainMenu1Adapter.setMessageCount(messageCount);
                    mMainMenu1Adapter.notifyDataSetChanged();
                });
            };
            new Thread(runnable).start();
        }
    }


    /**
     * 设置用户信息
     */
    private void setMineDialogUserView() {
        UserInfoVo.DataBean userInfoBean = UserInfoModel.getInstance().getUserInfo();
        if (userInfoBean != null) {
            mLlLayoutLogin.setVisibility(View.VISIBLE);
            mLlLayoutNoLogin.setVisibility(View.GONE);
            mIvArrowRight.setVisibility(View.VISIBLE);
            mProfileImage.setImageResource(R.mipmap.ic_user_login);
            GlideApp.with(mContext).asBitmap()
                    .load(userInfoBean.getUser_icon())
                    .error(R.mipmap.ic_user_login)
                    .transform(new GlideCircleTransform(mContext, (int) (3 * ScreenUtil.getScreenDensity(mContext))))
                    .into(mProfileImage);

            mTvUserNickname.setText(userInfoBean.getUser_nickname());
            mTvUsername.setText("用户名：" + userInfoBean.getUsername());

            String phone = userInfoBean.getMobile();
            if (TextUtils.isEmpty(phone)) {
                phone = "未绑定";
            }
            mTvBindPhone.setText("绑定手机：" + phone);

            mLlUserHeader.setOnClickListener(view -> {
                if (onMainPagerClickListener != null) {
                    dismissMainPagerDialog();
                    onMainPagerClickListener.onClick(R.id.main_page_id_user);
                }
            });
            mTvLoginTips.setText("若未绑定手机，则用户名为唯一登录凭证，请牢记！");

            ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_ff0000));
            //设置金币
            String fGold = String.valueOf(userInfoBean.getPingtaibi());
            SpannableString spGold = new SpannableString("钱包余额：" + fGold);
            spGold.setSpan(colorSpan, 5, spGold.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTvUserGold.setText(spGold);

            //设置积分
            String strIntegral = String.valueOf(userInfoBean.getIntegral());
            SpannableString spIntegral = new SpannableString("我的积分：" + strIntegral);
            spIntegral.setSpan(colorSpan, 5, spIntegral.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTvUserIntegral.setText(spIntegral);

            mIvArrowRight.setVisibility(View.VISIBLE);
        } else {
            mLlLayoutLogin.setVisibility(View.GONE);
            mLlLayoutNoLogin.setVisibility(View.VISIBLE);
            mIvArrowRight.setVisibility(View.GONE);
            mProfileImage.setImageResource(R.mipmap.ic_user_un_login);
            mLlUserHeader.setOnClickListener(view -> {
                //登录
                if (onMainPagerClickListener != null) {
                    dismissMainPagerDialog();
                    onMainPagerClickListener.onClick(R.id.main_page_id_login);
                }
            });

            ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_ff0000));

            String strGold = "0.00";
            SpannableString spGold = new SpannableString("钱包余额：" + strGold);
            spGold.setSpan(colorSpan, 5, spGold.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTvUserGold.setText(spGold);

            String strIntegral = String.valueOf(0);
            SpannableString spIntegral = new SpannableString("我的积分：" + strIntegral);
            spIntegral.setSpan(colorSpan, 5, spIntegral.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTvUserIntegral.setText(spIntegral);

            mTvLoginTips.setText("用户名、绑定手机号均可用于登录哦！");
            mIvArrowRight.setVisibility(View.GONE);
        }
    }


    private void initMainMenuView() {
        mVaOutput = mainPagerDialog.findViewById(R.id.va_output);
        mLlUserHeader = mainPagerDialog.findViewById(R.id.ll_user_header);
        mProfileImage = mainPagerDialog.findViewById(R.id.profile_image);
        mLlLayoutLogin = mainPagerDialog.findViewById(R.id.ll_layout_login);
        mTvUserNickname = mainPagerDialog.findViewById(R.id.tv_user_nickname);
        mTvUsername = mainPagerDialog.findViewById(R.id.tv_username);
        mTvBindPhone = mainPagerDialog.findViewById(R.id.tv_bind_phone);
        mLlLayoutNoLogin = mainPagerDialog.findViewById(R.id.ll_layout_no_login);
        mIvArrowRight = mainPagerDialog.findViewById(R.id.iv_arrow_right);
        mLlLayoutLoginBanner = mainPagerDialog.findViewById(R.id.ll_layout_login_banner);
        mTvLoginTips = mainPagerDialog.findViewById(R.id.tv_login_tips);
        mFlUserGold = mainPagerDialog.findViewById(R.id.fl_user_gold);
        mTvUserGold = mainPagerDialog.findViewById(R.id.tv_user_gold);

        mFlUserIntegral = mainPagerDialog.findViewById(R.id.fl_user_integral);
        mTvUserIntegral = mainPagerDialog.findViewById(R.id.tv_user_integral);

        mTvUnReceiveIntegralCount = mainPagerDialog.findViewById(R.id.tv_un_receive_integral_count);
        mViewPointIntegralCount = mainPagerDialog.findViewById(R.id.view_point_integral_count);

        mViewPointIntegralCount.setVisibility(View.GONE);

        GradientDrawable gd = new GradientDrawable();
        float density = ScreenUtil.getScreenDensity(mContext);
        gd.setCornerRadius(16 * density);
        gd.setColor(Color.parseColor("#292241c2"));
        mLlLayoutLoginBanner.setBackground(gd);

        mFlUserGold.setOnClickListener(view -> {
            if (onMainPagerClickListener != null) {
                dismissMainPagerDialog();
                onMainPagerClickListener.onClick(R.id.main_page_id_gold);
            }
        });
        mFlUserIntegral.setOnClickListener(view -> {
            if (onMainPagerClickListener != null) {
                dismissMainPagerDialog();
                mViewPointIntegralCount.setVisibility(View.GONE);
                setShowPointIntegralCount();
                onMainPagerClickListener.onClick(R.id.main_page_id_integral);
            }
        });
    }

    private void initMainBottomView() {
        float density = ScreenUtil.getScreenDensity(mContext);

        mFlBottomBtn1 = mainPagerDialog.findViewById(R.id.fl_bottom_btn_1);
        mTvBottomBtn1 = mainPagerDialog.findViewById(R.id.tv_bottom_btn_1);
        mTvClose = mainPagerDialog.findViewById(R.id.tv_close);
        mFlBottomBtn2 = mainPagerDialog.findViewById(R.id.fl_bottom_btn_2);
        mTvBottomBtn2 = mainPagerDialog.findViewById(R.id.tv_bottom_btn_2);
        mTvClose.setOnClickListener(view -> {
            dismissMainPagerDialog();
            JiuYaoStatisticsApi.getInstance().eventStatistics(6, 92);
        });

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(16 * density);
        gd.setColor(ContextCompat.getColor(mContext, R.color.white));
        gd.setStroke((int) (0.5 * density), ContextCompat.getColor(mContext, R.color.color_cccccc));
        mTvBottomBtn1.setTextColor(ContextCompat.getColor(mContext, R.color.color_333333));
        mTvBottomBtn1.setBackground(gd);

        mTvBottomBtn2.setTextColor(ContextCompat.getColor(mContext, R.color.color_333333));
        mTvBottomBtn2.setBackground(gd);

        mTvBottomBtn1.setText("邀请赚钱");
        mTvBottomBtn2.setText("联系客服");

        mFlBottomBtn1.setOnClickListener(view -> {
            if (onMainPagerClickListener != null) {
                dismissMainPagerDialog();
                onMainPagerClickListener.onClick(R.id.main_page_id_bottom_button_1);
            }
        });
        mFlBottomBtn1.setVisibility(View.GONE);

        mFlBottomBtn2.setOnClickListener(view -> {
            if (onMainPagerClickListener != null) {
                dismissMainPagerDialog();
                onMainPagerClickListener.onClick(R.id.main_page_id_bottom_button_2);
            }
        });
    }

    private void dismissMainPagerDialog() {
        if (mainPagerDialog != null && mainPagerDialog.isShowing()) {
            mainPagerDialog.dismiss();
        }
    }

    private MainMenuAdapter mMainMenu1Adapter;

    private void initMenuList1() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mMenuRecyclerView1.setLayoutManager(layoutManager);
        List<MainMenuBean> mainMenu1List = getMainMenu1List();

        mMainMenu1Adapter = new MainMenuAdapter(mContext, mainMenu1List);
        mMenuRecyclerView1.setAdapter(mMainMenu1Adapter);
    }

    private List<MainMenuBean> getMainMenu1List() {
        MainMenuBean mainMenuBean1 = new MainMenuBean(0, R.id.tag_main_menu_1, R.mipmap.ic_main_menu_1, "省钱攻略");
        MainMenuBean mainMenuBean2 = new MainMenuBean(0, R.id.tag_main_menu_2, R.mipmap.ic_main_menu_2, "消息");
        MainMenuBean mainMenuBean3 = new MainMenuBean(0, R.id.tag_main_menu_3, R.mipmap.ic_main_menu_3, "充值明细");
        MainMenuBean mainMenuBean4 = new MainMenuBean(0, R.id.tag_main_menu_4, R.mipmap.ic_main_menu_4, "设置");

        List<MainMenuBean> mainMenuBeanList = new ArrayList<>();

        mainMenuBeanList.add(mainMenuBean1);
        mainMenuBeanList.add(mainMenuBean2);
        mainMenuBeanList.add(mainMenuBean3);
        mainMenuBeanList.add(mainMenuBean4);

        return mainMenuBeanList;
    }

    private MainMenuAdapter mMainMenu2Adapter;

    private void initMenuList2() {
        GridLayoutManager shortCutMenuLayoutManager = new GridLayoutManager(mContext, 3);
        mMenuRecyclerView2.setLayoutManager(shortCutMenuLayoutManager);

        mMenuRecyclerView2.addItemDecoration(new DividerGridItemDecoration(mContext, ContextCompat.getColor(mContext, R.color.color_f1f1f1)));

        List<MainMenuBean> mainMenuBeanList = getMainMenu2List();

        mMainMenu2Adapter = new MainMenuAdapter(mContext, mainMenuBeanList);
        mMenuRecyclerView2.setAdapter(mMainMenu2Adapter);
    }

    private List<MainMenuBean> getMainMenu2List() {
        MainMenuBean 试玩赚钱 = new MainMenuBean(1, R.id.tag_main_menu_try_game, R.mipmap.ic_main_menu_grid_try_game, "试玩赚钱", "海量积分奖励");
        试玩赚钱.setNew(true);

        MainMenuBean 小号回收 = new MainMenuBean(1, R.id.tag_main_menu_xh_recycle, R.mipmap.ic_main_menu_grid_xh_recycle, "小号回收", "不玩来换钱！");

        MainMenuBean 我的游戏 = new MainMenuBean(1, R.id.tag_main_menu_user_games, R.mipmap.ic_main_menu_grid_user_games, "我的游戏", "游戏/礼包/优惠券");
        MainMenuBean 奖励申请 = new MainMenuBean(1, R.id.tag_main_menu_rewards, R.mipmap.ic_main_menu_grid_rewards, "奖励申请", "BT/折扣/H5游戏");
        MainMenuBean 转游福利 = new MainMenuBean(1, R.id.tag_main_menu_welfare, R.mipmap.ic_main_menu_grid_welfare, "转游福利", "游戏停运补偿");
//        MainMenuBean 开服表 = new MainMenuBean(1, R.id.tag_main_menu_server, R.mipmap.ic_main_menu_grid_server, "开服表", "最新服，找游戏");
        MainMenuBean 游戏分类 = new MainMenuBean(1, R.id.tag_main_menu_game_classification, R.mipmap.ic_main_menu_grid_classification, "游戏分类", "找游戏，更多选择");

        MainMenuBean 活动公告 = new MainMenuBean(1, R.id.tag_main_menu_activity, R.mipmap.ic_main_menu_grid_activity, "活动&公告", "活动/公告/通知");
        MainMenuBean 敬请期待 = new MainMenuBean(1, R.id.tag_main_menu_more, R.mipmap.ic_main_menu_grid_more, "敬请期待", "");

        List<MainMenuBean> mainMenuBeanList = new ArrayList<>();

        //2019.05.13 屏蔽“试玩赚钱”和“小号回收”入口
//        mainMenuBeanList.add(试玩赚钱);
//        mainMenuBeanList.add(小号回收);


        mainMenuBeanList.add(我的游戏);
        mainMenuBeanList.add(奖励申请);
        mainMenuBeanList.add(试玩赚钱);

        mainMenuBeanList.add(游戏分类);
        mainMenuBeanList.add(活动公告);
        mainMenuBeanList.add(转游福利);

//        mainMenuBeanList.add(敬请期待);

        return mainMenuBeanList;
    }


    public static class MainMenuBean {
        private int menuId;
        private int menuRes;
        private String menuName;
        private String menuDes;

        /*******0 menu   1 shortcut************/
        private int type;

        /**
         * 横向列表
         *
         * @param menuId
         * @param menuRes
         * @param menuName
         * @param type
         */
        public MainMenuBean(int type, int menuId, int menuRes, String menuName) {
            this.menuId = menuId;
            this.menuRes = menuRes;
            this.menuName = menuName;
            this.type = type;
        }

        /**
         * 网格列表
         *
         * @param menuId
         * @param menuRes
         * @param menuName
         * @param menuDes
         * @param type
         */
        public MainMenuBean(int type, int menuId, int menuRes, String menuName, String menuDes) {
            this.menuId = menuId;
            this.menuRes = menuRes;
            this.menuName = menuName;
            this.menuDes = menuDes;
            this.type = type;
        }

        public int getMenuId() {
            return menuId;
        }

        public void setMenuId(int menuId) {
            this.menuId = menuId;
        }

        public int getMenuRes() {
            return menuRes;
        }

        public void setMenuRes(int menuRes) {
            this.menuRes = menuRes;
        }

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getMenuDes() {
            return menuDes;
        }

        private boolean isNew;

        public boolean isNew() {
            return isNew;
        }

        public void setNew(boolean aNew) {
            isNew = aNew;
        }
    }

    class MainMenuAdapter extends RecyclerView.Adapter {

        private Context mContext;
        private List<MainMenuBean> mainMenuBeanList;

        private int messageCount;

        public void setMessageCount(int messageCount) {
            this.messageCount = messageCount;
        }

        private float density;

        public MainMenuAdapter(Context mContext, List<MainMenuBean> mainMenuBeanList) {
            this.mContext = mContext;
            this.mainMenuBeanList = mainMenuBeanList;
            density = ScreenUtil.getScreenDensity(mContext);
        }

        public void clear() {
            this.mainMenuBeanList.clear();
        }

        public void addAll(List<MainMenuBean> mDatas) {
            this.mainMenuBeanList.addAll(mDatas);
            notifyItemRangeInserted(this.mainMenuBeanList.size() - mDatas.size(), this.mainMenuBeanList.size());
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 0) {
                return new MainMenu1Holder(LayoutInflater.from(mContext).inflate(R.layout.item_main_menu_1, null));
            } else if (viewType == 1) {
                return new MainMenu2Holder(LayoutInflater.from(mContext).inflate(R.layout.item_main_menu_2, null));
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MainMenuBean mainMenuBean = mainMenuBeanList.get(position);
            if (getItemViewType(position) == 0) {
                MainMenu1Holder menu1Holder = (MainMenu1Holder) holder;
                menu1Holder.mTvMessageCount.setVisibility(View.GONE);
                if (mainMenuBean.getType() == 0) {
                    menu1Holder.mTvMenuText.setId(mainMenuBean.getMenuId());
                    menu1Holder.mTvMenuText.setText(mainMenuBean.getMenuName());
                    menu1Holder.mTvMenuText.setCompoundDrawablesWithIntrinsicBounds(null, mContext.getResources().getDrawable(mainMenuBean.getMenuRes()), null, null);

                    if (mainMenuBean.getMenuId() == R.id.tag_main_menu_2 && messageCount > 0) {
                        menu1Holder.mTvMessageCount.setVisibility(View.VISIBLE);

                        GradientDrawable gd = new GradientDrawable();
                        gd.setCornerRadius(density * 16);
                        gd.setColor(ContextCompat.getColor(mContext, R.color.color_ff0000));

                        menu1Holder.mTvMessageCount.setBackgroundDrawable(gd);
                        String strCount = String.valueOf(messageCount);
                        if (messageCount > 99) {
                            strCount = "99+";
                        }
                        menu1Holder.mTvMessageCount.setText(strCount);
                    }
                    menu1Holder.mTvMenuText.setOnClickListener(view -> {
                        if (onMainPagerClickListener != null) {
                            dismissMainPagerDialog();
                            onMainPagerClickListener.onClick(view.getId());
                        }
                    });
                }

            } else if (getItemViewType(position) == 1) {
                MainMenu2Holder menu2Holder = (MainMenu2Holder) holder;

                menu2Holder.mRootView.setId(mainMenuBean.getMenuId());
                menu2Holder.mIvMenuIcon.setImageResource(mainMenuBean.getMenuRes());
                menu2Holder.mTvMenuText1.setText(mainMenuBean.getMenuName());
                menu2Holder.mTvMenuText2.setText(mainMenuBean.getMenuDes());

                menu2Holder.mTvMenuText2.setVisibility(TextUtils.isEmpty(mainMenuBean.getMenuDes()) ? View.GONE : View.VISIBLE);
                menu2Holder.mRootView.setOnClickListener(view -> {
                    if (onMainPagerClickListener != null) {
                        dismissMainPagerDialog();
                        onMainPagerClickListener.onClick(view.getId());
                    }
                });
                menu2Holder.mIvVersionNew.setVisibility(mainMenuBean.isNew() ? View.VISIBLE : View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return mainMenuBeanList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return mainMenuBeanList.get(position).getType();
        }


        class MainMenu1Holder extends RecyclerView.ViewHolder {
            private TextView mTvMenuText;
            private TextView mTvMessageCount;

            public MainMenu1Holder(View itemView) {
                super(itemView);
                mTvMenuText = itemView.findViewById(R.id.tv_menu_text);
                mTvMessageCount = itemView.findViewById(R.id.tv_message_count);
            }
        }

        class MainMenu2Holder extends RecyclerView.ViewHolder {
            private LinearLayout mRootView;
            private ImageView mIvMenuIcon;
            private TextView mTvMenuText1;
            private TextView mTvMenuText2;
            private ImageView mIvVersionNew;

            public MainMenu2Holder(View itemView) {
                super(itemView);
                mRootView = itemView.findViewById(R.id.rootView);
                mIvMenuIcon = itemView.findViewById(R.id.iv_menu_icon);
                mTvMenuText1 = itemView.findViewById(R.id.tv_menu_text_1);
                mTvMenuText2 = itemView.findViewById(R.id.tv_menu_text_2);
                mIvVersionNew = itemView.findViewById(R.id.iv_version_new);

            }
        }

    }


    private String SP_MAIN_DIALOG_INTEGRAL_TIMESTAMP_TAG = "SP_MAIN_DIALOG_INTEGRAL_TIMESTAMP_TAG";

    private boolean isShowPointIntegralCount() {
        SPUtils spUtils = new SPUtils(mContext, Constants.SP_COMMON_NAME);
        long lastTimeStamp = spUtils.getLong(SP_MAIN_DIALOG_INTEGRAL_TIMESTAMP_TAG);
        long restTimeOfDay = getRestTimeOfDay();

        return lastTimeStamp + restTimeOfDay < System.currentTimeMillis();
    }

    /**
     * @return
     */
    private long getRestTimeOfDay() {
        Calendar midnight = Calendar.getInstance();
        midnight.setTime(new Date());
        midnight.add(Calendar.DAY_OF_MONTH, 1);
        midnight.set(Calendar.HOUR_OF_DAY, 0);
        midnight.set(Calendar.MINUTE, 0);
        midnight.set(Calendar.SECOND, 0);
        midnight.set(Calendar.MILLISECOND, 0);
        long restTimeOfDay = (midnight.getTime().getTime() - System.currentTimeMillis());

        Logs.e("restTimeOfDay = " + restTimeOfDay);
        return restTimeOfDay;
    }

    private void setShowPointIntegralCount() {
        SPUtils spUtils = new SPUtils(mContext, Constants.SP_COMMON_NAME);
        spUtils.putLong(SP_MAIN_DIALOG_INTEGRAL_TIMESTAMP_TAG, System.currentTimeMillis());
    }

}
