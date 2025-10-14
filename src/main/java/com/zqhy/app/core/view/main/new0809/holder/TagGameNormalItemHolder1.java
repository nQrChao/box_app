package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.data.model.game.new0809.item.MainPageItemVo;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2021/8/11 0011-10:38
 * @description
 */
public class TagGameNormalItemHolder1 extends BaseItemHolder<MainPageItemVo, TagGameNormalItemHolder1.ViewHolder> {


    public TagGameNormalItemHolder1(Context context) {
        super(context);
    }

    private BaseFragment _mSubFragment;

    @Override
    protected void initView(View view) {
        super.initView(view);
        _mSubFragment = (BaseFragment) view.getTag(R.id.tag_sub_fragment);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_tag_game1;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainPageItemVo item) {
        BaseRecyclerAdapter mPagerAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(GameInfoVo.class, new TagGameItemHolder1(mContext, item.has_tag))
                .build().setTag(R.id.tag_fragment, _mFragment).setTag(R.id.tag_sub_fragment, _mSubFragment);
        holder.mRecyclerView.setAdapter(mPagerAdapter);
        mPagerAdapter.clear();
        mPagerAdapter.addAllData(item.data);
        boolean hasTitle = false;
        if (!TextUtils.isEmpty(item.module_title)) {
            holder.mTitle.setVisibility(View.VISIBLE);
            holder.mTitle.setText(item.module_title);
            try {
                holder.mTitle.setTextColor(Color.parseColor(item.module_title_color));
            } catch (Exception e) {

            }
            hasTitle = true;
        } else {
            holder.mTitle.setVisibility(View.GONE);
        }
        if (item.additional != null) {
            holder.mTvText.setVisibility(View.VISIBLE);
            holder.mTvText.setText(item.additional.text);
            try {
                holder.mTvText.setTextColor(Color.parseColor(item.additional.textcolor));
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.mTvText.setOnClickListener(v -> {
                appJump(item.additional);
            });
            hasTitle = true;
        } else {
            holder.mTvText.setVisibility(View.GONE);
        }
        if(!hasTitle){
            holder.mFlTitleContainer.setVisibility(View.GONE);
        }else{
            holder.mFlTitleContainer.setVisibility(View.VISIBLE);
        }
    }

    public class ViewHolder extends AbsHolder {
        private FrameLayout  mFlTitleContainer;
        private TextView     mTitle;
        private RecyclerView mRecyclerView;
        private TextView     mTvText;

        public ViewHolder(View view) {
            super(view);
            mFlTitleContainer = findViewById(R.id.fl_title_container);
            mTitle = findViewById(R.id.title);
            mRecyclerView = findViewById(R.id.recycler_view);
            mTvText = findViewById(R.id.tv_text);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(layoutManager);
        }
    }
}
