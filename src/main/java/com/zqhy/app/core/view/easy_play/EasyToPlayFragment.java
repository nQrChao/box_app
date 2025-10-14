package com.zqhy.app.core.view.easy_play;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.box.other.hjq.toast.Toaster;
import com.google.android.material.imageview.ShapeableImageView;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.easy_play.RefundDetailVo;
import com.zqhy.app.core.data.model.user.VerificationCodeVo;
import com.zqhy.app.core.inner.OnNetWorkListener;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.easy_play.plug.VerifyEditText;
import com.zqhy.app.core.vm.easy_play.EasyToPlayViewModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.sp.SPUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author leeham2734
 * @date 2020/11/3-9:38
 * @description
 */
public class EasyToPlayFragment extends BaseFragment<EasyToPlayViewModel> {


    public static EasyToPlayFragment newInstance(String gameName, String gameTitle ,String gameIcon,String gameId) {
        EasyToPlayFragment fragment = new EasyToPlayFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gameId",gameId);
        bundle.putString("gameName",gameName);
        bundle.putString("gameTitle",gameTitle);
        bundle.putString("gameIcon",gameIcon);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "省心玩";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_easy_to_play;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    String gameId;
    String gameName;
    String gameTitle;
    String gameIcon;
    private boolean isShow = false;
    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gameName = getArguments().getString("gameName","");
            gameTitle = getArguments().getString("gameTitle","");
            gameIcon = getArguments().getString("gameIcon","");
            gameId = getArguments().getString("gameId","");
        }
        super.initView(state);
        initActionBackBarAndTitle("省心玩");
        bindViews();
        initData();
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;


    private LinearLayoutCompat lin_top;
    private LinearLayout lin_value;
    private TextView tv_top_tip;
    private TextView tv_next;
    private LinearLayoutCompat lin_acc;
    private TextView tv_czze_num;
    private TextView tv_thbl_num;
    private TextView tv_ktflb_num;
    private TextView tv_sbczsj_num;
    private TextView tv_sqjzsj_num;
    private TextView tv_sqjzsj1_num;
    private TextView tv_game_name;
    private TextView tv_game_title;
    RefundDetailVo.DataBean mInfo = null;
    private ShapeableImageView iv_icon;

    private void bindViews() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        findViewById(R.id.tv_title_right).setOnClickListener(view -> {
           String url = "https://mobile.tsyule.cn/index.php/Index/view/?id=190429https://mobile.tsyule.cn/index.php/Index/view/?id=190429";
            BrowserActivity.newInstance1(_mActivity,url, true);
        });
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            initData();
        });
        lin_top = findViewById(R.id.lin_top);
        lin_value = findViewById(R.id.lin_value);
        lin_valueless = findViewById(R.id.lin_valueless);
        tv_top_tip = findViewById(R.id.tv_top_tip);
        tv_next = findViewById(R.id.tv_next);
        tv_game_name = findViewById(R.id.tv_game_name);
        tv_game_title = findViewById(R.id.tv_game_title);

        lin_acc = findViewById(R.id.lin_acc);

        tv_czze_num = findViewById(R.id.tv_czze_num);
        tv_thbl_num = findViewById(R.id.tv_thbl_num);
        tv_ktflb_num = findViewById(R.id.tv_ktflb_num);
        tv_sbczsj_num = findViewById(R.id.tv_sbczsj_num);
        tv_sqjzsj_num = findViewById(R.id.tv_sqjzsj_num);
        tv_sqjzsj1_num = findViewById(R.id.tv_sqjzsj1_num);

        iv_icon = findViewById(R.id.iv_icon);
        tv_game_name.setText(gameName);
        tv_game_title.setText(gameTitle);
        GlideApp.with(this)
                .load(gameIcon)
                .placeholder(R.mipmap.ic_placeholder)
                .error(R.mipmap.ic_placeholder)
                .into(iv_icon);
    }
    private void initValue2Hour(RefundDetailVo.DataBean info) {
        if (lin_acc != null) {
            lin_acc.removeAllViews();
        }
        //可退还 2小时内
        lin_top.setVisibility(View.VISIBLE);
        lin_value.setVisibility(View.VISIBLE);
        lin_valueless.setVisibility(View.GONE);
        SpannableString ss = new SpannableString("首笔充值后2小时内可以申请退款");
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF803E")), 5, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_top_tip.setText(ss);
        tv_next.setText("申请退还");
        if (info.getXh_list()!=null&&info.getXh_list().size()>0) {
            for (RefundDetailVo.XHListData xhListData : info.getXh_list()) {
                View inflate = LayoutInflater.from(_mActivity).inflate(R.layout.item_easy_to_play_acc, null);
                TextView tv_acc = inflate.findViewById(R.id.tv_acc);
                TextView tv_pay_num = inflate.findViewById(R.id.tv_pay_num);
                tv_acc.setText(xhListData.getXh_showname());
                tv_pay_num.setText("充值:" + xhListData.getTotal());
                if (lin_acc != null) {
                    lin_acc.addView(inflate);
                }
            }
        }
//        tv_acc.setText(info.getXh_showname());
//        tv_pay_num.setText("充值:"+info.getTotal());


        tv_czze_num.setText(info.getTotal());
        tv_thbl_num.setText(info.getRate()+"%");
        tv_next.setBackgroundResource(R.drawable.shape_55c0fe_5571fe_big_radius);

        if (Float.parseFloat(info.getAmount())==0f) {
            //showTips();
            tv_next.setBackgroundResource(R.drawable.ts_shape_9b9b9b_big_radius);
        }else {
            tv_next.setOnClickListener(view -> {
                showDialogRefund();
            });
        }
        tv_ktflb_num.setText(info.getAmount());
        String first_time = CommonUtils.formatTimeStamp(Long.parseLong(info.getFirst_time()) * 1000, "yyyy/MM/dd HH:mm");
        String valid_time = CommonUtils.formatTimeStamp(info.getValid_time() * 1000, "yyyy/MM/dd HH:mm");
        tv_sbczsj_num.setText(first_time);
        tv_sqjzsj_num.setText(valid_time);
        tv_sqjzsj1_num.setVisibility(View.GONE);

    }
    private void initValue7Day(RefundDetailVo.DataBean info) {
        if (lin_acc != null) {
            lin_acc.removeAllViews();
        }
        //可退还 7天
        lin_top.setVisibility(View.VISIBLE);
        lin_value.setVisibility(View.VISIBLE);
        lin_valueless.setVisibility(View.GONE);
        SpannableString ss = new SpannableString("经系统检测，为您延长可申请退还时间");
        tv_top_tip.setText(ss);
        tv_next.setText("申请退还");
//        tv_acc.setText(info.getXh_showname());
//        tv_pay_num.setText("充值:"+info.getTotal());
        if (info.getXh_list()!=null&&info.getXh_list().size()>0) {
            for (RefundDetailVo.XHListData xhListData : info.getXh_list()) {
                View inflate = LayoutInflater.from(_mActivity).inflate(R.layout.item_easy_to_play_acc, null);
                TextView tv_acc = inflate.findViewById(R.id.tv_acc);
                TextView tv_pay_num = inflate.findViewById(R.id.tv_pay_num);
                tv_acc.setText(xhListData.getXh_showname());
                tv_pay_num.setText("充值:" + xhListData.getTotal());
                if (lin_acc != null) {
                    lin_acc.addView(inflate);
                }
            }
        }
        tv_next.setBackgroundResource(R.drawable.shape_55c0fe_5571fe_big_radius);

        if (Float.parseFloat(info.getAmount())==0f) {
            //showTips();
            tv_next.setBackgroundResource(R.drawable.ts_shape_9b9b9b_big_radius);
        }else {
            tv_next.setOnClickListener(view -> {
                showDialogRefund();
            });
        }
        tv_czze_num.setText(info.getTotal());
        tv_thbl_num.setText(info.getRate()+"%");
        tv_ktflb_num.setText(info.getAmount());
        String first_time = CommonUtils.formatTimeStamp(Long.parseLong(info.getFirst_time()) * 1000, "yyyy/MM/dd HH:mm");
        String valid_time = CommonUtils.formatTimeStamp(info.getValid_time() * 1000, "yyyy/MM/dd HH:mm");
        String last_time = CommonUtils.formatTimeStamp(info.getLast_time() * 1000, "yyyy/MM/dd HH:mm");
        tv_sbczsj_num.setText(first_time);
        tv_sqjzsj_num.setText(last_time);
        tv_sqjzsj1_num.setVisibility(View.VISIBLE);
        tv_sqjzsj1_num.setText(valid_time);
        tv_sqjzsj1_num.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }
    private void initValueOverTime(RefundDetailVo.DataBean info) {
        //超时不可退
        lin_top.setVisibility(View.VISIBLE);
        lin_value.setVisibility(View.VISIBLE);
        findViewById(R.id.con_det).setVisibility(View.GONE);
        lin_valueless.setVisibility(View.GONE);
        SpannableString ss = new SpannableString("已超时，需在首笔充值后2小时申请");
        tv_top_tip.setText(ss);
        tv_next.setText("已过期");
        tv_next.setBackgroundResource(R.drawable.ts_shape_9b9b9b_big_radius);

        String first_time = CommonUtils.formatTimeStamp(Long.parseLong(info.getFirst_time()) * 1000, "yyyy/MM/dd HH:mm");
        String valid_time = CommonUtils.formatTimeStamp(info.getValid_time() * 1000, "yyyy/MM/dd HH:mm");

        findViewById(R.id.tv_xhhs).setVisibility(View.GONE);
        findViewById(R.id.tv_xhhs_num).setVisibility(View.GONE);
        ConstraintLayout con_time = findViewById(R.id.con_time);
        con_time.setBackgroundResource(R.drawable.shape_white_bottom_10_radius);

        tv_sbczsj_num.setText(first_time);
        tv_sqjzsj_num.setText(valid_time);

        tv_next.setOnClickListener(view -> {

        });
    }
    private void initValueRefund(RefundDetailVo.DataBean info) {
        //已退
        TextView tv_game_name_r = findViewById(R.id.tv_game_name_r);
        TextView tv_game_title_r = findViewById(R.id.tv_game_title_r);
        ShapeableImageView iv_icon_r = findViewById(R.id.iv_icon_r);
        tv_game_name_r.setText(gameName);
        tv_game_title_r.setText(gameTitle);
        GlideApp.with(this)
                .load(gameIcon)
                .placeholder(R.mipmap.ic_placeholder)
                .error(R.mipmap.ic_placeholder)
                .into(iv_icon_r);

        findViewById(R.id.lin_normal).setVisibility(View.GONE);
        findViewById(R.id.tv_next).setVisibility(View.GONE);
        findViewById(R.id.lin_is_refund).setVisibility(View.VISIBLE);
        LinearLayoutCompat lin_acc_r = findViewById(R.id.lin_acc_r);
//        TextView tv_acc_r = findViewById(R.id.tv_acc_r);
//        TextView tv_pay_num_r = findViewById(R.id.tv_pay_num_r);
        TextView tv_czze_num_r = findViewById(R.id.tv_czze_num_r);
        TextView tv_thbl_num_r = findViewById(R.id.tv_thbl_num_r);
        TextView tv_ktflb_num_r = findViewById(R.id.tv_ktflb_num_r);
        TextView tv_sbczsj_num_r = findViewById(R.id.tv_sbczsj_num_r);
        TextView tv_sqjzsj_num_r = findViewById(R.id.tv_sqjzsj_num_r);
        TextView tv_ktflb_num_rr = findViewById(R.id.tv_ktflb_num_rr);
//        tv_acc_r.setText(info.getXh_showname());
//        tv_pay_num_r.setText("充值:"+info.getTotal());
        if (lin_acc_r != null) {
            lin_acc_r.removeAllViews();
        }
        if (info.getXh_list()!=null&&info.getXh_list().size()>0) {
            for (RefundDetailVo.XHListData xhListData : info.getXh_list()) {
                View inflate = LayoutInflater.from(_mActivity).inflate(R.layout.item_easy_to_play_acc, null);
                TextView tv_acc = inflate.findViewById(R.id.tv_acc);
                TextView tv_pay_num = inflate.findViewById(R.id.tv_pay_num);
                tv_acc.setText(xhListData.getXh_showname());
                tv_pay_num.setText("充值:" + xhListData.getTotal());
                if (lin_acc_r != null) {
                    lin_acc_r.addView(inflate);
                }
            }
        }
        tv_czze_num_r.setText(info.getTotal());
        tv_thbl_num_r.setText(info.getRate()+"%");
        tv_ktflb_num_r.setText(info.getAmount());
        tv_ktflb_num_rr.setText(info.getAmount());
        String first_time = CommonUtils.formatTimeStamp(Long.parseLong(info.getFirst_time()) * 1000, "yyyy/MM/dd HH:mm");
        String last_time = CommonUtils.formatTimeStamp(info.getLast_time() * 1000, "yyyy/MM/dd HH:mm");
        tv_sbczsj_num_r.setText(first_time);
        tv_sqjzsj_num_r.setText(last_time);
    }

    private void getCode(){
        if (mViewModel!=null){
            mViewModel.getCodeByUser1(new OnNetWorkListener<VerificationCodeVo>() {
                @Override
                public void onBefore() {

                }

                @Override
                public void onFailure(String message) {

                }

                @Override
                public void onSuccess(VerificationCodeVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            Toaster.show( "验证码发送成功");
                            String num = data.getData();
                            showDialogRefundCode(num);
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }

                @Override
                public void onAfter() {

                }
            });
        }

    }

    private LinearLayout lin_valueless;
    private TextView tv_valueless_explain;
    private TextView tv_valueless_explain_1;
    private TextView tv_valueless_explain_2;
    private TextView tv_valueless_explain_3;
    private void initValueless() {
        //没充值的号
        lin_top.setVisibility(View.GONE);
        lin_value.setVisibility(View.GONE);

        lin_valueless.setVisibility(View.VISIBLE);
        tv_valueless_explain = findViewById(R.id.tv_valueless_explain);
        tv_valueless_explain_1 = findViewById(R.id.tv_valueless_explain_1);
        tv_valueless_explain_2 = findViewById(R.id.tv_valueless_explain_2);
        tv_valueless_explain_3 = findViewById(R.id.tv_valueless_explain_3);
        SpannableString ss = new SpannableString("您还有1次退还机会未使用，充值后，游戏体验不满意可按要求申请退还");
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF803E")), 3, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_valueless_explain.setText(ss);

        SpannableString s1 = new SpannableString("1. 游戏首笔充值后2小时内可申请");
        s1.setSpan(new ForegroundColorSpan(Color.parseColor("#54A6FE")), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        s1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF803E")), 10, 14, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_valueless_explain_1.setText(s1);
        SpannableString s2 = new SpannableString("2. 收回小号，退还2小时内总充值福利币*50%");
        s2.setSpan(new ForegroundColorSpan(Color.parseColor("#54A6FE")), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        s2.setSpan(new ForegroundColorSpan(Color.parseColor("#FF803E")), 17, s2.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_valueless_explain_2.setText(s2);
        SpannableString s3 = new SpannableString("3. 退还至平台币，最高可退500福利币");
        s3.setSpan(new ForegroundColorSpan(Color.parseColor("#54A6FE")), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        s3.setSpan(new ForegroundColorSpan(Color.parseColor("#FF803E")), 14, s3.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_valueless_explain_3.setText(s3);
        tv_next.setText("去玩游戏");
        tv_next.setOnClickListener(view -> {
            pop();
        });
        tv_next.setBackgroundResource(R.drawable.shape_55c0fe_5571fe_big_radius);

    }


    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        Toaster.show("请重新登录");
        pop();
    }

    private void initData() {
        if (mViewModel!=null){
            mViewModel.setRefundDetail(gameId, new OnNetWorkListener<RefundDetailVo>() {
                @Override
                public void onBefore() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(String message) {

                }

                @Override
                public void onSuccess(RefundDetailVo data) {
                    showSuccess();
                    if (data!=null){
                        mInfo = data.getData();
                    }
                    showData(data);
                    if (isShow){
                        showTips();
                    }else {
                        doShowDialog();
                    }
                }

                @Override
                public void onAfter() {

                }
            });
        }
    }

    private void showTips() {
        if (mInfo != null && mInfo.getStatus() == 1 && !TextUtils.isEmpty(mInfo.getAmount()) && Float.parseFloat(mInfo.getAmount())==0f){
            CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_easy_to_play_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
            tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
                if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            });
            tipsDialog.show();
        }
    }

    private void showData(RefundDetailVo data) {
        if (data ==null){
            return;
        }
        RefundDetailVo.DataBean info = data.getData();
        if (info ==null){
            return;
        }
        int status = info.getStatus();
        //状态 0：未参与; 1:两小时内可退状态; 2:两小时内充值后且7天再未登录可退状态; 3:已过期不可退款; 4:已退款
        switch (status) {
            case 0:
                initValueless(); //未参与
                break;
            case 1:
                initValue2Hour(info);//两小时内可退状态
                break;
            case 2:
                initValue7Day(info);
                break;
            case 3:
                initValueOverTime(info);
                break;
            case 4:
                initValueRefund(info);
                break;
        }
    }

    private void doShowDialog() {
        SPUtils spUtils = new SPUtils(Constants.SP_COMMON_NAME + "_EASY_PLAY");
        boolean easyPlay = spUtils.getBoolean("EASY_PLAY",false);
        if (easyPlay){
            showTips();
            return;
        }
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_easy_play_default, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        CheckBox mCbShow = tipsDialog.findViewById(R.id.cb_show);

        tipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            showTips();
        });

        mCbShow.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                spUtils.putBoolean("EASY_PLAY", true);
            }else {
                if (spUtils.contains("EASY_PLAY")) {
                    spUtils.remove("EASY_PLAY");
                }
            }
        });
        tipsDialog.show();
        isShow = true;
    }
    private void showDialogRefund() {
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_easy_play_refund, null),
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        TextView tv_ktflb_num = tipsDialog.findViewById(R.id.tv_ktflb_num);
        if (mInfo!=null) {
            tv_ktflb_num.setText(mInfo.getAmount());
        }
        tipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.findViewById(R.id.tv_do).setOnClickListener(view -> {
            getCode();
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });

        tipsDialog.show();
    }
    CustomDialog codeDialog = null;
    TextView tv_code_do = null;
    Timer timer = null;
    int count = 60;
    String mCode = "";
    private void showDialogRefundCode(String num) {
        timer = new Timer();
        count = 60;
        codeDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_easy_play_refund_code, null),
                    WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        VerifyEditText edit_code = codeDialog.findViewById(R.id.edit_code);
        tv_code_do = codeDialog.findViewById(R.id.tv_do);

        TextView top_title_2 = codeDialog.findViewById(R.id.top_title_2);
        top_title_2.setText("已发送到您"+num+"的手机号");
        edit_code.setInputCompleteListener((et, content) -> {
            if (mCode.isEmpty()) {
                mCode = content;
            }else {
                return;
            }
            Log.d("Verify", "setInputCompleteListener---------------------"+ content);
            //调用回收号接口
            if (mViewModel!=null){
                mViewModel.setRefundDo(gameId, mCode, new OnNetWorkListener() {
                    @Override
                    public void onBefore() {

                    }

                    @Override
                    public void onFailure(String message) {
                        mCode = "";
                        Toaster.show(message);
                    }

                    @Override
                    public void onSuccess(BaseVo data) {
                        mCode = "";
                        if (data.isStateOK()) {
                            if (codeDialog != null && codeDialog.isShowing()) codeDialog.dismiss();
                            initData();
                        }else {
                            Toaster.show(data.getMsg());
                        }
                    }

                    @Override
                    public void onAfter() {

                    }
                });
            }
        });
        codeDialog.show();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (count<=0){
                    timer.cancel();
                    _mActivity.runOnUiThread(() -> {
                        try {
                            tv_code_do.setClickable(true);
                            tv_code_do.setText("重新获取");
                            tv_code_do.setOnClickListener(view -> {
                                getCode2();
                            });
                        }catch (Exception e){
                            Log.d("EasyToPay",e.toString());
                        }
                    });
                }else {
                    count--;
                    _mActivity.runOnUiThread(() -> {
                        try {
                            tv_code_do.setClickable(false);
                            tv_code_do.setText("重新获取(" + count + ")");
                        } catch (Exception e) {
                            Log.d("EasyToPay", e.toString());
                        }
                    });
                }
            }
        },0,1000);
    }
    private void getCode2(){
        if (mViewModel!=null){
            mViewModel.getCodeByUser1(new OnNetWorkListener<VerificationCodeVo>() {
                @Override
                public void onBefore() {

                }

                @Override
                public void onFailure(String message) {

                }

                @Override
                public void onSuccess(VerificationCodeVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (codeDialog!=null &&codeDialog.isShowing()) {
                                timer = new Timer();
                                count = 60;
                                try {
                                    tv_code_do.setClickable(false);
                                    tv_code_do.setText("重新获取("+count+")");
                                }catch (Exception e){

                                }
                                timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        if (count<=0){
                                            timer.cancel();
                                            _mActivity.runOnUiThread(() -> {
                                                try {
                                                    tv_code_do.setClickable(true);
                                                    tv_code_do.setText("重新获取");
                                                    tv_code_do.setOnClickListener(view -> {
                                                        getCode2();
                                                    });
                                                }catch (Exception e){

                                                }
                                            });
                                        }else {
                                            count--;
                                            _mActivity.runOnUiThread(() -> {
                                                try {
                                                    tv_code_do.setClickable(false);
                                                    tv_code_do.setText("重新获取(" + count + ")");
                                                } catch (Exception e) {

                                                }
                                            });
                                        }
                                    }
                                },0,1000);
                            }
                            Toaster.show( "验证码已重发");
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }

                @Override
                public void onAfter() {

                }
            });
        }

    }

    @Override
    public void onDestroy() {
        if (timer!=null) {
            timer.cancel();
        }
        super.onDestroy();
    }
}
