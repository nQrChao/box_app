package com.zqhy.app.core.view.kefu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.core.data.model.kefu.KefuInfoDataVo;
import com.zqhy.app.core.data.model.kefu.VipKefuInfoDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.utilcode.PhoneUtils;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.core.tool.utilcode.SizeUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.vm.kefu.KefuViewModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

/**
 * @author Administrator
 * @date 2018/11/22
 */

public class KefuCenterFragment extends BaseFragment<KefuViewModel> {

    public static KefuCenterFragment newInstance(int showType) {
        KefuCenterFragment fragment = new KefuCenterFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("auto_show_kefu_dialog", showType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "客服中心";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_kefu_center;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }


    private boolean isAutoShowKefuDialog;
    private int showType;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            showType = getArguments().getInt("auto_show_kefu_dialog", 0);
            isAutoShowKefuDialog = showType == 1;
        }
        super.initView(state);

        //setStatusBar(0x00000000);
        //initActionBackBarAndTitle("人工客服");
        //setTitleColor(ContextCompat.getColor(_mActivity, R.color.white));
        //setActionBackBar(R.mipmap.ic_actionbar_back_white);
        //setTitleBottomLine(View.GONE);
        bindViews();
    }

    private RelativeLayout mRlKefuFaq;
    private RelativeLayout mRlKefuFeedback;
    private ImageView mIvItem1;
    private TextView mTvItemTitle1;
    private TextView mTvItemSubTitle1;
    private TextView mBtnItem1;
    private ImageView mIvItem2;
    private TextView mTvItemTitle2;
    private TextView mTvItemSubTitle2;
    private TextView mBtnItem2;
    private ImageView mIvItem3;
    private TextView mTvItemTitle3;
    private TextView mTvItemSubTitle3;
    private TextView mBtnItem3;
    private ImageView mIvItem4;
    private TextView mTvItemTitle4;
    private TextView mTvItemSubTitle4;
    private TextView mBtnItem4;
    private RelativeLayout mRlItem1;
    private RelativeLayout mRlItem2;
    private RelativeLayout mRlItem3;
    private RelativeLayout mRlItem4;
    private ImageView mIvBack;
    private TextView mTvApp;
    private TextView mTvKefuTips;

    private RelativeLayout mRlItem5;
    private ImageView mIvItem5;
    private TextView mTvItemTitle5;
    private TextView mTvItemSubTitle5;
    private TextView mBtnItem5;

    private RelativeLayout mRlItem6;
    private ImageView mIvItem6;
    private TextView mTvItemTitle6;
    private TextView mTvItemSubTitle6;
    private TextView mBtnItem6;

    private ImageView leftIcon;
    private TextView leftText;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private void bindViews() {
        leftText = findViewById(R.id.leftText);
        leftIcon = findViewById(R.id.leftIcon);
        mRlKefuFaq = findViewById(R.id.rl_kefu_faq);
        mRlKefuFeedback = findViewById(R.id.rl_kefu_feedback);
        mIvItem1 = findViewById(R.id.iv_item_1);
        mTvItemTitle1 = findViewById(R.id.tv_item_title_1);
        mTvItemSubTitle1 = findViewById(R.id.tv_item_sub_title_1);
        mBtnItem1 = findViewById(R.id.btn_item_1);
        mIvItem2 = findViewById(R.id.iv_item_2);
        mTvItemTitle2 = findViewById(R.id.tv_item_title_2);
        mTvItemSubTitle2 = findViewById(R.id.tv_item_sub_title_2);
        mBtnItem2 = findViewById(R.id.btn_item_2);
        mIvItem3 = findViewById(R.id.iv_item_3);
        mTvItemTitle3 = findViewById(R.id.tv_item_title_3);
        mTvItemSubTitle3 = findViewById(R.id.tv_item_sub_title_3);
        mBtnItem3 = findViewById(R.id.btn_item_3);
        mIvItem4 = findViewById(R.id.iv_item_4);
        mTvItemTitle4 = findViewById(R.id.tv_item_title_4);
        mTvItemSubTitle4 = findViewById(R.id.tv_item_sub_title_4);
        mBtnItem4 = findViewById(R.id.btn_item_4);
        mRlItem1 = findViewById(R.id.rl_item_1);
        mRlItem2 = findViewById(R.id.rl_item_2);
        mRlItem3 = findViewById(R.id.rl_item_3);
        mRlItem4 = findViewById(R.id.rl_item_4);
        mIvBack = findViewById(R.id.iv_back);
        mTvApp = findViewById(R.id.tv_app);
        mTvKefuTips = findViewById(R.id.tv_kefu_tips);

        mRlItem5 = findViewById(R.id.rl_item_5);
        mIvItem5 = findViewById(R.id.iv_item_5);
        mTvItemTitle5 = findViewById(R.id.tv_item_title_5);
        mTvItemSubTitle5 = findViewById(R.id.tv_item_sub_title_5);
        mBtnItem5 = findViewById(R.id.btn_item_5);

        mRlItem6 = findViewById(R.id.rl_item_6);
        mIvItem6 = findViewById(R.id.iv_item_6);
        mTvItemTitle6 = findViewById(R.id.tv_item_title_6);
        mTvItemSubTitle6 = findViewById(R.id.tv_item_sub_title_6);
        mBtnItem6 = findViewById(R.id.btn_item_6);
        mTvApp.setText(getAppNameByXML(R.string.string_dyx_kefu));

        mIvBack.setOnClickListener(view -> {
            pop();
        });
        mRlKefuFaq.setOnClickListener(view -> {
            //startFragment(new KefuHelperFragment());
            BrowserActivity.newInstance(requireActivity(), "https://mobile.xiaodianyouxi.com/index.php/Index/view/?id=100000010");
            //startFragment(BrowserFragment.newInstance("https://mobile.xiaodianyouxi.com/index.php/Index/view/?id=100000010"));
        });
        mRlKefuFeedback.setOnClickListener(view -> {
            if (checkLogin()) {
                startFragment(new FeedBackFragment());
            }
        });

        getNetWorkData();

    }


    KefuInfoDataVo.DataBean kefuInfoBean;

    private void setViewValue(KefuInfoDataVo.DataBean data) {
        if (data == null) {
            return;
        }
        kefuInfoBean = data;

        if (data.getJy_kf() != null) {
            String qq_num = data.getJy_kf().getQq_num();
            //人工客服
            mTvItemTitle1.setText("人工客服：" + qq_num);
            mTvItemSubTitle1.setText(TextUtils.isEmpty(data.getKf_time()) ? "在线时间：10:00-22:00" : "在线时间：" + data.getKf_time());
            mBtnItem1.setOnClickListener(view -> {
                showKefuQQDialog();
            });

            String qq_group = data.getJy_kf().getQq_qun();
            String qq_group_key = data.getJy_kf().getQq_qun_key();
            //QQ群
            mTvItemTitle3.setText("玩家Q群：" + qq_group);
            mBtnItem3.setOnClickListener(view -> {
                joinQQGroup(qq_group_key);
            });

            //客服微信id
            String wechat_id = data.getJy_kf().getWechat_id();
            String wechat_url = data.getJy_kf().getWechat_url();
            mTvItemSubTitle5.setText(TextUtils.isEmpty(data.getKf_time()) ? "在线时间：10:00-22:00" : "在线时间：" + data.getKf_time());
            if (!TextUtils.isEmpty(wechat_id) || !TextUtils.isEmpty(wechat_url)) {
                mRlItem5.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(wechat_id)) {
                    mTvItemTitle5.setText("微信客服：" + wechat_id);
                } else {
                    mTvItemTitle5.setText("官方微信客服");
                }
                if (TextUtils.isEmpty(data.getJy_kf().getWechat_url())) {
                    mBtnItem5.setText("复制");
                } else {
                    mBtnItem5.setText("联系");
                }
                mBtnItem5.setOnClickListener(view -> {
                    if (TextUtils.isEmpty(data.getJy_kf().getWechat_url())) {
                        if (!TextUtils.isEmpty(wechat_id)) {
                            if (CommonUtils.copyString(_mActivity, wechat_id)) {
                                Toaster.show("复制成功");
                            }
                        } else {
                            if (CommonUtils.copyString(_mActivity, "官方微信客服")) {
                                Toaster.show("复制成功");
                            }
                        }
                    } else {
                        toBrowser(0, data.getJy_kf().getWechat_url());
                    }
                });
            } else {
                mRlItem5.setVisibility(View.GONE);
            }
            //客服微信公众号
            String wechat_gzh = data.getJy_kf().getWechat_gzh();
            if (!TextUtils.isEmpty(wechat_gzh)) {
                mRlItem6.setVisibility(View.VISIBLE);
                mTvItemTitle6.setText("微信公众号：" + wechat_gzh);
                mBtnItem6.setText("联系");
                mBtnItem6.setOnClickListener(view -> {
                    if (CommonUtils.copyString(_mActivity, wechat_gzh)) {
                        Toaster.show("复制成功");
                    }
                });
            } else {
                mRlItem6.setVisibility(View.GONE);
            }

        }

        String ts_email = data.getTs_email();
        mTvItemTitle4.setText("投诉邮箱：" + ts_email);
        mBtnItem4.setOnClickListener(view -> {
            if (CommonUtils.copyString(_mActivity, ts_email)) {
                Toaster.show( "复制成功");
            }
        });

        if (AppConfig.isGonghuiChannel()) {
            mTvKefuTips.setVisibility(View.GONE);
            if (data.getJy_kf() != null && !TextUtils.isEmpty(data.getJy_kf().getQq_qun())) {
                mRlItem3.setVisibility(View.VISIBLE);
            } else {
                mRlItem3.setVisibility(View.GONE);
            }
        } else {
            mRlItem3.setVisibility(View.GONE);
            mTvKefuTips.setVisibility(View.VISIBLE);
        }

        if (isAutoShowKefuDialog && mBtnItem1.getVisibility() == View.VISIBLE) {
            mBtnItem1.performClick();
        }


        if (showType == 2) {
            leftIcon.setVisibility(View.VISIBLE);
            leftIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop();
                }
            });
        }
    }

    private void session(String qq) {
        try {
            String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setAction(Intent.ACTION_VIEW);
            _mActivity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toaster.show( "未安装手Q或安装的版本不支持");
        }
    }

    private void joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            _mActivity.startActivity(intent);
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            e.printStackTrace();
            Toaster.show( "未安装手Q或安装的版本不支持");
        }
    }


    private void toBrowser(String url) {
        toBrowser(0, url);
    }


    /**
     * @param type 0 跳转外部浏览器  1 跳转App里的BrowserActivity
     * @param url
     */
    private void toBrowser(int type, String url) {
        if (type == 0) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            startActivity(intent);
        } else if (type == 1) {
            BrowserActivity.newInstance(_mActivity, url);
        }
    }


    public void getNetWorkData() {
        getKefuInfoData();
        getVipKefuInfoData();
    }

    private void getKefuInfoData() {
        if (mViewModel != null) {
            mViewModel.getKefuInfo(new OnBaseCallback<KefuInfoDataVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }

                @Override
                public void onSuccess(KefuInfoDataVo kefuInfoDataVo) {
                    if (kefuInfoDataVo != null) {
                        if (kefuInfoDataVo.isStateOK() && kefuInfoDataVo.getData() != null) {
                            setViewValue(kefuInfoDataVo.getData());
                        }
                    }
                }
            });
        }
    }

    private void getVipKefuInfoData() {
        if (mViewModel != null) {
            mViewModel.getVipKefuInfo(new OnBaseCallback<VipKefuInfoDataVo>() {
                @Override
                public void onSuccess(VipKefuInfoDataVo vipKefuInfoDataVo) {
                    if (vipKefuInfoDataVo != null) {
                        if (vipKefuInfoDataVo.isStateOK() && vipKefuInfoDataVo.getData() != null) {
                            VipKefuInfoDataVo.DataBean dataBean = vipKefuInfoDataVo.getData();

                            if (dataBean.isShowVipKefu()) {
                                mRlItem2.setVisibility(View.VISIBLE);
                                mTvItemTitle2.setText("VIP客服：" + dataBean.getVip_qq());
                                mBtnItem2.setOnClickListener(view -> {
                                    switch (dataBean.getLevel()) {
                                        case VipKefuInfoDataVo.KEFU_LEVEL_VIP_NOT_COMMIT:
                                            //是Vip,但没有提交Vip客服申请
                                            /*if (checkLogin()) {
                                                startForResult(new VipUpgradeServiceFragment(), VIP_UPGRADE_SERVICE);
                                            }*/
                                            break;
                                        case VipKefuInfoDataVo.KEFU_LEVEL_VIP_COMMITTED:
                                            if (CommonUtils.copyString(_mActivity, dataBean.getVip_qq())) {
                                                Toaster.show( "已复制VIP客服QQ,请添加好友联系");
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                });
                            } else {
                                mRlItem2.setVisibility(View.GONE);
                                mBtnItem2.setOnClickListener(null);
                            }

                        }
                    }
                }
            });
        }
    }

    private void showKefuQQDialog() {
        CustomDialog kefuQQDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_kefu_qq, null), ScreenUtils.getScreenWidth(_mActivity) - SizeUtils.dp2px(_mActivity, 24), WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        TextView mTvKefuQQCancel = kefuQQDialog.findViewById(R.id.tv_cancel);
        TextView mTvContact = kefuQQDialog.findViewById(R.id.tv_contact);
        ((TextView) kefuQQDialog.findViewById(R.id.tv_time)).setText(TextUtils.isEmpty(kefuInfoBean.getKf_time()) ? "10:00-22:00" : kefuInfoBean.getKf_time());
        mTvKefuQQCancel.setOnClickListener(view -> {
            if (kefuQQDialog != null && kefuQQDialog.isShowing()) {
                kefuQQDialog.dismiss();
            }
        });

        mTvContact.setOnClickListener(view -> {
            if (kefuQQDialog != null && kefuQQDialog.isShowing()) {
                kefuQQDialog.dismiss();
            }
            if (kefuInfoBean != null && kefuInfoBean.getJy_kf() != null) {
                if (kefuInfoBean.getJy_kf().getIs_yinxiao() == 1) {
                    int type = kefuInfoBean.getJy_kf().getYinxiao_jump_type();
                    toBrowser(type, kefuInfoBean.getJy_kf().getYinxiao_url());
                } else {
                    if (AppConfig.isGonghuiChannel()) {
                        session(kefuInfoBean.getJy_kf().getQq_num());
                    } else {
                        if (CommonUtils.copyString(_mActivity, kefuInfoBean.getJy_kf().getQq_num())) {
                            Toaster.show( "请在QQ中搜索并联系我们哦！");
                        }
                    }

                }
            }
        });
        kefuQQDialog.show();
    }

    private void showKefuPhoneDialog() {
        CustomDialog kefuPhoneDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_kefu_phone, null), ScreenUtils.getScreenWidth(_mActivity) - SizeUtils.dp2px(_mActivity, 24), WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        TextView mTvKefuPhoneCancel = kefuPhoneDialog.findViewById(R.id.tv_cancel);
        TextView mTvSubmit = kefuPhoneDialog.findViewById(R.id.tv_submit);

        mTvKefuPhoneCancel.setOnClickListener(view -> {
            if (kefuPhoneDialog != null && kefuPhoneDialog.isShowing()) {
                kefuPhoneDialog.dismiss();
            }
        });
        mTvSubmit.setOnClickListener(view -> {
            if (kefuPhoneDialog != null && kefuPhoneDialog.isShowing()) {
                kefuPhoneDialog.dismiss();
            }
            if (kefuInfoBean != null) {
                if (PhoneUtils.isTelephonyEnabled(_mActivity) && PhoneUtils.isPhone(_mActivity)) {
                    try {
                        PhoneUtils.dial(_mActivity, kefuInfoBean.getKefu_phone());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toaster.show( "当前设备不支持通话");
                    }
                } else {
                    Toaster.show( "当前设备不支持通话");
                }
            }
        });
        kefuPhoneDialog.show();
    }
}
