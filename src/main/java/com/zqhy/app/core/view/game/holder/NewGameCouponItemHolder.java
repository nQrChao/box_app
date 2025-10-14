package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.NewGameCouponItemVo;
import com.zqhy.app.core.data.model.game.detail.GameActivityVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.community.task.TaskCenterFragment;
import com.zqhy.app.core.view.game.GameDetaiActivityFragment;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.core.view.login.LoginActivity;
import com.zqhy.app.core.view.user.newvip.NewUserVipFragment;
import com.zqhy.app.core.view.user.provincecard.NewProvinceCardFragment;
import com.zqhy.app.core.view.user.welfare.MyCouponsListFragment;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/21
 */

public class NewGameCouponItemHolder extends AbsItemHolder<NewGameCouponItemVo, NewGameCouponItemHolder.ViewHolder>{

    private int gameid;
    private CouponListAdapter mAdapter;
    private final int ACTION_COUPON_LIST           = 0x461;

    public NewGameCouponItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull NewGameCouponItemVo item) {
        gameid = item.getGameInfoVo().getGameid();
        List<GameInfoVo.CouponListBean> data = item.getData();
        GameInfoVo gameInfoVo = item.getGameInfoVo();
        if (data == null){
            data = new ArrayList<>();
        }
        holder.mViewFlipper.removeAllViews();
        GameActivityVo gameActivityVo = gameInfoVo.getGameActivityVo();
        if (gameActivityVo.getItemCount() > 0) {
            List<GameActivityVo.ItemBean> itemBeanList = new ArrayList<>();
            //itemBeanList.addAll(createTopMenuBeans(gameActivityVo));
            itemBeanList.addAll(createNewsBeans(gameActivityVo));
            if (itemBeanList.size() > 0){
                for (GameActivityVo.ItemBean itemBean:itemBeanList) {
                    View inflate = LayoutInflater.from(mContext).inflate(R.layout.viewflipper_item_new, null, false);
                    TextView mTvTitle = inflate.findViewById(R.id.tv_title);
                    TextView mTvContent = inflate.findViewById(R.id.tv_content);
                    mTvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    mTvContent.setTextColor(Color.parseColor("#FF707C"));
                    if (itemBean.getType() == 1) {
                        GameInfoVo.NewslistBean newslistBean = itemBean.getNewslistBean();
                        if (newslistBean != null) {
                            mTvTitle.setText(newslistBean.getTitle());
                            mTvContent.setText(newslistBean.getTitle2() + " (" + CommonUtils.timeStamp2Date(Long.parseLong(newslistBean.getFabutime() + "000"), "yy-MM-dd HH:mm") + ")");
                        }
                    }else if (itemBean.getType() == 2) {
                        GameActivityVo.TopMenuInfoBean topMenuInfoBean = itemBean.getMenuInfoBean();
                        if (topMenuInfoBean != null) {
                            mTvTitle.setText(topMenuInfoBean.getTag());
                            mTvContent.setText(topMenuInfoBean.getMessage());
                        }
                    }
                    holder.mViewFlipper.addView(inflate);
                    holder.mViewFlipper.startFlipping();
                    holder.mViewFlipper.setAutoStart(true);
                    if (holder.mViewFlipper.getChildCount() < 2){
                        holder.mViewFlipper.stopFlipping();
                        holder.mViewFlipper.setAutoStart(false);
                    }
                }
            }else {
                View view = LayoutInflater.from(mContext).inflate(R.layout.viewflipper_item_new, null, false);
                holder.mViewFlipper.addView(view);
                holder.mViewFlipper.stopFlipping();
                holder.mViewFlipper.setAutoStart(false);
            }
        }else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.viewflipper_item_new, null, false);
            holder.mViewFlipper.addView(view);
            holder.mViewFlipper.stopFlipping();
            holder.mViewFlipper.setAutoStart(false);
        }

        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String format = decimalFormat.format(gameInfoVo.getCoupon_amount());
        String amount = (format.indexOf(".0") != -1)? format.substring(0, format.indexOf(".0")): format;
        if (!"0".equals(amount)){
            SpannableString spannableString = new SpannableString("送" + amount + "元游戏代金券");
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    ds.setColor(Color.parseColor("#FF6337"));
                }

                @Override
                public void onClick(@NonNull View widget) {}
            }, 1, amount.length() + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.mTvCouponAmount.setText(spannableString);
            holder.mTvCouponAmount.setMovementMethod(LinkMovementMethod.getInstance());
        }else {
            boolean hasMallCoupon = false;
            for (int i = 0; i < data.size(); i++) {
                if (!TextUtils.isEmpty(data.get(i).getCoupon_type()) && data.get(i).getCoupon_type().equals("shop_goods")){//判断是否是商城券
                    hasMallCoupon = true;
                }
            }
            if (hasMallCoupon){
                holder.mTvCouponAmount.setText("推荐福利券");
            }else {
                holder.mTvCouponAmount.setText("暂无券，为你推荐");
            }
        }

        holder.mTvCouponTips.setOnClickListener(v -> {
            if (_mFragment != null){
                if (UserInfoModel.getInstance().isLogined()){
                    //_mFragment.startFragment(GameWelfareFragment.newInstance(2));
                    _mFragment.startFragment(new MyCouponsListFragment());
                }else {
                    _mFragment.startActivity(new Intent(mContext, LoginActivity.class));
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
        List<GameInfoVo.CouponListBean> couponListBeans = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (i < 3){
                couponListBeans.add(data.get(i));
            }
        }
        mAdapter = new CouponListAdapter(mContext, couponListBeans);

        holder.mRecyclerView.setAdapter(mAdapter);

        holder.mLlMemberContent.setVisibility(View.GONE);
        if (couponListBeans.size() == 0){
            holder.mLlEmpty.setVisibility(View.VISIBLE);
            holder.mLlRecyclerView.setVisibility(View.GONE);
            holder.mTvConfrim.setOnClickListener(v -> {
                if (_mFragment != null) {
                    _mFragment.startFragment(TaskCenterFragment.newInstance());
                }
            });
        }else {
            holder.mLlEmpty.setVisibility(View.GONE);
            holder.mLlRecyclerView.setVisibility(View.VISIBLE);
        }

        holder.mLlCard.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.startFragment(new NewProvinceCardFragment());
            }
        });
        holder.mLlMember.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.start(new NewUserVipFragment());
            }
        });
        holder.mLlActivi.setOnClickListener(v -> {
            if (_mFragment != null) {
                if (gameActivityVo.getItemCount() > 0 && gameActivityVo.getActivity() != null && gameActivityVo.getActivity().size() > 0){
                    _mFragment.startFragment(GameDetaiActivityFragment.newInstance(gameInfoVo.getGamename(), gameInfoVo.getGameid()));
                }else{
                    Toaster.show("暂无，敬请期待！");
                }
            }
        });
        holder.mIvArrow.setOnClickListener(v -> {
            if (_mFragment != null){
                ((GameDetailInfoFragment)_mFragment).showCouponListDialog(0);
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_coupon_new;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private ViewFlipper mViewFlipper;
        private TextView      mTvCouponAmount;
        private TextView      mTvCouponTips;
        private RecyclerView mRecyclerView;
        private LinearLayout mLlMemberContent;
        private LinearLayout  mLlCard;
        private LinearLayout mLlMember;
        private LinearLayout mLlEmpty;
        private TextView mTvConfrim;
        private LinearLayout mLlActivi;
        private ImageView    mIvArrow;
        private LinearLayout    mLlRecyclerView;

        public ViewHolder(View view) {
            super(view);
            mViewFlipper = view.findViewById(R.id.viewflipper);
            mTvCouponAmount = view.findViewById(R.id.tv_coupon_amount);
            mTvCouponTips = view.findViewById(R.id.tv_coupon_tips);
            mRecyclerView = view.findViewById(R.id.recyclerView);
            mLlMemberContent = view.findViewById(R.id.ll_member_content);
            mLlCard = view.findViewById(R.id.ll_card);
            mLlMember = view.findViewById(R.id.ll_member);
            mLlEmpty = view.findViewById(R.id.ll_empty);
            mTvConfrim = view.findViewById(R.id.tv_confirm);
            mLlActivi = view.findViewById(R.id.ll_activi);
            mIvArrow = view.findViewById(R.id.iv_arrow);
            mLlRecyclerView = view.findViewById(R.id.ll_recyclerView);
        }
    }

    /**
     * 创建new item
     *
     * @param gameActivityVo
     * @return
     */
    private List<GameActivityVo.ItemBean> createNewsBeans(GameActivityVo gameActivityVo) {
        List<GameActivityVo.ItemBean> topMenuInfoBeanList = new ArrayList<>();

        if (gameActivityVo.getActivity() == null) {
            return topMenuInfoBeanList;
        }

        for (GameInfoVo.NewslistBean newslistBean : gameActivityVo.getActivity()) {
            GameActivityVo.ItemBean itemBean = new GameActivityVo.ItemBean();
            itemBean.setType(1);
            itemBean.setNewslistBean(newslistBean);
            topMenuInfoBeanList.add(itemBean);
        }
        return topMenuInfoBeanList;
    }

    class CouponListAdapter extends AbsAdapter<GameInfoVo.CouponListBean> {

        private List<GameInfoVo.CouponListBean> labels;
        public CouponListAdapter(Context context, List<GameInfoVo.CouponListBean> labels) {
            super(context, labels);
            this.labels = labels;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, GameInfoVo.CouponListBean item, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)viewHolder.mLlBg.getLayoutParams();
            int width = (ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 85)) / 3;
            layoutParams.width = width;
            layoutParams.height = width / 2;
            if(position == labels.size() - 1){
                layoutParams.setMargins(0, 0, 0, 0);
            }else {
                layoutParams.setMargins(0, 0, ScreenUtil.dp2px(mContext, 10), 0);
            }
            viewHolder.mLlBg.setLayoutParams(layoutParams);
            if (!TextUtils.isEmpty(item.getCoupon_type()) && item.getCoupon_type().equals("game_coupon")){//游戏券
                viewHolder.mTvVipTag.setVisibility(View.GONE);
                viewHolder.mTvReceive.setText("免\n费");
                viewHolder.mTvReceive.setTextColor(Color.parseColor("#FFFFFF"));
                viewHolder.mTvReceive.setBackgroundResource(R.mipmap.ic_game_detail_coupon_other_bg);
            }else if (!TextUtils.isEmpty(item.getCoupon_type()) && item.getCoupon_type().equals("shop_goods")){//商城券
                viewHolder.mTvVipTag.setVisibility(View.GONE);
                viewHolder.mTvReceive.setText("兑\n换");
                viewHolder.mTvReceive.setTextColor(Color.parseColor("#FFFFFF"));
                viewHolder.mTvReceive.setBackgroundResource(R.mipmap.ic_game_detail_coupon_other_bg);
            }
            if (item.getSign() == 1){//判断是不是vip券
                viewHolder.mTvVipTag.setVisibility(View.VISIBLE);
                viewHolder.mTvReceive.setText("免\n费");
                viewHolder.mTvReceive.setTextColor(Color.parseColor("#361702"));
                viewHolder.mTvReceive.setBackgroundResource(R.mipmap.ic_game_detail_coupon_vip_bg);
            }else {
                viewHolder.mTvVipTag.setVisibility(View.GONE);
            }
            DecimalFormat decimalFormat = new DecimalFormat("0.0");
            String format = decimalFormat.format(item.getAmount());
            String amount = (format.indexOf(".0") != -1)? format.substring(0, format.indexOf(".0")): format;
            viewHolder.mTvAmount.setText(amount);
            viewHolder.mTvCondition.setText(item.getCondition());

            viewHolder.itemView.setOnClickListener(v -> {
                /*if (item.getGameid() > 0){
                    if (_mFragment != null){
                        _mFragment.startForResult(GameCouponListFragment.newInstance(gameid), ACTION_COUPON_LIST);
                    }
                }else{
                    if (_mFragment != null){
                        _mFragment.startFragment(new CommunityIntegralMallFragment());
                    }
                }*/
                if (_mFragment != null){
                    if(!TextUtils.isEmpty(item.getCoupon_type()) && item.getCoupon_type().equals("game_coupon")){
                        ((GameDetailInfoFragment)_mFragment).showCouponListDialog(0);
                    }else if (!TextUtils.isEmpty(item.getCoupon_type()) && item.getCoupon_type().equals("shop_goods")){
                        ((GameDetailInfoFragment)_mFragment).showCouponListDialog(1);
                    }
                }
            });
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_game_list_coupon_new;
        }

        @Override
        public AbsViewHolder createViewHolder(View view) {
            return new CouponListAdapter.ViewHolder(view);
        }

        class ViewHolder extends AbsAdapter.AbsViewHolder {
            private RelativeLayout mLlBg;
            private TextView       mTvAmountType;
            private TextView mTvAmount;
            private TextView mTvCondition;
            private TextView mTvReceive;

            private TextView mTvVipTag;

            public ViewHolder(View view) {
                super(view);
                mLlBg = view.findViewById(R.id.ll_bg);
                mTvAmountType = view.findViewById(R.id.tv_amount_type);
                mTvAmount = view.findViewById(R.id.tv_amount);
                mTvCondition = view.findViewById(R.id.tv_condition);
                mTvReceive = view.findViewById(R.id.tv_receive);
                mTvVipTag = view.findViewById(R.id.tv_vip_tag);
            }
        }
    }
}
