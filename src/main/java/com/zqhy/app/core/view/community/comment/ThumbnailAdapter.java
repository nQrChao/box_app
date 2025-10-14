package com.zqhy.app.core.view.community.comment;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.box.other.hjq.toast.Toaster;
import com.donkingliang.imageselector.PreviewActivity;
import com.donkingliang.imageselector.entry.Image;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.core.data.model.ThumbnailBean;
import com.zqhy.app.core.view.game.GameFeedbackFragment;
import com.zqhy.app.core.view.game.GameReportFragment;
import com.zqhy.app.core.view.game.forum.ForumPostFragment;
import com.zqhy.app.glide.GlideUtils;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/12/17
 */

public class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailAdapter.ThumbnailHolder> {

    private static final int LAYOUT_VIEW_TYPE_ADD_PIC       = 1;
    private static final int LAYOUT_VIEW_TYPE_THUMBNAIL_PIC = 2;

    private final boolean isStartWithAddPic = true;

    public static final int REQUEST_CODE = 0x00000011;

    private List<ThumbnailBean> thumbnailBeanList;
    private Activity            mActivity;
    private BaseFragment        mFragment;
    private int                 maxCount;

    public ThumbnailAdapter(BaseFragment mFragment, List<ThumbnailBean> thumbnailBeanList) {
        this.thumbnailBeanList = thumbnailBeanList;
        this.mActivity = mFragment.getActivity();
        this.mFragment = mFragment;
        this.maxCount = 6;
    }

    public ThumbnailAdapter(BaseFragment mFragment, List<ThumbnailBean> thumbnailBeanList, int maxCount) {
        this.thumbnailBeanList = thumbnailBeanList;
        this.mActivity = mFragment.getActivity();
        this.mFragment = mFragment;
        this.maxCount = maxCount;
    }

    public void addAll(List<ThumbnailBean> data) {
        if (isStartWithAddPic) {
            thumbnailBeanList.addAll(data);
        } else {
            thumbnailBeanList.addAll(0, data);
        }
    }

    public void add(ThumbnailBean data) {
        if (isStartWithAddPic) {
            thumbnailBeanList.add(0, data);
        } else {
            thumbnailBeanList.add(data);
        }
    }

    public boolean isContainsAdd() {
        for (ThumbnailBean thumbnailBean : thumbnailBeanList) {
            if (thumbnailBean.getType() == 1) {
                return true;
            }
        }
        return false;
    }

    public void deleteItem(int position) {
        thumbnailBeanList.remove(position);
    }

    public List<ThumbnailBean> getDatas() {
        return thumbnailBeanList;
    }

    @Override
    public ThumbnailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LAYOUT_VIEW_TYPE_ADD_PIC) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_pic_thumbnail_add, null);
            return new ThumbnailHolder(view);
        } else if (viewType == LAYOUT_VIEW_TYPE_THUMBNAIL_PIC) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_pic_thumbnail, null);
            return new ThumbnailHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ThumbnailHolder holder, int position) {
        setData(holder, thumbnailBeanList.get(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        ThumbnailBean thumbnailBean = thumbnailBeanList.get(position);
        if (thumbnailBean.getType() == 1) {
            return LAYOUT_VIEW_TYPE_ADD_PIC;
        } else {
            return LAYOUT_VIEW_TYPE_THUMBNAIL_PIC;
        }
    }

    @Override
    public int getItemCount() {
        return thumbnailBeanList == null ? 0 : thumbnailBeanList.size();
    }


    public void setData(ThumbnailHolder holder, ThumbnailBean thumbnailBean, int position) {
        if (getItemViewType(position) == LAYOUT_VIEW_TYPE_ADD_PIC) {
            //添加图片
            holder.mIvThumbnailAdd.setOnClickListener(view -> {
                //多选(最多6张)
                if ((getItemCount() - 1) < maxCount) {
                    int selectMaxCount = maxCount - (getItemCount() - 1);
                    ImageSelectorUtils.openPhoto(mActivity, REQUEST_CODE, false, selectMaxCount);
                } else {
                    Toaster.show("亲，最多只能选取" + maxCount + "张图片哦~");
                }
            });
        } else if (getItemViewType(position) == LAYOUT_VIEW_TYPE_THUMBNAIL_PIC) {
            if (thumbnailBean.getImageType() == 0) {
                GlideUtils.loadLocalImage(mActivity, thumbnailBean.getLocalUrl(), holder.mIvThumbnail);
            } else {
                GlideUtils.loadRoundImage(mActivity, thumbnailBean.getLocalUrl(), holder.mIvThumbnail);
            }
            holder.mIvDelete.setVisibility(View.VISIBLE);

            holder.mIvThumbnail.setOnClickListener(v -> {
                if (thumbnailBean.getType() == 1) {

                } else {
                    //预览图片
                    ArrayList<Image> images = new ArrayList();
                    List<ThumbnailBean> thumbnailBeanList = getDatas();
                    for (ThumbnailBean currentThumbnailBean : thumbnailBeanList) {
                        if (currentThumbnailBean.getType() == 1) {
                            continue;
                        }
                        Image image = new Image();
                        if (currentThumbnailBean.getImageType() == 0) {
                            //本地图片
                            image.setType(0);
                            image.setPath(currentThumbnailBean.getLocalUrl());
                        } else if (currentThumbnailBean.getImageType() == 1) {
                            //网络图片
                            image.setType(1);
                            image.setPath(currentThumbnailBean.getHttpUrl());
                        }

                        images.add(image);
                    }
                    PreviewActivity.openActivity(mActivity, images, true, position, true);
                }
            });
            holder.mIvDelete.setOnClickListener(v -> {
                if (mFragment != null) {
                    if (mFragment instanceof WriteCommentsFragment){
                        ((WriteCommentsFragment) mFragment).picDelete(thumbnailBean, position);
                    }else if (mFragment instanceof GameReportFragment){
                        ((GameReportFragment) mFragment).picDelete(position);
                    }else if (mFragment instanceof GameFeedbackFragment){
                        ((GameFeedbackFragment) mFragment).picDelete(position);
                    }else if (mFragment instanceof ForumPostFragment){
                        ((ForumPostFragment) mFragment).picDelete(thumbnailBean, position);
                    }
                }
            });
        }
    }

    class ThumbnailHolder extends RecyclerView.ViewHolder {
        public  ImageView mIvThumbnail;
        public  ImageView mIvDelete;
        private ImageView mIvThumbnailAdd;

        public ThumbnailHolder(View itemView) {
            super(itemView);
            mIvThumbnail = (ImageView) itemView.findViewById(R.id.iv_thumbnail);
            mIvDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
            mIvThumbnailAdd = itemView.findViewById(R.id.iv_thumbnail_add);
        }

    }
}
