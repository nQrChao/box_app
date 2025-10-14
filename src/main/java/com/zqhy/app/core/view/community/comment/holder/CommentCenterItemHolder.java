package com.zqhy.app.core.view.community.comment.holder;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.donkingliang.imageselector.PreviewActivity;
import com.donkingliang.imageselector.entry.Image;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.community.comment.CommentInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.community.comment.CommentDetailFragment;
import com.zqhy.app.core.view.community.comment.UserCommentListFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class CommentCenterItemHolder extends AbsItemHolder<CommentInfoVo.DataBean, CommentCenterItemHolder.ViewHolder> {

    private float density;

    public CommentCenterItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(context);
    }

    private BaseFragment _mSubFragment;

    @Override
    protected void initView(View view) {
        super.initView(view);
        _mSubFragment = (BaseFragment) view.getTag(R.id.tag_sub_fragment);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_user_comment_center;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CommentInfoVo.DataBean item) {
        GlideUtils.loadRoundImage(mContext, item.getGameicon(), holder.mCivPortrait);
        holder.mTvUserNickname.setText(item.getGamename());
        holder.mTvTime.setText(item.getGenre_str());

        holder.mTvCommentContent.setText(item.getContent());

        holder.mTvCommentLike.setText(String.valueOf(item.getLike_count()));
        holder.mTvComments.setText(String.valueOf(item.getReply_count()));

        if (item.getReward_integral() > 0) {
            holder.mFlCommentIntegralOutside.setVisibility(View.VISIBLE);
            holder.mTvCommentIntegral.setText("积分+" + String.valueOf(item.getReward_integral()));
        } else {
            holder.mFlCommentIntegralOutside.setVisibility(View.GONE);
        }

        if (item.getMe_like() == 1) {
            holder.mTvCommentLike.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
            holder.mTvCommentLike.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_new_game_comment_like_select), null, null, null);
            holder.mTvCommentLike.setEnabled(false);
        } else {
            holder.mTvCommentLike.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
            holder.mTvCommentLike.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_user_praise), null, null, null);
            holder.mTvCommentLike.setEnabled(true);
        }
        //设置评论图片
        List<CommentInfoVo.PicInfoVo> picBeanList = item.getPics();
        if (picBeanList != null && picBeanList.size() > 0) {
            holder.mLlCommentPics.setVisibility(View.VISIBLE);

            holder.mIvCommentPic1.setVisibility(View.GONE);
            holder.mIvCommentPic2.setVisibility(View.GONE);
            holder.mFlCommentPic3.setVisibility(View.GONE);
            holder.mFlCommentPicShadow.setVisibility(View.GONE);

            if (picBeanList.size() >= 1) {
                holder.mIvCommentPic1.setVisibility(View.VISIBLE);
                GlideUtils.loadNormalImage(mContext, picBeanList.get(0).getPic_path(), holder.mIvCommentPic1);
            }

            if (picBeanList.size() >= 2) {
                holder.mIvCommentPic2.setVisibility(View.VISIBLE);
                GlideUtils.loadNormalImage(mContext, picBeanList.get(1).getPic_path(), holder.mIvCommentPic2);
            }

            if (picBeanList.size() >= 3) {
                holder.mFlCommentPic3.setVisibility(View.VISIBLE);
                holder.mIvCommentPic3.setVisibility(View.VISIBLE);
                GlideUtils.loadNormalImage(mContext, picBeanList.get(2).getPic_path(), holder.mIvCommentPic3);
                if (picBeanList.size() > 3) {
                    holder.mFlCommentPicShadow.setVisibility(View.VISIBLE);
                    holder.mTvMoreCommentPic.setText("+" + String.valueOf(picBeanList.size() - 3));
                }
            }
        } else {
            holder.mLlCommentPics.setVisibility(View.GONE);
        }
        //设置简洁评论
        holder.mFlCommentReply.setVisibility(View.GONE);
        holder.mTvCommentIng.setVisibility(View.GONE);

        //设置审核未通过的样式
        if (item.getVerify_status() == -1) {
            holder.mFlNotApprovedMaskLayer.setVisibility(View.VISIBLE);
            holder.mFlCommentBottom.setVisibility(View.INVISIBLE);
            holder.mIvCommentPrize.setVisibility(View.VISIBLE);
            holder.mIvCommentPrize.setImageResource(R.mipmap.img_comment_not_approved);
            setAllViewEnable(holder, false);

            holder.mFlCommentNotApproved.setVisibility(View.VISIBLE);

            holder.mTvCommentReason.setText(item.getFail_reason());

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(5 * density);
            gd.setColor(ContextCompat.getColor(mContext, R.color.white));
            gd.setStroke((int) (0.8 * density), ContextCompat.getColor(mContext, R.color.color_11a8ff));
            holder.mTvCommentReason.setBackground(gd);

            //修改次数超过2次，则隐藏修改按钮（modify_count默认为1）
            if (item.getModify_count() >= 3) {
                holder.mTvCommitModify.setVisibility(View.GONE);
            } else {
                holder.mTvCommitModify.setVisibility(View.VISIBLE);
            }
            holder.mTvCommitModify.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            holder.mTvCommitModify.setVisibility(View.INVISIBLE);
            holder.mTvCommitModify.setOnClickListener(view -> {
            });
            holder.mTvNotApproved.setOnClickListener(view -> {
                if (_mFragment != null) {
                    _mFragment.showCommentRule();
                }
            });

        } else {
            holder.mFlNotApprovedMaskLayer.setVisibility(View.GONE);
            holder.mFlCommentBottom.setVisibility(View.VISIBLE);
            holder.mIvCommentPrize.setVisibility(View.GONE);
            holder.mFlCommentNotApproved.setVisibility(View.GONE);
            setAllViewEnable(holder, true);
        }

        holder.mIvCommentPic1.setOnClickListener(v -> {
            ShowCommentPics(item.getPics(), 0);
        });
        holder.mIvCommentPic2.setOnClickListener(v -> {
            ShowCommentPics(item.getPics(), 1);
        });
        holder.mIvCommentPic3.setOnClickListener(v -> {
            ShowCommentPics(item.getPics(), 2);
        });
        holder.mFlCommentPicShadow.setOnClickListener(v -> {
            ShowCommentPics(item.getPics(), 2);
        });

        holder.mLlCommentInfo.setOnClickListener(v -> commentToDetail(item.getCid()));
        holder.mTvCommentContent.setOnClickListener(v -> commentToDetail(item.getCid()));
        holder.mTvComments.setOnClickListener(v -> commentToDetail(item.getCid()));

        holder.mTvTime.setOnClickListener(v -> goGameDetail(item.getGameid(), item.getGame_type()));
        holder.mCivPortrait.setOnClickListener(v -> goGameDetail(item.getGameid(), item.getGame_type()));
        holder.mTvUserNickname.setOnClickListener(v -> goGameDetail(item.getGameid(), item.getGame_type()));

        holder.mTvCommentLike.setOnClickListener(v -> commentLikeOrNot(item.getCid()));


        holder.mIvCommentPrize.setOnClickListener(v -> showCommentPrize());
        holder.mFlCommentIntegralOutside.setOnClickListener(v -> showCommentPrize());
    }

    private void setAllViewEnable(ViewHolder holder, boolean isEnable) {
        holder.mLlCommentInfo.setEnabled(isEnable);
        holder.mTvCommentContent.setEnabled(isEnable);
        holder.mTvComments.setEnabled(isEnable);
        holder.mTvUserNickname.setEnabled(isEnable);
        holder.mTvTime.setEnabled(isEnable);
        holder.mIvCommentPrize.setEnabled(isEnable);

        holder.mIvCommentPic1.setEnabled(isEnable);
        holder.mIvCommentPic2.setEnabled(isEnable);
        holder.mIvCommentPic3.setEnabled(isEnable);
        holder.mFlCommentPicShadow.setEnabled(isEnable);
    }

    private void goGameDetail(int gameid, int game_type) {
        if (_mFragment != null) {
            _mFragment.goGameDetail(gameid, game_type);
        }
    }

    private void commentToDetail(int cid) {
        if (_mFragment != null) {
            _mFragment.start(CommentDetailFragment.newInstance(cid));
        }
    }

    private void showCommentPrize() {
        if (_mFragment != null) {
            _mFragment.showCommentRuleDialog();
        }
    }

    private void commentLikeOrNot(int cid) {
        if (_mFragment != null) {
            if (_mFragment.checkLogin()) {
                if (_mSubFragment != null && _mSubFragment instanceof UserCommentListFragment) {
                    ((UserCommentListFragment) _mSubFragment).setCommentLike(cid);
                }
            }
        }
    }

    public class ViewHolder extends AbsHolder {
        private FrameLayout mFlRootView;
        private ImageView mIvCommentPrize;
        private ImageView mCivPortrait;
        private TextView mTvUserNickname;
        private TextView mTvTime;
        private TextView mTvCommentIng;
        private FrameLayout mFlCommentIntegralOutside;
        private LinearLayout mLlCommentInfo;
        private TextView mTvCommentContent;
        private LinearLayout mLlCommentPics;
        private ImageView mIvCommentPic1;
        private ImageView mIvCommentPic2;
        private FrameLayout mFlCommentPic3;
        private ImageView mIvCommentPic3;
        private FrameLayout mFlCommentPicShadow;
        private TextView mTvMoreCommentPic;
        private FrameLayout mFlCommentReply;
        private LinearLayout mLlReplyList;
        private FrameLayout mFlCommentBottom;
        private FrameLayout mFlNotApprovedMaskLayer;
        private FrameLayout mLlCommentReason;
        private TextView mTvCommentReason;
        private FrameLayout mFlCommentNotApproved;
        private TextView mTvCommitModify;
        private TextView mTvNotApproved;
        private TextView mTvComments;
        private TextView mTvCommentLike;

        private FrameLayout mFlCommentIntegralAll;
        private TextView mTvCommentIntegral;

        public ViewHolder(View view) {
            super(view);
            mFlRootView = findViewById(R.id.fl_rootView);
            mIvCommentPrize = findViewById(R.id.iv_comment_prize);
            mCivPortrait = findViewById(R.id.civ_portrait);
            mTvUserNickname = findViewById(R.id.tv_user_nickname);
            mTvTime = findViewById(R.id.tv_time);
            mTvCommentIng = findViewById(R.id.tv_comment_ing);
            mFlCommentIntegralOutside = findViewById(R.id.fl_comment_integral_outside);
            mLlCommentInfo = findViewById(R.id.ll_comment_info);
            mTvCommentContent = findViewById(R.id.tv_comment_content);
            mLlCommentPics = findViewById(R.id.ll_comment_pics);
            mIvCommentPic1 = findViewById(R.id.iv_comment_pic_1);
            mIvCommentPic2 = findViewById(R.id.iv_comment_pic_2);
            mFlCommentPic3 = findViewById(R.id.fl_comment_pic_3);
            mIvCommentPic3 = findViewById(R.id.iv_comment_pic_3);
            mFlCommentPicShadow = findViewById(R.id.fl_comment_pic_shadow);
            mTvMoreCommentPic = findViewById(R.id.tv_more_comment_pic);
            mFlCommentReply = findViewById(R.id.fl_comment_reply);
            mLlReplyList = findViewById(R.id.ll_reply_list);
            mFlCommentBottom = findViewById(R.id.fl_comment_bottom);
            mFlNotApprovedMaskLayer = findViewById(R.id.fl_not_approved_mask_layer);
            mLlCommentReason = findViewById(R.id.ll_comment_reason);
            mTvCommentReason = findViewById(R.id.tv_comment_reason);
            mFlCommentNotApproved = findViewById(R.id.fl_comment_not_approved);
            mTvCommitModify = findViewById(R.id.tv_commit_modify);
            mTvNotApproved = findViewById(R.id.tv_not_approved);
            mTvComments = findViewById(R.id.tv_comments);
            mTvCommentLike = findViewById(R.id.tv_comment_like);

            mFlCommentIntegralAll = findViewById(R.id.fl_comment_integral_all);
            mTvCommentIntegral = findViewById(R.id.tv_comment_integral);

            mTvUserNickname.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            mTvUserNickname.setTextColor(ContextCompat.getColor(mContext, R.color.color_000000));
        }
    }

    public void ShowCommentPics(List<CommentInfoVo.PicInfoVo> picBeanList, int position) {
        if (picBeanList == null) {
            return;
        }
        //预览图片
        ArrayList<Image> images = new ArrayList();
        for (CommentInfoVo.PicInfoVo picBean : picBeanList) {
            Image image = new Image();
            image.setType(1);
            image.setPath(picBean.getHigh_pic_path());
            images.add(image);
        }
        if (_mFragment != null) {
            PreviewActivity.openActivity(_mFragment.getActivity(), images, true, position, true);
        }
    }
}
