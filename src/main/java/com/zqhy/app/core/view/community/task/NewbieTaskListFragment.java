package com.zqhy.app.core.view.community.task;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import com.box.other.hjq.toast.Toaster;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zqhy.app.base.BaseFragment;
import com.zqhy.app.base.BaseRecyclerAdapter;
import com.zqhy.app.core.data.model.BaseVo;
import com.zqhy.app.core.data.model.community.UserIntegralVo;
import com.zqhy.app.core.data.model.community.task.TaskActionInfoBean;
import com.zqhy.app.core.data.model.community.task.TaskItemVo;
import com.zqhy.app.core.inner.OnBaseCallback;
import com.zqhy.app.core.ui.dialog.CustomDialog;
import com.zqhy.app.core.view.community.integral.CommunityIntegralMallFragment;
import com.zqhy.app.core.view.community.task.dialog.TaskDialogHelper;
import com.zqhy.app.core.view.community.task.holder.TaskListItemHolder;
import com.zqhy.app.core.view.user.UserInfoFragment;
import com.zqhy.app.core.vm.community.task.TaskViewModel;
import com.zqhy.app.model.UserInfoModel;
import com.zqhy.app.newproject.R;
import com.zqhy.app.utils.CommonUtils;
import com.zqhy.app.widget.listener.MyAppBarStateChangeListener;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Administrator
 */
public class NewbieTaskListFragment extends BaseFragment<TaskViewModel> implements View.OnClickListener {


    private static final int ACTION_USER_INFO_MODIFICATION = 0x1001;

    @Override
    public Object getStateEventKey() {
        return null;
    }

    @Override
    protected String getUmengPageName() {
        return "新手任务";
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_newbie_task;
    }

    @Override
    public int getContentResId() {
        return R.id.swipe_refresh_layout;
    }


    @Override
    public void initView(Bundle state) {
        super.initView(state);
        initActionBackBarAndTitle("新手任务");
        setTitleBottomLine(View.GONE);
        bindViews();
        initData();
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsing;
    private ImageView mIvTaskInfoBg;
    private LinearLayout mLlTaskTag;
    private ImageView mIvTaskTag;
    private TextView mTvTaskType;
    private FrameLayout mLlTaskLayout;
    private TextView mTvTaskInfoDescription;
    private TextView mTvIntegralCount;
    private ImageView mIvRefreshIntegral;
    private FrameLayout mFlIntegralMall;
    private TextView mTvIntegralMall;
    private LinearLayout mLlGameTitle;
    private RecyclerView mRecyclerView;

    private void bindViews() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mAppBarLayout = findViewById(R.id.appBarLayout);
        mCollapsing = findViewById(R.id.collapsing);
        mIvTaskInfoBg = findViewById(R.id.iv_task_info_bg);
        mLlTaskTag = findViewById(R.id.ll_task_tag);
        mIvTaskTag = findViewById(R.id.iv_task_tag);
        mTvTaskType = findViewById(R.id.tv_task_type);
        mLlTaskLayout = findViewById(R.id.ll_task_layout);
        mTvTaskInfoDescription = findViewById(R.id.tv_task_info_description);
        mTvIntegralCount = findViewById(R.id.tv_integral_count);
        mIvRefreshIntegral = findViewById(R.id.iv_refresh_integral);
        mFlIntegralMall = findViewById(R.id.fl_integral_mall);
        mTvIntegralMall = findViewById(R.id.tv_integral_mall);
        mLlGameTitle = findViewById(R.id.ll_game_title);
        mRecyclerView = findViewById(R.id.recyclerView);

        setCollapsingListener();
        setLayoutViews();
        setViewListeners();
        setSwipeRefresh();

        initList();
        mTaskDialogHelper = new TaskDialogHelper(_mActivity, (taskInfoBean) -> {
            getKnowTaskReward(taskInfoBean);
        });
    }

    private BaseRecyclerAdapter mAdapter;

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new BaseRecyclerAdapter.Builder()
                .bind(TaskItemVo.DataBean.class, new TaskListItemHolder(_mActivity))
                .build()
                .setTag(R.id.tag_fragment, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setLayoutViews() {
        GradientDrawable taskTagGd = new GradientDrawable();
        float radius = density * 16;
        taskTagGd.setCornerRadii(new float[]{0, 0, radius, radius, radius, radius, 0, 0});

        setTitleText("新手任务");
        mTvTaskInfoDescription.setText(getAppNameByXML(R.string.string_description_task_info_new));
        mTvTaskInfoDescription.setShadowLayer(2, 2, 2, Color.parseColor("#8A23B1D9"));
        mIvTaskInfoBg.setImageResource(R.mipmap.img_task_center_new);

        taskTagGd.setColor(Color.parseColor("#8A23B1D9"));
        mTvTaskType.setText("新手任务");
        mIvTaskTag.setImageResource(R.mipmap.ic_task_type_icon_new);

        mTvIntegralCount.setTextColor(Color.parseColor("#FF8F19"));
        mIvRefreshIntegral.setImageResource(R.mipmap.ic_integral_refresh_orange);
        mTvIntegralMall.setTextColor(Color.parseColor("#FF8F19"));
        mTvIntegralMall.setCompoundDrawablesWithIntrinsicBounds(_mActivity.getResources().getDrawable(R.mipmap.ic_integral_mall_orange), null, null, null);
        mLlTaskTag.setBackground(taskTagGd);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(16 * density);
        gd.setColor(ContextCompat.getColor(_mActivity, R.color.color_f5f5f5));
        mFlIntegralMall.setBackground(gd);
    }

    private void setSwipeRefresh() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //设置样式刷新显示的位置
        mSwipeRefreshLayout.setProgressViewOffset(true, -20, 100);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            initData();
        });
    }

    private void setCollapsingListener() {
        mAppBarLayout.addOnOffsetChangedListener(new MyAppBarStateChangeListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (i >= 0) {
                    mSwipeRefreshLayout.setEnabled(true);
                } else {
                    mSwipeRefreshLayout.setEnabled(false);
                }

                float totalRange = appBarLayout.getTotalScrollRange();
                float verticalOffset = Math.abs(i);

                int alpha = Math.round(verticalOffset / totalRange * 255);
                String hex = Integer.toHexString(alpha).toUpperCase();
                if (hex.length() == 1) {
                    hex = "0" + hex;
                }
                String srtColorRes = "#" + hex + "FFFFFF";
                try {
                    mLlGameTitle.setBackgroundColor(Color.parseColor(srtColorRes));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.onOffsetChanged(appBarLayout, i);
            }

            @Override
            public void onStateChanged(AppBarLayout appBarLayout, MyAppBarStateChangeListener.State state) {
                switch (state) {
                    case EXPANDED:
                        setExpandedTitleView();
                        break;
                    case COLLAPSED:
                        setCollapsedTitleView();
                        break;
                    case IDLE:
                        setTitleColor(ContextCompat.getColor(_mActivity, R.color.color_818181));
                        break;
                    default:
                        break;
                }
            }
        });
        setExpandedTitleView();
    }

    private void setExpandedTitleView() {
        setTitleColor(ContextCompat.getColor(_mActivity, R.color.white));
        mLlGameTitle.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.transparent));
        setActionBackBar(R.mipmap.ic_actionbar_back_white);
        setStatusBar(0x00cccccc);
        setSubTitle();
    }

    private void setCollapsedTitleView() {
        setTitleColor(ContextCompat.getColor(_mActivity, R.color.color_1b1b1b));
        mLlGameTitle.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.white));
        setActionBackBar(trecyclerview.com.mvvm.R.mipmap.ic_actionbar_back);
        setStatusBar(0xffcccccc);
        setSubTitle();
    }

    public void setSubTitle() {
        setTitleText("新手任务");
    }

    private void setViewListeners() {
        mFlIntegralMall.setOnClickListener(this);
        mIvRefreshIntegral.setOnClickListener(this);
    }

    private void setViewData(List<TaskItemVo.DataBean> data) {
        if (data == null) {
            return;
        }
        mAdapter.clear();
        mAdapter.notifyDataSetChanged();
        mAdapter.addAllData(data);
    }


    private TaskDialogHelper mTaskDialogHelper;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_integral_mall:
                //积分商城
                start(new CommunityIntegralMallFragment());
                break;
            case R.id.iv_refresh_integral:
                //刷新积分
                if (checkLogin()) {
                    refreshUserIntegralData();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onUserReLogin() {
        super.onUserReLogin();
        initData();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ACTION_USER_INFO_MODIFICATION:
                    initData();
                    break;
                default:
                    break;
            }
        }
    }

    public void actionTask(TaskItemVo.DataBean taskInfoBean) {
        if (taskInfoBean == null) {
            return;
        }
        switch (taskInfoBean.getTask_status()) {
            case 0:
                //进行中
                doingTaskWork(taskInfoBean);
                break;
            case 1:
                //领取任务奖励
                completeTaskWork(taskInfoBean);
                break;
            default:
                break;
        }
    }

    private void doingTaskWork(TaskItemVo.DataBean taskInfoBean) {
        StringBuilder sb = new StringBuilder();
        sb.append(taskInfoBean.getTask_name())
                .append("(")
                .append(String.valueOf(taskInfoBean.getFinished_count()))
                .append("/")
                .append(String.valueOf(taskInfoBean.getTask_count()))
                .append(")");

        TaskActionInfoBean taskTipInfoBean = getTaskTipByID(taskInfoBean.getTid());
        if (taskTipInfoBean != null) {
            taskTipInfoBean.setTaskTipTitle(sb.toString());
            taskTipInfoBean.setTask_type(taskInfoBean.getTask_type());
            taskTipInfoBean.setTaskInfoBean(taskInfoBean);

            if (taskInfoBean.getTask_type() == 3) {
                String taskProgress = taskTipInfoBean.getTaskTipProcess();
                if (!TextUtils.isEmpty(taskProgress)) {
                    taskTipInfoBean.setTaskTipProcess(taskProgress.replace("$", String.valueOf(taskInfoBean.getTask_count())));
                }
            }
            if (taskTipInfoBean.isShowDialog == 1) {
                showTaskTipDialog(taskTipInfoBean);
            } else {
                taskDialogBtnClick(taskTipInfoBean.getAction_without_dialog(), taskTipInfoBean.getTaskInfoBean());
            }
        }
    }

    private TextView mTvTaskTitle;
    private TextView mTvTaskProcess;
    private TextView mBtnTxt1;
    private TextView mBtnTxt2;
    private View mViewMidLine;

    private CustomDialog taskTipDialog;

    protected void showTaskTipDialog(TaskActionInfoBean taskTipInfoBean) {
        if (taskTipDialog == null) {
            taskTipDialog = new CustomDialog(_mActivity, LayoutInflater.from(_mActivity).inflate(R.layout.layout_dialog_task_tip, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

            LinearLayout mLlRootView = taskTipDialog.findViewById(R.id.ll_rootView);

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(4 * density);
            gd.setColor(ContextCompat.getColor(_mActivity, R.color.white));
            gd.setStroke((int) (0.5 * density), ContextCompat.getColor(_mActivity, R.color.color_dcdcdc));

            mLlRootView.setBackground(gd);

            mTvTaskTitle = taskTipDialog.findViewById(R.id.tv_task_title);
            mTvTaskProcess = taskTipDialog.findViewById(R.id.tv_task_process);
            mBtnTxt1 = taskTipDialog.findViewById(R.id.btn_txt_1);
            mBtnTxt2 = taskTipDialog.findViewById(R.id.btn_txt_2);
            mViewMidLine = taskTipDialog.findViewById(R.id.view_mid_line);
        }

        mTvTaskTitle.setText(taskTipInfoBean.getTaskTipTitle());
        mTvTaskProcess.setText(taskTipInfoBean.getTaskTipProcess());

        if (TextUtils.isEmpty(taskTipInfoBean.getBtnTxt1())) {
            mBtnTxt1.setVisibility(View.GONE);
        } else {
            mBtnTxt1.setVisibility(View.VISIBLE);
            mBtnTxt1.setText(taskTipInfoBean.getBtnTxt1());
            mBtnTxt1.setOnClickListener(view -> {
                taskDialogBtnClick(taskTipInfoBean.getBtnTxt1Action());
            });
        }

        if (TextUtils.isEmpty(taskTipInfoBean.getBtnTxt2())) {
            mBtnTxt2.setVisibility(View.GONE);
            mViewMidLine.setVisibility(View.GONE);
        } else {
            mBtnTxt2.setVisibility(View.VISIBLE);
            mViewMidLine.setVisibility(View.VISIBLE);
            mBtnTxt2.setText(taskTipInfoBean.getBtnTxt2());
            mBtnTxt2.setOnClickListener(view -> {
                taskDialogBtnClick(taskTipInfoBean.getBtnTxt2Action(), taskTipInfoBean.getTaskInfoBean());
            });
        }

        taskTipDialog.show();
    }

    protected void taskDialogBtnClick(int action) {
        taskDialogBtnClick(action, null);
    }

    protected void taskDialogBtnClick(int action, TaskItemVo.DataBean taskInfoBean) {
        if (taskTipDialog != null && taskTipDialog.isShowing()) {
            taskTipDialog.dismiss();
        }
        switch (action) {
            case 1:
                break;
            case 13:
                //13 无弹窗--跳转个人资料
                if (checkLogin()) {
                    //if(BuildConfig.APP_TEMPLATE != 9999){
                        startForResult(new UserInfoFragment(), ACTION_USER_INFO_MODIFICATION);
                    //}
                }
                break;
            case 14:
            case 15:
            case 18:
                if (checkLogin()) {
                    if (mTaskDialogHelper != null) {
                        mTaskDialogHelper.showTaskDialog(action, taskInfoBean);
                    }
                }
                break;
            default:
                break;
        }
    }


    private TaskActionInfoBean getTaskTipByID(int tid) {
        try {
            String json = CommonUtils.getJsonDataFromAsset(_mActivity, "json/task_tips_list.json");

            Type listType = new TypeToken<List<TaskActionInfoBean>>() {
            }.getType();
            Gson gson = new Gson();
            List<TaskActionInfoBean> taskTipInfoBeanList = gson.fromJson(json, listType);
            if (taskTipInfoBeanList != null) {
                for (TaskActionInfoBean taskTipInfoBean : taskTipInfoBeanList) {
                    if (tid == taskTipInfoBean.getTid()) {
                        return taskTipInfoBean;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 领取任务
     *
     * @param taskInfoBean
     */
    private void completeTaskWork(TaskItemVo.DataBean taskInfoBean) {
        getTaskReward(taskInfoBean);
    }


    private void initData() {
        getTaskNewData();
        setUserIntegral();
    }

    private void setUserIntegral() {
        if (UserInfoModel.getInstance().isLogined()) {
            int integralCount = UserInfoModel.getInstance().getUserInfo().getIntegral();
            mTvIntegralCount.setText(String.valueOf(integralCount));
        } else {
            mTvIntegralCount.setText(String.valueOf(0));
        }
    }

    private void getTaskNewData() {
        if (mViewModel != null) {
            mViewModel.getTaskNewData(new OnBaseCallback<TaskItemVo>() {
                @Override
                public void onAfter() {
                    super.onAfter();
                    showSuccess();
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onSuccess(TaskItemVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            setViewData(data.getData());
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void getTaskReward(TaskItemVo.DataBean taskInfoBean) {
        if (mViewModel != null) {
            mViewModel.getTaskReward(taskInfoBean.getTid(), new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            Toaster.show( "恭喜，获得" + taskInfoBean.getReward_integral() + "积分！");
                            setFragmentResult(RESULT_OK, null);
                            mViewModel.refreshUserData();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void getKnowTaskReward(TaskItemVo.DataBean taskInfoBean) {
        if (taskInfoBean == null) {
            return;
        }
        if (mViewModel != null) {
            mViewModel.getKnowTaskReward(taskInfoBean.getTid(), new OnBaseCallback() {
                @Override
                public void onSuccess(BaseVo data) {
                    if (data != null) {
                        if (data.isStateOK()) {
                            setFragmentResult(RESULT_OK, null);
                            refreshUserData();
                        } else {
                            Toaster.show( data.getMsg());
                        }
                    }
                }
            });
        }
    }

    private void refreshUserData() {
        if (mViewModel != null) {
            mViewModel.refreshUserData();
        }
    }

    private void refreshUserIntegralData() {
        if (mViewModel != null) {
            mViewModel.getUserIntegral(new OnBaseCallback<UserIntegralVo>() {

                @Override
                public void onBefore() {
                    super.onBefore();
                    loading();
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    loadingComplete();
                }

                @Override
                public void onSuccess(UserIntegralVo data) {
                    if (data != null) {
                        if (data.isStateOK() && data.getData() != null) {
                            mTvIntegralCount.setText(String.valueOf(data.getData().getIntegral()));
                        }
                    }
                }
            });
        }
    }
}
