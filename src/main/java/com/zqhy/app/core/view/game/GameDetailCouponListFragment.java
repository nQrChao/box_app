package com.zqhy.app.core.view.game;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.NewGameCouponItemVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.community.integral.CommunityIntegralMallFragment;
import com.zqhy.app.core.view.community.task.TaskCenterFragment;
import com.zqhy.app.core.view.game.holder.NewGameCouponListItemHolder;
import com.zqhy.app.core.view.user.BindPhoneFragment;
import com.zqhy.app.core.view.user.CertificationFragment;
import com.zqhy.app.core.view.user.newvip.NewUserVipFragment;
import com.zqhy.app.core.view.user.welfare.MyCouponsListFragment;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.RecyclerViewNoBugLinearLayoutManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 活动列表
 */
public class GameDetailCouponListFragment extends BaseFragment<GameViewModel> {

    public static GameDetailCouponListFragment newInstance(int gameid) {
        GameDetailCouponListFragment fragment = new GameDetailCouponListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("gameid", gameid);
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

    private int gameid;

    private BaseRecyclerAdapter couponListAdapter;
    private List<GameInfoVo.CouponListBean> gameCouponList = new ArrayList<>();
    private List<GameInfoVo.CouponListBean> shopCouponList = new ArrayList<>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_game_coupon_list_new;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    private ImageView mIvBack;
    private TextView mTvInstructionsTop;
    private RecyclerView couponListRecyclerView;
    private LinearLayout mLlEmpty;
    private TextView mTvGameCouponName;
    private TextView mTvGameCouponTips;
    private TextView mTvMallCouponName;
    private TextView mTvMallCouponTips;

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        if (getArguments() != null) {
            gameid = getArguments().getInt("gameid");
        }

        mIvBack = findViewById(R.id.iv_back);
        mTvInstructionsTop = findViewById(R.id.tv_instructions_top);

        mLlEmpty = findViewById(R.id.ll_empty);
        couponListRecyclerView = findViewById(R.id.recycler_view);
        RecyclerViewNoBugLinearLayoutManager layoutManager = new RecyclerViewNoBugLinearLayoutManager(_mActivity);
        couponListRecyclerView.setLayoutManager(layoutManager);
        couponListAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.CouponListBean.class, new NewGameCouponListItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
        couponListRecyclerView.setAdapter(couponListAdapter);

        mTvGameCouponName = findViewById(R.id.tv_game_coupon_name);
        mTvGameCouponTips = findViewById(R.id.tv_game_coupon_tips);
        mTvMallCouponName = findViewById(R.id.tv_mall_coupon_name);
        mTvMallCouponTips = findViewById(R.id.tv_mall_coupon_tips);

        mIvBack.setOnClickListener(v -> {
            pop();
        });
        mTvInstructionsTop.setOnClickListener(v -> {
            if (checkLogin()) startFragment(new MyCouponsListFragment());
        });

        findViewById(R.id.ll_game_coupon).setOnClickListener(v -> {
            couponListAdapter.setDatas(gameCouponList);
            couponListAdapter.notifyDataSetChanged();
            mTvGameCouponName.setTextColor(Color.parseColor("#5571FE"));
            mTvGameCouponTips.setTextColor(Color.parseColor("#5571FE"));
            mTvGameCouponTips.setBackgroundResource(R.drawable.shape_1a5571fe_big_radius);
            mTvMallCouponName.setTextColor(Color.parseColor("#232323"));
            mTvMallCouponTips.setTextColor(Color.parseColor("#9B9B9B"));
            mTvMallCouponTips.setBackgroundResource(R.drawable.shape_e2e2e2_big_radius);
            if (gameCouponList.size() > 0){
                couponListRecyclerView.setVisibility(View.VISIBLE);
                mLlEmpty.setVisibility(View.GONE);
            }else {
                couponListRecyclerView.setVisibility(View.GONE);
                mLlEmpty.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.ll_mall_coupon).setOnClickListener(v -> {
            couponListAdapter.setDatas(shopCouponList);
            couponListAdapter.notifyDataSetChanged();
            mTvGameCouponName.setTextColor(Color.parseColor("#232323"));
            mTvGameCouponTips.setTextColor(Color.parseColor("#9B9B9B"));
            mTvGameCouponTips.setBackgroundResource(R.drawable.shape_e2e2e2_big_radius);
            mTvMallCouponName.setTextColor(Color.parseColor("#5571FE"));
            mTvMallCouponTips.setTextColor(Color.parseColor("#5571FE"));
            mTvMallCouponTips.setBackgroundResource(R.drawable.shape_1a5571fe_big_radius);
            if (shopCouponList.size() > 0){
                couponListRecyclerView.setVisibility(View.VISIBLE);
                mLlEmpty.setVisibility(View.GONE);
            }else {
                couponListRecyclerView.setVisibility(View.GONE);
                mLlEmpty.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.tv_store).setOnClickListener(v -> {
            startFragment(new CommunityIntegralMallFragment());
        });
        findViewById(R.id.tv_vip).setOnClickListener(v -> {
            //startFragment(new NewUserVipFragment());
            //任务赚金
            startFragment(TaskCenterFragment.newInstance());
        });
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        getNetWorkData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getNetWorkData();
    }


    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getNetWorkData();
    }

    /**
     * 获取游戏代金券信息
     */
    private List<GameInfoVo.CouponListBean> gameinfoPartCouponList = new ArrayList<>();
    private void getNetWorkData() {
        if (mViewModel != null) {
            mViewModel.gameinfoPartCoupon(gameid, new OnBaseCallback<NewGameCouponItemVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                }
                @Override
                public void onSuccess(NewGameCouponItemVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null){
                                gameinfoPartCouponList = data.getData();

                                //设置代金券数据
                                gameCouponList.clear();
                                shopCouponList.clear();
                                for (int i = 0; i < gameinfoPartCouponList.size(); i++) {
                                    if ("game_coupon".equals(gameinfoPartCouponList.get(i).getCoupon_type())) {
                                        gameCouponList.add(gameinfoPartCouponList.get(i));
                                    }else if ("shop_goods".equals(gameinfoPartCouponList.get(i).getCoupon_type())){
                                        shopCouponList.add(gameinfoPartCouponList.get(i));
                                    }
                                }

                                if (gameCouponList.size() > 0){
                                    float coupon_amount = 0F;
                                    for (int i = 0; i < gameCouponList.size(); i++) {
                                        coupon_amount += gameCouponList.get(i).getAmount();
                                    }
                                    DecimalFormat decimalFormat = new DecimalFormat("0.0");
                                    String format = decimalFormat.format(coupon_amount);
                                    String amount = (format.indexOf(".0") != -1)? format.substring(0, format.indexOf(".0")): format;
                                    SpannableString spannableString = new SpannableString(amount + "元券");
                                    mTvGameCouponTips.setText(spannableString);
                                }else {
                                    mTvGameCouponTips.setText("暂无");
                                }

                                if (gameCouponList.size() != 0){
                                    couponListAdapter.setDatas(gameCouponList);
                                    couponListAdapter.notifyDataSetChanged();
                                    /*mTvGameCouponName.setTextColor(Color.parseColor("#5571FE"));
                                    mTvGameCouponTips.setTextColor(Color.parseColor("#5571FE"));
                                    mTvGameCouponTips.setBackgroundResource(R.drawable.shape_e2e2e2_big_radius);
                                    mTvMallCouponName.setTextColor(Color.parseColor("#232323"));
                                    mTvMallCouponTips.setTextColor(Color.parseColor("#9B9B9B"));
                                    mTvMallCouponTips.setBackgroundResource(R.drawable.shape_1a5571fe_big_radius);*/
                                    if (gameCouponList.size() > 0){
                                        couponListRecyclerView.setVisibility(View.VISIBLE);
                                        mLlEmpty.setVisibility(View.GONE);
                                    }else {
                                        couponListRecyclerView.setVisibility(View.GONE);
                                        mLlEmpty.setVisibility(View.VISIBLE);
                                    }
                                }/*else if (shopCouponList.size() != 0){
                                    couponListAdapter.clear();
                                    couponListAdapter.setDatas(shopCouponList);
                                    couponListAdapter.notifyDataSetChanged();
                                    mTvGameCouponName.setTextColor(Color.parseColor("#232323"));
                                    mTvGameCouponTips.setTextColor(Color.parseColor("#9B9B9B"));
                                    mTvMallCouponTips.setBackgroundResource(R.drawable.shape_1a5571fe_big_radius);
                                    mTvMallCouponName.setTextColor(Color.parseColor("#5571FE"));
                                    mTvMallCouponTips.setTextColor(Color.parseColor("#5571FE"));
                                    mTvMallCouponTips.setBackgroundResource(R.drawable.shape_e2e2e2_big_radius);
                                    if (shopCouponList.size() > 0){
                                        couponListRecyclerView.setVisibility(View.VISIBLE);
                                        mLlEmpty.setVisibility(View.GONE);
                                    }else {
                                        couponListRecyclerView.setVisibility(View.GONE);
                                        mLlEmpty.setVisibility(View.VISIBLE);
                                    }
                                }*/else {
                                    couponListRecyclerView.setVisibility(View.GONE);
                                    mLlEmpty.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            Toaster.show(data.getMsg());
                            //ToastT.error(_mActivity, data.getMsg());
                        }
                    }
                }
            });
        }
    }

    public void getCoupon(int coupon_id) {
        if (checkLogin()) {
            if (!UserInfoModel.getInstance().isBindMobile()) {
                startFragment(BindPhoneFragment.newInstance(false, ""));
                return;
            }
            if (TextUtils.isEmpty(UserInfoModel.getInstance().getUserInfo().getIdcard()) || TextUtils.isEmpty(UserInfoModel.getInstance().getUserInfo().getReal_name())){
                startFragment(CertificationFragment.newInstance());
                return;
            }
            if (mViewModel != null) {
                mViewModel.getCoupon(coupon_id, new OnBaseCallback() {
                    @Override
                    public void onSuccess(BaseVo data) {
                        if (data != null) {
                            if (data.isStateOK()) {
                                //ToastT.success(_mActivity, "领取成功");
                                Toaster.show("领取成功");
                                List<GameInfoVo.CouponListBean> coupon_list = gameinfoPartCouponList;
                                for (int i = 0; i < coupon_list.size(); i++) {
                                    if (Integer.parseInt(coupon_list.get(i).getId()) == coupon_id){
                                        coupon_list.get(i).setStatus(10);
                                    }
                                }
                                //设置代金券数据
                                for (int i = 0; i < gameinfoPartCouponList.size(); i++) {
                                    if (Integer.parseInt(gameinfoPartCouponList.get(i).getId()) == coupon_id){
                                        gameinfoPartCouponList.get(i).setStatus(10);
                                    }
                                }
                                for (int i = 0; i < gameCouponList.size(); i++) {
                                    if (Integer.parseInt(gameCouponList.get(i).getId()) == coupon_id){
                                        gameCouponList.get(i).setStatus(10);
                                    }
                                }
                                for (int i = 0; i < shopCouponList.size(); i++) {
                                    if (Integer.parseInt(shopCouponList.get(i).getId()) == coupon_id){
                                        shopCouponList.get(i).setStatus(10);
                                    }
                                }
                                couponListAdapter.notifyDataSetChanged();
                            } else {
                                Toaster.show(data.getMsg());
                                //ToastT.error(_mActivity, data.getMsg());
                            }
                        }
                    }
                });
            }
        }
    }

    private CustomDialog vipTipsDialog;
    public void showVipTipsDialog() {
        if (vipTipsDialog == null) {
            vipTipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_game_detail_vip_tips, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        }
        vipTipsDialog.findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            if (vipTipsDialog != null && vipTipsDialog.isShowing()){
                vipTipsDialog.dismiss();
            }
        });
        vipTipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if (vipTipsDialog != null && vipTipsDialog.isShowing()){
                vipTipsDialog.dismiss();
            }
            startFragment(new NewUserVipFragment());
        });
        vipTipsDialog.show();
    }
}
