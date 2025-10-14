package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.item.MainPageItemVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2021/8/11 0011-10:38
 * @description
 */
public class TagGameNormalItemHolder extends BaseItemHolder<MainPageItemVo, TagGameNormalItemHolder.ViewHolder> {


    public TagGameNormalItemHolder(Context context) {
        super(context);
    }

    public TagGameNormalItemHolder(Context context, boolean showDate) {
        super(context);
        this.showDate = showDate;
    }

    private BaseFragment _mSubFragment;
    private boolean showDate = false;

    @Override
    protected void initView(View view) {
        super.initView(view);
        _mSubFragment = (BaseFragment) view.getTag(R.id.tag_sub_fragment);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_tag_game;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainPageItemVo item) {
        if (TextUtils.isEmpty(item.module_title) && TextUtils.isEmpty(item.module_title_two)) {
            holder.mTvTitle.setVisibility(View.GONE);
        } else {
            holder.mTvTitle.setVisibility(View.VISIBLE);
            String title = "";
            if (!TextUtils.isEmpty(item.module_title)){
                title += item.module_title;
            }
            if (!TextUtils.isEmpty(item.module_title_two)){
                title += item.module_title_two;
            }
            SpannableString spannableString = new SpannableString(title);
            if (!TextUtils.isEmpty(item.module_title) && !TextUtils.isEmpty(item.module_title_color)){
                try {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(item.module_title_color)), 0, item.module_title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }catch (Exception e){}
            }
            if (!TextUtils.isEmpty(item.module_title_two) && !TextUtils.isEmpty(item.module_title_two_color)){
                try {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(item.module_title_two_color)), title.length() - item.module_title_two.length(), title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }catch (Exception e){}
            }
            holder.mTvTitle.setText(spannableString);
        }
        if (!TextUtils.isEmpty(item.module_sub_title)){
            holder.mTvDescription.setVisibility(View.VISIBLE);
            holder.mTvDescription.setText(item.module_sub_title);
            try {
                if (!TextUtils.isEmpty(item.module_sub_title_color)) holder.mTvDescription.setTextColor(Color.parseColor(item.module_sub_title_color));
            }catch (Exception e){}
        }else {
            holder.mTvDescription.setVisibility(View.GONE);
        }
        if (item.additional != null){
            holder.mTvMore.setVisibility(View.VISIBLE);
            holder.mTvMore.setText(item.additional.text);
            try {
                if (!TextUtils.isEmpty(item.additional.textcolor)) holder.mTvMore.setTextColor(Color.parseColor(item.additional.textcolor));
            }catch (Exception e){}
            holder.mTvMore.setOnClickListener(v -> {
                appJump(item.additional.getPage_type(), item.additional.getParam());
            });
        }else {
            holder.mTvMore.setVisibility(View.GONE);
        }

        ViewGroup.LayoutParams layoutParams = holder.mRecyclerView.getLayoutParams();
        layoutParams.height = ScreenUtil.dp2px(mContext, 98) * item.data.size();
        holder.mRecyclerView.setLayoutParams(layoutParams);

        BaseRecyclerAdapter mPagerAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.class, new TagGameItemHolder(mContext, item.has_tag, showDate))
                .build().setTag(R.id.tag_fragment, _mFragment).setTag(R.id.tag_sub_fragment, _mSubFragment);
        holder.mRecyclerView.setAdapter(mPagerAdapter);
        mPagerAdapter.clear();
        mPagerAdapter.addAllData(item.data);
    }

    public class ViewHolder extends AbsHolder {
        private TextView mTvTitle;
        private TextView mTvMore;
        private TextView mTvDescription;
        private RecyclerView mRecyclerView;

        public ViewHolder(View view) {
            super(view);
            mTvTitle = view.findViewById(R.id.tv_title);
            mTvMore = view.findViewById(R.id.tv_more);
            mTvDescription = view.findViewById(R.id.tv_description);

            mRecyclerView = findViewById(R.id.recycler_view);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext) {
                @Override
                protected int getExtraLayoutSpace(RecyclerView.State state) {
                    return ScreenUtil.getScreenHeight(mContext);
                }
            };
            mRecyclerView.setLayoutManager(linearLayoutManager);
        }
    }
}
