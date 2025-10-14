package com.zqhy.app.core.view.chat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.view.chat.holder.ChatMemberItemHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.chat.ChatViewModel;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;

public class ChatMemberFragment extends BaseFragment<ChatViewModel> {

    /*public static ChatMemberFragment newInstance(Team teamInfo) {
        ChatMemberFragment fragment = new ChatMemberFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("teamInfo", teamInfo);
        fragment.setArguments(bundle);
        return fragment;
    }*/

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_chat_member;
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
    private EditText mEtSearch;
    private ImageView mIvClear;
    private RecyclerView mRecylerView;
    private BaseRecyclerAdapter mAdapter;
    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            teamInfo = (Team)getArguments().getSerializable("teamInfo");
        }
        super.initView(state);

        findViewById(R.id.iv_back).setOnClickListener(v -> {
            pop();
        });

        mEtSearch = findViewById(R.id.et_search);
        mIvClear = findViewById(R.id.iv_clear_search);

        mRecylerView = findViewById(R.id.recycler_view);
        mRecylerView.setLayoutManager(new LinearLayoutManager(_mActivity));

        mAdapter = new BaseRecyclerAdapter.Builder<>()
                .bind(UserInfoWithTeam.class, new ChatMemberItemHolder(_mActivity))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
        mRecylerView.setAdapter(mAdapter);

        mIvClear.setOnClickListener(v -> {
            mEtSearch.setText("");
            *//*if (teamUserList != null){
                mAdapter.setDatas(teamUserList);
            }else {
                ArrayList<EmptyDataVo> emptyDataVos = new ArrayList<>();
                emptyDataVos.add(new EmptyDataVo(R.mipmap.img_empty_data_2));
                mAdapter.setDatas(emptyDataVos);
            }
            mAdapter.notifyDataSetChanged();*//*
        });

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String mSearchText = mEtSearch.getText().toString().trim();

                if (TextUtils.isEmpty(mSearchText)){
                    mIvClear.setVisibility(View.GONE);
                }else {
                    mIvClear.setVisibility(View.VISIBLE);
                }

                if (teamUserList != null) {
                    if (!TextUtils.isEmpty(mSearchText)) {
                        List<UserInfoWithTeam> searchList = new ArrayList<>();
                        for (int i = 0; i < teamUserList.size(); i++) {
                            UserInfoWithTeam userInfoWithTeam = teamUserList.get(i);
                            if (userInfoWithTeam.getName().contains(mSearchText) || userInfoWithTeam.getUserInfo().getAccount().contains(mSearchText)) {
                                searchList.add(userInfoWithTeam);
                            }
                        }
                        if (searchList.size() > 0) {
                            mAdapter.setDatas(searchList);
                        } else {
                            ArrayList<EmptyDataVo> emptyDataVos = new ArrayList<>();
                            emptyDataVos.add(new EmptyDataVo(R.mipmap.img_empty_data_2));
                            mAdapter.setDatas(emptyDataVos);
                        }
                    } else {
                        mAdapter.setDatas(teamUserList);
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    ArrayList<EmptyDataVo> emptyDataVos = new ArrayList<>();
                    emptyDataVos.add(new EmptyDataVo(R.mipmap.img_empty_data_2));
                    mAdapter.setDatas(emptyDataVos);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        getMemberList();
    }

    private List<UserInfoWithTeam> teamUserList;
    private void getMemberList() {
        *//*NIMClient.getService(TeamService.class).queryMemberList(teamInfo.getId()).setCallback(new RequestCallbackWrapper<List<TeamMember>>() {
            @Override
            public void onResult(int code, final List<TeamMember> members, Throwable exception) {
                if (members != null){
                    mAdapter.addAllData(members);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });*//*

        TeamRepo.getMemberList(teamInfo.getId(),
                new FetchCallback<List<UserInfoWithTeam>>() {
                    @Override
                    public void onSuccess(@Nullable List<UserInfoWithTeam> param) {
                        showSuccess();
                        teamUserList = param;
                        if (param != null){
                            mAdapter.setDatas(teamUserList);
                        }else {
                            mAdapter.addData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailed(int code) {
                        showSuccess();
                    }

                    @Override
                    public void onException(@Nullable Throwable exception) {
                        showSuccess();
                    }
                });
    }*/


}
