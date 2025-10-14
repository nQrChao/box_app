package com.zqhy.app.core.view.community.comment.holder;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.donkingliang.imageselector.PreviewActivity;
import com.donkingliang.imageselector.entry.Image;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.core.data.model.community.CommunityInfoVo;
import com.zqhy.app.core.data.model.community.comment.CommentInfoVo;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.view.community.user.CommunityUserFragment;
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
public class CommentDetailInfoHolder extends AbsItemHolder<CommentInfoVo.DataBean, CommentDetailInfoHolder.ViewHolder> {

    private float density;

    public CommentDetailInfoHolder(Context context) {
        super(context);
        density = ScreenUtil.getScreenDensity(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_comment_detail_info;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CommentInfoVo.DataBean item) {
        CommunityInfoVo communityInfoVo = item.getCommunity_info();
        if (communityInfoVo != null) {
            GlideUtils.loadCircleImage(mContext, communityInfoVo.getUser_icon(), holder.mCivPortrait, R.mipmap.ic_user_login_new_sign);
            holder.mTvUserNickname.setText(communityInfoVo.getUser_nickname());

            holder.mCivPortrait.setOnClickListener(view -> {
                goToUserMinePage(communityInfoVo.getUser_id());
            });
            holder.mTvUserNickname.setOnClickListener(view -> {
                goToUserMinePage(communityInfoVo.getUser_id());
            });
            //用户等级
            holder.mFlUserLevel.setVisibility(View.VISIBLE);
            UserInfoModel.setUserLevel(communityInfoVo.getUser_level(), holder.mIvUserLevel, holder.mTvUserLevel);
            UserInfoModel.setUserVipLevel(communityInfoVo.getVip_level(), holder.mIvUserVipLevel, holder.mTvUserVipLevel);
            if (communityInfoVo.getVip_member() != null) {
                UserInfoModel.setUserMonthCard(communityInfoVo.getVip_member().isVipMember(), holder.mFlUserMonthCard);
            } else {
                UserInfoModel.setUserMonthCard(false, holder.mFlUserMonthCard);
            }
        } else {
            UserInfoModel.setUserVipLevel(0, holder.mIvUserVipLevel, holder.mTvUserVipLevel);
            UserInfoModel.setUserMonthCard(false, holder.mFlUserMonthCard);
        }
        UserInfoModel.setUserMonthCard(false, holder.mFlUserMonthCard);
        //积分
        if (item.getReward_integral() > 0) {
            holder.mFlCommentIntegralAll.setVisibility(View.VISIBLE);
            holder.mTvCommentIntegral.setText("积分+" + String.valueOf(item.getReward_integral()));

        } else {
            holder.mFlCommentIntegralAll.setVisibility(View.GONE);
        }

        try {
            long ms = Long.parseLong(item.getRelease_time()) * 1000;
            holder.mTvCommentTime.setText(CommonUtils.formatTimeStamp(ms, "MM-dd HH:mm"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.mTvCommentContent.setText(item.getContent());

        holder.mTvCommentLike.setText("赞  " + String.valueOf(item.getLike_count()));
        holder.mTvComments.setText("回复  " + String.valueOf(item.getReply_count()));

        setCommentPics(holder, item);
        holder.mTvGamePageView.setText("（阅读 " + String.valueOf(item.getView_count()) + "）");

        if (item.getReply_list() != null && !item.getReply_list().isEmpty()) {
            holder.mIvCommentTriangle.setVisibility(View.VISIBLE);
        } else {
            holder.mIvCommentTriangle.setVisibility(View.GONE);
        }
        holder.mFlCommentIntegralAll.setOnClickListener(v -> {
            if (_mFragment != null) {
                _mFragment.showCommentRuleDialog();
            }
        });
    }

    private void setCommentPics(ViewHolder holder, CommentInfoVo.DataBean item) {
        LinearLayout.LayoutParams params;

        if (item.getPics() != null) {
            holder.mGvPics.setVisibility(View.VISIBLE);
            if (item.getPics().size() > 3) {
                params = new LinearLayout.LayoutParams((int) (276 * density), (int) (density * 180));
            } else {
                params = new LinearLayout.LayoutParams((int) (276 * density), (int) (density * 88));
            }
            params.setMargins(0, (int) (6 * density), 0, 0);
            holder.mGvPics.setLayoutParams(params);
            GridPicAdapter picAdapter = new GridPicAdapter(item.getPics());
            holder.mGvPics.setAdapter(picAdapter);
            holder.mGvPics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (item.getPics() == null) {
                        return;
                    }
                    //预览图片
                    ArrayList<Image> images = new ArrayList();
                    for (CommentInfoVo.PicInfoVo picBean : item.getPics()) {
                        Image image = new Image();
                        image.setType(1);
                        image.setPath(picBean.getHigh_pic_path());
                        images.add(image);
                    }
                    if (_mFragment != null) {
                        PreviewActivity.openActivity(_mFragment.getActivity(), images, true, position, true);
                    }
                }
            });
        } else {
            holder.mGvPics.setVisibility(View.GONE);
        }
    }

    private void goToUserMinePage(int user_id) {
        if (_mFragment != null) {
            _mFragment.start(CommunityUserFragment.newInstance(user_id));
        }
    }

    public class ViewHolder extends AbsHolder {

        private ClipRoundImageView mCivPortrait;
        private TextView           mTvUserNickname;
        private TextView           mTvCommentContent;
        private GridView           mGvPics;
        private TextView           mTvCommentTime;
        private TextView           mTvComments;
        private TextView           mTvCommentLike;
        private TextView           mTvGamePageView;
        private FrameLayout        mFlUserLevel;
        private ImageView          mIvUserLevel;
        private TextView           mTvUserLevel;
        private FrameLayout        mFlCommentIntegralAll;
        private TextView           mTvCommentIntegral;
        private TextView           mTvUserVipLevel;
        private ImageView          mIvUserVipLevel;
        private FrameLayout        mFlUserMonthCard;
        private ImageView          mIvCommentTriangle;

        public ViewHolder(View view) {
            super(view);
            mCivPortrait = findViewById(R.id.civ_portrait);
            mTvUserNickname = findViewById(R.id.tv_user_nickname);
            mTvCommentContent = findViewById(R.id.tv_comment_content);
            mGvPics = findViewById(R.id.gv_pics);
            mTvCommentTime = findViewById(R.id.tv_comment_time);
            mTvComments = findViewById(R.id.tv_comments);
            mTvCommentLike = findViewById(R.id.tv_comment_like);
            mTvGamePageView = findViewById(R.id.tv_game_page_view);
            mFlUserLevel = findViewById(R.id.fl_user_level);
            mIvUserLevel = findViewById(R.id.iv_user_level);
            mTvUserLevel = findViewById(R.id.tv_user_level);
            mFlCommentIntegralAll = findViewById(R.id.fl_comment_integral_all);
            mTvCommentIntegral = findViewById(R.id.tv_comment_integral);

            mIvCommentTriangle = findViewById(R.id.iv_comment_triangle);
            mTvUserVipLevel = findViewById(R.id.tv_user_vip_level);
            mIvUserVipLevel = findViewById(R.id.iv_user_vip_level);
            mFlUserMonthCard = findViewById(R.id.fl_user_month_card);
        }
    }

    class GridPicAdapter extends BaseAdapter {

        private List<CommentInfoVo.PicInfoVo> picBeanList;

        public GridPicAdapter(List<CommentInfoVo.PicInfoVo> picBeanList) {
            this.picBeanList = picBeanList;
        }

        @Override
        public int getCount() {
            return picBeanList == null ? 0 : picBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout_comment_detail_pic, null);

                viewHolder.mIvCommentPic = convertView.findViewById(R.id.iv_comment_pic);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            CommentInfoVo.PicInfoVo picBean = picBeanList.get(position);

            GlideUtils.loadNormalImage(mContext, picBean.getPic_path(), viewHolder.mIvCommentPic, R.mipmap.ic_placeholder);
            return convertView;
        }

        class ViewHolder {
            private ImageView mIvCommentPic;
        }
    }


}
