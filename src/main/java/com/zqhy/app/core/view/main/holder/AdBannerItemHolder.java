package com.zqhy.app.core.view.main.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.BaseItemHolder;
import com.zqhy.app.core.data.model.jump.AppJumpInfoBean;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 *
 * @author Administrator
 * @date 2018/11/23
 */

public class AdBannerItemHolder extends BaseItemHolder<AppJumpInfoBean,AdBannerItemHolder.ViewHolder> {
    public AdBannerItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull AppJumpInfoBean item) {
        GlideUtils.loadRoundImage(mContext,item.getPic(),holder.mIvNewsBanner,R.mipmap.img_placeholder_v_3);
        holder.mIvNewsBanner.setOnClickListener(view -> {
            appJump(item);
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_ad_banner;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {

        private LinearLayout mLlNewsBanner;
        private View mViewLine;
        private ImageView mIvNewsBanner;

        public ViewHolder(View view) {
            super(view);
            mLlNewsBanner = findViewById(R.id.ll_news_banner);
            mViewLine = findViewById(R.id.view_line);
            mIvNewsBanner = findViewById(R.id.iv_news_banner);

        }
    }
}
