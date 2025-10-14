package com.zqhy.app.core.view.community.comment.holder;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaoji.other.hjq.toast.Toaster;
import com.donkingliang.imageselector.PreviewActivity;
import com.donkingliang.imageselector.entry.Image;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.community.CommunityInfoVo;
import com.zqhy.app.core.data.model.community.comment.CommentInfoVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.tool.utilcode.KeyboardUtils;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.community.comment.CommentDetailFragment;
import com.zqhy.app.core.view.community.user.CommunityUserFragment;
import com.zqhy.app.core.view.game.GameDetailInfoFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.imageview.ClipRoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class CommentItemHolder extends AbsItemHolder<CommentInfoVo.DataBean, CommentItemHolder.ViewHolder> {

    private float density;

    public CommentItemHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(mContext);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_game_comment_list;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CommentInfoVo.DataBean item) {
        //icon
        CommunityInfoVo communityInfoVo = item.getCommunity_info();
        if (communityInfoVo != null) {
            GlideUtils.loadCircleImage(mContext, communityInfoVo.getUser_icon(), holder.mCivPortrait, R.mipmap.ic_user_login_new_sign);
            holder.mTvUserNickname.setText(communityInfoVo.getUser_nickname());
            //用户等级
            holder.mFlUserLevel.setVisibility(View.VISIBLE);
            UserInfoModel.setUserLevel(communityInfoVo.getUser_level(), holder.mIvUserLevel, holder.mTvUserLevel);

            UserInfoVo.VipMemberVo vipMemberVo = communityInfoVo.getVip_member();
            if (vipMemberVo != null) {
                UserInfoModel.setUserMonthCard(vipMemberVo.isVipMember(), holder.mFlUserMonthCard);
            } else {
                UserInfoModel.setUserMonthCard(false, holder.mFlUserMonthCard);
            }
            UserInfoModel.setUserVipLevel(communityInfoVo.getVip_level(), holder.mIvUserVipLevel, holder.mTvUserVipLevel);
        } else {
            UserInfoModel.setUserMonthCard(false, holder.mFlUserMonthCard);
            UserInfoModel.setUserVipLevel(0, holder.mIvUserVipLevel, holder.mTvUserVipLevel);
        }
        UserInfoModel.setUserMonthCard(false, holder.mFlUserMonthCard);
        holder.mFlUserVipLevel.setVisibility(View.GONE);
        //时间
        try {
            long ms = Long.parseLong(item.getRelease_time()) * 1000;
            //            holder.mTvTime.setText(CommonUtils.formatTimeStamp(ms, "MM-dd HH:mm"));
            holder.mTvTime.setText(CommonUtils.friendlyTime2(ms));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //积分
        if (item.getReward_integral() > 0) {
            holder.mFlCommentIntegralAll.setVisibility(View.VISIBLE);
            holder.mTvCommentIntegral.setText("积分+" + String.valueOf(item.getReward_integral()));
        } else {
            holder.mFlCommentIntegralAll.setVisibility(View.GONE);
        }

        //赞和回复
        holder.mTvCommentLike.setText(String.valueOf(item.getLike_count()));
        holder.mTvComments.setText(String.valueOf(item.getReply_count()));

        if (item.getMe_like() == 1) {
            holder.mTvCommentLike.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff8f19));
            holder.mTvCommentLike.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_new_game_comment_like_select), null, null, null);
            holder.mTvCommentLike.setEnabled(false);
        } else {
            holder.mTvCommentLike.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
            holder.mTvCommentLike.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_new_game_comment_like), null, null, null);
            holder.mTvCommentLike.setEnabled(true);
        }

        //评论文字
        holder.mTvCommentContent.setText(item.getContent());

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
                GlideUtils.loadNormalImage(mContext, picBeanList.get(0).getPic_path(), holder.mIvCommentPic1, R.mipmap.ic_placeholder);
            }

            if (picBeanList.size() >= 2) {
                holder.mIvCommentPic2.setVisibility(View.VISIBLE);
                GlideUtils.loadNormalImage(mContext, picBeanList.get(1).getPic_path(), holder.mIvCommentPic2, R.mipmap.ic_placeholder);
            }

            if (picBeanList.size() >= 3) {
                holder.mFlCommentPic3.setVisibility(View.VISIBLE);
                holder.mIvCommentPic3.setVisibility(View.VISIBLE);
                GlideUtils.loadNormalImage(mContext, picBeanList.get(2).getPic_path(), holder.mIvCommentPic3, R.mipmap.ic_placeholder);
                if (picBeanList.size() > 3) {
                    holder.mFlCommentPicShadow.setVisibility(View.VISIBLE);
                    holder.mTvMoreCommentPic.setText("+" + String.valueOf(picBeanList.size() - 3));
                }
            }
        } else {
            holder.mLlCommentPics.setVisibility(View.GONE);
        }

        //设置简洁评论
        if (item.getReply_list() != null) {
            holder.mFlCommentReply.setVisibility(View.VISIBLE);

            List<CommentInfoVo.ReplyInfoVo> replyBeanList = new ArrayList<>();

            replyBeanList.addAll(item.getReply_list());
            CommentInfoVo.ReplyInfoVo replyBean = new CommentInfoVo.ReplyInfoVo();

            int replyListSize = item.getReply_count();
            if (replyListSize > 3) {
                replyBean.setCid(-1);
                replyBeanList.add(replyBean);
            }
            setReplyList(holder, replyBeanList, replyListSize);
        } else {
            holder.mFlCommentReply.setVisibility(View.GONE);
        }


        holder.mFlRootView.setOnClickListener(v -> {
            commentToDetail(item.getCid());
        });
        holder.mFlCommentReply.setOnClickListener(v -> {
            commentToDetail(item.getCid());
        });
        holder.mTvComments.setOnClickListener(v -> {
            commentReply(item.getCid());
        });

        holder.mCivPortrait.setOnClickListener(v -> {
            goUserCommentList(item.getUid());
        });
        holder.mTvUserNickname.setOnClickListener(v -> {
            goUserCommentList(item.getUid());
        });
        holder.mTvCommentLike.setOnClickListener(v -> {
            commentLikeOrNot(item.getCid());
        });

        holder.mIvCommentPic1.setOnClickListener(v -> showCommentPics(item.getPics(), 0));
        holder.mIvCommentPic2.setOnClickListener(v -> showCommentPics(item.getPics(), 1));
        holder.mIvCommentPic3.setOnClickListener(v -> showCommentPics(item.getPics(), 2));
        holder.mFlCommentPicShadow.setOnClickListener(v -> showCommentPics(item.getPics(), 2));

        holder.mFlCommentIntegralAll.setOnClickListener(v -> {
            showCommentPrize();
        });
    }

    private int reply_count;

    private void setReplyList(ViewHolder holder, List<CommentInfoVo.ReplyInfoVo> replyBeanList, int reply_count) {
        this.reply_count = reply_count;
        holder.mLlReplyList.removeAllViews();
        for (int i = 0; i < replyBeanList.size(); i++) {
            CommentInfoVo.ReplyInfoVo replyBean = replyBeanList.get(i);
            TextView tv = new TextView(mContext);
            setReplyData(tv, replyBean, i);

            tv.setPadding(0, (int) (4 * density), 0, (int) (4 * density));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.mLlReplyList.addView(tv, params);
        }
    }

    public void setReplyData(TextView mTv, CommentInfoVo.ReplyInfoVo replyInfoBean, int position) {
        mTv.setTextSize(13);
        mTv.setTextColor(ContextCompat.getColor(mContext, R.color.color_1e1e1e));
        mTv.setLineSpacing(0, 1.1f);
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan((int) (12 * density));
        if (replyInfoBean.getCid() == -1) {
            String content = "查看全部" + reply_count + "条回复 >";
            mTv.setTextSize(12);
            mTv.setText(content);
            mTv.setTextColor(ContextCompat.getColor(mContext, R.color.color_336ba7));
        } else {
            CommunityInfoVo communityInfoVo = replyInfoBean.getCommunity_info();
            CommunityInfoVo toCommunityInfoVo = replyInfoBean.getTo_community_info();
            String nickname = communityInfoVo == null ? null : communityInfoVo.getUser_nickname();
            String replyNickname = toCommunityInfoVo == null ? null : toCommunityInfoVo.getUser_nickname();

            String content = replyInfoBean.getContent();
            if (TextUtils.isEmpty(nickname)) {
                nickname = "";
            }
            if (TextUtils.isEmpty(replyNickname)) {
                replyNickname = "";
            }

            boolean hasMoreReply = false;
            if (!TextUtils.isEmpty(replyNickname)) {
                replyNickname = "回复@" + replyNickname;
                hasMoreReply = true;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(nickname).append(replyNickname).append(":").append(content);

            SpannableString spannableString = new SpannableString(sb.toString());
            int nicknameColor = ContextCompat.getColor(mContext, R.color.color_336ba7);

            int nicknameStartLine = 0;
            int nicknameEndLine = nickname.length() + 1;

            spannableString.setSpan(new ClickableSpan() {

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false);
                    ds.setColor(nicknameColor);
                }

                @Override
                public void onClick(View view) {
                    //跳转nickname
                    goUserCommentList(replyInfoBean.getUid());
                }
            }, 0, nickname.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);


            if (hasMoreReply) {
                int toNicknameStartLine = nickname.length() + 2;
                int toNicknameEndLine = nickname.length() + replyNickname.length() + 1;

                spannableString.setSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setUnderlineText(false);
                        ds.setColor(nicknameColor);
                    }

                    @Override
                    public void onClick(View view) {
                        //跳转tonickname
                        goUserCommentList(replyInfoBean.getTouid());
                    }
                }, toNicknameStartLine, toNicknameEndLine, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                spannableString.setSpan(absoluteSizeSpan, toNicknameStartLine, toNicknameEndLine, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }

            int contentStartLine = nickname.length() + replyNickname.length() + 1;
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false);
                }

                @Override
                public void onClick(View view) {
                    //跳转评论详情，单条回复定位
                    commentToDetail(replyInfoBean.getCid(), position);
                }
            }, contentStartLine, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            spannableString.setSpan(absoluteSizeSpan, nicknameStartLine, nicknameEndLine, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            mTv.setMovementMethod(LinkMovementMethod.getInstance());
            mTv.setText(spannableString);
        }
    }


    private void goUserCommentList(int uid) {
        if (_mFragment != null) {
            _mFragment.start(CommunityUserFragment.newInstance(uid));
        }
    }

    private void commentToDetail(int cid) {
        commentToDetail(cid, -1);
    }

    private void commentToDetail(int cid, int position) {
        if (_mFragment != null) {
            _mFragment.start(CommentDetailFragment.newInstance(cid, position));
        }
    }

    private void commentLikeOrNot(int cid) {
        if (_mFragment != null) {
            if (_mFragment.checkLogin()) {
                ((GameDetailInfoFragment) _mFragment).setCommentLike(cid);
            }
        }
    }

    private void showCommentPics(List<CommentInfoVo.PicInfoVo> picBeanList, int position) {
        if (_mFragment != null) {
            if (picBeanList == null) {
                return;
            }
            //预览图片
            ArrayList<Image> images = new ArrayList();
            for (CommentInfoVo.PicInfoVo picBean : picBeanList) {
                Image image = new Image();
                image.setType(1);
                image.setPath(picBean.getPic_path());
                image.setHigh_path(picBean.getHigh_pic_path());
                images.add(image);
            }
            PreviewActivity.openActivity(_mFragment.getActivity(), images, true, position, true);
        }
    }

    private void showCommentPrize() {
        if (_mFragment != null) {
            _mFragment.showCommentRuleDialog();
        }
    }

    private void commentReply(int cid) {
        if (_mFragment != null) {
            if (_mFragment.checkLogin()) {
                showEditDialog(cid);
            }
        }
    }

    public class ViewHolder extends AbsHolder {
        private LinearLayout       mFlRootView;
        private ClipRoundImageView mCivPortrait;
        private TextView           mTvUserNickname;
        private FrameLayout        mFlUserLevel;
        private ImageView          mIvUserLevel;
        private TextView           mTvUserLevel;
        private TextView           mTvTime;
        private LinearLayout       mLlCommentInfo;
        private TextView           mTvCommentContent;
        private LinearLayout       mLlCommentPics;
        private ImageView          mIvCommentPic1;
        private ImageView          mIvCommentPic2;
        private FrameLayout        mFlCommentPic3;
        private ImageView          mIvCommentPic3;
        private FrameLayout        mFlCommentPicShadow;
        private TextView           mTvMoreCommentPic;
        private FrameLayout        mFlCommentReply;
        private LinearLayout       mLlReplyList;
        private FrameLayout        mFlCommentIntegralAll;
        private TextView           mTvCommentIntegral;
        private TextView           mTvComments;
        private TextView           mTvCommentLike;
        private TextView           mTvUserVipLevel;
        private ImageView          mIvUserVipLevel;
        private FrameLayout        mFlUserMonthCard;
        private FrameLayout        mFlUserVipLevel;

        public ViewHolder(View view) {
            super(view);

            mFlRootView = findViewById(R.id.fl_rootView);
            mCivPortrait = findViewById(R.id.civ_portrait);
            mTvUserNickname = findViewById(R.id.tv_user_nickname);
            mFlUserLevel = findViewById(R.id.fl_user_level);
            mIvUserLevel = findViewById(R.id.iv_user_level);
            mTvUserLevel = findViewById(R.id.tv_user_level);

            mTvTime = findViewById(R.id.tv_time);
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

            mFlCommentIntegralAll = findViewById(R.id.fl_comment_integral_all);
            mTvCommentIntegral = findViewById(R.id.tv_comment_integral);

            mTvComments = findViewById(R.id.tv_comments);
            mTvCommentLike = findViewById(R.id.tv_comment_like);

            mTvUserVipLevel = findViewById(R.id.tv_user_vip_level);
            mIvUserVipLevel = findViewById(R.id.iv_user_vip_level);


            mFlUserMonthCard = findViewById(R.id.fl_user_month_card);
            mFlUserVipLevel = findViewById(R.id.fl_user_vip_level);
        }
    }


    /**
     * 点评回复弹窗
     */
    private CustomDialog mEditDialog;

    private EditText mEtComment;
    private TextView mTvCommentRelease;

    private int targetCid = -1;

    private void showEditDialog(int cid) {
        targetCid = cid;
        if (mEditDialog == null) {
            View mEditView = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_edit_comment, null);
            mEditDialog = new CustomDialog(mContext, mEditView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
            mEtComment = mEditView.findViewById(R.id.et_comment);
            mTvCommentRelease = mEditView.findViewById(R.id.tv_comment_release);

            mEtComment.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String strComment = mEtComment.getText().toString().trim();
                    if (strComment.length() > 149) {
                        mEtComment.setText(strComment.substring(0, 149));
                        mEtComment.setSelection(mEtComment.getText().toString().length());
                        Toaster.show( "亲，字数超过啦~");
                    }

                    if (strComment.length() == 0) {
                        mTvCommentRelease.setEnabled(false);
                        mTvCommentRelease.setTextColor(ContextCompat.getColor(mContext, R.color.color_b7b7b7));
                    } else {
                        mTvCommentRelease.setEnabled(true);
                        mTvCommentRelease.setTextColor(ContextCompat.getColor(mContext, R.color.color_007aff));
                    }
                }
            });
            mTvCommentRelease.setOnClickListener(v -> {
                commentRelease();
            });
            mEditDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    mEtComment.getText().clear();
                    mEtComment.post(() -> KeyboardUtils.hideSoftInput(mContext, mEtComment));
                }
            });

            GradientDrawable gd = new GradientDrawable();
            gd.setColor(ContextCompat.getColor(mContext, R.color.color_f4f5f6));
            gd.setCornerRadius(18 * density);
            gd.setStroke((int) (1 * density), ContextCompat.getColor(mContext, R.color.color_efefef));
            mEtComment.setBackground(gd);
            mEtComment.setHint("回复Ta");
        }
        /** 3.自动弹出软键盘 **/
        mEtComment.post(() -> KeyboardUtils.showSoftInput(mContext, mEtComment));
        mEditDialog.show();
    }


    private void commentRelease() {
        if (_mFragment != null) {
            if (_mFragment.checkLogin()) {
                String strContent = mEtComment.getText().toString().trim();
                if (TextUtils.isEmpty(strContent)) {
                    Toaster.show( "请输入内容");
                    return;
                }
                if (strContent.length() > 150) {
                    Toaster.show( "亲，字数超过了~");
                    return;
                }

                if (targetCid != -1) {
                    if (_mFragment instanceof GameDetailInfoFragment) {
                        ((GameDetailInfoFragment) _mFragment).setCommentReply(mEditDialog, mTvCommentRelease, strContent, "", targetCid);
                    }
                }
            }
        }
    }
}
