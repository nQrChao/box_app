package com.zqhy.app.core.view.message;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.adapter.AdapterPool;
import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.data.model.community.CommunityInfoVo;
import com.zqhy.app.core.data.model.message.InteractiveMessageListVo;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.user.UserInfoVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.vm.message.MessageViewModel;
import com.zqhy.app.db.table.message.MessageDbInstance;
import com.zqhy.app.db.table.message.MessageVo;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/14
 */

public class MessageListFragment extends BaseListFragment<MessageViewModel> {

    public static MessageListFragment newInstance(int message_type) {
        MessageListFragment fragment = new MessageListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("message_type", message_type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private static final int MESSAGE_TYPE_CUSTOMER = 1;
    private static final int MESSAGE_TYPE_REPLY = 2;
    private static final int MESSAGE_TYPE_PRAISE = 3;
    private static final int MESSAGE_TYPE_SYSTEM = 4;


    private int message_type = 0;

    private int page = 1;
    private int pageCount = 12;


    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_MESSAGE_LIST_STATE;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return AdapterPool.newInstance()
                .getMessageListAdapter(_mActivity)
                .setTag(R.id.tag_first, message_type)
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }

    @Override
    public int getPageCount() {
        return pageCount;
    }

    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            message_type = getArguments().getInt("message_type", message_type);
        }
        super.initView(state);
        initActionBackBarAndTitle("");
        setPullRefreshEnabled(false);
        setLoadingMoreEnabled(false);
        switch (message_type) {
            case MESSAGE_TYPE_CUSTOMER:
                setTitleText("客服消息");
                break;
            case MESSAGE_TYPE_REPLY:
                setTitleText("互动消息");
                break;
            case MESSAGE_TYPE_PRAISE:
                setTitleText("系统消息");
                break;
            case MESSAGE_TYPE_SYSTEM:
                setTitleText("游戏动态");
                break;
            default:
                break;
        }
        initData();

        Bundle bundle = new Bundle();
        bundle.putInt("message_type", message_type);
        setFragmentResult(RESULT_OK, bundle);
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        initData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        if (message_type == 2) {
            //点评互动
            page = 1;
            getCommentMessages();
        }
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (message_type == 2) {
            if (page < 0) {
                return;
            }
            page++;
            getCommentMessages();
        }
    }


    private void initData() {
        if (message_type == 1) {
            //客服消息
            getKefuMessages();
        } else if (message_type == 2) {
            setPullRefreshEnabled(true);
            setLoadingMoreEnabled(true);
            //点评互动
            page = 1;
            getCommentMessages();
        } else if (message_type == 3) {
            //系统消息
            getSystemMessages();
        } else if (message_type == 4) {
            //游戏动态
            getDynamicGameMessages();
        }
    }

    /**
     * 数据转换成MessageVo
     *
     * @param dataBeanList
     * @return
     */
    @NonNull
    private List<MessageVo> getMessageVos(List<InteractiveMessageListVo.DataBean> dataBeanList) {
        List<MessageVo> messageList = new ArrayList<>();
        for (InteractiveMessageListVo.DataBean dataBean : dataBeanList) {
            MessageVo messageBean = new MessageVo();
            int comment_id = dataBean.getCid();
            messageBean.setComment_id(comment_id);
            messageBean.setQuestion_id(dataBean.getQid());
            messageBean.setAction_type(dataBean.getType());
            messageBean.setIs_go_the_original(1);

            CommunityInfoVo communityInfoVo = dataBean.getCommunity_info();
            String message_title = "";
            if (communityInfoVo != null) {
                if (dataBean.getType() == 1 ) {
                    //点评赞
                    message_title = communityInfoVo.getUser_nickname() + " 赞了我的点评";
                } else if (dataBean.getType() == 2) {
                    //回复
                    message_title = communityInfoVo.getUser_nickname() + " 回复了我";
                } else if(dataBean.getType() == 3){
                    //点评赞
                    message_title = communityInfoVo.getUser_nickname() + " 赞了我的回复";
                }else if(dataBean.getType() == 4){
                    //回答赞
                    message_title = communityInfoVo.getUser_nickname() + " 赞了我的回答";
                }
            }
            messageBean.setMessage_title(message_title);
            messageBean.setMessage_content(dataBean.getContent());
            messageBean.setMessage_time(dataBean.getAdd_time());
            //message_type
            messageBean.setMessage_type(2);
            //设置已读
            messageBean.setMessage_is_read(1);

            UserInfoVo.DataBean userInfo = UserInfoModel.getInstance().getUserInfo();
            try {
                if (userInfo != null) {
                    messageBean.setUid(userInfo.getUid());
                } else {
                    messageBean.setUid(-1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            messageList.add(messageBean);
        }
        return messageList;
    }

    /**
     * 获取游戏动态消息
     * （本地数据库）
     */
    private void getKefuMessages() {
        int message_type = 1;
        getMessageByType(message_type);
    }

    /**
     * 获取游戏动态消息
     * （本地数据库）
     */
    private void getSystemMessages() {
        int message_type = 3;
        getMessageByType(message_type);
    }

    /**
     * 获取游戏动态消息
     * （本地数据库）
     */
    private void getDynamicGameMessages() {
        int message_type = 4;
        getMessageByType(message_type);
    }

    /**
     * 获取本地数据库数据
     *
     * @param message_type
     */
    private void getMessageByType(int message_type) {
        showSuccess();
        Runnable runnable = () -> {
            List<MessageVo> messageInfoBeanList = MessageDbInstance.getInstance().getAllMessages(message_type);
            _mActivity.runOnUiThread(() -> {
                if (!messageInfoBeanList.isEmpty()) {
                    mDelegateAdapter.clear();
                    mDelegateAdapter.addAllData(messageInfoBeanList);
                    mDelegateAdapter.notifyDataSetChanged();
                } else {
                    mDelegateAdapter.addData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                }
            });
        };
        new Thread(runnable).start();
    }

    /**
     * 获取点评互动消息（回复/点赞）
     */
    private void getCommentMessages() {
        if (mViewModel != null) {

            mViewModel.getCommentMessages(page, pageCount, new OnBaseCallback<InteractiveMessageListVo>() {
                @Override
                public void onBefore() {
                    if (page == 1) {
                        setListNoMore(false);
                    }
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(InteractiveMessageListVo messageDbListVo) {
                    refreshAndLoadMoreComplete();
                    if (messageDbListVo != null) {
                        if (messageDbListVo.isStateOK()) {
                            if (messageDbListVo.getData() != null) {
                                if (page == 1) {
                                    clearData();
                                }
                                List<MessageVo> messageList = getMessageVos(messageDbListVo.getData());
                                addAllData(messageList);
                            } else {
                                if (page == 1) {
                                    clearData();
                                    // empty data
                                    addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_1));
                                } else {
                                    page = -1;
                                }
                                setListNoMore(true);
                            }
                        } else {
                            Toaster.show( messageDbListVo.getMsg());
                        }
                    }
                }
            });
        }
    }

}
