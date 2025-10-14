package com.zqhy.app.core.view.game.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.imageselector.PreviewActivity;
import com.donkingliang.imageselector.entry.Image;
import com.zqhy.app.adapter.abs.AbsAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.game.ReportItemVo;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2018/11/21
 */

public class MyReportItemHolder extends AbsItemHolder<ReportItemVo.DataBean, MyReportItemHolder.ViewHolder>{
    public MyReportItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ReportItemVo.DataBean item) {
        holder.mTvGamename.setText(item.getGamename());
        holder.mTvUsername.setText(item.getUsername());
        holder.mTvPlatform.setText(item.getPlatform_name());
        holder.mTvDownload.setText(item.getDownload_url());
        holder.mTvDiscount.setText(item.getDiscount());
        holder.mTvPhone.setText(item.getMobile());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
        holder.mRecyclerView.setAdapter(new ImageListAdapter(mContext, item.getPic()));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_my_report;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    class ImageListAdapter extends AbsAdapter<String>{

        private ArrayList<Image> images = new ArrayList<>();
        public ImageListAdapter(Context context, List<String> labels) {
            super(context, labels);
            for (int i = 0; i < labels.size(); i++) {
                Image image = new Image();
                //网络图片
                image.setType(1);
                image.setPath(labels.get(i));
                images.add(image);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, String data, int position) {
            Holder holder = (Holder) viewHolder;
            GlideUtils.loadRoundImage(mContext, data, holder.mIvPic, R.mipmap.ic_placeholder);
            holder.mIvPic.setOnClickListener(v -> {
                if (_mFragment != null) PreviewActivity.openActivity(_mFragment.getActivity(), images, true, position, true);
            });
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_report_image;
        }

        @Override
        public AbsViewHolder createViewHolder(View view) {
            return new Holder(view);
        }

        class Holder extends AbsViewHolder{
            private ImageView mIvPic;

            public Holder(View view) {
                super(view);
                mIvPic = view.findViewById(R.id.iv_pic);
            }
        }
    }

    public class ViewHolder extends AbsHolder {

        private TextView mTvGamename;
        private TextView mTvUsername;
        private TextView mTvPlatform;
        private TextView mTvDownload;
        private TextView mTvDiscount;
        private TextView mTvPhone;
        private RecyclerView mRecyclerView;

        public ViewHolder(View view) {
            super(view);
            mTvGamename = view.findViewById(R.id.tv_gamename);
            mTvUsername = view.findViewById(R.id.tv_username);
            mTvPlatform = view.findViewById(R.id.tv_platform);
            mTvDownload = view.findViewById(R.id.tv_download);
            mTvDiscount = view.findViewById(R.id.tv_discount);
            mTvPhone = view.findViewById(R.id.tv_phone);
            mRecyclerView = view.findViewById(R.id.recyclerView);
        }
    }
}
