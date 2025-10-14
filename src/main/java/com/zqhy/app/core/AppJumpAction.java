package com.zqhy.app.core;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.chaoji.other.hjq.toast.Toaster;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.event.LiveBus;
import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.data.model.share.InviteDataVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.FragmentHolderActivity;
import com.zqhy.app.core.view.activity.MainActivityFragment;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.classification.GameClassificationFragment;
import com.zqhy.app.core.view.community.comment.UserCommentCenterFragment;
import com.zqhy.app.core.view.community.integral.CommunityIntegralMallFragment;
import com.zqhy.app.core.view.community.qa.UserQaCollapsingCenterFragment;
import com.zqhy.app.core.view.community.task.TaskCenterFragment;
import com.zqhy.app.core.view.game.GameDetailCouponListFragment;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.core.view.game.coupon.GameCouponsListFragment;
import com.zqhy.app.core.view.game.forum.ForumDetailFragment;
import com.zqhy.app.core.view.game.forum.ForumLongDetailFragment;
import com.zqhy.app.core.view.invite.InviteFriendFragment;
import com.zqhy.app.core.view.kefu.FeedBackFragment;
import com.zqhy.app.core.view.kefu.KefuCenterFragment;
import com.zqhy.app.core.view.kefu.KefuHelperFragment;
import com.zqhy.app.core.view.login.LoginActivity;
import com.zqhy.app.core.view.main.GameCollectionListFragment;
import com.zqhy.app.core.view.main.MainGameClassification2Fragment;
import com.zqhy.app.core.view.main.new0809.MainPageGameCollectionFragment;
import com.zqhy.app.core.view.main.new0809.NewGameRankingActivityFragment;
import com.zqhy.app.core.view.main.new0809.NewMainPageGameCollectionFragment;
import com.zqhy.app.core.view.main.new_game.NewGameAppointmentFragment;
import com.zqhy.app.core.view.main.new_game.NewGameStartingFragment;
import com.zqhy.app.core.view.rebate.RebateMainFragment;
import com.zqhy.app.core.view.recycle_new.XhNewRecycleMainFragment;
import com.zqhy.app.core.view.server.TsServerListFragment;
import com.zqhy.app.core.view.strategy.DiscountStrategyFragment;
import com.zqhy.app.core.view.transaction.TransactionGoodDetailFragment;
import com.zqhy.app.core.view.transaction.TransactionMainFragment1;
import com.zqhy.app.core.view.transaction.TransactionOneBuyFragment;
import com.zqhy.app.core.view.transaction.TransactionZeroBuyFragment;
import com.zqhy.app.core.view.transfer.TransferMainFragment;
import com.zqhy.app.core.view.tryplay.TryGamePlayListFragment;
import com.zqhy.app.core.view.tryplay.TryGameTaskFragment;
import com.zqhy.app.core.view.user.BindPhoneFragment;
import com.zqhy.app.core.view.user.CertificationFragment;
import com.zqhy.app.core.view.user.TopUpFragment;
import com.zqhy.app.core.view.user.UserInfoFragment;
import com.zqhy.app.core.view.user.newvip.NewUserVipFragment;
import com.zqhy.app.core.view.user.provincecard.NewProvinceCardFragment;
import com.zqhy.app.core.view.user.provincecard.ProvinceCardExchangeFragment;
import com.zqhy.app.core.view.user.welfare.MyCardListFragment;
import com.zqhy.app.core.view.user.welfare.MyCouponsListFragment;
import com.zqhy.app.core.view.user.welfare.MyFavouriteGameListFragment;
import com.zqhy.app.core.vm.BaseViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.share.ShareHelper;

import java.lang.reflect.Type;

/**
 * @author Administrator
 * @date 2018/4/3
 */

public class AppJumpAction {

    private Activity     _mActivity;
    private BaseFragment mBaseFragment;

    public AppJumpAction(Activity mBaseActivity) {
        this._mActivity = mBaseActivity;
    }

    public AppJumpAction(Activity mBaseActivity, BaseFragment mBaseFragment) {
        this._mActivity = mBaseActivity;
        this.mBaseFragment = mBaseFragment;
    }

    public void jumpAction(String json) {
        try {
            Type listType = new TypeToken<AppBaseJumpInfoBean>() {
            }.getType();
            Gson gson = new Gson();
            AppBaseJumpInfoBean jumpInfoBean = gson.fromJson(json, listType);

            if (jumpInfoBean != null) {
                jumpAction(jumpInfoBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void jumpAction(AppBaseJumpInfoBean jumpInfoBean) {
        if (jumpInfoBean == null) {
            return;
        }
        if (jumpInfoBean.getPage_type() == null) {
            return;
        }
        Logs.e("jumpInfoBean = " + jumpInfoBean);
        Logs.json(new Gson().toJson(jumpInfoBean));
        AppBaseJumpInfoBean.ParamBean paramBean = jumpInfoBean.getParam();
        switch (jumpInfoBean.getPage_type()) {
            case "gameinfo": {
                //1.游戏详情页
                if (paramBean != null) {
                    goGameDetail(paramBean.getGameid(), paramBean.getGame_type());
                }
                break;
            }
            case "activityinfo": {
                //2.活动详情页
                if (paramBean != null) {
                    if (!TextUtils.isEmpty(paramBean.getNewsid())) {
                        //待定
                    }
                }
                break;
            }
            case "url": {
                //3.网页链接
                if (paramBean != null) {
                    if (!TextUtils.isEmpty(paramBean.getTarget_url())) {
                        BrowserActivity.newInstance(_mActivity, paramBean.getTarget_url());
                    }
                }
                break;
            }
            case "reward": {
                //4.返利申请页面（总页面 : 0/BT : 1/H5 : 3/折扣:2）
                if (checkLogin()) {
                    startFragment(new RebateMainFragment());
                    //                    int game_type = paramBean.getGame_type();
                    //                    if (game_type == 0) {
                    //                    } else {
                    //                        startFragment(RebateListFragment.newInstance(game_type));
                    //                    }
                }
                break;
            }
            case "gamelist": {
                //5.游戏合集
                if (paramBean != null) {
                    try {
                        String container_id = paramBean.getGame_list_id();
                        startFragment(GameCollectionListFragment.newInstance(Integer.parseInt(container_id)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case "gonglv": {
                //6.省钱攻略
                startFragment(new DiscountStrategyFragment());
                break;
            }
            case "kefu": {
                //7.客服中心
                startFragment(new KefuHelperFragment());
                break;
            }
            case "kefu_center": {
                //21.客服中心
                startFragment(new KefuCenterFragment());
                break;
            }
            case "gold": {
                //8.金币充值
                if (checkLogin()) {
                    startFragment(new TopUpFragment());
                }
                break;
            }
            case "inivite":
            case "usercenter_inivite": {
                //9.邀请好友
                //46 个人中心-邀请赚钱
                if (checkLogin()) {
                    UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
                    if (userInfo.getInvite_type() == 1) {
                        if (mBaseFragment.getViewModel() != null && mBaseFragment.getViewModel() instanceof BaseViewModel) {
                            ((BaseViewModel) mBaseFragment.getViewModel()).getShareData("1", new OnBaseCallback<InviteDataVo>() {

                                @Override
                                public void onSuccess(InviteDataVo data) {
                                    if (data != null && data.isStateOK() && data.getData() != null){
                                        if (data.getData().getInvite_info() != null){
                                            InviteDataVo.InviteDataInfoVo inviteInfo = data.getData().getInvite_info();
                                            new ShareHelper(_mActivity).shareToAndroidSystem(inviteInfo.getCopy_title(), inviteInfo.getCopy_description(), inviteInfo.getUrl());
                                        }
                                    }
                                }
                            });
                        }
                    } else {
                        startFragment(new InviteFriendFragment());
                    }
                }
                break;
            }
            case "money": {
                //10.福利中心（赚钱任务）
                startFragment(TaskCenterFragment.newInstance());
                break;
            }
            case "gamecenter": {
                //11.游戏大厅
                startFragment(new GameClassificationFragment());
                break;
            }
            case "appopinion": {
                //12.App建议
                if (checkLogin()) {
                    startFragment(new FeedBackFragment());
                }
                break;
            }
            case "transfer": {
                //13.停运转游
                if (checkLogin()) {
                    startFragment(new TransferMainFragment());
                }
                break;
            }
            case "mainpage": {
                //14.返回主页面
                if (paramBean != null) {
                }
                break;
            }
            case "login":
                //15.登录
                //checkLogin();
                if (_mActivity != null){
                    UserInfoModel.getInstance().logout();
                    _mActivity.startActivity(new Intent(_mActivity, LoginActivity.class));
                }
                break;
            case "share_web_page":
                //16.分享
                if (paramBean != null) {
                    String share_title = paramBean.getShare_title();
                    String share_text = paramBean.getShare_text();
                    String share_target_url = paramBean.getShare_target_url();
                    String share_image = paramBean.getShare_image();

                    Logs.e("share_title = " + share_title + "\n" +
                            "share_text = " + share_text + "\n" +
                            "share_target_url = " + share_target_url + "\n" +
                            "share_image = " + share_image);

                    ShareHelper shareHelper = new ShareHelper(_mActivity, new ShareHelper.OnShareListener() {
                        @Override
                        public void onSuccess() {
                            LiveBus.getDefault().postEvent(Constants.EVENT_KEY_BROWSER_SHARE_CALLBACK, "1");
                        }

                        @Override
                        public void onError(String error) {
                            LiveBus.getDefault().postEvent(Constants.EVENT_KEY_BROWSER_SHARE_CALLBACK, "-1");
                        }

                        @Override
                        public void onCancel() {
                            LiveBus.getDefault().postEvent(Constants.EVENT_KEY_BROWSER_SHARE_CALLBACK, "0");
                        }
                    });
                    if (_mActivity instanceof BaseActivity) {
                        ((BaseActivity) _mActivity).setShareHelper(shareHelper);
                    }
                    shareHelper.showNormalShare(share_title, share_text, share_target_url, share_image);
                }
                break;
            case "trial":
                //18.CPL任务(试玩任务)
                if (paramBean != null) {
                    try {
                        int trial_list = paramBean.getTrial_list();
                        if (trial_list == 1) {
                            //跳转任务列表
                            startFragment(TryGamePlayListFragment.newInstance());
                        } else if (trial_list == 0) {
                            int trial_id = Integer.parseInt(paramBean.getTrial_id());
                            //跳转单个任务详情页
                            startFragment(TryGameTaskFragment.newInstance(trial_id));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "coupon":
                //19 跳转领券中心
                startFragment(new GameCouponsListFragment());
                break;
            case "certification":
                //20 实名认证
                if (checkLogin()) {
                    startFragment(new CertificationFragment());
                }
                break;
            case "vip_member":
                //22 vip中心
                /*if (checkLogin()) {
                    startFragment(new UserVipFragment());
                }*/
                startFragment(new NewUserVipFragment());
                break;
            case "game_gift":
            case "usercenter_vouchers":
                //23 游戏/礼包
                //40 个人中心-游戏礼包
                if (paramBean != null) {
                    if (checkLogin()) {
                        try {
                            int index = paramBean.getTab_position();
                            if (index == 2){
                                startFragment(new MyCouponsListFragment());
                            }else {
                                if(index == 0){
                                    startFragment(new MyFavouriteGameListFragment());
                                }else if (index == 1){
                                    startFragment(new MyCardListFragment());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "usercenter_gamegift":
                if (checkLogin()) {
                    startFragment(new MyCouponsListFragment());
                }
                break;
            case "vip_month":
                //24 vip月卡
                /*if (checkLogin()) {
                    startFragment(new VipMemberFragment());
                }*/
                if (paramBean != null) {
                    startFragment(NewProvinceCardFragment.newInstance(paramBean.getType()));
                }else {
                    startFragment(NewProvinceCardFragment.newInstance(1));
                }
                break;
            case "transaction":
                //25 交易
                startFragment(new TransactionMainFragment1());
                break;
            case "game_vouchers":
                //26 游戏代金券
                try {
                    if (paramBean != null) {
                        int gameid = paramBean.getGameid();
                        //startFragment(GameDetailInfoFragment.newInstance(gameid, paramBean.getGame_type(), "coupon"));
                        startFragment(GameDetailCouponListFragment.newInstance(gameid));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "phone_bind":
                //27 手机绑定
                if (checkLogin()) {
                    startFragment(BindPhoneFragment.newInstance());
                }
                break;
            case "xh_recycle":
            case "usercenter_xh_recycle":
                //28 小号回收
                //45 个人中心-小号回收
                if (checkLogin()) {
                    startFragment(new XhNewRecycleMainFragment());
                }
                break;
            case "xinyoushoufa":
                //29 新游首发
                try {
                    /*if (paramBean != null) {
                        int tab_index = paramBean.getTab_position();
                        startFragment(NewGameMainFragment.newInstance(tab_index));
                    }*/
                    startFragment(new NewGameStartingFragment());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "gamecenter_new_ranking":
                //30 游戏大厅-排行
                try {
                    if (paramBean != null) {
                        int tab_id = paramBean.getTab_id();
                        //startFragment(MainGameRankingFragment.newInstance(tab_id, true));
                        if (tab_id == 6){
                            startFragment(NewGameRankingActivityFragment.newInstance("hot"));
                        }else if (tab_id == 7){
                            startFragment(NewGameRankingActivityFragment.newInstance("new"));
                        }else if (tab_id == 24){
                            startFragment(NewGameRankingActivityFragment.newInstance("zhekou_page"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case "gamecenter_new_fenlei":
                //31 游戏大厅-分类
                try {
                    if (paramBean != null) {
                        int game_type = paramBean.getGame_type();
                        //startFragment(MainGameFragment.newInstanceFenLei(game_type));
                        startFragment(MainGameClassification2Fragment.newInstance(game_type, 0, true));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "kaifubiao":
                //32 开服表
                startFragment(TsServerListFragment.newInstance("开服表"));
                break;
            case "youxiheji_type_1":
                //33 游戏合集
                if (paramBean != null) {
                    startFragment(MainPageGameCollectionFragment.newInstance(true, paramBean.getHeji_params()));
                }
                break;
            case "usercenter_integral": {
                //34 个人中心-积分
                if (checkLogin()) {
                    startFragment(TaskCenterFragment.newInstance());
                }
            }
            break;
            case "usercenter_pingtaibi": {
                //35 个人中心-平台币钱包
                if (checkLogin()) {
                    startFragment(new TopUpFragment());
                }
            }
            break;
            case "usercenter_vip": {
                //36 个人中心-VIP特权
                /*if (checkLogin()) {
                    startFragment(new UserVipFragment());
                }*/
                startFragment(new NewUserVipFragment());
            }
            break;
            case "usercenter_month_card": {
                //37 个人中心-月卡
                /*if (checkLogin()) {
                    startFragment(new VipMemberFragment());
                }*/
                if (paramBean != null) {
                    startFragment(NewProvinceCardFragment.newInstance(paramBean.getType()));
                }else {
                    startFragment(NewProvinceCardFragment.newInstance(1));
                }
            }
            break;
            case "usercenter_comment": {
                //38 个人中心-我的评论
                if (checkLogin()) {
                    int uid = UserInfoModel.getInstance().getUID();
                    String user_nickname = UserInfoModel.getInstance().getUserNickname();
                    startFragment(UserCommentCenterFragment.newInstance(uid, user_nickname));
                }
            }
            break;
            case "usercenter_qa": {
                //39 个人中心-我的问答
                if (checkLogin()) {
                    int uid = UserInfoModel.getInstance().getUID();
                    String user_nickname = UserInfoModel.getInstance().getUserNickname();
                    startFragment(UserQaCollapsingCenterFragment.newInstance(uid, user_nickname));
                }
            }
            break;
            case "usercenter_rebate":
                //42 个人中心-自助返利
                if (checkLogin()) {
                    startFragment(new RebateMainFragment());
                }
                break;
            case "usercenter_kefu":
                //43 个人中心-客服反馈
                if (checkLogin()) {
                    startFragment(new KefuCenterFragment());
                }
                break;
            case "usercenter_gonggao":
                //44 个人中心-公告讯息
                if (checkLogin()) {
                    startFragment(MainActivityFragment.newInstance("公告快讯"));
                }
                break;
            case "usercenter_profile":
                //46 个人中心-查看并编辑个人资料
                if (checkLogin()) {
                    //if(BuildConfig.APP_TEMPLATE != 9999){
                        startFragment(new UserInfoFragment());
                    //}
                }
                break;
            case "trade_goods_info":
                //48 交易商品详情
                startFragment(TransactionGoodDetailFragment.newInstance(paramBean.getGid(),paramBean.getGameid()+""));
                break;
            case "game_collection":
                //49 新版合集界面
                if (paramBean != null) {
                    startFragment(NewMainPageGameCollectionFragment.newInstance(true, paramBean.getContainer_id()));
                }
                break;
            case "zero_buy_index":
                //50 交易-0元淘号
                startFragment(new TransactionZeroBuyFragment());
                break;
            case "trade_one_discount_goods":
                //51 交易-一折捡漏
                startFragment(new TransactionOneBuyFragment());
                break;
            case "integral_mall":
                //52 积分商城
                startFragment(new CommunityIntegralMallFragment());
                break;
            case "discount_money_card_exchange":
                //53 折扣省钱卡兑换界面
                if (checkLogin()) {
                    startFragment(new ProvinceCardExchangeFragment());
                }
                break;
            case "cloud":
                //云挂机
                //if (checkLogin()) startFragment(CloudVeGuideFragment.newInstance());
                //ToastT.error("功能开发中。。。");
                Toaster.show("功能开发中...");
                break;
            case "reserve":
                startFragment(new NewGameAppointmentFragment());
                break;
            case "forum": {
                //社区
                if (paramBean != null) {
                    goGameDetail1(paramBean.getGameid(), paramBean.getGame_type(), "forum");
                }
                break;
            }
            case "comment_info": {
                if (paramBean != null){
                    //特征, 1:图文分开; 2:图文并茂
                    if (paramBean.getFeature() == 1) {
                        startFragment(ForumDetailFragment.newInstance(String.valueOf(paramBean.getTid())));
                    } else if (paramBean.getFeature() == 2) {
                        startFragment(ForumLongDetailFragment.newInstance(String.valueOf(paramBean.getTid())));
                    }
                }
            }
            default:
                break;
        }
    }

    private boolean checkLogin() {
        if (_mActivity == null) {
            return false;
        }
        if (!(_mActivity instanceof BaseActivity)) {
            return false;
        }
        return ((BaseActivity) _mActivity).checkUserLogin();
    }

    private void startFragment(BaseFragment fragment) {
        FragmentHolderActivity.startFragmentInActivity(_mActivity, fragment);
    }

    private void goGameDetail(int gameid, int game_type) {
        startFragment(GameDetailInfoFragment.newInstance(gameid, game_type));
    }

    private void goGameDetail1(int gameid, int game_type, String action) {
        startFragment(GameDetailInfoFragment.newInstance(gameid, game_type, action));
    }
}
