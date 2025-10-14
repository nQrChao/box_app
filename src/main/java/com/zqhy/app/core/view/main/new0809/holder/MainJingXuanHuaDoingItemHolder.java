package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.box.common.glide.GlideApp;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.game.new0809.MainJingXuanDataVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2021/8/14 0014-10:38
 * @description
 */
public class MainJingXuanHuaDoingItemHolder extends BaseItemHolder<MainJingXuanDataVo.HuaDongDataBeanVo, MainJingXuanHuaDoingItemHolder.ViewHolder> {
    public MainJingXuanHuaDoingItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_main_page_tj_huadong;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MainJingXuanDataVo.HuaDongDataBeanVo item) {
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);

        holder.mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_main_banner_section_item, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                MyViewHolder viewHolder1 = (MyViewHolder)viewHolder;

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewHolder1.mIvImage.getLayoutParams();
                layoutParams.width = ScreenUtil.getScreenWidth(mContext)/2;
                layoutParams.height = layoutParams.width * 400 / 660;
                if (viewHolder.getLayoutPosition() == 0){
                    layoutParams.setMargins(ScreenUtil.dp2px(mContext, 20F), 0, ScreenUtil.dp2px(mContext, 10F), 0);
                }else if(viewHolder.getLayoutPosition() == item.data.size() - 1){
                    layoutParams.setMargins(0, 0, ScreenUtil.dp2px(mContext, 20F), 0);
                }
                viewHolder1.mIvImage.setLayoutParams(layoutParams);
                GlideApp.with(mContext).asBitmap()
                        .load(item.data.get(i).getPic())
                        .placeholder(R.mipmap.img_placeholder_v_2)
                        .error(R.mipmap.img_placeholder_v_2)
                        .into(viewHolder1.mIvImage);
                viewHolder1.itemView.setOnClickListener(v -> {
                    appJump(item.data.get(i).getPage_type(), item.data.get(i).getParam());
                });
            }

            @Override
            public int getItemCount() {
                return item.data.size();
            }

            class MyViewHolder extends RecyclerView.ViewHolder{
                private ImageView mIvImage;
                public MyViewHolder(@NonNull View itemView) {
                    super(itemView);
                    mIvImage = itemView.findViewById(R.id.iv_image);
                }
            }
        });
    }


    public class ViewHolder extends AbsHolder {
        private TextView mTvTitle;
        private TextView mTvDescription;
        private RecyclerView mRecyclerView;

        public ViewHolder(View view) {
            super(view);
            mTvTitle = view.findViewById(R.id.tv_title);
            mTvDescription = view.findViewById(R.id.tv_description);
            mRecyclerView = view.findViewById(R.id.recycler_view);

            ViewGroup.LayoutParams layoutParams = mRecyclerView.getLayoutParams();
            layoutParams.width = ScreenUtil.getScreenWidth(mContext);
            layoutParams.height = layoutParams.width * 200 / 660;
            mRecyclerView.setLayoutParams(layoutParams);
        }
    }
}
