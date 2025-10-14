package com.zqhy.app.core.view.community.task;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqhy.app.base.BaseListFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.base.holder.AbsHolder;
import com.zqhy.app.base.holder.AbsItemHolder;
import com.zqhy.app.config.Constants;
import com.zqhy.app.core.AppJumpAction;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.jump.AppBaseJumpInfoBean;
import com.zqhy.app.core.data.model.nodata.EmptyDataVo;
import com.zqhy.app.core.data.model.user.CommunityDayTaskListVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.tool.ScreenUtil;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.user.newvip.NewUserVipFragment;
import com.zqhy.app.core.vm.community.task.TaskViewModel;
import com.zqhy.app.newproject.R;

/**
 * @author Administrator
 * @date 2018/11/21
 */

public class DailyTaskFragment extends BaseListFragment<TaskViewModel> {

    public static DailyTaskFragment newInstance() {
        DailyTaskFragment fragment = new DailyTaskFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Object getStateEventKey() {
        return Constants.EVENT_KEY_GAME_COUPON_LIST_STATE;
    }

    @Override
    protected String getStateEventTag() {
        return "";
    }

    @Override
    protected boolean isAddStatusBarLayout() {
        return true;
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new BaseRecyclerAdapter.Builder<>()
                .bind(CommunityDayTaskListVo.DataBean.class, new TaskModuleItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(_mActivity);
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("每日任务");
        setTitleLayout(LAYOUT_ON_CENTER);
        getNetWorkData();
        setListViewBackgroundColor(ContextCompat.getColor(_mActivity, R.color.white));
        addHeaderView();
    }

    View mHeaderView;

    private void addHeaderView() {
        mHeaderView = LayoutInflater.from(_mActivity).inflate(R.layout.layout_daily_task_head, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ScreenUtil.getScreenWidth(_mActivity), ViewGroup.LayoutParams.WRAP_CONTENT);
        mHeaderView.setLayoutParams(params);
        mHeaderView.setOnClickListener(view -> {
            startFragment(new NewUserVipFragment());
        });
        addHeaderView(mHeaderView);
    }


    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        getNetWorkData();
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getNetWorkData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getNetWorkData();
    }


    private void getNetWorkData() {
        if (mViewModel != null) {
            mViewModel.getCommunityDayTaskList(new OnBaseCallback<CommunityDayTaskListVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    refreshAndLoadMoreComplete();
                }

                @Override
                public void onSuccess(CommunityDayTaskListVo data) {
                    if (data != null) {
                        if (data.isStateOK() && data.getData() != null) {
                            clearData();
                            addAllData(data.getData());
                        }else {
                            addDataWithNotifyData(new EmptyDataVo(R.mipmap.img_empty_data_2));
                        }
                    }
                }
            });
        }
    }

    private void communityDayGetReward(String id) {
        if (mViewModel != null) {
            mViewModel.communityDayGetReward(id, new OnBaseCallback<BaseVo>() {

                @Override
                public void onAfter() {
                    super.onAfter();
                }

                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            getNetWorkData();
                        }
                    }
                }
            });
        }
    }

    private void showTaskTipDialog(CommunityDayTaskListVo.DataBean item) {
        CustomDialog taskTipDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_task_tip, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        TextView mTvTaskTitle = taskTipDialog.findViewById(R.id.tv_task_title);
        TextView mTvTaskSubTitle = taskTipDialog.findViewById(R.id.tv_task_sub_title);
        TextView mTvTaskReward = taskTipDialog.findViewById(R.id.tv_task_reward);
        TextView mTvTaskProcess = taskTipDialog.findViewById(R.id.tv_task_process);
        TextView mBtnTxt1 = taskTipDialog.findViewById(R.id.btn_txt_1);
        TextView mBtnTxt2 = taskTipDialog.findViewById(R.id.btn_txt_2);
        ImageView mIcon = taskTipDialog.findViewById(R.id.icon);


        mIcon.setImageResource(R.mipmap.ic_task_dialog_game_comment);
        mTvTaskTitle.setText(item.getName());
        mTvTaskSubTitle.setText(item.getDescription());
        mTvTaskReward.setText(item.getIntegral() + "积分/日; 会员+" + item.getSuper_user_reward() + "积分");

        mTvTaskProcess.setText(Html.fromHtml(item.getContent()));

        if (TextUtils.isEmpty(item.getBtn_label())){
            mBtnTxt1.setText("我知道了");
        }else {
            mBtnTxt1.setText(item.getBtn_label());
        }
        mBtnTxt1.setOnClickListener(view -> {
            new AppJumpAction(_mActivity).jumpAction(new AppBaseJumpInfoBean(item.getPage_type(), item.getParam()));
            if (taskTipDialog != null && taskTipDialog.isShowing()) {
                taskTipDialog.dismiss();
            }
        });

        if (TextUtils.isEmpty(item.getPage_type())) {
            mBtnTxt2.setVisibility(View.GONE);
        } else {
            mBtnTxt2.setVisibility(View.VISIBLE);
            mBtnTxt2.setText("取消");
            mBtnTxt2.setOnClickListener(view -> {
                if (taskTipDialog != null && taskTipDialog.isShowing()) {
                    taskTipDialog.dismiss();
                }
            });
        }

        taskTipDialog.show();
    }

    class TaskModuleItemHolder extends AbsItemHolder<CommunityDayTaskListVo.DataBean, TaskModuleItemHolder.ViewHolder> {

        public TaskModuleItemHolder(Context context) {
            super(context);
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_task_daily;
        }

        @Override
        public TaskModuleItemHolder.ViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }

        @Override
        protected void onBindViewHolder(@NonNull TaskModuleItemHolder.ViewHolder holder, @NonNull CommunityDayTaskListVo.DataBean item) {
            holder.mTvTitle.setText(item.getName());
            holder.mTvContent.setText("任务：" + item.getDescription());
            holder.mTvIntegral.setText("奖励：" + item.getIntegral() + "积分；会员");
            holder.mTvSuperIntegral.setText("+" + item.getSuper_user_reward() + "积分");
            if ("no_finish".equals(item.getStatus())){//未完成
                holder.mTvStatus.setText("待完成");
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_ff3d63_big_radius_with_line);
                holder.mTvStatus.setTextColor(Color.parseColor("#FF3D63"));
                holder.mTvStatus.setEnabled(true);
            }else if ("finish".equals(item.getStatus())){//完成任务，但未领取奖励
                holder.mTvStatus.setText("领取奖励");
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_ffa530_ff3572_big_radius);
                holder.mTvStatus.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mTvStatus.setEnabled(true);
            }else if ("is_get_reward".equals(item.getStatus())){//已领取奖励
                holder.mTvStatus.setText("今日已领");
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_ffddba_big_radius);
                holder.mTvStatus.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mTvStatus.setEnabled(false);
            }
            holder.mTvStatus.setOnClickListener(v -> {
                if (item.getStatus().equals("finish")) {
                    communityDayGetReward(String.valueOf(item.getId()));
                }else if(item.getStatus().equals("no_finish")){
                    showTaskTipDialog(item);
                }
            });
        }

        public class ViewHolder extends AbsHolder {

            private TextView mTvTitle;
            private TextView mTvContent;
            private TextView mTvIntegral;
            private TextView mTvSuperIntegral;
            private TextView mTvStatus;

            public ViewHolder(View view) {
                super(view);
                mTvTitle = view.findViewById(R.id.tv_title);
                mTvContent = view.findViewById(R.id.tv_content);
                mTvIntegral = view.findViewById(R.id.tv_integral);
                mTvSuperIntegral = view.findViewById(R.id.tv_super_integral);
                mTvStatus = view.findViewById(R.id.tv_status);
            }
        }
    }
}
