package com.zqhy.app.core.view.chat;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.chat.ChatTeamNoticeListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.view.chat.holder.ChatNoticeItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.chat.ChatViewModel;
import com.zqhy.app.newproject.R;

public class ChatNoticeFragment extends BaseFragment<ChatViewModel> {

    /*public static ChatNoticeFragment newInstance(Team teamInfo) {
        ChatNoticeFragment fragment = new ChatNoticeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("teamInfo", teamInfo);
        fragment.setArguments(bundle);
        return fragment;
    }*/

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_chat_notice;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }

    @Override
    public Object getStateEventKey() {
        return null;
    }


    /*private Team teamInfo;
    private XRecyclerView mRecylerView;
    private BaseRecyclerAdapter mAdapter;
    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            teamInfo = (Team)getArguments().getSerializable("teamInfo");
        }
        super.initView(state);
        showSuccess();

        findViewById(R.id.iv_back).setOnClickListener(v -> {
            pop();
        });

        mRecylerView = findViewById(R.id.recycler_view);
        mRecylerView.setLayoutManager(new LinearLayoutManager(_mActivity));

        mAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(ChatTeamNoticeListVo.DataBean.class, new ChatNoticeItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
        mRecylerView.setAdapter(mAdapter);

        mRecylerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                chatTeamNotice();
            }

            @Override
            public void onLoadMore() {
                page++;
                chatTeamNotice();
            }
        });

        chatTeamNotice();
    }

    private int page = 1;
    private int pageCount = 12;
    private void chatTeamNotice() {
        if (mViewModel != null) {
            mViewModel.chatTeamNotice(teamInfo.getId(), String.valueOf(page), new OnBaseCallback<ChatTeamNoticeListVo>() {

                @Override
                public void onSuccess(ChatTeamNoticeListVo data) {
                    if (data != null && data.isStateOK()) {
                        if (data.getData() != null && data.getData().size() > 0) {
                            if (page == 1) {
                                mAdapter.clear();
                            }
                            mAdapter.addAllData(data.getData());
                            if (data.getData().size() < pageCount) {
                                //has no more data
                                page = -1;
                                mRecylerView.setNoMore(true);
                            }
                            mAdapter.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                mAdapter.addData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                            }
                            page = -1;
                            mRecylerView.setNoMore(true);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }*/


}
