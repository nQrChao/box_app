package com.zqhy.app.core.view.community.comment.holder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.community.CommunityInfoVo;
import com.zqhy.app.core.data.model.community.comment.CommentInfoVo;
import com.zqhy.app.core.data.model.game.GameShortCommentVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class NewGameShortCommentItemHolder extends AbsItemHolder<GameShortCommentVo, NewGameShortCommentItemHolder.ViewHolder> {

    private float density;

    public NewGameShortCommentItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_info_short_comment_list;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameShortCommentVo item) {
        holder.mViewFlipper.removeAllViews();
        if (item != null && item.getShort_comment_list() != null && item.getShort_comment_list().size() > 0){
            for (CommentInfoVo.DataBean bean:item.getShort_comment_list()) {
                CommunityInfoVo communityInfoVo = bean.getCommunity_info();
                View inflate = LayoutInflater.from(mContext).inflate(R.layout.viewflipper_item_short_comment, null, false);
                GlideUtils.loadCircleImage(mContext, communityInfoVo.getUser_icon(), inflate.findViewById(R.id.iv_icon), R.mipmap.ic_user_login_new_sign);

                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_game_info_stort_comment_left);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                SpannableString spannableString = new SpannableString("x " + bean.getContent());
                spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_POINT_POINT);
                TextView mTvContent = (TextView) inflate.findViewById(R.id.tv_content);
                mTvContent.setText("");
                mTvContent.append(spannableString);

                holder.mViewFlipper.addView(inflate);
            }
        }
        holder.mViewFlipper.setOnClickListener(v -> {
            if (_mFragment != null){
                ((GameDetailInfoFragment) _mFragment).toShortCommentDetail();
            }
        });

    }

    public class ViewHolder extends AbsHolder {
        private ViewFlipper mViewFlipper;

        public ViewHolder(View view) {
            super(view);
            mViewFlipper = view.findViewById(R.id.viewflipper);
        }
    }

}
