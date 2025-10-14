package com.zqhy.app.core.view.main.new0809.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.box.common.glide.GlideApp;
import com.zqhy.app.core.data.model.game.GameInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.main.holder.GameNormalItemHolder;
import com.zqhy.app.glide.GlideRoundTransformNew;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2021/8/11 0011-10:59
 * @description
 */
public class TagZkGameItemHolder extends GameNormalItemHolder {

    private int size;
    public TagZkGameItemHolder(Context context, int size) {
        super(context);
        this.size = size;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_normal_tag_zk;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameNormalItemHolder.ViewHolder viewHolder, @NonNull GameInfoVo gameInfoVo) {
        super.onBindViewHolder(viewHolder, gameInfoVo);
        if (!TextUtils.isEmpty(gameInfoVo.getBg_pic())){
            ((TagZkGameItemHolder.ViewHolder) viewHolder).mIvTopImage.setVisibility(View.VISIBLE);
            GlideApp.with(mContext)
                    .asBitmap()
                    .load(gameInfoVo.getBg_pic())
                    .placeholder(R.mipmap.img_placeholder_v_2)
                    .transform(new GlideRoundTransformNew(mContext, 6))
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            if (bitmap != null && ((TagZkGameItemHolder.ViewHolder) viewHolder).mIvTopImage.getLayoutParams() != null) {
                                ((TagZkGameItemHolder.ViewHolder) viewHolder).mIvTopImage.setImageBitmap(bitmap);
                                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ((TagZkGameItemHolder.ViewHolder) viewHolder).mIvTopImage.getLayoutParams();
                                int width = ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dp2px(mContext, 12) * 2;
                                int height = width * bitmap.getHeight() / bitmap.getWidth();
                                params.width = width;
                                params.height = height;
                                ((TagZkGameItemHolder.ViewHolder) viewHolder).mIvTopImage.setLayoutParams(params);
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable drawable) {

                        }
                    });
            ((TagZkGameItemHolder.ViewHolder) viewHolder).mIvTopImage.setOnClickListener(v -> {
                if (_mFragment != null) {
                    _mFragment.goGameDetail(gameInfoVo.getGameid(), gameInfoVo.getGame_type());
                }
            });
        }else {
            ((TagZkGameItemHolder.ViewHolder) viewHolder).mIvTopImage.setVisibility(View.GONE);
        }

        if (viewHolder.getLayoutPosition() == size - 1){
            ((ViewHolder) viewHolder).mViewLine.setVisibility(View.GONE);
        }else{
            ((ViewHolder) viewHolder).mViewLine.setVisibility(View.VISIBLE);
        }

    }

    public class ViewHolder extends GameNormalItemHolder.ViewHolder {

        private ImageView mIvTopImage;
        private View mViewLine;

        public ViewHolder(View view) {
            super(view);
            mIvTopImage = view.findViewById(R.id.iv_top_image);
            mViewLine = view.findViewById(R.id.view_line);
        }
    }
}
