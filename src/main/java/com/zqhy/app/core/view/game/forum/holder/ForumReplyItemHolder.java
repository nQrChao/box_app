package com.zqhy.app.core.view.game.forum.holder;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.box.other.hjq.toast.Toaster;
import com.donkingliang.imageselector.PreviewActivity;
import com.donkingliang.imageselector.entry.Image;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.forum.ForumReplyTopExplicitVo;
import com.zqhy.app.core.data.model.forum.ForumReplyTopVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.community.comment.CommentDetailFragment;
import com.zqhy.app.core.view.community.user.CommunityUserFragment;
import com.zqhy.app.core.view.game.forum.tool.GlideImageGetter;
import com.zqhy.app.core.view.main.holder.GameNoMoreItemHolder;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;
import com.zqhy.app.widget.imageview.ClipRoundImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Administrator
 * @date 2018/11/24
 */

public class ForumReplyItemHolder extends AbsItemHolder<ForumReplyTopVo, ForumReplyItemHolder.ViewHolder> {

    private OnClickInterface clickInterface;

    public void setClickInterface(OnClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }

    BaseRecyclerAdapter adapter;

    public interface OnClickInterface {
        void onReply(ForumReplyTopVo bean);

        void onLike(ForumReplyTopVo bean, TextView textView);

        void onSecondReply(int firstRid, ForumReplyTopExplicitVo second);

        void getMore(int rid, int page, ForumReplyTopVo info,int postion);

        void onReport(String tid,String name);
    }

    public ForumReplyItemHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_forum_reply_top;
    }

    @Override
    public ForumReplyItemHolder.ViewHolder createViewHolder(View view) {
        return new ForumReplyItemHolder.ViewHolder(view);
    }

    int page = 1;

    @Override
    protected void onBindViewHolder(@NonNull ForumReplyItemHolder.ViewHolder holder, @NonNull ForumReplyTopVo item) {
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
        holder.tv_floor.setText(item.getFloor() + "楼 · 回复");
        holder.tv_play_time.setText(item.getGame_duration());

        GlideImageGetter imageGetter = new GlideImageGetter(mContext, holder.tv_comment_content);
        String content = item.getContent();
        content = content.replaceAll("\\\\", "");
        Spanned spanned = Html.fromHtml(content, imageGetter, null);
        holder.tv_comment_content.setText(removeTrailingNewLines(spanned));

        holder.tv_comment_like.setText(item.getLike_count() + "");
        int likeStatus = item.getLike_status();//like_status	int	点赞状态：0：未点赞, 1:已点赞
        if (likeStatus == 0) {
            holder.tv_comment_like.setTextColor(Color.parseColor("#999999"));
            holder.tv_comment_like.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_item_forum_list_1), null, null, null);
        } else {
            holder.tv_comment_like.setTextColor(Color.parseColor("#5571FE"));
            holder.tv_comment_like.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.ic_item_forum_list_1_t), null, null, null);
        }

        if (!item.getPic().isEmpty()) {
            GlideUtils.loadNormalImage(mContext, item.getPic().get(0), holder.mIvCommentPic1, R.mipmap.img_placeholder_h);
            holder.mIvCommentPic1.setOnClickListener(v -> showCommentPics(item.getPic(), 0));
            holder.mIvCommentPic1.setVisibility(View.VISIBLE);
        } else {
            holder.mIvCommentPic1.setVisibility(View.GONE);
        }

        holder.tv_comment_like.setOnClickListener(v -> {
            clickInterface.onLike(item, holder.tv_comment_like);
        });

        holder.tv_floor.setOnClickListener(v -> {
            clickInterface.onReply(item);
        });
       holder.ll_floor.setOnClickListener(v -> {
            clickInterface.onReply(item);
        });


        ForumSecondReplyItemHolder forumSecondReplyItemHolder = new ForumSecondReplyItemHolder(mContext);
        forumSecondReplyItemHolder.setClickInterface(bean -> {
            if (clickInterface != null) {
                clickInterface.onSecondReply(item.getRid(), bean);
            }
        });

        holder.iv_report.setOnClickListener(view -> {
            if (clickInterface != null) {
                clickInterface.onReport(item.getRid()+"",item.getNickname());
            }
        });

        page = item.getPage();
        adapter = new BaseRecyclerAdapter.Builder<>()
                .bind(ForumReplyTopExplicitVo.class, forumSecondReplyItemHolder)
                .bind(NoMoreDataVo.class, new GameNoMoreItemHolder(mContext))
                .build().setTag(R.id.tag_fragment, _mFragment).setTag(R.id.tag_sub_fragment, _mFragment);;
        holder.recyclerview.setFocusable(false);
        holder.recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        holder.recyclerview.setAdapter(adapter);

        List<ForumReplyTopExplicitVo> explicit = item.getExplicit();
        if (!explicit.isEmpty()) {
            if (item.getReply_count() > 3) {
                item.setShowMore(true);
                holder.tv_more.setOnClickListener(v -> {
                    if (clickInterface != null) {
                        if (item.getPage() != -1) {
                            holder.tv_more.setVisibility(View.GONE);
                            clickInterface.getMore(item.getRid(), item.getPage(), item,getItemPostion());
                        }
                    }
                });
            } else {
                item.setShowMore(false);
            }
            holder.recyclerview.setVisibility(View.VISIBLE);
            adapter.addAllData(explicit);
            adapter.notifyDataSetChanged();
        } else {
            holder.recyclerview.setVisibility(View.GONE);
        }
        if (item.isShowMore()) {
            holder.tv_more.setVisibility(View.VISIBLE);
            if (page == -1) {
                holder.tv_more.setVisibility(View.GONE);
            }
        } else {
            holder.tv_more.setVisibility(View.GONE);
        }
    }

    public Spanned removeTrailingNewLines(Spanned spanned) {
        if (spanned == null) {
            return null;
        }
        // 将 Spanned 转换为 String
        String text = spanned.toString();
        // 去除末尾的换行符
        text = text.replaceAll("\\n+$", "");
        // 创建一个新的 SpannableString 来保留样式
        SpannableString spannableString = new SpannableString(text);
        // 复制原有的样式到新的 SpannableString
        Object[] spans = spanned.getSpans(0, spanned.length(), Object.class);
        for (Object span : spans) {
            int start = spanned.getSpanStart(span);
            int end = spanned.getSpanEnd(span);
            int flags = spanned.getSpanFlags(span);
            spannableString.setSpan(span, start, end, flags);
        }
        return spannableString;
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
        private TextView mTvUserNickname;
        private TextView tv_more;
        private TextView tv_time;
        private TextView tv_play_time;

        private TextView tv_comment_like;
        private TextView tv_floor;
        private LinearLayout ll_floor;
        private TextView tv_comment_content;
        private ImageView mIvCommentPic1;
        private ImageView iv_report;
        private RecyclerView recyclerview;

        public ViewHolder(View view) {
            super(view);

            mFlRootView = findViewById(R.id.fl_rootView);
            ll_floor = findViewById(R.id.ll_floor);
            tv_time = findViewById(R.id.tv_time);
            tv_floor = findViewById(R.id.tv_floor);
            tv_more = findViewById(R.id.tv_more);
            iv_report = findViewById(R.id.iv_report);
            tv_play_time = findViewById(R.id.tv_play_time);

            tv_comment_like = findViewById(R.id.tv_comment_like);
            tv_comment_content = findViewById(R.id.tv_comment_content);
            mCivPortrait = findViewById(R.id.civ_portrait);
            mTvUserNickname = findViewById(R.id.tv_user_nickname);

            mIvCommentPic1 = findViewById(R.id.iv_comment_pic_1);

            recyclerview = findViewById(R.id.recyclerview);
        }
    }


}
