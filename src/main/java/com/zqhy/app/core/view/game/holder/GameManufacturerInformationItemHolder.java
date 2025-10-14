package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.detail.GameRefundVo;
import com.zqhy.app.core.view.browser.BrowserActivity;
import com.zqhy.app.core.view.refund.RefundMainFragment;
import com.zqhy.app.newproject.R;

/**
 * @author leeham2734
 * @date 2020/5/9-14:45
 * @description
 */
public class GameManufacturerInformationItemHolder extends AbsItemHolder<GameInfoVo.VendorInfo, GameManufacturerInformationItemHolder.ViewHolder> {
    public GameManufacturerInformationItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_detail_manufacturer_information;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameInfoVo.VendorInfo item) {
        holder.mTvInformation.setText("供应商：" + item.getCpname());
        if (!TextUtils.isEmpty(item.getClient_version_name())){
            holder.mTvVersion.setText("软件版本：" + item.getClient_version_name());
        }else {
            holder.mTvVersion.setText("软件版本：-");
        }
        if (!TextUtils.isEmpty(item.getRecord_number())){
            holder.mTvFiling.setVisibility(View.VISIBLE);
            holder.mTvFiling.setText("备案号：" + item.getRecord_number());
        }else {
            holder.mTvFiling.setVisibility(View.GONE);
            holder.mTvFiling.setText("备案号：-");
        }

        if (!TextUtils.isEmpty(item.getPower_url()) && !TextUtils.isEmpty(item.getPrivacy_url())){
            holder.mLlAuthority.setVisibility(View.VISIBLE);
            holder.mTvAuthority.setVisibility(View.VISIBLE);
            holder.mTvAuthorityView.setVisibility(View.VISIBLE);
            holder.mTvPrivacy.setVisibility(View.VISIBLE);
            holder.mTvPrivacyView.setVisibility(View.VISIBLE);
        }else if (!TextUtils.isEmpty(item.getPower_url()) && TextUtils.isEmpty(item.getPrivacy_url())){
            holder.mLlAuthority.setVisibility(View.VISIBLE);
            holder.mTvAuthority.setVisibility(View.VISIBLE);
            holder.mTvAuthorityView.setVisibility(View.VISIBLE);
            holder.mTvPrivacy.setVisibility(View.GONE);
            holder.mTvPrivacyView.setVisibility(View.GONE);
        }else if (TextUtils.isEmpty(item.getPower_url()) && !TextUtils.isEmpty(item.getPrivacy_url())){
            holder.mLlAuthority.setVisibility(View.VISIBLE);
            holder.mTvAuthority.setVisibility(View.GONE);
            holder.mTvAuthorityView.setVisibility(View.GONE);
            holder.mTvPrivacy.setVisibility(View.VISIBLE);
            holder.mTvPrivacyView.setVisibility(View.VISIBLE);
        }else {
            holder.mLlAuthority.setVisibility(View.GONE);
            holder.mTvAuthority.setVisibility(View.GONE);
            holder.mTvAuthorityView.setVisibility(View.GONE);
            holder.mTvPrivacy.setVisibility(View.GONE);
            holder.mTvPrivacyView.setVisibility(View.GONE);
        }
        holder.mTvAuthorityView.setOnClickListener(v -> {
            if(TextUtils.isEmpty(item.getPower_url())) return;
            if (_mFragment != null){
                BrowserActivity.newInstance(_mFragment.getActivity(), item.getPower_url());
            }
        });
        holder.mTvPrivacyView.setOnClickListener(v -> {
            if(TextUtils.isEmpty(item.getPrivacy_url())) return;
            if (_mFragment != null){
                BrowserActivity.newInstance(_mFragment.getActivity(), item.getPrivacy_url());
            }
        });

        holder.mTvFold.setOnClickListener(view -> {
            if (holder.mTvVersion.getVisibility() == View.VISIBLE){
                holder.mTvVersion.setVisibility(View.GONE);
                holder.mTvFiling.setVisibility(View.GONE);
                holder.mLlAuthority.setVisibility(View.GONE);
                holder.mTvFold.setText("展开");
                holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_rebate_arrow_down), null);
            }else{
                holder.mTvVersion.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(item.getRecord_number())){
                    holder.mTvFiling.setVisibility(View.VISIBLE);
                }else {
                    holder.mTvFiling.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(item.getPower_url()) && !TextUtils.isEmpty(item.getPrivacy_url())){
                    holder.mLlAuthority.setVisibility(View.VISIBLE);
                }else if (!TextUtils.isEmpty(item.getPower_url()) && TextUtils.isEmpty(item.getPrivacy_url())){
                    holder.mLlAuthority.setVisibility(View.VISIBLE);
                }else if (TextUtils.isEmpty(item.getPower_url()) && !TextUtils.isEmpty(item.getPrivacy_url())){
                    holder.mLlAuthority.setVisibility(View.VISIBLE);
                }else {
                    holder.mLlAuthority.setVisibility(View.GONE);
                }
                holder.mTvFold.setText("收起");
                holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_rebate_arrow_up), null);
            }
        });

        /*holder.mTvFold.post(() -> {
            holder.mTvVersion.setVisibility(View.GONE);
            holder.mLlAuthority.setVisibility(View.GONE);
            holder.mTvFold.setText("展开");
            holder.mTvFold.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.mipmap.ic_game_detail_rebate_arrow_down), null);
        });*/
    }

    public class ViewHolder extends AbsHolder {

        private TextView mTvInformation;
        private TextView mTvVersion;
        private TextView mTvFiling;
        private LinearLayout mLlAuthority;
        private TextView mTvAuthority;
        private TextView mTvAuthorityView;
        private TextView mTvPrivacy;
        private TextView mTvPrivacyView;
        private TextView mTvFold;

        public ViewHolder(View view) {
            super(view);
            mTvInformation = view.findViewById(R.id.tv_information);
            mTvVersion = view.findViewById(R.id.tv_version);
            mTvFiling = view.findViewById(R.id.tv_filing);
            mLlAuthority = view.findViewById(R.id.ll_authority);
            mTvAuthority = view.findViewById(R.id.tv_authority);
            mTvAuthorityView = view.findViewById(R.id.tv_authority_view);
            mTvPrivacy = view.findViewById(R.id.tv_privacy);
            mTvPrivacyView = view.findViewById(R.id.tv_privacy_view);
            mTvFold = view.findViewById(R.id.tv_fold);
        }
    }
}
