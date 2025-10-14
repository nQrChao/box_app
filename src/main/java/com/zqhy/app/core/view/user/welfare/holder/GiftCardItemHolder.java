package com.zqhy.app.core.view.user.welfare.holder;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.welfare.MyGiftCardListVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.utils.TimeUtils;

/**
 * @author Administrator
 * @date 2018/11/15
 */

public class GiftCardItemHolder extends AbsItemHolder<MyGiftCardListVo.DataBean, GiftCardItemHolder.ViewHolder> {
    public GiftCardItemHolder(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MyGiftCardListVo.DataBean item) {
        int position = getPosition(holder);

        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mIvGameImage);
        holder.mTvGameName.setText(item.getGamename() + item.getCardname());

        /*holder.mIvGameImage.setOnClickListener(view -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
            }
        });
        holder.mTvGameName.setOnClickListener(view -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
            }
        });*/
        holder.findViewById(R.id.ll_all).setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.goGameDetail(item.getGameid(), item.getGame_type());
            }
        });
        String getTime = item.getGettime();

        try {
            long ms = Long.parseLong(getTime) * 1000;
            holder.mTvTime.setText(TimeUtils.formatTimeStamp(ms, "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.mTvGiftCode.setText(item.getCard());
        holder.mTvCopy.setOnClickListener(view -> {
            if (CommonUtils.copyString(mContext, holder.mTvGiftCode.getText().toString())) {
                Toaster.show( "礼包码已复制");
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_gift_card;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends AbsHolder {
        private ImageView mIvGameImage;
        private TextView mTvGameName;
        private TextView mTvTime;
        private TextView mTvGiftCode;
        private TextView mTvCopy;

        public ViewHolder(View view) {
            super(view);
            mIvGameImage = view.findViewById(R.id.iv_game_image);
            mTvGameName = view.findViewById(R.id.tv_game_name);
            mTvTime = view.findViewById(R.id.tv_time);
            mTvGiftCode = view.findViewById(R.id.tv_gift_code);
            mTvCopy = view.findViewById(R.id.tv_copy);


            float density = ScreenUtil.getScreenDensity(mContext);
            GradientDrawable gd = new GradientDrawable();
            gd.setShape(GradientDrawable.RECTANGLE);
            gd.setColor(ContextCompat.getColor(mContext, R.color.white));
            gd.setStroke((int) (1 * density), ContextCompat.getColor(mContext, R.color.color_3478f6));

            mTvCopy.setBackground(gd);
        }
    }
}
