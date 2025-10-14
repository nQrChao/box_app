package com.zqhy.app.core.view.community.comment;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.community.comment.CommentInfoVo;
import com.zqhy.app.core.data.model.community.comment.UserCommentInfoVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.community.comment.holder.CommentCenterItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.community.comment.CommentViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

import java.util.List;

/**
 * @author Administrator
 */
public class UserCommentListFragment extends BaseListFragment<CommentViewModel> {

    public static UserCommentListFragment newInstance(int user_id) {
        UserCommentListFragment fragment = new UserCommentListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", user_id);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(CommentInfoVo.DataBean.class, new CommentCenterItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, getParentFragment())
                .setTag(R.id.tag_sub_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    private int user_id;

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            user_id = getArguments().getInt("user_id");
        }
        super.initView(state);
        initData();
    }


    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        initData();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (page < 0) {
            return;
        }
        page++;
        getUserCommentData();
    }

    private int page = 1, pageCount = 12;

    @Override
    public int getPageCount() {
        return pageCount;
    }

    private void initData() {
        page = 1;
        getUserCommentData();
    }

    private void getUserCommentData() {
        if (mViewModel != null) {
            mViewModel.getUserCommentData(user_id, page, pageCount, new OnBaseCallback<UserCommentInfoVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(UserCommentInfoVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null) {
                                if (data.getData().getList() != null) {
                                    if (page == 1) {
                                        clearData();
                                    }
                                    addAllData(data.getData().getList());
                                } else {
                                    if (page == 1) {
                                        clearData();
                                        addData(new EmptyDataVo(R.mipmap.img_empty_data_1)
                                                .setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT)
                                                .setPaddingTop((int) (24 * density)));
                                    } else {
                                        page = -1;
                                    }
                                    setListNoMore(true);
                                    notifyData();
                                }
                            }
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    public void setCommentLike(int cid) {
        if (mViewModel != null) {
            mViewModel.setCommentLike(cid, new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            refreshCommentList(cid, 1);
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    /**
     * 局部刷新
     *
     * @param cid
     * @param like_shift
     */
    private void refreshCommentList(int cid, int like_shift) {
        if (mDelegateAdapter != null && UserInfoModel.getInstance().isLogined()) {
            try {
                List<CommentInfoVo.DataBean> commentListBeanList = mDelegateAdapter.getData();
                int position = 0;
                for (CommentInfoVo.DataBean commentListBean : commentListBeanList) {
                    position++;
                    if (commentListBean.getCid() == cid) {
                        commentListBean.setMe_like(like_shift);
                        commentListBean.setLike_count(commentListBean.getLike_count() + 1);
                        mDelegateAdapter.notifyItemChanged(position);
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
