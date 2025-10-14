package com.zqhy.app.core.view.game.forum;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaoji.other.hjq.toast.Toaster;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.config.Constants;
import com.zqhy.app.config.EventConfig;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.ui.eventbus.EventCenter;
import com.zqhy.app.core.view.game.forum.holder.ForumDraftsHolder;
import com.zqhy.app.core.view.user.welfare.holder.EmptyItemHolder;
import com.zqhy.app.core.vm.game.ForumViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2018/11/23
 */

public class ForumDraftsFragment extends BaseFragment<ForumViewModel> implements ForumDraftsHolder.ClickDelete {

    public static ForumDraftsFragment newInstance(String gameId,String gameIcon,String gameName) {
        ForumDraftsFragment fragment = new ForumDraftsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gameId", gameId);
        bundle.putString("gameIcon", gameIcon);
        bundle.putString("gameName", gameName);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_GAME_DETAIL_STATE;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_forum_drafts;
    }

    @Override
    public int getContentResId() {
        return R.id.ll_content_layout;
    }
    private RecyclerView mRecyclerView;
    String gameId =  "";
    String gameIcon = "";
    String gameName = "";
    @Override
    public void initView(Bundle state) {
        if (getArguments() != null) {
            gameId = getArguments().getString("gameId");
            gameIcon = getArguments().getString("gameIcon");
            gameName = getArguments().getString("gameName");
        }
        super.initView(state);
        showSuccess();
        findViewById(R.id.iv_back).setOnClickListener(v -> pop());
        mRecyclerView = findViewById(R.id.recyclerView);
        initList();
    }
    BaseRecyclerAdapter baseRecyclerAdapter ;
    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(layoutManager);
        baseRecyclerAdapter =  new BaseRecyclerAdapter.Builder()
                .bind(ForumDraftsBean.class, new ForumDraftsHolder(_mActivity,this))
                .bind(EmptyDataVo.class, new EmptyItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
        mRecyclerView.setAdapter(baseRecyclerAdapter);

        List<ForumDraftsBean> allHistory = LitePal.findAll(ForumDraftsBean.class);
        List<ForumDraftsBean> list = new ArrayList<>();
        for (ForumDraftsBean forumDraftsBean : allHistory) {
            if (forumDraftsBean.getUid()== UserInfoModel.getInstance().getUID()){
                list.add(forumDraftsBean);
            }
        }
        if (!list.isEmpty()) {
            baseRecyclerAdapter.addAllData(list);
        }else {
            baseRecyclerAdapter.addData(new EmptyDataVo(R.mipmap.img_empty_data_1));
        }
        baseRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDelete(ForumDraftsBean bean) {
        showDelete(bean);
    }

    @Override
    public void onEdit(ForumDraftsBean bean) {
        long baseId = bean.getBaseId();
        if (bean.getType()==1) {
            start(ForumPostLongFragment.newInstance(baseId));
        }else if (bean.getType()==2){
            start(ForumPostFragment.newInstance(baseId));
        }
    }
    @Override
    public void onEvent(EventCenter eventCenter) {
        super.onEvent(eventCenter);
        if (eventCenter.getEventCode() == EventConfig.FORUM_UPDATE_CODE||eventCenter.getEventCode() == EventConfig.FORUM_PUSH_CODE) {
            upDate();
        }

    }

    private void upDate(){
        List<ForumDraftsBean> allHistory = LitePal.findAll(ForumDraftsBean.class);
        List<ForumDraftsBean> list = new ArrayList<>();
        for (ForumDraftsBean forumDraftsBean : allHistory) {
            if (forumDraftsBean.getUid()== UserInfoModel.getInstance().getUID()){
                list.add(forumDraftsBean);
            }
        }
        baseRecyclerAdapter.clear();
        if (!list.isEmpty()) {
            baseRecyclerAdapter.addAllData(list);
        }else {
            baseRecyclerAdapter.addData(new EmptyDataVo(R.mipmap.img_empty_data_1));
        }
        baseRecyclerAdapter.notifyDataSetChanged();
    }
    private void showDelete(ForumDraftsBean bean) {
        CustomDialog tipsDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_forum_delete_drafts, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        tipsDialog.findViewById(R.id.tv_confirm).setOnClickListener(view -> {
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
        });
        tipsDialog.findViewById(R.id.tv_save).setOnClickListener(view -> {
            bean.delete();
            Toaster.show("草稿删除成功");
            //ToastT.success("草稿删除成功");
            if (tipsDialog != null && tipsDialog.isShowing()) tipsDialog.dismiss();
            upDate();
        });
        tipsDialog.show();
    }
}
