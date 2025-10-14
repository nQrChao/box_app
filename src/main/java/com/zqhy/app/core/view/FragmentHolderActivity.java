package com.zqhy.app.core.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.blankj.utilcode.util.Logs;
import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.App;
import com.zqhy.app.base.BaseActivity;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.data.model.user.newvip.ComeBackVo;
import com.zqhy.app.core.data.model.user.newvip.RmbusergiveVo;
import com.zqhy.app.core.data.model.version.UnionVipDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.utilcode.ScreenUtils;
import com.zqhy.app.core.tool.utilcode.SizeUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.game.forum.ForumPostLongFragment;
import com.zqhy.app.core.view.main.MainActivity;
import com.zqhy.app.core.view.main.adapter.RmbusergiveAdapter;
import com.zqhy.app.core.view.user.welfare.MyCouponsListFragment;
import com.zqhy.app.core.vm.main.MainViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.sp.SPUtils;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Administrator
 * @date 2018/11/8
 */

public class FragmentHolderActivity extends BaseActivity<MainViewModel> {

    public static final String FRAGMENTCLASS = "FRAGMENTCLASS";
    public static final String FRAGMENTEXTRA = "FRAGMENTEXTRA";
    public static final int COMMON_REQUEST_CODE = 0x777;
    public int activityPosition;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fragment_holder;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        activityPosition = App.getActivityList().size() - 1;
        showSuccess();
        if (savedInstanceState == null && null != getIntent()) {
            Class<SupportFragment> clazz = (Class<SupportFragment>) getIntent().getSerializableExtra(FRAGMENTCLASS);
            if (null != clazz) {
                try {
                    SupportFragment fragment = clazz.newInstance();
                    if (null != getIntent().getBundleExtra(FRAGMENTEXTRA)) {
                        fragment.setArguments(getIntent().getBundleExtra(FRAGMENTEXTRA));
                    }
                    loadRootFragment(R.id.content, fragment);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    Logs.d(e.getMessage());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                    Logs.d(e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    Logs.d(e.getMessage());
                }
            }
        }
        if (App.isShowEasyFloat) {
            showEasyFloat(this, "holder");
        }
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        if (UserInfoModel.getInstance().isLogined()) {
            //因为这个登录接口回调是基于先注册先回调的机制 然后这些弹窗是需要在登录后第一时间显示 所以需要判断登录界面下面最近的一个界面的类型（由于界面可能存在多个，所以还有判断界面所处的位置是不是在登录界面或H5界面下面）
            if (getTheActivity() == FragmentHolderActivity.class && activityPosition == getTheActivityPosition()) {
                SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
                if (CommonUtils.isTodayOrTomorrow(spUtils.getLong("showUnionVipDialogTime", 0)) < 0) {
                    getUnionVipPop();
                }
                rmbusergive();
                getComeBack();
            }
        }
    }

    /**
     * 【改造后的新方法】
     * 全局Fragment路由器，将所有Fragment导航请求都转发给 MainActivity。
     *
     * @param context       调用此方法的上下文
     * @param fragmentClass 要启动的Fragment的Class对象
     * @param args          要传递给Fragment的参数
     */
    public static void navigateTo(Context context, Class<? extends SupportFragment> fragmentClass, Bundle args) {
        // 1. 目标始终是 MainActivity
        Intent intent = new Intent(context, MainActivity.class);
        // 2. 将 Fragment 信息作为 "指令" 放入 Intent
        intent.putExtra("TARGET_FRAGMENT_CLASS", fragmentClass);
        if (args != null) {
            intent.putExtra("TARGET_FRAGMENT_ARGS", args);
        }
        // 3. 设置 Flag，以便能将已存在的 MainActivity 带到前台
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        // 4. 启动
        context.startActivity(intent);
    }

    public static void startFragmentInActivity(Activity context, SupportFragment fragment) {
        startFragmentInActivity(context, fragment, false);
    }

    public static void startFragmentInActivity(Activity context, SupportFragment fragment, boolean isSingle) {
        Intent intent = new Intent(context, FragmentHolderActivity.class);
        if (isSingle) {
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
        intent.putExtra(FRAGMENTCLASS, fragment.getClass());
        intent.putExtra(FRAGMENTEXTRA, fragment.getArguments());
        context.startActivityForResult(intent, COMMON_REQUEST_CODE);
    }


    public static void startFragmentInActivity(Context context, SupportFragment fragment) {
        if (context instanceof Activity) {
            startFragmentInActivity((Activity) context, fragment);
        } else if (context instanceof Context) {
            Intent intent = new Intent(context, FragmentHolderActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(FRAGMENTCLASS, fragment.getClass());
            intent.putExtra(FRAGMENTEXTRA, fragment.getArguments());
            context.startActivity(intent);
        }
    }

    public static Intent getFragmentInActivity(Context context, SupportFragment fragment) {
        Intent intent = new Intent(context, FragmentHolderActivity.class);
        intent.putExtra(FRAGMENTCLASS, fragment.getClass());
        intent.putExtra(FRAGMENTEXTRA, fragment.getArguments());
        return intent;
    }

    public static void startFragmentForResult(Activity activity, SupportFragment fragment, int requestCode) {
        Intent intent = new Intent(activity, FragmentHolderActivity.class);
        intent.putExtra(FRAGMENTCLASS, fragment.getClass());
        intent.putExtra(FRAGMENTEXTRA, fragment.getArguments());
        activity.startActivityForResult(intent, requestCode);
    }

    private void getUnionVipPop() {
        if (!UserInfoModel.getInstance().isLogined()) {
            return;
        }

        if (mViewModel != null) {
            UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
            mViewModel.getUnionVipPop(String.valueOf(userInfo.getUid()), userInfo.getToken(), new OnBaseCallback<UnionVipDataVo>() {
                @Override
                public void onSuccess(final UnionVipDataVo data) {
                    if (data != null && data.isStateOK()) {
                        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME);
                        spUtils.putLong("showUnionVipDialogTime", System.currentTimeMillis());
                        long unionVip = spUtils.getLong("unionVip");
                        if (data.getData().getUtime() < unionVip) {
                            return;
                        }
                        CustomDialog unionVipDialog = new CustomDialog(FragmentHolderActivity.this, LayoutInflater.from(FragmentHolderActivity.this).inflate(R.layout.layout_dialog_union_vip, null),
                                ScreenUtils.getScreenWidth(FragmentHolderActivity.this) - SizeUtils.dp2px(FragmentHolderActivity.this, 40),
                                WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
                        unionVipDialog.setCanceledOnTouchOutside(false);
                        String text = data.getData().getText();
                        String type = "";
                        if (!TextUtils.isEmpty(text)) {
                            String[] split = text.split(" ");
                            if (split.length == 2) {
                                type = split[0];
                                text = split[1];
                            }
                        }
                        ((TextView) unionVipDialog.findViewById(R.id.tv_type)).setText(type);
                        ((TextView) unionVipDialog.findViewById(R.id.tv_contact)).setText(text);
                        if (data.getData() != null && !TextUtils.isEmpty(data.getData().getMsg()))
                            ((TextView) unionVipDialog.findViewById(R.id.tv_tips)).setText(data.getData().getMsg());

                        String finalText = text;
                        unionVipDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
                            /*if (unionVipDialog != null && unionVipDialog.isShowing()){
                                unionVipDialog.dismiss();
                            }*/
                            if (CommonUtils.copyString(FragmentHolderActivity.this, finalText)) {
                                //ToastT.success(FragmentHolderActivity.this, "联系方式已复制");
                                Toaster.show("联系方式已复制");
                            }
                        });
                        unionVipDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
                            if (unionVipDialog != null && unionVipDialog.isShowing()) {
                                unionVipDialog.dismiss();
                            }
                        });
                        unionVipDialog.show();
                        spUtils.putLong("unionVip", data.getData().getNtime());
                    }
                }
            });
        }
    }

    private void rmbusergive() {
        if (mViewModel != null) {
            mViewModel.rmbusergive(new OnBaseCallback<RmbusergiveVo>() {
                @Override
                public void onSuccess(RmbusergiveVo rmbusergiveVo) {
                    if (mViewModel != null) {
                        mViewModel.rmbusergive(new OnBaseCallback<RmbusergiveVo>() {
                            @Override
                            public void onSuccess(RmbusergiveVo rmbusergiveVo) {
                                if (rmbusergiveVo != null && rmbusergiveVo.getData() != null && rmbusergiveVo.isStateOK()) {
                                    if (0 < rmbusergiveVo.getData().getCoupon_total() && "yes".equals(rmbusergiveVo.getData().getHas_give())) {
                                        showRmbusergive(rmbusergiveVo.getData());
                                    }
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    private void rmbusergiveGetReward(RmbusergiveVo.DataBean dataBean) {
        if (mViewModel != null) {
            mViewModel.rmbusergiveGetReward(new OnBaseCallback<RmbusergiveVo>() {
                @Override
                public void onSuccess(RmbusergiveVo rmbusergiveVo) {
                    if (rmbusergiveVo != null && rmbusergiveVo.isStateOK()) {
                        showRmbusergiveSucceed(dataBean);
                    }
                }
            });
        }
    }

    private void showRmbusergive(RmbusergiveVo.DataBean dataBean) {
        CustomDialog tipsDialog = new CustomDialog(this, LayoutInflater.from(this).inflate(R.layout.layout_dialog_rmbusergive_ungain, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        tipsDialog.setCancelable(false);
        tipsDialog.setCanceledOnTouchOutside(false);
        ((TextView) tipsDialog.findViewById(R.id.tv_coupon_total)).setText(String.valueOf(dataBean.getCoupon_total()));
        RecyclerView recyclerView = (RecyclerView) tipsDialog.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RmbusergiveAdapter(this, dataBean.getCoupon_list()));
        tipsDialog.findViewById(R.id.ll_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            rmbusergiveGetReward(dataBean);
        });
        tipsDialog.show();
    }

    private void showRmbusergiveSucceed(RmbusergiveVo.DataBean dataBean) {
        CustomDialog tipsDialog = new CustomDialog(this, LayoutInflater.from(this).inflate(R.layout.layout_dialog_rmbusergive_gained, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        tipsDialog.setCancelable(false);
        tipsDialog.setCanceledOnTouchOutside(false);
        RecyclerView recyclerView = (RecyclerView) tipsDialog.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RmbusergiveAdapter(this, dataBean.getCoupon_list()));
        tipsDialog.findViewById(R.id.ll_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing())
                tipsDialog.dismiss();
            //代金券
            if (UserInfoModel.getInstance().isLogined()) {
                //FragmentHolderActivity.startFragmentInActivity(this, GameWelfareFragment.newInstance(2));
                FragmentHolderActivity.startFragmentInActivity(this, new MyCouponsListFragment());
            }
        });
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing())
                tipsDialog.dismiss();
        });
        tipsDialog.show();
    }

    private void getComeBack() {
        if (mViewModel != null) {
            mViewModel.getComeBack(new OnBaseCallback<ComeBackVo>() {
                @Override
                public void onSuccess(ComeBackVo comeBackVo) {
                    if (comeBackVo != null && comeBackVo.getData() != null && comeBackVo.isStateOK()) {
                        if ("yes".equals(comeBackVo.getData().getIs_come_back())) {
                            if (0 <= comeBackVo.getData().getDay()) {
                                SPUtils spUtils = new SPUtils(FragmentHolderActivity.this, Constants.SP_COMMON_NAME);
                                if (CommonUtils.isTodayOrTomorrow(spUtils.getLong("showComeBackDialogTime", 0)) < 0) {
                                    spUtils.putLong("showComeBackDialogTime", System.currentTimeMillis());
                                    if (TextUtils.isEmpty(spUtils.getString("showComeBack", ""))
                                            || !spUtils.getString("showComeBack", "").equals(comeBackVo.getData().getId())) {
                                        spUtils.putString("showComeBack", comeBackVo.getData().getId());
                                        showComeBackDialog(comeBackVo.getData());
                                    } else {
                                        showComeBackDialogSecond(comeBackVo.getData());
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    private void showComeBackDialog(ComeBackVo.DataBean dataBean) {
        CustomDialog tipsDialog = new CustomDialog(this, LayoutInflater.from(this).inflate(R.layout.layout_dialog_come_back, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        tipsDialog.setCancelable(false);
        tipsDialog.setCanceledOnTouchOutside(false);
        TextView mTvTips = (TextView) tipsDialog.findViewById(R.id.tv_tips);
        SpannableString spannableString = new SpannableString("将于" + CommonUtils.formatTimeStamp(dataBean.getEnd_time() * 1000, "yyyy-MM-dd ") + "过期");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FEA047")), 2, 12, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvTips.setText(spannableString);
        tipsDialog.findViewById(R.id.iv_close).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) {
                tipsDialog.dismiss();
            }
        });
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) {
                tipsDialog.dismiss();
            }
            BrowserActivity.newInstance(this, dataBean.getHd_url(), true);
        });
        tipsDialog.show();
    }

    private void showComeBackDialogSecond(ComeBackVo.DataBean dataBean) {
        CustomDialog tipsDialog = new CustomDialog(this, LayoutInflater.from(this).inflate(R.layout.layout_dialog_come_back_second, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        tipsDialog.setCancelable(false);
        tipsDialog.setCanceledOnTouchOutside(false);
        TextView mTvTips = (TextView) tipsDialog.findViewById(R.id.tv_tips);
        SpannableString spannableString = new SpannableString("将于" + CommonUtils.formatTimeStamp(dataBean.getEnd_time() * 1000, "yyyy-MM-dd ") + "过期");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FEA047")), 2, 12, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvTips.setText(spannableString);
        ((TextView) tipsDialog.findViewById(R.id.tv_day)).setText(String.valueOf(dataBean.getDay()));
        tipsDialog.findViewById(R.id.iv_close).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) {
                tipsDialog.dismiss();
            }
        });
        tipsDialog.findViewById(R.id.iv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) {
                tipsDialog.dismiss();
            }
            BrowserActivity.newInstance(this, dataBean.getHd_url(), true);
        });
        tipsDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (getTopFragment() != null && getTopFragment() instanceof ForumPostLongFragment) {
                ForumPostLongFragment topFragment = (ForumPostLongFragment) getTopFragment();
                topFragment.pop();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
