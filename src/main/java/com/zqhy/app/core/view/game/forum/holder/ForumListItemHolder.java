package com.zqhy.app.core.view.game.forum.holder;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.box.common.glide.GlideApp;
import com.box.other.hjq.toast.Toaster;
import com.donkingliang.imageselector.PreviewActivity;
import com.donkingliang.imageselector.entry.Image;
import com.google.android.material.imageview.ShapeableImageView;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.forum.ForumListVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.community.comment.CommentDetailFragment;
import com.zqhy.app.core.view.community.user.CommunityUserFragment;
import com.zqhy.app.core.view.game.forum.tool.GlideImageGetter;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.imageview.ClipRoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class ForumListItemHolder extends AbsItemHolder<ForumListVo.DataBean, ForumListItemHolder.ViewHolder> {

    private float density;
    private OnClickInterface clickInterface;

    public void setClickInterface(OnClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }

    public interface OnClickInterface {
        void onReport(String tid, String name);

        void onLike(String tid, ForumListVo.DataBean bean);
    }

    public ForumListItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_forum_list;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ForumListVo.DataBean item) {
        //icon
        GlideUtils.loadCircleImage(mContext, item.getIcon(), holder.mCivPortrait, R.mipmap.ic_user_login_new_sign);
        holder.mCivPortrait.setOnClickListener(v -> {
            if (_mFragment!=null) {
                if (item.getPlat_id()==4) {
                    _mFragment.startFragment(CommunityUserFragment.newInstance(item.getUid()));
                }else {
                    Toaster.show("ta很神秘");
                    //ToastT.warning("ta很神秘");
                }
            }
        });
        holder.mTvUserNickname.setOnClickListener(v -> {
            if (_mFragment!=null) {
                if (item.getPlat_id()==4) {
                    _mFragment.startFragment(CommunityUserFragment.newInstance(item.getUid()));
                }else {
                    Toaster.show("ta很神秘");
                    //ToastT.warning("ta很神秘");
                }
            }
        });

        holder.mTvUserNickname.setText(item.getNickname());
        holder.tv_time.setText(item.getTime_description());
        holder.tv_play_time.setText(item.getGame_duration());

        holder.tv_comments.setText(item.getReply_count() + "");
        holder.tv_comment_like.setText(item.getLike_count() + "");
        if (item.getLike_count() > 99) {
            holder.tv_comment_like.setText("99+");
        } else {
            holder.tv_comment_like.setText(item.getLike_count() + "");
        }
        if (item.getLike_status() == 0) {
            holder.tv_comment_like.setTextColor(Color.parseColor("#999999"));
            holder.tv_comment_like.setCompoundDrawablesWithIntrinsicBounds(_mFragment.getResources().getDrawable(R.mipmap.ic_item_forum_list_1), null, null, null);
        } else {
            holder.tv_comment_like.setTextColor(Color.parseColor("#5571FE"));
            holder.tv_comment_like.setCompoundDrawablesWithIntrinsicBounds(_mFragment.getResources().getDrawable(R.mipmap.ic_item_forum_list_1_t), null, null, null);
        }
        holder.tv_comment_like.setOnClickListener(v -> {
            if (clickInterface != null) {
                clickInterface.onLike(item.getTid() + "", item);
            }
        });

        if (item.getTitle().isEmpty()) {
            holder.tv_title.setVisibility(View.GONE);
        } else {
            holder.tv_title.setVisibility(View.VISIBLE);
            holder.tv_title.setText(item.getTitle());
        }

        if (item.getPic().size() >= 3) {
            holder.iv_comment1_pic_1.setVisibility(View.GONE);
            holder.ll_comment_pics3.setVisibility(View.VISIBLE);
            holder.ll_comment_pics2.setVisibility(View.GONE);

            int widths = _mFragment.getActivity().getWindow().getDecorView().getWidth();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.mIvCommentPic1.getLayoutParams();
            layoutParams.width = (widths - ScreenUtil.dp2px(mContext, 55))/3;
            layoutParams.height = (widths - ScreenUtil.dp2px(mContext, 55))/3;
            holder.mIvCommentPic1.setLayoutParams(layoutParams);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) holder.mIvCommentPic2.getLayoutParams();
            layoutParams2.width = (widths - ScreenUtil.dp2px(mContext, 55))/3;
            layoutParams2.height = (widths - ScreenUtil.dp2px(mContext, 55))/3;
            holder.mIvCommentPic2.setLayoutParams(layoutParams2);
            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) holder.mIvCommentPic3.getLayoutParams();
            layoutParams3.width = (widths - ScreenUtil.dp2px(mContext, 55))/3;
            layoutParams3.height = (widths - ScreenUtil.dp2px(mContext, 55))/3;
            holder.mIvCommentPic3.setLayoutParams(layoutParams3);
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) holder.fl_comment_pic_3.getLayoutParams();
            layoutParams4.width = (widths - ScreenUtil.dp2px(mContext, 55))/3;
            layoutParams4.height = (widths - ScreenUtil.dp2px(mContext, 55))/3;

            holder.fl_comment_pic_3.setLayoutParams(layoutParams4);

            GlideUtils.loadNormalImage(mContext, item.getPic().get(0), holder.mIvCommentPic1, R.mipmap.ic_placeholder);
            GlideUtils.loadNormalImage(mContext, item.getPic().get(1), holder.mIvCommentPic2, R.mipmap.ic_placeholder);
            GlideUtils.loadNormalImage(mContext, item.getPic().get(2), holder.mIvCommentPic3, R.mipmap.ic_placeholder);

            holder.mIvCommentPic1.setOnClickListener(v -> showCommentPics(item.getPic(), 0));
            holder.mIvCommentPic2.setOnClickListener(v -> showCommentPics(item.getPic(), 1));
            holder.mIvCommentPic3.setOnClickListener(v -> showCommentPics(item.getPic(), 2));
            if (item.getPic().size() > 3) {
                holder.tv_comment_pic_3.setVisibility(View.VISIBLE);
                holder.tv_comment_pic_3.setText("+" + (item.getPic().size() - 3));
            } else {
                holder.tv_comment_pic_3.setVisibility(View.GONE);
            }
        } else if (item.getPic().size() == 2) {
            holder.iv_comment1_pic_1.setVisibility(View.GONE);
            holder.ll_comment_pics3.setVisibility(View.GONE);
            holder.ll_comment_pics2.setVisibility(View.VISIBLE);
            int widths = _mFragment.getActivity().getWindow().getDecorView().getWidth();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.iv_comment2_pic_1.getLayoutParams();
            layoutParams.width = (widths - ScreenUtil.dp2px(mContext, 45))/2;
            layoutParams.height = (widths - ScreenUtil.dp2px(mContext, 45))/2;
            holder.iv_comment2_pic_1.setLayoutParams(layoutParams);

            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) holder.iv_comment2_pic_2.getLayoutParams();
            layoutParams2.width = (widths - ScreenUtil.dp2px(mContext, 45))/2;
            layoutParams2.height = (widths - ScreenUtil.dp2px(mContext, 45))/2;
            holder.iv_comment2_pic_2.setLayoutParams(layoutParams2);

            GlideApp.with(mContext)
                    .load(item.getPic().get(0))
                    .centerCrop()
                    .placeholder(R.mipmap.ic_placeholder)
                    .into( holder.iv_comment2_pic_1);
            GlideApp.with(mContext)
                    .load(item.getPic().get(1))
                    .centerCrop()
                    .placeholder(R.mipmap.ic_placeholder)
                    .into( holder.iv_comment2_pic_2);

            holder.iv_comment2_pic_1.setOnClickListener(v -> showCommentPics(item.getPic(), 0));
            holder.iv_comment2_pic_2.setOnClickListener(v -> showCommentPics(item.getPic(), 1));
        } else if (item.getPic().size() == 1) {
            holder.ll_comment_pics3.setVisibility(View.GONE);
            holder.ll_comment_pics2.setVisibility(View.GONE);

            holder.iv_comment1_pic_1.setVisibility(View.VISIBLE);
            holder.iv_comment1_pic_1.setOnClickListener(v -> showCommentPics(item.getPic(), 0));
            GlideUtils.loadNormalImage(mContext, item.getPic().get(0), holder.iv_comment1_pic_1, R.mipmap.img_placeholder_h);

        } else if (item.getPic().size() == 0) {
            holder.ll_comment_pics3.setVisibility(View.GONE);
            holder.ll_comment_pics2.setVisibility(View.GONE);
            holder.iv_comment1_pic_1.setVisibility(View.GONE);
        }
        String summary = item.getSummary();
        if (summary.length() >= 27) {
            GlideImageGetter imageGetter = new GlideImageGetter(mContext, holder.tv_comment_content);
            Spanned spanned = Html.fromHtml(summary, imageGetter, null);
            // 使用自定义的 ImageGetter 解析 HTML 内容
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spanned);
            int length = spannableStringBuilder.length();
            while (length > 0 && spannableStringBuilder.charAt(length - 1) == '\n') {
                spannableStringBuilder.delete(length - 1, length);
                length = spannableStringBuilder.length();
            }
            int startIndex = spannableStringBuilder.length();
            spannableStringBuilder.append(" ...全文");
            int endIndex = spannableStringBuilder.length();
            // 创建 ForegroundColorSpan 对象，设置颜色
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#5571FE"));
            // 为新追加的文本设置颜色
            spannableStringBuilder.setSpan(colorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tv_comment_content.setText(spannableStringBuilder);

        /*    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(summary);
            int startIndex = spannableStringBuilder.length();
            spannableStringBuilder.append(" ...全文");
            int endIndex = spannableStringBuilder.length();
            // 创建 ForegroundColorSpan 对象，设置颜色
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#5571FE"));
            // 为新追加的文本设置颜色
            spannableStringBuilder.setSpan(colorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tv_comment_content.setText(spannableStringBuilder);*/
        } else {
            GlideImageGetter imageGetter = new GlideImageGetter(mContext, holder.tv_comment_content);
            Spanned spanned = Html.fromHtml(summary, imageGetter, null);
            // 使用自定义的 ImageGetter 解析 HTML 内容
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spanned);
            holder.tv_comment_content.setText(spannableStringBuilder);
        }
        holder.iv_report.setOnClickListener(v -> {
            if (clickInterface != null) {
                clickInterface.onReport(item.getTid() + "", item.getNickname());
            }
        });
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
            PreviewActivity.openActivity(_mFragment.getActivity(), images, true, position, true,true);
        }
    }


    public class ViewHolder extends AbsHolder {
        private LinearLayout mFlRootView;
        private ImageView iv_report;
        private FrameLayout fl_comment_pic_3;
        private LinearLayout ll_comment_pics3;
        private LinearLayout ll_comment_pics2;
        private ClipRoundImageView mCivPortrait;
        private TextView mTvUserNickname;
        private TextView tv_title;
        private TextView tv_time;
        private TextView tv_play_time;
        private TextView tv_comments;
        private TextView tv_comment_like;
        private TextView tv_comment_content;
        private TextView tv_comment_pic_3;
        private ShapeableImageView mIvCommentPic1;
        private ShapeableImageView mIvCommentPic2;
        private ShapeableImageView mIvCommentPic3;
        private ShapeableImageView iv_comment2_pic_1;
        private ShapeableImageView iv_comment2_pic_2;
        private ShapeableImageView iv_comment1_pic_1;

        public ViewHolder(View view) {
            super(view);

            mFlRootView = findViewById(R.id.fl_rootView);
            iv_report = findViewById(R.id.iv_report);
            tv_time = findViewById(R.id.tv_time);
            tv_play_time = findViewById(R.id.tv_play_time);
            tv_comments = findViewById(R.id.tv_comments);
            tv_comment_like = findViewById(R.id.tv_comment_like);
            tv_comment_content = findViewById(R.id.tv_comment_content);
            tv_title = findViewById(R.id.tv_title);
            mCivPortrait = findViewById(R.id.civ_portrait);
            mTvUserNickname = findViewById(R.id.tv_user_nickname);

            fl_comment_pic_3 = findViewById(R.id.fl_comment_pic_3);
            tv_comment_pic_3 = findViewById(R.id.tv_comment_pic_3);
            mIvCommentPic1 = findViewById(R.id.iv_comment_pic_1);
            mIvCommentPic2 = findViewById(R.id.iv_comment_pic_2);
            ll_comment_pics3 = findViewById(R.id.ll_comment_pics3);
            ll_comment_pics2 = findViewById(R.id.ll_comment_pics2);
            mIvCommentPic3 = findViewById(R.id.iv_comment_pic_3);
            iv_comment2_pic_1 = findViewById(R.id.iv_comment2_pic_1);
            iv_comment2_pic_2 = findViewById(R.id.iv_comment2_pic_2);
            iv_comment1_pic_1 = findViewById(R.id.iv_comment1_pic_1);
        }
    }


}
