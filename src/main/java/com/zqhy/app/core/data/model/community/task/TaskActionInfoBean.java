package com.zqhy.app.core.data.model.community.task;

/**
 * @author Administrator
 */
public class TaskActionInfoBean {
    private int tid;
    private String taskTipTitle;
    private String taskTipProcess;
    private String btnTxt1;
    private String btnTxt2;
    /**
     * 点击响应类型
     * 1 有弹窗
     * 11 有弹窗--跳转游戏大厅
     * 12 有弹窗--跳转我的问答
     * 13 无弹窗--跳转个人资料
     * 14 无弹窗--跳转省钱攻略
     * 15 无弹窗--跳转停运转游
     * 16 无弹窗--跳转邀请分享（注意半分享）
     * 17 无弹窗--跳转签到页面
     */
    private int btnTxt1Action;
    private int btnTxt2Action;
    /**
     * 1 显示   0不显示
     */
    public int isShowDialog;
    /**
     * 无弹窗跳转方式
     */
    private int action_without_dialog;

    private int task_type;

    private TaskItemVo.DataBean taskInfoBean;

    public TaskItemVo.DataBean getTaskInfoBean() {
        return taskInfoBean;
    }

    public void setTaskInfoBean(TaskItemVo.DataBean taskInfoBean) {
        this.taskInfoBean = taskInfoBean;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTaskTipTitle() {
        return taskTipTitle;
    }

    public void setTaskTipTitle(String taskTipTitle) {
        this.taskTipTitle = taskTipTitle;
    }

    public String getTaskTipProcess() {
        return taskTipProcess;
    }

    public void setTaskTipProcess(String taskTipProcess) {
        this.taskTipProcess = taskTipProcess;
    }

    public String getBtnTxt1() {
        return btnTxt1;
    }

    public void setBtnTxt1(String btnTxt1) {
        this.btnTxt1 = btnTxt1;
    }

    public String getBtnTxt2() {
        return btnTxt2;
    }

    public void setBtnTxt2(String btnTxt2) {
        this.btnTxt2 = btnTxt2;
    }

    public int getBtnTxt1Action() {
        return btnTxt1Action;
    }

    public void setBtnTxt1Action(int btnTxt1Action) {
        this.btnTxt1Action = btnTxt1Action;
    }

    public int getBtnTxt2Action() {
        return btnTxt2Action;
    }

    public void setBtnTxt2Action(int btnTxt2Action) {
        this.btnTxt2Action = btnTxt2Action;
    }

    public int getIsShowDialog() {
        return isShowDialog;
    }

    public void setIsShowDialog(int isShowDialog) {
        this.isShowDialog = isShowDialog;
    }

    public int getAction_without_dialog() {
        return action_without_dialog;
    }

    public void setAction_without_dialog(int action_without_dialog) {
        this.action_without_dialog = action_without_dialog;
    }

    public int getTask_type() {
        return task_type;
    }

    public void setTask_type(int task_type) {
        this.task_type = task_type;
    }

    @Override
    public String toString() {
        return "TaskActionInfoBean{" +
                "tid=" + tid +
                ", taskTipTitle='" + taskTipTitle + '\'' +
                ", taskTipProcess='" + taskTipProcess + '\'' +
                ", btnTxt1='" + btnTxt1 + '\'' +
                ", btnTxt2='" + btnTxt2 + '\'' +
                ", btnTxt1Action=" + btnTxt1Action +
                ", btnTxt2Action=" + btnTxt2Action +
                ", isShowDialog=" + isShowDialog +
                ", action_without_dialog=" + action_without_dialog +
                ", task_type=" + task_type +
                ", taskInfoBean=" + taskInfoBean +
                '}';
    }
}
