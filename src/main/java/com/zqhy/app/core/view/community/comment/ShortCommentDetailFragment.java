package com.zqhy.app.core.view.community.comment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.box.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.AppConfig;
import com.zqhy.app.core.data.model.community.comment.CommentInfoVo;
import com.zqhy.app.core.data.model.community.comment.CommentListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.nodata.NoMoreDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.community.comment.holder.NewCommentItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.game.GameViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 */
public class ShortCommentDetailFragment extends BaseListFragment<GameViewModel> {

    public static ShortCommentDetailFragment newInstance(int gameid, String gameName) {
        ShortCommentDetailFragment fragment = new ShortCommentDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("gameid", gameid);
        bundle.putString("gameName", gameName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder()
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .bind(CommentInfoVo.DataBean.class, new NewCommentItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    private LinearLayoutManager layoutManager;

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        layoutManager = new LinearLayoutManager(_mActivity);
        return layoutManager;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        if (getArguments() != null) {
            gameid = getArguments().getInt("gameid");
            initActionBackBarAndTitle(getArguments().getString("gameName"));
        }else {
            initActionBackBarAndTitle("");
        }
        setTitleLayout(LAYOUT_ON_LEFT);
        setListViewBackgroundColor(ContextCompat.getColor(_mActivity, R.color.color_f2f2f2));
        mRecyclerView.setBackground(_mActivity.getResources().getDrawable(R.drawable.shape_white_radius_7));
        View mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_ts_short_comment_head, null);
        TextView mTvCommentHot = mHeaderView.findViewById(R.id.tv_comment_hot);
        TextView mTvCommentNew = mHeaderView.findViewById(R.id.tv_comment_new);
        mHeaderView.findViewById(R.id.tv_comment_hot).setOnClickListener(v -> {
            mTvCommentHot.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            mTvCommentHot.setTextColor(Color.parseColor("#5571FE"));
            mTvCommentNew.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            mTvCommentNew.setTextColor(Color.parseColor("#9B9B9B"));
            sort = "hottest";
            page = 1;
            getCommentList();
        });
        mHeaderView.findViewById(R.id.tv_comment_new).setOnClickListener(v -> {
            mTvCommentHot.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            mTvCommentHot.setTextColor(Color.parseColor("#9B9B9B"));
            mTvCommentNew.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            mTvCommentNew.setTextColor(Color.parseColor("#5571FE"));
            sort = "newest";
            page = 1;
            getCommentList();
        });
        mRecyclerView.addHeaderView(mHeaderView);
        initData();
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
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
        getCommentList();
    }

    private int page = 1, pageCount = 12;
    private int     gameid;
    private String sort = "hottest";
    private void initData() {
        page = 1;
        getCommentList();
    }


    private void getCommentList() {
        if (mViewModel != null && !AppConfig.isHideCommunity()) {
            if (page == 1) {
                if (mRecyclerView != null) {
                    mRecyclerView.setNoMore(false);
                }
            }
            mViewModel.getCommentListDataV2(gameid, "2","0", sort, page, pageCount, new OnBaseCallback<CommentListVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    if (mRecyclerView != null) {
                        mRecyclerView.loadMoreComplete();
                    }
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(CommentListVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            if (data.getData() != null && !data.getData().isEmpty()) {
                                if (page == 1) {
                                    mDelegateAdapter.clear();
                                }
                                mDelegateAdapter.addAllData(data.getData());
                                if (data.getData().size() < pageCount) {
                                    //无更多数据
                                    mRecyclerView.setNoMore(true);
                                    mDelegateAdapter.addData(new NoMoreDataVo());
                                }
                            } else {
                                if (page == 1) {
                                    mDelegateAdapter.clear();
                                    EmptyDataVo emptyDataVo = new EmptyDataVo(R.mipmap.img_empty_data_2)
                                            .setLayout(EmptyDataVo.LAYOUT_WRAP_CONTENT)
                                            .setPaddingTop((int) (24 * density));
                                    mDelegateAdapter.addData(emptyDataVo);
                                } else {
                                    mDelegateAdapter.addData(new NoMoreDataVo());
                                }
                                //无更多数据
                                page = -1;
                                mRecyclerView.setNoMore(true);
                            }
                            mDelegateAdapter.notifyDataSetChanged();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }
}
