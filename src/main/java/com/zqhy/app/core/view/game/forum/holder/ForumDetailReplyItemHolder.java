package com.zqhy.app.core.view.game.forum.holder;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.donkingliang.imageselector.PreviewActivity;
import com.donkingliang.imageselector.entry.Image;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.forum.ForumReplyTopVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.community.comment.CommentDetailFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.imageview.ClipRoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/24
 */

public class ForumDetailReplyItemHolder extends AbsItemHolder<ForumReplyTopVo, ForumDetailReplyItemHolder.ViewHolder> {

    private float density;

    public ForumDetailReplyItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_forum_detail_reply_top;
    }

    @Override
    public ForumDetailReplyItemHolder.ViewHolder createViewHolder(View view) {
        return new ForumDetailReplyItemHolder.ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ForumDetailReplyItemHolder.ViewHolder holder, @NonNull ForumReplyTopVo item) {
        //icon
        GlideUtils.loadCircleImage(mContext, item.getIcon(), holder.mCivPortrait, R.mipmap.ic_user_login_new_sign);
        holder.mTvUserNickname.setText(item.getNickname());
        holder.tv_time.setText(item.getTime_description());
        holder.tv_play_time.setText(item.getGame_duration());

        holder.tv_comments.setText(item.getReply_count()+"");
        holder.tv_comment_like.setText(item.getLike_count()+"");

        holder.mIvCommentPic1.setOnClickListener(v -> showCommentPics(item.getPic(), 0));
        holder.mIvCommentPic2.setOnClickListener(v -> showCommentPics(item.getPic(), 1));
        holder.mIvCommentPic3.setOnClickListener(v -> showCommentPics(item.getPic(), 2));
    }

    private void commentToDetail(int cid, int position) {
        if (_mFragment != null) {
            _mFragment.start(CommentDetailFragment.newInstance(cid, position));
        }
    }


    private void showCommentPics(List<String> picBeanList, int position) {
        if (_mFragment != null) {
            if (picBeanList == null) {
                return;
            }
            //预览图片
            ArrayList<Image> images = new ArrayList();
            for (String picBean : picBeanList) {
                Image image = new Image();
                image.setType(1);
                image.setPath(picBean);
                images.add(image);
            }
            PreviewActivity.openActivity(_mFragment.getActivity(), images, true, position, true, true);
        }
    }


    public class ViewHolder extends AbsHolder {
        private LinearLayout mFlRootView;
        private ClipRoundImageView mCivPortrait;
        private TextView           mTvUserNickname;
        private TextView           tv_title;
        private TextView           tv_time;
        private TextView           tv_play_time;
        private TextView           tv_comments;
        private TextView           tv_comment_like;
        private TextView           tv_comment_content;
        private ImageView mIvCommentPic1;
        private ImageView          mIvCommentPic2;
        private FrameLayout mFlCommentPic3;
        private ImageView          mIvCommentPic3;

        public ViewHolder(View view) {
            super(view);

            mFlRootView = findViewById(R.id.fl_rootView);
            tv_time = findViewById(R.id.tv_time);
            tv_play_time = findViewById(R.id.tv_play_time);
            tv_comments = findViewById(R.id.tv_comments);
            tv_comment_like = findViewById(R.id.tv_comment_like);
            tv_comment_content = findViewById(R.id.tv_comment_content);
            tv_title = findViewById(R.id.tv_title);
            mCivPortrait = findViewById(R.id.civ_portrait);
            mTvUserNickname = findViewById(R.id.tv_user_nickname);

            mIvCommentPic1 = findViewById(R.id.iv_comment_pic_1);
            mIvCommentPic2 = findViewById(R.id.iv_comment_pic_2);
            mFlCommentPic3 = findViewById(R.id.fl_comment_pic_3);
            mIvCommentPic3 = findViewById(R.id.iv_comment_pic_3);
        }
    }


}
